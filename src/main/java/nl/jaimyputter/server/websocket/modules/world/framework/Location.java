package nl.jaimyputter.server.websocket.modules.world.framework;

import lombok.Data;

/**
 * Created by Spraxs
 * Date: 10/29/2019
 */
@Data
public class Location {

    float x;
    float y;

    public Location(float x, float y) {
        this.x = x;
        this.y = y;
    }
}
