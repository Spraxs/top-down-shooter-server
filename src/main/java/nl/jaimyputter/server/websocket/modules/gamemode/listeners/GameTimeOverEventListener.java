package nl.jaimyputter.server.websocket.modules.gamemode.listeners;

import nl.jaimyputter.server.websocket.Server;
import nl.jaimyputter.server.websocket.framework.events.framework.Listener;
import nl.jaimyputter.server.websocket.framework.events.framework.registry.EventHandler;
import nl.jaimyputter.server.websocket.modules.gamemode.GameModeModule;
import nl.jaimyputter.server.websocket.modules.gamemode.events.GameTimeOverEvent;
import nl.jaimyputter.server.websocket.modules.gamemode.framework.GameState;
import nl.jaimyputter.server.websocket.modules.gamemode.framework.Team;

public class GameTimeOverEventListener implements Listener {

    private final GameModeModule gameModeModule;

    public GameTimeOverEventListener() {
        this.gameModeModule = Server.byModule(GameModeModule.class);
    }

    @EventHandler
    public void onTimeOver(GameTimeOverEvent event) {
        gameModeModule.updateGameState(GameState.AFTER_MATCH, true);

        final int blueScore = gameModeModule.getBlueScore();
        final int redScore = gameModeModule.getRedScore();

        if (blueScore == redScore) {
            for (Team value : Team.values()) {
                gameModeModule.handleWin(value);
            }
            return;
        }

        if (blueScore > redScore) {
            gameModeModule.handleWin(Team.BLUE);
        }

        if (redScore > blueScore) {
            gameModeModule.handleWin(Team.RED);
        }
    }
}
