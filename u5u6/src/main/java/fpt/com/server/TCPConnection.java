package fpt.com.server;

import java.net.Socket;

public class TCPConnection {
	private int name;
	private Socket socket;
	private Warehouse warehouse;
	
	private ServerIn in;
	private ServerOut out;
	
	public ServerIn getIn() {
		return in;
	}
	
	public ServerOut getOut() {
		return out;
	}
	
	public TCPConnection(int name, Socket socket, Warehouse warehouse) {
		this.name = name;
		this.socket = socket;
		this.warehouse = warehouse;
		
		System.out.printf("Verbindung %d hergestellt\n", this.name);
		
		this.in = new ServerIn(this.socket, this.warehouse);
		this.out = new ServerOut(this.socket, this.warehouse);
		
		Thread inThread = new Thread(this.in, "Incoming Server Connection");
		Thread outThread = new Thread(this.out, "Outgoing Server Connection");
		
		inThread.start();
		outThread.start();
	}
}
