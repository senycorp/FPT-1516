package view;

import fpt.com.Order;
import fpt.com.Product;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * View: Customer
 */
public class ViewCustomer
        extends VBox {

    /**
     * TableView: Products
     */
    private ProductsTableView<Product> productsTableView = new ProductsTableView<Product>();

    /**
     * TableView: Orders
     */
    private OrdersTableView<Order> ordersTableView = new OrdersTableView<Order>();

    /**
     * Button: Buy
     */
    private Button buyButton = new Button("Buy");

    /**
     * Input: Quantity
     */
    private TextField quantityInput = new TextField();

    /**
     * Constructor
     */
    public ViewCustomer() {
        // Create left part: Products
        Label productsLabel = new Label("Products");
        HBox  buyGroup      = new HBox(buyButton, quantityInput);
        VBox  productsBox   = new VBox(productsLabel, productsTableView, buyGroup);
        this.quantityInput.setPromptText("Quantity...");

        // Create right part: Orders
        Label ordersLAbel = new Label("Orders");
        VBox  ordersBox   = new VBox(ordersLAbel, ordersTableView);

        // Setting up splitpane with product and orders section
        SplitPane contentPane = new SplitPane(productsBox, ordersBox);
        contentPane.prefHeightProperty().bind(this.heightProperty());
        this.setMinHeight(300);

        // Add Splitpane to View
        this.getChildren().add(contentPane);

        // Activate add button only when all required input fields are filled
        BooleanBinding booleanBinding = quantityInput
                .textProperty()
                .isEqualTo("");
        buyButton.disableProperty().bind(booleanBinding);
    }

    /**
     * Add event handler for buttons
     *
     * @param eventHandler
     */
    public void addEventHandler(EventHandler<ActionEvent> eventHandler) {
        buyButton.addEventHandler(ActionEvent.ACTION, eventHandler);
    }

    public Button getBuyButton() {
        return this.buyButton;
    }

    /**
     * Get TableView: Products
     *
     * @return
     */
    public TableView<Product> getProductsTable() {
        return productsTableView;
    }

    /**
     * Get TableView: Orders
     *
     * @return
     */
    public TableView<Order> getOrdersTable() {
        return ordersTableView;
    }

    /**
     * Get selected row index of table
     *
     * @return
     */
    public Integer getSelectedProductIndex() {
        return productsTableView.getSelectionModel().getSelectedIndex();
    }

    /**
     * Get content of quantity input
     *
     * @return
     */
    public Integer getQuantity() {
        try {
            int number = Integer.parseInt(quantityInput.getText());
            if (number > 0) {
                return Integer.parseInt(quantityInput.getText());
            } else {
                return null;
            }
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
