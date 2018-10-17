package geso.traphaco.erp.beans.phieuxuatkho.imp;

import java.util.ArrayList;
import java.util.List;

import geso.traphaco.erp.beans.phieuxuatkho.ISpDetail;
import geso.traphaco.erp.beans.phieuxuatkho.ISanpham;

public class Sanpham implements ISanpham
{
	private static final long serialVersionUID = -9217977546733610214L;
	String id;
	String masp;
	String diengiai;
	
	String soluongTotal;
	String soluongDaXuat;
	String soluong;
	String solo;
	
	String donvitinh;
	String quycach;
	String thung;
	String le;
	String thetich;
	String trongluong;
	boolean isBean;
	
	List<ISpDetail> spDetailList;

	public Sanpham()
	{
		this.id = "";
		this.masp = "";
		this.diengiai = "";
		this.solo = "";
		this.soluong = "";
		this.soluongTotal = "";
		this.soluongDaXuat = "";
		
		this.donvitinh = "";
		this.quycach = "";
		this.thung = "";
		this.le = "";
		this.thetich = "";
		this.trongluong = "";
		this.spDetailList = new ArrayList<ISpDetail>();
	}
	
	public Sanpham(String id, String masp, String diengiai, String solo, String soluong)
	{
		this.id = id;
		this.masp = masp;
		this.diengiai = diengiai;
		this.solo = solo;
		this.soluong = soluong;
		
		this.soluongTotal = "";
		this.soluongDaXuat = "";
		this.donvitinh = "";
		this.quycach = "";
		this.thung = "";
		this.le = "";
		this.thetich = "";
		this.trongluong = "";
		this.spDetailList = new ArrayList<ISpDetail>();
	}
	
	public Sanpham(String[] param)
	{
		this.id = param[0];
		this.masp = param[1];
		this.diengiai = param[2];
		this.soluong = param[3];
		this.solo = param[4];
		this.spDetailList = new ArrayList<ISpDetail>();
	}

	public String getMa() 
	{
		return this.masp;
	}

	public void setMa(String masp) 
	{
		this.masp = masp;
	}
	public String getSolo()
	{
		return this.solo;
	}

	public void setSolo(String solo)
	{
		this.solo = solo;
	}

	public String getDiengiai()
	{
		return this.diengiai;
	}

	public void setDiengiai(String diengiai) 
	{
		this.diengiai = diengiai;
	}

	public String getSoluong() 
	{
		return this.soluong;
	}

	public void setSoluong(String soluong) 
	{
		this.soluong = soluong;
	}

	public List<ISpDetail> getSpDetailList() 
	{
		return this.spDetailList;
	}

	public void setSpDetailList(List<ISpDetail> spDetailList) 
	{
		this.spDetailList = spDetailList;
	}

	public String getId() 
	{
		return this.id;
	}

	public void setId(String id) 
	{
		this.id = id;
	}

	public String getDVT() 
	{
		return this.donvitinh;
	}

	public void setDVT(String dvt)
	{
		this.donvitinh = dvt;
	}

	public String getQuycach()
	{
		return this.quycach;
	}

	public void setQuycach(String quycach)
	{
		this.quycach = quycach;
	}

	public String getThung() 
	{
		return this.thung;
	}

	public void setThung(String thung)
	{
		this.thung = thung;
	}

	public String getLe() 
	{
		return this.le;
	}

	public void setLe(String le) 
	{
		this.le = le;
	}

	public String getTrongluong() 
	{
		return this.trongluong;
	}

	public void setTrongluong(String trongluong)
	{
		this.trongluong = trongluong;
	}

	public String getThetich() 
	{
		return this.thetich;
	}

	public void setThetich(String thetich) 
	{
		this.thetich = thetich;
	}

	
	public String getSoluongTotal() {
		
		return this.soluongTotal;
	}

	
	public void setSoluongTotal(String soluongTotal) {
		
		this.soluongTotal = soluongTotal;
	}

	
	public String getSoluongDaXuat() {
		
		return this.soluongDaXuat;
	}

	
	public void setSoluongDaXuat(String soluongDaXuat) {
		
		this.soluongDaXuat = soluongDaXuat;
	}

	@Override
	public boolean getIsBean() {
		// TODO Auto-generated method stub
		return isBean;
	}

	@Override
	public void setIsBean(boolean isbean) {
		this.isBean = isbean;
	}

	@Override
	public void setTen(String sPTen) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setDonViTinh(String sPTrongluong) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSoluongYC(String string) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSoluongNhan(String string) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getTen() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getGhiChu() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSoluongYC() {
		// TODO Auto-generated method stub
		return null;
	}


}
