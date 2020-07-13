package nl.jaimyputter.server.websocket.framework.events;

import lombok.Getter;
import nl.jaimyputter.server.websocket.framework.events.framework.Listener;
import nl.jaimyputter.server.websocket.framework.events.framework.events.Cancellable;
import nl.jaimyputter.server.websocket.framework.events.framework.events.Event;
import nl.jaimyputter.server.websocket.framework.events.framework.methods.EventMethod;
import nl.jaimyputter.server.websocket.framework.events.framework.registry.EventHandler;
import nl.jaimyputter.server.websocket.utils.ReflectionUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public class EventManager {

    private @Getter Map<Class<? extends Event>, List<EventMethod>> events = new HashMap<>();

    private static @Getter EventManager instance;

    public static void registerListener(Listener listener) {
        final Set<Method> methods = ReflectionUtil.getMethodsAnnotatedWith(listener.getClass(), EventHandler.class);

        final Set<Method> removeMethods = new HashSet<>();

        // Filter non event methods
        for (Method method : methods) {

            if (method.getParameterCount() != 1) {
                removeMethods.add(method);
                continue;
            }

            if (!Event.class.isAssignableFrom(method.getParameterTypes()[0])) {

                removeMethods.add(method);
            }
        }


        // Remove unwanted methods
        for (Method removeMethod : removeMethods) {
            methods.remove(removeMethod);
        }

        if (methods.size() == 0) return; // Return if no methods are left

        // Add correct event methods to method list of Event type class
        for (Method method : methods) {
            EventHandler eventHandler = method.getAnnotation(EventHandler.class);
            Class<? extends Event> eventType = (Class<? extends Event>) method.getParameterTypes()[0];

            List<EventMethod> methodList;

            if (!instance.events.containsKey(eventType)) {
                methodList = new ArrayList<>();
                instance.events.put(eventType, methodList);
            } else {
                methodList = instance.events.get(eventType);
            }

            methodList.add(new EventMethod(listener, eventHandler, method));
        }
    }

    public static void callEvent(Event event) {

        if (!instance.events.containsKey(event.getClass())) return;

        Class<?>[] interfaces = event.getClass().getInterfaces();

        boolean cancellable = false;

        for (Class<?> anInterface : interfaces) {
            if (anInterface.equals(Cancellable.class)) {
                cancellable = true;
                break;
            }
        }

        List<EventMethod> methodList = instance.events.get(event.getClass());

        methodList = methodList.stream().sorted(Comparator.comparing(eventMethod -> eventMethod.getEventHandler().priority())).collect(Collectors.toList());

        for (EventMethod eventMethod : methodList) {
            if (eventMethod.getEventHandler().ignoreCancelled() && cancellable && ((Cancellable) event).isCancelled()) continue; // Ignore method if cancelled & ignoreCancelled

            try {
                eventMethod.getMethod().invoke(eventMethod.getListener(), event);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    public static EventManager init() {
        instance = new EventManager();

        return instance;
    }
}
