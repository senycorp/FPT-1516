package fpt.com.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class UDPServer {
	public static void main(String args[]) {
		
		//DateFormatter
		SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		
		//Datagramm Socket erstellen, um auf UDP zu arbeiten
		try (DatagramSocket socket =  new DatagramSocket(6667);){
			while (true) {
				
				// Neues Paket anlegen
				DatagramPacket packet = new DatagramPacket(new byte[5], 5);
				// Auf Paket warten
				try {
					socket.receive(packet);
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				InetAddress address = packet.getAddress();
				int port = packet.getPort();
				byte[] data = packet.getData();
				
				System.out.printf("Anfrage von %s auf Port %d \n", address, port);
				
				//Nutzdaten in ein String uebergeben
				String dataString = new String(data);
				
				try (Scanner scanner = new Scanner(dataString).useDelimiter(":")) {
					String key = scanner.next();
					byte[] answer = null;
					
					switch (key) {
					case "time":
						Date date = new Date();
						answer = dateFormatter.format(date.getTime()).getBytes();
						break;
					default:
						answer = new String("Command unknown").getBytes();
						break;
					}
					
					packet = new DatagramPacket(answer, answer.length, address, 5555);
					socket.send(packet);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
}
