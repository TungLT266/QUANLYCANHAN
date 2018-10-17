package geso.traphaco.erp.beans.lapngansach;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.List;

public interface ILNSBanggianguyenlieuList extends Serializable
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

	public List<ILNSBanggianguyenlieu> getBgnguyenlieuList();
	
	public void setBgnguyenlieuList(List<ILNSBanggianguyenlieu> bgnguyenlieulist);

	public ResultSet getDvkd();
	
	public void setDvkd(ResultSet dvkd);
	
	public ResultSet getKenh();
	
	public void setKenh(ResultSet kenh);
	
	public boolean saveNewBgblc();
	
	public boolean UpdateBgblc();
	
	public ResultSet getBglist(); 
	public void setBglist(ResultSet bglist); 
	
	public void init(String search);
	public void closeDB();
}
