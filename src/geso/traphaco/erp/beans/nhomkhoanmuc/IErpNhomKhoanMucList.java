package geso.traphaco.erp.beans.nhomkhoanmuc;

import geso.traphaco.center.util.IPhan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;

public interface IErpNhomKhoanMucList  extends Serializable, IPhan_Trang
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
	
	public String getCongtyId();

	public void setCongtyId(String congtyId);

	public String getNppId();

	public void setNppId(String nppId);

	public void Init();

	public void closeDB();

	public boolean Create();

	public boolean Update();
	
	public void setChixem(String chixem);
	public String getChixem();
	
	public ResultSet getRsNhomKhoanMuc() ;

	public void setRsNhomKhoanMuc(ResultSet rsNhomKhoanMuc) ;

}
