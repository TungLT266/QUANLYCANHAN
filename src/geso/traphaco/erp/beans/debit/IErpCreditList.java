package geso.traphaco.erp.beans.debit;

import geso.traphaco.erp.db.sql.dbutils;

import java.sql.ResultSet;

public interface IErpCreditList {
	public String getNgayGhiNhan();
	public void setNgayGhiNhan(String ngayGhiNhan);
	public String getNguoiTao();
	public void setNguoiTao(String nguoiTao);
	public String getDoiTuong();
	public void setDoiTuong(String doiTuong);
	public dbutils getDb();
	public void setDb(dbutils db);
	public String getDienGiai();
	public void setDienGiai(String dienGiai);
	public String getMa();
	public void setMa(String ma);
	public String getUserId();
	public void setUserId(String userId);
	public String getTrangThai();
	public void setTrangThai(String trangThai);
	public ResultSet getRsDebit();
	public void setRsDebit(ResultSet rsDebit);
	public String getMsg();
	public void setMsg(String msg) ;
	
	public void init(String query);
	public boolean delete(String id);
	public boolean chot(String id);
	public void DbClose();
}
