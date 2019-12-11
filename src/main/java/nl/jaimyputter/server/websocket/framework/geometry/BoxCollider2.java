package nl.jaimyputter.server.websocket.framework.geometry;

import lombok.Getter;
import nl.jaimyputter.server.websocket.modules.world.framework.WorldObject;
import nl.jaimyputter.server.websocket.modules.world.framework.utils.Transform;

import java.util.Vector;

/**
 * Created by Spraxs
 * Date: 12/10/2019
 */

public class BoxCollider2 {

    private @Getter Transform transform;

    private @Getter Vector2 offset;
    private @Getter Vector2 size;

    private @Getter Bounds bounds;

    /**
     * Create a BoxCollider2D object with bounds
     *
     * @param transform Transform of player
     * @param offset of collider
     * @param size of collider
     */
    public BoxCollider2(Transform transform, Vector2 offset, Vector2 size) {

        this.transform = transform;
        this.offset = offset;
        this.size = size;

        this.bounds = new Bounds(transform.getPosition(), offset, size);
    }

    public void setOffset(Vector2 offset) {
        this.offset = offset;

        this.bounds.setCenter(this.transform.getPosition(), offset);
    }

    public void updatePosition(Vector2 position) {
        this.bounds.setCenter(position, offset);
    }

    public void setSize(Vector2 size) {
        this.size = size;
        this.bounds.setExtents(size);
    }

    public Vector2 getPointA() {
        return bounds.getMin();
    }

    public Vector2 getPointB() {
        Vector2 extents = bounds.getExtents();

        Vector2 center = Vector2.of(bounds.getCenter());

        center.setX(center.getX() + extents.getX());
        center.setY(center.getY() - extents.getY());

        return center;
    }

    public Vector2 getPointC() {
        return bounds.getMax();
    }

    public Vector2 getPointD() {
        Vector2 extents = bounds.getExtents();

        Vector2 center = Vector2.of(bounds.getCenter());

        center.setX(center.getX() - extents.getX());
        center.setY(center.getY() + extents.getY());

        return center;
    }

    public Vector2 getPointCenter() {
        return Vector2.of(getBounds().getCenter());
    }
}
