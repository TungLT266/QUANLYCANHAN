package geso.traphaco.erp.beans.kehoachlaymau;

import geso.traphaco.center.util.IPhan_Trang;
import geso.traphaco.erp.beans.kehoachlaymau.imp.PhieuKiemDinh;
import geso.traphaco.erp.db.sql.dbutils;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.List;

public interface IKeHoachLayMau extends Serializable, IPhan_Trang {
	
	public String getUserId();
	public void setUserId(String userId);
	
	public String getId();
	public void setId(String id);
	
	public String getMsg();
	public void setMsg(String msg);
	
	public void DBclose();
	public void createRs();
	public List<PhieuKiemDinh> getDsPhieuKiemDinhs();
	public String getSanphamId() ;
  
	public void setSanphamId(String sanphamId);

	public ResultSet getRsSanPham() ;

	public void setRsSanPham(ResultSet rsSanPham) ;
	 
	public void setDsPhieuKiemDinhs(List<PhieuKiemDinh> dsPhieuKiemDinhs) ;

	public String getCtyId() ;
	public void setCtyId(String ctyId) ;

	public String getNppId() ;

	public void setNppId(String nppId) ;
 
	public dbutils getDb() ;
	public void setDb(dbutils db);

	public String getNgayKeHoach();

	public void setNgayKeHoach(String ngayKeHoach);
 
	public String getDienGiai() ;

	public void setDienGiai(String dienGiai) ;

	public String getPhongBanId() ;

	public void setPhongBanId(String phongBanId) ;
 
	public ResultSet getRsPhongBan() ;

	public void setRsPhongBan(ResultSet rsPhongBan) ;
 
	public boolean create();
	public boolean update();
	public void init();
	
	public String getSoTT();

	public void setSoTT(String soTT);
	
	public String getNgayDanhGia();

	public void setNgayDanhGia(String ngayDanhGia);

	public String getNgayDanhGiaLai();

	public void setNgayDanhGiaLai(String ngayDanhGiaLai);

	public String getGhiChu();

	public void setGhiChu(String ghiChu);
	
	public String getTrangThai();

	public void setTrangThai(String trangthai);
	
	public boolean chot(String Id);
	public boolean bochot(String Id);
	public boolean xoa(String Id);

}
