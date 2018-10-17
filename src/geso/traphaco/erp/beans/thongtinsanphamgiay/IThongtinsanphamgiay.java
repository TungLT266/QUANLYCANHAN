package geso.traphaco.erp.beans.thongtinsanphamgiay;

import geso.traphaco.erp.beans.danhmucvattu.IDanhmucvattu_SP;

import java.sql.ResultSet;
import java.util.List;

public interface IThongtinsanphamgiay 
{
public void init();
	
	public String getCtyId();
	public void setCtyId(String ctyId);
	
	public String getCtyTen();
	public void setCtyTen(String ctyTen);
	
	public String getId();
	public void setId(String id);
	
	public String getUserId();
	public void setUserId(String userId);
	
	
	/********** Thong tin chung **************/
	public String getMasp();
	public void setMasp(String masp);
	
	public String getTennoibo();
	public void setTennoibo(String tennoibo);
	
	public String getTen();
	public void setTen(String ten);
	
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public String getDvdlId();
	public void setDvdlId(String dvdlId);
	
	public String getDvdl_qcIds();
	public void setDvdl_dcIds(String dvdlIds);
	
	public String getDvdlTen();
	public void setDvdlTen(String dvdlTen);
	
	public ResultSet getDvdl();
	public void setDvdl(ResultSet dvdl);
	
	public void setDvkdId(String dvkdId);
	public String getDvkdId();
	
	public String getDvkdTen();
	public void setDvkdTen(String dvkdTen);
	
	public ResultSet getDvkd();
	public void setDvkd(ResultSet dvkd);
	
	public String getNhId();
	public void setNhId(String nhId);
	
	public String getNhTen();
	public void setNhTen(String nhTen);
	
	public String getClId();
	public void setClId(String clId);
	
	public String getClTen();
	public void setClTen(String clTen);
	
	public void setKL(String kl);
	public String getKL();
	
	public void setDVKL(String dvkl);
	public String getDVKL();
	
	public void setDVTT(String dvtt);
	public String getDVTT();
	
	public void setTT(String tt);
	public String getTT();
	
	public void setGiablc(String giablc);
	public String getGiablc();
	
	public String getMessage();
	public void setMessage(String msg);
	
	public void setNh(ResultSet nh);
	public ResultSet getNh();
	
	public void setCl(ResultSet cl);
	public ResultSet getCl();
	
	public ResultSet getLoaiSpRs();
	public void setLoaiSpRs(ResultSet spRs);
	
	public String getLoaiSpId();
	public void setLoaiSpId(String loaisp);
	
	public void setLoaiSpMa(String loaispma);
	public String getLoaiSpMa();
	public String getLoaiSpMa(String loaispid);
	
	public void setType(String type);
	public String getType();
	
	public ResultSet getCongTyRs();
	public void setCongTyRs(ResultSet congty);
	
	public String getCongTy();
	public void setCongTy(String congty);
	
	public String getLoaiGiaTon();
	public void setLoaiGiaTon(String loaigiaton);
	
	public String getHanSuDung();
	public void setHanSuDung(String hansudung);
	
	public void setGiaMua(String giamua);
	public String getGiaMua();
	
	public String getHinhanh();
	public void setHinhanh(String hinhanh);
	
	public String getHinhcongboTP();
	public void setHinhcongboTP(String hinhcongboTP);
	
	public String getFilenamecbTP();
	public void setFilenamecbTP(String filenamecbTP);
	
	public String getHancongboTP();
	public void setHancongboTP(String hancongboTP);
	
	public String getFilehinhcb(int k);
	public void setFilehinhcb(String fhinh, int k);
	
	public String[] getFilehinhcb();
	public void setFilehinhcb(String[] fhinh);
	
	public String[] getKhovung();
	public void setKhovung(String[] khovung);
	
	public String[] getBantp();
	public void setBantp(String[] bantp);
	
	public void setHangbo(String value);
	public String getHangbo();
	
	/**************** End Thong Tin Chung ********************/
	
	
	
	/**************** Hang Ban ***********************/
	public void setNsp(ResultSet nsp);
	public ResultSet getNsp();
	
	public String getNspIds();
	public void setNspIds(String nspIds);
	
	//Quy cach
	public String[] getSl1();
	public void setSl1(String[] sl1);
	
	public String[] getDvdl1();
	public void setDvdl1(String[] dvdl1);
		
	public String[] getSl2();
	public void setSl2(String[] sl2);
	
	public String[] getDvdl2();
	public void setDvdl2(String[] dvdl2);
	
	//Bundle
	public ResultSet getSanphamRs();
	public void setSanphamRs(ResultSet spRs);
	public String getSpIds();
	public void setSpIds(String spIds);
	
	/***************** End Hang Ban ****************/
	
	
	public ResultSet createNccRs( String ma);
	
	public ResultSet createKhoRs();
	
