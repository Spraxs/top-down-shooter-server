package nl.jaimyputter.server.websocket.modules.input.framework;

import lombok.Data;

/**
 * Created by Spraxs
 * Date: 11/21/2019
 */

@Data // Makes getters & setters for variables
public class Input {

    private float left;
    private float right;
    private float up;
    private float down;
}
