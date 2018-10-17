package geso.traphaco.center.beans.thuongdauthung.imp;

import geso.traphaco.center.beans.thuongdauthung.ISanpham;

public class Sanpham implements ISanpham
{
	
	public Sanpham()
	{
		
		this.id = "";
		this.ma = "";
		this.ten = "";
		this.trongso="";
	}
	
	public Sanpham(String id, String ma, String ten)
	{
		super();
		this.id = id;
		this.ma = ma;
		this.ten = ten;
	}
	String id, ma, ten;
	
	public String getId()
	{
		return id;
	}
	
	public void setId(String id)
	{
		this.id = id;
	}
	
	public String getMa()
	{
		return ma;
	}
	public void setMa(String ma)
	{
		this.ma = ma;
	}
	
	public String getTen()
	{
		return ten;
	}
	public void setTen(String ten)
	{
		this.ten = ten;
	}
	
	String trongso;

	public String getTrongso()
	{
		return trongso;
	}

	public void setTrongso(String trongso)
	{
		this.trongso = trongso;
	}
}
