package geso.erp.beans.lapkehoach;

import java.sql.ResultSet;

public interface IErpCumsanxuat 
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

	public ResultSet getNhamayRs();
	public void setNhamayRs(ResultSet nmRs);
	public String getNhamayIds();
	public void setNhamayIds(String nmIds);
	
	public ResultSet getThietbiRs();
	public void setThietbiRs(ResultSet tbRs);
	public String getTbIds();
	public void setTbIds(String tbIds);
	
	public String getSonhancong();
	public void setSonhancong(String sonhancong);
	
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public String getMsg();
	public void setMsg(String msg);
	
	public boolean createCumsanxuat();
	public boolean updateCumsanxuat();
	
	public void createRs();
	public void init();
	
	public void DbClose();
	
}
