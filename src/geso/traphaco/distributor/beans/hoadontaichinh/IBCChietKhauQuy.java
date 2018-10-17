package geso.traphaco.distributor.beans.hoadontaichinh;

import java.io.Serializable;
import java.sql.ResultSet;

import javax.servlet.http.HttpServletRequest;

public interface IBCChietKhauQuy extends Serializable
{
	public String getUserId();
	public void setUserId(String userId);

	public String getTungay();
	public void setTungay(String tungay);
	public String getDenngay();
	public void setDenngay(String denngay);
	
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
	public void init2015();
	public void createRs();
	
	public void searchQuery_ETC(String searchquery);
	public void searchQuery_OTC(String searchquery);
	public void searchQuery_KM(String searchquery);
	
	public void DBclose();
	public void setQuery(String searchQuery);

	
	public String getTdvId();
	public void setTdvId(String tdvId);
	
	public ResultSet getTdvRs();
	public void setTdvRs(ResultSet tdvRs);
	
	public String getType();
	public void setType(String type);
	
}
