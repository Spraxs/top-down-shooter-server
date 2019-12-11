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

    public double damageDirectionX;
    public double damageDirectionY;

    public PacketOutPlayerDamageOwn(float damage, float playerHealth, Vector2 damageDirection) {
        this.damage = damage;
        this.playerHealth = playerHealth;

        this.damageDirectionX = damageDirection.getX();
        this.damageDirectionY = damageDirection.getY();
    }

    @Override
    public void onDataPrepare() {
        id = 7;
    }
}
