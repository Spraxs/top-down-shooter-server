package nl.jaimyputter.server.websocket.modules.packet.packets.out;

import nl.jaimyputter.server.websocket.framework.geometry.Vector2;
import nl.jaimyputter.server.websocket.modules.packet.packets.PacketOut;

/**
 * Created by Spraxs
 * Date: 12/11/2019
 */

public class PacketOutPlayerShootRay extends PacketOut {

    private final Vector2 beginPosition;
    private final Vector2 endPosition;

    public long playerId;

    public double beginPositionX;
    public double beginPositionY;

    public double endPositionX;
    public double endPositionY;

    public byte hit;

    public PacketOutPlayerShootRay(long playerId, Vector2 beginPosition, Vector2 endPosition, byte hit) {
        this.playerId = playerId;

        this.beginPosition = beginPosition;
        this.endPosition = endPosition;

        this.hit = hit;
    }

    @Override
    public void onDataPrepare() {
        id = 6;

        beginPositionX = beginPosition.getX();
        beginPositionY = beginPosition.getY();

        endPositionX = endPosition.getX();
        endPositionY = endPosition.getY();
    }
}
