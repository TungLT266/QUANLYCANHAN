package geso.traphaco.erp.beans.phieuxuatkhoTSCD.imp;

import geso.traphaco.erp.beans.phieuxuatkhoTSCD.ISpDetail;

public class SpDetail implements ISpDetail
{
	String id;
	String ma;
	String ten;
	String solo;
	String soluong;
	String khu;
	String vitri;
	String vitriId;
	String dvt;

	public SpDetail()
	{
		this.id = "";
		this.ma = "";
		this.ten = "";
		this.solo = "";
		this.soluong = "";
		this.khu = "";
		this.vitri = "";
		this.vitriId = "";
		this.dvt = "";
	}

	public SpDetail(String ma, String solo, String soluong, String khu, String vitri, String vitriId)
	{
		this.ma = ma;
		this.solo = solo;
		this.soluong = soluong;
		this.khu = khu;
		this.vitri = vitri;
		this.vitriId = vitriId;
	}

	public SpDetail(String id, String ma, String solo, String soluong, String khu, String vitri, String vitriId)
	{
		this.id = id;
		this.ma = ma;
		this.solo = solo;
		this.soluong = soluong;
		this.khu = khu;
		this.vitri = vitri;
		this.vitriId = vitriId;
	}
	public SpDetail(String ma, String solo, String soluong,String donvi)
	{
		this.ma = ma;
		this.solo = solo;
		this.soluong = soluong;
		this.dvt=donvi;
	}
	public String getMa()
	{
		return this.ma;
	}

	public void setMa(String masp)
	{
		this.ma = masp;
	}

	public String getSolo()
	{
		return this.solo;
	}

	public void setSolo(String solo)
	{
		this.solo = solo;
	}

	public String getKhu()
	{
		return this.khu;
	}

	public void setKhu(String khu)
	{
		this.khu = khu;
	}

	public String getVitri()
	{
		return this.vitri;
	}

	public void setVitri(String vitri)
	{
		this.vitri = vitri;
	}

	public String getSoluong()
	{
		return this.soluong;
	}

	public void setSoluong(String soluong)
	{
		this.soluong = soluong;
	}

	public String getVitriId()
	{
		return this.vitriId;
	}

	public void setVitriId(String vitriId)
	{
		this.vitriId = vitriId;
	}

	public String getId()
	{

		return this.id;
	}

	public void setId(String id)
	{
		this.id = id;

	}

	public String getDvt()
	{

		return this.dvt;
	}

	public void setDvt(String dvt)
	{
		this.dvt = dvt;

	}

	public String getTen()
	{

		return this.ten;
	}

	public void setTen(String ten)
	{
		this.ten = ten;

	}

}
