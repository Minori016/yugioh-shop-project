<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="pageTitle" value="Admin - User Management" scope="request" />
<jsp:include page="includes/header.jsp" />

<div classcontainer mt-4">
    <div class="row">
        <div class="col-md-10 offset-md-1">
            
            <h2>User Management (Admin)</h2>
            <p class="lead">Quản lý tất cả tài khoản người dùng trong hệ thống.</p>
            <hr>

            <%-- Hiển thị thông báo (nếu có) từ session sau khi redirect --%>
            <c:if test="${not empty sessionScope.msg_success}">
                <div class="alert alert-success" role="alert">
                    ${sessionScope.msg_success}
                    <c:remove var="msg_success" scope="session" />
                </div>
            </c:if>
            <c:if test="${not empty sessionScope.msg_error}">
                <div class="alert alert-danger" role="alert">
                    ${sessionScope.msg_error}
                    <c:remove var="msg_error" scope="session" />
                </div>
            </c:if>

            <%-- =========== 1. Form THÊM MỚI (Collapsible) =========== --%>
            <div class="mb-3">
                <a class="btn btn-success" data-toggle="collapse" href="#addUserCollapse" role="button" aria-expanded="false" aria-controls="addUserCollapse">
                    + Add New User
                </a>
            </div>
            <div class="collapse <c:if test='${not empty error_userName or not empty error_password or not empty error_fullName or not empty error_email or not empty error_role}'>show</c:if>" id="addUserCollapse">
                <div class="card card-body mb-4">
                    <h4>Add New User Form</h4>
                    <form action="MainController" method="POST">
                        <input type="hidden" name="txtAction" value="addUserByAdmin">
                        
                        <%-- Hiển thị lỗi chung (nếu có) --%>
                        <c:if test="${not empty error}">
                            <div class="alert alert-danger">${error}</div>
                        </c:if>
                        
                        <div class="form-row">
                            <div class="form-group col-md-6">
                                <label for="txtUsername">Username</label>
                                <input type="text" name="txtUsername" id="txtUsername" class="form-control" value="${u_add.userName}" required>
                                <small class="text-danger">${error_userName}</small>
                            </div>
                            <div class="form-group col-md-6">
                                <label for="txtEmail">Email</label>
                                <input type="email" name="txtEmail" id="txtEmail" class="form-control" value="${u_add.email}" required>
                                <small class="text-danger">${error_email}</small>
                            </div>
                        </div>
                        <div class="form-row">
                            <div class="form-group col-md-6">
                                <label for="txtPassword">Password</label>
                                <input type="password" name="txtPassword" id="txtPassword" class="form-control" required>
                                <small class="text-danger">${error_password}</small>
                            </div>
                            <div class="form-group col-md-6">
                                <label for="txtRePassword">Retype Password</label>
                                <input type="password" name="txtRePassword" id="txtRePassword" class="form-control" required>
                            </div>
                        </div>
                         <div class="form-row">
                            <div class="form-group col-md-6">
                                <label for="txtFullName">Full Name</label>
                                <input type="text" name="txtFullName" id="txtFullName" class="form-control" value="${u_add.fullName}" required>
                                <small class="text-danger">${error_fullName}</small>
                            </div>
                            <div class="form-group col-md-3">
                                <label for="txtRole">Role</label>
                                <select name="txtRole" id="txtRole" class="form-control" required>
                                    <option value="USER" ${u_add.role == 'USER' ? 'selected' : ''}>USER</option>
                                    <option value="ADMIN" ${u_add.role == 'ADMIN' ? 'selected' : ''}>ADMIN</option>
                                </select>
                                <small class="text-danger">${error_role}</small>
                            </div>
                            <div class="form-group col-md-3">
                                <label for="txtStatus">Status</label>
                                <select name="txtStatus" id="txtStatus" class="form-control">
                                    <option value="1" ${u_add.status ? 'selected' : ''}>Active</option>
                                    <option value="0" ${!u_add.status ? 'selected' : ''}>Inactive</option>
                                </select>
                            </div>
                        </div>
                        
                        <button type="submit" class="btn btn-primary">Create User</button>
                    </form>
                </div>
            </div>

            <%-- =========== 2. Form TÌM KIẾM =========== --%>
       

            <%-- =========== 3. Bảng DANH SÁCH NGƯỜI DÙNG =========== --%>
            <c:if test="${empty requestScope.ADMIN_USER_LIST}">
                <div class="alert alert-info">No users found.</div>
            </c:if>
                
            <c:if test="${not empty requestScope.ADMIN_USER_LIST}">
                <table class="table table-hover table-bordered">
                    <thead class="thead-dark">
                        <tr>
                            <th>Avatar</th>
                            <th>Username</th>
                            <th>Full Name</th>
                            <th>Email</th>
                            <th>Role</th>
                            <th>Status</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="user" items="${requestScope.ADMIN_USER_LIST}">
                            <tr>
                                <td class="text-center">
                                    <img src="${not empty user.avatarBase64 ? user.avatarBase64 : 'https://via.placeholder.com/50'}" 
                                         alt="Avatar" class="rounded" style="width: 50px; height: 50px; object-fit: cover;">
                                </td>
                                <td>${user.userName}</td>
                                <td>${user.fullName}</td>
                                <td>${user.email}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${user.role == 'ADMIN'}">
                                            <span class="badge badge-danger">ADMIN</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="badge badge-info">USER</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <c:choose>
                                        <c:when test="${user.status}">
                                            <span class="badge badge-success">Active</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="badge badge-secondary">Inactive</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <%-- Nút Update (Mở Modal) --%>
                                    <button class="btn btn-sm btn-primary" 
                                            data-toggle="modal" 
                                            data-target="#updateUserModal"
                                            data-userid="${user.userName}"
                                            data-fullname="${user.fullName}"
                                            data-avatar="${user.avatarBase64}">
                                        Update
                                    </button>
                                    
                                    <%-- Nút Delete (Soft Delete) --%>
                                    <c:if test="${user.status}">
                                        <a href="MainController?txtAction=softDeleteUserByAdmin&userID=${user.userName}"
                                           class="btn btn-sm btn-danger"
                                           onclick="return confirm('Are you sure you want to deactivate this user (${user.userName})?');">
                                            Deactivate
                                        </a>
                                    </c:if>
                                    
                                    <%-- Nút Activate --%>
                                    <c:if test="${!user.status}">
                                        <a href="MainController?txtAction=activateUserByAdmin&userID=${user.userName}"
                                           class="btn btn-sm btn-success"
                                           onclick="return confirm('Are you sure you want to activate this user (${user.userName})?');">
                                            Activate
                                        </a>
                                    </c:if>
                                    
                                    
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:if>

        </div>
    </div>
