<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="pageTitle" value="Manage Your Account" scope="request" />
<jsp:include page="includes/header.jsp" />

<div class="container mt-4">
    <div class="row">
        <div class="col-md-8 offset-md-2">
            
            <h2>Manage Your Account</h2>
            <p class="lead">Cập nhật thông tin cá nhân và địa chỉ của bạn.</p>
            <hr>

            <c:if test="${not empty msg_success}">
                <div class="alert alert-success" role="alert">${msg_success}</div>
            </c:if>
            <c:if test="${not empty msg_error}">
                <div class="alert alert-danger" role="alert">${msg_error}</div>
            </c:if>

            <c:set var="user" value="${profile != null ? profile : sessionScope.user}" />

            <form action="MainController" method="POST">
                <input type="hidden" name="txtAction" value="updateProfile">

                <div class="form-group">
                    <label>Username (Không thể đổi)</label>
                    <input type="text" class="form-control" value="${user.userName}" readonly>
                </div>
                
                <div class="form-group">
                    <label for="fullName">Full Name</label>
                    <input type="text" name="txtFullName" id="fullName" class="form-control" 
                           value="${user.fullName}" required>
                </div>
                
                <div class="form-group">
                    <label for="email">Email</label>
                    <input type="email" name="txtEmail" id="email" class="form-control" 
                           value="${user.email}" required>
                </div>

                <div class="mb-3">
                    <label class="form-label">Avatar</label>
                    <input type="file" id="avatarFile" accept="image/png, image/jpeg, image/gif" class="form-control" />
                    
                    <input type="hidden" name="txtAvatarBase64" id="avatarBase64" />

                    <div class="mt-3">
                        <img id="avatarPreview" 
                             src="${not empty user.avatarBase64 ? user.avatarBase64 : ''}" 
                             alt="Avatar Preview" 
                             class="rounded border" 
                             style="max-width: 150px; max-height: 150px; display: ${not empty user.avatarBase64 ? 'block' : 'none'};">
                    </div>
                </div>
                <button type="submit" class="btn btn-primary">Update Profile</button>
            </form>
            
            
            <hr> <h4>Add New Address</h4>
            <form action="MainController" method="POST" class="mb-3">
                <input type="hidden" name="txtAction" value="addAddress" />
                
                <div class="form-group">
                    <label for="receiverName">Receiver Name</label>
                    <input type="text" id="receiverName" name="txtReceiverName" class="form-control" required />
                </div>
                
                <div class="form-group">
                    <label for="phoneNumber">Phone Number</label>
                    <input type="text" id="phoneNumber" name="txtPhoneNumber" class="form-control" required />
                </div>
                
                <div class="form-group">
                    <label for="detailedAddress">Detailed Address</label>
                    <input type="text" id="detailedAddress" name="txtDetailedAddress" class="form-control" required />
                </div>
                
              
                
                <button type="submit" class="btn btn-success">Add Address</button>
            </form>

            
            <hr> <h4>My Saved Addresses</h4>
            
            <c:if test="${empty ADDRESS_LIST}">
                <p>You have no saved addresses.</p>
            </c:if>

            <c:forEach var="addr" items="${requestScope.ADDRESS_LIST}">
                <div class="card mb-3">
                    <div class="card-body">
                        <h5 class="card-title">
                            ${addr.receiverName}
                      
                        </h5>
                        <p class="card-text mb-1">${addr.phoneNumber}</p>
                        <p class="card-text">${addr.detailedAddress}</p>
                        
                        <a href="MainController?txtAction=callUpdateAddress&addressID=${addr.addressID}" class="btn btn-sm btn-outline-primary">
                            Update
                        </a>
                        <a href="MainController?txtAction=deleteAddress&addressID=${addr.addressID}" class="btn btn-sm btn-outline-danger"
                           onclick="return confirm('Are you sure you want to delete this address?');">
                            Delete
                        </a>
                    </div>
                </div>
            </c:forEach>


            <hr> <h4>Change Password</h4>
            <p><em>(Chức năng này cần được xây dựng riêng)</em></p>

        </div>
    </div>
</div>

<script>
    document.getElementById('avatarFile').addEventListener('change', function(event) {
        var file = event.target.files[0];
        if (file) {
            var reader = new FileReader();
            
            reader.onload = function(e) {
                var base64String = e.target.result;
                
                // 1. Gán chuỗi Base64 vào input ẩn
                document.getElementById('avatarBase64').value = base64String;
                
                // 2. Hiển thị ảnh preview
                var preview = document.getElementById('avatarPreview');
                preview.src = base64String;
                preview.style.display = 'block';
            };
            
            reader.readAsDataURL(file); // Đọc file thành chuỗi Base64
        }
    });
</script>

<jsp:include page="includes/footer.jsp" />