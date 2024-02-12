package com.ccdr.labyrinth.menu;

/**
 * This interface defines what are the required inputs in order to use the menu.
 */
public interface MenuInputs {

    /**
     * Up event received from the user, to then dispatch where necessary.
     */
    void moveUp();

    /**
     * Down event received from the user, to then dispatch where necessary.
     */
    void moveDown();

    /**
     * Select event received from the user, to then dispatch where necessary.
     */
    void select();

    /**
     * Back event received from the user, to then dispatch where necessary.
     */
    void back();

}