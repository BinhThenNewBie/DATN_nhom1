/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DBconnect.DBconnect;
import Model.SanPham;
import Model.UuDai;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nmttt
 */
public class UuDaiDAO {

    public List<UuDai> getAll() {
        List<UuDai> lstUD = new ArrayList<>();
        String sql = "SELECT * FROM UUDAI";
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
                lstUD.add(u);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return lstUD;
    }

    public Object[] getRow(UuDai u) {
        return new Object[]{
            u.getIdUD(), u.getGiaTri(), u.getSoLuong(),
            u.getNgayBatDau(), u.getNgayKetThuc()
        };
    }

    public void them(UuDai u) {
        String sql = "INSERT INTO UUDAI (ID_UD, GIATRI, SOLUONG, NGAYBATDAU, NGAYKETTHUC) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = DBconnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, u.getIdUD());
            ps.setString(2, u.getGiaTri());
            ps.setInt(3, u.getSoLuong());
            ps.setDate(4, u.getNgayBatDau());
            ps.setDate(5, u.getNgayKetThuc());
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Lỗi thêm ưu đãi: " + e.getMessage());
        }
    }

    public void sua(UuDai u) {
        String sql = "UPDATE UUDAI SET GIATRI = ?, SOLUONG = ?, NGAYBATDAU = ?, NGAYKETTHUC = ? WHERE ID_UD = ?";
        try (Connection con = DBconnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, u.getGiaTri());
            ps.setInt(2, u.getSoLuong());
            ps.setDate(3, u.getNgayBatDau());
            ps.setDate(4, u.getNgayKetThuc());
            ps.setString(5, u.getIdUD());
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Lỗi cập nhật ưu đãi: " + e.getMessage());
        }
    }

    public void xoa(String idUD) {
        String sql = "DELETE FROM UUDAI WHERE ID_UD = ?";
        try (Connection con = DBconnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, idUD);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Lỗi xóa ưu đãi: " + e.getMessage());
        }
    }

    public List<UuDai> getAll_GiaTri() {
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

    public Object[] getRow_GT(UuDai u) {
        return new Object[]{u.getGiaTri(), u.getSoLuong()};
    }
}
