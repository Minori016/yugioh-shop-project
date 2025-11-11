<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>${pageTitle != null ? pageTitle : "Yu-Gi-Oh! Card Shop"}</title>
        <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet">
        <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
        <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
        <link href="css/style.css" rel="stylesheet" type="text-css"/>
    </head>
    <body>
        <nav class="navbar navbar-expand-md navbar-dark bg-dark">
            <div class="container">
                <a class="navbar-brand" href="home.jsp">Our Collection!</a>
                <button class="navbar-toggler" type="button" data-toggle="collapse"
                        data-target="#navbarsExampleDefault" aria-controls="navbarsExampleDefault"
                        aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>

                <div class="collapse navbar-collapse justify-content-end" id="navbarsExampleDefault">
                    <ul class="navbar-nav m-auto">
                        <li class="nav-item"><a class="nav-link" href="MainController?txtAction=viewProfile">Manager Your Account</a></li>
                        <li class="nav-item"><a class="nav-link" href="#">Hello ${sessionScope.user.fullName}</a></li>
                        <li class="nav-item"><a class="nav-link" href="MainController?txtAction=logout">Logout</a></li>
                        <li class="nav-item"><a class="nav-link" href="login.jsp">Login</a></li> <%-- S?a l?i href="#" thành "login.jsp" --%>
                        
                        <%-- Ch? hi?n th? link này n?u user là ADMIN --%>
                        <c:if test="${sessionScope.user.role == 'ADMIN'}">
                            <li class="nav-item">
                                <a class="nav-link" href="MainController?txtAction=callManageAccountByAdmin" style="color: #ffc107; font-weight: bold;">
                                    Manage Users
                                </a>
                            </li>
                        </c:if>
                            
                            <%--Chi hien neu la staff hoac admin ( manageProduct) --%>
                        <c:if test="${sessionScope.user.role == 'ADMIN' || sessionScope.user.role == 'STAFF'}">
                            <li class="nav-item">
                                <a class="nav-link" href="MainController?txtAction=callManageProduct" style="color: #ffc107; font-weight: bold;">
                                    Manage Product
                                </a>
                            </li>
                        </c:if>
                        </ul>

                    <div>
                        <c:if test="${sessionScope.user != null}">
                            <c:choose>
                                <c:when test="${not empty sessionScope.user.avatarBase64}">
                                    <img src="${sessionScope.user.avatarBase64}"
                                         alt="Avatar"
                                         style="width:50px; height:50px; border-radius:50%; object-fit:cover; border:2px solid #f8f9fa; margin-left:10px; margin-right:5px; vertical-align:middle;">
                                </c:when>
                                <c:otherwise>
                                    <img src="https://anhavatardep.com/wp-content/uploads/2025/05/avatar-vo-danh-1.jpg"
                                         alt="Avatar"
                                         style="width:50px; height:50px; border-radius:50%; object-fit:cover; border:2px solid #f8f9fa; margin-left:10px; margin-right:5px; vertical-align:middle;">
                                </c:otherwise>
                            </c:choose>
                        </c:if>
                    </div>
                    <form action="MainController" method="GET" class="form-inline my-2 my-lg-0">

                        <%-- 1. Thêm txtAction ?n ?? MainController bi?t ?ây là hành ??ng gì --%>
                        <input type="hidden" name="txtAction" value="searchCard">

                        <div class="input-group input-group-sm">
                            <input name="txtSearchValue" type="text" class="form-control" 
                                   placeholder="Search by name..."
                                   value="${requestScope.txtSearchValue}"> 
                            <div class="input-group-append">
                                <button type="submit" class="btn btn-secondary btn-number">
                                    <i class="fa fa-search"></i>
                                </button>
                            </div>
                        </div>

                        <%-- PH?N GI? HÀNG (GI? NGUYÊN) --%>
                        <a class="btn btn-success btn-sm ml-3" href="Cart.jsp">
                            <i class="fa fa-shopping-cart"></i> Cart
                            <c:if test="${not empty sessionScope.cartItemCount && sessionScope.cartItemCount > 0}">
                                <span class="badge badge-light">
                                    ${sessionScope.cartItemCount}
                                </span>
                            </c:if>
                        </a>

                    </form>
                    <%-- === H?T FORM TÌM KI?M === --%>
                </div>
            </div>
        </nav>

        <section class="jumbotron text-center text-white"
                 style="background-image: url('https://www.yugioh-card.com/eu/wp-content/uploads/2024/08/LEDD_Reprint_News_Banner.webp');
                 background-size: cover; background-position: center; padding: 120px 0;">
            <div class="container">
                <h1 class="jumbotron-heading fw-bold">Trading Card Game Shop</h1>
                <p class="lead mb-0">Reputation builds the brand ? with over 10 years of providing TCG products such as Yu-Gi-Oh! trading cards.</p>
            </div>
        </section>