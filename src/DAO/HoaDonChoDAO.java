/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DBconnect.DBconnect;
import Model.ChiTietHoaDon;
import Model.HoaDonCho;
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
public class HoaDonChoDAO {
    List<HoaDonCho> lstHDC = new ArrayList<>();
    List<ChiTietHoaDon> lstHDCT = new ArrayList<>();
    
    // GET ALL ID HÓA ĐƠN
    public List<ChiTietHoaDon> getAllID_HD(String ID_HD) {
        List<ChiTietHoaDon> list = new ArrayList<>();
        String sql = "SElECT ID_HD, ID_SP, TENSP, GIASP, SOLUONG FROM CHITIETHOADON WHERE ID_HD = ?";
        try (
            Connection con = DBconnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql);) {
            ps.setString(1, ID_HD);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ChiTietHoaDon ct = new ChiTietHoaDon();
                ct.setID_HD(rs.getString("ID_HD"));
                ct.setID_SP(rs.getString("ID_SP"));
                ct.setTenSP(rs.getString("TENSP"));
                ct.setGiaSP(rs.getFloat("GIASP"));
                ct.setSoLuong(rs.getInt("SOLUONG")); 
                list.add(ct);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    /// GET ALL & GET ROW CHI TIẾT HÓA ĐƠN
    public List<ChiTietHoaDon> getAllCTHD() {
        String sql = "SELECT * FROM CHITIETHOADON";
        try (
                Connection con = DBconnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql);) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ChiTietHoaDon cthd = new ChiTietHoaDon();
                cthd.setID_HD(rs.getString(1));
                cthd.setID_SP(rs.getString(2));
                cthd.setTenSP(rs.getString(3));
                cthd.setGiaSP(rs.getFloat(4));
                cthd.setSoLuong(rs.getInt(5));
                lstHDCT.add(cthd);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lstHDCT;
    }

    public Object getRowCTHD(ChiTietHoaDon cthd) {
        Object[] row = new Object[]{
            cthd.getID_HD(),
            cthd.getID_SP(),
            cthd.getTenSP(),
            cthd.getGiaSP(),
            cthd.getSoLuong()
        };
        return row;
    }
    /// GET ALL & GET ROW HÓA ĐƠN CHỜ
    public List<HoaDonCho> getALLHDCHO() {
        String sql = "SELECT * FROM HOADONCHO";
        try (Connection con = DBconnect.getConnection(); Statement stm = con.createStatement(); ResultSet rs = stm.executeQuery(sql)) {
            while (rs.next()) {
                HoaDonCho hdc = new HoaDonCho();
                hdc.setID_HD(rs.getString(1));
                hdc.setNgayThangNam(rs.getString(2));
                hdc.setThoiGian(rs.getString(3));
                hdc.setTongTien(rs.getFloat(4));
                lstHDC.add(hdc);
            }
        } catch (Exception e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
        return lstHDC;
    }

    public Object getRowHDCHO(HoaDonCho hdc) {
        Object[] row = new Object[]{
            hdc.getID_HD(),
            hdc.getNgayThangNam(),
            hdc.getThoiGian(),
            hdc.getTongTien()
        };
        return row;
    }
}
