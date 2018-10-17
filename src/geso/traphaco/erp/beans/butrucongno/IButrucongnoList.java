package geso.traphaco.erp.beans.butrucongno;

import geso.traphaco.center.util.IThongTinHienThi;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.List;

public interface IButrucongnoList extends Serializable
{
	public String getUserId();
	public void setUserId(String userId);
	
	public String getCongtyId();
	public void setCongtyId(String congtyId);
	public String getnppdangnhap();
	public void setnppdangnhap(String nppdangnhap);
	
	public String getTungay();
	public void setTungay(String tungay);
	public String getDenngay();
	public void setDenngay(String denngay);
	
	public String getSohoadon();
	public void setSohoadon(String sohoadon);
	
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public ResultSet getDdkd();
	public void setDdkd(ResultSet ddkd);
	public String getDdkdId();
	public void setDdkdId(String ddkdId);
	
	public String getNppId();
	public void setNppId(String nppId);
	public String getNppTen();
	public void setNppTen(String nppTen);
	public String getSitecode();
	public void setSitecode(String sitecode);
	
	public ResultSet getBtcnList();
	public void setBtcnList(ResultSet btcnlist);
	
	public String getSochungtu();
	public void setSochungtu(String sochungtu);
	
	public String getKHChuyenNo();
	public void setKhChuyenNo(String KHChuyenNo);
	
	public String getKHNhanNo();
	public void setKhNhanNo(String KHNhanNo);
	
	public String getMgs();
	public void setMgs(String Mgs);
	
	public void init(String search);
	public void DBclose();
	
	public List<IThongTinHienThi> getHienthiList();
	public void setHienthiList(List<IThongTinHienThi> hienthiList);
	
	public String getSotien();
	public void setSotien(String Sotien);
}