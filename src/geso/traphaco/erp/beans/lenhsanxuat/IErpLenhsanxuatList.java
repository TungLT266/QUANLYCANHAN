package geso.traphaco.erp.beans.lenhsanxuat;

import geso.traphaco.center.util.IPhan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;

public interface IErpLenhsanxuatList extends Serializable, IPhan_Trang
{
	public String getCongtyId();
	public void setCongtyId(String congtyId);
	
	public String getUserId();
	public void setUserId(String userId);

	public String getTungayTao();
	public void setTungayTao(String tungay);
	public String getDenngayTao();
	public void setDenngayTao(String denngay);
	
	public String getTungayDk();
	public void setTungayDk(String ngaydk);
	public String getDenngayDk();
	public void setDenngayDk(String ngaydk);
	
	public String getMasp();
	public void setMasp(String masp);
	
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	public String getMsg();
	public void setMsg(String msg);
	
	public int getCurrentPage();
	public void setCurrentPage(int current);
	
	public int[] getListPages();
	public void setListPages(int[] listPages);
	
	public ResultSet getLsxRs();
	public void setLsxRs(ResultSet lsxRs);

	
	public void init(String search);
	public void DBclose();
}
