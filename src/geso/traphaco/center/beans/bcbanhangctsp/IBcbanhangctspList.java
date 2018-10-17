package geso.traphaco.center.beans.bcbanhangctsp;

import geso.traphaco.center.util.IPhan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;

public interface IBcbanhangctspList extends IPhan_Trang, Serializable
{
	public String getUserId();
	public void setUserId(String userId);

	public String getTungay();
	public void setTungay(String tungay);

	public String getDenngay();
	public void setDenngay(String denngay);
	
	public String getmaKH();
	public void setmaKH(String maKH);
	
	public String getmaFast();
	public void setmaFast(String mafast);
	
	public String getKhohang();
	public void setKhohang(String kho);
	
	public String getddkd();
	public void setddkd(String ddkd);
	public ResultSet getddkdRs();
	public void setddkdRS(ResultSet ddkd);

	public String getMsg();
	public void setMsg(String msg);

	public ResultSet getBcbanhangctspRs();
	public void setBcbanhangctspRs(ResultSet bcbanhangctspRs);

	public String getBcbanhangctspId();
	public void setBcbanhangctspId(String bcbanhangctspId);

	public void init(String query);
	
	
	public String getQuery();
	public void setQuery(String query);
	
	public void DbClose();
	

}
