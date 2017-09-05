package net.atomichive.core.listeners;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.reflect.StructureModifier;
import net.atomichive.core.Main;
import net.atomichive.core.entity.ActiveEntity;
import net.atomichive.core.player.AtomicPlayer;
import org.bukkit.entity.Entity;

/**
 * Listens to and modifies packets.
 */
public class PacketListener {

    public PacketListener () {

        // Get packet manager
        ProtocolManager manager = Main.getInstance().getProtocolManager();
        Main plugin = Main.getInstance();

        manager.addPacketListener(new PacketAdapter(plugin, ListenerPriority.NORMAL, PacketType.Play.Server.SPAWN_ENTITY_LIVING) {

            /**
             * Fired whenever a spawn living entity packet
             * is sent.
             *
             * @param event Packet event.
             */
            @Override
            public void onPacketSending (PacketEvent event) {

                if (event.getPacketType() == PacketType.Play.Server.SPAWN_ENTITY_LIVING) {

                    Main plugin = Main.getInstance();

                    // Get entity
                    StructureModifier<Entity> components = event.getPacket().getEntityModifier(event);
                    Entity entity = components.read(0);

                    AtomicPlayer player = plugin.getPlayerManager().get(event.getPlayer());
                    ActiveEntity active = plugin.getEntityManager().getActiveEntity(entity);

                    if (active == null) {
                        return;
                    }

                    entity.setCustomName(entity.getCustomName() + " " + player.getDisplayName() + " " + active.getLevel());

                    components.write(0, entity);

                }

//                ChatColor color;
//
//                if (player.getLevel() < active.getLevel() - 5) {
//                    color = ChatColor.GRAY;
//                } else if (player.getLevel() < active.getLevel() + 2) {
//                    color = ChatColor.GREEN;
//                } else if (player.getLevel() < active.getLevel() + 6) {
//                    color = ChatColor.YELLOW;
//                } else {
//                    color = ChatColor.RED;
//                }
//
//                entity.setCustomName(entity.getCustomName().replaceFirst("<COLOR>", color.toString()));
//
//                components.write(0, entity);

            }
        });

    }

}
