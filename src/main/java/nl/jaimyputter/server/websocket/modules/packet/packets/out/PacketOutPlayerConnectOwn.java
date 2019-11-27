package nl.jaimyputter.server.websocket.modules.packet.packets.out;

/**
 * Created by Spraxs
 * Date: 11/27/2019
 */

public class PacketOutPlayerConnectOwn extends PacketOutPlayerConnect {


    public PacketOutPlayerConnectOwn(long playerId, String playerName, double posX, double posY) {
        super(playerId, playerName, posX, posY);
    }

    @Override
    public void onDataPrepare() {
        id = 2;
    }
}
