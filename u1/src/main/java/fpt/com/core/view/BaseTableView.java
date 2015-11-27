package fpt.com.core.view;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.HashMap;

/**
 * BaseTableView
 *
 * @author senycorp
 */
abstract public class BaseTableView<E> extends TableView<E> {

    /**
     * Constructor
     */
    public BaseTableView() {

        HashMap<String, String> columnMappings = this.getColumnMappings();

        for (String e : columnMappings.keySet()) {
            this.getColumns().add(createColumn(e, columnMappings.get(e)));
        }

        // Set column resize policy
        this.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    /**
     * Create column
     *
     * @param display
     * @param propertyName
     * @return
     */
    protected TableColumn<E, String> createColumn(String display, String propertyName) {
        TableColumn<E, String> col = new TableColumn<E, String>(display);
        col.setCellValueFactory(new PropertyValueFactory<E, String>(propertyName));

        return col;
    }

    /**
     * Get
     *
     * @return
     */
    protected abstract HashMap<String, String> getColumnMappings();
}
