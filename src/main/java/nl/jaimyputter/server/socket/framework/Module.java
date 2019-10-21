package nl.jaimyputter.server.socket.framework;

import lombok.Getter;

public abstract class Module {

    public Module() {
        onEnable();
    }

    public abstract void onEnable();

}