package geso.traphaco.erp.beans.kiemdinhchatluong.kho.imp;

public class KiemDinhObj {
	String dinhTinhEO;
	String sanPham_fk;
	String khott_fk;
	String soLo;
	String ngayHetHan;
	String bin_fk;
	String ngayNhapKho;
	String maRQ;
	double hamLuong;
	double hamAm;
	String maMe;
	String maThung;
	String maPhieu;
	String maPhieuDinhTinh;
	String maPhieuEO;
	String khoChiTiet_fk;
	
	String soLuong;
	String soLuongDat;
	String soLuongMau;
	String soLuongKhongDat;
	double hamLuongNew;
	double hamAmNew;
	
	public KiemDinhObj() {
		super();
		this.dinhTinhEO = "";
		this.sanPham_fk = "";
		this.khott_fk = "";
		this.soLo = "";
		this.ngayHetHan = "";
		this.bin_fk = "";
		this.ngayNhapKho = "";
		this.maRQ = "";
		this.hamLuong = 100;
		this.hamAm = 0;
		this.maMe = "";
		this.maThung = "";
		this.maPhieu = "";
		this.maPhieuDinhTinh = "";
		this.maPhieuEO = "";
		this.khoChiTiet_fk = "";
		this.soLuong = "";
		this.soLuongDat = "";
		this.soLuongMau = "";
		this.soLuongKhongDat = "";
		this.hamLuongNew = 100;
		this.hamAmNew = 0;
	}
	
	public KiemDinhObj(String dinhTinhEO, String sanPham_fk, String khott_fk, String soLo, String ngayHetHan, String bin_fk, String ngayNhapKho,
			String maRQ, double hamLuong, double hamAm, String maMe, String maThung, String maPhieu, String maPhieuDinhTinh, String maPhieuEO,
			String khoChiTiet_fk, String soLuong, String soLuongDat, String soLuongMau, String soLuongKhongDat, double hamLuongNew, double hamAmNew) {
		super();
		this.dinhTinhEO = dinhTinhEO;
		this.sanPham_fk = sanPham_fk;
		this.khott_fk = khott_fk;
		this.soLo = soLo;
		this.ngayHetHan = ngayHetHan;
		this.bin_fk = bin_fk;
		this.ngayNhapKho = ngayNhapKho;
		this.maRQ = maRQ;
		this.hamLuong = hamLuong;
		this.hamAm = hamAm;
		this.maMe = maMe;
		this.maThung = maThung;
		this.maPhieu = maPhieu;
		this.maPhieuDinhTinh = maPhieuDinhTinh;
		this.maPhieuEO = maPhieuEO;
		this.khoChiTiet_fk = khoChiTiet_fk;
		this.soLuong = soLuong;
		this.soLuongDat = soLuongDat;
		this.soLuongMau = soLuongMau;
		this.soLuongKhongDat = soLuongKhongDat;
		this.hamLuongNew = hamLuongNew;
		this.hamAmNew = hamAmNew;
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
	public String getNgayNhapKho() {
		return ngayNhapKho;
	}
	public void setNgayNhapKho(String ngayNhapKho) {
		this.ngayNhapKho = ngayNhapKho;
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
	public String getMaThung() {
		return maThung;
	}
	public void setMaThung(String maThung) {
		this.maThung = maThung;
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
	public String getKhoChiTiet_fk() {
		return khoChiTiet_fk;
	}
	public void setKhoChiTiet_fk(String khoChiTiet_fk) {
		this.khoChiTiet_fk = khoChiTiet_fk;
	}
	public String getSoLuong() {
		return soLuong;
	}
	public void setSoLuong(String soLuong) {
		this.soLuong = soLuong;
	}
	public String getSoLuongDat() {
		return soLuongDat;
	}
	public void setSoLuongDat(String soLuongDat) {
		this.soLuongDat = soLuongDat;
	}
	public String getSoLuongMau() {
		return soLuongMau;
	}
	public void setSoLuongMau(String soLuongMau) {
		this.soLuongMau = soLuongMau;
	}
	public String getSoLuongKhongDat() {
		return soLuongKhongDat;
	}
	public void setSoLuongKhongDat(String soLuongKhongDat) {
		this.soLuongKhongDat = soLuongKhongDat;
	}
	public double getHamLuongNew() {
		return hamLuongNew;
	}
	public void setHamLuongNew(double hamLuongNew) {
		this.hamLuongNew = hamLuongNew;
	}
	public double getHamAmNew() {
		return hamAmNew;
	}
	public void setHamAmNew(double hamAmNew) {
		this.hamAmNew = hamAmNew;
	}	
}
