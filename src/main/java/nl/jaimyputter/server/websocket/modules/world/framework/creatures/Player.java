package nl.jaimyputter.server.websocket.modules.world.framework.creatures;

import lombok.Getter;
import nl.jaimyputter.server.websocket.Server;
import nl.jaimyputter.server.websocket.framework.geometry.BoxCollider2;
import nl.jaimyputter.server.websocket.modules.packet.PacketModule;
import nl.jaimyputter.server.websocket.modules.packet.packets.PacketOut;
import nl.jaimyputter.server.websocket.modules.packet.packets.out.PacketOutPlayerDeath;
import nl.jaimyputter.server.websocket.modules.packet.packets.out.PacketOutPlayerRespawn;
import nl.jaimyputter.server.websocket.modules.task.framework.Task;
import nl.jaimyputter.server.websocket.modules.world.framework.utils.Transform;
import nl.jaimyputter.server.websocket.server.handlers.Client;

/**
 * Created by Spraxs
 * Date: 10/29/2019
 */

public class Player extends Creature {

    private @Getter final Client client;
    private @Getter final String name;

    private final PacketModule packetModule;

    public Player(Transform transform, BoxCollider2 boxCollider2, Client client, String name, PacketModule packetModule) {
        super(transform, boxCollider2);
        this.client = client;
        this.name = name;

        this.packetModule = packetModule;
    }

    public void channelSend(PacketOut packet)
    {
        client.channelSend(packet);
    }

    @Override
    public void onDeath() {
        super.onDeath();

        // Send player death to all online clients
        Server.byModule(PacketModule.class).sendPacketToAllClients(new PacketOutPlayerDeath(getObjectId(), getTransform().getPosition()));

        new Task() {

            @Override
            public void run() {
                onRespawn();
            }
        }.runASyncLater(5000);
    }

    public void onRespawn() {
        setAlive(true);
        restoreHealth();
        packetModule.sendPacketToAllClients(new PacketOutPlayerRespawn(getObjectId(), getTransform().getPosition()));
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