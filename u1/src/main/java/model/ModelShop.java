package model;

import fpt.com.Product;
import javafx.collections.FXCollections;
import javafx.collections.ModifiableObservableListBase;
import javafx.collections.ObservableList;

/**
 * Model: SHOP
 */
public class ModelShop
        extends ModifiableObservableListBase<Product> {

    /**
     * Product list
     */
    private ObservableList<Product> products;

    /**
     * Constructor
     *
     * @param productList
     */
    public ModelShop(ProductList productList) {
        // Set products
        this.products = FXCollections.observableArrayList(productList);
    }

    /**
     * Returns observable list for the product list
     *
     * @return
     */
    public ObservableList<Product> getProducts() {
        return this.products;
    }

    @Override
    public Product get(int index) {
        return (Product) this.products.get(index);
    }

    @Override
    public int size() {
        return this.products.size();
    }

    @Override
    public void doAdd(int index, Product product) {
        this.products.add(index, product);
    }

    @Override
    public Product doSet(int index, Product product) {
        return (Product) this.products.set(index, product);
    }

    @Override
    public Product doRemove(int index) {
        return (Product) this.products.remove(index);
    }

}
