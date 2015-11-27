package fpt.com.model;

import fpt.com.Product;
import fpt.com.core.model.BaseModelList;

/**
 * Model: ProductList
 */
public class ProductList
        extends BaseModelList<Product>
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
