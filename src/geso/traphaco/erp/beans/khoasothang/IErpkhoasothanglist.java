package geso.traphaco.erp.beans.khoasothang;

import java.sql.ResultSet;
 
public interface IErpkhoasothanglist
{
	public String getId();
	public void setId(String IdDongHoDien);
	
	public String getTrangthai();
	public void setTrangthai(String Trangthai);
 
	public String getUserId();
	public void setUserId(String UserId);
	
	public String getMsg();
	public void setMsg(String msg);
	
	public int  getThang();
	public void setThang(int thang);
	
	public int  getNam();
	public void setNam(int nam);
	
 
	public ResultSet getRsList();
	public void DbClose();
	public void Init();
	public boolean Save();
	public boolean Edit();
	public boolean MoKhoaSoKho();
	
}
