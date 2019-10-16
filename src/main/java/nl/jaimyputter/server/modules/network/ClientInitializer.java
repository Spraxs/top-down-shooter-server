package nl.jaimyputter.server.modules.network;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.bytes.ByteArrayDecoder;
import io.netty.handler.codec.bytes.ByteArrayEncoder;
import nl.jaimyputter.server.modules.network.client.Client;

public class ClientInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) {
        ChannelPipeline pipeline = ch.pipeline();
        // Decoders.
        pipeline.addLast("decoder", new ByteArrayDecoder());
        pipeline.addLast("encoder", new ByteArrayEncoder());
        // Handle the client.
        pipeline.addLast("clientHandler", new Client());
    }
}