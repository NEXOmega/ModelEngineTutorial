package fr.nexomega.modelenginetutorial.entities.mobs;

import fr.nexomega.modelenginetutorial.entities.sample.PassiveMob;
import org.bukkit.Location;

/**
 * This class is an example of a passive mob
 */
public class Scarecrow extends PassiveMob {
    public Scarecrow(Location location) {
        super("scarecrow", location);
    }
}
