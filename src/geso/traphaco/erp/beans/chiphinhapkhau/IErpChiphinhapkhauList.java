package geso.traphaco.erp.beans.chiphinhapkhau;

import geso.traphaco.center.util.IThongTinHienThi;

import java.sql.ResultSet;
import java.util.List;

public interface IErpChiphinhapkhauList 
{
	public String getUserId();
	public void setUserId(String userId);

	public String getCongtyId();
	public void setCongtyId(String congtyId);
	
	public String getMa();
	public void setMa(String ma);	
	
	public String getDiengiai();
	public void setDiengiai(String diengiai);
	
	public String getSotokhai();
	public void setSotokhai(String Sotokhai);
	
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public String getMsg();
	public void setMsg(String msg);
	
	public ResultSet getChiphinhapkhauRs();
	public void setChiphinhapkhauRs(ResultSet cpnkRs);
	
	public void setNcc(String ncc); 
	
	public String getNcc();
	
	public void setNccId(String nccId); 
	
	public String getNccId();

	public void setPoId(String poId); 
	
	public String getPoId();
	
	public void init(String query);
	public void DbClose();
	
	public List<IThongTinHienThi> getHienthiList();
	public void setHienthiList(List<IThongTinHienThi> hienthiList);	
	public ResultSet getDinhkhoan(String Id);
	
	public String getTungay();
	public void setTungay(String tungay);
	
	
	public String getDenngay();
	public void setDenngay(String denngay);
	


	public ResultSet getNguoitaoRs();

	public void setNguoitaoRs(ResultSet nguoitaoRs);
	
	public String getNguoitaoId();
	public void setNguoitaoId(String nguoitaoId);
	
	public String getTusotien() ;
	public void setTusotien(String tusotien);

	public String getDensotien();

	public void setDensotien(String densotien);
}
