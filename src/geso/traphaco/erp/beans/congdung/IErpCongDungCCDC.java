package geso.traphaco.erp.beans.congdung;

import geso.traphaco.center.util.IPhan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;

public interface IErpCongDungCCDC extends Serializable, IPhan_Trang
{
	public void setMsg(String msg);

	public String getMsg();

	public void setTrangThai(String trangthai);

	public String getTrangThai();

	public void setUserId(String userId);

	public String getUserId();

	public void setTen(String ten);

	public String getTen();

	public void setMa(String ma);

	public String getMa();

	public String getTaiKhoan();

	public void setTaiKhoan(String taikhoan);

	public void setId(String id);
	
	public void setCtyId(String ctyId);
	
	public String getCtyId();

	public String getId();

	public boolean Update();

	public boolean Create();

	public boolean Search();

	public boolean Init();

	public boolean CheckUnique();

	public void CreateRs();

	public void Close();

	public ResultSet getTaiKhoanRs();

	public void setTaiKhoanRs(ResultSet taikhoan);

	public ResultSet getCongDungRs();

	public void setCongDungRs(ResultSet congdung);
}
