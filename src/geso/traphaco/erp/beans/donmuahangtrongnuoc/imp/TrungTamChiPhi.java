package geso.traphaco.erp.beans.donmuahangtrongnuoc.imp;

import geso.traphaco.erp.beans.donmuahangtrongnuoc.ITrungTamChiPhi;

public class TrungTamChiPhi implements ITrungTamChiPhi
{
	String Id;
	String Ten;
	String MaChiPhi;

	public String getId()
	{

		return this.Id;
	}

	public void setId(String id)
	{
		this.Id = id;
	}

	public String getTen()
	{

		return this.Ten;
	}

	public void setTen(String ten)
	{
		this.Ten = ten;
	}

	public void setMaChiPhi(String machiphi)
	{
		this.MaChiPhi = machiphi;
	}

	public String getMaChiPhi()
	{

		return this.MaChiPhi;
	}

	public TrungTamChiPhi()
	{
		this.Id = "";
		this.MaChiPhi = "";
		this.Ten = "";
	}
}
