package geso.traphaco.center.util;

import java.sql.ResultSet;
import java.util.List;

public interface IThongTinHienThi
{
	//THÔNG TIN CHUNG
	public String getId();
	public void setId(String id);
	public String getSoChungTu() ;

	public void setSoChungTu(String soChungTu);
	public String getNgaychungtu();
	public void setNgaychungtu(String ngaychungtu);
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	public String getSohoadon();
	public void setSohoadon(String sohoadon);
	public String getHttt();
	public void setHttt(String httt);
	public String getNoidungtt();
	public void setNoidungtt(String noidungtt);
	public String getTiente();
	public void setTiente(String tiente);
	
	public String getKhachhang();
	public void setKhachhang(String khachhang);
	public String getTongtien();
	public void setTongtien(String tongtien);
	
	public String getNgaytao();
	public void setNgaytao(String ngaytao);
	public String getNguoitao();
	public void setNguoitao(String nguoitao);
	
	public String getNgaysua();
	public void setNgaysua(String ngaysua);
	public String getNguoisua();
	public void setNguoisua(String nguoisua);
	
	//HOA DON NCC
	public String getPREFIX();
	public void setPREFIX(String PREFIX);
	
	public String getNgayghinhan();
	public void setNgayghinhan(String ngayghinhan);
	
	public String getTenNCC();
	public void setTenNCC(String tenncc);
	
	public String getDahoantat();
	public void setDahoantat(String dahoantat);
	
	public String getDathanhtoan();
	public void setDathanhtoan(String dathanhtoan);
	
	public String getNCCId();
	public void setNCCId(String nccId);
	
	public String getDaNhanHang();
	public void setDaNhanHang(String danhanhang);
	
	public String getLoaiHd();
	public void setLoaiHd(String loaihd);
	
	//
	
	//HOA DON BAN HANG
	public String getTenKH();
	public void setTenKH(String tenkh);
	public String getKenhBanHang();
	public void setKenhBanHang(String kenhbanhang);
	
	
	public List<IDinhKhoanKeToan> getLayDinhkhoanKT();
	public void setLayDinhkhoanKT(List<IDinhKhoanKeToan> layDinhkhoanKT);
	
	
	
	//PHIEU XUAT KHO
	
	public String getNoidungxuat();
	public void setNoidungxuat(String noidungxuat);	
	
	public String getSoxuatkho();
	public void setSoxuatkho(String soxuatkho);
	
	public String getNgayxuat();
	public void setNgayxuat(String ngayxuat);
	
	public String getSochungtu();
	public void setSochungtu(String sochungtu);
	
	public String getHoadonId();
	public void setHoadonId(String hoadonId);
	
	public String getndxTen();
	public void setndxTen(String ndxTen);
	
	public String getdondathangId();
	public void setdondathangId(String dondathangId);
	
	//GIAI NGAN	
	
	public String getNgaynhan();
	public void setNgaynhan(String ngaynhan);	
	
	public String getSohopdong();
	public void setSohopdong(String sohopdong);	
	
	public String getTKVAY();
	public void setTKVAY(String TKVAY);	
	
	public String getLaisuat();
	public void setLaisuat(String Laisuat);
	
	public String getHinhthuc();
	public void setHinhthuc(String Hinhthuc);
	
	//THANH TOAN NO VAY
	
	public String getTienlai();
	public void setTienlai(String Tienlai);
	
	public String getTiengoc();
	public void setTiengoc(String Tiengoc);
	
	public String getTienphat();
	public void setTienphat(String Tienphat);
	
	public String getPhikhac();
	public void setPhikhac(String Phikhac);
	
	public String getGhichu();
	public void setGhichu(String Ghichu);
	
	//NHAN HANG
	public String getdvthTen();
	public void setdvthTen(String dvthTen);
	
	public String getPoId();
	public void setPoId(String PoId);
	
	public String getDaRaHd();
	public void setDaRaHd(String DaRaHd);
		
	public String getloaihanghoaId();
	public void setloaihanghoaId(String loaihanghoaId);
	
	//THUE NHAP KHAU
	
	public String getDIENGIAI();
	public void setDIENGIAI(String DIENGIAI);
	
	public String getSOCHUNGTU();
	public void setSOCHUNGTU(String SOCHUNGTU);
	
	public String getNGAYCHUNGTU();
	public void setNGAYCHUNGTU(String NGAYCHUNGTU);
	
