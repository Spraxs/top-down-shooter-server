package nl.jaimyputter.server.websocket.modules.world;

import nl.jaimyputter.server.socket.framework.managers.IdManager;
import nl.jaimyputter.server.websocket.Main;
import nl.jaimyputter.server.websocket.framework.modular.Module;
import nl.jaimyputter.server.websocket.framework.registry.ModulePriority;
import nl.jaimyputter.server.websocket.modules.packet.PacketModule;
import nl.jaimyputter.server.websocket.modules.packet.packets.out.PacketOutPlayerSpawn;
import nl.jaimyputter.server.websocket.modules.world.framework.WorldObject;
import nl.jaimyputter.server.websocket.modules.world.framework.creatures.Player;
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

    public void onStart() {

    }

    public void createPlayer(Client client, String name) {
        Player player = new Player(client, name);

        playerObjects.put(player.getObjectId(), player);

        // Send packet to all clients of client
        Main.byModule(PacketModule.class).sendPacketToAllClients(new PacketOutPlayerSpawn(player));
    }

    public void removePlayer(Client client) {

        Optional<Player> optionalPlayer = playerObjects.values().stream().filter(player -> player.getClient().equals(client)).findFirst();

        if (optionalPlayer.isPresent()) {
            removePlayer(optionalPlayer.get());
        } else {
            System.out.println("Client " + client.getAccountName() + " has no player to remove.");
        }
    }

    public void removePlayer(Player player) {

        playerObjects.remove(player.getObjectId());

        // Send packet to all clients of player
        Main.byModule(PacketModule.class).sendPacketToAllClients(new PacketOutPlayerSpawn(player));
    }


}
