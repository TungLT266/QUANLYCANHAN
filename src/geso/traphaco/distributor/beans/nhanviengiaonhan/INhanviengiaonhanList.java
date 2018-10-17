package geso.traphaco.distributor.beans.nhanviengiaonhan;

import java.io.Serializable;
import geso.traphaco.distributor.beans.nhanviengiaonhan.INhanviengiaonhan;
import java.sql.ResultSet;
import java.util.List;

public interface INhanviengiaonhanList extends Serializable
{
	public String getUserId();
	public void setUserId(String userId);
	
	public String getTennv();
	public void setTennv(String tennv);	
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public ResultSet getDdkd();
	public void setDdkd(ResultSet ddkd);
	public String getDdkdId();
	public void setDdkdId(String ddkdId);
	
	public String getNppId();
	public void setNppId(String nppId);
	public String getNppTen();
	public void setNppTen(String nppTen);
	public String getSitecode();
	public void setSitecode(String sitecode);
	
	public List<INhanviengiaonhan> getNvgnList();
	public void setNvgnList(List<INhanviengiaonhan> nvgnList);
	
	public void init(String search);
	public void DBclose();
}