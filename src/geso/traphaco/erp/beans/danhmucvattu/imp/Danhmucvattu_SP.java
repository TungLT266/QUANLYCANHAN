package geso.traphaco.erp.beans.danhmucvattu.imp;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import geso.traphaco.erp.beans.danhmucvattu.IDanhmucvattu_SP;
import geso.traphaco.erp.beans.lenhsanxuatgiay.ISpSanxuatChitiet;
import geso.traphaco.erp.beans.phieuxuatkho.ISpDetail;

public class Danhmucvattu_SP implements IDanhmucvattu_SP
{
	String Khoid, BomId, Idvt, MaVatTu, TenVatTu, Dvtvt, SoLuong,SoLuongChuan, IdvatuTT, MaVatTuTT, TenVatTuTT, DvtTT, TiLe, SoLuongTT,
		SoLuongTon, SoLuongTonTT, Diengiaicongdoan,Loai,DoRong;
	String tenkho;
	String soluongTHThucte;
	String SlTonKhoSX;
	String CongDoanId;
	String Ghichu;
	String Dongdu;
	String  SoluongDaYC;

	String dungsai;
	
	String soluongxuat="0";
	
	String soluongdatieuhao="0";
	List<ISpDetail> spDetailList;
 
	List<ISpSanxuatChitiet> splistCt;
	List<ISpSanxuatChitiet> splistCt_DaYc;
	
	ResultSet rsSpthaythe;
	List<IDanhmucvattu_SP> ListThayThe;
	double trongluong;
	double Soi;
	double HaoHut;
	String isTinhHA;
	String isTinhHL;
	String hamam;
	String hamluong;
	String isNLTieuHao;
	String chon;
	double DinhLuong;
	double So_gsm_le;
	String Ischongbui="0";
	String MA="";
	String IsNl_TieuHao;
	String mavattuTT;
	
	
	String[] maNLTT;
	String[] soluongTT;
	
	ResultSet rstonkho;
	
	public Danhmucvattu_SP()
	{
		this.Dongdu="";
		this.Ghichu="";
		this.tenkho="";
		this.BomId = "";
		this.Idvt = "";
		this.IsNl_TieuHao="";
		this.MaVatTu = "";
		this.TenVatTu = "";
		this.SoLuong = "";
		this.dungsai = "0";
		this.SoLuongChuan = "";
		this.Dvtvt = "";
		this.IdvatuTT="";
		this.MaVatTuTT = "";
		this.TenVatTuTT = "";
		this.DvtTT = "";
		this.TiLe = "";
		this.SoLuongTT = "";
		this.CongDoanId = "";
		this.soluongTHThucte = "";
		this.spDetailList = new ArrayList<ISpDetail>();
		this.SoLuongTon = "";
		this.Diengiaicongdoan = "";
		this.DoRong="";
		this.chon="";
		this.trongluong=0;
		this.Soi=0;
		this.DinhLuong=0;
		this.HaoHut=0;
		this.isNLTieuHao="0";
		this.SlTonKhoSX="0";
		this.isTinhHA = "0";
		this.isTinhHL = "0";
		this.hamam = "";
		this.hamluong = "";
		this.mavattuTT = "";
		this.SoluongDaYC="";
	}

	public String getIdVT()
	{
		return Idvt;
	}

	public void setIdVT(String idvt)
	{
		this.Idvt = idvt;
	}

	public String getMaVatTu()
	{
		return MaVatTu;
	}

	public void setMaVatTu(String mavattu)
	{
		this.MaVatTu = mavattu;
	}

	public String getMaVatTuTT()
	{
		return MaVatTuTT;
	}

	public void setMaVatTuTT(String mavattuTT)
	{
		this.MaVatTuTT = mavattuTT;
	}

	public String getSoLuong()
	{
		return SoLuong;
	}

	public void setSoLuong(String soluong)
	{
		this.SoLuong = soluong;
	}

	public String getTenVatTu()
	{
		return TenVatTu;
	}

	public void setTenVatTu(String tenvattu)
	{
		this.TenVatTu = tenvattu;
	}

	public String getDvtVT()
	{
		return Dvtvt;
	}

	public void setDvtVT(String dvtvt)
	{
		this.Dvtvt = dvtvt;
	}

	public String getMaVatTuThayThe()
	{
		return this.MaVatTuTT;
	}

	public void setMaVatTuThayThe(String mavattuTT)
	{
		this.MaVatTuTT = mavattuTT;
	}

	public String getTenVatTuThayThe()
	{
		return this.TenVatTuTT;
	}

