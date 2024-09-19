package com.example.doan
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "qlbanhang"

        // Bảng BoPhan
        private const val TABLE_BOPHAN = "BoPhan"
        const val COLUMN_MA_BP = "MaBP"
        private const val COLUMN_TEN_BP = "TenBP"

        // Bảng NhanVien
        const val TABLE_NHANVIEN = "NhanVien"
        const val COLUMN_MA_NV = "MaNV"
        const val COLUMN_HO_TEN_NV = "HoTenNV"
        const val COLUMN_GIOI_TINH_NV = "GioiTinhNV"
        const val COLUMN_DIA_CHI_NV = "DiaChiNV"
        const val COLUMN_NGAY_SINH_NV = "NgaySinhNV"
        const val COLUMN_SDT_NV = "SDTNV"
        const val COLUMN_MA_BP_FKnv = "MaBP"
        const val COLUMN_MA_CH_FKnv = "MaCH"

        // Bảng KhachHang
        const val TABLE_KHACHHANG = "KhachHang"
        const val COLUMN_MA_KH = "MaKH"
        const val COLUMN_HO_TEN_KH = "HoTenKH"
        const val COLUMN_GIOI_TINH_KH = "GioiTinhKH"
        const val COLUMN_DIA_CHI_KH = "DiaChiKH"
        const val COLUMN_NGAY_SINH_KH = "NgaySinhKH"
        const val COLUMN_SDT_KH = "SDTKH"


        // Bảng CuaHang
        private const val TABLE_CUAHANG = "CuaHang"
        const val COLUMN_MA_CH = "MaCH"
        private const val COLUMN_TEN_CH = "TenCH"
        private const val COLUMN_DIA_CHI_CH = "DiaChiCH"
        private const val COLUMN_HOTLINE = "Hotline"


        // Bảng LoaiSP
        const val TABLE_LOAISP = "LoaiSP"
        const val COLUMN_MA_LOAI_SP = "MaLoaiSP"
        private const val COLUMN_TEN_LOAI_SP = "TenLoaiSP"
        private const val COLUMN_MO_TA_LOAI = "MoTaLoai"


        // Bảng SanPham
        const val TABLE_SANPHAM = "SanPham"
        const val COLUMN_MA_SP = "MaSP"
        const val COLUMN_TEN_SP = "TenSP"
        const val COLUMN_GIA_BAN = "GiaBan"
        const val COLUMN_DVT = "DVT"
        const val COLUMN_HINH_ANH = "HinhAnh"
        const val COLUMN_MO_TA_SP = "MoTaSP"
        private const val COLUMN_MA_LOAI_SP_FK = "MaLoaiSP"


        // Bảng HoaDon
        const val TABLE_HOADON = "HoaDon"
        const val COLUMN_MA_HD_FKhd = "MaHD"
        const val COLUMN_MA_KH_FKhd = "MaKH"
        const val COLUMN_MA_NV_FKhd = "MaNV"
        const val COLUMN_NGAY_XUAT_HD = "NgayXuatHD"
        const val COLUMN_PT_TTHD = "PTTT"


        // Bảng CTHoaDon
        const val TABLE_CTHOADON = "CTHoaDon"
        const val COLUMN_MA_HD = "MaHD"
        const val COLUMN_MA_SP_FKcthd = "MaSP"
        const val COLUMN_SO_LUONG_MUA = "SoLuongMua"
        const val COLUMN_DON_GIA_BAN = "DonGiaBan"


        // Bảng DonDatHang
        private const val TABLE_DONDATHANG = "DonDatHang"
        private const val COLUMN_MA_DDH = "MaDDH"
        private const val COLUMN_MA_KH_FKddh = "MaKH"
        private const val COLUMN_MA_CH_FKddh = "MaCH"
        private const val COLUMN_NGAY_DAT_HANG = "NgayDatHang"
        private const val COLUMN_NGAY_DK_NHAN_HANG = "NgayDKNhanHang"


        // Bảng CTDDH
        private const val TABLE_CTDDH = "CTDDH"
        private const val COLUMN_MA_DDH_FKctddh = "MaDDH"
        private const val COLUMN_MA_SP_FKctddh = "MaSP"
        private const val COLUMN_SL_DAT = "SLDat"
        private const val COLUMN_TINH_TRANG = "TinhTrang"
        private const val COLUMN_PT_TT = "PTTT"


        // Bảng TonKho
        private const val TABLE_TONKHO = "TonKho"
        private const val COLUMN_MA_CH_FKtk = "MaCH"
        private const val COLUMN_MA_SP_FKtk = "MaSP"
        private const val COLUMN_SL_TON = "SLTon"
    }
    override fun onCreate(db: SQLiteDatabase) {
        val createBoPhanTable = """
            CREATE TABLE $TABLE_BOPHAN (
                $COLUMN_MA_BP TEXT PRIMARY KEY,
                $COLUMN_TEN_BP TEXT
            )
        """
        db.execSQL(createBoPhanTable)

        val createNhanVienTable = """
            CREATE TABLE $TABLE_NHANVIEN (
                $COLUMN_MA_NV TEXT PRIMARY KEY,
                $COLUMN_HO_TEN_NV TEXT,
                $COLUMN_GIOI_TINH_NV TEXT,
                $COLUMN_DIA_CHI_NV TEXT,
                $COLUMN_NGAY_SINH_NV DATE,
                $COLUMN_SDT_NV TEXT,
                $COLUMN_MA_BP_FKnv TEXT,
                $COLUMN_MA_CH_FKnv TEXT
            )
        """
        db.execSQL(createNhanVienTable)

        val createKhachHangTable = """
            CREATE TABLE $TABLE_KHACHHANG (
                $COLUMN_MA_KH TEXT PRIMARY KEY,
                $COLUMN_HO_TEN_KH TEXT,
                $COLUMN_GIOI_TINH_KH TEXT,
                $COLUMN_DIA_CHI_KH TEXT,
                $COLUMN_NGAY_SINH_KH TEXT,
                $COLUMN_SDT_KH TEXT
            )
        """
        db.execSQL(createKhachHangTable)

        val createCuaHangTable = """
            CREATE TABLE $TABLE_CUAHANG (
                $COLUMN_MA_CH TEXT PRIMARY KEY,
                $COLUMN_TEN_CH TEXT,
                $COLUMN_DIA_CHI_CH TEXT,
                $COLUMN_HOTLINE TEXT
            )
        """
        db.execSQL(createCuaHangTable)

        val createLoaiSPTable = """
            CREATE TABLE $TABLE_LOAISP (
                $COLUMN_MA_LOAI_SP TEXT PRIMARY KEY,
                $COLUMN_TEN_LOAI_SP TEXT,
                $COLUMN_MO_TA_LOAI TEXT
            )
        """
        db.execSQL(createLoaiSPTable)

        val createSanPhamTable = """
            CREATE TABLE $TABLE_SANPHAM (
                $COLUMN_MA_SP TEXT PRIMARY KEY,
                $COLUMN_TEN_SP TEXT,
                $COLUMN_GIA_BAN REAL,
                $COLUMN_DVT TEXT,
                $COLUMN_HINH_ANH TEXT,
                $COLUMN_MO_TA_SP TEXT,
                $COLUMN_MA_LOAI_SP_FK TEXT
            )
        """
        db.execSQL(createSanPhamTable)


        val createHoaDonTable = """
            CREATE TABLE $TABLE_HOADON (
                $COLUMN_MA_HD_FKhd TEXT PRIMARY KEY,
                $COLUMN_MA_KH_FKhd TEXT,
                $COLUMN_MA_NV_FKhd TEXT,
                $COLUMN_NGAY_XUAT_HD DATE,
                $COLUMN_PT_TTHD TEXT
            )
        """
        db.execSQL(createHoaDonTable)

        val createCTHoaDonTable = """
            CREATE TABLE $TABLE_CTHOADON (
                $COLUMN_MA_HD TEXT,
                $COLUMN_MA_SP_FKcthd TEXT,
                $COLUMN_SO_LUONG_MUA INTEGER,
                $COLUMN_DON_GIA_BAN REAL,
                PRIMARY KEY ($COLUMN_MA_HD, $COLUMN_MA_SP_FKcthd)
            )
        """
        db.execSQL(createCTHoaDonTable)

        val createDonDatHangTable = """
            CREATE TABLE $TABLE_DONDATHANG (
                $COLUMN_MA_DDH TEXT PRIMARY KEY,
                $COLUMN_MA_KH_FKddh TEXT,
                $COLUMN_MA_CH_FKddh TEXT,
                $COLUMN_NGAY_DAT_HANG DATE,
                $COLUMN_NGAY_DK_NHAN_HANG DATE
            )
        """
        db.execSQL(createDonDatHangTable)


        val createCTDDHTable = """
            CREATE TABLE $TABLE_CTDDH (
                $COLUMN_MA_DDH_FKctddh TEXT,
                $COLUMN_MA_SP_FKctddh TEXT,
                $COLUMN_SL_DAT INTEGER,
                $COLUMN_TINH_TRANG TEXT,
                $COLUMN_PT_TT TEXT,
                PRIMARY KEY ($COLUMN_MA_DDH_FKctddh, $COLUMN_MA_SP_FKctddh)
            )
        """
        db.execSQL(createCTDDHTable)


        val createTonKhoTable = """
            CREATE TABLE $TABLE_TONKHO (
                $COLUMN_MA_CH_FKtk TEXT,
                $COLUMN_MA_SP_FKtk TEXT,
                $COLUMN_SL_TON INTEGER,
                PRIMARY KEY ($COLUMN_MA_CH_FKtk, $COLUMN_MA_SP_FKtk)
            )
        """
        db.execSQL(createTonKhoTable)

        // Chèn dữ liệu mẫu vào các bảng
        val insertBoPhanData = """
            INSERT INTO $TABLE_BOPHAN ($COLUMN_MA_BP, $COLUMN_TEN_BP) VALUES
            ('BP001', 'Bộ phận Kinh Doanh'),
            ('BP002', 'Bộ phận Kỹ Thuật'),
            ('BP003', 'Bộ phận Nhân Sự'),
            ('BP004', 'Bộ phận Marketing'),
            ('BP005', 'Bộ phận Kế Toán')
        """
        db.execSQL(insertBoPhanData)
// Chèn dữ liệu vào bảng KhachHang
        val insertKhachHangData = """
            INSERT INTO $TABLE_KHACHHANG ($COLUMN_MA_KH, $COLUMN_HO_TEN_KH, $COLUMN_GIOI_TINH_KH, $COLUMN_DIA_CHI_KH, $COLUMN_NGAY_SINH_KH, $COLUMN_SDT_KH) VALUES
            ('KH001', 'Nguyễn Văn A', 'Nam', 'Hà Nội', '1990-01-01', '0912345678'),
            ('KH002', 'Trần Thị B', 'Nữ', 'Hà Nội', '1992-02-02', '0987654321'),
            ('KH003', 'Lê Văn C', 'Nam', 'Hà Nội', '1994-03-03', '0911122233'),
            ('KH004', 'Phạm Thị D', 'Nữ', 'Hà Nội', '1996-04-04', '0944556677'),
            ('KH005', 'Hoàng Văn E', 'Nam', 'Hà Nội', '1998-05-05', '0933445566')
        """
        db.execSQL(insertKhachHangData)

// Chèn dữ liệu vào bảng NhanVien
        val insertNhanVienData = """
            INSERT INTO $TABLE_NHANVIEN ($COLUMN_MA_NV, $COLUMN_HO_TEN_NV, $COLUMN_GIOI_TINH_NV, $COLUMN_DIA_CHI_NV, $COLUMN_NGAY_SINH_NV, $COLUMN_SDT_NV, $COLUMN_MA_BP, $COLUMN_MA_CH) VALUES
            ('NV001', 'Nguyễn Văn A', 'Nam', 'Hà Nội', '1990-01-01', '0912345678', 'BP001', 'CH001'),
            ('NV002', 'Trần Thị B', 'Nữ', 'Hà Nội', '1992-02-02', '0987654321', 'BP002', 'CH002'),
            ('NV003', 'Lê Văn C', 'Nam', 'Hà Nội', '1994-03-03', '0911122233', 'BP003', 'CH003'),
            ('NV004', 'Phạm Thị D', 'Nữ', 'Hà Nội', '1996-04-04', '0944556677', 'BP004', 'CH004'),
            ('NV005', 'Hoàng Văn E', 'Nam', 'Hà Nội', '1998-05-05', '0933445566', 'BP005', 'CH005')
        """
        db.execSQL(insertNhanVienData)

// Chèn dữ liệu vào bảng CuaHang
        val insertCuaHangData = """
            INSERT INTO $TABLE_CUAHANG ($COLUMN_MA_CH, $COLUMN_TEN_CH, $COLUMN_DIA_CHI_CH, $COLUMN_HOTLINE) VALUES
            ('CH001', 'Cửa hàng A', 'Hà Nội', '0123456789'),
            ('CH002', 'Cửa hàng B', 'Hà Nội', '0234567890'),
            ('CH003', 'Cửa hàng C', 'Hà Nội', '0345678901'),
            ('CH004', 'Cửa hàng D', 'Hà Nội', '0456789012'),
            ('CH005', 'Cửa hàng E', 'Hà Nội', '0567890123')
        """
        db.execSQL(insertCuaHangData)

// Chèn dữ liệu vào bảng LoaiSP
        val insertLoaiSPData = """
            INSERT INTO $TABLE_LOAISP ($COLUMN_MA_LOAI_SP, $COLUMN_TEN_LOAI_SP, $COLUMN_MO_TA_LOAI) VALUES
            ('LSP001', 'Loại Sản Phẩm 1', 'Mô tả loại sản phẩm 1'),
            ('LSP002', 'Loại Sản Phẩm 2', 'Mô tả loại sản phẩm 2'),
            ('LSP003', 'Loại Sản Phẩm 3', 'Mô tả loại sản phẩm 3'),
            ('LSP004', 'Loại Sản Phẩm 4', 'Mô tả loại sản phẩm 4'),
            ('LSP005', 'Loại Sản Phẩm 5', 'Mô tả loại sản phẩm 5')
        """
        db.execSQL(insertLoaiSPData)

// Chèn dữ liệu vào bảng SanPham
        val insertSanPhamData = """
            INSERT INTO $TABLE_SANPHAM ($COLUMN_MA_SP, $COLUMN_TEN_SP, $COLUMN_GIA_BAN, $COLUMN_DVT, $COLUMN_HINH_ANH, $COLUMN_MO_TA_SP, $COLUMN_MA_LOAI_SP) VALUES
            ('SP001', 'Sản Phẩm 1', 10000, 'Cái', 'mental8.jpg', 'Mô tả sản phẩm 1', 'LSP001'),
            ('SP002', 'Sản Phẩm 2', 20000, 'Cái', 'noguchi.ipg', 'Mô tả sản phẩm 2', 'LSP002'),
            ('SP003', 'Sản Phẩm 3', 30000, 'Cái', 'obsidian.jpg', 'Mô tả sản phẩm 3', 'LSP003'),
            ('SP004', 'Sản Phẩm 4', 40000, 'Cái', 'oscar.jpg', 'Mô tả sản phẩm 4', 'LSP004'),
            ('SP005', 'Sản Phẩm 5', 50000, 'Cái', 'thecut19.jpg', 'Mô tả sản phẩm 5', 'LSP005')
        """
        db.execSQL(insertSanPhamData)

// Chèn dữ liệu vào bảng HoaDon
        val insertHoaDonData = """
            INSERT INTO $TABLE_HOADON ($COLUMN_MA_HD, $COLUMN_MA_KH, $COLUMN_MA_NV, $COLUMN_NGAY_XUAT_HD, $COLUMN_PT_TT) VALUES
            ('HD001', 'KH001', 'NV001', '2024-01-01', 'Tiền mặt'),
            ('HD002', 'KH002', 'NV002', '2024-01-15', 'Chuyển khoản'),
            ('HD003', 'KH003', 'NV003', '2024-02-01', 'Chuyển khoản'),
            ('HD004', 'KH004', 'NV004', '2024-02-15', 'Tiền mặt'),
            ('HD005', 'KH005', 'NV005', '2024-03-01', 'Chuyển khoản'),
            ('HD006', 'KH001', 'NV001', '2024-03-15', 'Chuyển khoản'),
            ('HD007', 'KH002', 'NV002', '2024-04-01', 'Tiền mặt'),
            ('HD008', 'KH003', 'NV002', '2024-04-15', 'Chuyển khoản'),
            ('HD009', 'KH004', 'NV002', '2024-05-01', 'Tiền mặt'),
            ('HD010', 'KH005', 'NV003', '2024-05-15', 'Tiền mặt'),
            ('HD011', 'KH001', 'NV002', '2024-06-01', 'Chuyển khoản'),
            ('HD012', 'KH002', 'NV001', '2024-06-15', 'Chuyển khoản'),
            ('HD013', 'KH003', 'NV003', '2024-07-01', 'Tiền mặt'),
            ('HD014', 'KH004', 'NV002', '2024-07-15', 'Chuyển khoản'),
            ('HD015', 'KH005', 'NV002', '2024-08-01', 'Tiền mặt'),
            ('HD016', 'KH001', 'NV004', '2024-08-15', 'Tiền mặt'),
            ('HD017', 'KH002', 'NV005', '2024-09-01', 'Chuyển khoản'),
            ('HD018', 'KH003', 'NV001', '2024-09-15', 'Tiền mặt'),
            ('HD019', 'KH004', 'NV003', '2024-10-01', 'Tiền mặt'),
            ('HD020', 'KH005', 'NV002', '2024-10-15', 'Chuyển khoản')

        """
        db.execSQL(insertHoaDonData)

// Chèn dữ liệu vào bảng CTHoaDon
        val insertCTHoaDonData = """
            INSERT INTO $TABLE_CTHOADON ($COLUMN_MA_HD, $COLUMN_MA_SP, $COLUMN_SO_LUONG_MUA, $COLUMN_DON_GIA_BAN) VALUES
            ('HD001', 'SP001', 2, 10000),
            ('HD002', 'SP002', 1, 20000),
            ('HD003', 'SP003', 3, 30000),
            ('HD004', 'SP004', 4, 40000),
            ('HD005', 'SP005', 5, 50000),
            ('HD006', 'SP001', 1, 10000),
            ('HD007', 'SP002', 2, 20000),
            ('HD008', 'SP003', 3, 30000),
            ('HD009', 'SP004', 4, 40000),
            ('HD010', 'SP005', 5, 50000),
            ('HD011', 'SP001', 2, 10000),
            ('HD012', 'SP002', 1, 20000),
            ('HD013', 'SP003', 3, 30000),
            ('HD014', 'SP004', 4, 40000),
            ('HD015', 'SP005', 5, 50000),
            ('HD016', 'SP001', 1, 10000),
            ('HD017', 'SP002', 2, 20000),
            ('HD018', 'SP003', 3, 30000),
            ('HD019', 'SP004', 4, 40000),
            ('HD020', 'SP005', 5, 50000)
        """
        db.execSQL(insertCTHoaDonData)

// Chèn dữ liệu vào bảng DonDatHang
        val insertDonDatHangData = """
            INSERT INTO $TABLE_DONDATHANG ($COLUMN_MA_DDH, $COLUMN_MA_KH, $COLUMN_MA_CH, $COLUMN_NGAY_DAT_HANG, $COLUMN_NGAY_DK_NHAN_HANG) VALUES
            ('DDH001', 'KH001', 'CH001', '2024-07-27', '2024-07-30'),
            ('DDH002', 'KH002', 'CH002', '2024-07-28', '2024-07-31'),
            ('DDH003', 'KH003', 'CH003', '2024-07-29', '2024-08-01'),
            ('DDH004', 'KH004', 'CH004', '2024-07-30', '2024-08-02'),
            ('DDH005', 'KH005', 'CH005', '2024-07-31', '2024-08-03')
        """
        db.execSQL(insertDonDatHangData)

// Chèn dữ liệu vào bảng CTDDH
        val insertCTDDHData = """
            INSERT INTO $TABLE_CTDDH ($COLUMN_MA_DDH, $COLUMN_MA_SP, $COLUMN_SL_DAT, $COLUMN_TINH_TRANG, $COLUMN_PT_TT) VALUES
            ('DDH001', 'SP001', 2, 'Đã đặt', 'Tiền mặt'),
            ('DDH002', 'SP002', 1, 'Đã đặt', 'Chuyển khoản'),
            ('DDH003', 'SP003', 3, 'Đã đặt', 'Thẻ tín dụng'),
            ('DDH004', 'SP004', 4, 'Đã đặt', 'Tiền mặt'),
            ('DDH005', 'SP005', 5, 'Đã đặt', 'Chuyển khoản')
        """
        db.execSQL(insertCTDDHData)

        val insertTonKhoData = """
            INSERT INTO $TABLE_TONKHO ($COLUMN_MA_CH, $COLUMN_MA_SP, $COLUMN_SL_TON) VALUES
            ('CH001', 'SP001', 20),
            ('CH001', 'SP002', 40),
            ('CH001', 'SP003', 69),
            ('CH001', 'SP004', 27),
            ('CH001', 'SP005', 19),
            ('CH002', 'SP001', 101),
            ('CH002', 'SP002', 26),
            ('CH002', 'SP003', 15),
            ('CH002', 'SP004', 49),
            ('CH002', 'SP005', 32),
            ('CH003', 'SP001', 101),
            ('CH003', 'SP002', 26),
            ('CH003', 'SP003', 15),
            ('CH003', 'SP004', 49),
            ('CH003', 'SP005', 32),
            ('CH004', 'SP001', 101),
            ('CH004', 'SP002', 26),
            ('CH004', 'SP003', 15),
            ('CH004', 'SP004', 49),
            ('CH004', 'SP005', 32),
            ('CH005', 'SP001', 101),
            ('CH005', 'SP002', 26),
            ('CH005', 'SP003', 15),
            ('CH005', 'SP004', 49),
            ('CH005', 'SP005', 32)
        """
        db.execSQL(insertTonKhoData)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Xóa bảng cũ nếu có
        db.execSQL("DROP TABLE IF EXISTS $TABLE_BOPHAN")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NHANVIEN")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_KHACHHANG")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_CUAHANG")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_LOAISP")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_SANPHAM")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_HOADON")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_CTHOADON")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_DONDATHANG")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_CTDDH")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_TONKHO")
        onCreate(db)
    }

    fun getAllKhachHang(): List<KhachHang> {
        val khachHangList = mutableListOf<KhachHang>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_KHACHHANG", null)
        if (cursor.moveToFirst()) {
            do {
                val maKH = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MA_KH))
                val hoTenKH = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_HO_TEN_KH))
                val gioiTinhKH = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_GIOI_TINH_KH))
                val diaChiKH = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DIA_CHI_KH))
                val ngaySinhKH = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NGAY_SINH_KH))
                val sdtKH = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SDT_KH))
                val khachHang = KhachHang(maKH, hoTenKH, gioiTinhKH, diaChiKH, ngaySinhKH, sdtKH)
                khachHangList.add(khachHang)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return khachHangList
    }
    fun getAllNhanVien(): List<NhanVien> {
        val nhanVienList = mutableListOf<NhanVien>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NHANVIEN", null)
        if (cursor.moveToFirst()) {
            do {
                val maNV = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MA_NV))
                val hoTenNV = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_HO_TEN_NV))
                val gioiTinhNV = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_GIOI_TINH_NV))
                val diaChiNV = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DIA_CHI_NV))
                val ngaySinhNV = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NGAY_SINH_NV))
                val sdtNV = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SDT_NV))
                val maBP = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MA_BP))
                val maCH = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MA_CH))

                val nhanVien = NhanVien(maNV, hoTenNV, gioiTinhNV, diaChiNV, ngaySinhNV, sdtNV, maBP,maCH)
                nhanVienList.add(nhanVien)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return nhanVienList
    }
    fun getAllSanPham(): List<SanPham> {
        val sanPhamList = mutableListOf<SanPham>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_SANPHAM", null)
        if (cursor.moveToFirst()) {
            do {
                val maSP = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MA_SP))
                val tenSP = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TEN_SP))
                val giaBan = cursor.getFloat(cursor.getColumnIndexOrThrow(COLUMN_GIA_BAN))
                val DVT = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DVT))
                val hinhAnh = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_HINH_ANH))
                val moTa = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MO_TA_SP))
                val maLoai = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MA_LOAI_SP))
                val sanPham = SanPham(maSP, tenSP, giaBan, DVT, hinhAnh, moTa, maLoai)
                sanPhamList.add(sanPham)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return sanPhamList
    }

    fun getAllHoaDon(): List<HoaDon> {
        val hoaDonList = mutableListOf<HoaDon>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_HOADON", null)
        if (cursor.moveToFirst()) {
            do {
                val maHD = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MA_HD_FKhd))
                val maKH = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MA_KH_FKhd))
                val maNV = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MA_NV_FKhd))
                val ngayXuatHD = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NGAY_XUAT_HD))
                val ptTT = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PT_TTHD))
                val hoaDon = HoaDon(maHD, maKH, maNV, ngayXuatHD, ptTT)
                hoaDonList.add(hoaDon)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return hoaDonList
    }

    fun getAllCTHoaDon(): List<CTHoaDon> {
        val cthoaDonList = mutableListOf<CTHoaDon>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_CTHOADON", null)
        if (cursor.moveToFirst()) {
            do {
                val maHD = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MA_HD))
                val maSP = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MA_SP))
                val soLuongMua = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SO_LUONG_MUA))
                val donGiaBan = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_DON_GIA_BAN))
                val cthoaDon = CTHoaDon(maHD, maSP, soLuongMua, donGiaBan)
                cthoaDonList.add(cthoaDon)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return cthoaDonList
    }
    fun getAllTonKho(): List<TonKho> {
        val tonKhoList = mutableListOf<TonKho>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_TONKHO", null)
        if (cursor.moveToFirst()) {
            do {
                val maCH = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MA_CH_FKtk))
                val maSP = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MA_SP_FKtk))
                val soLuongTon = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SL_TON))
                val tonKho = TonKho(maCH, maSP, soLuongTon)
                tonKhoList.add(tonKho)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return tonKhoList
    }
    fun getStoreList(): List<String> {
        val storeList = mutableListOf<String>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT DISTINCT $COLUMN_MA_CH_FKtk FROM $TABLE_TONKHO", null)
        if (cursor.moveToFirst()) {
            do {
                storeList.add(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MA_CH_FKtk)))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return storeList
    }

    fun getGiaBan(maSP: String): Double {
        val db = this.readableDatabase
        val cursor = db.query(
            TABLE_SANPHAM, arrayOf(COLUMN_GIA_BAN),
            "$COLUMN_MA_SP = ?", arrayOf(maSP),
            null, null, null
        )
        var giaBan = 0.0
        if (cursor.moveToFirst()) {
            giaBan = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_GIA_BAN))
        }
        cursor.close()
        return giaBan
    }
}