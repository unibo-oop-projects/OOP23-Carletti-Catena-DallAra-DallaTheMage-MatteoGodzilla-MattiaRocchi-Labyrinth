package com.ccdr.labyrinth.result;

import java.util.ArrayList;
import java.util.List;

import com.ccdr.labyrinth.engine.Engine;
import com.ccdr.labyrinth.engine.Executor;

public class ResultController implements Executor, ResultInputs{

    private Engine engine;
    private List<ResultView> views = new ArrayList<>();

    public ResultController(final Engine engine){
        this.engine = engine;
    }

    public void addView(final ResultView view ){
        this.views.add(view);
    }

    @Override
    public void onEnable() {
        for (ResultView resultView : views) {
            resultView.onEnable();
        }
    }

    @Override
    public void update(double deltaTime) {
        for (ResultView resultView : views) {
            resultView.draw();
        }
    }

    @Override
    public void close() {
        this.engine.changeExecutor(ID.MENU);
    }

}