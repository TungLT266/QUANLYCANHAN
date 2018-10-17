package geso.traphaco.distributor.beans.inchietkhautruckthang;

import java.io.Serializable;
import java.sql.ResultSet;


public interface IInKhauTruCKThang extends Serializable
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
	public String getKhId();
	public void setKhId(String KhId);
	
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
	
	public String getnppId();
	public void setnppId(String nppID) ;//Trung tâm (lấy chi nhánh đối tác)
	
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
	
	public void DBclose();
	public void setQuery(String searchQuery);

	public String getLoaiNv();
	public void setLoaiNv(String loaiNv);
	
	public void setPhanloai(String pl);
	public String getPhanloai();
	
	public String getDdkdId();
	public void setDdkdId(String ddkdId);
	
	public ResultSet getDdkdRs();
	public void setDdkdRs(ResultSet ddkdRs);
	

	public String getNvgnId();
	public void setNvgnId(String nvgnId);
	public ResultSet getNvgnRs();
	public void setNvgnRs(ResultSet nvgnRs);
	
	public void setvungId(String vungId);
	public String getvungId();

	public void setvung(ResultSet vung);
	public ResultSet getvung();
	
	public void setkhuvucId(String khuvucId);
	public String getkhuvucId();

	public void setkhuvuc(ResultSet khuvuc);
	public ResultSet getkhuvuc();
	
	public ResultSet getTtRs();
	public void setTtRs(ResultSet ttRs);
	
	public String getTtId();
	public void setTtId(String ttId);
	
	public void setKhIds(String khIds);
	public String getKhIds();
	
	public String getXemtheo();
	public void setXemtheo(String xemtheo);
}
