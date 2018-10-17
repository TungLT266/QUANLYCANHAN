package geso.traphaco.erp.beans.nhaphangungtra.imp;

import java.util.ArrayList;
import java.util.List;

import geso.traphaco.erp.beans.nhaphangungtra.IYeucau;
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

	@Override
	public String getQuyCach() {
		return this.quycach;
	}

	@Override
	public void setQuyCach(String quycach) {
		this.quycach = quycach;
	}

	@Override
	public String getNguonGoc() {
		return this.nguongoc;
	}

	@Override
	public void setNguonGoc(String nguongoc) {
		this.nguongoc = nguongoc;
	}

	@Override
	public String getSoluongTon() {
		// TODO Auto-generated method stub
		return this.Soluongton;
	}

	@Override
	public void setSoluongTon(String ton) {
		// TODO Auto-generated method stub
		this.Soluongton=ton;
	}


	public String getghichu_CK() {
		// TODO Auto-generated method stub
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

	@Override
	public String getKhoid() {
		// TODO Auto-generated method stub
		return this.KHoid;
	}

	@Override
	public void setKhoid(String khoid) {
		// TODO Auto-generated method stub
		this.KHoid=khoid;
	}
	
	
}
