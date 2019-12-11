package nl.jaimyputter.server.websocket.modules.packet.packets.in;

import nl.jaimyputter.server.websocket.framework.geometry.GeometryHelper;
import nl.jaimyputter.server.websocket.framework.geometry.Vector2;
import nl.jaimyputter.server.websocket.modules.packet.framework.PacketId;
import nl.jaimyputter.server.websocket.modules.packet.packets.PacketIn;
import nl.jaimyputter.server.websocket.modules.packet.packets.out.PacketOutPlayerDamageOwn;
import nl.jaimyputter.server.websocket.modules.world.framework.creatures.Player;
import nl.jaimyputter.server.websocket.server.handlers.Client;

import java.io.ByteArrayInputStream;

/**
 * Created by Spraxs
 * Date: 12/11/2019
 */

@PacketId(6)
public class PacketInPlayerShootRay extends PacketIn {

    public double rayPositionX;
    public double rayPositionY;

    public double rayDirectionX;
    public double rayDirectionY;

    public PacketInPlayerShootRay(Client client, ByteArrayInputStream bais) {
        super(client, bais);
    }

    @Override
    public void onDataHandled() {
        Player player = client.getPlayer();

        Vector2 rayPosition = new Vector2((float) rayPositionX, (float) rayPositionY);
        Vector2 rayDirection = new Vector2((float) rayDirectionX, (float) rayDirectionY);

        Player hitPlayer = GeometryHelper.rayCastHitPlayer(rayPosition, rayDirection, player);

        if (hitPlayer != null) {
            float damage = 5.0f;

            hitPlayer.setHealth(hitPlayer.getHealth() - damage);

            hitPlayer.channelSend(new PacketOutPlayerDamageOwn(damage, hitPlayer.getHealth(), rayPosition));
        }
    }
}
