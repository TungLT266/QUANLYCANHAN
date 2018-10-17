package geso.traphaco.erp.beans.loaitaikhoan;

import geso.traphaco.center.util.IPhan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;

public interface IErpLoaiTaiKhoan  extends Serializable, IPhan_Trang
{
	public String getId();

	public void setId(String id);

	public String getTen();

	public void setTen(String diengiai);

	public void setMa(String Ma);

	public String getMa();

	public String getNgayTao();

	public String getNgaySua();

	public void setNgayTao(String ngaytao);

	public void setNgaySua(String ngaysua);

	public String getUserId();

	public void setUserId(String userid);

	public String getTrangThai();

	public void setTrangThai(String trangthai);

	public String getMsg();

	public void setMsg(String msg);

	public ResultSet getRsLoaiTaiKhoan();

	public void setRsLoaiTaiKhoan(ResultSet rsloaitaikhoan);

	public void search();

	public void Init();

	public void closeDB();

	public boolean Create();

	public boolean Update();
	
	public boolean DeleteLtk();
	
	public void setChixem(String chixem);
	
	public String getChixem();

	public String getnppId();
	
	public void setnppId(String nppId);
	
	public String getcongtyId();
	
	public void setcongtyId(String congtyId);
}
