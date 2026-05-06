package fr.moodcraft.tgrade.towny;

import com.palmergames.bukkit.towny.TownyAPI;

import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Town;

import org.bukkit.entity.Player;

public class TownyHook {

    //
    // 🏙 GET TOWN
    //

    public static Town getTown(Player p) {

        return TownyAPI.getInstance()
                .getTown(p);
    }

    //
    // 👑 IS MAYOR
    //

    public static boolean isMayor(Player p) {

        Town town = getTown(p);

        if (town == null) {
            return false;
        }

        Resident resident =
                TownyAPI.getInstance()
                        .getResident(p);

        if (resident == null) {
            return false;
        }

        return town.getMayor()
                .equals(resident);
    }

    //
    // ⭐ IS ASSISTANT
    //

    public static boolean isAssistant(Player p) {

        Town town = getTown(p);

        if (town == null) {
            return false;
        }

        Resident resident =
                TownyAPI.getInstance()
                        .getResident(p);

        if (resident == null) {
            return false;
        }

        return town.getAssistants()
                .contains(resident);
    }

    //
    // 🛡 CAN MANAGE
    //

    public static boolean canManage(Player p) {

        return isMayor(p)
                || isAssistant(p);
    }
}