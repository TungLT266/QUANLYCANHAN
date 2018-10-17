package geso.traphaco.distributor.beans.banggiablnpp;

import geso.traphaco.distributor.beans.banggiablnpp.IBanggiablnpp;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.List;

public interface IBanggiablnppList extends Serializable
{	
	public String getUserId();
	public void setUserId(String userId);
	
	public String getTenbanggia();
	public void setTenbanggia(String tenbanggia);
		
	public ResultSet getNcc();
	public void setNcc(ResultSet ncc);
	public String  getNccId();
	public void setNccId(String nccId);
	
	public ResultSet getDvkd();
	public void setDvkd(ResultSet dvkd);
	public String  getDvkdId();
	public void setDvkdId(String dvkdId);
	
	public List<IBanggiablnpp> getBgblList();
	public void setBgstList(List<IBanggiablnpp> bgblList);
	
	public String getNppId();
	public void setNppId(String nppId);
	public String getNppTen();
	public void setNppTen(String nppTen);
	public String getSitecode();
	public void setSitecode(String sitecode);
	
	public void init(String search);
	public void DBclose();
}