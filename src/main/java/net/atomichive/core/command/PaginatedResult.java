package net.atomichive.core.command;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Paginated result
 * Used to simplify pagination in chat. Based off of
 * zml2008's implementation.
 */
public abstract class PaginatedResult<T> {

    // Attributes
    private final String header;

    protected static final int ITEMS_PER_PAGE = 9;


    /**
     * Paginated result
     * @param header String to be printed at the top of each page.
     */
    public PaginatedResult (String header) {
        this.header = header;
    }


    public void display (CommandSender sender, Collection<? extends T> results, int page) {
        display(sender, new ArrayList<T>(results), page);
    }


    public void display (CommandSender sender, List<? extends T> results, int page) {


        int maxPages = results.size() / ITEMS_PER_PAGE;

        if (results.size() % ITEMS_PER_PAGE == 0) {
            maxPages--;
        }

        page = Math.max(0, Math.min(page, maxPages));

        if (results.size() == 0) {
            sender.sendMessage(ChatColor.GOLD + header + " [Page " + (page + 1) + "/" + (maxPages + 1) + "]");
            sender.sendMessage("There's nothing here...");
            return;
        }

        // Output header
        sender.sendMessage(ChatColor.GOLD + header + " [Page " + (page + 1) + "/" + (maxPages + 1) + "]");
        for (int i = ITEMS_PER_PAGE * page; i < ITEMS_PER_PAGE * page + ITEMS_PER_PAGE; i++) {
            if (i < results.size()) break;
            sender.sendMessage(format(results.get(i)));
        }

    }


    public abstract String format (T entry);

}
