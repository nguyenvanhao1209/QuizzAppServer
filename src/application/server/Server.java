package application.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Server {
	public static volatile ServerThreadBus serverThreadBus;
	public static Socket socketOfServer;
	
	public static void main(String[] args) {
		ServerSocket listener = null;
		serverThreadBus = new ServerThreadBus();
		System.out.println("Server is waiting to accept user...");
		int clientNumber = 0;
		
		try {
			listener = new ServerSocket(5500);
				
		} catch (IOException e) {
			System.out.println(e);
			System.exit(1);
		}
		
		ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 100, 10, TimeUnit.SECONDS, new ArrayBlockingQueue<>(8));
		
		try {
			while(true) {
				socketOfServer = listener.accept();
				System.out.println(socketOfServer.getInetAddress().getHostAddress());
				ServerThread serverThread = new ServerThread(socketOfServer, clientNumber++);
				serverThreadBus.add(serverThread);
				System.out.println("So thread dang chay la: " + serverThreadBus.getLength());
				executor.execute(serverThread);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				listener.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
