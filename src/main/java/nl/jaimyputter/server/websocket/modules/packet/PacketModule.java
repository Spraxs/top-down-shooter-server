package nl.jaimyputter.server.websocket.modules.packet;

import nl.jaimyputter.server.websocket.framework.modular.Module;
import nl.jaimyputter.server.websocket.framework.registry.ModulePriority;
import nl.jaimyputter.server.websocket.modules.packet.framework.PacketId;
import nl.jaimyputter.server.websocket.modules.packet.packets.PacketIn;
import nl.jaimyputter.server.websocket.modules.packet.packets.PacketOut;
import nl.jaimyputter.server.websocket.server.Server;
import nl.jaimyputter.server.websocket.server.handlers.Client;
import nl.jaimyputter.server.websocket.utils.ReflectionUtil;
import org.reflections.Reflections;

import java.io.ByteArrayInputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
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

    private Map<Short, Class<? extends PacketIn>> packetInClasses = new HashMap<>();

    @Override
    public void onStart() {
        registerIncomingPackets();
    }

    public void sendPacketToAllClients(PacketOut packet) {
        Server.getOnlineClients().forEach(client -> client.channelSend(packet));
    }

    public void sendPacketToAllClientsExcept(PacketOut packet, Client filteredClient) {
        Server.getOnlineClients().stream().filter(client -> client != filteredClient).forEach(client -> client.channelSend(packet));
    }

    public <T extends PacketIn> T getIncomingPacket(byte[] bytes) {

        ByteArrayInputStream _bais = new ByteArrayInputStream(bytes);

        short id = (short) ((_bais.read() & 0xff) | ((_bais.read() << 8) & 0xff00)); // Read & remove packet id from input stream

        Class<? extends PacketIn> correctPacketClass = packetInClasses.get(id);

        try {
            T packetIn = (T) correctPacketClass.getDeclaredConstructor(ByteArrayInputStream.class).newInstance(_bais);

            Field[] fields = correctPacketClass.getFields();

            for (Field field : fields) {
                Object object = packetIn.readNext(field.getType());
                field.set(packetIn, object);
            }

            packetIn.onDataHandled();

            return packetIn;

        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }

    private void registerIncomingPackets() {

        ReflectionUtil.getIncomingPacketClasses(getClass().getPackage().getName()).forEach(clazz -> {
            PacketId packetId = ReflectionUtil.getClassPacketIdAnnotation(clazz);

            if (packetId == null) {
                throw new NullPointerException("Packet class " + clazz.getSimpleName() + " has no @PacketId annotation!");
            } else {

                short id = (short) packetId.value();

                packetInClasses.put(id, clazz);
            }
        });
    }
}
