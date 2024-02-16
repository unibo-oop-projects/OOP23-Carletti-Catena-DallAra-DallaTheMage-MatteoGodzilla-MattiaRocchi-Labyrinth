package com.ccdr.labyrinth.game;

import com.ccdr.labyrinth.jfx.JFXInputSource;

import javafx.scene.input.KeyEvent;

/**
 * Adapter responsible for routing events to the game controller.
 */
public final class GameInputAdapter implements JFXInputSource.Receiver {
    private final GameInputs controller;

    /**
     * @param controller reference to the game controller to invoke methods
     */
    public GameInputAdapter(final GameInputs controller) {
        this.controller = controller;
    }

    @Override
    public void onKeyPressed(final KeyEvent event) {
        switch (event.getCode()) {
            case W:
            case UP:
                this.controller.up();
                break;
            case D:
            case RIGHT:
                this.controller.right();
                break;
            case A:
            case LEFT:
                this.controller.left();
                break;
            case S:
            case DOWN:
                this.controller.down();
                break;
            case ENTER:
            case SPACE:
                this.controller.primary();
                break;
            case TAB:
            case CONTROL:
                this.controller.secondary();
                break;
            case ESCAPE:
            case BACK_SPACE:
                this.controller.back();
                break;
            case F8:
                this.controller.forceGameOver();
                break;
            default:
                break;
        }
    }
}
