package geso.traphaco.erp.beans.phanbomuahang.imp;

import java.util.List;

import geso.traphaco.erp.beans.phanbomuahang.*;

public class Congty implements ICongty
{
	private static final long serialVersionUID = -9217977546733610214L;
	
	String id;
	String mact;
	String tenct;
	String soluongpb;
	double tongtien;
	List<ISanpham> splist;

	public Congty()
	{
		this.id = "";
		this.mact = "";
		this.tenct = "";
		this.tongtien = 0;
		this.soluongpb = "0";
	}

	public Congty(String[] param, List<ISanpham> splist)
	{
		this.id = param[0];
		this.mact = param[1];
		this.tenct = param[2];
		this.tongtien = 0;
		this.splist = splist;
		this.soluongpb = "0";
	}

	public Congty(String ctId, String ctMa, String ctTen)
	{
		this.id = ctId;
		this.mact = ctMa;
		this.tenct = ctTen;
		this.tongtien = 0;
		this.soluongpb = "0";
	}

	public String getId()
	{
		return this.id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getMacongty()
	{
		return this.mact;
	}

	public void setMacongty(String mact)
	{
		this.mact = mact;
	}

	public String getTencongty()
	{
		return this.tenct;
	}

	public void setTencongty(String tenct)
	{
		this.tenct = tenct;
	}

	@Override
	public double getTongtien() {
		// TODO Auto-generated method stub
		return this.tongtien;
	}

	@Override
	public void setTongtien(double tongtien) {
		// TODO Auto-generated method stub
		this.tongtien = tongtien;
	}

	@Override
	public List<ISanpham> getSanphamList() {
		// TODO Auto-generated method stub
		return splist;
	}

	@Override
	public void setSanphamList(List<ISanpham> splist) {
		// TODO Auto-generated method stub
		this.splist = splist;
	}

	@Override
	public String getSoluongpb() {
		// TODO Auto-generated method stub
		return this.soluongpb;
	}

	@Override
	public void setSoluongpb(String soluongpb) {
		// TODO Auto-generated method stub
		this.soluongpb = soluongpb;
	}
}
