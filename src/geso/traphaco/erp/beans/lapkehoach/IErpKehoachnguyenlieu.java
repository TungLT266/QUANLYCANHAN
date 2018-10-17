package geso.erp.beans.lapkehoach;

import java.sql.ResultSet;

public interface IErpKehoachnguyenlieu
{
	public String getCtyId(); 

	public void setCtyId(String ctyId); 

	public String getId(); 

	public void setId(String Id); 
	
	public String getUserId(); 

	public void setUserId(String userId); 

	public String getNglieu(); 

	public void setNglieu(String nglieu); 
	
	public String getKho(); 

	public void setKho(String khoId); 

	public String getThangStr(); 

	public String getDiengiai(); 

	public void setDiengiai(String diengiai);
	
	public String getLoaiId(); 

	public void setLoaiId(String loaiId); 

	public String getMsg(); 

	public void setMsg(String msg); 

	public void init(); 
	
	public String getNgaykehoach(); 

	public void setNgaykehoach(String ngay); 

	public ResultSet getKhoRs(); 

	public void setKhoRs(ResultSet khoRs); 
	
	public ResultSet getSpRs(); 

	public void setSpRs(ResultSet spRs);

	public String getSpIds(); 

	public void setSpIds(String spIds); 

	public ResultSet getThangRs(); 

	public void setThangRs(ResultSet thangRs); 

	public String[] getThang();

	public void setThang(String[] thang);
	
	public boolean createKehoach();
	
	public void createRs(); 
	
	public void DbClose(); 
	
	public String getDateTime();
	

}
