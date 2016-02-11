package fpt.com.core.controller;

/**
 * ViewableController
 * <p/>
 * Implement this interface to automatically
 * instantiate the desired model for the controller
 *
 * @author senycorp
 */
public interface ViewableController {
    public Class getRequiredView();
}
