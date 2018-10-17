package geso.traphaco.erp.beans.chucdanh;

import java.sql.ResultSet;

public interface IChucdanhList {
	public String getChucdanh();

	public void setChucdanh(String chucdanh);

	public String getCdId();

	public void setCdId(String cdId);

	public String getCtyId();

	public void setCtyId(String ctyId);
	
	public String getTungay();

	public void setTungay(String tungay);
	
	public String getDenngay();

	public void setDenngay(String denngay);
	
	public String getTrangthai();

	public void setTrangthai(String trangthai);

	public void setCdlist(ResultSet cdlist);

	public ResultSet getCdlist();
	
	public void setCtyList(ResultSet ctylist);

	public ResultSet getCtyList();

	public void setNvList(ResultSet nvlist);

	public ResultSet getNvList();
	
	public void DBClose();

	public void setMsg(String Msg);
	
	public String getMsg();

}
