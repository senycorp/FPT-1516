package fpt.com.component.client;

import fpt.com.model.ModelCustomer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * Created by senycorp on 11.02.16.
 */
public class ClientIn implements Runnable{

    private Socket            socket;
    private ModelCustomer     modelCustomer;
    private ObjectInputStream objectInputStream;
    private fpt.com.model.Order       incommingOrder;


    public ClientIn(Socket socket, ModelCustomer modelCustomer) {
        this.socket = socket;
        this.modelCustomer = modelCustomer;
        this.incommingOrder = null;
    }

    @Override
    public void run() {

        try {
            objectInputStream = new ObjectInputStream(socket.getInputStream());

            while (socket.isConnected()) {
                try {
                    incommingOrder = (fpt.com.model.Order)objectInputStream.readObject();

                    modelCustomer.clear();
                    for (fpt.com.Product product : incommingOrder) {
                        modelCustomer.add(product);
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
