/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

/**
 *
 * @author ADMIN
 */
public class TaikhoanDAO {
<<<<<<< HEAD

=======
<<<<<<< Updated upstream
=======
    
>>>>>>> parent of a3e531a (k)
    // Lấy tất cả tài khoản
    public List<Taikhoan> GETALL(){
        List<Taikhoan> Listtk = new ArrayList<>();
        String sql = "SELECT * FROM TAIKHOAN";
        try {
            Connection conn = DBconnect.getConnection();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                Taikhoan tk = new Taikhoan();
                tk.setID_TK(rs.getString("ID_TK"));
                tk.setPass(rs.getString("PASS"));
                tk.setEmail(rs.getString("EMAIL"));
                tk.setVaiTro(rs.getString("VAITRO"));
                String trangThai = rs.getString("TRANGTHAI");
                tk.setTrangThai(trangThai != null ? trangThai : "ACTIVE");
                Listtk.add(tk);
            }
            conn.close();
        } catch (Exception e) {
            System.err.println("Error getting all accounts: " + e.getMessage());
            e.printStackTrace();
        }
        return Listtk;
    }
<<<<<<< HEAD
=======
>>>>>>> Stashed changes
>>>>>>> parent of a3e531a (k)
    
}
