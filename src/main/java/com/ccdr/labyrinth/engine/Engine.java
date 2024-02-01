package com.ccdr.labyrinth.engine;

import java.util.Map;
import java.util.HashMap;

/**
 * This class is responsible for managing and running the main game loop.
 * The {@link Executor} interface defines what kind of classes can be executed from the engine.
 * By design executors must be bounded to an Executor ID, and only one executor can be running.
 * @see Executor
 */
public class Engine {
    private static final double ONE_SECOND_IN_NANOS = 1e9;

    private Map<Executor.ID,Executor> executors = new HashMap<>();
    private Executor.ID activeExecutor;

    private double framerate;
    private volatile boolean running;

    public Engine(double requestedFramerate){
        this.framerate = requestedFramerate;
    }

    public void start(){
        this.running = true;
        double lastTick = System.nanoTime();
        double lastFrame = lastTick;
        double timeElapsed = 0;
        while(this.running){
            double currentTick = System.nanoTime();
            double deltaTick = currentTick - lastTick;
            timeElapsed += deltaTick;

            if(timeElapsed > ONE_SECOND_IN_NANOS / framerate){
                double newFrame = System.nanoTime();
                double deltaTimeInSeconds = (newFrame - lastFrame) / ONE_SECOND_IN_NANOS;

                this.executors.get(activeExecutor).update(deltaTimeInSeconds);

                lastFrame = newFrame;
                timeElapsed -= ONE_SECOND_IN_NANOS / framerate;
            }

            lastTick = currentTick;
        }
    }

    public void stop(){
        this.running = false;
    }

    //this might need syncronized, since it gets called from multiple threads
    public void changeExecutor(Executor.ID id){
        if(this.activeExecutor != null){
            executors.get(this.activeExecutor).onDisable();
        }
        this.activeExecutor = id;
        if(this.activeExecutor != null){
            executors.get(this.activeExecutor).onEnable(this);
        }
    }

    public void bindExecutor(Executor.ID id, Executor exec){
        this.executors.put(id, exec);
    }
}
