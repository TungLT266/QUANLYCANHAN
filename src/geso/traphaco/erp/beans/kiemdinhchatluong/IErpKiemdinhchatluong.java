package geso.traphaco.erp.beans.kiemdinhchatluong;

import geso.traphaco.center.beans.thongtinsanpham.ITieuchikiemdinh;
import geso.traphaco.center.util.*

import java.io.Serializable;
import java.util.List;

public interface IErpKiemdinhchatluong extends Serializable, IPhan_Trang

{
	public String getUserId();
	public void setUserId(String userId);
	
	public String getYcId();
	public void setYcId(String ycId);
	
	public String getSpId();
	public void setSpId(String spId);
	public String getSpTen();
	public void setSpTen(String spTen);
	
	public String getNhapkhoId();
	public void setNhapkhoId(String nkId);
	
	public String getSolo();
	public void setSolo(String solo);
	public String getSoluong();
	public void setSoluong(String soluong);
	
	public void setTieuchikiemdinhList(List<ITieuchikiemdinh> list);
	public List<ITieuchikiemdinh> getTieuchikiemdinhList();
	
	public String getDeNghiXuLy();
	public void setDeNghiXuLy(String denghixuly);
	
	public String getMsg();
	public void setMsg(String msg);
	
	public boolean updateKiemdinh();
	
	public void init();
	
	public void DbClose();
	
}
