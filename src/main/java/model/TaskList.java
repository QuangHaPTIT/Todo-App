package model;

import java.time.LocalDateTime;

public class TaskList {
    private int listId;
    private int userId;
    private String listName;
    private String colorCode;
    private String icon;
    private int displayOrder;
    private int taskCount;
    private LocalDateTime createdAt;

    public TaskList() {
    }
    
    public TaskList(int listId, int userId, String listName, String colorCode, 
            String icon, int displayOrder, LocalDateTime createdAt) {
		 this.listId = listId;
		 this.userId = userId;
		 this.listName = listName;
		 this.colorCode = colorCode;
		 this.icon = icon;
		 this.displayOrder = displayOrder;
		 this.createdAt = createdAt;
		 this.taskCount = 0;
    }

    public TaskList(int userId, String listName, String colorCode, String icon, int displayOrder, LocalDateTime createdAt) {
        this.userId = userId;
        this.listName = listName;
        this.colorCode = colorCode;
        this.icon = icon;
        this.displayOrder = displayOrder;
        this.createdAt = createdAt;
        this.taskCount = 0;
    }

    public int getListId() {
        return listId;
    }

    public void setListId(int listId) {
        this.listId = listId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
    
    public int getTaskCount() {
    	return this.taskCount;
    }
    
    public void setTaskCount(int taskCount) {
    	this.taskCount = taskCount;
    }

    public int getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(int displayOrder) {
        this.displayOrder = displayOrder;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}