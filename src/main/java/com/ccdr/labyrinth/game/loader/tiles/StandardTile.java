package com.ccdr.labyrinth.game.loader.tiles;

import com.ccdr.labyrinth.game.player.Player;

public class StandardTile extends GenericTile {
   
    public StandardTile() {
        super.setType(TileType.NORMAL);
    }

    /*TODO: OPTIONAL STANDARDTILE BONUS MATERIALS*/
    @Override
    public void onEnter(Player player) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'onEnter'");
    }

    @Override
    public void onExit(Player player) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'onExit'");
    }
}
