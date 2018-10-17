package geso.traphaco.erp.beans.chinhsachduyetmuahang;

import java.sql.ResultSet;

public interface IChinhsachduyetmuahangList {
	public String getCtyId();

	public void setCtyId(String ctyId);
	
	public String getDvthId();

	public void setDvthId(String dvthId);
		
	public void setDvthList(ResultSet dvthlist);

	public ResultSet getDvthList();
	
	public void setCS(ResultSet cs);

	public ResultSet getCS();
	
	public String getMessage();
	
	public void setMessage(String msg);

	public void init();
	
	public void createDvthList();
		
	public void DBClose();
	
	public void SetUserId(String userid);

	public void setChixem(String chixem);
	public String getChixem();
	
}
