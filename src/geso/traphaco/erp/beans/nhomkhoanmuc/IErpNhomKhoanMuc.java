package geso.traphaco.erp.beans.nhomkhoanmuc;

import geso.traphaco.center.util.IPhan_Trang;
import geso.traphaco.erp.beans.nhomkhoanmuc.imp.ErpKhoanMucChiPhi;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.List;

public interface IErpNhomKhoanMuc  extends Serializable, IPhan_Trang
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

	public boolean Deletenkm();
	
	public void Init();

	public void closeDB();

	public boolean Create();

	public boolean Update();
	
	public boolean DeleteLncc();
	
	public void setChixem(String chixem);
	public String getChixem();
	
	public ResultSet getRsNhomKhoanMuc() ;

	public void setRsNhomKhoanMuc(ResultSet rsNhomKhoanMuc) ;
	public void createListKhoanMucChiPhi();
	public void setListKhoanMuc(List<ErpKhoanMucChiPhi> listKhoanMuc);
	public List<ErpKhoanMucChiPhi> getListKhoanMuc();
	public void setDvthid(String dvthid);
	public String getDvthid();
	public void setTenKhoanMucChiPhi(String tenKhoanMucChiPhi);
	public String getTenKhoanMucChiPhi();
	public void createRsDonViThucHien();

	public ResultSet getRsDonViThucHien();

	public void setRsDonViThucHien(ResultSet rsDonViThucHien);
	
	public String getAction();
	public void setAction(String action);
}
