package geso.traphaco.erp.beans.thanhlytaisan;

import geso.traphaco.center.util.IPhan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;

public interface IErpThanhlytaisanList extends Serializable, IPhan_Trang
{
	public String getCongtyId();
	public void setCongtyId(String congtyId);
	
	public String getUserId();
	public void setUserId(String userId);
	
	public String getCtyId();

	public void setCtyId(String ctyId);
	
	public String getCty();
	
	public void setCty(String cty);

	public String getTungay();
	public void setTungay(String tungay);
	public String getDenngay();
	public void setDenngay(String denngay);
	public String getLoai(); 
	public void setLoai(String loai); 
	
	public String getKH();
	public void setKH(String kh);
	public String getTongtiensauVat();
	public void setTongtiensauVat(String ttsauvat);
	public void setmsg(String msg);
	public String getmsg();
	public ResultSet getDtltsList();
	public void setDtltsList(ResultSet dtltslist);
	
	public String getTask();
	public void setTask(String task);
	
	public String getSodonthanhlytaisan();
	public void setSodonthanhlytaisan(String sodonthanhlytaisan);
	
	
	//Bao cao don mua hang
	public ResultSet getKhRs();
	public void setKhRs(ResultSet khRs);
	public void setKhIds(String khIds);
	public String getKhIds();
	
	public String getSoHD();
	public void setSoHD(String soHD);
	public String getNgayHD();
	public void setNgayHD(String ngayHD);
	
	public void initBaoCao();
	
	public void init(String search);
	public void DBclose();
}
