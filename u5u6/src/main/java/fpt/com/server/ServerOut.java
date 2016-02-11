package fpt.com.server;

import fpt.com.Order;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerOut implements Runnable {

	private Socket socket;
	private Warehouse warehouse;
	private Boolean warehouseHasChanged;
	private ObjectOutputStream objectOutputStream;
	
	
	public void setWarehousHasChanged(Boolean warehousHasChanged) {
		this.warehouseHasChanged = warehousHasChanged;
	}
	
	public ServerOut(Socket socket, Warehouse warehouse) {
		this.socket = socket;
		this.warehouse = warehouse;
		this.warehouseHasChanged = false;
	}
	
	
	@Override
	public void run() {
		try {
			objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
			
			while (socket.isConnected()) {
				synchronized (warehouseHasChanged) {
					if (warehouseHasChanged) {
						Order ordersToSend = (Order) this.warehouse.getOrderedProducts();
						objectOutputStream.reset();
						objectOutputStream.writeObject(ordersToSend);
						objectOutputStream.flush();
						System.out.println("Server hat Rueckantwort gesendet auf Socket: " + this.socket);
						warehouseHasChanged = false;
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
