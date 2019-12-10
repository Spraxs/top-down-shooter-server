package nl.jaimyputter.server.websocket.modules.packet.packets.out;

import nl.jaimyputter.server.websocket.modules.packet.packets.PacketOut;
import nl.jaimyputter.server.websocket.modules.world.framework.utils.Transform;
import nl.jaimyputter.server.websocket.modules.world.framework.creatures.Player;

/**
 * Created by Spraxs
 * Date: 12/5/2019
 */

public class PacketOutPlayerPositionChange extends PacketOut {

    private final Player player;

    public long playerId;

    public double posX;
    public double posY;

    public PacketOutPlayerPositionChange(Player player) {
        this.player = player;

        playerId = player.getObjectId();
    }

    @Override
    public void onDataPrepare() {
        id = 4;

        Transform location = player.getLocation();

        posX = location.getX();
        posY = location.getY();
    }
}
