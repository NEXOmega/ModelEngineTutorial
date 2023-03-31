package fr.nexomega.modelenginetutorial.commands;

import com.ticxo.modelengine.api.ModelEngineAPI;
import com.ticxo.modelengine.api.model.vfx.VFX;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Axolotl;
import org.bukkit.entity.Player;

import java.util.List;

public class SpawnVfx implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Bukkit.broadcastMessage("§aCreating VFX...");
        if(!(commandSender instanceof Player)) return false;
        Player player = (Player) commandSender;

        Axolotl axolotl = player.getWorld().spawn(player.getLocation(), Axolotl.class);
        VFX vfx = ModelEngineAPI.createVFX(axolotl);

        vfx.useModel("kindletronsr", "h_head");
        vfx.setColor(Color.BLUE);
        vfx.create();
        Bukkit.broadcastMessage("§aVFX created.");
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return List.of("test");
    }
}
