package geso.traphaco.erp.beans.erp_trungtamchiphi;

import geso.traphaco.center.util.IPhan_Trang;

import java.io.IOException;
import java.io.Serializable;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

public interface IErp_trungtamchiphiList  extends Serializable, IPhan_Trang
{
	public String getUserId();
	
	public void setUserId(String userId);

	public String getDiengiai();
	
	public void setDiengiai(String diengiai);
	
	public String getTrangthai();
	
	public void setTrangthai(String trangthai);
	
	public String getCtyId();

	public void setCtyId(String ctyId);
		
	public ResultSet getTtcpList();
	
	public void setTtcpList(ResultSet ttcplist);
	
	public boolean getSearch();
	
	public void setSearch(boolean search);
	
	public void init();
	
	public String getData(String userId);	

	public void getTtcpBeanList(HttpServletRequest request);	
	
	public void getReqParam(HttpServletRequest request) throws ServletException, IOException ;
	
	public void DBClose();
	
	public void setMsg(String msg);
	
	public String getMsg();
	
	public ResultSet getNhanphanbo(String ttcpId);
	
	public ResultSet getChophanbo(String ttcpId);
	
	public void setChixem(String chixem);
	public String getChixem();
}
