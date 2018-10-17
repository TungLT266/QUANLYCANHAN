package geso.traphaco.erp.beans.thutien;

import java.sql.ResultSet;
import java.util.List;

public interface IErpThutienCK 
{
	public String getUserId();
	public void setUserId(String userId);

	public String getCtyId();
	public void setCtyId(String ctyId); 
	
	public String getXoakhtratruocId();
	public void setXoakhtratruocId(String xoakhtratruocId); 
	
	public String getId();
	public void setId(String id);
	public String getNgaychungtu();
	public void setNgaychungtu(String ngaychungtu);
	public String getNgayghiso();
	public void setNgayghiso(String ngayghiso);
	
	public String getNppId();
	public void setNppId(String nccId);
	public ResultSet getNppRs();
	public void setNppRs(ResultSet nccRs);
	
	public String getHtttId();
	public void setHtttId(String htttId);
	public ResultSet getHtttRs();
	public void setHtttRs(ResultSet htttRs);
	
	public String getNoidungId();
	public void setNoidungId(String ndId);
	public ResultSet getNoidungRs();
	public void setNoidungRs(ResultSet ndRs);
	
	public String getNganhangId();
	public void setNganhangId(String nganhangId);
	public ResultSet getNganhangRs();
	public void setNganhangRs(ResultSet nganhangRs);
	
	public String getChinhanhId();
	public void setChinhanhId(String cnId);
	public ResultSet getChinhanhRs();
	public void setChinhanhRs(ResultSet chinhanhRs);
	
	public String getSotaikhoan();
	public void setSotaikhoan(String sotk);
	
	public String getNoidungtt();
	public void setNoidungtt(String ndtt);
	public String getSotientt();
	public void setSotientt(String sotientt);
	
	public String getSotienttNT();
	
	public void setSotienttNT(String sotienttNT); 
	
	public String getSochungtu();
	public void setSochungtu(String soct);
	
	public String getTienteId();
	public void setTienteId(String ttId);
	public ResultSet getTienteRs();
	public void setTienteRs(ResultSet tienteRs);
	
	public String getDinhkhoanco();
	public void setDinhkhoanco(String dinhkhoanco);
	public String getDinhkhoancoId();
	public void setDinhkhoancoId(String dinhkhoancoId); 
		
	public String getDoiTuongDinhKhoan();
	public void setDoiTuongDinhKhoan(String DoiTuongDinhKhoan);
	public String getDoituongdinhkhoanId();
	public void setDoituongdinhkhoanId(String DoituongdinhkhoanId);
	public String getMaTenDoiTuongDinhKhoan();
	public void setMaTenDoiTuongDinhKhoan(String MaTenDoiTuongDinhKhoan);
	
	public String getDoiTuongTamUng();
	public void setDoiTuongTamUng(String DoiTuongTamUng);
	
	public String getNccId();
	public void setNccId(String nccId);
	public ResultSet getNccRs();
	public void setNccRs(ResultSet nccRs);
	
	public String getNvtuId();
	public void setNvtuId(String nvtuId);
	public ResultSet getNvtuRs();
	public void setNvtuRs(ResultSet nvtuRs);
	
	public String getHoadonIds();
	public void setHoadonIds(String hdIds);
	public List<IHoadon> getHoadonRs();
	public void setHoadonRs(List<IHoadon> hoadonRs);
	
	public String getNhomkhttId();
	public void setNhomkhttId(String nhomkhttId);
	public String getNhomkhtt();
	public void setNhomkhtt(String nhomkhtt);
	public ResultSet getNhomkhttRs();
	public void setNhomkhttRs(ResultSet nhomkhttRs);
	
	public boolean createTTHD();
	public boolean updateTTHD();
	public boolean chotTTHD(String userId);
	
	public String getMsg();
	public void setMsg(String msg);
	
	public void init();
	public void initDisplay();
	public void initPdf();
	public void initUnc();
	public void createRs();
	public void createRs_display();
	public void DBclose();
	
	public String getThuduoc(); 

	public void setThuduoc(String thuduoc);

	public String getThuduocNT(); 

