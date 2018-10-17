package geso.traphaco.erp.beans.yclaymaukiemnghiem;

import geso.traphaco.center.util.IPhan_Trang;
import geso.traphaco.erp.beans.yclaymaukiemnghiem.imp.PhieuKiemDinh;
import geso.traphaco.erp.db.sql.dbutils;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.List;

public interface IYCLayMauKiemNghiemList extends Serializable, IPhan_Trang {
	
	public String getUserId();
	public void setUserId(String userId);
	
	public String getMsg();
	public void setMsg(String msg);
	
	public void DBclose();
	
	public void init(String query);
		
	public String getCtyId() ;
	public void setCtyId(String ctyId) ;

	public String getNppId() ;
	public void setNppId(String nppId) ;
	
	public List<PhieuKiemDinh> getDsPhieuKiemDinhs() ;
	public void setDsPhieuKiemDinhs(List<PhieuKiemDinh> dsPhieuKiemDinhs) ;
	
	public ResultSet getRsKhoXuatMau() ;
	public void setRsKhoXuatMau(ResultSet rsKhoXuatMau) ;
	
	public String getKhoanMucId();
	public void setKhoanMucId(String khoanMucId) ;
	
	public ResultSet getRsKhoanMuc() ;
	public void setRsKhoanMuc(ResultSet rsKhoanMuc) ;
	
	public ResultSet getRsLoaiKiemDinh() ;
	public void setRsLoaiKiemDinh(ResultSet rsLoaiKiemDinh) ;
	
	public ResultSet getRsPhieuKiemDinh() ;
	public void setRsPhieuKiemDinh(ResultSet rsPhieuKiemDinh) ;
	
	public dbutils getDb() ;
	public void setDb(dbutils db);
	
	public String getMauKiemNghiemId();
	public void setMauKiemNghiemId(String mauKiemNghiemId) ;
	
	public String getKhoBietTruId() ;
	public void setKhoBietTruId(String khoBietTruId);
	
	public String getPhongBanId() ;
	public void setPhongBanId(String phongBanId) ;
	
	public String getPhieuNhanHangId();
	public void setPhieuNhanHangId(String phieuNhanHangId) ;
	
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
	
	public String getSanphamId() ;
	public void setSanphamId(String sanphamId);
	
	public ResultSet getRsSanPham() ;
	public void setRsSanPham(ResultSet rsSanPham);
	
	public String getTungay() ;
	public void setTungay(String tungay);
	
	public String getDenngay();
	public void setDenngay(String denngay) ;
	
	public String getLoaiKiemDinh() ;
	public void setLoaiKiemDinh(String loaiKiemDinh) ;

	public String getSophieu() ;
	public void setSophieu(String sophieu) ;
	
	public String getSophieukiemdinh() ;
	public void setSophieukiemdinh(String sophieukiemdinh) ;

	public String getQuytrinhmauso() ;
	public void setQuytrinhmauso(String quytrinhmauso);

	public boolean Chotphieu(String id);
	public boolean Bochotphieu(String id);
	public boolean Xoaphieu(String id);
	public void createRs();
	
}
