package geso.traphaco.erp.beans.kiemdinhchatluong.giay;

import geso.traphaco.center.util.*;

import java.io.Serializable;
import java.sql.ResultSet;

public interface IErpKiemdinhchatluongList  extends IPhan_Trang, Serializable 
{
	public String getUserId();
	public void setUserId(String userId);
	public String getMa();
	public void setMa(String ma); 
	public String getSanpham();
	public void setSanpham(String sanpham);
	public String getTungay();
	public void setTungay(String tungay);
	public String getDenngay();
	public void setDenngay(String denngay);
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public String getNguoiTao();
	public void setNguoiTao(String nguoitao);
	
	public String getNgaySanXuat();
	public void setNgaySanXuat(String ngaysanxuat);
	
	public String getChungtu();
	public void setChungtu(String Chungtu);
	
	public String getSolo();
	public void setSolo(String solo);
	
	public String getSoLSX();
	public void setSoLSX(String soLSX);
	
	public String getCongtyId();
	public void setCongtyId(String congtyId);
	
	public String getDiengiai();
	public void setDiengiai(String diengiai);
	public String getMsg();
	public void setMsg(String msg);
	
	
	public int getCurrentPage();
	public void setCurrentPage(int current);
	public int[] getListPages();
	public void setListPages(int[] listPages);
	
	public ResultSet getKdclRs();
	public void setKdclRs(ResultSet kdclRs);
	
	public ResultSet getNhaMayRs();
	public void setNhaMayRs(ResultSet nhamayRs);
	
	public String getNhaMayId();
	public void setNhaMayId(String nhamayid);
	
	public void init(String query);
	public void DbClose();
	
}
