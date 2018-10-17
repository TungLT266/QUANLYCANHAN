package geso.traphaco.erp.beans.hoadontrahang.imp;

import geso.traphaco.erp.beans.hoadontrahang.IErpHdTraHang_SanPham;

public class ErpHdTraHang_SanPham implements IErpHdTraHang_SanPham
{

	String Id, Ma, DonViTinh, Ten, DVDL;
	double SoLuong, DonGia, ThanhTien, ptVat;

	ErpHdTraHang_SanPham()
	{
		this.Id = "";
		this.Ma = "";
		this.SoLuong = 0;
		this.DonGia = 0;
		this.ThanhTien = 0;
		this.ptVat = 0;
		this.DonViTinh = "";
		this.DVDL = "";
		this.Ten = "";
	}

	public String getId()
	{

		return this.Id;
	}

	public void setId(String Id)
	{
		this.Id = Id;

	}

	public String getMa()
	{

		return this.Ma;
	}

	public void setMa(String Ma)
	{
		this.Ma = Ma;

	}

	public void setSoLuong(double SoLuong)
	{
		this.SoLuong = SoLuong;

	}

	public double getSoLuong()
	{

		return this.SoLuong;
	}

	public void setDonViTinh(String DonViTinh)
	{
		this.DonViTinh = DonViTinh;

	}

	public String getDonViTinh()
	{

		return this.DonViTinh;
	}

	public void setDonGia(double DonGia)
	{
		this.DonGia = DonGia;

	}

	public double getDonGia()
	{

		return this.DonGia;
	}

	public void setThanhTien(double ThanhTien)
	{
		this.ThanhTien = ThanhTien;

	}

	public double getThanhTien()
	{

		return this.ThanhTien;
	}

	public void setTen(String Ten)
	{
		this.Ten = Ten;

	}

	public String getTen()
	{

		return this.Ten;
	}

	
	public void setDVDL(String DVDL) {
		
		this.DVDL = DVDL;
	}

	
	public String getDVDL() {
		
		return DVDL;
	}

	
	public void setptVat(double ptVat) {
		
		this.ptVat = ptVat;
	}

	
	public double getptVat() {
		
		return this.ptVat;
	}

}
