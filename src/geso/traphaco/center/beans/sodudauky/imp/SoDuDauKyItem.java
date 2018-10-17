package geso.traphaco.center.beans.sodudauky.imp;

import java.util.List;

public class SoDuDauKyItem
{
	private String id;
	private String sheetName;
	private int trangThai;
	private String maChiNhanh;
	private String chiNhanhId;
	private String soChungTu;
	private String ngayChungTu;
	private String maDoiTuongFast;
	private String maDoiTuongErp;
	private String tenDoiTuong;
	private String doiTuongId;
	private String tenLoaiDoiTuong;
	private String loaiDoiTuongId;
	private String isDoiTuongNo;
	private String isNPP;
	
	private String soHieuTaiKhoanNo;
	private String soHieuTaiKhoanCo;
	private String taiKhoanNoId;
	private String taiKhoanCoId;
	private double soTienVND;
	
	private String tenTienTe;
	private String tenTienTeDisplay;
	private String tienTeId;
	private double tiGia;
	private String tiGiaDisplay;
	private double soTienNgoaiTe;
	private String soTienNgoaiTeDisplay;
	
	private String soHieuTaiKhoanNo1;
	private String soHieuTaiKhoanNo1Display;
	private String soHieuTaiKhoanCo1;
	private String soHieuTaiKhoanCo1Display;
	private String taiKhoanNoId1;
	private String taiKhoanCoId1;
	private double soTienVND1;
	private String soTienVND1Display;
	
	private String soHoaDonGoc;
	private String soHoaDonGocDisplay;
	private String ngayHoaDonGoc;
	private String ngayHoaDonGocDisplay;
	private String taiKhoanPhanMemFast;
	private String taiKhoanPhanMemFastDisplay;
	private double soTienHoaDonGoc;
	private String soTienHoaDonGocDisplay;
	
	private String maQuay;
	private String maQuayDisplay;
	private String maChiNhanhDKSD;

	private String maChiNhanhDLN;
	
	private String chiNhanhDLN_FK;
	private String chiNhanhDKSD_FK;
	
	private String quayId;

	private String BTTH_Id;
	private String BTTH_CT_Id;

	private String msg;
	private int isTaoMoiDoiTuong;
	
	public SoDuDauKyItem()
	{
		this.id = "";
		this.sheetName = "";
		this.trangThai = 0;
		this.maChiNhanh = "";
		this.chiNhanhId = "";
		this.soChungTu = "";
		this.ngayChungTu = "";
		////////////
		this.maDoiTuongFast = "";
		this.maDoiTuongErp = "";
		this.tenDoiTuong = "";
		this.doiTuongId = "";
		this.tenLoaiDoiTuong = "";
		this.loaiDoiTuongId = "";
		this.isDoiTuongNo = "1";
		this.isNPP = "0";
		this.maChiNhanhDKSD ="";
		this.maChiNhanhDLN ="";
		this.soHieuTaiKhoanNo = "";
		this.soHieuTaiKhoanCo = "";
		this.taiKhoanNoId = "";
		this.taiKhoanCoId = "";
		this.soTienVND = 0;
		
		this.tenTienTe = "";
		this.tienTeId = "";
		this.tiGia = 1;
		this.soTienNgoaiTe = 0;
		
		this.soHieuTaiKhoanNo1 = "";
		this.soHieuTaiKhoanCo1 = "";
		this.taiKhoanNoId1 = "";
		this.taiKhoanCoId1 = "";
		this.soTienVND1 = 0;
		
		this.soHoaDonGoc = "";
		this.ngayHoaDonGoc = "";
		this.taiKhoanPhanMemFast = "";
		this.setSoTienHoaDonGoc(0);
		
		this.maQuay = "";
		this.setMaQuayDisplay("");
		this.quayId = "";
		
		this.BTTH_Id = "";
		this.BTTH_CT_Id = "";
		this.msg = "";
		this.isTaoMoiDoiTuong = 0;
		
		this.tenTienTeDisplay = "normal";
		this.tiGiaDisplay = "normal";
		this.soTienNgoaiTeDisplay = "normal";
		
		this.soHieuTaiKhoanNo1Display = "normal";
		this.soHieuTaiKhoanCo1Display = "normal";
		this.soTienVND1Display = "normal";
		
		this.soHoaDonGocDisplay = "normal";
		this.ngayHoaDonGocDisplay = "normal";
		this.taiKhoanPhanMemFastDisplay = "normal";
		this.soTienHoaDonGocDisplay = "normal";
		this.chiNhanhDKSD_FK ="";
		this.chiNhanhDLN_FK= "";
	}
	
