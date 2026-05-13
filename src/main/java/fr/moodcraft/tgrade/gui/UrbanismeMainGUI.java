package fr.moodcraft.tgrade.gui;

import fr.moodcraft.tgrade.manager.RankingManager;
import fr.moodcraft.tgrade.model.SubmissionStatus;
import fr.moodcraft.tgrade.model.TownGrade;
import fr.moodcraft.tgrade.storage.SubmissionStorage;
import fr.moodcraft.tgrade.towny.TownyHook;
import fr.moodcraft.tgrade.util.MoodStyle;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.List;

public class UrbanismeMainGUI {

    public static final String TITLE = MoodStyle.MAIN_TITLE;

    private static final int[] BORDER = {
            0, 1, 2, 3, 4, 5, 6, 7, 8,
            9, 17, 18, 26, 27, 35, 36, 44,
            45, 46, 47, 48, 50, 51, 52, 53
    };

    public static void open(Player p) {

        Inventory inv = Bukkit.createInventory(null, 54, TITLE);
        MoodStyle.fill(inv, BORDER);

        long pending = SubmissionStorage.getAll().stream()
                .filter(sub -> sub.getStatus() == SubmissionStatus.PENDING)
                .count();

        TownGrade best = RankingManager.getBest();
        String bestTown = best == null ? "Aucune" : best.getTown();

        boolean canManage = TownyHook.canManage(p);
        boolean staff = p.hasPermission("moodtowngrade.staff");

        inv.setItem(
                4,
                MoodStyle.item(
                        Material.NETHER_STAR,
                        MoodStyle.button("Commission Urbaine"),
                        List.of(
                                "§7Centre urbain de",
                                "§aMood§6Craft§7.",
                                "",
                                "§7Meilleure ville: §e" + bestTown,
                                "§7Score moyen: §e" + String.format("%.1f", RankingManager.getAverageScore()),
                                "§7Villes classées: §e" + RankingManager.getFinishedTowns(),
                                "§7Demandes ouvertes: §e" + pending,
                                "",
                                "§8• §7Votes",
                                "§8• §7Projets",
                                "§8• §7Classement"
                        )
                )
        );

        inv.setItem(
                20,
                MoodStyle.item(
                        Material.BOOK,
                        MoodStyle.button("Votes Citoyens"),
                        List.of(
                                "§7Note les villes",
                                "§7avec un projet validé.",
                                "",
                                "§8• §7Vote public",
                                "§8• §7Avis global",
                                "§8• §7Classement hebdo",
                                "",
                                "§eOuvrir les votes"
                        )
                )
        );

        inv.setItem(
                22,
                MoodStyle.item(
                        Material.GOLD_INGOT,
                        MoodStyle.button("Classement Hebdo"),
                        List.of(
                                "§7Consulte le classement",
                                "§7des villes évaluées.",
                                "",
                                "§8• §7Score national",
                                "§8• §7Prestige",
                                "§8• §7Subventions",
                                "",
                                "§eVoir le classement"
                        )
                )
        );

        if (canManage) {
            inv.setItem(
                    29,
                    MoodStyle.item(
                            Material.NETHER_STAR,
                            MoodStyle.button("Déposer un projet"),
                            List.of(
                                    "§7Ouvre un dossier",
                                    "§7pour ta ville.",
                                    "",
                                    "§8• §7Nom du projet",
                                    "§8• §7Description",
                                    "§8• §7Position actuelle",
                                    "",
                                    "§eSaisie dans le chat"
                            )
                    )
            );
        }

        if (canManage || staff) {
            inv.setItem(
                    31,
                    MoodStyle.item(
                            Material.GOLD_BLOCK,
                            MoodStyle.button("Conseil des Maires"),
                            List.of(
                                    "§7Avis des maires",
                                    "§7sur les projets validés.",
                                    "",
                                    "§8• §7Vote municipal",
                                    "§8• §7Ville par ville",
                                    "§8• §7Classement hebdo",
                                    "",
                                    "§eOuvrir le conseil"
                            )
                    )
            );
        }

        if (staff) {
            inv.setItem(
                    33,
                    MoodStyle.item(
                            Material.COMPASS,
                            MoodStyle.button("Centre National"),
                            List.of(
                                    "§7Gère les dossiers",
                                    "§7de la Commission.",
                                    "",
                                    "§8• §7Demandes",
                                    "§8• §7Notations",
                                    "§8• §7Subventions",
                                    "",
                                    "§eAccès staff"
                            )
                    )
            );
        }

        inv.setItem(49, MoodStyle.backItem("principal"));
        p.openInventory(inv);
    }
}
