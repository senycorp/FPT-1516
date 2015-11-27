package fpt.com.core.controller;

import fpt.com.core.model.BaseModel;
import fpt.com.core.view.BaseView;

/**
 * BaseController
 *
 * @author senycorp
 */
abstract public class BaseController {

    /**
     * Model
     */
    protected BaseModel model;

    /**
     * View
     */
    protected BaseView view;

    /**
     * Constructor
     */
    public BaseController() {
        // Set model
        if (this instanceof ModelableController) {
            Object modelObject = null;
            try {
                modelObject = ((ModelableController) this).getRequiredModel().newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            if (modelObject instanceof BaseModel) {
                this.setModel((BaseModel) modelObject);
            }
        }

        // Set view
        if (this instanceof ViewableController) {
            Object viewObject = null;
            try {
                viewObject = ((ViewableController) this).getRequiredView().newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            if (viewObject instanceof BaseView) {
                this.setView((BaseView) viewObject);
            }
        }

        // Let us initialize the controller
        this.initializeController();
    }

    /**
     * Initialize Controller
     * <p/>
     * This method should be used for some pre-start-jobs
     * to set all necessary components.
     */
    protected abstract void initializeController();

    /**
     * Get Model
     *
     * @return
     */
    public BaseModel getModel() {
        return this.model;
    }

    /**
     * Set model for controller
     *
     * @param model
     * @return
     */
    public BaseController setModel(BaseModel model) {
        this.model = model;

        return this;
    }

    /**
     * Get view
     *
     * @return
     */
    public BaseView getView() {
        return this.view;
    }

    /**
     * Set view for controller
     *
     * @param view
     * @return
     */
    public BaseController setView(BaseView view) {
        this.view = view;

        return this;
    }

    /**
     * Get ID of controller
     * <p/>
     * This is required by the ControllerManager
     * to return the desired controller
     *
     * @return
     */
    public abstract String getID();

    /**
     * PostInitialization
     * <p/>
     * Overwrite this method for doing fancy stuff
     * after all necessary controllers are instantiated.
     */
    public void postInitialization() {
        // Nothing needed at this moment
    }
}
