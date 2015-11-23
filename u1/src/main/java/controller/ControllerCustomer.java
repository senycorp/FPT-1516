package controller;


import model.ModelShop;
import view.ViewCustomer;

/**
 * ControllerCustommer
 */
public class ControllerCustomer {

    /**
     * Model
     */
    private ModelShop model;

    /**
     * View
     */
    private ViewCustomer view;

    /**
     * Link model and view to controller
     *
     * @param modelShop
     * @param viewCustomer
     */
    public void link(ModelShop modelShop, ViewCustomer viewCustomer) {
        this.model = modelShop;
        this.view = viewCustomer;

        // Set items to view list
        viewCustomer.getProductsTable().setItems(modelShop.getProducts());
    }
}