	public void CreateRS();
	
	public boolean UpdateSp();
	
	public boolean CreateSp();
	
	public boolean checkThayDoiLoaiSanpham();
	
	public ResultSet createDvdlRS() throws java.sql.SQLException;
	
	public boolean CheckDVDL();
			
	public void init2();
	
	public void DBClose();
	
	
	public String[] getNhacungcap();
	public void setNhacungcap(String[] nhacungcap);
	
	public String[] getHinhcongbo();
	public void setHinhcongbo(String[] hinhcongbo);
	
	public String[] getFilenamecb();
	public void setFilenamecb(String[] filenamecb);
	
	public String[] getHancongbo();
	public void setHancongbo(String[] hancongbo);

	public String[] getLuongdattoithieu();
	public void setLuongdattoithieu(String[] luongdattoithieu);
	
	public String[] getThoihangiaohang();
	public void setThoihangiaohang(String[] thoihangiaohang);
	
	/*public String getLuongDatToiThieu();
	public void setLuongDatToiThieu(String value);
	
	public String getThoiGianChoGiaoHang();
	public void setThoiGianChoGiaoHang(String value);*/
	
	public String getTonKhoAnToan();
	public void setTonKhoAnToan(String value);
	

	public ResultSet getPacksizeRs();
	public void setPacksizeRs(ResultSet packsizeRs);
	public String getPacksizeId();
	public void setPacksizeId(String packsizeId);
	
	public void setCPNC(String cpnc);
	public String getCPNC();
	public void setCPVC(String cpvc);
	public String getCPVC();
	
	public void setPathHinhanhSp(String filepath);
	public String getPathHinhanhSp();
	
	public void setNameHinhanhSp(String filename);
	public String getNameHinhanhSp();
	
	/************ Kiem dinh chat luong *************/
	
	public String getKiemTraDinhLuong();
	public void setKiemTraDinhLuong(String value);
	
	public void setTieuchikiemdinhDinhluongList(List<ITieuchikiemdinh> list);
	public List<ITieuchikiemdinh> getTieuchikiemdinhDinhluongList();
	
	public String getKiemTraDinhTinh();
	public void setKiemTraDinhTinh(String value);
	
	public void setTieuchikiemdinhDinhtinhList(List<ITieuchikiemdinh> list);
	public List<ITieuchikiemdinh> getTieuchikiemdinhDinhtinhList();
	
	public void createTieuchikiemdinh();
	
	/*********** End Kiem dinh chat luong *************/
	
	public boolean checkPhongKeToan();
	
	// NEWTOYO CU
	
	public void init3();
		
	/********** Thong tin chung **************/
	
	public String getTen1();
	public void setTen1(String ten1);

	public String getTen2();
	public void setTen2(String ten2);
	
	public String getnguongoc();
	public void setnguongoc(String nguongoc);
 
	public void setDaiDay(String daiday);
	public String getDaiDay();
	
	public void setDvdlDaiDay(String dvdl_daiday);
	public String getDvdlDaiDay();
	
	public void setMaMetro(String maMetro);
	public String getMaMetro();
	
	/**************** End Thong Tin Chung ********************/
	
	
	
	/**************** Hang Ban ***********************/
	//Bundle
	public List<IBundle> getSanphamList();
	public void setSanphamList(List<IBundle> spList);
	

	
	/***************** End Hang Ban ****************/
	
	
	
	/************** San xuat OR Nguyen lieu **************/
	
	//neu la sanpham san xuat
	public void setSongaytonkhoantoan(String songayantoan);
	public String getSongaytonkhoantoan();
	
	public void setSoluongsanxuat(String soluongsanxuat);
	public String getSoluongsanxuat();
	
	public void setNhamayId(String nhamayId);
	public String getNhamayId();
	public void setNhamayRs(ResultSet nhamayRs);
	public ResultSet getNhamayRs();
	
	
	//neu la nguyen lieu
	public void setLamtronnguyenlieu(String lamtronguyenlieu);
	public String getLamtronnguyenlieu();
	
	public void setChogiaohangnguyenlieu(String chogiaonguyenlieu);
	public String getChogiaohangnguyenlieu();
	
	public void setLuongdattoithieu(String luongdattoithieu);
	
	/************* San xuat OR Nguyen lieu ***********/
	
	
	
	/************ BOM *************/
	
	public void setSoluongbomchuan(String soluongbomchuan);
	public String getSoluongbomchuan();
	
	public void setListDanhMuc(List<IDanhmucvattu_SP> list);
	public List<IDanhmucvattu_SP> getListDanhMuc();
	
	/*********** End BOM *************/
	
	
	/************ Kiem dinh chat luong *************/
	
