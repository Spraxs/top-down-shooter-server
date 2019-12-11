package nl.jaimyputter.server.websocket.modules.packet.packets.out;

import nl.jaimyputter.server.websocket.framework.geometry.Vector2;
import nl.jaimyputter.server.websocket.modules.packet.packets.PacketOut;

/**
 * Created by Spraxs
 * Date: 12/11/2019
 */

public class PacketOutDebugCollider extends PacketOut {

    public PacketOutDebugCollider(Vector2 pointA, Vector2 pointB, Vector2 pointC, Vector2 pointD) {
        x1 = pointA.getX();
        y1 = pointA.getY();

        x2 = pointB.getX();
        y2 = pointB.getY();

        x3 = pointC.getX();
        y3 = pointC.getY();

        x4 = pointD.getX();
        y4 = pointD.getY();
    }

    public double x1;
    public double y1;

    public double x2;
    public double y2;

    public double x3;
    public double y3;

    public double x4;
    public double y4;

    @Override
    public void onDataPrepare() {
        id = 5;
    }
}