</div>

<%-- =========== 4. MODAL CẬP NHẬT =========== --%>
<div class="modal fade" id="updateUserModal" tabindex="-1" role="dialog" aria-labelledby="updateUserModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <form action="MainController" method="POST">
                <input type="hidden" name="txtAction" value="updateUserByAdmin">
                <input type="hidden" name="txtUserID" id="modalUserID">
                <input type="hidden" name="txtAvatarBase64" id="modalAvatarBase64">
                
                <div class="modal-header">
                    <h5 class="modal-title" id="updateUserModalLabel">Update User</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <div class="form-group">
                        <label>Username</label>
                        <input type="text" id="modalUsername" class="form-control" readonly>
                    </div>
                    <div class="form-group">
                        <label for="modalFullName">Full Name</label>
                        <input type="text" name="txtFullName" id="modalFullName" class="form-control" required>
                    </div>
                    
                    <div class="form-group">
                        <label class="form-label">Avatar</label>
                        <input type="file" id="modalAvatarFile" accept="image/png, image/jpeg, image/gif" class="form-control" />
                        <div class="mt-3 text-center">
                            <img id="modalAvatarPreview" 
                                 src="" 
                                 alt="Avatar Preview" 
                                 class="rounded border" 
                                 style="max-width: 150px; max-height: 150px; display: none;">
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                    <button type="submit" class="btn btn-primary">Save Changes</button>
                </div>
            </form>
        </div>
    </div>
</div>

<%-- =========== 5. JavaScript (cho Modal và Base64) =========== --%>
<script>
    // JS cho Modal Cập nhật
    $('#updateUserModal').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget); // Nút đã click
        
        // Lấy data- attributes từ nút
        var userID = button.data('userid');
        var fullName = button.data('fullname');
        var avatarBase64 = button.data('avatar');
        
        // Cập nhật giá trị cho modal
        var modal = $(this);
        modal.find('#modalUserID').val(userID);
        modal.find('#modalUsername').val(userID);
        modal.find('#modalFullName').val(fullName);
        modal.find('#modalAvatarBase64').val(avatarBase64); // Set base64 ẩn
        
        var preview = modal.find('#modalAvatarPreview');
        if (avatarBase64 && avatarBase64.length > 0) {
            preview.attr('src', avatarBase64);
            preview.css('display', 'block');
        } else {
            preview.attr('src', '');
            preview.css('display', 'none');
        }
        
        // Reset file input
        modal.find('#modalAvatarFile').val('');
    });

    // JS cho chuyển đổi ảnh sang Base64
    document.getElementById('modalAvatarFile').addEventListener('change', function(event) {
        var file = event.target.files[0];
        if (file) {
            var reader = new FileReader();
            reader.onload = function(e) {
                var base64String = e.target.result;
                // 1. Gán chuỗi Base64 vào input ẩn
                document.getElementById('modalAvatarBase64').value = base64String;
                // 2. Hiển thị ảnh preview
                var preview = document.getElementById('modalAvatarPreview');
                preview.src = base64String;
                preview.style.display = 'block';
            };
            reader.readAsDataURL(file); // Đọc file thành chuỗi Base64
        }
    });
</script>


<jsp:include page="includes/footer.jsp" />