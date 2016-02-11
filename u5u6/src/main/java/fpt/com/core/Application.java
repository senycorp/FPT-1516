package fpt.com.core;

import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Application
 *
 * @author senycorp
 */
abstract public class Application
        extends javafx.application.Application {

    /**
     * Constructor
     */
    public Application() {
        /**
         * Nothing to implement
         */
    }

    /**
     * Get all controller
     *
     * @return
     */
    public abstract ControllerManager initializeController();

    @Override
    public void start(Stage primaryStage) throws Exception {
        ControllerManager controllerManager = this.initializeController();

        if (controllerManager.size() > 0) {
            for (String c : controllerManager.keySet()) {
                controllerManager.get(c).postInitialization();

                // Create GUI: Scene, Stage for Customer
                Stage stage = new Stage();
                Scene scene = new Scene(controllerManager.get(c).getView());
                controllerManager.get(c).setStage(stage).setScene(scene);
                stage.setScene(scene);
                stage.setX(0);
                stage.show();
            }
        }
    }
}
