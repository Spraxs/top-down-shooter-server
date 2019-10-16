package nl.jaimyputter.server.modules.network.client;

import com.spraxs.js.modules.worldobjects.creatures.Player;
import com.spraxs.js.modules.network.packet.Encryption;
import com.spraxs.js.modules.network.packet.ReceivablePacket;
import com.spraxs.js.modules.network.packet.RecievablePacketManager;
import com.spraxs.js.modules.network.packet.SendablePacket;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.Getter;
import lombok.Setter;
import nl.jaimyputter.server.modules.network.packets.Encryption;
import nl.jaimyputter.server.modules.network.packets.ReceivablePacket;
import nl.jaimyputter.server.modules.network.packets.ReceivablePacketManager;
import nl.jaimyputter.server.modules.network.packets.SendablePacket;
import nl.jaimyputter.server.modules.world.creatures.Player;

import java.util.logging.Logger;

/**
 * Created by Spraxs
 * Date: 25-9-2018
 */

public class Client extends SimpleChannelInboundHandler<byte[]> {

    private static final Logger LOGGER = Logger.getLogger(Client.class.getName());

    private Channel channel;
    private @Getter String ip;
    private @Getter @Setter String accountName;
    private @Getter @Setter
    Player activeChar;

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        // Connected.
        channel = ctx.channel();
        ip = channel.remoteAddress().toString();
        ip = ip.substring(1, ip.lastIndexOf(':')); // Trim out /127.0.0.1:12345
    }

    public void channelSend(SendablePacket packet) {
        if (channel.isActive())  {
            channel.writeAndFlush(packet.getSendableBytes());
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, byte[] bytes) {
        ReceivablePacketManager.handle(this, new ReceivablePacket(Encryption.decrypt(bytes)));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {

    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        // Disconnected.
        // WorldManager.removeClient(this);
        LOGGER.finer("Client Disconnected: " + ctx.channel());
    }
}