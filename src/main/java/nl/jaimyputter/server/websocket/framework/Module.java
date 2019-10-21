package nl.jaimyputter.server.websocket.framework;

import lombok.Getter;
import nl.jaimyputter.server.websocket.Main;
import nl.jaimyputter.server.websocket.utils.ReflectionUtil;

import javax.net.ssl.SSLException;
import java.security.cert.CertificateException;

public abstract class Module {

    private final @Getter String name;
    private @Getter int priority;

    protected Module(String name) {
        this.name = name;
    }

    public void startModule() {

        onStart();
    }

    public void endModule() {

        onEnd();
    }

    public void onStart() {}

    public void onEnd() {}

    public void initModelePriority() {
        priority = ReflectionUtil.getClassModulePriorityAnnotation(Main.byModule(this.getClass()).getClass()).value();
    }

}
