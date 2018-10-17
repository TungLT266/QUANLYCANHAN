package geso.traphaco.center.beans.nhapkho;

import geso.traphaco.center.util.IPhan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;

public interface IErpDontrahangList extends Serializable, IPhan_Trang
{
	public String getUserId();
	public void setUserId(String userId);
	
	public String getSohdId();
	public void setSohdId(String sohdId);
	
	public String getnppId();
	public void setnppId(String nppId);

	public String getTungayTao();
	public void setTungayTao(String tungay);
	public String getDenngayTao();
	public void setDenngayTao(String denngay);
	
	public String getSophieu();
	public void setSophieu(String sophieu);
	
	public ResultSet getNhapkhoRs();
	public void setNhapkhoRs(ResultSet nkRs);
	
	public ResultSet getnppRs();
	public void setnppRs(ResultSet nppRs);
	
	public ResultSet getSohdRs();
	public void setSohdRs(ResultSet sohdRs);
	
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public String getMsg();
	public void setMsg(String msg);
	
	
	public int getCurrentPage();
	public void setCurrentPage(int current);
	
	public int[] getListPages();
	public void setListPages(int[] listPages);
	
	public void init(String search);
	public void DBclose();
}