	public void setTenVatTuThayThe(String tenvattuTT)
	{
		this.TenVatTuTT = tenvattuTT;
	}

	public String getTile()
	{
		return this.TiLe;
	}

	public void setTile(String tile)
	{
		this.TiLe = tile;
	}

	public String getDvtThayThe()
	{
		return this.DvtTT;
	}

	public void setDvtThayThe(String dvtTT)
	{
		this.DvtTT = dvtTT;
	}

	public List<ISpDetail> getSpDetailList()
	{
		return this.spDetailList;
	}

	public void setSpDetailList(List<ISpDetail> spDetailList)
	{
		this.spDetailList = spDetailList;
	}

	public String getSoLuongTHThucTe()
	{
		return this.soluongTHThucte;
	}

	public void setSoLuongTHThucTe(String soluong)
	{
		this.soluongTHThucte = soluong;
	}

	public String getCongDoanId()
	{

		return this.CongDoanId;
	}

	public void setCongDoanId(String CdId)
	{

		this.CongDoanId = CdId;
	}

	public String getSoluongTon()
	{

		return this.SoLuongTon;
	}

	public void setSoLuongTon(String soluongton)
	{

		this.SoLuongTon = soluongton;
	}

	public String getTenCongDoan()
	{

		return this.Diengiaicongdoan;
	}

	public void setTenCongDoan(String TenCongDoan)
	{

		this.Diengiaicongdoan = TenCongDoan;
	}

	public String getBomId()
	{

		return this.BomId;
	}

	public void setBomId(String _BomId)
	{

		this.BomId = _BomId;
	}

	public ResultSet getRsSpThayThe()
	{

		return this.rsSpthaythe;
	}

	public void setRsSpThayThe(ResultSet rs)
	{

		this.rsSpthaythe = rs;
	}

	public String getIdVatTuThayThe()
	{

		return this.IdvatuTT;
	}

	public void setIdVatTuThayThe(String idvttt)
	{

		this.IdvatuTT = idvttt;
	}

	public void SetKhoid(String khoid)
	{

		this.Khoid = khoid;
	}

	public String getkhoid()
	{

		return this.Khoid;
	}

	public List<IDanhmucvattu_SP> getListThayThe()
	{

		return this.ListThayThe;
	}

	public void setListThayThe(List<IDanhmucvattu_SP> spList)
	{

		this.ListThayThe = spList;
	}

	public String getSoluongTonTT()
	{

		return this.SoLuongTonTT;
	}

	public void setSoluongTonTT(String SoluongTonTT)
	{

		this.SoLuongTonTT = SoluongTonTT;
	}

	
	public void setLoai(String Loai) {
	
		this.Loai=Loai;
	}

	
	public String getLoai() {
	
		return this.Loai;
	}

	
	public String getDoRong() {
	
		return this.DoRong;
	}

	
	public void setDoRong(String Rong) {
	
		this.DoRong=Rong;
	}

	
	public String getkhoten() {
	
		return this.tenkho;
	}

	
	public void setKhoten(String khoten) {
	
		this.tenkho=khoten;
		
	}

	
	public void setChon(String chon) {
	
		this.chon=chon;
	}

	
	public String getChon() {
	
		return this.chon;
	}

	
	public void SetTrongLuong(double trongluong) {
	
		this.trongluong=trongluong;
	}

	
	public void SetSoi(double soi) {
	
		this.Soi=soi;
	}

	
	public double getTrongLuong() {
	
		return this.trongluong;
	}

	
	public double getSoi() {
	
		return this.Soi;
	}

	
	public double getDinhLuong() {
	
		return this.DinhLuong;
	}

	
	public void SetDinhluong(double dinhluong) {
	
		this.DinhLuong=dinhluong;
	}

	
	public double getSo_gsm_le() {
	
		return this.So_gsm_le;
	}

	
	public void SetSo_gsm_le(double So_gsm_le) {
	
		this.So_gsm_le=So_gsm_le;
	}

	
	public String getIsChongBui() {
	
		return this.Ischongbui;
	}

	
	public void SetIsChongBui(String IsChongBui) {
	
		this.Ischongbui=IsChongBui;
	}

	
	public String getMA() {
	
		return this.MA;
	}

	
	public void setMA(String MA) {
	
		this.MA=MA;
	}

	
	public double getHaoHut() {
	
		return this.HaoHut;
	}

	
	public void setHaoHut(double haohut) {
	
		this.HaoHut=haohut;
	}

