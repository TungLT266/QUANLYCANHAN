package geso.traphaco.erp.beans.khoasothang;

import java.sql.ResultSet;
import java.util.List;

public interface IErpCapnhatgianhap 
{
	public String getUserId();
	public void setUserId(String userId);
	public String getId();
	public void setId(String Id);
	
	public String getTungay();
	public void setTungay(String tungay);
	public String getDenngay();
	public void setDenngay(String denngay);
	
	public String getThang();
	public void setThang(String thang);
	public String getNam();
	public void setNam(String nam);
	
	public ResultSet getLoaiSanPhamRs();
	public void setLoaiSanPhamRs(ResultSet loaisp);
	public String getLoaiSanPhamIds();
	public void setLoaiSanPhamIds(String loaispIds);
	
	public ResultSet getPnkRs();
	public void setPnkRs(ResultSet pnkRs);
	public String getPnkIds();
	public void setPnkIds(String pnkIds);
	
	public ResultSet getSanphamRs();
	public void setSanphamRs(ResultSet spRs);
	public String getSpIds();
	public void setSpIds(String pnkIds);
	
	public String getMsg();
	public void setMsg(String msg);
	public String getGhichu();
	public void setGhichu(String ghichu);
	
	public List<ISanpham> getSanphamList();
	public void setSanphamList(List<ISanpham> spList);
	
	public void createRs();
	public void init();
	
	public boolean createCngn();
	public boolean updateCngn();
	public void DbClose();
}
