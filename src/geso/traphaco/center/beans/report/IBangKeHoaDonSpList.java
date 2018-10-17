package geso.traphaco.center.beans.report;

import geso.traphaco.center.util.IPhan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;

import org.w3c.dom.NodeList;

public interface IBangKeHoaDonSpList extends IPhan_Trang, Serializable
{
	public String getTuNgay();
	public void setTuNgay(String denngay);
	
	public String getDenNgay();
	public void setDenNgay(String denngay);
	
	public String getUserTen() ;
	public void setUserTen(String userTen);

	public String getNppId();
	public void setNppId(String nppId);
	
	public ResultSet getNppRs();
	public void setNppRs(ResultSet nppRs);
	
	
	public String getUserId();
	public void setUserId(String userId);
	
	public String getSpId();
	public void setSpId(String spId);
	
	public String getDdkdId();
	public void setDdkdId(String ddkdId);
	
	public String getKhId();
	public void setKhId(String khId);
	
	public ResultSet getSpRs();
	public void setSpRs(ResultSet spRs);
	
	public ResultSet getKhRs();
	public void setKhRs(ResultSet khRs);
	
	
	
	public ResultSet getDdkdRs();
	public void setDdkdRs(ResultSet ddkdRs);
	
	public ResultSet getHoadonRs();
	public void setHoadonRs(ResultSet hoadonRs) ;
	
	public String getView();
	public void setView(String view);
	
	public void closeDB();

	public void createRs();
	
	
	public String getMsg();
	public void setMsg(String msg);
	
	public String getKbhId();
	public void setKbhId(String kbhId);
	
	public ResultSet getKbhRs();
	public void setKbhRs(ResultSet kbhRs);	

	public void init(String search);
	
	public String getQueryHd();
	public void setQueryHd(String query);
	
	public void setTotal_Query(String searchquery);


	public ResultSet getTotalRs();
	public void setTotalRs(ResultSet totalRs);
	
	public String getKvId();
	public void setKvId(String kvId);
	
	public ResultSet getKvRs();
	public void setKvRs(ResultSet kvRs);
	
	public String getVungId();
	public void setVungId(String vungId);
	
	public ResultSet getVungRs();
	public void setVungRs(ResultSet vungRs);
	
	public String getTtId();
	public void setTtId(String ttId);
	
	public ResultSet getTtRs();
	public void setTtRs(ResultSet ttRs);
	
	public String getNhomId();
	public void setNhomId(String nhomId);
	
	public ResultSet getNhomRs();
	public void setNhomRs(ResultSet nhomRs);
	
	
	public String getLoaiHoaDon();
	public void setLoaiHoaDon(String loaiHoaDon);
	
	public String getAction();
	public void setAction(String action);
	
	
	public String getMucCN_DT();
	public void setMucCN_DT(String cndt);
	
	public String getMuc_KhachHang();
	public void setMuc_KhachHang(String kh);
	
	public String getNhomhangId();
	public void setNhomhangId(String value);
	public ResultSet getNhomhangRs();
	public String getLaynk();
	public void setLaynk(String laynk) ;
	public ResultSet getRsnhomhangERPid();
	public void setRsnhomhangERPid(ResultSet rsnhomhangERPid);
	public String getNhomhangERPid();
	public void setNhomhangERPid(String nhomhangERPid);
	
	public String getQuerytong();
	public void setQuerytong(String querytong) ;
	public String getNhomkhERPid();
	public void setNhomkhERPid(String nhomkhERPid);
	public ResultSet getRsnhomkhERPid();
	public void setRsnhomkhERPid(ResultSet rsnhomkhERPid);
	
	public ResultSet getHoadonrsnhomhang() ;
	public void setHoadonrsnhomhang(ResultSet hoadonrsnhomhang);
	public String getLoai();
	public void setLoai(String loai);
	public String getQuerynh();
	public void setQuerynh(String querynh);
}
