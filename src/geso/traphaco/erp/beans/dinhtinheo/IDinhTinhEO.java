package geso.traphaco.erp.beans.dinhtinheo;

import geso.traphaco.erp.beans.dinhtinheo.imp.DinhTinhEODetail;
import geso.traphaco.erp.db.sql.dbutils;

import java.sql.ResultSet;
import java.util.List;

public interface IDinhTinhEO {
	public String getId();
	public void setId(String id);
	public String getNgayChungTu();
	public void setNgayChungTu(String ngayChungTu) ;
	public ResultSet getRsSanPham();
	public void setRsSanPham(ResultSet rsSanPham);
	public ResultSet getRsKho();
	public void setRsKho(ResultSet rsKho);
	public String getUserId();
	public void setUserId(String userId);
	public String getMsg();
	public void setMsg(String msg);
	public String getKhoId();
	public void setKhoId(String khoId);
	public String getSanPham();
	public void setSanPham(String sanPham);
	public String getLoai();
	public void setLoai(String loai);
	public String getTrangThai();
	public void setTrangThai(String trangThai);
	public String getDienGiai();
	public void setDienGiai(String dienGiai);
	public String getNguoiTao();
	public void setNguoiTao(String nguoiTao);
	public String getNguoiSua();
	public void setNguoiSua(String nguoiSua);
	public String getNgayTao();
	public void setNgayTao(String ngayTao);
	public String getNgaySua();
	public void setNgaySua(String ngaySua);
	public dbutils getDb();
	public void setDb(dbutils db);
	public List<DinhTinhEODetail> getListDetail();
	public void setListDetail(List<DinhTinhEODetail> listDetail) ;
	public void init(String query);
	public void initView();
	public void DBclose();
	public void createRs();
	public boolean create();
	public boolean update();
}
