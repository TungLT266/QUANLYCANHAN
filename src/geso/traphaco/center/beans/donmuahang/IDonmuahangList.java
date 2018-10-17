package geso.traphaco.center.beans.donmuahang;

import geso.traphaco.center.util.IPhan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;

public interface IDonmuahangList extends  Serializable, IPhan_Trang
{
	public String getUserId();
	public void setUserId(String userId);
	
	public String getnppTen();
	public void setnppTen(String nppten);

	public String getTen();
	public void setTen(String ten);
	
	public String getSKU();
	public void setSKU(String sku);
	
	public String getTungay();
	public void setTungay(String tungay);
	
	public String getDenngay();
	public void setDenngay(String denngay);
	
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public String getMaspstr();
	public void setMaspstr(String maspstr);
	
	public String getKvId();	
	public void setKvId(String kvId);
	public ResultSet getKvList();
	public void setKvList(ResultSet kvList);
	
	public String getSO();
	public void setSO(String so);

	public ResultSet getDhList();
	public void setDhList(ResultSet dhList);
	
	public String getMalist();
	public void setMalist(String malist);
		
	public void createDdhlist(String querystr);
	public void createDdhKTlist(String querystr);
	public ResultSet getDdhKTList();
	public ResultSet getDhDoneList();
	public void setDhDoneList(ResultSet dhdonelist);
	public void SetMsg(String msg);
	public String getMsg();
	public void DBclose();	
	
	public void initDonHangKm(String querystr);
	
	public ResultSet getNppRs();

	public void setNppRs(ResultSet nppRs);
	
	public String getNppId();

	public void setNppId(String nppId);
	
	
}