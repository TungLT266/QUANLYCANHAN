package geso.traphaco.distributor.beans.dangkykhuyenmaitichluy;

import java.sql.ResultSet;
import java.util.Hashtable;

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
	public boolean MoChot();
	
	public boolean LuuDuyetTra();
	public boolean ChotDuyetTra();
	
	public void createRs();
	public void init();
	public void initDuyet();

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
	public String[] getKhDoanhsoDAT();
	public void setKhDoanhsoDAT(String[] khDoanhsoDAT);
	
	public String[] getKhXuatdat();
	public void setKhXuatdat(String[] khXuatdat);
	public String[] getKhXuatduyet();
	public void setKhXuatduyet(String[] khXuatduyet);
	public String[] getKhHinhthuctra();
	public void setKhHinhthuctra(String[] khHinhthuctra);
	
	public void LayDoanhSoDat();
	public Hashtable<String, String> getDoanhsoDat_Upload();
	public Hashtable<String, String> setDoanhsoDat_Upload(Hashtable<String, String> dsDat);
	
	
}
