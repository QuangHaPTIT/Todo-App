package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.TaskListDao;
import model.Task;
import model.TaskList;
import utils.DBUtils;

@WebServlet("/taskLists")
public class TaskListController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private TaskListDao taskListDao;

    @Override
    public void init() throws ServletException {
        taskListDao = new TaskListDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {
            response.sendRedirect("login");
            return;
        }

        try {
            List<TaskList> taskLists = taskListDao.getTaskListsByUserId(userId);
            request.setAttribute("taskLists", taskLists);

            String listIdParam = request.getParameter("listId");
            if (listIdParam != null) {
                int listId = Integer.parseInt(listIdParam);
                for (TaskList taskList : taskLists) {
                    if (taskList.getListId() == listId) {
                        request.setAttribute("selectedTaskList", taskList);
                        break;
                    }
                }
            }

            response.sendRedirect("home" + (listIdParam != null ? "?listId=" + listIdParam : ""));
        } catch (SQLException e) {
            request.setAttribute("error", "Lỗi khi truy vấn cơ sở dữ liệu: " + e.getMessage());
            response.sendRedirect("home");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {
            response.sendRedirect("login");
            return;
        }

        String action = request.getParameter("action");
        try {
            if ("add".equals(action)) {
                String listName = request.getParameter("listName");
                String colorCode = request.getParameter("colorCode");

                TaskList taskList = new TaskList();
                taskList.setUserId(userId);
                taskList.setListName(listName);
                taskList.setColorCode(colorCode);
                taskList.setIcon(""); 
                taskList.setDisplayOrder(0); 

                TaskList addedTaskList = taskListDao.addTaskList(taskList);
                if (addedTaskList != null) {
                    request.setAttribute("message", "Thêm danh sách thành công!");
                } else {
                    request.setAttribute("message", "Thêm danh sách thất bại!");
                }
            } else if ("delete".equals(action)) {
                int listId = Integer.parseInt(request.getParameter("listId"));
                boolean deleted = taskListDao.deleteTaskList(listId, userId);
                if (deleted) {
                    request.setAttribute("message", "Xóa danh sách thành công!");
                } else {
                    request.setAttribute("message", "Xóa danh sách thất bại!");
                }
            }

            // Redirect to home instead of forwarding to JSP
            response.sendRedirect("home");
        } catch (SQLException e) {
            request.setAttribute("error", "Lỗi khi thực hiện thao tác: " + e.getMessage());
            response.sendRedirect("home");
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Lỗi định dạng dữ liệu: " + e.getMessage());
            response.sendRedirect("home");
        }
    }
    
    public List<Task> getTasksByListId(Integer listId, Integer userId) {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT * FROM tasks WHERE list_id = ? AND user_id = ?";

        Connection conn = null;
        try {
            conn = DBUtils.getConnection();
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setObject(1, listId);
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

    public TaskList getTaskListById(Integer listId) {
        TaskList taskList = null;
        String sql = "SELECT * FROM task_lists WHERE list_id = ?";

        Connection conn = null;
        try {
            conn = DBUtils.getConnection();
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, listId);

                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        taskList = new TaskList();
                        taskList.setListId(rs.getInt("list_id"));
                        taskList.setListName(rs.getString("list_name"));
                        taskList.setColorCode(rs.getString("color_code"));
                        taskList.setIcon(rs.getString("icon"));
                        taskList.setTaskCount(0);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtils.closeConnection(conn);
        }
        return taskList;
    }
    
    public List<Task> getAllTasksByUserId(Integer userId) {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT * FROM tasks WHERE user_id = ? ORDER BY created_at DESC";

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
}