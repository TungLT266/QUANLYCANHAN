package geso.traphaco.erp.beans.phieuxuatkho;

import java.util.List;

public interface ISanpham
{
	public String getId();
	public void setId(String id);
	public String getMa();
	public void setMa(String masp);
	public String getDiengiai();
	public void setDiengiai(String diengiai);
	
	//Xuat kho nhieu lan
	public String getSoluongTotal();
	public void setSoluongTotal(String soluongTotal);
	public String getSoluongDaXuat();
	public void setSoluongDaXuat(String soluongDaXuat);
	
	public String getSoluong();
	public void setSoluong(String soluong);
	
	
	public String getSolo();
	public void setSolo(String solo);
	
	public boolean getIsBean();
	public void setIsBean(boolean isbean);
	
	public String getDVT();
	public void setDVT(String dvt);
	public String getQuycach();
	public void setQuycach(String quycach);
	public String getThung();
	public void setThung(String thung);
	public String getLe();
	public void setLe(String le);
	public String getTrongluong();
	public void setTrongluong(String trongluong);
	public String getThetich();
	public void setThetich(String thetich);
	
	public List<ISpDetail> getSpDetailList();
	public void setSpDetailList(List<ISpDetail> spDetailList);
	public void setTen(String sPTen);
	public void setDonViTinh(String sPTrongluong);
	public void setSoluongYC(String string);
	public void setSoluongNhan(String string);
	public String getTen();
	public String getGhiChu();
	public String getSoluongYC();
}
