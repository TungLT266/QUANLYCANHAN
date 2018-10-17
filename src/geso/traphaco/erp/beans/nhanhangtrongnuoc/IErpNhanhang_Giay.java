package geso.traphaco.erp.beans.nhanhangtrongnuoc;

import geso.traphaco.erp.beans.donmuahangtrongnuoc.IDonvi;
import geso.traphaco.erp.beans.nhanhangtrongnuoc.ISanpham;
import geso.traphaco.erp.beans.nhanhangtrongnuoc.imp.KhuVucKho;

import java.sql.ResultSet;
import java.util.List;

public interface IErpNhanhang_Giay 
{
	public String getCongtyId();
	public void setCongtyId(String congtyId);
	
	public String getUserId();
	public void setUserId(String userId);
	
	public String getId();
	public void setId(String id);
	
	public String getNgaynhanhang();
	public void setNgaynhanhang(String ngaynhanhang);
	public String getNgaychot();
	public void setNgaychot(String ngaychot);
	
	public String getSohoadon();
	public void setSohoadon(String sohoadon);
	
	public String getDvthId();
	public void setDvthId(String dvthid);
	public ResultSet getDvthList();
	public void setDvthList(ResultSet dvthlist);

	public String getDonmuahangId();
	public void setDonmuahangId(String dmhid);
	public ResultSet getDmhList();
	public void setDmhList(ResultSet dmhlist);
	
	public String getMahangmuaId();
	public void setMahangmuaId(String mhmId);
	public ResultSet getMahangmuaList();
	public void setMahangmuaList(ResultSet mhmlist);
	
	public String getNdnId();
	public void setNdnId(String mhmId);
	public ResultSet getNdnList();
	public void setNdnList(ResultSet ndnlist);
	
	public void setLdnId(String ndnId);
	public String getLdnId();
	public ResultSet getLdnList();
	public void setLdnList(ResultSet ldnList);
	
	public void setNccId(String ndnId);
	public String getNccId();
	public ResultSet getNccList();
	public void setNccList(ResultSet nccList);
	
	public ResultSet getrskhoNhan();
	public void setrskhoNhan(ResultSet rskhoNhan);
	
	public String getKhoNhanId();
	public void setKhoNhanId(String KhoNhanId);
	
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	public String getDiengiai();
	public void setDiengiai(String diengiai);
	
	public String getTongSoluongPO();
	public void setTongSoluongPO(String tongslgPO);
	public String getTongSoluongDN();
	public void setTongSoluongDN(String tongslgDN);
	
	public String getIsPONK();
	public void setIsPONK(String poNK);
	
	public void setHdNccId(String hdNccId);
	public String getHdNccId();
	public ResultSet getHdNccList();
	public void setHdNccList(ResultSet hdnccList);
	
	
	public int getNgayhethan();
	public void setNgayhethan(int ngayhethan);
	
	public List<ISanpham> getSpList();
	public void setSpList(List<ISanpham> spList);
	
	public String getMsg();
	public void setMsg(String msg);
	
	public String getLoaihanghoa();
	public void setLoaihanghoa(String loaihh);
	
	public void createRs();
	public void init();
	public void initPdf(String spId);
	public void setLoaispId(String loaispId);
	public String getLoaispId();
	public boolean createNhanHang();
	public boolean updateNhanHang();
	public void updateDonmuahang(String poId);
	
	public boolean suaSoHoaDon();
	
	public String getIsNCCNK();
	public void setIsNCCNK(String isNCCNK);
	
	public String getLydonhan(); 
	public void setLydo(String lydo);
	
	public String getPhongBan();
	public void setPhongBan(String phongban);
	
	public void close();
	
	public boolean getIsKhoNhanQL_Khuvuc();
	public void setIsKhoNhanQL_Khuvuc(boolean bien);
	
	
	public String  getTigia();
	public void setTiia(String tigia);
	
	
	public String  getSopo_Id();
	public void setSopo_Id(String sopo_Id);
	
	public String  getmuahang_fk();
	public void setmuahang_fk(String muahang_fk);
	
	public boolean kt_nhanhang_theoHDNCC_bivuot(String hoadonncc, String ncc_fk);
	
	public void kt_capnhattrangthai_hoadonNCC(String hoadonncc, String ncc_fk, String nhanhang_ID);
	
	public Integer  getIs_saungayKS();
	public void setIs_saungayKS(Integer is_saungayKS);
	
	public String getGhichu();
	public void setGhichu(String ghichu);
	
	public String getLoaimh();
	public void setLoaimh(String loaimh);
	
	public String getIsTudong() ;
	public void setIsTudong(String isTudong) ;
	
	public String getKhachhangId() ;
	public void setKhachhangId(String khachhangId) ;
	
	public ResultSet getKhachhangRs() ;
	public void setKhachhangRs(ResultSet khachhangRs) ;
	
	public String getLoaikho() ;
	public void setLoaikho(String loaikho) ;
	
	public void init_convert(String hoadonnccId, String loaihd);
	
	public List<KhuVucKho> getListKhuVucKho();
	public void setListKhuVucKho(List<KhuVucKho> listKhuVucKho);
	public void createRs1();
	
	public String getKhoChoXuLy();
	public void setKhoChoXuLy(String khoChoXuLy);
	public ResultSet getRsKhoChoXuLy();
	public void setRsKhoChoXuLy(ResultSet rsKhoChoXuLy);
	
	public List<KhuVucKho> getListKhuVucKhoCXL();
	public void setListKhuVucKhoCXL(List<KhuVucKho> listKhuVucKhoCXL);
	
	public int getTilequydoi_dvdl();
	public void setTilequydoi_dvdl(int tilequydoi_dvdl);
	
	public List<IDonvi> getListDonvi();
	public void setListDonvi(List<IDonvi> listDonvi);
	
	public String getNgayhoadon();
	public void setNgayhoadon(String ngayhoadon);
}
