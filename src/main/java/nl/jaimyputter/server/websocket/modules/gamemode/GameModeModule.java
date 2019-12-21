package nl.jaimyputter.server.websocket.modules.gamemode;

import lombok.Getter;
import nl.jaimyputter.server.websocket.Server;
import nl.jaimyputter.server.websocket.framework.modular.Module;
import nl.jaimyputter.server.websocket.framework.registry.ModulePriority;
import nl.jaimyputter.server.websocket.modules.gamemode.framework.GameState;
import nl.jaimyputter.server.websocket.modules.gamemode.framework.Team;
import nl.jaimyputter.server.websocket.modules.packet.PacketModule;
import nl.jaimyputter.server.websocket.modules.packet.packets.out.gamemode.PacketOutGameModeLose;
import nl.jaimyputter.server.websocket.modules.packet.packets.out.gamemode.PacketOutGameModePointsUpdate;
import nl.jaimyputter.server.websocket.modules.packet.packets.out.gamemode.PacketOutGameModeStateUpdate;
import nl.jaimyputter.server.websocket.modules.packet.packets.out.gamemode.PacketOutGameModeWin;

import java.util.Date;

/**
 * Created by Spraxs
 * Date: 12/14/2019
 */

@ModulePriority
public final class GameModeModule extends Module {

    private @Getter GameState gameState;

    private static final int MAX_SCORE = 75;

    private @Getter int redScore;
    private @Getter int blueScore;


    // In milliseconds
    private @Getter long gameEndTime;

    private PacketModule packetModule;

    public GameModeModule() {
        super("GameMode");
    }

    public void onStart() {
        gameEndTime = System.currentTimeMillis() + (1000 * 10 * 60);

        packetModule = Server.byModule(PacketModule.class);

        updateGameState(GameState.LOBBY, false);

        redScore = 0;
        blueScore = 0;
    }

    public void updateGameState(GameState gameState, boolean updatePlayers) {
        this.gameState = gameState;

        if (updatePlayers) {
            packetModule.sendPacketToAllClients(new PacketOutGameModeStateUpdate(gameState));
        }

        // TODO Update database
    }


    public void addPoint(Team team) {
        if (team == Team.RED) {
            redScore++;

            packetModule.sendPacketToAllClients(new PacketOutGameModePointsUpdate(redScore, blueScore));

            if (redScore >= MAX_SCORE) {
                updateGameState(GameState.AFTER_MATCH, true);
                handleWin(Team.RED);
                handleLose(Team.BLUE);
            }

        } else if (team == Team.BLUE) {
            blueScore++;

            packetModule.sendPacketToAllClients(new PacketOutGameModePointsUpdate(redScore, blueScore));

            if (blueScore >= MAX_SCORE) {
                updateGameState(GameState.AFTER_MATCH, true);
                handleWin(Team.BLUE);
                handleLose(Team.RED);
            }
        }
    }

    public void handleWin(Team team) {
        Server.getOnlineClients().stream().filter(client -> client.getPlayer().getTeam() == team).forEach(client -> {
            client.channelSend(new PacketOutGameModeWin());
        });
    }

    public void handleLose(Team team) {
        Server.getOnlineClients().stream().filter(client -> client.getPlayer().getTeam() == team).forEach(client -> {
            client.channelSend(new PacketOutGameModeLose());
        });

    }
}
