package geso.traphaco.erp.servlets.baocao;

import geso.dms.db.sql.dbutils;
import geso.traphaco.center.servlets.report.ReportAPI;
import geso.traphaco.distributor.util.Utility;
import geso.traphaco.erp.beans.baocao.IBaocao;
import geso.traphaco.erp.beans.baocao.imp.Baocao;
import geso.traphaco.erp.beans.lenhsanxuat.IBaoCaoTheoDoiLSX;
import geso.traphaco.erp.beans.lenhsanxuat.imp.BaoCaoTheoDoiLSX;
import geso.traphaco.erp.beans.stockintransit.IStockintransit;
import geso.traphaco.erp.beans.stockintransit.imp.Stockintransit;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aspose.cells.BorderType;
import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Font;
import com.aspose.cells.HorizontalAlignmentType;
import com.aspose.cells.Style;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

/**
 * Servlet implementation class BaoCaoLSXSvl
 */
@WebServlet("/BaoCaoLSXSvl")
public class BaoCaoLSXSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BaoCaoLSXSvl() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IBaoCaoTheoDoiLSX obj = new BaoCaoTheoDoiLSX();
		String querystring = request.getQueryString();
		Utility util = new Utility();
		// String ctyId = (String)session.getAttribute("congtyId");

		String userId = util.getUserId(querystring);

		if (userId.length() == 0)
			userId = util.antiSQLInspection(request.getParameter("userId"));

		obj.setuserId(userId);

		session.setAttribute("obj", obj);
		String nextJSP = "/TraphacoHYERP/pages/Erp/BaoCaoLSXKichHoat.jsp";
		response.sendRedirect(nextJSP);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		OutputStream out = response.getOutputStream();
		IBaoCaoTheoDoiLSX obj;
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		HttpSession session = request.getSession();

		String userId = request.getParameter("userId");

		obj = new BaoCaoTheoDoiLSX();
		obj.setuserId(userId);

		String tungay = request.getParameter("tungay");
		if (tungay == null)
			tungay = "";
		obj.settungay(tungay);

		String denngay = request.getParameter("denngay");
		if (denngay == null)
			denngay = "";
		obj.setdenngay(denngay);

		response.setContentType("application/xlsm");
		response.setHeader("Content-Disposition",
				"Attachment; filename=ReportLSXKICHHOAT.xlsm");

		try {
			CreatePivotTable(out, response, request, obj);

		} catch (Exception ex) {
			obj.setMsg(ex.getMessage());
			request.getSession().setAttribute("errors", ex.getMessage());

			session.setAttribute("obj", obj);
			session.setAttribute("userId", userId);

			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBaoCaoLenhsanxuatKichHoat.jsp";
			response.sendRedirect(nextJSP);
		}
	}

	private void CreatePivotTable(OutputStream out,
			HttpServletResponse response, HttpServletRequest request,
			IBaoCaoTheoDoiLSX obj) throws Exception {
		try {
			FileInputStream fstream = null;
			Workbook workbook = new Workbook();

			String strfstream = getServletContext().getInitParameter("path")
					+ "\\BaoCaoLsxKichHoat.xlsm";

			fstream = new FileInputStream(strfstream);

			workbook.open(fstream);
			createReport(workbook, obj);

			workbook.save(out);

			fstream.close();

			/*
			 * workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
			 * Worksheets worksheets = workbook.getWorksheets(); Worksheet
			 * worksheet_2 = worksheets.getSheet("sheet1");
			 * worksheet_2.setName("REPORT_LENHSANXUAT"); Cells cells =
			 * worksheet_2.getCells();
			 * 
			 * Cell cell = cells.getCell("P1"); Style style1=cell.getStyle();
			 * 
			 * dbutils db = new dbutils();
			 * 
			 * String[] param = new String[2];
			 * 
			 * param[0] = obj.gettungay().equals("") ? null : obj.gettungay();
			 * param[1] = obj.getdenngay().equals("") ? null : obj.getdenngay();
			 * 
			 * ResultSet rs = db.getRsByPro("GET_REPORT_LENHSANXUAT", param);
			 * worksheets = workbook.getWorksheets();
			 * 
			 * this.TaoBaoCao(db,rs,worksheet_2,
			 * obj,"REPORT_LENHSANXUAT",style1);
			 * 
			 * 
			 * workbook.save(out);
			 */
			fstream.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("Error Message: " + ex.getMessage());
		}
	}

	private void createReport(Workbook workbook, IBaoCaoTheoDoiLSX obj) {

		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);

		worksheet.setName("Sheet1");

		Cells cells = worksheet.getCells();
		dbutils db = new dbutils();

		String query = " select CAST(NGAYKICHHOAT as date) as ngaykichhoat, nm.tennhamay, sp.MA as masp,sp.TEN as tensp, " +
				"COUNT(lsx.pk_seq) as soluong  \n"
				+ " from ERP_LENHSANXUAT_GIAY lsx  \n"
				+ " inner join ERP_LENHSANXUAT_SANPHAM lsx_sp on lsx.pk_seq = lsx_sp.lenhsanxuat_fk \n"
				+ " left join erp_sanpham sp on lsx_sp.sanpham_fk = sp.pk_seq \n"
				+ " left join erp_nhamay nm on lsx.nhamay_fk = nm.pk_seq \n"
				+ " where " 
				+ " (lsx.trangthai = 2 or lsx.TRANGTHAI = 3 or lsx.TRANGTHAI = 31 or lsx.TRANGTHAI = 4 or lsx.TRANGTHAI = 5 or lsx.TRANGTHAI = 6) \n" 
				+ " and CAST(NGAYKICHHOAT as date) >= '"
				+ obj.gettungay()
				+ "' and CAST(NGAYKICHHOAT as date) <= '"
				+ obj.getdenngay()
				+ "'" 
				+" group by CAST(NGAYKICHHOAT as date), nm.tennhamay,sp.MA,sp.TEN \n"
				+ " order by CAST(NGAYKICHHOAT as date) ASC ";

		System.out.println(":::: INFO : " + query);
		ResultSet rsInfo = db.get(query);

		Style style;
		Font font = new Font();
		font.setName("Times New Roman");
		Cell cell;

		cell = cells.getCell("A3");
		String tieude = "Từ ngày " + obj.gettungay() + " Đến ngày "
				+ obj.getdenngay();
		ReportAPI.getCellStyle(cell, Color.BLUE, false, 12, tieude);

		int index = 7;
		int sum = 0;
		try {
			if (rsInfo != null) {
				while (rsInfo.next()) {
					String ngaykichhoat = rsInfo.getString("ngaykichhoat");
					String tennhamay = rsInfo.getString("tennhamay");
					String masp = rsInfo.getString("MASP");
					String tensp = rsInfo.getString("TENSP");
					int soluong = rsInfo.getInt("soluong");
					sum += soluong;
					String date = "";
					String time = "";
					String formatNgayKH = "";
					if (ngaykichhoat != null) {
						date = ngaykichhoat;
						formatNgayKH = date.split("-")[2] + "-"
								+ date.split("-")[1] + "-" + date.split("-")[0];
					}
					cell = cells.getCell("A" + Integer.toString(index));
					cell.setValue(formatNgayKH );
					this.setCellBorderStyle(cell, HorizontalAlignmentType.LEFT,
							false);
					cell = cells.getCell("B" + Integer.toString(index));
					cell.setValue(tennhamay);
					this.setCellBorderStyle(cell, HorizontalAlignmentType.LEFT,
							false);
					cell = cells.getCell("C" + Integer.toString(index));
					cell.setValue(masp);
					this.setCellBorderStyle(cell, HorizontalAlignmentType.LEFT,
							false);
					cell = cells.getCell("D" + Integer.toString(index));
					cell.setValue(tensp);
					this.setCellBorderStyle(cell, HorizontalAlignmentType.LEFT,
							false);
					cell = cells.getCell("E" + Integer.toString(index));
					//cell.setValue(solo);
					cell.setValue(""+soluong);
					this.setCellBorderStyle(cell, HorizontalAlignmentType.LEFT,
							false);

					index++;
				}
				rsInfo.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		cell = cells.getCell("A" + Integer.toString(index));
		ReportAPI.getCellStyle(cell, Color.BLACK, true, 12, "Tổng cộng");// cell.setValue("Tổng cộng");
		int tongsolenh = index - 7;// WARNING
		cell = cells.getCell("E" + Integer.toString(index));
		ReportAPI.getCellStyle(cell, Color.RED, false, 12, "" + sum);// cell.setValue(""+tongsolenh);

	}

	private void setCellBorderStyle(Cell cell, short align, boolean kt) {
		Style style = cell.getStyle();
		style.setHAlignment(align);
		style.setBorderLine(BorderType.TOP, 1);
		style.setBorderLine(BorderType.RIGHT, 1);
		style.setBorderLine(BorderType.BOTTOM, 1);
		style.setBorderLine(BorderType.LEFT, 1);
		style.setTextWrapped(true);
		if (kt) {
			com.aspose.cells.Font font2 = new com.aspose.cells.Font();
			font2.setName("Calibri");
			font2.setColor(Color.BLACK);
			font2.setSize(11);
			style.setFont(font2);
			style.setColor(Color.SILVER);
		} else
			style.setColor(Color.WHITE);

		cell.setStyle(style);
	}

}
