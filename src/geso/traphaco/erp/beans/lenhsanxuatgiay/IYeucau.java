package geso.traphaco.erp.beans.lenhsanxuatgiay;

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
	public String getDonVi();
	public void setDonVi(String donvi);
	
	public String getSoluongYC();
	public void setSoluongYC(String soluongYC);
 
	public String getSoluongdachuyen();
	public void setSoluongdachuyen(String Soluongdachuyen);
	
	public String getSoluongDaNhan();
	public void setSoluongDaNhan(String danhan);
	public String getSoluongNhan();
	public void setSoluongNhan(String nhan);
	
	public String getSoLuongChuyen();
	public void setSoLuongChuyen(String soluongchuyen);
	public String getGhiChu();
	public void setGhiChu(String ghichu);
	
	
	//Them phan chuyen kho NL
	public String getTonhientai();
	public void setTonhientai(String tonkho);
	public String getSolo();
	public void setSolo(String solo);
	public String getVitriXuat();
	public void setVitriXuat(String vitri);
	public String getVitriNhan();
	public void setVitriNhan(String vitri);
	
	public List<ISpDetail> getSpDetailList(); //-- dùng làm pop- up
	public void setSpDetailList(List<ISpDetail> spDetailList);
	
	
	public List<ISpDetail> getSpDetail2List();
	public void setSpDetail2List(List<ISpDetail> spDetailList);
}
