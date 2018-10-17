package geso.traphaco.erp.beans.khoasothang;

import java.sql.ResultSet;

public interface IErpTinhgiatonTP 
{
	public String getUserId();
	public void setUserId(String userId);
	
	public String getType();
	public void setType(String type);
	
	public String getTungay();
	public void setTungay(String tungay);
	public String getDenngay();
	public void setDenngay(String denngay);
	
	public String getMsg();
	public void setMsg(String msg);
	
	public String tinhGiaTonkho(String tungay, String denngay);
	
	public void createRs();
	public ResultSet getGiavonRs();
	public ResultSet getGiavonCTRs();
	
	public String getNppId();
	public void setNppId(String nppId);
	public String getCongtyId();
	public void setCongtyId(String congtyId);
	
	public void DbClose();
	
}
