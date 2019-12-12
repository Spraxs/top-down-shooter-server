package nl.jaimyputter.server.websocket.modules.world;

import nl.jaimyputter.server.websocket.framework.geometry.BoxCollider2;
import nl.jaimyputter.server.websocket.framework.geometry.Vector2;
import nl.jaimyputter.server.websocket.framework.modular.Module;
import nl.jaimyputter.server.websocket.framework.registry.ModulePriority;
import nl.jaimyputter.server.websocket.modules.packet.PacketModule;
import nl.jaimyputter.server.websocket.modules.packet.packets.out.PacketOutPlayerDisconnect;
import nl.jaimyputter.server.websocket.modules.world.framework.utils.Transform;
import nl.jaimyputter.server.websocket.modules.world.framework.WorldObject;
import nl.jaimyputter.server.websocket.modules.world.framework.creatures.Player;
import nl.jaimyputter.server.websocket.Server;
import nl.jaimyputter.server.websocket.server.handlers.Client;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Spraxs
 * Date: 10/22/2019
 */

@ModulePriority(98)
public class WorldModule extends Module {

    public WorldModule() {
        super("World");
    }

    private final Map<Long, Player> playerObjects = new ConcurrentHashMap<>();
    private final Map<Long, WorldObject> gameObjects = new ConcurrentHashMap<>();

    private PacketModule packetModule;

    public void onStart() {
        packetModule = Server.byModule(PacketModule.class);
    }

    public void handlePlayerDisconnect(Client client) {

        Player player = client.getPlayer();

        if (player == null) {
            System.out.println("Player is null on disconnect.");
            return;
        }

        removePlayer(player); // Remove player & send packets
    }

    public Player createPlayer(Client client, String name, Vector2 position, Vector2 colliderOffset, Vector2 colliderSize) {

        final Transform transform = new Transform(position);

        final BoxCollider2 boxCollider2 = new BoxCollider2(transform, colliderOffset, colliderSize);

        final Player player = new Player(transform, boxCollider2, client, name, packetModule);

        playerObjects.put(player.getObjectId(), player);

        return player;
    }

    public Optional<Player> getOptionalPlayer(long id) {
        Player player = playerObjects.get(id);

        if (player == null) return Optional.empty();

        return Optional.of(player);
    }

    public void removePlayer(Player player) {

        playerObjects.remove(player.getObjectId());

        // Send packet to all clients of player
        packetModule.sendPacketToAllClients(new PacketOutPlayerDisconnect(player.getObjectId()));
    }

    public Collection<Player> getAllPlayers() {
        return playerObjects.values();
    }
}
