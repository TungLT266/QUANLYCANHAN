package geso.traphaco.center.beans.hethongbanhang;

import geso.traphaco.center.beans.hethongbanhang.IHethongbanhang;

import java.io.Serializable;
import java.util.List;

public interface IHethongbanhangList extends Serializable 
{
	public String getUserId();
	public void setUserId(String userId);
		
	public String getHethongbanhang();	
	public void setHethongbanhang(String hethongbanhang);
	
	public String getDiengiai();
	public void setDiengiai(String diengiai);
	
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public List<IHethongbanhang> getHtbhList();	
	public void setHtbhList(List<IHethongbanhang> htbhlist);
	
	public void init(String search);
	public void DBClose();
	public void setMsg(String Msg);
	public String getMsg();
}
