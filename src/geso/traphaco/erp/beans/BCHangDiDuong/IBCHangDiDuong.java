package geso.traphaco.erp.beans.BCHangDiDuong;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletOutputStream;

import com.itextpdf.text.Document;

public interface IBCHangDiDuong
{
	public void setuserId(String userId);
	public String getuserId();

	public void setuserTen(String userTen);
	public String getuserTen();

	public void setnppId(String nppId);
	public String getnppId();

	public void setnppTen(String nppTen);
	public String getnppTen();

	public void setkhId(String khId);
	public String getkhId();

	public void setkhTen(String khTen);
	public String getkhTen();
	
	public void setkenhId(String kenhId);
	public String getkenhId();

	public void setdvkdId(String dvkdId);
	public String getdvkdId();

	public void setnhanhangId(String nhanhangId);
	public String getnhanhangId();

	public void setchungloaiId(String chungloaiId);
	public String getchungloaiId();

	public void settungay(String tungay);
	public String gettungay();

	public void setdenngay(String denngay);
	public String getdenngay();

	public void setMsg(String msg);
	public String getMsg();

	public void setkenh(ResultSet kenh);
	public ResultSet getkenh();
	
	public void setNhomkenh(ResultSet nhomkenh);
	public ResultSet getNhomkenh();

	public void setdvkd(ResultSet dvkd);
	public ResultSet getdvkd();

	public void setnhanhang(ResultSet nhanhang);
	public ResultSet getnhanhang();

	public void setchungloai(ResultSet chungloai);
	public ResultSet getchungloai();

	public void setkhoId(String khoId);
	public String getkhoId();

	public void setkho(ResultSet kho);
	public ResultSet getkho();

	public void setbook(String book);
	public String getbook();

	public void setphanloai(String pl);
	public String getphanloai();

	public void setdiscount(String discount);
	public String getdiscount();

	public void setctkmtlId(String ctkmtlId);
	public String getctkmtlId();

	public void setRskmtl(ResultSet rskmtl);
	public ResultSet getRskmtl();

	public void setpromotion(String promotion);
	public String getpromotion();

	public void setvungId(String vungId);
	public String getvungId();

	public void setvung(ResultSet vung);
	public ResultSet getvung();

	public void setkhuvucId(String khuvucId);
	public String getkhuvucId();

	public void setkhuvuc(ResultSet khuvuc);
	public ResultSet getkhuvuc();

	public void setnpp(ResultSet npp);
	public ResultSet getnpp();
	
	public void setkh(ResultSet kh);
	public ResultSet getkh();

	public void setgsbhId(String gsbhId);
	public String getgsbhId();

	public void setASMId(String asmId);
	public String getASMId();

	public void setBMId(String bmId);
	public String getBMId();

	public void setgsbh(ResultSet gsbh);
	public ResultSet getgsbh();

	public void setASM(ResultSet asm);
	public ResultSet getASM();

	public void setBM(ResultSet bm);
	public ResultSet getBM();

	public void setsanphamId(String sanphamId);
	public String getsanphamId();

	public void setsanpham(ResultSet sanpham);
	public ResultSet getsanpham();

	public void setdvdlId(String dvdlId);
	public String getdvdlId();

	public void setdvdl(ResultSet dvdl);
	public ResultSet getdvdl();

	public void setFieldShow(String[] fieldShow);
	public String[] getFieldShow();

	public void setFieldHidden(String[] fieldHidden);
	public String[] getFieldHidden();

	public String getDateTime();

	public String getDate();

	public void init();

	public void DBclose();

	public void setngayton(String ngayton);
	public String getngayton();

	public void setvat(String vat);
	public String getvat();

	public void setlessday(String lessday);
	public String getlessday();

	public void setmoreday(String moreday);
	public String getmoreday();

	// Danh muc dai dien kinh doanh
	public void setDdkd(String ddkd);
	public String getDdkd();

	public void setRsddkd(ResultSet rs);
	public ResultSet getRsddkd();

	public void setUnit(String unit);
	public String getUnit();

	public void setGroupCus(String groupCus);
	public String getGroupCus();

	public void setPrograms(String programs);
	public String getPrograms();

	public void setRsPrograms(ResultSet RsPrograms);
	public ResultSet getRsPrograms();

	public void setDonViTinh(String donviTinh);
	public String getDonViTinh();

	public void setMonth(String month);
	public String getMonth();

	public void setYear(String year);
	public String getYear();

	public void setFromMonth(String month);
	public String getFromMonth();

	public void setToMonth(String month);
	public String getToMonth();

	public void setUnghang(String unghang);
	public String getUnghang();

	public void settype(String unghang);
	public String gettype();

	public void setFromYear(String fromyear);
	public String getFromYear();

	public void setToYear(String toyear);
	public String getToYear();

	public void SetNhoSPId(String nhomspid);
	public String GetNhoSPId();

	public void SetQuyCachId(String QuyCachId);
	public String GetQuyCachId();

	public void setRsNhomSP(ResultSet rs);
	public ResultSet GetRsNhomSP();

	public void setRsQuyCach(ResultSet QuyCach);
	public ResultSet GetRsQuyCach();

	public ResultSet getNhomspRs();
	public void setNhomspRs(ResultSet nspRs);

