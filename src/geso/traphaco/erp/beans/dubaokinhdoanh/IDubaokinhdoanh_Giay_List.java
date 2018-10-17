package geso.traphaco.erp.beans.dubaokinhdoanh;

import java.sql.ResultSet;
import geso.traphaco.center.util.IPhan_Trang;
public interface IDubaokinhdoanh_Giay_List extends IPhan_Trang 
{
	public String getCtyId(); 
	
	public void setCtyId(String ctyId);
	
	public String getDvkdId();

	public void setDvkdId(String dvkdId);

	public ResultSet getDvkdRs();

	public void setDvkdRs(ResultSet dvkd);
	
	public String getUserId();
	public void setUserId(String userId);
	
	public String getId();
	public void setId(String id);
	
	public String getNgaydubao();
	public void setNgaydubao(String ngaydubao);

	public String getDiengiai();
	
	public void setDiengiai(String diengiai); 
	
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public String getKhoId();
	public void setKhoId(String khoId);
	
	public String getPhuongphap();
	public void setPhuongphap(String phuongphap);
	
	public String getMsg();	
	public void setMsg(String msg);
	
	public ResultSet getKhoList();
	public void setKhoList(ResultSet kholist);
	
	public ResultSet getPhuongphapList();
	public void setPhuongphapList(ResultSet phuongphaplist);
	
	public ResultSet getDubaoList();
	public void setDubaoList(ResultSet dubaolist);
	
	public int getCurrentPage();
	public void setCurrentPage(int current);
	
	public int[] getListPages();
	public void setListPages(int[] listPages);
	
	public void createRs();
	
	public void init(String query);
	
	public void close();
}
