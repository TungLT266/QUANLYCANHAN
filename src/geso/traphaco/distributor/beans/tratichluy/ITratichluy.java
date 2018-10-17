package geso.traphaco.distributor.beans.dangkykhuyenmaitichluy;

import java.sql.ResultSet;

public interface IDangkykhuyenmaitichluy
{
	public void setId(String Id);
	public String getId();
	
	public String getUserId();
	public void setUserId(String userId);
	
	public String getNppId();
	public void setNppId(String nppId);
	public void setNppTen(String nppTen);
	public String getNppTen();

	public void setTungay(String tungay);
	public String getTungay();

	public void setDenngay(String denngay);
	public String getDenngay();

	public void setCtkmRs(ResultSet ctkmIds);
	public ResultSet getCtkmRs();
	public void setCtkmId(String ctkmId);
	public String getctkmId();

	public ResultSet getNvBanhang();
	public void setNvBanhang(ResultSet nvbanhang);
	public String getNvbhIds();
	public void setNvbhIds(String nvbdIds);
	
	public void setKhList(ResultSet KhList);
	public ResultSet getKhList();
	public String getKhId();
	public void setKhId(String khId);

	public void setMessage(String Msg);
	public String getMessage();


	public boolean save();
	public boolean edit();
	public boolean Chot();
	public boolean Delete();
	
	public void createRs();
	public void init();

	public void DBclose();
	
	public String getMucdangky();
	public void setMucdangky(String mucdangky);
	
	public String[] getKhMa();
	public void setKhMa(String[] khMa);
	public String[] getKhTen();
	public void setKhTen(String[] khTen);
	public String[] getKhDiachi();
	public void setKhDiachi(String[] khDiachi);
	public String[] getKhSohopdong();
	public void setKhSohopdong(String[] khSohopdong);
	public String[] getKhTungay();
	public void setKhTungay(String[] khTungay);
	public String[] getKhDenngay();
	public void setKhDenngay(String[] khDenngay);
	public String[] getKhDoanhso();
	public void setKhDoanhso(String[] khDoanhso);


}
