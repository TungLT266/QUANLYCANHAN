package geso.traphaco.distributor.beans.tdvdoanhso;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.Hashtable;

import javax.servlet.http.HttpServletRequest;

public interface ITDVDoanhso extends Serializable
{
	//Cac thuoc tinh 
	public String getUserId();
	public void setUserId(String userId);
	public String getId();
	public void setId(String id);
	public String getTen();
	public void setTen(String ten);
	public String getDiachi();
	public void setDiachi(String diachi);
	public String getTpId(); 
	public void setTpId(String tpId); 
	public String getQhId(); 
	public void setQhId(String qhId); 	
	public String getSodienthoai();
	public void setSodienthoai(String sodienthoai);	
	public String getMasothue();
	public void setMasothue(String masothue);	
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	public String getNgaytao();
	public void setNgaytao(String ngaytao);
	public String getNguoitao();
	public void setNguoitao(String nguoitao);
	public String getNgaysua();
	public void setNgaysua(String ngaysua);	
	public String getNguoisua();
	public void setNguoisua(String nguoisua);
	public String getMessage();
	public void setMessage(String msg);
	
	public String getThang();
	public void setThang(String thang);
	
	public String getNam();
	public void setNam(String nam);
	
	
	public String getDoanhso();
	public void setDoanhso(String doanhso);
	
	public String getDoanhsobosung();
	public void setDoanhsobosung(String doanhsobosung);
	
	public String getMck();
	public void setMck(String mck);
	public String getGhcn();
	public void setGhcn(String ghcn);
	public String getKbhId();
	public void setKbhId(String kbhId);
	public String getNppId();
	public void setNppId(String nppId);
	public String getNppTen();
	public void setNppTen(String nppTen);
	
	public String gettype();
	public void settype(String type);
	
	public boolean CreateKh( HttpServletRequest request);
	public boolean UpdateKh( HttpServletRequest request);
	public void createRS();
	public void init();
	public void DBclose();
	
	public String getDdkdId();
	public void setDdkdId(String ddkdId);

	public ResultSet getDdkdRs();
	public void setDdkdRs(ResultSet ddkdRs);
	
	public String getKhachhangId();
	public void setKhachhangId(String khId);
	
	public ResultSet getTbhRs();
	public void setTbhRs(ResultSet tbhRs);	
	
	public ResultSet getKh_DsRs();
	public void setKh_DSRs(ResultSet khrs);	
	

	
}
