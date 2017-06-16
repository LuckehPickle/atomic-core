package net.atomichive.core.player;

import net.atomichive.core.Utils;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Atomic Player object
 */
public class AtomicPlayer {

	// Attributes
	private UUID identifier;
	private String username;
	private int logins;
	private Timestamp lastSeen;
	private int messages;
	private int warnings;
	private List<String> aliases;


	/**
	 * Atomic Player constructor
	 * @param identifier UUID of player.
	 * @param username Player's username.
	 */
	public AtomicPlayer(UUID identifier, String username) {

		this.identifier = identifier;
		this.username = username;
		this.logins = 0;
		this.lastSeen = Utils.getCurrentTimestamp();
		this.messages = 0;
		this.warnings = 0;
		this.aliases = Collections.singletonList(username);

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

	public int getLogins() {
		return logins;
	}

	public void setLogins(int logins) {
		this.logins = logins;
	}

	public void incrementLoginCount() {
		logins++;
	}

	public Timestamp getLastSeen () {
		return lastSeen;
	}

	public void setLastSeen (Timestamp lastSeen) {
		this.lastSeen = lastSeen;
	}

	public int getMessages() {
		return messages;
	}

	public void setMessages(int messages) {
		this.messages = messages;
	}

	public int getWarnings() {
		return warnings;
	}

	public void setWarnings(int warnings) {
		this.warnings = warnings;
	}

	public List<String> getAliases () {
		return aliases;
	}

	public void setAliases (List<String> aliases) {
		this.aliases = aliases;
	}

}
