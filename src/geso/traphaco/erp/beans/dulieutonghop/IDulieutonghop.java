package geso.traphaco.erp.beans.dulieutonghop;

import java.sql.ResultSet;

import javax.servlet.http.HttpServletRequest;

public interface IDulieutonghop {
	public String getCtyId();
	public void setCtyId(String ctyId);
	public void setCtyList(ResultSet ctylist);
	public ResultSet getCtyList();	
	public String getDlthId();
	public void setDlthId(String dlthId);
	public void setDlthList(ResultSet dlth);	
	public ResultSet getDlthList();

	public ResultSet getTaikhoan();
	
	public ResultSet getTaikhoan_Selected();

	public String getLtkId();
	public void setLtkId(String ltkId);
	
	public void setLoaiTK(ResultSet loaitk);
	public ResultSet getLoaiTK();

	public String getMessage();
	public void setMessage(String msg);

	public void setTkIds(String[] tkIds);
	
	public void setRequest(HttpServletRequest request);
	
	public void init();
	
	public boolean Save();
	
	public void DBClose();

	public void SetUserId(String userid);
}
