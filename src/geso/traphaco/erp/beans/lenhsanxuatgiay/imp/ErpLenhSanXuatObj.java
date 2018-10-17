package geso.traphaco.erp.beans.lenhsanxuatgiay.imp;

public class ErpLenhSanXuatObj {
	
	
	String stt;
	String spMa;
	String spTen;
	String spDonvi;
	String spSoluongDinhmuc;
	String spSoluongThuc;
	String spGhichu;
	String spSoPKN="";
	
	public ErpLenhSanXuatObj () {
		this.stt="";
		this.spMa="";
		this.spTen="";
		this.spDonvi="";
		this.spSoluongDinhmuc="";
		this.spSoluongThuc="";
		this.spGhichu="";
		this.spSoPKN="";
	}
	
	public String getSpSoPKN() {
		return spSoPKN;
	}

	public void setSpSoPKN(String spSoPKN) {
		this.spSoPKN = spSoPKN;
	}

	public ErpLenhSanXuatObj (String stt,String spMa,String spTen,String spDonvi,String spSoPKN, String spSoluongDinhmuc,String spSoluongThuc,String spGhichu) 
	{
		this.stt=stt;
		this.spMa=spMa;
		this.spTen=spTen;
		this.spDonvi=spDonvi;
		this.spSoPKN=spSoPKN;
		this.spSoluongDinhmuc=spSoluongDinhmuc;
		this.spSoluongThuc=spSoluongThuc;
		this.spGhichu=spGhichu;
	}
	
	
	public String getStt() {
		return stt;
	}
	public void setStt(String stt) {
		this.stt = stt;
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
	public String getSpSoluongDinhmuc() {
		return spSoluongDinhmuc;
	}
	public void setSpSoluongDinhmuc(String spSoluongDinhmuc) {
		this.spSoluongDinhmuc = spSoluongDinhmuc;
	}
	public String getSpSoluongThuc() {
		return spSoluongThuc;
	}
	public void setSpSoluongThuc(String spSoluongThuc) {
		this.spSoluongThuc = spSoluongThuc;
	}
	public String getSpGhichu() {
		return spGhichu;
	}
	public void setSpGhichu(String spGhichu) {
		this.spGhichu = spGhichu;
	}
	

}
