package fpt.com.model;

import fpt.com.Product;
import fpt.com.core.model.BaseModelList;

import java.util.Iterator;

public class Order
        extends BaseModelList<Product>
        implements fpt.com.Order {

    @Override
    public Iterator<Product> iterator() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean add(Product e) {
        return false;
    }

    @Override
    public boolean delete(Product p) {
        return false;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public Product findProductById(long id) {
        return null;
    }

    @Override
    public Product findProductByName(String name) {
        return null;
    }

    @Override
    public double getSum() {
        return 0;
    }

    @Override
    public int getQuantity() {
        return 0;
    }
}
