package nl.jaimyputter.server.websocket.framework.events.framework.events;

public interface Cancellable {
    boolean isCancelled();

    void setCancelled(boolean var1);
}
