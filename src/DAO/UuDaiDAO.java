/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DBconnect.DBconnect;
import Model.UuDai;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author nmttt
 */
public class UuDaiDAO {

    public List<UuDai> getAll() {
        List<UuDai> list = new ArrayList<>();
        try {
            Connection conn = DBconnect.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM UUDAI");
            while (rs.next()) {
                UuDai ud = new UuDai(
                        rs.getString("ID_UD"),
                        rs.getString("GIATRI"),
                        rs.getFloat("APDUNGVOI"),
                        rs.getDate("NGAYBATDAU"),
                        rs.getDate("NGAYKETTHUC"),
                        rs.getString("TRANGTHAI")
                );
                list.add(ud);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public Object[] getRowUuDai(UuDai ud) {
        return new Object[]{
            ud.getGiaTri(),
            ud.getApDungVoi()
        };
    }
    public Object[] getRow(UuDai ud) {
        return new Object[]{
            ud.getIdUD(),
            ud.getGiaTri(),
            ud.getApDungVoi(),
            ud.getNgayBatDau(),
            ud.getNgayKetThuc(),
            ud.getTrangThai()
        };
    }

    public void them(UuDai ud) {
        try {
            Connection conn = DBconnect.getConnection();
            String sql = "INSERT INTO UUDAI (ID_UD, GIATRI, MOTA, NGAYBATDAU, NGAYKETTHUC, TRANGTHAI) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, ud.getIdUD());
            ps.setString(2, ud.getGiaTri());
            ps.setFloat(3, ud.getApDungVoi());
            ps.setDate(4, new java.sql.Date(ud.getNgayBatDau().getTime()));
            ps.setDate(5, new java.sql.Date(ud.getNgayKetThuc().getTime()));
            ps.setString(6, ud.getTrangThai());

            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Thêm ưu đãi thành công!");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Thêm ưu đãi thất bại!");
        }
    }

    public void sua(UuDai ud) {
        try {
            Connection conn = DBconnect.getConnection();
            String sql = "UPDATE UUDAI SET GIATRI=?, MOTA=?, NGAYBATDAU=?, NGAYKETTHUC=?, TRANGTHAI=? WHERE ID_UD=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, ud.getGiaTri());
            ps.setFloat(2, ud.getApDungVoi());
            ps.setDate(3, new java.sql.Date(ud.getNgayBatDau().getTime()));
            ps.setDate(4, new java.sql.Date(ud.getNgayKetThuc().getTime()));
            ps.setString(5, ud.getTrangThai());
            ps.setString(6, ud.getIdUD());

            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Cập nhật ưu đãi thành công!");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Cập nhật ưu đãi thất bại!");
        }
    }

//    public void xoa(String id) {
//        String sql = "DELETE FROM UUDAI WHERE ID_UD = ?";
//        try (Connection con = DBconnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
//
//            ps.setString(1, id);
//            ps.executeUpdate();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            JOptionPane.showMessageDialog(null, "Lỗi khi xóa ưu đãi!");
//        }
//    }

}
