package view;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * TableView: Orders
 *
 * @param <Order>
 */
public class OrdersTableView<Order> extends TableView<Order> {

    /**
     * Constructor
     */
    public OrdersTableView() {
        // Set observable properties here
        TableColumn<Order, String> nameCol = new TableColumn<Order, String>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<Order, String>("name"));
        TableColumn<Order, String> priceCol = new TableColumn<Order, String>("Price");
        priceCol.setCellValueFactory(new PropertyValueFactory<Order, String>("price"));
        TableColumn<Order, String> quantityCol = new TableColumn<Order, String>("Buy Count");
        quantityCol.setCellValueFactory(new PropertyValueFactory<Order, String>("buyCount"));
        this.getColumns().addAll(nameCol, priceCol, quantityCol);

        // Set column resize policy
        this.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }
}
