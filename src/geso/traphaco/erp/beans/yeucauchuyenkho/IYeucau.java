package geso.traphaco.erp.beans.yeucauchuyenkho;

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
	public String getSoluongDaNhan();
	public void setSoluongDaNhan(String danhan);
	public String getSoluongNhan();
	public void setSoluongNhan(String nhan);
	public String getSoluongTon();
	public void setSoluongTon(String ton);
	
	public void setDonViTinh(String donViTinh);
	public String getDonViTinh();
	
	public String getghichu_CK();
	public void setghichu_CK(String ghichu_ck);
	
	public String getLsxId();
	public void setLsxId(String lsxId);
	
	//Them phan chuyen kho NL
	public String getTonhientai();
	public void setTonhientai(String tonkho);
	public String getSolo();
	public void setSolo(String solo);
	public String getVitriXuat();
	public void setVitriXuat(String vitri);
	public String getVitriNhan();
	public void setVitriNhan(String vitri);
	
	public String getKhoid();
	public void setKhoid(String khoid);
	
	public List<ISpDetail> getSpDetailList();
	public void setSpDetailList(List<ISpDetail> spDetailList);
	
	public List<ISpDetail> getSpDetail2List();
	public void setSpDetail2List(List<ISpDetail> spDetailList);
	
	public String getCtkmId();
	public void setCtkmId(String CtkmId);
	
	public String getDongia();
	public void setDongia(String dongia);
	
	public String getDongia2();
	public void setDongia2(String dongia2);
	
	
	public String getDvtinh_id() ;

	public void setDvtinh_id(String dvtinh_id) ;
	
	
}
