/*
 * Copyright 2014 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package nl.jaimyputter.server.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.util.SelfSignedCertificate;
import lombok.Getter;
import nl.jaimyputter.server.websocket.framework.Module;
import nl.jaimyputter.server.websocket.server.initializer.ServerInitializer;
import nl.jaimyputter.server.websocket.utils.ReflectionUtil;

import javax.net.ssl.SSLException;
import java.lang.reflect.InvocationTargetException;
import java.security.cert.CertificateException;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A Benchmark application for websocket which is served at:
 *
 * http://localhost:8080/websocket
 *
 * Open your browser at http://localhost:8080/, then the benchmark page will be loaded and a Web Socket connection will
 * be made automatically.
 */
public final class Main {

    public static final boolean SSL = System.getProperty("ssl") != null;
    public static final int PORT = Integer.parseInt(System.getProperty("port", SSL? "8443" : "8080"));

    private static Main Instance;

    private @Getter ConcurrentHashMap<Class, Module> modules = new ConcurrentHashMap<>();

    private Main() {
        Instance = this;

        try {
            setupServer();
        } catch (CertificateException | InterruptedException | SSLException e) {
            System.out.println("Server startup error:");
            e.printStackTrace();

            System.exit(1);
            return;
        }

        onStart();
    }

    public void setupServer() throws CertificateException, SSLException, InterruptedException {
        // Configure SSL.
        final SslContext sslCtx;
        if (SSL) {
            SelfSignedCertificate ssc = new SelfSignedCertificate();
            sslCtx = SslContext.newServerContext(ssc.certificate(), ssc.privateKey());
        } else {
            sslCtx = null;
        }

        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ServerInitializer(sslCtx));

            Channel ch = b.bind(PORT).sync().channel();

            System.out.println("Open your web browser and navigate to " +
                    (SSL? "https" : "http") + "://127.0.0.1:" + PORT + '/');

            ch.closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    private void onStart() {

        initModules();

        enableModulesWithPriority();
    }

    public static void main(String[] args) throws Exception {
        new Main();
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

    // Disables all modules
    private void disableModules() {
        modules.values().stream().sorted(Comparator.comparing(Module::getPriority)).forEach(Module::endModule);
    }

    /**
     * Gets the plugin instance.
     *
     * @return  the RadiationPlugin
     */
    public static Main getInstance() {
        return Instance;
    }

    public static <T extends Module> T byModule(Class<T> moduleClass) {
        return ReflectionUtil.getModule(moduleClass, getInstance().getModules());
    }
}