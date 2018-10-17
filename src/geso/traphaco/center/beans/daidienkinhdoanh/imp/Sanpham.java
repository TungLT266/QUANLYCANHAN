package geso.traphaco.center.beans.daidienkinhdoanh.imp;

import java.util.ArrayList;
import java.util.List;

import geso.traphaco.center.beans.daidienkinhdoanh.*;

public class Sanpham implements ISanpham
{
	private static final long serialVersionUID = -9217977546733610214L;
	
	String id;
	String masp;
	String tensp;
	String doanhso;

	public Sanpham()
	{
		this.id = "";
		this.masp = "";
		this.tensp = "";
		this.doanhso = "";
	}

	public Sanpham(String[] param)
	{
		this.id = param[0];
		this.masp = param[1];
		this.tensp = param[2];
	}

	public Sanpham(String spId, String spMa, String spTen, String nhomhang)
	{
		this.id = spId;
		this.masp = spMa;
		this.tensp = spTen;
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

	@Override
	public String getDoanhso() {
		// TODO Auto-generated method stub
		return this.doanhso;
	}

	@Override
	public void setDoanhso(String doanhso) {
		// TODO Auto-generated method stub
		this.doanhso = doanhso;
	}

}
