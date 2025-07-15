/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import DAO.HoaDonChoDAO;
import DAO.HoaDonDAO;
import DAO.SanPhamDAO;
import DAO.UuDaiDAO;
import Model.ChiTietHoaDon;
import Model.HoaDonCho;
import Model.SanPham;
import Model.UuDai;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ADMIN
 */
public class StaffBanHang extends javax.swing.JFrame {

    DefaultTableModel modelUuDai;
    DefaultTableModel modelHDCho;
    DefaultTableModel modelCTHD;
    ChiTietHoaDon cthd = new ChiTietHoaDon();
    UuDaiDAO udd = new UuDaiDAO();
    HoaDonChoDAO hdd = new HoaDonChoDAO();
    HoaDonDAO hd = new HoaDonDAO();
    SanPhamDAO spd = new SanPhamDAO();
    String strAnh = "";

    /**
     * Creates new form StaffBanHang
     */
    public StaffBanHang() {
        initComponents();
        initTable();
        fillTableHDCho();
        fillTableCTHD();
        fillTableMenu();
        setLocationRelativeTo(null);
        Timer timer = new Timer(0, (e) -> {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
            String time = sdf.format(new Date());
            lblTime.setText(time);
        });
        timer.start();

    }

    private String formatVND(float amount) {
        DecimalFormat formatter = new DecimalFormat("#,###");
        return formatter.format(amount) + " VND";
    }

    public void initTable() {
        modelUuDai = new DefaultTableModel();
        String[] colsUuDai = new String[]{"Giá trị", "Số lượng"};
        modelUuDai.setColumnIdentifiers(colsUuDai);
        tblUuDai.setModel(modelUuDai);

        modelHDCho = new DefaultTableModel();
        String[] colsHDCho = new String[]{"ID_Hóa Đơn", "Tổng tiền"};
        modelHDCho.setColumnIdentifiers(colsHDCho);
        tblHoaDon.setModel(modelHDCho);

        modelCTHD = new DefaultTableModel();
        String[] cols = new String[]{"ID_Sản Phẩm", "Tên sản phẩm", "Giá sản phẩm", "Số Lượng"};
        modelCTHD.setColumnIdentifiers(cols);
        tblChiTietHoaDon.setModel(modelCTHD);
    }

    public void fillTableHDCho() {
        modelHDCho.setRowCount(0); // Xóa sạch bảng

        List<HoaDonCho> list = hdd.getALLHDCHO(); // Lấy tất cả hóa đơn chờ
        for (HoaDonCho hdc : list) {
            modelHDCho.addRow(new Object[]{
                hdc.getID_HD(),
                formatVND(hdc.getTongTien())
            });
        }
    }

    public void fillTableCTHD() {
        DefaultTableModel model = (DefaultTableModel) tblChiTietHoaDon.getModel();
        model.setRowCount(0);
        String ID_HD = lblMaHD.getText().trim();
        List<ChiTietHoaDon> lstcthd = hdd.getAllID_HD(ID_HD);
        for (ChiTietHoaDon cthd : lstcthd) {
            model.addRow(new Object[]{
                cthd.getID_SP(),
                cthd.getTenSP(),
                cthd.getGiaSP(),
                cthd.getSoLuong()
            });
        }
    }

    public void fillTableMenu() {
        // Xóa tất cả component cũ
        pnlMenu.removeAll();

        // Tạo panel chứa để có thể cuộn
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridLayout(0, 5, 10, 10)); // 0 rows, 5 columns, với khoảng cách 10px
        contentPanel.setBackground(Color.WHITE);

        // Thêm padding cho content panel
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        List<SanPham> list = spd.getAll();
        int itemWidth = 140;
        int itemHeight = 200;

