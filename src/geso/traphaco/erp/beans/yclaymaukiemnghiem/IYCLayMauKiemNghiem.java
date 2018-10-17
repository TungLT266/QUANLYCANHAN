package geso.traphaco.erp.beans.yclaymaukiemnghiem;

import geso.traphaco.center.util.IPhan_Trang;
import geso.traphaco.erp.beans.yclaymaukiemnghiem.imp.PhieuKiemDinh;
import geso.traphaco.erp.db.sql.dbutils;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.List;

public interface IYCLayMauKiemNghiem extends Serializable, IPhan_Trang {
	
	public String getUserId();
	public void setUserId(String userId);
	
	public String getMsg();
	public void setMsg(String msg);
	
	public void DBclose();
	public void createRs();
	public List<PhieuKiemDinh> getDsPhieuKiemDinhs();
	public String getSanphamId() ;
	public String getKhoXuatMauId();

	public void setKhoXuatMauId(String khoXuatMauId) ;

	public ResultSet getRsKhoXuatMau() ;

	public void setRsKhoXuatMau(ResultSet rsKhoXuatMau) ;
	public void setSanphamId(String sanphamId);

	public ResultSet getRsSanPham() ;

	public void setRsSanPham(ResultSet rsSanPham) ;
	public String getHosokemtheo();
	public void setHosokemtheo(String hosokemtheo);

	public String getKhoanMucId();

	public void setKhoanMucId(String khoanMucId) ;

	public ResultSet getRsKhoanMuc() ;
	public void setRsKhoanMuc(ResultSet rsKhoanMuc) ;

	public void setDsPhieuKiemDinhs(List<PhieuKiemDinh> dsPhieuKiemDinhs) ;

	public String getCtyId() ;
	public void setCtyId(String ctyId) ;

	public String getNppId() ;

	public void setNppId(String nppId) ;

	public String getLoaiKiemDinh() ;

	public void setLoaiKiemDinh(String loaiKiemDinh) ;

	public ResultSet getRsLoaiKiemDinh() ;

	public void setRsLoaiKiemDinh(ResultSet rsLoaiKiemDinh) ;

	public ResultSet getRsPhieuKiemDinh() ;
	public void setRsPhieuKiemDinh(ResultSet rsPhieuKiemDinh) ;
	public dbutils getDb() ;
	public void setDb(dbutils db);

	public String getNgayChungTu() ;

	public void setNgayChungTu(String ngayChungTu);


	public String getSoChungTuNhapMau() ;

	public void setSoChungTuNhapMau(String soChungTuNhapMau) ;

	public String getMauKiemNghiemId();

	public void setMauKiemNghiemId(String mauKiemNghiemId) ;

	public String getSoHoaDon() ;
	public void setSoHoaDon(String soHoaDon) ;
	public String getKyHieuHoaDon() ;

	public void setKyHieuHoaDon(String kyHieuHoaDon);

	public String getNgayHoaDon() ;

	public void setNgayHoaDon(String ngayHoaDon) ;

	public String getKhoBietTruId() ;

	public void setKhoBietTruId(String khoBietTruId);
	
	public String getDienGiai() ; 
	public void setDienGiai(String dienGiai) ;

	public String getPhongBanId() ;

	public void setPhongBanId(String phongBanId) ;

	public String getSoPhieuKiemDinh();

	public void setSoPhieuKiemDinh(String soPhieuKiemDinh);

	public String getPhieuNhanHangId();
	public void setPhieuNhanHangId(String phieuNhanHangId) ;

	public double getTongTienVND() ;

	public void setTongTienVND(double tongTienVND);

	public double getChenhLechNT() ;

	public void setChenhLechNT(double chenhLechNT) ;

	public String getKhoTonTruId() ;

	public void setKhoTonTruId(String khoTonTruId) ;

	public ResultSet getRsMauKiemNghiem();

	public void setRsMauKiemNghiem(ResultSet rsMauKiemNghiem);

	public ResultSet getRsPhongBan() ;

	public void setRsPhongBan(ResultSet rsPhongBan) ;

	public ResultSet getRsPhieuNhanHang();

	public void setRsPhieuNhanHang(ResultSet rsPhieuNhanHang);

	public ResultSet getRsKhoBietTru() ;

	public void setRsKhoBietTru(ResultSet rsKhoBietTru) ;

	public ResultSet getRsKhoTonTru();

	public void setRsKhoTonTru(ResultSet rsKhoTonTru);
	
	public boolean create();
	public boolean update();
	public void init();
	
	public ResultSet getRsYCLPKN();

	public void setRsYCLPKN(ResultSet rsYCLPKN);
	public String getId();
	public void setId(String id);
	
	public ResultSet getRsLayChiTiet(String soPhieuKiemDinh2, String masanpham );
}
