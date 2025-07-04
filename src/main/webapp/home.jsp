<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quản Lý Công Việc - Trang Chủ</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, sans-serif;
        }

        body {
            background: linear-gradient(135deg, #e8f5e8 0%, #d4edda 100%);
            min-height: 100vh;
            display: flex;
        }

        /* Sidebar */
        .sidebar {
            width: 300px;
            background: white;
            border-radius: 20px;
            margin: 20px;
            padding: 20px;
            box-shadow: 0 10px 30px rgba(0,0,0,0.1);
            height: fit-content;
        }

        .user-info {
            display: flex;
            align-items: center;
            justify-content: space-between;
            padding: 15px;
            background: linear-gradient(135deg, #4a90e2 0%, #357abd 100%);
            border-radius: 15px;
            color: white;
            margin-bottom: 20px;
        }

        .user-avatar {
            width: 40px;
            height: 40px;
            border-radius: 50%;
            background: rgba(255,255,255,0.2);
            display: flex;
            align-items: center;
            justify-content: center;
            font-weight: bold;
            font-size: 16px;
        }

        .user-details h3 {
            font-size: 16px;
            margin-bottom: 2px;
        }

        .user-details p {
            font-size: 12px;
            opacity: 0.8;
        }

        .logout-btn {
            background: rgba(255,255,255,0.2);
            border: none;
            color: white;
            padding: 8px 12px;
            border-radius: 8px;
            cursor: pointer;
            font-size: 12px;
            transition: all 0.3s ease;
        }

        .logout-btn:hover {
            background: rgba(255,255,255,0.3);
        }

        .menu-header {
            display: flex;
            align-items: center;
            justify-content: space-between;
            margin-bottom: 20px;
        }

        .menu-title {
            font-size: 24px;
            font-weight: 600;
            color: #333;
        }

        .menu-icon {
            width: 24px;
            height: 24px;
            cursor: pointer;
        }

        .search-box {
            position: relative;
            margin-bottom: 30px;
        }

        .search-input {
            width: 100%;
            padding: 12px 16px;
            border: 2px solid #f0f0f0;
            border-radius: 12px;
            font-size: 14px;
            outline: none;
            transition: all 0.3s ease;
        }

        .search-input:focus {
            border-color: #4a90e2;
        }

        .section {
            margin-bottom: 30px;
        }

        .section-title {
            font-size: 12px;
            font-weight: 600;
            color: #888;
            text-transform: uppercase;
            letter-spacing: 1px;
            margin-bottom: 15px;
        }

        .menu-item {
            display: flex;
            align-items: center;
            justify-content: space-between;
            padding: 12px 16px;
            border-radius: 12px;
            margin-bottom: 8px;
            cursor: pointer;
            transition: all 0.3s ease;
            color: #333;
            text-decoration: none;
        }

        .menu-item:hover {
            background: #f8f9fa;
        }

        .menu-item.active {
            background: #e3f2fd;
            color: #1976d2;
        }

        .menu-item-left {
            display: flex;
            align-items: center;
            flex: 1;
            gap: 12px;
        }

        .menu-item-icon {
            width: 20px;
            height: 20px;
            margin-right: 12px;
            display: flex;
            align-items: center;
            justify-content: center;
        }

        .menu-item-count {
            background: #e0e0f0;
            color: #666;
            padding: 4px 8px;
            border-radius: 12px;
            font-size: 12px;
            font-weight: 500;
        }

        .list-dot {
            width: 12px;
            height: 12px;
            border-radius: 50%;
            margin-right: 12px;
        }

        .delete-link {
            padding: 5px;
            text-decoration: none;
            color: #666;
            opacity: 0.7;
            transition: all 0.2s;
            border: none;
            background: none;
            cursor: pointer;
        }

        .delete-link:hover {
            opacity: 1;
            color: #ff0000;
        }

        .add-button {
            display: flex;
            align-items: center;
            padding: 10px 16px;
            color: #666;
            text-decoration: none;
            border-radius: 12px;
            transition: all 0.3s ease;
        }

        .add-button:hover {
            background: #f0f0f0;
        }

        .tag {
            display: inline-block;
            padding: 6px 12px;
            border-radius: 20px;
            font-size: 12px;
            margin-right: 8px;
            margin-bottom: 8px;
            cursor: pointer;
            transition: all 0.3s ease;
        }

        .tag-1 { background: #e3f2fd; color: #1976d2; }
        .tag-2 { background: #fce4ec; color: #c2185b; }

        .settings-section {
            border-top: 1px solid #f0f0f0;
            padding-top: 20px;
            margin-top: 20px;
        }

        /* Main Content */
        .main-content {
            flex: 1;
            padding: 20px;
            margin-right: 20px;
        }

        .main-header {
            background: white;
            border-radius: 20px;
            padding: 30px;
            margin-bottom: 20px;
            box-shadow: 0 10px 30px rgba(0,0,0,0.1);
        }

        .header-top {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 30px;
        }

        .today-section h1 {
            font-size: 48px;
            font-weight: 700;
            color: #333;
            margin-bottom: 5px;
        }

        .task-count {
            background: #4a90e2;
            color: white;
            width: 40px;
            height: 40px;
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 18px;
            font-weight: 600;
            margin-left: 20px;
        }

        .pro-badge {
            background: linear-gradient(135deg, #4a90e2 0%, #357abd 100%);
            color: white;
            padding: 12px 24px;
            border-radius: 20px;
            font-weight: 600;
            font-size: 18px;
        }

        .add-task-btn {
            display: flex;
            align-items: center;
            padding: 15px 20px;
            background: transparent;
            border: 2px dashed #ddd;
            border-radius: 15px;
            color: #666;
            text-decoration: none;
            transition: all 0.3s ease;
            margin-bottom: 20px;
        }

        .add-task-btn:hover {
            border-color: #4a90e2;
            color: #4a90e2;
        }

        .task-list {
            background: white;
            border-radius: 20px;
            padding: 20px;
            box-shadow: 0 10px 30px rgba(0,0,0,0.1);
        }

        .task-item {
            display: flex;
            align-items: center;
            justify-content: space-between;
            padding: 20px;
            border-radius: 15px;
            margin-bottom: 15px;
            transition: all 0.3s ease;
            cursor: pointer;
        }

        .task-item:hover {
            background: #f8f9fa;
        }

        .task-left {
            display: flex;
            align-items: center;
            flex: 1;
        }

        .task-icon {
            width: 40px;
            height: 40px;
            border-radius: 10px;
            display: flex;
            align-items: center;
            justify-content: center;
            margin-right: 15px;
            color: white;
            font-weight: 600;
        }

        .task-content h3 {
            font-size: 16px;
            color: #333;
            margin-bottom: 5px;
        }

        .task-meta {
            display: flex;
            align-items: center;
            gap: 15px;
            font-size: 12px;
            color: #666;
        }

        .task-date {
            display: flex;
            align-items: center;
        }

        .task-subtasks {
            background: #f0f0f0;
            padding: 4px 8px;
            border-radius: 12px;
        }

        .task-tag {
            display: inline-block;
            background: #e8f5e8;
            color: #2e7d32;
            padding: 2px 8px;
            margin-right: 8px;
            border-radius: 12px;
            font-size: 11px;
            font-weight: bold;
            text-transform: uppercase;
            letter-spacing: 0.5px;
        }

        .chevron {
            color: #ccc;
            font-size: 18px;
        }

        /* Task Detail Panel */
        .task-detail {
            width: 350px;
            background: white;
            border-radius: 20px;
            margin: 20px 20px 20px 0;
            padding: 30px;
            box-shadow: 0 10px 30px rgba(0,0,0,0.1);
            height: fit-content;
        }

        .task-detail-header {
            margin-bottom: 30px;
        }

        .task-detail h2 {
            font-size: 18px;
            color: #333;
            margin-bottom: 20px;
        }

        .detail-field {
            margin-bottom: 20px;
        }

        .detail-label {
            font-size: 12px;
            color: #666;
            margin-bottom: 8px;
            font-weight: 500;
        }

        .detail-value {
            font-size: 14px;
            color: #333;
        }

        .select-field {
            width: 100%;
            padding: 10px;
            border: 2px solid #f0f0f0;
            border-radius: 8px;
            background: white;
            outline: none;
        }

        .date-field {
            width: 100%;
            padding: 10px;
            border: 2px solid #f0f0f0;
            border-radius: 8px;
            outline: none;
        }

        .subtasks-section h3 {
            font-size: 16px;
            color: #333;
            margin-bottom: 15px;
        }

        .add-subtask {
            display: flex;
            align-items: center;
            padding: 12px;
            color: #666;
            text-decoration: none;
            border: 2px dashed #ddd;
            border-radius: 8px;
            margin-bottom: 20px;
            transition: all 0.3s ease;
        }

        .add-subtask:hover {
            border-color: #4a90e2;
            color: #4a90e2;
        }

        .action-buttons {
            display: flex;
            gap: 15px;
            margin-top: 30px;
        }

        .delete-btn {
            flex: 1;
            padding: 12px;
            background: #f5f5f5;
            border: none;
            border-radius: 8px;
            color: #666;
            cursor: pointer;
        }

        .save-btn {
            flex: 2;
            padding: 12px;
            background: #ffc107;
            border: none;
            border-radius: 8px;
            color: #333;
            font-weight: 600;
            cursor: pointer;
        }

        /* Icons */
        .icon {
            width: 16px;
            height: 16px;
            margin-right: 8px;
        }

        /* Hidden elements */
        .hidden {
            display: none;
        }

        /* Add task form */
        .add-task-form {
            background: white;
            border-radius: 15px;
            padding: 20px;
            margin-bottom: 20px;
            box-shadow: 0 5px 15px rgba(0,0,0,0.1);
            border: 2px solid #4a90e2;
        }

        .task-input, .task-textarea, .task-select {
            width: 100%;
            padding: 12px;
            border: 2px solid #f0f0f0;
            border-radius: 10px;
            font-size: 14px;
            outline: none;
            margin-bottom: 15px;
            box-sizing: border-box;
        }

        .task-textarea {
            resize: vertical;
            min-height: 60px;
        }

        .task-input:focus, .task-textarea:focus, .task-select:focus {
            border-color: #4a90e2;
        }

        .form-actions {
            display: flex;
            gap: 10px;
        }

        .btn-primary {
            background: #4a90e2;
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 8px;
            cursor: pointer;
            font-weight: 500;
            flex: 1;
        }

        .btn-secondary {
            background: #f5f5f5;
            color: #666;
            padding: 10px 20px;
            border: none;
            border-radius: 8px;
            cursor: pointer;
            flex: 1;
        }

        /* Add subtask form */
        .add-subtask-form {
            background: #f8f9fa;
            border-radius: 10px;
            padding: 15px;
            margin-bottom: 15px;
            border: 2px solid #e0e0e0;
        }

        .subtask-input {
            width: 100%;
            padding: 8px 12px;
            border: 1px solid #ddd;
            border-radius: 6px;
            font-size: 14px;
            outline: none;
            margin-bottom: 10px;
            box-sizing: border-box;
        }

        .subtask-input:focus {
            border-color: #4a90e2;
        }

        .subtask-actions {
            display: flex;
            gap: 8px;
        }

        .btn-sm {
            padding: 6px 12px;
            font-size: 12px;
            border-radius: 6px;
            border: none;
            cursor: pointer;
        }

        .btn-sm.btn-primary {
            background: #4a90e2;
            color: white;
        }

        .btn-sm.btn-secondary {
            background: #f5f5f5;
            color: #666;
        }

        /* Message Box */
        .message-box {
            padding: 10px;
            margin-bottom: 20px;
            border-radius: 8px;
            text-align: center;
        }
        .success { background: #e8f5e9; color: #2e7d32; }
        .error { background: #ffebee; color: #c62828; }
        
        /* Subtasks Styles */
        .subtasks-container {
            margin-left: 20px;
            border-left: 2px solid #e0e0e0;
            padding-left: 20px;
            margin-bottom: 10px;
        }
        
        .subtask-item {
            display: flex;
            align-items: center;
            justify-content: space-between;
            padding: 15px 20px;
            border-radius: 10px;
            margin-bottom: 10px;
            background: #f8f9fa;
            cursor: pointer;
            transition: all 0.3s ease;
        }
        
        .subtask-item:hover {
            background: #e9ecef;
        }
        
        .subtask-icon {
            color: #666;
            font-size: 16px;
            margin-right: 12px;
            font-weight: bold;
        }
        
        .subtask-item h4 {
            font-size: 14px;
            color: #333;
            margin-bottom: 3px;
        }
        
        .add-subtask-btn {
            display: flex;
            align-items: center;
            justify-content: center;
            padding: 10px 15px;
            margin: 10px 0;
            border: 2px dashed #ddd;
            border-radius: 8px;
            background: transparent;
            color: #666;
            cursor: pointer;
            transition: all 0.3s ease;
            font-size: 14px;
        }
        
        .add-subtask-btn:hover {
            border-color: #4a90e2;
            color: #4a90e2;
        }

        /* Tag Management Styles */
        .tags-container {
            display: flex;
            flex-wrap: wrap;
            gap: 8px;
            margin-bottom: 15px;
        }
        
        .tag-item {
            position: relative;
            flex: 0 1 calc(50% - 4px);
            min-width: 0;
            margin-bottom: 8px;
        }
        
        .tag {
            display: block;
            width: 100%;
            padding: 8px 20px 8px 12px;
            border-radius: 20px;
            font-size: 12px;
            font-weight: 500;
            cursor: pointer;
            transition: all 0.3s ease;
            white-space: nowrap;
            overflow: hidden;
            text-overflow: ellipsis;
            position: relative;
            box-sizing: border-box;
        }
        
        .tag:hover {
            transform: translateY(-1px);
            box-shadow: 0 4px 12px rgba(0,0,0,0.15);
        }
        
        .tag-delete-btn {
            position: absolute;
            top: -4px;
            right: -4px;
            width: 18px;
            height: 18px;
            border-radius: 50%;
            background: #ff4757;
            color: white;
            border: none;
            cursor: pointer;
            display: none;
            align-items: center;
            justify-content: center;
            font-size: 10px;
            font-weight: bold;
            line-height: 1;
            z-index: 10;
            transition: all 0.2s ease;
        }
        
        .tag-delete-btn:hover {
            background: #ff3838;
            transform: scale(1.1);
        }
        
        .tag-item:hover .tag-delete-btn {
            display: flex;
        }
        
        .tag-edit-btn {
            position: absolute;
            top: 2px;
            right: 2px;
            width: 16px;
            height: 16px;
            border-radius: 50%;
            background: rgba(255,255,255,0.8);
            color: #666;
            border: none;
            cursor: pointer;
            display: none;
            align-items: center;
            justify-content: center;
            font-size: 10px;
            z-index: 9;
            transition: all 0.2s ease;
        }
        
        .tag-edit-btn:hover {
            background: white;
            color: #333;
        }
        
        .tag-item:hover .tag-edit-btn {
            display: flex;
        }
        
        .tag-actions {
            display: flex;
            gap: 5px;
            align-items: center;
        }
        
        .add-tag-form {
            background: #f8f9fa;
            border-radius: 10px;
            padding: 15px;
            margin-top: 15px;
            border: 2px solid #e0e0e0;
        }
        
        .tag-input {
            width: 100%;
            padding: 8px 12px;
            border: 1px solid #ddd;
            border-radius: 6px;
            font-size: 14px;
            outline: none;
            margin-bottom: 10px;
            box-sizing: border-box;
        }
        
        .tag-input:focus {
            border-color: #4a90e2;
        }
        
        .tag-label {
            font-size: 12px;
            color: #666;
            font-weight: 500;
            display: block;
            margin-bottom: 5px;
        }
        
        .tag-color-input {
            width: 100%;
            height: 35px;
            border: 1px solid #ddd;
            border-radius: 6px;
            outline: none;
            cursor: pointer;
        }
        
        .tag-actions {
            display: flex;
            gap: 8px;
            margin-top: 10px;
        }
    </style>
</head>
<body>
    <div class="sidebar">
        <!-- Thông tin người dùng -->
        <div class="user-info">
            <div style="display: flex; align-items: center;">
                <div class="user-avatar">
                    ${sessionScope.user.username.substring(0,1).toUpperCase()}
                </div>
                <div class="user-details" style="margin-left: 12px;">
                    <h3>${sessionScope.user.username}</h3>
                    <p>${sessionScope.user.email}</p>
                </div>
            </div>
        </div>

        <!-- Menu Header -->
        <div class="menu-header">
            <h2 class="menu-title">Menu</h2>
            <div class="menu-icon">☰</div>
        </div>

        <!-- Search -->
        <div class="search-box">
            <input type="text" class="search-input" placeholder="🔍 Tìm kiếm">
        </div>

        <!-- Quick Actions Section -->
        <div class="section">
            <div class="section-title">Tổng quan</div>
            <a href="home?filter=today" class="menu-item ${param.listId == null ? 'active' : ''}">
                <div class="menu-item-left">
                    <span class="menu-item-icon">📅</span>
                    <span>Tới Hạn Hôm Nay</span>
                </div>
            </a>
        </div>

        <!-- Task Lists Section -->
        <div class="section">
            <div class="section-title">Danh sách công việc</div>
            <c:forEach var="taskList" items="${taskLists}">
                <a href="home?listId=${taskList.listId}" class="menu-item ${param.listId == taskList.listId ? 'active' : ''}">
                    <div class="menu-item-left">
                        
                        <c:choose>
                            <c:when test="${not empty taskList.icon}">
                                <span class="menu-item-icon">${taskList.icon}</span>
                            </c:when>
                            <c:otherwise>
                                <span class="menu-item-icon">📌</span>
                            </c:otherwise>
                        </c:choose>
                        <span>${taskList.listName}</span>
                        
                    </div>
                    <form action="taskLists" method="post" style="display: inline;">
                        <input type="hidden" name="action" value="delete">
                        <input type="hidden" name="listId" value="${taskList.listId}">
                        <button type="submit" class="delete-link" onclick="return confirm('Bạn có chắc muốn xóa danh sách này?')">🗑️</button>
                    </form>
                </a>
            </c:forEach>
            <a href="#" class="add-button" onclick="showAddListForm()">
                <span class="icon">➕</span>
                <span>Thêm danh sách mới</span>
            </a>

            <!-- Form thêm danh sách mới -->
            <div id="addListForm" class="add-task-form hidden">
                <form action="taskLists?action=add" method="post">
                    <input type="text" name="listName" class="task-input" placeholder="Nhập tên danh sách..." required>
                    <input type="color" name="colorCode" class="task-input" value="#4a90e2" style="width: 50px; padding: 5px;">
                    <div class="form-actions">
                        <button type="submit" class="btn-primary">Thêm danh sách</button>
                        <button type="button" class="btn-secondary" onclick="hideAddListForm()">Hủy</button>
                    </div>
                </form>
            </div>
        </div>

        <!-- Tags Section -->
        <div class="section">
            <div class="section-title">Thẻ</div>
            <div class="tags-container">
                <c:forEach var="tag" items="${tags}">
                    <div class="tag-item">
                        <span class="tag" style="background: ${tag.colorCode}; color: ${tag.textColor};" title="${tag.tagName}">${tag.tagName}</span>
                        <button class="tag-edit-btn" onclick="editTag(${tag.tagId}, '${tag.tagName}', '${tag.colorCode}', '${tag.textColor}')" title="Sửa thẻ">✏️</button>
                        <form action="tag" method="post" style="display: inline; margin: 0;" onsubmit="return confirm('Bạn có chắc muốn xóa thẻ này?')">
                            <input type="hidden" name="action" value="delete">
                            <input type="hidden" name="tagId" value="${tag.tagId}">
                            <button type="submit" class="tag-delete-btn" title="Xóa thẻ">×</button>
                        </form>
                    </div>
                </c:forEach>
            </div>
            <a href="#" onclick="showAddTagForm()" style="color: #666; text-decoration: none; font-size: 12px;">+ Thêm thẻ</a>

            <!-- Form thêm thẻ mới -->
            <div id="addTagForm" class="add-tag-form hidden">
                <form action="tag" method="post">
                    <input type="hidden" name="action" value="add">
                    <input type="text" name="tagName" class="tag-input" placeholder="Nhập tên thẻ..." required>
                    <div style="display: flex; gap: 10px; margin-bottom: 10px;">
                        <div style="flex: 1;">
                            <label class="tag-label">Màu nền:</label>
                            <input type="color" name="colorCode" class="tag-color-input" value="#007bff">
                        </div>
                        <div style="flex: 1;">
                            <label class="tag-label">Màu chữ:</label>
                            <input type="color" name="textColor" class="tag-color-input" value="#ffffff">
                        </div>
                    </div>
                    <div class="tag-actions">
                        <button type="submit" class="btn-sm btn-primary">Thêm thẻ</button>
                        <button type="button" class="btn-sm btn-secondary" onclick="hideAddTagForm()">Hủy</button>
                    </div>
                </form>
            </div>

            <!-- Form sửa thẻ -->
            <div id="editTagForm" class="add-tag-form hidden">
                <form action="tag" method="post">
                    <input type="hidden" name="action" value="update">
                    <input type="hidden" name="tagId" id="editTagId" value="">
                    <input type="text" name="tagName" id="editTagName" class="tag-input" placeholder="Nhập tên thẻ..." required>
                    <div style="display: flex; gap: 10px; margin-bottom: 10px;">
                        <div style="flex: 1;">
                            <label class="tag-label">Màu nền:</label>
                            <input type="color" name="colorCode" id="editTagColorCode" class="tag-color-input" value="#007bff">
                        </div>
                        <div style="flex: 1;">
                            <label class="tag-label">Màu chữ:</label>
                            <input type="color" name="textColor" id="editTagTextColor" class="tag-color-input" value="#ffffff">
                        </div>
                    </div>
                    <div class="tag-actions">
                        <button type="submit" class="btn-sm btn-primary">Cập nhật thẻ</button>
                        <button type="button" class="btn-sm btn-secondary" onclick="hideEditTagForm()">Hủy</button>
                    </div>
                </form>
            </div>
        </div>

        <!-- Settings -->
        <div class="settings-section">
            <a href="#" class="menu-item">
                <span>⚙️ Cài đặt</span>
            </a>
            <a href="${pageContext.request.contextPath}/logout" class="menu-item">
                <span>🚪 Đăng xuất</span>
            </a>
        </div>
    </div>

    <!-- Main Content -->
    <div class="main-content">
        <!-- Hiển thị thông báo -->
        <!--
        <c:if test="${not empty message}">
            <div class="message-box success">${message}</div>
        </c:if>
        -->
        <!--
        <c:if test="${not empty error}">
            <div class="message-box error">${error}</div>
        </c:if>
		-->
        <div class="main-header">
            <div class="header-top">
                <div style="display: flex; align-items: center;">
                    <div class="today-section">
                        <h1>
                            <c:choose>
                                <c:when test="${param.listId != null && selectedTaskList != null}">
                                    ${selectedTaskList.listName}
                                </c:when>
                                <c:otherwise>Tới Hạn Hôm Nay</c:otherwise>
                            </c:choose>
                        </h1>
                    </div>
                    <div class="task-count">
                        <c:out value="${taskCount != null ? taskCount : 0}"/>
                    </div>
                </div>
                <!-- Nút thêm task (hiển thị khi chọn danh sách) -->
                <c:if test="${param.listId != null && param.filter != 'today'}">
                    <button class="add-task-btn" onclick="toggleAddTaskForm()" id="addTaskBtn" style="border: none; background: #4a90e2; color: white;">
                        <span style="font-size: 16px; margin-right: 8px;">➕</span>
                        <span>Thêm công việc mới</span>
                    </button>
                </c:if>
            </div>

            <!-- Form thêm công việc mới (ẩn mặc định) -->
            <c:if test="${param.listId != null && param.filter != 'today'}">
                <div id="addTaskForm" class="add-task-form hidden">
                    <form action="TaskController" method="post" style="display: flex; flex-direction: column; gap: 10px;">
                        <input type="hidden" name="action" value="add">
                        <input type="hidden" name="listId" value="${param.listId}">
                        <div style="display: flex; gap: 10px;">
                            <div style="flex: 1;">
                                <label for="taskName" class="detail-label">Tên công việc</label>
                                <input type="text" id="taskName" name="taskName" class="task-input" placeholder="Nhập tên công việc..." required>
                            </div>
                            <div style="flex: 1;">
                                <label for="description" class="detail-label">Mô tả</label>
                                <textarea id="description" name="description" class="task-textarea" rows="2" placeholder="Nhập mô tả..."></textarea>
                            </div>
                        </div>
                        <div style="display: flex; gap: 10px;">
                            <div style="flex: 1;">
                                <label for="dueDate" class="detail-label">Ngày hết hạn</label>
                                <input type="date" id="dueDate" name="dueDate" class="task-input date-field" placeholder="Chọn ngày hết hạn">
                            </div>
                            <div style="flex: 1;">
                                <label for="priority" class="detail-label">Ưu tiên</label>
                                <select id="priority" name="priority" class="task-select">
                                    <option value="LOW">Thấp</option>
                                    <option value="MEDIUM">Trung bình</option>
                                    <option value="HIGH">Cao</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-actions" style="display: flex; gap: 10px;">
                            <button type="submit" class="btn-primary" style="flex: 1;">Thêm</button>
                            <button type="button" class="btn-secondary" onclick="hideAddTaskForm()" style="flex: 1;">Hủy</button>
                        </div>
                    </form>
                </div>
            </c:if>
        </div>

        <div class="task-list">
            <c:choose>
                <c:when test="${not empty tasks}">
                    <c:forEach var="task" items="${tasks}">
                        <!-- Parent Task -->
                        <div class="task-item" 
                             data-task-id="${task.taskId}"
                             data-task-title="${task.title}"
                             data-task-description="${task.description}"
                             data-task-list-id="${task.listId}"
                             data-task-due-date="${task.dueDate}"
                             data-task-priority="${task.priority}"
                             data-task-status="${task.status}"
                             data-parent-task-id="${task.parentTaskId}"
                             data-tag-id="${task.tagId}"
                             onclick="showTaskDetailFromElement(this)">
                            <div class="task-left">
                                <div class="task-icon" style="background: #4a90e2">
                                    <c:choose>
                                        <c:when test="${task.title.contains('Nghiên')}">📝</c:when>
                                        <c:when test="${task.title.contains('Tạo')}">👥</c:when>
                                        <c:when test="${task.title.contains('Gia')}">🆔</c:when>
                                        <c:when test="${task.title.contains('Tư')}">💼</c:when>
                                        <c:when test="${task.title.contains('In')}">🖨️</c:when>
                                        <c:otherwise>📋</c:otherwise>
                                    </c:choose>
                                </div>
                                <div class="task-content">
                                    <h3>${task.title}</h3>
                                    <div class="task-meta">
                                        <c:if test="${task.dueDate != null}">
                                            <span class="task-date">📅 ${task.dueDate}</span>
                                        </c:if>
                                        <c:if test="${task.description != null && !task.description.isEmpty()}">
                                            <span class="task-subtasks">${task.description}</span>
                                        </c:if>
                                        <!-- Tag cho task cha -->
                                        <c:if test="${task.parentTaskId == null || task.parentTaskId == 0}">
                                            <c:choose>
                                                <c:when test="${task.tagId != null && task.tagId > 0}">
                                                    <!-- Hiển thị tag thực tế -->
                                                    <c:forEach var="tag" items="${tags}">
                                                        <c:if test="${tag.tagId == task.tagId}">
                                                            <span class="task-tag" style="background: ${tag.colorCode}; color: ${tag.textColor}; padding: 4px 12px; border-radius: 12px; font-size: 11px; font-weight: 600; margin-right: 8px;">
                                                                🏷️ ${tag.tagName}
                                                            </span>
                                                        </c:if>
                                                    </c:forEach>
                                                </c:when>
                                                <c:otherwise>
                                                    <!-- Hiển thị "Task Cha" nếu chưa có tag -->
                                                    <span class="task-tag" style="background: #e3f2fd; color: #1976d2; padding: 4px 12px; border-radius: 12px; font-size: 11px; font-weight: 600; margin-right: 8px;">
                                                        🏷️ Task Cha
                                                    </span>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:if>
                                        <c:if test="${task.priority != null}">
                                            <span class="task-tag" style="background: 
                                                <c:choose>
                                                    <c:when test="${task.priority == 'HIGH'}">#ff4757</c:when>
                                                    <c:when test="${task.priority == 'MEDIUM'}">#ffa502</c:when>
                                                    <c:otherwise>#2ed573</c:otherwise>
                                                </c:choose>; color: #fff">
                                                <c:choose>
                                                    <c:when test="${task.priority == 'HIGH'}">Cao</c:when>
                                                    <c:when test="${task.priority == 'MEDIUM'}">Trung bình</c:when>
                                                    <c:otherwise>Thấp</c:otherwise>
                                                </c:choose>
                                            </span>
                                        </c:if>
                                        
                                        <!-- Hiển thị số lượng subtasks -->
                                        <c:if test="${subtasksMap[task.taskId] != null && subtasksMap[task.taskId].size() > 0}">
                                            <span class="task-subtasks">📝 ${subtasksMap[task.taskId].size()} subtask(s)</span>
                                        </c:if>
                                    </div>
                                </div>
                            </div>
                            <div style="display: flex; align-items: center; gap: 10px;">
                                <button onclick="toggleSubtasks(${task.taskId})" style="background: none; border: none; cursor: pointer; font-size: 16px;" title="Xem subtasks">
                                    <span id="expand-${task.taskId}">⬇️</span>
                                </button>
                                <form action="TaskController" method="post" style="margin: 0;">
                                    <input type="hidden" name="action" value="toggleStatus">
                                    <input type="hidden" name="taskId" value="${task.taskId}">
                                    <input type="hidden" name="listId" value="${task.listId}">
                                    <button type="submit" style="background: none; border: none; cursor: pointer; font-size: 18px;">
                                        <c:choose>
                                            <c:when test="${task.status == 'COMPLETED'}">✅</c:when>
                                            <c:otherwise>⭕</c:otherwise>
                                        </c:choose>
                                    </button>
                                </form>
                                <form action="TaskController" method="get" style="margin: 0;" onsubmit="return confirm('Bạn có chắc muốn xóa task này?')">
                                    <input type="hidden" name="action" value="delete">
                                    <input type="hidden" name="taskId" value="${task.taskId}">
                                    <input type="hidden" name="listId" value="${task.listId}">
                                    <button type="submit" style="background: none; border: none; cursor: pointer; color: #ff4757; font-size: 16px;">🗑️</button>
                                </form>
                                <span class="chevron">›</span>
                            </div>
                        </div>
                        
                        <!-- Subtasks Section -->
                        <div id="subtasks-${task.taskId}" class="subtasks-container" style="display: none;">
                            <c:if test="${subtasksMap[task.taskId] != null}">
                                <c:forEach var="subtask" items="${subtasksMap[task.taskId]}">
                                    <div class="subtask-item" 
                                         data-task-id="${subtask.taskId}"
                                         data-task-title="${subtask.title}"
                                         data-task-description="${subtask.description}"
                                         data-task-list-id="${subtask.listId}"
                                         data-task-due-date="${subtask.dueDate}"
                                         data-task-priority="${subtask.priority}"
                                         data-task-status="${subtask.status}"
                                         data-parent-task-id="${subtask.parentTaskId}"
                                         data-tag-id="${subtask.tagId}"
                                         data-parent-due-date="${task.dueDate}">
                                        <div class="task-left" onclick="showTaskDetailFromElement(this.parentElement)">
                                            <div class="subtask-icon">┣━</div>
                                            <div class="task-content">
                                                <h4>${subtask.title}</h4>
                                                <div class="task-meta">
                                                    <c:if test="${subtask.dueDate != null}">
                                                        <span class="task-date">📅 ${subtask.dueDate}</span>
                                                    </c:if>
                                                    <c:if test="${subtask.description != null && !subtask.description.isEmpty()}">
                                                        <span class="task-subtasks">${subtask.description}</span>
                                                    </c:if>
                                                    <c:if test="${subtask.priority != null}">
                                                        <span class="task-tag" style="background: 
                                                            <c:choose>
                                                                <c:when test="${subtask.priority == 'HIGH'}">#ff4757</c:when>
                                                                <c:when test="${subtask.priority == 'MEDIUM'}">#ffa502</c:when>
                                                                <c:otherwise>#2ed573</c:otherwise>
                                                            </c:choose>; color: #fff">
                                                            <c:choose>
                                                                <c:when test="${subtask.priority == 'HIGH'}">Cao</c:when>
                                                                <c:when test="${subtask.priority == 'MEDIUM'}">Trung bình</c:when>
                                                                <c:otherwise>Thấp</c:otherwise>
                                                            </c:choose>
                                                        </span>
                                                    </c:if>
                                                </div>
                                            </div>
                                        </div>
                                        <div style="display: flex; align-items: center; gap: 10px;">
                                            <form action="TaskController" method="post" style="margin: 0;">
                                                <input type="hidden" name="action" value="toggleStatus">
                                                <input type="hidden" name="taskId" value="${subtask.taskId}">
                                                <input type="hidden" name="listId" value="${subtask.listId}">
                                                <button type="submit" style="background: none; border: none; cursor: pointer; font-size: 16px;">
                                                    <c:choose>
                                                        <c:when test="${subtask.status == 'COMPLETED'}">✅</c:when>
                                                        <c:otherwise>⭕</c:otherwise>
                                                    </c:choose>
                                                </button>
                                            </form>
                                            <form action="TaskController" method="get" style="margin: 0;" onsubmit="return confirm('Bạn có chắc muốn xóa subtask này?')">
                                                <input type="hidden" name="action" value="delete">
                                                <input type="hidden" name="taskId" value="${subtask.taskId}">
                                                <input type="hidden" name="listId" value="${subtask.listId}">
                                                <button type="submit" style="background: none; border: none; cursor: pointer; color: #ff4757; font-size: 14px;">🗑️</button>
                                            </form>
                                        </div>
                                    </div>
                                </c:forEach>
                            </c:if>
                            
                            <!-- Add Subtask Form -->
                            <div id="add-subtask-${task.taskId}" class="add-subtask-form hidden">
                                <form action="TaskController" method="post">
                                    <input type="hidden" name="action" value="addSubtask">
                                    <input type="hidden" name="parentTaskId" value="${task.taskId}">
                                    <input type="hidden" name="listId" value="${task.listId}">
                                    <input type="text" name="taskName" class="subtask-input" placeholder="Nhập tên subtask..." required>
                                    <textarea name="description" class="subtask-input" placeholder="Mô tả subtask..." rows="2"></textarea>
                                    <div style="display: flex; gap: 10px;">
                                        <input type="date" name="dueDate" class="subtask-input" style="flex: 1;" 
                                               data-parent-due-date="${task.dueDate}" 
                                               max="${task.dueDate}" 
                                               onchange="validateSubtaskDueDate(this, ${task.taskId})">
                                        <select name="priority" class="subtask-input" style="flex: 1;">
                                            <option value="LOW">Thấp</option>
                                            <option value="MEDIUM">Trung bình</option>
                                            <option value="HIGH">Cao</option>
                                        </select>
                                    </div>
                                    <div id="due-date-error-${task.taskId}" style="color: #ff4757; font-size: 12px; margin-top: 5px; display: none;">
                                        Ngày hết hạn của subtask không được sau ngày hết hạn của task cha.
                                    </div>
                                    <div class="subtask-actions">
                                        <button type="submit" class="btn-sm btn-primary" id="submit-subtask-${task.taskId}">Thêm Subtask</button>
                                        <button type="button" class="btn-sm btn-secondary" onclick="hideAddSubtaskForm(${task.taskId})">Hủy</button>
                                    </div>
                                </form>
                            </div>
                            
                            <!-- Add Subtask Button -->
                            <button onclick="showAddSubtaskForm(${task.taskId})" class="add-subtask-btn">
                                <span>➕ Thêm Subtask</span>
                            </button>
                        </div>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <div style="text-align: center; padding: 40px; color: #666;">
                        <h3>Chưa có công việc nào</h3>
                        <c:if test="${param.listId != null}">
                            <p>Hãy thêm công việc mới cho danh sách này!</p>
                        </c:if>
                        <c:if test="${param.listId == null}">
                            <p>Hãy chọn một danh sách hoặc tạo danh sách mới!</p>
                        </c:if>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>

    <!-- Task Detail Panel -->
    <div id="taskDetailPanel" class="task-detail hidden">
        <div class="task-detail-header">
            <div style="display: flex; align-items: center; justify-content: space-between; margin-bottom: 15px;">
                <h2>Chi tiết công việc</h2>
                <button onclick="closeTaskDetail()" style="background: none; border: none; font-size: 20px; cursor: pointer;">×</button>
            </div>
            <h3 id="taskTitle" style="color: #333; font-weight: 500; margin-bottom: 20px;">Chọn một công việc để xem chi tiết</h3>
        </div>

        <form id="taskUpdateForm" action="TaskController" method="post">
            <input type="hidden" name="action" value="update">
            <input type="hidden" name="taskId" id="currentTaskId" value="">
            <input type="hidden" name="listId" id="currentListId" value="${param.listId}">

            <div class="detail-field">
                <div class="detail-label">Tên công việc</div>
                <input type="text" name="taskName" id="taskNameDetail" class="task-input" placeholder="Nhập tên công việc..." required>
            </div>

            <div class="detail-field">
                <div class="detail-label">Mô tả</div>
                <textarea name="description" id="taskDescription" class="task-textarea" rows="3" placeholder="Thêm mô tả..."></textarea>
            </div>

            <div class="detail-field">
                <div class="detail-label">Danh sách</div>
                <select name="listId" id="taskList" class="select-field">
                    <c:forEach var="taskList" items="${taskLists}">
                        <option value="${taskList.listId}">${taskList.listName}</option>
                    </c:forEach>
                </select>
            </div>

            <div class="detail-field">
                <div class="detail-label">Ngày hết hạn</div>
                <input type="date" name="dueDate" id="taskDueDate" class="date-field" value="" onchange="validateTaskDetailDueDate()">
            </div>
            <div id="detail-due-date-error" style="color: #ff4757; font-size: 12px; margin-top: -15px; margin-bottom: 15px; display: none;">
                Ngày hết hạn của subtask không được sau ngày hết hạn của task cha.
            </div>

            <div class="detail-field">
                <div class="detail-label">Mức độ ưu tiên</div>
                <select name="priority" id="taskPriority" class="select-field">
                    <option value="LOW">Thấp</option>
                    <option value="MEDIUM">Trung bình</option>
                    <option value="HIGH">Cao</option>
                </select>
            </div>

            <div class="detail-field" id="tagFieldContainer" style="display: none;">
                <div class="detail-label">Thẻ (Tag)</div>
                <select name="tagId" id="taskTag" class="select-field">
                    <option value="">-- Chọn thẻ --</option>
                    <c:forEach var="tag" items="${tags}">
                        <option value="${tag.tagId}">${tag.tagName}</option>
                    </c:forEach>
                </select>
            </div>

            <div class="detail-field">
                <div class="detail-label">Trạng thái</div>
                <select name="status" id="taskStatus" class="select-field">
                    <option value="PENDING">Chưa hoàn thành</option>
                    <option value="COMPLETED">Đã hoàn thành</option>
                </select>
            </div>

            <div class="action-buttons">
                <button type="button" class="delete-btn" onclick="deleteTaskFromDetail()">Xóa công việc</button>
                <button type="submit" class="save-btn">Lưu thay đổi</button>
            </div>
        </form>
    </div>

    <script>
        // Tạo map tags để dễ dàng tra cứu tên tag
        const tagsMap = {};
        <c:forEach var="tag" items="${tags}">
            tagsMap[${tag.tagId}] = {
                name: '<c:out value="${tag.tagName}" escapeXml="true"/>',
                color: '<c:out value="${tag.colorCode}" escapeXml="true"/>',
                textColor: '<c:out value="${tag.textColor}" escapeXml="true"/>'
            };
        </c:forEach>

        // Hiển thị/ẩn form thêm danh sách
        function showAddListForm() {
            const form = document.getElementById('addListForm');
            form.classList.remove('hidden');
            form.querySelector('input[name="listName"]').focus();
        }

        function hideAddListForm() {
            const form = document.getElementById('addListForm');
            form.classList.add('hidden');
            form.querySelector('input[name="listName"]').value = '';
            form.querySelector('input[name="colorCode"]').value = '#4a90e2';
        }

        // Chọn danh sách và hiển thị form thêm công việc
        // Không cần function này nữa vì đã dùng anchor link trực tiếp

        // Hiển thị/ẩn form thêm công việc
        function toggleAddTaskForm() {
            const form = document.getElementById('addTaskForm');
            const btn = document.getElementById('addTaskBtn');
            
            if (form.classList.contains('hidden')) {
                form.classList.remove('hidden');
                btn.querySelector('span:last-child').textContent = 'Ẩn form thêm';
                form.querySelector('input[name="taskName"]').focus();
            } else {
                form.classList.add('hidden');
                btn.querySelector('span:last-child').textContent = 'Thêm công việc mới';
                // Reset form
                form.querySelector('form').reset();
            }
        }

        function hideAddTaskForm() {
            const form = document.getElementById('addTaskForm');
            const btn = document.getElementById('addTaskBtn');
            
            if (form) {
                form.classList.add('hidden');
                if (btn) {
                    btn.querySelector('span:last-child').textContent = 'Thêm công việc mới';
                }
                // Reset form
                form.querySelector('form').reset();
            }
        }

        // Hiển thị chi tiết công việc từ element
        function showTaskDetailFromElement(element) {
            const taskId = element.getAttribute('data-task-id');
            const title = element.getAttribute('data-task-title');
            const description = element.getAttribute('data-task-description') || '';
            const listId = element.getAttribute('data-task-list-id');
            const dueDate = element.getAttribute('data-task-due-date') || '';
            const priority = element.getAttribute('data-task-priority') || 'LOW';
            const status = element.getAttribute('data-task-status') || 'PENDING';
            const parentTaskId = element.getAttribute('data-parent-task-id');
            const tagId = element.getAttribute('data-tag-id');
            
            // Truyền element để có thể lấy thêm thông tin nếu cần
            showTaskDetail(taskId, title, description, listId, dueDate, priority, status, parentTaskId, tagId, element);
        }

        // Hiển thị chi tiết công việc
        function showTaskDetail(taskId, title, description, listId, dueDate, priority, status, parentTaskId, tagId, element) {
            const panel = document.getElementById('taskDetailPanel');
            const taskTitle = document.getElementById('taskTitle');
            const currentTaskIdInput = document.getElementById('currentTaskId');
            const taskNameInput = document.getElementById('taskNameDetail');
            const taskDescInput = document.getElementById('taskDescription');
            const taskListSelect = document.getElementById('taskList');
            const taskDueDateInput = document.getElementById('taskDueDate');
            const taskPrioritySelect = document.getElementById('taskPriority');
            const taskStatusSelect = document.getElementById('taskStatus');
            const tagFieldContainer = document.getElementById('tagFieldContainer');
            const taskTagSelect = document.getElementById('taskTag');
            const dueDateErrorDiv = document.getElementById('detail-due-date-error');
            
            // Hiển thị panel
            panel.classList.remove('hidden');
            
            // Thiết lập taskId cho form cập nhật
            currentTaskIdInput.value = taskId;
            
            // Điền thông tin task vào form
            taskTitle.textContent = title || "Chi tiết công việc #" + taskId;
            taskNameInput.value = title || "";
            taskDescInput.value = description || "";
            taskListSelect.value = listId || "";
            taskDueDateInput.value = dueDate || "";
            taskPrioritySelect.value = priority || "LOW";
            taskStatusSelect.value = status || "PENDING";
            
            // Reset các thuộc tính data- trước
            taskDueDateInput.removeAttribute('data-parent-task-id');
            taskDueDateInput.removeAttribute('data-parent-due-date');
            taskDueDateInput.removeAttribute('max');
            
            // Hiển thị/ẩn tag field chỉ cho task cha
            if (!parentTaskId || parentTaskId == 'null' || parentTaskId == '' || parentTaskId == '0') {
                // Task cha - hiển thị tag field
                tagFieldContainer.style.display = 'block';
                taskTagSelect.value = tagId || '';
                dueDateErrorDiv.style.display = 'none';
            } else {
                // Task con - ẩn tag field
                tagFieldContainer.style.display = 'none';
                taskTagSelect.value = '';
                
                // Lưu thông tin parent task ID và setup validation cho due date
                taskDueDateInput.setAttribute('data-parent-task-id', parentTaskId);
                
                // Kiểm tra xem đã có thông tin parentDueDate từ element không (thông tin từ data-parent-due-date attribute)
                const parentDueDate = element ? element.getAttribute('data-parent-due-date') : null;
                
                if (parentDueDate) {
                    // Nếu element đã có sẵn thông tin về parent due date, sử dụng nó
                    taskDueDateInput.setAttribute('data-parent-due-date', parentDueDate);
                    taskDueDateInput.setAttribute('max', parentDueDate);
                    
                    // Validate ngay khi hiển thị
                    validateTaskDetailDueDate();
                } else {
                    // Tìm task cha để lấy due date nếu chưa có thông tin
                    const parentTaskElement = document.querySelector(`.task-item[data-task-id="${parentTaskId}"]`);
                    if (parentTaskElement) {
                        const parentTaskDueDate = parentTaskElement.getAttribute('data-task-due-date');
                        if (parentTaskDueDate) {
                            taskDueDateInput.setAttribute('data-parent-due-date', parentTaskDueDate);
                            taskDueDateInput.setAttribute('max', parentTaskDueDate);
                            
                            // Validate ngay khi hiển thị
                            validateTaskDetailDueDate();
                        }
                    }
                }
            }
        }
        
        function setDeleteTaskId() {
            const taskId = document.getElementById('currentTaskId').value;
            const listId = document.getElementById('currentListId').value;
            const deleteLink = document.getElementById('deleteTaskLink');
            deleteLink.href = 'TaskController?action=delete&taskId=' + taskId + '&listId=' + listId;
        }
        
        // Đóng task detail panel
        function closeTaskDetail() {
            document.getElementById('taskDetailPanel').classList.add('hidden');
        }
        
        // Xóa task từ detail panel
        function deleteTaskFromDetail() {
            const taskId = document.getElementById('currentTaskId').value;
            const listId = document.getElementById('currentListId').value;
            
            if (taskId && confirm('Bạn có chắc muốn xóa công việc này?')) {
                // Tạo form ẩn để submit
                const form = document.createElement('form');
                form.method = 'GET';
                form.action = 'TaskController';
                
                const actionInput = document.createElement('input');
                actionInput.type = 'hidden';
                actionInput.name = 'action';
                actionInput.value = 'delete';
                
                const taskIdInput = document.createElement('input');
                taskIdInput.type = 'hidden';
                taskIdInput.name = 'taskId';
                taskIdInput.value = taskId;
                
                const listIdInput = document.createElement('input');
                listIdInput.type = 'hidden';
                listIdInput.name = 'listId';
                listIdInput.value = listId;
                
                form.appendChild(actionInput);
                form.appendChild(taskIdInput);
                form.appendChild(listIdInput);
                
                document.body.appendChild(form);
                form.submit();
            }
        }
        
        // Subtasks functions
        function toggleSubtasks(taskId) {
            const subtasksContainer = document.getElementById('subtasks-' + taskId);
            const expandIcon = document.getElementById('expand-' + taskId);
            
            if (subtasksContainer.style.display === 'none' || subtasksContainer.style.display === '') {
                subtasksContainer.style.display = 'block';
                expandIcon.textContent = '⬆️';
            } else {
                subtasksContainer.style.display = 'none';
                expandIcon.textContent = '⬇️';
            }
        }
        
        function showAddSubtaskForm(taskId) {
            const form = document.getElementById('add-subtask-' + taskId);
            form.classList.remove('hidden');
            form.querySelector('input[name="taskName"]').focus();
        }
        
        function hideAddSubtaskForm(taskId) {
            const form = document.getElementById('add-subtask-' + taskId);
            form.classList.add('hidden');
            form.querySelector('form').reset();
        }

        // Tag Management Functions
        function showAddTagForm() {
            const form = document.getElementById('addTagForm');
            const editForm = document.getElementById('editTagForm');
            
            // Hide edit form if open
            editForm.classList.add('hidden');
            
            form.classList.remove('hidden');
            form.querySelector('input[name="tagName"]').focus();
        }

        function hideAddTagForm() {
            const form = document.getElementById('addTagForm');
            form.classList.add('hidden');
            form.querySelector('form').reset();
        }

        function editTag(tagId, tagName, colorCode, textColor) {
            const form = document.getElementById('editTagForm');
            const addForm = document.getElementById('addTagForm');
            
            // Hide add form if open
            addForm.classList.add('hidden');
            
            // Fill the edit form
            document.getElementById('editTagId').value = tagId;
            document.getElementById('editTagName').value = tagName;
            document.getElementById('editTagColorCode').value = colorCode;
            document.getElementById('editTagTextColor').value = textColor;
            
            form.classList.remove('hidden');
            form.querySelector('input[name="tagName"]').focus();
        }

        function hideEditTagForm() {
            const form = document.getElementById('editTagForm');
            form.classList.add('hidden');
            form.querySelector('form').reset();
        }

        // Validate dueDate cho subtask trong form Add Subtask
        function validateSubtaskDueDate(input, taskId) {
            const parentDueDate = input.getAttribute('data-parent-due-date');
            const errorDiv = document.getElementById('due-date-error-' + taskId);
            const submitBtn = document.getElementById('submit-subtask-' + taskId);
            
            // Nếu task cha không có due date hoặc subtask không có due date
            if (!parentDueDate || !input.value) {
                errorDiv.style.display = 'none';
                submitBtn.disabled = false;
                return;
            }
            
            // Convert to dates for comparison
            const subtaskDate = new Date(input.value);
            const parentDate = new Date(parentDueDate);
            
            // Set time to midnight for accurate date comparison
            subtaskDate.setHours(0, 0, 0, 0);
            parentDate.setHours(0, 0, 0, 0);
            
            if (subtaskDate > parentDate) {
                errorDiv.style.display = 'block';
                submitBtn.disabled = true;
            } else {
                errorDiv.style.display = 'none';
                submitBtn.disabled = false;
            }
        }
        
        // Validate dueDate cho subtask trong Task Detail Panel
        function validateTaskDetailDueDate() {
            const taskDueDateInput = document.getElementById('taskDueDate');
            const dueDateErrorDiv = document.getElementById('detail-due-date-error');
            const saveBtn = document.querySelector('#taskUpdateForm .save-btn');
            
            // Kiểm tra xem đây có phải là subtask không (có parent task id)
            const parentTaskId = taskDueDateInput.getAttribute('data-parent-task-id');
            if (!parentTaskId) {
                // Đây là task cha, không cần validate
                dueDateErrorDiv.style.display = 'none';
                saveBtn.disabled = false;
                return;
            }
            
            // Lấy due date của task cha
            const parentDueDate = taskDueDateInput.getAttribute('data-parent-due-date');
            
            // Nếu task cha không có due date hoặc subtask không có due date
            if (!parentDueDate || !taskDueDateInput.value) {
                dueDateErrorDiv.style.display = 'none';
                saveBtn.disabled = false;
                return;
            }
            
            // Convert to dates for comparison
            const subtaskDate = new Date(taskDueDateInput.value);
            const parentDate = new Date(parentDueDate);
            
            // Set time to midnight for accurate date comparison
            subtaskDate.setHours(0, 0, 0, 0);
            parentDate.setHours(0, 0, 0, 0);
            
            if (subtaskDate > parentDate) {
                dueDateErrorDiv.style.display = 'block';
                saveBtn.disabled = true;
            } else {
                dueDateErrorDiv.style.display = 'none';
                saveBtn.disabled = false;
            }
        }
    </script>
</body>
</html>