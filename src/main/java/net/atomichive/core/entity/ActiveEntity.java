package net.atomichive.core.entity;

import net.atomichive.core.Main;
import net.atomichive.core.entity.abilities.*;
import net.atomichive.core.exception.CustomObjectException;
import net.atomichive.core.nms.NMSUtil;
import net.atomichive.core.nms.VolatileGoalSelector;
import net.atomichive.core.nms.goals.*;
import net.atomichive.core.util.SmartMap;
import net.atomichive.core.util.Util;
import org.bukkit.entity.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

/**
 * Represents an entity who is active in the world.
 */
public class ActiveEntity {

    private Entity entity;                // The corresponding Bukkit entity
    private VolatileGoalSelector goals;   // Pathfinding goals
    private VolatileGoalSelector targets; // Pathfinding targets
    private Entity owner;                 // Owning entity, if one exists

    // Abilities
    private List<GenericAbilityHandler> onAttack = new ArrayList<>();
    private List<GenericAbilityHandler> onDamage = new ArrayList<>();
    private List<TimedAbilityHandler> onTimer = new ArrayList<>();


    /**
     * Constructor
     *
     * @param entity Bukkit entity.
     */
    @SuppressWarnings("WeakerAccess")
    public ActiveEntity (Entity entity) {
        this.entity = entity;
    }


    /**
     * Handles pathfinding map from entities.json, and applies
     * custom pathfinding to the entity as necessary.
     *
     * @param pathfinding Pathfinding map.
     */
    void applyPathfinding (Map pathfinding) throws CustomObjectException {

        // Ensure pathfinding map has been defined.
        if (pathfinding == null) return;

        this.goals   = NMSUtil.getGoalSelector(this.entity, VolatileGoalSelector.Type.GOAL);
        this.targets = NMSUtil.getGoalSelector(this.entity, VolatileGoalSelector.Type.TARGET);

        if (pathfinding.containsKey("goals")) {
            handlePathfindingList(
                    pathfinding.get("goals"),
                    VolatileGoalSelector.Type.GOAL
            );
        }

        if (pathfinding.containsKey("targets")) {
            handlePathfindingList(
                    pathfinding.get("targets"),
                    VolatileGoalSelector.Type.TARGET
            );
        }

    }


    /**
     * Handles a list of pathfinding goals or targets.
     *
     * @param list List of pathfinding goals to parse.
     * @param type Whether to handle goals of targets.
     */
    private void handlePathfindingList (Object list, VolatileGoalSelector.Type type)
            throws CustomObjectException {

        // Ensure object can be parsed as list
        if (!(list instanceof List)) {
            throw new CustomObjectException(String.format(
                    "Pathfinding %ss could not be parsed as a list.",
                    type.name().toLowerCase()
            ));
        }

        // Iterate over items in list
        for (Object selector : (List) list) {

            // Ensure selector is a string
            if (!(selector instanceof String)) {
                throw new CustomObjectException("Pathfinding selectors must be strings.");
            }

            // Split at first string
            String[] components = ((String) selector).split(" ", 2);

            if (components.length == 0) continue;

            String goal  = components[0].toLowerCase();
            int priority = 0;

            if (components.length == 2)
                priority = parsePriority(components[1]);

            switch (type) {
                case GOAL:
                    parseGoalSelector(goal, priority);
                    break;
                case TARGET:
                    parseTargetSelector(goal, priority);
                    break;
            }

        }

    }


    /**
     * Parses and applies a goal selector.
     *
     * @param goal Goal selector.
     * @param priority Priority of this selector.
     */
    private void parseGoalSelector (String goal, int priority) throws CustomObjectException {

        VolatileGoal volatileGoal;

        switch (goal) {
            case "clear":
                this.goals.clear();
                return;
            case "swim":
                volatileGoal = new VolatileGoalFloat(entity);
                break;
            case "look_around":
                volatileGoal = new VolatileGoalLookAround(entity);
                break;
            case "random_stroll":
                volatileGoal = new VolatileGoalRandomStroll(entity);
                break;
            case "look_at_player":
                volatileGoal = new VolatileGoalLookAtPlayer(entity);
                break;
            case "follow_owner":
                volatileGoal = new VolatileGoalFollowEntity(entity, this.getOwner());
                break;
            case "melee_attack":
                volatileGoal = new VolatileGoalMelee(entity);
                break;
            case "flee_player":
                volatileGoal = new VolatileGoalFleePlayer(entity);
                break;
            default:
                throw new CustomObjectException(String.format(
                        "Unknown goal selector: '%s'.",
                        goal
                ));
        }

        this.goals.add(priority, volatileGoal);

    }


