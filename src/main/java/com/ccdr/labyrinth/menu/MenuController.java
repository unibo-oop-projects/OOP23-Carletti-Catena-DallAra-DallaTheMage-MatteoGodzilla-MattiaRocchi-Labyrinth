package com.ccdr.labyrinth.menu;

import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.function.Consumer;

import com.ccdr.labyrinth.engine.Executor;
import com.ccdr.labyrinth.game.GameConfig;
import com.ccdr.labyrinth.menu.tree.MenuButtonElement;
import com.ccdr.labyrinth.menu.tree.MenuChoiceElement;
import com.ccdr.labyrinth.menu.tree.MenuElement;
import com.ccdr.labyrinth.menu.tree.MenuListElement;
import com.ccdr.labyrinth.menu.tree.MenuTextElement;

/**
 * Main class responsible for controlling the menu.
 * This class doesn't have any direct reference to the game controller
 */
public final class MenuController implements Executor, MenuInputs {
    private final GameConfig config = new GameConfig();
    private final Set<MenuView> views = new HashSet<>();
    private MenuElement current;
    // "events" fired when the menu has completed its task. Can be expanded so
    // multiple callbacks are activated.
    private Consumer<GameConfig> onPlay = config -> { };
    private Runnable onExit = () -> { };
    //Possible options for configuring the game
    private static final List<Integer> PLAYER_COUNT = List.of(2, 3, 4);
    private static final List<Integer> LABYRINTH_SIZE = List.of(15, 31, 45);
    private static final List<Integer> SOURCE_OPTIONS = List.of(4, 8, 12, 16);
    private static final List<Integer> OBJECTIVE_COUNT = List.of(4, 8, 12);

    @Override
    public void onEnable() {
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
                new MenuChoiceElement<>("Players", PLAYER_COUNT)
                    .defaultIndex(0)
                    .action(playerCount -> this.config.setPlayerCount(playerCount)),
                new MenuChoiceElement<>("Labyrinth Size", LABYRINTH_SIZE)
                    .defaultIndex(1)
                    .action(size -> {
                        this.config.setLabyrinthWidth(size);
                        this.config.setLabyrinthHeight(size);
                    }),
                new MenuChoiceElement<>("Source Tiles", SOURCE_OPTIONS)
                    .defaultIndex(1)
                    .action(count -> this.config.setSourceTiles(count))
            ),
            new MenuTextElement("How to play", new StringBuilder()
                .append("")
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
                .append(" ♥ ")
                .toString()
            ),
            new MenuTextElement("Exit", "Are you sure you want to close the game?")
                .onAction(() -> onExit.run()));
    }
}
