package geso.traphaco.erp.beans.chucdanh;

import java.sql.ResultSet;

public interface IChucdanh {
	public String getId();
	
	public void setId(String id);

	public String getUserId();

	public void setUserId(String userId);
	
	public String getChucdanh();
	
	public void setChucdanh(String chucdanh);

	public String getTrangthai();

	public void setTrangthai(String trangthai);

	public String getCtyId();

	public void setCtyId(String ctyId);
	
	public String getNvId();

	public void setNvId(String nvId);
	
	public String getMessage();
	
	public void setMessage(String msg);

	public void setCtyList(ResultSet ctylist);

	public ResultSet getCtyList();
	
	public void setNvList(ResultSet nvlist);
	public void creaters();
	public ResultSet getNvList();
	
	public boolean saveNewChucdanh();
	
	public boolean UpdateChucdanh();
	
	public boolean AllowtoChangeCty();
	
	public void init();	
	
	public void DBClose();
	
	public void setNvQuanly(String nvquanly);
	public String getNvquanly();	
	public void setRsNvquanly(ResultSet Nvquanly);
	public ResultSet getRsNvquanly(); 
	
	public void setDvthId(String dvthId);
	public String getDvthId();	
	public void setRsDvth(ResultSet rsDvth);
	public ResultSet getRsDvth(); 
	public void setRsCty(ResultSet rsCty);
	public ResultSet getRsCty(); 
	
	public void setLoai(String loai);
	public String getLoai();	
	
	public String getisTP();
	public void setisTP(String isTP);
	
	public String getisKTV();
	public void setisKTV(String isKTV);
	
	public String getisKTT();
	public void setisKTT(String isKTT);
	
	public String getDuyetDtvt();
	public void setDuyetDtvt(String duyetdtvt);
}