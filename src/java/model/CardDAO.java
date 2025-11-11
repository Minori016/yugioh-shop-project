/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import utils.DBUtils;

/**
 *
 * @author xuhoa
 */
public class CardDAO {

    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    public ArrayList<CardDTO> getAllCard() {
        ArrayList<CardDTO> list = new ArrayList<>();
        try {
            // 1 - Tao ket noi
            Connection conn = DBUtils.getConnection();

            // 2 - Tao cau lenh
            String sql = "SELECT * FROM tblCards";

            // 3 - Tao statement de co the run cau lenh
            PreparedStatement pst = conn.prepareStatement(sql);

            // 4 - Thuc thi cau lenh
            ResultSet rs = pst.executeQuery();

            // 5 - Kiem tra
            while (rs.next()) {
                CardDTO card = new CardDTO();
                card.setCardID(rs.getString("CardCode"));
                card.setCardName(rs.getString("CardName"));
                card.setRarity(rs.getString("Rarity"));
                card.setPrice(rs.getDouble("Price"));
                card.setImage(rs.getString("ImageUrl"));
                card.setSetID(rs.getInt("SetID"));

                list.add(card);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void main(String[] args) {
        CardDAO dao = new CardDAO();
        List<CardDTO> list = dao.getAllCard();
        for (CardDTO o : list) {
            System.out.println(o);
        }
    }

    public CardDTO getMostExpensiveCard() {
        CardDTO card = null;
        try {
            Connection conn = DBUtils.getConnection();
            String sql = "SELECT TOP 1 * FROM tblCards ORDER BY price DESC";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                card = new CardDTO();
                card.setCardID(rs.getString("cardCode"));
                card.setCardName(rs.getString("cardName"));
                card.setRarity(rs.getString("rarity"));
                card.setPrice(rs.getDouble("price"));
                card.setImage(rs.getString("ImageUrl"));
                card.setSetID(rs.getInt("SetID"));

            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return card;
    }

    public List<CardDTO> getCardsBySetID(String setID) {
        List<CardDTO> list = new ArrayList<>();
        try ( Connection conn = DBUtils.getConnection();  PreparedStatement ps = conn.prepareStatement(
                "SELECT * FROM tblCards WHERE SetID = ?")) {
            ps.setString(1, setID);
            try ( ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    CardDTO c = new CardDTO();
                    c.setCardID(rs.getString("CardCode"));
                    c.setCardName(rs.getString("CardName"));
                    c.setRarity(rs.getString("Rarity"));
                    c.setPrice(rs.getDouble("Price"));
                    c.setImage(rs.getString("ImageUrl"));
                    c.setSetID(rs.getInt("SetID"));
                    list.add(c);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public CardDTO getCardByID(String cardID) {
        CardDTO card = null;
        try {
            Connection conn = DBUtils.getConnection();
            String sql = "SELECT * FROM tblCards WHERE CardCode = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, cardID);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                card = new CardDTO();
                card.setCardID(rs.getString("cardCode"));
                card.setCardName(rs.getString("cardName"));
                card.setRarity(rs.getString("rarity"));
                card.setPrice(rs.getDouble("price"));
                card.setImage(rs.getString("ImageUrl"));
                card.setSetID(rs.getInt("SetID"));
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return card;
    }

    public List<CardDTO> searchCardByName(String searchValue) {
        List<CardDTO> list = new ArrayList<>();
        // Câu lệnh SQL dùng LIKE để tìm kiếm tên chứa giá trị
        String sql = "SELECT * FROM tblCards WHERE CardName LIKE ?";

        try ( Connection conn = DBUtils.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {

            // Gán tham số cho câu lệnh LIKE, thêm dấu %
            ps.setString(1, "%" + searchValue + "%");

            try ( ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    CardDTO c = new CardDTO();
                    c.setCardID(rs.getString("CardCode"));
                    c.setCardName(rs.getString("CardName"));
                    c.setRarity(rs.getString("Rarity"));
                    c.setPrice(rs.getDouble("Price"));
                    c.setImage(rs.getString("ImageUrl"));
                    c.setSetID(rs.getInt("SetID"));
                    list.add(c);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list; // Trả về danh sách (có thể rỗng nếu không tìm thấy)
    }

    // ... bên trong lớp CardDAO.java ...
    public boolean addCard(CardDTO card) {
        String sql = "INSERT INTO tblCards (CardCode, CardName, Rarity, Price, ImageUrl, SetID) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        // Dùng try-with-resources để tự động đóng kết nối
        try ( Connection conn = DBUtils.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, card.getCardID());
            ps.setString(2, card.getCardName());
            ps.setString(3, card.getRarity());
            ps.setDouble(4, card.getPrice());
            ps.setString(5, card.getImage());
            ps.setInt(6, card.getSetID());

            // executeUpdate() trả về số dòng bị ảnh hưởng
            int result = ps.executeUpdate();

            // Nếu result > 0 (tức là 1 dòng đã được chèn), trả về true
            return result > 0;

        } catch (Exception e) {
            e.printStackTrace(); // In lỗi ra console nếu có
        }
        return false; // Trả về false nếu có lỗi xảy ra
    }
    
    
    public boolean deleteCard(String cardID) {
        String sql = "DELETE FROM tblCards WHERE CardCode = ?";

      
        try (Connection conn = DBUtils.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, cardID);

            int result = ps.executeUpdate();

         
            return result > 0;

        } catch (Exception e) {
            e.printStackTrace(); 
        }
        return false; 
    }
    public boolean updateCard(CardDTO card) {
        String sql = "UPDATE tblCards "
                   + "SET CardName = ?, Rarity = ?, Price = ?, ImageUrl = ?, SetID = ? "
                   + "WHERE CardCode = ?";

        try (Connection conn = DBUtils.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, card.getCardName());
            ps.setString(2, card.getRarity());
            ps.setDouble(3, card.getPrice());
            ps.setString(4, card.getImage());
            ps.setInt(5, card.getSetID());
            
            // Tham số cho WHERE
            ps.setString(6, card.getCardID()); 

            int result = ps.executeUpdate();
            return result > 0; // Trả về true nếu cập nhật thành công (1 dòng bị ảnh hưởng)

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false; // Trả về false nếu có lỗi
    }
}
