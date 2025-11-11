<%-- Thêm 2 thư viện JSTL: 'c' (core) và 'fmt' (format) --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Giỏ hàng</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <link rel="stylesheet" href="css/style.css"> <%-- (Giả sử bạn dùng file này) --%>
        <style>
            /* Thêm 1 chút CSS để giống với ảnh của bạn */
            .cart-img {
                width: 80px;
                height: auto;
                object-fit: contain;
            }
            .quantity-input {
                width: 70px;
                display: inline-block;
            }


            /* CSS cho thông báo "Toast" */
            #cart-toast {
                visibility: hidden; /* Ẩn ban đầu */
                min-width: 250px;
                background-color: #4CAF50; /* Màu xanh lá */
                color: white;
                text-align: center;
                border-radius: 5px;
                padding: 16px;
                position: fixed; /* Luôn nằm cố định */
                z-index: 100;
                right: 30px;
                bottom: 30px;
                font-size: 17px;
            }

            /* Lớp 'show' để kích hoạt animation */
            #cart-toast.show {
                visibility: visible;
                -webkit-animation: fadein 0.5s, fadeout 0.5s 2.5s;
                animation: fadein 0.5s, fadeout 0.5s 2.5s;
            }

            /* Animation mờ dần vào và ra */
            @-webkit-keyframes fadein {
                from {
                    bottom: 0;
                    opacity: 0;
                }
                to {
                    bottom: 30px;
                    opacity: 1;
                }
            }
            @keyframes fadein {
                from {
                    bottom: 0;
                    opacity: 0;
                }
                to {
                    bottom: 30px;
                    opacity: 1;
                }
            }
            @-webkit-keyframes fadeout {
                from {
                    bottom: 30px;
                    opacity: 1;
                }
                to {
                    bottom: 0;
                    opacity: 0;
                }
            }
            @keyframes fadeout {
                from {
                    bottom: 30px;
                    opacity: 1;
                }
                to {
                    bottom: 0;
                    opacity: 0;
                }
            }

        </style>
    </head>
    <body>

        <%-- Gọi header --%>
        <jsp:include page="includes/header.jsp" />

        <div class="container my-5">
            <h2>Giỏ hàng của bạn</h2>

            <%-- 
              Kiểm tra xem giỏ hàng (CART) trong session có rỗng không.
              sessionScope.CART.items sẽ gọi hàm getItems() của class CartDTO
            --%>
            <c:if test="${empty listCart}">
                <div class="alert alert-info mt-3">
                    Giỏ hàng của bạn đang trống. 
                    <a href="home.jsp">Tiếp tục mua sắm</a>.
                </div>
            </c:if>

            <%-- Nếu giỏ hàng KHÔNG rỗng --%>
            <c:if test="${not empty listCart}">
                <div class="row mt-4">
                    <div class="col-md-8">
                        <table class="table table-hover align-middle">
                            <thead class="thead-dark">
                                <tr>
                                    <th>Sản phẩm</th>
                                    <th>Giá</th>
                                    <th>Số lượng</th>
                                    <th>Tổng phụ</th>
                                    <th>Xóa</th>
                                </tr>
                            </thead>
                            <tbody>
                                <%-- 
                                  Lặp qua từng món hàng trong giỏ.
                                  items="${sessionScope.CART.items}" -> lặp qua Map
                                  var="item" sẽ là một Map.Entry (có key và value)
                                  - item.key là cardID
                                  - item.value là đối tượng CartItemDTO
                                --%>
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
                                            <%-- Dùng fmt để định dạng tiền tệ --%>
                                            <fmt:formatNumber value="${item.price}" type="currency" currencySymbol="$" maxFractionDigits="2" minFractionDigits="2" />
                                        </td>

                                        <%-- 3. SỐ LƯỢNG (Form CẬP NHẬT) --%>
                                        <td>
                                            <form action="MainController" method="POST" class="form-inline" onsubmit="saveScrollPosition()">
                                                <input type="hidden" name="txtAction" value="updateFromCart">
                                                <input type="hidden" name="cardID" value="${item.cardCode}">
                                                <input type="number" name="quantity" value="${item.quantity}" class="form-control form-control-sm quantity-input" min="1">
                                                <button type="submit" class="btn btn-warning btn-sm ml-1">Cập nhật</button>
                                            </form>
                                        </td>

                                        <%-- 4. TỔNG PHỤ --%>
                                        <td>
                                            <%-- item.value.totalPrice sẽ gọi hàm getTotalPrice() của CartItemDTO --%>
                                            <fmt:formatNumber value="${item.totalPrice}" type="currency" currencySymbol="$" maxFractionDigits="2" minFractionDigits="2" />
                                        </td>

                                        <%-- 5. XÓA (Form XÓA) --%>
                                        <td>
                                            <form action="MainController" method="POST" onsubmit="saveScrollPosition()">
                                                <input type="hidden" name="txtAction" value="removeFromCart">
                                                <input type="hidden" name="cardID" value="${item.cardCode}">
                                                <button type="submit" class="btn btn-danger btn-sm">&times;</button>
                                            </form>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div> <%-- Hết col-md-8 --%>

                    <%-- PHẦN TỔNG TIỀN (Giống ảnh) --%>
                    <div class="col-md-4">
                        <div class="card">
                            <div class="card-body">
                                <h5 class="card-title">TỔNG GIỎ HÀNG</h5>
                                <hr>
                                <div class="d-flex justify-content-between">
                                    <strong>Tổng cộng:</strong>
                                    <%-- 
                                      sessionScope.CART.total sẽ gọi hàm getTotal() của CartDTO
                                    --%>
                                    <strong>
                                        <fmt:formatNumber value="${totalBill}" type="currency" currencySymbol="$" maxFractionDigits="2" minFractionDigits="2" />
                                    </strong>
                                </div>
                                <hr>
                                <a href="checkout.jsp" class="btn btn-success btn-block">Tiến hành thanh toán</a>
                                <a href="home.jsp" class="btn btn-outline-secondary btn-block mt-2">Tiếp tục mua sắm</a>
                            </div>
                        </div>
                    </div> <%-- Hết col-md-4 --%>

                </div> <%-- Hết .row --%>
            </c:if> <%-- Hết c:if (kiểm tra giỏ hàng rỗng) --%>

        </div>

        <%-- Gọi footer --%>
        <jsp:include page="includes/footer.jsp" />
        <script>
            // 1. Hàm này chạy KHI TRANG TẢI LÊN
            document.addEventListener("DOMContentLoaded", function () {
                // Lấy vị trí đã lưu
                let scrollPos = sessionStorage.getItem("scrollPos");

                if (scrollPos) {
                    // Nếu tìm thấy, cuộn trang đến vị trí đó
                    window.scrollTo(0, parseInt(scrollPos));

                    // Xóa vị trí đã lưu để không bị cuộn ở lần tải trang sau
                    sessionStorage.removeItem("scrollPos");
                }
            });

            // 2. Hàm này chạy KHI BẤM NÚT SUBMIT (do onsubmit ở Bước 1)
            function saveScrollPosition() {
                // Lưu vị trí cuộn dọc (Y) hiện tại
                sessionStorage.setItem("scrollPos", window.scrollY);
            }
        </script>
        <c:if test="${not empty sessionScope.cartMessage}">

            <div id="cart-toast" class="show">
                ${sessionScope.cartMessage}
            </div>

            <c:remove var="cartMessage" scope="session" />

            <script>
                // Đoạn script này không cần thiết nếu bạn dùng animation CSS ở trên
                // Animation CSS đã tự động ẩn sau 3 giây (0.5s + 2.5s)

                // (Nếu bạn không dùng animation 'fadeout', hãy bỏ comment đoạn dưới)
                /*
                 document.addEventListener("DOMContentLoaded", function() {
                 var toast = document.getElementById("cart-toast");
                 toast.classList.add("show");
                 
                 // Tự động ẩn sau 3 giây
                 setTimeout(function(){ 
                 toast.classList.remove("show"); 
                 }, 3000);
                 });
                 */
            </script>
        </c:if>
    </body>
</html>