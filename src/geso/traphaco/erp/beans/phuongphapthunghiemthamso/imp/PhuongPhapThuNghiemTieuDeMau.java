package geso.traphaco.erp.beans.phuongphapthunghiemthamso.imp;

import geso.traphaco.erp.beans.phuongphapthunghiemthamso.IPhuongPhapThuNghiemTieuDeMau;

public class PhuongPhapThuNghiemTieuDeMau implements IPhuongPhapThuNghiemTieuDeMau{
	private String stt;
	private String tieudemau;
	private String maPPTN;
	
	public PhuongPhapThuNghiemTieuDeMau(){
		this.stt="";
		this.tieudemau="";
		this.maPPTN="";
	}
	
	public String getMaPPTN() {
		return maPPTN;
	}
	public void setMaPPTN(String maPPTN) {
		this.maPPTN = maPPTN;
	}
	public String getStt() {
		return stt;
	}
	public void setStt(String stt) {
		this.stt = stt;
	}
	public String getTieudemau() {
		return tieudemau;
	}
	public void setTieudemau(String tieudemau) {
		this.tieudemau = tieudemau;
	}
	
}
