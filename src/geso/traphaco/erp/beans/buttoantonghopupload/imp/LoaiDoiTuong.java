package geso.traphaco.erp.beans.buttoantonghopupload.imp;


public class LoaiDoiTuong
{
	
	public static int getLoaiDoiTuongNumber(String tableName)
	{
		if (tableName.trim().toUpperCase().equals("ERP_SANPHAM")) return 0;
		if (tableName.trim().toUpperCase().equals("ERP_NGANHANG"))return 1;
		if (tableName.trim().toUpperCase().equals("ERP_NHACUNGCAP"))return 2;
		if (tableName.trim().toUpperCase().equals("ERP_TAISANCODINH"))return 3;
		if (tableName.trim().toUpperCase().equals("ERP_KHACHHANG"))return 4;
		if (tableName.trim().toUpperCase().equals("ERP_TRUNGTAMDOANHTHU")) return 5;
		if (tableName.trim().toUpperCase().equals("ERP_COPHIEU")) return 6;
//		if (tableName.trim().toUpperCase().equals("ERP_NGANHANG")) return 7;
		if (tableName.trim().toUpperCase().equals("ERP_NHANVIEN")) return 8;
		if (tableName.trim().toUpperCase().equals("ERP_DOITUONGKYQUY")) return 9;
		if (tableName.trim().toUpperCase().equals("ERP_DANHMUCDUAN")) return 10;
		if (tableName.trim().toUpperCase().equals("ERP_DOITUONGKHAC")) return 11;
		if (tableName.trim().toUpperCase().equals("NHAPHANPHOI")) return 12;
		
		return -1;
	}
	
	public static String getLoaiDoiTuongTableName(int type)
	{
		switch (type) {
		case 0: return "ERP_SANPHAM";
		case 1: return "ERP_NGANHANG";
		case 2: return "ERP_NHACUNGCAP";
		case 3: return "ERP_TAISANCODINH";
		case 4: return "ERP_KHACHHANG";
		case 5: return "ERP_TRUNGTAMDOANHTHU";
		case 6: return "ERP_COPHIEU";
//		case 7: return "ERP_NGANHANG";
		case 8: return "ERP_NHANVIEN";
		case 9: return "ERP_DOITUONGKYQUY";
		case 10: return "ERP_DANHMUCDUAN";
		case 11: return "ERP_DOITUONGKHAC";
		case 12: return "NHAPHANPHOI";
		}
		return "";
	}
	
	public static String doiTuongStr = 
		"TTCP_FK, KHACHHANG_FK, NCC_FK, KHO_FK\n" +
		", NGANHANG_FK, TAISAN_FK, SANPHAM_FK\n" +
		", NHANVIEN_FK, MASCLON_FK, DOITUONGKHAC_FK\n" +
		", CCDC_FK";
	
	public static String getDoiTuongStr(String doiTuongId)
	{
		String TTCP_FK = null, KHACHHANG_FK = null, NCC_FK = null, KHO_FK = null
		, NGANHANG_FK = null, TAISAN_FK = null, SANPHAM_FK = null
		, NHANVIEN_FK = null, MASCLON_FK = null, 
		DOITUONGKHAC_FK = null, CCDC_FK = null;
		
		if (doiTuongId != null && doiTuongId.split("_").length > 1) {
			String arr[] = doiTuongId.split("_");
			String doiTuongId1 = arr[1];
			int type = Integer.parseInt(arr[0]);
			switch (type) {
			case 0: SANPHAM_FK = doiTuongId1; break;
			case 1: NGANHANG_FK = doiTuongId1; break;
			case 2: NCC_FK = doiTuongId1; break;
			case 3: TAISAN_FK = doiTuongId1; break;
			case 4: KHACHHANG_FK = doiTuongId1; break;
	//		case 5: "Trung tâm doanh thu"; break;
	//		case 6: "Cổ phiếu"; break;
	//		case 7: "Ngân hàng"; break;
			case 8: NHANVIEN_FK = doiTuongId1; break;
	//		case 9: "Đối tượng ký quỹ"; break;
	//		case 10: "Danh mục dự án"; break;
			case 11: DOITUONGKHAC_FK = doiTuongId1; break;
	//		case 12: "Chi nhánh"; break;
			}
		}
		
		String result = 
			"" + TTCP_FK + ", " + KHACHHANG_FK + ", " + NCC_FK + ", " + KHO_FK + 
			", " + NGANHANG_FK + ", " + TAISAN_FK + ", " + SANPHAM_FK + 
			", " + NHANVIEN_FK + ", " + MASCLON_FK + ", " + DOITUONGKHAC_FK + 
			", " + CCDC_FK;
		
		System.out.println("chuoi doi tuong: " + result);
		return result;
	}
	
	public static String getLoaiDoiTuongName(int type)
	{
		switch (type) {
		case 0: return "Sản phẩm";
		case 1: return "Ngân hàng";
		case 2: return "Nhà cung cấp";
		case 3: return "Tài sản cố định";
		case 4: return "Khách hàng";
		case 5: return "Trung tâm doanh thu";
		case 6: return "Cổ phiếu";
//		case 7: return "Ngân hàng";
		case 8: return "Nhân viên";
		case 9: return "Đối tượng ký quỹ";
		case 10: return "Danh mục dự án";
		case 11: return "Đối tượng khác";
		case 12: return "Chi nhánh";
		}
		return "";
	}
	
	public static String getLoaiDoiTuongName(String type)
	{
		int t = -1;
		try {
			t = Integer.parseInt(type);
		} catch (Exception e) {
//			e.printStackTrace();
		}
		return getLoaiDoiTuongName(t);
	}
}