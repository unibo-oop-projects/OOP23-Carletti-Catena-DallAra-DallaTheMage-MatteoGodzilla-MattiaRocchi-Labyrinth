package com.ccdr.labyrinth.result;

import java.util.List;
import com.ccdr.labyrinth.game.player.Player;

/**
 * Interface that describes how a view for the result screen is structured.
 */
public interface ResultView {
    /**
     * This method gets called first, when the menu must be shown in the first place.
     */
    void onEnable();

    /**
     * Draw the entire Result screen .
     */
    void draw(List<Player> players);
}
