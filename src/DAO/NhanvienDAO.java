/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DBconnect.DBconnect;
import Model.Nhanvien;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


/**
 *
 * @author ADMIN
 */
public class NhanvienDAO {
    public List<Nhanvien> GETALL(){
        List<Nhanvien> Listnv =new ArrayList<>();
        String sql = "SELECT * FROM NHANVIEN" ;
        try {
            Connection conn = DBconnect.getConnection();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                Nhanvien nv = new Nhanvien();
                nv.setID_NV(rs.getString(1));
                nv.setHoTen(rs.getNString(2));
                nv.setChucVu(rs.getString(3));
                nv.setSDT(rs.getString(4));
                nv.setIMG(rs.getString(5));
                Listnv.add(nv);
            }
        } catch (Exception e) {
        }
        return Listnv;
    }  
    public Object[] GETROW(Nhanvien nv){
        String ID_NV = nv.getID_NV();
        String hoTen = nv.getHoTen();
        String chucVu = nv.getChucVu();
        String SDT = nv.getSDT();
        String IMG = nv.getIMG();
        Object[] rows = new Object[]{ID_NV,hoTen,chucVu,SDT,IMG};
        return rows;
    }
}
