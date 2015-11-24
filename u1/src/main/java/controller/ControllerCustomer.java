package controller;


import component.view.Alert;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.ModelShop;
import model.Product;
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

        // Set Eventhandler
        this.view.addEventHandler(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (event.getSource() == view.getBuyButton()) {
                    buyButtonHandler();
                }
            }
        });
    }

    /**
     * EventHandler: BuyButton
     */
    private void buyButtonHandler() {
        // Get quantity field from view
        Integer quantity = view.getQuantity();

        // Check for valid values
        if (quantity == null) {
            Alert.warning("Invalid Information",
                          null,
                          "Please make sure that quantity is a numeric value. ").showAndWait();
        } else {
            // Create product
            Product p = new Product();
            Product s = (Product) model.get(this.view.getSelectedProductIndex());
            /**
             * TODO: ID property is setted to millis.
             */
            p.setId(s.getId());
            p.setName(s.getName());
            p.setPrice(s.getPrice());
            p.setQuantity(quantity);

            // Add it to the model - FX will update the list automatically
            //modelShop.add(p);
        }
    }
}