<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Quản lý Đơn hàng</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <link rel="stylesheet" href="css/style.css">
    </head>
    <body>
        <jsp:include page="includes/header.jsp" />

        <%-- Dùng container-fluid để hiển thị rộng hơn --%>
        <div class="container-fluid my-5 px-5"> 
            <h2>Quản lý Đơn hàng</h2>

            <%-- Filter đã tải 'listOrders' --%>
            <c:if test="${empty listOrders}">
                <div class="alert alert-info mt-3">Chưa có đơn hàng nào.</div>
            </c:if>

            <c:if test="${not empty listOrders}">
                <div class="table-responsive">
                    <table class="table table-hover table-bordered table-sm mt-3" style="font-size: 0.9rem;">
                        <thead class="thead-dark">
                            <tr>
                                <th>Mã ĐH</th>
                                <th>Người đặt (UserID)</th>
                                <th>Ngày đặt</th>
                                <th>Tổng tiền</th>
                                <th>Trạng thái</th>
                                <th>Địa chỉ giao hàng</th>
                                <th>SĐT Giao</th>
                                <th>Chi tiết</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%-- Lặp qua danh sách đã được filter tải --%>
                            <c:forEach var="order" items="${listOrders}">
                                <tr>
                                    <td><strong>${order.orderID}</strong></td>
                                    <td>${order.fullName} (${order.userID})</td>
                                    <td>
                                        <fmt:formatDate value="${order.orderDate}" pattern="HH:mm 'ngày' dd/MM/yyyy" />
                                    </td>
                                    <td>
                                        <fmt:formatNumber value="${order.totalPrice}" type="currency" currencySymbol="$" />
                                    </td>
                                    <td>
                                        ${order.status}
                                    </td>
                                    <td>${order.shippingAddress}</td>
                                    <td>${order.shippingPhone}</td>
                                    <td>
                                        <%-- Link này vẫn gọi Controller để lấy chi tiết --%>
                                        <a href="MainController?txtAction=viewOrderDetails&orderID=${order.orderID}" class="btn btn-primary btn-sm">
                                            Xem
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:if>
        </div>

        <jsp:include page="includes/footer.jsp" />
    </body>
</html>