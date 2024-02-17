package com.ccdr.labyrinth.menu;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

import com.ccdr.labyrinth.engine.Executor;
import com.ccdr.labyrinth.game.GameConfig;
import com.ccdr.labyrinth.menu.element.MenuButtonElement;
import com.ccdr.labyrinth.menu.element.MenuChoiceElement;
import com.ccdr.labyrinth.menu.element.MenuElement;
import com.ccdr.labyrinth.menu.element.MenuListElement;
import com.ccdr.labyrinth.menu.element.MenuTextElement;

/**
 * Main class responsible for controlling the menu.
 * This class doesn't have any direct reference to the game controller
 */
public final class MenuController implements Executor, MenuInputs {
    private GameConfig config;
    private final Set<MenuView> views = new HashSet<>();
    private MenuElement current;
    // "events" fired when the menu has completed its task. Can be expanded so
    // multiple callbacks are activated.
    private Consumer<GameConfig> onPlay = config -> { };
    private Runnable onExit = () -> { };


    @Override
    public void onEnable() {
        this.config = new GameConfig();
        current = createMenuStructure();
        for (final MenuView view : views) {
            view.onEnable();
        }
    }

    @Override
    public void update(final double deltaTime) {
        for (final MenuView view : views) {
            view.draw(current);
        }
    }

    /**
     * Adds a view so that it gets updates from this controller.
     * @param view view object
     */
    public void addView(final MenuView view) {
        this.views.add(view);
    }

    // events
    /**
     * @param callback function to run when selecting 'play' in the menu
     */
    public void onPlay(final Consumer<GameConfig> callback) {
        this.onPlay = callback;
    }

    /**
     * @param callback function to run when selecting 'exit' in the menu
     */
    public void onExit(final Runnable callback) {
        this.onExit = callback;
    }

    //Functions called externally, in order to actually the menu
    /**
     * Up event received from the user, to then dispatch where necessary.
     */
    @Override
    public void moveUp() {
        current.up();
        sendChangeToViews();
    }

    /**
     * Down event received from the user, to then dispatch where necessary.
     */
    @Override
    public void moveDown() {
        current.down();
        sendChangeToViews();
    }

    /**
     * Select event received from the user, to then dispatch where necessary.
     */
    @Override
    public void select() {
        current = current.nextState();
        current.immediate();
        sendChangeToViews();
    }

    /**
     * Back event received from the user, to then dispatch where necessary.
     */
    @Override
    public void back() {
        if (current.getParent() != null) {
            current = current.getParent();
        }
        sendChangeToViews();
    }

    // functions related to menu movement
    private void sendChangeToViews() {
        for (final MenuView view : views) {
            view.changed(this.current);
        }
    }

    private MenuElement createMenuStructure() {
        return new MenuListElement("",
            new MenuButtonElement("Play", () -> onPlay.accept(config)),
            new MenuListElement("Configuration",
                new MenuChoiceElement<>("Players", GameConfig.PLAYER_COUNT_OPTIONS)
                    .defaultIndex(0)
                    .action(playerCount -> this.config.setPlayerCount(playerCount)),
                new MenuChoiceElement<>("Labyrinth Size", GameConfig.LABYRINTH_SIZE_OPTIONS)
                    .defaultIndex(1)
                    .action(size -> {
                        this.config.setLabyrinthWidth(size);
                        this.config.setLabyrinthHeight(size);
                    }),
                new MenuChoiceElement<>("Source Tiles", GameConfig.SOURCE_OPTIONS)
                    .defaultIndex(1)
                    .action(count -> this.config.setSourceTiles(count))
            ),
            new MenuTextElement("How to play", new StringBuilder()
                .append("Every turn is structured as following:\n\n")
                .append(" - PHASE 1: MODIFY THE BOARD - \n")
                .append("In a single turn the player can choose to either shift rows/columns or rotate tiles around it.\n")
                .append("Press Tab to change between the two possible modes\n")
                .append("- 1.1: Shifting mode - \n")
                .append("A/D or Left/Right arrow selects a column to shift. W/S or Up/Down select a row to shift.\n")
                .append("If a column is selected, press Enter/Space to move it down or Escape/Backspace to move it up\n")
                .append("If a row is selected, press Enter/Space to move it right or Escape/Backspace to move it left\n")
                .append("- 1.2: Rotating mode - \n")
                .append("W/A/S/D/ or Up/Left/Down/Right to select any neighboring tile around the player.\n")
                .append("Once selected, press Enter/Space to rotate clockwise or Escape/Backscape to rotate counterclockwise\n\n")
                .append(" - PHASE 2: PLAYER MOVEMENT - \n")
                .append("Press Enter/Space to roll the dice, then W/A/S/D or Up/Down/Left/Right arrow to move.\n")
                .append("Press Tab/Control to discard any extra moves and skip to the next phase.\n\n")
                .append(" - PHASE 3: GUILD INTERACTION - \n")
                .append("This phase is active only if the player is over the guild tile.\n")
                .append("Up/Left to move the cursor up, Down/Right to move the cursor down.\n")
                .append("Enter/Space to redeem a mission, Escape/Backspace to close the menu and end the turn.\n")
                .toString()
            ),
            new MenuTextElement("Credits", new StringBuilder()
                .append('\n')
                .append("Made by Team CCDR:\n")
                .append("Lorenzo Carletti\n")
                .append("Matteo Catena\n")
                .append("Lorenzo Dall'Ara\n")
                .append("Mattia Rocchi\n")
                .append("Art by Matteo Catena & Mattia Rocchi\n")
                .append("__  __\n")
                .append("/   \\/   \\\n")
                .append("\\       /\n")
                .append("\\   /\n")
                .append("\\/\n")
                .toString()
            ),
            new MenuTextElement("Exit", "Are you sure you want to close the game?")
                .onAction(() -> onExit.run()));
    }
}
