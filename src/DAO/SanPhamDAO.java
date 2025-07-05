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
    
public List<SanPham> getSPByTen(String ten) {
    List<SanPham> listsp = new ArrayList<>();
    String sql = "SELECT * FROM SANPHAM WHERE TENSP LIKE ?";
    Connection con = DBconnect.getConnection();
    try {
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setString(1, "%" + ten + "%"); // tìm gần đúng
        ResultSet rs = pst.executeQuery();
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
        ex.printStackTrace();
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
    String sql = "SELECT * FROM SANPHAM WHERE TENSP LIKE ?";
    try (Connection con = DBconnect.getConnection(); 
         PreparedStatement pstm = con.prepareStatement(sql)) {
        pstm.setString(1, "%" + tenSanPham + "%");
        try (ResultSet rs = pstm.executeQuery()) {  // Đóng ResultSet
            if (rs.next()) {
                SanPham sp = new SanPham();
                sp.setIDSanPham(rs.getString("ID_SP"));
                sp.setTenSanPham(rs.getString("TENSP"));
                sp.setGiaTien(rs.getString("GIA"));
                sp.setLoaiSanPham(rs.getString("LOAI"));
                sp.setIMG(rs.getString("IMG"));
                return sp;
            }
        }
    } catch (Exception e) {
        System.out.println("Lỗi: " + e.getMessage());
        e.printStackTrace();  // In stack trace để debug
    }
    return null;
}

    public List<SanPham> locTheoLoai(String loai) {
        List<SanPham> list = new ArrayList<>();
        String sql = "SELECT * FROM SANPHAM WHERE LOAI = ?";

        try (Connection conn = DBconnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

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

    // Tạo mã tự động: SP + (max ID + 1)
    public String layMaTuDong() {
        String sql = "SELECT MAX(CAST(SUBSTRING(ID_SP, 3, LEN(ID_SP)) AS INT)) AS maxID FROM SANPHAM";
        try (Connection con = DBconnect.getConnection(); 
             PreparedStatement ps = con.prepareStatement(sql); 
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                int max = rs.getInt("maxID") + 1;
                return "SP" + max;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "SP1"; // Mặc định nếu chưa có sản phẩm nào
    }

    // Thêm sản phẩm vào CSDL
    public void them(SanPham sp) {
        String sql = "INSERT INTO SANPHAM (ID_SP, TENSP, GIA, LOAI, IMG) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = DBconnect.getConnection(); 
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, sp.getIDSanPham());
            ps.setString(2, sp.getTenSanPham());
            ps.setString(3, sp.getGiaTien());
            ps.setString(4, sp.getLoaiSanPham());
            ps.setString(5, sp.getIMG());

            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
public int suaSanPham(SanPham sp, String IDcu) {
    int result = 0;
    String sql = "UPDATE SANPHAM SET ID_SP = ?, TENSP = ?, GIA = ?, LOAI = ?, IMG = ? WHERE ID_SP = ?";
    Connection con = DBconnect.getConnection();
    try {
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setString(1, sp.getIDSanPham());
        pst.setString(2, sp.getTenSanPham());
        pst.setString(3, sp.getGiaTien());
        pst.setString(4, sp.getLoaiSanPham());
        pst.setString(5, sp.getIMG());
        pst.setString(6, IDcu); // mã cũ để WHERE

        result = pst.executeUpdate();
    } catch (Exception e) {
        e.printStackTrace();
    }
    return result;
}

}
