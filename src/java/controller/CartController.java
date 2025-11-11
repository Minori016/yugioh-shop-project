package controller;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.CardDAO;
import model.CardDTO;
import model.CartDAO;
import model.CartDTO;
import model.UserDAO;
import model.UserDTO;

// Tên @WebServlet phải khớp với tên trong MainController
@WebServlet(name = "CartController", urlPatterns = {"/CartController"})
public class CartController extends HttpServlet {

    // Định nghĩa các trang sẽ điều hướng tới
    private static final String ERROR = "error.jsp"; // (Bạn tự tạo trang này)
    private static final String SUCCESS = "Cart.jsp"; // Trang giỏ hàng
    private static final String HOME = "home.jsp";    // Trang chủ

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String txtAction = request.getParameter("txtAction");

        if (txtAction == null) {
            txtAction = "login";
        }

        if (txtAction.equals("addToCart")) {
            processAddToCart(request, response);
        } else if (txtAction.equals("removeFromCart")) {
            processRemoveFromCart(request, response);
        } else if (txtAction.equals("updateFromCart")) {
            processUpdateFromCart(request, response);
        }
    }

    private void processAddToCart(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String url = "home.jsp"; // (Hoặc bạn muốn chuyển hướng về đâu)

        try {
            // 1. Lấy thông tin
            String cardID = request.getParameter("cardID");
            String userID = request.getParameter("userID");

            // --- THÊM 2 DÒNG NÀY ---
            String quantityStr = request.getParameter("quantity");
            // Mặc định là 1 nếu người dùng cố tình sửa HTML
            int quantityToAdd = 1;
            try {
                quantityToAdd = Integer.parseInt(quantityStr);
                if (quantityToAdd <= 0) {
                    quantityToAdd = 1; // Đảm bảo số dương
                }
            } catch (NumberFormatException e) {
                // Bỏ qua, vẫn dùng 1
            }
            // -----------------------

            CartDAO cartDAO = new CartDAO();

            // --- SỬA DÒNG NÀY ---
            // boolean success = cartDAO.updateCart(userID, cardID); // CŨ
            boolean success = cartDAO.updateCart(userID, cardID, quantityToAdd); // MỚI
            // -----------------------

            if (success) {
                session.setAttribute("cartMessage", "Đã thêm " + quantityToAdd + " sản phẩm vào giỏ!");
            } else {
                session.setAttribute("cartMessage", "Lỗi! Không thể thêm vào giỏ!");
            }

        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("cartMessage", "Lỗi hệ thống khi thêm vào giỏ!");
        }

        // Chuyển hướng (để kích hoạt popup và scroll-saving)
        response.sendRedirect(url);
    }

    private void processRemoveFromCart(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 1. Lấy thông tin
        String cardID = request.getParameter("cardID");
        HttpSession session = request.getSession();

        // Đặt URL chuyển hướng mặc định là Cart.jsp
        String url = "Cart.jsp";

        try {
            // 2. Lấy userID từ session (Giống như CartFilter)
            UserDTO user = (UserDTO) session.getAttribute("user");
            String userID = user.getUserName(); // (Hoặc getUserName() tùy bạn đặt)

            // 3. Gọi DAO để xóa
            if (cardID != null && userID != null) {
                CartDAO dao = new CartDAO();
                boolean success = dao.removeFromCart(userID, cardID);

                // 4. Đặt thông báo vào session
                if (success) {
                    session.setAttribute("cartMessage", "Đã xóa sản phẩm khỏi giỏ hàng!");
                } else {
                    session.setAttribute("cartMessage", "Lỗi! Không thể xóa sản phẩm.");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("cartMessage", "Lỗi hệ thống: " + e.getMessage());
        }

        // 5. Chuyển hướng về trang giỏ hàng
        // (Trang này sẽ tải lại và kích hoạt CartFilter)
        response.sendRedirect(url);
    }

    private void processUpdateFromCart(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String url = "Cart.jsp"; // Luôn chuyển hướng về trang Giỏ hàng

        try {
            // 1. Lấy thông tin từ form
            String cardID = request.getParameter("cardID");
            String quantityStr = request.getParameter("quantity"); // Lấy số lượng (dạng String)

            // 2. Lấy userID từ session
            UserDTO user = (UserDTO) session.getAttribute("user");
            String userID = user.getUserName(); // (Hoặc getUserName() tùy bạn đặt)

            // 3. Chuyển đổi số lượng sang int (Rất quan trọng)
            // Dùng try-catch để tránh lỗi nếu người dùng nhập "abc"
            int newQuantity = Integer.parseInt(quantityStr);

            // 4. Gọi DAO để cập nhật
            CartDAO dao = new CartDAO();
            boolean success = dao.updateQuantity(userID, cardID, newQuantity);

            // 5. Đặt thông báo vào session
            if (success) {
                session.setAttribute("cartMessage", "Đã cập nhật số lượng thành công!");
            } else {
                session.setAttribute("cartMessage", "Lỗi! Không thể cập nhật.");
            }

        } catch (NumberFormatException e) {
            // Bắt lỗi nếu quantityStr không phải là số
            session.setAttribute("cartMessage", "Lỗi: Số lượng không hợp lệ.");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("cartMessage", "Lỗi hệ thống: " + e.getMessage());
        }

        // 6. Chuyển hướng về Cart.jsp
        // (Việc này sẽ kích hoạt popup và script giữ vị trí cuộn của bạn)
        response.sendRedirect(url);
    }

    // <editor-fold defaultstate="collapsed" desc="doGet/doPost...">
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
    // </editor-fold>
}
