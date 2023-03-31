package fr.nexomega.modelenginetutorial.engine;

import com.ticxo.modelengine.api.animation.property.IAnimationProperty;
import com.ticxo.modelengine.api.animation.script.ScriptReader;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

/**
 * This class is used to read the script of the animation
 */
public class TutorialScriptReader implements ScriptReader {
    // Map of the functions
    HashMap<String, BiConsumer<IAnimationProperty, HashMap<String, String>>> functions = new HashMap<>();

    /**
     * Constructor of the class
     * Here we add the function "attack" to the map
     */
    public TutorialScriptReader() {
        // Add the function "attack" to the map
        functions.put("attack", (property, args) -> {
            // Get the hitbox of the bone
            Vector hitbox = property.getModel().getBone(args.get("bone")).getPosition();
            // Get the location of the hitbox
            Location location = new Location(property.getModel().getModeledEntity().getBase().getWorld(), hitbox.getX(), hitbox.getY(), hitbox.getZ());

            // Get the players near the hitbox and damage them, need to be called in the main thread so we use the scheduler
            Bukkit.getScheduler().runTask(Bukkit.getPluginManager().getPlugin("ModelEngineTutorial"), () -> {
                Objects.requireNonNull(location.getWorld()).getNearbyEntities(location, 1, 1, 1).forEach(entity -> {
                    if(entity instanceof Player) {
                        ((Player) entity).damage(args.containsKey("damage") ? Double.parseDouble(args.get("damage")) : 1);
                    }
                });
            });
        });
    }

    /**
     * This method is called when the script is read, it will call the function of the script if it exists
     * @param property The property of the animation
     * @param script The script of the animation, it will be in the format "function{arg1:value1,arg2:value2,...}"
     */
    @Override
    public void read(IAnimationProperty property, String script) {
        //Get the identifier of the function
        String identifier = script.substring(0, script.indexOf("{"));

        //Get the arguments of the function
        HashMap<String, String> args = new HashMap<>();

        //Split the arguments and add them to the map
        String[] argsArray = script.substring(script.indexOf("{") + 1, script.indexOf("}")).split(",");
        for(String arg : argsArray) {
            String[] argArray = arg.split(":");
            args.put(argArray[0], argArray[1]);
        }

        //Call the function if it exists
        if(functions.containsKey(identifier)) {
            functions.get(identifier).accept(property, args);
        }
    }
}
