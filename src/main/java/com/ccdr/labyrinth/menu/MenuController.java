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
import com.ccdr.labyrinth.menu.tree.MenuRootElement;
import com.ccdr.labyrinth.menu.tree.MenuTextElement;

/**
 * Main class responsible for controlling the menu.
 * This class doesn't have any direct reference to the game controller
 */
public class MenuController implements Executor {
    private GameConfig config = new GameConfig();
    private Set<MenuView> views = new HashSet<>();
    private MenuElement current = createMenuStructure();
    // "events" fired when the menu has completed its task. Can be expanded so
    // multiple callbacks are activated.
    private Consumer<GameConfig> onPlay;
    private Runnable onExit;

    @Override
    public void onEnable(Engine engine) {
        for (MenuView view : views) {
            view.onEnable();
        }
    }

    @Override
    public void update(double deltaTime) {
        for (MenuView view : views) {
            view.draw(current);
        }
    }

    @Override
    public void onDisable() {
        for (MenuView menuView : views) {
            menuView.onDisable();
        }
    }

    public void addView(MenuView view) {
        this.views.add(view);
    }

    // events
    public void onPlay(Consumer<GameConfig> callback) {
        this.onPlay = callback;
    }

    public void onExit(Runnable callback) {
        this.onExit = callback;
    }

    // functions related to menu movement

    private MenuElement createMenuStructure() {
        return new MenuRootElement("Labyrinth",
                new MenuButtonElement("Play", () -> onPlay.accept(config)),
                new MenuListElement("Configuration",
                        new MenuChoiceElement<>("Players", 0, List.of(1, 2, 3, 4), this::setPlayers),
                        new MenuChoiceElement<>("Width", 0, List.of(1, 2, 3), this::handleWidth)),
                new MenuTextElement("How to play", ""),
                new MenuTextElement("Credits", ""),
                new MenuTextElement("Exit", "Are you sure you want to close the game?")
                        .setAction(() -> onExit.run()));
    }

    private void handleWidth(Integer width) {
        System.out.println(width);
    }

    private void setPlayers(Integer players) {
        this.config.setPlayerCount(players);
    }

    public void moveUp() {
        current.up();
        sendChangeToViews();
    }

    public void moveDown() {
        current.down();
        sendChangeToViews();
    }

    public void select() {
        current = current.nextState();
        current.immediate();
        sendChangeToViews();
    }

    public void back() {
        if (current.getParent() != null) {
            current = current.getParent();
        }
        sendChangeToViews();
    }

    public void sendChangeToViews() {
        for (MenuView view : views) {
            view.changed(this.current);
        }
    }
}
