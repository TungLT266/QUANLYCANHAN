package geso.traphaco.erp.beans.lapngansach;

import java.sql.ResultSet;

public interface ILapngansachCPList {
	public String getId();
	
	public void setId(String Id);
	
	public String getUserId();
	
	public void setUserId(String userId);

	public String getNgay();
	
	public void setNgay(String ngay);
	
	public String getCtyId();
	
	public void setCtyId(String ctyId);

	public String getDiengiai();
	
	public void setDiengiai(String diengiai);
	
	public void createBudget();
	
	public ResultSet getCtylist();
	
	public void setCtylist(ResultSet ctylist);
	
	public String getCopyId();
	
	public void setCopyId(String copyId);
	
	public ResultSet getLapngansachCPRs();

	public String getMsg();
	
	public void setMsg(String msg);

	public ResultSet getNgansach();
	
	public void setNgansach(ResultSet nslist);
	
	public void init();
	
	public void initUpdate();
	
	public void Delete(String Id);
	
	public String getNamNew();
	
	public String getNamUpdate();

	public String getNam();
	
	public void setNam(String nam);
	
	public String getDateTime();
	
	public String getHieuluc();
	
	public void setHieuluc(String hieuluc);
	
	public void Copy();
	
	public String getDvkdId(); 
	
	public void setDvkdId(String dvkdId); 
	
	public ResultSet getDvkd(); 
	
	public void setDvkd(ResultSet dvkdRs); 
	
	public void DBClose();

}
