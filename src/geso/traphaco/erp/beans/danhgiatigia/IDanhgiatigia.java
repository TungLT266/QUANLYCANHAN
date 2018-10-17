package geso.traphaco.erp.beans.danhgiatigia;

import java.sql.ResultSet;

public interface IDanhgiatigia {
	public void setUserId(String userId);
	
	public String getUserId();

	public void setId(String Id);
	
	public String getId();
		
	public void setCtyId(String ctyId);
	
	public String getCtyId();

	public void setNam(String nam);
	
	public String getNam();
	
	public void setNamRS(ResultSet nam);
	
	public ResultSet getNamRS();

	public void setThangRS(ResultSet thang);
	
	public ResultSet getThangRS();
	
	public void setThang(String thang);

	public void setKqRS(ResultSet kqlist);
	
	public ResultSet getKqRS();
	
	public String getThang();
	
	public void setGhidao(String ghidao);
	
	public String getGhidao();
	
	public void setMsg(String msg);
		
	public String getMsg();
		
	public void init_New();
	
	public void init_Update();
	
	public void init_Display();

	public boolean Save();
	
	public void DBClose();
}