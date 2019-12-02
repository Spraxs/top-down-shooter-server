package nl.jaimyputter.server.websocket.modules.world.framework;

import lombok.Data;

/**
 * Created by Spraxs
 * Date: 10/29/2019
 */
@Data
public class Location {

    double x;
    double y;

    public Location(double x, double y) {
        this.x = x;
        this.y = y;
    }
}
