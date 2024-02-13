package com.ccdr.labyrinth.result;

import java.util.ArrayList;
import java.util.List;

import com.ccdr.labyrinth.engine.Engine;
import com.ccdr.labyrinth.engine.Executor;

/**
 * Class responsible for managing the result screen that appears after a game has ended.
 */
public final class ResultController implements Executor, ResultInputs {

    private Engine engine;
    private List<ResultView> views = new ArrayList<>();

    /**
     * @param engine reference to Engine class that will execute this
     */
    public ResultController(final Engine engine) {
        this.engine = engine;
    }

    /**
     * @param view ResultView object to bind to this controller
     */
    public void addView(final ResultView view) {
        this.views.add(view);
    }

    @Override
    public void onEnable() {
        for (ResultView resultView : views) {
            resultView.onEnable();
        }
    }

    @Override
    public void update(final double deltaTime) {
        for (ResultView resultView : views) {
            resultView.draw();
        }
    }

    @Override
    public void close() {
        this.engine.changeExecutor(ID.MENU);
    }
}
