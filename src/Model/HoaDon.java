/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author QuynhAnh2311
 */
public class HoaDon {
    private String ID_HD, ngayThangNam, thoiGian, mon;
    private float tongTien;

    public HoaDon() {
    }

    public HoaDon(String ID_HD, String ngayThangNam, String thoiGian, String mon, float tongTien) {
        this.ID_HD = ID_HD;
        this.ngayThangNam = ngayThangNam;
        this.thoiGian = thoiGian;
        this.mon = mon;
        this.tongTien = tongTien;
    }

    public String getID_HD() {
        return ID_HD;
    }

    public void setID_HD(String ID_HD) {
        this.ID_HD = ID_HD;
    }

    public String getNgayThangNam() {
        return ngayThangNam;
    }

    public void setNgayThangNam(String ngayThangNam) {
        this.ngayThangNam = ngayThangNam;
    }

    public String getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(String thoiGian) {
        this.thoiGian = thoiGian;
    }

    public String getMon() {
        return mon;
    }

    public void setMon(String mon) {
        this.mon = mon;
    }

    public float getTongTien() {
        return tongTien;
    }

    public void setTongTien(float tongTien) {
        this.tongTien = tongTien;
    }
    
    
}
