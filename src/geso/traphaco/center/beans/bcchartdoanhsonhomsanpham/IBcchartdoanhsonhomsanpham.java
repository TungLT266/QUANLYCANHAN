package geso.traphaco.center.beans.bcchartdoanhsonhomsanpham;

import geso.traphaco.center.util.IPhan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;

public interface IBcchartdoanhsonhomsanpham extends IPhan_Trang, Serializable
{
	public String getUserId();
	public void setUserId(String userId);

	public String getId();
	public void setId(String Id);
	public String getThang();
	public void setThang(String thang);
	public String getNam();
	public void setnam(String nam);
	public String getMsg();
	public void setMsg(String msg);
	public String[] getArrTenNSP();
	public String[] getArrIDNSP();
	public ResultSet getRs();
	public void init();

	public void DbClose();
}
