package nl.jaimyputter.server.websocket.modules.packet;

import nl.jaimyputter.server.websocket.framework.modular.Module;
import nl.jaimyputter.server.websocket.framework.registry.ModulePriority;
import nl.jaimyputter.server.websocket.modules.packet.packets.PacketIn;
import nl.jaimyputter.server.websocket.modules.packet.packets.PacketOut;
import nl.jaimyputter.server.websocket.server.Server;
import nl.jaimyputter.server.websocket.server.handlers.Client;
import nl.jaimyputter.server.websocket.utils.ReflectionUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Spraxs
 * Date: 10/29/2019
 */

@ModulePriority(99)
public class PacketModule extends Module {

    public PacketModule() {
        super("Packet");
    }

    private Map<Short, Class<? extends PacketIn>> packetClasses = new HashMap<>();

    @Override
    public void onStart() {

    }

    public void sendPacketToAllClients(PacketOut packet) {
        Server.getOnlineClients().forEach(client -> client.channelSend(packet));
    }

    public void sendPacketToAllClientsExcept(PacketOut packet, Client filteredClient) {
        Server.getOnlineClients().stream().filter(client -> client != filteredClient).forEach(client -> client.channelSend(packet));
    }

    public PacketIn GetIncomingPacket(byte[] bytes) {
        PacketIn packetIn = new PacketIn(bytes);

        Class<? extends PacketIn> correctPacketClass = packetClasses.get(packetIn.readShort());


    }
}
