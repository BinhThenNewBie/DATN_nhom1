package view;

import DAO.HoaDonChoDAO;
import DAO.SanPhamDAO;
import Model.ChiTietHoaDon;
import Model.SanPham;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
/**
 *
 * @author Admin
 */
public class QuanLySanPham extends javax.swing.JFrame {

    DefaultTableModel tableModel = new DefaultTableModel();
    SanPhamDAO spDao = new SanPhamDAO();
    String strAnh = "";
    HoaDonChoDAO hdd = new HoaDonChoDAO();

    /**
     * Creates new form QuanLySanPham
     */
    public QuanLySanPham() {
        initComponents();
        setLocationRelativeTo(null); // Đưa ra giữa màn hình
        setResizable(false);         // Không cho resize (tùy chọn)        
        initTable();
        fillTable();
    }

    public void initTable() {
        String[] cols = new String[]{"ID SẢN PHẨM", "TÊN SẢN PHẨM", "GIÁ TIỀN", "LOẠI SẢN PHẨM", "Ảnh", "TRẠNG THÁI"};
        tableModel.setColumnIdentifiers(cols);
        tblBang.setModel(tableModel);
    }

    public void fillTable() {
        tableModel.setRowCount(0);
        for (SanPham sp : spDao.getAll()) {
            tableModel.addRow(spDao.getRow(sp));
        }
    }

    public void showdetail() {
        int i = tblBang.getSelectedRow();
        if (i >= 0) {
            String timKiem = txtTimkiem.getText().trim();
            SanPham sp;

            if (timKiem.isEmpty()) {
                sp = spDao.getAll().get(i);
            } else {
                List<SanPham> list = spDao.getSPByTen(timKiem);
                if (list.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Không tìm thấy sản phẩm có tên: " + timKiem);
                    return;
                }
                sp = list.get(i);
            }

            lblID.setText(sp.getIDSanPham());
            txtTensp.setText(sp.getTenSanPham());
            txtGiatien.setText(String.valueOf(sp.getGiaTien()));
            cboLoai.setSelectedItem(sp.getLoaiSanPham());
            strAnh = sp.getIMG();

            if (strAnh == null || strAnh.trim().isEmpty() || strAnh.equalsIgnoreCase("NO IMAGE")) {
                lblAnh.setText("Hình Ảnh Không tồn tại");
                lblAnh.setIcon(null);
            } else {
                try {
                    String duongDanAnhDayDu = "src/Images_SanPham/" + strAnh;
                    File file = new File(duongDanAnhDayDu);
                    if (!file.exists()) {
                        lblAnh.setText("Không tìm thấy ảnh");
                        lblAnh.setIcon(null);
                        return;
                    }
                    ImageIcon icon = new ImageIcon(duongDanAnhDayDu);
                    Image img = icon.getImage().getScaledInstance(lblAnh.getWidth(), lblAnh.getHeight(), Image.SCALE_SMOOTH);
                    lblAnh.setIcon(new ImageIcon(img));
                    lblAnh.setText("");
                } catch (Exception e) {
                    lblAnh.setText("Ảnh Bị Lỗi");
                    lblAnh.setIcon(null);
                }
            }
        }
    }

    public void timKiemTheoTen() {
        String ten = txtTimkiem.getText().trim();
        if (ten.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập tên sản phẩm để tìm kiếm!");
            return;
        }

        List<SanPham> list = spDao.getSPByTen(ten);
        if (list.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Không tìm thấy sản phẩm nào!");
        } else {
            loadTable(list);  // nạp bảng dữ liệu tìm được
            tblBang.setRowSelectionInterval(0, 0); // chọn dòng đầu tiên
            showdetail(); // hiển thị chi tiết
        }
    }

