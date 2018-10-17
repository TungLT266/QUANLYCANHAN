package geso.traphaco.erp.beans.kehoachkinhdoanh;

import geso.traphaco.center.util.IPhan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;

public interface IErpKehoachkinhdoanhList extends Serializable, IPhan_Trang
{
	public String getCongtyId();
	public void setCongtyId(String congtyId);
	
	public String getUserId();
	public void setUserId(String userId);
	public String getNam();
	public void setNam(String nam);
	public ResultSet getKhkdList();
	public void setKhkdList(ResultSet khkdlist);
	public void setmsg(String msg);
	public String getmsg();
	public String getTask();
	public void setTask(String task);

	public String getLoai();
	public void setLoai(String loai);
	public void init(String search);
	public void DBclose();
	
}
