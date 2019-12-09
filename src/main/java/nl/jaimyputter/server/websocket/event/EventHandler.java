package nl.jaimyputter.server.websocket.event;

import nl.jaimyputter.server.websocket.event.events.Event;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashSet;

/**
 * Created by Spraxs
 * Date: 12/9/2019
 */

public class EventHandler {

    private Method[] methods;


    public void saveEvents(Class<? extends Listener> listener) {
        methods = listener.getMethods();

        HashSet<Method> methods = new HashSet<>();

        for (Method method : listener.getMethods()) {
            Parameter[] parameters = method.getParameters();


            if (parameters.length != 1) {
                continue;
            }

            Parameter parameter = parameters[0];

            if (parameter.getType().isAssignableFrom(Event.class)) {
                methods.add(method);
            }
        }

        this.methods = (Method[]) methods.toArray();
    }

}
