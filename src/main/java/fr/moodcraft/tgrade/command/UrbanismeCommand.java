package fr.moodcraft.tgrade.command;

import fr.moodcraft.tgrade.gui.UrbanismeMainGUI;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import org.bukkit.entity.Player;

public class UrbanismeCommand
        implements CommandExecutor {

    @Override
    public boolean onCommand(

            CommandSender sender,

            Command command,

            String label,

            String[] args
    ) {

        //
        // 👤 PLAYER ONLY
        //

        if (!(sender instanceof Player p)) {

            sender.sendMessage(
                    "§cCommande joueur uniquement."
            );

            return true;
        }

        //
        // 🏛 OPEN GUI
        //

        UrbanismeMainGUI.open(p);

        return true;
    }
}