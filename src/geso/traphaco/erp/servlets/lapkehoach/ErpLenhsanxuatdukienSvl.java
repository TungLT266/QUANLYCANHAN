package geso.traphaco.erp.servlets.lapkehoach;

import geso.traphaco.erp.beans.kehoachsanxuatvamuahang.KeHoachSanXuatVaMuaHang;
import geso.traphaco.erp.beans.kehoachsanxuatvamuahang.imp.IKeHoachSanXuatVaMuaHang;
import geso.traphaco.erp.beans.kehoachsanxuatvamuahang.imp.ISanPham;
import geso.traphaco.erp.beans.lapkehoach.*;
import geso.traphaco.erp.beans.lapkehoach.imp.*;
import geso.traphaco.erp.beans.lenhsanxuatgiay.ILenhSXCongDoan;
import geso.traphaco.erp.beans.lenhsanxuatgiay.imp.LenhSXCongDoan;
import geso.traphaco.center.servlets.report.ReportAPI;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.db.sql.dbutils;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aspose.cells.BorderLineType;
import com.aspose.cells.BorderType;
import com.aspose.cells.Cell;
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
import com.oreilly.servlet.MultipartRequest;

public class ErpLenhsanxuatdukienSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	PrintWriter out;
	private static final String UPLOAD_DIRECTORY = "C:\\upload\\excel\\";

	public ErpLenhsanxuatdukienSvl() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		out = response.getWriter();
		HttpSession session = request.getSession();
		String ctyId = (String) session.getAttribute("congtyId");
		String task = request.getParameter("task");
		if (task == null) {
			task = "";
		}

		if (task.trim().equals("chuyenthanhProc")) // Ajax
		{
			String userId = (String) session.getAttribute("userId");

			String id = request.getParameter("id");
			String kbsxId = request.getParameter("kbsxId");

			System.out.println("___Id la: " + id);

			id = id.substring(3, id.length());

			dbutils db = new dbutils();

			String query = "select trangthai from ERP_LENHSANXUATDUKIEN_SP_CHITIET where pk_seq = '" + id + "'";
			ResultSet rsCheck = db.get(query);

			String trangthai = "";
			if (rsCheck != null) {
				try {
					if (rsCheck.next()) {
						trangthai = rsCheck.getString("trangthai");
					}
					rsCheck.close();
				} catch (Exception e) {
				}
			}

			if (trangthai.equals("1")) 
			{
				out.write("PlainOrder này đã chuyển thành Production Order rồi.");
				return;
			} 
			else 
			{
				try 
				{
					db.getConnection().setAutoCommit(false);

					query = "insert ERP_LENHSANXUAT_GIAY(congty_fk, lenhsanxuatdukien_fk, kichbansanxuat_fk, sanpham_fk, ngaybatdau, ngayketthuc, soluong, nhamay_fk, trangthai, ngaytao, nguoitao, ngaysua, nguoisua)  "
							+ "select " + ctyId + ", '" + id + "', '" + kbsxId
							+ "', a.sanpham_fk, a.ngay, a.ngayketthuc, a.soluong, b.nhamay_fk, '0', '" + getDateTime() + "', '"
							+ userId + "', '" + getDateTime() + "', '" + userId + "'  "
							+ "from ERP_LENHSANXUATDUKIEN_SP_CHITIET a inner join ERP_NHAMAY_KHOTT b on a.khott_fk = b.khott_fk   "
							+ "where a.pk_seq = '" + id + "'";

					if (!db.update(query)) {
						out.write("1.Không thể chuyển sang Production Order.");
						System.out.println("Lỗi: " + query);
						db.getConnection().rollback();
						return;
					}

					query = "update ERP_LENHSANXUATDUKIEN_SP_CHITIET set trangthai = '1' where pk_seq = '" + id + "'";

					if (!db.update(query)) {
						out.write("2.Không thể chuyển sang Production Order.");
						db.getConnection().rollback();
						return;
					}

					// Tra ve ProductionOrder
					query = "select CAST( IDENT_CURRENT('ERP_LENHSANXUAT_GIAY') as varchar(20)) as ProId";
					ResultSet rsProc = db.get(query);
					String procId = "";
					if (rsProc.next()) {
						procId = rsProc.getString("ProId");
						rsProc.close();
					}

					out.write(procId);

					db.getConnection().commit();
					db.getConnection().setAutoCommit(true);
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
					out.write("3.Không thể chuyển sang Production Order.");
				}
			}
			db.shutDown();
		} 
		else 
		{
			Utility util = new Utility();

			String querystring = request.getQueryString();

			String userId = util.getUserId(querystring);
			out.println(userId);

			if (userId.length() == 0)
				userId = util.antiSQLInspection(request.getParameter("userId"));

			IErpLenhsanxuatdkList lsxdklist = new ErpLenhsanxuatdkList();
			lsxdklist.setCtyId(ctyId);
			lsxdklist.setUserId(userId);

			String action = util.getAction(querystring);
			String id = util.getId(querystring);

			if (action.equals("delete")) {
				lsxdklist.delete(id);
			}

			lsxdklist.init();
			session.setAttribute("lsxdklist", lsxdklist);

			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpLenhSanXuatDuKien.jsp";
			response.sendRedirect(nextJSP);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html ; charset=UTF-8");
		String contentType = request.getContentType();
		HttpSession session = request.getSession();

		String userId = (String) session.getAttribute("userId");
		String ctyId = (String) session.getAttribute("congtyId");

		// out = response.getWriter();
		Utility util = new Utility();

		IErpLenhsanxuatdkList lsxdklist;
		lsxdklist = new ErpLenhsanxuatdkList();
		lsxdklist.setCtyId(ctyId);
		lsxdklist.setUserId(userId);

		getSearchQuery(request, lsxdklist);

		String action = request.getParameter("action");
		if (action == null) {
			action = "";
		}
		System.out.println("Action:" + action);
		if (action.equals("exportExcelKHSX")) {
			// in kế hoạch sản xuất và mua hàng - Trinh làm

			khoiTaoDuLieuDeIn(request, response);

			lsxdklist.setUserId(userId);
			String msg = "";
			lsxdklist.setMsg(msg);
			lsxdklist.init();

			session.setAttribute("lsxdklist", lsxdklist);
			session.setAttribute("userId", userId);

			response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpLenhSanXuatDuKien.jsp");
		} else {
			if ((contentType != null)
					&& (contentType.indexOf("multipart/form-data") >= 0)) {
				MultipartRequest multi = new MultipartRequest(request,
						UPLOAD_DIRECTORY, 20000000, "UTF-8");
				action = util.antiSQLInspection(multi.getParameter("action"));
				if (action == null)
					action = "";

				System.out.println("Action Request encrypt: " + action);
				lsxdklist = new ErpLenhsanxuatdkList();

				lsxdklist.setCtyId(ctyId);
				lsxdklist.setUserId(userId);

				// HÀM NÀY LẤY CÁC GIÁ TRỊ TỪ BROWSER
				String thang = util.antiSQLInspection(multi
						.getParameter("thang"));
				if (thang == null)
					thang = "";
				lsxdklist.setThang(thang);

				String nam = util.antiSQLInspection(multi.getParameter("nam"));
				if (nam == null)
					nam = "";
				lsxdklist.setNam(nam);

				Enumeration files = multi.getFileNames();
				String filename = "";

				while (files.hasMoreElements()) {
					String name = (String) files.nextElement();
					filename = multi.getFilesystemName(name);
				}

				System.out.println("1____READ EXCEL TOI DAY, FILE NAME......"
						+ filename);
				if (filename != null && filename.length() > 0) {
					System.out.println("___READ EXCEL FILE: ");
					this.readExcel(lsxdklist, UPLOAD_DIRECTORY + filename,
							response);

				}

				lsxdklist.init();
				session.setAttribute("lsxdklist", lsxdklist);
				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpLenhSanXuatDuKien.jsp";
				response.sendRedirect(nextJSP);
			} else {
				try {
					String msg = "";
					if (action.equals("chuyenthanhLSX")) {
						String kbsxId = util.antiSQLInspection(request
								.getParameter("kbsxId"));

						System.out.println("kbsxId: " + kbsxId);

						String Id = util.antiSQLInspection(request
								.getParameter("Id"));

						double soluong = 0;
						soluong = Double.parseDouble(request.getParameter(
								"soluongsx").replaceAll(",", ""));

						msg = ChuyenThanhLSX(userId, ctyId, soluong + "", Id,
								kbsxId);
						System.out.println("Ket qua chuyen: " + msg);

					} else if (action.equals("exportExcel")) {
						response.setContentType("application/xlsx");

						response.setHeader("Content-Disposition",
								"attachment; filename=DE_NGHI_SAN_XUAT_THANG.xlsx");
						lsxdklist = new ErpLenhsanxuatdkList();
						lsxdklist.setCtyId(ctyId);
						lsxdklist.setUserId(userId);

						String thang = util.antiSQLInspection(request
								.getParameter("thang"));
						if (thang == null)
							thang = ""
									+ Integer.parseInt(this.getDateTime()
											.substring(5, 7));
						lsxdklist.setThang(thang);

						String nam = util.antiSQLInspection(request
								.getParameter("nam"));
						if (nam == null)
							nam = ""
									+ Integer.parseInt(this.getDateTime()
											.substring(0, 4));
						lsxdklist.setNam(nam);

						lsxdklist.init();

						System.out.println("Nam:" + nam + ", thang:" + thang);

						OutputStream out = response.getOutputStream();

						if (!CreatePivotTable(out, response, request, lsxdklist)) {
							response.setContentType("text/html");
							PrintWriter writer = new PrintWriter(out);
							writer.print("Không có dữ liệu trong thời gian này");
							writer.close();
						}

					}

					lsxdklist.setUserId(userId);
					lsxdklist.setMsg(msg);
					lsxdklist.init();

					session.setAttribute("lsxdklist", lsxdklist);
					session.setAttribute("userId", userId);

					response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpLenhSanXuatDuKien.jsp");

				} catch (Exception e) {
					System.out.println(e.toString());
					return;

				}
			}
		}
	}

	private void readExcel(IErpLenhsanxuatdkList lsxdklist, String fileName,
			HttpServletResponse response) throws IOException {
		dbutils db = new dbutils();

		OutputStream out = response.getOutputStream();
		FileInputStream fstream = null;
		Workbook workbook = new Workbook();

		fstream = new FileInputStream(fileName);
		workbook.open(fstream);
		workbook.setFileFormatType(FileFormatType.EXCEL2007);

		Worksheets worksheets = workbook.getWorksheets();

		// DÒNG VÀ CỘT TÍNH TỪ 0

		System.out.println("BẮT ĐẦU ĐỌC FILE");

		String thang = lsxdklist.getThang();
		String nam = lsxdklist.getNam();
		String nam_thang = nam + "-"
				+ (thang.length() == 1 ? "0" + thang : thang) + "-";

		try {
			db.getConnection().setAutoCommit(false);
			String sql = "";

			Worksheet readSheet = worksheets.getSheet("CONG TY");

			// LẤY SỐ DÒNG CỦA SHEET ĐANG ĐỌC. TÍNH TỪ 0.
			int readSheetRow = readSheet.getLastRowIndex() + 1;
			System.out.println("so dong:" + readSheetRow);
			sql = "";
			for (int i = 8; i < readSheetRow; i++) {
				// CỘT 1 - ĐỀ NGHỊ SX ID
				// CỘT 7 - 10: SỐ LƯỢNG

				String ID = readSheet.getCell(i, 1).getStringValue();
				String T1 = readSheet.getCell(i, 7).getStringValue()
						.replaceAll(",", "").trim();
				if (T1.length() == 0)
					T1 = "0";

				String T2 = readSheet.getCell(i, 8).getStringValue()
						.replaceAll(",", "").trim();
				if (T2.length() == 0)
					T2 = "0";

				String T3 = readSheet.getCell(i, 9).getStringValue()
						.replaceAll(",", "").trim();
				if (T3.length() == 0)
					T3 = "0";

				String T4 = readSheet.getCell(i, 10).getStringValue()
						.replaceAll(",", "").trim();
				if (T4.length() == 0)
					T4 = "0";

				if (ID.length() > 0) {
					if (T1.length() > 0) {
						sql += "UPDATE ERP_LENHSANXUATDUKIEN SET SOLUONG = '"
								+ T1 + "', " + "NGAYBATDAU = '" + nam_thang
								+ "01', NGAYKETTHUC = '" + nam_thang + "07' "
								+ "WHERE PK_SEQ = " + ID + " ";

					} else if (T2.length() > 0) {

						sql += "UPDATE ERP_LENHSANXUATDUKIEN SET SOLUONG = '"
								+ T2 + "', " + "NGAYBATDAU = '" + nam_thang
								+ "08', NGAYKETTHUC = '" + nam_thang + "14' "
								+ "WHERE PK_SEQ = " + ID + " ";

					} else if (T3.length() > 0) {

						sql += "UPDATE ERP_LENHSANXUATDUKIEN SET SOLUONG = '"
								+ T3 + "', " + "NGAYBATDAU = '" + nam_thang
								+ "16', NGAYKETTHUC = '" + nam_thang + "21' "
								+ "WHERE PK_SEQ = " + ID + " ";

					} else if (T4.length() > 0) {

						sql += "UPDATE ERP_LENHSANXUATDUKIEN SET SOLUONG = '"
								+ T4 + "', " + "NGAYBATDAU = '" + nam_thang
								+ "22', NGAYKETTHUC = '" + nam_thang + "28' "
								+ "WHERE PK_SEQ = " + ID + " ";

					} else {
						sql += "DELETE ERP_LENHSANXUATDUKIEN WHERE PK_SEQ = "
								+ ID + " ";
					}
				}

			}
			System.out.println(sql);
			db.update(sql);

			System.out.println("KẾT THÚC ĐỌC SHEET: " + readSheet.getName());

			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} catch (SQLException e) {
			System.out.println(e.toString());
		}

		db.shutDown();

		System.out.println("KẾT THÚC ĐỌC FILE");

	}

	private boolean CreatePivotTable(OutputStream out,
			HttpServletResponse response, HttpServletRequest request,
			IErpLenhsanxuatdkList lsxdklist) throws Exception {
		FileInputStream fstream = null;
		Workbook workbook = new Workbook();

		fstream = new FileInputStream(getServletContext().getInitParameter(
				"path")
				+ "\\DE NGHI SAN XUAT_THANG.xlsx");

		workbook.open(fstream);
		workbook.setFileFormatType(FileFormatType.EXCEL2007);

		FillData(workbook, lsxdklist, "CONG TY");

		workbook.save(out);
		fstream.close();
		return true;
	}

	private void FillData(Workbook workbook, IErpLenhsanxuatdkList lsxdklist,
			String sheet) {
		dbutils db = new dbutils();
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		Cell cell;
		String thang = lsxdklist.getThang();
		String nam = lsxdklist.getNam();

		String query = "";

		worksheet = worksheets.getSheet(sheet);
		Cells cells = worksheet.getCells();
		cell = cells.getCell("A5");
		cell.setValue("Tháng " + (thang.length() == 1 ? "0" + thang : thang)
				+ " năm " + nam);

		ResultSet rs = lsxdklist.getLenhsanxuatdkRs();
		try {
			if (rs != null) {
				int row = 9;
				Style style;
				Font font;
				worksheet = worksheets.getSheet(sheet);
				cells = worksheet.getCells();
				int stt = 1;

				while (rs.next()) {
					cell = cells.getCell("A" + row);
					setCategoryStyle(cells, cell);

					style = cell.getStyle();
					style.setHAlignment(TextAlignmentType.CENTER);
					cell.setStyle(style);
					ReportAPI.getCellStyle(cell, Color.BLACK, true, 11, ""
							+ stt, 0);

					cell = cells.getCell("B" + row);
					style = cell.getStyle();
					style.setHAlignment(TextAlignmentType.CENTER);
					cell.setStyle(style);
					ReportAPI.getCellStyle(cell, Color.BLACK, true, 11,
							rs.getString("ID"), 0);

					cell = cells.getCell("C" + row);
					style = cell.getStyle();
					style.setHAlignment(TextAlignmentType.LEFT);
					cell.setStyle(style);
					ReportAPI.getCellStyle(cell, Color.BLACK, true, 11,
							rs.getString("MA"), 0);

					cell = cells.getCell("D" + row);
					style = cell.getStyle();
					style.setHAlignment(TextAlignmentType.LEFT);
					cell.setStyle(style);
					ReportAPI.getCellStyle(cell, Color.BLACK, true, 11,
							rs.getString("TEN"), 0);

					cell = cells.getCell("E" + row);
					style = cell.getStyle();
					style.setHAlignment(TextAlignmentType.CENTER);
					cell.setStyle(style);
					ReportAPI.getCellStyle(cell, Color.BLACK, true, 11,
							rs.getString("DONVI"), 0);

					cell = cells.getCell("F" + row);
					style = cell.getStyle();
					style.setHAlignment(TextAlignmentType.CENTER);
					cell.setStyle(style);
					ReportAPI.getCellStyle(cell, Color.BLACK, true, 11,
							rs.getString("SOLO"), 0);

					cell = cells.getCell("G" + row);
					style = cell.getStyle();
					style.setHAlignment(TextAlignmentType.CENTER);
					cell.setStyle(style);
					ReportAPI.getCellStyle(cell, Color.BLACK, true, 11,
							rs.getString("NHAMAY"), 0);

					cell = cells.getCell("H" + row);
					style = cell.getStyle();
					style.setHAlignment(TextAlignmentType.RIGHT);
					style.setNumber(3);
					font = style.getFont();
					font.setName("Times New Roman");
					font.setSize(11);
					style.setFont(font);
					cell.setStyle(style);
					cell.setValue(rs.getDouble("TUAN_1"));

					cell = cells.getCell("I" + row);
					style = cell.getStyle();
					style.setHAlignment(TextAlignmentType.RIGHT);
					style.setNumber(3);
					font = style.getFont();
					font.setName("Times New Roman");
					font.setSize(11);
					style.setFont(font);
					cell.setStyle(style);
					cell.setValue(rs.getDouble("TUAN_2"));

					cell = cells.getCell("J" + row);
					style = cell.getStyle();
					style.setHAlignment(TextAlignmentType.RIGHT);
					style.setNumber(3);
					font = style.getFont();
					font.setName("Times New Roman");
					font.setSize(11);
					style.setFont(font);
					cell.setStyle(style);
					cell.setValue(rs.getDouble("TUAN_3"));

					cell = cells.getCell("K" + row);
					style = cell.getStyle();
					style.setHAlignment(TextAlignmentType.RIGHT);
					style.setNumber(3);
					font = style.getFont();
					font.setName("Times New Roman");
					font.setSize(11);
					style.setFont(font);
					cell.setStyle(style);
					cell.setValue(rs.getDouble("TUAN_4"));

					row++;
					stt++;
				}
				rs.close();
			}
		} catch (java.sql.SQLException e) {
			System.out.println(e.toString());
		}

		db.shutDown();
	}

	public static void main(String args[]) {
		System.out.println("Begin... : ");
		ChuyenThanhLSX("100002", "100000", "100000", "176976", "100017");
	}

	private static String ChuyenThanhLSX(String userId, String ctyId,
			String soluongsx, String Id, String kbsxId) {
		// Lay kich ban
		String msg = "";

		dbutils db = new dbutils();
		try {

			db.getConnection().setAutoCommit(false);

			List<ILenhSXCongDoan> ListCongdoan = new ArrayList<ILenhSXCongDoan>();
			/************* B0 insert lenh san xuat ****************************/
			String query = " select PK_SEQ,khonhannguyenlieu_fk from ERP_KHOTT where TrangThai = '1' and loai in ('10') and congty_fk = '"
					+ ctyId
					+ "' and NHAMAY_FK=(select NhaMay_FK from  Erp_KichBanSanXuat_Giay where PK_SEQ="
					+ kbsxId + ")";
			ResultSet rs = db.get(query);
			// String khonguyenlieu="";
			// chỉ booked kho xuất,

			String khosanxuat = "NULL";

			while (rs.next()) {

				khosanxuat = rs.getString("pk_seq");

			}
			rs.close();

			String spId, ngaybatdau, ngaydukienHT, solo;

			String sql = "SELECT SANPHAM_FK AS SPID, NGAYBATDAU, NGAYKETTHUC, ISNULL(SOLO, '') AS SOLO FROM ERP_LENHSANXUATDUKIEN "
					+ "WHERE PK_SEQ = " + Id + "";

			System.out.println("Lay LSX Du kien: " + sql);
			rs = db.get(sql);
			rs.next();
			spId = rs.getString("SPID");
			ngaybatdau = rs.getString("NGAYBATDAU");
			ngaydukienHT = rs.getString("NGAYKETTHUC");
			solo = rs.getString("SOLO");

			String lsxdkId = Id;
			rs.close();

			sql = "UPDATE ERP_LENHSANXUATDUKIEN SET SANXUAT = ISNULL(SANXUAT, 0) + "
					+ soluongsx + " WHERE PK_SEQ = " + Id + "";
			db.update(sql);

			sql = " insert into ERP_LENHSANXUAT_GIAY(SOLO, khosanxuat_fk,lenhsanxuatdukien_fk, ngaybatdau, ngaydukienht, kichbansanxuat_fk, congty_fk, soluong, nhamay_fk, trangthai, ngaytao, nguoitao, ngaysua, nguoisua) "
					+ " select  '"
					+ solo
					+ "', "
					+ khosanxuat
					+ ", "
					+ lsxdkId
					+ ", '"
					+ ngaybatdau
					+ "', '"
					+ ngaydukienHT
					+ "' ,'"
					+ kbsxId
					+ "', '"
					+ ctyId
					+ "',  "
					+ soluongsx
					+ ", nhamay_fk  , '0', '"
					+ getDateTime()
					+ "', '"
					+ userId
					+ "', '"
					+ getDateTime()
					+ "', '"
					+ userId
					+ "' "
					+ " from erp_kichbansanxuat_giay "
					+ " where pk_seq = "
					+ kbsxId;

			System.out.println("Insert LenhSanXuat: " + sql);

			if (!db.update(sql)) {

				db.getConnection().rollback();
				return sql;
			}

			sql = "select IDENT_CURRENT('ERP_LENHSANXUAT_GIAY') as clId";
			rs = db.get(sql);
			rs.next();
			String lsxId = rs.getString("clId");
			rs.close();

			sql = "";
			/************* B1 Đưa vào lệnh sản xuất **************/

			/*
			 * sql=
			 * " select distinct nm.pk_seq as nhamayid, a.thutu, a.danhmucvattu_fk ,0 as spid , "
			 * +
			 * " A.DINHLUONG_FK,isnull(B.DINHTINH,'0') as dinhtinh ,isnull(a.kiemdinhchatluong,'0') as kiemdinhchatluong, b.pk_seq,b.diengiai,a.kichbansanxuat_fk,  "
			 * +
			 * " a.thutu,b.nhamay_fk,nm.manhamay,nm.tennhamay ,isnull( a.sanpham_fk,0) as sanpham_fk ,isnull(sp.ma,'') as masp,isnull(sp.ten,'') as tensp "
			 * + " from Erp_KichBanSanXuat_CongDoanSanXuat_Giay a  "+
			 * " inner join erp_kichbansanxuat_giay kb on kb.pk_seq= a.kichbansanxuat_fk "
			 * +
			 * " inner join Erp_CongDoanSanXuat_Giay b on a.congdoansanxuat_fk=b.pk_Seq "
			 * + " inner join erp_nhamay nm on nm.pk_seq=b.nhamay_fk  " +
			 * " left join  erp_danhmucvattu dmvt on dmvt.pk_seq=a.danhmucvattu_fk		 "
			 * + " LEFT join  erp_sanpham sp on sp.ma=dmvt.masanpham"+
			 * " where kichbansanxuat_fk='"+kbsxId+"' and dmvt.sudung = 0  "+
			 * " order by a.thutu ";
			 */

			sql = "\n select distinct sp.dvkd_fk , nm.pk_seq as nhamayid, a.thutu, a.danhmucvattu_fk ,0 as spid , "
					+ "\n A.DINHLUONG_FK,isnull(B.DINHTINH,'0') as dinhtinh , "
					+ "\n case when sp1.kiemtradinhluong ='1' and  a.danhmucvattu_fk IS   null then 1  when sp1.kiemtradinhtinh =1 and  a.danhmucvattu_fk IS   null then 1 else isnull(a.kiemdinhchatluong,'0')end  as kiemdinhchatluong  "
					+ "\n ,b.pk_seq,b.diengiai,a.kichbansanxuat_fk,  "
					+ "\n a.thutu,b.nhamay_fk,nm.manhamay,nm.tennhamay ,isnull( a.sanpham_fk,0) as sanpham_fk ,isnull(sp.ma,'') as masp,isnull(sp.ten,'') as tensp "
					+ "\n from Erp_KichBanSanXuat_CongDoanSanXuat_Giay a  "
					+ "\n inner join erp_kichbansanxuat_giay kb on kb.pk_seq= a.kichbansanxuat_fk "
					+ "\n inner join Erp_CongDoanSanXuat_Giay b on a.congdoansanxuat_fk=b.pk_Seq "
					+ "\n inner join erp_nhamay nm on nm.pk_seq=b.nhamay_fk  "
					+ "\n left join  erp_danhmucvattu dmvt on dmvt.pk_seq=a.danhmucvattu_fk		 "
					+ "\n LEFT join  erp_sanpham sp on sp.ma=dmvt.mavattu "
					+ "\n left join erp_sanpham sp1 on sp1.ma=kb.MaSanPham "
					+ "\n where kichbansanxuat_fk='"
					+ kbsxId
					+ "' and kb.MaSanPham = (select ma from erp_sanpham where pk_seq="
					+ spId + ") " + "\n order by a.thutu ";

			System.out.println("get Cong doan  : " + sql);
			rs = db.get(sql);
			while (rs.next()) {

				ILenhSXCongDoan lsxcd = new LenhSXCongDoan();
				lsxcd.setCongDoanId(rs.getString("pk_seq"));
				lsxcd.setDiengiai(rs.getString("diengiai"));
				lsxcd.Setkichbansanxuat(rs.getString("kichbansanxuat_fk"));
				lsxcd.setTrangthai("0");
				lsxcd.setActive("1");

				lsxcd.setNhaMayId(rs.getString("nhamay_fk"));
				lsxcd.setBomId(rs.getString("danhmucvattu_fk"));
				lsxcd.setThuTu(rs.getFloat("thutu"));
				lsxcd.setPhanXuong(rs.getString("manhamay"));
				lsxcd.setSanPham(rs.getString("masp") + "-"
						+ rs.getString("tensp"));
				lsxcd.setMaSp(rs.getString("masp"));

				lsxcd.setSpId(rs.getString("sanpham_fk"));

				lsxcd.SetKiemDinhCL(rs.getString("kiemdinhchatluong"));
				lsxcd.setSoLuong("0");
				ListCongdoan.add(lsxcd);

			}
			rs.close();

			// thuc hien save cong doan
			if (ListCongdoan.size() > 0) {

				for (int i = 0; i < ListCongdoan.size(); i++) {
					ILenhSXCongDoan congdoan = ListCongdoan.get(i);

					String trangthaicd = "";
					if (congdoan.getActive().equals("1")) {
						trangthaicd = "0";
					} else {
						trangthaicd = "2";
					}
					query = "insert into ERP_LENHSANXUAT_CONGDOAN_GIAY (lenhsanxuat_fk,kichban_fk,congdoan_fk,tinhtrang,soluong,THUTU,SANPHAM_FK,DANHMUCVATTU_FK,KIEMDINHCHATLUONG,MASANPHAM) values "
							+ " ( '"
							+ lsxId
							+ "','"
							+ kbsxId
							+ "','"
							+ congdoan.getCongDoanId()
							+ "','"
							+ trangthaicd
							+ "',"
							+ congdoan.getSoLuong()
							+ ","
							+ congdoan.getThutu()
							+ ","
							+ congdoan.getSpid()
							+ ","
							+ congdoan.getBomId()
							+ ",'"
							+ congdoan.getKiemDinhCL()
							+ "','"
							+ congdoan.getMaSp() + "')";

					System.out.println("Các Cong Doanh");
					if (!db.update(query)) {

						db.getConnection().rollback();
						return query;
					}

				}
			}

			sql = "insert into erp_lenhsanxuat_sanpham(lenhsanxuat_fk, sanpham_fk, soluong, danhmucvt_fk) "
					+ "SELECT  '"
					+ lsxId
					+ "','"
					+ spId
					+ "','"
					+ soluongsx
					+ "',(select top(1) pk_seq from ERP_DANHMUCVATTU where trangthai=1   and masanpham =(select ma from erp_sanpham where pk_seq = "
					+ spId + ") and congty_fk=" + ctyId + ") ";

			System.out.println("Chen LSX - SP: " + sql);
			if (!db.update(sql)) {

				db.getConnection().rollback();
				return sql;
			}

			/************* END **************************/
			/*
			 * sql=" update ERP_LENHSANXUATDUKIEN set "+
			 * " sanxuat= isnull((select SUM(lsx.SOLUONG) from ERP_LENHSANXUAT_GIAY LSX "
			 * +
			 * " where LSX.LENHSANXUATDUKIEN_FK=ERP_LENHSANXUATDUKIEN.PK_SEQ ),0) "
			 * + " WHERE ERP_LENHSANXUATDUKIEN.pk_seq ="+lsxdkId;
			 * System.out.println(sql); if(!db.update(sql)) { msg =
			 * "Không thể tạo mới Kichbansanxuat: " + sql;
			 * db.getConnection().rollback(); return msg; }
			 */

			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			db.shutDown();

			return msg;
		} catch (Exception e) {
			e.printStackTrace();

			msg = "Không thể kích hoạt lệnh sản xuất: " + e.getMessage();
			db.update("rollback");
			db.shutDown();
			return msg;
		}

	}

	private void getSearchQuery(HttpServletRequest request,
			IErpLenhsanxuatdkList obj) {
		Utility util = new Utility();

		String thang = util.antiSQLInspection(request.getParameter("thang"));
		if (thang == null)
			thang = "";
		obj.setThang(thang);

		String nam = util.antiSQLInspection(request.getParameter("nam"));
		if (nam == null)
			nam = "";
		obj.setNam(nam);

		String nmId = util.antiSQLInspection(request.getParameter("nmId"));
		if (nmId == null)
			nmId = "";
		obj.setNhamayId(nmId);

		String spId = util.antiSQLInspection(request.getParameter("spId"));
		if (spId == null)
			spId = "";
		obj.setSpId(spId);

		String loaihanghoa = util.antiSQLInspection(request
				.getParameter("loaihanghoa"));
		if (loaihanghoa == null)
			loaihanghoa = "";
		obj.setLoaihh(loaihanghoa);

	}

	private void setCategoryStyle(Cells cells, Cell cell) {
		Cell cell1 = cells.getCell("AZ1");
		Style style;
		style = cell1.getStyle();
		style.setBorderLine(BorderType.LEFT, BorderLineType.THIN);
		style.setBorderLine(BorderType.RIGHT, BorderLineType.THIN);
		cell.setStyle(style);
	}

	private static String getDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}

	// ---------------------EXCEL KẾ HOẠCH SẢN XUẤT VÀ MUA HÀNG THEO
	// THÁNG-------------------//
	// gán dữ liệu in
	private void khoiTaoDuLieuDeIn(HttpServletRequest request,
			HttpServletResponse response) {

		HttpSession session = request.getSession();
		Utility util = new Utility();

		IKeHoachSanXuatVaMuaHang obj = new KeHoachSanXuatVaMuaHang();
		String thang = util.antiSQLInspection(request.getParameter("thang"));
		if (thang == null)
			thang = "";
		obj.setThang(thang);

		String nam = util.antiSQLInspection(request.getParameter("nam"));
		if (nam == null)
			nam = "";
		obj.setNam(nam);

		String nmId = util.antiSQLInspection(request.getParameter("nmId"));
		if (nmId == null)
			nmId = "";
		obj.setNhaMay(nmId);

		String spId = util.antiSQLInspection(request.getParameter("spId"));
		if (spId == null)
			spId = "";
		obj.setSanPham(spId);

		String loaihanghoa = util.antiSQLInspection(request
				.getParameter("loaihanghoa"));
		if (loaihanghoa == null)
			loaihanghoa = "";
		obj.setLoaihangHoa(loaihanghoa);
		
		try {
			request.setCharacterEncoding("utf-8");

			response.setContentType("application/xlsm");
			response.setHeader("Content-Disposition",
					"attachment; filename=Kehoachsanxuat_muahang.xlsm");
			OutputStream out = response.getOutputStream();

			if (!ExportToExcel(out, obj)) {

				/*
				 * obj.setMsg("Lỗi không tạo được báo cáo !");
				 * session.setAttribute("obj", obj);
				 * session.setAttribute("userId", userId); String nextJSP =
				 * "/OPV/pages/Center/Bctop5Skus.jsp";
				 * response.sendRedirect(nextJSP);
				 */
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			request.getSession().setAttribute("errors", ex.getMessage());
		}

	}

	private Boolean ExportToExcel(OutputStream out, IKeHoachSanXuatVaMuaHang obj)
			throws Exception {
		try {

			FileInputStream fstream = new FileInputStream(getServletContext()
					.getInitParameter("path") + "\\Kehoachsanxuat_muahang.xlsm");
			Workbook workbook = new Workbook();
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
			 IKeHoachSanXuatVaMuaHang tamthoi ;
			int thang = Integer.parseInt(obj.getThang());
			int soSheet = 0;
			for (int i = thang; i <= 12; i++) // ve 2 sheet
			{
				tamthoi = new KeHoachSanXuatVaMuaHang();
				tamthoi.setThang(String.valueOf(i));
				tamthoi.setNam(obj.getNam());
				tamthoi.setLoaihangHoa(obj.getLoaihangHoa());
				tamthoi.setNhaMay(obj.getNhaMay());
				tamthoi.setSanPham(obj.getSanPham());
				tamthoi.setDieuKien();
			
				
				if(tamthoi.getLoaihangHoa().equals("1")){
					tamthoi.createRS();
				}
				else if(tamthoi.getLoaihangHoa().equals("2")){
					tamthoi.createRSGiaCong();
				}
				else{
					tamthoi.createRS();
					tamthoi.createRSGiaCong();
					tamthoi.createRSMuaHang();
				}
				
		
				TaoBaoCao(workbook, tamthoi, i,  thang);
				soSheet++;
			}
			workbook.save(out);
			fstream.close();
			return true;

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception(ex.getMessage());

		}

	}

	private String setQuy(String thang) {
		String quy = "";
		if (thang.equals("1") || thang.equals("2") || thang.equals("3")) {
			quy = "I";
		} else if (thang.equals("4") || thang.equals("5") || thang.equals("6")) {
			quy = "II";
		} else if (thang.equals("7") || thang.equals("8") || thang.equals("9")) {
			quy = "III";
		}
		if (thang.equals("10") || thang.equals("11") || thang.equals("12")) {
			quy = "IV";
		}

		return quy;
	}

	private void TaoBaoCao(Workbook workbook, IKeHoachSanXuatVaMuaHang obj,
			int sheetNum, int tuThang) throws Exception {
		try {
			
			
			Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet(sheetNum-1);
			if(sheetNum == 12){
				for(int i=0 ; i< tuThang-1; i++)
					worksheets.removeSheet(0);
			}
			
			worksheet.setName("Tháng " + sheetNum);
			Cells cells = worksheet.getCells();
			Cell cell;

			dbutils db = new dbutils();
			int runrate = 0;
			String date = getDateTime();
			String[] tachDay = date.split("-");
			cell = cells.getCell("C3");
			cell.setValue("Hà Nội, ngày "+tachDay[2]+" tháng "+tachDay[1]+" năm " + tachDay[0]);
			setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED, "6", false);
			
			// dien ngay thang lap bao cao va nguoi lap\
			cells.merge(3, 0, 3, 5);
			cell = cells.getCell("A4");
			cell.setValue("KẾ HOẠCH SẢN XUẤT & MUA HÀNG" + "\n" + "THÁNG "
					+ obj.getThang() + " NĂM " + obj.getNam());
			setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED, "10",
					true);

			cells.merge(4, 0, 4, 5);
			cell = cells.getCell("A5");

			cell.setValue("- Căn cứ vào nhu cầu thị trường, khả năng sản xuất và Kế hoạch Quí "
					+ setQuy(obj.getThang()) + " năm " + obj.getNam());
			setCellBorderStyle(cell, HorizontalAlignmentType.LEFT, "9", false);

			cells.merge(5, 0, 5, 5);
			cell = cells.getCell("A6");

			cell.setValue("- Tổng Giám đốc giao Kế hoạch sản xuất & mua hàng Tháng "
					+ obj.getThang() + "/" + obj.getNam() + " như sau:");
			setCellBorderStyle(cell, HorizontalAlignmentType.LEFT, "9", false);

			// --------------------- sẢN XUẤT---------------------//

			ResultSet rsSanXuat = obj.getRs();
			
			List<ISanPham> spList = obj.getListSP();

			int countRowSanXuat = 10;// xác định số dòng bắt đầu
			int column = 0;

			int mang[] = new int[10000];
			int k = 0;
			int dem = 0;
			int socottrongSql ;
			// xác định các nhà máy

			int demNhaMay = 0;
			int sttSP = 0;
			if(rsSanXuat != null){
				ResultSetMetaData rsmd = rsSanXuat.getMetaData();
				
			 socottrongSql = rsmd.getColumnCount();

				for(int j =0 ; j< spList.size(); j++) {
					ISanPham sp = spList.get(j);
					for (int i = 1; i <= socottrongSql-1; i++) {

						String loai = sp.getLoai();
						Color c = Color.WHITE;
						if (loai.equals("0")) {
							if (i == 1) {
								c = new Color(252, 213, 180);
								mang[k] = countRowSanXuat;
								k++;

								// gộp ô và gán giá trị
								cells.merge(countRowSanXuat,0 ,countRowSanXuat, 5);
								cell = cells.getCell(countRowSanXuat, 0);
								int stt = countRowSanXuat + 1;
								cell.setValue("                           "+k + ". "
										+ sp.getTenSanPham());
								setCellBorderStyle(cell,
										HorizontalAlignmentType.LEFT, loai, true);

								sttSP = 0; // set số thứ tự cho sản phẩm ở mỗi nhà
											// máy
							}
						} else {
							if (i == 1) {
								sttSP++;
							}

							cell = cells.getCell(countRowSanXuat, 0);
							cell.setValue(sttSP);
							setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED,
									loai, false);
							
							
							cell = cells.getCell(countRowSanXuat, i);
							
							if(i==3 || i==4){
								if(i == 3){
									double sl = Double.parseDouble(spList.get(j).getSoLuong());
									cell.setValue(sl);
									ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 41);
								}
								else{
									cell.setValue(spList.get(j).getSoLo());
									ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 41);
								}
								setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT,
										"1", false);
							
							}
							else{
								
								if(i == 2){
									cell.setValue(spList.get(j).getDonVi());
									setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED,
											loai, false);
								}
								else{
									if(i==1){
										cell.setValue(sp.getTenSanPham());
									}
									else{
										cell.setValue(sp.getGhiChu());
									}
									setCellBorderStyle(cell, HorizontalAlignmentType.LEFT,
										loai, false);
								}
							}
							
						}

					}
					countRowSanXuat++;
				}
			
			}
			
			// ------------------------------GIA CÔNG--------------------------------//
			cell = cells.getCell(countRowSanXuat, 0);
			cell.setValue("B.");
			setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED, "8", true);

			for(int j=1; j< 6; j++){
				cell = cells.getCell(countRowSanXuat,j);
				setCellBorderStyle(cell, HorizontalAlignmentType.LEFT, "8", true);
			}
			cells.merge(countRowSanXuat, 1, countRowSanXuat, 5);
			cell = cells.getCell(countRowSanXuat, 1);
			cell.setValue("GIA CÔNG");
			setCellBorderStyle(cell, HorizontalAlignmentType.LEFT, "8", true);
			int countRowGiaCong = countRowSanXuat + 1;
			sttSP = 0;
			ResultSet rsGiaCong = obj.getRsGiaCong();
			
			List<ISanPham> spListGiaCong = obj.getListSPGiaCong();
			if(rsGiaCong != null){
				ResultSetMetaData rsmd = rsGiaCong.getMetaData();
				
				 socottrongSql = rsmd.getColumnCount();
				for(int  j = 0; j <spListGiaCong.size(); j++) {
					ISanPham sp = spListGiaCong.get(j);
					for (int i = 1; i <= socottrongSql-1; i++) {
						if (i == 1) {
							sttSP++;
						}

						cell = cells.getCell(countRowGiaCong, 0);
						cell.setValue(sttSP);
						setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED,
								"1", false);

						cell = cells.getCell(countRowGiaCong, i);
						if(i==3 || i==4){
							if(i == 3){
								double sl = Double.parseDouble(sp.getSoLuong());
								cell.setValue(sl);
								ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 41);
							}
							else{
								cell.setValue(sp.getSoLo());
								ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 41);
							}
							setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT,
									"1", false);
						
						}
						else{
							
							if(i == 2){
								cell.setValue(sp.getDonVi());
								setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED,
										"1", false);
							}
							else{
								if(i==1){
									cell.setValue(sp.getTenSanPham());
								}
								else{
									cell.setValue(sp.getGhiChu());
								}
								setCellBorderStyle(cell, HorizontalAlignmentType.LEFT,
									"1", false);
							}
						}
					}
					countRowGiaCong++;
				}
			
			}
			
			// ------------------------------MUA HÀNG
			// ---------------------------------------//
			cell = cells.getCell(countRowGiaCong, 0);
			cell.setValue("C.");
			setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED, "8", true);

			for(int j=1; j< 6; j++){
				cell = cells.getCell(countRowGiaCong,j);
				setCellBorderStyle(cell, HorizontalAlignmentType.LEFT, "8", true);
			}
			cells.merge(countRowGiaCong, 1, countRowGiaCong, 5);
			cell = cells.getCell(countRowGiaCong, 1);
			cell.setValue("MUA HÀNG");
			setCellBorderStyle(cell, HorizontalAlignmentType.LEFT, "8", true);
			int countRowMuaHang = countRowGiaCong + 1;
			sttSP = 0;
			ResultSet rsMuaHang = obj.getRsMuaHang();
			List<ISanPham> spListMuaHang = obj.getListSPMuaHang();
			if(rsMuaHang != null){
				ResultSetMetaData rsmd = rsMuaHang.getMetaData();
				
				 socottrongSql = rsmd.getColumnCount();
				for(int j = 0; j< spListMuaHang.size() ; j++){
					ISanPham sp = spListMuaHang.get(j);
					for (int i = 1; i <= socottrongSql-1; i++) {
						if (i == 1) {
							sttSP++;
						}
						//số thứ tự
						cell = cells.getCell(countRowMuaHang, 0);
						cell.setValue(sttSP);
						setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED,
								"1", false);
						
						//dữ liệu
						cell = cells.getCell(countRowMuaHang, i);
						if(i==3 || i==4){
							if(i == 3){
								double sl = Double.parseDouble(sp.getSoLuong());
								cell.setValue(sl);
								ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 41);
							}
							else{
								cell.setValue(sp.getSoLo());
								ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 41);
							}
							setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT,
									"1", false);
						
						}
						else{
							
							if(i == 2){
								cell.setValue(sp.getDonVi());
								setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED,
										"1", false);
							}
							else{
								if(i==1){
									cell.setValue(sp.getTenSanPham());
								}
								else{
									cell.setValue(sp.getGhiChu());
								}
								setCellBorderStyle(cell, HorizontalAlignmentType.LEFT,
									"1", false);
							}
						}
					}
					countRowMuaHang++;
				}
				
			}
			

			// ---------------------------thông tin bên
			// dưới-----------------------------//
			
			cell = cells.getCell(countRowMuaHang, 1);
			cell.setValue("Nơi nhận:");
			setCellBorderStyle(cell, HorizontalAlignmentType.LEFT, "51", false);
			
			//k/t Tổng giám đốc
			cells.merge(countRowMuaHang, 2, countRowMuaHang, 5);
			cell = cells.getCell(countRowMuaHang,2);
			cell.setValue("K/T TỔNG GIÁM ĐỐC");
			setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED, "7", true);
			
			countRowMuaHang++;
			cell = cells.getCell(countRowMuaHang, 1);
			cell.setValue("- HĐQT.");
			setCellBorderStyle(cell, HorizontalAlignmentType.LEFT, "5", false);

			countRowMuaHang++;
			cell = cells.getCell(countRowMuaHang, 1);
			cell.setValue("- Các phòng ban,phân xưởng");
			setCellBorderStyle(cell, HorizontalAlignmentType.LEFT, "5", false);

			countRowMuaHang++;
			cell = cells.getCell(countRowMuaHang, 1);
			cell.setValue("- LƯU.");
			setCellBorderStyle(cell, HorizontalAlignmentType.LEFT, "5", false);

			for(int i=7; i<countRowMuaHang - 4; i++){
				cell = cells.getCell(i,5);
				Style style = cell.getStyle();
				style.setBorderLine(BorderType.RIGHT, 1);
				cell.setStyle(style);
				
			}
			
			cells.hideColumn(6);

			cells.hideColumn(7);
			if (db != null) {
				db.shutDown();
			}

		} catch (Exception ex) {

			ex.printStackTrace();
			throw new Exception(
					"Qua trinh dien du lieu vao file Excel va tao PivotTable bi loi.!!!");
		}
	}

	public static String GetExcelColumnName(int columnNumber) {
		int dividend = columnNumber;
		String columnName = "";
		int modulo;

		while (dividend > 0) {
			modulo = (dividend - 1) % 26;
			columnName = (char) (65 + modulo) + columnName;
			dividend = (int) ((dividend - modulo) / 26);
		}

		return columnName;
	}

	private void setCellBorderStyle(Cell cell, short align, String loai,
			boolean kt) {
		Style style = cell.getStyle();
		style.setHAlignment(align);
		
		if(!loai.equals("5") && !loai.equals("10") && !loai.equals("51") && !loai.equals("9") && !loai.equals("8") && !loai.equals("7") && !loai.equals("6")){
			style.setBorderLine(BorderType.TOP, 1);
			style.setBorderLine(BorderType.RIGHT, 1);
			style.setBorderLine(BorderType.BOTTOM, 1);
			style.setBorderLine(BorderType.LEFT, 1);
		}
		if(loai.equals("8")){
			style.setBorderLine(BorderType.TOP, 1);
			style.setBorderLine(BorderType.RIGHT, 1);
			style.setBorderLine(BorderType.BOTTOM, 1);
			style.setBorderLine(BorderType.LEFT, 1);
		}

		if (kt) {
			Font font2 = new Font();
			font2.setName("Times New Roman");
			font2.setColor(Color.BLACK);
			font2.setBold(true);
			if(loai.equals("10")){
				font2.setSize(18);
			}
			if(loai.equals("8")){
				font2.setSize(13);
			}
			if(loai.equals("0")){
				font2.setSize(13);
			}
			if(loai.equals("7")){
				font2.setSize(14);
			}
			style.setFont(font2);

		} else {
			if(loai.equals("51")){
				Font font2 = new Font();
				font2.setName("Times New Roman");
				font2.setSize(10);
				font2.setUnderline(1);
				font2.setColor(Color.BLACK);
				font2.setItalic(true);
				style.setFont(font2);
			}else
			if(loai.equals("5")){
				Font font2 = new Font();
				font2.setName("Times New Roman");
				font2.setSize(10);
				font2.setColor(Color.BLACK);
				font2.setItalic(true);
				style.setFont(font2);
				
			}
			else{
				Font font2 = new Font();
				font2.setName("Times New Roman");
				font2.setSize(13);
				font2.setColor(Color.BLACK);
				
				style.setFont(font2);
			}
		}

		cell.setStyle(style);
	}
}