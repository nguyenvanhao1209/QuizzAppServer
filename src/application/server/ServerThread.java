package application.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import application.dao.UserDAO;
import application.model.User;

public class ServerThread implements Runnable {
	
	private User user;
	private UserDAO userDAO;
	private Socket socketOfServer;
	private BufferedReader is;
	private BufferedWriter os;
	private boolean isClosed;
	private String clientIP;
	private int clientNumber;
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public BufferedReader getIs() {
		return is;
	}

	public void setIs(BufferedReader is) {
		this.is = is;
	}

	public BufferedWriter getOs() {
		return os;
	}

	public void setOs(BufferedWriter os) {
		this.os = os;
	}

	public boolean isClosed() {
		return isClosed;
	}

	public void setClosed(boolean isClosed) {
		this.isClosed = isClosed;
	}

	public String getClientIP() {
		return clientIP;
	}

	public void setClientIP(String clientIP) {
		this.clientIP = clientIP;
	}

	
	public int getClientNumber() {
		return clientNumber;
	}

	public void setClientNumber(int clientNumber) {
		this.clientNumber = clientNumber;
	}

	public ServerThread(Socket socketOfServer, int clientNumber) {
		this.socketOfServer = socketOfServer;
		this.clientNumber = clientNumber;
		this.userDAO = new UserDAO();
		this.isClosed = false;
		if(this.socketOfServer.getInetAddress().getHostAddress().equals("127.0.0.1")) {
			clientIP = "127.0.0.1";
		}
		else {
			clientIP = this.socketOfServer.getInetAddress().getHostAddress();
		}
	}
	
	public String getStringFromUser(User user) {
		return user.getID()+","+user.getUsername()+","+user.getPassword();
	}

	@Override
	public void run() {
		try {
			is = new BufferedReader(new InputStreamReader(socketOfServer.getInputStream()));
			os = new BufferedWriter(new OutputStreamWriter(socketOfServer.getOutputStream()));
			System.out.println("Khời động luông mới thành công, ID là: " + clientNumber);
			write("server-send-id" + ", " + this.clientNumber);
			String message;
			while (!isClosed) {
				message = is.readLine();
				if(message == null) {
					break;
				}
				
				String[] messageSplit = message.split(",");
				// xu ly dang nhap
				if(messageSplit[0].equals("client-verify")) {
					System.out.println(message);
					User userLogin = userDAO.verifyUser(messageSplit[1], messageSplit[2]);
					if(userLogin == null) {
						write("wrong-user" + messageSplit[1] + "," + messageSplit[2]);
					}
					else if(userLogin.isOnline()) {
						write("duplicate-login"+messageSplit[1]+","+messageSplit[2]);
					}
					else {
						write("login-success" + getStringFromUser(userLogin));
						this.user = userLogin;
						userDAO.updateToOnline(this.user.getID());
						
					}
				}
				// xu ly dang ky
				if(messageSplit[0].equals("register")) {
					boolean checkDuplicate = userDAO.checkDuplicated(messageSplit[1]);
					if(checkDuplicate) write("duplicate-username");
					else {
						userDAO.addUser(messageSplit[1], messageSplit[2]);
						User userRegisted = userDAO.verifyUser(messageSplit[1], messageSplit[2]);
						this.user = userRegisted;
						userDAO.updateToOnline(this.user.getID());
						write("login-success" + getStringFromUser(this.user));
					}
				}
				// xu ly dang xuat
				if(messageSplit[0].equals("offline")) {
					userDAO.updateToOffline(this.user.getID());
					this.user = null;
				}
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void write(String message) throws IOException {
		os.write(message);
		os.newLine();
		os.flush();
	}
}
