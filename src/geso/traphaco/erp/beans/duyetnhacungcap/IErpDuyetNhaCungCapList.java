package geso.traphaco.erp.beans.duyetnhacungcap;

import geso.traphaco.center.util.IPhan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;

public interface IErpDuyetNhaCungCapList  extends Serializable, IPhan_Trang
{
	public String getId();

	public void setId(String id);

	
	public String getSanPham_Fk();

	public void setSanPham_Fk(String sanpham_fk);
	
	public String getNhacungcap_Fk();

	public void setNhacungcap_Fk(String nhacungcap_fk);
	
	public String getTuNgay();

	public String getDenNgay();

	public void setTuNgay(String tungay);

	public void setDenNgay(String denngay);

	public String getUserId();

	public void setUserId(String userid);

	public String getTrangThai();

	public void setTrangThai(String trangthai);
	
	public ResultSet getListSanPham();

	public void setListSanPham(ResultSet listsp);	
	
	public ResultSet getListDuyetNcc();

	public void setListDuyetNcc(ResultSet listduyetncc);	
	
	public ResultSet getListNhacungcap();

	public void setListNhacungcap(ResultSet listncc);	
	public String getMsg();

	public void setMsg(String msg);


	public void Init(String search);

	public void closeDB();

	public void Create_Sanpham();
	
	public void Create_Nhacungcap();
	public boolean Delete_duyetncc();
	public boolean Chot_duyetncc();

}
