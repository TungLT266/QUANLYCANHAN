package geso.traphaco.erp.beans.hoadonkhacncc.imp;

import geso.traphaco.erp.beans.hoadonkhacncc.ISanPhamPhanBo;

public class SanPhamPhanBo implements ISanPhamPhanBo
{
	String spId;
	String spMa;
	String spTen;
	String chon;
	
	public SanPhamPhanBo()
	{
		this.spId = "";
		this.spMa = "";
		this.spTen = "";
		this.chon = "0";
	}
	
	public SanPhamPhanBo(String spId, String spMa, String spTen, String chon)
	{
		this.spId = spId;
		this.spMa = spMa;
		this.spTen = spTen;
		this.chon = chon;
	}
	

	public String getSpId() 
	{
		return this.spId;
	}

	public void setSpId(String spId) 
	{
		this.spId = spId;
	}

	public String getSpMa() 
	{
		return this.spMa;
	}

	public void setSpMa(String spMa) 
	{
		this.spMa = spMa;
	}

	public String getSpTen() 
	{
		return this.spTen;
	}

	public void setSpTen(String spTen) 
	{
		this.spTen = spTen;
	}

	public String getChon() 
	{
		return this.chon;
	}

	public void setChon(String chon) 
	{
		this.chon = chon;
	}
	
	
}
