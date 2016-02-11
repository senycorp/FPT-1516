package fpt.com.model;

import fpt.com.Product;
import fpt.com.component.client.ClientIn;
import fpt.com.component.client.ClientOut;
import javafx.collections.ModifiableObservableListBase;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ModelCustomer  extends ModifiableObservableListBase<Product> implements fpt.com.Order  {

    /**
     *
     */
    private static final long  serialVersionUID = 20178282899792526L;
    private final        Order orders           = new Order();


    //Networkstuff
    private ClientIn  in;
    private ClientOut out;
    private Thread    inThread, outThread;
    private Socket socket;

    public ModelCustomer() {
        super();
        try {

            this.socket = new Socket("localhost", 6666);

            in = new ClientIn(socket, this);
            out = new ClientOut(socket);

            inThread = new Thread(in, "Client incoming Connection");
            outThread = new Thread(out, "Client outgoing Connection");

            inThread.start();
            outThread.start();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendOrder(String loginData, Order order) {
        if(in != null & out != null) {
            out.send(loginData, order);
        }
    }

    @Override
    public boolean delete(Product p) {
        return remove(p);
    }

    @Override
    public Product findProductById(long id) {
        return this.orders.findProductById(id);
    }

    @Override
    public Product findProductByName(String name) {
        return this.orders.findProductByName(name);
    }

    @Override
    public double getSum() {
        return this.orders.getSum();
    }

    @Override
    public int getQuantity() {
        return this.orders.getQuantity();
    }

    @Override
    public Product get(int index) {
        return (Product) this.orders.get(index);
    }

    @Override
    public int size() {
        return this.orders.size();
    }

    @Override
    protected void doAdd(int index, Product element) {
        this.orders.add(index, element);
    }

    @Override
    protected Product doSet(int index, Product element) {
        return (Product) this.orders.set(index, element);
    }

    @Override
    protected Product doRemove(int index) {
        return (Product) this.orders.remove(index);
    }

}
