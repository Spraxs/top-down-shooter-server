package nl.jaimyputter.server.websocket.modules.world.framework;

import lombok.Getter;
import lombok.Setter;
import nl.jaimyputter.server.websocket.modules.world.framework.creatures.Creature;
import nl.jaimyputter.server.websocket.modules.world.framework.creatures.Player;
import nl.jaimyputter.server.websocket.framework.managers.IdManager;

/**
 * Created by Spraxs
 * Date: 10/29/2019
 */

public class WorldObject {

    private @Getter final long objectId = IdManager.getNextId();
    private @Getter final long spawnTime = System.currentTimeMillis();
    private @Getter @Setter Location location = new Location(0, 0);

    /**
     * Calculates distance between this GameObject and given x, y.
     * @param x the X coordinate
     * @param y the Y coordinate
     * @return distance between object and given x, y, z.
     */
    public double calculateDistance(double x, double y)
    {
        return Math.pow(x - location.getX(), 2) + Math.pow(y - location.getY(), 2);
    }

    /**
     * Calculates distance between this GameObject and another GameObject.
     * @param object GameObject
     * @return distance between object and given x, y.
     */
    public double calculateDistance(WorldObject object)
    {
        return calculateDistance(object.getLocation().getX(), object.getLocation().getY());
    }

    /**
     * Verify if object is instance of Creature.
     * @return {@code true} if object is instance of Creature, {@code false} otherwise.
     */
    public boolean isCreature()
    {
        return false;
    }

    /**
     * @return {@link Creature} instance if current object is such, {@code null} otherwise.
     */
    public Creature asCreature()
    {
        return null;
    }

    /**
     * Verify if object is instance of Player.
     * @return {@code true} if object is instance of Player, {@code false} otherwise.
     */
    public boolean isPlayer()
    {
        return false;
    }

    /**
     * @return {@link Player} instance if current object is such, {@code null} otherwise.
     */
    public Player asPlayer()
    {
        return null;
    }
}
