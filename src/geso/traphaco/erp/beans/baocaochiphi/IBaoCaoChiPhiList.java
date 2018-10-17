package geso.traphaco.erp.beans.baocaochiphi;

import geso.dms.center.util.IPhan_Trang;
import geso.traphaco.center.util.Erp_Item;
import geso.traphaco.erp.db.sql.dbutils;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.List;

import javax.servlet.ServletOutputStream;

public interface IBaoCaoChiPhiList  extends Serializable, IPhan_Trang{
	public void init();
	
	public boolean exportExcel(ServletOutputStream out, String fileName);
	
	public void DBClose();
	
	public int getNum();
	public void setNum(int num);
	
	public int[] getListPages() ;
	public void setListPages(int[] listPages);
	
	public int getCurrentPages();
	public void setCurrentPages(int currentPages);
	
	public dbutils getDb();
	public void setDb(dbutils db);

	public void setMsg(String msg);
	public String getMsg();

	public void setCongTyId(String congTyId);
	public String getCongTyId();

	public void setPhongBanList(List<Erp_Item> phongBanList);
	public List<Erp_Item> getPhongBanList();

	public void setUserId(String userId);
	public String getUserId();

	public void setPhongBanId(String phongBanId);
	public String getPhongBanId();
	
	public void setPhongBanIds(String[] phongBanIds);
	public String[] getPhongBanIds();

	public void setChiNhanhId(String chiNhanhId);
	public String getChiNhanhId();

	public void setNam(String nam);
	public String getNam();

	public void setThangKeThuc(String thangKeThuc);
	public String getThangKeThuc();

	public void setThangBatDau(String thangBatDau);
	public String getThangBatDau();
	
	public String getNhomChiPhiId() ;
	public void setNhomChiPhiId(String nhomChiPhiId);
	public ResultSet getRsNhomChiPhi();
	public void setRsNhomChiPhi(ResultSet rsNhomChiPhi);
}