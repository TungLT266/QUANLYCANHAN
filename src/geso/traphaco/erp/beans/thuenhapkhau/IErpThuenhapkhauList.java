package geso.traphaco.erp.beans.thuenhapkhau;

import geso.traphaco.center.util.IThongTinHienThi;

import java.sql.ResultSet;
import java.util.List;

public interface IErpThuenhapkhauList 
{
	public String getId();
	public void setId(String id);
		
	public String getTungay() ;
	public void setTungay(String tungay);
	public String getSohoadon();
	public void setSohoadon(String sohoadon) ;
	public String getSotokhai() ;
	public void setSotokhai(String sotokhai);
	public String getDenngay();
	public void setDenngay(String denngay) ;
	
	public String getUserId();
	public void setUserId(String userId);

	public String getCongtyId();
	public void setCongtyId(String congtyId);
	
	public String getChungtu();
	public void setChungtu(String Id);	
	
	public String getDiengiai();
	public void setDiengiai(String diengiai);
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public String getMsg();
	public void setMsg(String msg);
	
	public ResultSet getThuenhapkhauRs();
	public void setThuenhapkhauRs(ResultSet tnkRs);
	
	public void setNcc(String ncc); 
	
	public String getNcc();
	
	public void setNccId(String nccId); 
	
	public String getNccId();

	public void setPoId(String poId); 
	
	public String getPoId();

	public void init(String query);
	
	public ResultSet getNccList();
	
	public void DbClose();
	
	public List<IThongTinHienThi> getHienthiList();
	public void setHienthiList(List<IThongTinHienThi> hienthiList);
	
	public String getnppdangnhap();
	public void setnppdangnhap(String nppdangnhap);
	public void setLoaiMh(String loaimh);
	public String getLoaiMh();
}
