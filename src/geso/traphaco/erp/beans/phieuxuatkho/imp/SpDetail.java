package geso.traphaco.erp.beans.phieuxuatkho.imp;

import java.sql.ResultSet;

import geso.traphaco.erp.beans.phieuxuatkho.ISpDetail;

public class SpDetail implements ISpDetail
{
	String id;
	String ma;
	String ten;
	String solo;
	String soluong;
	String soluongton;
	String khu;
	String vitri;
	String vitriId;
	String dvt;
	String haohutsp;
	double trongluong = 0;
	double thetich = 0;
	double haohut=0;
	double dongia=0;
	
	String ngaysanxuat;
	String ngayhethan;
	private String khuId;
	private String quycach;
	String ngaybatdau="";
	ResultSet rskhu;
	String Xk_Id="";
	String Marq="";
	String MaThung="";
	String Mame="";
	String Ngaynhapkho="";
	String hamam;
	String hamluong;
	String Maphieu="";
	String PhieuEO="";
	String PhieuDinhTinh="";
	
	String MaNsx="";
	String Nsx_fk="";
	
	
	public String getNgaysanxuat() {
		return ngaysanxuat;
	}

	public void setNgaysanxuat(String ngaysanxuat) {
		this.ngaysanxuat = ngaysanxuat;
	}

	public String getNgayhethan() {
		return ngayhethan;
	}

	public void setNgayhethan(String ngayhethan) {
		this.ngayhethan = ngayhethan;
	}

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
		this.soluongton="";
		this.ngayhethan="";
		this.ngaysanxuat="";
		this.haohutsp="";
		this.khuId = "";
		this.haohut=0;
		this.ngaybatdau="";
		  Xk_Id="";
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

	
	public String getSoluongton() {
		
		return this.soluongton;
	}

	
	public void setSoluongton(String soluongton) {
		
		this.soluongton=soluongton;
	}

	
	public double getTrongLuong() {
		return this.trongluong;
	}

	
	public void setTrongLuong(double trongluong) {
		this.trongluong = trongluong;
	}

	
	public double getTheTich() {
		return thetich;
	}

	
	public void setTheTich(double thetich) {
		this.thetich = thetich;
	}

	
	public String getHaoHutSp() {
		
		return this.haohutsp;
	}

	
	public void setHaoHutSp(String haohutsp) {
		this.haohutsp=haohutsp;
	}


	public String getKhuId() {
		
		return this.khuId;
	}


	public void setKhuId(String khuId) {
		this.khuId = khuId;
	}


	public void setQuycach(String string) {
		this.quycach = string;
	}


	public String getQuycach() {
		return this.quycach;
	}


	public double getHaoHut() {
		
		return this.haohut;
	}


	public void setHaoHut(double haohut) {
		
		this.haohut=haohut;
	}

	@Override
	public String getNgaybatdau() {
		// TODO Auto-generated method stub
		return 	this.ngaybatdau;
	}

	@Override
	public void setNgaybatdau(String _ngaybatdau) {
		// TODO Auto-generated method stub
		this.ngaybatdau= _ngaybatdau;
	}

	@Override
	public void setRsKhu(ResultSet rs) {
		// TODO Auto-generated method stub
		this.rskhu= rs;
	}

	@Override
	public ResultSet getRsKhu() {
		// TODO Auto-generated method stub
		return this.rskhu;
	}

	@Override
	public void setXk_Id(String xkId) {
		// TODO Auto-generated method stub
		this.Xk_Id=xkId;
	}

	@Override
	public String getXk_Id() {
		// TODO Auto-generated method stub
		return 	this.Xk_Id;
	}

	@Override
	public double getDongia() {
		// TODO Auto-generated method stub
		return this.dongia;
	}

	@Override
	public void setDongia(double Dongia) {
		// TODO Auto-generated method stub
		this.dongia=Dongia;
	}

	@Override
	public String getMarq() {
		// TODO Auto-generated method stub
		return this.Marq;
	}

	@Override
	public void setMarq(String Marq) {
		// TODO Auto-generated method stub
		this.Marq=Marq;
	}

	@Override
	public String getMathung() {
		// TODO Auto-generated method stub
		return this.MaThung;
	}

	@Override
	public void setMathung(String mathung) {
		// TODO Auto-generated method stub
		this.MaThung=mathung;
	}

	@Override
	public String getNgaynhapkho() {
		// TODO Auto-generated method stub
		return this.Ngaynhapkho;
	}

	@Override
	public void setNgaynhapkho(String Ngaynhapkho) {
		// TODO Auto-generated method stub
		this.Ngaynhapkho=Ngaynhapkho;
	}

	@Override
	public String getMame() {
		// TODO Auto-generated method stub
		return this.Mame;
	}

	@Override
	public void setMame(String mame) {
		// TODO Auto-generated method stub
		this.Mame=mame;
	}

	@Override
	public String getHamluong() {
		// TODO Auto-generated method stub
		return this.hamluong;
	}

	@Override
	public void setHamluong(String Hamluong) {
		// TODO Auto-generated method stub
		this.hamluong=Hamluong;
	}

	@Override
	public String getHamam() {
		// TODO Auto-generated method stub
		return this.hamam;
	}

	@Override
	public void setHamam(String Hamam) {
		// TODO Auto-generated method stub
		this.hamam=Hamam;
	}

	@Override
	public void setMaphieu(String maphieu) {
		// TODO Auto-generated method stub
		this.Maphieu=maphieu;
	}

	@Override
	public String getMaphieu() {
		// TODO Auto-generated method stub
		return this.Maphieu;
	}

	@Override
	public void setPHIEUEO(String PHIEUEO) {
		// TODO Auto-generated method stub
		 this.PhieuEO=PHIEUEO;
	}

	@Override
	public String getPHIEUEO() {
		// TODO Auto-generated method stub
		return this.PhieuEO;
	}

	@Override
	public void setMAPHIEUDINHTINH(String MAPHIEUDINHTINH) {
		// TODO Auto-generated method stub
		this.PhieuDinhTinh=MAPHIEUDINHTINH;
	}

	@Override
	public String getMAPHIEUDINHTINH() {
		// TODO Auto-generated method stub
		return this.PhieuDinhTinh;
	}

	@Override
	public String getMaNSX() {
		// TODO Auto-generated method stub
		return this.MaNsx;
	}

	@Override
	public void setMaNSX(String MaNSX) {
		// TODO Auto-generated method stub
		this.MaNsx =MaNSX;
	}

	@Override
	public String getNSX_FK() {
		// TODO Auto-generated method stub
		return this.Nsx_fk;
	}

	@Override
	public void setNSX_FK(String NSX_FK) {
		// TODO Auto-generated method stub
		 this.Nsx_fk =NSX_FK;
	}
	
}