	public String getTRANGTHAI();
	public void setTRANGTHAI(String TRANGTHAI);
	
	public String getNGAYTAO();
	public void setNGAYTAO(String NGAYTAO);
	
	public String getNGUOITAO();
	public void setNGUOITAO(String NGUOITAO);
	
	public String getNGAYSUA();
	public void setNGAYSUA(String NGAYSUA);
	
	public String getNGUOISUA();
	public void setNGUOISUA(String NGUOISUA);
	
	public String getTNKID();
	public void setTNKID(String TNKID);
	
	//HOA DON PHE LIEU
	
	public String getPoTen();
	public void setPoTen(String PoTen);
	
	//HOA DON HANG TRA VE
	
	public String getHoantat();
	public void setHoantat(String Hoantat);
		
	//PHIEU CHI
	
	public String getIsKTTDuyet();
	public void setIsKTTDuyet(String IsKTTDuyet);
	
	public String getthanhtoantutienvay();
	public void setthanhtoantutienvay(String thanhtoantutienvay);
	
	public String getvat();
	public void setvat(String vat);
	
	public String getphinganhang();
	public void setphinganhang(String phinganhang);
	
	public String getsoct();
	public void setsoct(String soct);
	
	//DIEU CHUYEN TIEN
	public String getNHCHUYEN();
	public void setNHCHUYEN(String NHCHUYEN);
	
	public String getTTID();
	public void setTTID(String TTID);
	
	public String getNHNHAN();
	public void setNHNHAN(String NHNHAN);
	
	public String getSOTIENVND();
	public void setSOTIENVND(String SOTIENVND);
	
	public String getSOTIENNT();
	public void setSOTIENNT(String SOTIENNT);
	
	//XOA NO CC
	
	public String gettendoituong();
	public void settendoituong(String tendoituong);	
	
	//CHI PHI NHAP KHAU
	
	public String getsotokhai();
	public void setsotokhai(String sotokhai);	
	
	//HỦY PHIẾU CHI
	public String getloaichungtu();
	public void setloaichungtu(String loaichungtu);	
	
	//BÙ TRỪ CÔNG NỢ
	public String getKHChuyenNo();
	public void setKHChuyenNo(String khchuyenno);	
		
	public String getKHNhanNo();
	public void setKHNhanNo(String khnhanno);	
	
	//ĐỀ NGHỊ THANH TOÁN	
	public String getNoibo();
	public void setNoibo(String noibo);	
	
	public String getNOTE();
	public void setNOTE(String NOTE);
	
	public String getNgaymua();
	public void setNgaymua(String Ngaymua);
	
	public String getDuyet();
	public void setDuyet(String Duyet);
	
	public String getDachot();
	public void setDachot(String Dachot);
	
	public String getTongtienAvat();
	public void setTongtienAvat(String TongtienAvat);
	
	public String getTongluong();
	public void setTongluong(String Tongluong);
		
	public String getSohoadon2();
	public void setSohoadon2(String sohoadon2);
	
	public String getIsChotGanMCP();
	public void setIsChotGanMCP(String isChotGanMCP);
	
	public String getTooltip() ;
	public void setTooltip(String tooltip) ;

	//HÓA ĐƠN TÀI CHÍNH
	public String gethtxuathoadon();
	public void sethtxuathoadon(String htxuathoadon);
	
	public String getIsTBPDuyet();
	public void setIsTBPDuyet(String isTBPDuyet);
	
	// DU TOAN VAT TU
	public ResultSet getNhaCCList() ;
	public void setNhaCCList(ResultSet nhaNCCList) ;
	
	// XÓA NỢ KH

	public String getIsHuyChungTu();
	public void setIsHuyChungTu(String isHuyChungTu);
	
	// ĐÃ THANH TOÁN

	public String getIsThanhToan();
	public void setIsThanhToan(String isThanhToan);
	
	//HỦY CHỨNG TỪ
	public String getSoChungTuGoc();
	public void setSoChungTuGoc(String sochungtugoc);
	
	public String getmachungtu();
	public void setmachungtu(String machungtu);
	
	public String getloaihanghoa();
	public void setloaihanghoa(String loaihanghoa); 

	public String getTIENTE() ;
	public void setTIENTE(String tIENTE) ;
	public void setMasanpham(String string);
	public String getMasanpham();
	public void setTensanpham(String string);
	public String getTensanpham();
	
	
	
}
