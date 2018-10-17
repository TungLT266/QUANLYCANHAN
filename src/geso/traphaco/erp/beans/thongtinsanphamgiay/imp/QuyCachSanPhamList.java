package geso.traphaco.erp.beans.thongtinsanphamgiay.imp;

import java.util.ArrayList;
import java.util.List;

import geso.traphaco.erp.beans.thongtinsanphamgiay.IQuyCachSanPhamList;
import geso.traphaco.erp.beans.thongtinsanphamgiay.IQuyDoi;

public class QuyCachSanPhamList implements IQuyCachSanPhamList
{
	private String MA;
	private String Issua;
	private String Id;
	private String Dai;
	private String Rong;
	private String TrongLuong;
	private String Mau;
	private String DinhLuong;
	private String DuocSua;
	private String MaKeToan;
	private String DVDL_Dai;
	private String DVDL_Rong;
	private String DVDL_TrongLuong;
	private String DVDL_DinhLuong;
	private String DonvidoluongID;
	private String thetich;
	private String nguongoc;
	private String soluongchuan;
	private String soluongquydoi;
	private String donvidoluong_quydoi;
	private String tendonvidoluong;
	private String tendonvidoluong_quydoi;
	private String Trangthai;
	
	String DauLon;
	String DauNho;
	String MauIn;
	String DuongKinhTrong;
	String DoDay;
	String LoGo;
	String Dvdl_Doday;
	String dvdl_Dktrong;
	String dvdl_daulon;
	String dvdl_daunho;
	String chuannen;
	String DaiDay, dvdl_DaiDay;
	String maMetro;
	List<IQuyDoi> quyDoiList;
	
	public QuyCachSanPhamList()
	{
		this.MA = "";
		this.Id = "";
		this.Dai = "";
		this.Rong = "";
		this.TrongLuong = "";
		this.Mau = "";
		this.DinhLuong = "";
		this.DuocSua = "";
		this.MaKeToan = "";
		this.DVDL_Dai = "";
		this.DVDL_Rong = "";
		this.DVDL_DinhLuong = "";
		this.DVDL_TrongLuong = "";
		this.DonvidoluongID = "";
		this.thetich = "";
		this.nguongoc = "";
		this.soluongchuan = "";
		this.soluongquydoi = "";
		this.donvidoluong_quydoi = "";
		
		this.DauLon = "";
		this.DauNho = "";
		this.MauIn = "";
		this.DuongKinhTrong = "";
		this.DoDay = "";
		this.LoGo = "";
		this.Dvdl_Doday = "";
		this.dvdl_Dktrong = "";
		this.dvdl_daulon = "";
		this.dvdl_daunho = "";
		this.chuannen = "";
		this.maMetro="";
		this.DaiDay = "";
		this.dvdl_DaiDay = "";
		this.Trangthai="";
		this.quyDoiList = new ArrayList<IQuyDoi>();
	}
	
	public String getDai() {
		
		return this.Dai;
	}

	
	public void setDai(String dai) {
		
		this.Dai=dai;
	}

	
	public String getRong() {
		
		return this.Rong;
	}

	
	public void setRong(String rong) {
		
		this.Rong=rong;
	}

	
	public String getTrongLuong() {
		
		return this.TrongLuong;
	}

	
	public void setTrongLuong(String trongluong) {
		
		this.TrongLuong=trongluong;
	}

	
	public String getMau() {
		
		return this.Mau;
	}

	
	public void setMau(String mau) {
		
		this.Mau=mau;
	}

	
	public void setDinhLuong(String dinhluong) {
		
		this.DinhLuong=dinhluong;
	}

	
	public String getDinhLuong() {
		
		return this.DinhLuong;
	}

	
	public void setId(String Id) {
		
		this.Id=Id;
	}

	
	public String getId() {
		
		return this.Id;
	}

	
	public void setDuocSua(String duocsua) {
		
		this.DuocSua=duocsua;
	}

	
	public String getDuocSua() {
		
		return this.DuocSua;
	}

	
	public void setMaKeToan(String maketoan) {
		
		this.MaKeToan=maketoan;
	}

	
	public String getMaKeToan() {
		
		return this.MaKeToan;
	}

	
	public void setDVDL_Dai(String dvdl_dai) {
		
		this.DVDL_Dai=dvdl_dai;
	}

	
	public String getDVDL_Dai() {
		
		return this.DVDL_Dai;
	}

	
	public void setDVDL_Rong(String DVDL_rong) {
		
		this.DVDL_Rong=DVDL_rong;
	}

	
	public String getDVDL_Rong() {
		
		return this.DVDL_Rong;
	}

	
	public void setDVDL_TrongLuong(String DVDL_Trongluong) {
		
		this.DVDL_TrongLuong=DVDL_Trongluong;
	}

	
	public String getDVDL_TrongLuong() {
		
		return this.DVDL_TrongLuong;
	}

	
	public void setDVDL_DinhLuong(String DVDL_DinhLuong) {
		
		this.DVDL_DinhLuong=DVDL_DinhLuong;
	}

	
	public String getDVDL_DinhLuong() {
		
		return this.DVDL_DinhLuong;
	}

	
	public void setDonvidoluong(String donvidoluong) {
		
		this.DonvidoluongID=donvidoluong;
	}

	
	public String getDonvidoluong() {
		
		return this.DonvidoluongID;
	}

	
	public void setNguongoc(String nguongoc) {
		
		this.nguongoc = nguongoc;
	}


