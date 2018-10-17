package geso.traphaco.center.beans.nhomkenh;

import java.io.Serializable;
import java.sql.ResultSet;

public interface INhomkenh extends Serializable 
{
	public String getUserId();
	public void setUserId(String userId);
	public String getId();
	public void setId(String id);
	public String getNhomkenh();
	public void setNhomkenh(String nhomkenh);
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
	
	public String getKbhId();
	public void setKbhId(String kbhId);
	
	public ResultSet getKbhRs();
	public void setKbhRs(ResultSet kbhRs);
	
	public boolean CreateNk();
	public boolean UpdateNk();
	public void createKbhRs();
	public void DBClose();
}
