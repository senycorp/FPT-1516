package fpt.com.component.client;

import fpt.com.model.Order;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by senycorp on 11.02.16.
 */
public class ClientOut implements Runnable {

    private Socket                       socket;
    private ObjectOutputStream           objectOutputStream;
    private CopyOnWriteArrayList<Object> workingQueue;

    public ClientOut(Socket socket) {
        this.socket = socket;
        this.workingQueue = new CopyOnWriteArrayList<Object>();
    }

    public void send(String loginData, Order order) {
        synchronized (workingQueue) {
            this.workingQueue.add(loginData);
            this.workingQueue.add(order);
        }
    }

    @Override
    public void run() {

        try {
            this.objectOutputStream = new ObjectOutputStream(socket.getOutputStream());

            while (socket.isConnected()) {
                synchronized (workingQueue) {
                    while (!workingQueue.isEmpty()) {
                        objectOutputStream.writeObject(workingQueue.get(0));
                        workingQueue.remove(0);

                        if (workingQueue.isEmpty()) {
                            objectOutputStream.flush();
                            System.out.println("Client hat eine Nachricht gesendet, ueber Socket: " + this.socket);
                        }
                    }
                }
            }

            if (socket.isClosed()) {
                this.objectOutputStream.close();
                this.objectOutputStream = null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
