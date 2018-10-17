package geso.traphaco.erp.beans.nhapkho.giay.imp;

import java.util.List;

import geso.traphaco.erp.beans.nhapkho.giay.ISanphamCon;

public class SanphamCon implements ISanphamCon 
{
	String ma;
	String soluong;
	String khu;
	String vitri;
	
	public SanphamCon()
	{
		this.ma = "";
		this.soluong = "";
		this.khu = "";
		this.vitri = "";
	}
	
	public SanphamCon(String ma, String soluong, String khu, String vitri)
	{
		this.ma = ma;
		this.soluong = soluong;
		this.khu = khu;
		this.vitri = vitri;
	}
	
	public String getMa()
	{
		return this.ma;
	}

	public void setMa(String masp) 
	{
		this.ma = masp;
	}

	public String getSoluong() 
	{
		return this.soluong;
	}

	public void setSoluong(String soluong) 
	{
		this.soluong = soluong;
	}

	public String getKhu()
	{
		return this.khu;
	}

	public void setKhu(String khu)
	{
		this.khu = khu;
	}

	public String getVitri()
	{
		return this.vitri;
	}

	public void setVitri(String vitri)
	{
		this.vitri = vitri;
	}
	
}
