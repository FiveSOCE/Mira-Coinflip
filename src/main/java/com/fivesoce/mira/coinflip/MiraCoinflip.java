package com.fivesoce.mira.coinflip;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.logging.Level;

/**
 * Safe initial implementation. Wager operations deliberately remain unavailable
 * until crash-consistent escrow and idempotent payouts have been implemented.
 */
public final class MiraCoinflip extends JavaPlugin implements Listener, TabExecutor {
    private File exclusionFile;
    private YamlConfiguration exclusions;
    private final String lobbyTitle = "Mira Coinflip";

    @Override public void onEnable() {
        saveDefaultConfig();
        exclusionFile = new File(getDataFolder(), "self-exclusions.yml");
        exclusions = YamlConfiguration.loadConfiguration(exclusionFile);
        Objects.requireNonNull(getCommand("cf")).setExecutor(this);
        Objects.requireNonNull(getCommand("cf")).setTabCompleter(this);
        getServer().getPluginManager().registerEvents(this, this);
        getLogger().info("Mira-Coinflip development build enabled. Wagering disabled pending escrow implementation.");
    }

    @Override public void onDisable() { saveExclusions(); }

    private void saveExclusions() {
        try {
            File parent = exclusionFile.getParentFile();
            if (!parent.exists() && !parent.mkdirs()) throw new IOException("Could not create plugin data folder");
            exclusions.save(exclusionFile);
        } catch (IOException e) {
            getLogger().log(Level.SEVERE, "Unable to save exclusion records; protect this file and investigate", e);
        }
    }

    private Instant excludedUntil(UUID id) {
        long millis = exclusions.getLong(id.toString(), 0L);
        return Instant.ofEpochMilli(millis);
    }

    private boolean excluded(Player player) { return excludedUntil(player.getUniqueId()).isAfter(Instant.now()); }

    private void showLobby(Player player) {
        Inventory inv = Bukkit.createInventory(null, 27, lobbyTitle);
        inv.setItem(11, icon(Material.GOLD_INGOT, ChatColor.YELLOW + "Money coinflips (coming soon)"));
        inv.setItem(13, icon(Material.CHEST, ChatColor.GOLD + "Item coinflips (coming soon)"));
        inv.setItem(15, icon(Material.BOOK, ChatColor.AQUA + "History (coming soon)"));
        inv.setItem(22, icon(Material.BARRIER, excluded(player)
                ? ChatColor.RED + "Excluded until " + excludedUntil(player.getUniqueId())
                : ChatColor.GRAY + "Self exclude: /cf exclude <days>"));
        player.openInventory(inv);
    }

    private ItemStack icon(Material material, String text) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) { meta.setDisplayName(text); item.setItemMeta(meta); }
        return item;
    }

    @EventHandler public void onInventoryClick(InventoryClickEvent event) {
        if (!lobbyTitle.equals(event.getView().getTitle())) return;
        event.setCancelled(true);
    }

    @Override public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Mira-Coinflip commands are player-only.");
            return true;
        }
        if (!player.hasPermission("miracoinflip.use")) {
            player.sendMessage(ChatColor.RED + "You do not have permission.");
            return true;
        }
        if (args.length == 0) { showLobby(player); return true; }
        switch (args[0].toLowerCase(Locale.ROOT)) {
            case "exclude" -> {
                if (args.length != 2) {
                    player.sendMessage(ChatColor.YELLOW + "Usage: /cf exclude <days> (minimum 10)");
                    return true;
                }
                long days;
                try { days = Long.parseLong(args[1]); }
                catch (NumberFormatException e) {
                    player.sendMessage(ChatColor.RED + "Days must be a whole number.");
                    return true;
                }
                if (days < 10 || days > 36500) {
                    player.sendMessage(ChatColor.RED + "Choose between 10 and 36500 days.");
                    return true;
                }
                Instant now = Instant.now();
                Instant requested = now.plus(days, ChronoUnit.DAYS);
                Instant existing = excludedUntil(player.getUniqueId());
                Instant until = existing.isAfter(requested) ? existing : requested;
                exclusions.set(player.getUniqueId().toString(), until.toEpochMilli());
                saveExclusions();
                player.sendMessage(ChatColor.GREEN + "Coinflip self-exclusion active until " + until + " UTC. It cannot be shortened.");
            }
            case "hand", "collect", "cancel", "history" ->
                player.sendMessage(ChatColor.YELLOW + "This feature is not released yet. No stakes have been taken.");
            default -> {
                try {
                    double amount = Double.parseDouble(args[0]);
                    if (!Double.isFinite(amount) || amount <= 0) {
                        player.sendMessage(ChatColor.RED + "Amount must be positive and finite.");
                    } else if (excluded(player)) {
                        player.sendMessage(ChatColor.RED + "You are currently self-excluded.");
                    } else {
                        player.sendMessage(ChatColor.YELLOW + "Money coinflips are not released yet. No money has been taken.");
                    }
                } catch (NumberFormatException exception) {
                    player.sendMessage(ChatColor.YELLOW + "Usage: /cf [hand|collect|cancel|history|exclude <days>|amount]");
                }
            }
        }
        return true;
    }

    @Override public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) return Arrays.asList("hand", "collect", "cancel", "history", "exclude");
        return Collections.emptyList();
    }
}
