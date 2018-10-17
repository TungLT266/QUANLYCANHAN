package geso.traphaco.distributor.beans.phanbogiaonhan;

import java.io.Serializable;

import geso.traphaco.distributor.beans.phanbogiaonhan.IPhanbogiaonhan;

import java.sql.ResultSet;
import java.util.List;

public interface IPhanbogiaonhanList extends Serializable
{
	public String getUserId();
	public void setUserId(String userId);
	
	public String getTennv();
	public void setTennv(String tennv);	
	public String getNgay();
	public void setNgay(String ngay);
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public ResultSet getNvgnRs();
	public void setNvgnRs(ResultSet nvgnRs);
	public String getDiengiai();
	public void setDiengiai(String diengiai);
	public String getCongtyId();
	public void setCongtyId(String congtyId);
	public String getNppId();
	public void setNppId(String nppId);
	public String getNppTen();
	public void setNppTen(String nppTen);
	public String getSitecode();
	public void setSitecode(String sitecode);
	
	public String getNvgnId();
	public void setNvgnId(String nvgnId);
	
	public List<IPhanbogiaonhan> getPbgnList();
	public void setPbgnList(List<IPhanbogiaonhan> pbgnList);
	
	public void init(String search);
	public void DBclose();
	
	//PHÂN QUYỀN THEO LOẠI NHÂN VIÊN ĐĂNG NHẬP
	public String getLoainhanvien();
	public void setLoainhanvien(Object loainhanvien);
	public String getDoituongId();
	public void setDoituongId(Object doituongId);
	public ResultSet getPbgnRs();

	public void setPbgnRs(ResultSet pbgnRs);
	
}