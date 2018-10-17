package geso.traphaco.erp.beans.hanmucnhapkhau;

import geso.traphaco.center.util.IPhan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;

public interface IErpHanMucNhapKhau  extends Serializable, IPhan_Trang
{
	public String getId();

	public void setId(String id);

	public float getSoluong();

	public void setSoluong(float sl);

	public String getNgayTao();

	public String getNgaySua();
	
	public String getSanPham_Fk();

	public void setSanPham_Fk(String sanpham_fk);

	public void setNgayTao(String ngaytao);

	public void setNgaySua(String ngaysua);
	
	public String getNguoiTao();

	public String getNguoiSua();

	public void setNguoiTao(String nguoitao);

	public void setNguoiSua(String nguoisua);

	public String getTuNgay();

	public String getDenNgay();

	public void setTuNgay(String tungay);

	public void setDenNgay(String denngay);
	
	public String getUserId();

	public void setUserId(String userid);

	public String getTrangThai();

	public void setTrangThai(String trangthai);

	public String getMsg();

	public void setMsg(String msg);
	
	public ResultSet getListSanPham();

	public void setListSanPham(ResultSet listsp);	

	public void Init();

	public void closeDB();

	public void Create_Sanpham();

	public boolean edit();
	
	public boolean Save();
	
	public void setSoDangKy(String soDangKy);
	public String getSoDangKy();
}