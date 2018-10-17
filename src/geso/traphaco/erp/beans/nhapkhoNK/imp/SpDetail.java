package geso.traphaco.erp.beans.nhapkhoNK.imp;

import geso.traphaco.erp.beans.nhapkhoNK.ISpDetail;

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
	String muahang_fk;
	String mathung;
	String mame;
	String maphieu;
	String khott_fk;
	String hamluong;
	String hamam;
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
		this.muahang_fk = "";
		this.maphieu = "";
		this.mame = "";
		this.mathung = "";
		this.khott_fk = "";
		this.hamam = "0";
		this.hamluong = "100";
		this.marrquet = "";
		this.NSXid = "";
		this.NSXTen = "";
	}
	
	public SpDetail(String ma, String solo, String soluong, String ngaysx, String ngayhh)
	{
		this.ma = ma;
		this.solo = solo;
		this.soluong = soluong;
		this.ngaysx = ngaysx;
		this.ngayhh = ngayhh;
		this.marrquet = "";
		this.NSXid = "";
		this.NSXTen = "";
	}
	
	public String getMuahang_fk() {
		return muahang_fk;
	}

	public void setMuahang_fk(String muahang_fk) {
		this.muahang_fk = muahang_fk;
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

	public String getMathung() {
		return mathung;
	}

	public void setMathung(String mathung) {
		this.mathung = mathung;
	}

	public String getMame() {
		return mame;
	}

	public void setMame(String mame) {
		this.mame = mame;
	}

	public String getMaphieu() {
		return maphieu;
	}

	public void setMaphieu(String maphieu) {
		this.maphieu = maphieu;
	}

	public String getKhott_fk() {
		return khott_fk;
	}

	public void setKhott_fk(String khott_fk) {
		this.khott_fk = khott_fk;
	}

	public String getHamluong() {
		return hamluong;
	}

	public void setHamluong(String hamluong) {
		this.hamluong = hamluong;
	}

	public String getHamam() {
		return hamam;
	}

	public void setHamam(String hamam) {
		this.hamam = hamam;
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

	

}
