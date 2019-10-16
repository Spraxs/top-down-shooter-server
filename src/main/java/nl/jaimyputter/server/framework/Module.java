package nl.jaimyputter.server.framework;

public abstract class Module {

    public Module() {
        onEnable();
    }

    public abstract void onEnable();

}