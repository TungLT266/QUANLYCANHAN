package geso.traphaco.center.beans.lotrinh;

import java.sql.ResultSet;

public interface ILoTrinh {
   
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

	public String getTungay();
	public void setTungay(String tungay);
	public String getDenngay();
	public void setDenngay(String denngay);
	
	
	public String getLoaiNv();
 
	public void setLoaiNv(String loaiNv);

	public String getPhanloai();


	public void setPhanloai(String phanloai);


	public String getUserId();
 

	public void setUserId(String userId);
 
	
	
	public void setVungId(String vungId);
	public String getVungId() ;
	public ResultSet getVungRs() ;
	public void setTinhthanhId(String tinhthanhId) ;
	public String getTinhthanhId() ;
	public ResultSet getTinhthanhRs() ;
	
	
}
