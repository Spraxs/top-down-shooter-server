package nl.jaimyputter.server.websocket.event.events;

import lombok.NonNull;
import nl.jaimyputter.server.websocket.modules.world.framework.creatures.Player;

public abstract class PlayerEvent extends Event {

    protected Player player;

    public PlayerEvent(@NonNull Player who) {
        this.player = who;
    }

    PlayerEvent(@NonNull Player who, boolean async) {
        super(async);
        this.player = who;
    }

    @NonNull
    public final Player getPlayer() {
        return this.player;
    }
}
