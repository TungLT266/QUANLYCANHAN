package geso.traphaco.center.beans.kenhbanhang;

import geso.traphaco.center.beans.kenhbanhang.IKenhbanhang;
import java.io.Serializable;
import java.util.List;

public interface IKenhbanhangList extends Serializable 
{
	public String getUserId();
	public void setUserId(String userId);
		
	public String getKenhbanhang();	
	public void setKenhbanhang(String kenhbanhang);
	
	public String getDiengiai();
	public void setDiengiai(String diengiai);
	
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public List<IKenhbanhang> getKbhList();	
	public void setKbhList(List<IKenhbanhang> kbhlist);
	
	public void init(String search);
	public void DBClose();
	public void setMsg(String Msg);
	public String getMsg();
}
