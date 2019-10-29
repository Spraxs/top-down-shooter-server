package nl.jaimyputter.server.websocket.framework.managers;

/**
 * Created by Spraxs
 * Date: 10/29/2019
 */

public class IdManager {

    private static volatile long lastId = 0;

    public static synchronized long getNextId() {
        return lastId++;
    }
}
