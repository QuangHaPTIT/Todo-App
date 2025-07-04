//package controller;
//
//import javax.servlet.ServletContextEvent;
//import javax.servlet.ServletContextListener;
//import javax.servlet.annotation.WebListener;
//
//import schedule.EmailSchedule;
//
//@WebListener
//public class EmailScheduleTestRunner implements ServletContextListener {
//    @Override
//    public void contextInitialized(ServletContextEvent sce) {
//        System.out.println("🧪 [TestRunner] Gửi email test khi start server...");
//
//        try {
//            EmailSchedule testSchedule = new EmailSchedule();
//            testSchedule.sendDailyTaskReminders(); // Gửi ngay email test
//            System.out.println("✅ [TestRunner] Email test đã được gửi.");
//        } catch (Exception e) {
//            System.err.println("❌ [TestRunner] Lỗi khi gửi email test: " + e.getMessage());
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void contextDestroyed(ServletContextEvent sce) {
//        // Không cần làm gì khi shutdown
//    }
//}
