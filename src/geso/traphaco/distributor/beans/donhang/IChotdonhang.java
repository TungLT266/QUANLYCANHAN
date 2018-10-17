package geso.traphaco.distributor.beans.donhang;

import geso.traphaco.center.util.IPhanTrang;
import geso.traphaco.center.util.IPhan_Trang;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;

import javax.servlet.http.HttpServletRequest;

public interface IChotdonhang extends IPhan_Trang
{
	HttpServletRequest getRequestObj();
	void setRequestObj(HttpServletRequest request);
	
	public String getUserId();
	public void setUserId(String userId);
	public String getNgaygiao();
	public void setNgaygiao(String ngaygiao);
	
	public String getMessege();
	public void setMessege(String msg);
	
	public String getNppId();
	public void setNppId(String nppId);
	public String getNppTen();
	public void setNppTen(String nppTen);
	public String getSitecode();
	public void setSitecode(String sitecode);
	
	public ResultSet getNvbhList();
	public void setNvbhList(ResultSet nvbhlist);
	public String getNvbhId();
	public void setNvbhId(String nvbhId);
	
	public ResultSet getNvgnList();
	public void setNvgnList(ResultSet nvgnlist);
	public String getNvgnId();
	public void setNvgnId(String nvgnId);
	
	/*
	public ResultSet getPxkList();
	public void setPxkList(ResultSet pxklist);
	public Hashtable<Integer, String> getPxkIds();
	public void setPxkIds(String[] pxkIds);
	*/
	
	public ResultSet getDhList();
	public void setDhList(ResultSet dhlist);
	public Hashtable<Integer, String> getDhIds();
	public void setDhIds(String[] dhIds);
	
	public boolean chotDonhang();
	
	public boolean TraTrungBay( HttpServletRequest request);
	
	public void init();
	public void DBclose();
		
}