        for (SanPham sp : list) {
            if (sp.getTrangThai() != 1) {
                continue; // ❗️Chỉ hiển thị sản phẩm đang hoạt động
            }

            JPanel panel = new JPanel(new BorderLayout());
            panel.setPreferredSize(new Dimension(itemWidth, itemHeight));
            panel.setMinimumSize(new Dimension(itemWidth, itemHeight));
            panel.setMaximumSize(new Dimension(itemWidth, itemHeight));
            panel.setBackground(Color.WHITE);
            panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

            // Tạo label mã sản phẩm
            JLabel lblMa = new JLabel(sp.getIDSanPham(), SwingConstants.CENTER);
            lblMa.setFont(new Font("Segoe UI", Font.BOLD, 15));
            lblMa.setPreferredSize(new Dimension(itemWidth, 30));

            // Tạo label hình ảnh
            JLabel lblImage = new JLabel("", SwingConstants.CENTER);
            lblImage.setPreferredSize(new Dimension(80, 90));
            try {
                ImageIcon icon = new ImageIcon("src/Images_SanPham/" + sp.getIMG());
                if (icon.getIconWidth() > 0) {
                    lblImage.setIcon(new ImageIcon(icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
                } else {
                    lblImage.setText("Không có ảnh");
                    lblImage.setFont(new Font("Segoe UI", Font.PLAIN, 10));
                }
            } catch (Exception e) {
                lblImage.setText("Không có ảnh");
                lblImage.setFont(new Font("Segoe UI", Font.PLAIN, 10));
            }

            // Tạo panel chứa thông tin dưới
            JPanel bottom = new JPanel();
            bottom.setLayout(new BoxLayout(bottom, BoxLayout.Y_AXIS));
            bottom.setBackground(Color.WHITE);
            bottom.setPreferredSize(new Dimension(itemWidth, 45));

            JLabel lblTen = new JLabel(sp.getTenSanPham(), SwingConstants.CENTER);
            lblTen.setFont(new Font("Segoe UI", Font.PLAIN, 10));
            lblTen.setAlignmentX(Component.CENTER_ALIGNMENT);

            // Cắt ngắn tên sản phẩm nếu quá dài
            if (sp.getTenSanPham().length() > 15) {
                lblTen.setText(sp.getTenSanPham().substring(0, 20) + "...");
            }

            JLabel lblGia = new JLabel(formatVND(sp.getGiaTien()), SwingConstants.CENTER);
            lblGia.setFont(new Font("Segoe UI", Font.BOLD, 18));
            lblGia.setAlignmentX(Component.CENTER_ALIGNMENT);

            bottom.add(lblTen);
            bottom.add(lblGia);

            // Thêm các component vào panel
            panel.add(lblMa, BorderLayout.NORTH);
            panel.add(lblImage, BorderLayout.CENTER);
            panel.add(bottom, BorderLayout.SOUTH);

            // Thêm sự kiện mouse
            panel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    panel.setBackground(new Color(240, 240, 240));
                    panel.setBorder(BorderFactory.createLineBorder(new Color(0, 120, 200), 2));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    panel.setBackground(Color.WHITE);
                    panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                }

                @Override
                public void mouseClicked(MouseEvent e) {
                    showDetail(sp);
                }
            });

