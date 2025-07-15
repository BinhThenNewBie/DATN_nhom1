/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DBconnect.DBconnect;
import Model.UuDai;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
<<<<<<< Updated upstream
import java.sql.Statement;
=======
import java.sql.Connection;
import java.text.DecimalFormat;
>>>>>>> Stashed changes
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nmttt
 */
public class UuDaiDAO {

    public List<UuDai> getAll() {
        List<UuDai> list = new ArrayList<>();
        String sql = "SELECT * FROM UUDAI";
<<<<<<< Updated upstream
        Connection con = DBconnect.getConnection();
        try {
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                UuDai u = new UuDai();
                u.setIdUD(rs.getString(1));
                u.setGiaTri(rs.getString(2));
                u.setSoLuong(rs.getInt(3));
                u.setNgayBatDau(rs.getDate(4));
                u.setNgayKetThuc(rs.getDate(5));
                list.add(u);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
=======

        try (Connection conn = DBconnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                UuDai ud = new UuDai();
                ud.setIdUD(rs.getString("ID_UD"));
                ud.setGiaTri(rs.getString("GIATRI"));

                // Nếu APDUNGVOI không tồn tại, comment dòng này lại:
                ud.setApDungVoi(rs.getFloat("APDUNGVOI"));

                ud.setNgayBatDau(rs.getDate("NGAYBATDAU"));
                ud.setNgayKetThuc(rs.getDate("NGAYKETTHUC"));
                ud.setTrangThai(rs.getString("TRANGTHAI"));
                list.add(ud);
            }

        } catch (Exception e) {
            e.printStackTrace();
>>>>>>> Stashed changes
        }

        return list;
    }

<<<<<<< Updated upstream
    public Object[] getRow(UuDai u) {
        return new Object[]{
            u.getIdUD(), u.getGiaTri(), u.getSoLuong(),
            u.getNgayBatDau(), u.getNgayKetThuc()
        };
    }

    public void insert(UuDai u) {
        String sql = "INSERT INTO UUDAI (ID_UD, GIATRI, SOLUONG, NGAYBATDAU, NGAYKETTHUC) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = DBconnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, u.getIdUD());
            ps.setString(2, u.getGiaTri());
            ps.setInt(3, u.getSoLuong());
            ps.setDate(4, u.getNgayBatDau());
            ps.setDate(5, u.getNgayKetThuc());
=======
    public Object[] getRow(UuDai ud) {
        DecimalFormat df = new DecimalFormat("#,###");
        String tienVND = df.format(ud.getApDungVoi()) + " VND";
        return new Object[]{
            ud.getIdUD(),
            ud.getGiaTri(),
            tienVND,
            ud.getNgayBatDau(),
            ud.getNgayKetThuc(),
            ud.getTrangThai()
        };
    }

    public void them(UuDai ud) {
        try {
            Connection conn = DBconnect.getConnection();
            String sql = "INSERT INTO UUDAI (ID_UD, GIATRI, APDUNGVOI, NGAYBATDAU, NGAYKETTHUC, TRANGTHAI) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, ud.getIdUD());
            ps.setString(2, ud.getGiaTri());
            ps.setFloat(3, ud.getApDungVoi());
            ps.setDate(4, new java.sql.Date(ud.getNgayBatDau().getTime()));
            ps.setDate(5, new java.sql.Date(ud.getNgayKetThuc().getTime()));
            ps.setString(6, ud.getTrangThai());
>>>>>>> Stashed changes
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Lỗi thêm ưu đãi: " + e.getMessage());
        }
    }

<<<<<<< Updated upstream
    public void update(UuDai u) {
        String sql = "UPDATE UUDAI SET GIATRI = ?, SOLUONG = ?, NGAYBATDAU = ?, NGAYKETTHUC = ? WHERE ID_UD = ?";
        try (Connection con = DBconnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, u.getGiaTri());
            ps.setInt(2, u.getSoLuong());
            ps.setDate(3, u.getNgayBatDau());
            ps.setDate(4, u.getNgayKetThuc());
            ps.setString(5, u.getIdUD());
=======
    public void sua(UuDai ud) {
        try {
            Connection conn = DBconnect.getConnection();
            String sql = "UPDATE UUDAI SET GIATRI=?, APDUNGVOI=?, NGAYBATDAU=?, NGAYKETTHUC=?, TRANGTHAI=? WHERE ID_UD=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, ud.getGiaTri());
            ps.setFloat(2, ud.getApDungVoi());
            ps.setDate(3, new java.sql.Date(ud.getNgayBatDau().getTime()));
            ps.setDate(4, new java.sql.Date(ud.getNgayKetThuc().getTime()));
            ps.setString(5, ud.getTrangThai());
            ps.setString(6, ud.getIdUD());
>>>>>>> Stashed changes
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Lỗi cập nhật ưu đãi: " + e.getMessage());
        }
    }
<<<<<<< Updated upstream

    public void delete(String idUD) {
        String sql = "DELETE FROM UUDAI WHERE ID_UD = ?";
        try (Connection con = DBconnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, idUD);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Lỗi xóa ưu đãi: " + e.getMessage());
        }
    }

    public List<UuDai> getAll_SL() {
        List<UuDai> lstSL = new ArrayList<>();
        String sql = "SELECT GIATRI, SOLUONG FROM UUDAI";
        Connection con = DBconnect.getConnection();
        try {
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                UuDai u = new UuDai();
                u.setGiaTri(rs.getString("GIATRI"));
                u.setSoLuong(rs.getInt("SOLUONG"));
                lstSL.add(u);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return lstSL;
    }
    
    public Object[] getRow_SL(UuDai u) {
    return new Object[] { u.getGiaTri(), u.getSoLuong() };
}


=======
>>>>>>> Stashed changes
}
