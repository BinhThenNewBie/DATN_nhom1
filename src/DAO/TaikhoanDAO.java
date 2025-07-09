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
                tk.setEmail(rs.getString(3));
                tk.setVaiTro(rs.getString(4));
                Listtk.add(tk);
            }
        } catch (Exception e) {
        }
        return Listtk;
    }  
    public Object[] GETROW(Taikhoan tk){
        String ID_TK = tk.getID_TK();
        String Pass = tk.getPass();
        String Email = tk.getEmail();
        String vaiTro = tk.getVaiTro();
        Object[] rows = new Object[]{ID_TK,Pass,Email,vaiTro};
        return rows;
    }
//        public int Them(Taikhoan tk){
//        String sql = "INSERT INTO TAIKHOAN (ID_TK, PASS,EMAIL, VAITRO) VALUES (?, ?, ?, ?)";
//        try (
//            Connection con = DBconnect.getConnection();
//            PreparedStatement pstm = con.prepareStatement(sql)) {
//            pstm.setString(1, tk.getID_TK());
//            pstm.setString(2, tk.getPass());
//            pstm.setString(3, tk.getEmail());
//            pstm.setString(4, tk.getVaiTro());
//            
//            if(pstm.executeLargeUpdate()>0){
//                System.out.println("Thêm tài khoản mới thành công!");
//                return 1;
//            }
//        } catch (Exception e) {
//            
//        }
//        return 0; 
//    }
      
    public int sua(String oldIDTK, Taikhoan tk){
    String sql = "UPDATE TAIKHOAN SET ID_TK= ?, PASS=?, EMAIL=?, VAITRO=? WHERE ID_TK = ?";
    try (Connection con = DBconnect.getConnection();
         PreparedStatement pstm = con.prepareStatement(sql)) {

        pstm.setString(1, tk.getID_TK());
        pstm.setString(2, tk.getPass());
        pstm.setString(3, tk.getEmail());
        pstm.setString(4, tk.getVaiTro());
        pstm.setString(5, oldIDTK); // dùng ID gốc để WHERE

        if (pstm.executeUpdate() > 0) {
            System.out.println("Sửa tài khoản thành công!");
            return 1;
        }
    } catch (Exception e) {
        e.printStackTrace(); // In lỗi ra để debug
    }
    return 0;
}

}
