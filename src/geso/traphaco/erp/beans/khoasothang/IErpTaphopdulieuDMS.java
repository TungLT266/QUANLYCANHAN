package geso.traphaco.erp.beans.khoasothang;

import java.sql.ResultSet;

public interface IErpTaphopdulieuDMS 
{
	public String getUserId();
	public void setUserId(String userId);
	
	public String getThang();
	public void setThang(String thang);
	public String getNam();
	public void setNam(String nam);
	
	public String getMsg();
	public void setMsg(String msg);
	
	public void TapHopDuLieu();
	public void createRs();
	public ResultSet getGiavonRs();
	public ResultSet getGiavonCTRs();
	
	public String getNppId();
	public void setNppId(String nppId);
	public String getCongtyId();
	public void setCongtyId(String congtyId);
	
	public void DbClose();
	
}
