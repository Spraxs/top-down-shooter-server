package nl.jaimyputter.server.websocket.modules.packet.packets.out;

import lombok.Getter;
import nl.jaimyputter.server.websocket.modules.packet.packets.PacketOut;
import nl.jaimyputter.server.websocket.modules.world.framework.creatures.Player;

/**
 * Created by Spraxs
 * Date: 10/29/2019
 */

public class PacketOutPlayerDestroy extends PacketOut {

    private @Getter static int id = 1;

    public PacketOutPlayerDestroy(Player player) {
        super();
        writeShort(id);

        long playerObjectId = player.getObjectId();

        writeLong(playerObjectId);
    }
}
