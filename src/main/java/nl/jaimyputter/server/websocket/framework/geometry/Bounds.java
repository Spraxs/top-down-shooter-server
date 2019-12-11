package nl.jaimyputter.server.websocket.framework.geometry;

import lombok.Getter;

/**
 * Created by Spraxs
 * Date: 12/10/2019
 */

public class Bounds {

    private @Getter Vector2 center;
    private @Getter Vector2 extents;

    /**
     * Create bounds object setting the center and extends variables
     *
     * @param position of WorldObject
     * @param offset of collider
     * @param size of collider
     */
    public Bounds(Vector2 position, Vector2 offset, Vector2 size) {
        this.center = Vector2.add(position, offset);

        this.extents = Vector2.divide(size, 2f);
    }

    /**
     * Sets bounds center by adding position and offset together
     *
     * @param position of WorldObject
     * @param offset of collider
     */
    public void setCenter(Vector2 position, Vector2 offset) {
        this.center = Vector2.add(position, offset);
    }

    /**
     * Sets bounds extents by multiplying the size of the collider by 0.5
     *
     * @param size of collider
     */
    public void setExtents(Vector2 size) {
        this.extents = Vector2.divide(size, 2f);
    }

    public boolean equals(Bounds other) {
        return this.center == other.center && this.extents == other.extents;
    }

    /**
     * @return size of the collider
     */
    public Vector2 getSize() {
        return Vector2.multiply(this.extents, 2);
    }

    /**
     * @return The minimal point of the box. This is always equal to center-extents
     */
    public Vector2 getMin() {
        return Vector2.subtract(this.center, this.extents);
    }

    /**
     * @return The maximal point of the box. This is always equal to center+extents.
     */
    public Vector2 getMax() {
        Vector2 max = Vector2.add(this.center, this.extents);
        return max;
    }
}