            panel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            contentPanel.add(panel);
        }

        // Tạo JScrollPane cho content panel
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // Không cho cuộn ngang
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // Tăng tốc độ cuộn
        scrollPane.setBorder(null); // Bỏ border của scroll pane

        // Thiết lập viewport để không thay đổi kích thước
        scrollPane.setPreferredSize(pnlMenu.getSize());
        scrollPane.setMinimumSize(pnlMenu.getSize());

        // Đặt layout cho pnlMenu và thêm scrollPane
        pnlMenu.setLayout(new BorderLayout());
        pnlMenu.add(scrollPane, BorderLayout.CENTER);

        // Refresh panel
        pnlMenu.revalidate();
        pnlMenu.repaint();
    }

    private void showDetail(SanPham sp) {
        if (sp == null) {
            return;
        }

        lblMaSP.setText(sp.getIDSanPham());
        txtSoLuong.setText("1");
        txtSoLuong.requestFocus();
        txtSoLuong.selectAll();

        try {
            ImageIcon icon = new ImageIcon("src/Images_SanPham/" + sp.getIMG());
            if (icon.getIconWidth() > 0) {
                // Sử dụng kích thước cố định thay vì kích thước hiện tại của label
                int fixedWidth = 156;  // Kích thước cố định từ layout
                int fixedHeight = 156; // Kích thước cố định (có thể điều chỉnh tỷ lệ)

                lblAnhSanPham.setIcon(new ImageIcon(icon.getImage().getScaledInstance(
                        fixedWidth, fixedHeight, Image.SCALE_SMOOTH)));
                lblAnhSanPham.setText("");
            } else {
                lblAnhSanPham.setIcon(null);
                lblAnhSanPham.setText("Không có ảnh");
            }
        } catch (Exception e) {
            lblAnhSanPham.setIcon(null);
            lblAnhSanPham.setText("Không có ảnh");
        }

        // Đảm bảo label giữ nguyên kích thước
        lblAnhSanPham.revalidate();
        lblAnhSanPham.repaint();
    }

    public void showDetailsHDCho() {
        int i = tblHoaDon.getSelectedRow();
        if (i >= 0) {
            String ID_HD = tblHoaDon.getValueAt(i, 0).toString();
            lblMaHD.setText(ID_HD);
            List<ChiTietHoaDon> lstcthd = hdd.getAllID_HD(ID_HD);
            modelCTHD.setRowCount(0);

            for (ChiTietHoaDon cthd : lstcthd) {
                modelCTHD.addRow(new Object[]{
                    cthd.getID_SP(),
                    cthd.getTenSP(),
                    cthd.getGiaSP(),
                    cthd.getSoLuong()
                });
            }
        }
    }

    public void showDetailsUuDai() {
        int i = tblUuDai.getSelectedRow();
        if (i >= 0) {
            lblUuDai.setText(tblUuDai.getValueAt(i, 0).toString());
        }
    }

    public void showDetailsCTHD() {
        int i = tblChiTietHoaDon.getSelectedRow();
        if (i < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một dòng trong bảng chi tiết!");
            return;
        }

        // Lấy dữ liệu từ bảng
        String maHD = lblMaHD.getText().trim(); // mã hóa đơn không có trong bảng
        String maSP = tblChiTietHoaDon.getValueAt(i, 0).toString(); // cột 0: ID_SP

        // Gọi DAO để lấy chi tiết hóa đơn
        ChiTietHoaDon cthd = hdd.selectCTHD(maHD, maSP);
        if (cthd == null) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy chi tiết hóa đơn!");
            return;
        }

        // Set dữ liệu lên giao diện
        lblMaHD.setText(cthd.getID_HD());
        lblMaSP.setText(cthd.getID_SP());
        txtSoLuong.setText(String.valueOf(cthd.getSoLuong()));

        // Lấy ảnh sản phẩm từ mã SP
        List<SanPham> spList = spd.getAllID_SP(maSP);
        if (spList == null || spList.isEmpty()) {
            lblAnhSanPham.setText("Không tìm thấy sản phẩm");
            lblAnhSanPham.setIcon(null);
            return;
        }

        SanPham sp = spList.get(0);
        String tenAnh = sp.getIMG();

        if (tenAnh == null || tenAnh.trim().isEmpty() || tenAnh.equalsIgnoreCase("NO IMAGE")) {
            lblAnhSanPham.setText("Hình ảnh không tồn tại");
            lblAnhSanPham.setIcon(null);
        } else {
            try {
                String duongDan = "src/Images_SanPham/" + tenAnh;
                File file = new File(duongDan);
                if (!file.exists()) {
                    lblAnhSanPham.setText("Không tìm thấy ảnh");
                    lblAnhSanPham.setIcon(null);
                    return;
                }
                ImageIcon icon = new ImageIcon(duongDan);
                Image img = icon.getImage().getScaledInstance(lblAnhSanPham.getWidth(), lblAnhSanPham.getHeight(), Image.SCALE_SMOOTH);
                lblAnhSanPham.setIcon(new ImageIcon(img));
                lblAnhSanPham.setText("");
            } catch (Exception e) {
                lblAnhSanPham.setText("Lỗi ảnh");
                lblAnhSanPham.setIcon(null);
            }
        }
    }

    private String generateMaHD() {
        Random rnd = new Random();
        int number = 100000 + rnd.nextInt(900000);
        return "HD" + number;
    }

    public void createMaHD() {
        hdd.clearOrderTemp();

        DefaultTableModel model = (DefaultTableModel) tblChiTietHoaDon.getModel();
        String newMaHD = generateMaHD();
        lblMaHD.setText(newMaHD);

        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm:ss");
        Date now = new Date();

        String ngayThangNam = sdfDate.format(now);
        String thoiGian = sdfTime.format(now);
        float tongTien = 0.0f;
        HoaDonCho newHD = new HoaDonCho(newMaHD, ngayThangNam, thoiGian, tongTien);
        hdd.SaveHDCHO(newHD);

        lblMaSP.setText("");
        model.setRowCount(0);
        fillTableHDCho();
    }

    public void addSP() {
        String ID_SP = lblMaSP.getText();
        String ID_HD = lblMaHD.getText();
        int soLuong = Integer.parseInt(txtSoLuong.getText());

        if (ID_HD.isEmpty()) {
            JOptionPane.showMessageDialog(this, "CHƯA CÓ HÓA ĐƠN");
            return;
        }
        ChiTietHoaDon cthdCu = hdd.selectCTHD(ID_HD, ID_SP);
        if (cthdCu != null) {
            int tongSoLuong = cthdCu.getSoLuong() + soLuong;
            cthdCu.setSoLuong(tongSoLuong); // Cập nhật số lượng mới
            hdd.UpdateSP(ID_HD, ID_SP, cthdCu);
        } else {
            for (SanPham sp : spd.getAll()) {
                if (sp.getIDSanPham().equals(ID_SP)) {
                    ChiTietHoaDon cthd = new ChiTietHoaDon();
                    cthd.setID_HD(ID_HD);
                    cthd.setID_SP(sp.getIDSanPham());
                    cthd.setTenSP(sp.getTenSanPham());
                    cthd.setGiaSP(sp.getGiaTien());
                    cthd.setSoLuong(soLuong);
                    hdd.SaveCTHD(cthd);
                    break;
                }
            }
        }
        float tong = 0;
        List<ChiTietHoaDon> list = hdd.getAllID_HD(ID_HD);
        for (ChiTietHoaDon ct : list) {
            tong += ct.getGiaSP() * ct.getSoLuong();
        }
        hdd.updateTongTien(ID_HD, tong);
        fillTableHDCho();
        fillTableCTHD();
    }

    public void deleteHD() {
        int i = tblHoaDon.getSelectedRow();
        if (i >= 0) {
            int choose = JOptionPane.showConfirmDialog(this, "XÁC NHẬN", "BẠN MUỐN HỦY", JOptionPane.YES_NO_OPTION);
            if (choose == JOptionPane.YES_OPTION) {
                String ID_HD = tblHoaDon.getValueAt(i, 0).toString();
                int res1 = hdd.DeleteCTHD(ID_HD);
                int res2 = hdd.DeleteHD(ID_HD);
                if (res1 == 1 && res2 == 1) {
                    fillTableHDCho();
                    lblMaHD.setText("");
                    fillTableCTHD();
                    JOptionPane.showMessageDialog(this, " HỦY THÀNH CÔNG");
                } else {
                    JOptionPane.showMessageDialog(this, "HỦY THẤT BẠI");
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "VUI LÒNG CHỌN HÓA ĐƠN MUỐN HỦY!");
        }
    }

    public void deleteSP() {
        String ID_HD = lblMaHD.getText();
        float tong = 0;
        float tru = 0;
        int i = tblChiTietHoaDon.getSelectedRow();
        if (i >= 0) {
            int choose = JOptionPane.showConfirmDialog(this, "XÁC NHẬN", "BẠN MUỐN XÓA", JOptionPane.YES_NO_OPTION);
            if (choose == JOptionPane.YES_OPTION) {
                String ID_SP = tblChiTietHoaDon.getValueAt(i, 0).toString();

                int result = hdd.DeleteSP(ID_SP, ID_HD);
                List<ChiTietHoaDon> list = hdd.getAllID_HD(ID_HD);
                for (ChiTietHoaDon ct : list) {
                    tong += ct.getGiaSP() * ct.getSoLuong();
                }
                hdd.updateTongTien(ID_HD, tong);
                fillTableCTHD();
                fillTableHDCho();
                JOptionPane.showMessageDialog(this, "XÓA THÀNH CÔNG");
            } else {
                JOptionPane.showMessageDialog(this, "XÓA KHÔNG THÀNH CÔNG");
            }
        } else {
            JOptionPane.showMessageDialog(this, "VUI LÒNG CHỌN SẢN PHẨM MUỐN XÓA!");
        }
    }

    public void updateSP() {
        float tong = 0;
        int i = tblChiTietHoaDon.getSelectedRow();
        if (i >= 0) {
            String ID_HD = lblMaHD.getText();
            String ID_SP = tblChiTietHoaDon.getValueAt(i, 0).toString();
            String tensp = tblChiTietHoaDon.getValueAt(i, 1).toString();
            float gia = Float.parseFloat(tblChiTietHoaDon.getValueAt(i, 2).toString());
            int soLuong = Integer.parseInt(txtSoLuong.getText());
            ChiTietHoaDon cthd = new ChiTietHoaDon(ID_HD, ID_SP, tensp, gia, soLuong);
            int result = hdd.UpdateSP(ID_HD, ID_SP, cthd);
            if (result == 1) {
                List<ChiTietHoaDon> list = hdd.getAllID_HD(ID_HD);
                for (ChiTietHoaDon ct : list) {
                    tong += ct.getGiaSP() * ct.getSoLuong();
                }
                hdd.updateTongTien(ID_HD, tong);
                fillTableCTHD();
                fillTableHDCho();
                JOptionPane.showMessageDialog(this, "SỬA THÀNH CÔNG");
            } else {
                JOptionPane.showMessageDialog(this, "SỬA KHÔNG THÀNH CÔNG");
            }
        } else {
            JOptionPane.showMessageDialog(this, "VUI LÒNG CHỌN SẢN PHẨM MUỐN SỬA!");
        }
    }

    public boolean validateForm() {
        String maHD = lblMaHD.getText().trim();
        String maSP = lblMaSP.getText().trim();
        String soLuongStr = txtSoLuong.getText().trim();

        if (maHD.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn!");
            return false;
        }

        if (maSP.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm!");
            return false;
        }

        if (soLuongStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập số lượng!");
            return false;
        }

        try {
            int soLuong = Integer.parseInt(soLuongStr);
            if (soLuong <= 0) {
                JOptionPane.showMessageDialog(this, "Số lượng phải là số nguyên dương!");
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Số lượng phải là số nguyên!");
            return false;
        }

        return true; // Hợp lệ
    }

    public void payment() {
        if (lblMaHD.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn Hóa Đơn cần thanh toán");
            return;
        }
        if (tblHoaDon.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Hóa đơn chưa có món nào!");
            return;
        }

        StringBuilder bill = new StringBuilder();
        String maHD = lblMaHD.getText();

        bill.append("======= HÓA ĐƠN: ").append(maHD).append(" =======\n");
        bill.append(String.format("%-20s %-10s %-10s\n", "Tên SP", "Số lượng", "Đơn giá"));
        bill.append("----------------------------------------\n");

        double tongTien = 0;

        for (int i = 0; i < tblHoaDon.getRowCount(); i++) {
            String tenSP = tblHoaDon.getValueAt(i, 1).toString();
            int soLuong = Integer.parseInt(tblHoaDon.getValueAt(i, 2).toString());
            double donGia = Double.parseDouble(tblHoaDon.getValueAt(i, 3).toString());

            tongTien += soLuong * donGia;

            bill.append(String.format("%-20s %-10d %-10.0f\n", tenSP, soLuong, donGia));
        }

        bill.append("----------------------------------------\n");

        

        bill.append(String.format("TỔNG TIỀN: %.0f VNĐ\n", tongTien));
        bill.append("========================================\n");

        JTextArea txtBill = new JTextArea(bill.toString());
        txtBill.setEditable(false);
        txtBill.setFont(new Font("Monospaced", Font.PLAIN, 14));

        JScrollPane scroll = new JScrollPane(txtBill);
        scroll.setPreferredSize(new Dimension(400, 300));

        JOptionPane.showMessageDialog(this, scroll, "Chi tiết thanh toán", JOptionPane.INFORMATION_MESSAGE);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlThongTin = new javax.swing.JPanel();
        lblTittlePnlThongTin = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        lblTittleMaHD = new javax.swing.JLabel();
        lblMaHD = new javax.swing.JLabel();
        lblMaSP = new javax.swing.JLabel();
        lblTittleMaSP = new javax.swing.JLabel();
        lblTittleSoLuong = new javax.swing.JLabel();
        txtSoLuong = new javax.swing.JTextField();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        lblTittleUuDai = new javax.swing.JLabel();
        lblUuDai = new javax.swing.JLabel();
        btnTaoHoaDon = new javax.swing.JButton();
        lblAnhSanPham = new javax.swing.JLabel();
        pnlUuDai = new javax.swing.JPanel();
        lblTittlePnlUuDai = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblUuDai = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        pnlMenu = new javax.swing.JPanel();
        lblTittlePnlMenu = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        pnlChiTietHoaDon = new javax.swing.JPanel();
        lblTittlePnlChiTietHoaDon = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblChiTietHoaDon = new javax.swing.JTable();
        pnlHoaDon = new javax.swing.JPanel();
        lblTittlePnlHoaDon = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblHoaDon = new javax.swing.JTable();
        btnThanhToan = new javax.swing.JButton();
        btnHuyDon = new javax.swing.JButton();
        lblTime = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pnlThongTin.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lblTittlePnlThongTin.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTittlePnlThongTin.setForeground(new java.awt.Color(0, 51, 102));
        lblTittlePnlThongTin.setText("THÔNG TIN HÓA ĐƠN:");

        lblTittleMaHD.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTittleMaHD.setText("MÃ HÓA ĐƠN:");

        lblMaHD.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblMaHD.setForeground(new java.awt.Color(255, 0, 0));

        lblMaSP.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblMaSP.setForeground(new java.awt.Color(255, 0, 0));

        lblTittleMaSP.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTittleMaSP.setText("MÃ SẢN PHẨM:");

        lblTittleSoLuong.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTittleSoLuong.setText("SỐ LƯỢNG:");

        txtSoLuong.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        btnThem.setBackground(new java.awt.Color(31, 51, 86));
        btnThem.setForeground(new java.awt.Color(255, 255, 255));
        btnThem.setText("THÊM");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnSua.setBackground(new java.awt.Color(31, 51, 86));
        btnSua.setForeground(new java.awt.Color(255, 255, 255));
        btnSua.setText("SỬA");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnXoa.setBackground(new java.awt.Color(31, 51, 86));
        btnXoa.setForeground(new java.awt.Color(255, 255, 255));
        btnXoa.setText("XÓA");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        lblTittleUuDai.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTittleUuDai.setText("ƯU ĐÃI:");

        lblUuDai.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblUuDai.setForeground(new java.awt.Color(255, 0, 0));

        btnTaoHoaDon.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnTaoHoaDon.setForeground(new java.awt.Color(255, 0, 0));
        btnTaoHoaDon.setText("TẠO HÓA ĐƠN");
        btnTaoHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTaoHoaDonActionPerformed(evt);
            }
        });

        lblAnhSanPham.setBackground(new java.awt.Color(0, 51, 102));
        lblAnhSanPham.setForeground(new java.awt.Color(0, 51, 102));
        lblAnhSanPham.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 71, 141), 5));
        lblAnhSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblAnhSanPhamMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout pnlThongTinLayout = new javax.swing.GroupLayout(pnlThongTin);
        pnlThongTin.setLayout(pnlThongTinLayout);
        pnlThongTinLayout.setHorizontalGroup(
            pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlThongTinLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1)
                    .addGroup(pnlThongTinLayout.createSequentialGroup()
                        .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTittlePnlThongTin)
                            .addComponent(lblTittleUuDai)
                            .addGroup(pnlThongTinLayout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addComponent(btnThem)
                                .addGap(13, 13, 13)
                                .addComponent(btnSua)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnXoa)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(pnlThongTinLayout.createSequentialGroup()
                        .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lblTittleMaHD)
                                .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblTittleSoLuong)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlThongTinLayout.createSequentialGroup()
                                        .addGap(19, 19, 19)
                                        .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addComponent(lblTittleMaSP)
                                .addComponent(lblMaSP, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblMaHD, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlThongTinLayout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addComponent(lblUuDai, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnTaoHoaDon, javax.swing.GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE)
                            .addGroup(pnlThongTinLayout.createSequentialGroup()
                                .addComponent(lblAnhSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        pnlThongTinLayout.setVerticalGroup(
            pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlThongTinLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTittlePnlThongTin)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 3, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlThongTinLayout.createSequentialGroup()
                        .addComponent(lblTittleMaHD)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblMaHD, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(23, 23, 23)
                        .addComponent(lblTittleMaSP)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblMaSP, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblTittleSoLuong))
                    .addGroup(pnlThongTinLayout.createSequentialGroup()
                        .addComponent(lblAnhSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 16, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThem)
                    .addComponent(btnSua)
                    .addComponent(btnXoa))
                .addGap(17, 17, 17)
                .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlThongTinLayout.createSequentialGroup()
                        .addComponent(lblTittleUuDai)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblUuDai, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnTaoHoaDon))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        pnlUuDai.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lblTittlePnlUuDai.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTittlePnlUuDai.setForeground(new java.awt.Color(0, 51, 102));
        lblTittlePnlUuDai.setText("ƯU ĐÃI:");

        tblUuDai.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tblUuDai.setModel(new javax.swing.table.DefaultTableModel(
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
        tblUuDai.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblUuDaiMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblUuDai);

        jButton1.setBackground(new java.awt.Color(31, 51, 86));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("ÁP DỤNG");

        javax.swing.GroupLayout pnlUuDaiLayout = new javax.swing.GroupLayout(pnlUuDai);
        pnlUuDai.setLayout(pnlUuDaiLayout);
        pnlUuDaiLayout.setHorizontalGroup(
            pnlUuDaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlUuDaiLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlUuDaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlUuDaiLayout.createSequentialGroup()
                        .addComponent(lblTittlePnlUuDai)
                        .addGap(0, 141, Short.MAX_VALUE))
                    .addComponent(jSeparator3)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(pnlUuDaiLayout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addComponent(jButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlUuDaiLayout.setVerticalGroup(
            pnlUuDaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlUuDaiLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTittlePnlUuDai)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlMenu.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lblTittlePnlMenu.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTittlePnlMenu.setForeground(new java.awt.Color(0, 51, 102));
        lblTittlePnlMenu.setText("MENU:");

        javax.swing.GroupLayout pnlMenuLayout = new javax.swing.GroupLayout(pnlMenu);
        pnlMenu.setLayout(pnlMenuLayout);
        pnlMenuLayout.setHorizontalGroup(
            pnlMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMenuLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator2)
                    .addGroup(pnlMenuLayout.createSequentialGroup()
                        .addComponent(lblTittlePnlMenu)
                        .addGap(0, 822, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnlMenuLayout.setVerticalGroup(
            pnlMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMenuLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTittlePnlMenu)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlChiTietHoaDon.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lblTittlePnlChiTietHoaDon.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTittlePnlChiTietHoaDon.setForeground(new java.awt.Color(0, 51, 102));
        lblTittlePnlChiTietHoaDon.setText("CHI TIẾT HÓA ĐƠN:");

        tblChiTietHoaDon.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tblChiTietHoaDon.setModel(new javax.swing.table.DefaultTableModel(
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
        tblChiTietHoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblChiTietHoaDonMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblChiTietHoaDon);

        javax.swing.GroupLayout pnlChiTietHoaDonLayout = new javax.swing.GroupLayout(pnlChiTietHoaDon);
        pnlChiTietHoaDon.setLayout(pnlChiTietHoaDonLayout);
        pnlChiTietHoaDonLayout.setHorizontalGroup(
            pnlChiTietHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlChiTietHoaDonLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlChiTietHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlChiTietHoaDonLayout.createSequentialGroup()
                        .addComponent(lblTittlePnlChiTietHoaDon)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jSeparator4)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 746, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlChiTietHoaDonLayout.setVerticalGroup(
            pnlChiTietHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlChiTietHoaDonLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTittlePnlChiTietHoaDon)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        pnlHoaDon.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lblTittlePnlHoaDon.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTittlePnlHoaDon.setForeground(new java.awt.Color(0, 51, 102));
        lblTittlePnlHoaDon.setText("HÓA ĐƠN:");

        tblHoaDon.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tblHoaDon.setModel(new javax.swing.table.DefaultTableModel(
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
        tblHoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHoaDonMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblHoaDon);

        btnThanhToan.setBackground(new java.awt.Color(31, 51, 86));
        btnThanhToan.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnThanhToan.setForeground(new java.awt.Color(255, 255, 255));
        btnThanhToan.setText("THANH TOÁN");
        btnThanhToan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThanhToanActionPerformed(evt);
            }
        });

        btnHuyDon.setBackground(new java.awt.Color(31, 51, 86));
        btnHuyDon.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnHuyDon.setForeground(new java.awt.Color(255, 0, 0));
        btnHuyDon.setText("HỦY ĐƠN");
        btnHuyDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHuyDonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlHoaDonLayout = new javax.swing.GroupLayout(pnlHoaDon);
        pnlHoaDon.setLayout(pnlHoaDonLayout);
        pnlHoaDonLayout.setHorizontalGroup(
            pnlHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHoaDonLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlHoaDonLayout.createSequentialGroup()
                        .addGroup(pnlHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(jSeparator5)
                            .addGroup(pnlHoaDonLayout.createSequentialGroup()
                                .addComponent(lblTittlePnlHoaDon)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(pnlHoaDonLayout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(btnThanhToan)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnHuyDon)
                        .addGap(18, 18, 18))))
        );
        pnlHoaDonLayout.setVerticalGroup(
            pnlHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHoaDonLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTittlePnlHoaDon)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 4, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(pnlHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThanhToan)
                    .addComponent(btnHuyDon))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        lblTime.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTime.setForeground(new java.awt.Color(0, 51, 102));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pnlThongTin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(pnlMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pnlUuDai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(pnlChiTietHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(pnlHoaDon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(28, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblTime, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTime, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnlMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlThongTin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnlUuDai, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlChiTietHoaDon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        // TODO add your handling code here:
        if (!validateForm()) {
            return;
        } else {
            addSP();
        }
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnTaoHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTaoHoaDonActionPerformed
        // TODO add your handling code here:
        createMaHD();
    }//GEN-LAST:event_btnTaoHoaDonActionPerformed

    private void btnThanhToanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThanhToanActionPerformed
        // TODO add your handling code here:
        payment();
    }//GEN-LAST:event_btnThanhToanActionPerformed

    private void tblHoaDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHoaDonMouseClicked
        // TODO add your handling code here:
        showDetailsHDCho();
    }//GEN-LAST:event_tblHoaDonMouseClicked

    private void tblUuDaiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblUuDaiMouseClicked
        // TODO add your handling code here:
        showDetailsUuDai();
    }//GEN-LAST:event_tblUuDaiMouseClicked

    private void lblAnhSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAnhSanPhamMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_lblAnhSanPhamMouseClicked

    private void btnHuyDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHuyDonActionPerformed
        // TODO add your handling code here:
        deleteHD();
    }//GEN-LAST:event_btnHuyDonActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        // TODO add your handling code here:
        deleteSP();
    }//GEN-LAST:event_btnXoaActionPerformed

    private void tblChiTietHoaDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblChiTietHoaDonMouseClicked
        // TODO add your handling code here:
        showDetailsCTHD();
    }//GEN-LAST:event_tblChiTietHoaDonMouseClicked

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        // TODO add your handling code here:
        if (!validateForm()) {
            return;
        } else {
            updateSP();
        }
    }//GEN-LAST:event_btnSuaActionPerformed

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
            java.util.logging.Logger.getLogger(StaffBanHang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(StaffBanHang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(StaffBanHang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(StaffBanHang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new StaffBanHang().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnHuyDon;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnTaoHoaDon;
    private javax.swing.JButton btnThanhToan;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JLabel lblAnhSanPham;
    private javax.swing.JLabel lblMaHD;
    private javax.swing.JLabel lblMaSP;
    private javax.swing.JLabel lblTime;
    private javax.swing.JLabel lblTittleMaHD;
    private javax.swing.JLabel lblTittleMaSP;
    private javax.swing.JLabel lblTittlePnlChiTietHoaDon;
    private javax.swing.JLabel lblTittlePnlHoaDon;
    private javax.swing.JLabel lblTittlePnlMenu;
    private javax.swing.JLabel lblTittlePnlThongTin;
    private javax.swing.JLabel lblTittlePnlUuDai;
    private javax.swing.JLabel lblTittleSoLuong;
    private javax.swing.JLabel lblTittleUuDai;
    private javax.swing.JLabel lblUuDai;
    private javax.swing.JPanel pnlChiTietHoaDon;
    private javax.swing.JPanel pnlHoaDon;
    private javax.swing.JPanel pnlMenu;
    private javax.swing.JPanel pnlThongTin;
    private javax.swing.JPanel pnlUuDai;
    private javax.swing.JTable tblChiTietHoaDon;
    private javax.swing.JTable tblHoaDon;
    private javax.swing.JTable tblUuDai;
    private javax.swing.JTextField txtSoLuong;
    // End of variables declaration//GEN-END:variables
}
