package nl.jaimyputter.server.framework;

public final class Config {

    public static int IO_PACKET_THREAD_CORE_SIZE;
    public static String SERVER_IP;
    public static int SERVER_PORT;

    public static void load() {

        SERVER_IP = "116.203.114.52";
        SERVER_PORT = 25565;


        IO_PACKET_THREAD_CORE_SIZE = 2;
    }
}