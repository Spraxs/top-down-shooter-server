package nl.jaimyputter.server.websocket.modules.packet.packets.out;

import nl.jaimyputter.server.websocket.modules.packet.packets.PacketOut;

public class PacketOutPlayerConnect extends PacketOut {

    public long playerId;
    public String playerName;
    public double posX;
    public double posY;

    public PacketOutPlayerConnect(long playerId, String playerName, double posX, double posY) {
        this.playerId = playerId;
        this.playerName = playerName;
        this.posX = posX;
        this.posY = posY;
    }

    @Override
    public void onDataPrepare() {
        id = 0; // TODO set this value with annotation
    }
}
