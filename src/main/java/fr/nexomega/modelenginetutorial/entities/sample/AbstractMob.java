package fr.nexomega.modelenginetutorial.entities.sample;

import com.ticxo.modelengine.api.ModelEngineAPI;
import com.ticxo.modelengine.api.model.ActiveModel;
import com.ticxo.modelengine.api.model.ModeledEntity;
import net.minecraft.world.entity.EntityCreature;
import net.minecraft.world.entity.EntityTypes;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_17_R1.CraftWorld;
import org.bukkit.event.entity.CreatureSpawnEvent;

/**
 * This class is used to create a mob with a model, it is not a mob itself.
 * Used to create a mob, you need to extend this class and override the generatePathfinder() method to add your own pathfinder
 * You can also override the spawn() method to add your own spawn logic but be sure to add the model
 */
public abstract class AbstractMob extends EntityCreature {

    String modelName;
    ActiveModel model;
    ModeledEntity modeledEntity;
    Location location;
    public AbstractMob(String modelName, Location location) {
        super(EntityTypes.e, ((CraftWorld) location.getWorld()).getHandle());
        this.modelName = modelName;
        this.location = location;
        this.model = ModelEngineAPI.createActiveModel(modelName);

        generatePathfinder();
    }

    protected abstract void generatePathfinder();

    /**
     * Spawn the mob at the location given in the constructor, and add the model to it
     */
    public void spawn() {
        //Basis for the spawn method
        this.setPosition(location.getX(), location.getY(), location.getZ());
        this.getWorld().getWorld().getHandle().addEntity(this, CreatureSpawnEvent.SpawnReason.CUSTOM);

        //Create the modeled entity and add the model to it, then hide the base entity
        this.modeledEntity = ModelEngineAPI.createModeledEntity(this.getBukkitEntity());
        this.modeledEntity.addModel(this.model, true);
        this.modeledEntity.setBaseEntityVisible(false);

        //  Little disclaimer, i saw that in MythicMobs, they create the ModeledEntity, then ActiveModel, then add the model to the entity
        // I think create the ModeledEntity first is better, because you can add multiple models to it, and you can hide the base entity
        // So I don't think I will change it because it's a tutorial to use ModelEngine Methods and not to create a mob plugin
    }

    public String getModelName() {
        return modelName;
    }

    public ActiveModel getModel() {
        return model;
    }

    public ModeledEntity getModeledEntity() {
        return modeledEntity;
    }

    public Location getLocation() {
        return location;
    }
}
