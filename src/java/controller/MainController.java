package controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "MainController", urlPatterns = {"/MainController", "/"})
public class MainController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        String txtAction = request.getParameter("txtAction");
        String url = "login.jsp"; // URL mặc định

        // --- SỬA LỖI 1: Thêm kiểm tra 'null' để tránh lỗi khi mới vào web ---
        if (txtAction != null) {

            // Định nghĩa các mảng action
            String[] userActions = {"login", "logout", "searchUser", "addUser", "callUpdateUser", "signup", "viewProfile", "updateProfile", "addAddress",
                "deleteAddress",
                "callUpdateAddress",
                "updateAddress",
                "callManageAccountByAdmin", // Tải trang quản lý
                "searchUserByAdmin", // Tìm kiếm trên trang
                "addUserByAdmin", // Thêm user từ trang
                "updateUserByAdmin", // Cập nhật user từ trang
                "softDeleteUserByAdmin" ,// Xóa mềm user
                    "activateUserByAdmin"
        };
            String[] productActions = {"categoryProduct",
                "viewDetail",
                "addCard",
                "deleteCard",
                "callUpdateCard",
                "updateCard",
                "searchCard",
                "manageProduct",
                "callManageProduct",
                "callUpdateCard",
                "updateCard"};

            String[] cartActions = {"addToCart", "removeFromCart", "updateFromCart"};

            // Khối if...else if để "chỉ đường"
            if (Arrays.asList(userActions).contains(txtAction)) {
                url = "UserController";
            } else if (Arrays.asList(productActions).contains(txtAction)) {
                url = "ProductController";

            } else if (Arrays.asList(cartActions).contains(txtAction)) {
                url = "CartController";
            }
            // -----------------------------------------------------------

        } // --- Hết khối if (txtAction != null) ---

        // Dòng forward này phải nằm BÊN NGOÀI để luôn được thực thi
        request.getRequestDispatcher(url).forward(request, response);
    }

    // Các phương thức doGet/doPost giữ nguyên
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
