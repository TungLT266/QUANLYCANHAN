package geso.traphaco.distributor.beans.hoadontaichinh;

import java.io.Serializable;
import java.sql.ResultSet;


public interface IBCChietKhauThang extends Serializable
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
	
	
	public String getTdvId();
	public void setTdvId(String tdvId);
	
	public ResultSet getTdvRs();
	public void setTdvRs(ResultSet tdvRs);
	
	public String getType();
	public void setType(String type);
	public String GetTichLuyThangCu(String nppId, String ngay);
	public String getngayCUOITHANG(String ngaygiaodich) ;
	public double condulai(int sodong1,int daduyet1,double tongDSThangtruoc1,double tongTHUVEThangtruoc1,String loaiNPP1,String xuatkhau1,String thanhtoan1,String PT_CHIETKHAU1,String duno_dauky1,Double DsHHBG1,String ngaynhap1,String khid1);

	
}
