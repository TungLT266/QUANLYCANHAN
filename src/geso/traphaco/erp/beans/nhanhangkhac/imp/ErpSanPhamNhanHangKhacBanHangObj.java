package geso.traphaco.erp.beans.nhanhangkhac.imp;

public class ErpSanPhamNhanHangKhacBanHangObj {
	String stt;
	String spId;
	String spMa;
	String spTen;
	String spDonvi;
	String spSoluongchungtu;
	String spSoluongthucnhap;
	String spDongia;
	String spThanhtien;
	String spGhichu;
	
	public ErpSanPhamNhanHangKhacBanHangObj () {
		this.stt="";
		this.spId="";
		this.spMa="";
		this.spTen="";
		this.spDonvi="";
		this.spSoluongchungtu="";
		this.spSoluongthucnhap="";
		this.spDongia="";
		this.spThanhtien="";
		this.spGhichu="";

	}

	public ErpSanPhamNhanHangKhacBanHangObj (String stt, String id, String ma, String ten,String donvi, String sl, String sl1,
			String dgviet, String spThanhtien,String spGhichu) {
		this.stt=stt;
		this.spId=id;
		this.spMa=ma;
		this.spTen=ten;
		this.spDonvi=donvi;
		this.spSoluongchungtu=sl;
		this.spSoluongthucnhap=sl1;
		this.spDongia=dgviet;
		this.spThanhtien=spThanhtien;
		this.spGhichu=spGhichu;

	}
	
	
	
	public String getStt() {
		return stt;
	}
	public void setStt(String stt) {
		this.stt = stt;
	}
	public String getSpId() {
		return spId;
	}
	public void setSpId(String spId) {
		this.spId = spId;
	}
	public String getSpMa() {
		return spMa;
	}
	public void setSpMa(String spMa) {
		this.spMa = spMa;
	}
	public String getSpTen() {
		return spTen;
	}
	public void setSpTen(String spTen) {
		this.spTen = spTen;
	}
	public String getSpDonvi() {
		return spDonvi;
	}
	public void setSpDonvi(String spDonvi) {
		this.spDonvi = spDonvi;
	}
	public String getSpSoluongchungtu() {
		return spSoluongchungtu;
	}
	public void setSpSoluongchungtu(String spSoluongchungtu) {
		this.spSoluongchungtu = spSoluongchungtu;
	}
	public String getSpSoluongthucnhap() {
		return spSoluongthucnhap;
	}
	public void setSpSoluongthucnhap(String spSoluongthucnhap) {
		this.spSoluongthucnhap = spSoluongthucnhap;
	}
	public String getSpDongia() {
		return spDongia;
	}
	public void setSpDongia(String spDongia) {
		this.spDongia = spDongia;
	}
	public String getSpThanhtien() {
		return spThanhtien;
	}
	public void setSpThanhtien(String spThanhtien) {
		this.spThanhtien = spThanhtien;
	}
	public String getSpGhichu() {
		return spGhichu;
	}
	public void setSpGhichu(String spGhichu) {
		this.spGhichu = spGhichu;
	}

	
}
