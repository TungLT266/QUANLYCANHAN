package geso.traphaco.erp.beans.hoadon;

import java.util.List;
public interface IErpHoaDonList 
{
	public String getId();
	public void setId(String id);

	public String getSoxuatkho();
	public void setSoxuatkho(String soxuatkho);
	
	public String getNgayxuathd();
	public void setNgayxuathd(String ngayxuathd);
	
	public String getSohoadon();
	public void setSohoadon(String sohoadon);
	
	public String getTongtienavat();
	public void setTongtienavat(String tongtienavat);
	
	public String getKhachhangId();
	public void setKhachhangId(String khachhangId);
	
	public String getKhachhangTen();
	public void setKhachhangTen(String khachhangTen);
	
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public String getHoantat();
	public void setHoantat(String hoantat);
	
	public String getNgaytao();
	public void setNgaytao(String ngaytao);
	public String getNgaysua();
	public void setNgaysua(String ngaysua);
	
	public String getNguoitao();
	public void setNguoitao(String nguoitao);
	public String getNguoisua();
	public void setNguoisua(String nguoisua);
	
	public List<IHdDetail> getHdDetailList();
	public void setHdDetailList(List<IHdDetail> hdDetailList);
	
}
