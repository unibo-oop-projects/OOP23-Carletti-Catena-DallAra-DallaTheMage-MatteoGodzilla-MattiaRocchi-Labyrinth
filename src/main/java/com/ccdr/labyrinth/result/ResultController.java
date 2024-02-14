package com.ccdr.labyrinth.result;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import com.ccdr.labyrinth.Material;
import com.ccdr.labyrinth.engine.Executor;
import com.ccdr.labyrinth.game.player.Player;

/**
 * Class responsible for managing the result screen that appears after a game has ended.
 */
public final class ResultController implements Executor, ResultInputs {

    private final List<ResultView> views = new ArrayList<>();
    private List<Player> players;
    private Map<Player,Integer> playerToIndex;
    private Runnable closeAction = () -> { };

    /**
     * @param players list of players, received from GameController on gameover
     */
    public void init(final List<Player> players) {
        this.players = new ArrayList<>(players);
        this.playerToIndex = new HashMap<>();
        //sort players
        //Since the players don't contain inside what index they are, if we sort them by points we'll lose the original index.
        //So we make a map to keep this old information
        for(int i = 0; i < this.players.size(); i++){
            this.playerToIndex.put(this.players.get(i), i);
        }
        this.players.sort((p1, p2) -> {
            int p1Points = p1.getPoints();
            int p2Points = p2.getPoints();
            if(p1Points == p2Points){
                int p1TotalMaterials = 0;
                int p2TotalMaterials = 0;
                for (Material m : Material.values()) {
                    p1TotalMaterials += p1.getQuantityMaterial(m);
                    p2TotalMaterials += p2.getQuantityMaterial(m);
                }
                return p2TotalMaterials - p1TotalMaterials;
            }
            return p2Points - p1Points;
        });
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
            resultView.draw(this.players, this.playerToIndex);
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
