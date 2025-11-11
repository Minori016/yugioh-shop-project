<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Manage Products</title>
        
        <%-- BẮT ĐẦU PHẦN STYLE NHÚNG TRỰC TIẾP --%>
        <style type="text/css">
            /* =========================================
               CSS RIÊNG CHO TRANG MANAGE PRODUCT
               ========================================= */

            /* --- Cài đặt chung --- */
            body {
                font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, "Helvetica Neue", Arial, sans-serif;
                background-color: #f4f7f6; /* Màu nền xám nhạt */
                margin: 0;
                padding: 0;
                color: #333;
            }

            .container {
                width: 90%;
                max-width: 1400px;
                margin: 25px auto; /* Tăng khoảng cách trên dưới */
                padding: 25px;
                background-color: #ffffff;
                border-radius: 10px; /* Bo góc nhiều hơn */
                box-shadow: 0 6px 18px rgba(0,0,0,0.06); /* Shadow đẹp hơn */
            }
            
            /* [MỚI] Dùng để chứa H1 và nút Back */
            .header-controls {
                display: flex;
                justify-content: space-between;
                align-items: center;
                border-bottom: 2px solid #ecf0f1; /* Chuyển border từ H1 ra đây */
                padding-bottom: 10px;
            }
            
            /* [MỚI] Style cho nút Back */
            .btn-back-home {
                display: inline-block;
                padding: 8px 15px;
                background-color: #6c757d; /* Màu xám */
                color: white;
                text-decoration: none;
                border-radius: 5px;
                font-size: 0.95em;
                font-weight: 500;
                transition: background-color 0.2s ease;
            }
            .btn-back-home:hover {
                background-color: #5a6268;
            }

            h1, h2 {
                color: #2c3e50; /* Màu xanh đậm */
            }

            h1 {
                font-size: 2.2em;
                margin-top: 0;
                margin-bottom: 0; /* Bỏ margin H1 */
                border-bottom: none; /* Bỏ border H1 */
                padding-bottom: 0; /* Bỏ padding H1 */
            }

            h2 {
                font-size: 1.6em;
                margin-top: 35px;
                border-bottom: 2px solid #ecf0f1;
                padding-bottom: 10px;
            }

            hr {
                border: 0;
                border-top: 1px solid #ecf0f1;
                margin: 40px 0;
            }

            /* --- Form Thêm Sản Phẩm --- */
            .add-form {
                margin-bottom: 30px;
                padding: 30px;
                background-color: #fdfdfd;
                border: 1px solid #e0e6ed;
                border-radius: 8px;
            }

            .add-form h2 {
                margin-top: 0;
                border-bottom: none;
                color: #34495e; /* Màu xanh đậm hơn */
            }

            .add-form div {
                margin-bottom: 18px; /* Tăng khoảng cách các dòng */
                display: flex;
                align-items: center;
            }

            .add-form label {
                display: inline-block;
                width: 140px; /* Tăng độ rộng label */
                font-weight: 600; /* Đậm hơn */
                color: #555;
                flex-shrink: 0;
            }

            .add-form input[type="text"],
            .add-form input[type="number"],
            .add-form select {
                flex-grow: 1;
                max-width: 550px;
                padding: 12px 15px; /* Tăng padding */
                border: 1px solid #ccd9e5;
                border-radius: 5px;
                font-size: 1em;
                box-sizing: border-box;
                transition: border-color 0.2s ease, box-shadow 0.2s ease;
            }

            /* Hiệu ứng khi focus vào input */
            .add-form input[type="text"]:focus,
            .add-form input[type="number"]:focus,
            .add-form select:focus {
                border-color: #007bff;
                box-shadow: 0 0 0 3px rgba(0,123,255,0.15);
                outline: none;
            }

            .add-form button {
                padding: 12px 25px;
                background-color: #007bff; /* Xanh dương */
                color: white;
                border: none;
                border-radius: 5px;
                cursor: pointer;
                font-size: 1.05em;
                font-weight: bold;
                margin-left: 140px; /* Bằng chiều rộng label */
                transition: background-color 0.2s ease;
            }

            .add-form button:hover {
                background-color: #0056b3;
            }

            /* --- Bảng Danh Sách Sản Phẩm --- */
            table {
                width: 100%;
                border-collapse: collapse;
                margin-top: 25px;
                box-shadow: 0 4px 10px rgba(0,0,0,0.05);
                border-radius: 8px; /* Bo góc cho bảng */
                overflow: hidden; /* Giúp bo góc hoạt động */
            }

            table th, table td {
                border-bottom: 1px solid #ddd; /* Chỉ cần border dưới */
                padding: 15px 18px;
                text-align: left;
                vertical-align: middle;
            }

            table th {
                background-color: #f8f9fa; /* Màu nền header xám nhạt */
                font-weight: 600;
                color: #333;
                text-transform: uppercase;
                font-size: 0.9em;
                letter-spacing: 0.5px;
            }

            /* Bỏ border cho hàng cuối cùng */
            table tr:last-child td {
                border-bottom: none;
            }

            table tr:nth-child(even) {
                background-color: #fdfdfd;
            }

            table tr:hover {
                background-color: #f1f5f8; /* Màu khi hover */
            }

            .img-thumbnail {
                max-width: 60px;
                height: auto;
                border-radius: 5px;
                border: 1px solid #ccc;
                background-color: white;
                padding: 2px;
            }

            /* --- Nút Bấm Actions (Edit/Delete) --- */
            .actions a {
                text-decoration: none;
                padding: 7px 14px;
                border-radius: 5px;
                margin-right: 6px;
                color: white;
                font-weight: 500; /* Hơi đậm vừa */
                font-size: 0.9em;
                display: inline-block;
                transition: opacity 0.2s ease;
            }

            .actions a.btn-edit {
                background-color: #ffc107; /* Vàng */
                color: #333;
            }
            .actions a.btn-delete {
                background-color: #dc3545; /* Đỏ */
                color: white;
            }

            .actions a:hover {
                opacity: 0.85;
            }

            /* --- Thông báo Lỗi/Thành công --- */
            .message-success,
            .message-error {
                padding: 15px 20px;
                border-radius: 5px;
                font-weight: 500;
                margin-top: 20px;
                border: 1px solid;
            }

            .message-success {
                background-color: #e6ffed;
                border-color: #b7ebc9;
                color: #257942;
            }

            .message-error {
                background-color: #ffe6e6;
                border-color: #ebccd1;
                color: #a94442;
            }
        </style>
        <%-- KẾT THÚC PHẦN STYLE --%>
        
    </head>
    <body>
        <div class="container">
            
            <%-- =============================================== --%>
            <%-- NÚT QUAY LẠI ĐÃ ĐƯỢC THÊM VÀO ĐÂY --%>
            <%-- =============================================== --%>
            <div class="header-controls">
                <h1>Product Management</h1>
                <a href="home.jsp" class="btn-back-home">&larr; Quay về trang chủ</a>
            </div>
            
          
            <div class="add-form">
                <h2>Add New Product</h2>
                <form action="MainController" method="POST">
                    <input type="hidden" name="txtAction" value="addCard">
                    <div>
                        <label>Card Code:</label>
                        <input type="text" name="cardID" required>
                    </div>
                    <div>
                        <label>Card Name:</label>
                        <input type="text" name="cardName" required>
                    </div>
                    <div>
                        <label>Rarity:</label>
                        <input type="text" name="rarity" required>
                    </div>
                    <div>
                        <label>Price:</label>
                        <input type="number" step="0.01" name="price" required>
                    </div>
                    <div>
                        <label>Image URL:</label>
                        <input type="text" name="imageUrl">
                    </div>
                    <div>
                        <label>Set (Category):</label>
                        <select name="setID" required>
                            <c:forEach items="${requestScope.listCater}" var="set">
                                <option value="${set.setID}">${set.setName}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <button type="submit">Add Product</button>
                </form>

                <%-- Hiển thị thông báo (nếu có) --%>
                <c:if test="${not empty requestScope.MESSAGE}">
                    <%-- Dùng class "message-success" --%>
                    <p class="message-success">${requestScope.MESSAGE}</p>
                </c:if>
                <c:if test="${not empty requestScope.ERROR}">
                    <%-- Dùng class "message-error" --%>
                    <p class="message-error">${requestScope.ERROR}</p>
                </c:if>
            </div>

            <hr>
            <h2>Product List</h2>

            <%-- BẢNG HIỂN THỊ SẢN PHẨM --%>
            <table>
                <thead>
                    <tr>
                        <th>Card Code</th>
                        <th>Image</th>
                        <th>Card Name</th>
                        <th>Rarity</th>
                        <th>Price</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${requestScope.listC}" var="c">
                        <tr>
                            <td>${c.cardID}</td>
                            <td>
                                <c:if test="${not empty c.image}">
                                    <img src="${c.image}" alt="${c.cardName}" class="img-thumbnail"/>
                                </c:if>
                                <c:if test="${empty c.image}">
                                    No Image
                                </c:if>
                            </td>
                            <td>${c.cardName}</td>
                            <td>${c.rarity}</td>
                            <td>${c.price} $</td>
                            <td class="actions">
                                <%-- Thêm class "btn-edit" và "btn-delete" --%>
                                <a href="MainController?txtAction=callUpdateCard&cardID=${c.cardID}" class="btn-edit">Edit</a>
                                
                                <%-- =============================================== --%>
                                <%-- LINK DELETE ĐÃ ĐƯỢC CẬP NHẬT --%>
                                <%-- =============================================== --%>
                                <a href="MainController?txtAction=deleteCard&cardID=${c.cardID}" 
                                   class="btn-delete"
                                   onclick="return confirm('Bạn có chắc muốn xóa lá bài \'${c.cardName}\' không?');">
                                   Delete
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </body>
</html>