	public void setThuduocNT(String thuduocNT);

	public String getChiphinganhang(); 

	public void setChiphinganhang(String cpnganhang);

	public String getChiphinganhangNT(); 

	public void setChiphinganhangNT(String cpnganhangNT);

	public String getChenhlech();

	public void setChenhlech(String chenhlech);
	
	public String getChenhlechNT();

	public void setChenhlechNT(String chenhlechNT);
	
	public String getTigia();

	public void setTigia(String tigia);
	
	public ResultSet getSotkRs();

	public void setSotkRs(ResultSet sotkRs);

	public String getTongNT(); 

	public void setTongNT(String tongNT);

	public String getTongVND(); 

	public void setTongVND(String tongVND);
	
	public String getTigia_hoadon();

	public void setTigia_hoadon(String Tigia_hoadon);
	
	public String getChietkhauNT();

	public void setChietkhauNT(String chietkhauNT);
	
	public String getChietkhau();

	public void setChietkhau(String chietkhau);
		
	public String getNguoinoptien();
	public void setNguoinoptien(String nguoinoptien);
	
	public String getDiachi();
	public void setDiachi(String diachi);
	
	public String getChungTuKemTheo();
	public void setChungTuKemTheo(String chungtukemtheo);
	
	public String getLydonop();
	public void setLydonop(String lydonop);
	
	public List<IDinhkhoanco> getDinhkhoancoRs();
	public void setDinhkhoancoRs(List<IDinhkhoanco> dinhkhoancoRs);
	
	public ResultSet getTaikhoanRs();
	public void setTaikhoanRs(ResultSet taikhoanRs);
	
	public ResultSet getSanphamList();
    public void setSanphamRs(ResultSet sanphamList);

    public ResultSet getNganhangList();
    public void setNganhangList(ResultSet nganhangList);
    
    public ResultSet getNccList();
    public void setNccList(ResultSet nccList);
    
    public ResultSet getKhachhangList();
    public void setKhachhangList(ResultSet khachhangList);
    
    public ResultSet getNhanvienList();
    public void setNhanvienList(ResultSet nhanvienList);
    
    public ResultSet getTaisanList();
    public void setTaisanList(ResultSet taisanList);
	
    public String getKbhId();
	public void setKbhId(String kbhId);
	public ResultSet getKbhRs();
	public void setKbhRs(ResultSet kbhRs);
	
	public String getnppdangnhap();
	public void setnppdangnhap(String nppdangnhap);
	
	public String getBangkeId();
	public void setBangkeId(String bangkeId);
	public ResultSet getBangkeRs();
	public void setBangkeRs(ResultSet bangkeRs);
		
	public String getisNPP();
	public void setisNPP(String isNPP);
	
	public String getnppIdgoc();
	public void setnppIdgoc(String nppIdgoc);
	
	//PHÂN QUYỀN THEO LOẠI NHÂN VIÊN ĐĂNG NHẬP
	public String getLoainhanvien();
	
	public void setLoainhanvien(Object loainhanvien);
	
	public String getDoituongIddn();
	
	public void setDoituongIddn(Object doituongIddn);
	
	public String getNPPChinhanhId();
	
	public void setNPPChinhanhId(String nppchinhanhId);
	
	public String getIsChuyenCN();
	
	public void setIsChuyenCN(String isChuyenCN);
	
	public String getIsthuhoCN();
	
	public void setIsthuhoCN(String isthuhoCN);
	
	public void setSoChungTu_Chu(String soChungTu_Chu);
	public String getSoChungTu_Chu();

	public void setSoChungTu_So(String soChungTu_So);
	public String getSoChungTu_So();
	
	public String getKh_npp_Ten();

	public void setKh_npp_Ten(String kh_npp_Ten) ;
	
	public String getQueryNpp();

	public void setQueryNpp(String queryNpp) ;
	
	public boolean BochotTTHD(String userId);
	
	public ResultSet getDoituongkhacList();	
	
	public void setDoituongkhacList(ResultSet DoituongkhacList);
	
	public void taoMoiSoChungTu();
	
}