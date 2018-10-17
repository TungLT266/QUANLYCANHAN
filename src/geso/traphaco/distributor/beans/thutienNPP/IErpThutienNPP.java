package geso.traphaco.distributor.beans.thutienNPP;

import java.sql.ResultSet;
import java.util.Hashtable;
import java.util.List;

public interface IErpThutienNPP 
{
	public String getUserId();
	public void setUserId(String userId);

	public String getCtyId();
	public void setCtyId(String ctyId); 

	public String getId();
	public void setId(String id);
	public String getNgaychungtu();
	public void setNgaychungtu(String ngaychungtu);
	public String getNgayghiso();
	public void setNgayghiso(String ngayghiso);
	
	public String getSoin() ;
	public void setSoin(String soin);
	
	public String getHinhthucTT() ;
	public void setHinhthucTT(String hinhthucTT);
	
	public Integer getCheckDN() ;
	public void setCheckDN(Integer checkDN) ;
	
	public String getNppId();
	public void setNppId(String nccId);
	public ResultSet getNppRs();
	public void setNppRs(ResultSet nccRs);
	
	public String getKhIds();
	public void setKhIds(String khIds);
	public ResultSet getKhRs();
	public void setKhRs(ResultSet khRs);
	
	public String getNoptienIds();
	public void setNoptienIds(String noptienIds);
	public ResultSet getNoptienRs();
	public void setNoptienRs(ResultSet noptienRs);
	
	public String getNhanvienGNIds();
	public void setNhanvienGNIds(String nhanvienGNIds);
	public ResultSet getNhanvienGNRs();
	public void setNhanvienGNRs(ResultSet nhanvienGNRs);
	
	public String getNhanvienBHIds();
	public void setNhanvienBHIds(String nhanvienBHIds);
	public ResultSet getNhanvienBHRs();
	public void setNhanvienBHRs(ResultSet nhanvienBHRs);
	
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
	
	public String getNguoinoptien();
	public void setNguoinoptien(String nguoinoptien);
	
	public String getNoidungtt();
	public void setNoidungtt(String ndtt);
	public String getSotientt();
	public void setSotientt(String sotientt);
	
	public String getSotiendanop();
	public void setSotiendanop(String sotiendanop);
	public String getSotienthuthem();
	public void setSotienthuthem(String sotienthuthem);
	
	public String getChonthutuHD();
	public void setChonthutuHD(String chonthutuHD);
	
	public String getSotienttNT();
	
	public void setSotienttNT(String sotienttNT); 
	
	public String getTungay();
	public void setTungay(String tungay);
	public String getDenngay();
	public void setDenngay(String denngay);
	
	public String getSochungtu();
	public void setSochungtu(String soct);
	
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
	public List<IHoadonNPP> getHoadonRs();
	public void setHoadonRs(List<IHoadonNPP> hoadonRs);
	
	public String getHdId();
	public void setHdId(String hdId);
	public ResultSet getHdTCRs();
	public void setHdTCRs(ResultSet hdTCRs);
	
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
	public void DBclose();
	public void createRsLoc();
	
	public String getThuduoc(); 

	public void setThuduoc(String thuduoc);

	public String getThuduocNT(); 

	public void setThuduocNT(String thuduocNT);

	public String getBpkinhdoanh(); 

	public void setBpkinhdoanh(String bpkinhdoanh);
	
	public String getLydonop(); 

	public void setLydonop(String lydonop);

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
	
	public String getNppIds();
	public void setNppIds(String ddhIds );
	
	public String getHdIds();
	public void setHdIds(String HdIds );
	
	public Hashtable<String,Double> getNo_KhachHangList();
	public void setNo_KhachHangList(Hashtable<String,Double> no_KhachHangList);
	
	public String[] getHoadonId_old();
	public void setHoadonId_old(String[] hoadonId_old);
	
	public String getKhId();
	public void setKhId(String khid);
	public ResultSet getKhList();
	public void setKhList(ResultSet Khlist);
		
	//Hóa đơn thêm
	public String get_add_madt();
	public void set_add_madt(String add_madt);
	
	public String get_add_makh();
	public void set_add_makh(String add_makh);
	
	public String get_add_idhd();
	public void set_add_idhd(String add_idhd);
	
	public String get_add_sohoadon();
	public void set_add_sohoadon(String add_sohoadon);
	
	public String get_add_ngayhd();
	public void set_add_ngayhd(String add_ngayhd);
	
	public String get_add_tongsotien();
	public void set_add_tongsotien(String add_tongsotien);
	
	public String get_add_thanhtoan();
	public void set_add_thanhtoan(String add_thanhtoan);
	
	public String get_add_conlai();
	public void set_add_conlai(String add_conlai);
	
	public String get_add_check();
	public void set_add_check(String add_check);
	
	
	//Hết hóa đơn thêm
}
