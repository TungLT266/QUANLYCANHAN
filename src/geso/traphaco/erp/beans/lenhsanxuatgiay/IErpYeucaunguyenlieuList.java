package geso.traphaco.erp.beans.lenhsanxuatgiay;

import geso.traphaco.center.util.IPhan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;

public interface IErpYeucaunguyenlieuList extends Serializable, IPhan_Trang
{
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
	
	public String getSochungtu();
	public void setSochungtu(String sochungtu);
	
	public String getSoyeucau();
	public void setSoyeucau(String Soyeucau);
	
	public String getLydo();
	public void setLydo(String Lydo);
	
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public String getMsg();
	public void setMsg(String msg);
	
	public String getIschuyenNL();
	public void setIschuyenNL(String ischuyenNL);
	
	public int getCurrentPage();
	public void setCurrentPage(int current);
	
	public int[] getListPages();
	public void setListPages(int[] listPages);
	
	public ResultSet getLsxRs();
	public void setLsxRs(ResultSet lsxRs);
	
	public void init(String search);
	public void DBclose();
}
