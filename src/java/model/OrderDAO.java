package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import model.AddressDTO;
import model.CartDAO;
import model.CartItemDTO;
import model.OrderDTO;
import model.OrderDetailDTO;
import model.UserDTO;
import utils.DBUtils;

public class OrderDAO {

    // (Bạn có thể cần thêm các hằng số SQL ở đây)
    private static final String INSERT_ORDER = "INSERT INTO tblOrders (UserID, TotalPrice, ShippingAddress, ShippingPhone) VALUES (?, ?, ?, ?)";
    private static final String INSERT_ORDER_DETAIL = "INSERT INTO tblOrderDetails (OrderID, CardCode, Quantity, PriceAtTime) VALUES (?, ?, ?, ?)";
    private static final String GET_ALL_ORDERS = "SELECT o.OrderID, o.OrderDate, o.TotalPrice, o.Status, u.fullName, o.UserID, o.ShippingAddress, o.ShippingPhone FROM tblOrders o JOIN tblUsers u ON o.UserID = u.userID ORDER BY o.OrderDate DESC";
    private static final String GET_ORDER_DETAILS = "SELECT d.OrderDetailID, d.OrderID, d.CardCode, d.Quantity, d.PriceAtTime, c.cardName, c.imageUrl FROM tblOrderDetails d JOIN tblCards c ON d.CardCode = c.cardCode WHERE d.OrderID = ?";

    /**
     * Tạo đơn hàng mới, bao gồm lưu Order và OrderDetails, và xóa giỏ hàng. Sử
     * dụng Transaction để đảm bảo toàn vẹn dữ liệu.
     */
    public boolean createOrder(UserDTO user, List<CartItemDTO> cart, double totalBill, AddressDTO shipAddress) throws SQLException {
        Connection con = null;
        PreparedStatement psOrder = null;
        PreparedStatement psDetail = null;
        ResultSet rsKeys = null;
        boolean check = false;
        int newOrderID = -1;

        try {
            con = DBUtils.getConnection();
            if (con != null) {
                // 1. Bắt đầu Transaction
                con.setAutoCommit(false);

                // 2. Insert vào tblOrders
                psOrder = con.prepareStatement(INSERT_ORDER, Statement.RETURN_GENERATED_KEYS);
                psOrder.setString(1, user.getUserName());
                psOrder.setDouble(2, totalBill);
                psOrder.setString(3, shipAddress.getDetailedAddress()); //
                psOrder.setString(4, shipAddress.getPhoneNumber()); //

                psOrder.executeUpdate();
                rsKeys = psOrder.getGeneratedKeys();
                if (rsKeys.next()) {
                    newOrderID = rsKeys.getInt(1); // Lấy OrderID vừa tạo
                }

                if (newOrderID > 0) {
                    // 3. Insert vào tblOrderDetails (dùng Batch)
                    psDetail = con.prepareStatement(INSERT_ORDER_DETAIL);
                    for (CartItemDTO item : cart) {
                        psDetail.setInt(1, newOrderID);
                        psDetail.setString(2, item.getCardCode()); // (lấy từ CartItemDTO)
                        psDetail.setInt(3, item.getQuantity()); //
                        psDetail.setDouble(4, item.getPrice()); // (Giá của 1 sản phẩm)
                        psDetail.addBatch();
                    }
                    psDetail.executeBatch();

                    // 4. Xóa giỏ hàng (trong tblCarts)
                    // === PHẦN SỬA CHỮA QUAN TRỌNG ===
                    CartDAO cartDAO = new CartDAO();
                    // Gọi hàm clearCart MỚI, truyền 'con' (Connection) vào
                    boolean cartCleared = cartDAO.clearCart(user.getUserName());

                    // 5. Commit Transaction (CHỈ KHI XÓA GIỎ HÀNG THÀNH CÔNG)
                    if (cartCleared) {
                        con.commit();
                        check = true;
                    } else {
                        // Nếu xóa giỏ hàng lỗi, hủy bỏ mọi thứ
                        con.rollback();
                        System.out.println("--- ORDERDAO LỖI: Không thể xóa giỏ hàng, rollback đơn hàng.");
                    }
                    // === KẾT THÚC PHẦN SỬA ===

                } else {
                    con.rollback(); // Rollback nếu không lấy được OrderID
                }
            }
        } catch (Exception e) {
            if (con != null) {
                con.rollback(); // Rollback nếu có lỗi
            }
            e.printStackTrace();
        } finally {
            // 6. Đóng tài nguyên và trả về AutoCommit
            if (rsKeys != null) {
                rsKeys.close();
            }
            if (psDetail != null) {
                psDetail.close();
            }
            if (psOrder != null) {
                psOrder.close();
            }
            if (con != null) {
                con.setAutoCommit(true);
                con.close();
            }
        }
        return check;
    }

    /**
     * Lấy tất cả đơn hàng (cho admin/staff)
     */
    public List<OrderDTO> getAllOrders() throws SQLException {
        List<OrderDTO> list = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DBUtils.getConnection();
            if (con != null) {
                ps = con.prepareStatement(GET_ALL_ORDERS);
                rs = ps.executeQuery();
                while (rs.next()) {
                    OrderDTO order = new OrderDTO();
                    order.setOrderID(rs.getInt("OrderID"));
                    order.setOrderDate(rs.getTimestamp("OrderDate").toLocalDateTime());
                    order.setTotalPrice(rs.getDouble("TotalPrice"));
                    order.setStatus(rs.getString("Status"));
                    order.setFullName(rs.getString("fullName")); // Tên người đặt
                    order.setUserID(rs.getString("UserID"));
                    order.setShippingAddress(rs.getString("ShippingAddress"));
                    order.setShippingPhone(rs.getString("ShippingPhone"));
                    list.add(order);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return list;
    }

    /**
     * Lấy chi tiết của một đơn hàng
     */
    public List<OrderDetailDTO> getOrderDetails(int orderID) throws SQLException {
        List<OrderDetailDTO> list = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DBUtils.getConnection();
            if (con != null) {
                ps = con.prepareStatement(GET_ORDER_DETAILS);
                ps.setInt(1, orderID);
                rs = ps.executeQuery();
                while (rs.next()) {
                    OrderDetailDTO detail = new OrderDetailDTO();
                    detail.setOrderDetailID(rs.getInt("OrderDetailID"));
                    detail.setOrderID(rs.getInt("OrderID"));
                    detail.setCardCode(rs.getString("CardCode"));
                    detail.setQuantity(rs.getInt("Quantity"));
                    detail.setPriceAtTime(rs.getDouble("PriceAtTime"));
                    detail.setCardName(rs.getString("cardName"));
                    detail.setImageUrl(rs.getString("imageUrl"));
                    list.add(detail);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return list;
    }
}
