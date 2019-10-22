package nl.jaimyputter.server.websocket.modules.task;

import io.netty.util.internal.ConcurrentSet;
import lombok.Getter;
import nl.jaimyputter.server.websocket.Main;
import nl.jaimyputter.server.websocket.framework.modular.Module;
import nl.jaimyputter.server.websocket.framework.registry.ModulePriority;
import nl.jaimyputter.server.websocket.modules.task.framework.Task;

/**
 * Created by Spraxs
 * Date: 10/22/2019
 */

@ModulePriority(100)
public class TaskModule extends Module {

    private @Getter ConcurrentSet<Task> tasks = new ConcurrentSet<>();

    private @Getter int lastTaskId = 0;

    public static TaskModule Instance;

    public TaskModule() {
        super("Task");

        Instance = this;
    }

    public int nextId() {
        return lastTaskId++;
    }
}
