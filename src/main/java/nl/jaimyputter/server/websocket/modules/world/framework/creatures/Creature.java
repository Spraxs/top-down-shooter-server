package nl.jaimyputter.server.websocket.modules.world.framework.creatures;

import lombok.Getter;
import lombok.Setter;
import nl.jaimyputter.server.websocket.framework.geometry.BoxCollider2;
import nl.jaimyputter.server.websocket.modules.world.framework.WorldObject;
import nl.jaimyputter.server.websocket.modules.world.framework.utils.Transform;

/**
 * Created by Spraxs
 * Date: 10/29/2019
 */

public class Creature extends WorldObject {

    private static final int BASE_HEALTH = 100;
    private static final float LEVEL_HP_MODIFIER = 1.1f;

    private @Getter int level = 1;
    private @Getter float health = BASE_HEALTH;
    private @Getter @Setter boolean alive = true;
    private @Getter @Setter WorldObject target;

    public Creature(Transform transform, BoxCollider2 boxCollider2) {
        super(transform, boxCollider2);
    }


    public void setLevel(int level)
    {
        this.level = level;
        calculateHP();
    }

    public synchronized void setHealth(float value) {
        if (health < 1) {
            health = 0;
            if (alive)
            {
                alive = false;
                onDeath();
            }
        } else {
            health = value;
        }
    }

    private void calculateHP()
    {
        final float hpModifier = level * LEVEL_HP_MODIFIER;
        health = (BASE_HEALTH + (hpModifier));
        // TODO: If player - Send HP status update to client.
    }

    public void onDeath()
    {
        // TODO: Send death animation.
    }

    @Override
    public boolean isCreature()
    {
        return true;
    }

    @Override
    public Creature asCreature()
    {
        return this;
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