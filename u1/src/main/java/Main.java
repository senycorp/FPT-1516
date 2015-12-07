import fpt.com.controller.ControllerCustomer;
import fpt.com.controller.ControllerShop;
import fpt.com.core.ControllerManager;
import fpt.com.core.controller.BaseController;
import javafx.application.Application;

/**
 * MainClass
 */
public class Main
        extends fpt.com.core.Application {

    /**
     * Main
     *
     * @param args
     */
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public ControllerManager initializeController() {
        ControllerManager controllerManager = ControllerManager.getInstance();

        // Set up controller
        BaseController c = new ControllerShop();
        controllerManager.put(c.getID(), c);
        c = new ControllerCustomer();
        controllerManager.put(c.getID(), c);

        return controllerManager;
    }
}
