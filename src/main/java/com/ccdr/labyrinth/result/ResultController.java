package com.ccdr.labyrinth.result;

import java.util.ArrayList;
import java.util.List;

import com.ccdr.labyrinth.engine.Engine;
import com.ccdr.labyrinth.engine.Executor;
import com.ccdr.labyrinth.game.player.Player;

/**
 * Class responsible for managing the result screen that appears after a game has ended.
 */
public final class ResultController implements Executor, ResultInputs {

    private List<ResultView> views = new ArrayList<>();
    private List<Player> players;
    private Runnable closeAction;

    /**
     *
     */
    public ResultController() {}

    public void init(List<Player> players){
        this.players = players;
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
            resultView.draw(this.players);
        }
    }

    @Override
    public void close() {
        this.closeAction.run();
    }

    public void onClose(Runnable action){
        this.closeAction = action;
    }
}
