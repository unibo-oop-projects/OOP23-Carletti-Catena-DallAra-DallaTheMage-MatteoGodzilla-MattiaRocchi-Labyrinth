package com.ccdr.labyrinth.game.loader.tiles;
import com.ccdr.labyrinth.game.player.Player;
/**
 * Class for the implementation of the guildtile.
 */
public class GuildTile extends GenericTile {

    private boolean state;
    private final int maxPoints;
    /**
     * 
     * @param nPlayer
     * @param maxPoints
     */
    public GuildTile(final int maxPoints) {
        this.maxPoints = maxPoints;
        this.discover();
    }

    /**
     * 
     */
    @Override
    public void onEnter(final Player player) {
        if (!state) {
            player.increasePoints(maxPoints / 2);
        }

        this.state = true;
    }

    @Override
    public void onExit(final Player player) {
    }
}
