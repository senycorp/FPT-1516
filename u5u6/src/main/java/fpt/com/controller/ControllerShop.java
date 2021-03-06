package fpt.com.controller;

import fpt.com.SerializableStrategy;
import fpt.com.component.view.Alert;
import fpt.com.component.view.Tooltip;
import fpt.com.core.controller.BaseController;
import fpt.com.core.controller.ModelableController;
import fpt.com.core.controller.ViewableController;
import fpt.com.model.ModelShop;
import fpt.com.model.Product;
import fpt.com.view.ViewShop;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import java.io.IOException;

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
     * DeleteButton Handler
     */
    private void deleteProduct() {
        // Delete product and update list
        int rowIndex = view.getTable().getSelectionModel().getSelectedIndex();
        String productName = "";
        /**
         * Check for filled index before trying to delete to prevent errors and exceptions
         */
        if (model.getProducts().get(rowIndex) != null) {
            productName = model.getProducts().get(rowIndex).getName();

            model.doRemove(rowIndex);

            if (model.getProducts().size() == 0) {
                Alert.info("Serialization",
                           "Deactivation of serialization",
                           "Please notice that serialization of empty product lists is not allowed. \n" +
                                   "Add some products to enable this feature again.").show();
                view.saveButton.setDisable(true);
            } else {
                view.saveButton.setDisable(false);
            }

            Tooltip.disappearingTooltip("Product \"" + productName + "\" removed successfully!");
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

            try {
                // Set product properties

                // This is not needed anymore by using a database
                p.setName(view.getName());
                p.setPrice(price);
                p.setQuantity(quantity);

                // Add it to the model - FX will update the list automatically
                model.add(p);

                Tooltip.disappearingTooltip("Product \""+p.getName()+"\" added successfully!");
            } catch (Exception ex) {
                // TODO: What should we do in this case?!?!?!
                ex.printStackTrace();
            }

            this.view.saveButton.setDisable(false);
        }
    }

    private void loadProducts() {
        SerializableStrategy strategy = (SerializableStrategy)this.view.comboBox.getValue();

        if (strategy == null) {
            Alert.warning("Strategy not selected!", "Please make sure that you select a strategy first.",
                          "You should choose a strategy first to deserialize your product list.").show();
            return;
        }

        try {
            // Clear the products list
            model.getProducts().clear();
            Product product;
            int i = 0;
            //long lastId = 0;

            while ((product = (Product)strategy.readObject()) != null) {
                model.doAdd(i++, product);
                //lastId = product.getId();
            }

            Tooltip.disappearingTooltip("Products loaded successfully!");

            // Activate save button again
            if (i > 0) {
                view.saveButton.setDisable(false);
            } else {
                view.saveButton.setDisable(true);
            }
        } catch (IOException openError) {
            Alert.error("Error",
                        "Unable to open output stream",
                        "Please make sure that the resource file is readable and try again.").show();
            openError.printStackTrace();
        } finally {
            try {
                strategy.close();
            } catch (IOException closeError) {
                Alert.error("Error",
                            "Unable to close output stream",
                            "Please make sure that the resource file is readable and allready existing on your harddisk.");
                closeError.printStackTrace();
            }
        }
    }

    private void saveProducts() {
        SerializableStrategy strategy = (SerializableStrategy)this.view.comboBox.getValue();

    	if (strategy == null) {
            Alert.warning("Strategy not selected!", "Please make sure that you select a strategy first.",
                          "You should choose a strategy first to serialize your product list.").show();
    		return;
    	}

        // Get products
        ObservableList<Product> pl = this.getModel().getProducts();

        // Serialize each product with the choosen strategy
        for (fpt.com.Product p : pl) {
            try {
                strategy.writeObject(p);
            } catch (IOException e) {
                Alert.warning("Error: Serializing object", "There was an error while serializing a product with the given strategy.",
                              "Please make sure that the there are no rights conflicts " +
                              "and the path is writable."
                              ).show();
                e.printStackTrace();

                return;
            }
        }

        try {
            strategy.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Tooltip.disappearingTooltip("Products saved successfully!");
    }

    @Override
    protected void initializeController() {
        // Cast view and model
        this.view = (ViewShop) this.getView();
        this.model = (ModelShop) this.getModel();

        // Set items for the ProductsTableView
        view.getTable().setCache(false);
        view.getTable().setItems(model.getProducts());

        // Deactivate save-button to prevent serialization of empty product lists
        view.saveButton.setDisable(true);

        // Set up eventhandler for add and delete Button
        view.addEventHandler(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                // Switch between add and delete button
                String buttonID = ((Button) event.getSource()).getId();

                switch(buttonID) {
                    case "addButton":
                        addProduct();
                        break;
                    case "deleteButton":
                        deleteProduct();
                        break;
                    case "loadButton":
                        loadProducts();
                        break;
                    case "saveButton":
                        saveProducts();
                        break;
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
