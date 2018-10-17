package geso.traphaco.erp.beans.xoanoncc;

import geso.traphaco.center.util.IPhan_Trang;
import geso.traphaco.center.util.IThongTinHienThi;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.List;

public interface IErpXoaNoNCCList extends Serializable, IPhan_Trang
{
	public String getUserId();
	public void setUserId(String userId);

	public String getTungay();
	public void setTungay(String tungay);
	public String getDenngay();
	public void setDenngay(String denngay);
	
	public String getNccId();
	public void setNccId(String nccid);
	public String getMaPhieu();
	public void setMaPhieu(String maphieu);
	public String getNgayChungTu();
	public void setNgayChungTu(String ngaychungtu);
	public ResultSet getNccList();
	public void setNccList(ResultSet ncclist);
	
	public String getNvId();
	public void setNvId(String nvid);
	public ResultSet getNvList();
	public void setNvList(ResultSet nvlist);

	public String getHtttId();
	public void setHtttId(String htttid);
	public ResultSet getHtttList();
	public void setHtttList(ResultSet htttlist);
	
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public void setmsg(String msg);
	public String getmsg();
	
	public ResultSet getTThoadonList();
	public void setTThoadonList(ResultSet tthdlist);
	
	public void init(String search);
	public void DBclose();
	public void NewDbUtil();
	public List<IThongTinHienThi> getHienthiList();
	public void setHienthiList(List<IThongTinHienThi> hienthiList);
	
	public String getCongtyId();
	public void setCongtyId(String congtyId);
	public String getnppdangnhap();
	public void setnppdangnhap(String nppdangnhap);
}
