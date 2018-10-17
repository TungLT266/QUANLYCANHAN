package geso.traphaco.distributor.beans.hopdong;

public interface IErpPhieumuahangNpp
{
	public String getUserId();
	public void setUserId(String userId);
	
	public String getId();
	public void setId(String Id);
	
	public String getMaphieu();
	public void setMaphieu(String maphieu);
	
	public String getTungay();
	public void setTungay(String tungay);
	public String getDenngay();
	public void setDenngay(String denngay);

	public String getSoluong();
	public void setSoluong(String soluong);
	public String getGiatri();
	public void setGiatri(String giatri);
	
	public String getGhichu();
	public void setGhichu(String ghichu);
	
	public String getMsg();
	public void setMsg(String msg);
	
	public String getNppId();
	public void setNppId(String nppId);
	public String getNppTen();
	public void setNppTen(String nppTen);
	public String getSitecode();
	public void setSitecode(String sitecode);
	
	public boolean create();
	public boolean update();
	
	public void createRs();
	public void init();
	
	public void DBclose();
	
}
