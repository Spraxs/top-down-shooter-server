package nl.jaimyputter.server.websocket.modules.gamemode.listeners;

import nl.jaimyputter.server.websocket.Server;
import nl.jaimyputter.server.websocket.framework.events.framework.Listener;
import nl.jaimyputter.server.websocket.framework.events.framework.registry.EventHandler;
import nl.jaimyputter.server.websocket.modules.gamemode.GameModeModule;
import nl.jaimyputter.server.websocket.modules.gamemode.framework.GameState;
import nl.jaimyputter.server.websocket.modules.gamemode.framework.Team;
import nl.jaimyputter.server.websocket.modules.packet.PacketModule;
import nl.jaimyputter.server.websocket.modules.packet.packets.out.gamemode.PacketOutGameModeJoin;
import nl.jaimyputter.server.websocket.modules.packet.packets.out.gamemode.PacketOutGameModeLose;
import nl.jaimyputter.server.websocket.modules.packet.packets.out.gamemode.PacketOutGameModeTeamUpdate;
import nl.jaimyputter.server.websocket.modules.packet.packets.out.gamemode.PacketOutGameModeWin;
import nl.jaimyputter.server.websocket.modules.world.WorldModule;
import nl.jaimyputter.server.websocket.modules.world.events.PlayerJoinEvent;
import nl.jaimyputter.server.websocket.modules.world.framework.creatures.Player;
import nl.jaimyputter.server.websocket.server.handlers.Client;

public class PlayerJoinEventListener implements Listener {

    private final PacketModule packetModule;
    private final GameModeModule gameModeModule;

    public PlayerJoinEventListener() {
        packetModule = Server.byModule(PacketModule.class);
        gameModeModule = Server.byModule(GameModeModule.class);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        final Client client = event.getPlayer().getClient();

        // Send gameMode info
        client.channelSend(new PacketOutGameModeJoin(gameModeModule.getGameEndTime(), gameModeModule.getRedScore(),
                gameModeModule.getBlueScore(), gameModeModule.getGameState().getId()));

        packetModule.sendPacketToAllClients(new PacketOutGameModeTeamUpdate(player));

        for (Player p : Server.byModule(WorldModule.class).getAllPlayers()) {
            if (p.getObjectId() == player.getObjectId()) continue;
            player.channelSend(new PacketOutGameModeTeamUpdate(p));
        }

        if (gameModeModule.getGameState() == GameState.AFTER_MATCH) {
            if (gameModeModule.getBlueScore() > gameModeModule.getRedScore()) {
                if (player.getTeam() == Team.BLUE) {
                    client.channelSend(new PacketOutGameModeWin());
                } else if (player.getTeam() == Team.RED) {
                    client.channelSend(new PacketOutGameModeLose());
                }
            } else if (gameModeModule.getRedScore() > gameModeModule.getBlueScore()) {
                if (player.getTeam() == Team.RED) {
                    client.channelSend(new PacketOutGameModeWin());
                } else if (player.getTeam() == Team.BLUE) {
                    client.channelSend(new PacketOutGameModeLose());
                }
            } else {
                client.channelSend(new PacketOutGameModeWin());
            }
        }
    }
}
