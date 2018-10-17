package geso.traphaco.erp.beans.lapkehoach;

import java.sql.ResultSet;

public interface IErpXemkehoach 
{
	public String getCtyId(); 
	public void setCtyId(String ctyId); 

	public String getDvkdId();

	public void setDvkdId(String dvkdId);

	public ResultSet getDvkdRs();

	public void setDvkdRs(ResultSet dvkd);	
	
	public String getUserId();
	public void setUserId(String userId);
	public String getId();
	public void setId(String Id);
	
	public ResultSet getThangRs();
	public void setThangRs(ResultSet thangRs);
	
	public String getThang();
	public void setThang(String thang);
	
	public ResultSet getNamRs();
	public void setNamRs(ResultSet namRs);
	
	public String getNam();
	public void setNam(String nam);
			
	public ResultSet getKhoTTRs();
	public void setKhoTTRs(ResultSet khoRs);
	
	public String getKhoId();
	public void setKhoId(String khoIds);

	public ResultSet getNhanhangRs();
	public void setNhanhangRs(ResultSet nhRs);

	public String getNhId();
	public void setNhId(String nhId);
	
	public ResultSet getChungloaiRs();
	public void setChungloaiRs(ResultSet clRs);
	
	public String getClId();
	public void setClId(String clId);
	
	public ResultSet getSpRs();
	public void setSpRs(ResultSet spRs);
	
	public String getSpId();
	public void setSpId(String spId);
	
	public String getDonvi();
	public void setDonvi(String donvi);
	
	public String getTondau();
	public void setTondau(String tondau);
	
	public String getMsg();
	public void setMsg(String msg);
	
	public String getNgayhientai(); 

	public void setNgayhientai(String ngayhientai); 

	public void createRs();
	
	public ResultSet getYeuCau(int thang, int nam);
	
	public String getNgaycuoithang(String thang, String nam);
	
	public ResultSet getData(String tungay, String denngay);
	public ResultSet getDataNL(String tungay, String denngay);
	
	public String getLoaiId(); 

	public void setLoaiId(String loaiId);
	
	public void DbClose();
}
