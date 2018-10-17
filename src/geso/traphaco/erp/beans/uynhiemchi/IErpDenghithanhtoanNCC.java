package geso.traphaco.erp.beans.uynhiemchi;

import geso.traphaco.erp.beans.thanhtoanhoadon.IHoadon;

import java.sql.ResultSet;
import java.util.List;

public interface IErpDenghithanhtoanNCC 
{
	public String getUserId();
	public void setUserId(String userId);
	public String getCtyId();

	public void setCtyId(String ctyId); 

	public String getId();
	public void setId(String id);
	public String getNgayghinhan();
	public void setNgayghinhan(String ngayghinhan);
	
	public String getNccId();
	public void setNccId(String nccId);
	public ResultSet getNccRs();
	public void setNccRs(ResultSet nccRs);

	public String getDiachi();
	public void setDiachi(String diachi);
	
	public String getHtttId();
	public void setHtttId(String htttId);
	public ResultSet getHtttRs();
	public void setHtttRs(ResultSet htttRs);
	
	public String getNganhangId();
	public void setNganhangId(String nganhangId);
	public ResultSet getNganhangRs();
	public void setNganhangRs(ResultSet nganhangRs);
	
	public String getChinhanhId();
	public void setChinhanhId(String cnId);
	public ResultSet getChinhanhRs();
	public void setChinhanhRs(ResultSet chinhanhRs);
	
	public String getSotaikhoan();
	public void setSotaikhoan(String sotk);
	
	public String getSotaikhoan_tp(); 

	public void setSotaikhoan_tp(String sotk_tp);
	
	public String getNoidungtt();
	public void setNoidungtt(String ndtt);
	public String getSotientt();
	public void setSotientt(String sotientt);
	
	public String getHoadonIds();
	public void setHoadonIds(String hdIds);
	public List<IHoadon> getHoadonRs();
	public void setHoadonRs(List<IHoadon> hoadonRs);
	
	public boolean createTTHD();
	public boolean updateTTHD();
	public boolean chotTTHD(String userId);
	
	public String getMsg();
	public void setMsg(String msg);

	public String getTrichphi();

	public void setTrichphi(String trichphi);
	public ResultSet getSotkRs_tp();

	public void setSotkRs_tp(ResultSet sotkRs_tp); 

	public void init();
	public void initDisplay();
	public void initPdf();
	public void initUnc();
	public void createRs();
	public void DBclose();
	
	// la loai thanh toan hoa don hay thanh toan tam ung
	public String getLoaiThanhToan();
	public void setLoaiThanhToan(String loaithanhtoan);
	
	//biet duoc tam ung la nhan vien hay nha cung cap
	public String getDoiTuongTamUng();
	public void setDoiTuongTamUng(String DoiTuongTamUng);
	
	public String getNhanVienId();
	public void setNhanVienId(String NhanVienId);
	public ResultSet getNhanvienRs();
	public void setNhanvienRs(ResultSet NhanvienRs);
		
	public String Gettenhienthi();
	public void settenhienthi(String tenhienthi); 
	
	public String getNhomNCCCN();
	public void setNhomNCCCN(String nhomNCCCN);
	
	
	public String getDoiTuongChiPhiKhac();
	public void setDoiTuongChiPhiKhac(String doiTuongChiPhiKhac);
	
	public String getPrefix();
	public void setPrefix(String prefix);
	
	public String getDinhkhoanno();
	public void setDinhkhoanno(String dinhkhoanno);
	
	public String getDinhkhoannoId();
	public void setDinhkhoannoId(String dinhkhoannoId);
	

	public String getDoiTuongDinhKhoan();
	public void setDoiTuongDinhKhoan(String doiTuongDinhKhoan);
	
	public String getMaTenDoiTuongDinhKhoan();
	public void setMaTenDoiTuongDinhKhoan(String maTenDoiTuongDinhKhoan);
	
	public String getDoituongdinhkhoanId();
	public void setDoituongdinhkhoanId(String doituongdinhkhoanId);
	
	public ResultSet getSotkRs();

	public void setSotkRs(ResultSet sotkRs);
	
	public String getSotienHD();

	public void setSotienHD(String sotienHD);

	public String getPhinganhang();

	public void setPhinganhang(String pnganhang);

	public String getThueVAT();
	
	public void setThueVAT(String thueVAT);
	
	public String getTienteId();
	
	public void setTienteId(String ttId);
	
	public ResultSet getTienteRs();

	public void setTienteRs(ResultSet tienteRs);
	
	public String getTigia();
	
	public void setTigia(String tigia);

	public String getSotienttNT();

	public void setSotienttNT(String sotienttNT);

	public String getSotienHDNT();

	public void setSotienHDNT(String sotienHDNT);

	public String getPhinganhangNT();
	
	public void setPhinganhangNT(String pnganhangNT);

	public String getThueVATNT();
	
	public void setThueVATNT(String thueVATNT);

	public String getChenhlech(); 

	public void setChenhlech(String chenhlech);
	
	public String getMahoadon();

	public void setMahoadon(String mahoadon);

	public String getMauhoadon();

	public void setMauhoadon(String mauhoadon);

	public String getKyhieu();

	public void setKyhieu(String kyhieu);

	public String getSohoadon();

	public void setSohoadon(String sohoadon);

	public String getNgayhoadon();

	public void setNgayhoadon(String ngayhoadon);

	public String getTenNCC();

	public void setTenNCC(String tenNCC);

	public String getMST();
	
	public void setMST(String mst);

	public String getTienhang();

	public void setTienhang(String tienhang);

	public String getThuesuat();

	public void setThuesuat(String thuesuat);
	
	public String getTienthue();

	public void setTienthue(String tienthue);

	public String getNhId_VAT();
	
	public void setNhId_VAT(String nhId_VAT);
	
	public String getCnId_VAT();
	
	public void setCnId_VAT(String cnId_VAT);

	public void setTenNCC_VAT(String tenNCC_VAT);
	
	public String getTenNCC_VAT();
	
	public void setCheckThanhtoantuTV(String checkThanhtoantuTV);
	
	public String getCheckThanhtoantuTV();
	
	public String getKhachhangId();
	public void setKhachhangId(String khachhangId);
	public ResultSet getKhachhangRs();
	public void setKhachhangRs(ResultSet khachhangRs);
	
	public String getNhomNCCCNId();
	public void setNhomNCCCNId(String nhomNCCCNId);
	public ResultSet getNhomNCCRs();
	public void setNhomNCCRs(ResultSet nhomNCCRs);
	
	public String getBophanTen();
	public void setBophanTen(String bophanTen);
	
	public String getBophanId();
	public void setBophanId(String bophanId);
	
	public String getnppdangnhap();
	public void setnppdangnhap(String nppdangnhap);
}
