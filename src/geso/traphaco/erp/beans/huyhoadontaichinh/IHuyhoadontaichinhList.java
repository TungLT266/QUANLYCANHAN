package geso.traphaco.erp.beans.huyhoadontaichinh;
import geso.traphaco.center.util.IPhan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;

public interface IHuyhoadontaichinhList extends IPhan_Trang, Serializable
{
	public String getUserId();
	public void setUserId(String userId);


	public String getMsg();
	public void setMsg(String msg);
	
	public ResultSet getHuyhoadontaichinhRs();
	public void setHuyhoadontaichinhRs(ResultSet huyhoadontaichinhRs);

	public String getHuyhoadontaichinhId();
	public void setHuyhoadontaichinhId(String huyhoadontaichinhId);

	public void init(String query);
	public void DbClose();
	
	// ***************** TÌM KIẾM TRÊN GIAO DIỆN *******************//
	
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public String gettungay();
	public void settungay(String tungay);
	
	public String getdenngay();
	public void setdenngay(String denngay);
	
	
	public String getsochungtu();
	public void setsochungtu(String sochungtu);
	
	public ResultSet getkhachhang();
	public void setkhachhang(ResultSet khachhang);
	
	public String getkhId();
	public void setkhId(String khId);

	
	
	
	
	//*********** TÁC VU ********************//
	public void chot(String Id);
	public void delete(String Id);
	
	public int getCurrentPage();
	public void setCurrentPage(int current);	
	public int[] getListPages();
	public void setListPages(int[] listPages);

}
