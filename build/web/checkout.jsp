<%-- Thêm 2 thư viện JSTL: 'c' (core) và 'fmt' (format) --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Thanh toán</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <link rel="stylesheet" href="css/style.css"> <%-- (Giả sử bạn dùng file này) --%>
        <style>
            /* CSS giống hệt Cart.jsp để đồng bộ giao diện */
            .cart-img {
                width: 80px;
                height: auto;
                object-fit: contain;
            }

            /* CSS mới cho phần chọn địa chỉ */
            .address-option {
                border: 1px solid #ddd;
                border-radius: 5px;
                padding: 15px;
                margin-bottom: 10px;
                cursor: pointer;
                transition: background-color 0.2s;
            }
            .address-option:hover {
                background-color: #f9f9f9;
            }
            .address-option input[type="radio"] {
                margin-right: 10px;
                vertical-align: middle;
            }
            .address-option label {
                width: 100%;
                margin-bottom: 0; /* Ghi đè bootstrap */
                cursor: pointer;
            }

        </style>
    </head>
    <body>

        <%-- Gọi header --%>
        <jsp:include page="includes/header.jsp" />

        <div class="container my-5">
            <h2>Thanh toán</h2>

            <%-- 
                1. Kiểm tra xem giỏ hàng (listCart) có rỗng không.
                (Logic y hệt Cart.jsp)
            --%>
            <c:if test="${empty listCart}">
                <div class="alert alert-info mt-3">
                    Giỏ hàng của bạn đang trống.
                    <a href="home.jsp">Tiếp tục mua sắm</a>.
                </div>
            </c:if>

            <%-- 
                2. Nếu giỏ hàng KHÔNG rỗng -> Hiển thị form thanh toán 
            --%>
            <c:if test="${not empty listCart}">

                <%-- 
                    Form này sẽ bao bọc toàn bộ nội dung trang
                    để gửi 'txtAction=placeOrder' và 'selectedAddressID' đi
                --%>
                <form action="MainController" method="POST">
                    <input type="hidden" name="txtAction" value="placeOrder"> <%-- Controller sẽ nhận action này --%>

                    <div class="row mt-4">

                        <%-- 
                            CỘT BÊN TRÁI (col-md-8)
                            Bao gồm: (A) Chọn địa chỉ, và (B) Review giỏ hàng
                        --%>
                        <div class="col-md-8">

                            <div class="card">
                                <div class="card-body">
                                    <h5 class="card-title">1. Chọn địa chỉ giao hàng</h5>
                                    <hr>

                                    <%-- 
                                        Kiểm tra xem 'listAddress' (được set từ Controller) có rỗng không.
                                        'listAddress' là ArrayList<AddressDTO>
                                    --%>
                                    <c:if test="${empty listAddress}">
                                        <div class="alert alert-warning">
                                            Bạn chưa có địa chỉ nào đã lưu.
                                            <a href="MainController?txtAction=viewProfile">Quản lý tài khoản</a> để thêm địa chỉ.
                                        </div>
                                    </c:if>

                                    <%-- Nếu CÓ địa chỉ -> Lặp và hiển thị --%>
                                    <c:if test="${not empty listAddress}">
                                        <c:forEach var="addr" items="${listAddress}" varStatus="loop">
                                            <%-- 
                                                addr là 1 đối tượng AddressDTO 
                                                addr.receiverName -> gọi getReceiverName()
                                            --%>
                                            <div class="address-option">
                                                <label>
                                                    <input type="radio" name="selectedAddressID" value="${addr.addressID}"
                                                           <%-- Tự động check cái đầu tiên --%>
                                                           <c:if test="${loop.first}">checked</c:if>
                                                    required> <%-- Bắt buộc phải chọn 1 cái --%>

                                                    <strong>${addr.receiverName}</strong> | ${addr.phoneNumber}
                                                    <br>
                                                    <small class="text-muted">${addr.detailedAddress}</small>
                                                </label>
                                            </div>
                                        </c:forEach>
                                    </c:if>
                                </div>
                            </div> <%-- Hết card chọn địa chỉ --%>


                            <div class="card mt-4">
                                <div class="card-body">
                                    <h5 class="card-title">2. Kiểm tra lại đơn hàng</h5>
                                    <%-- 
                                        Bảng này giống Cart.jsp nhưng KHÔNG có form Cập nhật/Xóa
                                    --%>
                                    <table class="table table-hover align-middle">
                                        <thead class="thead-dark">
                                            <tr>
                                                <th>Sản phẩm</th>
                                                <th>Giá</th>
                                                <th>Số lượng</th>
                                                <th>Tổng phụ</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <%-- Lặp qua 'listCart' (y hệt Cart.jsp) --%>
                                        <c:forEach var="item" items="${listCart}">
                                            <tr>
                                                <%-- 1. SẢN PHẨM --%>
                                                <td>
                                                    <div class="d-flex align-items-center">
                                                        <img src="${item.imageUrl}" alt="${item.cardName}" class="cart-img mr-3">
                                                        <span>${item.cardName}</span>
                                                    </div>
                                                </td>

                                                <%-- 2. GIÁ --%>
                                                <td>
                                            <fmt:formatNumber value="${item.price}" type="currency" currencySymbol="$" maxFractionDigits="2" minFractionDigits="2" />
                                            </td>

                                            <%-- 3. SỐ LƯỢNG (Chỉ hiển thị text) --%>
                                            <td>
                                                ${item.quantity}
                                            </td>

                                            <%-- 4. TỔNG PHỤ --%>
                                            <td>
                                            <fmt:formatNumber value="${item.totalPrice}" type="currency" currencySymbol="$" maxFractionDigits="2" minFractionDigits="2" />
                                            </td>
                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </div> <%-- Hết card review giỏ hàng --%>

                        </div> <%-- Hết col-md-8 --%>


                        <%-- 
                            CỘT BÊN PHẢI (col-md-4)
                            Phần TỔNG TIỀN (y hệt Cart.jsp)
                        --%>
                        <div class="col-md-4">
                            <div class="card">
                                <div class="card-body">
                                    <h5 class="card-title">TỔNG GIỎ HÀNG</h5>
                                    <hr>
                                    <div class="d-flex justify-content-between">
                                        <strong>Tổng cộng:</strong>
                                        <%-- Dùng 'totalBill' (y hệt Cart.jsp) --%>
                                        <strong>
                                            <fmt:formatNumber value="${totalBill}" type="currency" currencySymbol="$" maxFractionDigits="2" minFractionDigits="2" />
                                        </strong>
                                    </div>
                                    <hr>

                                    <%-- 
                                        NÚT SUBMIT FORM
                                        Thay <a> bằng <button type="submit"> 
                                    --%>

                                    <button type="submit" class="btn btn-success btn-block"
                                            <%-- Vô hiệu hóa nút nếu không có địa chỉ nào để chọn --%>
                                            <c:if test="${empty listAddress}">disabled</c:if>
                                        >
                                        Xác nhận Đặt hàng
                                    </button>

                                    <a href="Cart.jsp" class="btn btn-outline-secondary btn-block mt-2">
                                        Quay lại giỏ hàng
                                    </a>
                                </div>
                            </div>
                        </div> <%-- Hết col-md-4 --%>

                    </div> <%-- Hết .row --%>
                </form> <%-- Hết form chính --%>

            </c:if> <%-- Hết c:if (kiểm tra giỏ hàng rỗng) --%>

        </div>

        <%-- Gọi footer --%>
        <jsp:include page="includes/footer.jsp" />
    </body>
</html>