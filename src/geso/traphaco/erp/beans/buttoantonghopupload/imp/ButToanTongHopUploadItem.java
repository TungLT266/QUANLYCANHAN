package geso.traphaco.erp.beans.buttoantonghopupload.imp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Style;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

public class ButToanTongHopUploadItem
{
	private int row;
	private int colunm;
	private String id;
	private String msg;
	private int trangThai;
	private String maChiNhanh;
	private String chiNhanhId;
	private String congTyId;
	private String soChungTu;
	private String ngayChungTu;
	private String dienGiaiChungTu;
	
	private String maNgyenTe;
	private String nguyenTeId;
	private double tiGia;

	private double tongTienTruocThueNguyenTe;
	private double soTien;
	
	private String soHieuTaiKhoanNo;
	private String soHieuTaiKhoanCo;
	private String taiKhoanNoId;
	private String taiKhoanCoId;
	
	private int coKMCPNo;
	private int coKMCPCo;
	
	private String dienGiaiChiTiet;
	
	private String maDoiTuongNo;
	private String tenDoiTuongNo;
	private String doiTuongNoId;
	private String maDoiTuongCo;
	private String tenDoiTuongCo;
	private String doiTuongCoId;
	
	private String maKhoanMucChiPhiNo;
	private String tenKhoanMucChiPhiNo;
	private String khoanMucChiPhiNoId;
	private String maKhoanMucChiPhiCo;
	private String tenKhoanMucChiPhiCo;
	private String khoanMucChiPhiCoId;
	
	//Start thông tin hóa đơn
	private String mauHD;
	private String kyHieuHD;
	private String soHD;
	private String ngayHD;
	private String tenNCCHD;
	private String diaChiHD;
	private String MSTHD;
	private double tienHangVNDHD;
	private double thueSuatHD;
	private double tienThueVNDHD;
	private double thanhTienVNDHD;
	private String ghiChuHD;
	//End thông tin hóa đơn
	
	private String kenhBanHang;
	private String maVuViec;
	private String diaBan;
	private String tinhThanh;
	private String benhVien;
	private String kenhBanHangId;
	private String vuViecId;
	private String diaBanId;
	private String tinhThanhId;
	private String benhVienId;
	
	public ButToanTongHopUploadItem()
	{
		this.row = 0;
		this.colunm = 0;
		this.id = "";
		this.msg = "";
		this.trangThai = 1;
		this.maChiNhanh = "";
		this.chiNhanhId = "";
		this.congTyId = "";
		this.soChungTu = "";
		this.ngayChungTu = "";
		this.dienGiaiChungTu = "";
		
		this.maNgyenTe = "";
		this.nguyenTeId = "";
		this.tiGia = 1;
		
		this.tongTienTruocThueNguyenTe = 0;
		this.soTien = 0;
		
		this.soHieuTaiKhoanNo = "";
		this.soHieuTaiKhoanCo = "";
		this.taiKhoanNoId = "";
		this.taiKhoanCoId = "";
		
		this.setCoKMCPNo(0);
		this.setCoKMCPCo(0);
		
		this.dienGiaiChiTiet = "";
		
		this.maDoiTuongNo = "";
		this.tenDoiTuongNo = "";
		this.doiTuongNoId = "";
		this.maDoiTuongCo = "";
		this.tenDoiTuongCo = "";
		this.doiTuongCoId = "";
		
		this.maKhoanMucChiPhiNo = "";
		this.tenKhoanMucChiPhiNo = "";
		this.khoanMucChiPhiNoId = "";
		this.maKhoanMucChiPhiCo = "";
		this.tenKhoanMucChiPhiCo = "";
		this.khoanMucChiPhiCoId = "";
		
		//Start thông tin hóa đơn
		this.mauHD = "";
		this.kyHieuHD = "";
		this.soHD = "";
		this.ngayHD = "";
		this.tenNCCHD = "";
		this.diaChiHD = "";
		this.MSTHD = "";
		this.tienHangVNDHD = 0;
		this.thueSuatHD = 0;
		this.tienThueVNDHD = 0;
		this.thanhTienVNDHD = 0;
		this.ghiChuHD = "";
		//End thông tin hóa đơn
		
		this.kenhBanHang = "";
		this.maVuViec = "";
		this.diaBan = "";
		this.tinhThanh = "";
		this.benhVien = "";
		this.kenhBanHangId = "";
		this.vuViecId = "";
		this.diaBanId = "";
		this.tinhThanhId = "";
		this.benhVienId = "";
	}
	
