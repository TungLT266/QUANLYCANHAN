package geso.traphaco.erp.beans.vuviec;

import java.io.Serializable;

public interface IVuviec extends Serializable 
{
	public String getUserId();
	public void setUserId(String userId);
	public String getId();
	public void setId(String id);
	public String getMa();
	public void setMa(String ma);
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
	
	public String getCongty();
	public void setCongty(String congty);
	
	public boolean CreateKbh();
	public boolean UpdateKbh();
	public void DBClose();
	
}