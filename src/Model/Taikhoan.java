/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author ADMIN
 */
public class Taikhoan {
    private String ID_TK, pass, vaiTro;

    public Taikhoan() {
    }

    public Taikhoan(String ID_TK, String pass, String vaiTro) {
        this.ID_TK = ID_TK;
        this.pass = pass;
        this.vaiTro = vaiTro;
    }

    public String getID_TK() {
        return ID_TK;
    }

    public void setID_TK(String ID_TK) {
        this.ID_TK = ID_TK;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getVaiTro() {
        return vaiTro;
    }

    public void setVaiTro(String vaiTro) {
        this.vaiTro = vaiTro;
    }
    
}
