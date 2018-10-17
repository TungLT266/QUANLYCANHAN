package geso.traphaco.erp.beans.kiemnghiemchitiet;

import geso.traphaco.center.util.IPhan_Trang;
import geso.traphaco.erp.beans.kiemnghiemchitiet.imp.PhieuKiemDinh;
import geso.traphaco.erp.beans.kiemnghiemchitiet.imp.PhuongPhap;
import geso.traphaco.erp.db.sql.dbutils;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.List;

public interface IErpKiemNghiemChiTiet extends Serializable, IPhan_Trang {
	
	public String getUserId();
	public void setUserId(String userId);
	
	public String getId();
	public void setId(String id);
	
	public String getMsg();
	public void setMsg(String msg);
	
	public void DBclose();
	public void createRs();

	public String getSanphamId() ;
	
	public int __sizePhuongPhap() ;

	public void set__sizePhuongPhap(int __sizePhuongPhap) ;
	public void setSanphamId(String sanphamId);

	public ResultSet getRsSanPham() ;

	public void setRsSanPham(ResultSet rsSanPham) ;
	 
	public List<PhuongPhap> getDsPhieuKiemDinhs() ;

	public void setDsPhieuKiemDinhs(List<PhuongPhap> dsPhieuKiemDinhs);
	
	public List<PhieuKiemDinh> getDsHoaChatKiemNghiem();
	public void setDsHoaChatKiemNghiem(List<PhieuKiemDinh> dsHoaChatKiemNghiem);
	
	public String getCtyId() ;
	public void setCtyId(String ctyId) ;

	public String getNppId() ;

	public void setNppId(String nppId) ;
 
	public dbutils getDb() ;
	public void setDb(dbutils db);

	public String getNgayChungTu();

	public void setNgayChungTu(String ngayChungTu);
 
	public String getDienGiai() ;

	public void setDienGiai(String dienGiai) ;

	public String getPhongBanId() ;

	public void setPhongBanId(String phongBanId) ;
 
	public ResultSet getRsPhongBan() ;

	public void setRsPhongBan(ResultSet rsPhongBan) ;
	
	public ResultSet getRsHoSo();
	public void setRsHoSo(ResultSet rsHoSo);
 
	public boolean create();
	public boolean update();
	public void init();
	
	public String getSoTT();

	public void setSoTT(String soTT);
	
 
	public boolean chot(String Id);
	public boolean bochot(String Id);
	public boolean xoa(String Id);
	
	public String getHoSoId();
	public void setHoSoId(String hoSoId);

	public String getTieuChuanId();
	public void setTieuChuanId(String tieuChuanId);

	public String getYeuCauId();
	public void setYeuCauId(String yeuCauId);
	
	public String getThoiGianBD();
	public void setThoiGianBD(String thoiGianBD);

	public String getThoiGianKT();
	public void setThoiGianKT(String thoiGianKT);

	public String getTongThoiLuong();
	public void setTongThoiLuong(String tongThoiLuong);

	public String getDanhGia();
	public void setDanhGia(String danhGia);
	
	public ResultSet getRsYeuCau();
	public void setRsYeuCau(ResultSet rsYeuCau);
	
	public ResultSet getRsTieuChuan();
	public void setRsTieuChuan(ResultSet rsTieuChuan);
	
	public String getTieuChuan();
	public void setTieuChuan(String tieuChuan);
	
	public String getHoSo();
	public void setHoSo(String hoSo);
	
	public ResultSet getRsDanhGia();
	public void setRsDanhGia(ResultSet rsDanhGia);
	
	public String getSanphamTen();
	public void setSanphamTen(String sanphamTen);
	
	public ResultSet getRsTieuChuan1();
	public void setRsTieuChuan1(ResultSet rsTieuChuan1);

}
