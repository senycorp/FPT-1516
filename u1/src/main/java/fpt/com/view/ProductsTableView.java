package fpt.com.view;


import fpt.com.core.view.BaseTableView;
import javafx.scene.control.TableView;

import java.util.HashMap;

/**
 * TableView: ProductsTableView
 *
 * @param <Product>
 */
public class ProductsTableView<Product>
        extends BaseTableView<Product> {

    /**
     * Constructor
     *
     * @param showId
     */
    public ProductsTableView(boolean showId) {
        HashMap<String, String> columnMappings = this.getColumnMappings();

        for (String e : columnMappings.keySet()) {
            if (e.equals("ID") && showId) {
                this.getColumns().add(createColumn(e, columnMappings.get(e)));
            } else {
                this.getColumns().add(createColumn(e, columnMappings.get(e)));
            }
        }

        // Set column resize policy
        this.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    /**
     * Super-Constructor
     */
    public ProductsTableView() {
        super();
    }

    @Override
    protected HashMap<String, String> getColumnMappings() {
        HashMap<String, String> columnMappings = new HashMap<>();

        columnMappings.put("ID", "id");
        columnMappings.put("Name", "name");
        columnMappings.put("Price", "price");
        columnMappings.put("Quantity", "quantity");

        return columnMappings;
    }
}
