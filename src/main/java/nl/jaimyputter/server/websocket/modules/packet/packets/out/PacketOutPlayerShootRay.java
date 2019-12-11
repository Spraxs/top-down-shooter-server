package nl.jaimyputter.server.websocket.modules.packet.packets.out;

import nl.jaimyputter.server.websocket.framework.geometry.Vector2;
import nl.jaimyputter.server.websocket.modules.packet.packets.PacketOut;

/**
 * Created by Spraxs
 * Date: 12/11/2019
 */

public class PacketOutPlayerShootRay extends PacketOut {

    public PacketOutPlayerShootRay(Vector2 beginPosition, Vector2 hitPosition) {

    }

    @Override
    public void onDataPrepare() {
        id = 6;
    }
}
