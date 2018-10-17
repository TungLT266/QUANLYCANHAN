package geso.traphaco.erp.beans.phieuthanhtoan.imp;

import geso.traphaco.erp.beans.phieuthanhtoan.INhacungcap;


public class Nhacungcap implements INhacungcap
{
	String NccId;
	String NccMa;
	String NccTen;

	public Nhacungcap(String nccId, String nccMa, String nccTen)
	{
		this.NccId = nccId;
		this.NccMa = nccMa;
		this.NccTen = nccTen;
	}

	public String getNccId() 
	{
		return this.NccId;
	}

	public void setNccId(String nccId)
	{
		this.NccId = nccId;
	}

	public String getNccMa() 
	{
		return this.NccMa;
	}

	public void setNccMa(String nccMa) 
	{
		this.NccMa = nccMa;
	}

	public String getNccTen() 
	{
		return this.NccTen;
	}

	public void setNccTen(String nccTen) 
	{
		this.NccTen = nccTen;
	}
	

	
	
	



}
