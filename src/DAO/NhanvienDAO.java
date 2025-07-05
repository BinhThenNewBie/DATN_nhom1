/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import DBconnect.DBconnect;
import Model.Nhanvien;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author ADMIN
 */
public class NhanvienDAO {
    public List<Nhanvien> GETALL(){
        List<Nhanvien> Listnv = new ArrayList<>();
        String sql ="SELECT * FROM NHANVIEN";
        Connection conn = DBconnect.getConnection();
        try {
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                Nhanvien nv = new Nhanvien();
                nv.setID_NV(rs.getString(1));
                nv.setHoTen(rs.getNString(2));
                nv.setSDT(rs.getString(3));
                nv.setChucVu(rs.getString(4));
                nv.setIMG(rs.getString(5));
            }
        } catch (Exception e) {
        }
        return Listnv;
    }
}
