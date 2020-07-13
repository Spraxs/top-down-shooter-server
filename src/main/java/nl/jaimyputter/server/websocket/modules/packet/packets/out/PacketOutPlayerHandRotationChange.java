package nl.jaimyputter.server.websocket.modules.packet.packets.out;

import nl.jaimyputter.server.websocket.framework.geometry.Vector2;
import nl.jaimyputter.server.websocket.modules.packet.packets.PacketOut;

public class PacketOutPlayerHandRotationChange extends PacketOut {

    private final Vector2 handPosition;

    public long playerId;

    public double posX;
    public double posY;

    public double rotZ;

    public double scaleX;

    public PacketOutPlayerHandRotationChange(long playerId, Vector2 handPosition, double handRotationZ, double handScaleX) {
        this.playerId = playerId;
        this.handPosition = handPosition;

        this.rotZ = handRotationZ;
        this.scaleX = handScaleX;
    }

    @Override
    public void onDataPrepare() {
        id = 16;

        posX = handPosition.getX();
        posY = handPosition.getY();
    }
}