    public void loadTable(List<SanPham> list) {
        DefaultTableModel model = (DefaultTableModel) tblBang.getModel();
        model.setRowCount(0);
        for (SanPham sp : list) {
            model.addRow(new Object[]{
                sp.getIDSanPham(),
                sp.getTenSanPham(),
                sp.getGiaTien(),
                sp.getLoaiSanPham(),
                sp.getIMG(),
                sp.getTrangThai() == 1 ? "Đang hoạt động" : "Đã khóa"
            });
        }
    }

// Bỏ phương thức lọc theo trạng thái
    public void locTheoLoai() {
        String loai = cboLocSP.getSelectedItem().toString();
        tableModel.setRowCount(0); // Xóa dữ liệu cũ trong bảng

        List<SanPham> list;
        if (loai.equalsIgnoreCase("Tất cả")) {
            list = spDao.getAll(); // Gọi từ DAO
        } else {
            list = spDao.locTheoLoai(loai); // Gọi từ DAO
        }

        for (SanPham sp : list) {
            tableModel.addRow(spDao.getRow(sp)); // Thêm vào bảng
        }
    }

// Thêm phương thức khóa sản phẩm
    public void khoaSanPham() {
        int i = tblBang.getSelectedRow();
        if (i == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm cần khóa!");
            return;
        }

        SanPham sp = spDao.getAll().get(i);
        if (sp.getTrangThai() == 0) {
            JOptionPane.showMessageDialog(this, "Sản phẩm đã bị khóa!");
            return;
        }

        int chon = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn khóa sản phẩm này?",
                "Xác Nhận Khóa", JOptionPane.YES_NO_OPTION);
        if (chon == JOptionPane.YES_OPTION) {
            boolean result = spDao.khoaSanPham(sp.getIDSanPham());
            if (result) {
                JOptionPane.showMessageDialog(this, "Khóa sản phẩm thành công!");
                fillTable();
            } else {
                JOptionPane.showMessageDialog(this, "Khóa sản phẩm thất bại!");
            }
        }
    }

    public void moKhoaSanPham() {
        int i = tblBang.getSelectedRow();
        if (i == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm cần mở khóa!");
            return;
        }

        String timKiem = txtTimkiem.getText().trim();
        SanPham sp;

        if (timKiem.isEmpty()) {
            sp = spDao.getAll().get(i);
        } else {
            List<SanPham> list = spDao.getSPByTen(timKiem);
            if (list.isEmpty() || i >= list.size()) {
                JOptionPane.showMessageDialog(this, "Không thể lấy thông tin sản phẩm!");
                return;
            }
            sp = list.get(i);
        }

        if (sp.getTrangThai() == 1) {
            JOptionPane.showMessageDialog(this, "Sản phẩm đang hoạt động!");
            return;
        }

        int chon = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn mở khóa sản phẩm này?",
                "Xác Nhận Mở Khóa", JOptionPane.YES_NO_OPTION);
        if (chon == JOptionPane.YES_OPTION) {
            boolean result = spDao.moKhoaSanPham(sp.getIDSanPham());
            if (result) {
                JOptionPane.showMessageDialog(this, "Mở khóa sản phẩm thành công!");
                fillTable();
            } else {
                JOptionPane.showMessageDialog(this, "Mở khóa sản phẩm thất bại!");
            }
        }
    }

// Thêm phương thức kiểm tra trạng thái khóa
    private boolean kiemTraSanPhamBiKhoa() {
        int i = tblBang.getSelectedRow();
        if (i == -1) {
            return false; // Không có sản phẩm nào được chọn
        }

        String timKiem = txtTimkiem.getText().trim();
        SanPham sp;

        if (timKiem.isEmpty()) {
            // Không tìm kiếm, dùng getAll()
            sp = spDao.getAll().get(i);
        } else {
            // Đang tìm kiếm theo tên
            List<SanPham> list = spDao.getSPByTen(timKiem);
            if (list.isEmpty() || i >= list.size()) {
                return false;
            }
            sp = list.get(i);
        }

        if (sp.getTrangThai() == 0) {
            JOptionPane.showMessageDialog(this, "Sản phẩm này đã bị khóa! Không thể thực hiện thao tác.");
            return true;
        }
        return false;
    }

    private boolean validateThemSanPham() {
        String id = lblID.getText().trim();
        String ten = txtTensp.getText().trim();
        String giaStr = txtGiatien.getText().trim().replace(",", "").replace(".", "");
        String duongDanAnh = lblAnh.getToolTipText();
        String loai = cboLoai.getSelectedItem().toString();

        // Check mã sản phẩm
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhấn nút 'TẠO MÃ' trước!");
            return false;
        }

        // Check tên sản phẩm
        if (ten.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tên sản phẩm không được để trống!");
            return false;
        }
        if (ten.matches(".*\\d.*")) {
            JOptionPane.showMessageDialog(this, "Tên sản phẩm không được chứa số!");
            return false;
        }
        if (ten.matches(".*[^a-zA-ZÀ-ỹ\\s].*")) {
            JOptionPane.showMessageDialog(this, "Tên sản phẩm không được chứa ký tự đặc biệt!");
            return false;
        }

        // Check tên đã tồn tại
        List<SanPham> danhSach = new SanPhamDAO().getAll();
        for (SanPham sp : danhSach) {
            if (sp.getTenSanPham().equalsIgnoreCase(ten)) {
                JOptionPane.showMessageDialog(this, "Tên sản phẩm đã tồn tại. Vui lòng nhập tên khác!");
                return false;
            }
        }

