package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import model.Task;
import utils.DBUtils;

import java.util.ArrayList;
import java.util.Collections;

public class TaskDao {
	
	
	
    public Task addTask(Task task) {
        String sql = "INSERT INTO tasks (user_id, list_id, title, description, due_date, priority, status, display_order, created_at, updated_at) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        Connection conn = null;
        try {
            conn = DBUtils.getConnection();
            try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setInt(1, task.getUserId());
                pstmt.setObject(2, task.getListId());
                pstmt.setString(3, task.getTitle());
                pstmt.setString(4, task.getDescription());
                pstmt.setObject(5, task.getDueDate());
                pstmt.setString(6, task.getPriority());
                pstmt.setString(7, task.getStatus()); // Mặc định PENDING
                pstmt.setInt(8, task.getDisplayOrder()); 
                pstmt.setObject(9, task.getCreatedAt()); 
                pstmt.setObject(10, task.getUpdatedAt()); 

                int affectedRows = pstmt.executeUpdate();
                if (affectedRows == 0) {
                    return null;
                }

                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        task.setTaskId(generatedKeys.getInt(1));
                    } else {
                        return null;
                    }
                }
                return task;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            DBUtils.closeConnection(conn);
        }
    }

    public Task updateTask(Task task) {
        String sql = "UPDATE tasks SET list_id = ?, title = ?, description = ?, due_date = ?, priority = ?, status = ?, " +
                     "display_order = ?, updated_at = ? WHERE task_id = ? AND user_id = ?";

        Connection conn = null;
        try {
            conn = DBUtils.getConnection();
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setObject(1, task.getListId());
                pstmt.setString(2, task.getTitle());
                pstmt.setString(3, task.getDescription());
                pstmt.setObject(4, task.getDueDate());
                pstmt.setString(5, task.getPriority());
                pstmt.setString(6, task.getStatus());
                pstmt.setInt(7, task.getDisplayOrder());
                pstmt.setObject(8, LocalDateTime.now());
                pstmt.setInt(9, task.getTaskId());
                pstmt.setInt(10, task.getUserId());

                int affectedRows = pstmt.executeUpdate();
                if (affectedRows == 0) {
                    return null;
                }
                task.setUpdatedAt(LocalDateTime.now());
                return task;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            DBUtils.closeConnection(conn);
        }
    }

    public boolean deleteTask(int taskId, int userId) {
        String sql = "DELETE FROM tasks WHERE task_id = ? AND user_id = ?";

        Connection conn = null;
        try {
            conn = DBUtils.getConnection();
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, taskId);
                pstmt.setInt(2, userId);

                int affectedRows = pstmt.executeUpdate();
                return affectedRows > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DBUtils.closeConnection(conn);
        }
    }

    public Task getTaskById(int taskId, int userId) {
        String sql = "SELECT * FROM tasks WHERE task_id = ? AND user_id = ?";

        Connection conn = null;
        try {
            conn = DBUtils.getConnection();
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, taskId);
                pstmt.setInt(2, userId);

                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        Task task = new Task();
                        task.setTaskId(rs.getInt("task_id"));
                        task.setUserId(rs.getInt("user_id"));
                        task.setListId(rs.getObject("list_id", Integer.class));
                        task.setTitle(rs.getString("title"));
                        task.setDescription(rs.getString("description"));
                        task.setDueDate(rs.getObject("due_date", LocalDate.class));
                        task.setPriority(rs.getString("priority"));
                        task.setStatus(rs.getString("status"));
                        task.setDisplayOrder(rs.getInt("display_order"));
                        task.setCreatedAt(rs.getObject("created_at", LocalDateTime.class));
                        task.setUpdatedAt(rs.getObject("updated_at", LocalDateTime.class));
                        return task;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtils.closeConnection(conn);
        }
        return null;
    }
    
    public List<Task> getTasksByUserIdAndDateNow(int userId, LocalDate nowDate) {
    	String sql = "SELECT * FROM tasks WHERE userId = ? AND due_date = ? AND ";
    	//TODO: Code continue;
    	return Collections.emptyList();
    }
    
    public List<Task> getTasksByListId(int listId, int userId) {
        String sql = "SELECT * FROM tasks WHERE list_id = ? AND user_id = ? ORDER BY created_at DESC";
        List<Task> tasks = new ArrayList<>();
        
        Connection conn = null;
        try {
            conn = DBUtils.getConnection();
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, listId);
                pstmt.setInt(2, userId);
                
                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        Task task = new Task();
                        task.setTaskId(rs.getInt("task_id"));
                        task.setUserId(rs.getInt("user_id"));
                        task.setListId(rs.getObject("list_id", Integer.class));
                        task.setTitle(rs.getString("title"));
                        task.setDescription(rs.getString("description"));
                        task.setDueDate(rs.getObject("due_date", LocalDate.class));
                        task.setPriority(rs.getString("priority"));
                        task.setStatus(rs.getString("status"));
                        task.setDisplayOrder(rs.getInt("display_order"));
                        task.setCreatedAt(rs.getObject("created_at", LocalDateTime.class));
                        task.setUpdatedAt(rs.getObject("updated_at", LocalDateTime.class));
                        tasks.add(task);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtils.closeConnection(conn);
        }
        return tasks;
    }
    
    
    public List<Task> getAllTasksByUserId(int userId) {
        String sql = "SELECT * FROM tasks WHERE user_id = ? ORDER BY created_at DESC";
        List<Task> tasks = new ArrayList<>();
        
        Connection conn = null;
        try {
            conn = DBUtils.getConnection();
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, userId);
                
                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        Task task = new Task();
                        task.setTaskId(rs.getInt("task_id"));
                        task.setUserId(rs.getInt("user_id"));
                        task.setListId(rs.getObject("list_id", Integer.class));
                        task.setTitle(rs.getString("title"));
                        task.setDescription(rs.getString("description"));
                        task.setParentTaskId(rs.getInt("parent_task_id"));
                        task.setDueDate(rs.getObject("due_date", LocalDate.class));
                        task.setPriority(rs.getString("priority"));
                        task.setStatus(rs.getString("status"));
                        task.setDisplayOrder(rs.getInt("display_order"));
                        task.setCreatedAt(rs.getObject("created_at", LocalDateTime.class));
                        task.setUpdatedAt(rs.getObject("updated_at", LocalDateTime.class));
                        tasks.add(task);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtils.closeConnection(conn);
        }
        return tasks;
    }
    
    public List<Task> getSubtasksByParentId(int parentTaskId, int userId) {
        List<Task> subtasks = new ArrayList<>();
        String sql = "SELECT * FROM tasks WHERE parent_task_id = ? AND user_id = ? ORDER BY display_order, created_at";
        
        Connection conn = null;
        try {
            conn = DBUtils.getConnection();
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, parentTaskId);
                pstmt.setInt(2, userId);
                
                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        Task task = new Task();
                        task.setTaskId(rs.getInt("task_id"));
                        task.setUserId(rs.getInt("user_id"));
                        task.setListId(rs.getObject("list_id", Integer.class));
                        task.setTagId(rs.getObject("tag_id", Integer.class));
                        task.setTitle(rs.getString("title"));
                        task.setDescription(rs.getString("description"));
                        task.setDueDate(rs.getObject("due_date", LocalDate.class));
                        task.setPriority(rs.getString("priority"));
                        task.setStatus(rs.getString("status"));
                        task.setParentTaskId(rs.getObject("parent_task_id", Integer.class));
                        task.setDisplayOrder(rs.getInt("display_order"));
                        task.setCreatedAt(rs.getObject("created_at", LocalDateTime.class));
                        task.setUpdatedAt(rs.getObject("updated_at", LocalDateTime.class));
                        task.setCompletedAt(rs.getObject("completed_at", LocalDateTime.class));
                        
                        subtasks.add(task);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtils.closeConnection(conn);
        }
        return subtasks;
    }
    
    public Task addSubtask(Task subtask) {
        String sql = "INSERT INTO tasks (user_id, list_id, title, description, due_date, priority, status, parent_task_id, display_order, created_at, updated_at) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        Connection conn = null;
        try {
            conn = DBUtils.getConnection();
            try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setInt(1, subtask.getUserId());
                pstmt.setObject(2, subtask.getListId());
                pstmt.setString(3, subtask.getTitle());
                pstmt.setString(4, subtask.getDescription());
                pstmt.setObject(5, subtask.getDueDate());
                pstmt.setString(6, subtask.getPriority());
                pstmt.setString(7, subtask.getStatus());
                pstmt.setObject(8, subtask.getParentTaskId());
                pstmt.setInt(9, subtask.getDisplayOrder());
                pstmt.setObject(10, subtask.getCreatedAt());
                pstmt.setObject(11, subtask.getUpdatedAt());

                int affectedRows = pstmt.executeUpdate();
                if (affectedRows == 0) {
                    return null;
                }

                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        subtask.setTaskId(generatedKeys.getInt(1));
                    } else {
                        return null;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            DBUtils.closeConnection(conn);
        }
        return subtask;
    }
  
    public List<Task> getParentTasksByListId(int listId, int userId) {
        String sql = "SELECT * FROM tasks WHERE list_id = ? AND user_id = ? AND parent_task_id IS NULL ORDER BY created_at DESC";
        List<Task> tasks = new ArrayList<>();
        
        Connection conn = null;
        try {
            conn = DBUtils.getConnection();
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, listId);
                pstmt.setInt(2, userId);
                
                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        Task task = new Task();
                        task.setTaskId(rs.getInt("task_id"));
                        task.setUserId(rs.getInt("user_id"));
                        task.setListId(rs.getObject("list_id", Integer.class));
                        task.setTagId(rs.getObject("tag_id", Integer.class));
                        task.setTitle(rs.getString("title"));
                        task.setDescription(rs.getString("description"));
                        task.setDueDate(rs.getObject("due_date", LocalDate.class));
                        task.setPriority(rs.getString("priority"));
                        task.setStatus(rs.getString("status"));
                        task.setParentTaskId(rs.getObject("parent_task_id", Integer.class));
                        task.setDisplayOrder(rs.getInt("display_order"));
                        task.setCreatedAt(rs.getObject("created_at", LocalDateTime.class));
                        task.setUpdatedAt(rs.getObject("updated_at", LocalDateTime.class));
                        task.setCompletedAt(rs.getObject("completed_at", LocalDateTime.class));
                        
                        tasks.add(task);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtils.closeConnection(conn);
        }
        return tasks;
    }
    
    public List<Task> getTaskByDueDate(LocalDate dueDate) {
    	String sql = "SELECT * FROM tasks WHERE due_date = ?";
    	List<Task> tasks = new ArrayList<Task>();
    	
    	Connection conn = null;
    	try {
    		conn = DBUtils.getConnection();
    		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
    			pstmt.setObject(1, dueDate);
    			try (ResultSet rs = pstmt.executeQuery()) {
    				while (rs.next()) {
    					Task task = new Task();
                        task.setTaskId(rs.getInt("task_id"));
                        task.setUserId(rs.getInt("user_id"));
                        task.setListId(rs.getObject("list_id", Integer.class));
                        task.setTagId(rs.getObject("tag_id", Integer.class));
                        task.setTitle(rs.getString("title"));
                        task.setDescription(rs.getString("description"));
                        task.setDueDate(rs.getObject("due_date", LocalDate.class));
                        task.setPriority(rs.getString("priority"));
                        task.setStatus(rs.getString("status"));
                        task.setParentTaskId(rs.getObject("parent_task_id", Integer.class));
                        task.setDisplayOrder(rs.getInt("display_order"));
                        task.setCreatedAt(rs.getObject("created_at", LocalDateTime.class));
                        task.setUpdatedAt(rs.getObject("updated_at", LocalDateTime.class));
                        task.setCompletedAt(rs.getObject("completed_at", LocalDateTime.class));
                        
                        tasks.add(task);
    				}
                }
            }
    	} catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtils.closeConnection(conn);
        }
        return tasks;
    }
    
    public List<Task> getParentTasksDueToday(int userId, LocalDate today) {
        String sql = "SELECT * FROM tasks WHERE user_id = ? AND due_date = ? AND parent_task_id IS NULL ORDER BY created_at DESC";
        List<Task> tasks = new ArrayList<>();
        
        System.out.println("=== DEBUG TaskDao.getParentTasksDueToday ===");
        System.out.println("SQL: " + sql);
        System.out.println("userId: " + userId + ", today: " + today);
        
        Connection conn = null;
        try {
            conn = DBUtils.getConnection();
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, userId);
                pstmt.setObject(2, today);
                
                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        Task task = new Task();
                        task.setTaskId(rs.getInt("task_id"));
                        task.setUserId(rs.getInt("user_id"));
                        task.setListId(rs.getObject("list_id", Integer.class));
                        task.setTagId(rs.getObject("tag_id", Integer.class));
                        task.setTitle(rs.getString("title"));
                        task.setDescription(rs.getString("description"));
                        task.setDueDate(rs.getObject("due_date", LocalDate.class));
                        task.setPriority(rs.getString("priority"));
                        task.setStatus(rs.getString("status"));
                        task.setParentTaskId(rs.getObject("parent_task_id", Integer.class));
                        task.setDisplayOrder(rs.getInt("display_order"));
                        task.setCreatedAt(rs.getObject("created_at", LocalDateTime.class));
                        task.setUpdatedAt(rs.getObject("updated_at", LocalDateTime.class));
                        task.setCompletedAt(rs.getObject("completed_at", LocalDateTime.class));
                        
                        System.out.println("Found task: ID=" + task.getTaskId() + 
                                         ", Title=" + task.getTitle() + 
                                         ", ParentID=" + task.getParentTaskId() + 
                                         ", DueDate=" + task.getDueDate());
                        
                        tasks.add(task);
                    }
                }
            }
            System.out.println("Total parent tasks found: " + tasks.size());
        } catch (SQLException e) {
            System.out.println("Error in getParentTasksDueToday: " + e.getMessage());
            e.printStackTrace();
        } finally {
            DBUtils.closeConnection(conn);
        }
        return tasks;
    }
}
