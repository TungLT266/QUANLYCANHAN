package geso.traphaco.erp.beans.nhapkho.giay;

import java.sql.ResultSet;
import java.util.List;

public interface IErpNhapkho
{

	public String getCongtyId();

	public void setCongtyId(String congtyId);

	public String getUserId();

	public void setUserId(String userId);

	public String getId();

	public void setId(String userId);

	public String getNgaynhapkho();

	public void setNgaynhapkho(String ngaynhap);

	public String getTrangthai();

	public void setTrangthai(String trangthai);

	public void setmsg(String msg);

	public String getmsg();

	public void setKhoId(String khoId);

	public String getKhoId();

	public ResultSet getKhoList();

	public void setKhoList(ResultSet khoList);

	public ResultSet getRsCongDoan();

	public void setRsCongDoan(ResultSet rscongdoan);

	public ResultSet getRsLenhSanXuat();

	public void setRsLenhSanXuat(ResultSet rsLenhsanxuat);

	public void setNdnId(String ndnId);

	public String getNdnId();

	public ResultSet getNdnList();

	public void setNdnList(ResultSet ndnList);

	public void setLhhId(String lhhId);

	public String getLhhId();

	public ResultSet getLhhList();

	public void setLhhList(ResultSet lhhList);

	public List<ISanpham> getSpList();

	public void setSpList(List<ISanpham> spList);

	public boolean createNhapKho();

	public boolean updateNhapKho();

	public boolean HuyNhapKhoLsx();

	public String chotNhapKho(String userId);

	public String chotNhapKhoLSX();

	public boolean createNhapKhoLSX();

	public String getNgaychotNV();

	public void setNgaychotNV(String ngaychotNV);

	public String getSoLenhsx();

	public void setSoLenhsx(String soLenhsx);

	// pdf
	public String getNguoigiaohang();

	public void setNguoinhanhang(String nguoinhanhang);

	public String getDiachi();

	public void setDiachi(String diachi);

	public String getNhaptaikho();

	public void setNhaptaikho(String nhaptaikho);

	public String getGhichu();

	public void setGhichu(String ghichu);

	public void init();

	public void initPdf();

	public void createRs();

	public void DBclose();

	public void setCongDoanId(String ctidcurrent);

	public String GetCongDoanId();
	
	public String getDvdkId();

	public void setDvdkId(String DvdkId);
	
	public void setIsQLKV(String value);

	public String getIsLsxCongNghe();
	public void setIsLsxCongNghe(String IsLsxCongNghe);
	
	public String getKhongkiemdinh();
	public void setKhongkiemdinh(String kokiemdinh);
	
	
	
	public String getLoaisanpham();
	public void setLoaisanpham(String loaisanpham);
	
	public ResultSet getRsLoaisanpham();
	public void setRsLoaisanpham(ResultSet rs);
	
	public ResultSet getRsBTP();
	public void setRsBTP(ResultSet rs);
	public String getBTPId();
	public void setBTPId(String BTPId);
	
	public String getDonViTinh();
	public void setDonViTinh(String donvitinh);
	
	public void setNguoiTao(String nguoiTao);
	public String getNguoiTao();

	public void setKhoNhanTP(String khonhanTP);
	public String getKhoNhanTP();
}
