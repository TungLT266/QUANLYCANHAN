package geso.traphaco.distributor.beans.phanbogiaonhan;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.Hashtable;

public interface IPhanbogiaonhan extends Serializable
{
	public String getUserId();
	public void setUserId(String userId);
	public String getId();
	public void setId(String id);	
	public String getTennv();
	public void setTennv(String tennv);
	public String getDiengiai();
	public void setDiengiai(String diengiai);
	public String getNgay();
	public void setNgay(String ngay);
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
	public String getNvgnId();
	public void setNvgnId(String nvgnId);
	public String getNvgn_newId();
	public void setNvgn_newId(String nvgn_newId);
	public String getTinhthanhId();
	public void setTinhthanhId(String tinhthanhId);
	public String getQuanhuyenId();
	public void setQuanhuyenId(String quanhuyenId);
	public String getMessage();
	public void setMessage(String msg);
	
	public String getCongtyId();
	public void setCongtyId(String congtyId);
	public String getNppId();
	public void setNppId(String nppId);
	public String getNppTen();
	public void setNppTen(String nppTen);
	public String getSitecode();
	public void setSitecode(String sitecode);
	public ResultSet getNvgnRs();
	public void setNvgnRs(ResultSet nvgnRs);
	public ResultSet getNvgn_newRs();
	public void setNvgn_newRs(ResultSet nvgn_newRs);
	public ResultSet getTinhthanhRs();
	public void setTinhthanhRs(ResultSet tinhthanhRs);
	public ResultSet getQuanhuyenRs();
	public void setQuanhuyenRs(ResultSet quanhuyenRs); 
	
	public String getKhId();
	public void setKhId(String khId);
	public ResultSet getKhRs();
	public void setKhRs(ResultSet khRs);
	public String getDdhId();
	public void setDdhId(String ddhId);
	public ResultSet getDdhRs();
	public void setDdhRs(ResultSet ddhRs);
	
	public boolean CreatePbgn();
	public boolean UpdatePbgn();
	public void createRS();
	public void init();
	public void DBclose();
	
	public String getDenngay();
	public void setDenngay(String denngay);
	
	//PHÂN QUYỀN THEO LOẠI NHÂN VIÊN ĐĂNG NHẬP
	public String getLoainhanvien();
	public void setLoainhanvien(Object loainhanvien);
	public String getDoituongId();
	public void setDoituongId(Object doituongId);
	
}