    /**
     * Parses and applies a target selector.
     *
     * @param target Target selector.
     * @param priority Priority of this selector.
     */
    private void parseTargetSelector (String target, int priority) throws CustomObjectException {

        VolatileGoal volatileTargets;

        switch (target) {
            case "clear":
                this.targets.clear();
                return;
            case "attacker":
                volatileTargets = new VolatileTargetAttacker(entity);
                break;
            case "players":
                volatileTargets = new VolatileTargetPlayer(entity);
                break;
            default:
                throw new CustomObjectException(String.format(
                        "Unknown target selector: '%s'.",
                        target
                ));
        }

        this.targets.add(priority, volatileTargets);

    }


    /**
     * Parses a priority string.
     *
     * @param str Priority as a string.
     * @return Parsed priority.
     */
    private int parsePriority (String str) throws CustomObjectException {

        // Ensure str is an integer
        if (!Util.isInteger(str)) {
            throw new CustomObjectException("Pathfinding priority could not be parsed as an integer.");
        }

        int priority = Integer.parseInt(str);

        // Ensure priority is positive
        if (priority < 0) {
            throw new CustomObjectException("Pathfinding priority cannot be negative.");
        }

        return priority;

    }


    /**
     * Parses and applies abilities.
     *
     * @param abilities Map array of abilities. Where
     *                  each map is an ability.
     */
    void applyAbilities (Map[] abilities) {

        // Iterate over each map
        for (Map ability : abilities) {
            // Note: By catching here we can continue if a problem occurs.
            try {
                parseAbility(ability);
            } catch (CustomObjectException e) {
                Main.getInstance().log(Level.SEVERE, e.getMessage());
            }
        }

    }


    /**
     * Parse and applies an ability.
     *
     * @param map An ability map.
     */
    private void parseAbility (Map map) throws CustomObjectException {

        SmartMap attributes = new SmartMap(map);
        Ability ability = getBaseAbility(attributes);


        // Determine trigger and target
        String trigger = attributes.get(String.class, "trigger");
        String target  = attributes.get(String.class, "target");

        // Ensure a trigger was defined.
        if (trigger == null) {
            throw new CustomObjectException("Ability has no trigger defined.");
        }


        int radius = attributes.getInteger("radius", 30);
        int ticks  = attributes.getInteger("ticks",  10);

        Ability.Target abilityTarget = Ability.Target.CLOSEST_ENTITY;

        // Attempt to get target
        if (target != null) {
            try {
                abilityTarget = Ability.Target.valueOf(target);
            } catch (IllegalArgumentException e) {
                throw new CustomObjectException(String.format(
                        "Unknown ability target: '%s'.",
                        target
                ));
            }
        }


        // Add ability
        switch (trigger) {
            case "on_attack":
                this.onAttack.add(new GenericAbilityHandler(ability, abilityTarget, radius));
                break;
            case "on_damage":
                this.onDamage.add(new GenericAbilityHandler(ability, abilityTarget, radius));
                break;
            case "on_timer":
                this.onTimer.add(new TimedAbilityHandler(ability, abilityTarget, radius, ticks));
                break;
            default:
                throw new CustomObjectException(String.format(
                        "Unknown ability trigger: '%s'.",
                        trigger
                ));
        }

    }


    /**
     * Returns a base ability as defined in a smart map.
     *
     * @param attributes Ability smart map.
     * @return Base ability
     */
    private Ability getBaseAbility (SmartMap attributes) throws CustomObjectException {

        // Get base as string
        String base = attributes.get(String.class, "base");

        // Ensure base was defined
        if (base == null) {
            throw new CustomObjectException("An ability was created but no base ability was defined.");
        }

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
                throw new CustomObjectException(String.format(
                        "Failed to add ability. Unknown base '%s'.",
                        base
                ));
        }

    }


    /**
     * Runs all abilities that are triggered by an attack.
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
     * Runs all abilities that are triggered by damage.
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
     * Determines whether this active entity is the same
     * as the given bukkit entity.
     *
     * @param entity Bukkit entity.
     * @return Whether the entities are the same.
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
