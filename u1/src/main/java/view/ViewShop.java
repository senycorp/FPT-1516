package view;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import fpt.com.Product;

public class ViewShop extends VBox {

	public Button addButton = new Button("Add");
	public Button deleteButton = new Button("Delete");

	public TextField nameInput = new TextField();
	private TextField priceInput = new TextField();
	private TextField quantityInput = new TextField();

	private Label nameLabel = new Label("Name:");
	private Label priceLabel = new Label("Price:");
	private Label quantityLabel = new Label("Quantity:");

	public TableView<Product> productsTableView = new TableView<Product>();


	TextFormatter<Product> priceFormatter;
	TextFormatter<Product> quantityFormatter;

	@SuppressWarnings("unchecked")
	public ViewShop() {
		VBox nameBox = new VBox(nameLabel, nameInput);
		VBox priceBox = new VBox(priceLabel, priceInput);
		VBox quantityBox = new VBox(quantityLabel, quantityInput);
		HBox buttonBox = new HBox(addButton, deleteButton);

		this.addButton.setId("addButton");
		this.deleteButton.setId("deleteButton");

		VBox rightBox = new VBox(nameBox, priceBox, quantityBox, buttonBox);

		//TableView Zuordnungen
		TableColumn<Product, String> nameCol = new TableColumn<Product, String>("Name");
		nameCol.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));

		TableColumn<Product, Double> priceCol = new TableColumn<Product, Double>("Price");
		priceCol.setCellValueFactory(new PropertyValueFactory<Product, Double>("price"));

		TableColumn<Product, Integer> quantityCol = new TableColumn<Product, Integer>("Quantity");
		quantityCol.setCellValueFactory(new PropertyValueFactory<Product, Integer>("quantity"));

		productsTableView.getColumns().addAll(nameCol, priceCol, quantityCol);

		//Fuelle die SplitPane mit der Table und HBox
		SplitPane contentPane = new SplitPane();
		contentPane.getItems().addAll(productsTableView, rightBox);

		//Fuelle die VBox mit allen Elementen
		this.getChildren().addAll( contentPane);

		//Bei veränderung der Größe wird nichts "kaputt" gemacht
		productsTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		priceInput.setTextFormatter(priceFormatter);
		quantityInput.setTextFormatter(quantityFormatter);

		deleteButton.disableProperty().bind(
				Bindings.equal(-1, productsTableView.getSelectionModel()
						.selectedIndexProperty()));

		BooleanBinding booleanBinding = nameInput
				.textProperty()
				.isEqualTo("")
				.or(priceInput.textProperty().isEqualTo("")
						.or(quantityInput.textProperty().isEqualTo("")));

		addButton.disableProperty().bind(booleanBinding);
	}

	public void addEventHandler(EventHandler<ActionEvent> eventHandler) {
		addButton.addEventHandler(ActionEvent.ACTION, eventHandler);
		deleteButton.addEventHandler(ActionEvent.ACTION, eventHandler);
	}

	public TableView<Product> getTable() {
		return productsTableView;
	}

	public String getNameInputPaneText() {
		if (nameInput.getText().length() != 0) {
			return nameInput.getText();
		} else {
			return null;
		}
	}

	public Double getPriceInputPaneText() {
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

	public Integer getQuantityInputPaneText() {
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
