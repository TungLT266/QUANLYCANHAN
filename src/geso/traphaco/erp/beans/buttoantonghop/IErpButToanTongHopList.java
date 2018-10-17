package geso.traphaco.erp.beans.buttoantonghop;

import geso.traphaco.center.util.IPhan_Trang;
import geso.traphaco.center.util.IThongTinHienThi;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.List;

public interface IErpButToanTongHopList extends Serializable, IPhan_Trang
{
	public String getCongtyId();
	public void setCongtyId(String congtyId);
	
	public String getNgayButToan();

	public void setNgayButToan(String NgayButToan);
	
	public String getDenNgayButToan();

	public void setDenNgayButToan(String DenNgayButToan);
	
	public String getSoId() ;
	public void setSoId(String soId) ;

	public String getDienGiai();

	public void setDienGiai(String DienGiai);

	public String getUserId();

	public void setUserId(String UserId);

	public String getMsg();

	public void setMsg(String Msg);

	public ResultSet getRsButToan();

	public void setRsButToan(ResultSet RsButToan);

	public void init();

	public void DBClose();
	
	public String getSoChungTu();

	public void setSoChungTu(String SoChungTu);	
	
	public String getTaiKhoanNo();

	public void setTaiKhoanNo(String TaiKhoanNo);
	
	public ResultSet getRsTaiKhoanNo();

	public void setRsTaiKhoanNo(ResultSet RsTaiKhoanNo);
	
	public String getTaiKhoanCo();

	public void setTaiKhoanCo(String TaiKhoanCo);
	
	public ResultSet getRsTaiKhoanCo();

	public void setRsTaiKhoanCo(ResultSet RsTaiKhoanCo);
	
	public String getSoTien();

	public void setSoTien(String SoTien);
	
	public String getNguoiTao();

	public void setNguoiTao(String NguoiTao);
	
	public ResultSet getRsNguoiTao();

	public void setRsNguoiTao(ResultSet RsNguoiTao);
	
	public List<IThongTinHienThi> getHienthiList();
	public void setHienthiList(List<IThongTinHienThi> hienthiList);
	
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	//NEU DANG NHAP LA TDV, THI CHI LAY NPP VA TDV NAY
	
	public String getNpp_duocchon_id();
	public void setNpp_duocchon_id(String npp_duocchon_id);
	
	public String getnppId();
	public void setnppId(String nppId);

}
