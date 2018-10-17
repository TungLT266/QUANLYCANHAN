package geso.traphaco.erp.beans.doanhthu;

import java.sql.ResultSet;

public interface IMucdoanhthu {
	public String getUserId();
	
	public void setUserId(String userId);
	
	public String getId();
	
	public void setId(String Id);
	
	public String getParentId();
	
	public void setParentId(String parentId);

	public String getCtyId();
	
	public void setCtyId(String ctyId);

	public String getDvttId();
	
	public void setDvttId(String ctyId);	
	
	public String getTen();
	
	public void setTen(String ten);

	public String getDiengiai();
	
	public void setDiengiai(String diengiai);
	
	public String[] getLoai();
	
	public void setLoai(String[] loai);

	public String getTrangthai();
	
	public void setTrangthai(String trangthai);
	
	public String getCongty();

	public void setCongty(String congty);
	
	public void setDonvi(String donvi);

	public String getDonvi();
	
	public ResultSet getTkList();
	
	public ResultSet getTtdtList();
	
	public ResultSet getNhomList();
	
	public String getTkId();

	public void setTkId(String tkId);
	
	public String getTtdtId();

	public void setTtdtId(String ttdtId);	
	
	public void init();
	
	public void initNew();
	
	public boolean Update();
	
	public boolean New();
		
	public void createDvttList();
	
	public ResultSet getDvttList();
	
	public void createTkList();
	
	public void createTtdtList();
	
	public void DBClose();
	
	public String getMsg();
	
	public void setMsg(String msg);
}