package fpt.com.server;

import fpt.com.Order;
import fpt.com.Product;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Warehouse
 */
public final class Warehouse implements Runnable {
	
	private Order orders;
	
	public Order getOrderedProducts() {
		synchronized (this.orders) {
			return this.orders;
		}
	}
	
	public void addOrder(Order order) {
		synchronized (queue) {
			 queue.add(order);
		}
	}
	
	
	
	private CopyOnWriteArrayList<Order> queue;
	public CopyOnWriteArrayList<Order> getQueue() {
		synchronized (queue) {
			return queue;
		}
	}
	
	private ArrayList<TCPConnection> tcpConnections = new ArrayList<TCPConnection>();
	public ArrayList<TCPConnection> getTcpConnections() {
		synchronized (tcpConnections) {
			return tcpConnections;
		}
	}
	
	private void notifyAllOutConnections() {
		for (TCPConnection connection : tcpConnections) {
			connection.getOut().setWarehousHasChanged(true);
		}
	}
	
	private double sumPrice, sumQuantity;
	
	public Warehouse() {
		this.orders = new fpt.com.model.Order();
		this.queue = new CopyOnWriteArrayList<fpt.com.Order>();
		this.sumPrice = 0;
		this.sumQuantity = 0;
	}
	
	
	public static void main(String args[]) {
		Warehouse warehouse = new Warehouse();
		
		Thread warehouseThread = new Thread(warehouse, "Warenhaus");
		warehouseThread.start();
		
		try (ServerSocket server = new ServerSocket(6666);) {
			int connections = 0;
			while (true) {
				try {
					Socket socket = server.accept();
					connections++;
					TCPConnection newTCPConnection = new TCPConnection(connections, socket, warehouse);
					warehouse.getTcpConnections().add(newTCPConnection);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		
		while (true) {
			synchronized (this.queue) {
				synchronized (this.orders) {

					if (!this.queue.isEmpty()) {
						StringBuilder outputStringBuilder = new StringBuilder();

						outputStringBuilder.append("...\n");
						outputStringBuilder.append("Order eingegangen:\n");

						// Erstes Element der Queue zur orderList hinzufuegen
						for (Product product : this.queue.get(0)) {
							// Zuerst der Teil fuer die Ausgabe
							outputStringBuilder.append(String.format("%s	%d	%.2f EUR\n",product.getName(),product.getQuantity(),product.getQuantity()* product.getPrice()));
							
							// Und dann fuer die eigentliche Liste
							if (this.orders.findProductByName(product.getName()) != null) {
								Product tmp = this.orders.findProductByName(product.getName());
								tmp.setQuantity(tmp.getQuantity() + product.getQuantity());
							} else {
								this.orders.add(product);
							}
						}
						
						// QueueItem wurde abgearbeitet also raus aus der Queue
						this.queue.remove(0);
						// Gebe allen Connections Bescheid, dass es eine Aenderung gab
						notifyAllOutConnections();
						
						//Gesamtuebersicht zusammenbauen
						outputStringBuilder.append("\nOrders gesamt\n");
						outputStringBuilder.append("========================================\n");
						
						for (Product product : this.orders) {
							this.sumPrice += product.getPrice() * product.getQuantity();
							this.sumQuantity += product.getQuantity();
							outputStringBuilder.append(String.format("%s	%d	%.2f EUR\n",product.getName(),product.getQuantity(),product.getQuantity()* product.getPrice()));
						}
						
						outputStringBuilder.append("========================================\n");
						outputStringBuilder.append("Gesamtanzahl: " + this.sumQuantity + "\n");
						outputStringBuilder.append("Gesamtwert: " + this.sumPrice + " EUR\n...\n");
						
						System.out.println(outputStringBuilder.toString());
					} // else do nothing
				}
			}
		}		
	}
	
}
