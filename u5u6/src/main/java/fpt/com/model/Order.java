package fpt.com.model;

import fpt.com.Product;

public class Order extends ProductList implements fpt.com.Order {

    private static final long serialVersionUID = 813561588704786786L;

    @Override
    public double getSum() {
        double sum = 0f;

        for (Product product : this) {
            sum += product.getPrice() * product.getQuantity();
        }
        return sum;
    }

    @Override
    public int getQuantity() {
        int overallQuantity = 0;

        for (Product product : this) {
            overallQuantity += product.getQuantity();
        }
        return overallQuantity;
    }

}