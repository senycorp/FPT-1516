package fpt.com.controller;

import fpt.com.component.view.Alert;
import fpt.com.component.view.IDGenerator;
import fpt.com.core.controller.BaseController;
import fpt.com.core.controller.ModelableController;
import fpt.com.core.controller.ViewableController;
import fpt.com.model.ModelShop;
import fpt.com.model.Product;
import fpt.com.view.ViewShop;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

/**
 * ControllerShop
 */
public class ControllerShop
        extends BaseController
        implements ModelableController, ViewableController {

    /**
     * Model
     */
    protected ModelShop model;

    /**
     * View
     */
    protected ViewShop view;

    /**
     * IDGenerator
     */
    private IDGenerator idGen;

    /**
     * DeleteButton Handler
     */
    private void deleteButton() {
        // Delete product and update list
        int rowIndex = view.getTable().getSelectionModel().getSelectedIndex();

        /**
         * Check for filled index before trying to delete to prevent errors and exceptions
         */
        if (model.getProducts().get(rowIndex) != null) {
            model.doRemove(rowIndex);
        }
    }

    /**
     * AddButton Handler
     */
    private void addProduct() {
        // Get price and quantity fields from view
        Double  price    = view.getPrice();
        Integer quantity = view.getQuantity();

        // Check for valid values
        if (price == null || quantity == null) {
            Alert.warning("Invalid Information",
                          null,
                          "Please make sure that price and quantity are numeric values. " +
                                  "It is necessary to use (dot) as floating point instead of commata.").showAndWait();
        } else {
            // Create product
            Product p = new Product();

            // add ID to the Product
            try {
                p.setId(idGen.getId());
            } catch (Exception ex) {
                // TODO: What should we do in this case?!?!?!
                ex.printStackTrace();
            }

            // Set product properties
            p.setName(view.getName());
            p.setPrice(price);
            p.setQuantity(quantity);

            // Add it to the model - FX will update the list automatically
            model.add(p);
        }
    }

    @Override
    protected void initializeController() {
        // Cast view and model
        this.view = (ViewShop) this.getView();
        this.model = (ModelShop) this.getModel();

        // Create ID-Generator
        this.idGen = IDGenerator.getInstance();

        // Set items for the ProductsTableView
        view.getTable().setCache(false);
        view.getTable().setItems(model.getProducts());

        // Set up eventhandler for add and delete Button
        view.addEventHandler(new EventHandler<ActionEvent>() {

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

    @Override
    public String getID() {
        return "shop";
    }

    @Override
    public Class getRequiredModel() {
        return ModelShop.class;
    }

    @Override
    public Class getRequiredView() {
        return ViewShop.class;
    }
}
