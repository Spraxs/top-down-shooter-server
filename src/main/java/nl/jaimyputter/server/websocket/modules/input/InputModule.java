package nl.jaimyputter.server.websocket.modules.input;

import nl.jaimyputter.server.websocket.framework.modular.Module;
import nl.jaimyputter.server.websocket.framework.registry.ModulePriority;

/**
 * Created by Spraxs
 * Date: 11/21/2019
 */

@ModulePriority(0)
public class InputModule extends Module {

    public InputModule() {
        super("Input");
    }
}
