/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import DAO.NhanvienDAO;
import Model.Nhanvien;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ADMIN
 */
public class QuanLyNhanVien extends javax.swing.JFrame {
      DefaultTableModel tableModel= new DefaultTableModel();
      NhanvienDAO nvd = new NhanvienDAO();
      String strAnh = "";
    /**
     * Creates new form QuanLyNhanVien
     */
    public QuanLyNhanVien() {
        initComponents();
        initTable();
        fillTable();
        setLocationRelativeTo(null);
    }
    private ImageIcon resizeImage(String imagePath, int width, int height) {
    try {
        ImageIcon originalIcon = new ImageIcon(getClass().getResource(imagePath));
        Image originalImage = originalIcon.getImage();
        
        // Resize ảnh với kích thước cố định
        Image resizedImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    } catch (Exception e) {
        return null;
    }
}
public void initTable(){
    tableModel = new DefaultTableModel();
    String[] cols = new String[]{"ID nhân viên", "Họ và tên", "Chức vụ", "Số điện thoại", "Trạng thái"};
    tableModel.setColumnIdentifiers(cols);
    tblNhanvien.setModel(tableModel);
}
public void fillTable(){
    tableModel.setRowCount(0);
    for (Nhanvien nv : nvd.GETALL()) {
        Object[] rows = nvd.GETROW(nv);
        tableModel.addRow(rows);
    }
}
public void showdetail(){
    int chon= tblNhanvien.getSelectedRow();
    if(chon >=0){
        Nhanvien nv = nvd.GETALL().get(chon);
        txtIdnv.setText(nv.getID_NV());
        txtSdt.setText(nv.getSDT());
        txtTennv.setText(nv.getHoTen());
        txtChucvu.setText(nv.getChucVu());
     // Thiết lập kích thước cố định cho ảnh (ví dụ: 200x200 pixels)
        int IMAGE_WIDTH = 220;
        int IMAGE_HEIGHT = 240;
        
        if(nv.getIMG() == null || nv.getIMG().equals("NO IMAGE") || nv.getIMG().isEmpty()){
            lblAnh.setText("NO IMAGE");
            lblAnh.setIcon(null);
        } else {
            try {
                strAnh = nv.getIMG();
                // Sử dụng method helper để resize ảnh
                ImageIcon resizedIcon = resizeImage("/Images_nhanvien/" + strAnh, IMAGE_WIDTH, IMAGE_HEIGHT);
                
                if(resizedIcon != null) {
                    lblAnh.setText("");
                    lblAnh.setIcon(resizedIcon);
                } else {
                    lblAnh.setText("Không tìm thấy ảnh");
                    lblAnh.setIcon(null);
                }
            } catch (Exception e) {
                lblAnh.setText("Lỗi hiển thị ảnh");
                lblAnh.setIcon(null);
            }
        }
        String trangThai = nv.getTrangThai();
        if("LOCKED".equalsIgnoreCase(trangThai)){
    btnKhoa.setEnabled(false);
    btnMokhoa.setEnabled(true);
    btnSua.setEnabled(false);
    btnLamMoi.setEnabled(false);
    btnThem.setEnabled(false);
    txtIdnv.setEnabled(false);
    txtSdt.setEnabled(false);
    txtTennv.setEnabled(false);
} else {
    btnKhoa.setEnabled(true);
    btnMokhoa.setEnabled(false);
    btnSua.setEnabled(true);
    btnLamMoi.setEnabled(true);
    btnThem.setEnabled(true);
    txtIdnv.setEnabled(true);
    txtSdt.setEnabled(true);
    txtTennv.setEnabled(true);
}
        
    }
}
public void lammoi(){
   txtIdnv.setText("");
    txtTennv.setText("");
    txtSdt.setText("");
    txtChucvu.setText("STAFF");
    lblAnh.setText("ẢNH NHÂN VIÊN");
    lblAnh.setIcon(null);
    strAnh = "";  
}
public void them(){
    // Validation input
    String ID_NV = txtIdnv.getText().trim();
    String hoTen = txtTennv.getText().trim();
    String SDT = txtSdt.getText().trim();
    String chucVu = txtChucvu.getText().trim();
    
    // Kiểm tra dữ liệu đầu vào
    if(ID_NV.isEmpty() || hoTen.isEmpty() || SDT.isEmpty()){
        JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        return;
    }
    
    // Kiểm tra định dạng số điện thoại
    if(!SDT.matches("^0\\d{9}$")){
        JOptionPane.showMessageDialog(this, "Số điện thoại phải có 10 chữ số và bắt đầu bằng 0!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        return;
    }
    
    // Kiểm tra ID nhân viên đã tồn tại
    for(Nhanvien nv : nvd.GETALL()){
        if(nv.getID_NV().equals(ID_NV)){
            JOptionPane.showMessageDialog(this, "ID nhân viên đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
    }
    
    // SỬA LỖI: Tạo object Nhanvien với constructor đúng
    Nhanvien nv = new Nhanvien(ID_NV, hoTen, SDT, chucVu,strAnh, "");
    nv.setTrangThai("ACTIVE"); // Set trạng thái mặc định
    
    int result = nvd.Themnv(nv);
    if(result == 1){
        fillTable();
        JOptionPane.showMessageDialog(this, "Thêm nhân viên thành công!");
    } else {
        JOptionPane.showMessageDialog(this, "Có lỗi xảy ra khi thêm nhân viên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
    }
}
public void sua(){
    int chon = tblNhanvien.getSelectedRow();
    if(chon < 0){
        JOptionPane.showMessageDialog(this, "Vui lòng chọn nhân viên cần sửa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
        return;
    }
    
    int sua = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn sửa thông tin nhân viên này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
    if(sua == JOptionPane.YES_OPTION){
        // Validation input
        String ID_NV = txtIdnv.getText().trim();
        String hoTen = txtTennv.getText().trim();
        String SDT = txtSdt.getText().trim();
        String chucVu = txtChucvu.getText().trim();
        
        // Kiểm tra dữ liệu đầu vào
        if(ID_NV.isEmpty() || hoTen.isEmpty() || SDT.isEmpty()){
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Kiểm tra định dạng số điện thoại
        if(!SDT.matches("^0\\d{9}$")){
            JOptionPane.showMessageDialog(this, "Số điện thoại phải có 10 chữ số và bắt đầu bằng 0!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Lấy ID nhân viên gốc từ table
        String idNVGoc = nvd.GETALL().get(chon).getID_NV();
        
        // Kiểm tra nếu thay đổi ID và ID mới đã tồn tại
        if(!ID_NV.equals(idNVGoc)){
            for(Nhanvien nv : nvd.GETALL()){
                if(nv.getID_NV().equals(ID_NV)){
                    JOptionPane.showMessageDialog(this, "ID nhân viên mới đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
        }
        
        // SỬA LỖI: Tạo object Nhanvien với constructor đúng
        Nhanvien nv = new Nhanvien(ID_NV, hoTen, SDT, chucVu, strAnh,"");
        nv.setTrangThai("ACTIVE"); // Giữ nguyên trạng thái hoặc set từ UI
        
        int result = nvd.suanv(nv, idNVGoc);
        if(result == 1){
            fillTable();
            JOptionPane.showMessageDialog(this, "Sửa thông tin nhân viên thành công!");
        } else {
            JOptionPane.showMessageDialog(this, "Có lỗi xảy ra khi sửa thông tin nhân viên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}
public void khoa(){
   int chon = tblNhanvien.getSelectedRow();
        if(chon >= 0){
            Nhanvien chonnv = nvd.GETALL().get(chon);
            
            // Kiểm tra nếu nhân viên đã bị khóa
            if("LOCKED".equals(chonnv.getTrangThai())){
                JOptionPane.showMessageDialog(this, "Nhân viên này đã bị khóa!");
                return;
            }
            
            int xacNhan = JOptionPane.showConfirmDialog(this, 
                "Bạn có chắc muốn khóa tài khoản: " + chonnv.getID_NV()+ "?", 
                "Xác nhận khóa", JOptionPane.YES_NO_OPTION);
                
            if(xacNhan == JOptionPane.YES_OPTION){
                int result = nvd.khoaTaiKhoan(chonnv.getID_NV());
                if(result == 1){
                    fillTable();
                    JOptionPane.showMessageDialog(this, "Khóa nhân viên thành công!");
                } else {
                    JOptionPane.showMessageDialog(this, "Có lỗi xảy ra khi khóa nhân viên!");
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn tài khoản cần khóa!");
        } 
}
public void mokhoa(){
    int chon = tblNhanvien.getSelectedRow();
    if(chon >= 0){
        Nhanvien chontk = nvd.GETALL().get(chon);
        
        int xacNhan = JOptionPane.showConfirmDialog(this, 
            "Bạn có chắc muốn mở khóa tài khoản: " + chontk.getID_NV()+ "?", 
            "Xác nhận mở khóa", JOptionPane.YES_NO_OPTION);
            
        if(xacNhan == JOptionPane.YES_OPTION){
            int result = nvd.moKhoaTaiKhoan(chontk.getID_NV());
            if(result == 1){
                fillTable();
                showdetail(); // Refresh button states
                JOptionPane.showMessageDialog(this, "Mở khóa tài khoản thành công!");
            } else {
                JOptionPane.showMessageDialog(this, "Có lỗi xảy ra khi mở khóa tài khoản!");
            }
        }
    } else {
        JOptionPane.showMessageDialog(this, "Vui lòng chọn tài khoản cần mở khóa!");
    }
}
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        lblAnh = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtIdnv = new javax.swing.JTextField();
        txtTennv = new javax.swing.JTextField();
        txtChucvu = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblNhanvien = new javax.swing.JTable();
        btnThem = new javax.swing.JButton();
        btnKhoa = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnLamMoi = new javax.swing.JButton();
        txtSdt = new javax.swing.JTextField();
        btnMokhoa = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanel3.setBackground(new java.awt.Color(133, 151, 186));

        lblAnh.setText("ẢNH NHÂN VIÊN");
        lblAnh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblAnhMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblAnh, javax.swing.GroupLayout.DEFAULT_SIZE, 239, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel1.setFont(new java.awt.Font("Segoe UI Light", 1, 14)); // NOI18N
        jLabel1.setText("ID NHÂN VIÊN");

        jLabel2.setFont(new java.awt.Font("Segoe UI Light", 1, 14)); // NOI18N
        jLabel2.setText("TÊN NHÂN VIÊN");

        txtIdnv.setFont(new java.awt.Font("Segoe UI Light", 1, 12)); // NOI18N

        txtTennv.setFont(new java.awt.Font("Segoe UI Light", 1, 12)); // NOI18N

        txtChucvu.setFont(new java.awt.Font("Segoe UI Light", 1, 12)); // NOI18N
        txtChucvu.setText("STAFF");
        txtChucvu.setActionCommand("<Not Set>");
        txtChucvu.setEnabled(false);
        txtChucvu.setFocusable(false);
        txtChucvu.setOpaque(true);

        jLabel3.setFont(new java.awt.Font("Segoe UI Light", 1, 14)); // NOI18N
        jLabel3.setText("SỐ ĐIỆN THOẠI");

        jLabel4.setFont(new java.awt.Font("Segoe UI Light", 1, 14)); // NOI18N
        jLabel4.setText("CHỨC VỤ");

        tblNhanvien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblNhanvien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblNhanvienMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblNhanvien);

        btnThem.setBackground(new java.awt.Color(31, 51, 86));
        btnThem.setFont(new java.awt.Font("Segoe UI Light", 1, 14)); // NOI18N
        btnThem.setForeground(new java.awt.Color(255, 255, 255));
        btnThem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MainForm_Admin/image/Them.png"))); // NOI18N
        btnThem.setText("THÊM NHÂN VIÊN");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnKhoa.setBackground(new java.awt.Color(31, 51, 86));
        btnKhoa.setFont(new java.awt.Font("Segoe UI Light", 1, 14)); // NOI18N
        btnKhoa.setForeground(new java.awt.Color(255, 255, 255));
        btnKhoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MainForm_Admin/image/lock.png"))); // NOI18N
        btnKhoa.setText("KHÓA NHÂN VIÊN");
        btnKhoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKhoaActionPerformed(evt);
            }
        });

        btnSua.setBackground(new java.awt.Color(31, 51, 86));
        btnSua.setFont(new java.awt.Font("Segoe UI Light", 1, 14)); // NOI18N
        btnSua.setForeground(new java.awt.Color(255, 255, 255));
        btnSua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MainForm_Admin/image/Sua.png"))); // NOI18N
        btnSua.setText("SỬA NHÂN VIÊN");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnLamMoi.setBackground(new java.awt.Color(31, 51, 86));
        btnLamMoi.setFont(new java.awt.Font("Segoe UI Light", 1, 14)); // NOI18N
        btnLamMoi.setForeground(new java.awt.Color(255, 255, 255));
        btnLamMoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MainForm_Admin/image/lamMoi.png"))); // NOI18N
        btnLamMoi.setText("LÀM MỚI ");
        btnLamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiActionPerformed(evt);
            }
        });

        txtSdt.setFont(new java.awt.Font("Segoe UI Light", 1, 12)); // NOI18N

        btnMokhoa.setBackground(new java.awt.Color(31, 51, 86));
        btnMokhoa.setFont(new java.awt.Font("Segoe UI Light", 1, 13)); // NOI18N
        btnMokhoa.setForeground(new java.awt.Color(255, 255, 255));
        btnMokhoa.setText("MỞ KHÓA");
        btnMokhoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMokhoaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnKhoa, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnLamMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(btnMokhoa)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 868, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING))
                                .addGap(29, 29, 29)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtTennv)
                                    .addComponent(txtChucvu, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txtSdt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 331, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtIdnv, javax.swing.GroupLayout.PREFERRED_SIZE, 331, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(59, 59, 59)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(97, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtIdnv, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addGap(28, 28, 28)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTennv, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addGap(31, 31, 31)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(txtSdt, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(33, 33, 33)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtChucvu, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)))
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThem)
                    .addComponent(btnSua)
                    .addComponent(btnKhoa)
                    .addComponent(btnLamMoi))
                .addGap(14, 14, 14)
                .addComponent(btnMokhoa)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 6, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        // TODO add your handling code here:
        them();
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        // TODO add your handling code here:
        sua();
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiActionPerformed
        // TODO add your handling code here:
        lammoi();
    }//GEN-LAST:event_btnLamMoiActionPerformed

    private void lblAnhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAnhMouseClicked
        // TODO add your handling code here:
         JFileChooser jFC = new JFileChooser("src\\Images_nhanvien");
    // Thêm filter để chỉ chọn file ảnh
    jFC.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
        "Image Files", "jpg", "jpeg", "png", "gif", "bmp"));
    
    int result = jFC.showOpenDialog(this);
    if(result == JFileChooser.APPROVE_OPTION) {
        File file = jFC.getSelectedFile();
        
        // Kiểm tra xem file có tồn tại không
        if(file != null && file.exists()) {
            try {
                // Thiết lập kích thước cố định
                int IMAGE_WIDTH = 220;
                int IMAGE_HEIGHT = 240;
                
                // Đọc ảnh từ file
                BufferedImage originalImage = ImageIO.read(file);
                
                // Resize ảnh với kích thước cố định
                Image resizedImage = originalImage.getScaledInstance(
                    IMAGE_WIDTH, IMAGE_HEIGHT, Image.SCALE_SMOOTH);
                
                // Hiển thị ảnh đã resize
                ImageIcon imageIcon = new ImageIcon(resizedImage);
                lblAnh.setText("");
                lblAnh.setIcon(imageIcon);
                
                // Lưu tên file để sử dụng sau này
                strAnh = file.getName();
                
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Lỗi khi đọc file ảnh: " + ex.getMessage(), 
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
                lblAnh.setText("Lỗi đọc ảnh");
                lblAnh.setIcon(null);
            }
        }
    }
    }//GEN-LAST:event_lblAnhMouseClicked

    private void tblNhanvienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNhanvienMouseClicked
        // TODO add your handling code here:
        showdetail();
    }//GEN-LAST:event_tblNhanvienMouseClicked

    private void btnMokhoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMokhoaActionPerformed
        // TODO add your handling code here:
        mokhoa();
    }//GEN-LAST:event_btnMokhoaActionPerformed

    private void btnKhoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKhoaActionPerformed
        // TODO add your handling code here:
        khoa();
    }//GEN-LAST:event_btnKhoaActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(QuanLyNhanVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(QuanLyNhanVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(QuanLyNhanVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(QuanLyNhanVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new QuanLyNhanVien().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnKhoa;
    private javax.swing.JButton btnLamMoi;
    private javax.swing.JButton btnMokhoa;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblAnh;
    private javax.swing.JTable tblNhanvien;
    private javax.swing.JTextField txtChucvu;
    private javax.swing.JTextField txtIdnv;
    private javax.swing.JTextField txtSdt;
    private javax.swing.JTextField txtTennv;
    // End of variables declaration//GEN-END:variables
}
