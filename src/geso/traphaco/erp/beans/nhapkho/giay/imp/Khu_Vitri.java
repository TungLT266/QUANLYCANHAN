package geso.traphaco.erp.beans.nhapkho.giay.imp;

import geso.traphaco.erp.beans.nhapkho.giay.IKhu_Vitri;

public class Khu_Vitri implements IKhu_Vitri
{
	String ma;
	String ten;
	
	public Khu_Vitri()
	{
		this.ma = "";
		this.ten = "";
	}
	
	public Khu_Vitri(String ma, String ten)
	{
		this.ma = ma;
		this.ten = ten;
	}
	
	public String getMa()
	{
		return this.ma;
	}

	public void setMa(String makhu) 
	{
		this.ma = makhu;
	}

	public String getTen() 
	{
		return this.ten;
	}

	public void setTen(String tenkhu) 
	{
		this.ten = tenkhu;
	}
	
}
