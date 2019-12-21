package nl.jaimyputter.server.websocket.modules.packet.packets.out.gamemode;

import nl.jaimyputter.server.websocket.modules.gamemode.framework.GameState;
import nl.jaimyputter.server.websocket.modules.packet.packets.PacketOut;

/**
 * Created by Spraxs
 * Date: 12/14/2019
 */

public class PacketOutGameModeStateUpdate extends PacketOut {

    public int stateId;

    public PacketOutGameModeStateUpdate(GameState gameState) {
        this.stateId = gameState.getId();
    }

    @Override
    public void onDataPrepare() {
        id = 11;
    }
}
