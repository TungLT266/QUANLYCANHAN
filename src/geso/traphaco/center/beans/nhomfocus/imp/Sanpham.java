package geso.traphaco.center.beans.nhomfocus.imp;

import geso.traphaco.center.beans.nhomfocus.ISanpham;

public class Sanpham implements ISanpham
{
	String idsp,tensp,masp, phantram;
	public String getTensp() {
		
		return this.tensp;
	}

	
	public void setTensp(String tensanpham) 
	{
		this.tensp=	tensanpham;
	}

	
	public String getMasp() {
		
		return this.masp;
	}

	
	public void setMasp(String masanpham) 
	{
		this.masp=	masanpham;
	}

	
	public String getIDsp() 
	{
		
		return this.idsp;
	}

	
	public void setIDsp(String idsanpham) 
	{
		this.idsp=idsanpham;
	}


	
	public String getPhantram() {
		
		return this.phantram;
	}


	
	public void setPhantram(String phantram) {
		
		this.phantram = phantram;
	}
	

}
