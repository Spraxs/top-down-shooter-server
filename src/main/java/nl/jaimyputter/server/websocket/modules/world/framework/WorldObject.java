package nl.jaimyputter.server.websocket.modules.world.framework;

import lombok.Getter;
import lombok.Setter;
import nl.jaimyputter.server.websocket.framework.geometry.BoxCollider2;
import nl.jaimyputter.server.websocket.framework.geometry.Vector2;
import nl.jaimyputter.server.websocket.framework.managers.IdManager;
import nl.jaimyputter.server.websocket.modules.world.framework.creatures.Creature;
import nl.jaimyputter.server.websocket.modules.world.framework.creatures.Player;
import nl.jaimyputter.server.websocket.modules.world.framework.utils.Transform;

/**
 * Created by Spraxs
 * Date: 10/29/2019
 */

public class WorldObject {

    private @Getter final long objectId = IdManager.getNextId();
    private @Getter final long spawnTime = System.currentTimeMillis();
    private @Getter @Setter Transform transform;

    private @Getter BoxCollider2 boxCollider2;

    public WorldObject(Transform transform) {
        this.transform = transform;
    }

    public WorldObject(Transform transform, BoxCollider2 boxCollider2) {
        this.transform = transform;
        this.boxCollider2 = boxCollider2;
    }

    public void setPosition(Vector2 position) {
        transform.setPosition(position);

        if (boxCollider2 != null) {
            boxCollider2.updatePosition(position);
        }
    }

    /**
     * Calculates distance between this GameObject and given x, y.
     * @param x the X coordinate
     * @param y the Y coordinate
     * @return distance between object and given x, y, z.
     */
    public double calculateDistance(double x, double y)
    {
        return Math.pow(x - transform.getPosition().getX(), 2) + Math.pow(y - transform.getPosition().getY(), 2);
    }

    /**
     * Calculates distance between this GameObject and another GameObject.
     * @param object GameObject
     * @return distance between object and given x, y.
     */
    public double calculateDistance(WorldObject object)
    {
        return calculateDistance(object.getTransform().getPosition().getX(), object.getTransform().getPosition().getY());
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
