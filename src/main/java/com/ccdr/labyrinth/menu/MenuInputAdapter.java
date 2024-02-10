package com.ccdr.labyrinth.menu;

import com.ccdr.labyrinth.jfx.JFXInputSource;

import javafx.scene.input.KeyEvent;

/**
 * Adapter responsible for receiving input events from the user when in the main menu.
 */
public final class MenuInputAdapter implements JFXInputSource.Receiver {
    private final MenuController menuController;

    /**
     * @param controller reference to the menu controller to call
     */
    public MenuInputAdapter(final MenuController controller) {
        this.menuController = controller;
    }

    @Override
    public void onKeyPressed(final KeyEvent event) {
        switch (event.getCode()) {
            case UP:
                menuController.moveUp();
                break;
            case DOWN:
                menuController.moveDown();
                break;
            case ENTER:
                menuController.select();
                break;
            case ESCAPE:
            case BACK_SPACE:
                menuController.back();
                break;
            default:
                break;
        }
    }

    @Override
    public void onKeyReleased(final KeyEvent event) { }
}
