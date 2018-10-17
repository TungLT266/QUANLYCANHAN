package geso.traphaco.distributor.beans.bcbanhangctsp;


import java.io.Serializable;
import java.sql.ResultSet;

public interface IBcbanhangctsp extends  Serializable
{
	public String getUserId();
	public void setUserId(String userId);

	public String getId();
	public void setId(String Id);

	public String getMsg();
	public void setMsg(String msg);

	public boolean createBcbanhangctsp();
	public boolean updateBcbanhangctsp();

	public void init();
	public void createRs();

	public void DbClose();
}
