package qlcn.pages.taikhoan.beans.imp;

public class TaiKhoanLogList {
	private String ngaylog;
	private String ten;
	private String sotien;
	private String donvi;
	private String nganhang;
	private String isTknganhang;
	private String isTktindung;
	private String hanmuc;
	private String noTindung;
	private String trangthai;
	private String msg;
	
	public TaiKhoanLogList() {
		this.ngaylog = "";
		this.ten = "";
		this.sotien = "";
		this.donvi = "";
		this.nganhang = "";
		this.isTknganhang = "1";
		this.isTktindung = "0";
		this.hanmuc = "";
		this.noTindung = "";
		this.trangthai = "1";
		this.msg = "";
	}

	public String getTen() {
		return ten;
	}

	public void setTen(String ten) {
		this.ten = ten;
	}

	public String getSotien() {
		return sotien;
	}

	public void setSotien(String sotien) {
		this.sotien = sotien;
	}

	public String getDonvi() {
		return donvi;
	}

	public void setDonvi(String donvi) {
		this.donvi = donvi;
	}

	public String getNganhang() {
		return nganhang;
	}

	public void setNganhang(String nganhang) {
		this.nganhang = nganhang;
	}

	public String getIsTknganhang() {
		return isTknganhang;
	}

	public void setIsTknganhang(String isTknganhang) {
		this.isTknganhang = isTknganhang;
	}

	public String getIsTktindung() {
		return isTktindung;
	}

	public void setIsTktindung(String isTktindung) {
		this.isTktindung = isTktindung;
	}

	public String getHanmuc() {
		return hanmuc;
	}

	public void setHanmuc(String hanmuc) {
		this.hanmuc = hanmuc;
	}

	public String getNoTindung() {
		return noTindung;
	}

	public void setNoTindung(String noTindung) {
		this.noTindung = noTindung;
	}

	public String getTrangthai() {
		return trangthai;
	}

	public void setTrangthai(String trangthai) {
		this.trangthai = trangthai;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getNgaylog() {
		return ngaylog;
	}

	public void setNgaylog(String ngaylog) {
		this.ngaylog = ngaylog;
	}
}
