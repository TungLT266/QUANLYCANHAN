package geso.traphaco.center.beans.banggiamuanpp;

import geso.traphaco.center.util.IPhan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.List;

public interface IBanggiamuanppList extends Serializable, IPhan_Trang
{
	public String getUserId();
	
	public void setUserId(String userId);

	public String getUserTen(); 

	public void setUserTen(String userTen); 
	
	public String getTen(); 
	
	public void setTen(String ten);
	
	public String getDvkdId();
	
	public void setDvkdId(String dvkdId);
	
	public String getKenhId();
	
	public void setKenhId(String kenhId);
	
	public String getTrangthai(); 
	
	public void setTrangthai(String trangthai); 

	public List<IBanggiamuanpp> getBgmuanppList();
	
	public void setBgmuanppList(List<IBanggiamuanpp> bgmuanpplist);

	public ResultSet getDvkd();
	
	public void setDvkd(ResultSet dvkd);
	
	public ResultSet getKenh();
	
	public void setKenh(ResultSet kenh);
	
	public boolean saveNewBgblc();
	
	public boolean UpdateBgblc();
	
	public void createBanggiamuanppBeanList(String query);

	public ResultSet getBglist(); 
	public void setBglist(ResultSet bglist); 
	
	public void init(String search);
	public void closeDB();
	
	public String getTungay();
	public void setTungay(String tungay);
	
	public void setDenngay(String denngay);
	public String getDenngay();
	
	public String getMsg();
	public void setMsg(String msg);
	
}
