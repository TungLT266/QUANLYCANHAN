package geso.traphaco.erp.beans.doanhthu;

import java.io.IOException;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

public interface IMucdoanhthuList {
	public String getDiengiai();
	
	public void setDiengiai(String diengiai);
	
	public String getLoai();
	
	public void setLoai(String thanhvien);

	public String getTrangthai();
	
	public void setTrangthai(String trangthai);
	
	public String getCtyId();

	public void setCtyId(String ctyId);
	
	public void setDvttId(String dvttId);

	public String getDvttId();
	
	public ResultSet getDtList();
	
	public void setDtList(ResultSet Dtlist);
	
	public ResultSet getCtyList();
	
	public ResultSet getDvttList();	
	
	public boolean getSearch();

	public void setSearch(boolean search);
	
	public void init();

	public void setMsg(String Msg);
	
	public String getMsg();
	
//	public void getDtBeanList(HttpServletRequest request);
	public void DBClose();

	public String getUserId();
	
	public void setUserId(String userId);

	public void getReqParam(HttpServletRequest request) throws ServletException, IOException;
	
	public void setChixem(String chixem);
	public String getChixem();
	
	
}