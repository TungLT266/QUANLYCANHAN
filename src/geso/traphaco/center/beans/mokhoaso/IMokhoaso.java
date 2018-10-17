package geso.traphaco.center.beans.mokhoaso;

import java.sql.ResultSet;

public interface IMokhoaso {
	public void setVungId(String vungId);
	
	public String getVungId();
	
	public void setKhuvucId(String kvId);
	
	public String getKhuvucId();

	public void setNppId(String nppId);
	
	public String getNppId();
	
	public void init();
	
	public String MoKhoaSoNgay();
	
	public void setMsg(String msg);
	
	public String getMsg();
	
	public void setVung(ResultSet vung);
	
	public ResultSet getVung();

	public void setKhuvuc(ResultSet kv);
	
	public ResultSet getKhuvuc();

	public void setNpp(ResultSet npp);
	
	public ResultSet getNpp();
	public void  DBClose();
	

	public String getThangks();
	public void setThangks(String thangks);
	
	public String getNamks();
	public void setNamks(String namks);
	
	public String getUserId();
	public void setUserId(String userId);
	
	
}
