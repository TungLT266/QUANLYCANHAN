package geso.traphaco.erp.beans.bangkechungtu.imp;

import geso.traphaco.center.db.sql.Idbutils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Erp_BangKeChungTuListItem{
	private int stt;
	private String maChiNhanhNo;
	private String maChiNhanhCo;
	private String soChungTu;
	private String loaiChungTu;
	private String taiKhoanNo;
	private String taiKhoanCo;
	private double soTien;
	private double tiGia;
	private String tienTeGoc;
	private double tienNgoaiTe;
	private String doiTuongNo;
	private String doiTuongCo;
	private String tenDoiTuongNo;
	private String tenDoiTuongCo;
	private String ngayGhiNhan;
	
	public Erp_BangKeChungTuListItem()
	{
		this.stt = 1;
		this.maChiNhanhNo = "";
		this.maChiNhanhCo = "";
		this.soChungTu = "";
		this.loaiChungTu = "";
		this.taiKhoanNo = "";
		this.taiKhoanCo = "";
		this.soTien = 0;
		this.tiGia = 0;
		this.tienTeGoc = "";
		this.tienNgoaiTe = 0;
		this.doiTuongNo = "";
		this.doiTuongCo = "";
		this.tenDoiTuongNo = "";
		this.tenDoiTuongCo = "";
		this.ngayGhiNhan = "";
	}
	
	public Erp_BangKeChungTuListItem(int stt, String maChiNhanhNo, String maChiNhanhCo, String soChungTu
			, String loaiChungTu, String taiKhoanNo, String taiKhoanCo, double soTien
			, double tiGia, String tienTeGoc, double tienNgoaiTe, String doiTuongNo
			, String doiTuongCo, String tenDoiTuongNo, String tenDoiTuongCo, String ngayGhiNhan)
	{
		this.stt = stt;
		this.maChiNhanhNo = maChiNhanhNo;
		this.maChiNhanhCo = maChiNhanhCo;
		this.soChungTu = soChungTu;
		this.loaiChungTu = loaiChungTu;
		this.taiKhoanNo = taiKhoanNo;
		this.taiKhoanCo = taiKhoanCo;
		this.soTien = soTien;
		this.tiGia = tiGia;
		this.tienTeGoc = tienTeGoc;
		this.tienNgoaiTe = tienNgoaiTe;
		this.doiTuongNo = doiTuongNo;
		this.doiTuongCo = doiTuongCo;
		this.tenDoiTuongNo = tenDoiTuongNo;
		this.tenDoiTuongCo = tenDoiTuongCo;
		this.ngayGhiNhan = ngayGhiNhan;
	}
	
	public static String getListFromQuery(Idbutils db, String query, List<Erp_BangKeChungTuListItem> list)
	{
		String msg = "";
		
		if (list == null)
			list = new ArrayList<Erp_BangKeChungTuListItem>();
		else
			list.clear();	

		ResultSet rs = null;
		
		try {
			rs = db.get(query);
			int stt = 1;
			if (rs != null)
				while (rs.next())
				{
					String maChiNhanhNo = rs.getString("MACHINHANH_NO");
					String maChiNhanhCo = rs.getString("MACHINHANH_CO");
					String soChungTu = rs.getString("sochungtu");
					String loaiChungTu = rs.getString("loaichungtu");
					String taiKhoanNo = rs.getString("tk_no");
					String taiKhoanCo = rs.getString("tk_co");
					double soTien = rs.getDouble("tien");
					double tiGia = rs.getDouble("TIGIA_FK");
					String tienTeGoc = rs.getString("tientegoc_fk");
					double tienNgoaiTe = rs.getDouble("tien_nt");
					String doiTuongNo = rs.getString("doituong_no");
					String doiTuongCo = rs.getString("TENDOITUONG_NO");
					String tenDoiTuongNo = rs.getString("doituong_co");
					String tenDoiTuongCo = rs.getString("TENDOITUONG_CO");
					String ngayGhiNhan = rs.getString("ngayGhiNhan"); 
					Erp_BangKeChungTuListItem item = new Erp_BangKeChungTuListItem
					(stt, maChiNhanhNo, maChiNhanhCo, soChungTu
							, loaiChungTu, taiKhoanNo, taiKhoanCo, soTien
							, tiGia, tienTeGoc, tienNgoaiTe, doiTuongNo
							, doiTuongCo, tenDoiTuongNo, tenDoiTuongCo, ngayGhiNhan);
					list.add(item);
					stt++;
				}
		} catch (Exception e) {
			e.printStackTrace();
			msg = "GLFQ1.0 Lỗi đổ list";
		}
		finally{
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		
		return msg;
	}

	public static String getListFromRs(Idbutils db, ResultSet rs, List<Erp_BangKeChungTuListItem> list)
	{
		String msg = "";
		
		if (list == null)
			list = new ArrayList<Erp_BangKeChungTuListItem>();
		else
			list.clear();	

		try {
			int stt = 1;
			if (rs != null)
				while (rs.next())
				{
					String maChiNhanhNo = rs.getString("MACHINHANH_NO");
					String maChiNhanhCo = rs.getString("MACHINHANH_CO");
					String soChungTu = rs.getString("sochungtu");
					String loaiChungTu = rs.getString("loaichungtu");
					String taiKhoanNo = rs.getString("tk_no");
					String taiKhoanCo = rs.getString("tk_co");
					double soTien = rs.getDouble("tien");
					double tiGia = rs.getDouble("TIGIA_FK");
					String tienTeGoc = rs.getString("tientegoc_fk");
					double tienNgoaiTe = rs.getDouble("tien_nt");
					String doiTuongNo = rs.getString("doituong_no");
					String doiTuongCo = rs.getString("doituong_co");
					String tenDoiTuongNo = rs.getString("TENDOITUONG_NO");
					String tenDoiTuongCo = rs.getString("TENDOITUONG_CO");
					String ngayGhiNhan = rs.getString("ngayGhiNhan"); 
					Erp_BangKeChungTuListItem item = new Erp_BangKeChungTuListItem
					(stt, maChiNhanhNo, maChiNhanhCo, soChungTu
							, loaiChungTu, taiKhoanNo, taiKhoanCo, soTien
							, tiGia, tienTeGoc, tienNgoaiTe, doiTuongNo
							, doiTuongCo, tenDoiTuongNo, tenDoiTuongCo, ngayGhiNhan);
					list.add(item);
					stt++;
				}
		} catch (Exception e) {
			e.printStackTrace();
			msg = "GLFQ1.0 Lỗi đổ list";
		}
		finally{
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		
		return msg;
	}



	
	public int getStt() {
		return stt;
	}
	public void setStt(int stt) {
		this.stt = stt;
	}
	public String getMaChiNhanhNo() {
		return maChiNhanhNo;
	}
	public void setMaChiNhanhNo(String maChiNhanhNo) {
		this.maChiNhanhNo = maChiNhanhNo;
	}
	public String getMaChiNhanhCo() {
		return maChiNhanhCo;
	}
	public void setMaChiNhanhCo(String maChiNhanhCo) {
		this.maChiNhanhCo = maChiNhanhCo;
	}
	public String getSoChungTu() {
		return soChungTu;
	}
	public void setSoChungTu(String soChungTu) {
		this.soChungTu = soChungTu;
	}
	public String getLoaiChungTu() {
		return loaiChungTu;
	}
	public void setLoaiChungTu(String loaiChungTu) {
		this.loaiChungTu = loaiChungTu;
	}
	public String getTaiKhoanNo() {
		return taiKhoanNo;
	}
	public void setTaiKhoanNo(String taiKhoanNo) {
		this.taiKhoanNo = taiKhoanNo;
	}
	public String getTaiKhoanCo() {
		return taiKhoanCo;
	}
	public void setTaiKhoanCo(String taiKhoanCo) {
		this.taiKhoanCo = taiKhoanCo;
	}
	public double getSoTien() {
		return soTien;
	}
	public void setSoTien(double soTien) {
		this.soTien = soTien;
	}
	public double getTiGia() {
		return tiGia;
	}
	public void setTiGia(double tiGia) {
		this.tiGia = tiGia;
	}
	public String getTienTeGoc() {
		return tienTeGoc;
	}
	public void setTienTeGoc(String tienTeGoc) {
		this.tienTeGoc = tienTeGoc;
	}
	public double getTienNgoaiTe() {
		return tienNgoaiTe;
	}
	public void setTienNgoaiTe(double tienNgoaiTe) {
		this.tienNgoaiTe = tienNgoaiTe;
	}
	public String getDoiTuongNo() {
		return doiTuongNo;
	}
	public void setDoiTuongNo(String doiTuongNo) {
		this.doiTuongNo = doiTuongNo;
	}
	public String getDoiTuongCo() {
		return doiTuongCo;
	}
	public void setDoiTuongCo(String doiTuongCo) {
		this.doiTuongCo = doiTuongCo;
	}
	public String getTenDoiTuongNo() {
		return tenDoiTuongNo;
	}
	public void setTenDoiTuongNo(String tenDoiTuongNo) {
		this.tenDoiTuongNo = tenDoiTuongNo;
	}
	public String getTenDoiTuongCo() {
		return tenDoiTuongCo;
	}
	public void setTenDoiTuongCo(String tenDoiTuongCo) {
		this.tenDoiTuongCo = tenDoiTuongCo;
	}

	public void setNgayGhiNhan(String ngayGhiNhan) {
		this.ngayGhiNhan = ngayGhiNhan;
	}

	public String getNgayGhiNhan() {
		return ngayGhiNhan;
	}
}
