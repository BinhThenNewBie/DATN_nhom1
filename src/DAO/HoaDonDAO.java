/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DBconnect.DBconnect;
import Model.HoaDon;
import java.sql.Connection;
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
            hd.getTongTien()
        };
        return row;
    }
}
