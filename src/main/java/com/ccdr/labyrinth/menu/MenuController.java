package com.ccdr.labyrinth.menu;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

import com.ccdr.labyrinth.engine.Engine;
import com.ccdr.labyrinth.engine.Executor;
import com.ccdr.labyrinth.game.GameConfig;
import com.ccdr.labyrinth.menu.tree.MenuElement;
import com.ccdr.labyrinth.menu.tree.MenuListElement;
import com.ccdr.labyrinth.menu.tree.MenuTextElement;

public class MenuController implements Executor {
    private GameConfig config = new GameConfig();
    private Set<MenuView> views = new HashSet<>();
    private MenuElement current = createMenuStructure();
    //"events" fired when the menu has completed its task. Can be expanded so multiple callbacks are activated.
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
        for(MenuView view : views){
            view.draw(current);
        }
    }

    @Override
    public void onDisable() {
        for (MenuView menuView : views) {
            menuView.onDisable();
        }
    }

    public void addView(MenuView view){
        this.views.add(view);
    }

    //events
    public void onPlay(Consumer<GameConfig> callback){
        this.onPlay = callback;
    }

    public void onExit(Runnable callback){
        this.onExit = callback;
    }

    //functions related to menu movement

    private MenuElement createMenuStructure() {
        return new MenuListElement("Labyrinth",
            new MenuTextElement("Play", ()-> onPlay.accept(config)),
            new MenuListElement("Configuration",
                new MenuTextElement("Players", null),
                new MenuTextElement("Width", null),
                new MenuTextElement("Height", null)
            ),
            new MenuTextElement("Credits", ()-> System.out.println("Chosen Credits")),
            new MenuTextElement("Exit", ()-> onExit.run())
        );
    }

    public void moveUp() {
        current.up();
    }
    public void moveDown() {
        current.down();
    }
    public void select() {
        current = current.nextState();
    }
    public void back() {
        if(current.getParent() != null){
            current = current.getParent();
        }
    }
}
