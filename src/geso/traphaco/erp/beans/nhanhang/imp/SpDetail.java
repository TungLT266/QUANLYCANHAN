package geso.traphaco.erp.beans.nhanhang.imp;

import java.util.List;

import geso.traphaco.erp.beans.nhanhang.ISpDetail;

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
	String PhieuEO;
	String Maphieu;
	String Maphieudinhtinh;
	String Mame;
	String Hamluong;
	String Hamam;
	String MARQ;
	String NsxId="";
	
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
		  PhieuEO="";
		  Maphieu="";
		  Maphieudinhtinh="";
		  Mame="";
		  Hamluong="";
		  Hamam="";
		  MARQ="";
	}
	
	public SpDetail(String ma, String solo,String sothung, String soluong, String ngaysx, String ngayhh)
	{
		this.ma = ma;
		this.solo = solo;
		this.sothung=sothung;
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

	@Override
	public String getMame() {
		// TODO Auto-generated method stub
		return this.Mame;
	}

	@Override
	public void setMame(String mame) {
		// TODO Auto-generated method stub
		this.Mame= mame;
	}

	@Override
	public String getMaphieu() {
		// TODO Auto-generated method stub
		return this.Maphieu;
	}

	@Override
	public void setMaphieu(String maphieu) {
		// TODO Auto-generated method stub
		 this.Maphieu =maphieu;
	}

	@Override
	public String getMaphieudinhtinh() {
		// TODO Auto-generated method stub
		return this.Maphieudinhtinh;
	}

	@Override
	public void setMaphieudinhtinh(String maphieudinhtinh) {
		// TODO Auto-generated method stub
		this.Maphieudinhtinh=maphieudinhtinh;
	}

	@Override
	public String getPhieuEO() {
		// TODO Auto-generated method stub
		return this.PhieuEO;
	}

	@Override
	public void setPhieuEO(String PhieuEO) {
		// TODO Auto-generated method stub
		this.PhieuEO= PhieuEO;
	}

	@Override
	public String getHamluong() {
		// TODO Auto-generated method stub
		return this.Hamluong;
	}

	@Override
	public void setHamluong(String Hamluong) {
		// TODO Auto-generated method stub
		this.Hamluong =Hamluong;
	}

	@Override
	public String getHamAm() {
		// TODO Auto-generated method stub
		return this.Hamam;
	}

	@Override
	public void setHamAm(String HamAm) {
		// TODO Auto-generated method stub
		this.Hamam=HamAm;
	}

	@Override
	public String getMarq() {
		// TODO Auto-generated method stub
		return this.MARQ;
	}

	@Override
	public void setMarq(String Marq) {
		// TODO Auto-generated method stub
		this.MARQ=Marq;
	}

	@Override
	public String getNsxId() {
		// TODO Auto-generated method stub
		return NsxId;
	}

	@Override
	public void setNsxId(String nsxid) {
		// TODO Auto-generated method stub
		NsxId=nsxid;
	}


	

}
