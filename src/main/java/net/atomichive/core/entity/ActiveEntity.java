package net.atomichive.core.entity;

import net.atomichive.core.Main;
import net.atomichive.core.entity.abilities.*;
import net.atomichive.core.entity.pathfinding.PathfinderGoalFollowEntity;
import net.atomichive.core.exception.AbilityException;
import net.atomichive.core.exception.AtomicEntityException;
import net.atomichive.core.util.NMSUtil;
import net.atomichive.core.util.SmartMap;
import net.atomichive.core.util.Util;
import net.minecraft.server.v1_12_R1.*;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftLivingEntity;
import org.bukkit.entity.Entity;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

/**
 * Active Entity
 * Represents an entity who is active in the world.
 * This class is currently volatile.
 */
@SuppressWarnings("WeakerAccess")
public class ActiveEntity {


    // Attributes
    private Entity entity;
    private PathfinderGoalSelector goals;
    private PathfinderGoalSelector targets;
    private Entity owner;

    // Abilities
    private List<Ability> abilities = new ArrayList<>();
    private List<GenericAbilityHandler> onAttack = new ArrayList<>();
    private List<GenericAbilityHandler> onDamage = new ArrayList<>();
    private List<TimedAbilityHandler> onTimer = new ArrayList<>();


    /**
     * Active Entity
     *
     * @param entity Bukkit entity.
     */
    public ActiveEntity (Entity entity) {
        this.entity = entity;
    }


    /**
     * Apply pathfinding
     * Handles pathfinding map from entities.json, and applies
     * custom pathfinding to the entity as necessary.
     *
     * @param pathfinding Pathfinding map.
     */
    public void applyPathfinding (Map pathfinding) {

        // Ensure pathfinding map has been defined.
        if (pathfinding == null)
            return;

        // Init
        EntityInsentient entity = (EntityInsentient) ((CraftLivingEntity) this.entity).getHandle();
        Field goalsField = NMSUtil.getPrivateField(EntityInsentient.class, "goalSelector");
        Field targetsField = NMSUtil.getPrivateField(EntityInsentient.class, "targetSelector");

        // Attempt to retrieve goal selectors
        try {
            this.goals = (PathfinderGoalSelector) goalsField.get(entity);
            this.targets = (PathfinderGoalSelector) targetsField.get(entity);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return;
        }


        // Handle pathfinding goals
        try {

            if (pathfinding.containsKey("goals"))
                handleGoals(entity, pathfinding.get("goals"));

            if (pathfinding.containsKey("targets"))
                handleTargets(entity, pathfinding.get("targets"));

        } catch (AtomicEntityException e) {
            Main.getInstance().log(Level.SEVERE, e.getMessage());
        }

    }


    /**
     * Handle goals
     * Parses and applies pathfinding goals.
     *
     * @param entity Entity to apply goals to.
     * @param goals  List of goals.
     * @throws AtomicEntityException if an exception occurs.
     */
    private void handleGoals (EntityInsentient entity, Object goals)
            throws AtomicEntityException {

        // Ensure goals is a list
        if (!List.class.isInstance(goals))
            throw new AtomicEntityException("Pathfinding goals could not be parsed as list.");

        // Iterate over goals
        for (Object obj : (List) goals) {

            // Ensure obj is a string
            if (!String.class.isInstance(obj)) {
                Main.getInstance().log(
                        Level.WARNING,
                        "A pathfinding goal could not be parsed as a string."
                );
                continue;
            }

            // Attempt to handle goal
            try {
                handleGoal(entity, (String) obj);
            } catch (AtomicEntityException e) {
                Main.getInstance().log(Level.WARNING, e.getMessage());
            }

        }

    }


