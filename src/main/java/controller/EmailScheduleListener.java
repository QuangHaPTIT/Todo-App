package controller;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import schedule.EmailSchedule;

@WebListener
public class EmailScheduleListener implements ServletContextListener{
	private EmailSchedule emailSchedule;
    
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("Starting Todo App with Email Scheduler...");
        
        // Khởi tạo và start EmailSchedule
        emailSchedule = new EmailSchedule();
        emailSchedule.startEmailScheduler();
        
        sce.getServletContext().setAttribute("emailSchedule", emailSchedule);
        
        System.out.println("Todo App started with Email Scheduler!");
    }
    
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if (emailSchedule != null) {
            emailSchedule.stopScheduler();
        }
        System.out.println("Todo App stopped - Email Scheduler terminated");
    }
}
