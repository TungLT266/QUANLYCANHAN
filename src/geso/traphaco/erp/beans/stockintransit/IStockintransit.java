package geso.traphaco.erp.beans.stockintransit;

import geso.traphaco.erp.beans.baocao.BaoCaoVayTienPoJo;

import java.sql.ResultSet;
import java.util.List;

public interface IStockintransit 
{
	public void setuserId (String userId);

	public String getuserId();

	public void setuserTen(String userTen);

	public String getuserTen();
	
	// Nhà phân phối
	public void setnppId(String nppId);

	public String getnppId();
	
	public ResultSet getNppRs();
	
	public void setNppRs( ResultSet nppRs);
	//====================================

	public void setnppTen(String nppTen);	

	public String getnppTen();

	public void setkenhId(String kenhId);

	public String getkenhId();
	
	public void setView(String view);

	public String getView();
	
	public void setLoaiBC(String loaiBC);

	public String getLoaiBC();
	
	public String getKenhBH();

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

	public void setdiscount(String discount);

	public String getdiscount();

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

	public void setrskhachhang(ResultSet rskhachhang);

	public ResultSet getrskhachhang();
	
	public void setgsbhId(String gsbhId);

	public String getgsbhId();

	public void setgsbh(ResultSet gsbh);

	public ResultSet getgsbh();

	public void setsanphamId(String sanphamId);

	public String getsanphamId();
	
	public void setKhachhangIds(String khachhangIds);

	public String getKhachhangIds();

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
	
	public void setPivot(String Pivot);
	public String getPivot();
	
	
	public void setFromYear(String fromyear);
	public String getFromYear();
	
	public void setToYear(String toyear);
	public String getToYear();
	
	public void SetNhoSPId(String nhomspid);
	public String GetNhoSPId();
	
	public void setRsNhomSP(ResultSet rs);

	public ResultSet GetRsNhomSP();
	
	public void setChartRs(List<IDataChart> chartRs);

	public List<IDataChart> getChartRs();
	
	public void SetKHid(String KhId);
	public String GetKhId();
	
	public void setRsKhErp(ResultSet rs);

	public ResultSet GetRsKhErp();
	
	public ResultSet getDiabanRs();
	
	public void SetLoaiSPId(String loaispid);
	public String GetLoaiSPId();
	
	public void setRsLoaiSP(ResultSet rs);

	public ResultSet GetRsLoaiSP();
	
	
	public void SetErpKhottId(String id);
	public String GetErpKhottId();
	
	public void setRsErpKho(ResultSet rs);

	public ResultSet GetRsErpKho();
	
	public ResultSet getRsErpCongty();
	public void setRsErpCongty(ResultSet rs);

	public String getErpCongtyId();
	public void setErpCongtyId(String rs);
	
	public ResultSet getRsErpTiente();
	public void setRsErpTiente(ResultSet rs);

	public String getErpTienteId();
	public void setErpTienteId(String rs);
	
	public String getErpTaiKhoanKTId();
	public void setErpTaiKhoanKTId(String rs);

	public String getErpLoaiTaiKhoanKTId();
	public void setErpLoaiKhoanKTId(String id);
	
	public String getTTCPId();
	public void setTTCPId(String ttcpid);
	
	public String getNhomTaiSanId();
	public void setNhomTaiSanId(String nhomtaisanid);
	
	public String getLoaiTaiSanId();
	public void setLoaiTaiSanId(String loaitaisanid);
	
	public ResultSet getRsErpLoaiTK();
	public void setRsErpLoaiTK(ResultSet rs);
	
	public ResultSet getNhomTaiSanRs();
	public void setNhomTaiSanRs(ResultSet nhomtaisanrs);
	
	public ResultSet getTTCPRs();
	public void setTTCPRs(ResultSet ttcprs);
	
	public ResultSet getLoaiTaiSanRs();
	public void setLoaiTaiSanRs(ResultSet loaitaisanrs);
	
	
	public void InitErp();
	
	public String getErpKhachHangId();
	public void setErpKhachHangId(String id);
	
	public String[][]  getMang2chieu();
	
	public void setMang2Chieu(String[][]  mang );
	
	public ResultSet getRsErpDonViTH();
	public void setRsErpDonViTH(ResultSet rs);
	public String getErpDonViTHId();
	public void setErpDonViTHid(String id);
	
	public String getErpNCCId();
	public void setErpNCCId(String id);
	
	public ResultSet getRsErpNCCId();
	public void setRsErpNCCId(ResultSet rs);
	public String getthangtrave();
	public void sethangtrave(String hangtrave);
	
	public String getLoaincc();
	public String getLoaiNCCIds();
	public void setLoaiNCCIds(String loainccIds);
	
	public ResultSet getRsErpLoaiNCC();
	public String getErpTiente();

	public ResultSet getRsNCC();

	public void setRsNCC(ResultSet rs);
	
	public ResultSet getRsNhamay();
	public void setRsNhamay(ResultSet rs);
	
	public String getNhamayId();
	public void setNhamayId(String NhamayId);
	
	
	public ResultSet getRsLoaiNCC();
	
	public void setRsLoaiNCC(ResultSet rs);

	public void setLNccIds(String[] lnccIds);

	public String[] getLNccIds();

	public void setNccIds(String[] nccIds);

	public String[] getNccIds();

	public void setLSPIds(String[] lspIds);

	public String[] getLSPIds();
	
	public String getNhomKhId();
	public void setNhomKhId(String nhomkhid);
	
	public ResultSet getRsNhomKh();
	public void setRsNhomKh(ResultSet nhomkhrs);
	
	public void settaisanIds(String taisanIds);
	public String gettaisanIds();
	
	public void settaisanRs(ResultSet taisanRs);

	public ResultSet gettaisanRs();
	
	public String gettaisanId();
	public void settaisanId(String taisanId);
	
	public String getnppdangnhap();
	public void setnppdangnhap(String nppdangnhap);
	

	public void setIsNPP(String isNPP);

	public String getIsNPP();

	public String getNhanvienId();
	public void setNhanvienId(String nhanvienid);
	
	public ResultSet getRsNhanvien();
	public void setRsNhanvien(ResultSet nhanvienrs);
	
	public void setCtyRs(ResultSet ctyRs);
	public ResultSet getCtyRs();
	
	public void setCtyIds(String[] ctyIds);

	public String[] getCtyIds();
	
	public ResultSet getCongtyRS(String ctyId);
	
	public ResultSet getKhachhangRs();
	
	public ResultSet getKhachhangRS(String khId);
	
	//PHÂN QUYỀN THEO LOẠI NHÂN VIÊN ĐĂNG NHẬP
	public String getLoainhanvien();
	
	public void setLoainhanvien(Object loainhanvien);
	
	public String getDoituongId();
	
	public void setDoituongId(Object doituongId);
	
	public String getTenxuathd();
	
	public void setTenxuathd(String tenxuathd);
	
	public String getDiabanId();
	
	public void setDiabanId(String diabanId);
		
	public List<BaoCaoVayTienPoJo> getListBaoCaoVayTien();

	public void setListBaoCaoVayTien(List<BaoCaoVayTienPoJo> listBaoCaoVayTien);
	
	public void getCongTyBaoCao();

	public void init_Khoaso();

	void initKhachHangRs();

	String getChiNhanh();

	void setChiNhanh(String chiNhanh);

	String getChiNhanhTen();

	void setChiNhanhTen(String chiNhanhTen);

	void initChiNhanhRs();

	ResultSet getChiNhanhRs();

	void setChiNhanhRs(ResultSet chiNhanhRs);

	String getCongTy();

	void setCongTy(String congTy);
}
