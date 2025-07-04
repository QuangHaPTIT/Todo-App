package dao;

import java.util.Properties;

import utils.ConfigLoader;
import javax.mail.*;
import javax.mail.internet.*;

public class MailDao {
	public boolean sendTaskReminder(String toEmail, 
			String username, 
			String taskTitle, 
			String taskDescription, 
			String dueDate, 
			String priority) {
		try {
			Properties props = new Properties();
			props.put("mail.smtp.host", ConfigLoader.getProperty("mail.smtp.host"));
            props.put("mail.smtp.port", ConfigLoader.getProperty("mail.smtp.port"));
            props.put("mail.smtp.auth", ConfigLoader.getProperty("mail.smtp.auth"));
            props.put("mail.smtp.starttls.enable", ConfigLoader.getProperty("mail.smtp.starttls.enable"));
            
            Session session = Session.getInstance(props, new Authenticator() {
            	@Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(
                        ConfigLoader.getProperty("mail.smtp.username"),
                        ConfigLoader.getProperty("mail.smtp.password")
                    );
                }
			});
            
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(ConfigLoader.getProperty("mail.smtp.username")));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("🔔 Nhắc nhở: Task sắp hết hạn - " + taskTitle);
            
            String emailContent = buildEmailTemplate(username, taskTitle, taskDescription, dueDate, priority);
            message.setContent(emailContent, "text/html; charset=utf-8");
            
            Transport.send(message);
            System.out.println("✅ Email sent successfully to: " + toEmail);
            return true;
		 } catch (MessagingException e) {
	            System.err.println("❌ Failed to send email: " + e.getMessage());
	            e.printStackTrace();
	            return false;
	     }
	}
	
	private String buildEmailTemplate(String username, String taskTitle, String taskDescription, String dueDate, String priority) {
        String priorityColor = getPriorityColor(priority);
        String priorityIcon = getPriorityIcon(priority);
        
        return "<!DOCTYPE html>" +
               "<html lang='vi'>" +
               "<head>" +
               "<meta charset='UTF-8'>" +
               "<meta name='viewport' content='width=device-width, initial-scale=1.0'>" +
               "<title>Nhắc nhở Task</title>" +
               "<style>" +
               "body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; margin: 0; padding: 0; background-color: #f8f9fa; }" +
               ".container { max-width: 600px; margin: 0 auto; background-color: white; }" +
               ".header { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); color: white; padding: 30px; text-align: center; }" +
               ".header h1 { margin: 0; font-size: 28px; font-weight: 300; }" +
               ".header .icon { font-size: 48px; margin-bottom: 10px; }" +
               ".content { padding: 40px; }" +
               ".greeting { font-size: 18px; color: #333; margin-bottom: 20px; }" +
               ".task-card { background: #f8f9fa; border-radius: 12px; padding: 25px; margin: 20px 0; border-left: 5px solid " + priorityColor + "; }" +
               ".task-title { font-size: 24px; font-weight: 600; color: #2c3e50; margin-bottom: 15px; display: flex; align-items: center; }" +
               ".task-title .priority-icon { margin-right: 10px; font-size: 20px; }" +
               ".task-description { color: #666; margin-bottom: 20px; line-height: 1.6; }" +
               ".task-details { display: flex; justify-content: space-between; flex-wrap: wrap; }" +
               ".detail-item { background: white; padding: 15px; border-radius: 8px; margin: 5px; flex: 1; min-width: 200px; box-shadow: 0 2px 4px rgba(0,0,0,0.1); }" +
               ".detail-label { font-size: 12px; color: #666; text-transform: uppercase; margin-bottom: 5px; }" +
               ".detail-value { font-size: 16px; font-weight: 600; color: #2c3e50; }" +
               ".due-date { color: #e74c3c; }" +
               ".priority { color: " + priorityColor + "; }" +
               ".cta-section { text-align: center; margin: 30px 0; }" +
               ".cta-button { display: inline-block; background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); color: white; padding: 15px 30px; text-decoration: none; border-radius: 25px; font-weight: 600; transition: transform 0.3s ease; }" +
               ".cta-button:hover { transform: translateY(-2px); }" +
               ".footer { background: #2c3e50; color: white; padding: 20px; text-align: center; }" +
               ".footer p { margin: 5px 0; }" +
               ".social-links { margin-top: 15px; }" +
               ".social-links a { color: #ecf0f1; text-decoration: none; margin: 0 10px; }" +
               "@media (max-width: 600px) { .task-details { flex-direction: column; } .detail-item { margin: 5px 0; } }" +
               "</style>" +
               "</head>" +
               "<body>" +
               "<div class='container'>" +
               "<div class='header'>" +
               "<div class='icon'>⏰</div>" +
               "<h1>Nhắc nhở Task</h1>" +
               "<p>Todo App - Quản lý công việc hiệu quả</p>" +
               "</div>" +
               "<div class='content'>" +
               "<div class='greeting'>Xin chào <strong>" + username + "</strong>,</div>" +
               "<p>Bạn có một task quan trọng sắp hết hạn. Đừng quên hoàn thành nó nhé!</p>" +
               "<div class='task-card'>" +
               "<div class='task-title'>" +
               "<span class='priority-icon'>" + priorityIcon + "</span>" +
               taskTitle +
               "</div>" +
               (taskDescription != null && !taskDescription.isEmpty() ? 
                "<div class='task-description'>" + taskDescription + "</div>" : "") +
               "<div class='task-details'>" +
               "<div class='detail-item'>" +
               "<div class='detail-label'>Ngày hết hạn</div>" +
               "<div class='detail-value due-date'>📅 " + dueDate + "</div>" +
               "</div>" +
               "<div class='detail-item'>" +
               "<div class='detail-label'>Độ ưu tiên</div>" +
               "<div class='detail-value priority'>" + priorityIcon + " " + getPriorityText(priority) + "</div>" +
               "</div>" +
               "</div>" +
               "</div>" +
               "<div class='cta-section'>" +
               "<a href='#' class='cta-button'>Mở Todo App</a>" +
               "</div>" +
               "<p style='color: #666; font-size: 14px; line-height: 1.6;'>" +
               "💡 <strong>Mẹo:</strong> Hãy chia task lớn thành các task nhỏ hơn để dễ quản lý và hoàn thành." +
               "</p>" +
               "</div>" +
               "<div class='footer'>" +
               "<p><strong>Todo App</strong> - Ứng dụng quản lý công việc</p>" +
               "<p>📧 Liên hệ: " + ConfigLoader.getProperty("mail.smtp.username") + "</p>" +
               "<div class='social-links'>" +
               "<a href='#'>Facebook</a> | <a href='#'>Twitter</a> | <a href='#'>LinkedIn</a>" +
               "</div>" +
               "</div>" +
               "</div>" +
               "</body>" +
               "</html>";
    }
	
	private String getPriorityColor(String priority) {
        if (priority == null) return "#6c757d";
        
        switch (priority.toLowerCase()) {
            case "high": return "#e74c3c";
            case "medium": return "#f39c12";
            case "low": return "#27ae60";
            default: return "#6c757d";
        }
    }
    
    private String getPriorityIcon(String priority) {
        if (priority == null) return "📄";
        
        switch (priority.toLowerCase()) {
            case "high": return "🔴";
            case "medium": return "🟡";
            case "low": return "🟢";
            default: return "📄";
        }
    }
    
    private String getPriorityText(String priority) {
        if (priority == null) return "Không xác định";
        
        switch (priority.toLowerCase()) {
            case "high": return "Cao";
            case "medium": return "Trung bình";
            case "low": return "Thấp";
            default: return "Không xác định";
        }
    }
}
