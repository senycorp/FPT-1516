package fpt.com.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ServerIn implements Runnable {

	private Socket socket;
	private Warehouse warehouse;
	private ObjectInputStream objectInputStream;
	private String loginString;
	private fpt.com.model.Order incommingOrder;
	
	public ServerIn(Socket socket, Warehouse warehouse) {
		this.socket = socket;
		this.warehouse = warehouse;
		this.loginString = null;
		this.incommingOrder = null;
	}
	
	@Override
	public void run() {
		try {
			objectInputStream = new ObjectInputStream(socket.getInputStream());
			
			while (socket.isConnected()) {
				try {					
					loginString = (String)objectInputStream.readObject();
					incommingOrder = (fpt.com.model.Order)objectInputStream.readObject();
					
					if (login(loginString.split(";")[0], loginString.split(";")[1])) {
						this.warehouse.addOrder(incommingOrder);
					}
					
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	
	private Boolean login(String username, String password) {
		Boolean valid = false;
		
		if (username.equals("admin") & password.equals("admin")) {
			valid = true;
		}
		
		return valid;
	}
}
