package geso.traphaco.erp.beans.yeucauchuyenkho.imp;

import java.util.ArrayList;
import java.util.List;

import geso.traphaco.erp.beans.yeucauchuyenkho.IYeucau;
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
	String Soluongton;
	String donViTinh;
	String ghichu_ck;
	String lsxId;
	String KHoid;
	String ctkmId;
	String dongia;
	String dongia2;
	String dvtinh_id;
	
	
	
	public String getDvtinh_id() {
		return dvtinh_id;
	}

	public void setDvtinh_id(String dvtinh_id) {
		this.dvtinh_id = dvtinh_id;
	}

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
		this.soluongDN = "";
		this.soluongNhan = "";
		
		this.tonhientai = "";
		this.solo = "";
		this.vitriNhan = "";
		this.vitriXuat = "";
		
		this.lsxId = "";
		
		this.spDetailList = new ArrayList<ISpDetail>();
		this.spDetail2List = new ArrayList<ISpDetail>();
		
		this.dongia = "0";
		this.dongia2 = "0";
	}
	
	public Yeucau(String id, String ma, String ten, String soluongYC, String soluongDN, String soluongN)
	{
		this.id = id;
		this.ma = ma;
		this.ten = ten;
		this.soluongYC = soluongYC;
		this.soluongDN = soluongDN;
		this.soluongNhan = soluongN;
		
		
		this.tonhientai = "";
		this.solo = "";
		this.vitriNhan = "";
		this.vitriXuat = "";
		
		this.lsxId = "";
		
		this.spDetailList = new ArrayList<ISpDetail>();
		this.spDetail2List = new ArrayList<ISpDetail>();
		
		this.dongia = "0";
		this.dongia2 = "0";
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

	
	public String getSoluongTon() {
		
		return this.Soluongton;
	}

	
	public void setSoluongTon(String ton) {
		
		this.Soluongton=ton;
	}


	public String getghichu_CK() {
		
		return this.ghichu_ck;
	}

	
	public void setghichu_CK(String ghichu_ck) {
		this.ghichu_ck= ghichu_ck;
		
	}

	public String getLsxId()
	{
		return this.lsxId;
	}

	public void setLsxId(String lsxId)
	{
		this.lsxId = lsxId;
	}

	
	public String getKhoid() {
		
		return this.KHoid;
	}

	
	public void setKhoid(String khoid) {
		
		this.KHoid=khoid;
	}

	
	public String getCtkmId() {
		
		return this.ctkmId;
	}

	
	public void setCtkmId(String _CtkmId) {
		
		this.ctkmId=_CtkmId;
	}

	
	public String getDongia() {
		
		return this.dongia;
	}

	
	public void setDongia(String dongia) {
		
		this.dongia = dongia;
	}
	
	public String getDongia2() {
		
		return this.dongia2;
	}

	public void setDongia2(String dongia2) {
		
		this.dongia2 = dongia2;
	}
	
}
