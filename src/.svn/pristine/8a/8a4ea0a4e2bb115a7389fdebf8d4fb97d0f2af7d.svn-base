package geso.dms.center.util;

import geso.dms.center.*;

import java.util.ArrayList;
import java.util.List;

public class ThongTinHienThi implements IThongTinHienThi
{
	String id;
	String ngaychungtu;
	String trangthai;
	String sohoadon;
	String httt;
	String noidungtt;
	String khachhang;
	String tongtien;
	String tiente;
	String ngaytao, nguoitao, ngaysua, nguoisua;
	//HOADON NCC
	String PREFIX;
	String ngayghinhan;
	String tenncc;
	String dahoantat;
	String dathanhtoan;
	
	//HOADON BANHANG
	String tenkh;
	String htxuathoadon;
	
	//PHIEUXUATKHO
	String ndx;
	String soxuatkho;
	String ngayxuat;
	String sochungtu;
	String hoadonId;
	String ndxTen;
	String dondathang;
	
	//GIAI NGAN
	String Ngaynhan;
	String Sohopdong;
	String TKVAY;
	String Laisuat;
	String Hinhthuc;
	
	//THANH TOAN NO VAY
	String Tienlai;
	String Tiengoc;
	String Tienphat;
	String Phikhac;
	String Ghichu;
	
	//NHAN HANG
	String dvthTen;
	String PoId;
	String DaRaHd;
	String loaihanghangId;
	
	//THUE NHAP KHAU
	String TNKID;
	String DIENGIAI;
	String SOCHUNGTU;
	String NGAYCHUNGTU;
	String TRANGTHAI;
	String NGAYTAO;
	String NGUOITAO;
	String NGAYSUA;
	String NGUOISUA;
	
	//HOA DON KHAC
	String PoTen;
	
	//HOA DON KHAC
	String Hoantat;
	
	//PHIEU CHI
	String thanhtoantutienvay;
	String vat;
	String phinganhang;	
	String isKTTDuyet;
	
	//DIEU CHUYEN TIEN
	String NHCHUYEN;
	String NHNHAN;
	String TTID;
	String SOTIENVND;
	String SOTIENNT;
	
	//XOA NO NCC
	String tendoituong;
	
	//CHI PHI NHAP KHAU
	String sotokhai;
	
	//HUY PHIEU CHI
	String loaichungtu;
	 
	//CAN TRU CONG NO
	String khchuyenno;
	String khnhanno;
	
	// DE NGHI THANH TOAN
	String noibo;
	String NOTE;
	String Ngaymua;
	String Duyet;
	String Dachot;
	String TongtienAvat;
	String Tongluong;
	String isTBPDuyet;
	String isChotGanMCP;
	
	//HOA DON TAI CHINH
	String sohoadon2;
	
	//XOA NO KH
	String isHuyChungTu;
	
	List<IDinhKhoanKeToan> layDinhkhoanKT;
	private String canhBao;
	
