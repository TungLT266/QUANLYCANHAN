package geso.traphaco.erp.beans.banggiaban;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.List;

public interface IBanggiabanList extends Serializable
{
	public String getUserId();
	
	public void setUserId(String userId);

	public String getUserTen(); 

	public void setUserTen(String userTen); 

	public String getCtyId();
	
	public void setCtyId(String ctyId);
	
	public String getCtyTen();
	
	public void setCtyTen(String ctyTen);
	
	public String getTen(); 
	
	public void setTen(String ten);
	
	public String getDvkdId();
	
	public void setDvkdId(String dvkdId);
	
	public String getKenhId();
	
	public void setKenhId(String kenhId);
	
	public String getTrangthai(); 
	
	public void setTrangthai(String trangthai); 

	public List<IBanggiaban> getBgbanList();
	
	public void setBgbanList(List<IBanggiaban> bgbanlist);

	public ResultSet getDvkd();
	
	public void setDvkd(ResultSet dvkd);
	
	public ResultSet getKenh();
	
	public void setKenh(ResultSet kenh);
	
	public boolean saveNewBgblc();
	
	public boolean UpdateBgblc();
	
	public void createBanggiabanBeanList(String query);

	public ResultSet getBglist(); 
	public void setBglist(ResultSet bglist); 
	
	public void init(String search);
	public void closeDB();
}
