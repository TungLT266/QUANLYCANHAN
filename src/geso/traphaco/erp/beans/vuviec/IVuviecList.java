package geso.traphaco.erp.beans.vuviec;

import geso.traphaco.erp.beans.vuviec.IVuviec;

import java.io.Serializable;
import java.util.List;

public interface IVuviecList extends Serializable 
{
	public String getUserId();
	public void setUserId(String userId);
		
	public String getMa();	
	public void setMa(String ma);
	
	public String getDiengiai();
	public void setDiengiai(String diengiai);
	
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public List<IVuviec> getDmplList();	
	public void setDmplList(List<IVuviec> dmpllist);
	
	public void init(String search);
	public void DBClose();
	public void setMsg(String Msg);
	public String getMsg();
	
	public String getCongty();
	public void setCongty(String congty);
}
