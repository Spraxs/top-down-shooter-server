package nl.jaimyputter.server.websocket.modules.packet.packets.out.gamemode;

import nl.jaimyputter.server.websocket.modules.packet.packets.PacketOut;

/**
 * Created by Spraxs
 * Date: 12/14/2019
 */

public class PacketOutGameModePointsUpdate extends PacketOut {

    public int redScore;
    public int blueScore;

    public PacketOutGameModePointsUpdate(int redScore, int blueScore) {
        this.redScore = redScore;
        this.blueScore = blueScore;
    }

    @Override
    public void onDataPrepare() {
        id = 10;
    }
}
