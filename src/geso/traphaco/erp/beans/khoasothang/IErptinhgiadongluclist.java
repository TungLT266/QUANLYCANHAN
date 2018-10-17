package geso.traphaco.erp.beans.khoasothang;

 

import java.sql.ResultSet;
 
public interface IErptinhgiadongluclist
{
	public String getId();
	public void setId(String IdDongHoDien);
	
	public String getUserId();
	public void setUserId(String UserId);
	
	public String getMsg();
	public void setMsg(String msg);
	
	public int  getThang();
	public void setThang(int thang);
	
	public int  getNam();
	public void setNam(int nam);
	
	public String gettrangthai();
	public void setTrangthai(String trangthai);
  
	public ResultSet GetRsDongluc();
	public void setRsDongluc(ResultSet RsDongluc);
		
	public void DbClose();
	public void Init();
	public String Duyet(String id);
	public String huybo(String id);
	public String Boduyet(String id);
	
}
