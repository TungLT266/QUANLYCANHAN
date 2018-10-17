package geso.traphaco.center.beans.nhomkenh;

import java.io.Serializable;
import java.util.List;

public interface INhomkenhList extends Serializable  
{
	public String getUserId();
	public void setUserId(String userId);
		
	public String getNhomkenh();	
	public void setNhomkenh(String nhomkenh);
	
	public String getDiengiai();
	public void setDiengiai(String diengiai);
	
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public List<INhomkenh> getNkList();	
	public void setNkList(List<INhomkenh> nklist);
	
	public void init(String search);
	public void DBClose();
	public void setMsg(String Msg);
	public String getMsg();

}
