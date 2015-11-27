package fpt.com.core.controller;

/**
 * ModelableController
 * <p/>
 * Implement this interface to automatically
 * instantiate the desired model for the controller
 *
 * @author senycorp
 */
public interface ModelableController {
    public Class getRequiredModel();
}
