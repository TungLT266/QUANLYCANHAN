package geso.traphaco.erp.beans.nhacungcap;

import geso.traphaco.center.util.Erp_Item;
import geso.traphaco.center.util.IPhan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.List;

public interface IErpNhaCungCap extends Serializable, IPhan_Trang
{
	public void NewDbUtil();
	public String getFax() ;
	
	public void setFax(String fax);
	public String getId();

	public void setId(String id);

	public void setMa(String ma);

	public String getMa();

	public String getTen();

	public void setTen(String ten);

	public String getMST();

	public void setMST(String mst);

	public void setThoiHanNo(String thoihan);

	public String getThoiHanNo();

	public String getHanMucNo();

	public void setHanMucNo(String hanmuc);

	public String getDienThoai_NCC();

	public void setDienThoai_NCC(String dienthoai_ncc);

	public String getNguoiLienHe();

	public void setNguoiLienHe(String nguoilienhe);

	public String getEmail_NLH();

	public void setEmail_NLH(String email);

	public String getDienThoai_NLH();

	public void setDienThoai_NLH(String dienthoai_nlh);

	public String getNguoiMuaHang();

	public void setNguoiMuaHang(String nguoimuahang);

	public void setUserId(String userId);

	public String getUserId();

	public String getMsg();

	public void setMsg(String msg);

	public void setTrangThai(String trangthai);

	public String getTrangThai();

	public String getCongTy();

	public void setCongTy(String congty);

	public String getDiaChi_NCC();

	public void setDiaChi_NCC(String diachi_ncc);

	public String getLoaiNCC();

	public void setLoaiNCC(String loaincc);

	public String getMucTinDung();

	public void setMucTinDung(String muctindung);
	
	public String getNganHang();

	public void setNganHang(String nganhang);

	public String getSoTaiKhoan();

	public void setSoTaiKhoan(String sotaikhoan);

	public String getTaiKhoan();

	public void setTaiKhoan(String taikhoan);

	public String getChiNhanh();

	public void setChiNhanh(String chinhanh);

	public void init();

	public void search();

	public void close();

	public void createaRs();

	public boolean UpdateNcc();

	public boolean CreateNcc();

	public boolean DeleteNcc();

	public boolean CheckUnique();

	public boolean CheckReferences(String column, String table);

	public boolean CheckNumerOrNot(String number);

	public ResultSet getCongTyRs();
	public void setCongTyRs(ResultSet congty);

	public ResultSet getLoaiNCCRs();
	public void setLoaiNCC(ResultSet LoaiNCC);
	public String getLoaiNCC(String loaincc);
	public ResultSet getChiNhanhRs();
	public void setChiNhanhRs(ResultSet chinhanh);

	public void setNganHang(ResultSet nganhang);
	public ResultSet getNganHangRs();
	
	public ResultSet getTaiKhoanRs();
	public void setTaiKhoanRs(ResultSet taikhoan);

	public ResultSet getNhaCungCapRs();
	public void setNhaCungCapRs(ResultSet nhacungcap);
	
	public void setSpNhanGiaCong(ResultSet spNhangiacong);
	public ResultSet getSpNhanGiaCong();

	public void setSpNhangiacongIds(String spNhangiacongIds);
	public String getSpNhangiacongIds();
	
	public void setKhoNlRs(ResultSet spNhangiacong);
	public ResultSet getKhoNlRs();

	public void setKhoNlId(String khoNlId);
	public String getKhoNlId();
	
	public void setQuanlycongno(String quanlyCN);
	public String getQuanlycongno();
	
	
	
	public ResultSet getLoaigiamuaRs();
	public void setLoaigiamuaNCC(ResultSet loaigiamuaNCC);
	public void setLoaigiamua(String loaigiamua);
	public String getLoaigiamua();
	
	public void setDuyet(String duyet);

	public String getDuyet();

	public boolean DuyetNcc();

	public boolean BoDuyetNcc();
	
	
	public void setIs_khuchexuat(String is_khuchexuat);
	public String getIs_khuchexuat();
	
	public void setChixem(String chixem);
	public String getChixem();
	
	public void setSoItems(int soItems);
	public int getSoItems();
	
	public void setTkkqRs(ResultSet tkkqRs);	
	public ResultSet getTkkqRs();
	
	public void setTkkqId(String tkkqId);
	public String getTkkqId();
	
	public void setNppId(String nppId);
	public String getNppId();

	public void setNppList(List<Erp_Item> nppList);
	public List<Erp_Item> getNppList();
	
	public String getNhTen();

	public void setNhTen(String nhTen);

	public String getTenCN() ;

	public void setTenCN(String tenCN) ;

	public String getNhId() ;

	public void setNhId(String nhId) ;

	public String getCNId() ;

	public void setCNId(String cNId) ;
	
}