package geso.traphaco.erp.beans.tigia.imp;

import geso.traphaco.erp.beans.tigia.ICongTy;

public class CongTy implements ICongTy
{
	String Id, Ten, Ma;

	public CongTy()
	{
		this.Id = "";
		this.Ten = "";
		this.Ma = "";
	}

	public String getId()
	{

		return this.Id;
	}

	public void setId(String Id)
	{
		this.Id = Id;
	}

	public String getTen()
	{

		return this.Ten;
	}

	public void setTen(String Ten)
	{
		this.Ten = Ten;
	}

	public String getMa()
	{

		return this.Ma;
	}

	public void setMa(String Ma)
	{
		this.Ma = Ma;

	}

}
