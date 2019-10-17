package nl.jaimyputter.server.socket.framework;

public abstract class Module {

    public Module() {
        onEnable();
    }

    public abstract void onEnable();

}