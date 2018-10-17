package geso.traphaco.erp.beans.lenhsanxuat;

import geso.traphaco.erp.beans.phieuxuatkho.ISpDetail;

import java.util.List;

public interface IYeucau 
{
	public String getId();
	public void setId(String id);
	public String getMa();
	public void setMa(String masp);
	public String getTen();
	public void setTen(String tensp);
	public String getQuyCach();
	public void setQuyCach(String quycach);
	public String getNguonGoc();
	public void setNguonGoc(String nguongoc);
	
	public String getSoluongYC();
	public void setSoluongYC(String soluongYC);
	
	public String getSoluongchuyen();
	public void setSoluongchuyen(String soluongchuyen);
 
	public String getSoluongDaNhan();
	public void setSoluongDaNhan(String danhan);
	public String getSoluongNhan();
	public void setSoluongNhan(String nhan);
	
	public double getDongia();
	public void setDongia(double Dongia);
	
	public void setDonViTinh(String donViTinh);
	public String getDonViTinh();
	
	
	
	//Them phan chuyen kho NL
	public String getTonhientai();
	public void setTonhientai(String tonkho);
	public String getSolo();
	public void setSolo(String solo);
	public String getVitriXuat();
	public void setVitriXuat(String vitri);
	public String getVitriNhan();
	public void setVitriNhan(String vitri);
	
	public List<ISpDetail> getSpDetailList();
	public void setSpDetailList(List<ISpDetail> spDetailList);
	
	
	public List<ISpDetail> getSpDetail2List();
	public void setSpDetail2List(List<ISpDetail> spDetailList);
	
	public String getMavattu();
	public void setMavattu(String mavattu);
 
	public String getDiengiai();
	public void setDiengiai(String diengiai);
	public String getGhiChu();
	public String getTrongluong();
	public void setTrongLuong(String trongluong);
	public void setTongSoluongDaXuat(String string);
}
