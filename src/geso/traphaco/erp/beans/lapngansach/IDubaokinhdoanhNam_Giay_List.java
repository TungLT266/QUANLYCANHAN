package geso.traphaco.erp.beans.lapngansach;

import java.sql.ResultSet;
import geso.traphaco.center.util.IPhan_Trang;
public interface IDubaokinhdoanhNam_Giay_List extends IPhan_Trang 
{
	public String getCtyId(); 
	
	public void setCtyId(String ctyId);
	
	public String getUserId();
	public void setUserId(String userId);
	
	public String getId();
	public void setId(String id);
	
	public String getNsId();	
	public void setNsId(String nsId); 
	
	public String getNamdubao();
	public void setNamdubao(String nam);

	public String getDiengiai();
	
	public void setDiengiai(String diengiai); 
	
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public String getKho();
	public void setKho(String kho);
	
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
	
	public String getDvkdId(); 
	
	public void setDvkdId(String dvkdId); 
	
	public ResultSet getDvkd(); 

	public void setDvkd(ResultSet dvkd); 
	
	public ResultSet getDubaoCopy();

	public void setDubaoCopy(ResultSet dubaocopy);
	
	public ResultSet getNsList(); 
	
	public void setNsList(ResultSet nslist);
	
	public String getCopyId(); 
	
	public void setCopyId(String copyId); 

	public void Copy();
	
	public String getDateTime();	
	
	public void close();
}
