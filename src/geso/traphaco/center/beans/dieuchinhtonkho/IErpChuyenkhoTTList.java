package geso.traphaco.center.beans.dieuchinhtonkho;

import geso.traphaco.center.util.IPhan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;

public interface IErpChuyenkhoTTList extends Serializable, IPhan_Trang
{
	public String getUserId();
	public void setUserId(String userId);

	public String getTungayTao();
	public void setTungayTao(String tungay);
	public String getDenngayTao();
	public void setDenngayTao(String denngay);
	
	public String getctId();
	public void setctId(String ctId);
	
	public String getSophieu();
	public void setSophieu(String sophieu);
	
	public ResultSet getNhapkhoRs();
	public void setNhapkhoRs(ResultSet nkRs);
	
	public ResultSet getNppRs();
	public void setNppRs(ResultSet npprs);
	
	public String getNppTen();
	public void setNppTen(String nppid);
	
	public String getNppId();
	public void setNppId(String nppid);
	
	public ResultSet getChungtuRs();
	public void setChungtuRs(ResultSet chtuRs);
	
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
	
	public String getLoaidonhang();
	public void setLoaidonhang(String loaidonhang);
	
	
}
