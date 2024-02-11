package com.ccdr.labyrinth.game;

public interface Context  {
    void up();

    void down();

    void left();

    void right();

    void primary();

    void secondary();

    void tertiary();

    boolean done();

    Context getNextContext();
}