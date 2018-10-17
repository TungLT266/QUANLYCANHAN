package geso.traphaco.erp.beans.nhanhangnhapkhau.imp;

import java.util.List;

import geso.traphaco.erp.beans.nhanhangnhapkhau.ISpDetail;

public class SpDetail implements ISpDetail 
{
	String ma;
	String solo;
	// luu ma thung cua lo nao
	String sothung;
	String soluong;
	String soluongmax;
	String ngaysx;
	String ngayhh;
	String KhuId;
	String NgayNhapkho="";
	double CpLuuKho=0;
	double CpCapDong=0;
	double Dongia=0;
	String dongialo;
	String NSXTen;
	String NSXid;
	String marrquet;
	public SpDetail()
	{
		this.ma = "";
		this.solo = "";
		this.soluong = "";
		this.ngaysx = "";
		this.ngayhh = "";
		this.KhuId="";
		this.soluongmax = "";
		this.dongialo = "";
		this.sothung="";
		this.marrquet = "";
		this.NSXid = "";
		this.NSXTen = "";
	}
	
	public SpDetail(String ma, String solo,String sothung, String soluong, String ngaysx, String ngayhh)
	{
		this.marrquet = "";
		this.NSXid = "";
		this.NSXTen = "";
		this.ma = ma;
		this.solo = solo;
		this.sothung=sothung;
		this.soluong = soluong;
		this.ngaysx = ngaysx;
		this.ngayhh = ngayhh;
	}
	public String getNSXTen() {
		return NSXTen;
	}

	public void setNSXTen(String nSXTen) {
		NSXTen = nSXTen;
	}

	public String getNSXid() {
		return NSXid;
	}

	public void setNSXid(String nSXid) {
		NSXid = nSXid;
	}

	public String getMarrquet() {
		return marrquet;
	}

	public void setMarrquet(String marrquet) {
		this.marrquet = marrquet;
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
		return this.KhuId;
	}

	@Override
	public void setkhuid(String khuid) {
		this.KhuId=khuid;
	}

	@Override
	public String getNgaynhapkho() {
		return this.NgayNhapkho;
	}

	@Override
	public void setNgaynhapkho(String _Ngaynhapkho) {
		this.NgayNhapkho=_Ngaynhapkho;
	}

	@Override
	public double getCPLukho() {
		return this.CpLuuKho;
	}

	@Override
	public void setCPLukho(double CPLukho) {
		this.CpLuuKho=CPLukho;
	}

	@Override
	public double getCapdong() {
		return this.CpCapDong;
	}

	@Override
	public void setCapdong(double Capdong) {
		this.CpCapDong=Capdong;
	}

	@Override
	public double getDongia() {
		return this.Dongia;
	}

	@Override
	public void setDongia(double _Dongia) {
		this.Dongia=_Dongia;
	}

	@Override
	public String getSoluongmax() {
		return this.soluongmax;
	}

	@Override
	public void setSoluongmax(String soluongmax) {
		this.soluongmax = soluongmax;
	}

	@Override
	public String getDongiaLo() {
		return this.dongialo;
	}

	@Override
	public void setDongiaLo(String dongialo) {
		this.dongialo = dongialo;
	}

	@Override
	public String getSothung() {
		// TODO Auto-generated method stub
		return this.sothung;
	}

	@Override
	public void setSothung(String sothung) {
		// TODO Auto-generated method stub
		this.sothung=sothung;
	}


	

}
