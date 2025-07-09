/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.util.Date;

/**
 *
 * @author nmttt
 */
public class UuDai {
    private String idUD;
    private String giaTri;
    private String moTa;
    private Date ngayBatDau;
    private Date ngayKetThuc;

    public UuDai() {
    }

    public UuDai(String idUD, String giaTri, String moTa, Date ngayBatDau, Date ngayKetThuc) {
        this.idUD = idUD;
        this.giaTri = giaTri;
        this.moTa = moTa;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
    }

    public String getIdUD() {
        return idUD;
    }

    public void setIdUD(String idUD) {
        this.idUD = idUD;
    }

    public String getGiaTri() {
        return giaTri;
    }

    public void setGiaTri(String giaTri) {
        this.giaTri = giaTri;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public Date getNgayBatDau() {
        return ngayBatDau;
    }

    public void setNgayBatDau(Date ngayBatDau) {
        this.ngayBatDau = ngayBatDau;
    }

    public Date getNgayKetThuc() {
        return ngayKetThuc;
    }

    public void setNgayKetThuc(Date ngayKetThuc) {
        this.ngayKetThuc = ngayKetThuc;
    }
    
    
}
