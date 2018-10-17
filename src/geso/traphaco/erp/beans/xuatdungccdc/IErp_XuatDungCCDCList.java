package geso.traphaco.erp.beans.xuatdungccdc;

import geso.dms.center.util.IPhan_Trang;
import geso.traphaco.erp.db.sql.dbutils;

import java.io.Serializable;
import java.util.List;

public interface IErp_XuatDungCCDCList  extends Serializable, IPhan_Trang{
	public void init();
	
	public void DBClose();
	
	public String getNgayBatDau();
	public void setNgayBatDau(String ngayBatDau);
	
	public String getNgayKetThuc();
	public void setNgayKetThuc(String ngayKetThuc);
	
	public String getSoChungTu();
	public void setSoChungTu(String soChungTu) ;
	
	public String getMaCCDC();
	public void setMaCCDC(String maCCDC) ;
	
	public String getTrangThai();
	public void setTrangThai(String trangThai);
	
	public List<Erp_XuatDungItem> getXuatDungList();
	public void setXuatDungList(List<Erp_XuatDungItem> xuatDungList);
	
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
}