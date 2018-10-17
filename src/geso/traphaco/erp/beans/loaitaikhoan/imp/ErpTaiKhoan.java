package geso.traphaco.erp.beans.loaitaikhoan.imp;

import geso.traphaco.erp.beans.loaitaikhoan.IErpTaiKhoan;

public class ErpTaiKhoan implements IErpTaiKhoan
{
	String id;
	String Ten;
	String Ma;
	public ErpTaiKhoan()
	{
		this.id="";
		this.Ten="";
		this.Ma="";
	}
	public String getId()
	{
		return this.id;
	}
	public void setId(String id)
	{
		this.id = id;
	}
	public String getTen()
	{
		return this.Ten;
	}
	public void setTen(String ten)
	{
		this.Ten = ten;
	}
	public void setMa(String Ma)
	{
		this.Ma = Ma;
	}
	public String getMa()
	{
		return this.Ma;
	}

}
