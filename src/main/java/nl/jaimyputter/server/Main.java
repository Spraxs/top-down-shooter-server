package nl.jaimyputter.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import nl.jaimyputter.server.framework.Config;
import nl.jaimyputter.server.modules.network.ClientInitializer;
import nl.jaimyputter.server.modules.network.packets.Encryption;

import java.awt.*;
import java.util.logging.Logger;

public final class Main {

    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {

        try {
            new Main();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Main() throws InterruptedException {

        // Initialize all Modules

        // Keep start time for later.
        final long serverLoadStart = System.currentTimeMillis();

        printSection("Configs");
        Config.load();

        printSection("Encryption");
        Encryption.init();

        // Post info.
        printSection("Info");
        LOGGER.info("Server loaded in " + ((System.currentTimeMillis() - serverLoadStart) / 1000) + " seconds.");
        System.gc();
        LOGGER.info("Started, using " + ((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1048576) + " of " + (Runtime.getRuntime().maxMemory() / 1048576) + " MB total memory.");


        new ServerBootstrap()
                .group(new NioEventLoopGroup(1), new NioEventLoopGroup(Config.IO_PACKET_THREAD_CORE_SIZE))
                .channel(NioServerSocketChannel.class)
                .childHandler(new ClientInitializer())
                .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                .bind(Config.SERVER_IP, Config.SERVER_PORT)
                .sync();

        LOGGER.info("Listening on " + Config.SERVER_IP + ":" + Config.SERVER_PORT);

        // Notify sound.
        Toolkit.getDefaultToolkit().beep();
    }

    private void printSection(String s)
    {
        s = "=[ " + s + " ]";
        while (s.length() < 62)
        {
            s = "-" + s;
        }
        LOGGER.info(s);
    }
}