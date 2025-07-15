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
    private float apDungVoi;
    private Date ngayBatDau;
    private Date ngayKetThuc;
    private String trangThai;

    public UuDai() {
    }

    public UuDai(String idUD, String giaTri, Float apDungVoi, Date ngayBatDau, Date ngayKetThuc, String trangThai) {
        this.idUD = idUD;
        this.giaTri = giaTri;
        this.apDungVoi = apDungVoi;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
        this.trangThai = trangThai;
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

    public Float getApDungVoi() {
        return apDungVoi;
    }

    public void setApDungVoi(Float apDungVoi) {
        this.apDungVoi = apDungVoi;
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

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    
}
