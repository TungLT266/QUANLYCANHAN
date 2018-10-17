package geso.traphaco.erp.beans.nhomchiphi;

import java.sql.ResultSet;

public interface INhomchiphi {
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
	
	public String getLoai();
	
	public void setLoai(String loai);

	public String getTrangthai();
	
	public void setTrangthai(String trangthai);
	
	public String getCongty();

	public void setCongty(String congty);
	
	public void setDonvi(String donvi);

	public String getDonvi();
	
	public ResultSet getTkList();
	
	public ResultSet getTtcpList();
	
	public ResultSet getNhomList();
	
	public String getTkId();

	public void setTkId(String tkId);
	
	public String getTtcpId();

	public void setTtcpId(String ttcpId);	
	
	public void init();
	
	public void initNew();
	
	public boolean Update();
	
	public boolean New();
	
	public void createCtyList();
	
	public ResultSet getCtyList();
	
	public void createDvttList();
	
	public ResultSet getDvttList();
	
	public void DBClose();
	
	public String getMsg();
	
	public void setMsg(String msg);
}