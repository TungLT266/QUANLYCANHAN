package geso.traphaco.erp.beans.lapkehoach;

import java.sql.ResultSet;

public interface IErpDaychuyensanxuat 
{
	public String getCtyId(); 

	public void setCtyId(String ctyId); 
	
	public String getUserId();
	public void setUserId(String userId);
	
	public String getId();
	public void setId(String Id);
	
	public String getMa();
	public void setMa(String ma);
	
	public String getDiengiai();
	public void setDiengiai(String diengiai);

	public ResultSet getSpRs();
	public void setSpRs(ResultSet spRs);
	public String getSpIds();
	public void setSpIds(String spIds);
	
	public ResultSet getCumsxRs();
	public void setCumsxRs(ResultSet cumsxRs);
	public String getCumsxIds();
	public void setCumsxIds(String cumsxIds);
	
	public String getSoluongchuan();
	public void setSoluongchuan(String soluongchuan);
	
	public String getThoigian();
	public void setThoigian(String thoigian);
	
	public String getMsg();
	public void setMsg(String msg);
	
	public boolean createDaychuyensanxuat();
	public boolean updateDaychuyensanxuat();
	
	public void createRs();
	public void init();
	
	public void DbClose();
	
}