	public SoDuDauKyItem(String soChungTu, String ngayChungTu
			, String maDoiTuongFast, String maDoiTuongErp, String tenDoiTuong, String tenLoaiDoiTuong
			, String isDoiTuongNo, String isNPP, String soHieuTaiKhoanNo, String soHieuTaiKhoanCo
			, double soTienVND, String tenTienTe, double tiGia, double soTienNgoaiTe
			, String soHieuTaiKhoanNo1, String soHieuTaiKhoanCo1, double soTienVND1, String soHoaDonGoc
			, String ngayHoaDonGoc, String taiKhoanPhanMemFast,String maChiNhanhDKSD,String maChiNhanhDLN)
	{
		this();
		this.soChungTu = soChungTu;
		this.ngayChungTu = ngayChungTu;
		this.maDoiTuongFast = maDoiTuongFast;
		this.maDoiTuongErp = maDoiTuongErp;
		this.tenDoiTuong = tenDoiTuong;
		this.tenLoaiDoiTuong = tenLoaiDoiTuong;
		this.isDoiTuongNo = isDoiTuongNo;
		this.isNPP = isNPP;
		this.soHieuTaiKhoanNo = soHieuTaiKhoanNo;
		this.soHieuTaiKhoanCo = soHieuTaiKhoanCo;
		this.soTienVND = soTienVND;
		this.tenTienTe = tenTienTe;
		this.tiGia = tiGia;
		this.soTienNgoaiTe = soTienNgoaiTe;
		this.soHieuTaiKhoanNo1 = soHieuTaiKhoanNo1;
		this.soHieuTaiKhoanCo1 = soHieuTaiKhoanCo1;
		this.soTienVND1 = soTienVND1;
		this.soHoaDonGoc = soHoaDonGoc;
		this.ngayHoaDonGoc = ngayHoaDonGoc;
		this.taiKhoanPhanMemFast = taiKhoanPhanMemFast;
		this.maChiNhanhDKSD = maChiNhanhDKSD;
		this.maChiNhanhDLN = maChiNhanhDLN;
	}
	