    /**
     * Handle goal
     * Parses and applies a pathfinding goal.
     *
     * @param g      Pathfinding goal as a string.
     * @param entity Entity to apply goal to.
     */
    private void handleGoal (EntityInsentient entity, String g)
            throws AtomicEntityException {

        // Split at spaces
        String[] parts = g.split(" ");
        int priority;
        String goal;

        // Parse
        priority = parsePriority(parts[0]);
        goal = parts[1].toLowerCase();

        switch (goal) {
            case "clear":
                clear(this.goals);
                break;
            case "swim":
                this.goals.a(priority, new PathfinderGoalFloat(entity));
                break;
            case "look_around":
                this.goals.a(priority, new PathfinderGoalRandomLookaround(entity));
                break;
            case "random_stroll":
                if (entity instanceof EntityCreature) {
                    this.goals.a(priority, new PathfinderGoalRandomStroll((EntityCreature) entity, 1.0d));
                    break;
                }
                throw new AtomicEntityException("Only creatures can be assigned with the goal 'random_stroll'.");
            case "look_at_player":
                this.goals.a(priority, new PathfinderGoalLookAtPlayer(entity, EntityHuman.class, 8.0f));
                break;
            case "follow_owner":
                if (this.getOwner() != null) {
                    this.goals.a(priority, new PathfinderGoalFollowEntity(entity, this.getOwner(), 1.0d, 2.5f, 30.0f));
                    break;
                }
                throw new AtomicEntityException("Cannot follow owner; no owner assigned.");
            case "melee_attack":
                if (entity instanceof EntityCreature) {
                    this.goals.a(priority, new PathfinderGoalMeleeAttack((EntityCreature) entity, 1.0d, true));
                    break;
                }
                throw new AtomicEntityException("Only creatures can be assigned with the goal 'melee_attack'.");
            case "flee_player":
                if (entity instanceof EntityCreature) {
                    this.goals.a(priority, new PathfinderGoalAvoidTarget((EntityCreature) entity, EntityHuman.class, 5.0f, 1.0d, 1.2d));
                    break;
                }
                throw new AtomicEntityException("Only creatures can be assigned with the goal 'flee_player'");
            default:
                throw new AtomicEntityException("Unknown goal: " + goal);
        }

    }


    /**
     * Handle targets
     * Parses and applies pathfinding targets.
     *
     * @param targets List of targets.
     */
    private void handleTargets (EntityInsentient entity, Object targets) throws AtomicEntityException {

        // Ensure goals is a list
        if (!List.class.isInstance(targets))
            throw new AtomicEntityException("Pathfinding targets could not be parsed as list.");

        // Iterate over goals
        for (Object obj : (List) targets) {

            // Ensure obj is a string
            if (!String.class.isInstance(obj)) {
                Main.getInstance().log(
                        Level.WARNING,
                        "A pathfinding target could not be parsed as a string."
                );
                continue;
            }

            // Attempt to handle target
            try {
                handleTarget(entity, (String) obj);
            } catch (AtomicEntityException e) {
                Main.getInstance().log(Level.WARNING, e.getMessage());
            }

        }

    }


    /**
     * Handle target
     *
     * @param entity Entity to give pathfinding target.
     * @param t      Pathfinding target as string.
     * @throws AtomicEntityException if an exception is encountered.
     */
    private void handleTarget (EntityInsentient entity, String t)
            throws AtomicEntityException {

        // Split at spaces
        String[] parts = t.split(" ");
        int priority;
        String target;

        // Parse
        priority = parsePriority(parts[0]);
        target = parts[1].toLowerCase();

        switch (target) {
            case "clear":
                clear(this.targets);
                break;
            case "attacker":
                if (entity instanceof EntityCreature) {
                    this.targets.a(priority, new PathfinderGoalHurtByTarget((EntityCreature) entity, false, new Class[0]));
                    break;
                }
                throw new AtomicEntityException("Only creatures can be assigned with the target 'attacker'.");
            case "players":
                if (entity instanceof EntityCreature) {
                    this.targets.a(priority, new PathfinderGoalNearestAttackableTarget((EntityCreature) entity, EntityHuman.class, true));
                    break;
                }
                throw new AtomicEntityException("Only creatures can be assigned with the target 'players'.");
            default:
                throw new AtomicEntityException("Unknown target: " + target);
        }

    }


    /**
     * Parse priority
     *
     * @param str Priority as a string.
     * @return Parsed priority.
     * @throws AtomicEntityException if priority cannot be parsed.
     */
    private int parsePriority (String str) throws AtomicEntityException {

        // Ensure str is an integer
        if (!Util.isInteger(str))
            throw new AtomicEntityException("Pathfinding priority could not be parsed as an integer.");

        int priority = Integer.parseInt(str);

        // Ensure priority is positive
        if (priority < 0)
            throw new AtomicEntityException("Pathfinding priority cannot be negative.");

        return priority;

    }


