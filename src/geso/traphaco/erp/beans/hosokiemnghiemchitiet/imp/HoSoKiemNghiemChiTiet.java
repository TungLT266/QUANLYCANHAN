package geso.traphaco.erp.beans.hosokiemnghiemchitiet.imp;

import geso.traphaco.erp.beans.hosokiemnghiemchitiet.IHoSoKiemNghiemChiTiet;

public class HoSoKiemNghiemChiTiet implements IHoSoKiemNghiemChiTiet{
	private String MaHSTN;
	private String MaKiemNghiemVien;
	private String TenKiemNghiemVien;
	private String MaTCKN;
	private String MaYCKT;
	private String TenYCKT;
	private String ThongSoYeuCau;
	private String ThongSoYeuCauDen;
	private String ThoiGianBD;
	private String ThoiGianKT;
	private String ThoiLuong;
	private String DanhGia;
	private String UserId;
	private String UserName;
	private String msg;
	private String SoTT;
	
	public HoSoKiemNghiemChiTiet(){
		this.MaHSTN="";
		this.MaYCKT="";
		this.MaKiemNghiemVien="";
		this.TenKiemNghiemVien="";
		this.MaTCKN="";
		this.TenYCKT="";
		this.ThongSoYeuCau="";
		this.ThongSoYeuCauDen="";
		this.ThoiGianBD="";
		this.ThoiGianKT="";
		this.ThoiLuong="";
		this.DanhGia="";
		this.msg="";
		this.UserId="";
		this.UserName="";
		this.SoTT="";
	}
	
	public String getTenYCKT() {
		return TenYCKT;
	}

	public void setTenYCKT(String tenYCKT) {
		TenYCKT = tenYCKT;
	}

	public String getMaYCKT() {
		return MaYCKT;
	}

	public void setMaYCKT(String maYCKT) {
		MaYCKT = maYCKT;
	}

	public String getSoTT() {
		return SoTT;
	}

	public void setSoTT(String soTT) {
		SoTT = soTT;
	}

	public String getMaHSTN() {
		return MaHSTN;
	}

	public void setMaHSTN(String maHSTN) {
		MaHSTN = maHSTN;
	}

	public String getMaKiemNghiemVien() {
		return MaKiemNghiemVien;
	}

	public void setMaKiemNghiemVien(String maKiemNghiemVien) {
		MaKiemNghiemVien = maKiemNghiemVien;
	}

	public String getMaTCKN() {
		return MaTCKN;
	}

	public void setMaTCKN(String maTCKN) {
		MaTCKN = maTCKN;
	}

	public String getThongSoYeuCau() {
		return ThongSoYeuCau;
	}

	public void setThongSoYeuCau(String thongSoYeuCau) {
		ThongSoYeuCau = thongSoYeuCau;
	}

	public String getThoiGianBD() {
		return ThoiGianBD;
	}

	public void setThoiGianBD(String thoiGianBD) {
		ThoiGianBD = thoiGianBD;
	}

	public String getThoiGianKT() {
		return ThoiGianKT;
	}

	public void setThoiGianKT(String thoiGianKT) {
		ThoiGianKT = thoiGianKT;
	}

	public String getThoiLuong() {
		return ThoiLuong;
	}

	public void setThoiLuong(String thoiLuong) {
		ThoiLuong = thoiLuong;
	}

	public String getDanhGia() {
		return DanhGia;
	}

	public void setDanhGia(String danhGia) {
		DanhGia = danhGia;
	}

	public String getUserId() {
		return UserId;
	}

	public void setUserId(String userId) {
		UserId = userId;
	}

	public String getUserName() {
		return UserName;
	}

	public void setUserName(String userName) {
		UserName = userName;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getThongSoYeuCauDen() {
		return ThongSoYeuCauDen;
	}

	public void setThongSoYeuCauDen(String thongSoYeuCauDen) {
		ThongSoYeuCauDen = thongSoYeuCauDen;
	}

	public String getTenKiemNghiemVien() {
		return TenKiemNghiemVien;
	}

	public void setTenKiemNghiemVien(String tenKiemNghiemVien) {
		TenKiemNghiemVien = tenKiemNghiemVien;
	}
	
	
}
