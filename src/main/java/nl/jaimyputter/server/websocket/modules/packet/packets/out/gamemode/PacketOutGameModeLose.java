package nl.jaimyputter.server.websocket.modules.packet.packets.out.gamemode;

import nl.jaimyputter.server.websocket.modules.packet.packets.PacketOut;

/**
 * Created by Spraxs
 * Date: 12/14/2019
 */

public class PacketOutGameModeLose extends PacketOut {
    @Override
    public void onDataPrepare() {
        id = 13;
    }
}
