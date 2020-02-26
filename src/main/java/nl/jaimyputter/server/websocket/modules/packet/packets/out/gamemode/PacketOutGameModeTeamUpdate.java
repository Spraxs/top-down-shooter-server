package nl.jaimyputter.server.websocket.modules.packet.packets.out.gamemode;

import nl.jaimyputter.server.websocket.modules.packet.packets.PacketOut;
import nl.jaimyputter.server.websocket.modules.world.framework.creatures.Player;

public class PacketOutGameModeTeamUpdate extends PacketOut {

    public long objectId;
    public int teamId;

    public PacketOutGameModeTeamUpdate(Player player) {
        objectId = player.getObjectId();
        teamId = player.getTeam().getId();
    }

    @Override
    public void onDataPrepare() {
        id = 15;
    }
}
