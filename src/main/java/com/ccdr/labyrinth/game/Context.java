package com.ccdr.labyrinth.game;

/**
 * This interface is used to define a Context, which is a class that can receive Game Inputs calls from the outside.
 * The methods inside this interface are just the ones to manage Contexts and switching between them.
 * {@see GameInputs} for the actual list of functions that should be overridden for gameplay purposes
 */
public interface Context extends GameInputs{
    boolean done();

    Context getNextContext();
}