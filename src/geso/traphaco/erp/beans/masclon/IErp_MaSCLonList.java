package geso.traphaco.erp.beans.masclon;

import geso.traphaco.center.util.Erp_ListItem;
import geso.traphaco.center.util.IPhan_Trang;
import geso.traphaco.erp.beans.xuatdungccdc.Erp_Item;
import geso.traphaco.erp.db.sql.dbutils;

import java.io.Serializable;
import java.util.List;

public interface IErp_MaSCLonList extends Serializable, IPhan_Trang
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

	public void setTaiKhoanId(String taiKhoanId);
	public String getTaiKhoanId();

	public void setTaiSanId(String taiSanId);
	public String getTaiSanId();

	public void setList(List<Erp_ListItem> list);
	public List<Erp_ListItem> getList();

	public void setTaiKhoanList(List<Erp_Item> taiKhoanList);
	public List<Erp_Item> getTaiKhoanList();

	public void setTaiSanCDList(List<Erp_Item> taiSanCDList);
	public List<Erp_Item> getTaiSanCDList();
	
	public void setMa(String ma);
	public String getMa();
	

}