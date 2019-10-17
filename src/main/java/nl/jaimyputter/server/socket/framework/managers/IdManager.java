package nl.jaimyputter.server.socket.framework.managers;

/**
 * @author Pantelis Andrianakis
 */
public class IdManager
{
    private static volatile long lastId = 0;

    public static synchronized long getNextId() {
        return lastId++;
    }
}