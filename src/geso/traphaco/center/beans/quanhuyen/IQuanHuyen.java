package geso.traphaco.center.beans.quanhuyen;

import java.io.Serializable;
import java.sql.ResultSet;

public interface IQuanHuyen extends Serializable  {
	public String getUserId();
	public void setUserId(String userId);
	public String getId();
	public void setId(String id);
	
	public ResultSet getTinhthanhRs();
	public void setTinhthanhRs(ResultSet ttRs);
	public String getTinhthanhId();
	public void setTinhthanhId(String ttId);
	
	public String getDiengiai();
	public void setDiengiai(String diengiai);
	
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public String getNgaytao();
	public void setNgaytao(String ngaytao);
	public String getNguoitao();
	public void setNguoitao(String nguoitao);
	public String getNgaysua();
	public void setNgaysua(String ngaysua);	
	public String getNguoisua();
	public void setNguoisua(String nguoisua);
	public String getMessage();
	public void setMessage(String msg);
	
	public boolean CreateQh();
	public boolean UpdateQh();
	public void createRS();
	

}
