package geso.traphaco.distributor.beans.cttongketnapchai;

import java.sql.ResultSet;
import java.util.Hashtable;

public interface ICttongketnapchai
{
	public void setId(String Id);
	public String getId();
	
	public String getUserId();
	public void setUserId(String userId);
	
	public String getMa();
	public void setMa(String ma);
	
	public String getDiengiai();
	public void setDiengiai(String diengiai);
	
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public String getNgaytao();
	public void setNgaytao(String ngaytao);
	
	public String getNgaysua();
	public void setNgaysua(String ngaysua);
	
	public String getNguoitao();
	public void setNguoitao(String nguoitao);
	
	public String getNguoisua();
	public void setNguoisua(String nguoisua);	
	
	public String getTungay();
	public void setTungay(String tungay);
	public String getDenngay();
	public void setDenngay(String denngay);
	public String getSoluong();
	public void setSoluong(String soluong);
	public String getNppId();
	public void setNppId(String nppId);
	public void setNppTen(String nppTen);
	public String getNppTen();

	public ResultSet getNvBanhang();
	public void setNvBanhang(ResultSet nvbanhang);
	public String getNvbhIds();
	public void setNvbhIds(String nvbdIds);
	
	public void setKhList(ResultSet KhList);
	public ResultSet getKhList();
	public String getKhId();
	public void setKhId(String khId);

	public void setSpRS(ResultSet SpRS);
	public ResultSet getSpRS();
	public String getSpId();
	public void setSpId(String SpId);
	
	public Hashtable<String, String> getKhSoluonHtb();
	public void setKhSoluonHtb(Hashtable<String, String> khsoluonghtb);
	
	public void setMessage(String Msg);
	public String getMessage();


	public boolean save();
	public boolean edit();
//	public boolean Chot();
	public boolean Delete();
	
	public void createRs();
	public void init();

	public void DBclose();


}
