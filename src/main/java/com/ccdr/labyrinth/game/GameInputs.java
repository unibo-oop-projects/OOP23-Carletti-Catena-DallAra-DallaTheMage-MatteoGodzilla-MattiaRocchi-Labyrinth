package com.ccdr.labyrinth.game;

/**
 * Interface responsible to describe what are the input types that the game controller can understand.
 */
public interface GameInputs {

    /**
     * method that calls the activeContext method to execute when the W or up arrow key is pressed.
     */
    void up();

    /**
     * method that calls the activeContext method to execute when the S or down arrow key is pressed.
     */
    void down();

    /**
     * method that calls the activeContext method to execute when the A or left arrow key is pressed.
     */
    void left();

    /**
     * method that calls the activeContext method to execute when the D or right arrow key is pressed.
     */
    void right();

    /**
     * method that calls the activeContext method to execute when the ENTER or SPACE key is pressed.
     */
    void primary();

    /**
     * method that calls the activeContext method to execute when the TAB or CTRL key is pressed.
     */
    void secondary();

    /**
     * method that calls the activeContext method to execute when the ESC or BACKSPACE key is pressed.
     */
    void back();

    /**
     * method used to end the game prematurely 
     */
    void forceGameOver();
}
