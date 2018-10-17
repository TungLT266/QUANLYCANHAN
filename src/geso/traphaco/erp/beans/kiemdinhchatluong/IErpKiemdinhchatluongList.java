package geso.traphaco.erp.beans.kiemdinhchatluong;

import geso.traphaco.center.util.*
import geso.traphaco.center.util.*

import java.io.Serializable;
import java.sql.ResultSet;

public interface IErpKiemdinhchatluongList extends Serializable, IPhan_Trang

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
	
	public void init(String query);
	public void DbClose();
	
}
