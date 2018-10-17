package geso.traphaco.erp.beans.phieuxuatkhoTH.imp;

import java.sql.ResultSet;

import geso.traphaco.erp.beans.phieuxuatkhoTH.ISpDetail;

public class SpDetail implements ISpDetail
{
	String id;
	String khoChiTietId;
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
	String Hamluong="";
	String Hamam="";
	
	String ngaysanxuat;
	String ngayhethan;
	String ngaynhapkho;
	
	private String khuId;
	private String quycach;
	String ngaybatdau="";
	ResultSet rskhu;
	String Xk_Id="";

	String Maphieu;
	String Maphieudinhtinh;
	String PhieuEO;
	 
	String mathung;
	String mame;
	String idmarquette;
	String mamarquette;
	String idnsx;
	String tennsx;
	public String getKhoChiTietId() {
		return khoChiTietId;
	}

	public void setKhoChiTietId(String khoChiTietId) {
		this.khoChiTietId = khoChiTietId;
	}

	public String getIdmarquette() {
		return idmarquette;
	}

	public void setIdmarquette(String idmarquette) {
		this.idmarquette = idmarquette;
	}

	public String getMamarquette() {
		return mamarquette;
	}

	public void setMamarquette(String mamarquette) {
		this.mamarquette = mamarquette;
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
		this.ngaynhapkho="";
		  Xk_Id="";
		this.mathung = "";
		this.mame = "";
		this.khoChiTietId = "";
		this.idnsx = "";
		this.tennsx = "";
	}

	public SpDetail(String ma, String solo, String soluong, String khu, String vitri, String vitriId)
	{
		this.ma = ma;
		this.solo = solo;
		this.soluong = soluong;
		this.khu = khu;
		this.vitri = vitri;
		this.vitriId = vitriId;
		this.mathung = "";
		this.mame = "";
		this.khoChiTietId = "";
		this.ngaynhapkho="";
		this.idnsx = "";
		this.tennsx = "";
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
		this.mathung = "";
		this.mame = "";
		this.khoChiTietId = "";
		this.ngaynhapkho="";
		this.idnsx = "";
		this.tennsx = "";
	}
	public SpDetail(String ma, String solo, String soluong,String donvi)
	{
		this.ma = ma;
		this.solo = solo;
		this.soluong = soluong;
		this.dvt=donvi;
		this.mathung = "";
		this.mame = "";
		this.khoChiTietId = "";
		this.ngaynhapkho="";
		this.idnsx = "";
		this.tennsx = "";
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
	public String getMaphieu() {
		// TODO Auto-generated method stub
		return this.Maphieu;
	}

	@Override
	public void setMaphieu(String maphieu) {
		// TODO Auto-generated method stub
		this.Maphieu=maphieu;
	}

	@Override
	public String getMaphieudinhtinh() {
		// TODO Auto-generated method stub
		return this.Maphieudinhtinh;
	}

	@Override
	public void setMaphieudinhtinh(String maphieudinhtinh) {
		// TODO Auto-generated method stub
		this.Maphieudinhtinh= maphieudinhtinh;
	}

	@Override
	public String getPhieuEO() {
		// TODO Auto-generated method stub
		return this.PhieuEO;
	}

	@Override
	public void setPhieuEO(String PhieuEO) {
		// TODO Auto-generated method stub
		this.PhieuEO=PhieuEO;
	}

	@Override
	public String getNgaynhapkho() {
		// TODO Auto-generated method stub
		return this.ngaynhapkho;
	}

	@Override
	public void setNgaynhapkho(String ngaynhapkho) {
		// TODO Auto-generated method stub
		this.ngaynhapkho=ngaynhapkho;
	}

	@Override
	public String getHamluong() {
		// TODO Auto-generated method stub
		return this.Hamluong;
	}

	@Override
	public void setHamluong(String hamluong) {
		// TODO Auto-generated method stub
		this.Hamluong=hamluong;
	}

	@Override
	public String getHamam() {
		// TODO Auto-generated method stub
		return this.Hamam;
	}

	@Override
	public void setHamam(String hamam) {
		// TODO Auto-generated method stub
		this.Hamam=hamam;
	}

	public String getIdnsx() {
		return idnsx;
	}

	public void setIdnsx(String idnsx) {
		this.idnsx = idnsx;
	}

	public String getTennsx() {
		return tennsx;
	}

	public void setTennsx(String tennsx) {
		this.tennsx = tennsx;
	}

}
