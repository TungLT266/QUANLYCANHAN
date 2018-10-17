package geso.erp.beans.lapkehoach;

import java.sql.ResultSet;

public interface IErpDenghihuymua 
{
	public String getCtyId(); 
	public void setCtyId(String ctyId); 
	
	public String getUserId();
	public void setUserId(String userId);

	public String getMsg();
	public void setMsg(String msg);
		
	public ResultSet getDenghihuymuaRs();
	public void setDenghihuymuaRs(ResultSet huyRs);
	
	public void init();
	
	public void DbClose();
	
	public String getNam(); 

	public void setNam(String nam); 
	
	public String getThang(); 

	public void setThang(String thang); 
	
	public String getDateTime();
	
	public ResultSet getChitiet(String spId, String nam, String thang);
}
