package application.model;

public class User {
	private int ID;
	private String username;
	private String password;
	private boolean isOnline;
	public User(int ID ,String username, String password, boolean isOnline) {
		super();
		this.ID = ID;
		this.username = username;
		this.password = password;
		this.isOnline = isOnline;
	}
	
	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isOnline() {
		return isOnline;
	}
	public void setOnline(boolean isOnline) {
		this.isOnline = isOnline;
	}
	
}
