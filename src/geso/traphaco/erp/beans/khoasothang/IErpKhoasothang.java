package geso.traphaco.erp.beans.khoasothang;

import java.sql.ResultSet;

public interface IErpKhoasothang 
{
	public String getUserId();
	public void setUserId(String userId);
	
	public String getThang();
	public void setThang(String thang);
	public String getNam();
	public void setNam(String nam);
	
	public String getThangKSGannhat();
	public void setThangKSGannhat(String thangksGannhat);
	public String getNamKSGannhat();
	public void setNamKSGannhat(String namksGannhat);
	
	public void Init();
	public void createRs();
	public ResultSet getChungtuRs();
	public void setChungtuRs(ResultSet ctRs);
	public ResultSet getAmkhoRs();
	public void setAmkhoRs(ResultSet amkhoRs);
	
	public String getMsg();
	public void setMsg(String msg);

	public String getNppId();
	public void setNppId(String nppId);
	public String getCongtyId();
	public void setCongtyId(String congtyId);
	
	public boolean KhoaSoThang();
	public boolean MoKhoaSoThang();
	
	public void DbClose();
	
}

