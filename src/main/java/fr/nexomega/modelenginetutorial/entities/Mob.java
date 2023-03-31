package fr.nexomega.modelenginetutorial.entities;

import fr.nexomega.modelenginetutorial.entities.mobs.Kindlestronsr;
import fr.nexomega.modelenginetutorial.entities.mobs.Scarecrow;
import fr.nexomega.modelenginetutorial.entities.sample.AbstractMob;
import org.bukkit.Location;

import java.lang.reflect.InvocationTargetException;

/**
 * This enum is used to spawn mobs more easily
 * by using the spawn method of the enum
 */
public enum Mob {
    SCARECROW(Scarecrow.class),
    KINDLESTRONSR(Kindlestronsr.class)
    ;

    private final Class<? extends AbstractMob> mobClass;

    Mob(Class<? extends AbstractMob> mobClass) {
        this.mobClass = mobClass;
    }

    public void spawn(Location location) {
        try {
            mobClass.getDeclaredConstructor(Location.class).newInstance(location).spawn();
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException |
                 InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
