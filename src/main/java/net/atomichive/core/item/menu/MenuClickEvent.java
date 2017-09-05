package net.atomichive.core.item.menu;

import org.bukkit.entity.Player;

/**
 * An event which is fired whenever a player clicks
 * on an item inside of a custom menu.
 */
public class MenuClickEvent {

    private final Player player;
    private final int position;
    private final String name;

    private boolean willClose = true;


    /**
     * Menu click event
     *
     * @param player   Player that initiated the event.
     * @param position Position of clicked item stack.
     * @param name     Name of item stack.
     */
    public MenuClickEvent (Player player, int position, String name) {
        this.player = player;
        this.position = position;
        this.name = name;
    }


    /*
        Getters and setters.
     */

    public Player getPlayer () {
        return player;
    }

    public int getPosition () {
        return position;
    }

    public String getName () {
        return name;
    }

    public boolean willClose () {
        return willClose;
    }

    public void setWillClose (boolean willClose) {
        this.willClose = willClose;
    }

}
