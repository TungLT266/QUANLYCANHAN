package geso.traphaco.distributor.beans.hoadonphelieu;

import geso.traphaco.distributor.beans.hoadonphelieu.IErpHoaDonPL_SP;

import java.sql.ResultSet;
import java.util.Hashtable;
import java.util.List;

public interface IErpHoadonphelieu
{
	public String getUserId();
	public void setUserId(String userId);
	
	public String getCongtyId();
	public void setCongtyId(String congtyId);
	
	public String getId();
	public void setId(String Id);
	
	public String getNgayghinhan();
	public void setNgayghinhan(String ngayghinhan);
	public String getNgayhoadon();
	public void setNgayhoadon(String ngayhoadon);
	public String getKyhieuhoadon();
	public void setKyhieuhoadon(String kyhieuhd);
	public String getSohoadon();
	public void setSohoadon(String sohoadon);
	public String getVat();
	public void setVat(String vat);
	public String getBvat();
	public void setBvat(String bvat);
	public String getAvat();
	public void setAvat(String avat);
	
	public String getDiengiai();
	public void setDiengiai(String diengiai);
	
	public String getTungay();
	public void setTungay(String tungay);
	public String getDenngay();
	public void setDenngay(String denngay);
	
	public String getNccId();
	public void setNccId(String nccId);
	public String getNccTen();
	public void setNccTen(String nccTen);

	public ResultSet getPORs();
	public void setPORs(ResultSet poRs);
	public String getPOId();
	public void setPOId(String poId);
	
	
	public String[] getTensansham();
	public void setTensanpham(String[] tensanpham);
	public String[] getDvt();
	public void setDvt(String[] donvi);
	public String[] getQuyDoi();
	public void setQuyDoi(String[] quyDoi);
	public String[] getSoluong();
	public void setSoluong(String[] soluong);
	public String[] getDongia();
	public void setDongia(String[] dongia);
	public String[] getTongtien();
	public void setTongtien(String[] tongtien);
	
	
	public String getMsg();
	public void setMsg(String msg);
	
	public ResultSet getKhoanmucDTRs();
	public void setKhoanmucDTRs(ResultSet khoanmucDTRs);
	public String getKhoanmucDTId();
	public void setKhoanmucDTId(String khoanmucDTId);
	
	public String getLoaiCK();
	public void setLoaiCK(String loaiCK);
	
	public boolean createGiamgia();
	public boolean updateGiamgia();
	
	public boolean updateGiamgiaSohoadon();
	
	public void createRS();
	public void loadhd();
	public void init();
	
	
	public void DbClose();
	public void initExcel();
		
	public List<IErpHoaDonPL_SP> GetSanPhamList();
	public void setSanPhamList(List<IErpHoaDonPL_SP> SanPhamList);
	
	public String CreateLSIN(String ddhId, String loaihd);
	public void initInLS(String lsinId);	
	
	public void setSanphamGhiChu( Hashtable<String, String> sanpham_ghichu );
	public Hashtable<String, String> getSanphamGhiChu();
	
	public String getkhId();
	public void setkhId(String khId);
	
	public String getkhIdGoc();
	public void setkhIdGoc(String khIdgoc);
	
	public String getisNPP();
	public void setisNPP(String isNPP);
	
	public ResultSet getkhRs();
	public void setkhRs(ResultSet khRs);
	
	public String getHdId();
	public void setHdId(String HdId);
	public ResultSet getHdRs();
	public void setHdRs(ResultSet HdRs);
	
	public String gettenhanghoadichvu();
	public void settenhanghoadichvu(String tenhanghoadichvu);
	
	public String gethinhthucthanhtoan();
	public void sethinhthucthanhtoan(String hinhthucthanhtoan);
	
	public String getNguoiMuaHang();
	public void setNguoiMuaHang(String nguoimuahang);
	
	public String getDonVi();
	public void setDonVi(String donvi);
	
	public String getDiaChi();
	public void setDiaChi(String diachi);
	
	public String getMST();
	public void setMST(String masothue);
	
	public ResultSet getKbhRs();
	public void setKbhRs(ResultSet kbhRs);
	public String getKbhId();
	public void setKbhId(String kbhId);
	public String getKbhTen();
	public void setKbhTen(String kbhTen);
	
	public String getTrangthai() ;
	public void setTrangthai(String trangthai) ;
	
	public ResultSet getTaikhoandoanhthuRs();
	public void setTaikhoandoanhthuRs(ResultSet taikhoandoanhthuRs);
	public String getTaikhoandoanhthuId();
	public void setTaikhoandoanhthuId(String taikhoandoanhthuId);
	
	public ResultSet getTaikhoanghinoRs();
	public void setTaikhoanghinoRs(ResultSet taikhoanghinoRs);
	public String getTaikhoanghinoId();
	public void setTaikhoanghinoId(String taikhoanghinoId);
	
	public String getHoadonId();
	public void setHoadonId(String hdId);
	public ResultSet getHoadonRs();
	public void setHoadonRs(ResultSet hoadonRs);
	
	public String gettimHoadon();
	public void settimHoadon(String timhdId);
	
	//PHÂN QUYỀN THEO LOẠI NHÂN VIÊN ĐĂNG NHẬP
	public String getLoainhanvien();
	
	public void setLoainhanvien(Object loainhanvien);
	
	public String getDoituongIddn();
	
	public void setDoituongIddn(Object doituongIddn);

}
