package geso.traphaco.distributor.beans.nhomkhachhang;
import java.io.Serializable;
import java.util.List;;

public interface INhomkhachhangListNPP extends Serializable {
	
	public String getCongtyId();
	public void setCongtyId(String congtyId);
	
	public String getUserId();
	public void setUserId(String userId);
	
	public String getDiengiai();
	
	public void setDiengiai (String diengiai);

	public String getTrangthai();

	public void setTrangthai(String trangthai);
	
	public String getTungay();
	
	public void setTungay(String tungay);

	public String getDenngay();
	
	public void setDenngay(String denngay);	

	public List<INhomkhachhangNPP> getNkhList();
	
	public void setNkhList(List<INhomkhachhangNPP> nkhlist);
	
	public boolean getSearch();

	public void setSearch(boolean search);

	public void setMaKH(String maKH);
	public String getMaKH();
}

