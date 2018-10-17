package geso.traphaco.erp.beans.canhbaothieuhang;

import java.sql.ResultSet;

public interface IErpKehoachgiaohang 
{
	public String getUserId();
	public void setUserId(String userId);

	public String getCongtyId();
	public void setCongtyId(String congtyId);
	
	public String getSo() ;
	public void setSo(String po);
	
	public String getMaKH();
	public void setMaKH(String maKH);
	
	public String getQuyCach();
	public void setQuyCach(String quyCach);
	
	
	
	
	
	
	public ResultSet getThangRs();
	public void setThangRs(ResultSet thangRs);
	public String getThangId();
	public void setThangId(String thangId);	
	
	public ResultSet getNamRs();
	public void setNamRs(ResultSet namRs);
	public String getNamId();
	public void setNamId(String namId);	
	
	public String getTuNgay();
	public void setTuNgay(String tungay);	
	public String getDenNgay();
	public void setDenNgay(String denngay);	
	
	public ResultSet getLoaispRs();
	public void setLoaispRs(ResultSet loaispRs);
	public String getLoaispId();
	public void setLoaiId(String loaispId);	
	
	public ResultSet getSanphamRs();
	public void setSanphamRs(ResultSet spRs);
	public String getspIds();
	public void setspIds(String spIds);	
	
	public String getMsg();
	public void setMsg(String msg);
	
	public ResultSet getCanhbaoRs();
	public void setCanhbaoRs(ResultSet canhbaoRs);
	
	public void init();
	public void DbClose();
	
}
