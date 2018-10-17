package geso.traphaco.erp.beans.xoakhachhangtt;

import geso.traphaco.center.util.IPhan_Trang;
import geso.traphaco.center.util.IThongTinHienThi;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.List;

public interface IErpXoakhachhangttList extends Serializable, IPhan_Trang
{
	public String getUserId();
	public void setUserId(String userId);

	public String getTungay();
	public void setTungay(String tungay);
	public String getDenngay();
	public void setDenngay(String denngay);
	
	public String getNccId();
	public void setNccId(String nccid);
	
	public String getMaPhieu();
	public void setMaPhieu(String maphieu);
	
	public ResultSet getNccList();
	public void setNccList(ResultSet ncclist);

	public String getHtttId();
	public void setHtttId(String htttid);
	public ResultSet getHtttList();
	public void setHtttList(ResultSet htttlist);
	
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public void setmsg(String msg);
	public String getmsg();
	
	public ResultSet getTThoadonList();
	public void setTThoadonList(ResultSet tthdlist);
	
	public ResultSet getxnttList();
	public void setxnttList(ResultSet xnttList);
	
	public void init(String search);
	public void DBclose();
	
	public List<IThongTinHienThi> getHienthiList();
	public void setHienthiList(List<IThongTinHienThi> hienthiList);
	
	public String getKbhId();
	public void setKbhId(String Kbhid);
	public ResultSet getKbhRs();
	public void setKbhRs(ResultSet KbhRs);
	
	public String getNhomkhId();
	public void setNhomkhId(String Nhomkhid);
	public ResultSet getNhomkhRs();
	public void setNhomkhRs(ResultSet NhomkhRs);
	
	public String getSotien();
	public void setSotien(String Sotien);
	
	public String getCongtyId();
	public void setCongtyId(String CongtyId);
	
	//PHÂN QUYỀN THEO LOẠI NHÂN VIÊN ĐĂNG NHẬP
	public String getLoainhanvien();
	public void setLoainhanvien(Object loainhanvien);
	public String getDoituongId();
	public void setDoituongId(Object doituongId);
	
	public String getnppdangnhap();
	public void setnppdangnhap(String nppdangnhap);
	
}
