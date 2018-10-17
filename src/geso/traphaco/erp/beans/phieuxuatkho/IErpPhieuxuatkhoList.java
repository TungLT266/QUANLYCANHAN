package geso.traphaco.erp.beans.phieuxuatkho;

import geso.traphaco.center.util.IPhan_Trang;
import geso.traphaco.center.util.IThongTinHienThi;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.List;

public interface IErpPhieuxuatkhoList extends Serializable, IPhan_Trang
{
	public String getXuatKhoId();

	public void setXuatKhoId(String xuatkhoId);

	public String getUserId();

	public void setUserId(String userId);

	public String getTrangthai();

	public void setTrangthai(String trangthai);

	public String getTungay();

	public void setTungay(String tungay);

	public String getDenngay();

	public void setDenngay(String denngay);

	public String getMsg();

	public void setMsg(String msg);

	public String getSoChungTu();

	public void setSoChungTu(String sochungtu);

	public ResultSet getPxkList();

	public void setPxkList(ResultSet pxkList);

	public void init(String query);

	//public String getDDH(String ddhIds);
	
	public void DBclose();
	
	//HIỂN THỊ THÔNG TIN KẾ TOÁN
	public List<IThongTinHienThi> getHienthiList();
	public void setHienthiList(List<IThongTinHienThi> hienthiList);
	
	public ResultSet getrsDonhang_daduyet();
	
	public ResultSet get_khoxuatRs();
	public void set_khoxuatRs(ResultSet khoxuatRs);
	
	public String get_khoxuatId();
	public void set_khoxuatId(String khoxuatId);
	
	public ResultSet getNoidungxuatRs();
	public void setNoidungxuatRs(ResultSet noidungxuatRs);
	
	public String getnoidungxuatId();
	public void setnoidungxuatId(String noidungxuatId);
	
	public String getSohoadon();
	public void setSohoadon(String sohoadon);
	
}
