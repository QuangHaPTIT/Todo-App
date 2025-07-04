package schedule;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import dao.MailDao;
import dao.TaskDao;
import dao.UserDao;
import model.Task;
import model.User;

public class EmailSchedule {
    private MailDao mailDao;
    private TaskDao taskDao;
    private UserDao userDao;
    private ScheduledExecutorService scheduler;
    
    private static final LocalTime DAILY_REMINDER_TIME = LocalTime.of(12, 0);
    
    public EmailSchedule() {
        this.mailDao = new MailDao();
        this.taskDao = new TaskDao();
        this.userDao = new UserDao();
        this.scheduler = Executors.newScheduledThreadPool(2);
    }
    
    public void startEmailScheduler() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nextRun = calculateNextRunTime(now);
        
        long delaySeconds = ChronoUnit.SECONDS.between(now, nextRun);
        
        // Log thông tin schedule
        System.out.println("🚀 Server started at: " + now.format(DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy")));
        System.out.println("⏰ Next email check scheduled for: " + nextRun.format(DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy")));
        System.out.println("⏱️ Delay: " + formatDelay(delaySeconds));
        
        scheduler.scheduleAtFixedRate(
            this::sendDailyTaskReminders,
            delaySeconds,
            24 * 60 * 60,
            TimeUnit.SECONDS
        );
        
        System.out.println("📧 Email Scheduler started successfully!");
    }
    
    private String formatDelay(long seconds) {
        if (seconds < 60) {
            return seconds + " giây";
        } else if (seconds < 3600) {
            long minutes = seconds / 60;
            long remainingSeconds = seconds % 60;
            return minutes + " phút " + remainingSeconds + " giây";
        } else {
            long hours = seconds / 3600;
            long remainingMinutes = (seconds % 3600) / 60;
            return hours + " giờ " + remainingMinutes + " phút";
        }
    }
    
    private LocalDateTime calculateNextRunTime(LocalDateTime now) {
        LocalDateTime todayAt12 = now.toLocalDate().atTime(DAILY_REMINDER_TIME);
        
        if (now.isBefore(todayAt12)) {
            return todayAt12;
        } else {
            return todayAt12.plusDays(1);
        }
    }
    
    public void sendDailyTaskReminders() {
        LocalDate today = LocalDate.now();
        LocalDateTime now = LocalDateTime.now();
        
        System.out.println("🔔 Daily reminder check started at " + 
                          now.format(DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy")));
        
        try {
            // ✅ Handle SQLException from DAO
            List<Task> tasksDueToday = taskDao.getTaskByDueDate(today);
            
            System.out.println("📝 Found " + tasksDueToday.size() + " tasks due today (" + 
                             today.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + ")");
            
            if (tasksDueToday.isEmpty()) {
                System.out.println("✅ No tasks due today - no emails to send");
                scheduleNextRun();
                return;
            }
            
            int emailsSent = 0;
            int tasksChecked = 0;
            
            for (Task task : tasksDueToday) {
                tasksChecked++;
                System.out.println("📋 Checking task " + tasksChecked + "/" + tasksDueToday.size() + 
                                 ": '" + task.getTitle() + "' (Status: " + task.getStatus() + ")");
                
                if (isTaskIncomplete(task)) {
                    if (sendReminderEmail(task)) {
                        emailsSent++;
                    }
                    
                    try {
                        Thread.sleep(2000); // 2 giây
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                } else {
                    System.out.println("⏭️ Task already completed - skipping email");
                }
            }
            
            System.out.println("✅ Daily reminder completed: " + emailsSent + "/" + tasksChecked + " emails sent");
            scheduleNextRun();
            
        } catch (Exception e) {
            System.err.println("❌ Error in daily reminder: " + e.getMessage());
            e.printStackTrace();
            scheduleNextRun();
        }
    }
    
    private boolean sendReminderEmail(Task task) {
        try {
            User user = userDao.getUserById(task.getUserId());
            if (user == null) {
                System.err.println("⚠️ User not found for task: " + task.getTitle());
                return false;
            }
            
            if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
                System.err.println("⚠️ Email empty for user: " + user.getUsername());
                return false;
            }
            
            String formattedDueDate = task.getDueDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            
            System.out.println("📧 Sending reminder email...");
            System.out.println("   → Task: " + task.getTitle());
            System.out.println("   → User: " + user.getUsername());
            System.out.println("   → Email: " + user.getEmail());
            System.out.println("   → Due: " + formattedDueDate);
            
            boolean sent = mailDao.sendTaskReminder(
                user.getEmail(),
                user.getUsername(),
                task.getTitle(),
                task.getDescription() != null ? task.getDescription() : "",
                formattedDueDate + " (HÔM NAY - HẾT HẠN!)",
                task.getPriority() != null ? task.getPriority() : "medium"
            );
            
            if (sent) {
                System.out.println("✅ Email sent successfully!");
            } else {
                System.err.println("❌ Failed to send email");
            }
            
            return sent;
            
        } catch (Exception e) {
            System.err.println("❌ Error sending reminder for task '" + task.getTitle() + "': " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    private void scheduleNextRun() {
        LocalDateTime nextRun = LocalDateTime.now().plusDays(1).toLocalDate().atTime(DAILY_REMINDER_TIME);
        System.out.println("⏰ Next email check scheduled for: " + 
                          nextRun.format(DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy")));
    }
    
    private boolean isTaskIncomplete(Task task) {
        String status = task.getStatus();
        if (status == null) {
            return true;
        }
        
        String lowerStatus = status.toLowerCase().trim();
        return !lowerStatus.equals("completed") && 
               !lowerStatus.equals("done") && 
               !lowerStatus.equals("finished") &&
               !lowerStatus.equals("complete");
    }
    
    // ✅ Thêm method để dừng scheduler
    public void stopScheduler() {
        if (scheduler != null && !scheduler.isShutdown()) {
            scheduler.shutdown();
            try {
                if (!scheduler.awaitTermination(5, TimeUnit.SECONDS)) {
                    scheduler.shutdownNow();
                }
            } catch (InterruptedException e) {
                scheduler.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("🛑 Email Scheduler stopped");
    }
    
    // ✅ Method để check status
    public boolean isRunning() {
        return scheduler != null && !scheduler.isShutdown();
    }
}