package fr.moodcraft.tgrade.util;

import org.bukkit.Bukkit;
import org.bukkit.Sound;

import org.bukkit.entity.Player;

import java.text.NumberFormat;

import java.util.ArrayList;
import java.util.List;

import java.util.Locale;

public class MessageManager {

    //
    // 🎨 STYLE
    //

    public static final String LINE =
            "§8━━━━━━━━━━━━━━━━━━━━━━━━━━━━";

    public static final String PREFIX =
            "§6✦ §eMoodCraft §8• ";

    //
    // 📩 SIMPLE
    //

    public static void send(
            Player p,
            String message
    ) {

        p.sendMessage(
                PREFIX + "§7" + message
        );
    }

    //
    // ✅ SUCCESS
    //

    public static void success(
            Player p,
            String message
    ) {

        p.sendMessage("");

        p.sendMessage(LINE);

        p.sendMessage(
                "§a✦ " + message
        );

        p.sendMessage(LINE);

        p.sendMessage("");
    }

    //
    // ❌ ERROR
    //

    public static void error(
            Player p,
            String message
    ) {

        p.sendMessage("");

        p.sendMessage(LINE);

        p.sendMessage(
                "§c✦ " + message
        );

        p.sendMessage(LINE);

        p.sendMessage("");
    }

    //
    // 📢 INFO
    //

    public static void info(
            Player p,
            String title,
            String... lines
    ) {

        p.sendMessage("");

        p.sendMessage(LINE);

        p.sendMessage(
                "§6✦ " + title
        );

        p.sendMessage("");

        for (String line : lines) {

            p.sendMessage(
                    "§7" + line
            );
        }

        p.sendMessage("");

        p.sendMessage(LINE);

        p.sendMessage("");
    }

    //
    // 🌍 BROADCAST
    //

    public static void broadcast(
            String title,
            String... lines
    ) {

        Bukkit.broadcastMessage("");

        Bukkit.broadcastMessage(LINE);

        Bukkit.broadcastMessage(
                "§6✦ " + title
        );

        Bukkit.broadcastMessage("");

        for (String line : lines) {

            Bukkit.broadcastMessage(
                    "§7" + line
            );
        }

        Bukkit.broadcastMessage("");

        Bukkit.broadcastMessage(LINE);

        Bukkit.broadcastMessage("");
    }

    //
    // 💰 MONEY FORMAT
    //

    public static String money(
            int amount
    ) {

        NumberFormat format =
                NumberFormat.getInstance(
                        Locale.FRANCE
                );

        return format.format(amount)
                + "$";
    }

    //
    // ⭐ SCORE FORMAT
    //

    public static String score(
            int value,
            int max
    ) {

        return "§e"
                + value
                + "§7/"
                + max;
    }

    //
    // 🏆 PRESTIGE COLOR
    //

    public static String prestige(
            int score
    ) {

        if (score >= 45) {

            return "§6Métropole légendaire";
        }

        if (score >= 38) {

            return "§eCapitale prospère";
        }

        if (score >= 30) {

            return "§aVille développée";
        }

        if (score >= 25) {

            return "§bVille émergente";
        }

        return "§cVille fragile";
    }

    //
    // 📜 LORE
    //

    public static List<String> lore(
            String... lines
    ) {

        List<String> lore =
                new ArrayList<>();

        for (String line : lines) {

            lore.add(
                    "§7" + line
            );
        }

        return lore;
    }

    //
    // 🔊 SUCCESS SOUND
    //

    public static void successSound(
            Player p
    ) {

        p.playSound(

                p.getLocation(),

                Sound.UI_TOAST_CHALLENGE_COMPLETE,

                1f,

                1f
        );
    }

    //
    // ❌ ERROR SOUND
    //

    public static void errorSound(
            Player p
    ) {

        p.playSound(

                p.getLocation(),

                Sound.ENTITY_VILLAGER_NO,

                1f,

                1f
        );
    }

    //
    // 🔘 CLICK SOUND
    //

    public static void clickSound(
            Player p
    ) {

        p.playSound(

                p.getLocation(),

                Sound.UI_BUTTON_CLICK,

                1f,

                1.2f
        );
    }
}