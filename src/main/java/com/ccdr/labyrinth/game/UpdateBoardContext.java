package com.ccdr.labyrinth.game;

import com.ccdr.labyrinth.game.loader.tiles.SourceTile;
import com.ccdr.labyrinth.game.loader.tiles.Tile;

public class UpdateBoardContext implements Context {

    private Board board;
    private Context following;

    public UpdateBoardContext(Board board, Context following){
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
        for (Tile tile : this.board.getMap().values()) {
            if(tile instanceof SourceTile){
                SourceTile source = (SourceTile)tile;
                source.updateTile();
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
