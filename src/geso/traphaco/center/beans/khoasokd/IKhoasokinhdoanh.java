package geso.traphaco.center.beans.khoasokd;

import java.sql.ResultSet;
import java.util.Hashtable;

public interface IKhoasokinhdoanh {
	
	
	public String getUserId();
	public void setUserId(String userId);
	
	public String getMsg();
	public void setMsg(String msg);

	public void init();
	public void createRs();
	public boolean updateKhoasokd();
	
	public void SetGioTT(String gio);
	public String getGioTT();
	
	public void SetPhutTT(String phuttt);
	public String getPhutTT();
	
	public void SetTrangthai1(String trangthai1);
	public String getTrangthai1();
	
	public void SetTrangthai2(String trangthai2);
	public String getTrangthai2();
	
	public void SetTrangthai3(String trangthai3);
	public String getTrangthai3();
	
	public void SetGioNPP(String gio);
	public String getGioNPP();
	
	public void SetPhutNPP(String gio);
	public String getPhutNPP();
	
	public void SetGioCTV(String gio);
	public String getGioCTV();
	public void SetPhutCTV(String gio);
	public String getPhutCTV();
	
	public void SetNgayKSKDTT(String ngayKsTT);
	public String getNgayKSKDTT();
	
	public void DBclose();
	
	public void SetNgayKSKDNPP(String NgayKsNpp);
	public String GetNgayKSKDNPP();
	
	public void SetNgayKSKDCTV(String NgayKsCTV);
	public String GetNgayKSKDCTV();
	
	public String getActiveTab();
	public void setActiveTab(String tab);
	
}
