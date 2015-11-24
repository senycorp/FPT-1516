package view;


import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * TableView: ProductsTableView
 *
 * @param <Product>
 */
public class ProductsTableView<Product>
        extends TableView<Product> {

    /**
     * Constructor
     *
     * @param showId
     */
    public ProductsTableView(boolean showId) {
        buildTable(showId);
    }

    /**
     * Constructor
     */
    public ProductsTableView() {
        buildTable(false);
    }

    /**
     * Build table and set properties
     *
     * @param showId
     */
    private void buildTable(boolean showId) {
        TableColumn<Product, String> idCol = new TableColumn<Product, String>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<Product, String>("id"));
        TableColumn<Product, String> nameCol = new TableColumn<Product, String>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        TableColumn<Product, String> priceCol = new TableColumn<Product, String>("Price");
        priceCol.setCellValueFactory(new PropertyValueFactory<Product, String>("price"));
        TableColumn<Product, String> quantityCol = new TableColumn<Product, String>("Quantity");
        quantityCol.setCellValueFactory(new PropertyValueFactory<Product, String>("quantity"));

        // ID-Handling
        if (showId) {
            this.getColumns().addAll(idCol, nameCol, priceCol, quantityCol);
        } else {
            this.getColumns().addAll(nameCol, priceCol, quantityCol);
        }

        this.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }
}
