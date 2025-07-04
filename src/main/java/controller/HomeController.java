package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.TaskListDao;
import dao.TagDao;
import dao.TaskDao;
import model.Task;
import model.TaskList;
import model.Tag;

@WebServlet("/home")
public class HomeController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private TaskListDao taskListDao;
    private TaskDao taskDao;
    private TagDao tagDao;
    @Override
    public void init() throws ServletException {
        taskListDao = new TaskListDao();
        taskDao = new TaskDao();
        tagDao = new TagDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Fix bug vòng lặp vô hạn
        if (request.getAttribute("processed") != null) {
            request.getRequestDispatcher("/home.jsp").forward(request, response);
            return;
        }
        request.setAttribute("processed", true);

        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {
            response.sendRedirect("login");
            return;
        }

        try {
            // Lấy danh sách task lists
            List<TaskList> taskLists = taskListDao.getTaskListsByUserId(userId);
            request.setAttribute("taskLists", taskLists);

            List<Tag> tags = tagDao.getAllTagByUserId(userId);
            
            request.setAttribute("tags", tags);

            String listIdParam = request.getParameter("listId");
            if (listIdParam != null) {
                int listId = Integer.parseInt(listIdParam);
                
                // Tìm task list được chọn
                TaskList selectedTaskList = null;
                for (TaskList taskList : taskLists) {
                    if (taskList.getListId() == listId) {
                        selectedTaskList = taskList;
                        break;
                    }
                }
                request.setAttribute("selectedTaskList", selectedTaskList);
                
                // Lấy danh sách parent tasks theo listId (chỉ lấy parent tasks)
                List<Task> parentTasks = taskDao.getParentTasksByListId(listId, userId);
                
                // Tạo map để lưu subtasks cho mỗi parent task
                Map<Integer, List<Task>> subtasksMap = new HashMap<>();
                
                for (Task task : parentTasks) {
                    // Lấy subtasks cho task này
                    List<Task> subtasks = taskDao.getSubtasksByParentId(task.getTaskId(), userId);
                    subtasksMap.put(task.getTaskId(), subtasks);
                }
                
                request.setAttribute("tasks", parentTasks);
                request.setAttribute("subtasksMap", subtasksMap);
                request.setAttribute("taskCount", parentTasks.size());
            } else {
                // Xử lí khi mới vào Home chưa có listId
            	
                List<Task> allTasks = taskDao.getAllTasksByUserId(userId);
                System.out.println("taskSize: " + allTasks.size());
                System.out.println("Vào đây");
                // Tạo map để lưu subtasks cho mỗi parent task
                Map<Integer, List<Task>> subtasksMap = new HashMap<>();
                List<Task> parentTasks = new ArrayList<>();
                for (Task task : allTasks) {
                	System.out.println(task);
                    if ((task.getParentTaskId() == null || task.getParentTaskId() == 0) 
                    		&& task.getDueDate().isEqual(LocalDate.now())) {
                    	System.out.println(task);
                    	parentTasks.add(task);
                        // Chỉ lấy subtasks cho parent tasks
                        List<Task> subtasks = taskDao.getSubtasksByParentId(task.getTaskId(), userId);
                        subtasksMap.put(task.getTaskId(), subtasks);
                    }
                }
                
                request.setAttribute("tasks", parentTasks);
                request.setAttribute("subtasksMap", subtasksMap);
                request.setAttribute("taskCount", parentTasks.size());
            }

            request.getRequestDispatcher("/home.jsp").forward(request, response);
        } catch (SQLException e) {
            request.setAttribute("error", "Lỗi khi truy vấn cơ sở dữ liệu: " + e.getMessage());
            request.getRequestDispatcher("/home.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Lỗi định dạng dữ liệu: " + e.getMessage());
            request.getRequestDispatcher("/home.jsp").forward(request, response);
        }
    }
}