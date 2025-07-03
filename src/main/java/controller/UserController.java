package controller;

import java.io.IOException;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.UserDao;
import model.User;
import utils.UserUtils;

@WebServlet(name = "UserController", urlPatterns = {
    "/register",
    "/login",
    "/profile",
    "/logout"
})
public class UserController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private static final int SESSION_TIMEOUT = 30 * 60; // 30 phút
    
    private UserDao userDao;
    
    @Override
    public void init() throws ServletException {
        super.init();
        userDao = new UserDao(); 
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        
        String path = request.getServletPath();
        
        switch (path) {
            case "/register":
                handleRegister(request, response);
                break;
            case "/login":
                handleLogin(request, response);
                break;
            case "/logout":
                handleLogout(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String path = request.getServletPath();
        switch (path) {
            case "/login":
                request.getRequestDispatcher("/login.jsp").forward(request, response);
                break;
            case "/register":
                request.getRequestDispatcher("/register.jsp").forward(request, response);
                break;
            case "/profile":
                request.getRequestDispatcher("/profile.jsp").forward(request, response);
                break;
            case "/logout":
                handleLogout(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
    
    private void handleRegister(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String dobParam = request.getParameter("dob");
        
        try {
            if (userDao.existingByEmail(email)) {
                request.setAttribute("error", "Email đã tồn tại");
                request.getRequestDispatcher("/register.jsp").forward(request, response);
                return;
            }
            
            if (!password.equals(confirmPassword)) {
                request.setAttribute("error", "Mật khẩu và xác nhận mật khẩu không khớp.");
                request.getRequestDispatcher("/register.jsp").forward(request, response);
                return;
            }
            
            LocalDate dob = null;
            if (dobParam != null && !dobParam.isBlank()) {
                dob = LocalDate.parse(dobParam);
            }
            
            String username = UserUtils.BuildUsername(firstName, lastName);
            
            User user = new User(firstName, lastName, username, email, password, dob);
            
            if (userDao.registerUser(user)) {
                // Redirect to controller instead of JSP
                response.sendRedirect(request.getContextPath() + "/login?registerSuccess=true");
            } else {
                request.setAttribute("error", "Đăng kí thất bại");
                request.getRequestDispatcher("/register.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi hệ thống: " + e.getMessage());
            request.getRequestDispatcher("/register.jsp").forward(request, response);
        }
    }
    
    private void handleLogin(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        try {
            User authUser = userDao.checkLogin(email, password);
            
            if (authUser != null) {
                authUser.setPassword(null);
                
                HttpSession session = request.getSession();
                session.setAttribute("user", authUser);
                
                session.setAttribute("userId", authUser.getId()); 
                session.setMaxInactiveInterval(SESSION_TIMEOUT);
                System.out.println("Login successful, userId set: " + authUser.getId());
                response.sendRedirect(request.getContextPath() + "/home");
            } else {
                request.setAttribute("error", "Tài khoản hoặc mật khẩu không chính xác");
                request.getRequestDispatcher("/login.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi hệ thống: " + e.getMessage());
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }
    
    private void handleLogout(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        // Redirect to controller instead of JSP
        response.sendRedirect(request.getContextPath() + "/login");
    }
}