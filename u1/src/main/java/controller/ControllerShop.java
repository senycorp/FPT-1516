package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import model.ModelShop;
import model.Product;
import view.ViewShop;

/**
 * ControllerShop
 */
public class ControllerShop {

    /**
     * Model
     */
    private ModelShop modelShop;

    /**
     * View
     */
    private ViewShop viewShop;


    /**
     * Link model and view to controller
     *
     * @param model
     * @param view
     */
    public void link(ModelShop model, ViewShop view) {
        this.modelShop = model;
        this.viewShop = view;

        // Set items for the ProductsTableView
        this.viewShop.getTable().setCache(false);
        this.viewShop.getTable().setItems(this.modelShop.getProducts());

        // Set up eventhandler for add and delete Button
        this.viewShop.addEventHandler(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                // Switch between add and delete button
                if (((Button) event.getSource()).getId().equals("addButton")) {
                    // Add new Product
                    addProduct();
                } else {
                    // Delete selected product form model
                    deleteButton();
                }
            }
        });
    }

    /**
     * DeleteButton Handler
     */
    private void deleteButton() {
        // Delete product and update list
        modelShop.doRemove(viewShop.getTable().getSelectionModel().getSelectedIndex());
    }

    /**
     * AddButton Handler
     */
    private void addProduct() {
        // Get price and quantity fields from view
        Double  price    = viewShop.getPrice();
        Integer quantity = viewShop.getQuantity();

        // Check for valid values
        if (price == null || quantity == null) {
            System.out.println("Please make sure you typed numeric values for quantity and price!");
        } else {
            // Create product
            Product p = new Product();
            p.setId(System.currentTimeMillis() % 1000);
            p.setName(viewShop.getName());
            p.setPrice(price);
            p.setQuantity(quantity);

            // Add it to the model and update the tableView
            modelShop.add(p);
        }
    }
}
