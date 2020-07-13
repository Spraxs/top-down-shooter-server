package nl.jaimyputter.server.websocket.modules.gamemode;

import lombok.Getter;
import nl.jaimyputter.server.websocket.Server;
import nl.jaimyputter.server.websocket.framework.events.EventManager;
import nl.jaimyputter.server.websocket.framework.modular.Module;
import nl.jaimyputter.server.websocket.framework.registry.ModulePriority;
import nl.jaimyputter.server.websocket.modules.gamemode.events.GameTimeOverEvent;
import nl.jaimyputter.server.websocket.modules.gamemode.framework.GameState;
import nl.jaimyputter.server.websocket.modules.gamemode.framework.Team;
import nl.jaimyputter.server.websocket.modules.packet.PacketModule;
import nl.jaimyputter.server.websocket.modules.packet.packets.out.PacketOutPlayerDisconnect;
import nl.jaimyputter.server.websocket.modules.packet.packets.out.gamemode.PacketOutGameModeLose;
import nl.jaimyputter.server.websocket.modules.packet.packets.out.gamemode.PacketOutGameModePointsUpdate;
import nl.jaimyputter.server.websocket.modules.packet.packets.out.gamemode.PacketOutGameModeStateUpdate;
import nl.jaimyputter.server.websocket.modules.packet.packets.out.gamemode.PacketOutGameModeWin;
import nl.jaimyputter.server.websocket.modules.task.framework.Task;
import nl.jaimyputter.server.websocket.modules.world.WorldModule;
import nl.jaimyputter.server.websocket.modules.world.framework.creatures.Player;

/**
 * Created by Spraxs
 * Date: 12/14/2019
 */

@ModulePriority
public final class GameModeModule extends Module {

    private @Getter GameState gameState;

    private static final int MAX_SCORE = 10;

    private @Getter int redScore;
    private @Getter int blueScore;

    // In milliseconds
    private @Getter long gameEndTime;

    private PacketModule packetModule;
    private WorldModule worldModule;

    public GameModeModule() {
        super("GameMode");
    }

    public void onStart() {
        gameEndTime = System.currentTimeMillis() + (1000 * 60 * 4);

        worldModule = Server.byModule(WorldModule.class);
        packetModule = Server.byModule(PacketModule.class);

        updateGameState(GameState.IN_GAME, false);

        redScore = 0;
        blueScore = 0;

        setupGameTimeTask();
    }

    public void updateGameState(GameState gameState, boolean updatePlayers) {
        this.gameState = gameState;

        if (updatePlayers) {
            packetModule.sendPacketToAllClients(new PacketOutGameModeStateUpdate(gameState));
        }

        if (gameState == GameState.AFTER_MATCH) {
            createKickTask();
        }

        // TODO Update database
    }

    private void createKickTask() {
        new Task() {
            @Override
            public void run() {
                for (Player p : worldModule.getAllPlayers()) {
                    worldModule.removePlayer(p);
                }
                
                System.exit(0);
            }
        }.runASyncLater(1000 * 10);
    }

    private void setupGameTimeTask() {

        new Task() {
            @Override
            public void run() {

                if (System.currentTimeMillis() >= gameEndTime) {
                    cancel();
                    EventManager.callEvent(new GameTimeOverEvent());
                }
            }
        }.runASyncTimer(1000L);
    }

    public void addPoint(Team team) {
        if (gameState != GameState.IN_GAME) return; // If gameState is not in-game, return

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

    public int getRedTeamSize() {
        int size = 0;

        for (Player player : worldModule.getAllPlayers()) {
            if (player.getTeam() == Team.RED) size++;
        }

        return size;
    }

    public int getBlueTeamSize() {
        int size = 0;

        for (Player player : worldModule.getAllPlayers()) {
            if (player.getTeam() == Team.BLUE) size++;
        }

        return size;
    }
}
