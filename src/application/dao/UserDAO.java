package application.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import application.model.User;

public class UserDAO extends DAO {
	
	public UserDAO(){
		super();
	}
	
	public User verifyUser(String username, String password) {
		try {
			PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?");
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, password);
			ResultSet rs = preparedStatement.executeQuery();
			if(rs.next()) {
				return new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getBoolean(4));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public User getUserByID(int ID) {
		try {
			PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM users WHERE id = ?");
			preparedStatement.setInt(1, ID);
			ResultSet rs = preparedStatement.executeQuery();
			if(rs.next()) {
				return new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getBoolean(4));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void addUser(String username, String password) {
		try {
			PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO users (username, password) VALUES (?,?)");
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, password);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public boolean checkDuplicated(String username) {
		try {
			PreparedStatement preparedStatement = con.prepareStatement("SELECT * from users WHERE username = ?");
			preparedStatement.setString(1, username);
			ResultSet rs = preparedStatement.executeQuery();
			if(rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public void updateToOnline(int ID) {
		try {
			PreparedStatement preparedStatement = con.prepareStatement("UPDATE users SET isOnline = 1 WHERE ID = ?");
			preparedStatement.setInt(1, ID);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void updateToOffline(int ID) {
		try {
			PreparedStatement preparedStatement = con.prepareStatement("UPDATE users SET isOnline = 0 WHERE ID = ?");
			preparedStatement.setInt(1, ID);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
