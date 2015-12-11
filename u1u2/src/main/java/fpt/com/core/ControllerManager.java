package fpt.com.core;

import fpt.com.core.controller.BaseController;

import java.util.HashMap;

/**
 * ControllerManager
 * <p/>
 * Global manager for accessing all controllers
 * from everywhere...
 *
 * @author senycorp
 */
public class ControllerManager
        extends HashMap<String, BaseController> {

    private static ControllerManager instance;

    /**
     * Constructor
     * <p/>
     * This is private to prevent multiple instances of this manager
     */
    private ControllerManager() {
        /**
         * Nothing to implement
         */
    }

    /**
     * Get singleton object
     *
     * @return
     */
    public static ControllerManager getInstance() {
        // Check for existing instance
        if (instance == null) {
            instance = new ControllerManager();
        }

        return instance;
    }

    /**
     * Get controller by id
     *
     * @param controllerID
     * @return
     */
    public BaseController getControllerById(String controllerID) {
        return this.get(controllerID);
    }
}
