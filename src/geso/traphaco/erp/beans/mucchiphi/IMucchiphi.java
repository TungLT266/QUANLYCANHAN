package geso.traphaco.erp.beans.mucchiphi;

import java.sql.ResultSet;

public interface IMucchiphi {
	public String getId();

	public void setId(String id);

	public String getUserId();
	
	public void setUserId(String userId);

	public String getSotientu();

	public void setSotientu(String sotientu);

	public String getSotienden();
	
	public void setSotienden(String sotienden);

	public String getTrangthai();

	public void setTrangthai(String trangthai);
	
	public String getCtyId();

	public void setCtyId(String ctyId);
	
	public String[] getDvthIds();
	
	public void setDvthIds(String[] dvthIds);

	public String getMessage();
	
	public void setMessage(String msg);

	public void setCtyList(ResultSet ctylist);
	
	public ResultSet getCtyList();

	public void setDvthList(ResultSet dvthlist);
	
	public ResultSet getDvthList();
	
	public void init();
	
	public void createDvthList();
	
	public boolean saveNewMucchiphi();
	
	public boolean UpdateMucchiphi();
	
	public void DBClose();
}