package geso.traphaco.erp.beans.buttoantonghop.imp;

import geso.traphaco.erp.beans.buttoantonghop.IErp_TrungTamChiPhi;

public class Erp_TrungTamChiPhi implements IErp_TrungTamChiPhi
{
	String Id, Ten, Ma;

	public Erp_TrungTamChiPhi()
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
