/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DBconnect.DBconnect;
import Model.HoaDon;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author QuynhAnh2311
 */
public class HoaDonDAO {
    List<HoaDon> lstHD = new ArrayList<>();
    
    public int saveHOADON(HoaDon hd) {
        String sql = "INSERT INTO HOADON VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = DBconnect.getConnection(); PreparedStatement pstm = con.prepareStatement(sql)) {
            pstm.setString(1, hd.getID_HD());
            pstm.setString(2, hd.getNgayThangNam());
            pstm.setString(3, hd.getThoiGian());
            pstm.setString(4, hd.getMon());
            pstm.setFloat(5, hd.getTongTien());
            pstm.setString(6, hd.getUuDai());
            int row = pstm.executeUpdate();
            if (row > 0) {
                return 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    public List<HoaDon> getALLHD() {
        lstHD.clear();
        String sql = "SELECT * FROM HOADON";
        try (Connection con = DBconnect.getConnection(); Statement stm = con.createStatement(); ResultSet rs = stm.executeQuery(sql)) {
            while (rs.next()) {
                HoaDon hd = new HoaDon();
                hd.setID_HD(rs.getString(1));
                hd.setNgayThangNam(rs.getString(2));
                hd.setThoiGian(rs.getString(3));
                hd.setMon(rs.getString(4));
                hd.setTongTien(rs.getFloat(5));
                hd.setUuDai(rs.getString(6));
                lstHD.add(hd);
            }
        } catch (Exception e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
        return lstHD;
    }
    
    public Object getRowHD(HoaDon hd) {
        Object[] row = new Object[]{
            hd.getID_HD(),
            hd.getNgayThangNam(),
            hd.getThoiGian(),
            hd.getMon(),
            hd.getTongTien(),
            hd.getUuDai()
        };
        return row;
    }
}
