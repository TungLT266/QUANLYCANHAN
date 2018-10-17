package geso.traphaco.erp.beans.kehoachsanxuatvamuahang;
import geso.traphaco.erp.beans.kehoachsanxuatvamuahang.imp.*;
public class SanPham implements ISanPham{
	 private String maSanPham;
	 private String tenSanPham;
	 private String donVi;
	 private String ghiChu;
	 private String soLo;
	 private String loai;
	 private String soLuong;
	 
	 
	public String getSoLuong() {
		return soLuong;
	}
	public void setSoLuong(String soLuong) {
		this.soLuong = soLuong;
	}
	public String getLoai() {
		return loai;
	}
	public void setLoai(String loai) {
		this.loai = loai;
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
	public String getDonVi() {
		return donVi;
	}
	public void setDonVi(String donVi) {
		this.donVi = donVi;
	}
	public String getGhiChu() {
		return ghiChu;
	}
	public void setGhiChu(String ghiChu) {
		this.ghiChu = ghiChu;
	}
	public String getSoLo() {
		return soLo;
	}
	public void setSoLo(String soLo) {
		this.soLo = soLo;
	}
	public SanPham(String maSanPham, String tenSanPham, String donVi,
			String ghiChu, String soLo) {
		super();
		this.maSanPham = maSanPham;
		this.tenSanPham = tenSanPham;
		this.donVi = donVi;
		this.ghiChu = ghiChu;
		this.soLo = soLo;
	}
	
	public SanPham() {
		super();
		this.maSanPham = "";
		this.tenSanPham = "";
		this.donVi = "";
		this.ghiChu = "";
		this.soLo = "";
		this.loai = "";
		this.soLuong = "0";
		
	}
	
	 
}
