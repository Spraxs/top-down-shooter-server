package nl.jaimyputter.server.websocket.modules.world.framework.utils;

import lombok.Getter;
import nl.jaimyputter.server.websocket.framework.geometry.Vector2;

/**
 * Created by Spraxs
 * Date: 10/29/2019
 */
public class Transform {

    private @Getter Vector2 position;
    private @Getter Vector2 rotation;
    private @Getter Vector2 scale;

    public Transform(Vector2 position, Vector2 rotation, Vector2 scale) {
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
    }

    public Transform(Vector2 position, Vector2 rotation) {
        this.position = position;
        this.rotation = rotation;
        this.scale = Vector2.zero();
    }

    public Transform(Vector2 position) {
        this.position = position;
        this.rotation = Vector2.zero();
        this.scale = Vector2.one();
    }

    public Transform() {
        this.position = Vector2.zero();
        this.rotation = Vector2.zero();
        this.scale = Vector2.one();
    }

    /**
     * Use WorldObject.setPosition
     *
     * @param position position as Vector2
     */
    @Deprecated
    public void setPosition(Vector2 position) {
        this.position = position;
    }

    /**
     * Use WorldObject.setRotation
     *
     * @param rotation rotation as Vector2
     */
    @Deprecated
    public void setRotation(Vector2 rotation) {
        this.rotation = rotation;
    }

    /**
     * Use WorldObject.setScale
     *
     * @param scale scale as Vector2
     */
    @Deprecated
    public void setScale(Vector2 scale) {
        this.scale = scale;
    }
}
