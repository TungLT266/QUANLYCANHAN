package geso.traphaco.center.beans.nhomfocus;


import geso.traphaco.center.util.IPhan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;


public interface INhomfocusKPIList extends Serializable, IPhan_Trang 
{
	public String getUserId();
	public void setUserId(String userId);
	
	public String getId();
	public void setId(String id);
	
	public String getThang();
	public void setThang(String thang);
		
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public String getNam();
	public void setNam(String nam);
	
	public String getDvkd();
	public void setDvkd(String donvikinhdoanh);
	
	public String getKenhbanhang();
	public void setKenhbanhang(String kenhbanhang);
	
	public String getMsg();
	public void setMsg(String msg);
	
	public String getDoituong();
	public void setDoituong(String doituong);
	///
	
	public int getCurrentPage();
	public void setCurrentPage(int current);
	
	public int[] getListPages();
	public void setListPages(int[] listPages);
	
	public ResultSet getDvkdList();
	public void setDvkdList(ResultSet Dvkdlist);
	public ResultSet getKenhbanhangList();
	public void setKenhbanhangList(ResultSet Kenhbanhanglist);
	public ResultSet getNhomfocusList();
	public void setNhomfocusList(ResultSet Kenhbanhanglist);
	///
	public void createRs();	
	public void init(String query);
	public void close();
	
	
}
