package geso.traphaco.center.beans.hoadon;

import geso.traphaco.center.util.IPhan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;

public interface IHoaDonList extends Serializable, IPhan_Trang
{
	public ResultSet getNhaphangList();
	public void setNhaphangList(ResultSet nhaphangList);

	public String getUserId();
	public void setUserId(String userId);

	public String getNppId();
	public void setNppId(String nppId);

	public String getSKU();
	public void setSKU(String sku);

	public String getTungay();
	public void setTungay(String tungay);

	public String getDenngay();
	public void setDenngay(String denngay);

	public String getTrangthai();
	public void setTrangthai(String trangthai);

	public void createNhaphanglist(String querystr);
	
	public void setMsg(String Msg);
	public String getMsg();

	public String getSoHoaDon();
	public void setSoHoaDon(String sohoadon);

	public ResultSet getNppRs();
	public void setNppRs(ResultSet nppRs);

	public void DBclose();
	
	
	public String getLoaihd();
	public void setLoaihd(String loaiHd);
	
	public String getDdhId();
	public void setDdhId(String dddhId);

}
