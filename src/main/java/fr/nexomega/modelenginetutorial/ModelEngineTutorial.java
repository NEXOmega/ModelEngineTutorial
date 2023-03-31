package fr.nexomega.modelenginetutorial;

import com.ticxo.modelengine.api.ModelEngineAPI;
import fr.nexomega.modelenginetutorial.commands.SpawnMob;
import fr.nexomega.modelenginetutorial.commands.SpawnVfx;
import fr.nexomega.modelenginetutorial.engine.TutorialScriptReader;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

/**
 * Main class of the plugin, just register the commands and the script reader
 */
public final class ModelEngineTutorial extends JavaPlugin {

    @Override
    public void onEnable() {
        Objects.requireNonNull(this.getCommand("mob")).setExecutor(new SpawnMob());

        // Register the command to spawn a vfx
        // Objects.requireNonNull(this.getCommand("vfx")).setExecutor(new SpawnVfx()); Not working yet, still looking how to make it work

        // Register the script reader, so when animation play a keyframe with the identifier "tutorial", it will call the read method of the script reader
        ModelEngineAPI.getScriptReaderRegistry().register("tutorial", new TutorialScriptReader());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
