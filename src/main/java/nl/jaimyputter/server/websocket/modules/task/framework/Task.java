package nl.jaimyputter.server.websocket.modules.task.framework;

import lombok.Getter;
import nl.jaimyputter.server.websocket.modules.task.TaskModule;

/**
 * Created by Spraxs
 * Date: 10/22/2019
 */

public abstract class Task implements Runnable {

    private @Getter int id;

    private @Getter Thread thread;

    public Task() {
        id = TaskModule.Instance.nextId();
    }

    public void runNewThread() {
        runNewThread(null);
    }

    public void runNewThread(String name) {
        thread = new Thread(this::run);

        if (name != null)
            thread.setName(name);

        thread.start();
    }

    public void cancel() {

    }
}
