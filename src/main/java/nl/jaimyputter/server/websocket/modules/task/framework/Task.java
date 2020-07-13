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

    private boolean cancelled = false;

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

    @Deprecated
    public void runLater(long timeMillis) {
         // Run on Task thread
    }

    public void runASyncLater(long timeInMillis) {
        runASyncLater(timeInMillis, null);
    }

    public void runASyncLater(long timeInMillis, String name) {
        thread = new Thread(() -> {
            try {
                sleepRun(timeInMillis);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        if (name != null)
            thread.setName(name);

        thread.start();
    }

    public void runASyncTimer(long periodInMillis) {
        runASyncTimer(periodInMillis, null);
    }

    public void runASyncTimer(long periodInMillis, String name) {
        thread = new Thread(() -> {
            while (!cancelled) {
                try {
                    sleepRun(periodInMillis);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        if (name != null)
            thread.setName(name);

        thread.start();
    }

    private void sleepRun(long timeInMillis) throws InterruptedException {
        Thread.sleep(timeInMillis);

        run();
    }

    public void cancel() {
        cancelled = true;
    }
}
