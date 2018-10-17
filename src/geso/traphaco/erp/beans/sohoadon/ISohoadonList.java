package geso.traphaco.erp.beans.sohoadon;

import java.io.IOException;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

public interface ISohoadonList {
	public String getTuso();
	
	public void setTuso(String tuso);
	
	public String getDenso();
	
	public void setDenso(String denso);

	public String getTrangthai();
	
	public void setTrangthai(String trangthai);
	
	public String getCtyId();

	public void setCtyId(String ctyId);
	
	public void setKhoId(String khoId);

	public String getKhoId();
	
	public String getLoai();
	
	public void setLoai(String loai);
	
	public ResultSet getSohoadonList();
	
	public void setSohoadonList(ResultSet sohoadonlist);
	
	public ResultSet getCtyList();
	
	public ResultSet getKhoList();	
	
	public boolean getSearch();

	public void setSearch(boolean search);
	
	public void init();

	public void setMsg(String Msg);
	
	public String getMsg();
	
//	public void getNcpBeanList(HttpServletRequest request);
	public void DBClose();

	public String getUserId();
	
	public void setUserId(String userId);

	public void getReqParam(HttpServletRequest request) throws ServletException, IOException;
	
	public boolean isNotInUse(String Id);
	
}