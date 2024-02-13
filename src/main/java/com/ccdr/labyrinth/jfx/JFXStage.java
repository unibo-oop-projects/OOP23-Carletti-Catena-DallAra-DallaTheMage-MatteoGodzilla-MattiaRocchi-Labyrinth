package com.ccdr.labyrinth.jfx;

import java.util.Set;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import java.util.HashSet;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * This class is responsible for launching the JavaFX library, owning the window that gets created and pass events around.
 * Since this class gets instantiated in the JavaFX Application thread, there is no way to get a reference
 * on the engine thread. To fix this we could either make the entire class a singleton or mark as static
 * only the things necessary.
 * The second option was chosen in this case.
 */
public final class JFXStage extends Application {
    /**
     * Initial window width.
     */
    public static final int WINDOW_WIDTH = 800;
    /**
     * Initial window height.
     */
    public static final int WINDOW_HEIGHT = 600;

    private static Stage stage;
    private static Set<Runnable> onCloseCallbacks = new HashSet<>();

    @Override
    @SuppressFBWarnings("ST_WRITE_TO_STATIC_FROM_INSTANCE_METHOD")
    public void start(final Stage primaryStage) throws Exception {
        stage = primaryStage;
        stage.setTitle("Labyrinth");
        stage.setMinWidth(WINDOW_WIDTH);
        stage.setMinHeight(WINDOW_HEIGHT);
        stage.setWidth(WINDOW_WIDTH);
        stage.setHeight(WINDOW_HEIGHT);
        stage.show();
    }

    //stage.onCloseRequestProperty() only works if the user clicks on the X
    //while this stop function always works
    @Override
    public void stop() {
        for (final Runnable runnable : onCloseCallbacks) {
            runnable.run();
        }
    }

    /**
     * @return reference to the stage that is created by JavaFX
     */
    @SuppressFBWarnings("MS_EXPOSE_REP")
    public static Stage getStage() {
        return stage;
    }

    /**
     * @param runnable callback to something to execute when the framework closes.
     */
    public static void addOnCloseListener(final Runnable runnable) {
        onCloseCallbacks.add(runnable);
    }
}
