/*
         * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
         * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.CardDTO;
import model.UserDTO;
import model.UserDAO;
import utils.EmailUtils;
import model.AddressDAO;
import model.AddressDTO;

@WebServlet(name = "UserController", urlPatterns = {"/UserController"})
public class UserController extends HttpServlet {


private void processLogin(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

    String u = request.getParameter("user");
    String p = request.getParameter("pass");
    UserDAO userDAO = new UserDAO();

    UserDTO user = null;
    if (userDAO.login(u, p)) {
        user = userDAO.getUserById(u);
        if (user == null) user = userDAO.getUserByEmail(u);
    }

    // Chỉ cho vào khi user tồn tại và đang active
    if (user != null && user.isStatus()) {
        request.getSession().setAttribute("user", user);

        // dữ liệu trang chủ
        model.CardDAO cardDAO = new model.CardDAO();
        model.SetDAO setDAO = new model.SetDAO();
        request.setAttribute("listC", cardDAO.getAllCard());
        request.setAttribute("listCater", setDAO.getAllSet());
        request.setAttribute("p", cardDAO.getMostExpensiveCard());

        request.getRequestDispatcher("home.jsp").forward(request, response);
        return;
    }

    request.setAttribute("msg", "Username, Password incorrect or Account is deactivated.");
    request.getRequestDispatcher("login.jsp").forward(request, response);
}

    
    private void processLogout(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.invalidate();
        response.sendRedirect("login.jsp");
    }

    private void processSearchUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String keyword = request.getParameter("txtName");
        UserDAO userDAO = new UserDAO();
        ArrayList<UserDTO> listOfUsers = new ArrayList<>();
        if (keyword == null || keyword.trim().length() == 0) {
         
        } else {
        
        }
        request.setAttribute("listOfUsers", listOfUsers);
        request.setAttribute("keyword", keyword);
        request.getRequestDispatcher("home.jsp").forward(request, response);
    }

    private void processAddUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UserDAO userDAO = new UserDAO();
        String txtUsername = request.getParameter("txtUsername");
        String txtPassword = request.getParameter("txtPassword");
        String txtFullName = request.getParameter("txtFullName");
        String txtRole = request.getParameter("txtRole");
        String txtStatus = request.getParameter("txtStatus");
        boolean status = (txtStatus != null && txtStatus.equals("1")) ? true : false;

        UserDTO user = new UserDTO(txtUsername, txtRole, txtPassword, txtFullName, txtRole, status, txtRole, LocalDateTime.MIN, null);
        String error_userName = "";
        String error_password = "";
        String error_fullName = "";
        String error_role = "";

        boolean hasError = false;

        if (txtUsername == null || txtUsername.trim().isEmpty()) {
            error_userName = "Username can't left empty";
            hasError = true;
        } else {
            if (userDAO.getUserById(txtUsername) != null) {
                error_userName = "Username is already existed!";
                hasError = true;
            }
        }

        String regex = "^(?=.*[!@#$%^&*(),.?\":{}|<>])[A-Za-z\\d!@#$%^&*(),.?\":{}|<>]{8,}$";
        if (!txtPassword.matches(regex) || txtPassword == null) {
            error_password = "Password must have atleast 8 letters, at least 1 special letter!";
            hasError = true;
        }

        if (txtFullName == null || txtFullName.trim().isEmpty()) {
            error_fullName = "Full Name can't left empty";
            hasError = true;
        }

        if (txtRole == null || txtRole.trim().isEmpty()) {
            error_role = "Role can't left empty";
            hasError = true;
        }

        String error = "";
        if (!hasError && !userDAO.insert(user)) {
            error = "Can't add new User!";
            hasError = true;
        }

        if (hasError) {
            request.setAttribute("u", user);
            request.setAttribute("error_userName", error_userName);
            request.setAttribute("error_password", error_password);
            request.setAttribute("error_fullName", error_fullName);
            request.setAttribute("error_role", error_role);
            request.setAttribute("error", error);
            request.getRequestDispatcher("userForm.jsp").forward(request, response);
        }

        processSearchUser(request, response);
    }

    private void processCallUpdateUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UserDAO userDAO = new UserDAO();
        String username = request.getParameter("username");
        UserDTO user = userDAO.getUserById(username);
        if (user != null) {
            request.setAttribute("update", true);
            request.setAttribute("u", user);
        }
        request.getRequestDispatcher("userForm.jsp").forward(request, response);
    }

    private void processOurCollection(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        model.CardDAO cardDAO = new model.CardDAO();
        model.SetDAO setDAO = new model.SetDAO();

        java.util.List<model.CardDTO> listC = cardDAO.getAllCard();
        java.util.List<model.SetDTO> listCater = setDAO.getAllSet();
        CardDTO staffPick = cardDAO.getMostExpensiveCard();

        request.setAttribute("listC", listC);
        request.setAttribute("listCater", listCater);
        request.setAttribute("p", staffPick);

        request.getRequestDispatcher("home.jsp").forward(request, response);
    }

    private void processRegister(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = "login.jsp";

        String username = request.getParameter("user");
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");

        try {
            String password = request.getParameter("pass");
            String rePassword = request.getParameter("repass");

            UserDAO userDAO = new UserDAO();
            String msg = "";
            boolean hasError = false;

            String regex = "^(?=.*[!@#$%^&*(),.?\":{}|<>])[A-Za-z\\d!@#$%^&*(),.?\":{}|<>]{8,}$";
            if (!password.matches(regex) || password == null) {
                msg = "Password must have at least 8 characters and 1 special character!";
                hasError = true;
            } else if (!password.equals(rePassword)) {
                msg = "Passwords do not match!";
                hasError = true;
            }
            else if (userDAO.getUserById(username) != null) {
                msg = "Username '" + username + "' already exists!";
                hasError = true;
            } 
            else if (userDAO.getUserByEmail(email) != null) {
                msg = "Email '" + email + "' already registered!";
                hasError = true;
            }

            if (hasError) {
                request.setAttribute("msg", msg);
                request.setAttribute("username", username);
                request.setAttribute("fullName", fullName);
                request.setAttribute("email", email);
                request.setAttribute("showSignup", true); 

            } else {
                UserDTO newUser = new UserDTO(username, email, password, fullName, "", true, "USER", java.time.LocalDateTime.now(), null);
                boolean checkInsert = userDAO.insert(newUser);

                if (checkInsert) {
                    try {
                        String toEmail = email;
                        EmailUtils.sendRegistrationEmail(toEmail, fullName, username);
                    } catch (Exception e) {
                        log("Error sending registration email: " + e.getMessage());
                        e.printStackTrace();
                    }
                    response.sendRedirect("login.jsp?msg=Registration successful! Please sign in.");
                    return;
                } else {
                    request.setAttribute("msg", "An error occurred during registration. Please try again.");
                    request.setAttribute("showSignup", true);
                    request.setAttribute("username", username);
                    request.setAttribute("fullName", fullName);
                    request.setAttribute("email", email);
                }
            }
        } catch (Exception e) {
            log("Error at processRegister: " + e.toString());
            request.setAttribute("msg", "An unexpected error occurred.");
            e.printStackTrace(); 

            request.setAttribute("showSignup", true);
            request.setAttribute("username", username);
            request.setAttribute("fullName", fullName);
            request.setAttribute("email", email);
        }

        // Chỉ forward nếu có lỗi
        request.getRequestDispatcher(url).forward(request, response);
    }

    private void processViewProfile(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String url = "login.jsp";
        HttpSession session = request.getSession(false);

        if (session != null && session.getAttribute("user") != null) {
            UserDTO sessionUser = (UserDTO) session.getAttribute("user");
            UserDAO userDAO = new UserDAO();

            UserDTO freshUser = userDAO.getUserById(sessionUser.getUserName());

            if (freshUser != null) {
                request.setAttribute("profile", freshUser);

                ///lay ra dia chi
                AddressDAO addressDAO = new AddressDAO();
                ArrayList<AddressDTO> addressList = addressDAO.getAddressesByUserID(sessionUser.getUserName());
                request.setAttribute("ADDRESS_LIST", addressList);

                if (session.getAttribute("msg_success") != null) {
                    request.setAttribute("msg_success", session.getAttribute("msg_success"));
                    session.removeAttribute("msg_success"); // 
                }
                if (session.getAttribute("msg_error") != null) {
                    request.setAttribute("msg_error", session.getAttribute("msg_error"));
                    session.removeAttribute("msg_error"); // 
                }

                url = "manageAccount.jsp";
            } else {
                session.invalidate();
                request.setAttribute("msg", "Account not found. Please login again.");
            }
        } else {
            request.setAttribute("msg", "Please login to manage your account.");
        }

        request.getRequestDispatcher(url).forward(request, response);
    }

    private void processUpdateProfile(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String url = "manageAccount.jsp";

        try {
            UserDTO sessionUser = (UserDTO) session.getAttribute("user");
            String username = sessionUser.getUserName();

            String fullName = request.getParameter("txtFullName");
            String email = request.getParameter("txtEmail");

            // --- THÊM DÒNG NÀY ---
            String avatarBase64 = request.getParameter("txtAvatarBase64");

            UserDAO userDAO = new UserDAO();
            UserDTO userToUpdate = userDAO.getUserById(username);

            userToUpdate.setFullName(fullName);
            userToUpdate.setEmail(email);

            if (avatarBase64 != null && !avatarBase64.isEmpty()) {
                userToUpdate.setAvatarBase64(avatarBase64);
            }

            boolean check = userDAO.updateProfile(userToUpdate);

            if (check) {
                session.setAttribute("user", userToUpdate);
                request.setAttribute("msg_success", "Profile updated successfully!");
                // Gửi user DTO mới nhất sang JSP
                request.setAttribute("profile", userToUpdate);
            } else {
                request.setAttribute("msg_error", "Error: Could not update profile.");

                request.setAttribute("profile", userToUpdate);
            }

        } catch (Exception e) {
            log("Error at processUpdateProfile: " + e.toString());
            request.setAttribute("msg_error", "An unexpected error occurred.");
            e.printStackTrace();
        }

        request.getRequestDispatcher(url).forward(request, response);
    }

    private void processAddAddress(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String url = "MainController?txtAction=viewProfile"; // Luôn redirect về profile
        HttpSession session = request.getSession(false);

        try {
            UserDTO loginUser = (UserDTO) session.getAttribute("user");
            if (loginUser == null) {
                response.sendRedirect("login.jsp");
                return;
            }

            String receiverName = request.getParameter("txtReceiverName");
            String phoneNumber = request.getParameter("txtPhoneNumber");
            String detailedAddress = request.getParameter("txtDetailedAddress");
            String userID = loginUser.getUserName();

            AddressDTO newAddr = new AddressDTO();
            newAddr.setUserID(userID);
            newAddr.setReceiverName(receiverName);
            newAddr.setPhoneNumber(phoneNumber);
            newAddr.setDetailedAddress(detailedAddress);
            AddressDAO addressDAO = new AddressDAO();

            boolean check = addressDAO.insertAddress(newAddr); //

            if (check) {

                session.setAttribute("msg_success", "Address added successfully!");
            } else {
                session.setAttribute("msg_error", "Could not add address. Please check your inputs.");
            }

        } catch (Exception e) {
            log("Error at processAddAddress: " + e.toString());
            e.printStackTrace();

            session.setAttribute("msg_error", "An error occurred: " + e.getMessage());
        }

        response.sendRedirect(url); // Redirect
    }

    private void processDeleteAddress(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String url = "MainController?txtAction=viewProfile";
        try {
            int addressID = Integer.parseInt(request.getParameter("addressID"));
            AddressDAO addressDAO = new AddressDAO();
            addressDAO.deleteAddress(addressID);
        } catch (Exception e) {
            log("Error at processDeleteAddress: " + e.toString());
        }
        response.sendRedirect(url);
    }

    private void processCallUpdateAddress(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String url = "login.jsp";
        try {
            int addressID = Integer.parseInt(request.getParameter("addressID"));
            AddressDAO addressDAO = new AddressDAO();
            AddressDTO addr = addressDAO.getAddressByID(addressID);

            if (addr != null) {
                request.setAttribute("ADDRESS_TO_UPDATE", addr);
                url = "updateAddress.jsp"; // Trang JSP mới
            } else {
                url = "MainController?txtAction=viewProfile"; // Không tìm thấy
            }
        } catch (Exception e) {
            log("Error at processCallUpdateAddress: " + e.toString());
            url = "MainController?txtAction=viewProfile";
        }
        request.getRequestDispatcher(url).forward(request, response);
    }

    private void processUpdateAddress(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String url = "MainController?txtAction=viewProfile";
        HttpSession session = request.getSession(false);

        try {
            UserDTO loginUser = (UserDTO) session.getAttribute("user");
            if (loginUser == null) {
                response.sendRedirect("login.jsp");
                return;
            }

            int addressID = Integer.parseInt(request.getParameter("addressID"));
            String receiverName = request.getParameter("txtReceiverName");
            String phoneNumber = request.getParameter("txtPhoneNumber");
            String detailedAddress = request.getParameter("txtDetailedAddress");
            String userID = loginUser.getUserName();

            AddressDTO addr = new AddressDTO(addressID, userID, receiverName, phoneNumber, detailedAddress);
            AddressDAO addressDAO = new AddressDAO();
            addressDAO.updateAddress(addr);

        } catch (Exception e) {
            log("Error at processUpdateAddress: " + e.toString());
        }

        response.sendRedirect(url);
    }


    private void processCallManageAccountByAdmin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String url = "manageAccountByAdmin.jsp";
        try {
            UserDAO userDAO = new UserDAO();
            ArrayList<UserDTO> userList = userDAO.getAllUser();
            request.setAttribute("ADMIN_USER_LIST", userList);

        } catch (Exception e) {
            log("Error at processCallManageAccountByAdmin: " + e.toString());
            request.setAttribute("msg_error", "Could not load user list.");
        }
        request.getRequestDispatcher(url).forward(request, response);
    }


    private void processSearchUserByAdmin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String url = "manageAccountByAdmin.jsp";
        try {
            String keyword = request.getParameter("txtKeyword");
            UserDAO userDAO = new UserDAO();
            ArrayList<UserDTO> userList;

            if (keyword == null || keyword.trim().isEmpty()) {
                userList = userDAO.getAllUser();
            } else {
                userList = userDAO.getAllUserByName(keyword);
            }

            request.setAttribute("ADMIN_USER_LIST", userList);
            request.setAttribute("txtKeyword", keyword); // Giữ lại keyword trên ô search

        } catch (Exception e) {
            log("Error at processSearchUserByAdmin: " + e.toString());
            request.setAttribute("msg_error", "Error during search.");
        }
        request.getRequestDispatcher(url).forward(request, response);
    }


    private void processSoftDeleteUserByAdmin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String url = "MainController?txtAction=callManageAccountByAdmin"; // Redirect về trang
        HttpSession session = request.getSession();

        try {
            String userID = request.getParameter("userID");
            UserDAO userDAO = new UserDAO();
            boolean check = userDAO.softDelete(userID);

            if (check) {
                session.setAttribute("msg_success", "User " + userID + " has been deactivated.");
            } else {
                session.setAttribute("msg_error", "Could not deactivate user.");
            }

        } catch (Exception e) {
            log("Error at processSoftDeleteUserByAdmin: " + e.toString());
            session.setAttribute("msg_error", "An error occurred.");
        }
        response.sendRedirect(url);
    }
    
    private void processActivateUserByAdmin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String url = "MainController?txtAction=callManageAccountByAdmin"; // Redirect về trang
        HttpSession session = request.getSession();

        try {
            String userID = request.getParameter("userID");
            UserDAO userDAO = new UserDAO();
            boolean check = userDAO.activateUser(userID); // Gọi hàm mới

            if (check) {
                session.setAttribute("msg_success", "User " + userID + " has been activated.");
            } else {
                session.setAttribute("msg_error", "Could not activate user.");
            }

        } catch (Exception e) {
            log("Error at processActivateUserByAdmin: " + e.toString());
            session.setAttribute("msg_error", "An error occurred.");
        }
        response.sendRedirect(url);
    }

    /**
     * (Admin) Thêm một user mới từ trang quản lý.
     */
    private void processAddUserByAdmin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String url = "manageAccountByAdmin.jsp"; // Trang lỗi
        String urlSuccess = "MainController?txtAction=callManageAccountByAdmin"; // Trang thành công

        UserDAO userDAO = new UserDAO();
        String txtUsername = request.getParameter("txtUsername");
        String txtPassword = request.getParameter("txtPassword");
        String txtRePassword = request.getParameter("txtRePassword");
        String txtFullName = request.getParameter("txtFullName");
        String txtEmail = request.getParameter("txtEmail");
        String txtRole = request.getParameter("txtRole");
        String txtStatus = request.getParameter("txtStatus");
        boolean status = (txtStatus != null && txtStatus.equals("1"));

        // Giữ lại thông tin nếu có lỗi
        UserDTO user = new UserDTO(txtUsername, txtEmail, txtPassword, txtFullName, null, status, txtRole, LocalDateTime.now(), null);

        String error_userName = "";
        String error_password = "";
        String error_fullName = "";
        String error_email = "";
        String error_role = "";

        boolean hasError = false;

        // Validation
        if (txtUsername == null || txtUsername.trim().isEmpty()) {
            error_userName = "Username can't be empty";
            hasError = true;
        } else if (userDAO.getUserById(txtUsername) != null) {
            error_userName = "Username is already existed!";
            hasError = true;
        }

        if (txtEmail == null || txtEmail.trim().isEmpty()) {
            error_email = "Email can't be empty";
            hasError = true;
        } else if (userDAO.getUserByEmail(txtEmail) != null) {
            error_email = "Email is already registered!";
            hasError = true;
        }

        String regex = "^(?=.*[!@#$%^&*(),.?\":{}|<>])[A-Za-z\\d!@#$%^&*(),.?\":{}|<>]{8,}$";
        if (txtPassword == null || !txtPassword.matches(regex)) {
            error_password = "Password must have at least 8 letters, 1 special character!";
            hasError = true;
        } else if (!txtPassword.equals(txtRePassword)) {
            error_password = "Passwords do not match!";
            hasError = true;
        }

        if (txtFullName == null || txtFullName.trim().isEmpty()) {
            error_fullName = "Full Name can't be empty";
            hasError = true;
        }

        if (txtRole == null || (!txtRole.equals("ADMIN") && !txtRole.equals("USER"))) {
            error_role = "Invalid role";
            hasError = true;
        }

        if (hasError) {
            request.setAttribute("u_add", user); // Đặt tên khác (u_add) để không trùng với user trong list
            request.setAttribute("error_userName", error_userName);
            request.setAttribute("error_password", error_password);
            request.setAttribute("error_fullName", error_fullName);
            request.setAttribute("error_email", error_email);
            request.setAttribute("error_role", error_role);

            // Phải tải lại danh sách user khi forward
            ArrayList<UserDTO> userList = userDAO.getAllUser();
            request.setAttribute("ADMIN_USER_LIST", userList);

            request.getRequestDispatcher(url).forward(request, response);
        } else {
            // Không lỗi, tiến hành insert
            boolean checkInsert = userDAO.insert(user);
            HttpSession session = request.getSession();
            if (checkInsert) {
                session.setAttribute("msg_success", "User " + txtUsername + " created successfully!");
                response.sendRedirect(urlSuccess);
            } else {
                session.setAttribute("msg_error", "Could not create user.");
                response.sendRedirect(urlSuccess);
            }
        }
    }

  
    private void processUpdateUserByAdmin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String url = "MainController?txtAction=callManageAccountByAdmin";
        HttpSession session = request.getSession();

        try {
            String userID = request.getParameter("txtUserID");
            String fullName = request.getParameter("txtFullName");
            String avatarBase64 = request.getParameter("txtAvatarBase64");

            UserDAO userDAO = new UserDAO();
            UserDTO userToUpdate = userDAO.getUserById(userID); 

            if (userToUpdate != null) {
                userToUpdate.setFullName(fullName);
                if (avatarBase64 != null && !avatarBase64.isEmpty()) {
                    userToUpdate.setAvatarBase64(avatarBase64);
                }

                boolean check = userDAO.updateProfile(userToUpdate);

                if (check) {
                    session.setAttribute("msg_success", "Profile for " + userID + " updated successfully!");
                } else {
                    session.setAttribute("msg_error", "Error: Could not update profile for " + userID);
                }
            } else {
                session.setAttribute("msg_error", "User " + userID + " not found.");
            }

        } catch (Exception e) {
            log("Error at processUpdateUserByAdmin: " + e.toString());
            session.setAttribute("msg_error", "An unexpected error occurred.");
            e.printStackTrace();
        }

        response.sendRedirect(url);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String txtAction = request.getParameter("txtAction");

        if (txtAction == null) {
            txtAction = "login";
        }

        if (txtAction.equals("login")) {
            processLogin(request, response);
        } else if (txtAction.equals("logout")) {
            processLogout(request, response);
        } else if (txtAction.equals("searchUser")) { // kh su dung
            processSearchUser(request, response);
        } else if (txtAction.equals("addUser")) { // kh su dung 
            processAddUser(request, response);
        } else if (txtAction.equals("callUpdateUser")) {
            processCallUpdateUser(request, response);
        } else if (txtAction.equals("ourCollection")) {
            processOurCollection(request, response);
        } else if (txtAction.equals("signup")) {
            processRegister(request, response);
        } else if (txtAction.equals("viewProfile")) {
            processViewProfile(request, response);
        } else if (txtAction.equals("updateProfile")) {
            processUpdateProfile(request, response);
        } else if (txtAction.equals("addAddress")) {
            processAddAddress(request, response);
        } else if (txtAction.equals("deleteAddress")) {
            processDeleteAddress(request, response);
        } else if (txtAction.equals("callUpdateAddress")) {
            processCallUpdateAddress(request, response);
        } else if (txtAction.equals("updateAddress")) {
            processUpdateAddress(request, response);
            
            //// PHAN DANH CHO ADMIN CRUD USER
        } else if (txtAction.equals("callManageAccountByAdmin")) {
            processCallManageAccountByAdmin(request, response);
        } else if (txtAction.equals("searchUserByAdmin")) {
            processSearchUserByAdmin(request, response);
        } else if (txtAction.equals("softDeleteUserByAdmin")) {
            processSoftDeleteUserByAdmin(request, response);
        } else if (txtAction.equals("addUserByAdmin")) {
            processAddUserByAdmin(request, response);
        } else if (txtAction.equals("updateUserByAdmin")) {
            processUpdateUserByAdmin(request, response);
        
        } else if (txtAction.equals("activateUserByAdmin")) {
            processActivateUserByAdmin(request, response);

    }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
    
