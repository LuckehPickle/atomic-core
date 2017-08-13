package net.atomichive.core.listeners;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import net.atomichive.core.Main;

/**
 * Packet Listener
 * Listens to and modifies packets.
 */
public class PacketListener {

    public PacketListener () {

        // Get packet manager
        ProtocolManager manager = Main.getInstance().getProtocolManager();

        manager.addPacketListener(new PacketAdapter(
                Main.getInstance(),
                ListenerPriority.NORMAL,
                PacketType.Play.Server.ENTITY_METADATA) {
            @Override
            public void onPacketSending (PacketEvent event) {
                if (event.getPacketType() == PacketType.Play.Server.ENTITY_METADATA) {
//                    Main.getInstance().log(Level.INFO, event.toString());
                }
            }
        });

    }

}
