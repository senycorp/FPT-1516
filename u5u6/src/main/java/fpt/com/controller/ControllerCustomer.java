package fpt.com.controller;

import fpt.com.component.view.Alert;
import fpt.com.core.ControllerManager;
import fpt.com.core.controller.BaseController;
import fpt.com.core.controller.ViewableController;
import fpt.com.model.ModelCustomer;
import fpt.com.model.ModelShop;
import fpt.com.model.Order;
import fpt.com.model.Product;
import fpt.com.view.LoginDialog;
import fpt.com.view.ViewCustomer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.util.Optional;

/**
 * ControllerCustommer
 */
public class ControllerCustomer
        extends BaseController
        implements ViewableController {

    /**
     * Model
     */
    protected ModelShop model;

    /**
     * Model: Orders
     */
    protected ModelCustomer modelCustomer;

    /**
     * View
     */
    protected ViewCustomer view;

    /**
     *
     */
    protected LoginDialog loginDialog;

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

            Order myOrder = new Order();
            myOrder.add(p);

            Optional<String> loginResult = this.loginDialog.showAndWait();
            if (loginResult.isPresent()) {
                String loginString = loginResult.get();
                this.modelCustomer.sendOrder(loginString, myOrder);
            }
        }
    }

    @Override
    protected void initializeController() {
        // Cast view and model
        this.view = (ViewCustomer) this.getView();
        this.model = (ModelShop) this.getModel();
        this.modelCustomer = new ModelCustomer();

        // Initialize dialog
        this.loginDialog = new LoginDialog();

        // Set Eventhandler
        view.addEventHandler(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (event.getSource() == view.getBuyButton()) {
                    buyButtonHandler();
                }
            }
        });
    }

    @Override
    public void postInitialization() {
        // Get modelShop from ControllerShop to access the same model
        this.setModel(ControllerManager.getInstance().getControllerById("shop").getModel());

        // Do the cast
        this.model = (ModelShop) this.getModel();

        // Set items to view list
        this.view.getProductsTable().setItems(this.model.getProducts());

        // Set items to view list
        this.view.getOrdersTable().setItems(this.modelCustomer);
    }

    @Override
    public String getID() {
        return "customer";
    }

    @Override
    public Class getRequiredView() {
        return ViewCustomer.class;
    }

}