// Check giá tiền rỗng
        if (giaStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Giá tiền không được để trống!");
            return false;
        }

        // Check giá tiền chỉ chứa khoảng trắng
        if (giaStr.matches("\\s*")) {
            JOptionPane.showMessageDialog(this, "Giá tiền không được chứa khoảng trắng!");
            return false;
        }

        // Check giá tiền có chứa chữ cái
        if (giaStr.matches(".*[a-zA-ZÀ-ỹ].*")) {
            JOptionPane.showMessageDialog(this, "Giá tiền không được chứa chữ cái!");
            return false;
        }

        // Check giá tiền có chứa ký tự đặc biệt (trừ dấu + và -)
        if (giaStr.matches(".*[^0-9+\\-].*")) {
            JOptionPane.showMessageDialog(this, "Giá tiền chỉ được chứa số!");
            return false;
        }

        // Check giá tiền có dấu âm
        if (giaStr.startsWith("-") || giaStr.contains("-")) {
            JOptionPane.showMessageDialog(this, "Giá tiền không được là số âm!");
            return false;
        }

        // Check giá tiền bắt đầu bằng dấu +
        if (giaStr.startsWith("+")) {
            giaStr = giaStr.substring(1); // Bỏ dấu + ở đầu
        }

        // Check giá tiền phải là số nguyên dương
        if (!giaStr.matches("^[1-9]\\d*$")) {
            if (giaStr.matches("^0+$")) {
                JOptionPane.showMessageDialog(this, "Giá tiền phải là số nguyên dương (không được là 0)!");
            } else if (giaStr.matches("^0\\d+$")) {
                JOptionPane.showMessageDialog(this, "Giá tiền không được có số 0 ở đầu.");
            } else {
                JOptionPane.showMessageDialog(this, "Giá tiền phải là số nguyên dương hợp lệ!");
            }
            return false;
        }

        try {
            long giaLong = Long.parseLong(giaStr);

            // Check giá tiền vượt quá giới hạn int
            if (giaLong > Integer.MAX_VALUE) {
                JOptionPane.showMessageDialog(this, "Giá tiền quá lớn. Vui lòng nhập giá trị nhỏ hơn!");
                return false;
            }

            int gia = (int) giaLong;

            if (gia < 10000) {
                JOptionPane.showMessageDialog(this, "Giá tiền phải từ 10.000 VND trở lên!");
                return false;
            }

            if (gia > 500000) {
                JOptionPane.showMessageDialog(this, "Giá tiền phải nhỏ hơn hoặc bằng 500.000 VND!");
                return false;
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Giá tiền không hợp lệ!");
            return false;
        }

        return true;
    }

    private boolean validateSuaSanPham() {
        String id = lblID.getText().trim();
        String ten = txtTensp.getText().trim();
        String giaStr = txtGiatien.getText().trim().replace(",", "").replace(".", "");

        float tong = 0;
        List<ChiTietHoaDon> list = hdd.getAllCTHD();
        hdd.UpdateGia(id);
        for (ChiTietHoaDon ct : list) {
            tong += ct.getGiaSP() * ct.getSoLuong();
        }
        hdd.updateTongTien(id, tong);
        
        // Check mã sản phẩm
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm cần sửa!");
            return false;
        }

        // Check tên sản phẩm không được để trống
        if (ten.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tên sản phẩm không được để trống!");
            return false;
        }

        // Check tên sản phẩm không được chứa số
        if (ten.matches(".*\\d.*")) {
            JOptionPane.showMessageDialog(this, "Tên sản phẩm không được chứa số!");
            return false;
        }

        // Check tên sản phẩm không được chứa ký tự đặc biệt
        if (ten.matches(".*[^a-zA-ZÀ-ỹ\\s].*")) {
            JOptionPane.showMessageDialog(this, "Tên sản phẩm không được chứa ký tự đặc biệt!");
            return false;
        }

        // Check tên đã tồn tại (chỉ check với sản phẩm khác, không check với chính nó)
        List<SanPham> danhSach = new SanPhamDAO().getAll();
        for (SanPham sp : danhSach) {
            if (sp.getTenSanPham().equalsIgnoreCase(ten) && !sp.getIDSanPham().equals(id)) {
                JOptionPane.showMessageDialog(this, "Tên sản phẩm đã tồn tại. Vui lòng nhập tên khác!");
                return false;
            }
        }

        // Check giá tiền rỗng
        if (giaStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Giá tiền không được để trống!");
            return false;
        }

        // Check giá tiền chỉ chứa khoảng trắng
        if (giaStr.matches("\\s*")) {
            JOptionPane.showMessageDialog(this, "Giá tiền không được chứa khoảng trắng!");
            return false;
        }

        // Check giá tiền có chứa chữ cái
        if (giaStr.matches(".*[a-zA-ZÀ-ỹ].*")) {
            JOptionPane.showMessageDialog(this, "Giá tiền không được chứa chữ cái!");
            return false;
        }

        // Check giá tiền có chứa ký tự đặc biệt (trừ dấu + và -)
        if (giaStr.matches(".*[^0-9+\\-].*")) {
            JOptionPane.showMessageDialog(this, "Giá tiền chỉ được chứa số!");
            return false;
        }

        // Check giá tiền có dấu âm
        if (giaStr.startsWith("-") || giaStr.contains("-")) {
            JOptionPane.showMessageDialog(this, "Giá tiền không được là số âm!");
            return false;
        }

        // Check giá tiền bắt đầu bằng dấu +
        if (giaStr.startsWith("+")) {
            giaStr = giaStr.substring(1); // Bỏ dấu + ở đầu
        }

        // Check giá tiền phải là số nguyên dương
        if (!giaStr.matches("^[1-9]\\d*$")) {
            if (giaStr.matches("^0+$")) {
                JOptionPane.showMessageDialog(this, "Giá tiền phải là số nguyên dương (không được là 0)!");
            } else if (giaStr.matches("^0\\d+$")) {
                JOptionPane.showMessageDialog(this, "Giá tiền không được có số 0 ở đầu!");
            } else {
                JOptionPane.showMessageDialog(this, "Giá tiền phải là số nguyên dương hợp lệ!");
            }
            return false;
        }

        try {
            long giaLong = Long.parseLong(giaStr);

            // Check giá tiền vượt quá giới hạn int
            if (giaLong > Integer.MAX_VALUE) {
                JOptionPane.showMessageDialog(this, "Giá tiền quá lớn. Vui lòng nhập giá trị nhỏ hơn!");
                return false;
            }

            int gia = (int) giaLong;

            if (gia < 10000) {
                JOptionPane.showMessageDialog(this, "Giá tiền phải từ 10.000 VND trở lên!");
                return false;
            }

            if (gia > 500000) {
                JOptionPane.showMessageDialog(this, "Giá tiền phải nhỏ hơn hoặc bằng 500.000 VND!");
                return false;
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Giá tiền không hợp lệ!");
            return false;
        }

        return true;
    }

    private void taoMaSanPham() {
        String maTuDong = spDao.layMaTuDong();
        lblID.setText(maTuDong);
        JOptionPane.showMessageDialog(this, "Đã tạo mã sản phẩm: " + maTuDong);
    }

    private void themSanPham() {
        if (kiemTraSanPhamBiKhoa()) {
            return;
        }
        if (!validateThemSanPham()) {
            return;
        }

        String maSP = lblID.getText().trim();
        String tenSP = txtTensp.getText().trim();
        float giaTien = Float.parseFloat(txtGiatien.getText().trim());
        String loai = cboLoai.getSelectedItem().toString();

        // Sử dụng constructor mặc định và set từng thuộc tính
        SanPham sp = new SanPham();
        sp.setIDSanPham(maSP);
        sp.setTenSanPham(tenSP);
        sp.setGiaTien(giaTien);
        sp.setLoaiSanPham(loai);
        sp.setIMG(strAnh);
        sp.setTrangThai(1); // Mặc định là đang hoạt động

        spDao.them(sp);
        JOptionPane.showMessageDialog(this, "Thêm sản phẩm thành công!");
        fillTable();
        lamMoiForm();
    }

    private void suaSanPham() {
        // Kiểm tra xem có chọn sản phẩm không
        int i = tblBang.getSelectedRow();
        if (i == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm cần sửa!");
            return;
        }

        // Kiểm tra xem sản phẩm có bị khóa không
        if (kiemTraSanPhamBiKhoa()) {
            return;
        }

        // Validate dữ liệu nhập
        if (!validateSuaSanPham()) {
            return;
        }

        // Lấy sản phẩm gốc để so sánh
        String timKiem = txtTimkiem.getText().trim();
        SanPham spGoc;
        if (timKiem.isEmpty()) {
            spGoc = spDao.getAll().get(i);
        } else {
            List<SanPham> list = spDao.getSPByTen(timKiem);
            spGoc = list.get(i);
        }

        // Kiểm tra xem có thay đổi gì không
        String tenMoi = txtTensp.getText().trim();
        String giaStrMoi = txtGiatien.getText().trim().replace(",", "").replace(".", "");
        if (giaStrMoi.startsWith("+")) {
            giaStrMoi = giaStrMoi.substring(1);
        }
        float giaMoi = Float.parseFloat(giaStrMoi);
        String loaiMoi = cboLoai.getSelectedItem().toString();
        String anhMoi = strAnh;

        // So sánh với dữ liệu gốc
        boolean coThayDoi = false;
        if (!spGoc.getTenSanPham().equals(tenMoi)) {
            coThayDoi = true;
        }
        if (spGoc.getGiaTien() != giaMoi) {
            coThayDoi = true;
        }
        if (!spGoc.getLoaiSanPham().equals(loaiMoi)) {
            coThayDoi = true;
        }
        if (!spGoc.getIMG().equals(anhMoi)) {
            coThayDoi = true;
        }

        // Nếu không có thay đổi gì
        if (!coThayDoi) {
            JOptionPane.showMessageDialog(this, "Không có thay đổi nào để lưu!");
            return;
        }

        int chon = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn lưu các thay đổi này?",
                "Xác Nhận", JOptionPane.YES_NO_OPTION);
        if (chon == JOptionPane.YES_OPTION) {
            try {
                // Lấy dữ liệu từ form
                String ID = lblID.getText().trim();
                String ten = txtTensp.getText().trim();

                // Xử lý giá tiền giống như trong validation
                String giaStr = txtGiatien.getText().trim().replace(",", "").replace(".", "");
                if (giaStr.startsWith("+")) {
                    giaStr = giaStr.substring(1);
                }

                float gia = Float.parseFloat(giaStr);
                String loaiSanPham = cboLoai.getSelectedItem().toString();
                String anh = strAnh;

                // Tạo đối tượng sản phẩm mới
                SanPham sp = new SanPham();
                sp.setIDSanPham(ID);
                sp.setTenSanPham(ten);
                sp.setGiaTien(gia);
                sp.setLoaiSanPham(loaiSanPham);
                sp.setIMG(anh);
                sp.setTrangThai(spGoc.getTrangThai()); // Giữ nguyên trạng thái cũ

                // Gọi DAO để update
                int result = spDao.suaSanPham(sp, ID);
                if (result == 1) {
                    JOptionPane.showMessageDialog(this, "Sửa sản phẩm thành công!");
                    fillTable(); // Load lại bảng
                    lamMoiForm(); // Reset form sau khi sửa thành công
                } else {
                    JOptionPane.showMessageDialog(this, "Sửa sản phẩm thất bại!");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Lỗi định dạng số: " + e.getMessage());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Lỗi khi sửa sản phẩm: " + e.getMessage());
            }
        }

    }

    private void lamMoiForm() {
        lblID.setText("");
        txtTensp.setText("");
        txtGiatien.setText("");
        cboLoai.setSelectedIndex(0);
        strAnh = "";
        lblAnh.setText("ẢNH SẢN PHẨM");
        lblAnh.setIcon(null);
        fillTable();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtTensp = new javax.swing.JTextField();
        txtGiatien = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        lblAnh = new javax.swing.JLabel();
        btnThemSP = new javax.swing.JButton();
        khoaSP = new javax.swing.JButton();
        btnSuaSP = new javax.swing.JButton();
        btnLamMoiSP = new javax.swing.JButton();
        cboLoai = new javax.swing.JComboBox<>();
        lblID = new javax.swing.JLabel();
        btnTaoMa = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        txtTimkiem = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblBang = new javax.swing.JTable();
        jButton4 = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        cboLocSP = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(234, 232, 232));

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel3.setFont(new java.awt.Font("Segoe UI Light", 1, 14)); // NOI18N
        jLabel3.setText("ID SẢN PHẨM");

        jLabel4.setFont(new java.awt.Font("Segoe UI Light", 1, 14)); // NOI18N
        jLabel4.setText("TÊN SẢN PHẨM");

        jLabel5.setFont(new java.awt.Font("Segoe UI Light", 1, 14)); // NOI18N
        jLabel5.setText("GIÁ TIỀN");

        jLabel6.setFont(new java.awt.Font("Segoe UI Light", 1, 14)); // NOI18N
        jLabel6.setText("LOẠI SẢN PHẨM");

        txtTensp.setFont(new java.awt.Font("Segoe UI Light", 1, 12)); // NOI18N

        txtGiatien.setFont(new java.awt.Font("Segoe UI Light", 1, 12)); // NOI18N
        txtGiatien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtGiatienActionPerformed(evt);
            }
        });

        jPanel4.setBackground(new java.awt.Color(133, 151, 186));

        lblAnh.setText("ẢNH SẢN PHẨM");
        lblAnh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblAnhMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblAnh, javax.swing.GroupLayout.DEFAULT_SIZE, 233, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblAnh, javax.swing.GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE)
                .addContainerGap())
        );

        btnThemSP.setBackground(new java.awt.Color(31, 51, 86));
        btnThemSP.setFont(new java.awt.Font("Segoe UI Light", 1, 14)); // NOI18N
        btnThemSP.setForeground(new java.awt.Color(255, 255, 255));
        btnThemSP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MainForm_Admin/image/Them.png"))); // NOI18N
        btnThemSP.setText("THÊM SẢN PHẨM");
        btnThemSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemSPActionPerformed(evt);
            }
        });

        khoaSP.setBackground(new java.awt.Color(31, 51, 86));
        khoaSP.setFont(new java.awt.Font("Segoe UI Light", 1, 14)); // NOI18N
        khoaSP.setForeground(new java.awt.Color(255, 255, 255));
        khoaSP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MainForm_Admin/image/lock.png"))); // NOI18N
        khoaSP.setText("KHÓA SẢN PHẨM");
        khoaSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                khoaSPActionPerformed(evt);
            }
        });

        btnSuaSP.setBackground(new java.awt.Color(31, 51, 86));
        btnSuaSP.setFont(new java.awt.Font("Segoe UI Light", 1, 14)); // NOI18N
        btnSuaSP.setForeground(new java.awt.Color(255, 255, 255));
        btnSuaSP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MainForm_Admin/image/Sua.png"))); // NOI18N
        btnSuaSP.setText("SỬA SẢN PHẨM");
        btnSuaSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaSPActionPerformed(evt);
            }
        });

        btnLamMoiSP.setBackground(new java.awt.Color(31, 51, 86));
        btnLamMoiSP.setFont(new java.awt.Font("Segoe UI Light", 1, 14)); // NOI18N
        btnLamMoiSP.setForeground(new java.awt.Color(255, 255, 255));
        btnLamMoiSP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MainForm_Admin/image/lamMoi.png"))); // NOI18N
        btnLamMoiSP.setText("LÀM MỚI ");
        btnLamMoiSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiSPActionPerformed(evt);
            }
        });

        cboLoai.setFont(new java.awt.Font("Segoe UI Light", 1, 12)); // NOI18N
        cboLoai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "CAFE", "BÁNH NGỌT", "NƯỚC ÉP" }));

        lblID.setFont(new java.awt.Font("Segoe UI Light", 1, 36)); // NOI18N
        lblID.setForeground(new java.awt.Color(102, 0, 51));

        btnTaoMa.setBackground(new java.awt.Color(31, 51, 86));
        btnTaoMa.setFont(new java.awt.Font("Segoe UI Light", 1, 15)); // NOI18N
        btnTaoMa.setForeground(new java.awt.Color(255, 255, 255));
        btnTaoMa.setText("TẠO MÃ");
        btnTaoMa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTaoMaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(btnThemSP, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnSuaSP, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(khoaSP, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnLamMoiSP, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))
                        .addGap(40, 40, 40)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtTensp)
                                .addComponent(txtGiatien)
                                .addComponent(cboLoai, 0, 447, Short.MAX_VALUE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(lblID, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnTaoMa, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(30, 30, 30)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(36, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addComponent(btnTaoMa))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(lblID, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3))))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTensp, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtGiatien, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(cboLoai, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThemSP)
                    .addComponent(btnSuaSP)
                    .addComponent(khoaSP)
                    .addComponent(btnLamMoiSP))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        txtTimkiem.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Segoe UI Light", 1, 16)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(31, 51, 86));
        jLabel7.setText("TÌM KIẾM SẢN PHẨM");

        jButton3.setBackground(new java.awt.Color(31, 51, 86));
        jButton3.setFont(new java.awt.Font("Segoe UI Light", 1, 15)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("TÌM KIẾM");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        tblBang.setModel(new javax.swing.table.DefaultTableModel(
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
        tblBang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblBangMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblBang);

        jButton4.setBackground(new java.awt.Color(31, 51, 86));
        jButton4.setFont(new java.awt.Font("Segoe UI Light", 1, 15)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("LỌC");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Segoe UI Light", 1, 16)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(31, 51, 86));
        jLabel8.setText("LỌC SẢN PHẨM");

        cboLocSP.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        cboLocSP.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "TẤT CẢ", "CAFE", "BÁNH NGỌT", "NƯỚC ÉP" }));

        jButton1.setBackground(new java.awt.Color(31, 51, 86));
        jButton1.setFont(new java.awt.Font("Segoe UI Light", 1, 13)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("MỞ KHÓA");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtTimkiem, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(75, 75, 75)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(cboLocSP, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jButton1)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 893, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTimkiem, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboLocSP, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 343, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tblBangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblBangMouseClicked
        // TODO add your handling code here:
        showdetail();
    }//GEN-LAST:event_tblBangMouseClicked

    private void lblAnhMouseClicked(java.awt.event.MouseEvent evt) {
        // TODO add your handling code here:
        JFileChooser jFC = new JFileChooser("src\\Images");
        jFC.showOpenDialog(null);
        File file = jFC.getSelectedFile();
        lblAnh.setText("");
        try {
            Image img = ImageIO.read(file);
            strAnh = file.getName();
            int width = lblAnh.getWidth();
            int height = lblAnh.getHeight();
            lblAnh.setIcon(new ImageIcon(img.getScaledInstance(width, height, 0)));
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "ĐÃ XẢY RA LỖI!");
        }

    }

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        timKiemTheoTen();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        locTheoLoai();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void btnThemSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemSPActionPerformed
        // TODO add your handling code here:
        themSanPham();
    }//GEN-LAST:event_btnThemSPActionPerformed

    private void btnTaoMaSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTaoMaSPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnTaoMaSPActionPerformed

    private void btnLamMoiSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiSPActionPerformed
        // TODO add your handling code here:
        lamMoiForm();
    }//GEN-LAST:event_btnLamMoiSPActionPerformed

    private void btnTaoMaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTaoMaActionPerformed
        // TODO add your handling code here:
        taoMaSanPham();

    }//GEN-LAST:event_btnTaoMaActionPerformed

    private void btnSuaSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaSPActionPerformed
        // TODO add your handling code here:
        suaSanPham();
    }//GEN-LAST:event_btnSuaSPActionPerformed

    private void txtGiatienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtGiatienActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtGiatienActionPerformed

    private void khoaSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_khoaSPActionPerformed
        // TODO add your handling code here:
        khoaSanPham();
    }//GEN-LAST:event_khoaSPActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        moKhoaSanPham();
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(QuanLySanPham.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(QuanLySanPham.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(QuanLySanPham.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(QuanLySanPham.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new QuanLySanPham().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLamMoiSP;
    private javax.swing.JButton btnSuaSP;
    private javax.swing.JButton btnTaoMa;
    private javax.swing.JButton btnThemSP;
    private javax.swing.JComboBox<String> cboLoai;
    private javax.swing.JComboBox<String> cboLocSP;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton khoaSP;
    private javax.swing.JLabel lblAnh;
    private javax.swing.JLabel lblID;
    private javax.swing.JTable tblBang;
    private javax.swing.JTextField txtGiatien;
    private javax.swing.JTextField txtTensp;
    private javax.swing.JTextField txtTimkiem;
    // End of variables declaration//GEN-END:variables
}
