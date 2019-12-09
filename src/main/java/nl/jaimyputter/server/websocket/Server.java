package nl.jaimyputter.server.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.util.SelfSignedCertificate;
import lombok.Getter;
import nl.jaimyputter.server.websocket.framework.modular.Module;
import nl.jaimyputter.server.websocket.modules.packet.packets.framework.Encryption;
import nl.jaimyputter.server.websocket.modules.task.framework.Task;
import nl.jaimyputter.server.websocket.server.handlers.Client;
import nl.jaimyputter.server.websocket.server.initializer.ClientInitializer;
import nl.jaimyputter.server.websocket.utils.ReflectionUtil;

import javax.net.ssl.SSLException;
import java.lang.reflect.InvocationTargetException;
import java.security.cert.CertificateException;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Logger;

/**
 * Created by Spraxs
 * Date: 10/29/2019
 */

public final class Server {

    public static final boolean SSL = System.getProperty("ssl") != null;
    public static final int PORT = Integer.parseInt(System.getProperty("port", SSL? "8443" : "8080"));

    private static Server Instance;

    private @Getter ConcurrentHashMap<Class, Module> modules = new ConcurrentHashMap<>();

    private @Getter Logger logger;

    public static void main(String[] args) {
        new Server();
    }

    public Server() {
        Instance = this;
        onStart();
        setupServer();
    }

    private void onStart() {

        logger = Logger.getLogger("server");

        Encryption.init();

        initModules();

        enableModulesWithPriority();
    }

    private static List<Client> ONLINE_CLIENTS = new CopyOnWriteArrayList<>();

    public static Collection<Client> getOnlineClients() {
        return ONLINE_CLIENTS;
    }

    public static void addClient(Client client) {
        if (!ONLINE_CLIENTS.contains(client)) {
            ONLINE_CLIENTS.add(client);
        }
    }

    public static void removeClient(Client client) {
        ONLINE_CLIENTS.remove(client);
    }



    public void setupServer() {
        new Task() {

            @Override
            public void run() {
                // Configure SSL.
                SslContext sslCtx = null;
                if (SSL) {
                    SelfSignedCertificate ssc = null;
                    try {
                        ssc = new SelfSignedCertificate();
                    } catch (CertificateException e) {
                        e.printStackTrace();
                    }

                    try {
                        sslCtx = SslContext.newServerContext(ssc.certificate(), ssc.privateKey());
                    } catch (SSLException e) {
                        e.printStackTrace();
                    }
                }

                EventLoopGroup bossGroup = new NioEventLoopGroup(1);
                EventLoopGroup workerGroup = new NioEventLoopGroup();
                try {
                    ServerBootstrap b = new ServerBootstrap();
                    b.group(bossGroup, workerGroup)
                            .channel(NioServerSocketChannel.class)
                            .childHandler(new ClientInitializer(sslCtx));

                    Channel ch = b.bind(PORT).sync().channel();

                    System.out.println("Open your web browser and navigate to " +
                            (SSL ? "https" : "http") + "://127.0.0.1:" + PORT + '/');

                    ch.closeFuture().sync();
                } catch (InterruptedException e) {
                    // Server error
                    System.out.println("Server startup error:");
                    e.printStackTrace();

                    System.exit(1);

                } finally {
                    bossGroup.shutdownGracefully();
                    workerGroup.shutdownGracefully();
                }

                disableModules();
            }
        }.runNewThread("server");
    }


    // Disables all modules
    private void disableModules() {
        modules.values().stream().sorted(Comparator.comparing(Module::getPriority)).forEach(Module::endModule);
    }

    // Enabled modules in order of Priority
    private void enableModulesWithPriority() {
        modules.values().stream().sorted(Collections.reverseOrder(Comparator.comparing(Module::getPriority))).forEach(Module::startModule);
    }

    // Cast all module classes to Module and enable them
    private void initModules() {
        ReflectionUtil.moduleClassScan(clazz -> {
            try {
                Module module = (Module) clazz.getDeclaredConstructor().newInstance();

                modules.put(clazz, module);

                module.initModelePriority();

            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }, getClass().getPackage().getName());
    }

    /**
     * Gets the server instance.
     *
     * @return  the Server
     */
    public static Server getServer() {
        return Instance;
    }

    public static <T extends Module> T byModule(Class<T> moduleClass) {
        return ReflectionUtil.getModule(moduleClass, getServer().getModules());
    }
}
