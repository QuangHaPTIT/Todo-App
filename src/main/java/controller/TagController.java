package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.TagDao;
import model.Tag;
import model.User;

/**
 * Servlet implementation class TagController
 */
@WebServlet("/tag")
public class TagController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TagDao tagDao;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TagController() {
        super();
        tagDao = new TagDao();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Redirect to home if accessed directly
		response.sendRedirect(request.getContextPath() + "/home");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
		
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		
		if (user == null) {
			response.sendRedirect(request.getContextPath() + "/login.jsp");
			return;
		}
		
		String action = request.getParameter("action");
		
		if ("add".equals(action)) {
			addTag(request, response, user);
		} else if ("update".equals(action)) {
			updateTag(request, response, user);
		} else if ("delete".equals(action)) {
			deleteTag(request, response, user);
		} else {
			response.sendRedirect(request.getContextPath() + "/home");
		}
	}
	
	private void addTag(HttpServletRequest request, HttpServletResponse response, User user) 
			throws ServletException, IOException {
		
		response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
		
		String tagName = request.getParameter("tagName");
		String colorCode = request.getParameter("colorCode");
		String textColor = request.getParameter("textColor");
		
		// Validate input
		if (tagName == null || tagName.trim().isEmpty()) {
			request.getSession().setAttribute("error", "Tên thẻ không được để trống");
			response.sendRedirect(request.getContextPath() + "/home");
			return;
		}
		
		// Set default values if not provided
		if (colorCode == null || colorCode.trim().isEmpty()) {
			colorCode = "#007bff"; // Default blue color
		}
		if (textColor == null || textColor.trim().isEmpty()) {
			textColor = "#ffffff"; // Default white text
		}
		
		try {
			Tag tag = new Tag();
			tag.setUserId(user.getId());
			tag.setTagName(tagName.trim());
			tag.setColorCode(colorCode);
			tag.setTextColor(textColor);
			
			Tag createdTag = tagDao.addTag(tag);
			
			if (createdTag != null) {
				request.getSession().setAttribute("message", "Thêm thẻ thành công!");
			} else {
				request.getSession().setAttribute("error", "Không thể thêm thẻ. Vui lòng thử lại!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			request.getSession().setAttribute("error", "Lỗi hệ thống: " + e.getMessage());
		}
		
		response.sendRedirect(request.getContextPath() + "/home");
	}
	
	private void updateTag(HttpServletRequest request, HttpServletResponse response, User user) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        
		String tagIdStr = request.getParameter("tagId");
		String tagName = request.getParameter("tagName");
		String colorCode = request.getParameter("colorCode");
		String textColor = request.getParameter("textColor");
		
		// Validate input
		if (tagIdStr == null || tagName == null || tagName.trim().isEmpty()) {
			request.getSession().setAttribute("error", "Thông tin thẻ không hợp lệ");
			response.sendRedirect(request.getContextPath() + "/home");
			return;
		}
		
		try {
			int tagId = Integer.parseInt(tagIdStr);
			
			// Set default values if not provided
			if (colorCode == null || colorCode.trim().isEmpty()) {
				colorCode = "#007bff";
			}
			if (textColor == null || textColor.trim().isEmpty()) {
				textColor = "#ffffff";
			}
			
			Tag tag = new Tag();
			tag.setTagId(tagId);
			tag.setUserId(user.getId());
			tag.setTagName(tagName.trim());
			tag.setColorCode(colorCode);
			tag.setTextColor(textColor);
			
			Tag updatedTag = tagDao.updateTag(tag);
			
			if (updatedTag != null) {
				request.getSession().setAttribute("message", "Cập nhật thẻ thành công!");
			} else {
				request.getSession().setAttribute("error", "Không thể cập nhật thẻ. Vui lòng thử lại!");
			}
		} catch (NumberFormatException e) {
			request.getSession().setAttribute("error", "ID thẻ không hợp lệ");
		} catch (Exception e) {
			e.printStackTrace();
			request.getSession().setAttribute("error", "Lỗi hệ thống: " + e.getMessage());
		}
		
		response.sendRedirect(request.getContextPath() + "/home");
	}
	
	private void deleteTag(HttpServletRequest request, HttpServletResponse response, User user) throws ServletException, IOException {
		String tagIdStr = request.getParameter("tagId");
		
		if (tagIdStr == null) {
			request.getSession().setAttribute("error", "ID thẻ không hợp lệ");
			response.sendRedirect(request.getContextPath() + "/home");
			return;
		}
		
		try {
			int tagId = Integer.parseInt(tagIdStr);
			
			boolean deleted = tagDao.deleteTag(tagId, user.getId());
			
			if (deleted) {
				request.getSession().setAttribute("message", "Xóa thẻ thành công!");
			} else {
				request.getSession().setAttribute("error", "Không thể xóa thẻ. Vui lòng thử lại!");
			}
		} catch (NumberFormatException e) {
			request.getSession().setAttribute("error", "ID thẻ không hợp lệ");
		} catch (Exception e) {
			e.printStackTrace();
			request.getSession().setAttribute("error", "Lỗi hệ thống: " + e.getMessage());
		}
		
		response.sendRedirect(request.getContextPath() + "/home");
	}
}
