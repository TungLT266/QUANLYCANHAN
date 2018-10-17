package geso.traphaco.erp.beans.nhapkho.giay;

import java.sql.ResultSet;
import java.util.List;

public interface IErpNhapKhoLsx
{
	public String getLsxId();
	public void setLsxId(String lsxId);
	
	public String getCongDoanId();
	public void setCongDoanId(String congdoanId);
	
	public String getCongTyId();
	public void setCongTyId(String congtyId);
	
	public String getUserId();
	public void setUserId(String userId);
	
	
	public String getIsLsxCongNghe();
	public void setIsLsxCongNghe(String IsLsxCongNghe);
	
	public String getKhongkiemdinh();
	public void setKhongkiemdinh(String kokiemdinh);
	
	
	
	public String getNhaMayId();
	public void setNhaMayId(String nhamayId);
	
	public String getTrangThai();
	public void setTrangThai(String trangthai);
	
	public String getMsg();
	public void setMsg(String msg);
	
	
	public String getNdnId();
	public void setNdnId(String ndnId);
	
	public String getKhoId();
	public void setKhoId(String khoId);
	
	public String getLoaisanpham();
	public void setLoaisanpham(String loaisanpham);
	
	public ResultSet getRsLoaisanpham();
	public void setRsLoaisanpham(ResultSet rs);
	
	
	
	public ResultSet getRsThupham();
	public void setRsThupham(ResultSet rs);
	public String getThuphamId();
	public void setThuphamId(String thuphamId);
	
	public ResultSet getRsBTP();
	public void setRsBTP(ResultSet rs);
	public String getBTPId();
	public void setBTPId(String BTPId);
	
 
	public String getIsNhapBTP_Loi();
	public void setIsNhapBTP_Loi(String IsNhapBTP_loi);
	
	public String getNgaynhapkho();
	public void setNgaynhapkho(String ngaynhapkho);
	
	public String getBomID();
	public void setBomID(String bomid);
	
	public String getSoLuongSanXuat();
	public void setSoLuongSanXuat(String soluongsanxuat);
	
	public String getDonViTinh();
	public void setDonViTinh(String donvitinh);
	
	public List<IErpNhapkho> getListNhapKho();
	public void setListNhapKho(List<IErpNhapkho> lstNhapkho);
	
	public void initNhapKhoLsx();
	
	public boolean createNhapKhoLSX();
	
	public String getIsQLKV();
	public void setIsQLKV(String value);
	public ResultSet getQuery(String string);
	
	public void shutdown();
	public ResultSet getRsdvdl(String string);
	public void CreateRs();
	
	public void setrsKhoNhanTP(ResultSet rskho);
	public ResultSet getrsKhoNhanTP();

	public String getKhoNhanTP();
	public void setKhoNhanTP(String KhoNhanTP);
	
}
