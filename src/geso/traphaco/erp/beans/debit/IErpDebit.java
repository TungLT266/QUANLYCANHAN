package geso.traphaco.erp.beans.debit;

import java.sql.ResultSet;
import java.util.List;

import geso.traphaco.erp.beans.debit.imp.TaiKhoan;
import geso.traphaco.erp.beans.debit.imp.TrungTamChiPhi;
import geso.traphaco.erp.db.sql.dbutils;

public interface IErpDebit {
	public String getId();
	public void setId(String id);
	public String getNgayGhiNhan();
	public void setNgayGhiNhan(String ngayGhiNhan);
	public String getDoiTuong();
	public void setDoiTuong(String doiTuong);
	public String getLoaiDoiTuong();
	public void setLoaiDoiTuong(String loaiDoiTuong);
	public String getDienGiai();
	public void setDienGiai(String dienGiai);
	public double getTongTienNguyenTe();
	public void setTongTienNguyenTe(double tongTienNguyenTe);
	public double getTongTienVND();
	public void setTongTienVND(double tongTienVND);
	public String getNguoiTao();
	public void setNguoiTao(String nguoiTao);
	public String getNguoiSua();
	public void setNguoiSua(String nguoiSua);
	public String getNgayTao();
	public void setNgayTao(String ngayTao);
	public String getNgaySua();
	public void setNgaySua(String ngaySua);
	public dbutils getDb() ;
	public void setDb(dbutils db) ;
	public String getUserId() ;
	public void setUserId(String userId);
	public String getMsg();
	public void setMsg(String msg) ;
	public ResultSet getRsDoiTuong();
	public void setRsDoiTuong(ResultSet rsDoiTuong);
	public List<TaiKhoan> getListTaiKhoan();
	public void setListTaiKhoan(List<TaiKhoan> listTaiKhoan);
	public List<TrungTamChiPhi> getListTrungTamChiPhi() ;
	public void setListTrungTamChiPhi(List<TrungTamChiPhi> listTrungTamChiPhi);
	public double getTigia();
	public void setTigia(double tigia) ;
	
	public List<String> getTaiKhoans() ;
	public void setTaiKhoans(List<String> taiKhoans);
	
	public List<Double> getNoNguyenTes();
	public void setNoNguyenTes(List<Double> noNguyenTes);
	public List<Double> getNoVNDs() ;
	public void setNoVNDs(List<Double> noVNDs);
	
	public List<String> getTrungTamChiPhis() ;
	public void setTrungTamChiPhis(List<String> trungTamChiPhis);
	public List<String> getKhoanMucChiPhis() ;
	public void setKhoanMucChiPhis(List<String> khoanMucChiPhis);
	public List<String> getMoTas();
	public void setMoTas(List<String> moTas);
	
	public void init(String tiente, boolean check);
	public boolean create();
	public boolean update();
	public boolean delete();
	public void DbClose();
	public void getTiGiaUSD(String tiente,boolean check);
	
	public String getTienTe();
	public void setTienTe(String tienTe);
	public ResultSet getRsTienTe();
	public void setRsTienTe(ResultSet rsTienTe);
	
	public void getView();

}
