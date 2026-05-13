package fr.moodcraft.tgrade.util;

import fr.moodcraft.tgrade.Main;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public final class MoodStyle {

    private MoodStyle() {
    }

    public static final String MODULE = "Commission Urbaine";

    public static final String MAIN_TITLE = title("Commission Urbaine");
    public static final String ADMIN_TITLE = title("Centre National");
    public static final String PENDING_TITLE = title("Demandes Urbaines");
    public static final String PROJECT_REVIEW_TITLE = title("Inspection Nationale");
    public static final String EVALUATION_TITLE = title("Notation Admin");
    public static final String RATE_TITLE = title("Notation Staff");
    public static final String CLASSEMENT_TITLE = title("Classement Hebdo");
    public static final String CITIZEN_LIST_TITLE = title("Votes Citoyens");
    public static final String CITIZEN_VOTE_TITLE = title("Vote Citoyen");
    public static final String MAYOR_LIST_TITLE = title("Conseil des Maires");
    public static final String MAYOR_VOTE_TITLE = title("Vote des Maires");
    public static final String REVIEW_TITLE = title("Suivi Urbain");

    public static final String TAG_TOWN = "mood_town";
    public static final String TAG_SUBMISSION_ID = "mood_submission_id";

    public static String title(String name) {
        return "§6✦ §8" + name + " §6✦";
    }

    public static String button(String name) {
        return "§6✦ §f" + name + " §6✦";
    }

    public static String header(String module) {
        return "§8----- §6✦ " + module + " ✦ §8-----";
    }

    public static String footer() {
        return "§8-----------------------------";
    }

    public static ItemStack item(
            Material material,
            String name,
            List<String> lore
    ) {

        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();

        if (meta == null) {
            return item;
        }

        meta.setDisplayName(name);

        if (lore != null) {
            meta.setLore(lore);
        }

        meta.addItemFlags(
                ItemFlag.HIDE_ATTRIBUTES,
                ItemFlag.HIDE_ENCHANTS,
                ItemFlag.HIDE_UNBREAKABLE
        );

        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack item(
            Material material,
            String name,
            String... lore
    ) {

        List<String> lines = new ArrayList<>();

        if (lore != null) {
            for (String line : lore) {
                lines.add(line);
            }
        }

        return item(material, name, lines);
    }

    public static ItemStack glass() {
        return item(
                Material.BLACK_STAINED_GLASS_PANE,
                " ",
                List.of()
        );
    }

    public static ItemStack backItem(String target) {
        return item(
                Material.ARROW,
                button("Retour"),
                List.of(
                        "§7Retour au menu",
                        "§7" + target + ".",
                        "",
                        "§8• §7Navigation"
                )
        );
    }

    public static void fill(
            Inventory inv,
            int... slots
    ) {

        ItemStack glass = glass();

        for (int slot : slots) {
            inv.setItem(slot, glass);
        }
    }

    public static boolean titleEquals(
            String current,
            String expected
    ) {

        String a = strip(current);
        String b = strip(expected);

        return a.equalsIgnoreCase(b)
                || a.endsWith(b)
                || b.endsWith(a);
    }

    public static String strip(String text) {

        if (text == null) {
            return "";
        }

        return text
                .replaceAll("§.", "")
                .replace("✦", "")
                .trim();
    }

    public static String cleanButtonName(String text) {
        return strip(text)
                .replace("Ville:", "")
                .replace("Projet:", "")
                .trim();
    }

    public static ItemStack tag(
            ItemStack item,
            String key,
            String value
    ) {

        if (item == null || value == null) {
            return item;
        }

        ItemMeta meta = item.getItemMeta();

        if (meta == null || Main.get() == null) {
            return item;
        }

        meta.getPersistentDataContainer().set(
                new NamespacedKey(Main.get(), key),
                PersistentDataType.STRING,
                value
        );

        item.setItemMeta(meta);
        return item;
    }

    public static String tag(
            ItemStack item,
            String key
    ) {

        if (item == null || Main.get() == null || !item.hasItemMeta()) {
            return null;
        }

        ItemMeta meta = item.getItemMeta();

        if (meta == null) {
            return null;
        }

        return meta.getPersistentDataContainer().get(
                new NamespacedKey(Main.get(), key),
                PersistentDataType.STRING
        );
    }

    public static void click(Player p) {
        p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 1f, 1f);
    }

    public static void ok(Player p) {
        p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 1.4f);
    }

    public static void denySound(Player p) {
        p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 1f, 1f);
    }

    public static void send(
            Player p,
            String module,
            String... lines
    ) {

        p.sendMessage("");
        p.sendMessage(header(module));
        p.sendMessage("");

        for (String line : lines) {
            p.sendMessage(line);
        }

        p.sendMessage("");
        p.sendMessage(footer());
    }

    public static void deny(
            Player p,
            String message,
            String detail
    ) {

        denySound(p);
        send(
                p,
                MODULE,
                "§c✘ §f" + message,
                "",
                "§7" + detail
        );
    }
}
