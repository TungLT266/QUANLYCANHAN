package geso.traphaco.center.beans.tinhthanh;

import geso.traphaco.center.util.IPhan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;

public interface ITinhThanhList extends IPhan_Trang, Serializable
{
	public String getUserId();
	public void setUserId(String userId);
	
	/*public ResultSet getTinhthanhRs();
	public void setTinhthanhRs(ResultSet ttRs);
	public String getTinhthanhId();
	public void setTinhthanhId(String ttId);*/
	
	/*public ResultSet getQuanhuyenRs();
	public void setQuanhuyenRs(ResultSet qhRs);
	public String getQuanhuyenId();
	public void setQuanhuyenId(String qhId);*/
	
	public ResultSet getTinhThanhRs();
	public void setTinhThanhRs(ResultSet ttRs);
	
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public String getTen();
	public void setTen(String ten);
	
	public void init(String search);
	public void setMsg(String Msg);
	public String getMsg();
	
	public String getTenqg();
	public void setTenqg(String tenqg);
	
	public void createRS();
	
	public int getCurrentPage();
	public void setCurrentPage(int current);
	
	public int[] getListPages();
	public void setListPages(int[] listPages);
	public int getLastPage();
}

