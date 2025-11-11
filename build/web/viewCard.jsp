<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Card Detail - ${p.cardName}</title>

        <%-- Include CSS/JS cơ bản --%>
        <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
        <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">

        <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>

        <%-- Link CSS chung của bạn --%>
        <link href="css/style.css" rel="stylesheet" type="text/css"/>

        <%-- Link đến file CSS mới --%>
        <link href="css/viewCard.css" rel="stylesheet" type="text/css"/>

    </head>
    <body>
        <jsp:include page="includes/header.jsp"></jsp:include>

            <div class="container mt-4 mb-4">
                <div class="row">
                    <div class="col-lg-3">
                        <div class="card bg-light mb-3">
                            <div class="card-header bg-primary text-white text-uppercase"><i class="fa fa-list"></i> Categories</div>
                            <ul class="list-group category_block">
                            <c:forEach items="${listCater}" var="o">
                                <li class="list-group-item text-white">
                                    <a href="MainController?txtAction=categoryProduct&setID=${o.setID}">${o.setName}</a>
                                </li>
                            </c:forEach>
                        </ul>
                    </div>
                    <c:if test="${not empty last}">
                        <div class="card bg-light mb-3">
                            <div class="card-header bg-success text-white text-uppercase"><i class="fa fa-star"></i> Staff's Pick</div>
                            <div class="card-body text-center">
                                <img class="img-fluid" src="${last.image}" />
                                <h5 class="card-title mt-2">${last.cardName}</h5>
                                <p class="card-text text-muted">${last.rarity}</p>
                                <p class="bloc_left_price h5 text-danger">${last.price} $</p>
                            </div>
                        </div>
                    </c:if>
                </div>

                <div class="col-lg-9">
                    <div class="card main-card mb-4">
                        <div class="row no-gutters">
                            <!-- Hình ảnh -->
                            <aside class="col-md-6">
                                <div class="img-big-wrap">
                                    <a href="${p.image}" target="_blank">
                                        <img src="${p.image}" alt="${p.cardName}" class="img-fluid">
                                    </a>
                                </div>
                            </aside>

                            <!-- Thông tin -->
                            <aside class="col-md-6">
                                <article class="card-body p-4 p-lg-5">
                                    <h3 class="title mb-2">${p.cardName}</h3>
                                    <h5 class="card-set-name">Set: ${cardSet.setName}</h5>

                                    <div class="price-detail-wrap mb-3"> 
                                        <span class="price text-danger">
                                            <span class="currency">Giá: </span>
                                            <span class="num">${p.price} $</span>
                                        </span> 
                                    </div>

                                    <dl class="item-property mb-3">
                                        <dt>Rarity</dt>
                                        <dd><p class="badge badge-info" style="font-size: 1rem; padding: 5px 10px;">${p.rarity}</p></dd>
                                    </dl>

                                    <dl class="item-property mb-3">
                                        <dt>Card ID</dt>
                                        <dd><p>${p.cardID}</p></dd>
                                    </dl>

                                    <hr>

                                    <form action="MainController" method="POST" onsubmit="saveScrollPosition()">
                                        <input type="hidden" name="txtAction" value="addToCart" />
                                        <input type="hidden" name="cardID" value="${p.cardID}" />
                                        <input type="hidden" name="userID" value="${sessionScope.user.userName}" />
                                        <div class="row">
                                            <div class="col-sm-6">
                                                <dl class="param param-inline">
                                                    <dt>Quantity:</dt>
                                                    <dd>
                                                        <input class="form-control form-control-sm" type="number" 
                                                               name="quantity" min="1" value="1" style="width:90px;">
                                                    </dd>
                                                </dl>
                                            </div>
                                        </div>

                                        <div class="button-group">
                                            <button type="submit" class="btn btn-success text-uppercase">
                                                <i class="fa fa-shopping-cart"></i> Add to cart
                                            </button>

                                            <a href="#" class="btn btn-primary text-uppercase">
                                                <i class="fa fa.bolt"></i> Buy now
                                            </a>
                                        </div>

                                    </form>
                                </article>
                            </aside>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <jsp:include page="includes/footer.jsp"></jsp:include>
    </body>
</html>