	public ThongTinHienThi()
	{
		this.id = "";
		this.ngaychungtu = "";
		this.trangthai = "";
		this.sohoadon = "";
		this.httt = "";
		this.noidungtt = "";
		this.khachhang = "";
		this.tongtien = "";
		this.tiente = "";
		this.ngaytao = "";
		this.ngaysua = "";
		this.nguoitao = "";
		this.nguoisua = "";
		
		
		//HOADON NCC
		this.PREFIX = "";
		this.ngayghinhan ="";		
		this.tenncc = "";
		this.dahoantat = "";
		this.dathanhtoan = "";
		
		//HOADON BANHANG
		this.tenkh = "";
		this.htxuathoadon = "";
		
		//PHIEUXUATKHO
		this.ndx = "";
		this.soxuatkho = "";
		this.ngayxuat = "";
		this.sochungtu = "";
		this.hoadonId = "";
		this.ndxTen = "";
		this.dondathang = "";
		
		// GIAI NGAN
		this.Ngaynhan = "";
		this.Sohopdong = "";
		this.TKVAY = "";
		this.Laisuat = "";
		this.Hinhthuc = "";
		
		//THANH TOAN NO VAY
		this.Tienlai = "";
		this.Tiengoc = "";
		this.Tienphat = "";
		this.Phikhac = "";
		this.Ghichu = "";
		
		//NHAN HANG
		this.dvthTen = "";
		this.PoId = "";
		this.DaRaHd = "";
		this.loaihanghangId = "";
		
		//THUE NHAP KHAU
		this.DIENGIAI = "";
		this.SOCHUNGTU = "";
		this.NGAYCHUNGTU = "";
		this.TRANGTHAI = "";
		this.NGAYTAO = "";
		this.NGUOITAO = "";
		this.NGAYSUA = "";
		this.NGUOISUA = "";
		this.TNKID = "";
		
		//HOA DON KHAC
		this.PoTen = "";
		
		//HOA DON KHAC
		this.Hoantat = "";
		
		//PHIEU CHI
		this.thanhtoantutienvay = "";
		this.vat = "";
		this.phinganhang = "";
		this.isKTTDuyet = "";
		
		//DIEU CHUYEN TIEN
		this.NHCHUYEN = "";
		this.NHNHAN = "";
		this.TTID = "";		
		this.SOTIENVND = "";
		this.SOTIENNT = "";
		
		//XOA NO NCC
		this.tendoituong = "";
		
		//CHI PHI NHAP KHAU
		this.sotokhai =  "";
		
		//HUY PHIEU CHI
		this.loaichungtu = "";
		
		//CAN TRU CONG NO
		this.khchuyenno = "";
		this.khnhanno = "";
		
		// DE NGHI THANH TOAN
		this.noibo = "";
		this.NOTE = "";
		this.Ngaymua = "";
		this.Duyet = "";
		this.Dachot = "";
		this.TongtienAvat = "";
		this.Tongluong ="";
		this.isTBPDuyet = "";
		this.isChotGanMCP = "";
		
		//HOA DON TAI CHINH
		this.sohoadon2 = "";
		
		//XOA NO KH
		this.isHuyChungTu = "";
		
		this.layDinhkhoanKT = new ArrayList<IDinhKhoanKeToan>();
		
		this.canhBao = "";
	}
	
	public ThongTinHienThi(String id, String ngaychungtu, String sohoadon, String khachhang, String tongtien, String tiente,
			               String httt, String noidungtt,String trangthai, String ngaysua, String nguoisua)
	{
		this.id = id;
		this.ngaychungtu = ngaychungtu;
		this.sohoadon = sohoadon;
		this.khachhang = khachhang;
		this.tongtien = tongtien;
		this.tiente = tiente;
		this.httt = httt;
		this.noidungtt = noidungtt;
		this.trangthai = trangthai;
		this.ngaysua = ngaysua;
		this.nguoisua = nguoisua;
		this.canhBao = "";
	}
	
	
	public String getId() 
	{
		return this.id;
	}

	public void setId(String id) 
	{
		this.id = id;
	}

	public String getNgaychungtu() 
	{
		return this.ngaychungtu;
	}

	public void setNgaychungtu(String ngaychungtu) 
	{
		this.ngaychungtu = ngaychungtu;
	}

