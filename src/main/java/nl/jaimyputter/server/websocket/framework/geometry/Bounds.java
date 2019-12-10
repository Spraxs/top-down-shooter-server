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

        this.extents = Vector2.multiply(size, 0.5f);
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
        this.extents = Vector2.multiply(size, 0.5f);
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
     * Sets min point of collider by changing the extents variable
     *
     * @param value position of min point of collider
     */
    public void setMin(Vector2 value) {
        setMinMax(value, getMax());
    }

    /**
     * @return The maximal point of the box. This is always equal to center+extents.
     */
    public Vector2 getMax() {
        return Vector2.add(this.center, this.extents);
    }

    /**
     * Sets max point of collider by changing the extents variable
     *
     * @param value position of max point of collider
     */
    public void setMax(Vector2 value) {
        setMinMax(getMin(), value);
    }

    /**
     * Sets the bounds to the min and max value of the box.
     *
     * @param min minimum point of collider.
     * @param max maximum point of collider.
     */
    public void setMinMax(Vector2 min, Vector2 max) {
        this.extents = Vector2.multiply(Vector2.subtract(max, min), 0.5f);
        this.center = Vector2.add(min, this.extents);
    }
}
