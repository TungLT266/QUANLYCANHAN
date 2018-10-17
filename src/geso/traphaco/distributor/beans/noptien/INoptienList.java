package geso.traphaco.distributor.beans.noptien;

import geso.traphaco.center.util.IPhan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.List;

public interface INoptienList extends IPhan_Trang, Serializable
{	
	public String getUserId();
	public void setUserId(String userId);
	public String getTen();
	public void setTen(String ten);
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public String getNppId();
	public void setNppId(String nppId);
	public String getNppTen();
	public void setNppTen(String nppTen);
	public String getSitecode();
	public void setSitecode(String sitecode);
	
	//public List<IKhachhang> getKhList();
	//public void setKhList(List<IKhachhang> khlist);
	
	public ResultSet getKhList();
	public void setKhList(ResultSet khlist);
	
	
	
	public void init(String search);
	public void khChuaPhanTuyen(String dk);
	
	public int getCurrentPage();
	public void setCurrentPage(int current);
	
	public int[] getListPages();
	public void setListPages(int[] listPages);
	public int getLastPage();
	public void DBclose();
	public void setMsg(String msg);
	public String getMsg();
	
	public String getDiachi();
	public void setDiachi(String diachi);
	
	public String getTungay();
	public void setTungay(String tungay);
	
	public String getDenngay();
	public void setDenngay(String denngay);
	
	public String getSochungtu();
	public void setSochungtu(String sochungtu);
	
	public String getNgaynop();
	public void setNgaynop(String ngaynop);
	
	public String getNvbhId();
	public void setNvbhId(String nvbhId);
	
	public ResultSet getNvbhRs();
	public void setNvbhRs(ResultSet nvbhRs);
	
	public String getNvgnId();
	public void setNvgnId(String nvgnId);
	
	public ResultSet getNvgnRs();
	public void setNvgnRs(ResultSet nvgnRs);
	
	public String getKhId();
	public void setKhId(String KhId);
	
	public ResultSet getKhRs();
	public void setKhRs(ResultSet KhRs);
	
	
}