	public String getHamam() {
		
		return this.hamam;
	}


	public void setHamam(String hamam) {
		if(hamam.trim().length() == 0) hamam = "0";
		this.hamam = hamam;
	}

	public String getIsTinhHA() {
		
		return this.isTinhHA;
	}


	public void setIsTinhHA(String tinhHA) {
		
		this.isTinhHA = tinhHA;
	}


	public String getHamluong() {
		
		return this.hamluong;
	}


	public void setHamluong(String hamluong) {
		if(hamluong.trim().length() == 0) hamluong = "0";
		this.hamluong = hamluong;
	}

	public String getIsTinhHL() {
		
		return this.isTinhHL;
	}


	public void setIsTinhHL(String tinhHL) {
		
		this.isTinhHL = tinhHL;
	}

	public String getIsNLTieuHao() {
		
		return this.isNLTieuHao;
	}


	public void setIsNLTieuHao(String nltieuhao) {
		this.isNLTieuHao=nltieuhao;
	}


	public String getSoluongTonKhoSx() {
		
		return this.SlTonKhoSX;
	}


	public void setSoluongTonKhoSx(String SoluongTonKhoSx) {
		
		this.SlTonKhoSX=SoluongTonKhoSx;
	}


	public ResultSet getRsTonKho() {
		
		return this.rstonkho;
	}


	public void setRsTonkho(ResultSet rs) {
		this.rstonkho=rs;
	}


	public String getSoLuongChuan() {
		return this.SoLuongChuan;
	}


	public void setSoLuongChuan(String soluongchuan) {
		this.SoLuongChuan=soluongchuan;
	}

	public String[] getMaNLTT() {
		return this.maNLTT;
	}


	public void setMaNLTT(String[] maNLTT) {
		this.maNLTT = maNLTT;
	}

	public String[] getSoluongNLTT() {
		return this.soluongTT;
	}


	public void setSoluongNLTT(String[] soluongTT) {
		this.soluongTT = soluongTT;
	}

	public void DBClose()
	{
		try {
			if (this.spDetailList != null)
				this.spDetailList.clear();
			if (this.rsSpthaythe != null)
				this.rsSpthaythe.close();
			if (this.ListThayThe != null)
				this.ListThayThe.clear();
			if (this.rstonkho != null)
				this.rstonkho.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@Override
	public List<ISpSanxuatChitiet> getListCTkho() {
		// TODO Auto-generated method stub
		return splistCt;
	}

	@Override
	public void setListCTkho(List<ISpSanxuatChitiet> listCt) {
		// TODO Auto-generated method stub
		splistCt= listCt;
	}

	@Override
	public List<ISpSanxuatChitiet> getListCT_DaYCCK() {
		// TODO Auto-generated method stub
		return splistCt_DaYc;
	}

	@Override
	public void setListCT__DaYCCK(List<ISpSanxuatChitiet> listCt) {
		// TODO Auto-generated method stub
		splistCt_DaYc=listCt;
	}

	@Override
	public String getDongdu() {
		// TODO Auto-generated method stub
		return this.Dongdu;
	}

	@Override
	public void setDongdu(String Dongdu) {
		// TODO Auto-generated method stub
		this.Dongdu=Dongdu;
	}

	@Override
	public String getGhichu() {
		// TODO Auto-generated method stub
		return this.Ghichu;
	}

	@Override
	public void setGhichu(String ghichu) {
		// TODO Auto-generated method stub
		this.Ghichu=ghichu;
	}

	@Override
	public String getSoLuongXuat() {
		// TODO Auto-generated method stub
		return soluongxuat;
	}

	@Override
	public void setSoLuongXuat(String soluongxuat) {
		// TODO Auto-generated method stub
		this.soluongxuat=soluongxuat;
	}
	@Override
	public String getSoLuongDaTieuHao() {
		// TODO Auto-generated method stub
		return this.soluongdatieuhao;
	}

	@Override
	public void setSoLuongDaTieuHao(String soluongdatieuhao) {
		// TODO Auto-generated method stub
		this.soluongdatieuhao=soluongdatieuhao;
	}

	@Override
	public void setSoluongDaYC(String soluong) {
		// TODO Auto-generated method stub
		this.SoluongDaYC=soluong;
	}

	@Override
	public String getSoluongDaYC() {
		// TODO Auto-generated method stub
		return this.SoluongDaYC;
	}

	public String getDungsai() {
		return dungsai;
	}
	public void setDungsai(String dungsai) {
		this.dungsai = dungsai;
	}
}
