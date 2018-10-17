package geso.traphaco.center.beans.hoadontaichinh;

import java.io.Serializable;
import java.sql.ResultSet;

public interface IBCHoadonbanra extends Serializable
{
	public String getUserId();
	public void setUserId(String userId);

	public String getTungay();
	public void setTungay(String tungay);
	public String getDenngay();
	public void setDenngay(String denngay);
	
	public String getSohoadontu();
	public void setSohoadontu(String sohoadontu);
	public String getSohoadonden();
	public void setSohoadonden(String sohoadonden);
	
	public String getNppTen();
	public void setNppTen(String nppTen);
	public ResultSet getNppRs();
	public void setNppRs(ResultSet nppRs);
	
	public String getKhTen();
	public void setKhTen(String KhTen);
	public ResultSet getKhRs();
	public void setKhRs(ResultSet KhRs);
	
	public ResultSet getHoadonRs();
	public void setHoadonRs(ResultSet hdRs);
	
	public ResultSet getHoadon10PtRs();
	public void setHoadon10PtRs(ResultSet hd10PtRs);
	
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	public String getPtVat();
	public void setPtVat(String ptVat);
	
	public String getMsg();
	public void setMsg(String msg);
	
    public String getActiveTab();
    public void setActiveTab(String active);
    
    public ResultSet getETCRs();
	public void setETCRS(ResultSet ETCRs);
	public ResultSet getOTCRs();
	public void setOTCRS(ResultSet OTCRs);
	public ResultSet getKMRs();
	public void setKMRS(ResultSet KMRs);
	public ResultSet getHDKhacRs();
	public void setHDKhacRS(ResultSet HDKhacRs);
	
	
    
	public String getVung();
	public void setVung(String vung);
	public String getKhuvuc();
	public void setKhuvuc(String khuvuc);
	public ResultSet getKhuvucRs();
	public void setKhuvucRs(ResultSet khuvucRs);
	
	
	public ResultSet getnppRs();
	public void setnppRs(ResultSet nppRs);
	
	
	public String getLoaidonhang();
	public void setLoaidonhang(String loaidonhang);
	
	public String getNppId();
	public void setNppId(String nppId) ;
	
	public String getNvbhId();
	public void setNvbhId(String nvbhId);
	public ResultSet getNvbhRs();
	public void setNvbhRs(ResultSet nvbhRs);
	
	public String getMaFast();
	public void setMaFast(String maFAST);
	
	public String getFormatDate(String date);
	
	public void init();
	public void createRs();
	
	public void searchQuery_ETC(String searchquery);
	public void searchQuery_OTC(String searchquery);
	public void searchQuery_KM(String searchquery);
	public void searchQuery_HDKhac(String searchquery);
	
	public void search_NPP();
	
	public void DBclose();
	public void setQuery(String searchQuery);

	public String getView();
	public void setView(String view);
	
	

	
	public void setTotal_Query(String searchquery);
	public ResultSet getTotalRs();
	public void setTotalRs(ResultSet totalRs);
	
	
	public String getMuclay();
	public void setMuclay(String muclay);
	
	public String getTdvId();
	public void setTdvId(String tdvId);
	
	
	public ResultSet getTdvRs();
	public void setTdvRs(ResultSet tdbRs);
	
	public String getKhoId();
	public void setKhoId(String khoId);
	
	public ResultSet getKhoRs();
	public void setKhoRs(ResultSet khoRs);
	
	
	public String getMucCN_DT();
	public void setMucCN_DT(String cndt);
	
	public String getMuc_KhachHang();
	public void setMuc_KhachHang(String kh);
	public String getCn();

	public void setCn(String cn);
	
}
