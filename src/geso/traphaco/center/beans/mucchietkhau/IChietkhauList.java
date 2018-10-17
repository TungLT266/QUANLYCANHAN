package geso.traphaco.center.beans.mucchietkhau;

import java.sql.ResultSet;

public interface IChietkhauList 
{
	public String getUserId();
	public void setUserId(String userId);
		
	public String getSotien();	
	public void setSotien(String sotien);
	
	public String getChietkhau();
	public void setChietkhau(String chietkhau);
	
	public ResultSet getCongnoList();	
	public void setCongnoList(ResultSet congnoList);
	
	public void setMsg(String Msg);
	public String getMsg();
	
	public void init(String search);
	public void DBClose();
}
