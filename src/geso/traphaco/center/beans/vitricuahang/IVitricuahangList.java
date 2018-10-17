package geso.traphaco.center.beans.vitricuahang;

import geso.traphaco.center.beans.vitricuahang.IVitricuahang;
import java.io.Serializable;
import java.util.List;

public interface IVitricuahangList extends Serializable 
{
	public String getUserId();
	public void setUserId(String userId);
		
	public String getVitricuahang();	
	public void setVitricuahang(String vitricuahang);
	
	public String getDiengiai();
	public void setDiengiai(String diengiai);
	
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public List<IVitricuahang> getHchList();	
	public void setHchList(List<IVitricuahang> hchlist);
	
	public void init(String search);
    public void setMsg(String Msg);
    public String getMsg();
}

