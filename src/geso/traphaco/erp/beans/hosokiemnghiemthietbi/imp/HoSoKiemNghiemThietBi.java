package geso.traphaco.erp.beans.hosokiemnghiemthietbi.imp;

import geso.traphaco.erp.beans.hosokiemnghiemthietbi.IHoSoKiemNghiemThietBi;

public class HoSoKiemNghiemThietBi implements IHoSoKiemNghiemThietBi {
	private String SoTT;
	private String MaHoSoKiemNghiem;
	private String MaThietBi;
	private String TenThietBi;
	private String NgayDanhGia;
	private String NgayDanhGiaKeTiep;
	private String GhiChu;
	private String msg;
	
	public HoSoKiemNghiemThietBi(){
		this.SoTT="";
		this.MaHoSoKiemNghiem="";
		this.MaThietBi="";
		this.TenThietBi="";
		this.NgayDanhGia="";
		this.NgayDanhGiaKeTiep="";
		this.msg="";
	}

	
	public String getSoTT() {
		return SoTT;
	}


	public void setSoTT(String soTT) {
		SoTT = soTT;
	}


	public String getMaHoSoKiemNghiem() {
		return MaHoSoKiemNghiem;
	}

	public void setMaHoSoKiemNghiem(String maHoSoKiemNghiem) {
		MaHoSoKiemNghiem = maHoSoKiemNghiem;
	}

	public String getMaThietBi() {
		return MaThietBi;
	}

	public void setMaThietBi(String maThietBi) {
		MaThietBi = maThietBi;
	}

	public String getTenThietBi() {
		return TenThietBi;
	}

	public void setTenThietBi(String tenThietBi) {
		TenThietBi = tenThietBi;
	}

	public String getNgayDanhGia() {
		return NgayDanhGia;
	}

	public void setNgayDanhGia(String ngayDanhGia) {
		NgayDanhGia = ngayDanhGia;
	}

	public String getNgayDanhGiaKeTiep() {
		return NgayDanhGiaKeTiep;
	}

	public void setNgayDanhGiaKeTiep(String ngayDanhGiaKeTiep) {
		NgayDanhGiaKeTiep = ngayDanhGiaKeTiep;
	}

	public String getGhiChu() {
		return GhiChu;
	}

	public void setGhiChu(String ghiChu) {
		GhiChu = ghiChu;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
}
