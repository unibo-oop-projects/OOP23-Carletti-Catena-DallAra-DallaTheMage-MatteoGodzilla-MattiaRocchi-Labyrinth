package com.ccdr.labyrinth.game;

import com.ccdr.labyrinth.game.loader.tiles.SourceTile;
import com.ccdr.labyrinth.game.loader.tiles.Tile;

/**
 * This context has the only purpose of updating the source tiles in the board.
 * This update action is written as a context in order to set when it becomes active through the player's turn
 */
public final class UpdateBoardContext implements Context {

    private final Board board;
    private final Context following;

    /**
     * @param board Board object to get the source tiles from
     * @param following reference to context object that should activate after this one
     */
    public UpdateBoardContext(final Board board, final Context following) {
        this.board = board;
        //this might require a separate bind function
        this.following = following;
    }

    @Override
    public void up() { }

    @Override
    public void down() { }

    @Override
    public void left() { }

    @Override
    public void right() { }

    @Override
    public void primary() {
        for (final Tile tile : this.board.getMap().values()) {
            if (tile instanceof SourceTile) {
                ((SourceTile) tile).updateTile();

            }
        }
    }

    @Override
    public void secondary() { }

    @Override
    public void back() { }

    @Override
    public boolean done() {
        return true;
    }

    @Override
    public Context getNextContext() {
        return this.following;
    }

}
