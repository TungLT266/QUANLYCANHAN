package geso.traphaco.erp.beans.chiphinhapkhau.imp;

import geso.traphaco.erp.beans.chiphinhapkhau.ISanPhamPhanBo;

import java.util.List;

public class SanPhamPhanBo implements ISanPhamPhanBo{

	private String maSp;
	private String tenSp;
	private Double tien;
	private Double phanTram;
	private Double phanBo;
	private String soLo;
	private String loai;
	private String manhanHang;
	private String maLon;
	private String idSP;
	
	
	public String getIdSP(){
		return this.idSP ;
	}
	public void setIdSP(String idSP){
		this.idSP = idSP;
	}
	public String getMaLon() {
		return maLon;
	}
	public void setMaLon(String maLon) {
		this.maLon = maLon;
	}
	public String getManhanHang() {
		return manhanHang;
	}
	public void setManhanHang(String manhanHang) {
		this.manhanHang = manhanHang;
	}
	public String getLoai() {
		return loai;
	}
	public void setLoai(String loai) {
		this.loai = loai;
	}
	public String getSoLo() {
		return soLo;
	}
	public void setSoLo(String soLo) {
		this.soLo = soLo;
	}
	public String getMaSp() {
		return maSp;
	}
	public void setMaSp(String maSp) {
		this.maSp = maSp;
	}
	public String getTenSp() {
		return tenSp;
	}
	public void setTenSp(String tenSp) {
		this.tenSp = tenSp;
	}
	public Double getTien() {
		return tien;
	}
	public void setTien(Double tien) {
		this.tien = tien;
	}
	public Double getPhanTram() {
		return phanTram;
	}
	public void setPhanTram(Double phanTram) {
		this.phanTram = phanTram;
	}
	public Double getPhanBo() {
		return phanBo;
	}
	public void setPhanBo(Double phanBo) {
		this.phanBo = phanBo;
	}
	public SanPhamPhanBo(String maSp, String tenSp, Double tien,
			Double phanTram, Double phanBo) {
		super();
		this.maSp = maSp;
		this.tenSp = tenSp;
		this.tien = tien;
		this.phanTram = phanTram;
		this.phanBo = phanBo;
		
	}
	
	public SanPhamPhanBo() {
		super();
		this.maSp = "";
		this.tenSp = "";
		this.tien = 0.0;
		this.phanTram = 0.0;
		this.phanBo = 0.0;
		this.soLo = "";
		this.maLon = "";
		this.idSP = "";
	}
	
	public static double getTongTien(List<ISanPhamPhanBo> list)
	{
		double tongTien = 0;
		
		for (ISanPhamPhanBo sp : list)
		 tongTien += sp.getTien();
		
		return tongTien;
	}
}
