package geso.traphaco.erp.beans.taikhoankt;

import geso.traphaco.center.util.IPhan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;

public interface ITaikhoanktList extends Serializable, IPhan_Trang
{
	public String getUserId();

	public void setUserId(String userId);

	public String getId();

	public void setId(String Id);
	
	public String getSoHieuTaiKhoan();

	public void setSoHieuTaiKhoan(String SoHieuTaiKhoan);

	public String getTenTaiKhoan();

	public void setTenTaiKhoan(String TenTaiKhoan);

	public String getLoaiTaiKhoanId();

	public void setLoaiTaiKhoanId(String LoaiTaiKhoanId);

	public String getCongTyId();

	public void setCongTyId(String CongTyId);

	public String getTrangThai();

	public void setTrangThai(String TrangThai);

	public String getMsg();

	public void setMsg(String msg);

	public ResultSet getCongTyRs();

	public void setCongTyRs(ResultSet CongTyRs);

	public ResultSet getLoaiTaiKhoanRs();

	public void setLoaiTaiKhoanRs(ResultSet LoaiTaiKhoanRs);

	public ResultSet getTaiKhoanRs();

	public void setTaiKhoanRs(ResultSet TaiKhoanRs);

	public void init(String query);

	public void CreateRs();

	public String Delete(String Id);
	
	public void closeDB();
	
	public void setChixem(String chixem);
	public String getChixem();
}
