package geso.traphaco.erp.beans.danhgianhacc.imp;

import java.util.ArrayList;
import java.util.List;

import geso.traphaco.erp.beans.timnhacc.*;

public class Sanpham implements ISanpham
{
	private static final long serialVersionUID = -9217977546733610214L;
	
	String id;
	String masp;
	String tensp;
	String nhomhang;
	List<INhacungcap> nhacungcap;

	public Sanpham()
	{
		this.id = "";
		this.masp = "";
		this.tensp = "";
		this.nhomhang = "";
		this.nhacungcap = new ArrayList<INhacungcap>();
	}

	public Sanpham(String[] param, List<INhacungcap>nhacungcap)
	{
		this.id = param[0];
		this.masp = param[1];
		this.tensp = param[2];
		this.nhomhang = param[8];
		this.nhacungcap = nhacungcap;
	}

	public Sanpham(String spId, String spMa, String spTen, String nhomhang, List<INhacungcap> nhacungcap)
	{
		this.id = spId;
		this.masp = spMa;
		this.tensp = spTen;
		this.nhomhang = nhomhang;
		this.nhacungcap = nhacungcap;
	}

	public String getId()
	{
		return this.id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getMasanpham()
	{
		return this.masp;
	}

	public void setMasanpham(String masp)
	{
		this.masp = masp;
	}

	public String getTensanpham()
	{
		return this.tensp;
	}

	public void setTensanpham(String tensp)
	{
		this.tensp = tensp;
	}
	public String getNhomhang()
	{
		return this.nhomhang;
	}

	public void setNhomhang(String nhomhang)
	{
		this.nhomhang = nhomhang;
	}

	@Override
	public void setNhacungcap(List<INhacungcap> list) {
		this.nhacungcap = list;
	}

	@Override
	public List<INhacungcap> getNhacungcap() {
		return this.nhacungcap;
	}

}
