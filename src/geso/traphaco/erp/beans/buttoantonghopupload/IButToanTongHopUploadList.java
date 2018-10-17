package geso.traphaco.erp.beans.buttoantonghopupload;

import geso.traphaco.center.util.Erp_Item;
import geso.traphaco.center.util.Erp_ListItem;
import geso.traphaco.center.util.IPhan_Trang;
import geso.traphaco.erp.db.sql.dbutils;

import java.io.Serializable;
import java.util.List;

public interface IButToanTongHopUploadList extends Serializable, IPhan_Trang
{
	public void init();
	
	public void DBClose();
	
	public int getNum();
	public void setNum(int num);
	
	public int[] getListPages();
	public void setListPages(int[] listPages);
	
	public int getCurrentPages();
	public void setCurrentPages(int currentPages);

	public String getNgayBatDau();
	public void setNgayBatDau(String ngayBatDau);

	public String getNgayKetThuc();
	public void setNgayKetThuc(String ngayKetThuc);

	public String getSoChungTu();
	public void setSoChungTu(String soChungTu);

	public String getMa();
	public void setMa(String ma);

	public String getChiNhanhId();
	public void setChiNhanhId(String chiNhanhId);

	public String getUserId();
	public void setUserId(String userId);

	public String getNppId();
	public void setNppId(String nppId);

	public String getMsg() ;
	public void setMsg(String msg);

	public List<Erp_ListItem> getList();
	public void setList(List<Erp_ListItem> list);

	public dbutils getDb();
	public void setDb(dbutils db);

	public void setCongTyId(String congTyId);
	public String getCongTyId();
	
	public void setChiNhanhList(List<Erp_Item> chiNhanhList);
	public List<Erp_Item> getChiNhanhList();
}