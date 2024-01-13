package com.ccdr.labyrinth;

import java.util.Set;
import java.util.HashSet;

import javafx.application.Application;
import javafx.stage.Stage;

/* This class is responsible for launching the JavaFX library, owning the window that gets created
 * and pass necessary events around.
 * Since this class gets instantiated in the JavaFX Application thread, there is no way to get a reference
 * on the engine thread. To fix this we could either make the entire class a singleton or mark as static
 * only the things necessary.
 * The second option was chosen in this case.
 */
public class JFXStage extends Application {
    public static final int WINDOW_WIDTH=300;
    public static final int WINDOW_HEIGHT=300;

    private static Stage stage;
    private static Set<Runnable> onCloseCallbacks = new HashSet<>();

    @Override
    public void start(Stage primaryStage) throws Exception {
        //System.out.println("--------JFX APP START-------");
        stage = primaryStage;
        stage.setTitle("Labyrinth");
        stage.setWidth(WINDOW_WIDTH);
        stage.setHeight(WINDOW_HEIGHT);
        stage.show();
    }

    //stage.onCloseRequestProperty() only works if the user clicks on the X
    //while this stop function always works
	@Override
	public void stop() throws Exception {
        for (Runnable runnable : onCloseCallbacks) {
            runnable.run();
        }
    }

    public static Stage getStage() {
        return stage;
    }

    public static void addOnCloseListener(Runnable runnable){
        onCloseCallbacks.add(runnable);
    }
}
