package geso.traphaco.erp.beans.daytruyensanxuat;

import java.sql.ResultSet;

public interface IErpDaytruyensanxuat
{
	public String getUserId();
	public void setUserId(String userId);
	
	public String getCongtyId();
	public void setCongtyId(String congtyId);
	
	public String getId();
	public void setId(String Id);
	
	public String getMa();
	public void setMa(String ma);
	
	public String getDiengiai();
	public void setDiengiai(String diengiai);

	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public String getMsg();
	public void setMsg(String msg);
	
	public boolean createDaytruyensanxuat();
	public boolean updateDaytruyensanxuat();
	
	public String getNhamayId();
	
	public void setNhamayId(String nmId); 

	public String getTugio();
	
	public void setTugio(String tugio);
	
	public String getDengio();
	
	public void setDengio(String dengio); 

	public String getNghitruaTu();
	
	public void setNghitruaTu(String nghitruatu);
	
	public String getNghitruaDen();
	
	public void setNghitruaDen(String nghitruaden); 

	public ResultSet getGioRs();
	
	public void setGioRs(ResultSet gioRs); 
	
	public void init();
	
	public ResultSet getNhamayRs(); 
	public void setNhamayRs(ResultSet nhamayRs) ;
	
	public void createRs();
	
	public void DbClose();
	
}
