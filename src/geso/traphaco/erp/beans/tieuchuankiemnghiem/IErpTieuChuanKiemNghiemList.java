package geso.traphaco.erp.beans.tieuchuankiemnghiem;

import java.sql.ResultSet;

public interface IErpTieuChuanKiemNghiemList {

	public void init();
	public String getCongtyId();
	public void setCongtyId(String congtyId);
	public String getUserId();
	public void setUserId(String userId);
	public String getUserTen();
	public void setUserTen(String userTen);
	public String getId();
	public void setId(String id);
	public String getMa();
	public void setMa(String ma);
	public String getTen();
	public void setTen(String ten);
	public String getNgaycap();
	public void setNgaycap(String ngaycap);
	public String getTungay();
	public void setTungay(String tungay);
	public String getDenngay();
	public void setDenngay(String denngay);
	public String getLoaispId();
	public void setLoaispId(String loaispId);
	public String getSanphamId();
	public void setSanphamId(String sanphamId);
	public String getHosomau();
	public void setHosomau(String hosomau);
	public String getHosokiemnghiem();
	public void setHosokiemnghiem(String hosokiemnghiem);
	public String getPhieukiemnghiem();
	public void setPhieukiemnghiem(String phieukiemnghiem);
	public String getIshoatdong();
	public void setIshoatdong(String ishoatdong);
	public String getMsg();
	public void setMsg(String msg);
	
	public ResultSet getTieuchuanRs();
	public void setTieuchuanRs(ResultSet tieuchuanRs);
	public void Delete(String id, String userId);
	public String getMaTieuChuan();
	public void setMaTieuChuan(String maTieuChuan);
	public String getMaSanPham();
	public void setMaSanPham(String maSanPham);
}
