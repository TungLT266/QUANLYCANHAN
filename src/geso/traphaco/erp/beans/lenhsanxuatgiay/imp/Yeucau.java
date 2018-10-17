package geso.traphaco.erp.beans.lenhsanxuatgiay.imp;

import java.util.ArrayList;
import java.util.List;

import geso.traphaco.erp.beans.lenhsanxuatgiay.IYeucau;
import geso.traphaco.erp.beans.phieuxuatkho.ISpDetail;

public class Yeucau implements IYeucau 
{
	String id;
	String ma;
	String ten;
	String soluongYC;
	String soluongDN;
	String soluongNhan;
	String soluongdachuyen;
	//Chuyen kho
	String tonhientai;
	String solo;
	String vitriXuat;
	String vitriNhan;
	String donvi = "";
	
	String soluongchuyen;
	String ghichu;
	
	
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
		
		this.soluongchuyen="";
		this.ghichu="";
		
		this.tonhientai = "";
		this.solo = "";
		this.vitriNhan = "";
		this.vitriXuat = "";
		
		this.spDetailList = new ArrayList<ISpDetail>();
		this.spDetail2List = new ArrayList<ISpDetail>();
	}
	
	public Yeucau(String id, String ma, String ten, String soluongYC, String soluongDN, String soluongN, String soluongchuyen)
	{
		this.id = id;
		this.ma = ma;
		this.ten = ten;
		this.soluongYC = soluongYC;
		this.soluongDN = soluongDN;
		this.soluongNhan = soluongN;
		this.soluongchuyen = soluongchuyen;
		
		
		this.tonhientai = "";
		this.solo = "";
		this.vitriNhan = "";
		this.vitriXuat = "";
		
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
	public String getSoluongdachuyen() {
		return soluongdachuyen;
	}

	@Override
	public void setSoluongdachuyen(String Soluongdachuyen) {
		this.soluongdachuyen=Soluongdachuyen;
	}

	@Override
	public String getDonVi() {
		return this.donvi;
	}

	@Override
	public void setDonVi(String donvi) {
		this.donvi = donvi;
	}


	public String getSoLuongChuyen() {

		return this.soluongchuyen;
	}


	public void setSoLuongChuyen(String soluongchuyen) {
		this.soluongchuyen = soluongchuyen;
	}


	public String getGhiChu() {
		
		return this.ghichu;
	}


	public void setGhiChu(String ghichu) {
		this.ghichu = ghichu;
		
	}
	
	
}
