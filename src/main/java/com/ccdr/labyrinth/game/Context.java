package com.ccdr.labyrinth.game;

import com.ccdr.labyrinth.game.loader.Direction;

/*TODO: CONTEXT INTERFACE*/
public interface Context<T>  {
    void switchAction();
    T move(T target, Direction way);
    T execute(T selected);
}