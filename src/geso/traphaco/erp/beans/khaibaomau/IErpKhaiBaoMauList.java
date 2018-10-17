package geso.traphaco.erp.beans.khaibaomau;

import geso.traphaco.center.util.IPhan_Trang;
import geso.traphaco.center.util.TitleItem;
import geso.traphaco.center.util.ViewItem;
import geso.traphaco.erp.db.sql.dbutils;

import java.io.Serializable;
import java.util.List;

public interface IErpKhaiBaoMauList extends Serializable, IPhan_Trang
{
	public void init();
	
	public void DBClose();
	
	public String getNgayBatDau();
	public void setNgayBatDau(String ngayBatDau);
	
	public String getNgayKetThuc();
	public void setNgayKetThuc(String ngayKetThuc);
	
	public String getSoChungTu();
	public void setSoChungTu(String soChungTu);
	
	public String getTrangThai();
	public void setTrangThai(String trangThai);

	public int getNum();
	public void setNum(int num);
	
	public int[] getListPages();
	public void setListPages(int[] listPages);
	
	public int getCurrentPages();
	public void setCurrentPages(int currentPages);
	
	public dbutils getDb();
	public void setDb(dbutils db);
	
	public void setMsg(String msg);
	public String getMsg();

	public void setCongTyId(String congTyId);
	public String getCongTyId();

	public List<TitleItem> getTitlelist();

	public void setViewList(List<List<ViewItem>> viewList);
	public List<List<ViewItem>> getViewList() ;
	
	public void setMa(String ma);
	public String getMa();
	
	public void setTen(String ten);
	public String getTen();
}