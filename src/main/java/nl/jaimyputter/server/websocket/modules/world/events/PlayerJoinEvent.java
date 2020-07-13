package nl.jaimyputter.server.websocket.modules.world.events;

import lombok.Getter;
import nl.jaimyputter.server.websocket.framework.events.framework.events.Event;
import nl.jaimyputter.server.websocket.modules.world.framework.creatures.Player;

public class PlayerJoinEvent extends Event {
    private @Getter Player player;

    public PlayerJoinEvent(Player player) {
        this.player = player;
    }
}
