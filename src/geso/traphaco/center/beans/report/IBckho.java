package geso.traphaco.center.beans.report;

import geso.traphaco.center.util.IPhan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;

public interface IBckho extends IPhan_Trang, Serializable
{
	public String getUserId();
	public void setUserId(String userId);
	
	public String getUserTen();
	public void setUserTen(String userTen);
	
	public String getNhomkenhId();
	public void setNhomkenhId(String nhomkenhId);
	public ResultSet getNhomkenhRs();
	public void setNhomkenhRs(ResultSet nhomkenhRs);
	
	public String getKhoId();
	public void setKhoId(String khoId);
	public ResultSet getKhoRs();
	public void setKhoRs(ResultSet khoRs);
	
	public void setLoaikhoRs(ResultSet loaikhoRs);
	public ResultSet getLoaikhoRs();
	public String getLoaikho();
	public void setLoaikho(String loaikho);
	
	public String getDoituongId();
	public void setDoituongId(String doituongId);
	public ResultSet getDoituongRs();
	public void setDoituongRs(ResultSet doituongRs);
	
	public String getLaytatca();
	public void setLaytatca(String laytatca);
	
	public String getLaysolo();
	public void setLaysolo(String laysolo);
	
	public String getMsg();
	public void setMsg(String msg);
	
	public void createRs();
	public String getDateTime();
	
	public void DBclose();
}
