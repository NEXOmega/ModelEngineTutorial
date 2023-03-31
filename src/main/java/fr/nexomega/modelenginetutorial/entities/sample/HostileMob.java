package fr.nexomega.modelenginetutorial.entities.sample;

import fr.nexomega.modelenginetutorial.entities.pathfinders.PlayAttackAnimation;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalHurtByTarget;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalNearestAttackableTarget;
import net.minecraft.world.entity.player.EntityHuman;
import org.bukkit.Location;

/**
 * A hostile mob that will attack the player, requires a custom pathfinder because attacking is not a default behavior in ModelEngine
 */
public abstract class HostileMob extends AbstractMob {
    public HostileMob(String modelName, Location location) {
        super(modelName, location);
    }

    @Override
    protected void generatePathfinder() {

        // Custom pathfinder to PLAY the attack animation when near the player, damage is handled by ScriptReader
        this.bP.a(1, new PlayAttackAnimation(this, 0.4, 0.25, 0.25, 3, 4, 10));

        this.bQ.a(1, new PathfinderGoalHurtByTarget(this));
        this.bQ.a(2, new PathfinderGoalNearestAttackableTarget<>(this, EntityHuman.class, true));
    }
}
