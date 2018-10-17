package geso.traphaco.erp.beans.mucchiphi;

import java.io.IOException;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

public interface IMucchiphiList {
	
	public String getSotientu();

	public void setSotientu(String sotientu);
	
	public String getSotienden();

	public void setSotienden(String sotienden);

	public String getCtyId();

	public void setCtyId(String ctyId);
	
	public String getDvthId();
	
	public void setDvthId(String dvthId);
	
	public String getTrangthai();
	
	public void setTrangthai(String trangthai);
	
	public void setCtyList(ResultSet ctylist);
	
	public ResultSet getCtyList();	

	public void setDvthList(ResultSet dvthlist);
	
	public ResultSet getDvthList();
	
	public void setMcpList(ResultSet mcplist);
	
	public void createMcpList();
	
	public void createDvthList();

	public ResultSet getMcpList();	
	
	public void DBClose();
	
	public void setMsg(String Msg);
	
	public String getMsg();
	
	public void getSearchQuery(HttpServletRequest request)throws ServletException, IOException;
	
	public void setUserId(String Userid);

	public boolean delete(String id);
	
	public void setChixem(String chixem);
	public String getChixem();
	
}
