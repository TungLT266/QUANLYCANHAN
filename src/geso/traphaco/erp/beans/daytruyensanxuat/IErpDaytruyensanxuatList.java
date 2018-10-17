package geso.traphaco.erp.beans.daytruyensanxuat;

import java.sql.ResultSet;

public interface IErpDaytruyensanxuatList 
{
	public String getUserId();
	public void setUserId(String userId);

	public String getCongtyId();
	public void setCongtyId(String congtyId);
	
	public String getMa();
	public void setMa(String ma);	
	
	public String getDiengiai();
	public void setDiengiai(String diengiai);
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public String getMsg();
	public void setMsg(String msg);
	
	public ResultSet getDaytruyensanxuatRs();
	public void setDaytruyensanxuatRs(ResultSet daytruyensanxuatRs);
	
	public String getNhamayId();
	public void setNhamayId(String nmId);
	
	public ResultSet getNhamayRs(); 
	public void setNhamayRs(ResultSet nhamayRs) ;
	
	public void init(String query);
	public void DbClose();
	
}
