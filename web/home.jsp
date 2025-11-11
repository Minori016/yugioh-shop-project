<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>

<%@ include file="includes/header.jsp" %>
<style>
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
<div class="container">
    <div class="row">
        <div class="col-sm-3">
            <div class="card bg-light mb-3">
                <div class="card-header bg-primary text-white text-uppercase">
                    <i class="fa fa-list"></i> Categories
                </div>
                <ul class="list-group category_block">
                    <c:forEach items="${listCater}" var="o">
                        <li class="list-group-item text-white">
                            <a href="MainController?txtAction=categoryProduct&setID=${o.setID}">${o.setName}</a>
                        </li>
                    </c:forEach>
                </ul>
            </div>

            <c:if test="${not empty p}">
                <div class="card bg-light mb-3 shadow-sm border-0">
                    <div class="card-header bg-success text-white text-uppercase text-center">
                        <i class="fa fa-star"></i> Staff's Pick
                    </div>
                    <div class="card-body text-center">
                        <div class="d-flex justify-content-center align-items-center bg-white p-2"
                             style="height:240px; overflow:hidden; border-radius:8px;">
                            <img src="${p.image}" alt="${p.cardName}"
                                 style="width:100%; height:auto; max-height:230px; object-fit:scale-down;">
                        </div>
                        <h5 class="card-title font-weight-bold mb-1 mt-2">${p.cardName}</h5>
                        <p class="card-text text-muted mb-2">${p.rarity}</p>
                        <p class="h5 text-danger mb-0">${p.price} $</p>
                    </div>
                </div>
            </c:if>
        </div>

        <div class="col-sm-9">
            <div class="row">
                <c:forEach items="${listC}" var="o">

                    <div class="col-12 col-md-6 col-lg-4 mb-4">

                        <div class="card h-100">
                            <img class="card-img-top" src="${o.image}" alt="${o.cardName}">

                            <div class="card-body d-flex flex-column">
                                <h4 class="card-title show_txt">
                                    <a href="MainController?txtAction=viewDetail&cardID=${o.cardID}" title="View Product">${o.cardName}</a>
                                </h4>
                                <p class="card-text show_txt">${o.rarity}</p>

                                <div class="mt-auto">
                                    <div class="row">
                                        <div class="col">
                                            <p class="btn btn-danger btn-block">${o.price} $</p>
                                        </div>
                                    </div>

                                    <div class="row">
                                        <div class="col-6" style="padding-right: 5px;">
                                            <form action="MainController" method="POST" style="margin-bottom: 0;" onsubmit="saveScrollPosition()">

                                                <input type="hidden" name="txtAction" value="addToCart" /> 
                                                <input type="hidden" name="cardID" value="${o.cardID}" />
                                                <input type="hidden" name="userID" value="${user.userName}" />

                                                <button type="submit" class="btn btn-success btn-block">Thêm giỏ</button>
                                            </form>
                                        </div>
                                        <div class="col-6" style="padding-left: 5px;">
                                            <form action="MainController" method="POST" style="margin-bottom: 0;">
                                                <input type="hidden" name="cardID" value="${o.cardID}" />
                                                <input type="hidden" name="txtAction" value="Buy Now" />
                                                <button type="submit" class="btn btn-primary btn-block">Mua ngay</button>
                                            </form>
                                        </div>
                                    </div>

                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
</div>
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
<%@ include file="includes/footer.jsp" %>