package model;

import java.time.LocalDateTime;

public class Tag {
    private int tagId;
    private int userId;
    private String tagName;
    private String colorCode;
    private String textColor;
    private LocalDateTime createdAt;

    public Tag() {
    }

    public Tag(int userId, String tagName, String colorCode, String textColor, LocalDateTime createdAt) {
        this.userId = userId;
        this.tagName = tagName;
        this.colorCode = colorCode;
        this.textColor = textColor;
        this.createdAt = createdAt;
    }

    public int getTagId() {
        return tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    public String getTextColor() {
        return textColor;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
