package fpt.com.view;

import fpt.com.core.view.BaseTableView;

import java.util.HashMap;

/**
 * TableView: Orders
 *
 * @param <Order>
 */
public class OrdersTableView<Order>
        extends BaseTableView<Order> {

    @Override
    protected HashMap<String, String> getColumnMappings() {
        HashMap<String, String> columnMappings = new HashMap<>();

        columnMappings.put("Name", "name");
        columnMappings.put("Price", "price");
        columnMappings.put("Buy count", "buyCount");

        return columnMappings;
    }
}
