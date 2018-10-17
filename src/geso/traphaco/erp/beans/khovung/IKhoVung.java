package geso.traphaco.erp.beans.khovung;

import java.io.Serializable;

public interface IKhoVung extends Serializable 
{
	public String getCtyId(); 

	public void setCtyId(String ctyId); 
	
	public String getUserId();
	public void setUserId(String userId);
	public String getId();
	public void setId(String id);
	public String getMa();
	public void setMa(String ma);
	public String getTen();
	public void setTen(String ten);
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
	public void init();
	public boolean CreateKhoVung();
	public boolean UpdateKhoVung();
	public void closeDB();
	
}
