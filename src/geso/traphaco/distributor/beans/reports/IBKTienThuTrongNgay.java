package geso.traphaco.distributor.beans.reports;

import java.sql.ResultSet;

public interface IBKTienThuTrongNgay {

	public String getNgayKS();
	public String getNPPID();
	public void setUserId(String userId);
	public String getUserId();
	
	public void setTuNgay(String tuNgay);
	public String getTuNgay();
	public void setDenNgay(String denNgay);
	public String getDenNgay();
	
	public void setKhIds(String khIds);
	public String getKhIds();
	public void setKhRs(ResultSet khRs);
	public ResultSet getKhRs();
	
	public void setNvbhIds(String nvbhIds);
	public String getNvbhIds();
	public void setNvbhRs(ResultSet nvbhRs);
	public ResultSet getNvbhRs();
	
	public void setNvgnIds(String nvgnIds);
	public String getNvgnIds();
	public void setNvgnRs(ResultSet nvgnRs);
	public ResultSet getNvgnRs();
	
	public void setPhieuNopTienRs(ResultSet phieunoptienRs);
	public ResultSet getPhieuNopTienRs();
	public void setPhieuNopTienIds(String PhieuNopTienIds);
	public String getPhieuNopTienIds();
	
	public void init();
	public ResultSet getBKTienThuTrongNgay();
	
	public ResultSet getBKTienThuTrongNgay_KT();
	public void DBclose();
	public void createRs();
}
