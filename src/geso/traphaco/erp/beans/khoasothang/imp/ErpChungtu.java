package geso.traphaco.erp.beans.khoasothang.imp;

import geso.traphaco.erp.beans.khoasothang.IErpChungtu;

public class ErpChungtu implements IErpChungtu 
{
	String soct;
	String ngayct;
	String ngaytao;
	String nguoitao;
	String ngaysua;
	String nguoisua;
	
	public ErpChungtu()
	{
		this.soct = "";
		this.ngayct = "";
		this.ngaytao = "";
		this.nguoitao = "";
		this.ngaysua = "";
		this.nguoisua = "";
	}

	public String getSoct() 
	{
		return this.soct;
	}

	public void setSoct(String soct)
	{
		this.soct = soct;
	}

	public String getNgayct() 
	{
		return this.ngayct;
	}

	public void setNgayct(String ngayct)
	{
		this.ngayct = ngayct;
	}

	public String getNgaytao() 
	{
		return this.ngaytao;
	}

	public void setNgaytao(String ngaytao) 
	{
		this.ngaytao = ngaytao;
	}

	public String getNguoitao() 
	{
		return this.nguoitao;
	}

	public void setNguoitao(String nguoitao)
	{
		this.nguoitao = nguoitao;
	}

	public String getNgaysua() 
	{
		return this.ngaysua;
	}

	public void setNgaysua(String ngaysua) 
	{
		this.ngaysua = ngaysua;
	}

	public String getNguoisua()
	{
		return this.nguoisua;
	}

	public void setNguoisua(String nguoisua) 
	{
		this.nguoisua = nguoisua;
	}
}
