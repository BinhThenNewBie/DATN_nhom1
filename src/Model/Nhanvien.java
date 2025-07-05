/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author ADMIN
 */
public class Nhanvien {
   private String ID_NV, hoTen, SDT, chucVu, IMG;

    public Nhanvien() {
    }

    public Nhanvien(String ID_NV, String hoTen, String SDT, String chucVu, String IMG) {
        this.ID_NV = ID_NV;
        this.hoTen = hoTen;
        this.SDT = SDT;
        this.chucVu = chucVu;
        this.IMG = IMG;
    }

    public String getID_NV() {
        return ID_NV;
    }

    public void setID_NV(String ID_NV) {
        this.ID_NV = ID_NV;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public String getChucVu() {
        return chucVu;
    }

    public void setChucVu(String chucVu) {
        this.chucVu = chucVu;
    }

    public String getIMG() {
        return IMG;
    }

    public void setIMG(String IMG) {
        this.IMG = IMG;
    } 
}
