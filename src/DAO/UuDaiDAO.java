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

/**
 *
 * @author nmttt
 */
public class UuDaiDAO {

    public void them(UuDai uuDai) {
        String sql = "INSERT INTO UUDAI (ID_UD, GIATRI, MOTA, NGAYBATDAU, NGAYKETTHUC) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = DBconnect.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, uuDai.getIdUD());
            stmt.setString(2, uuDai.getGiaTri());
            stmt.setString(3, uuDai.getMoTa());
            stmt.setDate(4, new java.sql.Date(uuDai.getNgayBatDau().getTime()));
            stmt.setDate(5, new java.sql.Date(uuDai.getNgayKetThuc().getTime()));
            stmt.executeUpdate();
            System.out.println("Thêm ưu đãi thành công!");
        } catch (Exception e) {
            System.out.println("Thêm ưu đãi thất bại!");
            e.printStackTrace();
        }
    }

    // Cập nhật ưu đãi
    public void sua(UuDai uuDai) {
        String sql = "UPDATE UUDAI SET GIATRI = ?, MOTA = ?, NGAYBATDAU = ?, NGAYKETTHUC = ? WHERE ID_UD = ?";
        try (Connection con = DBconnect.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, uuDai.getGiaTri());
            stmt.setString(2, uuDai.getMoTa());
            stmt.setDate(3, new java.sql.Date(uuDai.getNgayBatDau().getTime()));
            stmt.setDate(4, new java.sql.Date(uuDai.getNgayKetThuc().getTime()));
            stmt.setString(5, uuDai.getIdUD());
            stmt.executeUpdate();
            System.out.println("Cập nhật ưu đãi thành công!");
        } catch (Exception e) {
            System.out.println("Cập nhật ưu đãi thất bại!");
            e.printStackTrace();
        }
    }

    // Xóa ưu đãi
    public void xoa(String idUd) {
        String sql = "DELETE FROM UUDAI WHERE ID_UD = ?";
        try (Connection con = DBconnect.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, idUd);
            stmt.executeUpdate();
            System.out.println("Xóa ưu đãi thành công!");
        } catch (Exception e) {
            System.out.println("Xóa ưu đãi thất bại!");
            e.printStackTrace();
        }
    }

    public List<UuDai> getAll() {
        List<UuDai> list = new ArrayList<>();
        String sql = "SELECT * FROM UUDAI";
        try (Connection conn = DBconnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                UuDai ud = new UuDai();
                ud.setIdUD(rs.getString("ID_UD"));
                ud.setGiaTri(rs.getString("GIATRI"));
                ud.setMoTa(rs.getString("MOTA"));
                ud.setNgayBatDau(rs.getDate("NGAYBATDAU"));
                ud.setNgayKetThuc(rs.getDate("NGAYKETTHUC"));
                list.add(ud);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public Object[] getRow(UuDai ud) {
    String id = ud.getIdUD();
    String giaTri = ud.getGiaTri();
    String moTa = ud.getMoTa();
    Date ngayBatDau = ud.getNgayBatDau();
    Date ngayKetThuc = ud.getNgayKetThuc();

    Object[] row = new Object[]{id, giaTri, moTa, ngayBatDau, ngayKetThuc};
    return row;
}

}
