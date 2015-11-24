package model;

import fpt.com.Product;

import java.util.ArrayList;

/**
 * Model: ProductList
 */
public class ProductList
        extends ArrayList<Product>
        implements fpt.com.ProductList {

    /**
     * SerialID
     */
    private static final long serialVersionUID = 5814542130333539386L;

    @Override
    public boolean delete(Product product) {
        return this.remove(product);
    }

    @Override
    public Product findProductById(long id) {
        for (Product e : this) {
            if (e.getId() == id) {
                return e;
            }
        }

        return null;
    }

    @Override
    public Product findProductByName(String name) {
        for (Product e : this) {
            if (e.getName().equals(name)) {
                return e;
            }
        }

        return null;
    }
}
