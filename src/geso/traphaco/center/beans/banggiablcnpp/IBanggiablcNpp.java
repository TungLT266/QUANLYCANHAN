package geso.traphaco.center.beans.banggiablcnpp;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.Hashtable;

public interface IBanggiablcNpp extends Serializable
{
	public String getUserId();
	public void setUserId(String userId);
	public String getUserTen(); 
	public void setUserTen(String userTen);	
	
	public String getId();
	public void setId(String id);	
	
	public String getTen();
	public void setTen(String ten);
	
	public void setTrangthai(String trangthai);	
	public String getTrangthai();

	public String getMessage();
	public void setMessage(String msg);
	
	public ResultSet getDvkdRs();	
	public void setDvkdRs(ResultSet dvkdRs);
	public String getDvkdId();
	public void setDvkdId(String dvkdId);
	
	public ResultSet getKenhRs();
	public void setKenhRs(ResultSet kenhRs);
	public String getKenhId();	
	public void setKenhId(String kenhId);
	
	public ResultSet getSanPhamList();
	public void setSanPhamList(ResultSet sanphamlist);
	public String getSanphamId();	
	public void setSanphamId(String sanphamId);
	public Hashtable<String, String> getSanphamCK();	
	public void setSanphamCK(Hashtable<String, String> spCK);
	public Hashtable<String, String> getSanphamDG_SauCK();	
	public void setSanphamDG_SauCK(Hashtable<String, String> spDG_SauCK);
	
	public boolean CreateBg(); 
	public boolean UpdateBg();
	
	public void createRS();
	public void init();
	public void closeDB();
	
	public String getTungay();
	public void setTungay(String tungay);
	
	public void setDenngay(String denngay);
	public String getDenngay();
	
	public String getNppIds();
	public void setNppIds(String nppIds);
	public ResultSet getNppRs();
	public String getSitecode();
	public void setSitecode(String sitecode);
	
	public String getBanggiaId();
	public void setBanggiaId(String value);
	
	public ResultSet getBanggiaRs();
	public void setCk(String ck);
	public String getCk();
	public void setSanphamDG_TruocCK(Hashtable<String, String> sanphamDG_TruocCK);
	
}
