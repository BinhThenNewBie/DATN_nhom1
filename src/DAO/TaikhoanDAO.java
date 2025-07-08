/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DBconnect.DBconnect;
import Model.Taikhoan;
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
public class TaikhoanDAO {
 public List<Taikhoan> GETALL(){
        List<Taikhoan> Listtk =new ArrayList<>();
        String sql = "SELECT * FROM TAIKHOAN" ;
        try {
            Connection conn = DBconnect.getConnection();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                Taikhoan tk = new Taikhoan();
                tk.setID_TK(rs.getString(1));
                tk.setPass(rs.getString(2));
                tk.setVaiTro(rs.getString(3));
                Listtk.add(tk);
            }
        } catch (Exception e) {
        }
        return Listtk;
    }  
    public Object[] GETROW(Taikhoan tk){
        String ID_TK = tk.getID_TK();
        String Pass = tk.getPass();
        String vaiTro = tk.getVaiTro();
        Object[] rows = new Object[]{ID_TK,Pass,vaiTro};
        return rows;
    }   
}
