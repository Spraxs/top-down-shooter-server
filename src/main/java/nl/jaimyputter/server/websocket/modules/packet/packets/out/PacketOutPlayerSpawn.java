package nl.jaimyputter.server.websocket.modules.packet.packets.out;

import lombok.Getter;
import nl.jaimyputter.server.websocket.modules.packet.packets.PacketOut;
import nl.jaimyputter.server.websocket.modules.world.framework.creatures.Player;

/**
 * Created by Spraxs
 * Date: 10/29/2019
 */

public class PacketOutPlayerSpawn extends PacketOut {

    private @Getter static int id = 0;

    public PacketOutPlayerSpawn(Player player) {
        super();
        writeShort(id);

        long playerObjectId = player.getObjectId();

        float x = player.getLocation().getX();
        float y = player.getLocation().getY();

        writeLong(playerObjectId);

        writeFloat(x);
        writeFloat(y);
    }
}
