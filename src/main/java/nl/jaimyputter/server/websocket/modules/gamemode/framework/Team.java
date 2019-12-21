package nl.jaimyputter.server.websocket.modules.gamemode.framework;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor @Getter
public enum Team {
    RED(0, "Red"), BLUE(1, "Blue"), DEFAULT(2, "Default");

    private int id;
    private String name;
}
