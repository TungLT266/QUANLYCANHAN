package geso.traphaco.erp.beans.dinhtinheo.imp;

public class DinhTinhEODetail {
	String dinhTinhEO;
	String sanPham_fk;
	String khott_fk;
	String soLo;
	String ngayHetHan;
	String bin_fk;
	String ngaySanXuat;
	String ngayNhapKho;
	String ngayBatDau;
	String maRQ;
	double hamLuong;
	double hamAm;
	String maMe;
	String maThung;
	String maPhieu;
	String maPhieuDinhTinh;
	String maPhieuDinhTinhNew;
	String maPhieuEO;
	String maPhieuEONew;
	String maSanPham;
	String tenSanPham;
	String khoChiTiet_fk;
	
	String nsxMa;
	String nsx_fk;
	
	
	public DinhTinhEODetail() {
		super();
		this.dinhTinhEO = "";
		this.sanPham_fk = "";
		this.khott_fk = "";
		this.soLo = "";
		this.ngayHetHan = "";
		this.bin_fk = "";
		this.ngaySanXuat = "";
		this.ngayNhapKho = "";
		this.ngayBatDau = "";
		this.maRQ = "";
		this.hamLuong = 0;
		this.hamAm = 0;
		this.maMe = "";
		this.maPhieu = "";
		this.maPhieuDinhTinh = "";
		this.maPhieuEO = "";
		this.maThung = "";
		this.maSanPham = "";
		this.tenSanPham = "";
		this.maPhieuDinhTinhNew = "";
		this.maPhieuEONew = "";
		this.khoChiTiet_fk = "";
		this.nsxMa = "";
		this.nsx_fk = "";
	}
	public DinhTinhEODetail(String dinhTinhEO, String sanPham_fk, String khott_fk, String soLo, String ngayHetHan, String bin_fk, String ngaySanXuat,
			String ngayNhapKho, String ngayBatDau, String maRQ, double hamLuong, double hamAm, String maMe, String maPhieu, String maPhieuDinhTinh,
			String maPhieuEO) {
		super();
		this.dinhTinhEO = dinhTinhEO;
		this.sanPham_fk = sanPham_fk;
		this.khott_fk = khott_fk;
		this.soLo = soLo;
		this.ngayHetHan = ngayHetHan;
		this.bin_fk = bin_fk;
		this.ngaySanXuat = ngaySanXuat;
		this.ngayNhapKho = ngayNhapKho;
		this.ngayBatDau = ngayBatDau;
		this.maRQ = maRQ;
		this.hamLuong = hamLuong;
		this.hamAm = hamAm;
		this.maMe = maMe;
		this.maPhieu = maPhieu;
		this.maPhieuDinhTinh = maPhieuDinhTinh;
		this.maPhieuEO = maPhieuEO;
		this.maSanPham = "";
		this.tenSanPham = "";
	}
	
	public String getNsxMa() {
		return nsxMa;
	}
	public void setNsxMa(String nsxMa) {
		this.nsxMa = nsxMa;
	}
	public String getNsx_fk() {
		return nsx_fk;
	}
	public void setNsx_fk(String nsx_fk) {
		this.nsx_fk = nsx_fk;
	}
	public String getKhoChiTiet_fk() {
		return khoChiTiet_fk;
	}
	public void setKhoChiTiet_fk(String khoChiTiet_fk) {
		this.khoChiTiet_fk = khoChiTiet_fk;
	}
	public String getMaPhieuDinhTinhNew() {
		return maPhieuDinhTinhNew;
	}
	public void setMaPhieuDinhTinhNew(String maPhieuDinhTinhNew) {
		this.maPhieuDinhTinhNew = maPhieuDinhTinhNew;
	}
	public String getMaPhieuEONew() {
		return maPhieuEONew;
	}
	public void setMaPhieuEONew(String maPhieuEONew) {
		this.maPhieuEONew = maPhieuEONew;
	}
	public String getMaSanPham() {
		return maSanPham;
	}
	public void setMaSanPham(String maSanPham) {
		this.maSanPham = maSanPham;
	}
	public String getTenSanPham() {
		return tenSanPham;
	}
	public void setTenSanPham(String tenSanPham) {
		this.tenSanPham = tenSanPham;
	}
	public String getMaThung() {
		return maThung;
	}
	public void setMaThung(String maThung) {
		this.maThung = maThung;
	}
	public String getDinhTinhEO() {
		return dinhTinhEO;
	}
	public void setDinhTinhEO(String dinhTinhEO) {
		this.dinhTinhEO = dinhTinhEO;
	}
	public String getSanPham_fk() {
		return sanPham_fk;
	}
	public void setSanPham_fk(String sanPham_fk) {
		this.sanPham_fk = sanPham_fk;
	}
	public String getKhott_fk() {
		return khott_fk;
	}
	public void setKhott_fk(String khott_fk) {
		this.khott_fk = khott_fk;
	}
	public String getSoLo() {
		return soLo;
	}
	public void setSoLo(String soLo) {
		this.soLo = soLo;
	}
	public String getNgayHetHan() {
		return ngayHetHan;
	}
	public void setNgayHetHan(String ngayHetHan) {
		this.ngayHetHan = ngayHetHan;
	}
	public String getBin_fk() {
		return bin_fk;
	}
	public void setBin_fk(String bin_fk) {
		this.bin_fk = bin_fk;
	}
	public String getNgaySanXuat() {
		return ngaySanXuat;
	}
	public void setNgaySanXuat(String ngaySanXuat) {
		this.ngaySanXuat = ngaySanXuat;
	}
	public String getNgayNhapKho() {
		return ngayNhapKho;
	}
	public void setNgayNhapKho(String ngayNhapKho) {
		this.ngayNhapKho = ngayNhapKho;
	}
	public String getNgayBatDau() {
		return ngayBatDau;
	}
	public void setNgayBatDau(String ngayBatDau) {
		this.ngayBatDau = ngayBatDau;
	}
	public String getMaRQ() {
		return maRQ;
	}
	public void setMaRQ(String maRQ) {
		this.maRQ = maRQ;
	}
	public double getHamLuong() {
		return hamLuong;
	}
	public void setHamLuong(double hamLuong) {
		this.hamLuong = hamLuong;
	}
	public double getHamAm() {
		return hamAm;
	}
	public void setHamAm(double hamAm) {
		this.hamAm = hamAm;
	}
	public String getMaMe() {
		return maMe;
	}
	public void setMaMe(String maMe) {
		this.maMe = maMe;
	}
	public String getMaPhieu() {
		return maPhieu;
	}
	public void setMaPhieu(String maPhieu) {
		this.maPhieu = maPhieu;
	}
	public String getMaPhieuDinhTinh() {
		return maPhieuDinhTinh;
	}
	public void setMaPhieuDinhTinh(String maPhieuDinhTinh) {
		this.maPhieuDinhTinh = maPhieuDinhTinh;
	}
	public String getMaPhieuEO() {
		return maPhieuEO;
	}
	public void setMaPhieuEO(String maPhieuEO) {
		this.maPhieuEO = maPhieuEO;
	}
}