	public String getNguongoc() {
		
		return this.nguongoc;
	}


	
	public void setSoluongchuan(String soluongchuan) {
		
		this.soluongchuan = soluongchuan;
	}


	
	public String getSoluongchuan() {
		
		return this.soluongchuan;
	}


	
	public void setSoluongquidoi(String soluongquydoi) {
		
		this.soluongquydoi = soluongquydoi;
	}


	
	public String getSoluongquidoi() {
		
		return this.soluongquydoi;
	}


	
	public void setDonvidoluong_quydoi(String quydoi) {
		
		this.donvidoluong_quydoi = quydoi;
	}


	
	public String getDonvidoluong_quydoi() {
		
		return this.donvidoluong_quydoi;
	}


	public void setThetich(String thetich) {
		
		this.thetich = thetich;
	}


	public String getThetich() {
		
		return this.thetich;
	}


	
	public String getMA() {
		
		return this.MA;
	}


	
	public void setMA(String MA) {
		
		this.MA = MA;
	}

	
	public void setDaulon(String daulon) {
		
		this.DauLon = daulon;
	}

	
	public String getDaulon() {
		
		return this.DauLon;
	}

	
	public void setDaunho(String daunho) {
		
		this.DauNho = daunho;
	}

	
	public String getDaunho() {
		
		return this.DauNho;
	}

	
	public void setMauin(String mauin) {
		
		this.MauIn = mauin;
	}

	
	public String getMauin() {
		
		return this.MauIn;
	}

	
	public void setDuongkinhtrong(String duongkinhtrong) {
		
		this.DuongKinhTrong = duongkinhtrong;
	}

	
	public String getDuongkinhtrong() {
		
		return this.DuongKinhTrong;
	}

	
	public void setDoday(String doday) {
		
		this.DoDay = doday;
	}

	
	public String getDoday() {
		
		return this.DoDay;
	}

	
	public void setLogo(String logo) {
		
		this.LoGo = logo;
	}

	
	public String getLogo() {
		
		return this.LoGo;
	}

	
	public void setDVDL_Doday(String dvdl_doday) {
		
		this.Dvdl_Doday = dvdl_doday;
	}

	
	public String getDVDL_Doday() {
		
		return this.Dvdl_Doday;
	}

	
	public void setDVDL_Dktrong(String dvdl_dktrong) {
		
		this.dvdl_Dktrong = dvdl_dktrong;
	}

	
	public String getDVDL_Dktrong() {
		
		return this.dvdl_Dktrong;
	}

	
	public void setDVDL_Daulon(String dvdl_daulon) {
		
		this.dvdl_daulon = dvdl_daulon;
	}

	
	public String getDVDL_Daulon() {
		
		return this.dvdl_daulon;
	}

	
	public void setDVDL_Daunho(String dvdl_daunho) {
		
		this.dvdl_daunho = dvdl_daunho;
	}

	
	public String getDVDL_Daunho() {
		
		return this.dvdl_daunho;
	}

	
	public void setChuannen(String chuannen) {
		
		this.chuannen = chuannen;
	}


	public String getChuannen() {
		
		return this.chuannen;
	}

	@Override
	public void setTenDonvidoluong(String tendonvidoluong) {
		this.tendonvidoluong=tendonvidoluong;
	}

	@Override
	public String getTenDonvidoluong() {
		return this.tendonvidoluong;
	}

	@Override
	public void setTenDonvidoluong_quydoi(String tenquydoi) {
		this.tendonvidoluong_quydoi=tenquydoi;
	}

	@Override
	public String getTenDonvidoluong_quydoi() {
		return this.tendonvidoluong_quydoi;
	}

	@Override
	public void setIssua(String issua) {
		this.Issua=issua;
	}

	@Override
	public String getIssua() {
		return this.Issua;
	}
	

	
	public String getDaiDay() {
		
		return this.DaiDay;
	}

	
	public void setDaiDay(String daiday) {
		
		this.DaiDay = daiday;
	}
	
	public String getDvdl_DaiDay() {
		
		return this.dvdl_DaiDay;
	}

	
	public void setDvdl_DaiDay(String daiday) {
		
		this.dvdl_DaiDay = daiday;
	}

	@Override
	public List<IQuyDoi> getQuyDoiList() {
		return this.quyDoiList;
	}

	@Override
	public void setQuyDoiList(List<IQuyDoi> quyDoiList) {
		this.quyDoiList = quyDoiList;
	}

	@Override
	public void setTrangthai(String _Trangthai) {
		// TODO Auto-generated method stub
		this.Trangthai=_Trangthai;
	}

	@Override
	public String getTrangthai() {
		// TODO Auto-generated method stub
		return this.Trangthai;
	}

	
	public void setMaMetro(String maMetro) {
		this.maMetro = maMetro;
		
	}


	public String getMaMetro() {

		return this.maMetro;
	}

	

}
