package geso.traphaco.center.beans.baocao;

import java.sql.ResultSet;

public interface IBaocao 
{
	public ResultSet getChungloaiRs();
	public void setChungloaiRs(ResultSet clRs);
	public String getChungloaiIds();
	public void setChungloaiIds(String loaispIds);
	
	public ResultSet getLoaiSanPhamRs();
	public void setLoaiSanPhamRs(ResultSet loaisp);
	public String getLoaiSanPhamIds();
	public void setLoaiSanPhamIds(String loaispIds);
	
	public ResultSet getSanPhamRs();
	public void setSanPhamRs(ResultSet sanpham);
	public String getSanPhamIds();
	public void setSanPhamIds(String spIds);
	
	public ResultSet getKhoRs();
	public void setKhoRs(ResultSet khoRs);
	public String getKhoIds();
	public void setKhoIds(String khoId);
	public String getKhoTen();
	public void setKhoTen(String khoTen);
	
	public String getUserId();
	public void setUserId(String userId);
	public String getUserTen();
	public void setUserTen(String userTen);
	
	public String getTuNgay();
	public void setTuNgay(String tungay);
	public String getDenNgay();
	public void setDenNgay(String denngay);
	
	public String getFlag();
	public void setFlag(String flag);
	public String getMsg();
	public void setMsg(String msg);
	
	public void createRs();
	public void close();
}
