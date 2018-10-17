package geso.traphaco.erp.beans.thietbisx;

import java.sql.ResultSet;

public interface IErpThietBiSXThongSo {
	public String getUserId();
	public void setUserId(String userId);
	
	public String getCongtyId();
	public void setCongtyId(String congtyId);
	
	public String getId();
	public void setId(String id);
	
	public String getMa();
	public void setMa(String ma);
	
	public String getTen();
	public void setTen(String ten);
	
	public String getTrangThai();
	public void setTrangThai(String trangThai);
	
	public String getMsg();
	public void setMsg(String msg);
	
	public String[] getTsycTu();
	public void setTsycTu(String[] tsycTu);
	
	public String[] getTsycDen();
	public void setTsycDen(String[] tsycDen);
	
	public String[] getDvdlFk();
	public void setDvdlFk(String[] dvdlFk);
	
	public String[] getCheck();
	public void setCheck(String[] check);
	
	public ResultSet getDvtRs();
	public void setDvtRs(ResultSet dvtRs);
	
	public void init();
	
	public void creaters();
	
	public void DbClose();
	
	public boolean CheckUnique();
	
	public boolean create();
	
	public String[] getDienGiai();
	public void setDienGiai(String[] dienGiai);
	
	public String getTscdFk();
	public void setTscdFk(String tscdFk);
	
	public ResultSet getTscdRs();
	public void setTscdRs(ResultSet tscdRs);
	
	public boolean update();
	
	public String getStt();
	public void setStt(String stt);
	
	public String getIsChangeMa();
	public void setIsChangeMa(String isChangeMa);
}
