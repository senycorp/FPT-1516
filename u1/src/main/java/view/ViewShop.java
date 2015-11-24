package view;

import fpt.com.Product;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ViewShop
        extends VBox {

    /**
     * Button: Add
     */
    public Button addButton = new Button("Add");

    /**
     * Button: Delete
     */
    public Button deleteButton = new Button("Delete");

    /**
     * Input: Name
     */
    public TextField nameInput = new TextField();

    /**
     * Input: Price
     */
    private TextField priceInput = new TextField();

    /**
     * Input: Quantity
     */
    private TextField quantityInput = new TextField();

    /**
     * TableView: Products
     */
    public TableView<Product> productsTableView = new TableView<Product>();

    /**
     * TextFormatter: Price
     */
    TextFormatter<Product> priceFormatter;

    /**
     * TextFormatter: Quantity
     */
    TextFormatter<Product> quantityFormatter;

    /**
     * Label: Name
     */
    private Label nameLabel = new Label("Name:");

    /**
     * Label: Price
     */
    private Label priceLabel = new Label("Price:");

    /**
     * Label: Quantity
     */
    private Label quantityLabel = new Label("Quantity:");

    @SuppressWarnings("unchecked")
    public ViewShop() {
        // Create VBox parts
        VBox nameBox     = new VBox(nameLabel, nameInput);
        VBox priceBox    = new VBox(priceLabel, priceInput);
        VBox quantityBox = new VBox(quantityLabel, quantityInput);
        HBox buttonBox   = new HBox(addButton, deleteButton);

        // Set id of buttons for identification purpose in handler
        this.addButton.setId("addButton");
        this.deleteButton.setId("deleteButton");

        // Add all parts to right area
        VBox rightBox = new VBox(nameBox, priceBox, quantityBox, buttonBox);

        // Set table column assignment
        TableColumn<Product, String> nameCol = new TableColumn<Product, String>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));

        TableColumn<Product, Double> priceCol = new TableColumn<Product, Double>("Price");
        priceCol.setCellValueFactory(new PropertyValueFactory<Product, Double>("price"));

        TableColumn<Product, Integer> quantityCol = new TableColumn<Product, Integer>("Quantity");
        quantityCol.setCellValueFactory(new PropertyValueFactory<Product, Integer>("quantity"));

        // Add column descriptions to tableView
        productsTableView.getColumns().addAll(nameCol, priceCol, quantityCol);

        // Create Splitpane and add rightbox and tableView
        SplitPane contentPane = new SplitPane();
        contentPane.getItems().addAll(productsTableView, rightBox);

        // Set content of view
        this.getChildren().addAll(contentPane);

        // Prevent GUI defects on resize
        productsTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Set text formatter for name and quantity input
        priceInput.setTextFormatter(priceFormatter);
        quantityInput.setTextFormatter(quantityFormatter);

        // Activate delete button only when table row is selected
        deleteButton.disableProperty().bind(
                Bindings.equal(-1, productsTableView.getSelectionModel()
                                                    .selectedIndexProperty()));

        // Activate add button only when all required input fields are filled
        BooleanBinding booleanBinding = nameInput
                .textProperty()
                .isEqualTo("")
                .or(priceInput.textProperty().isEqualTo("")
                              .or(quantityInput.textProperty().isEqualTo("")));
        addButton.disableProperty().bind(booleanBinding);
    }

    /**
     * Add event handler for buttons
     *
     * @param eventHandler
     */
    public void addEventHandler(EventHandler<ActionEvent> eventHandler) {
        addButton.addEventHandler(ActionEvent.ACTION, eventHandler);
        deleteButton.addEventHandler(ActionEvent.ACTION, eventHandler);
    }

    /**
     * Get tableView: Product
     *
     * @return
     */
    public TableView<Product> getTable() {
        return productsTableView;
    }

    /**
     * Get content of name input
     *
     * @return
     */
    public String getName() {
        if (nameInput.getText().length() != 0) {
            return nameInput.getText();
        } else {
            return null;
        }
    }

    /**
     * Get content of price input
     *
     * @return
     */
    public Double getPrice() {
        try {
            double number = Double.parseDouble(priceInput.getText());
            if (number > 0) {
                return Double.parseDouble(priceInput.getText());
            } else {
                return null;
            }
        } catch (NumberFormatException e) {

            return null;
        }
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

    public String getName(String name) {
        if (nameInput.equals("")) {
            return "You have to input a name.";
        } else {
            return name;
        }
    }

    public String getPrice(Double price) {
        if (priceInput.equals("")) {
            return "You have to input a price.";
        } else {
            return price.toString();
        }
    }

    public String getQuantity(Integer quantity) {
        if (quantityInput.equals("")) {
            return "You have to input a quantity.";
        } else {
            return quantity.toString();
        }
    }

}
