package geso.traphaco.distributor.beans.report;

import java.sql.ResultSet;

public interface Ireport 
{

	public void setuserId(String userId);
	public String getuserId();
	
	public void setuserTen(String userTen);
	public String getuserTen();
		
	public void setnppId(String nppId);
	public String getnppId();
	
	public void setnppTen(String nppTen);
	public String getnppTen();
	
	public void setkenhId(String kenhId);
	public String getkenhId();
	
	public void setdvkdId(String dvkdId);
	public String getdvkdId();
	
	public void setnhanhangId(String nhanhangId);
	public String getnhanhangId();
	
	public void setchungloaiId(String chungloaiId);
	public String getchungloaiId();
	
	public void settungay(String tungay);
	public String gettungay();
	
	public void setdenngay(String denngay);
	public String getdenngay();
	
	public void setkhachhangId(String khachhangId);
	public String getkhachhangId();
	
	public void setkhachhang(ResultSet khachhang);
	public ResultSet getkhachhang();
	public void setMsg(String msg);
	public String getMsg();
	
	public void setkenh(ResultSet kenh);
	public ResultSet getkenh();
	
	public void setddkdId(String ddkdId);
	public String getddkdId();
	
	public void setddkd(ResultSet ddkd);
	public ResultSet getddkd();
	
	
	public void setFieldShow(String[] fieldShow);
	public String[] getFieldShow();
	
	public void setFieldHidden(String[] fieldHidden);
	public String[] getFieldHidden();
	public String getDateTime() ;
	
	public void setngayton(String ngayton);
	public String getngayton();
	
	public void setNppRs(ResultSet nppRs);
	public ResultSet getNppRs();
	

	public String getKvId();
	public void setKvId(String kvId);
	
	public String getVungId();
	public void setVungId(String vungId);
	
	
	public ResultSet getVungRs();
	public void setVungRs(ResultSet vungRs);
		
	public ResultSet getKvRs();
	public void setKvRs(ResultSet kvRs);
	
	
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	public void DBClose();
	public void init();
	
}