	public void setKiemtradinhluong(String kiemtradinhluong);
	public String getKiemtradinhluong();
	
	
	public void setKiemtradinhtinh(String kiemdinhchatluong);
	public String getKiemtradinhtinh();
	
	
	/*********** End Kiem dinh chat luong *************/
	
	/* *******Quy cách sản phẩm giấy *****  */
	
	public void SetQuyCachGiayList(List<IQuyCachSanPhamList> listquycach);
	public List<IQuyCachSanPhamList> GetQuyCachGiayList();
	
	
	public void SetThongtinNCClist(List<IThongtinNCC> list);
	public List<IThongtinNCC> GetThongtinNCClist();
	
	
	public ResultSet getRsMau();
	
	public void SetRsMaKeToan(ResultSet RsMaKT);
	public ResultSet getRsMaKeToan();
	public void setMaKeToan(String maketoan);
	public String getMaKeToan();
	
	public ResultSet getRsNguonGoc();
	public ResultSet getRsMauIn();
	public ResultSet getRsDoNen();
	
	
	public void setNhomNLId(String nnlId);
	public String getNhomNLId();
	public void setNhomNguyenLieuRs(ResultSet nhomNLRs);
	public ResultSet getNhomNguyenLieuRs();
	
	public void setSoLuongBanTp(String soluong);
	public String getSoLuongBanTp();
	public void setSoLuongTp(String soluong);
	public String getSoLuongTp();
	public void SetQuyCachGiay  (IQuyCachSanPhamList  qc);
	public IQuyCachSanPhamList GetQuyCachGiay ();
	public boolean deleteQc(String idquycach);
	public boolean kichhoatquycach(String idquycach);
	public boolean Ngunghoatdong_Qc(String idquycach);
	
	public String getBTPId();
	public void setBTPId(String BTPId);
	
	public String getPhephamId();
	public void setPhephamId(String PhephamId);
	
	public void setBTPRs(ResultSet BTPRs);
	public ResultSet  getBTPRs();
	public void setPhePhamRs(ResultSet PhePhamRs);
	public ResultSet  getPhePhamRs();
	
	public String getgetBatbuockiemdinh();
	public void setBatbuockiemdinh(String  Batbuockiemdinh);
	
	public String getcheck_VoThoiHan();
	public void setcheck_VoThoiHan(String  check_vothoihan);
	
	public String getSongayhancanhbao();
	public void setSongayhancanhbao(String  songayhancanhbao);

	List<IHoaChat_SanPham> getHoaChatKiemDinhList();

	void setHoaChatKiemDinhList(List<IHoaChat_SanPham> hoaChatKiemDinhList);


	public List<IMayMoc_SanPham> getMayMocKiemDinhList();
	public void setMayMocKiemDinhList(List<IMayMoc_SanPham> mayMocKiemDinhList);

	String getThueSuat();

	void setThueSuat(String thueSuat);
	
	public String getDangBaoChe();
	public void setDangBaoChe(String dangBaoChe);
	public String getTckt();
	public void setTckt(String tckt) ;
	public String getLoaiHangHoa() ;
	public void setLoaiHangHoa(String loaiHangHoa) ;
	
	public String getKiemtraoe();
	public void setKiemtraoe(String kiemtraoe);
	
	public ResultSet getDangBaoCheRs();
	public void setDangBaoCheRs(ResultSet dangBaoCheRs) ;
	
	public String getNgayhoanthanh();
	public void setNgayhoanthanh(String Ngayhoanthanh);
	
	
	public String getTenthuongmai();

	public void setTenthuongmai(String tenthuongmai);
	
	
	public String getIschietkhau();

	public void setIschietkhau(String ischietkhau) ;

	public String getIs_khongthue();
	public void setIs_khongthue(String is_khongthue);
	public String getThietBiCan();
	public void setThietBiCan(String thietBiCan);
	public String getThietBiCanKhac();
	public void setThietBiCanKhac(String thietBiCanKhac);
	public ResultSet getTscdRs();
	public void setTscdRs(ResultSet tscdRs);
	
	public String getYcnlsx();
	public void setYcnlsx(String ycnlsx);

	public String getMotaSp();
	public void setMotaSp(String motaSp);

	public String getYeucaudonggoi();
	public void setYeucaudonggoi(String yeucaudonggoi);

	public String getDactinhkythuat();
	public void setDactinhkythuat(String dactinhkythuat);

	public String getCongthuchoahoc();
	public void setCongthuchoahoc(String congthuchoahoc);

	public String getNhomluutru();
	public void setNhomluutru(String nhomluutru);

	public String getMucdonguyhiem();
	public void setMucdonguyhiem(String mucdonguyhiem);

	public String getKhuvucbaoquan();
	public void setKhuvucbaoquan(String khuvucbaoquan);
	
	public String getKhongqlsl();
	public void setKhongqlsl(String khongqlsl);
	
	public String getThuocloaisp();
	public void setThuocloaisp(String thuocloaisp);
}
