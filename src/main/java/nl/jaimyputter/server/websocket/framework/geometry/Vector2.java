package nl.jaimyputter.server.websocket.framework.geometry;

import lombok.Data;

import java.util.Vector;

/**
 * Created by Spraxs
 * Date: 12/10/2019
 */

@Data
public class Vector2 {
    private float x;
    private float y;

    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2() {
        this.x = 0;
        this.y = 0;
    }

    public static Vector2 of(Vector2 vector2) {
        return new Vector2(vector2.x, vector2.y);
    }

    public static Vector2 divide(Vector2 vector2, float value) {
        return new Vector2(vector2.x / value, vector2.y / value);
    }

    public static Vector2 multiply(Vector2 vector2, float value) {
        return new Vector2(vector2.x * value, vector2.y * value);
    }

    public static Vector2 subtract(Vector2 a, Vector2 b) {
        return new Vector2(a.x - b.x, a.y - b.y);
    }

    public static Vector2 add(Vector2 a, Vector2 b) {
        return new Vector2(a.x + b.x, a.y + b.y);
    }

    public static Vector2 zero() {
        return new Vector2(0, 0);
    }

    public static Vector2 one() {
        return new Vector2(1, 1);
    }
}
