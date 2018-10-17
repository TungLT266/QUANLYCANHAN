package geso.traphaco.center.beans.sodudauky.imp;

import geso.traphaco.center.util.Utility;
import geso.traphaco.center.util.UtilityKeToan;
import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.erp.util.LibraryKS;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class SoDuDauKy
{
	private String id;
	private String userId;
	private String nppId;
	private String congTyId;
	private String maChiNhanh;
	private String chiNhanhId;
	private String dienGiai;
	private String trangThai;
	
	private String msg;

	private List<SoDuDauKyItem> itemList;
	private List<String> sheetNames;
	private dbutils db;

	public SoDuDauKy()
	{
		this.id = "";
		this.userId = "";
		this.nppId = "1";
		this.congTyId = "100000";
		this.maChiNhanh = "";
		this.chiNhanhId = "1";
		this.dienGiai = "";
		this.trangThai = "0";
		
		this.msg = "";

		this.setItemList(new ArrayList<SoDuDauKyItem>());
		this.sheetNames = new ArrayList<String>();
		this.db = new dbutils();
	}
	
	public void init()
	{
		if (this.id != null && this.id.trim().length() > 0)
		{
			String query = 
				"select dienGiai, trangThai, congTy_fk, npp_fk\n" +
				", ngayTao, nguoiTao, ngaySua, nguoiSua\n" +
				"from ERP_CHUNGTUDAUKY\n" +
				"where PK_SEQ = " + this.id;
			
			ResultSet rs = this.db.get(query);
			try {
				if (rs!= null)
				{
					while (rs.next())
					{
						this.dienGiai = rs.getString("dienGiai");
						this.trangThai = rs.getString("trangThai");
						this.congTyId = rs.getString("congTy_fk");
						this.chiNhanhId = rs.getString("npp_fk");
					}
					rs.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			query = 
				"select pk_seq, trangThai, chungTuDauKy_fk, soChungTu, ngayChungTu \n" +
				", maDoiTuong_Fast,maDoiTuong_Erp, tenDoiTuong, doiTuong_fk \n" +
				", tenLoaiDoiTuong, loaiDoiTuong_fk, isDoiTuongNo,soHieuTaiKhoanNo, taiKhoanNo_fk \n" +
				", soHieuTaiKhoanCo, taiKhoanCo_fk, soTienVND, tenTienTe, tienTe_fk \n" +
				", tiGia,soTienNgoaiTe, soHieuTaiKhoanNo1, taiKhoanNo1_fk \n" +
				", soHieuTaiKhoanCo1, taiKhoanCo1_fk, soTienVND1, soHoaDonGoc \n" +
				", ngayHoaDonGoc, taiKhoanPhanMemFast, soTienHoaDonGoc, BTTH_FK,BTTH_CT_FK \n" +
				", sheetName, isNPP, maQuay, quay_fk,maChiNhanhDKSD,chiNhanhDKSD_FK, maChiNhanhDLN,chiNhanhDLN_FK \n" +
				"from ERP_CHUNGTUDAUKY_CHITIET\n" +
				"where chungTuDauKy_fk = " + this.id;
			
			rs = this.db.get(query);
			try {
				if (rs!= null)
				{
					this.itemList.clear();
					this.sheetNames.clear();
					////////////
					while (rs.next())
					{
						String sheetName = rs.getString("sheetName");
						if (!this.sheetNames.contains(sheetName))
							this.sheetNames.add(sheetName);
						
						String quayId = rs.getString("quay_fk");
						String maQuay = rs.getString("maQuay");
						String id = rs.getString("pk_seq");
						int trangThai = rs.getInt("trangThai");
						String soChungTu = rs.getString("soChungTu"); 
						String ngayChungTu = rs.getString("ngayChungTu");
						String maDoiTuongFast = rs.getString("maDoiTuong_Fast");
						String maDoiTuongErp = rs.getString("maDoiTuong_Erp"); 
						String tenDoiTuong = rs.getString("tenDoiTuong"); 
						String doiTuongId = rs.getString("doiTuong_fk");
						String tenLoaiDoiTuong = rs.getString("tenLoaiDoiTuong"); 
						String loaiDoiTuongId = rs.getString("loaiDoiTuong_fk"); 
						String isDoiTuongNo = rs.getString("isDoiTuongNo");
						String soHieuTaiKhoanNo = rs.getString("soHieuTaiKhoanNo"); 
						String taiKhoanNoId = rs.getString("taiKhoanNo_fk");
						String soHieuTaiKhoanCo = rs.getString("soHieuTaiKhoanCo"); 
						String taiKhoanCoId = rs.getString("taiKhoanCo_fk"); 
						double soTienVND = rs.getDouble("soTienVND");
						String tenTienTe = rs.getString("tenTienTe"); 
						String tienTeId = rs.getString("tienTe_fk");
						double tiGia = rs.getDouble("tiGia");
						double soTienNgoaiTe = rs.getDouble("soTienNgoaiTe");
						String soHieuTaiKhoanNo1 = rs.getString("soHieuTaiKhoanNo1"); 
						String taiKhoanNoId1 = rs.getString("taiKhoanNo1_fk");
						String soHieuTaiKhoanCo1 = rs.getString("soHieuTaiKhoanCo1"); 
						String taiKhoanCoId1 = rs.getString("taiKhoanCo1_fk"); 
						double soTienVND1 = rs.getDouble("soTienVND1");
						String soHoaDonGoc = rs.getString("soHoaDonGoc");
						String ngayHoaDonGoc = rs.getString("ngayHoaDonGoc"); 
						String taiKhoanPhanMemFast = rs.getString("taiKhoanPhanMemFast"); 
						double soTienHoaDonGoc = rs.getDouble("soTienHoaDonGoc");
						String isNPP = rs.getString("isNPP");
						String maChiNhanhDKSD = rs.getString("maChiNhanhDKSD");
						String chiNhanhDKSD_FK = rs.getString("chiNhanhDKSD_FK");		
						String maChiNhanhDLN = rs.getString("maChiNhanhDLN");
						String chiNhanhDLN_FK = rs.getString("chiNhanhDLN_FK");
						String BTTH_ID = rs.getString("BTTH_FK");
						String BTTH_CT_ID = rs.getString("BTTH_CT_FK");
						
						SoDuDauKyItem item = new SoDuDauKyItem( soChungTu, ngayChungTu, maDoiTuongFast
								, maDoiTuongErp, tenDoiTuong, tenLoaiDoiTuong, isDoiTuongNo
								, "0", soHieuTaiKhoanNo, soHieuTaiKhoanCo, soTienVND
								, tenTienTe, tiGia, soTienNgoaiTe, soHieuTaiKhoanNo1
								, soHieuTaiKhoanCo1, soTienVND1, soHoaDonGoc, ngayHoaDonGoc
								, taiKhoanPhanMemFast,maChiNhanhDKSD,maChiNhanhDLN);
						item.setId(id);
						item.setTrangThai(trangThai);
						item.setDoiTuongId(doiTuongId);
						item.setLoaiDoiTuongId(loaiDoiTuongId);
						item.setTaiKhoanNoId(taiKhoanNoId);
						item.setTaiKhoanCoId(taiKhoanCoId);
						item.setTienTeId(tienTeId);
						item.setTaiKhoanCoId1(taiKhoanCoId1);
						item.setTaiKhoanNoId1(taiKhoanNoId1);
						item.setSoHieuTaiKhoanNo1(soHieuTaiKhoanNo1);
						item.setSoHieuTaiKhoanCo1(soHieuTaiKhoanCo1);
						item.setChiNhanhDKSD_FK(chiNhanhDKSD_FK);
						item.setChiNhanhDLN_FK(chiNhanhDLN_FK);
						
						item.setSoTienHoaDonGoc(soTienHoaDonGoc);
						
						item.setBTTH_Id(BTTH_ID);
						item.setBTTH_Id(BTTH_CT_ID);
						item.setSheetName(sheetName);
						item.setIsNPP(isNPP);
						item.setQuayId(quayId);
						item.setMaQuay(maQuay);
						this.itemList.add(item);
					}
					rs.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		SoDuDauKyItem.setDisplayForList(this.itemList);
		initList();
	}
	
	public void readExcelFile(String fileName)
	{
		try {
			this.readXLSXFile(fileName);
			this.kiemTraDuLieu();
		} catch (IOException e) {
			e.printStackTrace();
			if (this.msg.trim().length() <= 0)
				this.msg = "REF1.1 Lỗi đọc file";
		}
	}
	
	public boolean create()
	{
		try {
			this.db.getConnection().setAutoCommit(false);
			int stt = 1;
			
			String query = 
				"insert into ERP_CHUNGTUDAUKY(dienGiai, trangThai, congTy_fk, npp_fk--\n"+ 
				", ngayTao, nguoiTao, ngaySua, nguoiSua)--\n"+ 
				"values(N'" + this.getDienGiai() + "', 0, " + this.getCongTyId() + ", " + this.getChiNhanhId() + "--\n"+ 
				", '" + Utility.getCurrentDate() + "', " + this.getUserId() + ", '" + Utility.getCurrentDate() + "', " + this.getUserId() + ")--\n";
			query = UtilityKeToan.refactorQuery(query);
			System.out.println("query sddk:\n" + query + "\n------------------------------------");
			if (!this.db.update(query))
			{
				this.msg = "C1.1 Không tạo được";
				this.db.getConnection().rollback();
				return false;
			}
			
			String curId = "";
			query = "select SCOPE_IDENTITY()";
			ResultSet rs = this.db.get(query);
			
			if (rs != null)
			{
				if (rs.next())
					curId = rs.getString(1);
				rs.close();
			}
			
			String queryCT = "";
			for (int i = 0; i < this.itemList.size(); i++)
			{
				SoDuDauKyItem item = this.itemList.get(i);
					
				queryCT = 
					"insert into ERP_CHUNGTUDAUKY_CHITIET(trangThai, chungTuDauKy_fk, soChungTu, ngayChungTu--\n" + 
					", maDoiTuong_Fast,maDoiTuong_Erp, tenDoiTuong, doiTuong_fk--\n" + 
					", tenLoaiDoiTuong, loaiDoiTuong_fk, isDoiTuongNo,soHieuTaiKhoanNo, taiKhoanNo_fk--\n" + 
					", soHieuTaiKhoanCo, taiKhoanCo_fk, soTienVND, tenTienTe, tienTe_fk--\n" + 
					", tiGia,soTienNgoaiTe, soHieuTaiKhoanNo1, taiKhoanNo1_fk--\n" + 
					", soHieuTaiKhoanCo1, taiKhoanCo1_fk, soTienVND1, soHoaDonGoc--\n" + 
					", ngayHoaDonGoc, taiKhoanPhanMemFast, BTTH_FK,BTTH_CT_FK--\n" +
					", sheetName, isNPP, maQuay, quay_fk,maChiNhanhDKSD,maChiNhanhDLN)--\n" + 
					"values(" + item.getTrangThai() + ", " + curId  + ", '" + item.getSoChungTu() + "', '" + item.getNgayChungTu() + "'--\n" + 
					", '" + item.getMaDoiTuongFast() + "', '" + item.getMaDoiTuongErp() + "', N'" + item.getTenDoiTuong().replaceAll("'", "''") + "', " + item.getDoiTuongId() + "--\n" + 
					", N'" + item.getTenLoaiDoiTuong() + "', " + item.getLoaiDoiTuongId() + ", " + item.getIsDoiTuongNo() + ", '" + item.getSoHieuTaiKhoanNo() + "', " + item.getTaiKhoanNoId() + "--\n" + 
					", '" + item.getSoHieuTaiKhoanCo() + "', " + item.getTaiKhoanCoId() + ", " + item.getSoTienVND() + ", '" + item.getTenTienTe() + "', " + item.getTienTeId() + "--\n" + 
					", " + item.getTiGia() + ", " + item.getSoTienNgoaiTe() + ",'" + item.getSoHieuTaiKhoanNo1() + "', " + item.getTaiKhoanNoId1() + "--\n" + 
					", '" + item.getSoHieuTaiKhoanCo1() + "', " + item.getTaiKhoanCoId1() + ", " + item.getSoTienVND1() + ", '" + item.getSoHoaDonGoc() + "'--\n" + 
					", '" + item.getNgayHoaDonGoc() + "', '" + item.getTaiKhoanPhanMemFast() + "', " + item.getBTTH_Id() + ", " + item.getBTTH_CT_Id() + "--\n" +
					", '" + item.getSheetName() + "', " + item.getIsNPP() + ", N'" + item.getMaQuay() + "', " + item.getQuayId() + " ,N'"+item.getMaChiNhanhDKSD()+"',N'"+item.getMaChiNhanhDLN()+"'--\n" + 
					");--\n";
				queryCT = UtilityKeToan.refactorQuery(queryCT);
				System.out.println("Cau lenh tao ctdk ct: \n" + queryCT + "\n---------------------------------------------");
				stt = stt + 2;
				if (!this.db.update(queryCT))
				{
					this.msg = "C1.2 Không tạo được";
					this.db.getConnection().rollback();
					return false;
				}
			}
			
			this.db.getConnection().commit();	
			this.db.getConnection().setAutoCommit(true);
		} catch (Exception e) {
			this.msg = "C1.4 Không tạo được";
			e.printStackTrace();
			this.db.update("rollback");
			return false;
		}
		return true;
	}
	
	public boolean chot()
	{
		try {
//			this.kiemTraDuLieu();
//			if (this.msg.trim().length() > 0)
//			{
//				return false;
//			}
			
			this.db.getConnection().setAutoCommit(false);
			
			int stt = 1;
			
			String query = "";
			String queryCT = "";
			String curId = "";
			query = 
				"update ERP_CHUNGTUDAUKY set trangThai = 1, nguoiSua = " + this.userId + ", ngaySua = '" + Utility.getCurrentDate() + "' where PK_SEQ = " + this.id + ";\n";
			
			System.out.println("update dk: \n" + query + "\n---------------------------------------------");
			if (!this.db.update(query))
			{
				this.msg = "C1.0 Không tạo được";
				this.db.getConnection().rollback();
				return false;
			}
			
			for (int i = 0; i < this.itemList.size(); i++)
			{
				SoDuDauKyItem item = this.itemList.get(i);
				if (item.getTrangThai() == 1)
				{
					if (i == 0 || (i > 0 && !item.getSoChungTu().equals(this.itemList.get(i - 1).getSoChungTu())))
					{
						stt = 1;
						query = 
							"insert ERP_BUTTOANTONGHOP (ngayButToan, dienGiai, ngayTao, ngaySua\n" +
							", nguoiTao, nguoiSua, trangThai, congTy_FK\n" +
							", npp_fk, maChungTu, tienTe_FK, tiGia\n" +
							", ISDAUKY)\n" +
							"values('" + item.getNgayChungTu() + "', N'" + item.getSoChungTu() + "', '" + Utility.getCurrentDate() + "', '" + Utility.getCurrentDate() + "'\n" +
							", " + this.userId + ", " + this.userId + ", '1', 100000\n" +
							", " + (item.getMaChiNhanhDKSD().length()==0?"1":item.getMaChiNhanhDKSD().length()) + ", '" + item.getSoChungTu() + "', " + item.getTienTeId() + ", " + item.getTiGia() + "\n" +
							", '1')\n";
						System.out.println("Cau lenh tao btth: \n" + query + "\n---------------------------------------------");
						if (!this.db.update(query))
						{
							this.msg = "C1.1 Không tạo được";
							this.db.getConnection().rollback();
							return false;
						}
						
						query = "select SCOPE_IDENTITY()";
						ResultSet rs = this.db.get(query);
						
						if (rs != null)
						{
							if (rs.next())
								curId = rs.getString(1);
							rs.close();
						}
					}
						
					String khachHangNoId = null;
					String khachHangCoId = null;
					String nhaCungCapNoId = null;
					String nhaCungCapCoId = null;
					String nhanVienNoId = null;
					String nhanVienCoId = null;
					String doiTuongKhacNoId = null;
					String doiTuongKhacCoId = null;
					String nganHangNoId = null;
					String nganHangCoId = null;
					String xdcbNoId = null;
					String xdcbCoId = null;
					String tscdNoId = null;
					String tscdCoId = null;
					String cpttNoId = null;
					String cpttCoId = null;
					String sanPhamNoId = null;
					String sanPhamCoId = null;
					
					String doiTuongNoId = "";
					String doiTuongCoId = "";
					
					String quayNoId = null;
					String quayCoId = null;
					
					String loaiDoiTuongNo = "";
					String loaiDoiTuongCo = "";
					String isNPPNo = "";
					String isNPPCo = "";
					
					if (item.getIsDoiTuongNo().equals("1"))
					{
						doiTuongNoId = item.getDoiTuongId();
						quayNoId = item.getQuayId();
						loaiDoiTuongNo = item.getTenLoaiDoiTuong();
						if (item.getTenLoaiDoiTuong().toLowerCase().contains("khách hàng") 
								|| item.getTenLoaiDoiTuong().toLowerCase().contains("chi nhánh") 
								|| item.getTenLoaiDoiTuong().toLowerCase().contains("đối tác")
								|| item.getTenLoaiDoiTuong().toLowerCase().contains("bán lẻ") 
								|| item.getTenLoaiDoiTuong().toLowerCase().contains("bán buôn") 
								)
						{
							khachHangNoId = item.getDoiTuongId();
							loaiDoiTuongNo = "Khách hàng";
						}
						else if (item.getTenLoaiDoiTuong().toLowerCase().contains("nhà cung cấp")) 
							nhaCungCapNoId = item.getDoiTuongId();
						else if (item.getTenLoaiDoiTuong().toLowerCase().contains("nhân viên")) 
							nhanVienNoId = item.getDoiTuongId();
						else if (item.getTenLoaiDoiTuong().toLowerCase().contains("đối tượng khác")) 
							doiTuongKhacNoId = item.getDoiTuongId();
						else if (item.getTenLoaiDoiTuong().toLowerCase().contains("ngân hàng")) 
							nganHangNoId = item.getDoiTuongId();
						else if (item.getTenLoaiDoiTuong().toLowerCase().contains("xây dựng cơ bản dở dang")) 
							xdcbNoId = item.getDoiTuongId();
						else if (item.getTenLoaiDoiTuong().toLowerCase().contains("tài sản cố định")) 
							tscdNoId = item.getDoiTuongId();
						else if (item.getTenLoaiDoiTuong().toLowerCase().contains("chi phí trả trước")) 
							cpttNoId = item.getDoiTuongId();
						else if (item.getTenLoaiDoiTuong().toLowerCase().contains("sản phẩm")) 
							sanPhamNoId = item.getDoiTuongId();
						
						isNPPNo = item.getIsNPP();
					}
					else
						if (item.getIsDoiTuongNo().equals("0"))
						{
							doiTuongCoId = item.getDoiTuongId();
							quayCoId = item.getQuayId();
							loaiDoiTuongCo = item.getTenLoaiDoiTuong();
							if (item.getTenLoaiDoiTuong().toLowerCase().contains("khách hàng") 
									|| item.getTenLoaiDoiTuong().toLowerCase().contains("chi nhánh") 
									|| item.getTenLoaiDoiTuong().toLowerCase().contains("đối tác")
									|| item.getTenLoaiDoiTuong().toLowerCase().contains("bán lẻ") 
									|| item.getTenLoaiDoiTuong().toLowerCase().contains("bán buôn") 
									)
							{
								khachHangCoId = item.getDoiTuongId();
								loaiDoiTuongCo = "Khách hàng";
							}
							else if (item.getTenLoaiDoiTuong().toLowerCase().contains("nhà cung cấp")) 
								nhaCungCapCoId = item.getDoiTuongId();
							else if (item.getTenLoaiDoiTuong().toLowerCase().contains("nhân viên")) 
								nhanVienCoId = item.getDoiTuongId();
							else if (item.getTenLoaiDoiTuong().toLowerCase().contains("đối tượng khác")) 
								doiTuongKhacCoId = item.getDoiTuongId();
							else if (item.getTenLoaiDoiTuong().toLowerCase().contains("ngân hàng")) 
								nganHangCoId = item.getDoiTuongId();
							else if (item.getTenLoaiDoiTuong().toLowerCase().contains("xây dựng cơ bản dở dang")) 
								xdcbCoId = item.getDoiTuongId();
							else if (item.getTenLoaiDoiTuong().toLowerCase().contains("tài sản cố định")) 
								tscdCoId = item.getDoiTuongId();
							else if (item.getTenLoaiDoiTuong().toLowerCase().contains("chi phí trả trước")) 
								cpttCoId = item.getDoiTuongId();
							else if (item.getTenLoaiDoiTuong().toLowerCase().contains("sản phẩm")) 
								sanPhamNoId = item.getDoiTuongId();
							
							isNPPCo = item.getIsNPP();
						}
					
					queryCT = 
						"insert into ERP_BUTTOANTONGHOP_CHITIET(BUTTOANTONGHOP_FK, TAIKHOANKT_FK, NO, CO\n" +
						", KHACHHANG_FK, NCC_FK, NHANVIEN_FK, STT\n" +
						", NGANHANG_FK, TAISAN_FK, SANPHAM_FK, MASCLON_FK\n" +
						", DOITUONGKHAC_FK, CCDC_FK, isNPP, PKSEQ\n" +
						", GIATRINT, QUAY_FK)\n" +
						"values(" + curId + ", " + item.getTaiKhoanNoId() + ", " + item.getSoTienVND() + ", 0\n" +
						", " + khachHangNoId + ", " + nhaCungCapNoId+ ", " + nhanVienNoId + ", " + stt + "\n" +
						", " + nganHangNoId+ ", " + tscdNoId+ ", " + sanPhamNoId + ", " + xdcbNoId + "\n" +
						", " + doiTuongKhacNoId + ", " + cpttNoId + ", '" + isNPPNo + "', " + stt + "\n" +
						", " + item.getSoTienNgoaiTe() + ", " + quayNoId + "--\n" +
						");\n" +
						
						"insert into ERP_BUTTOANTONGHOP_CHITIET(BUTTOANTONGHOP_FK, TAIKHOANKT_FK, NO, CO\n" +
						", KHACHHANG_FK, NCC_FK, NHANVIEN_FK, STT\n" +
						", NGANHANG_FK, TAISAN_FK, SANPHAM_FK, MASCLON_FK\n" +
						", DOITUONGKHAC_FK, CCDC_FK, isNPP, PKSEQ\n" +
						", GIATRINT, QUAY_FK)\n" +
						"values(" + curId + ", " + item.getTaiKhoanCoId() + ", 0, " + item.getSoTienVND() + "\n" +
						", " + khachHangCoId + ", " + nhaCungCapCoId+ ", " + nhanVienCoId + ", " + (stt + 1) + "\n" +
						", " + nganHangCoId+ ", " + tscdCoId+ ", " + sanPhamCoId + ", " + xdcbCoId + "\n" +
						", " + doiTuongKhacCoId + ",  " + cpttCoId + ", '" + isNPPCo + "', " + (stt + 1) + "\n" +
						", " + item.getSoTienNgoaiTe() + ", " + quayCoId + "--\n" +
						");\n";
					
					System.out.println("Cau lenh tao btth ct: \n" + queryCT + "\n---------------------------------------------");
					queryCT = UtilityKeToan.refactorQuery(queryCT);
					if (!this.db.update(queryCT))
					{
						this.msg = "C1.2 Không tạo được";
						this.db.getConnection().rollback();
						return false;
					}
					
					
					if ((item.getSoHoaDonGoc() != null && item.getSoHoaDonGoc().trim().length() > 0)
							|| (item.getNgayHoaDonGoc() != null && item.getNgayHoaDonGoc().trim().length() > 0)
							|| (item.getTaiKhoanPhanMemFast() != null && item.getTaiKhoanPhanMemFast().trim().length() > 0)
							|| (item.getSoTienHoaDonGoc() > 0)
							){
						queryCT = 
							"insert into ERP_BUTTOANTONGHOP_CHITIET_HOADON (BTTH_FK, PKSEQ, SOHOADON, NGAYHOADON\n" +
							", TIENHANG, THUESUAT, TIENTHUE, TONGCONG\n" +
							", GHICHU, SOTT)\n" +
							"values(" + curId + ", " + (stt + 1) + ", '" + item.getSoHoaDonGoc() + "', '" + item.getNgayHoaDonGoc() + "'\n" +
							", " + item.getSoTienHoaDonGoc() + ", 0, 0, " + item.getSoTienHoaDonGoc() + "\n" +
							", N'" + item.getTaiKhoanPhanMemFast() + "', 1);\n";
						
						System.out.println("Cau lenh tao btth ct hd: \n" + queryCT + "\n---------------------------------------------");
						if (!this.db.update(queryCT))
						{
							this.msg = "C1.2.2 Không tạo được";
							this.db.getConnection().rollback();
							return false;
						}
					}
	
					//Lưu TaiKhoanPhanMemFast trong ghi chú hóa đơn
					queryCT = 
						"update ERP_CHUNGTUDAUKY_CHITIET set BTTH_FK = " + curId + ", BTTH_CT_FK = " + (stt + 1) + " where PK_SEQ = " + item.getId() + ";\n";
					
					System.out.println("update dk ct: \n" + queryCT + "\n---------------------------------------------");
					if (!this.db.update(queryCT))
					{
						this.msg = "C1.2.3 Không tạo được";
						this.db.getConnection().rollback();
						return false;
					}
					
					stt = stt + 2;
					//Chạy kế toán
					System.out.println("item.getQuayId(): " + item.getQuayId());
					if (item.getQuayId() != null && item.getQuayId().trim().length() > 0)
					{
						if (loaiDoiTuongNo != null && loaiDoiTuongNo.trim().length() > 0)
						{
							loaiDoiTuongNo = "Khách hàng";
							doiTuongNoId = quayNoId;
							isNPPNo = "1";
						}else{
							loaiDoiTuongCo = "Khách hàng";
							doiTuongCoId = quayCoId;
							isNPPCo = "1";
						}
					}
					String loaiChungTu = "Đăng ký số dư";
					queryCT = "";
					//Đưa đơn, không đưa kép
					if (item.getSoHieuTaiKhoanNo().trim().length() > 0 && !item.getSoHieuTaiKhoanNo().startsWith("4118") && item.getTaiKhoanNoId().trim().length() > 0)
					//Ghi nợ
					queryCT += 
						"insert into ERP_PHATSINHKETOAN (NGAYCHUNGTU, NGAYGHINHAN, LOAICHUNGTU, SOCHUNGTU\n" +
						", TAIKHOAN_FK, TAIKHOANDOIUNG_FK, NO, CO\n" +
						", DOITUONG, MADOITUONG, TIENTEGOC_FK, TIGIA_FK\n" +
						", TONGGIATRI, TONGGIATRINT, DIENGIAI, MACHUNGTU\n" +
						", isNPP)\n" +
						"values('" + item.getNgayChungTu() + "', '" + item.getNgayChungTu() + "', N'" + loaiChungTu + "', " + curId + " \n" +
						", " + item.getTaiKhoanNoId() + ", " + item.getTaiKhoanCoId() + ", " + item.getSoTienVND() + ", 0\n" +
						", N'" + loaiDoiTuongNo + "', N'" + doiTuongNoId + "', " + item.getTienTeId() + ", " + item.getTiGia() + "\n" +
						", " + item.getSoTienVND() + ", " + item.getSoTienNgoaiTe() + ", N'" + item.getSoChungTu() + "', '" + item.getSoChungTu() + "'\n" +
						", '" + isNPPNo + "');\n";
					
					if (item.getSoHieuTaiKhoanCo().trim().length() > 0 && !item.getSoHieuTaiKhoanCo().startsWith("4118") && item.getTaiKhoanCoId().trim().length() > 0)
						queryCT += 
						"insert into ERP_PHATSINHKETOAN (NGAYCHUNGTU, NGAYGHINHAN, LOAICHUNGTU, SOCHUNGTU\n" +
						", TAIKHOAN_FK, TAIKHOANDOIUNG_FK, NO,CO\n" +
						", DOITUONG, MADOITUONG, TIENTEGOC_FK, TIGIA_FK\n" +
						", TONGGIATRI, TONGGIATRINT, DIENGIAI, MACHUNGTU\n" +
						", isNPP)\n" +
						"values('" + item.getNgayChungTu() + "', '" + item.getNgayChungTu() + "', N'" + loaiChungTu + "', " + curId + " \n" +
						", " + item.getTaiKhoanCoId() + ", " + item.getTaiKhoanNoId() + ", 0, " + item.getSoTienVND() + "\n" +
						", N'" + loaiDoiTuongCo + "', N'" + doiTuongCoId + "', " + item.getTienTeId() + ", " + item.getTiGia() + "\n" +
						", " + item.getSoTienVND() + ", " + item.getSoTienNgoaiTe() + ", N'" + item.getSoChungTu() + "', '" + item.getSoChungTu() + "'\n" +
						", '" + isNPPCo + "')";
					
					if (item.getSheetName().contains("TSCD"))//Ghi nhận khấu hao cho TSCD
					{
						if (item.getSoHieuTaiKhoanNo1().trim().length() > 0 && !item.getSoHieuTaiKhoanNo1().startsWith("4118") && item.getTaiKhoanNoId1().trim().length() > 0)
							//Ghi nợ
							queryCT += 
								"insert into ERP_PHATSINHKETOAN (NGAYCHUNGTU, NGAYGHINHAN, LOAICHUNGTU, SOCHUNGTU\n" +
								", TAIKHOAN_FK, TAIKHOANDOIUNG_FK, NO, CO\n" +
								", DOITUONG, MADOITUONG, TIENTEGOC_FK, TIGIA_FK\n" +
								", TONGGIATRI, TONGGIATRINT, DIENGIAI, MACHUNGTU\n" +
								", isNPP)\n" +
								"values('" + item.getNgayChungTu() + "', '" + item.getNgayChungTu() + "', N'" + loaiChungTu + "', " + curId + " \n" +
								", " + item.getTaiKhoanNoId1() + ", " + item.getTaiKhoanCoId1() + ", " + item.getSoTienVND1() + ", 0\n" +
								", N'" + item.getTenLoaiDoiTuong() + "', N'" + item.getDoiTuongId() + "', " + item.getTienTeId() + ", " + item.getTiGia() + "\n" +
								", " + item.getSoTienVND1() + ", 0, N'" + item.getSoChungTu() + "', '" + item.getSoChungTu() + "'\n" +
								", '" + item.getIsNPP() + "');\n";
							
							if (item.getSoHieuTaiKhoanCo1().trim().length() > 0 && !item.getSoHieuTaiKhoanCo1().startsWith("4118") && item.getTaiKhoanCoId1().trim().length() > 0)
								queryCT += 
								"insert into ERP_PHATSINHKETOAN (NGAYCHUNGTU, NGAYGHINHAN, LOAICHUNGTU, SOCHUNGTU\n" +
								", TAIKHOAN_FK, TAIKHOANDOIUNG_FK, NO,CO\n" +
								", DOITUONG, MADOITUONG, TIENTEGOC_FK, TIGIA_FK\n" +
								", TONGGIATRI, TONGGIATRINT, DIENGIAI, MACHUNGTU\n" +
								", isNPP)\n" +
								"values('" + item.getNgayChungTu() + "', '" + item.getNgayChungTu() + "', N'" + loaiChungTu + "', " + curId + " \n" +
								", " + item.getTaiKhoanCoId1() + ", " + item.getTaiKhoanNoId1() + ", 0, " + item.getSoTienVND1() + "\n" +
								", N'" + item.getTenLoaiDoiTuong() + "', N'" + item.getDoiTuongId() + "', " + item.getTienTeId() + ", " + item.getTiGia() + "\n" +
								", " + item.getSoTienVND1() + ", 0, N'" + item.getSoChungTu() + "', '" + item.getSoChungTu() + "'\n" +
								", '" + item.getIsNPP() + "')";
					}
					System.out.println("Cau lenh chay kt: \n" + queryCT + "\n---------------------------------------------");
					if (!this.db.update(queryCT))
					{
						this.msg = "C1.3 Không tạo được";
						this.db.getConnection().rollback();
						return false;
					}
				}
			}
			
			String result = LibraryKS.Tinhcuoiky(db, "1", "2016", "9");
			if (result.trim().length() > 0)
			{
				this.msg = "C1.3.1 Không đưa đầu kỳ được";
				this.db.getConnection().rollback();
				return false;
			}
			
			this.db.getConnection().commit();	
			this.db.getConnection().setAutoCommit(true);
		} catch (Exception e) {
			this.msg = "C1.4 Không tạo được";
			e.printStackTrace();
			this.db.update("rollback");
			return false;
		}
		return true;
	}
	
	public boolean delete(String id)
	{
		try {
			this.db.getConnection().setAutoCommit(false);
			
			String query = 
				"update ERP_CHUNGTUDAUKY set trangThai = 2 where pk_seq = " + this.id; 
			System.out.println("cau lenh xoa:\n" + query + "\n---------------------------------------");
			if (!this.db.update(query))
			{
				this.msg = "D1.1 Không xóa được";
				this.db.getConnection().rollback();
				return false;
			}
			
			this.db.getConnection().commit();	
			this.db.getConnection().setAutoCommit(true);
		} catch (Exception e) {
			this.msg = "D1.2 Không tạo được";
			e.printStackTrace();
			this.db.update("rollback");
			return false;
		}
		return true;
	}
	
//	public boolean delete(String id)
//	{
//		try {
//			this.db.getConnection().setAutoCommit(false);
//			
//			String query = "delete erp_PhatSinhKeToan where loaiChungTu = N'Bút toán tổng hợp' and soChungTu = " + id;
//			if (!this.db.update(query))
//			{
//				this.msg = "D1.1 Không xóa được chứng từ đầu kỳ";
//				this.db.getConnection().rollback();
//				return false;
//			}
//			
//			query = "delete erp_ButToanTongHop_ChiTiet where butToanTongHop_FK = " + id;
//			if (!this.db.update(query))
//			{
//				this.msg = "D1.2 Không xóa được chứng từ đầu kỳ";
//				this.db.getConnection().rollback();
//				return false;
//			}
//			
//			query = "delete erp_ButToanTongHop where pk_seq = " + id;
//			if (!this.db.update(query))
//			{
//				this.msg = "D1.3 Không xóa được chứng từ đầu kỳ";
//				this.db.getConnection().rollback();
//				return false;
//			}
//			
//			String result = LibraryKS.Tinhcuoiky(db, "1", "2016", "6");
//			if (result.trim().length() > 0)
//			{
//				this.msg = "D1.3.1 Không xóa được chứng từ đầu kỳ";
//				this.db.getConnection().rollback();
//				return false;
//			}
//			
//			this.db.getConnection().commit();	
//			this.db.getConnection().setAutoCommit(true);
//		} catch (Exception e) {
//			this.msg = "D1.4 Không xóa được chứng từ đầu kỳ";
//			e.printStackTrace();
//			this.db.update("rollback");
//			return false;
//		}
//		return true;
//	}
//	
	private void readXLSXFile(String fileName) throws IOException
	{
		this.itemList.clear();
		this.sheetNames.clear();
		InputStream ExcelFileToRead;
		try {
			ExcelFileToRead = new FileInputStream(fileName);
			XSSFWorkbook wb = new XSSFWorkbook(ExcelFileToRead);
			int sosheet=wb.getNumberOfSheets();
			for(int ii = 0; ii < sosheet; ii++)
			{
				XSSFSheet sheet = wb.getSheetAt(ii);
				String sheetName = sheet.getSheetName();
				if(!wb.isSheetHidden(ii))
				{
					org.apache.poi.ss.usermodel.Row row;
					Iterator<?> rows = sheet.rowIterator();
					int rowIndex = 1;
					boolean isContinue = true;
					if (sheetName.contains("TSCD"))
					{
						this.sheetNames.add(sheetName);
						while (rows.hasNext()) 
						{
							row = (org.apache.poi.ss.usermodel.Row)rows.next();
							if(rowIndex > 2 && isContinue == true)
							{
								String maChiNhanh = sheetName;
								maChiNhanh = "0";
								String soChungTu = getContent(row, 0);
								
								if (soChungTu.trim().length() <= 0)
									isContinue = false;
								else
								{
									String ngayChungTu = getContent(row, 1);
									String maDoiTuongFast = getContent(row, 2);
									String maDoiTuongErp = getContent(row, 3);
									String tenDoiTuong = getContent(row, 4);
									String tenLoaiDoiTuong = "Tài sản cố định";
									String soHieuTaiKhoanNo = getContent(row, 6);
									String soHieuTaiKhoanCo = getContent(row, 7);
									double soTienVND = Double.parseDouble(getContent(row, 8).replaceAll(",", ""));
									
									String tenTienTe = getContent(row, 9);
									double tiGia = 1;
									if (getContent(row, 10).trim().length() > 0)
										tiGia = Double.parseDouble(getContent(row, 10).replaceAll(",", ""));
									
									double soTienNgoaiTe = 0;
									if (getContent(row, 11).trim().length() > 0)
										soTienNgoaiTe = Double.parseDouble(getContent(row, 11).replaceAll(",", ""));
									
									String soHieuTaiKhoanNo1 = getContent(row, 12);
									String soHieuTaiKhoanCo1 = getContent(row, 13);
									double soTienVND1 = 0;
									if (getContent(row, 14).trim().length() > 0)
										soTienVND1 = Double.parseDouble(getContent(row, 14).replaceAll(",", ""));
									
									String soHoaDonGoc = "";
									String ngayHoaDonGoc = "";
									String taiKhoanPhanMemFast = "";
									
									double soTienHoaDonGoc = 0;
									String maChiNhanhDKSD = getContent(row, 16);
									String maChiNhanhDLN = getContent(row, 17);
									String isDoiTuongNo = ((soHieuTaiKhoanNo.trim().length() == 0 ||  soHieuTaiKhoanNo.contains("411")) ? "0" : "1");
									String isNPP = "0";
									ngayChungTu = "2017-07-31";
									
									SoDuDauKyItem item = new SoDuDauKyItem(soChungTu, ngayChungTu
											, maDoiTuongFast, maDoiTuongErp, tenDoiTuong, tenLoaiDoiTuong
											, isDoiTuongNo, isNPP, soHieuTaiKhoanNo, soHieuTaiKhoanCo
											, soTienVND, tenTienTe, tiGia, soTienNgoaiTe
											, soHieuTaiKhoanNo1, soHieuTaiKhoanCo1, soTienVND1, soHoaDonGoc
											, ngayHoaDonGoc, taiKhoanPhanMemFast,maChiNhanhDKSD,maChiNhanhDLN);
									item.setSheetName(sheetName);
									item.setSoTienHoaDonGoc(soTienHoaDonGoc);
									this.itemList.add(item);
								}
							}
							rowIndex++;
						}
					}
					else if (sheetName.contains("TKBT") || sheetName.contains("TKCT") || sheetName.contains("XDCB") || sheetName.contains("CPTT") || sheetName.contains("KH Quay"))
					{
						this.sheetNames.add(sheetName);
						String maQuay = "";
						while (rows.hasNext()) 
						{
							row = (org.apache.poi.ss.usermodel.Row)rows.next();
	//						if (rowIndex == 1)
	//						{
	//							maQuay = getContent(row, 1);
	//							System.out.println("maQuay: " + maQuay);
	//						}
	//						
							if(rowIndex > 2 && isContinue == true)
							{
								String soChungTu = getContent(row, 0);
								
								if (soChungTu.trim().length() <= 0)
									isContinue = false;
								else
								{
									String ngayChungTu = getContent(row, 1);
									String maDoiTuongFast = getContent(row, 2);
									String maDoiTuongErp = getContent(row, 3);
									String tenDoiTuong = getContent(row, 4);
									String tenLoaiDoiTuong = getContent(row, 5);
									if (sheetName.contains("XDCB"))
										tenLoaiDoiTuong = "Xây dựng cơ bản dở dang";
									else if (sheetName.contains("CPTT"))
										tenLoaiDoiTuong = "Chi phí trả trước";
									
									String soHieuTaiKhoanNo = getContent(row, 6);
									String soHieuTaiKhoanCo = getContent(row, 7);
									double soTienVND = Double.parseDouble(getContent(row, 8).replaceAll(",", ""));
									
									String tenTienTe = getContent(row, 9);
									double tiGia = 1;
									if (getContent(row, 10).trim().length() > 0)
										tiGia = Double.parseDouble(getContent(row, 10).replaceAll(",", ""));
									
									double soTienNgoaiTe = 0;
									if (getContent(row, 11).trim().length() > 0)
										soTienNgoaiTe = Double.parseDouble(getContent(row, 11).replaceAll(",", ""));
									
									String soHieuTaiKhoanNo1 = "";
									String soHieuTaiKhoanCo1 = "";
									double soTienVND1 = 0;
									
									String soHoaDonGoc = getContent(row, 12);
									String ngayHoaDonGoc = getContent(row, 13);
									String taiKhoanPhanMemFast = getContent(row, 14);
									
									double soTienHoaDonGoc = 0;
									if (getContent(row, 15).trim().length() > 0)
										soTienHoaDonGoc = Double.parseDouble(getContent(row, 15).replaceAll(",", ""));
									
									String maChiNhanhDKSD = getContent(row, 16);
									String maChiNhanhDLN = getContent(row, 17);
									String isDoiTuongNo = ((soHieuTaiKhoanNo.trim().length() == 0 ||  soHieuTaiKhoanNo.startsWith("411")) ? "0" : "1");
									String isNPP = "0";
									ngayChungTu = "2017-06-30";
									
									SoDuDauKyItem item = new SoDuDauKyItem( soChungTu, ngayChungTu
											, maDoiTuongFast, maDoiTuongErp, tenDoiTuong, tenLoaiDoiTuong
											, isDoiTuongNo, isNPP, soHieuTaiKhoanNo, soHieuTaiKhoanCo
											, soTienVND, tenTienTe, tiGia, soTienNgoaiTe
											, soHieuTaiKhoanNo1, soHieuTaiKhoanCo1, soTienVND1, soHoaDonGoc
											, ngayHoaDonGoc, taiKhoanPhanMemFast,maChiNhanhDKSD,maChiNhanhDLN);
									item.setSheetName(sheetName);
									item.setSoTienHoaDonGoc(soTienHoaDonGoc);
									item.setMaQuay(maQuay);
		//							item.setMaChiNhanh(maChiNhanh);
		//							item.setSoChungTu(soChungTu);
		//							item.setNgayChungTu(ngayChungTu);
		//							item.setMaDoiTuong(maDoiTuong);
		//							item.setTenDoiTuong(tenDoiTuong);
		//							item.setLoaiDoiTuong(loaiDoiTuong);
		//							item.setIsDoiTuongNo(isDoiTuongNo);
		//							item.setSoHieuTaiKhoanNo(soHieuTaiKhoanNo);
		//							item.setSoHieuTaiKhoanCo(soHieuTaiKhoanCo);
		//							item.setSoTien(soTien);
									
									this.itemList.add(item);
								}
							}
							rowIndex++;
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.msg = "RXF1.1 Lỗi đọc file";
		}
	}
	
//	private void layDuLieuIds()
//	{
//		
//	}
	private void layDuLieuIds()
	{
//		String query = "";
//		for (int i = 0; i < this.itemList.size(); i++)
//		{
//			if (i > 0 && i <= 50)
//			{
//				SoDuDauKyItem item = this.itemList.get(i);
//				if (i > 0)
//					query += "union all\n";
//				query += 
//					"select isNull(convert(nvarchar, (\n" +
//					"			select PK_SEQ from NHAPHANPHOI \n" +
//					"			where TRANGTHAI = 1 and (ma like '" + item.getMaChiNhanh() + "' or MaFAST like '" + item.getMaChiNhanh() + "')\n" +
//					"		)), '')\n" +
//					"		as chiNhanhId\n" +
//					"		, isNull(convert(nvarchar, (\n" +
//					"			select PK_SEQ from ERP_TAIKHOANKT \n" +
//					"			where TRANGTHAI = 1 and left(SOHIEUTAIKHOAN,7) like LEFT('" + item.getSoHieuTaiKhoanNo() + "', 7)\n" +
//					"			and npp_fk = (select PK_SEQ from NHAPHANPHOI \n" +
//					"			where TRANGTHAI = 1 and (ma like '" + item.getMaChiNhanh() + "' or MaFAST like '" + item.getMaChiNhanh() + "'))\n" +
//					"		)), '')\n" +
//					"		as taiKhoanNoId\n" +
//					"		, isNull(convert(nvarchar, (\n" +
//					"			select PK_SEQ from ERP_TAIKHOANKT \n" +
//					"			where TRANGTHAI = 1 and left(SOHIEUTAIKHOAN,7) like LEFT('" + item.getSoHieuTaiKhoanCo() + "', 7)\n" +
//					"			and npp_fk = (select PK_SEQ from NHAPHANPHOI \n" +
//					"			where TRANGTHAI = 1 and (ma like '" + item.getMaChiNhanh() + "' or MaFAST like '" + item.getMaChiNhanh() + "'))\n" +
//					"		)), '')\n" +
//					"		as taiKhoanCoId\n" +
//					"		, isNull(convert(nvarchar, (case when N'" + item.getLoaiDoiTuong() + "' like N'Khách hàng%' then	(select PK_SEQ from KHACHHANG where TRANGTHAI = 1 and maFAST like '" + item.getMaDoiTuong() + "')\n" +
//					"		end)), '') as doiTuongId\n" +
//					"		, " + i +" as stt\n";
//			}
//		}
//		
//		if (query.trim().length() > 0)
//		{
//			System.out.println("cau lenh truy xuat db:\n" + query + "\n---------------------------------------------------------");
//			try {
//				ResultSet rs = this.db.get(query);
//				
//				if (rs != null)
//				{
//					while (rs.next())
//					{
//						int i = rs.getInt("stt");
//						SoDuDauKyItem item = this.itemList.get(i);
//						
//						String chiNhanhId = rs.getString("chiNhanhId");
//						String taiKhoanNoId = rs.getString("taiKhoanNoId");
//						String taiKhoanCoId = rs.getString("taiKhoanCoId");
//						String doiTuongId = rs.getString("doiTuongId");
//						
//						item.setChiNhanhId(chiNhanhId);
//						item.setTaiKhoanNoId(taiKhoanNoId);
//						item.setTaiKhoanCoId(taiKhoanCoId);
//						item.setDoiTuongId(doiTuongId);
//					}
//					rs.close();
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//				this.msg = "LDL1.1 Lỗi trong khi đọc file";
//			}
//		}
		String query = "";
		Utility util = new Utility();
		for (int i = 0; i < this.itemList.size(); i++)
		{
//			if (i > 0 && i <= 50)
//			{
				SoDuDauKyItem item = this.itemList.get(i);
//				if (i > 0)
//					query += "union all\n";
//				query = 
//					"select isNull(convert(nvarchar, (\n" +
//					"			select PK_SEQ from "+Utility.prefixDMS+"NHAPHANPHOI \n" +
//					"			where TRANGTHAI = 1 and isKhachHang = 0 and (ma like '" + item.getMaChiNhanh() + "' or MaFAST like '" + item.getMaChiNhanh() + "')\n" +
//					"		)), '')\n" +
//					"		as chiNhanhId\n" +
//					"		, isNull(convert(nvarchar, (\n" +
//					"			select PK_SEQ from TraphacoERP..ERP_TAIKHOANKT \n" +
//					"			where TRANGTHAI = 1 and SOHIEUTAIKHOAN like rtrim('" + item.getSoHieuTaiKhoanNo() + "') + '%' \n" +
//					"			and npp_fk = (select PK_SEQ from "+Utility.prefixDMS+"NHAPHANPHOI \n" +
//					"			where TRANGTHAI = 1 and isKhachHang = 0 and (ma like '" + item.getMaChiNhanh() + "' or MaFAST like '" + item.getMaChiNhanh() + "'))\n" +
//					"		)), '')\n" +
//					"		as taiKhoanNoId\n" +
//					"		, isNull(convert(nvarchar, (\n" +
//					"			select PK_SEQ from TraphacoERP..ERP_TAIKHOANKT \n" +
//					"			where TRANGTHAI = 1 and SOHIEUTAIKHOAN like rtrim('" + item.getSoHieuTaiKhoanCo() + "') + '%'\n" +
//					"			and npp_fk = (select PK_SEQ from "+Utility.prefixDMS+"NHAPHANPHOI \n" +
//					"			where TRANGTHAI = 1 and isKhachHang = 0 and (ma like '" + item.getMaChiNhanh() + "' or MaFAST like '" + item.getMaChiNhanh() + "'))\n" +
//					"		)), '')\n" +
//					"		as taiKhoanCoId\n" +
//					"		, isNull(convert(nvarchar, (\n" +
//					"			case when N'" + item.getLoaiDoiTuong() + "' like N'Khách hàng%' then \n" +
//					"				isNull((select top 1 PK_SEQ from "+Utility.prefixDMS+"KHACHHANG where rtrim(maFAST) like rtrim('" + item.getMaDoiTuong() + "'))\n" +
//					"				, (select top 1 PK_SEQ from "+Utility.prefixDMS+"nhaPhanPhoi where rtrim(maFAST) like rtrim('" + item.getMaDoiTuong() + "') or rtrim(ma) like rtrim('" + item.getMaDoiTuong() + "')))" +
//					"			when N'" + item.getLoaiDoiTuong() + "' like N'Nhân viên%' then (select top 1 PK_SEQ from TraphacoERP..erp_nhanVien where TRANGTHAI = 1 and (maFAST like '" + item.getMaDoiTuong() + "' or ma like '" + item.getMaDoiTuong() + "' or ten like N'" + item.getTenDoiTuong().replaceAll("'", "''") + "'))\n" +
//					"			when N'" + item.getLoaiDoiTuong() + "' like N'Nhà cung cấp%' then (select top 1 PK_SEQ from TraphacoERP..ERP_NHACUNGCAP where TRANGTHAI = 1 and rtrim(ma) like rtrim('" + item.getMaDoiTuong() + "'))\n" +
//					"			when N'" + item.getLoaiDoiTuong() + "' like N'Khách hàng%' then (select top 1 PK_SEQ from "+Utility.prefixDMS+"KHACHHANG where TRANGTHAI = 1 and maFAST like '" + item.getMaDoiTuong() + "')\n" +
//					"		end)), '') as doiTuongId\n" +
//					"		, case when N'" + item.getLoaiDoiTuong() + "' like N'Khách hàng%' then \n" +
//					"			case when (select top 1 PK_SEQ from "+Utility.prefixDMS+"KHACHHANG where maFAST like '" + item.getMaDoiTuong() + "') is not null\n" +
//					"			then 0 else 1 end else 0 end as isNPP\n" +
//					"		, " + i +" as stt\n";
				
				query = 
					"select "+(item.getMaChiNhanhDKSD().length()>0?item.getMaChiNhanhDKSD():"1") +"\n" +
					"		as chiNhanhId\n" +
					(
							item.getSoHieuTaiKhoanNo().trim().length() > 0 ? 
					"		, isNull(convert(nvarchar, (\n" +
					"			select PK_SEQ from ERP_TAIKHOANKT \n" +
					"			where TRANGTHAI = 1 and SOHIEUTAIKHOAN like rtrim('" + item.getSoHieuTaiKhoanNo() + "') + '%' \n" +
					"			and npp_fk = (select PK_SEQ from NHAPHANPHOI \n" +
					"			where TRANGTHAI = 1 and isKhachHang = 0 and pk_seq = "+(item.getMaChiNhanhDKSD().length()>0?item.getMaChiNhanhDKSD():"1")+" )\n" +
					"		)), '')\n" +
					"		as taiKhoanNoId\n"
					: "		, (select PK_SEQ from ERP_TAIKHOANKT where soHieuTaiKhoan = '41180000' and TRANGTHAI = 1 and npp_fk = (select PK_SEQ from "+Utility.prefixDMS+"NHAPHANPHOI\n" + 
					"			where TRANGTHAI = 1 and isKhachHang = 0 and pk_seq = "+(item.getMaChiNhanhDKSD().length()>0?item.getMaChiNhanhDKSD():"1")+" )) \n" +
					"		as taiKhoanNoId\n"
					) +
					(
							item.getSoHieuTaiKhoanCo().trim().length() > 0 ? 
					"		, isNull(convert(nvarchar, (\n" +
					"			select PK_SEQ from ERP_TAIKHOANKT \n" +
					"			where TRANGTHAI = 1 and SOHIEUTAIKHOAN like rtrim('" + item.getSoHieuTaiKhoanCo() + "') + '%'\n" +
					"			and npp_fk = (select PK_SEQ from NHAPHANPHOI \n" +
					"			where TRANGTHAI = 1 and isKhachHang = 0  and pk_seq = "+(item.getMaChiNhanhDKSD().length()>0?item.getMaChiNhanhDKSD():"1")+")\n" +
					"		)), '')\n" +
					"		as taiKhoanCoId\n"
					: "		, (select PK_SEQ from ERP_TAIKHOANKT where soHieuTaiKhoan = '41180000' and TRANGTHAI = 1 and npp_fk = (select PK_SEQ from "+Utility.prefixDMS+"NHAPHANPHOI\n" + 
					"			where TRANGTHAI = 1 and isKhachHang = 0  and pk_seq = "+(item.getMaChiNhanhDKSD().length()>0?item.getMaChiNhanhDKSD():"1")+")) \n" +
					"		as taiKhoanCoId\n"
					) +
					///////////////////TSCD
					(
							(item.getSoHieuTaiKhoanNo1().trim().length() > 0 && item.getSheetName().contains("TSCD")) ? 
					"		, isNull(convert(nvarchar, (\n" +
					"			select PK_SEQ from ERP_TAIKHOANKT \n" +
					"			where TRANGTHAI = 1 and SOHIEUTAIKHOAN like rtrim('" + item.getSoHieuTaiKhoanNo1() + "') + '%' \n" +
					"			and npp_fk = (select PK_SEQ from NHAPHANPHOI \n" +
					"			where TRANGTHAI = 1 and isKhachHang = 0  and pk_seq = "+(item.getMaChiNhanhDKSD().length()>0?item.getMaChiNhanhDKSD():"1")+"))\n" +
					"		)), '')\n" +
					"		as taiKhoanNoId1\n"
					: "		, null as taiKhoanNoId1\n"
					) +
					(
							(item.getSoHieuTaiKhoanCo1().trim().length() > 0 && item.getSheetName().contains("TSCD")) ?
					"		, isNull(convert(nvarchar, (\n" +
					"			select PK_SEQ from ERP_TAIKHOANKT \n" +
					"			where TRANGTHAI = 1 and SOHIEUTAIKHOAN like rtrim('" + item.getSoHieuTaiKhoanCo1() + "') + '%'\n" +
					"			and npp_fk = (select PK_SEQ from NHAPHANPHOI \n" +
					"			where TRANGTHAI = 1 and isKhachHang = 0  and pk_seq = "+(item.getMaChiNhanhDKSD().length()>0?item.getMaChiNhanhDKSD():"1")+"))\n" +
					"		)), '')\n" +
					"		as taiKhoanCoId1\n"
					: "		, null as taiKhoanCoId1\n"
					) +
					//////////////////
					"		, isNull(convert(nvarchar, (\n" +
					"			case \n" +
					"			when N'" + item.getTenLoaiDoiTuong() + "' like N'Khách hàng DMS%' " +
					"				or N'" + item.getTenLoaiDoiTuong() + "' like N'Bán lẻ%' " +
					"				or N'" + item.getTenLoaiDoiTuong() + "' like N'Bán buôn%' " +
					"				then (select top 1 PK_SEQ from khachHang where TRANGTHAI = 1 and (maFAST like '" + item.getMaDoiTuongFast() + "' or maFAST like '" + item.getMaDoiTuongErp() + "') and pk_seq = "+(item.getMaChiNhanhDLN().length()>0?item.getMaChiNhanhDLN():"1")+" )\n" +
					"			when (N'" + item.getTenLoaiDoiTuong() + "' like N'Khách hàng' or N'" + item.getTenLoaiDoiTuong() + "' like N'Chi nhánh%' or N'" + item.getTenLoaiDoiTuong() + "' like N'Đối tác%' or N'" + item.getTenLoaiDoiTuong() + "' like N'Chi nhánh/Đối tác%') then \n" +
					"				(select top 1 PK_SEQ from nhaPhanPhoi where trangThai = 1 and (rtrim(maFAST) like rtrim('" + item.getMaDoiTuongFast() + "') or rtrim(ma) like rtrim('" + item.getMaDoiTuongFast() + "')))" +
					"			when N'" + item.getTenLoaiDoiTuong() + "' like N'Nhân viên%' then \n" +
					"						(select top 1 PK_SEQ from erp_nhanVien \n" +
					"						where TRANGTHAI = 1 and \n" +
					"						(ltrim(rtrim(maFAST)) like ltrim(rtrim('" + item.getMaDoiTuongFast() + "')) or ltrim(rtrim(ma)) like ltrim(rtrim('" + item.getMaDoiTuongFast() + "')) \n" +
					"						or ltrim(rtrim(maFAST)) like ltrim(rtrim('" + item.getMaDoiTuongErp() + "')) or ltrim(rtrim(ma)) like ltrim(rtrim('" + item.getMaDoiTuongErp() + "')) \n" +
					"						or ltrim(rtrim(ten)) like ltrim(rtrim(N'" + item.getTenDoiTuong().replaceAll("'", "''") + "'))))\n" +
					"			when N'" + item.getTenLoaiDoiTuong() + "' like N'Nhà cung cấp%' then (select top 1 PK_SEQ from ERP_NHACUNGCAP where TRANGTHAI = 1 and rtrim(ma) like rtrim('" + item.getMaDoiTuongFast() + "'))\n" +
					"			when N'" + item.getTenLoaiDoiTuong() + "' like N'Đối tượng khác%' then (select top 1 PK_SEQ from ERP_DOITUONGKHAC where TRANGTHAI = 1 and ltrim(rtrim(maDoiTuong)) like ltrim(rtrim('" + item.getMaDoiTuongFast() + "'))  and npp_fk ="+(item.getMaChiNhanhDLN().length()>0?item.getMaChiNhanhDLN():"1")+")\n" +
					"			when N'" + item.getTenLoaiDoiTuong() + "' like N'Xây dựng cơ bản%' then (select top 1 PK_SEQ from ERP_MASCLON where TRANGTHAI = 1 and (rtrim(ma) like rtrim('" + item.getMaDoiTuongFast() + "') or rtrim(ma) like rtrim('" + item.getMaDoiTuongErp() + "')))\n" +
					"			when N'" + item.getTenLoaiDoiTuong() + "' like N'Chi phí trả trước%' then (select top 1 PK_SEQ from ERP_CONGCUDUNGCU where TRANGTHAI = 1 and (rtrim(ma) like rtrim('" + item.getMaDoiTuongFast() + "') or rtrim(ma) like rtrim('" + item.getMaDoiTuongErp() + "')))\n" +
					"			when N'" + item.getTenLoaiDoiTuong() + "' like N'Tài sản cố định%' then (select top 1 PK_SEQ from ERP_TAISANCODINH where TRANGTHAI = 1 and (rtrim(ma) like rtrim('" + item.getMaDoiTuongFast() + "') or rtrim(ma) like rtrim('" + item.getMaDoiTuongErp() + "')))\n" +
					"		end)), '') as doiTuongId\n" +
					
					//////
					"		, isNull(convert(nvarchar, (\n" +
					"			case \n" +
					"			when (N'" + item.getTenLoaiDoiTuong() + "' like N'Khách hàng' or N'" + item.getTenLoaiDoiTuong() + "' like N'Chi nhánh%' or N'" + item.getTenLoaiDoiTuong() + "' like N'Đối tác%' or N'" + item.getTenLoaiDoiTuong() + "' like N'Chi nhánh / Đối tác%') then \n" +
					"				(select top 1 PK_SEQ from khachHang where trangThai = 1 and (rtrim(maFAST) like rtrim('" + item.getMaDoiTuongFast() + "'))  and npp_fk ="+(item.getMaChiNhanhDLN().length()>0?item.getMaChiNhanhDLN():"1")+")\n" +
					"		end)), '') as doiTuongDMSId\n" +
					/////
					"		, case when ((N'" + item.getTenLoaiDoiTuong() + "' like N'Khách hàng' or N'" + item.getTenLoaiDoiTuong() + "' like N'Chi nhánh%' or N'" + item.getTenLoaiDoiTuong() + "' like N'Đối tác%' or N'" + item.getTenLoaiDoiTuong() + "' like N'Chi nhánh / Đối tác%')) then 1 else 0 end as isNPP\n" +
					
					"		, " + i +" as stt\n" +
					"		, (select PK_SEQ from erp_tienTe where TRANGTHAI = 1 and ma = '" + (item.getTenTienTe().trim().length() > 0 ? item.getTenTienTe() : "VND") + "') tienTeId\n" +
					"		, (select PK_SEQ from ERP_DANHMUCDOITUONG where TEN like N'" + item.getTenLoaiDoiTuong() + "') loaiDoiTuongId\n" +
					(
							item.getMaQuay().trim().length() > 0 ? 
					"		, (select top 1 PK_SEQ from nhaPhanPhoi npp where npp.TRANGTHAI = 1 and MaFAST like '%" + item.getMaQuay() + "%') quayId\n"
					: "		, null quayId\n"
					)
					;
//			}
		
		
//				if (item.getSoHieuTaiKhoanCo().contains("33550000") || item.getSoHieuTaiKhoanCo().contains("33560000"))
//					System.out.println("cau lenh truy xuat db:\n" + query + "\n---------------------------------------------------------");
		if (query.trim().length() > 0)
		{
			System.out.println("cau lenh truy xuat db:\n" + query + "\n---------------------------------------------------------");
			try {
				ResultSet rs = this.db.get(query);
				
				if (rs != null)
				{
					if (rs.next())
					{
						String chiNhanhId = rs.getString("chiNhanhId");
						String taiKhoanNoId = rs.getString("taiKhoanNoId");
						String taiKhoanCoId = rs.getString("taiKhoanCoId");
						
						String taiKhoanNoId1 = rs.getString("taiKhoanNoId1");
						String taiKhoanCoId1 = rs.getString("taiKhoanCoId1");
						
						String isNPP = rs.getString("isNPP");
						String doiTuongId = rs.getString("doiTuongId");
						String doiTuongDMSId = rs.getString("doiTuongDMSId");
//						if (item.getTenDoiTuong().contains("Cao Triệu Phong"))
//						{
//							System.out.println("cau lenh truy xuat Cao Triệu Phong:\n" + query + "\n---------------------------------------------------------");
//							System.out.println("doiTuongDMSId: " + doiTuongDMSId);
//						}
						if ((doiTuongId == null || doiTuongId.trim().length() == 0) && (doiTuongDMSId != null && doiTuongDMSId.trim().length() > 0))
						{
							doiTuongId = doiTuongDMSId;
							isNPP = "0";
						}
						String tienTeId = rs.getString("tienTeId");
						String loaiDoiTuongId = rs.getString("loaiDoiTuongId");
						String quayId = rs.getString("quayId");
						
						item.setChiNhanhId(chiNhanhId);
						item.setTaiKhoanNoId(taiKhoanNoId);
						item.setTaiKhoanCoId(taiKhoanCoId);
						
						item.setTaiKhoanNoId1(taiKhoanNoId1);
						item.setTaiKhoanCoId1(taiKhoanCoId1);
						
						item.setDoiTuongId(doiTuongId);
						item.setIsNPP(isNPP);
						item.setTienTeId(tienTeId);
						item.setLoaiDoiTuongId(loaiDoiTuongId);
						item.setQuayId(quayId);
						
//						if (item.getTenLoaiDoiTuong().toLowerCase().contains("Mã sửa chữa lớn"))
//							item.setDoiTuongId(item.getTenDoiTuong());
						
					}
					rs.close();
				}
//				else
//					System.out.println("cau lenh truy xuat db:\n" + query + "\n---------------------------------------------------------");

			} catch (Exception e) {
				e.printStackTrace();
				this.msg = "LDL1.1 Lỗi trong khi đọc file";
				return;
			}
		}
		}
	}

	public boolean kiemTraDuLieu()
	{
		SoDuDauKyItem.setDisplayForList(this.itemList);
		layDuLieuIds();
		String ngayChungTuMsg = "";
		String doiTuongMsg = "";
		String loaiDoiTuongMsg = "";
		String taiKhoanNoMsg = "";
		String taiKhoanCoMsg = "";
		String soTienMsg = "";
		String quayMsg = "";
		
		for (int i = 0; i < this.itemList.size(); i++)
		{
			SoDuDauKyItem item = this.itemList.get(i);
			item.setTrangThai(1);
			String itemMsg = "";
			if (item.getNgayChungTu().trim().length() <= 0)
			{
				item.setTrangThai(0);
				ngayChungTuMsg += "\n	" + item.getNgayChungTu();
				itemMsg += "Ngày chứng từ không hợp lệ; ";
			}
			if (item.getTenLoaiDoiTuong().trim().length() <= 0 
					&& (item.getMaDoiTuongFast().trim().length() > 0 || item.getTenDoiTuong().trim().length() > 0))
			{
				item.setTrangThai(0);
				loaiDoiTuongMsg += "\n	Đối tượng " + item.getMaDoiTuongFast();
				itemMsg += "Loại đối tượng không hợp lệ; ";
			}
			else if (item.getTenLoaiDoiTuong().trim().length() > 0 
					&& (item.getMaDoiTuongFast().trim().length() > 0 || item.getTenDoiTuong().trim().length() > 0)
					&& item.getDoiTuongId().trim().length() == 0)
			{
				item.setTrangThai(0);
				doiTuongMsg += "\n	Đối tượng " + item.getTenLoaiDoiTuong() + " - " + item.getMaDoiTuongFast() + " - " + item.getMaDoiTuongErp() + " - " + item.getTenDoiTuong();
//				doiTuongMsg += "\n	" + item.getMaDoiTuongFast();
//				doiTuongMsg += "\n	" + item.getTenDoiTuong();
				itemMsg += "Không tìm thấy đối tượng; ";
			}
			if (item.getSoHieuTaiKhoanNo().trim().length() > 0 && !item.getSoHieuTaiKhoanNo().startsWith("411") && item.getTaiKhoanNoId().trim().length() <= 0)
			{
				item.setTrangThai(0);
				taiKhoanNoMsg += "\n	Số hiệu tài khoản: " + item.getSoHieuTaiKhoanNo() + " (mã đối tượng " + item.getMaDoiTuongFast() + ")";
				itemMsg += "Không tìm thấy tài khoản nợ; ";
			}
			if (item.getSoHieuTaiKhoanCo().trim().length() > 0 && !item.getSoHieuTaiKhoanCo().startsWith("411") && item.getTaiKhoanCoId().trim().length() <= 0)
			{
				item.setTrangThai(0);
				taiKhoanNoMsg += "\n	Số hiệu tài khoản: " + item.getSoHieuTaiKhoanCo() + " (mã đối tượng " + item.getMaDoiTuongFast() + ")";
				itemMsg += "Không tìm thấy tài khoản có; ";
			}
			/////////////////TSCD
			if (item.getSheetName().contains("TSCD"))
			{
				if (item.getSoHieuTaiKhoanNo().trim().length() > 0 && !item.getSoHieuTaiKhoanNo().startsWith("411") && item.getTaiKhoanNoId().trim().length() <= 0)
				{
					item.setTrangThai(0);
					taiKhoanNoMsg += "\n	Số hiệu tài khoản: " + item.getSoHieuTaiKhoanNo() + " (mã đối tượng " + item.getMaDoiTuongFast() + ")";
					itemMsg += "Không tìm thấy tài khoản nợ; ";
				}
				if (item.getSoHieuTaiKhoanCo().trim().length() > 0 && !item.getSoHieuTaiKhoanCo().startsWith("411") && item.getTaiKhoanCoId().trim().length() <= 0)
				{
					item.setTrangThai(0);
					taiKhoanNoMsg += "\n	Số hiệu tài khoản: " + item.getSoHieuTaiKhoanCo() + " (mã đối tượng " + item.getMaDoiTuongFast() + ")";
					itemMsg += "Không tìm thấy tài khoản có; ";
				}
				
				if (item.getSoHieuTaiKhoanNo1().trim().length() > 0 && !item.getSoHieuTaiKhoanNo1().startsWith("411") && item.getTaiKhoanNoId1().trim().length() <= 0)
				{
					item.setTrangThai(0);
					taiKhoanNoMsg += "\n	Số hiệu tài khoản: " + item.getSoHieuTaiKhoanNo1() + " (mã đối tượng " + item.getMaDoiTuongFast() + ")";
					itemMsg += "Không tìm thấy tài khoản nợ; ";
				}
				if (item.getSoHieuTaiKhoanCo1().trim().length() > 0 && !item.getSoHieuTaiKhoanCo1().startsWith("411") && item.getTaiKhoanCoId1().trim().length() <= 0)
				{
					item.setTrangThai(0);
					taiKhoanNoMsg += "\n	Số hiệu tài khoản: " + item.getSoHieuTaiKhoanCo1() + " (mã đối tượng " + item.getMaDoiTuongFast() + ")";
					itemMsg += "Không tìm thấy tài khoản có; ";
				}
			}				
			///////////////
			if (item.getSheetName().contains("KH Quay") && (item.getQuayId() == null || item.getQuayId().trim().length() <= 0))
			{
				item.setTrangThai(0);
				quayMsg += "\n	Quầy: " + item.getMaQuay() + " (mã đối tượng " + item.getMaDoiTuongFast() + ")";
				itemMsg += "Không tìm thấy quầy; ";
			}
			
			item.setMsg(itemMsg);
//			if (item.getSoTien() <= 0)
//				soTienMsg += "\n	Số tiền " + item.getSoTien();
		}
		
		if (ngayChungTuMsg.trim().length() > 0)
			ngayChungTuMsg = "Lỗi ngày chứng từ không hợp lệ:" + ngayChungTuMsg;
		if (doiTuongMsg.trim().length() > 0)
			doiTuongMsg = "Lỗi đối tượng không tìm thấy:" + doiTuongMsg;
		if (loaiDoiTuongMsg.trim().length() > 0)
			loaiDoiTuongMsg = "Lỗi không có loại đối tượng:" + loaiDoiTuongMsg;
		if (taiKhoanNoMsg.trim().length() > 0)
			taiKhoanNoMsg = "Lỗi tài khoản nợ không tìm thấy:" + taiKhoanNoMsg;
		if (taiKhoanCoMsg.trim().length() > 0)
			taiKhoanCoMsg = "Lỗi tài khoản có không tìm thấy:" + taiKhoanCoMsg;
		if (soTienMsg.trim().length() > 0)
			soTienMsg = "Lỗi số tiền không hợp lệ:" + soTienMsg;
		if (quayMsg.trim().length() > 0)
			quayMsg = "Lỗi số tiền không hợp lệ:" + quayMsg;
		
		this.msg = ngayChungTuMsg 
		+ doiTuongMsg
		+ loaiDoiTuongMsg
		+ taiKhoanNoMsg
		+ taiKhoanCoMsg
		+ soTienMsg
		+ quayMsg;
		
//		kiemTraDoiTuongKhachHang();
		if (this.msg.trim().length() > 0)
			return false;
		return true;
	}
//	
	
//	private void kiemTraDoiTuongKhachHang() {
//		String query = "";
//		
//		for (SoDuDauKyItem item : this.itemList)
//		{
//			if (item.getLoaiDoiTuong().trim().contains("Khách hàng"))
//			{
//				if (query.trim().length() > 0)
//					query += "union all\n";
//				query += 
//					"select '" + item.getMaDoiTuong() + "' maDoiTuong\n" +
//					", (select count(*) from "+Utility.prefixDMS+"KHACHHANG where trangThai = 1 and rtrim(ltrim(maFAST)) like rtrim(ltrim('" + item.getMaDoiTuong() +  "'))) as maKH\n" +
//					", (select count(*) from "+Utility.prefixDMS+"NHAPHANPHOI where rtrim(ltrim(maFAST)) like rtrim(ltrim('" + item.getMaDoiTuong() +  "')) or rtrim(ltrim(ma)) like rtrim(ltrim('" + item.getMaDoiTuong() + "'))) as maNPP\n";
//			}
//		}
//		String msgTmp = "";
//		try{
//			if (query.trim().length() > 0)
//			{
//				ResultSet rs = this.db.get(query);
//				if (rs != null)
//				{
//					while (rs.next())
//					{
//						int maKH = rs.getInt("maKH");
//						int maNPP = rs.getInt("maNPP");
//						String maDoiTuong = rs.getString("maDoiTuong");
//						if (maKH > 0 && maNPP > 0)
//							msgTmp += "	Đối tượng: " + maDoiTuong + "\n";
//					}
//					rs.close();
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		if (msgTmp.trim().length() > 0)
//			this.msg += "Lỗi trùng đối tượng khách hàng HO + CN:\n" + msgTmp;
//	}

	public boolean taoMoiDoiTuong() 
	{
		for (SoDuDauKyItem item : this.itemList)
		{
			if (item.getIsTaoMoiDoiTuong() == 1)
			{
				String taiKhoanId = null;
				String soHieuTaiKhoan  = null;
				if (item.getIsDoiTuongNo().trim().equals("1"))
				{
					taiKhoanId = item.getTaiKhoanNoId();
					soHieuTaiKhoan = item.getSoHieuTaiKhoanNo();
				}
				else
				{
					taiKhoanId = item.getTaiKhoanCoId();
					soHieuTaiKhoan = item.getSoHieuTaiKhoanCo();
				}
				
				try {
					String query = null;
					
					if (item.getTenLoaiDoiTuong().trim().toLowerCase().contains("đối tượng khác"))
					{
						query = 
							"if not exists (select PK_SEQ from ERP_DOITUONGKHAC where MADOITUONG like N'%" + item.getMaDoiTuongFast()+ "%' or MADOITUONG like N'%" + item.getMaDoiTuongErp()+ "%')\n" +
							"begin\n" +
							"	insert into ERP_DOITUONGKHAC(MADOITUONG, TENDOITUONG, SOHIEUTAIKHOAN, TENTAIKHOAN, TRANGTHAI)\n" +
							"	values('" + item.getMaDoiTuongFast() + "', N'" + item.getTenDoiTuong() + "', '" + soHieuTaiKhoan + "', '', 1)\n" +
							"end\n"; 
					}
					else if (item.getTenLoaiDoiTuong().trim().toLowerCase().contains("nhân viên"))
					{
						query = 
							"if not exists (select PK_SEQ \n" +
							"				from ERP_NHANVIEN \n" +
							"				where ma like N'%" + item.getMaDoiTuongFast()+ "%' or ma like N'%" + item.getMaDoiTuongErp()+ "%' \n" +
							"					or mafast like N'%" + item.getMaDoiTuongErp()+ "%' or mafast like N'%" + item.getMaDoiTuongErp()+ "%')\n" +
							"begin\n" +
							"	insert into ERP_NHANVIEN(MA, MAFAST, TEN, TRANGTHAI\n" +
							"	, TAIKHOAN_FK, CONGTY_FK, NGAYTAO, NGAYSUA\n" +
							"	, NGUOITAO, NGUOISUA, NPP_FK)\n" +
							"	values('" + item.getMaDoiTuongErp() + "', '" + item.getMaDoiTuongFast() + "', N'" + item.getTenDoiTuong() + "',1\n" +
							"	, " + taiKhoanId + ", " + this.congTyId + ", '" + Utility.getCurrentDate() + "', '" + Utility.getCurrentDate() + "'\n" +
							"	, " + this.userId + ", " + this.userId + ", '" + this.getChiNhanhId() + "')\n" +
							"end\n"; 
					}
					else if (item.getTenLoaiDoiTuong().trim().toLowerCase().contains("nhà cung cấp"))
					{
						 query = 
							"if not exists (select PK_SEQ \n" +
							"				from erp_nhaCungCap \n" +
							"				where ma like N'%" + item.getMaDoiTuongFast()+ "%' or ma like N'%" + item.getMaDoiTuongErp()+ "%')\n" +
							"begin\n" +
							
							"	insert into erp_nhaCungCap(MA, TEN, LOAINHACUNGCAP_FK, CONGTY_FK\n" +
							"	, TIENTE_FK, TAIKHOAN_FK, TRANGTHAI, NGUOITAO\n" +
							"	, NGUOISUA, NGAYTAO, NGAYSUA, NPP_FK)\n" +
							"	values('" + item.getMaDoiTuongErp() + "', N'" + item.getTenDoiTuong() + "', 100016, " + this.congTyId + "\n" +
							"	, " + item.getTienTeId() + ", " + taiKhoanId + ", 1, " + this.userId + "\n" +
							"	, " + this.userId + ", '" + Utility.getCurrentDate() + "', '" + Utility.getCurrentDate() + "', " + this.chiNhanhId + ");\n" +
							
//							"	set identity_insert "+Utility.prefixDMS+"erp_nhaCungCap on;" +
							"	insert into "+Utility.prefixDMS+"erp_nhaCungCap(pk_seq, MA, TEN, LOAINHACUNGCAP_FK, CONGTY_FK\n" +
							"	, TIENTE_FK, TAIKHOAN_FK, TRANGTHAI, NGUOITAO\n" +
							"	, NGUOISUA, NGAYTAO, NGAYSUA, NPP_FK)\n" +
							"	values((select pk_seq from erp_nhaCungCap where MA = '" + item.getMaDoiTuongErp() + "'), '" + item.getMaDoiTuongErp() + "', N'" + item.getTenDoiTuong() + "', 100016, " + this.congTyId + "\n" +
							"	, " + item.getTienTeId() + ", " + taiKhoanId + ", 1, " + this.userId + "\n" +
							"	, " + this.userId + ", '" + Utility.getCurrentDate() + "', '" + Utility.getCurrentDate() + "', " + this.chiNhanhId + ");\n" +
							
//							"	set identity_insert "+Utility.prefixDMS+"erp_nhaCungCap off;" +
							
							"end\n"; 
					}
					
					System.out.println("tao moi doi tuong:\n" + query + "\n----------------------------------------");
					if (query != null && query.trim().length() > 0)
					{
						this.db.getConnection().setAutoCommit(false);
						if (!this.db.update(query))
						{
							this.msg += "TMDT1.3 " + item.getSheetName() + ": Không tạo được đối tượng: " + item.getMaDoiTuongFast() + " - " + item.getTenDoiTuong() + "\n";
							this.db.getConnection().rollback();
						}
						else
						{
							item.setTrangThai(1);
							item.setIsTaoMoiDoiTuong(0);
							this.db.getConnection().commit();
						}
						this.db.getConnection().setAutoCommit(true);
					}
				} catch (Exception e) {
					this.msg += "TMDT1.2 " + item.getSheetName() + ": Không tạo được đối tượng: " + item.getMaDoiTuongFast() + " - " + item.getTenDoiTuong() + "\n";
					e.printStackTrace();
				}
			}
		}
		String myMsg = this.msg;
		
		kiemTraDuLieu();
		this.msg = myMsg + "\n\n\n\n" + this.msg;
		
		return true;
	}
	
	public void DbClose()
	{
		if (this.db != null)
			this.db.shutDown();
	}
	
	private static String getContent(org.apache.poi.ss.usermodel.Row row, int column)
	{
		Cell cell = row.getCell(column, org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK);
		cell.setCellType(Cell.CELL_TYPE_STRING);
		return cell.toString();
	}
	
	private void initList() {
	}
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getNppId() {
		return nppId;
	}

	public void setNppId(String nppId) {
		this.nppId = nppId;
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

	public String getMsg() {
		return msg;
	}
	
	public void setMsg(String msg) {
		this.msg = msg;
	}

	public dbutils getDb() {
		return db;
	}

	public void setDb(dbutils db) {
		this.db = db;
	}

	public void setItemList(List<SoDuDauKyItem> itemList) {
		this.itemList = itemList;
	}

	public List<SoDuDauKyItem> getItemList() {
		return itemList;
	}

	public void setCongTyId(String congTyId) {
		this.congTyId = congTyId;
	}

	public String getCongTyId() {
		return congTyId;
	}

	public void setDienGiai(String dienGiai) {
		this.dienGiai = dienGiai;
	}

	public String getDienGiai() {
		return dienGiai;
	}

	public void setTrangThai(String trangThai) {
		this.trangThai = trangThai;
	}

	public String getTrangThai() {
		return trangThai;
	}

	public void setSheetNames(List<String> sheetNames) {
		this.sheetNames = sheetNames;
	}

	public List<String> getSheetNames() {
		return sheetNames;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}
}