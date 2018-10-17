package geso.traphaco.distributor.beans.ctkhuyenmai;

import geso.traphaco.distributor.beans.dieukienkhuyenmai.IDieukienkhuyenmai;
import geso.traphaco.distributor.beans.trakhuyenmai.ITrakhuyenmai;

import java.util.List;

public interface ICtkhuyenmai 
{
	public String getId();
	public void setId(String id);
	public String getscheme();
	public void setscheme(String scheme);
	public String getKhoId();
	public void setKhoId(String khoId);
	public String getTungay();
	public void setTungay(String tungay);
	public String getDenngay();
	public void setDenngay(String denngay);
	public int getLoaict();
	public void setLoaict(int loaict);
	public float getNgansach();
	public void setNgansach(float ngansach);
	public float getDasudung();
	public void setDasudung(float dasudung);
	
	public int getSoxuatKM_TOIDA();
	public void setSoxuatKM_TOIDA(int soxuatKM_TOIDA);
	public float getSoxuatKM();
	public void setSoxuatKM(float soxuatKM);
	
	public String getDiengiai();
	public void setDiengiai(String diengiai);
	
	public float getCK();
	public void setCK(float ck);
	
	public long getTongTienTheoDKKM();
	public void setTongTienTheoDKKM(long tongtien);
	
	//kiem tra 1 ctkm co dung chung sp hoac thoa ngan sach hay ko
	public boolean getConfirm();
	public void setConfirm(boolean confirm);
	public float checkCtkm(float tonggiatri);
	
	public void setTra_OR(boolean tra_OR);
	public boolean getTra_OR();
	public String getHinhthucPrimary();
	public void setHinhthucPrimary(String hinhthucPrimary);
	
	public List<IDieukienkhuyenmai> getDkkhuyenmai();
	public void setDkkhuyenmai(List<IDieukienkhuyenmai> dkkm);
	public List<ITrakhuyenmai> getTrakhuyenmai();
	public void setTrakhuyenmai(List<ITrakhuyenmai> trakm);
	
	public String getPhanbotheoDH();
	public void setPhanbotheoDH(String phanbotheoDH);
	
	public String getSchemeDungChung();
	public void setSchemeDungChung(String schemeDungchung);
	
	public String getCtkmTLLoaitruId();
	public void setCtkmTLLoaitruId(String ctkmLoaitruId);
	
	public String getGiohang();
	public void setGiohang(String giohang);
	
	public float getTientoithieu();
	public void setTientoithieu(float tientoithieu);
	
	public long getTongGiaTriTraKM();
	public long getTongGiaTriTraKM_TheoTRAKM(String tkmId);
	
}
