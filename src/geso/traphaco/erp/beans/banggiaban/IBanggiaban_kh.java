package geso.erp.beans.banggiaban;

import java.io.Serializable;
import java.sql.ResultSet;
import javax.servlet.http.HttpServletRequest;

public interface IBanggiaban_kh extends Serializable
{
	public String getUserId(); 
	
	public void setUserId(String userId); 
	
	public String getUserTen(); 
	
	public void setUserTen(String userTen); 

	public String getId(); 
	
	public void setId(String id); 

	public String getTen(); 

	public void setTen(String ten); 
	
	public String getDvkdId(); 

	public void setDvkdId(String dvkdId); 
	
	public String getKenhId(); 

	public void setKenhId(String kenhId); 
		
	public void setTrangthai(String trangthai); 

	public String getTrangthai(); 

	public String getNgaytao();

	public void setNgaytao(String ngaytao);
	
	public String getNgaysua();

	public void setNgaysua(String ngaysua);
	
	public String getNguoitao();
	
	public void setNguoitao(String nguoitao);

	public String getNguoisua();

	public void setNguoisua(String nguoisua);
	
	public String getMessage(); 
	
	public void setMessage(String msg); 
	
	public ResultSet getDvkdIds(); 

	public void setDvkdIds(ResultSet dvkdIds); 

	public ResultSet getKenhIds(); 

	public void setKenhIds(ResultSet kenhIds); 
	
	public String[] getkhIds(); 

	public void setkhIds(String[] khIds); 

	public ResultSet getKHSelected();
	
	public ResultSet  getKHList();
	
	public String getKHString();
	
	public void init();
	
	public boolean UpdateBgban(HttpServletRequest request);
	
	public void DBClose();
}
