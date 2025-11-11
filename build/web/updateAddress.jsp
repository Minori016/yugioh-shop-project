<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- Đặt tiêu đề trang và gọi header --%>
<c:set var="pageTitle" value="Update Your Address" scope="request" />
<jsp:include page="includes/header.jsp" />

<div class="container mt-4">
    <div class="row">
        <div class="col-md-8 offset-md-2">
            
            <h2>Update Your Address</h2>
            <p class="lead">Chỉnh sửa thông tin địa chỉ nhận hàng của bạn.</p>
            <hr>

            <%-- 
                Kiểm tra xem Controller có gửi sang đối tượng ADDRESS_TO_UPDATE không.
                (Đây là đối tượng đã được hàm processCallUpdateAddress set)
            --%>
            <c:if test="${not empty requestScope.ADDRESS_TO_UPDATE}">
                
                <%-- Đặt một biến JSTL tên 'addr' cho ngắn gọn --%>
                <c:set var="addr" value="${requestScope.ADDRESS_TO_UPDATE}" />
                
                <form action="MainController" method="POST">
                    
                    <%-- Đây là 2 trường ẩn CỰC KỲ quan trọng để Controller hoạt động --%>
                    <input type="hidden" name="txtAction" value="updateAddress" />
                    <input type="hidden" name="addressID" value="${addr.addressID}" />
                    
                    <%-- 
                        Các form-group bên dưới sử dụng tên (name)
                        khớp với hàm processUpdateAddress trong UserController
                    --%>
                    <div class="form-group">
                        <label for="receiverName">Receiver Name</label>
                        <input type="text" id="receiverName" name="txtReceiverName" class="form-control" 
                               value="${addr.receiverName}" required />
                    </div>
                    
                    <div class="form-group">
                        <label for="phoneNumber">Phone Number</label>
                        <input type="text" id="phoneNumber" name="txtPhoneNumber" class="form-control" 
                               value="${addr.phoneNumber}" required />
                    </div>
                    
                    <div class="form-group">
                        <label for="detailedAddress">Detailed Address</label>
                        <input type="text" id="detailedAddress" name="txtDetailedAddress" class="form-control" 
                               value="${addr.detailedAddress}" required />
                    </div>
                    
                   
                    
                    <button type="submit" class="btn btn-primary">Save Changes</button>
                    <%-- Nút Cancel để quay về trang quản lý tài khoản --%>
                    <a href="manageAccount.jsp" class="btn btn-secondary">Cancel</a>
                </form>
            
            </c:if>

            <%-- 
                Trường hợp không tìm thấy địa chỉ (VD: người dùng sửa ID trên URL)
            --%>
            <c:if test="${empty requestScope.ADDRESS_TO_UPDATE}">
                <div class="alert alert-danger" role="alert">
                    Address not found or could not be loaded.
                </div>
                <a href="MainController?txtAction=viewProfile" class="btn btn-primary">Back to My Account</a>
            </c:if>

        </div>
    </div>
</div>

<jsp:include page="includes/footer.jsp" />