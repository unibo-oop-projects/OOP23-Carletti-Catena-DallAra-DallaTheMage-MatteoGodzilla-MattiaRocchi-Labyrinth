package com.ccdr.labyrinth.engine;

import java.util.Map;
import java.util.HashMap;

public class Engine {
    private static final double ONE_SECOND_IN_NANOS = 1e9;
    private static final double FRAMERATE = 120;

    private Map<Executor.ID,Executor> executors = new HashMap<>();
    private Executor.ID activeExecutor;

    public void start(){
        double lastTick = System.nanoTime();
        double lastFrame = lastTick;
        double timeElapsed = 0;
        while(true){
            double currentTick = System.nanoTime();
            double deltaTick = currentTick - lastTick;
            timeElapsed += deltaTick;

            if(timeElapsed > ONE_SECOND_IN_NANOS / FRAMERATE){
                double newFrame = System.nanoTime();
                double deltaTimeInSeconds = (newFrame - lastFrame) / ONE_SECOND_IN_NANOS;

                executors.get(activeExecutor).update(deltaTimeInSeconds);

                lastFrame = newFrame;
                timeElapsed -= ONE_SECOND_IN_NANOS / FRAMERATE;
            }

            lastTick = currentTick;
        }
    }

    public void changeExecutor(Executor.ID id){
        if(activeExecutor != null){
            executors.get(activeExecutor).onDisable();
        }
        activeExecutor = id;
        if(activeExecutor != null){
            executors.get(activeExecutor).onEnable(this);
        }
    }

    public void bindExecutor(Executor.ID id, Executor exec){
        executors.put(id, exec);
    }
}
