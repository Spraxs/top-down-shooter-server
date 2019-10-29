package nl.jaimyputter.server.websocket.modules.packet;

import nl.jaimyputter.server.websocket.framework.modular.Module;
import nl.jaimyputter.server.websocket.framework.registry.ModulePriority;
import nl.jaimyputter.server.websocket.modules.packet.packets.PacketOut;
import nl.jaimyputter.server.websocket.server.Server;
import nl.jaimyputter.server.websocket.server.handlers.Client;

/**
 * Created by Spraxs
 * Date: 10/29/2019
 */

@ModulePriority(99)
public class PacketModule extends Module {

    public PacketModule() {
        super("Packet");
    }

    @Override
    public void onStart() {

    }

    public void sendPacketToAllClients(PacketOut packet) {
        Server.getOnlineClients().forEach(client -> client.channelSend(packet));
    }

    public void sendPacketToAllClientsExcept(PacketOut packet, Client filteredClient) {
        Server.getOnlineClients().stream().filter(client -> client != filteredClient).forEach(client -> client.channelSend(packet));
    }
}
