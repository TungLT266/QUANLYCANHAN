package geso.traphaco.erp.servlets.donmuahang;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.servlets.report.ReportAPI;
import geso.traphaco.center.util.GiuDieuKienLoc;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.donmuahang.IErpDonmuahangList;
import geso.traphaco.erp.beans.donmuahang.imp.ErpDonmuahangList;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import sun.security.krb5.internal.tools.Ktab;

import com.aspose.cells.Cell;
import com.aspose.cells.BorderLineType;
import com.aspose.cells.BorderType;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Font;
import com.aspose.cells.HorizontalAlignmentType;
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;
import com.cete.dynamicpdf.pageelements.CellBorderStyle;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class ErpBCDonmuahangSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ErpBCDonmuahangSvl() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		IErpDonmuahangList obj;
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		HttpSession session = request.getSession();

		Utility util = new Utility();

		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);
		String action= "";

		if (userId.length() == 0)
			userId = util.antiSQLInspection(request.getParameter("userId"));

		obj = new ErpDonmuahangList();
		obj.setCongtyId((String) session.getAttribute("congtyId"));
		obj.setUserId(userId);
		GiuDieuKienLoc.getSetDieuKienLoc(session, this.getServletName(), Thread.currentThread().getStackTrace()[1].getMethodName(), action, userId, obj);
		obj.initBaoCao();

		session.setAttribute("obj", obj);

		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBCTheoDoiDMH.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		OutputStream out = response.getOutputStream();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();

		IErpDonmuahangList obj = new ErpDonmuahangList();
		obj.setCongtyId((String) session.getAttribute("congtyId"));

		String userId = request.getParameter("userId");
		obj.setUserId(userId);
		String loaingay = request.getParameter("loaingay");
		if (loaingay == null)
			loaingay = "";
		obj.setLoaingay(loaingay);

		String tungay = request.getParameter("tungay");
		if (tungay == null)
			tungay = "";
		obj.setTungay(tungay);

		String denngay = request.getParameter("denngay");
		if (denngay == null)
			denngay = "";
		obj.setDenngay(denngay);

		String dvthId = request.getParameter("dvth");
		if (dvthId == null)
			dvthId = "";
		obj.setDvthId(dvthId);

		String [] sanpham = request.getParameterValues("sanpham");
		String str = "";
		if (sanpham != null) {
			for (int i = 0; i < sanpham.length; i++)
				str += sanpham[i] + ",";

			if (str.length() > 0)
				str = str.substring(0, str.length() - 1);

			obj.setSanphamId(str);
		}
		
		
		String [] nguoitaoids = request.getParameterValues("nguoitaoids");
		str = "";
		if (nguoitaoids != null) {
			for (int i = 0; i < nguoitaoids.length; i++)
				str += nguoitaoids[i] + ",";

			if (str.length() > 0)
				str = str.substring(0, str.length() - 1);

			obj.setNguoitaoIds(str);
		}
		
		

		String trangthai = request.getParameter("trangthai");
		if (trangthai == null)
			trangthai = "";

		String pivot = request.getParameter("pivot");
		if (pivot == null)
			pivot = "0";
		obj.setPivot(pivot);

		String[] nccIds = request.getParameterValues("nccIds");
	    str = "";
		if (nccIds != null) {
			for (int i = 0; i < nccIds.length; i++)
				str += nccIds[i] + ",";

			if (str.length() > 0)
				str = str.substring(0, str.length() - 1);

			obj.setNccIds(str);
		}

		String action = request.getParameter("action");
		if (action.equals("taoBC")) {
			// XEM BÁO CÁO THEO DÕI
			response.setContentType("application/xlsm");
			response.setHeader("Content-Disposition", "attachment; filename=BCTheodoiDMHANG.xlsm");

			try {
				CreatePivotTable(out, obj, trangthai, tungay, denngay, loaingay);
			} catch (Exception e) {
				session.setAttribute("obj", obj);
				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBCTheoDoiDMH.jsp";
				obj.setmsg("Không thể tạo báo cáo - " + e.getMessage());

				response.sendRedirect(nextJSP);
			}
		} else if (action.equals("xemNH")) {
			// XEM BÁO CÁO NHẬN HÀNG
			response.setContentType("application/xlsm");
			response.setHeader("Content-Disposition", "attachment; filename=BCTheodoiNhanHang.xlsm");

			try {
				CreatePivotTableTheoDoiNhan(out, obj, trangthai, tungay, denngay);
			} catch (Exception e) {
				session.setAttribute("obj", obj);
				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBCTheoDoiDMH.jsp";
				obj.setmsg("Không thể tạo báo cáo - " + e.getMessage());

				response.sendRedirect(nextJSP);
			}
		} else {
			obj.initBaoCao();

			session.setAttribute("obj", obj);

			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBCTheoDoiDMH.jsp";
			response.sendRedirect(nextJSP);
		}

	}

	private boolean CreatePivotTable(OutputStream out, IErpDonmuahangList obj, String trangthai, String tungay,
			String denngay, String loaingay) throws Exception {
		FileInputStream fstream = null;
		Workbook workbook = new Workbook();

		fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\BCTheoDoiDMHANG.xlsm");

		// -- báo cáo mới --//
		/*
		 * fstream = new
		 * FileInputStream(getServletContext().getInitParameter("path") +
		 * "\\BCTheodoiDMH.xlsm");
		 */
		dbutils db = new dbutils();
		workbook.open(fstream);
		workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);

		String userName = "";
		try {
			String sql = "select ten from nhanvien where pk_seq=" + obj.getUserId();
			System.out.println("SQL"+sql);
			ResultSet rs = db.get(sql);
			while (rs.next()){
				userName = rs.getString("TEN");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		CreateStaticHeader(workbook, obj.getTungay(), obj.getDenngay(),userName,obj.getPivot() != null && obj.getPivot().equals("1"));

		boolean isTrue = CreateStaticData(workbook, obj, trangthai, tungay, denngay, loaingay);
		if (!isTrue) {
			return false;
		}
		workbook.save(out);

		fstream.close();
		return true;
	}

	private boolean CreatePivotTableTheoDoiNhan(OutputStream out, IErpDonmuahangList obj, String trangthai,
			String tungay, String denngay) throws Exception {
		FileInputStream fstream = null;
		Workbook workbook = new Workbook();

		fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\ErpNhacNhanHang.xlsm");

		workbook.open(fstream);
		workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);

		boolean isTrue = CreateStaticDataTheoDoiNhan(workbook, obj, trangthai, tungay, denngay);
		if (!isTrue) {
			return false;
		}
		workbook.save(out);

		fstream.close();
		return true;
	}

	
	//bao cao theo doi ban hang
	private void CreateStaticHeader(Workbook workbook, String dateFrom, String dateTo, String UserName, boolean pivot) {
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);

		worksheet.setName("Sheet1");

		String col = pivot ? "A" : "";
		String row = pivot ? "1" : "8";

		Cells cells = worksheet.getCells();
		cells.setRowHeight(3, 18.0f);
		Cell cell = cells.getCell("D3");
		ReportAPI.getCellStyle(cell, Color.NAVY, true, 11, dateFrom );
		
		cells.setRowHeight(3, 18.0f);
		cell = cells.getCell("H3");
		ReportAPI.getCellStyle(cell, Color.NAVY, true, 11, dateTo);
		
		cells.setRowHeight(3, 18.0f);
		cell = cells.getCell("D4");
		ReportAPI.getCellStyle(cell, Color.NAVY, true, 11, ReportAPI.NOW("yyyy-MM-dd"));
		cells.setRowHeight(3, 18.0f);
		cell = cells.getCell("H4");
		ReportAPI.getCellStyle(cell, Color.NAVY, true, 11, UserName);
		
	}

	
	// du lieu bao cao  theo doi ban hang
	private boolean CreateStaticData(Workbook workbook, IErpDonmuahangList obj, String trang_thai, String tungay,
			String denngay, String loaingay) throws Exception {

		dbutils db = new dbutils();
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);

		Font font = new Font();

	
		String query = "SELECT  distinct   ROW_NUMBER() over (order by  MH.LOAI,MH.SOPO )  as STT,"
				+ " \n  CASE MH.LOAI WHEN 0 THEN N'Nhập Khẩu' WHEN 1 THEN N'Trong Nước' WHEN 2 THEN N'VT/CPDV/TSCD/CCDC' ELSE '' END AS LOAIDONHANG,"
				+ " \n  ISNULL(MH.SOPO,'') AS SODONHANG,ISNULL(MH.NGAYMUA,'') AS NGAYMUA,ISNULL(NCC.TEN,'') AS TENNCC,"
				+ " \n  ISNULL(SP.MA,'') AS MASP,ISNULL(SP.TEN,'') AS TENSP,  isnull ( (select donvi from donvidoluong where cast ( pk_seq as nvarchar (18)) = MHSP.DONVI ),'')   AS DONVI ,"
				+ " \n  ISNULL(MHSP.SOLUONG,0) AS SOLUONGDAT,"
				+ " \n  ISNULL(HD.SOLUONGHD,0) AS SOLUONGHD,"
				+ " \n  ISNULL(NH.SOLUONGNHAN,0) AS SOLUONGNHAN,MHSP.DONGIA AS DONGIA, MHSP.THANHTIEN AS THANHTIEN,"
				+ " \n  ISNULL(MHSP.NGAYNHAN,'') AS NGAYNHANDUKIEN,ISNULL(NGAYNH.NGAYNHAN,'') AS NGAYNHAN,TT.TEN AS TIENTE, "
				+ " \n  ISNULL(MH.GHICHU,'') AS GHICHU,ISNULL(MH.SOHOPDONG,'') AS SOHOPDONG"
				+ " \n  FROM ERP_MUAHANG MH"
				+ " \n  LEFT JOIN ERP_MUAHANG_SP MHSP ON MHSP.MUAHANG_FK = MH.PK_SEQ"
				+ " \n  LEFT JOIN ERP_NHACUNGCAP NCC ON MH.NHACUNGCAP_FK = NCC.PK_SEQ"
				+ " \n  LEFT JOIN ERP_SANPHAM SP ON MHSP.SANPHAM_FK = SP.PK_SEQ"
				+ " \n  LEFT JOIN ERP_TIENTE TT ON MH.TIENTE_FK = TT.PK_SEQ "
				+ " \n  LEFT JOIN ("
				+ " \n  SELECT NHSP1.MUAHANG_FK,SANPHAM_FK,MAX(NGAYNHAN) AS NGAYNHAN "
				+ " \n  FROM ERP_NHANHANG NH1"
				+ " \n  LEFT JOIN ERP_NHANHANG_SANPHAM NHSP1 ON NHSP1.NHANHANG_FK = NH1.PK_SEQ"
				+ " \n  GROUP BY NHSP1.MUAHANG_FK,SANPHAM_FK) NGAYNH ON NGAYNH.MUAHANG_FK = MH.PK_SEQ AND NGAYNH.SANPHAM_FK = MHSP.SANPHAM_FK"
				+ " \n  LEFT JOIN (SELECT MUAHANG_FK,SANPHAM_FK,SUM (SOLUONG) AS SOLUONGHD FROM ERP_HOADONNCC_DONMUAHANG GROUP BY MUAHANG_FK,SANPHAM_FK)"
				+ " \n  HD ON HD.MUAHANG_FK = MH.PK_SEQ AND HD.SANPHAM_FK = MHSP.SANPHAM_FK"
				+ " \n  LEFT JOIN (SELECT MUAHANG_FK,SANPHAM_FK, SUM (SOLUONGNHAN)AS SOLUONGNHAN FROM ERP_NHANHANG_SANPHAM GROUP BY MUAHANG_FK,SANPHAM_FK)"
				+ " \n  NH ON NH.MUAHANG_FK = MH.PK_SEQ AND NH.SANPHAM_FK = MHSP.SANPHAM_FK" 
				+ " \n  LEFT JOIN NHANVIEN NV ON NV.PK_SEQ= MH.NGUOITAO"
				+"\n   LEFT JOIN   "+    
				 "\n    (SELECT  a.MUAHANG_FK AS MUAHANG_FK, b.ngayhoadon as ngayhoadon   FROM  ERP_HOADONNCC_DONMUAHANG a   "+    
				 "\n  	inner join erp_hoadonncc b on a.HOADONNCC_FK=b.pk_seq GROUP BY a.MUAHANG_FK, b.ngayhoadon)  "+    
				 "\n    NGAYHD ON NGAYHD.MUAHANG_FK= MH.PK_SEQ "	
				+ " \n  WHERE 1=1 ";
		
		if (trang_thai.length() > 0) {
			if (trang_thai.equals("-1")) { // Đã nhận hàng
				query += " \n and NH.SOLUONGNHAN >0 ";
			} else {
				query += " \n and MH.TRANGTHAI = '" + trang_thai + "'";
			}
		}

		if (obj.getDvthId().length() > 0) {
			query += " \n and MH.DONVITHUCHIEN_FK = '" + obj.getDvthId() + "'";
		}

		if (obj.getNccIds().trim().length() > 0) {
			query += " \n and MH.NHACUNGCAP_FK in (" + obj.getNccIds() + ")";
		}
		
		if (obj.getNguoitaoIds().trim().length() > 0) {
			query += " \n and MH.NGUOITAO in (" + obj.getNguoitaoIds() + ")";
		}

		// 0 : ngày mua; 1: ngày nhận; 2: ngày hóa đơn
		if (loaingay.equals("0")  ) {
			if(tungay.length()>0){
			query += " \n and MH.NGAYMUA >='" + tungay + "'";
			}
			if(denngay.length()>0){
				query += " \n AND MH.NGAYMUA <='" + denngay + "'";
			}
		} else if (loaingay.equals("1")) {
			query += " \n and MH.NGAYNHAN >='" + tungay + "'   AND MH.NGAYNHAN <='" + denngay + "'";
		} else if (loaingay.equals("2")) {
			/*query += " \n and NGAYHD.NGAYHOADON >='" + tungay + "'   AND NGAYHD.NGAYHOADON <='" + denngay + "'";*/
			
			if(tungay.length()>0){
				query += " \n and NGAYHD.NGAYHOADON >='" + tungay + "'";
				}
				if(denngay.length()>0){
					query += " \n  AND NGAYHD.NGAYHOADON <='" + denngay + "'";
				}
		}
		if(obj.getSanphamId().length()>0){
			query += " \n and MHSP.SANPHAM_FK in (" + obj.getSanphamId() +")"; 
		}
		
		query += " \n AND MH.ISDNTT <>1  AND MH.LYDOTRAHANG IS NULL  AND MH.ISDNTT is not null";
	/*	query += " \n ORDER BY MH.LOAI,MH.SOPO";*/
		
		
		System.out.println("Theo doi Don Mua Hang TT hien tai : " + query);
		/*ResultSet rs = db.get(query);*/
		
		
		
		ResultSet rs=db.get(query);
		ResultSetMetaData rsmd=rs.getMetaData();;
		int socottrongSql=rsmd.getColumnCount();

		// --- đổ dữ liệu ---//

		int index = 7;
		int countRow = 6;
		int column = 0;
		try {
			// CHÈN VÀO EXCEL
			int indexHeader = index - 1;
			Cells cells = worksheet.getCells();

			Cell cell = cells.getCell("A2");
			

			cell = null;
			Style style = null;

			if (obj.getPivot().equals("1")) {
				cell = cells.getCell("BA" + String.valueOf(indexHeader));
				cell.setValue("LOẠI ĐƠN HÀNG");
				style = cell.getStyle();
				style.setHAlignment(TextAlignmentType.CENTER);
				cell.setStyle(style);

				cell = cells.getCell("BB" + String.valueOf(indexHeader));
				cell.setValue("SỐ ĐƠN HÀNG");
				style = cell.getStyle();
				style.setHAlignment(TextAlignmentType.CENTER);
				cell.setStyle(style);

				cell = cells.getCell("BC" + String.valueOf(indexHeader));
				cell.setValue("NGÀY MUA");
				style = cell.getStyle();
				style.setHAlignment(TextAlignmentType.CENTER);
				cell.setStyle(style);

				cell = cells.getCell("BD" + String.valueOf(indexHeader));
				cell.setValue("TÊN NHÀ CUNG CẤP");
				style = cell.getStyle();
				style.setHAlignment(TextAlignmentType.CENTER);
				cell.setStyle(style);

				cell = cells.getCell("BE" + String.valueOf(indexHeader));
				cell.setValue("MÃ SẢN PHẨM");
				style = cell.getStyle();
				style.setHAlignment(TextAlignmentType.CENTER);
				cell.setStyle(style);

				cell = cells.getCell("BF" + String.valueOf(indexHeader));
				cell.setValue("TÊN SẢN PHẨM");
				style = cell.getStyle();
				style.setHAlignment(TextAlignmentType.CENTER);
				cell.setStyle(style);

				cell = cells.getCell("BG" + String.valueOf(indexHeader));
				cell.setValue("ĐƠN VỊ");
				style = cell.getStyle();
				style.setHAlignment(TextAlignmentType.CENTER);
				cell.setStyle(style);

				cell = cells.getCell("BH" + String.valueOf(indexHeader));
				cell.setValue("SỐ LƯỢNG ĐẶT");
				style = cell.getStyle();
				style.setHAlignment(TextAlignmentType.CENTER);
				cell.setStyle(style);

				cell = cells.getCell("BI" + String.valueOf(indexHeader));
				cell.setValue("SỐ LƯỢNG HÓA ĐƠN");
				style = cell.getStyle();
				style.setHAlignment(TextAlignmentType.CENTER);
				cell.setStyle(style);

				cell = cells.getCell("BJ" + String.valueOf(indexHeader));
				cell.setValue("SỐ LƯỢNG NHẬN");
				style = cell.getStyle();
				style.setHAlignment(TextAlignmentType.CENTER);
				cell.setStyle(style);

				cell = cells.getCell("BK" + String.valueOf(indexHeader));
				cell.setValue("ĐƠN GIÁ");
				style = cell.getStyle();
				style.setHAlignment(TextAlignmentType.CENTER);
				cell.setStyle(style);

				cell = cells.getCell("BL" + String.valueOf(indexHeader));
				cell.setValue("THÀNH TIỀN");
				style = cell.getStyle();
				style.setHAlignment(TextAlignmentType.CENTER);
				cell.setStyle(style);

				cell = cells.getCell("BM" + String.valueOf(indexHeader));
				cell.setValue("NGÀY NHẬN DK");
				style = cell.getStyle();
				style.setHAlignment(TextAlignmentType.CENTER);
				cell.setStyle(style);

				cell = cells.getCell("BN" + String.valueOf(indexHeader));
				cell.setValue("NGÀY NHẬN");
				style = cell.getStyle();
				style.setHAlignment(TextAlignmentType.CENTER);
				cell.setStyle(style);

				cell = cells.getCell("BO" + String.valueOf(indexHeader));
				cell.setValue("TIỀN TỆ");
				style = cell.getStyle();
				style.setHAlignment(TextAlignmentType.CENTER);
				cell.setStyle(style);
				
				cell = cells.getCell("BP" + String.valueOf(indexHeader));
				cell.setValue("GHI CHÚ");
				style = cell.getStyle();
				style.setHAlignment(TextAlignmentType.CENTER);
				cell.setStyle(style);
				
				cell = cells.getCell("BQ" + String.valueOf(indexHeader));
				cell.setValue("SỐ HỢP ĐỒNG");
				style = cell.getStyle();
				style.setHAlignment(TextAlignmentType.CENTER);
				cell.setStyle(style);
			} else {
				
			}
			
			while (rs.next()) {
				cell = null;
				style = null;
				NumberFormat formatter = new DecimalFormat("#,###,###.###");

				int stt=1;
				String LOAIDONHANG = rs.getString("LOAIDONHANG");
				String SODONHANG = rs.getString("SODONHANG");
				String NGAYMUA = rs.getString("NGAYMUA");
				String TENNCC = rs.getString("TENNCC");
				String MASP = rs.getString("MASP");
				String TENSP = rs.getString("TENSP");
				String DONVI = rs.getString("DONVI");
				Double SOLUONGDAT = rs.getDouble("SOLUONGDAT");
				Double SOLUONGHD = rs.getDouble("SOLUONGHD");
				Double SOLUONGNHAN = rs.getDouble("SOLUONGNHAN");
				String DONGIA = rs.getString("DONGIA");
				String THANHTIEN = rs.getString("THANHTIEN");
				String NGAYNHANDUKIEN = rs.getString("NGAYNHANDUKIEN");
				String NGAYNHAN = rs.getString("NGAYNHAN");
				String TIENTE = rs.getString("TIENTE");
				String GHICHU = rs.getString("GHICHU");
				String SOHOPDONG = rs.getString("SOHOPDONG");
				
				// CHÈN VÀO EXCEL

				if (obj.getPivot().equals("1")) {
					cell = cells.getCell("BA" + String.valueOf(index));
					cell.setValue(LOAIDONHANG);
					style = cell.getStyle();
					style.setHAlignment(TextAlignmentType.LEFT);
					style.setBorderLine(BorderType.TOP, BorderLineType.THICK);
					style.setBorderColor(BorderType.TOP, Color.BLACK);
					style.setBorderLine(BorderType.LEFT, BorderLineType.THICK);
					style.setBorderColor(BorderType.LEFT, Color.BLACK);
					style.setBorderLine(BorderType.RIGHT, BorderLineType.THICK);
					style.setBorderColor(BorderType.RIGHT, Color.BLACK);
					style.setBorderLine(BorderType.BOTTOM, BorderLineType.THICK);
					style.setBorderColor(BorderType.BOTTOM, Color.BLACK);
					cell.setStyle(style);

					cell = cells.getCell("BB" + String.valueOf(index));
					cell.setValue(SODONHANG);
					style = cell.getStyle();
					style.setHAlignment(TextAlignmentType.LEFT);
					cell.setStyle(style);

					cell = cells.getCell("BC" + String.valueOf(index));
					cell.setValue(NGAYMUA);
					style = cell.getStyle();
					style.setHAlignment(TextAlignmentType.LEFT);
					cell.setStyle(style);

					cell = cells.getCell("BD" + String.valueOf(index));
					cell.setValue(TENNCC);
					style = cell.getStyle();
					style.setHAlignment(TextAlignmentType.LEFT);
					cell.setStyle(style);

					cell = cells.getCell("BE" + String.valueOf(index));
					cell.setValue(MASP);
					style = cell.getStyle();
					style.setHAlignment(TextAlignmentType.LEFT);
					cell.setStyle(style);

					cell = cells.getCell("BF" + String.valueOf(index));
					cell.setValue(TENSP);
					style = cell.getStyle();
					style.setHAlignment(TextAlignmentType.LEFT);
					cell.setStyle(style);

					cell = cells.getCell("BG" + String.valueOf(index));
					cell.setValue(DONVI);
					style = cell.getStyle();
					style.setHAlignment(TextAlignmentType.LEFT);
					cell.setStyle(style);

					cell = cells.getCell("BH" + String.valueOf(index));
					cell.setValue(SOLUONGDAT);
					style = cell.getStyle();
					style.setHAlignment(TextAlignmentType.LEFT);
					cell.setStyle(style);

					cell = cells.getCell("BI" + String.valueOf(index));
					cell.setValue(SOLUONGHD);
					style = cell.getStyle();
					style.setHAlignment(TextAlignmentType.LEFT);
					cell.setStyle(style);

					cell = cells.getCell("BJ" + String.valueOf(index));
					cell.setValue(SOLUONGNHAN);
					style = cell.getStyle();
					style.setHAlignment(TextAlignmentType.LEFT);
					cell.setStyle(style);

					cell = cells.getCell("BK" + String.valueOf(index));
					cell.setValue(DONGIA);
					style = cell.getStyle();
					style.setHAlignment(TextAlignmentType.LEFT);
					cell.setStyle(style);

					cell = cells.getCell("BL" + String.valueOf(index));
					cell.setValue(THANHTIEN);
					style = cell.getStyle();
					style.setHAlignment(TextAlignmentType.LEFT);
					cell.setStyle(style);

					cell = cells.getCell("BM" + String.valueOf(index));
					cell.setValue(NGAYNHANDUKIEN);
					style = cell.getStyle();
					style.setHAlignment(TextAlignmentType.LEFT);
					cell.setStyle(style);

					cell = cells.getCell("BN" + String.valueOf(index));
					cell.setValue(NGAYNHAN);
					style = cell.getStyle();
					style.setHAlignment(TextAlignmentType.LEFT);
					cell.setStyle(style);

					cell = cells.getCell("BO" + String.valueOf(index));
					cell.setValue(TIENTE);
					style = cell.getStyle();
					style.setHAlignment(TextAlignmentType.LEFT);
					cell.setStyle(style);
					
					cell = cells.getCell("BP" + String.valueOf(index));
					cell.setValue(GHICHU);
					style = cell.getStyle();
					style.setHAlignment(TextAlignmentType.LEFT);
					cell.setStyle(style);
					
					cell = cells.getCell("BQ" + String.valueOf(index));
					cell.setValue(SOHOPDONG);
					style = cell.getStyle();
					style.setHAlignment(TextAlignmentType.LEFT);
					cell.setStyle(style);
					
				} else {
					
					// vao day chen data vao bc theo doi don mua hang: THU SUA 24/11/2016
					for(int i =1;i <=socottrongSql ; i ++)
					{
						cell = cells.getCell(countRow,column + i-1 );
						
						if( (i>=9 &&i<=13) || i==1){
							
							cell.setValue(rs.getDouble(i));
						}
						else{
							cell.setValue(rs.getString(i));
						}
						
						if(i==1){
							setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED,false);
						} else{
							setCellBorderStyle(cell, HorizontalAlignmentType.LEFT,false);
						}	
						
					}
					countRow++;
					
				}

				index++;
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			db.shutDown();
		}
	}

	
	
	private void setCellBorderStyle(Cell cell, short align,boolean kt) {
		Style style = cell.getStyle();
		style.setHAlignment(align);
		style.setBorderLine(BorderType.TOP, 1);
		style.setBorderLine(BorderType.RIGHT, 1);
		style.setBorderLine(BorderType.BOTTOM, 1);
		style.setBorderLine(BorderType.LEFT, 1);
		if(kt)
		{
			Font font2 = new Font(); 
			font2.setName("Calibri");
			font2.setColor(Color.RED);
			style.setFont(font2);
			
		}
		
		style.setColor(Color.WHITE);
		
		cell.setStyle(style);
	}
	
	
	
	
	
	private boolean CreateStaticDataTheoDoiNhan(Workbook workbook, IErpDonmuahangList obj, String trang_thai,
			String tungay, String denngay) throws Exception {

		dbutils db = new dbutils();
		Utility util = new Utility();
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);

		Font font = new Font();

		String query = "	SELECT   Distinct A.*,(A.SOLUONG-A.SOLUONGDANHAN) as SOLUONGCONLAI  FROM( "
				+ "	(SELECT ISNULL(DATHANG.MHID,NHANHANG.MHID) AS MUAHANG_FK,ISNULL(DATHANG.SPID,NHANHANG.SPID) AS MASANPHAM,  "
				+ "	 DATHANG.NGAYMUA, DATHANG.POID,DATHANG.NCC as TENNCC, ISNULL(DATHANG.TEN,DATHANG.DIENGIAI)  AS TENSP , "
				+ "	  DATHANG.DONVI,   DATHANG.SOLUONG, DATHANG.DONGIA, DATHANG.THANHTIEN ,DATHANG.TIENTE, "
				+ "	 ISNULL(DATHANG.NGAYDENGHI,'') AS NGAYYEUCAUNHAN, ISNULL(NHANHANG.SOLUONGNHAN,0) AS SOLUONGDANHAN, "
				+ "	 NHANHANG.NHID, ISNULL(NHANHANG.NGAYNHAN,'') AS NGAYNHAN,  "
				+ "	 ISNULL(NHANHANG.DONGIA, DATHANG.DONGIA) AS DONGIANHAN, "
				+ "	 ISNULL(NHANHANG.SOHOADON,'') AS SOHOADON, "
				+ "	 ISNULL(NHANHANG.NGAYHOADON,'') AS NGAYHOADON,  DATHANG.NGUOITAO, "
				+ "	 DATHANG.DONVITHUCHIEN_FK,  DATHANG.NHACUNGCAP_FK,DATHANG.GHICHU      " + "	 FROM   " + "	 	( "
				+ "	 		SELECT MH.PK_SEQ AS MHID,  MHSP.SANPHAM_FK AS SPID, MH.NGAYTAO, MH.SOPO AS POID, ISNULL(NCC.TEN,'') AS NCC, "
				+ "	 		ISNULL(SP.MA,'') AS ITEM, MH.NGAYMUA   ,  MHSP.DONVI, MHSP.SOLUONG,    "
				+ "	 		MHSP.DONGIA, (MHSP.DONGIA*MHSP.SOLUONG) AS THANHTIEN,TIENTE.TEN as TIENTE, "
				+ "	 		ISNULL(MHSP.NGAYNHAN,'') AS NGAYDENGHI,   "
				+ "	 		DVTH.MA, SP.TEN, MHSP.DIENGIAI, MH.NGUOITAO, SP.LOAISANPHAM_FK , MH.DONVITHUCHIEN_FK, MH.NHACUNGCAP_FK,MH.GHICHU "
				+ "	 		FROM ERP_MUAHANG MH     "
				+ "	 		INNER JOIN ERP_MUAHANG_SP MHSP ON MH.PK_SEQ=MHSP.MUAHANG_FK   "
				+ "	 		INNER JOIN ERP_NHACUNGCAP NCC ON MH.NHACUNGCAP_FK=NCC.PK_SEQ "
				+ "	 		LEFT JOIN ERP_SANPHAM SP ON SP.PK_SEQ=MHSP.SANPHAM_FK "
				+ "			inner join ERP_TIENTE TIENTE on MH.TIENTE_FK = TIENTE.PK_SEQ "
				+ "	 		INNER JOIN ERP_DONVITHUCHIEN DVTH ON MH.DONVITHUCHIEN_FK= DVTH.PK_SEQ  "
				+ "	 		LEFT JOIN(  SELECT 	MUAHANG_FK AS DMHID,  " + "	 		CASE WHEN SUM(QUYETDINH) > 0 THEN  "
				+ "	 		(CASE WHEN  ( SELECT SUM(TRANGTHAI)    FROM ERP_DUYETMUAHANG  	 "
				+ "	 		WHERE MUAHANG_FK = DUYETMUAHANG.MUAHANG_FK AND QUYETDINH = 1) > 0 THEN 0  ELSE 1  END)	  "
				+ "	 		ELSE COUNT(TRANGTHAI) - SUM(TRANGTHAI) 	  END AS DUYET    "
				+ "	 		FROM ERP_DUYETMUAHANG DUYETMUAHANG  "
				+ "	 		GROUP BY MUAHANG_FK  ) DUYET ON DUYET.DMHID = MH.PK_SEQ     "
				+ "	 		WHERE MHSP.SANPHAM_FK is not null " + // and
																	// MH.NGAYMUA
																	// >='"+tungay+"'
																	// AND
																	// MH.NGAYMUA
																	// <='"+denngay+"'
																	// "+
				"	 ) DATHANG   " + "	 LEFT  JOIN       " + "	 ( "
				+ "	 		SELECT NHSP.SANPHAM_FK AS SPID,NH.PK_SEQ AS NHID  ,ISNULL(NHSP.SOLUONGNHAN,0) AS SOLUONGNHAN , "
				+ "	 		MH.PK_SEQ AS MHID, NH.NGAYNHAN, NHSP.DONGIA, MH.SOPO AS POID,  NH.SOHOADON, HD.NGAYHOADON, MH.PK_SEQ, NHSP.DIENGIAI,TIENTE.TEN as TIENTE   "
				+ "	 		FROM ERP_NHANHANG NH     INNER JOIN ERP_NHANHANG_SANPHAM NHSP ON NH.PK_SEQ= NHSP.NHANHANG_FK     "
				+ "	 		INNER JOIN ERP_MUAHANG MH ON NH.MUAHANG_FK=MH.PK_SEQ     "
				+ "	 		LEFT JOIN ERP_SANPHAM SP ON SP.PK_SEQ=NHSP.SANPHAM_FK "
				+ "			inner join ERP_TIENTE TIENTE on MH.TIENTE_FK = TIENTE.PK_SEQ  "
				+ "	 		LEFT JOIN ERP_HOADONNCC_PHIEUNHAP HDPN ON  NH.PK_SEQ= HDPN.PHIEUNHAN_FK   "
				+ "	 		LEFT JOIN ERP_HOADONNCC HD ON  HD.PK_SEQ= HDPN.HOADONNCC_FK   "
				+ "	 		WHERE NHSP.SOLUONGNHAN >0 AND NH.TRANGTHAI not in(3,4) and NHSP.SANPHAM_FK is not null   " + // AND
																															// MH.NGAYMUA
																															// >='"+tungay+"'
																															// AND
																															// MH.NGAYMUA
																															// <='"+denngay+"'
																															// "+
				"	 		UNION ALL      " + "	 		SELECT  "
				+ "	 		NHSP.SANPHAM_FK AS SPID,NH.PK_SEQ AS NHID, "
				+ "	 		ISNULL(NHSP.SOLUONGNHAN,0) AS SOLUONGNHAN,  NHSP.MUAHANG_FK AS MHID, NH.NGAYNHAN, NHSP.DONGIA, MH.SOPO AS POID, "
				+ "	 		NH.SOHOADON, HD.NGAYHOADON  , MH.PK_SEQ ,TIENTE.TEN as TIENTE, NHSP.DIENGIAI       "
				+ "	 		FROM ERP_NHANHANG NH    "
				+ "	 		INNER JOIN ERP_NHANHANG_SANPHAM NHSP ON NH.PK_SEQ= NHSP.NHANHANG_FK     "
				+ "	 		INNER JOIN ERP_THUENHAPKHAU  TNK ON TNK.PK_SEQ=NH.SOTOKHAI_FK     "
				+ "	 		LEFT JOIN ERP_SANPHAM SP ON SP.PK_SEQ=NHSP.SANPHAM_FK     "
				+ "	 		INNER JOIN ERP_MUAHANG MH ON NHSP.MUAHANG_FK=MH.PK_SEQ    "
				+ "			inner join ERP_TIENTE TIENTE on MH.TIENTE_FK = TIENTE.PK_SEQ "
				+ "	 		INNER JOIN ERP_THUENHAPKHAU_HOADONNCC TNK_HD ON TNK.PK_SEQ= TNK_HD.THUENHAPKHAU_FK   "
				+ "	 		LEFT JOIN ERP_HOADONNCC HD ON HD.PK_SEQ= TNK_HD.HOADONNCC_FK   "
				+ "	 		WHERE NHSP.SOLUONGNHAN >0 AND NH.TRANGTHAI not in(3,4) and NHSP.SANPHAM_FK is not null  AND NH.MUAHANG_FK IS NULL  AND NHSP.MUAHANG_FK IS NOT NULL "
				+
				// " AND MH.NGAYMUA >='"+tungay+"' AND MH.NGAYMUA
				// <='"+denngay+"' "+
				"	 ) NHANHANG  ON   DATHANG.MHID=NHANHANG.MHID AND DATHANG.SPID= NHANHANG.SPID  ) " +

				"	 union all " +

				"	 (SELECT ISNULL(DATHANG.MHID,NHANHANG.MHID) AS MUAHANG_FK,ISNULL(DATHANG.SPID,NHANHANG.SPID) AS MASANPHAM,  "
				+ "	 DATHANG.NGAYMUA, DATHANG.POID,DATHANG.NCC as TENNCC, ISNULL(DATHANG.TEN,DATHANG.DIENGIAI)  AS TENSP , "
				+ "	  DATHANG.DONVI,   DATHANG.SOLUONG, DATHANG.DONGIA, (DATHANG.SOLUONG* DATHANG.DONGIA) as THANHTIEN,DATHANG.TIENTE, "
				+ "	 ISNULL(DATHANG.NGAYDENGHI,'') AS NGAYYEUCAUNHAN, ISNULL(NHANHANG.SOLUONGNHAN,0) AS SOLUONGDANHAN, "
				+ "	 NHANHANG.NHID, ISNULL(NHANHANG.NGAYNHAN,'') AS NGAYNHAN,  "
				+ "	 ISNULL(NHANHANG.DONGIA, DATHANG.DONGIA) AS DONGIANHAN, "
				+ "	 ISNULL(NHANHANG.SOHOADON,'') AS SOHOADON, "
				+ "	 ISNULL(NHANHANG.NGAYHOADON,'') AS NGAYHOADON,  DATHANG.NGUOITAO, "
				+ "	 DATHANG.DONVITHUCHIEN_FK,  DATHANG.NHACUNGCAP_FK,DATHANG.GHICHU      " + "	 FROM   " + "	 	( "
				+ "	 		SELECT MH.PK_SEQ AS MHID,  isnull(MHSP.SANPHAM_FK, MHSP.CHIPHI_FK) AS SPID, MH.NGAYTAO, MH.SOPO AS POID, ISNULL(NCC.TEN,'') AS NCC, "
				+ "	 		ISNULL(SP.MA,'') AS ITEM, MH.NGAYMUA   , MHSP.DONVI, MHSP.SOLUONG,    "
				+ "	 		MHSP.DONGIA, (MHSP.DONGIA*MHSP.SOLUONG) AS THANHTIEN,TIENTE.TEN as TIENTE, ISNULL( MH.SOTHAMCHIEU,'')AS SPYC,  "
				+ "	 		ISNULL(MHSP.NGAYNHAN,'') AS NGAYDENGHI,    SUBSTRING(CAST(MHSP.NGAYNHAN AS VARCHAR(10)),6,2)  AS THANGDAT , "
				+ "	 		DVTH.MA, SP.TEN, MHSP.DIENGIAI, MH.NGUOITAO, SP.LOAISANPHAM_FK , MH.DONVITHUCHIEN_FK, MH.NHACUNGCAP_FK,MH.GHICHU "
				+ "	 		FROM ERP_MUAHANG MH     "
				+ "	 		INNER JOIN ERP_MUAHANG_SP MHSP ON MH.PK_SEQ=MHSP.MUAHANG_FK      "
				+ "	 		INNER JOIN ERP_NHACUNGCAP NCC ON MH.NHACUNGCAP_FK=NCC.PK_SEQ "
				+ "			inner join ERP_TIENTE TIENTE on MH.TIENTE_FK = TIENTE.PK_SEQ "
				+ "	 		LEFT JOIN ERP_SANPHAM SP ON SP.PK_SEQ=MHSP.SANPHAM_FK "
				+ "	 		INNER JOIN ERP_DONVITHUCHIEN DVTH ON MH.DONVITHUCHIEN_FK= DVTH.PK_SEQ    "
				+ "	 		LEFT JOIN(  SELECT 	MUAHANG_FK AS DMHID,  " + "	 		CASE WHEN SUM(QUYETDINH) > 0 THEN  "
				+ "	 		(CASE WHEN  ( SELECT SUM(TRANGTHAI)    FROM ERP_DUYETMUAHANG  	 "
				+ "	 		WHERE MUAHANG_FK = DUYETMUAHANG.MUAHANG_FK AND QUYETDINH = 1) > 0 THEN 0  ELSE 1  END)	  "
				+ "	 		ELSE COUNT(TRANGTHAI) - SUM(TRANGTHAI) 	  END AS DUYET    "
				+ "	 		FROM ERP_DUYETMUAHANG DUYETMUAHANG  "
				+ "	 		GROUP BY MUAHANG_FK  ) DUYET ON DUYET.DMHID = MH.PK_SEQ     "
				+ "	 		WHERE MHSP.SANPHAM_FK is null " + // and MH.NGAYMUA
																// >='"+tungay+"'
																// AND
																// MH.NGAYMUA
																// <='"+denngay+"'
																// "+
				"	 ) DATHANG   " + "	 LEFT  JOIN       " + "	 ( "
				+ "	 		SELECT isnull(NHSP.SANPHAM_FK, NHSP.CHIPHI_FK) AS SPID,NH.PK_SEQ AS NHID  ,ISNULL(NHSP.SOLUONGNHAN,0) AS SOLUONGNHAN , "
				+ "	 		MH.PK_SEQ AS MHID, NH.NGAYNHAN, NHSP.DONGIA, TIENTE.TEN as TIENTE,MH.SOPO AS POID,  NH.SOHOADON, HD.NGAYHOADON, MH.PK_SEQ, NHSP.DIENGIAI   "
				+ "	 		FROM ERP_NHANHANG NH     INNER JOIN ERP_NHANHANG_SANPHAM NHSP ON NH.PK_SEQ= NHSP.NHANHANG_FK     "
				+ "	 		INNER JOIN ERP_MUAHANG MH ON NH.MUAHANG_FK=MH.PK_SEQ "
				+ "			inner join ERP_TIENTE TIENTE on MH.TIENTE_FK = TIENTE.PK_SEQ     "
				+ "	 		LEFT JOIN ERP_SANPHAM SP ON SP.PK_SEQ=NHSP.SANPHAM_FK   "
				+ "	 		LEFT JOIN ERP_HOADONNCC_PHIEUNHAP HDPN ON  NH.PK_SEQ= HDPN.PHIEUNHAN_FK   "
				+ "	 		LEFT JOIN ERP_HOADONNCC HD ON  HD.PK_SEQ= HDPN.HOADONNCC_FK   "
				+ "	 		WHERE NHSP.SOLUONGNHAN >0 AND NH.TRANGTHAI not in(3,4) and NHSP.SANPHAM_FK is null " + // AND
																													// MH.NGAYMUA
																													// >='"+tungay+"'
																													// AND
																													// MH.NGAYMUA
																													// <='"+denngay+"'
																													// "+
				"	 		UNION ALL      " + "	 		SELECT  "
				+ "	 		isnull(NHSP.SANPHAM_FK, NHSP.CHIPHI_FK) AS SPID,NH.PK_SEQ AS NHID, "
				+ "	 		ISNULL(NHSP.SOLUONGNHAN,0) AS SOLUONGNHAN,  NHSP.MUAHANG_FK AS MHID, NH.NGAYNHAN, NHSP.DONGIA,TIENTE.TEN as TIENTE, MH.SOPO AS POID, "
				+ "	 		NH.SOHOADON, HD.NGAYHOADON  , MH.PK_SEQ , NHSP.DIENGIAI       "
				+ "	 		FROM ERP_NHANHANG NH    "
				+ "	 		INNER JOIN ERP_NHANHANG_SANPHAM NHSP ON NH.PK_SEQ= NHSP.NHANHANG_FK     "
				+ "	 		INNER JOIN ERP_THUENHAPKHAU  TNK ON TNK.PK_SEQ=NH.SOTOKHAI_FK     "
				+ "	 		LEFT JOIN ERP_SANPHAM SP ON SP.PK_SEQ=NHSP.SANPHAM_FK     "
				+ "	 		INNER JOIN ERP_MUAHANG MH ON NHSP.MUAHANG_FK=MH.PK_SEQ  "
				+ "			inner join ERP_TIENTE TIENTE on MH.TIENTE_FK = TIENTE.PK_SEQ   "
				+ "	 		INNER JOIN ERP_THUENHAPKHAU_HOADONNCC TNK_HD ON TNK.PK_SEQ= TNK_HD.THUENHAPKHAU_FK   "
				+ "	 		LEFT JOIN ERP_HOADONNCC HD ON HD.PK_SEQ= TNK_HD.HOADONNCC_FK   "
				+ "	 		WHERE NHSP.SOLUONGNHAN >0 AND NH.TRANGTHAI not in(3,4) and NHSP.SANPHAM_FK is null  AND NH.MUAHANG_FK IS NULL  AND NHSP.MUAHANG_FK IS NOT NULL   "
				+
				// " AND MH.NGAYMUA >='"+tungay+"' AND MH.NGAYMUA
				// <='"+denngay+"' "+
				"	 ) NHANHANG  "
				+ "	 ON   DATHANG.MHID=NHANHANG.MHID AND DATHANG.SPID= NHANHANG.SPID   and DATHANG.DIENGIAI=NHANHANG.DIENGIAI) "
				+ "	 )A WHERE 1=1  and A.NGAYYEUCAUNHAN < CONVERT(VARCHAR(10),DATEADD(DAY,10,GETDATE()),126) and (A.SOLUONG-A.SOLUONGDANHAN) > 0 ";

		if (obj.getNguoitaoIds().trim().length() > 0) {
			query += " and A.NGUOITAO = '" + obj.getNguoitaoIds() + "' ";
		}

		if (obj.getDvthId().length() > 0) {
			query += " and A.donvithuchien_fk = '" + obj.getDvthId() + "'";
		}

		if (obj.getNspIds().length() > 0) {
			query += " and A.LOAISANPHAM_FK in (" + obj.getNspIds() + ")";
		}

		if (obj.getNccIds().length() > 0) {
			query += " and A.NHACUNGCAP_FK in (" + obj.getNccIds() + ")";
		}

		query += " ORDER BY A.MUAHANG_FK, A.SOHOADON ";

		System.out.println("Theo dõi nhắc nhở nhận hàng 2: " + query);
		ResultSet rs = db.get(query);

		// --- đổ dữ liệu ---//

		int index = 6;
		try {
			// CHÈN VÀO EXCEL
			int indexHeader = index - 1;
			Cells cells = worksheet.getCells();

			// LẤY NGÀY HIỆN TẠI & 10 NGÀY TIẾP THEO

			DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			Calendar c = Calendar.getInstance();
			c.setTime(new Date());
			c.add(Calendar.DATE, 10);

			Cell cell = cells.getCell("A2");
			cell.setValue(" Từ ngày :  " + dateFormat.format(Calendar.getInstance().getTime()) + " 			Đến ngày : "
					+ dateFormat.format(c.getTime()));

			/*
			 * cell = cells.getCell("A3"); cell.setValue("Người tạo: "
			 * +util.getTenNhaPP()); cell = cells.getCell("A4"); cell.setValue(
			 * "Đơn vị thực hiện: " ); cell = cells.getCell("A5");
			 * cell.setValue("Loại sản phẩm: "); cell = cells.getCell("A6");
			 * cell.setValue("Nhà cung cấp: ");
			 */

			cell = null;
			Style style = null;

			if (obj.getPivot().equals("1")) {
				cell = cells.getCell("BA" + String.valueOf(indexHeader));
				cell.setValue("MUAHANG_FK");
				style = cell.getStyle();
				style.setHAlignment(TextAlignmentType.CENTER);
				cell.setStyle(style);

				cell = cells.getCell("BB" + String.valueOf(indexHeader));
				cell.setValue("NGAYMUA");
				style = cell.getStyle();
				style.setHAlignment(TextAlignmentType.LEFT);
				cell.setStyle(style);

				cell = cells.getCell("BC" + String.valueOf(indexHeader));
				cell.setValue("TENNCC");
				style = cell.getStyle();
				style.setHAlignment(TextAlignmentType.LEFT);
				cell.setStyle(style);

				cell = cells.getCell("BD" + String.valueOf(indexHeader));
				cell.setValue("MASANPHAM");
				style = cell.getStyle();
				style.setHAlignment(TextAlignmentType.LEFT);
				cell.setStyle(style);

				cell = cells.getCell("BE" + String.valueOf(indexHeader));
				cell.setValue("TENSP");
				style = cell.getStyle();
				style.setHAlignment(TextAlignmentType.RIGHT);
				cell.setStyle(style);

				cell = cells.getCell("BF" + String.valueOf(indexHeader));
				cell.setValue("DONVI");
				style = cell.getStyle();
				style.setHAlignment(TextAlignmentType.RIGHT);
				cell.setStyle(style);

				cell = cells.getCell("BG" + String.valueOf(indexHeader));
				cell.setValue("SOLUONG");
				style = cell.getStyle();
				style.setHAlignment(TextAlignmentType.RIGHT);
				cell.setStyle(style);

				cell = cells.getCell("BH" + String.valueOf(indexHeader));
				cell.setValue("DONGIA");
				style = cell.getStyle();
				style.setHAlignment(TextAlignmentType.LEFT);
				cell.setStyle(style);

				cell = cells.getCell("BI" + String.valueOf(indexHeader));
				cell.setValue("THANHTIEN");
				style = cell.getStyle();
				style.setHAlignment(TextAlignmentType.RIGHT);
				cell.setStyle(style);

				cell = cells.getCell("BJ" + String.valueOf(indexHeader));
				cell.setValue("TIENTE");
				style = cell.getStyle();
				style.setHAlignment(TextAlignmentType.RIGHT);
				cell.setStyle(style);

				cell = cells.getCell("BK" + String.valueOf(indexHeader));
				cell.setValue("NGAYYEUCAUNHAN");
				style = cell.getStyle();
				style.setHAlignment(TextAlignmentType.RIGHT);
				cell.setStyle(style);

				cell = cells.getCell("BL" + String.valueOf(indexHeader));
				cell.setValue("NGAYNHAN");
				style = cell.getStyle();
				style.setHAlignment(TextAlignmentType.LEFT);
				cell.setStyle(style);

				cell = cells.getCell("BM" + String.valueOf(indexHeader));
				cell.setValue("SOLUONGNHAN");
				style = cell.getStyle();
				style.setHAlignment(TextAlignmentType.LEFT);
				cell.setStyle(style);

				cell = cells.getCell("BN" + String.valueOf(indexHeader));
				cell.setValue("SOLUONGCONLAI");
				style = cell.getStyle();
				style.setHAlignment(TextAlignmentType.CENTER);
				cell.setStyle(style);

				cell = cells.getCell("BO" + String.valueOf(indexHeader));
				cell.setValue("GHICHU");
				style = cell.getStyle();
				style.setHAlignment(TextAlignmentType.CENTER);
				cell.setStyle(style);

			} else {

				cell = cells.getCell("A" + String.valueOf(indexHeader));
				cell.setValue("MUAHANG_FK");
				style = cell.getStyle();
				style.setHAlignment(TextAlignmentType.CENTER);
				cell.setStyle(style);

				cell = cells.getCell("B" + String.valueOf(indexHeader));
				cell.setValue("NGAYMUA");
				style = cell.getStyle();
				style.setHAlignment(TextAlignmentType.LEFT);
				cell.setStyle(style);

				cell = cells.getCell("C" + String.valueOf(indexHeader));
				cell.setValue("TENNCC");
				style = cell.getStyle();
				style.setHAlignment(TextAlignmentType.LEFT);
				cell.setStyle(style);

				cell = cells.getCell("D" + String.valueOf(indexHeader));
				cell.setValue("MASANPHAM");
				style = cell.getStyle();
				style.setHAlignment(TextAlignmentType.LEFT);
				cell.setStyle(style);

				cell = cells.getCell("E" + String.valueOf(indexHeader));
				cell.setValue("TENSP");
				style = cell.getStyle();
				style.setHAlignment(TextAlignmentType.RIGHT);
				cell.setStyle(style);

				cell = cells.getCell("F" + String.valueOf(indexHeader));
				cell.setValue("DONVI");
				style = cell.getStyle();
				style.setHAlignment(TextAlignmentType.RIGHT);
				cell.setStyle(style);

				cell = cells.getCell("G" + String.valueOf(indexHeader));
				cell.setValue("SOLUONG");
				style = cell.getStyle();
				style.setHAlignment(TextAlignmentType.RIGHT);
				cell.setStyle(style);

				cell = cells.getCell("H" + String.valueOf(indexHeader));
				cell.setValue("DONGIA");
				style = cell.getStyle();
				style.setHAlignment(TextAlignmentType.LEFT);
				cell.setStyle(style);

				cell = cells.getCell("I" + String.valueOf(indexHeader));
				cell.setValue("THANHTIEN");
				style = cell.getStyle();
				style.setHAlignment(TextAlignmentType.RIGHT);
				cell.setStyle(style);

				cell = cells.getCell("J" + String.valueOf(indexHeader));
				cell.setValue("TIENTE");
				style = cell.getStyle();
				style.setHAlignment(TextAlignmentType.RIGHT);
				cell.setStyle(style);

				cell = cells.getCell("K" + String.valueOf(indexHeader));
				cell.setValue("NGAYYEUCAUNHAN");
				style = cell.getStyle();
				style.setHAlignment(TextAlignmentType.RIGHT);
				cell.setStyle(style);

				cell = cells.getCell("L" + String.valueOf(indexHeader));
				cell.setValue("NGAYNHAN");
				style = cell.getStyle();
				style.setHAlignment(TextAlignmentType.LEFT);
				cell.setStyle(style);

				cell = cells.getCell("M" + String.valueOf(indexHeader));
				cell.setValue("SOLUONGNHAN");
				style = cell.getStyle();
				style.setHAlignment(TextAlignmentType.LEFT);
				cell.setStyle(style);

				cell = cells.getCell("N" + String.valueOf(indexHeader));
				cell.setValue("SOLUONGCONLAI");
				style = cell.getStyle();
				style.setHAlignment(TextAlignmentType.CENTER);
				cell.setStyle(style);

				cell = cells.getCell("O" + String.valueOf(indexHeader));
				cell.setValue("GHICHU");
				style = cell.getStyle();
				style.setHAlignment(TextAlignmentType.CENTER);
				cell.setStyle(style);
			}

			while (rs.next()) {
				cell = null;
				style = null;
				NumberFormat formatter = new DecimalFormat("#,###,###.###");

				String MUAHANG_FK = rs.getString("MUAHANG_FK");
				String NGAYMUA = rs.getString("NGAYMUA");
				String TENNCC = rs.getString("TENNCC");
				String MASANPHAM = rs.getString("MASANPHAM");
				String TENSP = rs.getString("TENSP");
				String DONVI = rs.getString("DONVI");
				String SOLUONG = rs.getString("SOLUONG");
				String DONGIA = rs.getString("DONGIA");
				String THANHTIEN = rs.getString("THANHTIEN");
				String TIENTE = rs.getString("TIENTE");
				String NGAYYEUCAUNHAN = rs.getString("NGAYYEUCAUNHAN");
				String NGAYNHAN = rs.getString("NGAYNHAN");
				String SOLUONGNHAN = rs.getString("SOLUONGDANHAN");
				String SOLUONGCONLAI = rs.getString("SOLUONGCONLAI");
				String GHICHU = rs.getString("GHICHU");

				// CHÈN VÀO EXCEL

				if (obj.getPivot().equals("1")) {

					cell = cells.getCell("BA" + String.valueOf(index));
					cell.setValue(MUAHANG_FK);
					style = cell.getStyle();
					style.setHAlignment(TextAlignmentType.CENTER);
					cell.setStyle(style);

					cell = cells.getCell("BB" + String.valueOf(index));
					cell.setValue(NGAYMUA);
					style = cell.getStyle();
					style.setHAlignment(TextAlignmentType.LEFT);
					cell.setStyle(style);

					cell = cells.getCell("BC" + String.valueOf(index));
					cell.setValue(TENNCC);
					style = cell.getStyle();
					style.setHAlignment(TextAlignmentType.LEFT);
					cell.setStyle(style);

					cell = cells.getCell("BD" + String.valueOf(index));
					cell.setValue(MASANPHAM);
					style = cell.getStyle();
					style.setHAlignment(TextAlignmentType.LEFT);
					cell.setStyle(style);

					cell = cells.getCell("BE" + String.valueOf(index));
					cell.setValue(TENSP);
					style = cell.getStyle();
					style.setHAlignment(TextAlignmentType.RIGHT);
					cell.setStyle(style);

					cell = cells.getCell("BF" + String.valueOf(index));
					
					cell.setValue(DONVI);
					style = cell.getStyle();
					style.setHAlignment(TextAlignmentType.RIGHT);
					cell.setStyle(style);

					cell = cells.getCell("BG" + String.valueOf(index));
					cell.setValue(SOLUONG);
					style = cell.getStyle();
					style.setHAlignment(TextAlignmentType.RIGHT);
					cell.setStyle(style);

					cell = cells.getCell("BH" + String.valueOf(index));
					cell.setValue(DONGIA);
					style = cell.getStyle();
					style.setHAlignment(TextAlignmentType.LEFT);
					cell.setStyle(style);

					cell = cells.getCell("BI" + String.valueOf(index));
					cell.setValue(THANHTIEN);
					style = cell.getStyle();
					style.setHAlignment(TextAlignmentType.RIGHT);
					cell.setStyle(style);

					cell = cells.getCell("BJ" + String.valueOf(index));
					cell.setValue(TIENTE);
					style = cell.getStyle();
					style.setHAlignment(TextAlignmentType.RIGHT);
					cell.setStyle(style);

					cell = cells.getCell("BK" + String.valueOf(index));
					cell.setValue(NGAYYEUCAUNHAN);
					style = cell.getStyle();
					style.setHAlignment(TextAlignmentType.RIGHT);
					cell.setStyle(style);

					cell = cells.getCell("BL" + String.valueOf(index));
					cell.setValue(NGAYNHAN);
					style = cell.getStyle();
					style.setHAlignment(TextAlignmentType.LEFT);
					cell.setStyle(style);

					cell = cells.getCell("BM" + String.valueOf(index));
					cell.setValue(SOLUONGNHAN);
					style = cell.getStyle();
					style.setHAlignment(TextAlignmentType.LEFT);
					cell.setStyle(style);

					cell = cells.getCell("BN" + String.valueOf(index));
					cell.setValue(SOLUONGCONLAI);
					style = cell.getStyle();
					style.setHAlignment(TextAlignmentType.CENTER);
					cell.setStyle(style);

					cell = cells.getCell("BO" + String.valueOf(index));
					cell.setValue(GHICHU);
					style = cell.getStyle();
					style.setHAlignment(TextAlignmentType.CENTER);
					cell.setStyle(style);
				} else {
					cell = cells.getCell("A" + String.valueOf(index));
					cell.setValue(MUAHANG_FK);
					style = cell.getStyle();
					style.setHAlignment(TextAlignmentType.CENTER);
					cell.setStyle(style);

					cell = cells.getCell("B" + String.valueOf(index));
					cell.setValue(NGAYMUA);
					style = cell.getStyle();
					style.setHAlignment(TextAlignmentType.LEFT);
					cell.setStyle(style);

					cell = cells.getCell("C" + String.valueOf(index));
					cell.setValue(TENNCC);
					style = cell.getStyle();
					style.setHAlignment(TextAlignmentType.LEFT);
					cell.setStyle(style);

					cell = cells.getCell("D" + String.valueOf(index));
					cell.setValue(MASANPHAM);
					style = cell.getStyle();
					style.setHAlignment(TextAlignmentType.LEFT);
					cell.setStyle(style);

					cell = cells.getCell("E" + String.valueOf(index));
					cell.setValue(TENSP);
					style = cell.getStyle();
					style.setHAlignment(TextAlignmentType.RIGHT);
					cell.setStyle(style);

					cell = cells.getCell("F" + String.valueOf(index));
					cell.setValue(DONVI);
					style = cell.getStyle();
					style.setHAlignment(TextAlignmentType.RIGHT);
					cell.setStyle(style);

					cell = cells.getCell("G" + String.valueOf(index));
					cell.setValue(SOLUONG);
					style = cell.getStyle();
					style.setHAlignment(TextAlignmentType.RIGHT);
					cell.setStyle(style);

					cell = cells.getCell("H" + String.valueOf(index));
					cell.setValue(DONGIA);
					style = cell.getStyle();
					style.setHAlignment(TextAlignmentType.LEFT);
					cell.setStyle(style);

					cell = cells.getCell("I" + String.valueOf(index));
					cell.setValue(THANHTIEN);
					style = cell.getStyle();
					style.setHAlignment(TextAlignmentType.RIGHT);
					cell.setStyle(style);

					cell = cells.getCell("J" + String.valueOf(index));
					cell.setValue(TIENTE);
					style = cell.getStyle();
					style.setHAlignment(TextAlignmentType.RIGHT);
					cell.setStyle(style);

					cell = cells.getCell("K" + String.valueOf(index));
					cell.setValue(NGAYYEUCAUNHAN);
					style = cell.getStyle();
					style.setHAlignment(TextAlignmentType.RIGHT);
					cell.setStyle(style);

					cell = cells.getCell("L" + String.valueOf(index));
					cell.setValue(NGAYNHAN);
					style = cell.getStyle();
					style.setHAlignment(TextAlignmentType.LEFT);
					cell.setStyle(style);

					cell = cells.getCell("M" + String.valueOf(index));
					cell.setValue(SOLUONGNHAN);
					style = cell.getStyle();
					style.setHAlignment(TextAlignmentType.LEFT);
					cell.setStyle(style);

					cell = cells.getCell("N" + String.valueOf(index));
					cell.setValue(SOLUONGCONLAI);
					style = cell.getStyle();
					style.setHAlignment(TextAlignmentType.CENTER);
					cell.setStyle(style);

					cell = cells.getCell("O" + String.valueOf(index));
					cell.setValue(GHICHU);
					style = cell.getStyle();
					style.setHAlignment(TextAlignmentType.CENTER);
					cell.setStyle(style);
				}

				index++;

			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			db.shutDown();
		}

	}

	private String getdatetime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
}
