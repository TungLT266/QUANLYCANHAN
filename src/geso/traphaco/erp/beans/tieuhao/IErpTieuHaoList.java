package geso.traphaco.erp.beans.tieuhao;

 
import geso.dms.center.util.IPhan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;
 
public interface IErpTieuHaoList extends Serializable, IPhan_Trang
{
	public String getCongtyId();
	public void setCongtyId(String congtyId);
	
	public String getUserId();
	public void setUserId(String userId);

	public String getTungay();
	public void setTungay(String tungay);
	public String getDenngay();
	public void setDenngay(String denngay);
	
	public String getId();
	public void setId(String id);
	
	public String getNgayTao();
	public void setNgayTao(String ngaytao);
	public String getNgaySua();
	public void setNgaySua(String ngaysua);
	
	public String getNguoiTao();
	public void Nguoi(String nguoitao);
	public String getNguoiSua();
	public void setNguoiSua(String nguoisua);
	
	public String getNhanHangId();
	public void setNhanHangId(String nhanhangId);
	
	public String getSanphamId();
	public void setSanphamId(String spId);
	
	public String getSoLuong();
	public void setSoLuong(String soluong);
	
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public String getSoPo();
	public void setSoPo(String SoPo);
	
	
	
	public String getMsg();
	public void setMsg(String msg);
	
	public ResultSet getTieuHaoRs();
	public void setTieuHaoRs(ResultSet rs);
	
	public void init(String search);
	public void DBclose();
}
