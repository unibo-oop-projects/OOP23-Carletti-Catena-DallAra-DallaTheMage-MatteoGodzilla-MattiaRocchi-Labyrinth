package com.ccdr.labyrinth.menu;

import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.function.Consumer;

import com.ccdr.labyrinth.engine.Engine;
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
public final class MenuController implements Executor {
    /**
     * Name of the root of the menu, used to tell if the player is at the root of the menu.
     */
    public static final String ROOT_NAME = "Labyrinth";

    private final GameConfig config = new GameConfig();
    private final Set<MenuView> views = new HashSet<>();
    private MenuElement current = createMenuStructure();
    // "events" fired when the menu has completed its task. Can be expanded so
    // multiple callbacks are activated.
    private Consumer<GameConfig> onPlay;
    private Runnable onExit;

    @Override
    public void onEnable(final Engine engine) {
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

    @Override
    public void onDisable() {
        for (final MenuView menuView : views) {
            menuView.onDisable();
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
    public void moveUp() {
        current.up();
        sendChangeToViews();
    }

    /**
     * Down event received from the user, to then dispatch where necessary.
     */
    public void moveDown() {
        current.down();
        sendChangeToViews();
    }

    /**
     * Select event received from the user, to then dispatch where necessary.
     */
    public void select() {
        current = current.nextState();
        current.immediate();
        sendChangeToViews();
    }

    /**
     * Back event received from the user, to then dispatch where necessary.
     */
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
        return new MenuListElement(ROOT_NAME,
            new MenuButtonElement("Play", () -> onPlay.accept(config)),
            new MenuListElement("Configuration",
                new MenuChoiceElement<>("Players", List.of(1, 2, 3, 4))
                    .action(this::setPlayers),
                new MenuChoiceElement<>("Width", List.of(1, 2, 3))
                    .action(this::handleWidth)
                ),
            new MenuTextElement("How to play", ""),
            new MenuTextElement("Credits", ""),
            new MenuTextElement("Exit", "Are you sure you want to close the game?")
                .onAction(() -> onExit.run()));
    }

    //Handlers for the single menu elements
    private void handleWidth(final Integer size) {
        this.config.setLabyrinthHeight(size);
        this.config.setLabyrinthHeight(size);
    }

    private void setPlayers(final Integer players) {
        this.config.setPlayerCount(players);
    }
}
