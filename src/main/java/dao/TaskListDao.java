package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import model.TaskList;
import utils.DBUtils;

public class TaskListDao {
    public TaskList addTaskList(TaskList taskList) throws SQLException {
        String sql = "INSERT INTO task_lists (user_id, list_name, color_code, icon, display_order, created_at) "
                   + "VALUES (?, ?, ?, ?, ?, ?) RETURNING *";
        
        try (Connection con = DBUtils.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            
            if (taskList.getCreatedAt() == null) {
                taskList.setCreatedAt(LocalDateTime.now());
            }
            
            stmt.setInt(1, taskList.getUserId());
            stmt.setString(2, taskList.getListName());
            stmt.setString(3, taskList.getColorCode());
            stmt.setString(4, taskList.getIcon() != null ? taskList.getIcon() : "");
            stmt.setInt(5, taskList.getDisplayOrder() != 0 ? taskList.getDisplayOrder() : 0);
            stmt.setTimestamp(6, Timestamp.valueOf(taskList.getCreatedAt()));
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToTaskList(rs);
                }
            }
        }
        throw new SQLException("Thêm task list thất bại, không có ID được trả về");
    }
    
    public TaskList updateTaskList(TaskList taskList) throws SQLException {
        String sql = "UPDATE task_lists SET list_name = ?, color_code = ?, icon = ?, "
                   + "display_order = ? WHERE list_id = ? AND user_id = ? RETURNING *";
        
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, taskList.getListName());
            stmt.setString(2, taskList.getColorCode());
            stmt.setString(3, taskList.getIcon() != null ? taskList.getIcon() : "");
            stmt.setInt(4, taskList.getDisplayOrder() != 0 ? taskList.getDisplayOrder() : 0);
            stmt.setInt(5, taskList.getListId());
            stmt.setInt(6, taskList.getUserId());
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToTaskList(rs);
                }
            }
        }
        throw new SQLException("Cập nhật task list thất bại");
    }
    
    public boolean deleteTaskList(int listId, int userId) throws SQLException {
        String sql = "DELETE FROM task_lists WHERE list_id = ? AND user_id = ?";
        
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, listId);
            stmt.setInt(2, userId);
            
            return stmt.executeUpdate() > 0;
        }
    }
    
    public List<TaskList> getTaskListsByUserId(int userId) throws SQLException {
        String sql = "SELECT * FROM task_lists WHERE user_id = ? ORDER BY display_order";
        List<TaskList> taskLists = new ArrayList<>();
        
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    taskLists.add(mapResultSetToTaskList(rs));
                }
            }
        }
       System.out.print(taskLists.get(0));
        return taskLists;
    }
    
    // Lấy task lists với task count
    public List<TaskList> getTaskListsWithCount(int userId) throws SQLException {
        String sql = "SELECT tl.*, " +
                     "COALESCE(COUNT(t.task_id), 0) as task_count " +
                     "FROM task_lists tl " +
                     "LEFT JOIN tasks t ON tl.list_id = t.list_id AND t.user_id = ? AND t.parent_task_id IS NULL " +
                     "WHERE tl.user_id = ? " +
                     "GROUP BY tl.list_id, tl.user_id, tl.list_name, tl.color_code, tl.icon, tl.display_order, tl.created_at " +
                     "ORDER BY tl.display_order";
        
        List<TaskList> taskLists = new ArrayList<>();
        
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, userId);
            stmt.setInt(2, userId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    TaskList taskList = mapResultSetToTaskList(rs);
                    taskList.setTaskCount(rs.getInt("task_count"));
                    taskLists.add(taskList);
                }
            }
        }
        
        return taskLists;
    }
    
    private TaskList mapResultSetToTaskList(ResultSet rs) throws SQLException {
        int listId = rs.getInt("list_id");
        int userId = rs.getInt("user_id");
        String listName = rs.getString("list_name");
        String colorCode = rs.getString("color_code");
        String icon = rs.getString("icon");
        int displayOrder = rs.getInt("display_order");
        LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();
        
        return new TaskList(listId, userId, listName, colorCode, icon, displayOrder, createdAt);
    }
}