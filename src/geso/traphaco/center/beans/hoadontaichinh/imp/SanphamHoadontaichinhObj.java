package geso.traphaco.center.beans.hoadontaichinh.imp;

public class SanphamHoadontaichinhObj {
	String stt,tenSp,noiSX,donviSp,soluongSp,dongiaSp,thanhtien;
	String solo,maSp,ptThue,tienThue,sotientt;
	String ngaySp,diengiaiCk;
	

	public SanphamHoadontaichinhObj(String stt,String tenSp,String noiSX,String donviSp,String soluongSp,String dongiaSp,String thanhtien){
		this.stt=stt;
		this.tenSp=tenSp;
		this.noiSX=noiSX;
		this.donviSp=donviSp;
		this.soluongSp=soluongSp;
		this.dongiaSp=dongiaSp;
		this.thanhtien=thanhtien;
	}
	
	public SanphamHoadontaichinhObj(String stt,String maSp,String tenSp,String solo,String ngaySp,
			String donviSp,String soluongSp,String dongiaSp,String thanhtien
			,String ptThue,String tienThue,String sotientt){
		this.stt=stt;
		this.maSp=maSp;
		this.tenSp=tenSp;
		this.solo=solo;
		this.ngaySp=ngaySp;
		this.donviSp=donviSp;
		this.soluongSp=soluongSp;
		this.dongiaSp=dongiaSp;
		this.thanhtien=thanhtien;
		this.ptThue=ptThue;
		this.tienThue=tienThue;
		this.sotientt=sotientt;
	}
	
	public SanphamHoadontaichinhObj(){
		this.stt="";
		this.tenSp="";
		this.noiSX="";
		this.donviSp="";
		this.soluongSp="";
		this.dongiaSp="";
		this.thanhtien="";
		
		this.solo="";
		this.maSp="";
		this.ptThue="";
		this.tienThue="";
		this.sotientt="";
		
		this.diengiaiCk="";
	}
	
	
	public String getStt() {
		return stt;
	}

	public void setStt(String stt) {
		this.stt = stt;
	}

	public String getTenSp() {
		return tenSp;
	}

	public void setTenSp(String tenSp) {
		this.tenSp = tenSp;
	}

	public String getNoiSX() {
		return noiSX;
	}

	public void setNoiSX(String noiSX) {
		this.noiSX = noiSX;
	}

	public String getDonviSp() {
		return donviSp;
	}

	public void setDonviSp(String donviSp) {
		this.donviSp = donviSp;
	}

	public String getSoluongSp() {
		return soluongSp;
	}

	public void setSoluongSp(String soluongSp) {
		this.soluongSp = soluongSp;
	}

	public String getDongiaSp() {
		return dongiaSp;
	}

	public void setDongiaSp(String dongiaSp) {
		this.dongiaSp = dongiaSp;
	}

	public String getThanhtien() {
		return thanhtien;
	}

	public void setThanhtien(String thanhtien) {
		this.thanhtien = thanhtien;
	}
	
	public String getSolo() {
		return solo;
	}

	public void setSolo(String solo) {
		this.solo = solo;
	}

	public String getMaSp() {
		return maSp;
	}

	public void setMaSp(String maSp) {
		this.maSp = maSp;
	}

	public String getPtThue() {
		return ptThue;
	}

	public void setPtThue(String ptThue) {
		this.ptThue = ptThue;
	}

	public String getTienThue() {
		return tienThue;
	}

	public void setTienThue(String tienThue) {
		this.tienThue = tienThue;
	}

	public String getSotientt() {
		return sotientt;
	}

	public void setSotientt(String sotientt) {
		this.sotientt = sotientt;
	}

	public String getNgaySp() {
		return ngaySp;
	}

	public void setNgaySp(String ngaySp) {
		this.ngaySp = ngaySp;
	}

	public String getDiengiaiCk() {
		return diengiaiCk;
	}

	public void setDiengiaiCk(String diengiaiCk) {
		this.diengiaiCk = diengiaiCk;
	}
}
