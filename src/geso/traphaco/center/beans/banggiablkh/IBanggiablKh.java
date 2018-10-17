package geso.traphaco.center.beans.banggiablkh;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.Hashtable;
import java.util.List;

public interface IBanggiablKh extends Serializable
{
	public String getUserId();
	public void setUserId(String userId);
	public String getUserTen(); 
	public void setUserTen(String userTen);	
	
	public String getSpId();
	public void setSpId(String id);	
	
	public String getSpMa();
	public void setSpMa(String ma);
	
	public String getSpTen();
	public void setSpTen(String ten);	

	public String getMessage();
	public void setMessage(String msg);
	
	
	
	public boolean CreateBg(); 
	public boolean UpdateBg();
	
	public void createRS();
	public void init();
	public void closeDB();
	
	public List<IGiaKh> getKhlist();
	public void setKhlist(List<IGiaKh> khlst);
	
}
