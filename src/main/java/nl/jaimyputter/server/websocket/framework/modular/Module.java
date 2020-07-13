package nl.jaimyputter.server.websocket.framework.modular;

import lombok.Getter;
import nl.jaimyputter.server.websocket.Server;
import nl.jaimyputter.server.websocket.framework.events.EventManager;
import nl.jaimyputter.server.websocket.framework.events.framework.Listener;
import nl.jaimyputter.server.websocket.utils.ReflectionUtil;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class Module {

    private final @Getter String name;
    private @Getter int priority;

    private final @Getter Logger logger;

    private @Getter Set<Listener> eventListeners = new HashSet<>();

    public Module(String name) {
        this.name = name;

        logger = LoggerFactory.getLogger(name);
    }

    public void startModule() {

        System.out.println("Enabling " + name + " module..");

        String packageName = getClass().getPackage().getName();

        registerListeners(packageName);

        onStart();

        System.out.println("Enabled " + name + " module");
    }

    public void endModule() {

        onEnd();
    }

    public void onStart() {}

    public void onEnd() {}

    public void initModelePriority() {
        boolean error = false;
        try {
            priority = ReflectionUtil.getClassModulePriorityAnnotation(Server.byModule(this.getClass()).getClass()).value();
        } catch (NullPointerException e) {
            throw new NullPointerException("No ModulePriority annotation set in " + name + " module.");
        }
    }

    private void registerListeners(String packageName) {
        logger.info("Registering listeners..");

        Set<Class<? extends Listener>> classes = new Reflections(packageName).getSubTypesOf(Listener.class);

        classes = classes.stream().filter(clazz -> ReflectionUtil.getClassDontRegisterAnnotation(clazz) == null).collect(Collectors.toSet());

        classes.stream().filter(ReflectionUtil::isListener).forEach(listenerClass -> {
            try {
                try {
                    Constructor<?> constr = listenerClass.getConstructor();
                    Listener listener = (Listener) constr.newInstance();
                    EventManager.registerListener(listener);
                    eventListeners.add(listener);

                    logger.info("RBL: " + listenerClass.getName());
                } catch (NoSuchMethodException e) {
                    logger.error("Failed to register listener " + listenerClass.getSimpleName() + ", NoArgsConstructor not found. Skipping...");
                }
            } catch (Exception e) {
                logger.error("Could not instantiate listener " + listenerClass.getSimpleName() + "!");
                e.printStackTrace();
            }
        });
    }

}
