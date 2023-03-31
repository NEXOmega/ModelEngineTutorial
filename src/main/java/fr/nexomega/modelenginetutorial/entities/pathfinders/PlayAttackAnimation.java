package fr.nexomega.modelenginetutorial.entities.pathfinders;

import fr.nexomega.modelenginetutorial.entities.sample.AbstractMob;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.ai.goal.PathfinderGoal;
import net.minecraft.world.level.pathfinder.PathEntity;

import java.util.EnumSet;

/**
 * This class is a custom pathfinder goal, it will make the mob play an animation when it's close enough to the target
 */
public class PlayAttackAnimation extends PathfinderGoal {
    private final double walkSpeed, lerpIn, lerpOut, speed, reach, distance;
    private AbstractMob mob;
    private PathEntity path;

    /**
     * @param mob The mob that will play the animation
     * @param walkSpeed The speed of the mob when it's walking
     * @param lerpIn The lerp in value of the animation, it's the time the animation will take to start
     * @param lerpOut The lerp out value of the animation, it's the time the animation will take to stop
     * @param speed The speed of the animation
     * @param reach The distance at which the mob will play the animation
     * @param distance The distance at which the mob will stop following the target
     */
    public PlayAttackAnimation(AbstractMob mob, double walkSpeed, double lerpIn, double lerpOut, double speed, double reach, double distance) {
        this.mob = mob;
        this.walkSpeed = walkSpeed;
        this.lerpIn = lerpIn;
        this.lerpOut = lerpOut;
        this.speed = speed;
        this.reach = reach;
        this.distance = distance;

        this.a(EnumSet.of(Type.a, Type.b));
    }

    /**
     * Can start running
     * @return true if the mob has an alive target in range and false if not
     *
     * I'm not sur of the path part because it was a part of Minecraft Melee Attack Pathfinder Goal and I don't know what it does exactly
     */
    @Override
    public boolean a() {
        EntityLiving target = mob.getGoalTarget();
        if(target == null) return false;
        if(!target.isAlive()) return false;

        path = mob.getNavigation().a(target, 0);
        if(path != null) return true;
        return mob.e(target) < distance * distance;
    }

    /**
     * Can continue running, will call c() if true and d() if false
     * @return true if the mob has an alive target in range or is at destination and false if not
     */
    @Override
    public boolean b() {
        EntityLiving target = mob.getGoalTarget();
        if(target == null) return false;
        if(!target.isAlive()) return false;

        if(mob.getNavigation().m()) return true;
        return mob.e(target) < (distance * distance);
    }

    /**
     * Start pathfinding
     * Called when a() returns true
     * Will make the mob walk to the target at the speed specified in the constructor
     */
    @Override
    public void c() {
        mob.getNavigation().a(path, walkSpeed);
    }

    /**
     * Stop pathfinding
     * Called when b() returns false
     * Will make the mob stop following the target and stop walking
     */
    @Override
    public void d() {
        mob.setGoalTarget(null);
        mob.getNavigation().o();
    }

    /**
     * Called every tick
     * Will make the mob look at the target and play the animation if it's close enough
     * If it's not close enough it will make the mob walk to the target
     */
    @Override
    public void e() {
        EntityLiving target = mob.getGoalTarget();
        this.mob.getControllerLook().a(target, 30.0F, 30.0F);
        double distance = this.mob.h(target.locX(), target.locY(), target.locZ());

        String animationName = "attack";
        if(distance < (reach*reach) && !mob.getModeledEntity().getModel(mob.getModelName()).getAnimationHandler().isPlayingAnimation(animationName)) {
            mob.getModeledEntity().getModel(mob.getModelName()).getAnimationHandler().playAnimation(animationName, lerpIn, lerpOut, speed, false);
        } else {
            mob.getNavigation().a(target, walkSpeed);
        }
    }

}