	public ButToanTongHopUploadItem(String maChiNhanh, String soChungTu, String ngayChungTu, String dienGiaiChungTu
			, String maNgyenTe, double tiGia, double tongTienTruocThueNguyenTe, double soTien
			, String soHieuTaiKhoanNo, String soHieuTaiKhoanCo, String dienGiaiChiTiet, String maDoiTuongNo
			, String tenDoiTuongNo, String maDoiTuongCo, String tenDoiTuongCo, String maKhoanMucChiPhiNo
			, String tenKhoanMucChiPhiNo, String maKhoanMucChiPhiCo, String tenKhoanMucChiPhiCo, String mauHD
			, String kyHieuHD, String soHD, String ngayHD, String tenNCCHD
			, String diaChiHD, String MSTHD, double tienHangVNDHD, double thueSuatHD
			, double tienThueVNDHD, double thanhTienVNDHD, String ghiChuHD, String kenhBanHang
			, String maVuViec, String diaBan, String tinhThanh, String benhVien) {
		this();
		this.maChiNhanh = maChiNhanh;
		this.soChungTu = soChungTu;
		this.ngayChungTu = ngayChungTu;
		this.dienGiaiChungTu = dienGiaiChungTu;
		
		this.maNgyenTe = maNgyenTe;
		this.tiGia = tiGia;
		this.tongTienTruocThueNguyenTe = tongTienTruocThueNguyenTe;
		this.soTien = soTien;
		
		this.soHieuTaiKhoanNo = soHieuTaiKhoanNo;
		this.soHieuTaiKhoanCo = soHieuTaiKhoanCo;
		this.dienGiaiChiTiet = dienGiaiChiTiet;
		this.maDoiTuongNo = maDoiTuongNo;
		
		this.tenDoiTuongNo = tenDoiTuongNo;
		this.maDoiTuongCo = maDoiTuongCo;
		this.tenDoiTuongCo = tenDoiTuongCo;
		this.maKhoanMucChiPhiNo = maKhoanMucChiPhiNo;
		
		this.tenKhoanMucChiPhiNo = tenKhoanMucChiPhiNo;
		this.maKhoanMucChiPhiCo = maKhoanMucChiPhiCo;
		this.tenKhoanMucChiPhiCo = tenKhoanMucChiPhiCo;
		this.mauHD = mauHD;
		
		this.kyHieuHD = kyHieuHD;
		this.soHD = soHD;
		this.ngayHD = ngayHD;
		this.tenNCCHD = tenNCCHD;
		
		this.diaChiHD = tenNCCHD;
		this.MSTHD = MSTHD;
		this.tienHangVNDHD = tienHangVNDHD;
		this.thueSuatHD = thueSuatHD;
		
		this.tienThueVNDHD = tienThueVNDHD;
		this.thanhTienVNDHD = thanhTienVNDHD;
		this.ghiChuHD = ghiChuHD;
		this.kenhBanHang = kenhBanHang;
		
		this.maVuViec = maVuViec;
		this.diaBan = diaBan;
		this.tinhThanh = tinhThanh;
		this.benhVien = benhVien;
	}
	
	public static void setDisplayForList(List<ButToanTongHopUploadItem> list)
	{
	}

