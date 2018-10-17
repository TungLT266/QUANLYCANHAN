package geso.traphaco.erp.beans.lenhsanxuatgiay;

import geso.traphaco.erp.beans.danhmucvattu.IDanhmucvattu;
import geso.traphaco.erp.beans.danhmucvattu.IDanhmucvattu_SP;
import geso.traphaco.erp.beans.lapkehoach.IErpBom;
import geso.traphaco.erp.beans.lapkehoach.IErpDinhmuc;

import java.sql.ResultSet;
import java.util.Hashtable;
import java.util.List;

public interface IErpLenhsanxuat 
{
	public String getCongtyId();
	public void setCongtyId(String congtyId);
	
	public String getUserId();
	public void setUserId(String userId);
	
	
	public String getIsLsxGiacong();
	public void setIsLsxGiacong(String islsxGc);
	
	public String getSome();
	public void setSome(String Some);
	
	public String getNgayYCThenNL();
	public void setNgayYCThenNL(String NgayYCThenNL);
	
	public String getghichu();
	public void setghichu(String ghichu);
	
	public String getIsLsxCongNghe();
	public void setIsLsxCongNghe(String IsLsxCongNghe);
	
	public String getId();
	public void setId(String Id);

	public String getNgaytieuhao();
	public void setNgaytieuhao(String ngaytieuhao);
	
	public String getNgaytao();
	public void setNgaytao(String ngaytao);
	public String getNgaydukien();
	public void setNgaydukien(String ngaydk);
	
	public String getSpma();
	public void setSpma(String spma);
	
	public String getSpId();
	public void setSpId(String spId);
	public ResultSet getSpRs();
	public void setSpRs(ResultSet spRs);
	
	public String getKbsxId();
	public void setKbsxId(String kbsxId);
	public ResultSet getKbsxRs();
	public void setKbsxRs(ResultSet kbsxRs);
 
	public String getSoluong();
	public void setSoluong(String soluong);
	
	public String getDvtBOM();
	public void setDvtBOM(String dvtBOM);
	
	public String getPlaintOrder();
	public void setPlaintOrder(String PO);
	
	public String getKhottId();
	public void setKhottId(String khott);
	public ResultSet getKhoTTRs();
	public void setKhoTTRs(ResultSet khottRs);
	
	public String getNhamayId();
	public void setNhamayId(String nhamayId);
	public ResultSet getNhamayRs();
	public void setNhamayRs(ResultSet nhamayRs);
	
	public ResultSet getChitietNLRs();
	public void setChitietNLRs(ResultSet nlRs);
	public String getTuan(String ngay);
	
	public String getCongDoanCurrent();
	public void setCongDoanCurrent(String congdoanid);
	
	public String getTenCongDoanCurrent();
	public void setTenCongDoanCurrent(String congdoanten);
	
	public String getSoluongchuan();
	public void setSoluongchuan(String slgchuan);
	public String getChophepTT();
	public void setChophepTT(String chophepTT);
	
	public void setListDanhMuc(List<IDanhmucvattu_SP> list);
	public List<IDanhmucvattu_SP> getListDanhMuc();
	
	public void setListDanhMucThieu(List<IDanhmucvattu_SP> list);
	public List<IDanhmucvattu_SP> getListDanhMucThieu();
	
	public String getMsg();
	public void setMsg(String msg);
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public boolean getIsChangeBom();
	public void setIsChangeBom(boolean  IsChangeBom);
	
	
	public String getViewBom();
	public void setViewBom(String viewBom);
	
	public boolean createLsx();
	public boolean updateLsx();
	public boolean tieuhaoLsx();
	public boolean checkTieuHaoLsx();
	
	public void ChangeSpOrKichBan();

	public String CheckBomExpire();
	
	public void createRs();
	public void Kiemtranguyenlieu();
	
	public void init_tieuhao_lsx();
	public void init();
	public void initDisplay();
	
	public void DBclose();
	public void SetListCongDoan(List<ILenhSXCongDoan> lenhsxcd);
	public List<ILenhSXCongDoan> getListCongDoan();
	
	public void SetListSanPhamSx(List<ISanPhamSanXuat> listspsx);
	public List<ISanPhamSanXuat> getListSanPhamSx();
	public void sethtpsp(Hashtable<String,String> htb);
	public ResultSet getRsBomIdsp(String idsp,String trongluong);
	public ResultSet getRsSpThayThe(IDanhmucvattu_SP spvt);
	public boolean HoantatCongDoan(String ischeckdungsai);
	public void KhoiTaoListCongDoan();
	public void SetTrangthaixemtieuhao(String tt);
	public String GetTrangthaixemtieuhao();
	
