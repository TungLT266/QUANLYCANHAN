package geso.traphaco.center.beans.bchoadonbanhang;

import geso.traphaco.center.util.IPhan_Trang;
import java.io.Serializable;
import java.sql.ResultSet;

public interface IBchoadonbanhang extends IPhan_Trang, Serializable
{
	public String getUserId();
	public void setUserId(String userId);

	public String getId();
	public void setId(String Id);

	public String getMsg();
	public void setMsg(String msg);

	public boolean createBchoadonbanhang();
	public boolean updateBchoadonbanhang();

	public void init();
	public void createRs();

	public void DbClose();
}
