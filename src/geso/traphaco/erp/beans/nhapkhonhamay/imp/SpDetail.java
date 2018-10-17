package geso.traphaco.erp.beans.nhapkhonhamay.imp;

import geso.traphaco.erp.beans.nhapkhonhamay.ISpDetail;

public class SpDetail implements ISpDetail 
{
	String ma;
	String solo;
	String soluong;
	String ngaysx;
	String ngayhh;
	String KhuId;
	String NgayNhapkho="";
	double CpLuuKho=0;
	double CpCapDong=0;
	double Dongia=0;
	public SpDetail()
	{
		this.ma = "";
		this.solo = "";
		this.soluong = "";
		this.ngaysx = "";
		this.ngayhh = "";
		this.KhuId="";
	}
	
	public SpDetail(String ma, String solo, String soluong, String ngaysx, String ngayhh)
	{
		this.ma = ma;
		this.solo = solo;
		this.soluong = soluong;
		this.ngaysx = ngaysx;
		this.ngayhh = ngayhh;
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

	public String getSoluong()
	{
		return this.soluong;
	}

	public void setSoluong(String soluong)
	{
		this.soluong = soluong;
	}

	public String getNgaySx() 
	{
		return this.ngaysx;
	}

	public void setNgaySx(String ngaysx) 
	{
		this.ngaysx = ngaysx;
	}

	public String getNgayHethan() 
	{
		return this.ngayhh;
	}

	public void setNgayHethan(String ngayhh) 
	{
		this.ngayhh = ngayhh;
	}

	@Override
	public String getkhuid() {
		// TODO Auto-generated method stub
		return this.KhuId;
	}

	@Override
	public void setkhuid(String khuid) {
		// TODO Auto-generated method stub
		this.KhuId=khuid;
	}

	@Override
	public String getNgaynhapkho() {
		// TODO Auto-generated method stub
		return this.NgayNhapkho;
	}

	@Override
	public void setNgaynhapkho(String _Ngaynhapkho) {
		// TODO Auto-generated method stub
		this.NgayNhapkho=_Ngaynhapkho;
	}

	@Override
	public double getCPLukho() {
		// TODO Auto-generated method stub
		return this.CpLuuKho;
	}

	@Override
	public void setCPLukho(double CPLukho) {
		// TODO Auto-generated method stub
		this.CpLuuKho=CPLukho;
	}

	@Override
	public double getCapdong() {
		// TODO Auto-generated method stub
		return this.CpCapDong;
	}

	@Override
	public void setCapdong(double Capdong) {
		// TODO Auto-generated method stub
		this.CpCapDong=Capdong;
	}

	@Override
	public double getDongia() {
		// TODO Auto-generated method stub
		return this.Dongia;
	}

	@Override
	public void setDongia(double _Dongia) {
		// TODO Auto-generated method stub
		this.Dongia=_Dongia;
	}

	

}
