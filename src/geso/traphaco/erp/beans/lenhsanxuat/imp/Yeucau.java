package geso.traphaco.erp.beans.lenhsanxuat.imp;

import java.util.ArrayList;
import java.util.List;

import geso.traphaco.erp.beans.lenhsanxuat.IYeucau;
import geso.traphaco.erp.beans.phieuxuatkho.ISpDetail;

public class Yeucau implements IYeucau 
{
	String id;
	String ma;
	String ten;
	String soluongYC;
	String soluongDN;
	String soluongNhan;
	String quycach = "", nguongoc = "";
	String mavattu;
	
	String donViTinh;
	
	String TrongLuong;
	String Diengiai;
	String Ghichu;
	String 	soluongtongdaxuat;
	String Soluongchuyen;
	double Dongia=0;
	
	public String getDonViTinh() {
		return donViTinh!=null?donViTinh:"";
	}

	public void setDonViTinh(String donViTinh) {
		this.donViTinh = donViTinh;
	}

	//Chuyen kho
	String tonhientai;
	String solo;
	String vitriXuat;
	String vitriNhan;
	
	List<ISpDetail> spDetailList;
	List<ISpDetail> spDetail2List;

	public Yeucau()
	{
		this.id = "";
		this.ma = "";
		this.ten = "";
		this.soluongYC = "";
		this.Ghichu="";
		this.Diengiai="";
		this.TrongLuong="";
		this.soluongDN = "";
		this.soluongNhan = "";
		this.mavattu ="";
		
		this.tonhientai = "";
		this.solo = "";
		this.vitriNhan = "";
		this.vitriXuat = "";
		this.soluongtongdaxuat="0";
		this.Soluongchuyen="";
		this.Dongia=0;
		
		
		this.spDetailList = new ArrayList<ISpDetail>();
		this.spDetail2List = new ArrayList<ISpDetail>();
	}
	
	public Yeucau(String id, String ma, String mavattu, String ten, String soluongYC, String soluongDN, String soluongN)
	{
		this.id = id;
		this.ma = ma;
		this.ten = ten;
		this.soluongYC = soluongYC;
		this.soluongDN = soluongDN;
		this.soluongNhan = soluongN;
		this.mavattu = mavattu;
		this.Soluongchuyen="";
		this.tonhientai = "";
		this.solo = "";
		this.vitriNhan = "";
		this.vitriXuat = "";
		this.Dongia=0;
		this.spDetailList = new ArrayList<ISpDetail>();
		this.spDetail2List = new ArrayList<ISpDetail>();
	}
	
	public String getId() 
	{
		return this.id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getMa() 
	{
		return this.ma;
	}

	public void setMa(String masp) 
	{
		this.ma = masp;
	}

	public String getSoluongYC()
	{
		return this.soluongYC;
	}

	public void setSoluongYC(String soluongYC)
	{
		this.soluongYC = soluongYC;
	}

	public String getSoluongDaNhan() 
	{
		return this.soluongDN;
	}

	public void setSoluongDaNhan(String danhan) 
	{
		this.soluongDN = danhan;
	}

	public String getSoluongNhan()
	{
		return this.soluongNhan;
	}

	public void setSoluongNhan(String nhan) 
	{
		this.soluongNhan = nhan;	
	}

	public List<ISpDetail> getSpDetailList()
	{
		return this.spDetailList;
	}

	public void setSpDetailList(List<ISpDetail> spDetailList) 
	{
		this.spDetailList = spDetailList;
	}

	public String getTen()
	{
		return this.ten;
	}

	public void setTen(String tensp) 
	{
		this.ten = tensp;
	}

	
	public String getTonhientai() 
	{
		return this.tonhientai;
	}

	public void setTonhientai(String tonkho) 
	{
		this.tonhientai = tonkho;
	}

	public String getSolo() 
	{
		return this.solo;
	}

	public void setSolo(String solo)
	{
		this.solo = solo;
	}

	public String getVitriXuat() 
	{
		return this.vitriXuat;
	}

	public void setVitriXuat(String vitri) 
	{
		this.vitriXuat = vitri;
	}

	public String getVitriNhan()
	{
		return this.vitriNhan;
	}

	public void setVitriNhan(String vitri) 
	{
		this.vitriNhan = vitri;
	}

	public List<ISpDetail> getSpDetail2List() 
	{
		return this.spDetail2List;
	}

	public void setSpDetail2List(List<ISpDetail> spDetailList) 
	{
		this.spDetail2List = spDetailList;
	}

	
	public String getQuyCach() {
		return this.quycach;
	}

	
	public void setQuyCach(String quycach) {
		this.quycach = quycach;
	}

	
	public String getNguonGoc() {
		return this.nguongoc;
	}

	
	public void setNguonGoc(String nguongoc) {
		this.nguongoc = nguongoc;
	}

	public String getMavattu() {
		return mavattu;
	}

	public void setMavattu(String mavattu) {
		this.mavattu = mavattu;
	}

 

	
	public String getDiengiai() {
		
		return this.Diengiai;
	}

 
	
	public String getGhiChu() {
		
		return this.Ghichu;
	}

	
	public String getTrongluong() {
		
		return this.TrongLuong;
	}

	
	public void setTrongLuong(String trongluong) {
		
		this.TrongLuong=trongluong;
	}

	
	public void setDiengiai(String diengiai) {
		
		this.Diengiai=diengiai;
	}

	
	public void setTongSoluongDaXuat(String soluongtongdaxuat) {
		
		this.soluongtongdaxuat=soluongtongdaxuat;
	}

	
	public String getSoluongchuyen() {
		
		return this.Soluongchuyen;
	}

	
	public void setSoluongchuyen(String soluongchuyen) {
		
		this.Soluongchuyen=soluongchuyen;
	}

	
	public double getDongia() {
		
		return this.Dongia;
	}

	
	public void setDongia(double _Dongia) {
		
	this.Dongia=	_Dongia;
	}
 
	
}
