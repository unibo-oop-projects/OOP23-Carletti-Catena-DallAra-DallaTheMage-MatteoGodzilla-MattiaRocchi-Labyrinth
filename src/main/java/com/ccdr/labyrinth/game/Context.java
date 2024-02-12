package com.ccdr.labyrinth.game;

public interface Context  {
    void up();

    void down();

    void left();

    void right();

    //SPACE/ENTER
    void primary();

    //TAB/LCTRL
    void secondary();

    //ESCAPE/BACKSPACE
    void back();

    boolean done();

    Context getNextContext();
}