	public static void setDisplayForList(List<SoDuDauKyItem> list)
	{
		for (SoDuDauKyItem item : list)
		{
			String sheetName = item.getSheetName();
			if (sheetName.contains("TSCD"))
			{
				item.setTenTienTeDisplay("normal");
				item.setTiGiaDisplay("normal");
				item.setSoTienNgoaiTeDisplay("normal");
				
				item.setSoHieuTaiKhoanNo1Display("normal");
				item.setSoHieuTaiKhoanCo1Display("normal");
				item.setSoTienVND1Display("normal");
				
				item.setSoHoaDonGocDisplay("none");
				item.setNgayHoaDonGocDisplay("none");
				item.setTaiKhoanPhanMemFastDisplay("none");
				item.setSoTienHoaDonGocDisplay("none");
				
				item.setMaQuayDisplay("none");
			}else if (sheetName.contains("XDCB"))
			{
				item.setTenTienTeDisplay("none");
				item.setTiGiaDisplay("none");
				item.setSoTienNgoaiTeDisplay("none");
				
				item.setSoHieuTaiKhoanNo1Display("none");
				item.setSoHieuTaiKhoanCo1Display("none");
				item.setSoTienVND1Display("none");
				
				item.setSoHoaDonGocDisplay("none");
				item.setNgayHoaDonGocDisplay("none");
				item.setTaiKhoanPhanMemFastDisplay("none");
				item.setSoTienHoaDonGocDisplay("none");
				
				item.setMaQuayDisplay("none");
			}else if (sheetName.contains("CPTT"))
			{
				item.setTenTienTeDisplay("normal");
				item.setTiGiaDisplay("normal");
				item.setSoTienNgoaiTeDisplay("normal");
				
				item.setSoHieuTaiKhoanNo1Display("none");
				item.setSoHieuTaiKhoanCo1Display("none");
				item.setSoTienVND1Display("none");
				
				item.setSoHoaDonGocDisplay("none");
				item.setNgayHoaDonGocDisplay("none");
				item.setTaiKhoanPhanMemFastDisplay("none");
				item.setSoTienHoaDonGocDisplay("none");
				
				item.setMaQuayDisplay("none");
			}else if (sheetName.contains("TKCT") || sheetName.contains("KH Quay"))
			{
				item.setTenTienTeDisplay("normal");
				item.setTiGiaDisplay("normal");
				item.setSoTienNgoaiTeDisplay("normal");
				
				item.setSoHieuTaiKhoanNo1Display("none");
				item.setSoHieuTaiKhoanCo1Display("none");
				item.setSoTienVND1Display("none");
				
				item.setSoHoaDonGocDisplay("normal");
				item.setNgayHoaDonGocDisplay("normal");
				item.setTaiKhoanPhanMemFastDisplay("normal");
				item.setSoTienHoaDonGocDisplay("normal");
				
				if (sheetName.contains("KH Quay"))
					item.setMaQuayDisplay("normal");
				else item.setMaQuayDisplay("none");
			}
		}
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

	public String getMaDoiTuongFast() {
		return maDoiTuongFast;
	}

	public void setMaDoiTuongFast(String maDoiTuongFast) {
		this.maDoiTuongFast = maDoiTuongFast;
	}

	public String getMaDoiTuongErp() {
		return maDoiTuongErp;
	}

	public void setMaDoiTuongErp(String maDoiTuongErp) {
		this.maDoiTuongErp = maDoiTuongErp;
	}

	public String getTenDoiTuong() {
		return tenDoiTuong;
	}

	public void setTenDoiTuong(String tenDoiTuong) {
		this.tenDoiTuong = tenDoiTuong;
	}

	public String getDoiTuongId() {
		return doiTuongId;
	}

	public void setDoiTuongId(String doiTuongId) {
		this.doiTuongId = doiTuongId;
	}

	public String getTenLoaiDoiTuong() {
		return tenLoaiDoiTuong;
	}

	public void setTenLoaiDoiTuong(String tenLoaiDoiTuong) {
		this.tenLoaiDoiTuong = tenLoaiDoiTuong;
	}

	public String getLoaiDoiTuongId() {
		return loaiDoiTuongId;
	}

	public void setLoaiDoiTuongId(String loaiDoiTuongId) {
		this.loaiDoiTuongId = loaiDoiTuongId;
	}

	public String getIsDoiTuongNo() {
		return isDoiTuongNo;
	}

	public void setIsDoiTuongNo(String isDoiTuongNo) {
		this.isDoiTuongNo = isDoiTuongNo;
	}

	public String getIsNPP() {
		return isNPP;
	}

	public void setIsNPP(String isNPP) {
		this.isNPP = isNPP;
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

	public double getSoTienVND() {
		return soTienVND;
	}

	public void setSoTienVND(double soTienVND) {
		this.soTienVND = soTienVND;
	}

	public String getTenTienTe() {
		return tenTienTe;
	}

	public void setTenTienTe(String tenTienTe) {
		this.tenTienTe = tenTienTe;
	}

	public String getTienTeId() {
		return tienTeId;
	}

	public void setTienTeId(String tienTeId) {
		this.tienTeId = tienTeId;
	}

	public double getTiGia() {
		return tiGia;
	}

	public void setTiGia(double tiGia) {
		this.tiGia = tiGia;
	}

	public double getSoTienNgoaiTe() {
		return soTienNgoaiTe;
	}

	public void setSoTienNgoaiTe(double soTienNgoaiTe) {
		this.soTienNgoaiTe = soTienNgoaiTe;
	}

	public String getSoHieuTaiKhoanNo1() {
		return soHieuTaiKhoanNo1;
	}

	public void setSoHieuTaiKhoanNo1(String soHieuTaiKhoanNo1) {
		this.soHieuTaiKhoanNo1 = soHieuTaiKhoanNo1;
	}

	public String getSoHieuTaiKhoanCo1() {
		return soHieuTaiKhoanCo1;
	}

	public void setSoHieuTaiKhoanCo1(String soHieuTaiKhoanCo1) {
		this.soHieuTaiKhoanCo1 = soHieuTaiKhoanCo1;
	}

	public String getTaiKhoanNoId1() {
		return taiKhoanNoId1;
	}

	public void setTaiKhoanNoId1(String taiKhoanNoId1) {
		this.taiKhoanNoId1 = taiKhoanNoId1;
	}

	public String getTaiKhoanCoId1() {
		return taiKhoanCoId1;
	}

	public void setTaiKhoanCoId1(String taiKhoanCoId1) {
		this.taiKhoanCoId1 = taiKhoanCoId1;
	}

	public double getSoTienVND1() {
		return soTienVND1;
	}

	public void setSoTienVND1(double soTienVND1) {
		this.soTienVND1 = soTienVND1;
	}

	public String getSoHoaDonGoc() {
		return soHoaDonGoc;
	}

	public void setSoHoaDonGoc(String soHoaDonGoc) {
		this.soHoaDonGoc = soHoaDonGoc;
	}

	public String getNgayHoaDonGoc() {
		return ngayHoaDonGoc;
	}

	public void setNgayHoaDonGoc(String ngayHoaDonGoc) {
		this.ngayHoaDonGoc = ngayHoaDonGoc;
	}

	public String getTaiKhoanPhanMemFast() {
		return taiKhoanPhanMemFast;
	}

	public void setTaiKhoanPhanMemFast(String taiKhoanPhanMemFast) {
		this.taiKhoanPhanMemFast = taiKhoanPhanMemFast;
	}

	public String getBTTH_Id() {
		return BTTH_Id;
	}

	public void setBTTH_Id(String bTTH_Id) {
		BTTH_Id = bTTH_Id;
	}

	public String getBTTH_CT_Id() {
		return BTTH_CT_Id;
	}

	public void setBTTH_CT_Id(String bTTH_CT_Id) {
		BTTH_CT_Id = bTTH_CT_Id;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	public String getSheetName() {
		return sheetName;
	}

	public void setTrangThai(int trangThai) {
		this.trangThai = trangThai;
	}

	public int getTrangThai() {
		return trangThai;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}

	public void setIsTaoMoiDoiTuong(int isTaoMoiDoiTuong) {
		this.isTaoMoiDoiTuong = isTaoMoiDoiTuong;
	}

	public int getIsTaoMoiDoiTuong() {
		return isTaoMoiDoiTuong;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setSoTienHoaDonGoc(double soTienHoaDonGoc) {
		this.soTienHoaDonGoc = soTienHoaDonGoc;
	}

	public double getSoTienHoaDonGoc() {
		return soTienHoaDonGoc;
	}

	public String getTenTienTeDisplay() {
		return tenTienTeDisplay;
	}

	public void setTenTienTeDisplay(String tenTienTeDisplay) {
		this.tenTienTeDisplay = tenTienTeDisplay;
	}

	public String getTiGiaDisplay() {
		return tiGiaDisplay;
	}

	public void setTiGiaDisplay(String tiGiaDisplay) {
		this.tiGiaDisplay = tiGiaDisplay;
	}

	public String getSoTienNgoaiTeDisplay() {
		return soTienNgoaiTeDisplay;
	}

	public void setSoTienNgoaiTeDisplay(String soTienNgoaiTeDisplay) {
		this.soTienNgoaiTeDisplay = soTienNgoaiTeDisplay;
	}

	public String getSoHieuTaiKhoanNo1Display() {
		return soHieuTaiKhoanNo1Display;
	}

	public void setSoHieuTaiKhoanNo1Display(String soHieuTaiKhoanNo1Display) {
		this.soHieuTaiKhoanNo1Display = soHieuTaiKhoanNo1Display;
	}

	public String getSoHieuTaiKhoanCo1Display() {
		return soHieuTaiKhoanCo1Display;
	}

	public void setSoHieuTaiKhoanCo1Display(String soHieuTaiKhoanCo1Display) {
		this.soHieuTaiKhoanCo1Display = soHieuTaiKhoanCo1Display;
	}

	public String getSoTienVND1Display() {
		return soTienVND1Display;
	}

	public void setSoTienVND1Display(String soTienVND1Display) {
		this.soTienVND1Display = soTienVND1Display;
	}

	public String getSoHoaDonGocDisplay() {
		return soHoaDonGocDisplay;
	}

	public void setSoHoaDonGocDisplay(String soHoaDonGocDisplay) {
		this.soHoaDonGocDisplay = soHoaDonGocDisplay;
	}

	public String getNgayHoaDonGocDisplay() {
		return ngayHoaDonGocDisplay;
	}

	public void setNgayHoaDonGocDisplay(String ngayHoaDonGocDisplay) {
		this.ngayHoaDonGocDisplay = ngayHoaDonGocDisplay;
	}

	public String getTaiKhoanPhanMemFastDisplay() {
		return taiKhoanPhanMemFastDisplay;
	}

	public void setTaiKhoanPhanMemFastDisplay(String taiKhoanPhanMemFastDisplay) {
		this.taiKhoanPhanMemFastDisplay = taiKhoanPhanMemFastDisplay;
	}

	public String getSoTienHoaDonGocDisplay() {
		return soTienHoaDonGocDisplay;
	}

	public void setSoTienHoaDonGocDisplay(String soTienHoaDonGocDisplay) {
		this.soTienHoaDonGocDisplay = soTienHoaDonGocDisplay;
	}

	public void setMaQuay(String maQuay) {
		this.maQuay = maQuay;
	}

	public String getMaQuay() {
		return maQuay;
	}

	public void setQuayId(String quayId) {
		this.quayId = quayId;
	}

	public String getQuayId() {
		return quayId;
	}

	public void setMaQuayDisplay(String maQuayDisplay) {
		this.maQuayDisplay = maQuayDisplay;
	}

	public String getMaQuayDisplay() {
		return maQuayDisplay;
	}

	public String getMaChiNhanhDKSD() {
		return maChiNhanhDKSD;
	}

	public void setMaChiNhanhDKSD(String maChiNhanhDKSD) {
		this.maChiNhanhDKSD = maChiNhanhDKSD;
	}

	public String getMaChiNhanhDLN() {
		return maChiNhanhDLN;
	}

	public void setMaChiNhanhDLN(String maChiNhanhDLN) {
		this.maChiNhanhDLN = maChiNhanhDLN;
	}

	public String getChiNhanhDLN_FK() {
		return chiNhanhDLN_FK;
	}

	public void setChiNhanhDLN_FK(String chiNhanhDLN_FK) {
		this.chiNhanhDLN_FK = chiNhanhDLN_FK;
	}

	public String getChiNhanhDKSD_FK() {
		return chiNhanhDKSD_FK;
	}

	public void setChiNhanhDKSD_FK(String chiNhanhDKSD_FK) {
		this.chiNhanhDKSD_FK = chiNhanhDKSD_FK;
	}
}