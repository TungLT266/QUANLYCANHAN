package geso.traphaco.center.beans.quanhuyen;

import java.io.Serializable;
import java.sql.ResultSet;

import geso.traphaco.center.util.IPhan_Trang;

public interface IQuanHuyenList extends IPhan_Trang, Serializable{
	public String getUserId();
	public void setUserId(String userId);
	
	public ResultSet getTinhthanhRs();
	public void setTinhthanhRs(ResultSet ttRs);
	public String getTinhthanhId();
	public void setTinhthanhId(String ttId);
	
	/*public ResultSet getQuanhuyenRs();
	public void setQuanhuyenRs(ResultSet qhRs);
	public String getQuanhuyenId();
	public void setQuanhuyenId(String qhId);*/
	
	public ResultSet getQuanHuyenRs();
	public void setQuanHuyenRs(ResultSet qhRs);
	
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public void init(String search);
	public void setMsg(String Msg);
	public String getMsg();
	
	public void createRS();
	
	public int getCurrentPage();
	public void setCurrentPage(int current);
	
	public int[] getListPages();
	public void setListPages(int[] listPages);
	public int getLastPage();

}
