package geso.traphaco.erp.beans.khoasothang;

import java.sql.ResultSet;

public interface IErpTinhgianhap 
{
	public String getUserId();
	public void setUserId(String userId);
	
	public String getTungay();
	public void setTungay(String tungay);
	public String getDenngay();
	public void setDenngay(String denngay);
	
	public String getMsg();
	public void setMsg(String msg);
	
	public String tinhGiaTonkho(String tungay, String denngay);
	
	public String updateGiaNhap(String tungay, String denngay);
	public String updateGiaNhap2(String tungay, String denngay);
	
	public void createRs();
	public ResultSet getGiavonRs();
	public ResultSet getGiavonCTRs();
	
	public void DbClose();
}
