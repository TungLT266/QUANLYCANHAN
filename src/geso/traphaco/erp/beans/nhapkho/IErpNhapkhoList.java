package geso.traphaco.erp.beans.nhapkho;

import geso.traphaco.center.util.IPhan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;

public interface IErpNhapkhoList extends Serializable, IPhan_Trang
{
	public String getCongtyId();
	public void setCongtyId(String congtyId);
	
	public String getUserId();
	public void setUserId(String userId);

	public String getTungay();
	public void setTungay(String tungay);
	public String getDenngay();
	public void setDenngay(String denngay);

	public String getSoPnh();
	public void setSoPnh(String soPnh);
	
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public void setmsg(String msg);
	public String getmsg();
	
	public ResultSet getNhapKhoList();
	public void setNhapKhoList(ResultSet nhapkhoList);
	
	public int getCurrentPage();
	public void setCurrentPage(int current);
	
	public int[] getListPages();
	public void setListPages(int[] listPages);
	
	public void init(String search);
	public void DBclose();
}