	public String getNspId();
	public void setNspId(String nspId);

	public void CreaterRsKh();

	public void createKhRS1();
	
	public ResultSet GetRsTinhThanh();
	
	public ResultSet GetRsQuanHuyen();

	public ResultSet GetRsHangCuahang();

	public ResultSet GetRsLoaicuahang();

	public ResultSet GetRsVitriCuahang();

	public String GetIdTinhThanh();

	public String GetIdQuanHuyen();

	public String GetIdHangCuahang();

	public String GetIdLoaicuahang();

	public String GetIdVitriCuahang();

	public void SetIdTinhThanh(String id);

	public void SetIdQuanHuyen(String id);
	public void SetIdHangCuahang(String id);

	public void SetIdLoaicuahang(String id);
	public void SetIdVitriCuahang(String id);

	public void setxemtheo(String xemtheo);
	public String getxemtheo();

	public String getMuclay();
	public void setMuclay(String muclay);

	public String getTieuchiId();
	public void setTieuchiId(String tieuchiId);

	public ResultSet getTieuchiRs();
	public void setTieuchiRs(ResultSet tieuchiRs);

	public String getNgayketthucctkm();
	public void setNgayketthucctkm(String Ngayketthucctkm);

	public String getHangDiDuong();
	public void setHangDiDuong(String hangdiduong);
	
	public String getNganhHangId();
	public void SetNganhHangId(String nganhhangId);
	
	public ResultSet getNganhHangRs();
	public ResultSet setNganhHangRs(ResultSet nganhhangRs);
	
	public String getHoaDonKmDb();
	public void setHoaDonKmDb(String hdkmdb);
	
	public String getTbhId();
	public void setTbhId(String tbhId);
	
	
	public String getLoaiNv();
	public void setLoaiNv(String loaiNv);
	
	
	public String getNppId();
	public void setNppId(String nppId);
	
	public ResultSet getNppRs();
	public void setNppRs(ResultSet nppRs);
	
	// Chỉnh sửa
    public String getActiveTab();
    public void setActiveTab(String active);
    public ResultSet getETCRs();
	public void setETCRS(ResultSet ETCRs);
	public ResultSet getOTCRs();
	public void setOTCRS(ResultSet OTCRs);
	public ResultSet getKMRs();
	public void setKMRS(ResultSet KMRs);
	
	public ResultSet getHdKhacRs();
	public void setHdKhacRs(ResultSet HdKhacRs);
	
	public void searchQuery_ETC(String searchquery);
	public void searchQuery_OTC(String searchquery);
	public void searchQuery_KM(String searchquery);	
	public void searchQuery_HDKhac(String searchquery);
	// Hết chỉnh sửa
	
	public String getNvgnId();
	public void setNvgnId(String nvgnId);
	
	public ResultSet getNvgnRs();
	public void setNvgnRs(ResultSet nvgnRs);
	
	
	public void setTotal_Query(String searchquery);
	public ResultSet getTotalRs();
	public void setTotalRs(ResultSet totalRs);
	
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public String getTructhuoc_fk();
	public void setTructhuoc_fk(String tructhuoc);
	
	public String getTtId();
	public void setTtId(String ttId);
	
	public ResultSet getTtRs();
	public void setTtRs(ResultSet ttRs);
	
	public String getMucCN_DT();
	public void setMucCN_DT(String cndt);
	
	public String getMuc_KhachHang();
	public void setMuc_KhachHang(String kh);
	
	public ResultSet getTinhthanh();
	public void setTinhthanh(ResultSet tinhthanh);
	
	public String getTinhthanhid();
	public void setTinhthanhid(String tinhthanhid);
	
	public String getCoHoadon();
	public void setCoHoadon(String value);
	
	public ResultSet getSpRs();
	public void setSpId(String spid);
	public String getSpId();
	public void setRSBaocao(ResultSet rs);
	public ResultSet getRSBaocao();
	
	public String getLaytheo();
	public void setLaytheo(String value);
	
	public String getQuy();
	public void setQuy(String quy);
	public String getCn1() ;

	public void setCn1(String cn1) ;
	public String getKhoid();
	public void setKhoid(String khoid);
	
	public String getTenkho();
	public void setTenkho(String tenkho);
	
	public void setLoaikhoRs(ResultSet loaikhoRs);
	public ResultSet getLoaikhoRs();
	public String getLoaikho();
	public void setLoaikho(String loaikho);
	
	public ResultSet getKhors();
	public void setKhors(ResultSet khors) ;

	public String getCttbId();
	public void setCttbId(String cttbId);
	public ResultSet getCttbRs() ;
	public String getLoaiMenu();
	public void setLoaiMenu(String loaiMenu) ;
	
	public void setDataRs(String query);
	public ResultSet getDataRs();
	
	public void setNccId(String nccId);
	public String getNccId();
	public void setNccRs(ResultSet nccRs);
	public ResultSet getNccRs();
	
	public String getChiTietXNT_Lo();
	public void setChiTietXNT_Lo(String ChiTietXNT_Lo);
	
	public void setDongiagoc(String dongiaGoc);
	public String getDongiagoc();
	
	public void setChuyenSale(String chuyenSale);
	public String getChuyenSale();
	
	public void setKhuyenmai(String khuyenmai);
	public String getKhuyenmai();
	
}
