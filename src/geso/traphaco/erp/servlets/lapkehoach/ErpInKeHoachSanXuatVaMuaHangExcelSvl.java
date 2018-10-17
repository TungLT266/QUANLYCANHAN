package geso.traphaco.erp.servlets.lapkehoach;

import geso.traphaco.center.servlets.report.ReportAPI;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.db.sql.dbutils;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aspose.cells.BorderLineType;
import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.HorizontalAlignmentType;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

public class ErpInKeHoachSanXuatVaMuaHangExcelSvl extends HttpServlet {

	private static final long serialVersionUID = 1L;
	PrintWriter out;

	public ErpInKeHoachSanXuatVaMuaHangExcelSvl() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		this.out = response.getWriter();

		HttpSession session = request.getSession();

		Utility util = new Utility();
		out = response.getWriter();

		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);
		out.println(userId);

		if (userId.length() == 0)
			userId = util.antiSQLInspection(request.getParameter("userId"));

		System.out.println(" \n da vao do get");

		String nextJSP = "/OPV/pages/Center/Bctop5Skus.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IBctop5Skus obj = new Bctop5Skus();
		Utility util = new Utility();

		String userId = (String) session.getAttribute("userId");
		if (userId == null)
			userId = "";
		obj.setUserId(userId);

		String denngay = util.antiSQLInspection(request.getParameter("ngay"));
		if (denngay == null)
			denngay = "";
		obj.setNgay(denngay);
		obj.init();
		String action = request.getParameter("action");
		if (action.equals("tao")) {
			try {
				request.setCharacterEncoding("utf-8");

				response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition",
						"attachment; filename=top5skus.xlsm");
				OutputStream out = response.getOutputStream();

				if (!ExportToExcel(out, obj)) {

					obj.setMsg("Lỗi không tạo được báo cáo !");
					session.setAttribute("obj", obj);
					session.setAttribute("userId", userId);
					String nextJSP = "/OPV/pages/Center/Bctop5Skus.jsp";
					response.sendRedirect(nextJSP);
				}

			} catch (Exception ex) {
				ex.printStackTrace();
				request.getSession().setAttribute("errors", ex.getMessage());
			}
		} else {
			obj.init();
			session.setAttribute("obj", obj);
			session.setAttribute("userId", userId);
			String nextJSP = "/OPV/pages/Center/Bctop5Skus.jsp";
			response.sendRedirect(nextJSP);

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

	private String setQuery(IBctop5Skus obj, int numsheet, int numtbl)
			throws SQLException {
		dbutils db = new dbutils();
		String top5[] = new String[5];

		if (numsheet == 0)
			top5 = obj.getTop5();
		else
			top5 = obj.getTop5_fp();

		String q = "";
		// cua bang 1
		if (numtbl == 1)
			q = "	select  ds.TENKV AS TENKV,ds.TENGSBH AS TENGSBH,"
					+ "\n 			ISNULL( ds_ct0.doanhso,0) as doanhso1,isnull(ds_ct0.chitieu,0) as chitieu1, 0 as vs0,"
					+ "\n 			ISNULL( ds_ct1.doanhso,0) as doanhso2,isnull(ds_ct1.chitieu,0) as chitieu2,0 as vs1,"
					+ "\n 			ISNULL( ds_ct2.doanhso,0) as doanhso3,isnull(ds_ct2.chitieu,0) as chitieu3,0 as vs2,"
					+ "\n 			ISNULL( ds_ct3.doanhso,0) as doanhso4,isnull(ds_ct3.chitieu,0) as chitieu4,0 as vs3,"
					+ "\n 			ISNULL( ds_ct4.doanhso,0) as doanhso5,isnull(ds_ct4.chitieu,0) as chitieu5,0 as vs4,"
					+ "\n 			ds.loai  AS loai"
					+ "\n 			from "
					+ "\n 			(	select case when  db.VUNG_FK = 100001 then '1' when  db.VUNG_FK = 100003 then '2' 	"
					+ "\n  				when  db.VUNG_FK = 100002 then '3' 	"
					+ "\n  				when  db.VUNG_FK = 100008 then '4' 	end +'--|--'+db.Ten+'--|--'+db.DienGiai as GSBH, "
					+ "\n  				0 AS loai, ddkd.PK_SEQ as MATDV, db.TEN as TENKV, ddkd.TEN as TENGSBH"
					+ "\n  			from   "
					+ "\n  				KHUVUC db inner join GIAMSATBANHANG ddkd on db.PK_SEQ = ddkd.KHUVUC_FK and ddkd.KBH_FK =100025"
					+ "\n  				inner join KHUVUC kv on ddkd.KHUVUC_FK = kv.PK_SEQ  where ddkd.TRANGTHAI = 1 and kv.TRANGTHAI = 1 "
					+ "\n  				union ALL"
					+ "\n  				select case when  kv.VUNG_FK = 100001 then '1'when  kv.VUNG_FK = 100003 then '2' "
					+ "\n  				when  kv.VUNG_FK = 100002 then '3' 	when  kv.VUNG_FK = 100008 then '4' 	end +'--|--'+kv.Ten+'--|--'+kv.DienGiai as GSBH, "
					+ "\n  				1 AS loai, ddkd.PK_SEQ as MATDV , db.TEN as TENKV ,  ddkd.TEN as TENGSBH"
					+ "\n  			from DIABAN db  "
					+ "\n  				inner join DAIDIENKINHDOANH ddkd on db.PK_SEQ = ddkd.diaban_fk "
					+ "\n  				inner join KHUVUC kv on kv.PK_SEQ = db.KHUVUC_FK "
					+ "\n  				inner join GIAMSATBANHANG gsbh on gsbh.KHUVUC_FK = kv.PK_SEQ and gsbh.KBH_FK = 100025 "
					+ "\n  				and  gsbh.PK_SEQ in (select gsbh_fk from DDKD_GSBH where DDKD_FK = ddkd.PK_SEQ) where ddkd.trangthai = 1 "

					/*
					 * select gsbh.PK_SEQ as MAGSBH,0 as MATDV,gsbh.TEN as
					 * TENGSBH,'0' loai,kv.TEN AS TENKV" +
					 * "\n 				from GIAMSATBANHANG gsbh INNER JOIN KHUVUC kv on kv.PK_SEQ=gsbh.KHUVUC_FK"
					 * + "\n 				where gsbh.TRANGTHAI = 1" + "\n 				union" +
					 * "\n 				select b.GSBH_FK as MAGSBH, a.PK_SEQ as MATDV,a.TEN as TENTDV,'1' loai ,db.TEN AS TENDB"
					 * + "\n 				from DAIDIENKINHDOANH a" +
					 * "\n 				inner join DDKD_GSBH  b on a.PK_SEQ = b.DDKD_FK"
					 * + "\n 				inner join DIABAN db on db.PK_SEQ=a.diaban_fk"
					 * + "\n 				where a.TRANGTHAI = 1" + "\n 				"
					 */

					+ "\n 			) ds" + "\n 			left join ";
		for (int i = 0; i < 4; i++) {
			q += "\n 		    dbo.GetChiTieu_DoanhSo_SP_Month(" + top5[i] + ","
					+ "" + " '"
					+ Integer.parseInt(obj.getNgay().substring(5, 7)) + "'"
					+ "," + "" + "'" + obj.getNgay().substring(0, 4) + "' "
					+ ")" + "\n 			ds_ct" + i + "\n 			on ds_ct" + i
					+ ".MADDKD=ds.MATDV" + "\n 			left join ";
		}
		q += "\n 		    dbo.GetChiTieu_DoanhSo_SP_Month(" + top5[4] + "," + "'"
				+ Integer.parseInt(obj.getNgay().substring(5, 7)) + "'" + ","
				+ "'" + obj.getNgay().substring(0, 4) + "'" + ")"
				+ "\n 			ds_ct" + 4 + "\n 			on ds_ct" + 4 + ".MADDKD=ds.MATDV";

		q += "\n 			order by ds.GSBH,ds.loai,ds.TENKV  ";
		if (numtbl == 2) {
			q = "	select  ds.TENKV AS TENKV,ds.TENGSBH AS TENGSBH,"
					+ "\n 			ISNULL( ds_ct0.sokh,0) as doanhso1,isnull(ds_ct0.chitieu,0) as chitieu1, 0 as vs0,"
					+ "\n 			ISNULL( ds_ct1.sokh,0) as doanhso2,isnull(ds_ct1.chitieu,0) as chitieu2,0 as vs1,"
					+ "\n 			ISNULL( ds_ct2.sokh,0) as doanhso3,isnull(ds_ct2.chitieu,0) as chitieu3,0 as vs2,"
					+ "\n 			ISNULL( ds_ct3.sokh,0) as doanhso4,isnull(ds_ct3.chitieu,0) as chitieu4,0 as vs3,"
					+ "\n 			ISNULL( ds_ct4.sokh,0) as doanhso5,isnull(ds_ct4.chitieu,0) as chitieu5,0 as vs4,"
					+ "\n 			ds.loai  AS loai"
					+ "\n 			from"
					+ "\n 			( select case when  db.VUNG_FK = 100001 then '1' when  db.VUNG_FK = 100003 then '2' 	"
					+ "\n  				when  db.VUNG_FK = 100002 then '3' 	"
					+ "\n  				when  db.VUNG_FK = 100008 then '4' 	end +'--|--'+db.Ten+'--|--'+db.DienGiai as GSBH, "
					+ "\n  				0 AS loai, ddkd.PK_SEQ as MATDV, db.TEN as TENKV, ddkd.TEN as TENGSBH"
					+ "\n  			from   "
					+ "\n  				KHUVUC db inner join GIAMSATBANHANG ddkd on db.PK_SEQ = ddkd.KHUVUC_FK and ddkd.KBH_FK =100025"
					+ "\n  				inner join KHUVUC kv on ddkd.KHUVUC_FK = kv.PK_SEQ  where ddkd.TRANGTHAI = 1 and kv.TRANGTHAI = 1 "
					+ "\n  				union ALL"
					+ "\n  				select case when  kv.VUNG_FK = 100001 then '1'when  kv.VUNG_FK = 100003 then '2' "
					+ "\n  				when  kv.VUNG_FK = 100002 then '3' 	when  kv.VUNG_FK = 100008 then '4' 	end +'--|--'+kv.Ten+'--|--'+kv.DienGiai as GSBH, "
					+ "\n  				1 AS loai, ddkd.PK_SEQ as MATDV , db.TEN as TENKV ,  ddkd.TEN as TENGSBH"
					+ "\n  			from DIABAN db  "
					+ "\n  				inner join DAIDIENKINHDOANH ddkd on db.PK_SEQ = ddkd.diaban_fk "
					+ "\n  				inner join KHUVUC kv on kv.PK_SEQ = db.KHUVUC_FK "
					+ "\n  				inner join GIAMSATBANHANG gsbh on gsbh.KHUVUC_FK = kv.PK_SEQ and gsbh.KBH_FK = 100025 "
					+ "\n  				and  gsbh.PK_SEQ in (select gsbh_fk from DDKD_GSBH where DDKD_FK = ddkd.PK_SEQ) where ddkd.trangthai = 1 "

					/*
					 * select gsbh.PK_SEQ as MAGSBH,0 as MATDV,gsbh.TEN as
					 * TENGSBH,'0' loai,kv.TEN AS TENKV" +
					 * "\n 				from GIAMSATBANHANG gsbh INNER JOIN KHUVUC kv on kv.PK_SEQ=gsbh.KHUVUC_FK"
					 * + "\n 				where gsbh.TRANGTHAI = 1" + "\n 				union" +
					 * "\n 				select b.GSBH_FK as MAGSBH, a.PK_SEQ as MATDV,a.TEN as TENTDV,'1' loai ,db.TEN AS TENDB"
					 * + "\n 				from DAIDIENKINHDOANH a" +
					 * "\n 				inner join DDKD_GSBH  b on a.PK_SEQ = b.DDKD_FK"
					 * + "\n 				inner join DIABAN db on db.PK_SEQ=a.diaban_fk"
					 * + "\n 				where a.TRANGTHAI = 1" + "\n 				"
					 */
					+ "\n 			) ds" + "\n 			left join ";
			for (int i = 0; i < 4; i++) {
				q += "\n 		    dbo.GetChiTieu_SoKH_SP_Month(" + top5[i] + ","
						+ "'" + obj.getNgay().substring(0, 8) + "01'" + ","
						+ "'" + obj.getNgay() + "'" + ")" + "\n 			ds_ct" + i
						+ "\n 			on ds_ct" + i + ".MADDKD=ds.MATDV"
						+ "\n 			left join ";
			}
			q += "\n 		    dbo.GetChiTieu_SoKH_SP_Month(" + top5[4] + "," + "'"
					+ obj.getNgay().substring(0, 8) + "01'" + "," + "'"
					+ obj.getNgay() + "'" + ")" + "\n 			ds_ct" + 4
					+ "\n 			on ds_ct" + 4 + ".MADDKD=ds.MATDV";

			q += "\n 			order by ds.GSBH,ds.loai,ds.TENKV  ";
		}
		return q;
	}

	private String getDateTime() {
		/* DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy"); */
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}

	private void TaoBaoCao(Workbook workbook, IBctop5Skus obj, int sheetNum,
			String query, String query2) throws Exception {
		try {
			Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet(sheetNum);
			Cells cells = worksheet.getCells();
			Cell cell;

			dbutils db = new dbutils();
			int runrate = 0;

			// dien ngay thang lap bao cao va nguoi lap
			cell = cells.getCell("B2");

			cell.setValue(getDateTime());
			setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT, "1", false);

			cell = cells.getCell("B3");
			if (sheetNum == 0)
				cell.setValue(obj.getNgay());
			else
				cell.setValue(obj.getNgay().substring(0, 7));
			setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT, "1", false);

			cell = cells.getCell("D2");
			cell.setValue(obj.getUsername());
			setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT, "1", false);

			if (sheetNum == 1) {
				cell = cells.getCell("B4");
				cell.setValue(obj.getQuy() + " năm "
						+ obj.getNgay().substring(0, 4));
				setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT, "1",
						false);
			}

			String top5_ten[] = new String[5];
			if (sheetNum == 0)
				top5_ten = obj.getTop5_tensp();
			else
				top5_ten = obj.getTop5_tensp_fp();

			// dien ten top 5 san pham,
			/* String[]chucai={"C","I","F","L","O"}; */

			// san pham thuong
			int tt = 0;
			for (int i = 2; i < 15; i += 3) {
				if (sheetNum == 0)
					cell = cells.getCell(4, i);
				if (sheetNum == 1)
					cell = cells.getCell(5, i);
				cell.setValue(top5_ten[tt]);
				tt++;

			}
			/*
			 * if(sheetNum==1) // san pham forcus for (int i =0; i
			 * <chucai.length; i++) { cell = cells.getCell(chucai[i]+6);
			 * cell.setValue(obj.getTop5_tensp_fp()[i]);
			 * 
			 * }
			 */

			// --------------------- du lieu bang 1---------------------//

			ResultSet rs1 = db.get(query);
			ResultSetMetaData rsmd = rs1.getMetaData();
			;
			int socottrongSql = rsmd.getColumnCount();

			System.out.println("so co trong sql:" + socottrongSql + "\n");

			int countRow = 0;
			if (sheetNum == 0) {
				countRow = 7;
			} else
				countRow = 8;
			int column = 0;

			// dem moc tinh tong cua GIAM SAT BAN HANG
			int mang[] = new int[10000];
			int k = 0;
			int dem = 0;

			// bang 1 sheet 1, sheet 2
			while (rs1.next()) {
				for (int i = 1; i <= socottrongSql; i++) {
					String loai = rs1.getString("loai");
					Color c = Color.WHITE;
					if (loai.equals("0")) {
						c = new Color(252, 213, 180);
						mang[k] = countRow;
						k++;
					}

					cell = cells.getCell(countRow, column + i - 1);

					if (i <= 2) {

						cell.setValue(rs1.getString(i));
					} else {
						if (rsmd.getColumnType(i) == Types.DOUBLE
								|| rsmd.getColumnType(i) == Types.INTEGER
								|| rsmd.getColumnType(i) == Types.DECIMAL) {

							cell.setValue(rs1.getDouble(i));
							ReportAPI.setCellBackground(cell, new Color(255,
									255, 255), BorderLineType.THIN, true, 41);
						} else {

							cell.setValue(rs1.getDouble(i));
							ReportAPI.setCellBackground(cell, new Color(255,
									255, 255), BorderLineType.THIN, true, 41);
						}
					}

					// lay phan tram doanh so / chi tieu
					if (rsmd.getColumnName(i).contains("vs")) {
						cell.setFormula("=" + GetExcelColumnName(i - 2)
								+ (countRow + 1) + "/"
								+ GetExcelColumnName(i - 1) + (countRow + 1));
						ReportAPI.setCellBackground(cell, c,
								BorderLineType.THIN, true, 10);
					}

					setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT,
							loai, false);

				}
				countRow++;
			}

			mang[k] = countRow;
			if (rs1 != null)
				rs1.close();

			// tính sum giám sát bán hàng
			for (int j = 0; j < k; j++) {
				for (int i = 3; i < socottrongSql; i++) {
					// System.out.println("\n chi so: dong:"+ mang[j] +"cot: " +
					// (i-1));
					cell = cells.getCell(mang[j], i - 1);
					// System.out.println("\n chi so:"+ (mang[j+1]-1));
					if (i != 5 && i != 8 && i != 11 && i != 14 && i != 17) {
						// System.out.println(" \n cong thuc: "+
						// "=SUM("+GetExcelColumnName(i)+""+(mang[j]+1)+":"+GetExcelColumnName(i)+(mang[j+1]-1)+")"+"\n ");
						cell.setFormula("=SUM(" + GetExcelColumnName(i) + ""
								+ (mang[j] + 2) + ":" + GetExcelColumnName(i)
								+ (mang[j + 1]) + ")");
						ReportAPI.setCellBackground(cell, new Color(252, 213,
								180), BorderLineType.THIN, true, 41);
					} else {
						cell.setFormula("=" + GetExcelColumnName(i - 2)
								+ (mang[j] + 1) + "/"
								+ GetExcelColumnName(i - 1) + (mang[j] + 1));
						ReportAPI.setCellBackground(cell, new Color(252, 213,
								180), BorderLineType.THIN, true, 10);
					}
				}
			}
			// tính sum total
			for (int i = 3; i < socottrongSql; i++) {
				if (sheetNum == 0) {
					cell = cells.getCell(6, column + i - 1);
					if (i != 5 && i != 8 && i != 11 && i != 14 && i != 17) {
						cell.setFormula("=SUMIF(R8:R" + countRow + ",1,"
								+ GetExcelColumnName(i) + "8:"
								+ GetExcelColumnName(i) + countRow + ")");
						ReportAPI.setCellBackground(cell, new Color(196, 189,
								151), BorderLineType.THIN, true, 41);
					} else {
						cell.setFormula("=" + GetExcelColumnName(i - 2) + 7
								+ "/" + GetExcelColumnName(i - 1) + 7);
						ReportAPI.setCellBackground(cell, new Color(196, 189,
								151), BorderLineType.THIN, true, 10);
					}
				}

				if (sheetNum == 1) {
					cell = cells.getCell(7, column + i - 1);
					if (i != 5 && i != 8 && i != 11 && i != 14 && i != 17) {
						cell.setFormula("=SUMIF(R9:R" + countRow + ",1,"
								+ GetExcelColumnName(i) + "9:"
								+ GetExcelColumnName(i) + countRow + ")");
						ReportAPI.setCellBackground(cell, new Color(196, 189,
								151), BorderLineType.THIN, true, 41);
					} else {
						cell.setFormula("=" + GetExcelColumnName(i - 2) + 8
								+ "/" + GetExcelColumnName(i - 1) + 8);
						ReportAPI.setCellBackground(cell, new Color(196, 189,
								151), BorderLineType.THIN, true, 10);
					}
				}
			}
			// --------------------- du lieu bang 2---------------------//

			// lam tieu de dong
			// dong 1
			System.out.println("\n so dong:" + countRow);
			cells.merge(countRow + 2, 0, countRow + 2, 1);
			cell = cells.getCell(countRow + 2, 0);
			cell.setValue("EC");
			setCellBorderStyle(cell, HorizontalAlignmentType.JUSTIFIED, "2",
					false);
			ReportAPI.setCellBackground(cell, new Color(255, 255, 0),
					BorderLineType.THIN, true, 11);

			/*
			 * cell = cells.getCell(countRow+2,1); setCellBorderStyle(cell,
			 * HorizontalAlignmentType.JUSTIFIED,"2",false);
			 */

			// ten san pham
			/*
			 * String top5_ten[]=new String[5]; if(sheetNum==0 )
			 * top5_ten=obj.getTop5_tensp(); else
			 * top5_ten=obj.getTop5_tensp_fp();
			 */
			int stten = 0;

			for (int i = 2; i < 15; i += 3) {
				cells.merge(countRow + 2, i, countRow + 2, i + 2);
				cell = cells.getCell(countRow + 2, i);
				cell.setValue(top5_ten[stten]);
				ReportAPI.setCellBackground(cell, new Color(217, 217, 217),
						BorderLineType.THIN, true, 11);
				stten++;
			}

			// dong 2
			cell = cells.getCell(countRow + 3, 0);
			ReportAPI.setCellBackground(cell, new Color(255, 192, 0),
					BorderLineType.THIN, true, 11);
			cell.setValue("TERRITORY");

			cell = cells.getCell(countRow + 3, 1);
			ReportAPI.setCellBackground(cell, new Color(255, 192, 0),
					BorderLineType.THIN, true, 11);
			cell.setValue("SR/ASM/RSM");

			for (int i = 2; i < 15; i += 3) {
				/*
				 * Style style1=cell.getStyle();
				 * style1.setHAlignment(TextAlignmentType.JUSTIFY);
				 */

				cell = cells.getCell(countRow + 3, i);
				ReportAPI.setCellBackground(cell, new Color(255, 192, 0),
						BorderLineType.THIN, true, 11);
				cell.setValue("MTD- EC");
				/* cell.setStyle(style1); */

				cell = cells.getCell(countRow + 3, i + 1);
				ReportAPI.setCellBackground(cell, new Color(255, 192, 0),
						BorderLineType.THIN, true, 11);
				cell.setValue("MCP");
				/* cell.setStyle(style1); */

				cell = cells.getCell(countRow + 3, i + 2);
				ReportAPI.setCellBackground(cell, new Color(255, 192, 0),
						BorderLineType.THIN, true, 11);
				cell.setValue("% vs Target");
				/* cell.setStyle(style1); */

			}

			// dong 3
			cells.merge(countRow + 4, 0, countRow + 4, 1);
			cell = cells.getCell(countRow + 4, 0);
			cell.setValue("Total Pharmacy");
			setCellBorderStyle(cell, HorizontalAlignmentType.JUSTIFIED, "2",
					false);
			ReportAPI.setCellBackground(cell, new Color(196, 189, 151),
					BorderLineType.THIN, true, 11);

			// du lieu
			countRow++;
			int dongtotal = countRow + 3;
			int countRow2 = countRow + 4;
			ResultSet rs2 = db.get(query2);
			ResultSetMetaData rsmd2 = rs2.getMetaData();
			;
			int socottrongSql2 = rsmd2.getColumnCount();

			// dem moc tinh tong cua GIAM SAT BAN HANG
			int mang2[] = new int[10000];
			int k2 = 0;
			int dem2 = 0;

			// bang 1 sheet 1, sheet 2
			while (rs2.next()) {
				for (int i = 1; i <= socottrongSql2; i++) {
					String loai = rs2.getString("loai");
					Color c = Color.WHITE;
					if (loai.equals("0")) {
						c = new Color(252, 213, 180);
						mang2[k2] = countRow2;
						k2++;
					}

					cell = cells.getCell(countRow2, column + i - 1);

					if (i <= 2) {

						cell.setValue(rs2.getString(i));
					} else {
						if (rsmd2.getColumnType(i) == Types.DOUBLE
								|| rsmd2.getColumnType(i) == Types.INTEGER
								|| rsmd2.getColumnType(i) == Types.DECIMAL) {

							cell.setValue(rs2.getDouble(i));
							ReportAPI.setCellBackground(cell, new Color(255,
									255, 255), BorderLineType.THIN, true, 41);

						} else {

							cell.setValue(rs2.getDouble(i));
							ReportAPI.setCellBackground(cell, new Color(255,
									255, 255), BorderLineType.THIN, true, 41);
						}
					}

					// lay phan tram doanh so / chi tieu
					if (rsmd2.getColumnName(i).contains("vs")) {
						cell.setFormula("=" + GetExcelColumnName(i - 2)
								+ (countRow2 + 1) + "/"
								+ GetExcelColumnName(i - 1) + (countRow2 + 1));
						ReportAPI.setCellBackground(cell, c,
								BorderLineType.THIN, true, 10);
					}

					setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT,
							loai, false);

				}
				countRow2++;
			}

			mang2[k2] = countRow2;
			if (rs2 != null)
				rs2.close();
			System.out.println("gia tri k2:" + k2);
			// tính sum giám sát bán hàng
			for (int j = 0; j < k2; j++) {
				for (int i = 3; i < socottrongSql2; i++) {
					// System.out.println("\n chi so: dong:"+ mang[j] +"cot: " +
					// (i-1));
					cell = cells.getCell(mang2[j], i - 1);
					// System.out.println("\n chi so:"+ (mang[j+1]-1));
					if (i != 5 && i != 8 && i != 11 && i != 14 && i != 17) {
						// System.out.println(" \n cong thuc: "+
						// "=SUM("+GetExcelColumnName(i)+""+(mang[j]+1)+":"+GetExcelColumnName(i)+(mang[j+1]-1)+")"+"\n ");
						cell.setFormula("=SUM(" + GetExcelColumnName(i) + ""
								+ (mang2[j] + 2) + ":" + GetExcelColumnName(i)
								+ (mang2[j + 1]) + ")");
						ReportAPI.setCellBackground(cell, new Color(252, 213,
								180), BorderLineType.THIN, true, 41);
					} else {
						cell.setFormula("=" + GetExcelColumnName(i - 2)
								+ (mang2[j] + 1) + "/"
								+ GetExcelColumnName(i - 1) + (mang2[j] + 1));
						ReportAPI.setCellBackground(cell, new Color(252, 213,
								180), BorderLineType.THIN, true, 10);
					}
				}
			}
			System.out.println(" \n dong total:" + dongtotal);

			// tính sum total
			for (int i = 3; i < socottrongSql2; i++) {

				cell = cells.getCell(dongtotal, i - 1);
				if (i != 5 && i != 8 && i != 11 && i != 14 && i != 17) {
					cell.setFormula("=SUMIF(R" + (dongtotal + 1) + ":R"
							+ countRow2 + ",1," + GetExcelColumnName(i)
							+ (dongtotal + 1) + ":" + GetExcelColumnName(i)
							+ countRow2 + ")");
					ReportAPI.setCellBackground(cell, new Color(196, 189, 151),
							BorderLineType.THIN, true, 41);
				} else {
					cell.setFormula("=" + GetExcelColumnName(i - 2)
							+ (dongtotal + 1) + "/" + GetExcelColumnName(i - 1)
							+ (dongtotal + 1));
					ReportAPI.setCellBackground(cell, new Color(196, 189, 151),
							BorderLineType.THIN, true, 10);
				}
			}

			cells.hideColumn(17);

			if (db != null) {
				db.shutDown();
			}

		} catch (Exception ex) {

			ex.printStackTrace();
			throw new Exception(
					"Qua trinh dien du lieu vao file Excel va tao PivotTable bi loi.!!!");
		}
	}

	private void setCellBorderStyle(Cell cell, short align, String loai,
			boolean kt) {
		Style style = cell.getStyle();
		style.setHAlignment(align);
		style.setBorderLine(BorderType.TOP, 1);
		style.setBorderLine(BorderType.RIGHT, 1);
		style.setBorderLine(BorderType.BOTTOM, 1);
		style.setBorderLine(BorderType.LEFT, 1);
		if (kt) {
			Font font2 = new Font();
			font2.setName("Calibri");
			font2.setColor(Color.RED);
			style.setFont(font2);

		}
		if (loai.equals("0"))
			style.setColor(new Color(252, 213, 180));
		else
			style.setColor(Color.WHITE);

		cell.setStyle(style);
	}

	private Boolean ExportToExcel(OutputStream out, IBctop5Skus obj)
			throws Exception {
		try {

			FileInputStream fstream = new FileInputStream(getServletContext()
					.getInitParameter("path") + "\\top5skus.xlsm");
			Workbook workbook = new Workbook();
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);

			for (int i = 0; i < 2; i++) // ve 2 sheet
			{
				String query = setQuery(obj, i, 1);
				System.out.println("\n query lay du lieu: " + query);
				String query2 = setQuery(obj, i, 2);
				System.out.println("\n query lay du lieu: " + query2);
				TaoBaoCao(workbook, obj, i, query, query2);
			}
			workbook.save(out);
			fstream.close();
			return true;

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception(ex.getMessage());

		}

	}
}
