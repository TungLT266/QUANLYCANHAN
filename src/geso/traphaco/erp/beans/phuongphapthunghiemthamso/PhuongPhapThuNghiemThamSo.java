package geso.traphaco.erp.beans.phuongphapthunghiemthamso;

import geso.traphaco.erp.beans.phuongphapthunghiemthamso.imp.IPhuongPhapThuNghiemThamSo;

public class PhuongPhapThuNghiemThamSo implements IPhuongPhapThuNghiemThamSo{
	private String MaPPTN;
	private String MaDVDL;
	private String PopupThamSo;
	private String LoaiTick;
	private String ThamSoPopup;
	private String kyhieuThamso;
	private String congthuc;
	private String min;
	private String max;
	private String avg;
	private String sum;
	
	public PhuongPhapThuNghiemThamSo(){
		this.MaPPTN="";
		this.MaDVDL="";
		this.PopupThamSo="";
		this.LoaiTick="";
		this.ThamSoPopup="";
		this.kyhieuThamso="";
		this.congthuc="";
		this.min="";
		this.max="";
		this.avg="";
		this.sum="";
	}

	public String getCongthuc() {
		return congthuc;
	}
	public void setCongthuc(String congthuc) {
		this.congthuc = congthuc;
	}
	public String getMin() {
		return min;
	}
	public void setMin(String min) {
		this.min = min;
	}
	public String getMax() {
		return max;
	}
	public void setMax(String max) {
		this.max = max;
	}
	public String getAvg() {
		return avg;
	}
	public void setAvg(String avg) {
		this.avg = avg;
	}
	public String getSum() {
		return sum;
	}
	public void setSum(String sum) {
		this.sum = sum;
	}
	public String getKyhieuThamso() {
		return kyhieuThamso;
	}
	public void setKyhieuThamso(String kyhieuThamso) {
		this.kyhieuThamso = kyhieuThamso;
	}
	public String getMaPPTN() {
		return MaPPTN;
	}
	public void setMaPPTN(String maPPTN) {
		MaPPTN = maPPTN;
	}
	public String getMaDVDL() {
		return MaDVDL;
	}
	public void setMaDVDL(String maDVDL) {
		MaDVDL = maDVDL;
	}

	public String getPopupThamSo() {
		return PopupThamSo;
	}

	public void setPopupThamSo(String popupThamSo) {
		PopupThamSo = popupThamSo;
	}

	public String getLoaiTick() {
		return LoaiTick;
	}

	public void setLoaiTick(String loaiTick) {
		LoaiTick = loaiTick;
	}

	public String getThamSoPopup() {
		return ThamSoPopup;
	}

	public void setThamSoPopup(String thamSoPopup) {
		ThamSoPopup = thamSoPopup;
	}
	
}
