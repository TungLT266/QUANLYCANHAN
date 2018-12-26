package qlcn.pages.vayno.beans;

import java.sql.ResultSet;

import qlcn.center.util.IPhan_Trang;

public interface IVayNoList extends IPhan_Trang {
	public void init();
	public void chot(String id);
	public void unChot(String id);
	public void unNhantra(String id);
	public void delete(String id);
	public void deleteDB(String pinUser);
	public void DBClose();
	
	public String getUserId();
	public void setUserId(String userId);
	
	public String getID();
	public void setID(String iD);
	
	public String getMsg();
	public void setMsg(String msg);

	public String getSoItems();
	public void setSoItems(String soItems);

	public String getLoai();
	public void setLoai(String loai);

	public String getNguoivayno();
	public void setNguoivayno(String nguoivayno);

	public String getNoidung();
	public void setNoidung(String noidung);

	public String getTungay();
	public void setTungay(String tungay);

	public String getDenngay();
	public void setDenngay(String denngay);

	public String getSotientu();
	public void setSotientu(String sotientu);

	public String getSotienden();
	public void setSotienden(String sotienden);
	
	public ResultSet getVaynoRs();
	public void setVaynoRs(ResultSet vaynoRs);
	
	public String getTrangthai();
	public void setTrangthai(String trangthai);
}
