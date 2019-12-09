package nl.jaimyputter.server.websocket.framework.modular;

import lombok.Getter;
import nl.jaimyputter.server.websocket.Server;
import nl.jaimyputter.server.websocket.utils.ReflectionUtil;

public abstract class Module {

    private final @Getter String name;
    private @Getter int priority;

    public Module(String name) {
        this.name = name;
    }

    public void startModule() {

        System.out.println("Enabling " + name + " module..");

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

}
