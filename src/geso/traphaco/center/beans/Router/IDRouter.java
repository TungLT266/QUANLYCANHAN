package geso.traphaco.center.beans.Router;

import java.sql.ResultSet;

public interface IDRouter {
   
	public void setkhuvucId(String khuvucId);
	public void setnppId(String nppId);
	public String getkhuvucId();
	public String getnppId();
	public void setddkdId(String ddkdId);
	public String getddkdId();
	public ResultSet getkhuvuc();
	public ResultSet getnpp();
	public ResultSet getddkd();
	public void setKenhId(String kenhId);
	public String getkenhId();
	public void settuyenId(String tuyenId);
	public String gettuyenId();
	public ResultSet getdanhsach();
	public ResultSet getTuyen();
	public ResultSet getKenh();
	public void init();
	public void DBclose();
	public void createNPP();
	public void setStatus(String status);
	public String getStatus();
	
	public String getUserId();
	public void setUserId(String userId);
	
	public String getPhanloai();
	public void setPhanloai(String phanloai);
	
	public String getLoaiNv();
	public void setLoaiNv(String loaiNv);
	
	
	
}
