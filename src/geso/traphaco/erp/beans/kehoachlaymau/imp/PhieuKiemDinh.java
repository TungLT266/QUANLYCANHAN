package geso.traphaco.erp.beans.kehoachlaymau.imp;

public class PhieuKiemDinh {
	
	
	private String sott;
	private String ngayDanhGia;
	private String ngayDanhGiaLai;
	private String ngayThucTe;
	private String ghiChu;
	
	
	
	public PhieuKiemDinh(String sott, String ngayDanhGia, String ngayDanhGiaLai, String ghiChu) {
		super();
		this.sott = sott;
		this.ngayDanhGia = ngayDanhGia;
		this.ngayDanhGiaLai = ngayDanhGiaLai;
	}
	
	
	
	public PhieuKiemDinh() {
		this.sott = "";
		this.ngayDanhGia = "";
		this.ngayDanhGiaLai = "";
	}

	

	public String getSott() {
		return this.sott;
	}



	public void setSott(String sott) {
		this.sott = sott;
	}



	public String getNgayDanhGia() {
		return ngayDanhGia;
	}



	public void setNgayDanhGia(String ngayDanhGia) {
		this.ngayDanhGia = ngayDanhGia;
	}



	public String getNgayDanhGiaLai() {
		return ngayDanhGiaLai;
	}



	public void setNgayDanhGiaLai(String ngayDanhGiaLai) {
		this.ngayDanhGiaLai = ngayDanhGiaLai;
	}



	public String getGhiChu() {
		return ghiChu;
	}



	public void setGhiChu(String ghiChu) {
		this.ghiChu = ghiChu;
	}



	public String getNgayThucTe() {
		return ngayThucTe;
	}



	public void setNgayThucTe(String ngayThucTe) {
		this.ngayThucTe = ngayThucTe;
	}
	
	
 
}
