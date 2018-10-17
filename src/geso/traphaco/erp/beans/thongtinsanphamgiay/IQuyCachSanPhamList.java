package geso.traphaco.erp.beans.thongtinsanphamgiay;

import java.util.List;

import geso.traphaco.erp.beans.thongtinsanphamgiay.IQuyDoi;

public interface IQuyCachSanPhamList {
	
	public String getMA();
	public void setMA(String MA);
	
	public String getDai();
	public void setDai(String Dai);
	
	public String getRong();
	public void setRong(String rong);
	
	public String getTrongLuong();
	public void setTrongLuong(String trongluong);
	
	public String getMau();
	public void setMau(String Mau);
	
	public void setDinhLuong(String DinhLuong);
	public String getDinhLuong();
	
	public void setId(String Id);
	public String getId();
	
	public void setIssua(String issua);
	public String getIssua();
	// set trạng thái của sản phẩm có được sửa thông tin quy cách nữa ko? 1 là được sửa,0 là ko được sửa.
	
	public void setDuocSua(String duocsua);
	public String getDuocSua();
	
	/****** NHOM **********/
	public void setMaKeToan(String maketoan);
	public String getMaKeToan();
	public void setDVDL_Dai(String dvdl_dai);
	public String getDVDL_Dai();
	public void setDVDL_Rong(String DVDL_rong);
	public String getDVDL_Rong();
	public void setDVDL_TrongLuong(String DVDL_TrongLuong);
	public String getDVDL_TrongLuong();
	public void setDVDL_DinhLuong(String DVDL_DinhLuong);
	public String getDVDL_DinhLuong();
	public void setDonvidoluong(String donvidoluong);
	public String getDonvidoluong();
	
	public void setTenDonvidoluong(String tendonvidoluong);
	public String getTenDonvidoluong();
	
	public void setThetich(String thetich);
	public String getThetich();
	
	public void setNguongoc(String nguongoc);
	public String getNguongoc();
	
	/***** END NHOM ************/
	
	
	/***** LOI *****************/
	
	//Ong CONE
	public void setDaulon(String daulon);
	public String getDaulon();
	public void setDaunho(String daunho);
	public String getDaunho();
	public void setMauin(String mauin);
	public String getMauin();
	
	//Ong CORE
	public void setDuongkinhtrong(String duongkinhtrong);
	public String getDuongkinhtrong();
	public void setDoday(String doday);
	public String getDoday();
	public void setLogo(String logo);
	public String getLogo();
	public void setChuannen(String chuannen);
	public String getChuannen();
	
	public void setDVDL_Doday(String dvdl_doday);
	public String getDVDL_Doday();
	public void setDVDL_Dktrong(String dvdl_dktrong);
	public String getDVDL_Dktrong();
	public void setDVDL_Daulon(String dvdl_daulon);
	public String getDVDL_Daulon();
	public void setDVDL_Daunho(String dvdl_daunho);
	public String getDVDL_Daunho();
	
	public void setTrangthai(String Trangthai);
	public String getTrangthai();
	
	//Ong DTO, POY ( duong kính trong, dài, dày, logo, mau in, mau sac )
	
	
	/***** END LOI *************/
	
	public void setMaMetro(String maMetro);
	public String getMaMetro();
	
	
	public void setSoluongchuan(String soluongchuan);
	public String getSoluongchuan();
	
	public void setSoluongquidoi(String soluongquydoi);
	public String getSoluongquidoi();
	
	public void setDonvidoluong_quydoi(String quydoi);
	public String getDonvidoluong_quydoi();
	
	public void setTenDonvidoluong_quydoi(String tenquydoi);
	public String getTenDonvidoluong_quydoi();
	
	public String getDaiDay();
	public void setDaiDay(String daiday);
	public String getDvdl_DaiDay();
	public void setDvdl_DaiDay(String daiday);
	
	public List<IQuyDoi> getQuyDoiList();
	public void setQuyDoiList(List<IQuyDoi> quyDoiList);
	
}

