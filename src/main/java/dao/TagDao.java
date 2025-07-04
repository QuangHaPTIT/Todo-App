package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import model.Tag;
import utils.DBUtils;

public class TagDao {
	public Tag addTag(Tag tag) throws SQLException {
		
		String sql = "INSERT INTO tags (user_id, tag_name, color_code, text_color, created_at) VALUES (?, ?, ?, ?, ?)";
		Connection conn = null;
		try {
			conn = DBUtils.getConnection();
			try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
				pstmt.setInt(1, tag.getUserId());
				pstmt.setString(2, tag.getTagName());
				pstmt.setString(3, tag.getColorCode());
				pstmt.setString(4, tag.getTextColor());
				LocalDateTime createdAt = LocalDateTime.now();
				pstmt.setObject(5, createdAt);
				
				int affectedRows = pstmt.executeUpdate();
				
				if(affectedRows == 0) {
					return null;
				}
				
				try(ResultSet generatedKey = pstmt.getGeneratedKeys()) {
					if(generatedKey.next()) {
						tag.setTagId(generatedKey.getInt(1));
					} else {
						return null;
					}
				} 
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e; // Re-throw the exception
		} finally {
			DBUtils.closeConnection(conn);
		}
		return tag; // Return the tag with generated ID
	}
	
	public Tag updateTag(Tag tag) {
		
		String sql = "UPDATE tags SET tag_name = ?, color_code = ?, text_color = ? WHERE tag_id = ? AND user_id = ?";
		
		try(Connection conn = DBUtils.getConnection(); 
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			
			pstmt.setString(1, tag.getTagName());
			pstmt.setString(2, tag.getColorCode());
			pstmt.setString(3, tag.getTextColor());
			pstmt.setInt(4, tag.getTagId());
			pstmt.setInt(5, tag.getUserId());
			
			int rowsAffect = pstmt.executeUpdate();
			
			if(rowsAffect > 0) {
				return tag;
			} else {
				System.err.println("No tag updated. Check tag_id and user_id.");
	            return null;
			}
			
		}catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean deleteTag(int tagId, int userId) throws SQLException {
		String sql = "DELETE FROM tags WHERE tag_id = ? AND user_id = ?";
		
		try(Connection conn = DBUtils.getConnection(); 
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			
			pstmt.setInt(1, tagId);
			pstmt.setInt(2, userId);
			
			int rowsAffected = pstmt.executeUpdate();
			
			return rowsAffected > 0;
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public List<Tag> getAllTagByUserId(int user_id) {
		System.out.println("=== DEBUG TagDao.getAllTagByUserId ===");
		System.out.println("user_id parameter: " + user_id);
		
		String sql = "SELECT * FROM tags WHERE user_id = ?";
		System.out.println("SQL query: " + sql);
		
		List<Tag> tags = new ArrayList<Tag>();
		
		try (Connection conn = DBUtils.getConnection(); 
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, user_id);
			System.out.println("Executing query with user_id: " + user_id);
			
			try (ResultSet rs = pstmt.executeQuery()) {
				while(rs.next()) {
					Tag tag = new Tag();
					tag.setTagId(rs.getInt("tag_id"));
					tag.setTagName(rs.getString("tag_name"));
					tag.setTextColor(rs.getString("text_color"));
					tag.setColorCode(rs.getString("color_code"));
					tag.setCreatedAt(rs.getObject("created_at", LocalDateTime.class));
					
					System.out.println("Found tag: " + tag.getTagName() + " (id: " + tag.getTagId() + ")");
					tags.add(tag);
				}
			}
			
			System.out.println("Total tags found: " + tags.size());
		} catch (SQLException e) {
			System.out.println("Error fetching tags: " + e.getMessage());
			e.printStackTrace();
		}
		
		List<Tag> result = tags.isEmpty() ? Collections.emptyList() : tags;
		System.out.println("Returning " + result.size() + " tags");
		return result;
	}
	
	public Tag getTagById(int tagId, int userId) {
		String sql = "SELECT * FROM tags WHERE tag_id = ? AND user_id = ?";
		
		try(Connection conn = DBUtils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);) {
			pstmt.setInt(1, tagId);
			pstmt.setInt(2, userId);
			
			try (ResultSet rs = pstmt.executeQuery()) {
				if(rs.next()) {
					Tag tag = new Tag();
					tag.setTagId(tagId);
					tag.setUserId(userId);
					tag.setTagName(rs.getString("tag_name"));
					tag.setColorCode(rs.getString("color_code"));
					tag.setTextColor(rs.getString("text_color"));
					tag.setCreatedAt(rs.getObject("created_at", LocalDateTime.class));
					return tag;
				}
			}
		} catch (SQLException e) {
			System.out.print("Error fetch tag with id = " + tagId);
			System.out.print("Error :" + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
}