	public void SetRsCongDoan(ResultSet rscd);
	public ResultSet getRsCongDoan();
	
	public void setListVattuThem(List<IDanhmucvattu_SP> list);
	public List<IDanhmucvattu_SP> getListVattuThem();
	// Những bán thành phẩm được sản xuất từ công đoạn trước,những sản phẩm này sẽ có loại=3 và được tiêu hao 
	public void setListBanThanhPham(List<IDanhmucvattu_SP> list);
	public List<IDanhmucvattu_SP> getListBanThanhPham();
	
	
	public void setTieuHaoId(String id);
	public String getTieuHaoId();
	public boolean HuyBookedNL();
	
	public boolean getVuotDungSai();
	
	public void setListVattuDeNghi(List<IDanhmucvattu_SP> list);
	public List<IDanhmucvattu_SP> getListVattuDeNghi();
	
	public String getDvkdId();
	public void setDvkdId(String DvkdId);
	
	
	public ResultSet getRsDvkd();
	public void setRsDvkd(ResultSet rs);
	
	public ResultSet getRsNhapKho();
	public void setRsNhapKho(ResultSet Rs);
	
	public String getNhapKhoId();
	public void setNhapkhoId(String nhapkhoId);
	
	public ResultSet getRsHangDaNhan_SPMoi();
	public ResultSet getRsDaTieuHao_SPMoi();
	public ResultSet getRsHangDaKD_SpMoi();
	
	
	public ResultSet getRsYeuCauChuyenKho();
	
	public String getCoKiemDinhChatLuong();
	public void CreateRs_tieuhao();
	public boolean CheckYeuCauNL();
	
	public String getsoPoKH();
	public void setsoPoKH(String soPoKH);
	
	public ResultSet getBom();
	public String getBomId();
	public void setBomId(String bomId);
	
	public List<IErpDinhmuc> getDinhmucList();
	public void setDinhmucList(List<IErpDinhmuc> value);
	
	public ResultSet getLohoiRs();
	public void setLohoiId(String lohoiId);
	public ResultSet getRskhoTp();
	public ResultSet getRsKhoLayNL();
	
	public String getLohoiId();
	public String getLoaisanphamTH() ;
	public void setLoaisanphamTH(String loaisanphamTH);
	public ResultSet getLoaisanphamRs();

	public void setLoaisanphamRs(ResultSet loaitaisanRs) ;
	public void createLoaisanphamRs();
	
	
	public List<IErpBom> getBomList();
	public void setBomList(List<IErpBom> list);
	
	public String getSohoso() ;
	public void setSohoso(String Sohoso);
	public String getDmvt() ;
	public void setDmvt(String Dmvt);
	public String getNSX() ;
	public void setNSX(String NSX);
	
	public String getHD() ;
	public void setHD(String HD);
	
	public String[] getMangCheck() ;
	public void MangCheck(String[] HD);
	
	public String[] getMangCheck_Value() ;
	public void MangCheck_Value(String[] HD);
	
	public String  getStrCheck_Value() ;
	public void setStrCheck_Value(String  str);
	
	
	
	
	public String[] getMangCheck_TKL() ;
	public void MangCheck_TKL(String[] HD);
	
	public String[] getMangCheck_ValueTKL() ;
	public void setMangCheck_ValueTKL(String[] HD);
	
	public String[] getMangView_CheckTKL() ;
	public void setMangView_CheckTKL(String[] HD);
	
	public String  getStrCheck_TKL() ;
	public void setStrCheck_ValueTKL(String  str);
 	
	public String getNDHL() ;
	public void setNDHL(String NDHL);

	public String getGHICHU_TIENDO() ;
	public void setGHICHU_TIENDO(String GHICHU_TIENDO);
	public String getYKIENKHAC() ;
	public void setYKIENKHAC(String YKIENKHAC);
	
	public String getPHACHE() ;
	public void setPHACHE(String PHACHE);
	public String getHIEUSUAT_SX() ;	
	public void setHIEUSUAT_SX(String HIEUSUAT_SX);
	public String getHIEUSUAT_DG() ;
	public void setHIEUSUAT_DG(String HIEUSUAT_DG);
	
