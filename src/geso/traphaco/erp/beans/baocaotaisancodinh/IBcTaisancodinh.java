package geso.traphaco.erp.beans.baocaotaisancodinh;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public interface IBcTaisancodinh
{
	public void setuserId(String userId);

	public String getuserId();
	
	public void setCtyId(String ctyId);
	
	public String getCtyId();
	
	public String getCtyTen();

	public String getDiachi();
	
	public String getMasothue();
	
	public void setTungay(String tungay);
	
	public String getTungay();

	public String getDenngay();
	
	public void setDenngay(String denngay);

	public String getThang();

	public void setThang(String thang);
	
	public String getNam();

	public void setNam(String nam);
	
	public void setMsg(String msg);

	public String getMsg();
	public void setLoai(String loai);

	public String getLoai();

	public String getLtsId();

	public void setLtsId(String ltsId);

	public String getTsId();
	
	public void setTsId(String tsId);
	
	public ResultSet getTaisan();
	
	public ResultSet getLoaiTS();
	
	public ResultSet getKhauhao();
	
	
	public ResultSet getTscdlist();
	
	public void init_TheodoiTSCD();

	public void init();

	public String getDate();
	
	public void DBClose();
}