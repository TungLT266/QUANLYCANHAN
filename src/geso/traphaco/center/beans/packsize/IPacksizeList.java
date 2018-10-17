package geso.traphaco.center.beans.packsize;
import java.io.Serializable;
import java.sql.ResultSet;

public interface IPacksizeList extends Serializable 
{
	public String getUserId();
	public void setUserId(String userId);
		
	public String getKenhbanhang();	
	public void setKenhbanhang(String kenhbanhang);
	
	public String getDiengiai();
	public void setDiengiai(String diengiai);
	
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public ResultSet getKbhList();	
	public void setKbhList(ResultSet kbhlist);
	
	public void init(String search);
	public void DBClose();
	public void setMsg(String Msg);
	public String getMsg();
}
