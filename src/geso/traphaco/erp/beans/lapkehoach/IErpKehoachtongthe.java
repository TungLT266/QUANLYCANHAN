package geso.erp.beans.lapkehoach;

import java.sql.ResultSet;

public interface IErpKehoachtongthe 
{
	public String getCtyId(); 
	public void setCtyId(String ctyId); 

	public String getUserId();
	public void setUserId(String userId);
	public String getId();
	public void setId(String Id);
	
	public String getNgaykehoach();
	public void setNgaykehoach(String ngay);

	public String getDiengiai();
	public void setDiengiai(String diengiai);
	public String getMsg();
	public void setMsg(String msg);
	
	public String getNglieu(); 
	public void setNglieu(String nglieu); 

	public ResultSet getNhRs();
	public void setNhRs(ResultSet nhRs);
	public String getNhIds();
	public void setNhIds(String nhIds);
	
	public ResultSet getClRs();
	public void setClRs(ResultSet clRs);
	public String getClIds();
	public void setClIds(String clIds);
		
	public ResultSet getSpRs();
	public void setSpRs(ResultSet spRs);
	public String getSpIds();
	public void setSpIds(String spIds);
	
	public ResultSet getThangRs();
	public void setThangRs(ResultSet thangRs);
	
	public String[] getThang();
	public void setThang(String[] thang);
		
	public String getThangStr();
	
	public boolean createKehoach();
	
	public void createRs();
	public void init();

	public ResultSet getKhoRs();
	
	public String getKho(); 

	public void setKho(String khoId); 

	public void setKhoRs(ResultSet khoRs); 
	
	public void DbClose();
}
