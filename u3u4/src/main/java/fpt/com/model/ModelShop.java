package fpt.com.model;

import fpt.com.Product;
import fpt.com.core.model.BaseModel;
import javafx.collections.ObservableList;

/**
 * Model: SHOP
 */
public class ModelShop
        extends BaseModel<Product, ProductList> {

    /**
     * Product list
     */
    private ObservableList<Product> products;


    @Override
    public ProductList getList() {
        return new ProductList();
    }
}
