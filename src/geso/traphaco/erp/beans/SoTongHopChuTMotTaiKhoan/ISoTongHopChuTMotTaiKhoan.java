package geso.traphaco.erp.beans.SoTongHopChuTMotTaiKhoan;

import java.sql.ResultSet;

import javax.servlet.ServletOutputStream;

public interface ISoTongHopChuTMotTaiKhoan {

	String getTuNgay();

	void setTuNgay(String tuNgay);

	String getDenNgay();

	void setDenNgay(String denNgay);

	String getTaiKhoan();

	void setTaiKhoan(String taiKhoan);

	String getChiNhanh();

	void setChiNhanh(String chiNhanh);

	boolean xuatExcel(ServletOutputStream out, String fileName);

	void DBClose();

	void initBC();

	ResultSet getChiNhanhRs();

	void setChiNhanhRs(ResultSet chiNhanhRs);

	ResultSet getTaiKhoanRs();

	void setTaiKhoanRs(ResultSet taiKhoanRs);

	void getSoPhatSinh();

	ResultSet getTongSoPhatSinh();

	ResultSet getQuery(int loaiQuery);

	void initRs();

	ResultSet getPhatSinhRs();

	void setPhatSinhRs(ResultSet phatSinhRs);

	Double getDauKyCo();

	void setDauKyCo(Double dauKyCo);
	
	Double getDauKyNo();

	void setDauKyNo(Double dauKyNo);

	Double getTongPSNo();

	void setTongPSNo(Double tongPSNo);

	Double getTongPSCo();

	void setTongPSCo(Double tongPSCo);

	Double getCuoiKyNo();

	void setCuoiKyNo(Double cuoiKyNo);

	Double getCuoiKyCo();

	void setCuoiKyCo(Double cuoiKyCo);


}
