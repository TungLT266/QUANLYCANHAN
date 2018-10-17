package geso.traphaco.erp.beans.kiemdinhchatluong.giay;

import geso.traphaco.center.beans.thongtinsanpham.ITieuchikiemdinh;

import java.sql.ResultSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public interface IErpKiemdinhchatluong
{
	public String getUserId();

	public void setUserId(String userId);

	public String getYcId();

	public void setYcId(String ycId);
	
	public String getMaphieu();
	public void setMaphieu(String Maphieu);
	
	public String getNgaySanXuat();
	public void setNgaySanXuat(String ngaysanxuat);
	
	
	public String getNgayNhanHang();

	public void setNgayNhanHang(String ngaynhanhang);

	public String getSpId();

	public void setSpId(String spId);

	public String getSpTen();

	public void setSpTen(String spTen);
	
	public String getThongTinBom();

	public void setThongTinBom(String thongtinbom);

	public String getNhapkhoId();

	public void setNhapkhoId(String nkId);

	public String getSolo();

	public void setSolo(String solo);

	public String getSoLuongKiemDinh();

	public void setSoLuongKiemDinh(String soluong);

	public String getsoluongDat();
	public void setsoluongDat(String soluongDat);
	
	public String getsoluongkhongdat();
	public void setsoluongkhongdat(String soluonghong );
	
	public String getsoluongmau();
	public void setsoluongmau(String soluongmau);

	public String getCongtyId();

	public void setCongtyId(String congtyId);

	public String getCongdoanId();

	public void setCongdoanId(String congdoanId);

	public String getLenhsanxuatId();

	public void setLenhsanxuat(String lenhsanxuatId);

	public String getTrangThai();

	public void setTrangThai(String trangthai);

	public String getDatCl();

	public void setDatCl(String datcl);

	public String getDinhluong();

	public void setDinhluong(String dinhluong);

	public String getDinhtinh();

	public void setDinhtinh(String dinhtinh);

	public String getNguoiduyet();

	public void setNguoiduyet(String nguoiduyet);

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

	public void setTieuchikiemdinhList(List<ITieuchikiemdinh> list);

	public List<ITieuchikiemdinh> getTieuchikiemdinhList();

	public String getMsg();

	public void setMsg(String msg);

	public boolean updateKiemdinh(HttpServletRequest request);

	public boolean duyetKiemDinh();

	public boolean HuyKiemDinh();

	public void setRsYeuCauKiemDinh(ResultSet rsYc);
	public ResultSet getRsYeuCauKiemDinh();

	public void setRsDvdl(ResultSet rs);
	public ResultSet getRsDvdl();

	public void setDvdlId(String Dvdlid);
	public ResultSet getDvdlid();
	
	public void setRsKhonhanhangdat(ResultSet rs);
	public ResultSet getRsKhonhanhangdat();

	public void setKhonhanhangdatId(String Khochuyenhangdatid);
	public String getKhonhanhangdatId();
	
	
	public void init();

	public boolean IsKiemDinhCongDoan();
	public void setIsKiemDinhCongDoan(boolean kiemdinhcongdoan);

	public void createRs();
	
	public ResultSet getRsCongDoan();
	public void setRsCongDoan(ResultSet rsCongdoan);

	
	public ResultSet getRsKhoNhan();
	public void setRsKhoNhan(ResultSet rsKhoNhan);
	
	public String getKhoNhanId();
	public void setKhoNhanId(String KhoNhanId);
	
	public ResultSet getRsKhuVucKho();
	public void setRsKhuVuckho(ResultSet rsKhuVucKho);
	
	public String getKhuvuckhoid();
	public void setKhuvuckhoid(String Khuvuckhoid);
	
	public boolean GetIsQuanLyKhuVuc();
	public ResultSet getRsLenhsanxuat();

	public void setRsLenhsanxuat(ResultSet rsLenhsanxuat);
	
	public String getDeNghiXuLy();
	public void setDeNghiXuLy(String denghixuly);

	public void DbClose();

	public void setIsCapDong(String iscapdong);
	public String getIsCapDong();

	public void Hoantat();
	
	public String getKyhieukd();
	public void setKyhieukd(String kyhieukd);

}
