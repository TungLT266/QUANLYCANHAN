package geso.traphaco.center.beans.hoadontaichinh;

import java.io.Serializable;
import java.sql.ResultSet;

import javax.servlet.http.HttpServletRequest;

public interface ISoathoadon extends Serializable
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
	
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	public String getPtVat();
	public void setPtVat(String ptVat);
	
	public String getMsg();
	public void setMsg(String msg);
	
	public String getLoaidonhang();
	public void setLoaidonhang(String loaidonhang);
	
	public String getNppId();
	public void setNppId(String nppId) ;
	
	public String getNvbhId();
	public void setNvbhId(String nvbhId);
	public ResultSet getNvbhRs();
	public void setNvbhRs(ResultSet nvbhRs);
	
	public String getKyhieuHD();
	public void setKyhieuHD(String kyhieuhd);
	
	public ResultSet getKyhieuHDRs();
	public void setKyhieuHDRs(ResultSet kyhieuhdRs);
	
	public String getMaFast();
	public void setMaFast(String maFAST);
	
	public  double getTongtientruocthue();
	public void setTongtientruocthue(double tongtientruocthue);
	
	public  double getTongtienthue();
	public void setTongtienthue(double tongtienthue);
	
	public  double getTongtienhoadon();
	public void setTongtienhoadon(double tongtienhoadon);
	
	public boolean save(HttpServletRequest request);
	public boolean saveETC(HttpServletRequest request);
	public boolean saveDoihoadon(HttpServletRequest request);
	
	public  int getTongSLHD();
	public void setTongSLHD(int tongSLHD);
	
	public void init();
	public void initETC();
	
	public void createRs();
	
	public void DBclose();
	
	public Boolean getIsHO();
	public void setIsHO(Boolean isHO);
	
	public String getLoaihd() ;

	public void setLoaihd(String loaihd);

}
