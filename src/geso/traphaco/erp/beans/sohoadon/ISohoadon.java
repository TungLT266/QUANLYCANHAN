package geso.traphaco.erp.beans.sohoadon;

import java.sql.ResultSet;

public interface ISohoadon {
	public String getId();
	
	public void setId(String Id);

	public String getCtyId();
	
	public void setCtyId(String ctyId);

	public String getKhoId();
	
	public void setKhoId(String khoId);

	public String getUserId();
	
	public void setUserId(String userId);

	public String getTuso();
	
	public void setTuso(String tuso);

	public String getDenso();
	
	public void setDenso(String denso);
	
	public String getKyhieu();
	
	public void setKyhieu(String kyhieu);

	public String getLoai();
	
	public void setLoai(String loai);

	public String getTrangthai();
	
	public void setTrangthai(String trangthai);

	public ResultSet getKhoList();
	
	public void init();
	
	public boolean Update();
	
	public boolean New();

	public void setMsg(String msg);
	
	public String getMsg();

	public void initNew();
		
	public void DBClose();
}