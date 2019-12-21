package nl.jaimyputter.server.websocket.modules.packet.packets.out.gamemode;

import nl.jaimyputter.server.websocket.modules.packet.packets.PacketOut;

/**
 * Created by Spraxs
 * Date: 12/19/2019
 */

public class PacketOutGameModeJoin extends PacketOut {

    public long gameEndTimeInMillis;
    public int redScore;
    public int blueScore;
    public int stateId;

    public PacketOutGameModeJoin(long gameEndTimeInMillis, int redScore, int blueScore, int stateId) {
        this.gameEndTimeInMillis = gameEndTimeInMillis;
        this.redScore = redScore;
        this.blueScore = blueScore;
        this.stateId = stateId;
    }

    @Override
    public void onDataPrepare() {
        id = 14;
    }
}
