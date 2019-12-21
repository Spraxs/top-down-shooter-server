package nl.jaimyputter.server.websocket.modules.gamemode.framework;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor @Getter
public enum GameState {

    LOBBY(0), IN_GAME(1), AFTER_MATCH(2);

    private int id;
}
