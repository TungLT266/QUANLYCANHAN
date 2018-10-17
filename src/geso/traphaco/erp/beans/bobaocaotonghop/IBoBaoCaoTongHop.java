package geso.traphaco.erp.beans.bobaocaotonghop;

import java.sql.ResultSet;

public interface IBoBaoCaoTongHop 
{
	public void setuserId(String userId);

	public String getuserId();

	public void setCtyId(String ctyId);
	
	public String getCtyId();
	
	public void setMsg(String msg);

	public String getMsg();

	public String getDate();

	public void setMonth(String month);
	
	public String getMonth();

	public void setYear(String year);

	public String getYear();
	
	public String getCtyTen();
	
	public String getDiachi();

	public String getMasothue();
	
	public void init();
	
	public ResultSet getData();
	
	public void DBClose();

//	public Double getNoTruCo(String sohieu , boolean kytruoc);
	
	public Double getNoTruCo_TheoDoiTuong(String sohieu , boolean kytruoc);
	public Double getCoTruNo_TheoDoiTuong(String sohieu , boolean kytruoc);
	
	
	
	public Double getCoTruNo(String sohieu , boolean dauki);
	public Double getNo(String sohieu , boolean dauki, String ctyId);
	public Double getCo(String sohieu , boolean dauki, String ctyId);
	public Double getNoTruCoCuoiKy(String sohieu, String ctyId);
	
	
	public void setCtyRs(ResultSet ctyRs);
	public ResultSet getCtyRs();
	
	
	public String getErpCongtyId();
	public void setErpCongtyId(String rs);
	public void setView(String view);

	public String getView();
	
	public String getNppId();
	public void setNppId(String nppId);
	public ResultSet getNppRs();
	public void setNppRs(ResultSet nppRs);
	public void createRs();
	
	public ResultSet getRsCDPS();
	public ResultSet getRsCDKT();
	public ResultSet getRsHDKD();
	//Duong
	public Double getNo_New(String soHieu, boolean kyTruoc);
	public Double getCo_New(String soHieu, boolean kyTruoc);
	public double getTongPhatSinh(int loai, String Sohieu, String SohieuDoiung);
	public double getTongPhatSinh_Kytruoc(int loai, String Sohieu, String SohieuDoiung);
	
	public String getTieuChiLayBC() ;


	public void setTieuChiLayBC(String tieuChiLayBC);
	


	public String getTuNgay();


	public void setTuNgay(String tuNgay);


	public String getDenNgay() ;
	public void setDenNgay(String denNgay) ;
	

	public int[] getNamBC();
	


	public void setNamBC(int[] namBC);
	
}
