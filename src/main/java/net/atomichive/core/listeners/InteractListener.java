package net.atomichive.core.listeners;

import net.atomichive.core.Main;
import net.atomichive.core.exception.MetadataException;
import net.atomichive.core.item.ItemUtil;
import net.atomichive.core.item.menu.Menus;
import net.atomichive.core.player.AtomicPlayer;
import net.atomichive.core.util.Util;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Listens for players interacting with various things.
 */
public class InteractListener extends BaseListener implements Listener {


    /**
     * Fired whenever a player interacts with an entity.
     *
     * @param event Event data.
     */
    @EventHandler
    void onEntityInteract (PlayerInteractEntityEvent event) {

        // Get clicked and source player
        Entity clicked = event.getRightClicked();

//        if (clicked.hasMetadata("is_rideable")) {
//            boolean isRideable = Util.getMetadata(clicked, "is_rideable");
//            if (!isRideable) {
//                event.setCancelled();
//            }
//            List<MetadataValue> metadata = clicked.getMetadata("is_rideable");
//            for (MetadataValue value : metadata) {
//                if (value.getOwningPlugin().equals(Main.getInstance()))
//                    event.setCancelled(value.asBoolean());
//            }
//        }

        if (clicked instanceof Villager) {
            try {
                boolean preventTrade = Util.getMetadata(clicked, "prevent_trade");
                event.setCancelled(preventTrade);
            } catch (MetadataException ignored) {}
        }

    }


    /**
     * Fired whenever a player interacts with anything.
     *
     * @param event Event data.
     */
    @EventHandler
    void onItemInteract (PlayerInteractEvent event) {

        // Get item stack
        ItemStack stack = event.getItem();
        AtomicPlayer player = Main.getInstance().getPlayerManager().get(event.getPlayer());

        if (stack == null) return;

        if (stack.getType().equals(Material.COMPASS)) {
            Menus.MAIN.getMenu().open(event.getPlayer());
            event.setCancelled(true);
        }

        if (player.getLevel() < ItemUtil.getLevel(stack)) {
            event.getPlayer().sendMessage(ChatColor.RED + "This item is too high level for you.");
            event.setCancelled(true);
        }

    }

}
