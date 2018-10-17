package geso.traphaco.erp.beans.yeucauchuyenkho.imp;

import geso.traphaco.erp.beans.yeucauchuyenkho.ILenhsanxuat;

public class Lenhsanxuat implements ILenhsanxuat 
{
	String id;
	String ngaybatdau;
	
	public Lenhsanxuat()
	{
		this.id = "";
		this.ngaybatdau = "";
	}
	
	public Lenhsanxuat(String id, String ngaybatdau)
	{
		this.id = id;
		this.ngaybatdau = ngaybatdau;
	}
	
	public String getId() 
	{
		return this.id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getNgaybatdau() 
	{
		return this.ngaybatdau;
	}

	public void setNgaybatdau(String ngaybatdau) 
	{
		this.ngaybatdau = ngaybatdau;
	}

}
