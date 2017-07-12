package net.atomichive.core.player;

import net.atomichive.core.util.Utils;
import org.bukkit.entity.Player;

import java.sql.Timestamp;
import java.util.UUID;

/**
 * Atomic Player
 * Tracks various additional things about players.
 */
@SuppressWarnings("WeakerAccess")
public class AtomicPlayer {

    // Attributes
    private UUID identifier;
    private String username;
    private int experience;
    private Timestamp lastSeen;
    private int loginCount;
    private int messageCount;
    private int warningCount;


    /**
     * Atomic Player constructor
     */
    public AtomicPlayer () {
        this(null, null);
    }

    /**
     * Atomic Player constructor
     * @param identifier UUID of player.
     * @param username   Player's current username;
     */
    public AtomicPlayer (UUID identifier, String username) {

        this.identifier = identifier;
        this.username = username;
        this.experience = 0;
        this.lastSeen = Utils.getCurrentTimestamp();
        this.loginCount = 0;
        this.messageCount = 0;
        this.warningCount = 0;

    }


    /**
     * Is
     * @param player Bukkit player.
     * @return Whether the atomic player is the same as the bukkit player.
     */
    public boolean is (Player player) {
        return identifier.equals(player.getUniqueId());
    }



	/*
        Getters and setters.
	 */

    public UUID getIdentifier () {
        return identifier;
    }

    public void setIdentifier (UUID identifier) {
        this.identifier = identifier;
    }

    public String getUsername () {
        return username;
    }

    public void setUsername (String username) {
        this.username = username;
    }

    public int getExperience () {
        return experience;
    }

    public void setExperience (int experience) {
        this.experience = experience;
    }

    public Timestamp getLastSeen () {
        return lastSeen;
    }

    public void setLastSeen (Timestamp lastSeen) {
        this.lastSeen = lastSeen;
    }

    public int getLoginCount () {
        return loginCount;
    }

    public void setLoginCount (int loginCount) {
        this.loginCount = loginCount;
    }

    public void incrementLoginCount () {
        loginCount++;
    }

    public int getMessageCount () {
        return messageCount;
    }

    public void setMessageCount (int messageCount) {
        this.messageCount = messageCount;
    }

    public int getWarningCount () {
        return warningCount;
    }

    public void setWarningCount (int warningCount) {
        this.warningCount = warningCount;
    }
}
