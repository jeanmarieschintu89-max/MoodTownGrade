package fr.moodcraft.tgrade.gui;

import fr.moodcraft.tgrade.model.SubmissionStatus;
import fr.moodcraft.tgrade.model.TownSubmission;

import fr.moodcraft.tgrade.storage.SubmissionStorage;

import org.bukkit.Bukkit;
import org.bukkit.Material;

import org.bukkit.entity.Player;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ReviewGUI {

    public static void open(Player p,
                            String town) {

        Inventory inv =
                Bukkit.createInventory(
                        null,
                        54,
                        "§8Visite • " + town
                );

        List<TownSubmission> list =
                SubmissionStorage.getTown(town);

        int slot = 0;

        for (TownSubmission sub : list) {

            //
            // ✅ VALIDÉS UNIQUEMENT
            //

            if (sub.getStatus()
                    != SubmissionStatus.APPROVED) {
                continue;
            }

            ItemStack item =
                    new ItemStack(Material.BOOK);

            ItemMeta meta =
                    item.getItemMeta();

            meta.setDisplayName(
                    "§6" + sub.getBuildName()
            );

            List<String> lore =
                    new ArrayList<>();

            lore.add("");

            lore.add(
                    "§7Ville: §a"
                            + sub.getTown()
            );

            lore.add("");

            lore.add(
                    "§7📍 "
                            + sub.getX()
                            + " "
                            + sub.getY()
                            + " "
                            + sub.getZ()
            );

            lore.add("");

            lore.add(
                    "§eClique pour visiter"
            );

            meta.setLore(lore);

            item.setItemMeta(meta);

            inv.setItem(slot, item);

            slot++;
        }

        p.openInventory(inv);
    }
}