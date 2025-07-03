package dao;

import model.User;
import utils.DBUtils;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.mindrot.jbcrypt.BCrypt;


public class UserDao {
	
	public UserDao() {};
	
	public boolean registerUser(User user) {
		String sql = "INSERT INTO users (first_name, last_name, username, email, password, dob) VALUES (?, ?, ?, ?, ?, ?)";
		
		try(Connection con = DBUtils.getConnection()) {
			PreparedStatement statement = con.prepareStatement(sql);
			String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
			
			statement.setString(1, user.getFirstName());
			statement.setString(2, user.getLastName());
			statement.setString(3, user.getUsername());
			statement.setString(4, user.getEmail());
			statement.setString(5, hashedPassword);
			statement.setDate(6, Date.valueOf(user.getDob()));
			
			return statement.executeUpdate() > 0;
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public User checkLogin (String email, String password) {
		User user = findByEmail(email);
		
		if(user == null) return null;
		
		if(BCrypt.checkpw(password, user.getPassword())) {
			return user;
		}
		return null;
	}
	
	public User findByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password")); // hashed password
                user.setUsername("username");
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
	
	public boolean existingByEmail(String email) {
	    String sql = "SELECT 1 FROM users WHERE email = ?";

	    try (Connection con = DBUtils.getConnection();
	         PreparedStatement statement = con.prepareStatement(sql)) {

	        statement.setString(1, email);
	        ResultSet rs = statement.executeQuery();

	        return rs.next(); // nếu có kết quả ⇒ đã tồn tại
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return false; // nếu lỗi hoặc không tìm thấy ⇒ không tồn tại
	}

}
