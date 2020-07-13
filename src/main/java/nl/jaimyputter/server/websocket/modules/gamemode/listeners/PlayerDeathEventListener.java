package nl.jaimyputter.server.websocket.modules.gamemode.listeners;

import nl.jaimyputter.server.websocket.Server;
import nl.jaimyputter.server.websocket.framework.events.framework.Listener;
import nl.jaimyputter.server.websocket.framework.events.framework.registry.EventHandler;
import nl.jaimyputter.server.websocket.modules.gamemode.GameModeModule;
import nl.jaimyputter.server.websocket.modules.gamemode.framework.Team;
import nl.jaimyputter.server.websocket.modules.world.events.PlayerDeathEvent;
import nl.jaimyputter.server.websocket.modules.world.framework.creatures.Player;

public class PlayerDeathEventListener implements Listener {

    private final GameModeModule gameModeModule;

    public PlayerDeathEventListener() {
        this.gameModeModule = Server.byModule(GameModeModule.class);
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        gameModeModule.getLogger().info("Player death event listener");

        addPointToOtherTeam(event.getPlayer());
    }

    public void addPointToOtherTeam(Player player) {
        if (player.getTeam() == Team.DEFAULT) return;

        if (player.getTeam() == Team.BLUE) {
            gameModeModule.addPoint(Team.RED);
        } else {
            gameModeModule.addPoint(Team.BLUE);
        }
    }
}
