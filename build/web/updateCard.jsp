<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Update Product</title>
        
        <%-- Lấy style y hệt trang Manage Product --%>
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
            
            .header-controls {
                display: flex;
                justify-content: space-between;
                align-items: center;
                border-bottom: 2px solid #ecf0f1; /* Chuyển border từ H1 ra đây */
                padding-bottom: 10px;
            }
            
            .btn-back-manage {
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
            .btn-back-manage:hover {
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
            }

            /* --- Form Cập Nhật --- */
            .update-form {
                margin-bottom: 30px;
                padding: 30px;
                background-color: #fdfdfd;
                border: 1px solid #e0e6ed;
                border-radius: 8px;
            }

            .update-form h2 {
                margin-top: 0;
                border-bottom: none;
                color: #34495e; /* Màu xanh đậm hơn */
            }

            .update-form div {
                margin-bottom: 18px; /* Tăng khoảng cách các dòng */
                display: flex;
                align-items: center;
            }

            .update-form label {
                display: inline-block;
                width: 140px; /* Tăng độ rộng label */
                font-weight: 600; /* Đậm hơn */
                color: #555;
                flex-shrink: 0;
            }

            .update-form input[type="text"],
            .update-form input[type="number"],
            .update-form select {
                flex-grow: 1;
                max-width: 550px;
                padding: 12px 15px; /* Tăng padding */
                border: 1px solid #ccd9e5;
                border-radius: 5px;
                font-size: 1em;
                box-sizing: border-box;
                transition: border-color 0.2s ease, box-shadow 0.2s ease;
            }
            
            /* Input bị khóa (readonly) */
            .update-form input[readonly] {
                background-color: #e9ecef;
                color: #6c757d;
                cursor: not-allowed;
            }

            .update-form input[type="text"]:focus,
            .update-form input[type="number"]:focus,
            .update-form select:focus {
                border-color: #007bff;
                box-shadow: 0 0 0 3px rgba(0,123,255,0.15);
                outline: none;
            }

            .update-form button {
                padding: 12px 25px;
                background-color: #28a745; /* Màu xanh lá cây (Save) */
                color: white;
                border: none;
                border-radius: 5px;
                cursor: pointer;
                font-size: 1.05em;
                font-weight: bold;
                margin-left: 140px; /* Bằng chiều rộng label */
                transition: background-color 0.2s ease;
            }

            .update-form button:hover {
                background-color: #218838;
            }
        </style>
    </head>
    <body>
        
        <%-- 
            Chúng ta dùng 2 đối tượng từ ProductController:
            1. ${requestScope.cardToUpdate} : Thông tin của lá bài cần sửa
            2. ${requestScope.listCater}   : Danh sách Set/Category
        --%>

        <div class="container">
            <div class="header-controls">
                <h1>Update Product</h1>
                <a href="MainController?txtAction=manageProduct" class="btn-back-manage">&larr; Quay về trang quản lý</a>
            </div>

            <div class="update-form">
                <form action="MainController" method="POST">
                    
                    <%-- Action này sẽ được gửi đi khi bấm "Save Changes" --%>
                    <input type="hidden" name="txtAction" value="updateCard">
                    
                    <div>
                        <label>Card Code:</label>
                        <%-- 
                          Khóa Card Code lại, không cho sửa khóa chính
                          Dùng value="" để điền dữ liệu cũ vào
                        --%>
                        <input type="text" name="cardID" value="${requestScope.cardToUpdate.cardID}" readonly>
                    </div>
                    <div>
                        <label>Card Name:</label>
                        <input type="text" name="cardName" value="${requestScope.cardToUpdate.cardName}" required>
                    </div>
                    <div>
                        <label>Rarity:</label>
                        <input type="text" name="rarity" value="${requestScope.cardToUpdate.rarity}" required>
                    </div>
                    <div>
                        <label>Price:</label>
                        <input type="number" step="0.01" name="price" value="${requestScope.cardToUpdate.price}" required>
                    </div>
                    <div>
                        <label>Image URL:</label>
                        <input type="text" name="imageUrl" value="${requestScope.cardToUpdate.image}">
                    </div>
                    <div>
                        <label>Set (Category):</label>
                        <select name="setID" required>
                            <c:forEach items="${requestScope.listCater}" var="set">
                                <%-- 
                                  Dùng C:IF để kiểm tra xem SetID của vòng lặp
                                  có bằng SetID của lá bài không.
                                  Nếu bằng, thêm thuộc tính 'selected'
                                --%>
                                <option value="${set.setID}" 
                                        <c:if test="${set.setID == requestScope.cardToUpdate.setID}">selected</c:if>
                                >
                                    ${set.setName}
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                    <button type="submit">Save Changes</button>
                </form>
            </div>
        </div>
        

    </body>
</html>