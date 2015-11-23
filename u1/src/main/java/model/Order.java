package model;

import java.util.Iterator;

import fpt.com.Product;

public class Order implements fpt.com.Order {

	@Override
	public Iterator<Product> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean add(Product e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Product p) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Product findProductById(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Product findProductByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getSum() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getQuantity() {
		// TODO Auto-generated method stub
		return 0;
	}

}
