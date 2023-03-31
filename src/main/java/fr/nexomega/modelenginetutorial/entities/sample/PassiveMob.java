package fr.nexomega.modelenginetutorial.entities.sample;

import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.player.EntityHuman;
import org.bukkit.Location;

/**
 * A passive mob is a mob does not require custom pathfinding because all animations are already handled by ModelEngine
 */
public abstract class PassiveMob extends AbstractMob {

    public PassiveMob(String modelName, Location location) {
        super(modelName, location);
    }

    @Override
    protected void generatePathfinder() {
        this.bP.a(0, new PathfinderGoalFloat(this));
        this.bP.a(1, new PathfinderGoalPanic(this, 0.5D));
        this.bP.a(2, new PathfinderGoalRandomStrollLand(this, 0.4D));
        this.bP.a(3, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 6.0F));
        this.bP.a(4, new PathfinderGoalRandomLookaround(this));
    }
}
