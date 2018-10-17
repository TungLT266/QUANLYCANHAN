package geso.traphaco.erp.beans.thongtinsanphamgiay.imp;

import geso.traphaco.erp.beans.thongtinsanphamgiay.IBundle;

public class Bundle implements IBundle 
{
	String spId;
	String spMa;
	String spTen;
	String soluong;
	String dongia;
	String isSpChinh;
	String isChon;
	
	public Bundle()
	{
		this.spId = "";
		this.spMa = "";
		this.spTen = "";
		this.soluong = "";
		this.dongia = "";
		this.isSpChinh = "0";
		this.isChon = "0";
	}
	
	public String getSpId() {
		
		return this.spId;
	}

	
	public void setSpId(String spId) {
		
		this.spId = spId;
	}

	
	public String getSoluong() {
		
		return this.soluong;
	}

	
	public void setSoluong(String soluong) {
		
		this.soluong = soluong;
	}

	
	public String getDongia() {
		
		return this.dongia;
	}

	
	public void setDongia(String dongia) {
		
		this.dongia = dongia;
	}

	
	public String getIsSpChinh() {
		
		return this.isSpChinh;
	}

	
	public void setIsSpChinh(String spChinh) {
		
		this.isSpChinh = spChinh;
	}

	
	public String getIsChon() {
		
		return this.isChon;
	}

	
	public void setIsChon(String isChon) {
		
		this.isChon = isChon;
	}

	
	public String getSpMa() {
		
		return this.spMa;
	}

	
	public void setSpMa(String spMa) {
		
		this.spMa = spMa;
	}

	
	public String getSpTen() {
		
		return this.spTen;
	}

	
	public void setSpTen(String spTen) {
		
		this.spTen = spTen;
	}

	
	

}
