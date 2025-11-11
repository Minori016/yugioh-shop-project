package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException; // Cần import ( rollback trasaction - ) 
import java.util.ArrayList;
import utils.DBUtils; //

public class AddressDAO {

    // --- CÁC HÀM GET VÀ DELETE (Giữ nguyên như file gốc của bạn) ---

    public ArrayList<AddressDTO> getAddressesByUserID(String userID) {
        // ... (Code của bạn đã đúng)
        ArrayList<AddressDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM tblAddresses WHERE user_id = ?";
        try ( Connection conn = DBUtils.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, userID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                AddressDTO addr = new AddressDTO();
                addr.setAddressID(rs.getInt("address_id"));
                addr.setUserID(rs.getString("user_id"));
                addr.setReceiverName(rs.getString("receiver_name")); 
                addr.setPhoneNumber(rs.getString("phone_number"));
                addr.setDetailedAddress(rs.getString("detailed_address"));
                list.add(addr);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public AddressDTO getAddressByID(int addressID) {
        // ... (Code của bạn đã đúng)
        String sql = "SELECT * FROM tblAddresses WHERE address_id = ?";
        try ( Connection conn = DBUtils.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, addressID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                AddressDTO addr = new AddressDTO();
                addr.setAddressID(rs.getInt("address_id"));
                addr.setUserID(rs.getString("user_id"));
                addr.setReceiverName(rs.getString("receiver_name"));
                addr.setPhoneNumber(rs.getString("phone_number"));
                addr.setDetailedAddress(rs.getString("detailed_address"));
              
                return addr;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean deleteAddress(int addressID) {
        // ... (Code của bạn đã đúng)
        String sql = "DELETE FROM tblAddresses WHERE address_id = ?";
        try ( Connection conn = DBUtils.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, addressID);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


   public boolean insertAddress(AddressDTO addr) {
    // Xóa 'is_default' khỏi INSERT statement
    String sql_insert = "INSERT INTO tblAddresses (user_id, receiver_name, phone_number, detailed_address) "
            + "VALUES (?, ?, ?, ?)";

    try (Connection conn = utils.DBUtils.getConnection();
         PreparedStatement ps_insert = conn.prepareStatement(sql_insert)) {

        ps_insert.setString(1, addr.getUserID());
        ps_insert.setString(2, addr.getReceiverName());
        ps_insert.setString(3, addr.getPhoneNumber());
        ps_insert.setString(4, addr.getDetailedAddress());

        return ps_insert.executeUpdate() > 0;

    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}


     public boolean updateAddress(AddressDTO addr) {
    // Xóa 'is_default' khỏi UPDATE statement
    String sql_update = "UPDATE tblAddresses "
            + "SET receiver_name = ?, phone_number = ?, detailed_address = ? "
            + "WHERE address_id = ?";

    try (Connection conn = DBUtils.getConnection();
         PreparedStatement ps_update = conn.prepareStatement(sql_update)) {

        ps_update.setString(1, addr.getReceiverName());
        ps_update.setString(2, addr.getPhoneNumber());
        ps_update.setString(3, addr.getDetailedAddress());
        ps_update.setInt(4, addr.getAddressID());

        return ps_update.executeUpdate() > 0;

    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}
}