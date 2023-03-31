package fr.nexomega.modelenginetutorial.commands;

import com.ticxo.modelengine.api.ModelEngineAPI;
import fr.nexomega.modelenginetutorial.entities.Mob;
import fr.nexomega.modelenginetutorial.entities.mobs.Scarecrow;
import fr.nexomega.modelenginetutorial.entities.sample.AbstractMob;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * SpawnMob command, used to spawn a mob.
 * Not really complex so I will not describe everything.
 *
 */
public class SpawnMob implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length == 0) {
            sender.sendMessage("§cUsage: /spawnmob <mob>");
            return false;
        }

        if(!(sender instanceof Player)) {
            sender.sendMessage("§cYou must be a player to execute this command.");
            return false;
        }

        Player player = (Player) sender;
        // We get the mob from the enum.
        Mob mob = Mob.valueOf(args[0].toUpperCase());
        // We spawn the mob.
        mob.spawn(player.getLocation());

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        ArrayList<String> mobs = new ArrayList<>();
        if(args.length == 1) {
            for(Mob mob : Mob.values()) {
                mobs.add(mob.name());
            }
            return mobs;
        }
        return null;
    }
}
