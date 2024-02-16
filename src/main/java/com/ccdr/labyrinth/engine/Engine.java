package com.ccdr.labyrinth.engine;

import java.util.Map;
import java.util.HashMap;

/**
 * This class is responsible for managing and running the main game loop.
 * The {@link Executor} interface defines what kind of classes can be executed from the engine.
 * By design executors must be bounded to an Executor ID, and only one executor can be running.
 * @see Executor
 */
public final class Engine {
    private static final double ONE_SECOND_IN_NANOS = 1e9;

    private final Map<Executor.ID, Executor> executors = new HashMap<>();
    private Executor.ID activeExecutor;

    private final double framerate;
    private volatile boolean running;

    /**
     * @param requestedFramerate framerate that the engine should target
     */
    public Engine(final double requestedFramerate) {
        this.framerate = requestedFramerate;
    }

    /**
     * Starts the engine. This function call blocks the caller.
     */
    public void start() {
        this.running = true;
        double lastTick = System.nanoTime();
        double lastFrame = lastTick;
        double timeElapsed = 0;
        while (this.running) {
            final double currentTick = System.nanoTime();
            final double deltaTick = currentTick - lastTick;
            timeElapsed += deltaTick;

            if (timeElapsed > ONE_SECOND_IN_NANOS / framerate) {
                final double newFrame = System.nanoTime();
                final double deltaTimeInSeconds = (newFrame - lastFrame) / ONE_SECOND_IN_NANOS;

                this.executors.get(activeExecutor).update(deltaTimeInSeconds);

                lastFrame = newFrame;
                timeElapsed -= ONE_SECOND_IN_NANOS / framerate;
            }

            lastTick = currentTick;
        }
    }

    /**
     * Stops the engine.
     */
    public void stop() {
        this.running = false;
    }

    /**
     * Switches the currently running executor to the one associated with the id provided.
     * @param id id of the new executor to run
     */
    public void changeExecutor(final Executor.ID id) {
        this.activeExecutor = id;
        if (this.activeExecutor != null) {
            executors.get(this.activeExecutor).onEnable();
        }
    }

    /**
     * Associates an executor id to an executor object.
     * @param id executor id
     * @param exec executor object
     */
    public void bindExecutor(final Executor.ID id, final Executor exec) {
        this.executors.put(id, exec);
    }
}
