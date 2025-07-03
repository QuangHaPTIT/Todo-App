package controller;


import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.TaskDao;
import model.Task;





@WebServlet("/TaskController")
public class TaskController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private TaskDao taskDao;

    public TaskController() {
        super();
        taskDao = new TaskDao();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {
            response.sendRedirect("login");
            return;
        }

        if ("delete".equals(action)) {
            int taskId = Integer.parseInt(request.getParameter("taskId"));
            boolean isDeleted = taskDao.deleteTask(taskId, userId);
            if (isDeleted) {
                request.setAttribute("message", "Xóa công việc thành công!");
            } else {
                request.setAttribute("error", "Xóa công việc thất bại!");
            }
            response.sendRedirect("home?listId=" + request.getParameter("listId"));
        } else {
            response.sendRedirect("home");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        System.out.println("TaskController doPost - action: " + action); // Debug log
        
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {
            response.sendRedirect("login");
            return;
        }

        if ("add".equals(action)) {
            Integer listId = Integer.parseInt(request.getParameter("listId"));
            String taskName = request.getParameter("taskName");
            String description = request.getParameter("description");
            String dueDateStr = request.getParameter("dueDate");
            String priority = request.getParameter("priority");

            LocalDate dueDate = (dueDateStr != null && !dueDateStr.isEmpty()) ? LocalDate.parse(dueDateStr) : null;

            Task task = new Task();
            task.setUserId(userId);
            task.setListId(listId);
            task.setTitle(taskName);
            task.setDescription(description);
            task.setDueDate(dueDate);
            task.setPriority(priority);
            task.setStatus("PENDING");
            task.setDisplayOrder(0);
            task.setCreatedAt(LocalDateTime.now());
            task.setUpdatedAt(LocalDateTime.now());

            Task addedTask = taskDao.addTask(task);
            if (addedTask != null) {
                request.setAttribute("message", "Thêm công việc thành công!");
            } else {
                request.setAttribute("error", "Thêm công việc thất bại!");
            }
            response.sendRedirect("home?listId=" + listId);
        } else if ("update".equals(action)) {
            int taskId = Integer.parseInt(request.getParameter("taskId"));
            Integer listId = Integer.parseInt(request.getParameter("listId"));
            String taskName = request.getParameter("taskName");
            String description = request.getParameter("description");
            String dueDateStr = request.getParameter("dueDate");
            String priority = request.getParameter("priority");

            LocalDate dueDate = (dueDateStr != null && !dueDateStr.isEmpty()) ? LocalDate.parse(dueDateStr) : null;

            Task task = taskDao.getTaskById(taskId, userId);
            if (task != null) {
                task.setListId(listId);
                task.setTitle(taskName);
                task.setDescription(description);
                task.setDueDate(dueDate);
                task.setPriority(priority);
                task.setStatus(request.getParameter("status") != null ? request.getParameter("status") : task.getStatus());
                task.setUpdatedAt(LocalDateTime.now());

                Task updatedTask = taskDao.updateTask(task);
                if (updatedTask != null) {
                    request.setAttribute("message", "Cập nhật công việc thành công!");
                } else {
                    request.setAttribute("error", "Cập nhật công việc thất bại!");
                }
            } else {
                request.setAttribute("error", "Không tìm thấy công việc!");
            }
            response.sendRedirect("home?listId=" + listId);
        } else if ("toggleStatus".equals(action)) {
            int taskId = Integer.parseInt(request.getParameter("taskId"));
            Integer listId = Integer.parseInt(request.getParameter("listId"));
            
            Task task = taskDao.getTaskById(taskId, userId);
            if (task != null) {
                // Toggle status: COMPLETED <-> PENDING
                String newStatus = "COMPLETED".equals(task.getStatus()) ? "PENDING" : "COMPLETED";
                task.setStatus(newStatus);
                task.setUpdatedAt(LocalDateTime.now());
                
                Task updatedTask = taskDao.updateTask(task);
                if (updatedTask != null) {
                    request.setAttribute("message", "Cập nhật trạng thái thành công!");
                } else {
                    request.setAttribute("error", "Cập nhật trạng thái thất bại!");
                }
            } else {
                request.setAttribute("error", "Không tìm thấy công việc!");
            }
            response.sendRedirect("home?listId=" + listId);
        } else if ("addSubtask".equals(action)) {
            System.out.println("TaskController - Processing addSubtask"); // Debug log
            Integer parentTaskId = Integer.parseInt(request.getParameter("parentTaskId"));
            Integer listId = Integer.parseInt(request.getParameter("listId"));
            String taskName = request.getParameter("taskName");
            String description = request.getParameter("description");
            String dueDateStr = request.getParameter("dueDate");
            String priority = request.getParameter("priority");
            
            System.out.println("addSubtask params - parentTaskId: " + parentTaskId + ", listId: " + listId + ", taskName: " + taskName); // Debug log

            LocalDate dueDate = (dueDateStr != null && !dueDateStr.isEmpty()) ? LocalDate.parse(dueDateStr) : null;

            Task subtask = new Task();
            subtask.setUserId(userId);
            subtask.setListId(listId);
            subtask.setParentTaskId(parentTaskId);
            subtask.setTitle(taskName);
            subtask.setDescription(description);
            subtask.setDueDate(dueDate);
            subtask.setPriority(priority);
            subtask.setStatus("PENDING");
            subtask.setDisplayOrder(0);
            subtask.setCreatedAt(LocalDateTime.now());
            subtask.setUpdatedAt(LocalDateTime.now());

            Task addedSubtask = taskDao.addSubtask(subtask);
            if (addedSubtask != null) {
                request.setAttribute("message", "Thêm subtask thành công!");
            } else {
                request.setAttribute("error", "Thêm subtask thất bại!");
            }
            response.sendRedirect("home?listId=" + listId);
        }
    }
}