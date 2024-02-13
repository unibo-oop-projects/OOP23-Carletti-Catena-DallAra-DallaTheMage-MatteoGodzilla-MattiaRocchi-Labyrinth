package com.ccdr.labyrinth.result;

import java.util.ArrayList;
import java.util.List;

import com.ccdr.labyrinth.engine.Executor;
import com.ccdr.labyrinth.game.player.Player;

/**
 * Class responsible for managing the result screen that appears after a game has ended.
 */
public final class ResultController implements Executor, ResultInputs {

    private final List<ResultView> views = new ArrayList<>();
    private List<Player> players;
    private Runnable closeAction = () -> { };

    /**
     * @param players list of players, received from GameController on gameover
     */
    public void init(final List<Player> players) {
        this.players = List.copyOf(players);
    }

    /**
     * @param view ResultView object to bind to this controller
     */
    public void addView(final ResultView view) {
        this.views.add(view);
    }

    @Override
    public void onEnable() {
        for (final ResultView resultView : views) {
            resultView.onEnable();
        }
    }

    @Override
    public void update(final double deltaTime) {
        for (final ResultView resultView : views) {
            resultView.draw(this.players);
        }
    }

    @Override
    public void close() {
        this.closeAction.run();
    }

    /**
     * @param action runnable callback to run when the player wants to close the result screen
     */
    public void onClose(final Runnable action) {
        this.closeAction = action;
    }
}
