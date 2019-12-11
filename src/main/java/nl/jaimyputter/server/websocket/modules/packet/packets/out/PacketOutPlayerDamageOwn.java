package nl.jaimyputter.server.websocket.modules.packet.packets.out;

import nl.jaimyputter.server.websocket.framework.geometry.Vector2;
import nl.jaimyputter.server.websocket.modules.packet.packets.PacketOut;

/**
 * Created by Spraxs
 * Date: 12/11/2019
 */

public class PacketOutPlayerDamageOwn extends PacketOut {

    public double damage;
    public double playerHealth;

    public double damagePositionX;
    public double damagePositionY;

    public PacketOutPlayerDamageOwn(float damage, float playerHealth, Vector2 damagePosition) {
        this.damage = damage;
        this.playerHealth = playerHealth;

        this.damagePositionX = damagePosition.getX();
        this.damagePositionY = damagePosition.getY();
    }

    @Override
    public void onDataPrepare() {
        id = 7;
    }
}
