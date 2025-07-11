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
        List<Taikhoan> Listtk = new ArrayList<>();
        String sql = "SELECT * FROM TAIKHOAN";
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
                // Thêm trường TrangThai (nếu chưa có trong DB thì mặc định là ACTIVE)
                try {
                    tk.setTrangThai(rs.getString(5));
                } catch (Exception e) {
                    tk.setTrangThai("ACTIVE"); // Mặc định nếu chưa có cột TrangThai
                }
                Listtk.add(tk);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Listtk;
    }
    
    public Object[] GETROW(Taikhoan tk){
        String ID_TK = tk.getID_TK();
        String Pass = tk.getPass();
        String Email = tk.getEmail();
        String vaiTro = tk.getVaiTro();
        String trangThai = tk.getTrangThai();
        Object[] rows = new Object[]{ID_TK, Pass, Email, vaiTro, trangThai};
        return rows;
    }
    
    public int sua(String oldIDTK, Taikhoan tk){
        String sql = "UPDATE TAIKHOAN SET ID_TK= ?, PASS=?, EMAIL=?, VAITRO=? WHERE ID_TK = ?";
        try (Connection con = DBconnect.getConnection();
             PreparedStatement pstm = con.prepareStatement(sql)) {
            pstm.setString(1, tk.getID_TK());
            pstm.setString(2, tk.getPass());
            pstm.setString(3, tk.getEmail());
            pstm.setString(4, tk.getVaiTro());
            pstm.setString(5, oldIDTK);
            if (pstm.executeUpdate() > 0) {
                System.out.println("Sửa tài khoản thành công!");
                return 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    // Phương thức đổi mật khẩu
    public int passwordchange(String passin, String email){
    String sql = "UPDATE TAIKHOAN SET PASS = ? WHERE EMAIL = ?";
    try (Connection con = DBconnect.getConnection();
         PreparedStatement pstm = con.prepareStatement(sql)) {
        
        pstm.setString(1, passin);
        pstm.setString(2, email);
        
        // Execute update và trả về số dòng bị ảnh hưởng
        int rowsAffected = pstm.executeUpdate();
        
        return rowsAffected; // Trả về 1 nếu thành công, 0 nếu không tìm thấy email
        
    } catch (Exception e) {
        e.printStackTrace();
        return -1; // Trả về -1 nếu có lỗi
    }
}
    
    
    // Phương thức khóa tài khoản
    public int khoaTaiKhoan(String ID_TK) {
        String sql = "UPDATE TAIKHOAN SET TRANGTHAI = 'LOCKED' WHERE ID_TK = ?";
        try (Connection con = DBconnect.getConnection();
             PreparedStatement pstm = con.prepareStatement(sql)) {
            pstm.setString(1, ID_TK);
            if (pstm.executeUpdate() > 0) {
                System.out.println("Khóa tài khoản thành công!");
                return 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    // Phương thức mở khóa tài khoản
    public int moKhoaTaiKhoan(String ID_TK) {
        String sql = "UPDATE TAIKHOAN SET TRANGTHAI = 'ACTIVE' WHERE ID_TK = ?";
        try (Connection con = DBconnect.getConnection();
             PreparedStatement pstm = con.prepareStatement(sql)) {
            pstm.setString(1, ID_TK);
            if (pstm.executeUpdate() > 0) {
                System.out.println("Mở khóa tài khoản thành công!");
                return 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    // Phương thức kiểm tra trạng thái tài khoản
    public String getTrangThaiTaiKhoan(String ID_TK) {
        String sql = "SELECT TRANGTHAI FROM TAIKHOAN WHERE ID_TK = ?";
        try (Connection con = DBconnect.getConnection();
             PreparedStatement pstm = con.prepareStatement(sql)) {
            pstm.setString(1, ID_TK);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                return rs.getString("TRANGTHAI");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "ACTIVE"; // Mặc định trả về ACTIVE nếu không tìm thấy
    }
}

