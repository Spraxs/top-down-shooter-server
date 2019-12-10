package nl.jaimyputter.server.websocket.framework.geometry;

import lombok.Getter;
import nl.jaimyputter.server.websocket.modules.world.framework.WorldObject;

/**
 * Created by Spraxs
 * Date: 12/10/2019
 */

public class BoxCollider2 {

    private @Getter WorldObject worldObject;

    private @Getter Vector2 offset;
    private @Getter Vector2 size;

    private @Getter Bounds bounds;

    /**
     * Create a BoxCollider2D object with bounds
     *
     * @param worldObject WorldObject of collider
     * @param offset of collider
     * @param size of collider
     */
    public BoxCollider2(WorldObject worldObject, Vector2 offset, Vector2 size) {

        this.worldObject = worldObject;
        this.offset = offset;
        this.size = size;

        this.bounds = new Bounds(worldObject.getTransform().getPosition(), offset, size);
    }

    public void setOffset(Vector2 offset) {
        this.offset = offset;

        bounds.setCenter(worldObject.getTransform().getPosition(), offset);
    }

    public void updatePosition() {
        bounds.setCenter(worldObject.getTransform().getPosition(), offset);
    }

    public void setSize(Vector2 size) {
        this.size = size;
        bounds.setExtents(size);
    }
}