    /**
     * Clear
     * Uses reflection to clear pathfinder goal selectors.
     *
     * @param selector Goal select to clear.
     */
    @SuppressWarnings("ConstantConditions")
    private void clear (PathfinderGoalSelector selector) {

        Field fieldB = NMSUtil.getPrivateField(PathfinderGoalSelector.class, "b");
        Field fieldC = NMSUtil.getPrivateField(PathfinderGoalSelector.class, "c");

        try {
            ((Set) fieldB.get(selector)).clear();
            ((Set) fieldC.get(selector)).clear();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }


    /**
     * Apply abilities
     * Parses and applies abilities to this entity.
     *
     * @param abilities A list of abilities strings.
     */
    public void applyAbilities (List abilities) {

        try {

            // Iterate over define abilities
            for (Object obj : abilities) {

                if (!(obj instanceof Map))
                    throw new AbilityException("Could not parse ability.");

                // Get attributes map
                SmartMap attributes = new SmartMap((Map) obj);
                Ability ability = getBaseAbility(attributes);


                // Get trigger and add ability
                String trigger = attributes.get(String.class, "trigger");
                int radius = attributes.getInteger("radius", 30);
                int ticks = attributes.getInteger("ticks", 10);

                Ability.Target target = Util.getEnumValue(
                        Ability.Target.class,
                        attributes.get(String.class, "target")
                );


                // Handle null trigger
                if (trigger == null) {
                    this.abilities.add(ability);
                }

                switch (trigger) {
                    case "on_attack":
                        this.onAttack.add(new GenericAbilityHandler(ability, target, radius));
                        break;
                    case "on_damage":
                        this.onDamage.add(new GenericAbilityHandler(ability, target, radius));
                        break;
                    case "on_timer":
                        this.onTimer.add(new TimedAbilityHandler(ability, target, radius, ticks));
                        break;
                    default:
                        this.abilities.add(ability);
                }

            }

        } catch (AbilityException e) {
            Main.getInstance().log(Level.SEVERE, e.getMessage());
        }

    }


    /**
     * Get base ability
     * Returns a base ability constructed from
     * an ability attributes map.
     *
     * @param attributes Ability attributes map.
     * @return Base ability
     * @throws AbilityException if no base ability is defined.
     */
    private Ability getBaseAbility (SmartMap attributes) throws AbilityException {

        // Get base as string
        String base = attributes.get(String.class, "base");

        // Ensure base was defined
        if (base == null)
            throw new AbilityException("No base ability defined.");

        switch (base.toLowerCase()) {
            case "effect":
                return new AbilityEffect(attributes);
            case "explode":
                return new AbilityExplode(attributes);
            case "fireball":
                return new AbilityFireball();
            case "ignite":
                return new AbilityIgnite(attributes);
            case "lightning":
                return new AbilityLightning();
            case "snowball":
                return new AbilitySnowball();
            case "summon":
                return new AbilitySummon(attributes);
            case "swap_places":
                return new AbilitySwapPlaces();
            case "throw":
                return new AbilityThrow(attributes);
            case "throw_block":
                return new AbilityThrowBlock(attributes);
            default:
                throw new AbilityException("Unknown base ability: '" + base + "'.");
        }

    }


    /**
     * Run on attack
     *
     * @param source Entity who attacked.
     * @param target Entity being attacked.
     */
    public void runOnAttack (Entity source, Entity target) {

        // Iterate over abilities
        for (GenericAbilityHandler ability : onAttack) {
            ability.run(source, target);
        }

    }


    /**
     * Run on damage
     *
     * @param source Entity who attacked.
     * @param target Entity being attacked.
     */
    public void runOnDamage (Entity source, Entity target) {

        // Iterate over abilities
        for (GenericAbilityHandler ability : onDamage) {
            ability.run(source, target);
        }

    }


    /**
     * Is
     *
     * @param entity Bukkit entity.
     * @return Whether this active entity represents the given
     * Bukkit entity.
     */
    public boolean is (Entity entity) {
        return this.entity.equals(entity);
    }

    /*
        Getters and setters.
     */

    public Entity getEntity () {
        return entity;
    }

    public Entity getOwner () {
        return owner;
    }

    public void setOwner (Entity owner) {
        this.owner = owner;
    }

    public List<TimedAbilityHandler> getOnTimer () {
        return onTimer;
    }

}
