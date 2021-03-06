package nl.jaimyputter.server.websocket.modules.packet.framework;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME) @Target({ElementType.TYPE})
public @interface PacketId {

    int value() default 0;
}