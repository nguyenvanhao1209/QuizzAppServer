package application.server;

public class Server {
	public static void main(String[] args) {
		int port = 8080;
		ServerThread  serverThread = new ServerThread(port);
		serverThread.start();
	}
}