	public String getTrangthai() 
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai) 
	{
		this.trangthai = trangthai;
	}

	public String getSohoadon() 
	{
		return this.sohoadon;
	}

	public void setSohoadon(String sohoadon) 
	{
		this.sohoadon = sohoadon;
	}

	public String getHttt() 
	{
		return this.httt;
	}

	public void setHttt(String httt) 
	{
		this.httt = httt;
	}

	public String getNoidungtt() 
	{
		return this.noidungtt;
	}

	public void setNoidungtt(String noidungtt) 
	{
		this.noidungtt = noidungtt;
	}

	public String getTiente() 
	{
		return this.tiente;
	}

	public void setTiente(String tiente) 
	{
		this.tiente = tiente;
	}

	public String getKhachhang() 
	{
		return this.khachhang;
	}

	public void setKhachhang(String khachhang) 
	{
		this.khachhang = khachhang;
	}

	public String getTongtien() 
	{
		return this.tongtien;
	}

	public void setTongtien(String tongtien) 
	{
		this.tongtien = tongtien;
	}

	public String getNgaytao() 
	{
		return this.ngaytao;
	}

	public void setNgaytao(String ngaytao) 
	{
		this.ngaytao = ngaytao;
	}

	public String getNguoitao() 
	{
		return this.nguoitao;
	}

	public void setNguoitao(String nguoitao) 
	{
		this.nguoitao = nguoitao;
	}

	public String getNgaysua() 
	{
		return this.ngaysua;
	}

	public void setNgaysua(String ngaysua) 
	{
		this.ngaysua = ngaysua;
	}

	public String getNguoisua() 
	{
		return this.nguoisua;
	}

	public void setNguoisua(String nguoisua) 
	{
		this.nguoisua = nguoisua;
	}

	public List<IDinhKhoanKeToan> getLayDinhkhoanKT() 
	{
		return this.layDinhkhoanKT;
	}

	public void setLayDinhkhoanKT(List<IDinhKhoanKeToan> layDinhkhoanKT) 
	{
		this.layDinhkhoanKT = layDinhkhoanKT;
	}

	
	public String getPREFIX() {
		
		return this.PREFIX;
	}

	
	public void setPREFIX(String PREFIX) {
		
		this.PREFIX = PREFIX;
	}

	
	public String getNgayghinhan() {
		
		return this.ngayghinhan;
	}

	
	public void setNgayghinhan(String ngayghinhan) {
		
		this.ngayghinhan = ngayghinhan;
	}

	
	public String getTenNCC() {
		
		return this.tenncc;
	}

	
	public void setTenNCC(String tenncc) {
		this.tenncc = tenncc;
		
	}

	
	public String getDahoantat() {
		
		return this.dahoantat;
	}

	
	public void setDahoantat(String dahoantat) {
		
		this.dahoantat = dahoantat;
	}

	
	public String getDathanhtoan() {
		
		return this.dathanhtoan;
	}

	
	public void setDathanhtoan(String dathanhtoan) {
		
		this.dathanhtoan = dathanhtoan;
	}

	
	public String getTenKH() {
		
		return this.tenkh;
	}

	
	public void setTenKH(String tenkh) {
		
		this.tenkh = tenkh;
	}

	
	public String getNoidungxuat() {
		
		return this.ndx;
	}

	
	public void setNoidungxuat(String noidungxuat) {
		
		this.ndx = noidungxuat;
	}

	
	public String getSoxuatkho() {
		
		return this.soxuatkho;
	}

	
	public void setSoxuatkho(String soxuatkho) {
		this.soxuatkho =soxuatkho;
		
	}

	
	public String getNgayxuat() {
		
		return this.ngayxuat;
	}

	
	public void setNgayxuat(String ngayxuat) {
		
		this.ngayxuat = ngayxuat;
	}

	
	public String getSochungtu() {
		
		return this.sochungtu;
	}

	
	public void setSochungtu(String sochungtu) {
		
		this.sochungtu = sochungtu;
	}

	
	public String getHoadonId() {
		
		return this.hoadonId;
	}

	
	public void setHoadonId(String hoadonId) {
		
		this.hoadonId = hoadonId;
	}

	
	public String getndxTen() {
		
		return this.ndxTen;
	}

	
	public void setndxTen(String ndxTen) {
		
		this.ndxTen = ndxTen;
	}

	
	public String getNgaynhan() {
		
		return this.Ngaynhan;
	}

	
	public void setNgaynhan(String ngaynhan) {
		
		this.Ngaynhan = ngaynhan;
	}

	
	public String getSohopdong() {
		
		return this.Sohopdong;
	}

	
	public void setSohopdong(String sohopdong) {
		
		this.Sohopdong = sohopdong;
	}

	
	public String getTKVAY() {
		
		return this.TKVAY;
	}

	
	public void setTKVAY(String TKVAY) {
		
		this.TKVAY = TKVAY;
	}

	
	public String getLaisuat() {
		
		return this.Laisuat;
	}

	
	public void setLaisuat(String Laisuat) {
		
		this.Laisuat = Laisuat;
	}

	
	public String getHinhthuc() {
		
		return this.Hinhthuc;
	}

	
	public void setHinhthuc(String Hinhthuc) {
		
		this.Hinhthuc = Hinhthuc;
	}

	
	public String getTienlai() {
		
		return this.Tienlai;
	}

	
	public void setTienlai(String Tienlai) {
		
		this.Tienlai = Tienlai;
	}

	
	public String getTiengoc() {
		
		return this.Tiengoc;
	}

	
	public void setTiengoc(String Tiengoc) {
		
		this.Tiengoc = Tiengoc;
	}

	
	public String getTienphat() {
		
		return this.Tienphat;
	}

	
	public void setTienphat(String Tienphat) {
		
		this.Tienphat = Tienphat;
	}

	
	public String getPhikhac() {
		
		return this.Phikhac;
	}

	
	public void setPhikhac(String Phikhac) {
		
		this.Phikhac = Phikhac;
	}

	
	public String getGhichu() {
		
		return this.Ghichu;
	}

	
	public void setGhichu(String Ghichu) {
		
		this.Ghichu = Ghichu;
	}

	
	public String getdvthTen() {
		
		return this.dvthTen;
	}

	
	public void setdvthTen(String dvthTen) {
		
		this.dvthTen = dvthTen;
	}

	
	public String getPoId() {
		
		return this.PoId;
	}

	
	public void setPoId(String PoId) {
		
		this.PoId = PoId;
	}

	
	public String getDaRaHd() {
		
		return this.DaRaHd;
	}

	
	public void setDaRaHd(String DaRaHd) {
		
		this.DaRaHd = DaRaHd;
	}

	
	public String getloaihanghoaId() {
		
		return this.loaihanghangId;
	}

	
	public void setloaihanghoaId(String loaihanghoaId) {
		
		this.loaihanghangId = loaihanghoaId;
	}

	
	public String getDIENGIAI() {
		
		return this.DIENGIAI;
	}

	
	public void setDIENGIAI(String DIENGIAI) {
		
		this.DIENGIAI = DIENGIAI;
	}

	
	public String getSOCHUNGTU() {
		
		return this.SOCHUNGTU;
	}

	
	public void setSOCHUNGTU(String SOCHUNGTU) {
		
		this.SOCHUNGTU = SOCHUNGTU;
	}

	
	public String getNGAYCHUNGTU() {
		
		return this.NGAYCHUNGTU;
	}

	
	public void setNGAYCHUNGTU(String NGAYCHUNGTU) {
		
		this.NGAYCHUNGTU = NGAYCHUNGTU;
	}

	
	public String getTRANGTHAI() {
		
		return this.TRANGTHAI;
	}

	
	public void setTRANGTHAI(String TRANGTHAI) {
		
		this.TRANGTHAI = TRANGTHAI;
	}

	
	public String getNGAYTAO() {
		
		return this.NGAYTAO;
	}

	
	public void setNGAYTAO(String NGAYTAO) {
		
		this.NGAYTAO = NGAYTAO;
	}

	
	public String getNGUOITAO() {
		
		return this.NGUOITAO;
	}

	
	public void setNGUOITAO(String NGUOITAO) {
		
		this.NGUOITAO  = NGUOITAO;
	}

	
	public String getNGAYSUA() {
		
		return this.NGAYSUA;
	}

	
	public void setNGAYSUA(String NGAYSUA) {
		
		this.NGAYSUA = NGAYSUA;
	}

	
	public String getNGUOISUA() {
		
		return this.NGUOISUA;
	}

	
	public void setNGUOISUA(String NGUOISUA) {
		
		this.NGUOISUA = NGUOISUA;
	}

	
	public String getTNKID() {
		
		return this.TNKID;
	}

	
	public void setTNKID(String TNKID) {
		
		this.TNKID = TNKID;
	}

	
	public String getPoTen() {
		
		return this.PoTen;
	}

	
	public void setPoTen(String PoTen) {
		
		this.PoTen = PoTen;
	}

	
	public String getHoantat() {
		
		return this.Hoantat;
	}

	
	public void setHoantat(String Hoantat) {
		
		this.Hoantat = Hoantat;
	}

	
	public String getthanhtoantutienvay() {
		
		return this.thanhtoantutienvay;
	}

	
	public void setthanhtoantutienvay(String thanhtoantutienvay) {
		
		this.thanhtoantutienvay = thanhtoantutienvay;
	}

	
	public String getvat() {
		
		return this.vat;
	}

	
	public void setvat(String vat) {
		
		this.vat = vat;
	}

	
	public String getphinganhang() {
		
		return this.phinganhang;
	}

	
	public void setphinganhang(String phinganhang) {
		
		this.phinganhang = phinganhang;
	}

	
	public String getNHCHUYEN() {
		
		return this.NHCHUYEN;
	}

	
	public void setNHCHUYEN(String NHCHUYEN) {
		
		this.NHCHUYEN = NHCHUYEN;
	}

	
	public String getNHNHAN() {
		
		return this.NHNHAN;
	}

	
	public void setNHNHAN(String NHNHAN) {
		
		this.NHNHAN = NHNHAN;
	}

	
	public String getTTID() {
		
		return this.TTID;
	}

	
	public void setTTID(String TTID) {
		
		this.TTID = TTID;
	}

	
	public String getSOTIENVND() {
		
		return this.SOTIENVND;
	}

	
	public void setSOTIENVND(String SOTIENVND) {
		
		this.SOTIENVND = SOTIENVND;
	}

	
	public String getSOTIENNT() {
		
		return this.SOTIENNT;
	}

	
	public void setSOTIENNT(String SOTIENNT) {
		
		this.SOTIENNT = SOTIENNT;
	}

	
	public String gettendoituong() {
		
		return this.tendoituong;
	}

	
	public void settendoituong(String tendoituong) {
		
		this.tendoituong = tendoituong;
	}


	public String getsotokhai() {
		
		return this.sotokhai;
	}


	public void setsotokhai(String sotokhai) {
		
		this.sotokhai = sotokhai;
	}


	public String getloaichungtu() {
		
		return this.loaichungtu;
	}


	public void setloaichungtu(String loaichungtu) {
		
		this.loaichungtu = loaichungtu;
	}

	
	public String getKHChuyenNo() {
		
		return this.khchuyenno;
	}

	
	public void setKHChuyenNo(String khchuyenno) {
		
		this.khchuyenno = khchuyenno;
	}

	
	public String getKHNhanNo() {
		
		return this.khnhanno;
	}

	
	public void setKHNhanNo(String khnhanno) {
		this.khnhanno = khnhanno;
		
	}

	
	public String getNoibo() {
		
		return this.noibo;
	}

	
	public void setNoibo(String noibo) {
		this.noibo = noibo;
		
	}

	
	public String getNOTE() {
		
		return this.NOTE;
	}

	
	public void setNOTE(String NOTE) {
		
		this.NOTE = NOTE;
	}

	
	public String getNgaymua() {
		
		return this.Ngaymua;
	}

	
	public void setNgaymua(String Ngaymua) {
		
		this.Ngaymua = Ngaymua;
	}

	
	public String getDuyet() {
		
		return this.Duyet;
	}

	
	public void setDuyet(String Duyet) {
		
		this.Duyet = Duyet;
	}

	
	public String getDachot() {
		
		return this.Dachot;
	}

	
	public void setDachot(String Dachot) {
		
		this.Dachot = Dachot;
	}

	
	public String getTongtienAvat() {
		
		return this.TongtienAvat;
	}

	
	public void setTongtienAvat(String TongtienAvat) {
		
		this.TongtienAvat = TongtienAvat;
		
	}

	
	public String getTongluong() {
		
		return this.Tongluong;
	}

	
	public void setTongluong(String Tongluong) {
		
		this.Tongluong = Tongluong;
	}

	
	public String getSohoadon2() {
		
		return this.sohoadon2;
	}

	
	public void setSohoadon2(String sohoadon2) {
		
		this.sohoadon2 = sohoadon2;
	}

	public String getIsKTTDuyet() 
	{
		return this.isKTTDuyet;
	}

	public void setIsKTTDuyet(String IsKTTDuyet) 
	{
		this.isKTTDuyet = IsKTTDuyet;
	}

	
	public String gethtxuathoadon() {
	
		return this.htxuathoadon;
	}

	
	public void sethtxuathoadon(String htxuathoadon) {
	
		this.htxuathoadon = htxuathoadon;
	}

	public String getIsTBPDuyet() 
	{
		return this.isTBPDuyet ;
	}

	public void setIsTBPDuyet(String isTBPDuyet)
	{
		this.isTBPDuyet = isTBPDuyet;
		
	}

	public String getIsChotGanMCP() 
	{
		return this.isChotGanMCP ;
	}

	public void setIsChotGanMCP(String isChotGanMCP) 
	{
		this.isChotGanMCP = isChotGanMCP;
		
	}

	
	public String getdondathangId() {
		
		return this.dondathang;
	}

	
	public void setdondathangId(String dondathangId) {
		
		this.dondathang = dondathangId;
	}

	
	public String getIsHuyChungTu() {
		
		return this.isHuyChungTu;
	}

	
	public void setIsHuyChungTu(String isHuyChungTu) {
		
		this.isHuyChungTu = isHuyChungTu;
	}

	public void setCanhBao(String canhBao) {
		this.canhBao = canhBao;
	}

	public String getCanhBao() {
		return canhBao;
	}
		
}
