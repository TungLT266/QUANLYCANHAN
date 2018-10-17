package geso.traphaco.erp.beans.donmuahangtrongnuoc.imp;

import geso.traphaco.erp.beans.donmuahangtrongnuoc.INgaynhan;

public class Ngaynhan implements INgaynhan
{
	String ngay;
	String soluong;
	
	public Ngaynhan()
	{
		this.ngay = "";
		this.soluong = "";
	}
	
	public Ngaynhan(String ngay, String soluong)
	{
		this.ngay = ngay;
		this.soluong = soluong;
	}
	
	public String getNgay() 
	{
		return this.ngay;
	}
	
	public void setNgay(String ngay) 
	{
		this.ngay = ngay;
	}
	
	public String getSoluong() 
	{
		return this.soluong;
	}
	
	public void setSoluong(String soluong) 
	{
		this.soluong = soluong;
	}
	
	
}