	public static boolean createErrorFile(ServletOutputStream out, String fileName, List<ButToanTongHopUploadItem> list)
	{
		FileInputStream fstream = null;
		try{
			Workbook workbook = new Workbook();
			System.out.println("fileName: " + fileName);
//			fileName = "D:\\A.xlsx";
//			fileName = "D:\\B.xlsm";
			System.out.println("fileName: " + fileName);
			fstream = new FileInputStream(new File(fileName));
			
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007);
			
			Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet(0);
			
			
			worksheet.setName("Sheet1");
			
			Cells cells = worksheet.getCells();
			
			for (int i = 0; i < list.size(); i++) {
				ButToanTongHopUploadItem item = list.get(i);
				if (item.getTrangThai() == 0) {
					int j = 0;
					for (; j < 40; j++) {
						Cell cell = cells.getCell(item.getRow(), j);
						Style style = cell.getStyle();
						style.setPatternStyle((short) 0);
						style.setColor(Color.YELLOW);
						cell.setStyle(style);
					}
					Cell cell = cells.getCell(item.getRow(), j);
					cell.setValue(item.getMsg());
					Style style = cell.getStyle();
					cell.getStyle().setTextWrapped(true);
					cell.setStyle(style);
				}
			}
			
			workbook.save(out);
		}catch(Exception ex){
			ex.printStackTrace();
			return false;
		} finally {
			if (fstream != null) {
				try {
					fstream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return true;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public int getTrangThai() {
		return trangThai;
	}

	public void setTrangThai(int trangThai) {
		this.trangThai = trangThai;
	}

	public String getMaChiNhanh() {
		return maChiNhanh;
	}

	public void setMaChiNhanh(String maChiNhanh) {
		this.maChiNhanh = maChiNhanh;
	}

	public String getChiNhanhId() {
		return chiNhanhId;
	}

	public void setChiNhanhId(String chiNhanhId) {
		this.chiNhanhId = chiNhanhId;
	}

	public String getSoChungTu() {
		return soChungTu;
	}

	public void setSoChungTu(String soChungTu) {
		this.soChungTu = soChungTu;
	}

	public String getNgayChungTu() {
		return ngayChungTu;
	}

	public void setNgayChungTu(String ngayChungTu) {
		this.ngayChungTu = ngayChungTu;
	}

	public String getDienGiaiChungTu() {
		return dienGiaiChungTu;
	}

	public void setDienGiaiChungTu(String dienGiaiChungTu) {
		this.dienGiaiChungTu = dienGiaiChungTu;
	}

	public String getMaNgyenTe() {
		return maNgyenTe;
	}

	public void setMaNgyenTe(String maNgyenTe) {
		this.maNgyenTe = maNgyenTe;
	}

	public String getNguyenTeId() {
		return nguyenTeId;
	}

	public void setNguyenTeId(String nguyenTeId) {
		this.nguyenTeId = nguyenTeId;
	}

	public double getTiGia() {
		return tiGia;
	}

	public void setTiGia(double tiGia) {
		this.tiGia = tiGia;
	}

	public double getTongTienTruocThueNguyenTe() {
		return tongTienTruocThueNguyenTe;
	}

	public void setTongTienTruocThueNguyenTe(double tongTienTruocThueNguyenTe) {
		this.tongTienTruocThueNguyenTe = tongTienTruocThueNguyenTe;
	}

	public double getSoTien() {
		return soTien;
	}

	public void setSoTien(double soTien) {
		this.soTien = soTien;
	}

	public String getSoHieuTaiKhoanNo() {
		return soHieuTaiKhoanNo;
	}

	public void setSoHieuTaiKhoanNo(String soHieuTaiKhoanNo) {
		this.soHieuTaiKhoanNo = soHieuTaiKhoanNo;
	}

	public String getSoHieuTaiKhoanCo() {
		return soHieuTaiKhoanCo;
	}

	public void setSoHieuTaiKhoanCo(String soHieuTaiKhoanCo) {
		this.soHieuTaiKhoanCo = soHieuTaiKhoanCo;
	}

	public String getTaiKhoanNoId() {
		return taiKhoanNoId;
	}

	public void setTaiKhoanNoId(String taiKhoanNoId) {
		this.taiKhoanNoId = taiKhoanNoId;
	}

	public String getTaiKhoanCoId() {
		return taiKhoanCoId;
	}

	public void setTaiKhoanCoId(String taiKhoanCoId) {
		this.taiKhoanCoId = taiKhoanCoId;
	}

	public String getDienGiaiChiTiet() {
		return dienGiaiChiTiet;
	}

	public void setDienGiaiChiTiet(String dienGiaiChiTiet) {
		this.dienGiaiChiTiet = dienGiaiChiTiet;
	}

	public String getMaDoiTuongNo() {
		return maDoiTuongNo;
	}

	public void setMaDoiTuongNo(String maDoiTuongNo) {
		this.maDoiTuongNo = maDoiTuongNo;
	}

	public String getTenDoiTuongNo() {
		return tenDoiTuongNo;
	}

	public void setTenDoiTuongNo(String tenDoiTuongNo) {
		this.tenDoiTuongNo = tenDoiTuongNo;
	}

	public String getDoiTuongNoId() {
		return doiTuongNoId;
	}

	public void setDoiTuongNoId(String doiTuongNoId) {
		this.doiTuongNoId = doiTuongNoId;
	}

	public String getMaDoiTuongCo() {
		return maDoiTuongCo;
	}

	public void setMaDoiTuongCo(String maDoiTuongCo) {
		this.maDoiTuongCo = maDoiTuongCo;
	}

	public String getTenDoiTuongCo() {
		return tenDoiTuongCo;
	}

	public void setTenDoiTuongCo(String tenDoiTuongCo) {
		this.tenDoiTuongCo = tenDoiTuongCo;
	}

	public String getDoiTuongCoId() {
		return doiTuongCoId;
	}

	public void setDoiTuongCoId(String doiTuongCoId) {
		this.doiTuongCoId = doiTuongCoId;
	}

	public String getMaKhoanMucChiPhiNo() {
		return maKhoanMucChiPhiNo;
	}

	public void setMaKhoanMucChiPhiNo(String maKhoanMucChiPhiNo) {
		this.maKhoanMucChiPhiNo = maKhoanMucChiPhiNo;
	}

	public String getTenKhoanMucChiPhiNo() {
		return tenKhoanMucChiPhiNo;
	}

	public void setTenKhoanMucChiPhiNo(String tenKhoanMucChiPhiNo) {
		this.tenKhoanMucChiPhiNo = tenKhoanMucChiPhiNo;
	}

	public String getKhoanMucChiPhiNoId() {
		return khoanMucChiPhiNoId;
	}

	public void setKhoanMucChiPhiNoId(String khoanMucChiPhiNoId) {
		this.khoanMucChiPhiNoId = khoanMucChiPhiNoId;
	}

	public String getMaKhoanMucChiPhiCo() {
		return maKhoanMucChiPhiCo;
	}

	public void setMaKhoanMucChiPhiCo(String maKhoanMucChiPhiCo) {
		this.maKhoanMucChiPhiCo = maKhoanMucChiPhiCo;
	}

	public String getTenKhoanMucChiPhiCo() {
		return tenKhoanMucChiPhiCo;
	}

	public void setTenKhoanMucChiPhiCo(String tenKhoanMucChiPhiCo) {
		this.tenKhoanMucChiPhiCo = tenKhoanMucChiPhiCo;
	}

	public String getKhoanMucChiPhiCoId() {
		return khoanMucChiPhiCoId;
	}

	public void setKhoanMucChiPhiCoId(String khoanMucChiPhiCoId) {
		this.khoanMucChiPhiCoId = khoanMucChiPhiCoId;
	}

	public String getMauHD() {
		return mauHD;
	}

	public void setMauHD(String mauHD) {
		this.mauHD = mauHD;
	}

	public String getKyHieuHD() {
		return kyHieuHD;
	}

	public void setKyHieuHD(String kyHieuHD) {
		this.kyHieuHD = kyHieuHD;
	}

	public String getSoHD() {
		return soHD;
	}

	public void setSoHD(String soHD) {
		this.soHD = soHD;
	}

	public String getNgayHD() {
		return ngayHD;
	}

	public void setNgayHD(String ngayHD) {
		this.ngayHD = ngayHD;
	}

	public String getTenNCCHD() {
		return tenNCCHD;
	}

	public void setTenNCCHD(String tenNCCHD) {
		this.tenNCCHD = tenNCCHD;
	}

	public String getDiaChiHD() {
		return diaChiHD;
	}

	public void setDiaChiHD(String diaChiHD) {
		this.diaChiHD = diaChiHD;
	}

	public String getMSTHD() {
		return MSTHD;
	}

	public void setMSTHD(String mSTHD) {
		MSTHD = mSTHD;
	}

	public double getTienHangVNDHD() {
		return tienHangVNDHD;
	}

	public void setTienHangVNDHD(double tienHangVNDHD) {
		this.tienHangVNDHD = tienHangVNDHD;
	}

	public double getThueSuatHD() {
		return thueSuatHD;
	}

	public void setThueSuatHD(double thueSuatHD) {
		this.thueSuatHD = thueSuatHD;
	}

	public double getTienThueVNDHD() {
		return tienThueVNDHD;
	}

	public void setTienThueVNDHD(double tienThueVNDHD) {
		this.tienThueVNDHD = tienThueVNDHD;
	}

	public double getThanhTienVNDHD() {
		return thanhTienVNDHD;
	}

	public void setThanhTienVNDHD(double thanhTienVNDHD) {
		this.thanhTienVNDHD = thanhTienVNDHD;
	}

	public String getGhiChuHD() {
		return ghiChuHD;
	}

	public void setGhiChuHD(String ghiChuHD) {
		this.ghiChuHD = ghiChuHD;
	}

	public String getKenhBanHang() {
		return kenhBanHang;
	}

	public void setKenhBanHang(String kenhBanHang) {
		this.kenhBanHang = kenhBanHang;
	}

	public String getMaVuViec() {
		return maVuViec;
	}

	public void setMaVuViec(String maVuViec) {
		this.maVuViec = maVuViec;
	}

	public String getDiaBan() {
		return diaBan;
	}

	public void setDiaBan(String diaBan) {
		this.diaBan = diaBan;
	}

	public String getTinhThanh() {
		return tinhThanh;
	}

	public void setTinhThanh(String tinhThanh) {
		this.tinhThanh = tinhThanh;
	}

	public String getBenhVien() {
		return benhVien;
	}

	public void setBenhVien(String benhVien) {
		this.benhVien = benhVien;
	}

	public String getKenhBanHangId() {
		return kenhBanHangId;
	}

	public void setKenhBanHangId(String kenhBanHangId) {
		this.kenhBanHangId = kenhBanHangId;
	}

	public String getVuViecId() {
		return vuViecId;
	}

	public void setVuViecId(String vuViecId) {
		this.vuViecId = vuViecId;
	}

	public String getDiaBanId() {
		return diaBanId;
	}

	public void setDiaBanId(String diaBanId) {
		this.diaBanId = diaBanId;
	}

	public String getTinhThanhId() {
		return tinhThanhId;
	}

	public void setTinhThanhId(String tinhThanhId) {
		this.tinhThanhId = tinhThanhId;
	}

	public String getBenhVienId() {
		return benhVienId;
	}

	public void setBenhVienId(String benhVienId) {
		this.benhVienId = benhVienId;
	}

	public void setCongTyId(String congTyId) {
		this.congTyId = congTyId;
	}

	public String getCongTyId() {
		return congTyId;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getRow() {
		return row;
	}

	public void setColunm(int colunm) {
		this.colunm = colunm;
	}

	public int getColunm() {
		return colunm;
	}

	public void setCoKMCPNo(int coKMCPNo) {
		this.coKMCPNo = coKMCPNo;
	}

	public int getCoKMCPNo() {
		return coKMCPNo;
	}

	public void setCoKMCPCo(int coKMCPCo) {
		this.coKMCPCo = coKMCPCo;
	}

	public int getCoKMCPCo() {
		return coKMCPCo;
	}

}