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
import java.sql.Statement;
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
        }
        return list;
    }

    public Object[] getRow(UuDai u) {
        return new Object[] {
            u.getIdUD(), u.getGiaTri(), u.getSoLuong(),
            u.getNgayBatDau(), u.getNgayKetThuc()
        };
    }

    public UuDai timKiem(String idUD) {
        String sql = "SELECT * FROM UUDAI WHERE ID_UD = ?";
        try (Connection con = DBconnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, idUD);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                UuDai u = new UuDai();
                u.setIdUD(rs.getString(1));
                u.setGiaTri(rs.getString(2));
                u.setSoLuong(rs.getInt(3));
                u.setNgayBatDau(rs.getDate(4));
                u.setNgayKetThuc(rs.getDate(5));
                return u;
            }
        } catch (Exception e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
        return null;
    }

    public List<UuDai> locTheoGiaTri(String giaTri) {
        List<UuDai> list = new ArrayList<>();
        String sql = "SELECT * FROM UUDAI WHERE GIATRI = ?";

        try (Connection conn = DBconnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, giaTri);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                UuDai u = new UuDai();
                u.setIdUD(rs.getString(1));
                u.setGiaTri(rs.getString(2));
                u.setSoLuong(rs.getInt(3));
                u.setNgayBatDau(rs.getDate(4));
                u.setNgayKetThuc(rs.getDate(5));
                list.add(u);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
