package application.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import application.dao.UserDAO;
import application.model.User;

public class ServerThread extends Thread {
	private int port = 8080;
	private ServerSocket serverSocket = null;
	private Socket socket = null;
	private boolean isClosed;
	
	public ServerThread(int port) {
		this.port = port;
		isClosed = false;
	}
	
	public String getStringFromUser(User user) {
		return ""+user.getID()+","+user.getUsername()+","+user.getPassword();
	}
	
	public void handleClient(Socket socket) throws IOException, RuntimeException {
		User user = null;
		UserDAO userDAO = new UserDAO();
		System.out.println("Connection from "+socket.getRemoteSocketAddress());
		BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		String message;	
		while(!isClosed) {
			message = input.readLine();
			String[] messageSplit = message.split(",");
			// xu ly dang nhap
			if(messageSplit[0].equals("client-verify")) {
				User userLogin = userDAO.verifyUser(messageSplit[1], messageSplit[2]);
				if(userLogin == null) {
					PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
					out.println("wrong-user,"+messageSplit[1]+","+messageSplit[2]);
				}
				else if(userLogin.isOnline()) {
					PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
					out.println("dupplicate-login,"+messageSplit[1]+","+messageSplit[2]);
				}
				else {
					PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
					out.println("login-success,"+getStringFromUser(userLogin));
					user = userLogin;
					userDAO.updateToOnline(user.getID());
				}
			}
			// xu ly dang xuat
			if(messageSplit[0].equals("offline")) {
				userDAO.updateToOffline(Integer.parseInt(messageSplit[1]));
				user = null;
			}
			// xu ly dang ky
			if(messageSplit[0].equals("register")) {
				boolean checkduplicate = userDAO.checkDuplicated(messageSplit[1]);
				if(checkduplicate) {
					PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
					out.println("duplicate-username");
				}
				else {
					userDAO.addUser(messageSplit[1], messageSplit[2]);
					PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
					out.println("registed-success");
				}
			}
		}
	}
	
	@Override
	public void run() {
		try {
			System.out.println("Waiting for connection...");
			serverSocket = new ServerSocket(port);
			
			while(true) {
				socket = serverSocket.accept();
				new Thread(()->{
					try {
						handleClient(socket);
					} catch (IOException e) {
						throw new RuntimeException(e);
					}
				}).start();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
