/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DBconnect.DBconnect;
import Model.SanPham;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class SanPhamDAO {
    public List<SanPham> getAll() {
        List<SanPham> listsp = new ArrayList<>();
        String sql = "SELECT*FROM SANPHAM";
        Connection con = DBconnect.getConnection();
        try {
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                SanPham sp = new SanPham();
                sp.setIDSanPham(rs.getString(1));
                sp.setTenSanPham(rs.getString(2));
                sp.setGiaTien(rs.getString(3));
                sp.setLoaiSanPham(rs.getString(4));
                sp.setIMG(rs.getString(5));

                listsp.add(sp);
            }
        } catch (Exception ex) {
        }
        return listsp;
    }
    public Object[] getRow(SanPham sp) {
        String ID = sp.getIDSanPham();
        String ten = sp.getTenSanPham();
        String giaTien = sp.getGiaTien();
        String loai = sp.getLoaiSanPham();
        String IMG = sp.getIMG();
        Object[] row = new Object[]{ID, ten, giaTien, loai, IMG};
        return row;
    }
    
    public SanPham timkiem(String tenSanPham) {
    String sql = "SELECT * FROM SANPHAM WHERE TENSP = ?";
    try (Connection con = DBconnect.getConnection(); PreparedStatement pstm = con.prepareStatement(sql)) {

        pstm.setString(1, tenSanPham);
        ResultSet rs = pstm.executeQuery();

        if (rs.next()) {
            SanPham sp = new SanPham();
            sp.setIDSanPham(rs.getString("ID_SP"));
            sp.setTenSanPham(rs.getString("TENSP"));
            sp.setGiaTien(rs.getString("GIA"));
            sp.setLoaiSanPham(rs.getString("LOAI"));
            sp.setIMG(rs.getString("IMG"));
            return sp;
        }

    } catch (Exception e) {
        System.out.println("Lỗi: " + e.getMessage());
    }
    return null;
}
    
   public List<SanPham> locTheoLoai(String loai) {
        List<SanPham> list = new ArrayList<>();
        String sql = "SELECT * FROM SANPHAM WHERE LOAI = ?";

        try (Connection conn = DBconnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, loai);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                SanPham sp = new SanPham();
                sp.setIDSanPham(rs.getString(1));
                sp.setTenSanPham(rs.getString(2));
                sp.setGiaTien(rs.getString(3));
                sp.setLoaiSanPham(rs.getString(4));
                sp.setIMG(rs.getString(5));
                list.add(sp);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
