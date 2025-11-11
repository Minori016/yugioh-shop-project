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
public class SetDAO {
     Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    public ArrayList<SetDTO> getAllSet() {
        ArrayList<SetDTO> list = new ArrayList<>();
        try {
            // 1 - Tao ket noi
            Connection conn = DBUtils.getConnection();

            // 2 - Tao cau lenh
            String sql = "SELECT * FROM tblCardSet";

            // 3 - Tao statement de co the run cau lenh
            PreparedStatement pst = conn.prepareStatement(sql);

            // 4 - Thuc thi cau lenh
            ResultSet rs = pst.executeQuery();

            // 5 - Kiem tra
            while (rs.next()) {
                SetDTO set = new SetDTO();
                set.setSetID(rs.getInt("SetID"));
                set.setSetName(rs.getString("SetName"));
                set.setRealeseDate(rs.getString("ReleaseDate"));
                set.setTotalCard(rs.getInt("TotalCards"));
                

                list.add(set);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
}
    
    public SetDTO getSetByID(int setID) {
        SetDTO set = null;
        String sql = "SELECT * FROM tblCardSet WHERE SetID = ?";
        
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, setID);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    set = new SetDTO();
                    set.setSetID(rs.getInt("SetID"));
                    set.setSetName(rs.getString("SetName"));
                    set.setRealeseDate(rs.getString("ReleaseDate"));
                    set.setTotalCard(rs.getInt("TotalCards"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return set;
    }
       public static void main(String[] args) {
        SetDAO dao = new SetDAO();
        List<SetDTO> list = dao.getAllSet();
        for(SetDTO o :list ) {
            System.out.println(o);
        }
    }
}
