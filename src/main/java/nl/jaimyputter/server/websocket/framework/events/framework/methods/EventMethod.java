package nl.jaimyputter.server.websocket.framework.events.framework.methods;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nl.jaimyputter.server.websocket.framework.events.framework.Listener;
import nl.jaimyputter.server.websocket.framework.events.framework.registry.EventHandler;

import java.lang.reflect.Method;

@Getter @AllArgsConstructor
public class EventMethod {

    private Listener listener;
    private EventHandler eventHandler;
    private Method method;
}
