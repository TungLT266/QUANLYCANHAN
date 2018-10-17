package geso.traphaco.erp.beans.kiemdinhchatluong;

import geso.traphaco.center.beans.thongtinsanpham.ITieuchikiemdinh;
import geso.traphaco.erp.beans.kiemdinhchatluong.giay.IErpHoso;

import java.sql.ResultSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public interface IErpKiemdinhchatluong_NhGiay 
{
	public String getUserId();
	public void setUserId(String userId);

	public String getYcId();
	public void setYcId(String ycId);

	public String getSpId();
	public void setSpId(String spId);

	public String getSpTen();
	public void setSpTen(String spTen);

	public String getNhanhangId();
	public void setNhanhangId(String nkId);
	
	public String getNccId();
	public void setNccId(String nccid);

	public String getSolo();
	public void setSolo(String solo);

	public String getSoLuongKiemDinh();
	public void setSoLuongKiemDinh(String soluong);
	
	public String getSoLuongmau();
	public void setSoLuongmau(String soluongmau);

	public String getTongsoluongnhap();
	public void setTongsoluongnhap(String soluong);
	
	public String getsoluongchuakiem();
	public void setsoluongchuakiem(String soluong);
	
	public String getsoluongDat();
	public void setsoluongDat(String soluongDat);

	public String getCongtyId();
	public void setCongtyId(String congtyId);

	public String getTrangThai();
	public void setTrangThai(String trangthai);

	public String getDatCl();
	public void setDatCl(String datcl);

	public String getDinhluong();
	public void setDinhluong(String dinhluong);

	public String getDinhtinh();
	public void setDinhtinh(String dinhtinh);

	public String[] getTieuchi_dinhtinh();
	public void setTieuchi_dinhtinh(String[] tieuchi_dinhtinh);

	public String[] getGhinhan_dinhtinh();
	public void setGhinhan_dinhtinh(String[] ghinhan_dinhtinh);

	public String[] getKetqua_dinhtinh();
	public void setKetqua_dinhtinh(String[] ketqua_dinhtinh);

	public String[] getNguoiSua_dinhtinh();
	public void setNguoiSua_dinhtinh(String[] nguoisua_dinhtinh);

	public String getNgayKiem();
	public void setNgayKiem(String ngaykiem);
	
	public String getNgaySanXuat();
	public void setNgaySanXuat(String ngaysanxuat);
	
	public String getNhaCungCap();
	public void setNhaCungCap(String nhacungcap);
	
	public String getNgayNhanHang();
	public void setNgayNhanHang(String ngaynhanhang);
 
	public String getThieuhoso();
	public void setThieuhoso(String Thieuhoso);
 
	public void setTieuchikiemdinhList(List<ITieuchikiemdinh> list);
	public List<ITieuchikiemdinh> getTieuchikiemdinhList();
	
	public void setListHoso(List<IErpHoso> ListHoso);
	public List<IErpHoso> getListHoso();
	
	public void setListSanPhamDuyet(List<IErpSanphamduyet> List);
	public List<IErpSanphamduyet> getListSanPhamDuyet();
	
	
	
	public String getDeNghiXuLy();
	public void setDeNghiXuLy(String denghixuly);

	public String getMsg();
	public void setMsg(String msg);

	public boolean updateKiemdinh(HttpServletRequest request);

	public boolean duyetKiemDinh();

	public void setRsYeuCauKiemDinh(ResultSet rsYc);
	public ResultSet getRsYeuCauKiemDinh();

	public void init();

	public void createRs();

	public void DbClose();
	public boolean CapNhatHoSo();
	public void setIsCapDong(String iscapdong);
	public String getIsCapDong();

	public String getNgayhethan();
	public void setNgayhethan(String ngayhethan);
	
	public String getKhoId();
	public void setKhoId(String khoId);
	public String getGhichu();
	public void setGhichu(String ghichu);
	
	public void setRsSolo(ResultSet soloRs);
	
	public ResultSet getRsSolo();
	public void setRsKho(ResultSet khoRs);	
	public ResultSet getRsKho();
	
	public ResultSet getRsSanPham();
	public ResultSet getRsLoaiSanPham();
	// tao them 2 resultset lay thong tin hoa cgat san pham va may moc san pham
	public ResultSet getRshoachatsp();
	public void setRshoachatsp(ResultSet rshcsp);
	
	public ResultSet getRsmaymocsp();
	public void setRsmaymocsp(ResultSet rsmmsp);
	//
	public String getLOAISPID();

	public void setLOAISPID(String loaispId);
	
	public String getLoai();
	public void setLoai(String loai);
	
	public String getKyhieukd();
	public void setKyhieukd(String kyhieukd);
	
	// them loai mua hang(trong nuoc hoac nhap khau)
	public String getloaimuahang();
	public void setloaimuahang(String loaimh);
	
	public ResultSet getRsSoThung();
	public void setRsSoThung(ResultSet rsSoThung);
	
	public String[] getListSoThung();
	public void setListSoThung(String[] listSoThung);
	public String[] getListSoLuongThung(); 
	public void setListSoLuongThung(String[] listSoLuongThung);
	public String[] getListLayMauThung(); 
	public void setListLayMauThung(String[] listLayMauThung);
	public String[] getListDatThung();
	public void setListDatThung(String[] listDatThung);
	public String[] getListKhongDatThung();
	public void setListKhongDatThung(String[] listKhongDatThung);
	public String[] getListHamAm();
	public void setListHamAm(String[] listHamAm);
	public String[] getListHamLuong();
	public void setListHamLuong(String[] listHamLuong) ;
	public boolean duyetKiemDinhNhapKhau(String loaimuahang);
	
	public double getHamAm();
	public void setHamAm(double hamAm);
	public double getHamLuong();
	public void setHamLuong(double hamLuong);
	
	public String getMaPhieu();
	public void setMaPhieu(String maPhieu);
	
	public String getSohoadon();
	public void setSohoadon(String sohoadon);

}
