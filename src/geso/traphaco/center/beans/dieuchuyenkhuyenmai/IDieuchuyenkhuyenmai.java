package geso.traphaco.center.beans.dieuchuyenkhuyenmai;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.Hashtable;

import javax.servlet.http.HttpServletRequest;

public interface IDieuchuyenkhuyenmai extends Serializable{
	public String getUserId();
	public void setUserId(String userId);
	public String getSchemeId();
	public void setSchemeId(String schemeId);
	public String getTieuchi();
	public void setTieuchi(String tieuchi);
	public String getNppstr();
	public void setNppstr(String nppstr);
	public String getSize();
	public void setSize(String size);
	public String getRest();
	public void setRest(String rest);
	public String getScheme();
	public void setScheme(String scheme);
	public String getMessage();
	public void setMessage(String msg);
	public String getBudget();
	public String getUsedBudget();
	public ResultSet getSchemeRS(); 
	public void setSchemeRS(ResultSet schemeRS); 
	public ResultSet getKv();
	public void setKv(ResultSet khuvuc);
	public String getKvId();
	public void setKvId(String kvId);
	public ResultSet getNpp(); 
	public void setNpp(ResultSet nppRS);
	public Hashtable<String, String> getusedPro();
	public void setusedPro(Hashtable<String, String> usedPro);
	public Hashtable<String, String> getAdjust();
	public void createAdjust();
	public void init();	
	public void setdieuchuyen(String[] dieuchuyen);
	public String[] getdieuchuyen();
	public boolean save(HttpServletRequest request);
}
