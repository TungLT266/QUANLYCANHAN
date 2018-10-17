package geso.traphaco.distributor.beans.hopdong.imp;

import geso.traphaco.distributor.beans.hopdong.ISanpham;

public class Sanpham implements ISanpham
{
	private static final long serialVersionUID = 1L;
	
	String id;
	String ma;
	String ten;
	String donvi;
	String soluong;
	String dongia;
	String chietkhau;
	String thueVAT;
		
	public Sanpham()
	{
		this.id = "";
		this.ma = "";
		this.ten = "";
		this.donvi = "";
		this.soluong = "";
		this.dongia = "";
		this.chietkhau = "";
		this.thueVAT = "";
	}

	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String getMa() {
		return this.ma;
	}

	@Override
	public void setMa(String ma) {
		this.ma = ma;
	}

	@Override
	public String getTen() {
		return this.ten;
	}

	@Override
	public void setTen(String ten) {
		this.ten = ten;
	}

	@Override
	public String getDonvi() {
		return this.donvi;
	}

	@Override
	public void setDonvi(String donvi) {
		this.donvi = donvi;
	}

	@Override
	public String getSoluong() {
		return this.soluong;
	}

	@Override
	public void setSoluong(String soluong) {
		this.soluong = soluong;
	}

	@Override
	public String getDongia() {
		return this.dongia;
	}

	@Override
	public void setDongia(String dongia) {
		this.dongia = dongia;
	}

	@Override
	public String getChietkhau() {
		return this.chietkhau;
	}

	@Override
	public void setChietkhau(String chietkhau) {
		this.chietkhau = chietkhau;
	}

	@Override
	public String getThueVAT() {
		return this.thueVAT;
	}

	@Override
	public void setThueVAT(String thueVAT) {
		this.thueVAT = thueVAT;
	}
		
}
