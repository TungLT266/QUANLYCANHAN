package geso.traphaco.center.beans.thongtinsanpham;

import geso.traphaco.center.util.IPhan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;

public interface IThongtinsanphamList extends IPhan_Trang, Serializable 
{
	public void init();
	
	public String getUserId();
	
	public void setUserId(String userId);
	
	public String getMasp();
	
	public void setMasp(String masp);
	
	public String getTensp();
	
	public void setTensp(String tensp);
	
	public String getDvkdId();
	
	public void setDvkdId(String dvkdId);

	public String getNhId();
	
	public void setNhId(String nhId);

	public String getClId();
	
	public void setClId(String clId);
			
	public String getTrangthai();
	
	public void setTrangthai(String trangthai);

	public String getQuery();
	
	public void setQuery(String query);

	public ResultSet getDvkd();
	
	public void setDvkd(ResultSet dvkd);
	
	public ResultSet getNh();
	
	public void setNh(ResultSet nh);

	public ResultSet getCl();
	
	public void setCl(ResultSet cl);
	
	public void CreateRS();
	
	public ResultSet getThongtinsanphamList();
	
	public void setThongtinsanphamList(ResultSet splist);
	
	public void setMsg(String Msg);
	
	public String getMsg();
	
	public void DBClose();
	
	public int getCurrentPage();
	public void setCurrentPage(int current);
	
	public int[] getListPages();
	public void setListPages(int[] listPages);
	public int getLastPage();

	public void setSoItems(int soItems);
	public int getSoItems();
	
	public ResultSet getLoaispRs();	
	public void setLoaispRs(ResultSet loaispRs);
	
	public String getLoaispId();	
	public void setLoaispId(String loaispId);
}
	
