package geso.traphaco.center.beans.banggiablc;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.List;

public interface IBanggiablcList extends Serializable
{
	public String getUserId();
	public void setUserId(String userId);	
	public String getTen();
	public void setTen(String tenbanggia);	
	public ResultSet getDvkd();
	public void setDvkd(ResultSet dvkd);
	public String getDvkdId();
	public void setDvkdId(String dvkdId);
	public String getTrangthai();
	public void setTrangthai(String trangthai);
		
	public List<IBanggiablc> getBgblcList();
	public void setBgblcList(List<IBanggiablc> bgblclist);
	public void init(String search);
	public void DbClose();
	
}
