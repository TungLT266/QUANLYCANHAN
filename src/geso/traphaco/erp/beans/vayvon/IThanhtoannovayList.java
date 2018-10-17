package geso.traphaco.erp.beans.vayvon;

import java.sql.ResultSet;

public interface IThanhtoannovayList 
{
	public void setSoHD(String SoHD);

	public String getSoHD();
	
	public void setCtyId(String ctyId);

	public String getCtyId();
	
	public ResultSet getTtnvRS();

	public void setUserId(String userId);

	public String getUserId();

	public void setMsg(String msg);
	
	public String getMsg();
	
	public void init(String st);
	
	public void Xoa(String Id);
	
	public void DbClose();
	
	public String getTkvay();

	public void setTkvay(String tkvay);
}
