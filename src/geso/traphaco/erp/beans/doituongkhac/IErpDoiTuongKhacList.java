package geso.traphaco.erp.beans.doituongkhac;

import geso.dms.center.util.PhanTrang;
import geso.traphaco.center.db.sql.dbutilsPool;
import geso.traphaco.center.util.Erp_Item;
import geso.traphaco.center.util.Erp_ListItem;
import geso.traphaco.center.util.IPhan_Trang;

import java.io.Serializable;
import java.util.List;

public interface IErpDoiTuongKhacList extends Serializable, IPhan_Trang
{
	public void init();

	public String getId() ;
	public void setId(String id);

	public String getUserId();
	public void setUserId(String userId);

	public String getCongTyId();
	public void setCongTyId(String congTyId);

	public String getMaTenDoiTuong();
	public void setMaTenDoiTuong(String maTenDoiTuong);

	public String getNganHangId();
	public void setNganHangId(String nganHangId);

	public String getChiNhanhId();
	public void setChiNhanhId(String chiNhanhId);

	public List<Erp_Item> getNppList();
	public void setNppList(List<Erp_Item> nppList);

	public List<Erp_Item> getChiNhanhList();
	public void setChiNhanhList(List<Erp_Item> chiNhanhList);

	public List<Erp_Item> getNganHangList();
	public void setNganHangList(List<Erp_Item> nganHangList);

	public List<Erp_ListItem> getViewList();
	public void setViewList(List<Erp_ListItem> viewList);

	public void setNppId(String nppId);
	public String getNppId();
	
	public void setMsg(String msg);
	public String getMsg();
	
	public int getNum();
	public void setNum(int num);
	public int[] getListPages();
	
	public void setListPages(int[] listPages);
	public int getCurrentPages();
}