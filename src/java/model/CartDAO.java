package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import static model.UserDAO.hashPassword;
import utils.DBUtils;

public class CartDAO {

    private static final String DELETE_CART_BY_USER = "DELETE * FROM tblCarts WHERE userID = ?";

    ArrayList<CartDTO> list;

    public CartDAO() {
    }

    public ArrayList<CartDTO> getCart(String userId) {
        ArrayList<CartDTO> list = new ArrayList<>();
        try {
            Connection conn = DBUtils.getConnection();
            String sql = "SELECT "
                    + "    c.CardCode, "
                    + "    c.quantity, "
                    + "    t.cardName, "
                    + "    t.ImageUrl, "
                    + "    t.Price AS singlePrice,  -- Lấy giá LIVE từ tblCards "
                    + "    (t.Price * c.quantity) AS totalPrice  -- TÍNH TOÁN tổng giá live "
                    + "FROM "
                    + "    tblCarts AS c"
                    + "JOIN "
                    + "    tblCards AS t ON c.CardCode = t.CardCode"
                    + "WHERE "
                    + "    c.userID = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                CartDTO cart = new CartDTO();
                cart.setUserId(rs.getString("userID"));
                cart.setCardId(rs.getString("CardCode"));
                cart.setQuantity(rs.getInt("quantity"));
                cart.setPrice(rs.getDouble("priceAtTime"));
                cart.setAddTime(rs.getTimestamp("added_at").toLocalDateTime());
                list.add(cart);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public CartDTO getCardInCart(String userId) {
        try {
            Connection conn = DBUtils.getConnection();
            String sql = "SELECT * FROM tblCarts WHERE userID=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                CartDTO cart = new CartDTO();
                cart.setUserId(rs.getString("userID"));
                cart.setCardId(rs.getString("CardCode"));
                cart.setQuantity(rs.getInt("quantity"));
                cart.setPrice(rs.getDouble("priceAtTime"));
                cart.setAddTime(rs.getTimestamp("added_at").toLocalDateTime());
                return cart;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateCart(String userID, String cardID, int quantityToAdd) {
        // 1. Logic kiểm tra của bạn...
        // CardDAO cardDAO = new CardDAO();
        // CardDTO card = cardDAO.getCardByID(cardID); 
        // ^ (Nếu bạn dùng "Lựa chọn 2", bạn không cần 2 dòng này)

        // 2. Chuỗi SQL (vẫn y hệt)
        String sql = "MERGE INTO YUGIOH_TCG_SHOP.dbo.tblCarts AS T "
                + "USING (VALUES (?, ?, ?)) AS S (userID, CardCode, quantity) "
                + "ON (T.userID = S.userID AND T.CardCode = S.CardCode) "
                + "WHEN MATCHED THEN "
                + "    UPDATE SET T.quantity = T.quantity + S.quantity "
                + "WHEN NOT MATCHED BY TARGET THEN "
                + "    INSERT (userID, CardCode, quantity, added_at) "
                + "    VALUES (S.userID, S.CardCode, S.quantity, GETDATE());";

        try ( Connection c = DBUtils.getConnection();  PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, userID);
            ps.setString(2, cardID);

            // --- SỬA DÒNG NÀY ---
            // ps.setInt(3, 1); // CŨ
            ps.setInt(3, quantityToAdd); // MỚI
            // ---------------------

            int i = ps.executeUpdate();
            return i > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<CartItemDTO> getCartDetails(String userId) {
        List<CartItemDTO> list = new ArrayList<>();

        // Lệnh JOIN đã được SỬA (xóa comment SQL và dấu ';')
        String sql = "SELECT "
                + "    c.CardCode,  "
                + "    c.quantity,  "
                + "    t.cardName,  "
                + "    t.ImageUrl,  "
                + "    t.Price AS singlePrice,  "
                + "    (t.Price * c.quantity) AS totalPrice  "
                + "FROM  "
                + "    tblCarts AS c "
                + "JOIN "
                + "    tblCards AS t ON c.CardCode = t.CardCode "
                + "WHERE "
                + "    c.userID = ?";

        try ( Connection con = DBUtils.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, userId);

            try ( ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String cardCode = rs.getString("CardCode");
                    int quantity = rs.getInt("quantity");
                    String cardName = rs.getString("cardName");
                    String imageUrl = rs.getString("ImageUrl");
                    double singlePrice = rs.getDouble("singlePrice");
                    double totalPrice = rs.getDouble("totalPrice");

                    CartItemDTO item = new CartItemDTO(cardCode, cardName, imageUrl, singlePrice, quantity, totalPrice);
                    list.add(item);
                }
            }
        } catch (Exception e) {
            System.out.println("--- LỖI TRONG getCartDetails ---");
            e.printStackTrace();
        }

        return list; // Trả về danh sách chi tiết
    }

    public boolean removeFromCart(String userID, String cardID) {
        // Câu lệnh SQL đơn giản để xóa
        String sql = "DELETE FROM tblCarts WHERE userID = ? AND CardCode = ?";

        try ( Connection c = DBUtils.getConnection();  PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, userID);
            ps.setString(2, cardID);

            // executeUpdate() trả về số dòng đã bị ảnh hưởng
            int rowsAffected = ps.executeUpdate();

            // Nếu số dòng > 0, nghĩa là đã xóa thành công
            return rowsAffected > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateQuantity(String userID, String cardID, int newQuantity) {
        // Validation: Đảm bảo số lượng luôn lớn hơn 0
        if (newQuantity <= 0) {
            return false;
        }

        // Câu lệnh SQL UPDATE
        String sql = "UPDATE tblCarts SET quantity = ? WHERE userID = ? AND CardCode = ?";

        try ( Connection c = DBUtils.getConnection();  PreparedStatement ps = c.prepareStatement(sql)) {

            // ? thứ 1: newQuantity
            ps.setInt(1, newQuantity);
            // ? thứ 2: userID
            ps.setString(2, userID);
            // ? thứ 3: cardID
            ps.setString(3, cardID);

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public int getCartItemCount(String userID) {
        // Dùng COUNT(*) cho an toàn và chính xác nhất
        String sql = "SELECT COUNT(*) FROM tblCarts WHERE userID = ?";
        int count = 0; // Mặc định là 0

        try ( Connection c = DBUtils.getConnection();  PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, userID);

            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt(1); // Lấy kết quả đếm
                }
            }
        } catch (Exception e) {
            // In lỗi ra nếu có
            System.out.println("--- LỖI NGHIÊM TRỌNG TRONG getCartItemCount ---");
            e.printStackTrace();
        }

        // Thêm dòng debug này để bạn kiểm tra
        System.out.println("--- DEBUG (CartCountFilter): getCartItemCount cho user " + userID + " trả về: " + count + " ---");

        return count; // Trả về số lượng đếm được
    }

    public boolean clearCart(String userID) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        boolean check = false;
        try {
            con = DBUtils.getConnection();
            if (con != null) {
                ps = con.prepareStatement(DELETE_CART_BY_USER);
                ps.setString(1, userID);
                check = ps.executeUpdate() > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Ném lại lỗi nếu bạn muốn xử lý ở nơi gọi
            if (e instanceof SQLException) {
                throw (SQLException) e;
            }
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return check;
    }
}
