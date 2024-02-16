package com.ccdr.labyrinth.game;

/**
 * This interface is used to define a Context, which is a class that controls a phase in the gameplay.
 * These functions are called from keyboard inputs.
 */
public interface Context {
    /**
     * method to execute when the W or up arrow key is pressed.
     */
    void up();

    /**
     * method to execute when the S or down arrow key is pressed.
     */
    void down();

    /**
     * method to execute when the A or left arrow key is pressed.
     */
    void left();

    /**
     * method to execute when the D or right arrow key is pressed.
     */
    void right();

    /**
     * method to execute when the ENTER or SPACE key is pressed.
     */
    void primary();

    /**
     * method to execute when the TAB or CTRL key is pressed.
     */
    void secondary();

    /**
     * method to execute when the ESC or BACKSPACE key is pressed.
     */
    void back();

    /**
     * @return true if this context has finished execution and the next context should become active
     */
    boolean done();

    /**
     * @return the next context instance that should become active
     */
    Context getNextContext();
}
