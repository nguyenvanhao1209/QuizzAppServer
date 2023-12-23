package application.model;

public class Room {
	private int ID;
	private String name;
	private int status;
	private int categoryID;
	private int examID;
	private int adminID;
	public Room(int iD, String name, int status, int categoryID, int examID, int adminID) {
		super();
		ID = iD;
		this.name = name;
		this.status = status;
		this.categoryID = categoryID;
		this.examID = examID;
		this.adminID = adminID;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getCategoryID() {
		return categoryID;
	}
	public void setCategoryID(int categoryID) {
		this.categoryID = categoryID;
	}
	public int getExamID() {
		return examID;
	}
	public void setExamID(int examID) {
		this.examID = examID;
	}
	public int getAdminID() {
		return adminID;
	}
	public void setAdminID(int adminID) {
		this.adminID = adminID;
	}
	
}
