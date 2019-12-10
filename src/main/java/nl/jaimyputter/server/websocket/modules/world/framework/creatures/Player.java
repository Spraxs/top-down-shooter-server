package nl.jaimyputter.server.websocket.modules.world.framework.creatures;

import lombok.Getter;
import nl.jaimyputter.server.websocket.framework.geometry.BoxCollider2;
import nl.jaimyputter.server.websocket.modules.packet.packets.PacketOut;
import nl.jaimyputter.server.websocket.modules.world.framework.utils.Transform;
import nl.jaimyputter.server.websocket.server.handlers.Client;

/**
 * Created by Spraxs
 * Date: 10/29/2019
 */

public class Player extends Creature {

    private @Getter final Client client;
    private @Getter final String name;

    public Player(Transform transform, BoxCollider2 boxCollider2, Client client, String name) {
        super(transform, boxCollider2);
        this.client = client;
        this.name = name;
    }

    public void channelSend(PacketOut packet)
    {
        client.channelSend(packet);
    }

    @Override
    public void onDeath() {
        super.onDeath();

        // TODO: Send player confirm dialog to go at nearest re-spawn location.
    }

    @Override
    public boolean isPlayer() {
        return true;
    }

    @Override
    public Player asPlayer() {
        return this;
    }
}