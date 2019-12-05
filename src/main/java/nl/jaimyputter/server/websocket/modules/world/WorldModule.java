package nl.jaimyputter.server.websocket.modules.world;

import nl.jaimyputter.server.websocket.Main;
import nl.jaimyputter.server.websocket.framework.modular.Module;
import nl.jaimyputter.server.websocket.framework.registry.ModulePriority;
import nl.jaimyputter.server.websocket.modules.packet.PacketModule;
import nl.jaimyputter.server.websocket.modules.packet.packets.out.PacketOutPlayerConnect;
import nl.jaimyputter.server.websocket.modules.packet.packets.out.PacketOutPlayerConnectOwn;
import nl.jaimyputter.server.websocket.modules.packet.packets.out.PacketOutPlayerDisconnect;
import nl.jaimyputter.server.websocket.modules.world.framework.Location;
import nl.jaimyputter.server.websocket.modules.world.framework.WorldObject;
import nl.jaimyputter.server.websocket.modules.world.framework.creatures.Player;
import nl.jaimyputter.server.websocket.server.Server;
import nl.jaimyputter.server.websocket.server.handlers.Client;

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

    private final PacketModule packetModule = Main.byModule(PacketModule.class);

    public void onStart() {
    }

    public void handlePlayerDisconnect(Client client) {

        Player player = client.getPlayer();

        if (player == null) {
            System.out.println("Player is null on disconnect.");
            return;
        }

        removePlayer(player); // Remove player & send packets
    }

    public Player createPlayer(Client client, String name, double posX, double posY) {
        Player player = new Player(client, name);

        player.setLocation(new Location(posX, posY));

        playerObjects.put(player.getObjectId(), player);

        return player;
    }

    public void removePlayer(Client client) {

        Optional<Player> optionalPlayer = playerObjects.values().stream().filter(player -> player.getClient().equals(client)).findFirst();

        if (optionalPlayer.isPresent()) {
            removePlayer(optionalPlayer.get());
        } else {
            System.out.println("Client " + client.getAccountName() + " has no player to remove.");
        }
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
}
