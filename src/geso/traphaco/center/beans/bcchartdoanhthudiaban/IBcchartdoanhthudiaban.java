package geso.traphaco.center.beans.bcchartdoanhthudiaban;

import geso.traphaco.center.util.IPhan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;

public interface IBcchartdoanhthudiaban extends IPhan_Trang, Serializable
{
	public String getUserId();
	public void setUserId(String userId);

	public String getId();
	public void setId(String Id);
	public String getThang();
	public void setThang(String thang);
	public String getNam();
	public void setNam(String nam);
	public String getMsg();
	public void setMsg(String msg);
	public String[] getArrTenDiaban();
	public String[] getArrIDDiaban();
	public ResultSet getRs();
	public void init();

	public void DbClose();
}
