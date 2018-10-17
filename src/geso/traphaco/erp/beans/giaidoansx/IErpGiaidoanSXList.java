package geso.traphaco.erp.beans.giaidoansx;

import java.sql.ResultSet;

public interface IErpGiaidoanSXList 
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
	
	public ResultSet getGiaidoanRs();
	public void setGiaidoanRs(ResultSet giaidoanRs);
	
	public void init(String query);
	
	public void DbClose();
	
	public String getNppId();
	public void setNppId(String nppId);
	
	public void delete(String idxoa);
}