	public String getTOANCHANG() ;
	public void setTOANCHANG(String TOANCHANG);
/*	String[]  SOTT=new String[4] ;
	String[] NOIDUNG =new String[4] ;
	String[] SOPKN=new String[4] ;
	
	String[] NGAY=new String[4] ;
	String[] KETQUA=new String[4] ;
	String[] GHICHU=new String[4] */;
	
	public String[] getSOTT() ;
	public void setSOTT(String[] SOTT);
	public String[] getNOIDUNG() ;
	public void setNOIDUNG(String[] NOIDUNG);
	public String[] getSOPKN() ;
	public void setSOPKN(String[] SOPKN);
	public String[] getNGAY() ;
	public void setNGAY(String[] NGAY);
	public String[] getKETQUA() ;
	public void setKETQUA(String[] KETQUA);
	public String[] getGHICHU() ;
	public void setGHICHU(String[] GHICHU);
	public String getQuatrinh1() ;

	public void setQuatrinh1(String quatrinh1) ;

	public String getQuatrinh2() ;

	public void setQuatrinh2(String quatrinh2) ;

	public String getDang1() ;

	public void setDang1(String dang1) ;

	public String getDang2() ;

	public void setDang2(String dang2) ;

	public String getDang3() ;

	public void setDang3(String dang3);

	public String getSKN1() ;

	public void setSKN1(String sKN1) ;

	public String getSKN2() ;

	public void setSKN2(String sKN2) ;
	public String getSKN3() ;

	public void setSKN3(String sKN3) ;
	public boolean yeucauNguyenlieuthem();
	
	
	public String getNGAYBATDAUSX() ;

	public void setNGAYBATDAUSX(String nGAYBATDAUSX) ;

	public String getNGAYHOANTHANH() ;

	public void setNGAYHOANTHANH(String nGAYHOANTHANH) ;

	public String getSONGAYCHAMSOVOILENH() ;

	public void setSONGAYCHAMSOVOILENH(String sONGAYCHAMSOVOILENH);
	
	public String getSOLUONGNHAPKHO() ;

	public void setSOLUONGNHAPKHO(String sOLUONGNHAPKHO) ;

	public String getSOLUONGLAYMAU() ;

	public void setSOLUONGLAYMAU(String sOLUONGLAYMAU) ;
	
	public String getQTSX() ;

	public void setQTSX(String qTSX) ;

	public String getTCKT() ;

	public void setTCKT(String tCKT) ;

	public String getHOSOLOGOC() ;

	public void setHOSOLOGOC(String hOSOLOGOC) ;

	public String getQUYETDINHLUUHANH() ;

	public void setQUYETDINHLUUHANH(String qUYETDINHLUUHANH) ;
	public String getSoLenhSanXuat() ;

	public void setSoLenhSanXuat(String soLenhSanXuat) ;

	public String getSDK() ;

	public void setSDK(String sDK) ;

	public String getCanCuLamLenh() ;

	public void setCanCuLamLenh(String canCuLamLenh) ;
	
	public String getNppId();
	public void setNppId(String nppId);
	public String getIsGiacong();
	public boolean LuuCongDoanNhapXuat();

	public ResultSet getKhoanmucchiphiRs();

	public void setKhoanmucchiphiRs(ResultSet khoanmucchiphiRs) ;

	public String getKhoanmucchiphi();

	public void setKhoanmucchiphi(String khoanmucchiphi) ;
	public String taoLsxBtp();
	public boolean is_ChuaTao_LSX_BTP();
	public boolean isLsxBTP();
	
	public String getSoLSX();
	public void setSoLSX(String soLSX);
	
	public String getKhoSXId();
	public void setKhoSXId(String KhoSXId);
	public ResultSet getRsKhoSX();
	public void setRsKhoSX(ResultSet  RsKhoSX );
	
	public ResultSet getRskho(ILenhSXCongDoan lsxcd);
	
	public String getSolenhsanxuat();

	public void setSolenhsanxuat(String solenhsanxuat) ;
	public void ReLoad_CongDoan(boolean is_reload);
	public boolean update_CongDoan();
	public boolean yeucauNguyenlieu_TheoCongdoan();
	 
	public String getSolsxNew();
	public void setSolsxNew(String solsxNew);
	public void Init_congdoan();
	
	
	
}
