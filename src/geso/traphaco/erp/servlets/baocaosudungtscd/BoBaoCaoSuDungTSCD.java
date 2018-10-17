package geso.traphaco.erp.servlets.baocaosudungtscd;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
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
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.baocaosudungtscd.IErp_BaoCaoSuDungTSCD;
import geso.traphaco.erp.beans.baocaosudungtscd.imp.Erp_BaoCaoSuDungTSCD;


/**
 * Servlet implementation class BoBaoCaoSuDungTSCD
 */
@WebServlet("/BoBaoCaoSuDungTSCD")
public class BoBaoCaoSuDungTSCD extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BoBaoCaoSuDungTSCD() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Utility Ult = new Utility();
		HttpSession session = request.getSession();
		session.setAttribute("util", Ult);
		String querystring = request.getQueryString();
		String userId = Ult.getUserId(querystring);
		IErp_BaoCaoSuDungTSCD obj = new Erp_BaoCaoSuDungTSCD();
		obj.createRs();

		System.out.println("----------Báo cáo tăng TSCĐ --------------");
		System.out.println(queryBaoCaoTangTSCD(obj));
		System.out.println("-------------------------------------------");

		System.out.println("----------Báo cáo giảm TSCĐ --------------");
		System.out.println(queryBaoCaoGiamTSCD(obj));
		System.out.println("-------------------------------------------");

		System.out.println("----------Báo cáo Khấu hao chi tiết --------------");
		System.out.println(queryKhauHaoChiTiet(obj));
		System.out.println("-------------------------------------------");

		System.out.println("----------Báo cáo Khấu hao đơn vị --------------");
		System.out.println(queryKhauHaoDonVi(obj));
		System.out.println("-------------------------------------------");

		System.out.println("----------Báo cáo Khấu hao phân bổ --------------");
		System.out.println(queryKhauHaoPhanBo(obj));
		System.out.println("-------------------------------------------");

		String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_BaoCaoSuDungTSCD.jsp";
		session.setAttribute("obj", obj);
		response.sendRedirect(nextJSP);
		return;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		Utility util = new Utility();

		HttpSession session = request.getSession();

		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		String sum = (String) session.getAttribute("sum");
		Utility cutil = (Utility) session.getAttribute("util");

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		OutputStream out = response.getOutputStream();
		IErp_BaoCaoSuDungTSCD obj = new Erp_BaoCaoSuDungTSCD();

		String ctyId = (String) session.getAttribute("congtyId");
		obj.setuserId(userId);
		obj.setCtyId(ctyId);

		String loaiBC = util.antiSQLInspection(request.getParameter("loaiBC"));
		if (loaiBC == null)
			loaiBC = "";
		obj.setLoaiBaoCao(loaiBC);

		String year = util.antiSQLInspection(request.getParameter("year"));
		if (year == null)
			year = "";
		obj.setYear(year);

		String month = util.antiSQLInspection(request.getParameter("month"));
		if (month == null)
			month = "";
		obj.setMonth(month);
		
		String loaiTS = util.antiSQLInspection(request.getParameter("loaiTaiSan"));
		if(loaiTS == null)
			loaiTS = "";
		obj.setLoaiTaiSanId(loaiTS);
		
		
		String dvthId = util.antiSQLInspection(request.getParameter("dvth"));
		if(dvthId == null)
			dvthId = "";
		obj.setDvthId(dvthId);

		obj.createRs();
		obj.getDataCongty();

		String action = request.getParameter("action");

		String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_BaoCaoSuDungTSCD.jsp";

		if (action.equals("tao")) {
			try {
				response.setContentType("application/xlsm");
				// 1 -- Báo cáo tăng
				// 2 -- Báo cáo giảm
				// 3 -- Báo cáo khấu hao đơn vị
				// 4 -- Báo cáo khấu hao chi tiết
				// 5 -- Báo cáo khấu hao khấu phân bổ
				if (loaiBC.equals("1")) {
					response.setHeader("Content-Disposition", "attachment; filename=BaoCaoTangTSCD.xlsm");
					CreateReportTangTSCD(out, obj);
				} else if (loaiBC.equals("2")) {
					response.setHeader("Content-Disposition", "attachment; filename=BaoCaoGiamTSCD.xlsm");
					CreateReportGiamTSCD(out, obj);
				} else if (loaiBC.equals("3")) {
					response.setHeader("Content-Disposition", "attachment; filename=BaoCaoKhauHaoTheoDonViTSCD.xlsm");
					CreateReportKhauHaoDonVi(out, obj);
				} else if (loaiBC.equals("4")) {
					response.setHeader("Content-Disposition", "attachment; filename=BaoCaoKhauHaoChiTietTSCD.xlsm");
					CreateReportKhauHaoChiTiet(out, obj);
				} else if (loaiBC.equals("5")) {
					response.setHeader("Content-Disposition", "attachment; filename=BaoCaoKhauHaoPhanBoTSCD.xlsm");
					CreateReportKhauHaoPhanBo(out, obj);
				}

			} catch (Exception ex) {
				obj.setMsg(ex.getMessage());
				session.setAttribute("obj", obj);
				response.sendRedirect(nextJSP);
				return;
			}
		}

		session.setAttribute("obj", obj);
		response.sendRedirect(nextJSP);
		return;

	}

	private void CreateReportTangTSCD(OutputStream out, IErp_BaoCaoSuDungTSCD obj) throws Exception {

		try {
			String file = getServletContext().getInitParameter("path") + "\\BaoCaoTangTSCD.xlsm";

			System.out.println(file);

			FileInputStream fstream = new FileInputStream(file);

			Workbook workbook = new Workbook();
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);

			Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet("Sheet1");

			Cells cells = worksheet.getCells();
			Cell cell = cells.getCell("A1");
			Style style;
			Font font = new Font();
			font.setColor(Color.NAVY);
			font.setSize(13);
			font.setBold(true);

			cell = cells.getCell("A1");
			cell.setValue("" + obj.getCtyTen());
			style = cell.getStyle();
			style.setFont(font);
			style.setHAlignment(TextAlignmentType.LEFT);
			cell.setStyle(style);

			cells.setRowHeight(1, 20.0f);
			cell = cells.getCell("A2");
			cell.setValue("Địa chỉ: " + obj.getDiachi());
			style = cell.getStyle();
			style.setFont(font);
			style.setHAlignment(TextAlignmentType.LEFT);
			cell.setStyle(style);

			cells.setRowHeight(2, 20.0f);
			cell = cells.getCell("A3");
			cell.setValue("Mã số thuế: " + obj.getMasothue());
			style = cell.getStyle();
			style.setFont(font);
			style.setHAlignment(TextAlignmentType.LEFT);
			cell.setStyle(style);
			
			cell = cells.getCell("D6");
			String ngaythang="";
			if(obj.getMonth().length()>0)
			{
				ngaythang="Tháng "+obj.getMonth() + " năm "+obj.getYear();
			}else
				ngaythang="Năm "+obj.getYear();
			cell.setValue("" + ngaythang); 
			style = cell.getStyle();
			style.setFont(font);
			style.setHAlignment(TextAlignmentType.CENTER);
			cell.setStyle(style);
			
			double tongNguyenGia=0;
			double tongGiaTriDaKH=0;
			double tongGiaTriConLai=0;
			double tongSoThangKH=0;

			String query = queryBaoCaoTangTSCD(obj);
			System.out.println("[CreateReportTangTSCD]: "+ query);
			obj.createRsBaoCao(query);
			ResultSet rs = obj.getRsBaoCao();
			int row = 10;
			if (rs != null) {
				font.setColor(Color.BLACK);
				font.setSize(10);
				font.setBold(false);
				style = cell.getStyle();
				style.setFont(font);
				style.setNumber(0);
				style.setHAlignment(TextAlignmentType.LEFT);
				while (rs.next()) {
					// Loại TS
					cell = cells.getCell("A" + row);
					cell.setValue(rs.getString("LOAITAISAN"));
					cell.setStyle(style);
					cell = CreateBorderSetting2(cell, 0);
					// Mã TS
					cell = cells.getCell("B" + row);
					cell.setValue(rs.getString("MATSCD"));
					cell.setStyle(style);
					cell = CreateBorderSetting2(cell, 0);
					// Tên TS
					cell = cells.getCell("C" + row);
					cell.setValue(rs.getString("TENTSCD"));
					cell.setStyle(style);
					cell = CreateBorderSetting2(cell, 0);
					// Đơn vị sử dụng
					cell = cells.getCell("D" + row);
					cell.setValue(rs.getString("TENDVTH"));
					cell.setStyle(style);
					cell = CreateBorderSetting2(cell, 0);
					// Ngày bắt đầu sử dụng
					cell = cells.getCell("E" + row);
					cell.setValue(rs.getString("NGAYBATDAUSD"));
					cell.setStyle(style);
					cell = CreateBorderSetting2(cell, 0);
					// Ngày tính khấu hao
					cell = cells.getCell("F" + row);
					cell.setValue(rs.getString("NGAYBATDAUKH"));
					cell.setStyle(style);
					cell = CreateBorderSetting2(cell, 0);
					// Thời gian khấu hao (tháng)
					cell = cells.getCell("G" + row);
					cell.setValue(rs.getInt("SOTHANGKH"));
					cell.setStyle(style);
					cell = CreateBorderSetting2(cell, 41);
					// Nguyên giá
					cell = cells.getCell("H" + row);
					cell.setValue(rs.getDouble("NGUYENGIA"));
					cell.setStyle(style);
					cell = CreateBorderSetting2(cell, 41);
					// Giá trị đã khấu hao
					cell = cells.getCell("I" + row);
					cell.setValue(rs.getDouble("GIATRIDAKHAUHAO"));
					cell.setStyle(style);
					cell = CreateBorderSetting2(cell, 41);
					// Giá trị còn lại
					cell = cells.getCell("J" + row);
					cell.setValue(rs.getDouble("GIATRICONLAI"));
					cell.setStyle(style);
					cell = CreateBorderSetting2(cell, 41);
					// Lý do tăng
					cell = cells.getCell("K" + row);
					cell.setValue(rs.getString("LYDOTANG"));
					cell.setStyle(style);
					cell = CreateBorderSetting2(cell, 0);
					
					tongNguyenGia +=rs.getDouble("NGUYENGIA");
					tongSoThangKH +=rs.getDouble("SOTHANGKH");
					tongGiaTriDaKH +=rs.getDouble("GIATRIDAKHAUHAO");
					tongGiaTriConLai +=rs.getDouble("GIATRICONLAI");
					
					row ++;
				}
			}
			
			cell = cells.getCell("A" + row);
			cell.setValue("TỔNG CỘNG");
			cell.setStyle(style);
			cell = CreateBorderSetting(cell, 0);
			// Mã TS
			cell = cells.getCell("B" + row);
			cell.setValue("");
			cell.setStyle(style);
			cell = CreateBorderSetting(cell, 0);
			// Tên TS
			cell = cells.getCell("C" + row);
			cell.setValue("");
			cell.setStyle(style);
			cell = CreateBorderSetting(cell, 0);
			// Đơn vị sử dụng
			cell = cells.getCell("D" + row);
			cell.setValue("");
			cell.setStyle(style);
			cell = CreateBorderSetting(cell, 0);
			// Ngày bắt đầu sử dụng
			cell = cells.getCell("E" + row);
			cell.setValue("");
			cell.setStyle(style);
			cell = CreateBorderSetting(cell, 0);
			// Ngày tính khấu hao
			cell = cells.getCell("F" + row);
			cell.setValue("");
			cell.setStyle(style);
			cell = CreateBorderSetting(cell, 0);
			// Thời gian khấu hao (tháng)
			cell = cells.getCell("G" + row);
			cell.setValue(tongSoThangKH);
			cell.setStyle(style);
			cell = CreateBorderSetting(cell, 41);
			// Ngày thanh lý
			cell = cells.getCell("H" + row);
			cell.setValue("");
			cell.setStyle(style);
			cell = CreateBorderSetting(cell, 0);
			// Nguyên giá
			cell = cells.getCell("H" + row);
			cell.setValue(tongNguyenGia);
			cell.setStyle(style);
			cell = CreateBorderSetting(cell, 41);
			// Giá trị đã khấu hao
			cell = cells.getCell("I" + row);
			cell.setValue(tongGiaTriDaKH);
			cell.setStyle(style);
			cell = CreateBorderSetting(cell, 41);
			// Giá trị còn lại
			cell = cells.getCell("J" + row);
			cell.setValue(tongGiaTriConLai);
			cell.setStyle(style);
			cell = CreateBorderSetting(cell, 41);
			// Lý do giảm
			cell = cells.getCell("K" + row);
			cell.setValue("");
			cell.setStyle(style);
			cell = CreateBorderSetting(cell, 0);
			
			
			row ++;
			cell = cells.getCell("B"+(row+1));
			cell.setValue("Người lập biểu");
			cell = cells.getCell("D"+(row+1));
			cell.setValue("Kế toán trưởng");
			cell = cells.getCell("H"+row);
			cell.setValue("Ngày   "+ "Tháng    "+"Năm    ");
			cell= cells.getCell("H"+(row+1));
			cell.setValue("Tổng giám đốc");
			workbook.save(out);
			fstream.close();

		} catch (Exception ex) {
			System.out.println(ex.toString());
			ex.printStackTrace();
			throw new Exception("Không tạo được báo cáo");
		}

	}

	private void CreateReportGiamTSCD(OutputStream out, IErp_BaoCaoSuDungTSCD obj) throws Exception {

		try {
			String file = getServletContext().getInitParameter("path") + "\\BaoCaoGiamTSCD.xlsm";

			System.out.println(file);

			FileInputStream fstream = new FileInputStream(file);

			Workbook workbook = new Workbook();
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);

			Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet("Sheet1");

			Cells cells = worksheet.getCells();
			Cell cell = cells.getCell("A1");
			Style style;
			Font font = new Font();
			font.setColor(Color.NAVY);
			font.setSize(13);
			font.setBold(true);

			cell = cells.getCell("A1");
			cell.setValue("" + obj.getCtyTen());
			style = cell.getStyle();
			style.setFont(font);
			style.setHAlignment(TextAlignmentType.LEFT);
			cell.setStyle(style);

			cells.setRowHeight(1, 20.0f);
			cell = cells.getCell("A2");
			cell.setValue("Địa chỉ: " + obj.getDiachi());
			style = cell.getStyle();
			style.setFont(font);
			style.setHAlignment(TextAlignmentType.LEFT);
			cell.setStyle(style);

			cells.setRowHeight(2, 20.0f);
			cell = cells.getCell("A3");
			cell.setValue("Mã số thuế: " + obj.getMasothue());
			style = cell.getStyle();
			style.setFont(font);
			style.setHAlignment(TextAlignmentType.LEFT);
			cell.setStyle(style);
			cell = cells.getCell("D6");
			String ngaythang="";
			if(obj.getMonth().length()>0)
			{
				ngaythang="Tháng "+obj.getMonth() + " năm "+obj.getYear();
			}else
				ngaythang="Năm "+obj.getYear();
			cell.setValue("" + ngaythang); 
			style = cell.getStyle();
			style.setFont(font);
			style.setHAlignment(TextAlignmentType.CENTER);
			cell.setStyle(style);
			double tongNguyenGia=0;
			double tongGiaTriDaKH=0;
			double tongGiaTriConLai=0;
			double tongSoThangKH=0;
			String query = queryBaoCaoGiamTSCD(obj);
			System.out.println("[CreateReportGiamTSCD]:"+query);
			obj.createRsBaoCao(query);
			ResultSet rs = obj.getRsBaoCao();
			int row = 10;
			if (rs != null) {
				font.setColor(Color.BLACK);
				font.setSize(10);
				font.setBold(false);
				style = cell.getStyle();
				style.setFont(font);
				style.setNumber(0);
				style.setHAlignment(TextAlignmentType.LEFT);
				while (rs.next()) {
					// Loại TS
					cell = cells.getCell("A" + row);
					cell.setValue(rs.getString("LOAITAISAN"));
					cell.setStyle(style);
					cell = CreateBorderSetting2(cell, 0);
					// Mã TS
					cell = cells.getCell("B" + row);
					cell.setValue(rs.getString("MATSCD"));
					cell.setStyle(style);
					cell = CreateBorderSetting2(cell, 0);
					// Tên TS
					cell = cells.getCell("C" + row);
					cell.setValue(rs.getString("TENTSCD"));
					cell.setStyle(style);
					cell = CreateBorderSetting2(cell, 0);
					// Đơn vị sử dụng
					cell = cells.getCell("D" + row);
					cell.setValue(rs.getString("TENDVTH"));
					cell.setStyle(style);
					cell = CreateBorderSetting2(cell, 0);
					// Ngày bắt đầu sử dụng
					cell = cells.getCell("E" + row);
					cell.setValue(rs.getString("NGAYBATDAUSD"));
					cell.setStyle(style);
					cell = CreateBorderSetting2(cell, 0);
					// Ngày tính khấu hao
					cell = cells.getCell("F" + row);
					cell.setValue(rs.getString("NGAYBATDAUKH"));
					cell.setStyle(style);
					cell = CreateBorderSetting2(cell, 0);
					// Thời gian khấu hao (tháng)
					cell = cells.getCell("G" + row);
					cell.setValue(rs.getInt("SOTHANGKH"));
					cell.setStyle(style);
					cell = CreateBorderSetting2(cell, 41);
					// Ngày thanh lý
					cell = cells.getCell("H" + row);
					cell.setValue(rs.getString("NGAYTHANHLY"));
					cell.setStyle(style);
					cell = CreateBorderSetting2(cell, 0);
					// Nguyên giá
					cell = cells.getCell("I" + row);
					cell.setValue(rs.getDouble("NGUYENGIA"));
					cell.setStyle(style);
					cell = CreateBorderSetting2(cell, 41);
					// Giá trị đã khấu hao
					cell = cells.getCell("J" + row);
					cell.setValue(rs.getDouble("GIATRIDAKHAUHAO"));
					cell.setStyle(style);
					cell = CreateBorderSetting2(cell, 41);
					// Giá trị còn lại
					cell = cells.getCell("K" + row);
					cell.setValue(rs.getDouble("GIATRICONLAI"));
					cell.setStyle(style);
					cell = CreateBorderSetting2(cell, 41);
					// Lý do giảm
					cell = cells.getCell("L" + row);
					cell.setValue(rs.getString("LYDOGIAM"));
					cell.setStyle(style);
					cell = CreateBorderSetting2(cell, 0);
					
					tongNguyenGia +=rs.getDouble("NGUYENGIA");
					tongSoThangKH +=rs.getDouble("SOTHANGKH");
					tongGiaTriDaKH +=rs.getDouble("GIATRIDAKHAUHAO");
					tongGiaTriConLai +=rs.getDouble("GIATRICONLAI");
					
					row ++;
				}
			}
			
			
			
			cell = cells.getCell("A" + row);
			cell.setValue("TỔNG CỘNG");
			cell.setStyle(style);
			cell = CreateBorderSetting(cell, 0);
			// Mã TS
			cell = cells.getCell("B" + row);
			cell.setValue("");
			cell.setStyle(style);
			cell = CreateBorderSetting(cell, 0);
			// Tên TS
			cell = cells.getCell("C" + row);
			cell.setValue("");
			cell.setStyle(style);
			cell = CreateBorderSetting(cell, 0);
			// Đơn vị sử dụng
			cell = cells.getCell("D" + row);
			cell.setValue("");
			cell.setStyle(style);
			cell = CreateBorderSetting(cell, 0);
			// Ngày bắt đầu sử dụng
			cell = cells.getCell("E" + row);
			cell.setValue("");
			cell.setStyle(style);
			cell = CreateBorderSetting(cell, 0);
			// Ngày tính khấu hao
			cell = cells.getCell("F" + row);
			cell.setValue("");
			cell.setStyle(style);
			cell = CreateBorderSetting(cell, 0);
			// Thời gian khấu hao (tháng)
			cell = cells.getCell("G" + row);
			cell.setValue(tongSoThangKH);
			cell.setStyle(style);
			cell = CreateBorderSetting(cell, 41);
			// Ngày thanh lý
			cell = cells.getCell("H" + row);
			cell.setValue("");
			cell.setStyle(style);
			cell = CreateBorderSetting(cell, 0);
			// Nguyên giá
			cell = cells.getCell("I" + row);
			cell.setValue(tongNguyenGia);
			cell.setStyle(style);
			cell = CreateBorderSetting(cell, 41);
			// Giá trị đã khấu hao
			cell = cells.getCell("J" + row);
			cell.setValue(tongGiaTriDaKH);
			cell.setStyle(style);
			cell = CreateBorderSetting(cell, 41);
			// Giá trị còn lại
			cell = cells.getCell("K" + row);
			cell.setValue(tongGiaTriConLai);
			cell.setStyle(style);
			cell = CreateBorderSetting(cell, 41);
			// Lý do giảm
			cell = cells.getCell("L" + row);
			cell.setValue("");
			cell.setStyle(style);
			cell = CreateBorderSetting(cell, 0);
			 
			
			row++;
			cell = cells.getCell("B"+(row+1));
			cell.setValue("Người lập biểu");
			cell = cells.getCell("D"+(row+1));
			cell.setValue("Kế toán trưởng");
			cell = cells.getCell("H"+row);
			cell.setValue("Ngày   "+ "Tháng    "+"Năm    ");
			cell= cells.getCell("H"+(row+1));
			cell.setValue("Tổng giám đốc");
			
			
			workbook.save(out);
			fstream.close();

		} catch (Exception ex) {
			System.out.println(ex.toString());
			ex.printStackTrace();
			throw new Exception("Không tạo được báo cáo");
		}

	}

	private void CreateReportKhauHaoDonVi(OutputStream out, IErp_BaoCaoSuDungTSCD obj) throws Exception {

		try {
			String file = getServletContext().getInitParameter("path") + "\\BaoCaoKHTSCDTheoDonVi.xlsm";

			System.out.println(file);

			FileInputStream fstream = new FileInputStream(file);

			Workbook workbook = new Workbook();
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);

			Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet("Sheet1");

			Cells cells = worksheet.getCells();

			Cell cell;
			Style style;

			Font font = new Font();
			font.setColor(Color.NAVY);
			font.setSize(13);
			font.setBold(true);

			cell = cells.getCell("A1");
			cell.setValue("" + obj.getCtyTen());
			style = cell.getStyle();
			style.setFont(font);
			style.setHAlignment(TextAlignmentType.LEFT);
			cell.setStyle(style);

			cells.setRowHeight(1, 20.0f);
			cell = cells.getCell("A2");
			cell.setValue("Địa chỉ: " + obj.getDiachi());
			style = cell.getStyle();
			style.setFont(font);
			style.setHAlignment(TextAlignmentType.LEFT);
			cell.setStyle(style);

			cells.setRowHeight(2, 20.0f);
			cell = cells.getCell("A3");
			cell.setValue("Mã số thuế: " + obj.getMasothue());
			style = cell.getStyle();
			style.setFont(font);
			style.setHAlignment(TextAlignmentType.LEFT);
			cell.setStyle(style);
			
			
			
			cell = cells.getCell("F6");
			String ngaythang="";
			if(obj.getMonth().length()>0)
			{
				ngaythang="Tháng "+obj.getMonth() + " năm "+obj.getYear();
			}else
				ngaythang="Năm "+obj.getYear();
			cell.setValue("" + ngaythang); 
			style = cell.getStyle();
			style.setFont(font);
			style.setHAlignment(TextAlignmentType.CENTER);
			cell.setStyle(style);
			
			if(obj.getDvthId().length()>0)
			{
				cell = cells.getCell("F7");
				String donvi=obj.getDonViTen();
				cell.setValue("Đơn vị:" + donvi); 
				style = cell.getStyle();
				style.setFont(font);
				style.setHAlignment(TextAlignmentType.CENTER);
				cell.setStyle(style);
			}
			
			double tongSoThangKH=0;
			double tongSoThangDaKH=0;
			double tongConLaiDauKy=0;
			double tongConLaiCuoiKy=0;
			double tongKhauHaoTrongKy=0;
			double tongNguyenGiaDauKy=0;
			double tongNguyenGiaCuoiKy=0;
			double tongKhauHaoDauKy=0;
			double tongKhauHaoCuoiKy=0;
			///////// theo loại
			
			double tongSoThangKHTheoLoaiTS=0;
			double tongSoThangDaKHTheoLoaiTS=0;
			double tongConLaiDauKyTheoLoaiTS=0;
			double tongConLaiCuoiKyTheoLoaiTS=0;
			double tongKhauHaoTrongKyTheoLoaiTS=0;
			double tongNguyenGiaDauKyTheoLoaiTS=0;
			double tongNguyenGiaCuoiKyTheoLoaiTS=0;
			double tongKhauHaoDauKyTheoLoaiTS=0;
			double tongKhauHaoCuoiKyTheoLoaiTS=0;
			String loaitaisanOld="";

			String query = queryKhauHaoDonVi(obj);
			System.out.println("[CreateReportKhauHaoDonVi] :"+query);
			obj.createRsBaoCao(query);
			ResultSet rs = obj.getRsBaoCao();
			String maTSCDNew ="";
			String maTSCDOld="";
			int rowmergeEnd=0;
			int rowmergeEndStart=0;
			boolean isSetrowmerge= false;
			boolean isMerge =false;
			
			int row = 10;
			if (rs != null) {
				while (rs.next()) {
					String loaitaisanNew=rs.getString("LOAITAISAN");
					

					if((loaitaisanOld!="") && (!loaitaisanOld.equals(loaitaisanNew)))
					{
						cell = cells.getCell("A" + row);
						cell.setValue("");
						font.setColor(Color.BLACK);	
						font.setSize(10);
						font.setBold(false);
						style.setFont(font);
						style.setNumber(0);
						style.setHAlignment(TextAlignmentType.LEFT);
						cell.setStyle(style);
						cell = CreateBorderSetting(cell,0);
						
						
						cell = cells.getCell("B"+row);			
						cell.setValue("TỔNG CỘNG");
						font.setColor(Color.BLACK);	
						font.setSize(10);
						font.setBold(false);
						style.setFont(font);
						style.setNumber(0);
						style.setHAlignment(TextAlignmentType.LEFT);
						cell.setStyle(style);
						cell = CreateBorderSetting(cell,0);
						
						cell = cells.getCell("C"+row);			
						cell.setValue("");
						font.setColor(Color.BLACK);	
						font.setSize(10);
						font.setBold(false);
						style.setFont(font);
						style.setNumber(0);
						style.setHAlignment(TextAlignmentType.LEFT);
						cell.setStyle(style);
						cell = CreateBorderSetting(cell,0);
						
						
						cell = cells.getCell("D"+row);			
						cell.setValue("");
						font.setColor(Color.BLACK);	
						font.setSize(10);
						font.setBold(false);
						style.setFont(font);
						style.setNumber(0);
						style.setHAlignment(TextAlignmentType.LEFT);
						cell.setStyle(style);
						cell = CreateBorderSetting(cell,0);
						
						
						cell = cells.getCell("E"+row);			
						cell.setValue("");
						font.setColor(Color.BLACK);	
						font.setSize(10);
						font.setBold(false);
						style.setFont(font);
						style.setNumber(0);
						style.setHAlignment(TextAlignmentType.LEFT);
						cell.setStyle(style);
						cell = CreateBorderSetting(cell,0);
						
						
						cell = cells.getCell("F"+row);			
						cell.setValue("");
						font.setColor(Color.BLACK);	
						font.setSize(10);
						font.setBold(false);
						style.setFont(font);
						style.setNumber(0);
						style.setHAlignment(TextAlignmentType.LEFT);
						cell.setStyle(style);
						cell = CreateBorderSetting(cell,0);
						
						
						cell = cells.getCell("G"+row);			
						cell.setValue(tongSoThangKHTheoLoaiTS);
						font.setColor(Color.BLACK);	
						font.setSize(10);
						font.setBold(false);
						style.setFont(font);
						style.setNumber(0);
						style.setHAlignment(TextAlignmentType.LEFT);
						cell.setStyle(style);
						cell = CreateBorderSetting(cell,41);
						
						
						cell = cells.getCell("H"+row);			
						cell.setValue(tongSoThangDaKHTheoLoaiTS);
						font.setColor(Color.BLACK);	
						font.setSize(10);
						font.setBold(false);
						style.setFont(font);
						style.setNumber(0);
						style.setHAlignment(TextAlignmentType.LEFT);
						cell.setStyle(style);
						cell = CreateBorderSetting(cell,41);
						
						
						cell = cells.getCell("I"+row);			
						cell.setValue(tongNguyenGiaDauKyTheoLoaiTS);
						font.setColor(Color.BLACK);	
						font.setSize(10);
						font.setBold(false);
						style.setFont(font);
						style.setNumber(0);
						style.setHAlignment(TextAlignmentType.LEFT);
						cell.setStyle(style);
						cell = CreateBorderSetting(cell,41);
						
						
						cell = cells.getCell("J"+row);			
						cell.setValue(tongKhauHaoDauKyTheoLoaiTS);
						font.setColor(Color.BLACK);	
						font.setSize(10);
						font.setBold(false);
						style.setFont(font);
						style.setNumber(0);
						style.setHAlignment(TextAlignmentType.LEFT);
						cell.setStyle(style);
						cell = CreateBorderSetting(cell,41);
						
						
						cell = cells.getCell("K"+row);			
						cell.setValue(tongConLaiDauKyTheoLoaiTS);
						font.setColor(Color.BLACK);	
						font.setSize(10);
						font.setBold(false);
						style.setFont(font);
						style.setNumber(0);
						style.setHAlignment(TextAlignmentType.LEFT);
						cell.setStyle(style);
						cell = CreateBorderSetting(cell,41);
						
						
						cell = cells.getCell("L"+row);			
						cell.setValue(tongKhauHaoTrongKyTheoLoaiTS);
						font.setColor(Color.BLACK);	
						font.setSize(10);
						font.setBold(false);
						style.setFont(font);
						style.setNumber(0);
						style.setHAlignment(TextAlignmentType.LEFT);
						cell.setStyle(style);
						cell = CreateBorderSetting(cell,41);
						
						
						cell = cells.getCell("M"+row);			
						cell.setValue(tongNguyenGiaCuoiKyTheoLoaiTS);
						font.setColor(Color.BLACK);	
						font.setSize(10);
						font.setBold(false);
						style.setFont(font);
						style.setNumber(0);
						style.setHAlignment(TextAlignmentType.LEFT);
						cell.setStyle(style);
						cell = CreateBorderSetting(cell,41);
						
						
						cell = cells.getCell("N"+row);			
						cell.setValue(tongKhauHaoCuoiKyTheoLoaiTS);
						font.setColor(Color.BLACK);	
						font.setSize(10);
						font.setBold(false);
						style.setFont(font);
						style.setNumber(0);
						style.setHAlignment(TextAlignmentType.LEFT);
						cell.setStyle(style);
						cell = CreateBorderSetting(cell,41);
						
						
						cell = cells.getCell("O"+row);			
						cell.setValue(tongConLaiCuoiKyTheoLoaiTS);
						font.setColor(Color.BLACK);	
						font.setSize(10);
						font.setBold(false);
						style.setFont(font);
						style.setNumber(0);
						style.setHAlignment(TextAlignmentType.LEFT);
						cell.setStyle(style);
						cell = CreateBorderSetting(cell,41);
						row++;
						
						tongSoThangKHTheoLoaiTS=0;
						tongSoThangDaKHTheoLoaiTS=0;
						tongConLaiDauKyTheoLoaiTS=0;
						tongConLaiCuoiKyTheoLoaiTS=0;
						tongKhauHaoTrongKyTheoLoaiTS=0;
						tongNguyenGiaDauKyTheoLoaiTS=0;
						tongNguyenGiaCuoiKyTheoLoaiTS=0;
						tongKhauHaoDauKyTheoLoaiTS=0;
						tongKhauHaoCuoiKyTheoLoaiTS=0;
					
					}
					cell = cells.getCell("A" + row);
					cell.setValue("" + rs.getString("LOAITAISAN"));
					font.setColor(Color.BLACK);	
					font.setSize(10);
					font.setBold(false);
					style.setFont(font);
					style.setNumber(0);
					style.setHAlignment(TextAlignmentType.LEFT);
					cell.setStyle(style);
					cell = CreateBorderSetting(cell,0);
					
					
					cell = cells.getCell("B"+row);			
					cell.setValue("" +rs.getString("MATSCD"));
					font.setColor(Color.BLACK);	
					font.setSize(10);
					font.setBold(false);
					style.setFont(font);
					style.setNumber(0);
					style.setHAlignment(TextAlignmentType.LEFT);
					cell.setStyle(style);
					cell = CreateBorderSetting(cell,0);
					
					cell = cells.getCell("C"+row);			
					cell.setValue("" +rs.getString("TENTSCD"));
					font.setColor(Color.BLACK);	
					font.setSize(10);
					font.setBold(false);
					style.setFont(font);
					style.setNumber(0);
					style.setHAlignment(TextAlignmentType.LEFT);
					cell.setStyle(style);
					cell = CreateBorderSetting(cell,0);
					
					
					cell = cells.getCell("D"+row);			
					cell.setValue("" +rs.getString("NGAYGHITANG"));
					font.setColor(Color.BLACK);	
					font.setSize(10);
					font.setBold(false);
					style.setFont(font);
					style.setNumber(0);
					style.setHAlignment(TextAlignmentType.LEFT);
					cell.setStyle(style);
					cell = CreateBorderSetting(cell,0);
					
					
					cell = cells.getCell("E"+row);			
					cell.setValue("" +rs.getString("NGAYBATDAUKH"));
					font.setColor(Color.BLACK);	
					font.setSize(10);
					font.setBold(false);
					style.setFont(font);
					style.setNumber(0);
					style.setHAlignment(TextAlignmentType.LEFT);
					cell.setStyle(style);
					cell = CreateBorderSetting(cell,0);
					
					
					cell = cells.getCell("F"+row);			
					cell.setValue("" +rs.getString("DONVI"));
					font.setColor(Color.BLACK);	
					font.setSize(10);
					font.setBold(false);
					style.setFont(font);
					style.setNumber(0);
					style.setHAlignment(TextAlignmentType.LEFT);
					cell.setStyle(style);
					cell = CreateBorderSetting(cell,0);
					
					
					cell = cells.getCell("G"+row);			
					cell.setValue(rs.getInt("SOTHANGKH"));
					font.setColor(Color.BLACK);	
					font.setSize(10);
					font.setBold(false);
					style.setFont(font);
					style.setNumber(0);
					style.setHAlignment(TextAlignmentType.LEFT);
					cell.setStyle(style);
					cell = CreateBorderSetting(cell,41);
					
					
					cell = cells.getCell("H"+row);			
					cell.setValue(rs.getInt("SOTHANGDAKHAUHAO"));
					font.setColor(Color.BLACK);	
					font.setSize(10);
					font.setBold(false);
					style.setFont(font);
					style.setNumber(0);
					style.setHAlignment(TextAlignmentType.LEFT);
					cell.setStyle(style);
					cell = CreateBorderSetting(cell,41);
					
					
					cell = cells.getCell("I"+row);			
					cell.setValue(rs.getDouble("NGUYENGIADAUKY"));
					font.setColor(Color.BLACK);	
					font.setSize(10);
					font.setBold(false);
					style.setFont(font);
					style.setNumber(0);
					style.setHAlignment(TextAlignmentType.LEFT);
					cell.setStyle(style);
					cell = CreateBorderSetting(cell,41);
					
					
					cell = cells.getCell("J"+row);			
					cell.setValue(rs.getDouble("KHAUHAODAUKY"));
					font.setColor(Color.BLACK);	
					font.setSize(10);
					font.setBold(false);
					style.setFont(font);
					style.setNumber(0);
					style.setHAlignment(TextAlignmentType.LEFT);
					cell.setStyle(style);
					cell = CreateBorderSetting(cell,41);
					
					
					cell = cells.getCell("K"+row);			
					cell.setValue(rs.getDouble("CONLAIDAUKY"));
					font.setColor(Color.BLACK);	
					font.setSize(10);
					font.setBold(false);
					style.setFont(font);
					style.setNumber(0);
					style.setHAlignment(TextAlignmentType.LEFT);
					cell.setStyle(style);
					cell = CreateBorderSetting(cell,41);
					
					
					cell = cells.getCell("L"+row);			
					cell.setValue(rs.getDouble("KHAUHAOTRONGKY"));
					font.setColor(Color.BLACK);	
					font.setSize(10);
					font.setBold(false);
					style.setFont(font);
					style.setNumber(0);
					style.setHAlignment(TextAlignmentType.LEFT);
					cell.setStyle(style);
					cell = CreateBorderSetting(cell,41);
					
					
					cell = cells.getCell("M"+row);			
					cell.setValue(rs.getDouble("NGUYENGIACUOIKY"));
					font.setColor(Color.BLACK);	
					font.setSize(10);
					font.setBold(false);
					style.setFont(font);
					style.setNumber(0);
					style.setHAlignment(TextAlignmentType.LEFT);
					cell.setStyle(style);
					cell = CreateBorderSetting(cell,41);
					
					
					cell = cells.getCell("N"+row);			
					cell.setValue(rs.getDouble("KHAUHAOCUOIKY"));
					font.setColor(Color.BLACK);	
					font.setSize(10);
					font.setBold(false);
					style.setFont(font);
					style.setNumber(0);
					style.setHAlignment(TextAlignmentType.LEFT);
					cell.setStyle(style);
					cell = CreateBorderSetting(cell,41);
					
					
					cell = cells.getCell("O"+row);			
					cell.setValue(rs.getDouble("CONLAICUOIKY"));
					font.setColor(Color.BLACK);	
					font.setSize(10);
					font.setBold(false);
					style.setFont(font);
					style.setNumber(0);
					style.setHAlignment(TextAlignmentType.LEFT);
					cell.setStyle(style);
					cell = CreateBorderSetting(cell,41);
					
					maTSCDNew = rs.getString("MATSCD");
					
					
					/// ĐOẠN NÀY LÀ. KIỂM TRA NẾU GIỐNG LẦN ĐẦU TIÊN. CỐ ĐỊNH ĐIỂM BẮT ĐẦU MERGE.
					if(maTSCDOld.equals(maTSCDNew) && !isSetrowmerge)
					{
						System.out.println("row :" +(row-2) + "row 2 :" +(row-1));
						// merge dòng trước và dòng này
						isSetrowmerge=true;
						isMerge=false;
						rowmergeEndStart = row-2;
					}
					System.out.println("CŨ :"+maTSCDOld);
					System.out.println("Mới :"+maTSCDOld);
					// NẾU CÒN GIỐNG TIẾP THÌ SET ĐIỂM KẾT THÚC MERGE
					if(maTSCDOld.equals(maTSCDNew) && isSetrowmerge )
					{
						rowmergeEnd = row-1;
					}
					// NẾU MÀ KHÁC RỒI THÌ MERGE
					if(!maTSCDOld.equals("") &&  !maTSCDOld.equals(maTSCDNew)&& isSetrowmerge)
					{
						System.out.println("row :" +(row-2) + "row 2 :" +(row-1));
						isSetrowmerge=false;
						isMerge=true;
					}
					//Merge những thông tin chung để tránh bị double giá trị.
					if(isMerge)
					{
						cells.merge(rowmergeEndStart, 0 , rowmergeEnd, 0);
						cells.merge(rowmergeEndStart, 1,  rowmergeEnd, 1);
						cells.merge(rowmergeEndStart, 2 , rowmergeEnd, 2);
						cells.merge(rowmergeEndStart, 3 , rowmergeEnd, 3);
						cells.merge(rowmergeEndStart, 4, rowmergeEnd, 4);
						cells.merge(rowmergeEndStart, 6, rowmergeEnd, 6);
						cells.merge(rowmergeEndStart, 7, rowmergeEnd, 7);
						cells.merge(rowmergeEndStart, 8, rowmergeEnd, 8);
						cells.merge(rowmergeEndStart, 10, rowmergeEnd, 10);
						cells.merge(rowmergeEndStart, 12, rowmergeEnd, 12);
						cells.merge(rowmergeEndStart, 14, rowmergeEnd, 14);
						cells.merge(rowmergeEndStart, 16, rowmergeEnd, 16);
						isMerge=false;
					}
					
					
					
					if(!maTSCDOld.equals(maTSCDNew))
					{
						
					tongNguyenGiaDauKy += rs.getDouble("NGUYENGIADAUKY");		
					tongSoThangKH +=rs.getDouble("SOTHANGKH");
					tongSoThangDaKH +=rs.getDouble("SOTHANGDAKHAUHAO");
					tongConLaiDauKy+=rs.getDouble("CONLAIDAUKY");
					tongConLaiCuoiKy+=rs.getDouble("CONLAICUOIKY");
					tongNguyenGiaCuoiKy+=rs.getDouble("KHAUHAOTRONGKY");
					
					}
					tongKhauHaoTrongKy+=rs.getDouble("KHAUHAOTRONGKY");
					tongKhauHaoDauKy+=rs.getDouble("KHAUHAODAUKY");
					tongKhauHaoCuoiKy+=rs.getDouble("KHAUHAOCUOIKY");
					
					
					/// CỘNG CHO TỪNG LOẠI
					if(!maTSCDOld.equals(maTSCDNew))
					{
					tongNguyenGiaDauKyTheoLoaiTS += rs.getDouble("NGUYENGIADAUKY");		
					tongSoThangKHTheoLoaiTS +=rs.getDouble("SOTHANGKH");
					tongSoThangDaKHTheoLoaiTS +=rs.getDouble("SOTHANGDAKHAUHAO");
					tongNguyenGiaCuoiKyTheoLoaiTS+=rs.getDouble("KHAUHAOTRONGKY");
					tongConLaiDauKyTheoLoaiTS+=rs.getDouble("CONLAIDAUKY");
					tongConLaiCuoiKyTheoLoaiTS+=rs.getDouble("CONLAICUOIKY");
					
					}
					tongKhauHaoDauKyTheoLoaiTS+=rs.getDouble("KHAUHAODAUKY");
					tongKhauHaoCuoiKyTheoLoaiTS+=rs.getDouble("KHAUHAOCUOIKY");
					tongKhauHaoTrongKyTheoLoaiTS+=rs.getDouble("KHAUHAOTRONGKY");
					
					row++;
					
					/// set lại loại mới
					
					loaitaisanOld=loaitaisanNew;
					maTSCDOld=maTSCDNew;
					
				}
			}
			maTSCDNew="";
			///// DÀNH CHO TRƯỜNG HỢP DÒNG CUỐI CÙNG, KHI ĐÃ THOÁT RA RS K THỂ KIỂM TRA ĐƯỢC. -> KIỂM TRA Ở NGOÀI LẦN NỮA
			if(maTSCDOld.equals(maTSCDNew) && !isSetrowmerge)
			{
				System.out.println("row :" +(row-2) + "row 2 :" +(row-1));
				// merge dòng trước và dòng này
				isSetrowmerge=true;
				isMerge=false;
				rowmergeEndStart = row-2;
			}

			// NẾU CÒN GIỐNG TIẾP THÌ SET ĐIỂM KẾT THÚC MERGE
			if(maTSCDOld.equals(maTSCDNew) && isSetrowmerge )
			{
				rowmergeEnd = row-1;
			}
			// NẾU MÀ KHÁC RỒI THÌ MERGE
			if(!maTSCDOld.equals("") &&  !maTSCDOld.equals(maTSCDNew)&& isSetrowmerge)
			{
				System.out.println("row :" +(row-2) + "row 2 :" +(row-1));
				isSetrowmerge=false;
				isMerge=true;
			}
			//Merge những thông tin chung để tránh bị double giá trị.
			if(isMerge)
			{
				cells.merge(rowmergeEndStart, 0 , rowmergeEnd, 0);
				cells.merge(rowmergeEndStart, 1,  rowmergeEnd, 1);
				cells.merge(rowmergeEndStart, 2 , rowmergeEnd, 2);
				cells.merge(rowmergeEndStart, 3 , rowmergeEnd, 3);
				cells.merge(rowmergeEndStart, 4, rowmergeEnd, 4);
				cells.merge(rowmergeEndStart, 6, rowmergeEnd, 6);
				cells.merge(rowmergeEndStart, 7, rowmergeEnd, 7);
				cells.merge(rowmergeEndStart, 8, rowmergeEnd, 8);
				cells.merge(rowmergeEndStart, 10, rowmergeEnd, 10);
				cells.merge(rowmergeEndStart, 12, rowmergeEnd, 12);
				cells.merge(rowmergeEndStart, 14, rowmergeEnd, 14);
				cells.merge(rowmergeEndStart, 16, rowmergeEnd, 16);
				isMerge=false;
			}
			
			
			//////////////TỔNG CỘNG CỦA LTS CUỐI CÙNG
			
			cell = cells.getCell("A" + row);
			cell.setValue("");
			font.setColor(Color.BLACK);	
			font.setSize(10);
			font.setBold(false);
			style.setFont(font);
			style.setNumber(0);
			style.setHAlignment(TextAlignmentType.LEFT);
			cell.setStyle(style);
			cell = CreateBorderSetting(cell,0);
			
			
			cell = cells.getCell("B"+row);			
			cell.setValue("TỔNG CỘNG");
			font.setColor(Color.BLACK);	
			font.setSize(10);
			font.setBold(false);
			style.setFont(font);
			style.setNumber(0);
			style.setHAlignment(TextAlignmentType.LEFT);
			cell.setStyle(style);
			cell = CreateBorderSetting(cell,0);
			
			cell = cells.getCell("C"+row);			
			cell.setValue("");
			font.setColor(Color.BLACK);	
			font.setSize(10);
			font.setBold(false);
			style.setFont(font);
			style.setNumber(0);
			style.setHAlignment(TextAlignmentType.LEFT);
			cell.setStyle(style);
			cell = CreateBorderSetting(cell,0);
			
			
			cell = cells.getCell("D"+row);			
			cell.setValue("");
			font.setColor(Color.BLACK);	
			font.setSize(10);
			font.setBold(false);
			style.setFont(font);
			style.setNumber(0);
			style.setHAlignment(TextAlignmentType.LEFT);
			cell.setStyle(style);
			cell = CreateBorderSetting(cell,0);
			
			
			cell = cells.getCell("E"+row);			
			cell.setValue("");
			font.setColor(Color.BLACK);	
			font.setSize(10);
			font.setBold(false);
			style.setFont(font);
			style.setNumber(0);
			style.setHAlignment(TextAlignmentType.LEFT);
			cell.setStyle(style);
			cell = CreateBorderSetting(cell,0);
			
			
			cell = cells.getCell("F"+row);			
			cell.setValue("");
			font.setColor(Color.BLACK);	
			font.setSize(10);
			font.setBold(false);
			style.setFont(font);
			style.setNumber(0);
			style.setHAlignment(TextAlignmentType.LEFT);
			cell.setStyle(style);
			cell = CreateBorderSetting(cell,0);
			
			
			cell = cells.getCell("G"+row);			
			cell.setValue(tongSoThangKHTheoLoaiTS);
			font.setColor(Color.BLACK);	
			font.setSize(10);
			font.setBold(false);
			style.setFont(font);
			style.setNumber(0);
			style.setHAlignment(TextAlignmentType.LEFT);
			cell.setStyle(style);
			cell = CreateBorderSetting(cell,41);
			
			
			cell = cells.getCell("H"+row);			
			cell.setValue(tongSoThangDaKHTheoLoaiTS);
			font.setColor(Color.BLACK);	
			font.setSize(10);
			font.setBold(false);
			style.setFont(font);
			style.setNumber(0);
			style.setHAlignment(TextAlignmentType.LEFT);
			cell.setStyle(style);
			cell = CreateBorderSetting(cell,41);
			
			
			cell = cells.getCell("I"+row);			
			cell.setValue(tongNguyenGiaDauKyTheoLoaiTS);
			font.setColor(Color.BLACK);	
			font.setSize(10);
			font.setBold(false);
			style.setFont(font);
			style.setNumber(0);
			style.setHAlignment(TextAlignmentType.LEFT);
			cell.setStyle(style);
			cell = CreateBorderSetting(cell,41);
			
			
			cell = cells.getCell("J"+row);			
			cell.setValue(tongKhauHaoDauKyTheoLoaiTS);
			font.setColor(Color.BLACK);	
			font.setSize(10);
			font.setBold(false);
			style.setFont(font);
			style.setNumber(0);
			style.setHAlignment(TextAlignmentType.LEFT);
			cell.setStyle(style);
			cell = CreateBorderSetting(cell,41);
			
			
			cell = cells.getCell("K"+row);			
			cell.setValue(tongConLaiDauKyTheoLoaiTS);
			font.setColor(Color.BLACK);	
			font.setSize(10);
			font.setBold(false);
			style.setFont(font);
			style.setNumber(0);
			style.setHAlignment(TextAlignmentType.LEFT);
			cell.setStyle(style);
			cell = CreateBorderSetting(cell,41);
			
			
			cell = cells.getCell("L"+row);			
			cell.setValue(tongKhauHaoTrongKyTheoLoaiTS);
			font.setColor(Color.BLACK);	
			font.setSize(10);
			font.setBold(false);
			style.setFont(font);
			style.setNumber(0);
			style.setHAlignment(TextAlignmentType.LEFT);
			cell.setStyle(style);
			cell = CreateBorderSetting(cell,41);
			
			
			cell = cells.getCell("M"+row);			
			cell.setValue(tongNguyenGiaCuoiKyTheoLoaiTS);
			font.setColor(Color.BLACK);	
			font.setSize(10);
			font.setBold(false);
			style.setFont(font);
			style.setNumber(0);
			style.setHAlignment(TextAlignmentType.LEFT);
			cell.setStyle(style);
			cell = CreateBorderSetting(cell,41);
			
			
			cell = cells.getCell("N"+row);			
			cell.setValue(tongKhauHaoCuoiKyTheoLoaiTS);
			font.setColor(Color.BLACK);	
			font.setSize(10);
			font.setBold(false);
			style.setFont(font);
			style.setNumber(0);
			style.setHAlignment(TextAlignmentType.LEFT);
			cell.setStyle(style);
			cell = CreateBorderSetting(cell,41);
			
			
			cell = cells.getCell("O"+row);			
			cell.setValue(tongConLaiCuoiKyTheoLoaiTS);
			font.setColor(Color.BLACK);	
			font.setSize(10);
			font.setBold(false);
			style.setFont(font);
			style.setNumber(0);
			style.setHAlignment(TextAlignmentType.LEFT);
			cell.setStyle(style);
			cell = CreateBorderSetting(cell,41);
			
			
			row++;
			
			
			cell = cells.getCell("A" + row);
			cell.setValue("TỔNG CỘNG");
			font.setColor(Color.BLACK);	
			font.setSize(10);
			font.setBold(false);
			style.setFont(font);
			style.setNumber(0);
			style.setHAlignment(TextAlignmentType.LEFT);
			cell.setStyle(style);
			cell = CreateBorderSetting(cell,0);
			
			
			cell = cells.getCell("B"+row);			
			cell.setValue("");
			font.setColor(Color.BLACK);	
			font.setSize(10);
			font.setBold(false);
			style.setFont(font);
			style.setNumber(0);
			style.setHAlignment(TextAlignmentType.LEFT);
			cell.setStyle(style);
			cell = CreateBorderSetting(cell,0);
			
			cell = cells.getCell("C"+row);			
			cell.setValue("");
			font.setColor(Color.BLACK);	
			font.setSize(10);
			font.setBold(false);
			style.setFont(font);
			style.setNumber(0);
			style.setHAlignment(TextAlignmentType.LEFT);
			cell.setStyle(style);
			cell = CreateBorderSetting(cell,0);
			
			
			cell = cells.getCell("D"+row);			
			cell.setValue("");
			font.setColor(Color.BLACK);	
			font.setSize(10);
			font.setBold(false);
			style.setFont(font);
			style.setNumber(0);
			style.setHAlignment(TextAlignmentType.LEFT);
			cell.setStyle(style);
			cell = CreateBorderSetting(cell,0);
			
			
			cell = cells.getCell("E"+row);			
			cell.setValue("");
			font.setColor(Color.BLACK);	
			font.setSize(10);
			font.setBold(false);
			style.setFont(font);
			style.setNumber(0);
			style.setHAlignment(TextAlignmentType.LEFT);
			cell.setStyle(style);
			cell = CreateBorderSetting(cell,0);
			
			
			cell = cells.getCell("F"+row);			
			cell.setValue("");
			font.setColor(Color.BLACK);	
			font.setSize(10);
			font.setBold(false);
			style.setFont(font);
			style.setNumber(0);
			style.setHAlignment(TextAlignmentType.LEFT);
			cell.setStyle(style);
			cell = CreateBorderSetting(cell,0);
			
			
			cell = cells.getCell("G"+row);			
			cell.setValue(tongSoThangKH);
			font.setColor(Color.BLACK);	
			font.setSize(10);
			font.setBold(false);
			style.setFont(font);
			style.setNumber(0);
			style.setHAlignment(TextAlignmentType.LEFT);
			cell.setStyle(style);
			cell = CreateBorderSetting(cell,41);
			
			
			cell = cells.getCell("H"+row);			
			cell.setValue(tongSoThangDaKH);
			font.setColor(Color.BLACK);	
			font.setSize(10);
			font.setBold(false);
			style.setFont(font);
			style.setNumber(0);
			style.setHAlignment(TextAlignmentType.LEFT);
			cell.setStyle(style);
			cell = CreateBorderSetting(cell,41);
			
			
			cell = cells.getCell("I"+row);			
			cell.setValue(tongNguyenGiaDauKy);
			font.setColor(Color.BLACK);	
			font.setSize(10);
			font.setBold(false);
			style.setFont(font);
			style.setNumber(0);
			style.setHAlignment(TextAlignmentType.LEFT);
			cell.setStyle(style);
			cell = CreateBorderSetting(cell,41);
			
			
			cell = cells.getCell("J"+row);			
			cell.setValue(tongKhauHaoDauKy);
			font.setColor(Color.BLACK);	
			font.setSize(10);
			font.setBold(false);
			style.setFont(font);
			style.setNumber(0);
			style.setHAlignment(TextAlignmentType.LEFT);
			cell.setStyle(style);
			cell = CreateBorderSetting(cell,41);
			
			
			cell = cells.getCell("K"+row);			
			cell.setValue(tongConLaiDauKy);
			font.setColor(Color.BLACK);	
			font.setSize(10);
			font.setBold(false);
			style.setFont(font);
			style.setNumber(0);
			style.setHAlignment(TextAlignmentType.LEFT);
			cell.setStyle(style);
			cell = CreateBorderSetting(cell,41);
			
			
			cell = cells.getCell("L"+row);			
			cell.setValue(tongKhauHaoTrongKy);
			font.setColor(Color.BLACK);	
			font.setSize(10);
			font.setBold(false);
			style.setFont(font);
			style.setNumber(0);
			style.setHAlignment(TextAlignmentType.LEFT);
			cell.setStyle(style);
			cell = CreateBorderSetting(cell,41);
			
			
			cell = cells.getCell("M"+row);			
			cell.setValue(tongNguyenGiaCuoiKy);
			font.setColor(Color.BLACK);	
			font.setSize(10);
			font.setBold(false);
			style.setFont(font);
			style.setNumber(0);
			style.setHAlignment(TextAlignmentType.LEFT);
			cell.setStyle(style);
			cell = CreateBorderSetting(cell,41);
			
			
			cell = cells.getCell("N"+row);			
			cell.setValue(tongKhauHaoCuoiKy);
			font.setColor(Color.BLACK);	
			font.setSize(10);
			font.setBold(false);
			style.setFont(font);
			style.setNumber(0);
			style.setHAlignment(TextAlignmentType.LEFT);
			cell.setStyle(style);
			cell = CreateBorderSetting(cell,41);
			
			
			cell = cells.getCell("O"+row);			
			cell.setValue(tongConLaiCuoiKy);
			font.setColor(Color.BLACK);	
			font.setSize(10);
			font.setBold(false);
			style.setFont(font);
			style.setNumber(0);
			style.setHAlignment(TextAlignmentType.LEFT);
			cell.setStyle(style);
			cell = CreateBorderSetting(cell,41);
			
			
			
			
			
			row ++;
			cell = cells.getCell("B"+(row+1));
			cell.setValue("Người lập biểu");
			cell = cells.getCell("D"+(row+1));
			cell.setValue("Kế toán trưởng");
			cell = cells.getCell("H"+row);
			cell.setValue("Ngày   "+ "Tháng    "+"Năm    ");
			cell= cells.getCell("H"+(row+1));
			cell.setValue("Tổng giám đốc");
			workbook.save(out);
			fstream.close();

		} catch (Exception ex) {
			System.out.println(ex.toString());
			ex.printStackTrace();
			throw new Exception("Không tạo được báo cáo");
		}

	}

	private void CreateReportKhauHaoChiTiet(OutputStream out, IErp_BaoCaoSuDungTSCD obj) throws Exception {

		try {
			String file = getServletContext().getInitParameter("path") + "\\BaoCaoKHTSCDChiTiet.xlsm";

			System.out.println(file);

			FileInputStream fstream = new FileInputStream(file);

			Workbook workbook = new Workbook();
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);

			Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet("Sheet1");

			Cells cells = worksheet.getCells();
			Cell cell = cells.getCell("A1");
			Style style;
			Font font = new Font();
			font.setColor(Color.NAVY);
			font.setSize(13);
			font.setBold(true);

			cell = cells.getCell("A1");
			cell.setValue("" + obj.getCtyTen());
			style = cell.getStyle();
			style.setFont(font);
			style.setHAlignment(TextAlignmentType.LEFT);
			cell.setStyle(style);

			cells.setRowHeight(1, 20.0f);
			cell = cells.getCell("A2");
			cell.setValue("Địa chỉ: " + obj.getDiachi());
			style = cell.getStyle();
			style.setFont(font);
			style.setHAlignment(TextAlignmentType.LEFT);
			cell.setStyle(style);

			cells.setRowHeight(2, 20.0f);
			cell = cells.getCell("A3");
			cell.setValue("Mã số thuế: " + obj.getMasothue());
			style = cell.getStyle();
			style.setFont(font);
			style.setHAlignment(TextAlignmentType.LEFT);
			cell.setStyle(style);
			
			cell = cells.getCell("E6");
			String ngaythang="";
			if(obj.getMonth().length()>0)
			{
				ngaythang="Tháng "+obj.getMonth() + " năm "+obj.getYear();
			}else
				ngaythang="Năm "+obj.getYear();
			cell.setValue("" + ngaythang); 
			style = cell.getStyle();
			style.setFont(font);
			style.setHAlignment(TextAlignmentType.CENTER);
			cell.setStyle(style);
			
			
			double tongSoThangKH=0;
			double tongSoThangDaKH=0;
			double tongConLaiDauKy=0;
			double tongConLaiCuoiKy=0;
			double tongKhauHaoTrongKy=0;
			double tongNguyenGiaDauKy=0;
			double tongNguyenGiaCuoiKy=0;
			double tongKhauHaoDauKy=0;
			double tongKhauHaoCuoiKy=0;
			///////// theo loại
			
			double tongSoThangKHTheoLoaiTS=0;
			double tongSoThangDaKHTheoLoaiTS=0;
			double tongConLaiDauKyTheoLoaiTS=0;
			double tongConLaiCuoiKyTheoLoaiTS=0;
			double tongKhauHaoTrongKyTheoLoaiTS=0;
			double tongNguyenGiaDauKyTheoLoaiTS=0;
			double tongNguyenGiaCuoiKyTheoLoaiTS=0;
			double tongKhauHaoDauKyTheoLoaiTS=0;
			double tongKhauHaoCuoiKyTheoLoaiTS=0;
			String loaitaisanOld="";

			
			String maTSCDNew ="";
			String maTSCDOld="";
			int rowmergeEnd=0;
			int rowmergeEndStart=0;
			boolean isSetrowmerge= false;
			boolean isMerge =false;
			
			String query = queryKhauHaoChiTiet(obj);
			System.out.println("[CreateReportKhauHaoChiTiet]: "+query);
			obj.createRsBaoCao(query);
			ResultSet rs = obj.getRsBaoCao();
			int row = 10;
			if (rs != null) {
				font.setColor(Color.BLACK);
				font.setSize(10);
				font.setBold(false);
				style = cell.getStyle();
				style.setFont(font);
				style.setNumber(0);
				
				
				
				style.setHAlignment(TextAlignmentType.LEFT);
				while (rs.next()) {
					
					String loaitaisanNew=rs.getString("LOAITAISAN");
					

					if((loaitaisanOld!="") && (!loaitaisanOld.equals(loaitaisanNew)))
					{
						// Loại TS
						cell = cells.getCell("A" + row);
						cell.setValue("");
						cell.setStyle(style);
						cell = CreateBorderSetting2(cell, 0);
						// Mã TS
						cell = cells.getCell("B" + row);
						cell.setValue("TỔNG CỘNG");
						cell.setStyle(style);
						cell = CreateBorderSetting2(cell, 0);
						// Tên TS
						cell = cells.getCell("C" + row);
						cell.setValue("");
						cell.setStyle(style);
						cell = CreateBorderSetting2(cell, 0);
						// Đơn vị sử dụng
						cell = cells.getCell("D" + row);
						cell.setValue("");
						cell.setStyle(style);
						cell = CreateBorderSetting2(cell, 0);
						// Ngày ghi tăng
						cell = cells.getCell("E" + row);
						cell.setValue("");
						cell.setStyle(style);
						cell = CreateBorderSetting2(cell, 0);
						// Ngày bắt đầu khấu hao
						cell = cells.getCell("F" + row);
						cell.setValue("");
						cell.setStyle(style);
						cell = CreateBorderSetting2(cell, 0);
						// Thời gian khấu hao (tháng)
						cell = cells.getCell("G" + row);
						cell.setValue(tongSoThangKHTheoLoaiTS);
						cell.setStyle(style);
						cell = CreateBorderSetting2(cell, 41);
						// Số tháng đã khấu hao
						cell = cells.getCell("H" + row);
						cell.setValue(tongSoThangDaKHTheoLoaiTS);
						cell.setStyle(style);
						cell = CreateBorderSetting2(cell, 41);
						// Tài khoản chi phí
						cell = cells.getCell("I" + row);
						cell.setValue("");
						cell.setStyle(style);
						cell = CreateBorderSetting2(cell, 41);
						// Giá trị TS đầu kỳ - Nguyên giá
						cell = cells.getCell("J" + row);
						cell.setValue(tongNguyenGiaDauKyTheoLoaiTS);
						cell.setStyle(style);
						cell = CreateBorderSetting2(cell, 41);
						// Giá trị TS đầu kỳ - Khấu hao
						cell = cells.getCell("K" + row);
						cell.setValue(tongKhauHaoDauKyTheoLoaiTS);
						cell.setStyle(style);
						cell = CreateBorderSetting2(cell, 41);
						// Giá trị TS đầu kỳ - Còn lại
						cell = cells.getCell("L" + row);
						cell.setValue(tongConLaiDauKyTheoLoaiTS);
						cell.setStyle(style);
						cell = CreateBorderSetting2(cell, 41);
						// Khấu hao trong kỳ
						cell = cells.getCell("M" + row);
						cell.setValue(tongKhauHaoTrongKyTheoLoaiTS);
						cell.setStyle(style);
						cell = CreateBorderSetting2(cell, 41);
						// Giá trị TS cuối kỳ - Nguyên giá
						cell = cells.getCell("N" + row);
						cell.setValue(tongNguyenGiaCuoiKyTheoLoaiTS);
						cell.setStyle(style);
						cell = CreateBorderSetting2(cell, 41);
						// Giá trị TS cuối kỳ - Khấu hao
						cell = cells.getCell("O" + row);
						cell.setValue(tongKhauHaoCuoiKyTheoLoaiTS);
						cell.setStyle(style);
						cell = CreateBorderSetting2(cell, 41);
						// Giá trị TS cuối kỳ - Còn lại
						cell = cells.getCell("P" + row);
						cell.setValue(tongConLaiCuoiKyTheoLoaiTS);
						cell.setStyle(style);
						cell = CreateBorderSetting2(cell, 41);	
						row++;
						
						
						tongSoThangKHTheoLoaiTS=0;
						tongSoThangDaKHTheoLoaiTS=0;
						tongConLaiDauKyTheoLoaiTS=0;
						tongConLaiCuoiKyTheoLoaiTS=0;
						tongKhauHaoTrongKyTheoLoaiTS=0;
						tongNguyenGiaDauKyTheoLoaiTS=0;
						tongNguyenGiaCuoiKyTheoLoaiTS=0;
						tongKhauHaoDauKyTheoLoaiTS=0;
						tongKhauHaoCuoiKyTheoLoaiTS=0;
						
						
					}
					maTSCDNew = rs.getString("MATSCD");
					if(maTSCDOld.equals(maTSCDNew) && !isSetrowmerge)
					{
						System.out.println("row :" +(row-2) + "row 2 :" +(row-1));
						// merge dòng trước và dòng này
						isSetrowmerge=true;
						isMerge=false;
						rowmergeEndStart = row-2;
					}
					if(maTSCDOld.equals(maTSCDNew) && isSetrowmerge )
					{
						rowmergeEnd = row-1;
					}
					if(!maTSCDOld.equals("") &&  !maTSCDOld.equals(maTSCDNew)&& isSetrowmerge)
					{
						System.out.println("row :" +(row-2) + "row 2 :" +(row-1));
						// merge dòng trước và dòng này
						isSetrowmerge=false;
						isMerge=true;
					}
					
					if(isMerge)
					{
						cells.merge(rowmergeEndStart, 0 , rowmergeEnd, 0);
						cells.merge(rowmergeEndStart, 1,  rowmergeEnd, 1);
						cells.merge(rowmergeEndStart, 2 , rowmergeEnd, 2);
						cells.merge(rowmergeEndStart, 3 , rowmergeEnd, 3);
						cells.merge(rowmergeEndStart, 4, rowmergeEnd, 4);
						cells.merge(rowmergeEndStart, 5, rowmergeEnd, 5);
						cells.merge(rowmergeEndStart, 6, rowmergeEnd, 6);
						
						cells.merge(rowmergeEndStart, 7, rowmergeEnd, 7);
						
						cells.merge(rowmergeEndStart, 9, rowmergeEnd, 9);
						cells.merge(rowmergeEndStart, 11, rowmergeEnd, 11);
						cells.merge(rowmergeEndStart, 13, rowmergeEnd, 13);
						cells.merge(rowmergeEndStart, 15, rowmergeEnd, 15);
						isMerge=false;
					}
					
				
					
					
					
					// Loại TS
					cell = cells.getCell("A" + row);
					cell.setValue(rs.getString("LOAITAISAN"));
					cell.setStyle(style);
					cell = CreateBorderSetting2(cell, 0);
					// Mã TS
					cell = cells.getCell("B" + row);
					cell.setValue(rs.getString("MATSCD"));
					cell.setStyle(style);
					cell = CreateBorderSetting2(cell, 0);
					// Tên TS
					cell = cells.getCell("C" + row);
					cell.setValue(rs.getString("TENTSCD"));
					cell.setStyle(style);
					cell = CreateBorderSetting2(cell, 0);
					// Đơn vị sử dụng
					cell = cells.getCell("D" + row);
					cell.setValue(rs.getString("DVTHTEN"));
					cell.setStyle(style);
					cell = CreateBorderSetting2(cell, 0);
					// Ngày ghi tăng
					cell = cells.getCell("E" + row);
					cell.setValue(rs.getString("NGAYGHITANG"));
					cell.setStyle(style);
					cell = CreateBorderSetting2(cell, 0);
					// Ngày bắt đầu khấu hao
					cell = cells.getCell("F" + row);
					cell.setValue(rs.getString("NGAYBATDAUKH"));
					cell.setStyle(style);
					cell = CreateBorderSetting2(cell, 0);
					// Thời gian khấu hao (tháng)
					cell = cells.getCell("G" + row);
					cell.setValue(rs.getInt("SOTHANGKH"));
					cell.setStyle(style);
					cell = CreateBorderSetting2(cell, 41);
					// Số tháng đã khấu hao
					cell = cells.getCell("H" + row);
					cell.setValue(rs.getDouble("SOTHANGDAKHAUHAO"));
					cell.setStyle(style);
					cell = CreateBorderSetting2(cell, 41);
					// Tài khoản chi phí
					cell = cells.getCell("I" + row);
					cell.setValue(rs.getDouble("TAIKHOANCHIPHI"));
					cell.setStyle(style);
					cell = CreateBorderSetting2(cell, 0);
					// Giá trị TS đầu kỳ - Nguyên giá
					cell = cells.getCell("J" + row);
					cell.setValue(rs.getDouble("NGUYENGIADAUKY"));
					cell.setStyle(style);
					cell = CreateBorderSetting2(cell, 41);
					// Giá trị TS đầu kỳ - Khấu hao
					cell = cells.getCell("K" + row);
					cell.setValue(rs.getDouble("KHAUHAODAUKY"));
					cell.setStyle(style);
					cell = CreateBorderSetting2(cell, 41);
					// Giá trị TS đầu kỳ - Còn lại
					cell = cells.getCell("L" + row);
					cell.setValue(rs.getDouble("CONLAIDAUKY"));
					cell.setStyle(style);
					cell = CreateBorderSetting2(cell, 41);
					// Khấu hao trong kỳ
					cell = cells.getCell("M" + row);
					cell.setValue(rs.getDouble("KHAUHAOTRONGKY"));
					cell.setStyle(style);
					cell = CreateBorderSetting2(cell, 41);
					// Giá trị TS cuối kỳ - Nguyên giá
					cell = cells.getCell("N" + row);
					cell.setValue(rs.getDouble("NGUYENGIACUOIKY"));
					cell.setStyle(style);
					cell = CreateBorderSetting2(cell, 41);
					// Giá trị TS cuối kỳ - Khấu hao
					cell = cells.getCell("O" + row);
					cell.setValue(rs.getDouble("KHAUHAOCUOIKY"));
					cell.setStyle(style);
					cell = CreateBorderSetting2(cell, 41);
					// Giá trị TS cuối kỳ - Còn lại
					cell = cells.getCell("P" + row);
					cell.setValue(rs.getDouble("CONLAICUOIKY"));
					cell.setStyle(style);
					cell = CreateBorderSetting2(cell, 41);
					
					
					
					if(!maTSCDOld.equals(maTSCDNew))
					{
						
					tongNguyenGiaDauKy += rs.getDouble("NGUYENGIADAUKY");		
					tongSoThangKH +=rs.getDouble("SOTHANGKH");
					tongSoThangDaKH +=rs.getDouble("SOTHANGDAKHAUHAO");
					tongConLaiDauKy+=rs.getDouble("CONLAIDAUKY");
					tongConLaiCuoiKy+=rs.getDouble("CONLAICUOIKY");
					tongNguyenGiaCuoiKy+=rs.getDouble("NGUYENGIACUOIKY");
					
					}
					tongKhauHaoTrongKy+=rs.getDouble("KHAUHAOTRONGKY");
					tongKhauHaoDauKy+=rs.getDouble("KHAUHAODAUKY");
					tongKhauHaoCuoiKy+=rs.getDouble("KHAUHAOCUOIKY");
					
					
					/// CỘNG CHO TỪNG LOẠI
					if(!maTSCDOld.equals(maTSCDNew))
					{
					tongNguyenGiaDauKyTheoLoaiTS += rs.getDouble("NGUYENGIADAUKY");		
					tongSoThangKHTheoLoaiTS +=rs.getDouble("SOTHANGKH");
					tongSoThangDaKHTheoLoaiTS +=rs.getDouble("SOTHANGDAKHAUHAO");
					tongNguyenGiaCuoiKyTheoLoaiTS+=rs.getDouble("NGUYENGIACUOIKY");
					tongConLaiDauKyTheoLoaiTS+=rs.getDouble("CONLAIDAUKY");
					tongConLaiCuoiKyTheoLoaiTS+=rs.getDouble("CONLAICUOIKY");
					
					}
					tongKhauHaoDauKyTheoLoaiTS+=rs.getDouble("KHAUHAODAUKY");
					tongKhauHaoCuoiKyTheoLoaiTS+=rs.getDouble("KHAUHAOCUOIKY");
					tongKhauHaoTrongKyTheoLoaiTS+=rs.getDouble("KHAUHAOTRONGKY");
					
					row++;
					
					/// set lại loại mới
					
					loaitaisanOld=loaitaisanNew;
					maTSCDOld=maTSCDNew;
					
				}
			}
			
			if(maTSCDOld.equals(maTSCDNew) && !isSetrowmerge)
			{
				System.out.println("row :" +(row-2) + "row 2 :" +(row-1));
				// merge dòng trước và dòng này
				isSetrowmerge=true;
				isMerge=false;
				rowmergeEndStart = row-2;
			}
			if(maTSCDOld.equals(maTSCDNew) && isSetrowmerge )
			{
				rowmergeEnd = row-1;
			}
			if(!maTSCDOld.equals("") &&  !maTSCDOld.equals(maTSCDNew)&& isSetrowmerge)
			{
				System.out.println("row :" +(row-2) + "row 2 :" +(row-1));
				// merge dòng trước và dòng này
				isSetrowmerge=false;
				isMerge=true;
			}
			
			if(isMerge)
			{
				cells.merge(rowmergeEndStart, 0 , rowmergeEnd, 0);
				cells.merge(rowmergeEndStart, 1,  rowmergeEnd, 1);
				cells.merge(rowmergeEndStart, 2 , rowmergeEnd, 2);
				cells.merge(rowmergeEndStart, 3 , rowmergeEnd, 3);
				cells.merge(rowmergeEndStart, 4, rowmergeEnd, 4);
				cells.merge(rowmergeEndStart, 5, rowmergeEnd, 5);
				cells.merge(rowmergeEndStart, 6, rowmergeEnd, 6);
				cells.merge(rowmergeEndStart, 7, rowmergeEnd, 7);
				cells.merge(rowmergeEndStart, 10, rowmergeEnd, 10);
				cells.merge(rowmergeEndStart, 12, rowmergeEnd, 12);
				cells.merge(rowmergeEndStart, 14, rowmergeEnd, 14);
				cells.merge(rowmergeEndStart, 16, rowmergeEnd, 16);
				isMerge=false;
			}
			
			
			
			
			
			
			//// GHI TỔNG CỘNG CỦA LOẠI CUỐI CÙNG
			
			
			
			
			// Loại TS
			cell = cells.getCell("A" + row);
			cell.setValue("");
			cell.setStyle(style);
			cell = CreateBorderSetting2(cell, 0);
			// Mã TS
			cell = cells.getCell("B" + row);
			cell.setValue("TỔNG CỘNG");
			cell.setStyle(style);
			cell = CreateBorderSetting2(cell, 0);
			// Tên TS
			cell = cells.getCell("C" + row);
			cell.setValue("");
			cell.setStyle(style);
			cell = CreateBorderSetting2(cell, 0);
			// Đơn vị sử dụng
			cell = cells.getCell("D" + row);
			cell.setValue("");
			cell.setStyle(style);
			cell = CreateBorderSetting2(cell, 0);
			// Ngày ghi tăng
			cell = cells.getCell("E" + row);
			cell.setValue("");
			cell.setStyle(style);
			cell = CreateBorderSetting2(cell, 0);
			// Ngày bắt đầu khấu hao
			cell = cells.getCell("F" + row);
			cell.setValue("");
			cell.setStyle(style);
			cell = CreateBorderSetting2(cell, 0);
			// Thời gian khấu hao (tháng)
			cell = cells.getCell("G" + row);
			cell.setValue(tongSoThangKHTheoLoaiTS);
			cell.setStyle(style);
			cell = CreateBorderSetting2(cell, 41);
			// Số tháng đã khấu hao
			cell = cells.getCell("H" + row);
			cell.setValue(tongSoThangDaKHTheoLoaiTS);
			cell.setStyle(style);
			cell = CreateBorderSetting2(cell, 41);
			// Tài khoản chi phí
			cell = cells.getCell("I" + row);
			cell.setValue("");
			cell.setStyle(style);
			cell = CreateBorderSetting2(cell, 41);
			// Giá trị TS đầu kỳ - Nguyên giá
			cell = cells.getCell("J" + row);
			cell.setValue(tongNguyenGiaDauKyTheoLoaiTS);
			cell.setStyle(style);
			cell = CreateBorderSetting2(cell, 41);
			// Giá trị TS đầu kỳ - Khấu hao
			cell = cells.getCell("K" + row);
			cell.setValue(tongKhauHaoDauKyTheoLoaiTS);
			cell.setStyle(style);
			cell = CreateBorderSetting2(cell, 41);
			// Giá trị TS đầu kỳ - Còn lại
			cell = cells.getCell("L" + row);
			cell.setValue(tongConLaiDauKyTheoLoaiTS);
			cell.setStyle(style);
			cell = CreateBorderSetting2(cell, 41);
			// Khấu hao trong kỳ
			cell = cells.getCell("M" + row);
			cell.setValue(tongKhauHaoTrongKyTheoLoaiTS);
			cell.setStyle(style);
			cell = CreateBorderSetting2(cell, 41);
			// Giá trị TS cuối kỳ - Nguyên giá
			cell = cells.getCell("N" + row);
			cell.setValue(tongNguyenGiaCuoiKyTheoLoaiTS);
			cell.setStyle(style);
			cell = CreateBorderSetting2(cell, 41);
			// Giá trị TS cuối kỳ - Khấu hao
			cell = cells.getCell("O" + row);
			cell.setValue(tongKhauHaoCuoiKyTheoLoaiTS);
			cell.setStyle(style);
			cell = CreateBorderSetting2(cell, 41);
			// Giá trị TS cuối kỳ - Còn lại
			cell = cells.getCell("P" + row);
			cell.setValue(tongConLaiCuoiKyTheoLoaiTS);
			cell.setStyle(style);
			cell = CreateBorderSetting2(cell, 41);	
			row++;
			
			// Loại TS
			cell = cells.getCell("A" + row);
			cell.setValue("TỔNG CỘNG");
			cell.setStyle(style);
			cell = CreateBorderSetting2(cell, 0);
			// Mã TS
			cell = cells.getCell("B" + row);
			cell.setValue("");
			cell.setStyle(style);
			cell = CreateBorderSetting2(cell, 0);
			// Tên TS
			cell = cells.getCell("C" + row);
			cell.setValue("");
			cell.setStyle(style);
			cell = CreateBorderSetting2(cell, 0);
			// Đơn vị sử dụng
			cell = cells.getCell("D" + row);
			cell.setValue("");
			cell.setStyle(style);
			cell = CreateBorderSetting2(cell, 0);
			// Ngày ghi tăng
			cell = cells.getCell("E" + row);
			cell.setValue("");
			cell.setStyle(style);
			cell = CreateBorderSetting2(cell, 0);
			// Ngày bắt đầu khấu hao
			cell = cells.getCell("F" + row);
			cell.setValue("");
			cell.setStyle(style);
			cell = CreateBorderSetting2(cell, 0);
			// Thời gian khấu hao (tháng)
			cell = cells.getCell("G" + row);
			cell.setValue(tongSoThangKH);
			cell.setStyle(style);
			cell = CreateBorderSetting2(cell, 41);
			// Số tháng đã khấu hao
			cell = cells.getCell("H" + row);
			cell.setValue(tongSoThangDaKH);
			cell.setStyle(style);
			cell = CreateBorderSetting2(cell, 41);
			// Tài khoản chi phí
			cell = cells.getCell("I" + row);
			cell.setValue("");
			cell.setStyle(style);
			cell = CreateBorderSetting2(cell, 41);
			// Giá trị TS đầu kỳ - Nguyên giá
			cell = cells.getCell("J" + row);
			cell.setValue(tongNguyenGiaDauKy);
			cell.setStyle(style);
			cell = CreateBorderSetting2(cell, 41);
			// Giá trị TS đầu kỳ - Khấu hao
			cell = cells.getCell("K" + row);
			cell.setValue(tongKhauHaoDauKy);
			cell.setStyle(style);
			cell = CreateBorderSetting2(cell, 41);
			// Giá trị TS đầu kỳ - Còn lại
			cell = cells.getCell("L" + row);
			cell.setValue(tongConLaiDauKy);
			cell.setStyle(style);
			cell = CreateBorderSetting2(cell, 41);
			// Khấu hao trong kỳ
			cell = cells.getCell("M" + row);
			cell.setValue(tongKhauHaoTrongKy);
			cell.setStyle(style);
			cell = CreateBorderSetting2(cell, 41);
			// Giá trị TS cuối kỳ - Nguyên giá
			cell = cells.getCell("N" + row);
			cell.setValue(tongNguyenGiaCuoiKy);
			cell.setStyle(style);
			cell = CreateBorderSetting2(cell, 41);
			// Giá trị TS cuối kỳ - Khấu hao
			cell = cells.getCell("O" + row);
			cell.setValue(tongKhauHaoCuoiKy);
			cell.setStyle(style);
			cell = CreateBorderSetting2(cell, 41);
			// Giá trị TS cuối kỳ - Còn lại
			cell = cells.getCell("P" + row);
			cell.setValue(tongConLaiCuoiKy);
			cell.setStyle(style);
			cell = CreateBorderSetting2(cell, 41);	

			
			row ++;
			cell = cells.getCell("B"+(row+1));
			cell.setValue("Người lập biểu");
			cell = cells.getCell("D"+(row+1));
			cell.setValue("Kế toán trưởng");
			cell = cells.getCell("H"+row);
			cell.setValue("Ngày   "+ "Tháng    "+"Năm    ");
			cell= cells.getCell("H"+(row+1));
			cell.setValue("Tổng giám đốc");
			workbook.save(out);
			fstream.close();

		} catch (Exception ex) {
			System.out.println(ex.toString());
			ex.printStackTrace();
			throw new Exception("Không tạo được báo cáo");
		}

	}

	private void CreateReportKhauHaoPhanBo(OutputStream out, IErp_BaoCaoSuDungTSCD obj) throws Exception {

		try {
			String file = getServletContext().getInitParameter("path") + "\\BaoCaoPhanBoKHTSCD.xlsm";

			System.out.println(file);

			FileInputStream fstream = new FileInputStream(file);

			Workbook workbook = new Workbook();
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);

			Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet("Sheet1");

			Cells cells = worksheet.getCells();


			Cell cell;
			Style style;

			Font font = new Font();
			font.setColor(Color.NAVY);
			font.setSize(13);
			font.setBold(true);

			cell = cells.getCell("A1");
			cell.setValue("" + obj.getCtyTen());
			style = cell.getStyle();
			style.setFont(font);
			style.setHAlignment(TextAlignmentType.LEFT);
			cell.setStyle(style);

			cells.setRowHeight(1, 20.0f);
			cell = cells.getCell("A2");
			cell.setValue("Địa chỉ: " + obj.getDiachi());
			style = cell.getStyle();
			style.setFont(font);
			style.setHAlignment(TextAlignmentType.LEFT);
			cell.setStyle(style);

			cells.setRowHeight(2, 20.0f);
			cell = cells.getCell("A3");
			cell.setValue("Mã số thuế: " + obj.getMasothue());
			style = cell.getStyle();
			style.setFont(font);
			style.setHAlignment(TextAlignmentType.LEFT);
			cell.setStyle(style);
			
			
			
			cell = cells.getCell("B6");
			String ngaythang="";
			if(obj.getMonth().length()>0)
			{
				ngaythang="Tháng "+obj.getMonth() + " năm "+obj.getYear();
			}else
				ngaythang="Năm "+obj.getYear();
			cell.setValue("" + ngaythang); 
			style = cell.getStyle();
			style.setFont(font);
			style.setHAlignment(TextAlignmentType.CENTER);
			cell.setStyle(style);
			

			double tongConLaiDauKy=0;
			double tongConLaiCuoiKy=0;
			double tongKhauHaoTrongKy=0;
			double tongNguyenGiaDauKy=0;
			double tongNguyenGiaCuoiKy=0;
			double tongKhauHaoDauKy=0;
			double tongKhauHaoCuoiKy=0;
			///////// theo loại

			double tongConLaiDauKyTheoLoaiTS=0;
			double tongConLaiCuoiKyTheoLoaiTS=0;
			double tongKhauHaoTrongKyTheoLoaiTS=0;
			double tongNguyenGiaDauKyTheoLoaiTS=0;
			double tongNguyenGiaCuoiKyTheoLoaiTS=0;
			double tongKhauHaoDauKyTheoLoaiTS=0;
			double tongKhauHaoCuoiKyTheoLoaiTS=0;
			
			
			
			
			String loaitaisanOld="";
			
			int row = 10;
			String query = queryKhauHaoPhanBo(obj);
			System.out.println("[CreateReportKhauHaoPhanBo]:"+query);
			obj.createRsBaoCao(query);
			ResultSet rs = obj.getRsBaoCao();
			if (rs != null) {
				while (rs.next()) {
					
					String loaitaisanNew=rs.getString("LOAITAISAN");
					

					if((loaitaisanOld!="") && (!loaitaisanOld.equals(loaitaisanNew)))
					{
						cell = cells.getCell("A" + row);
						cell.setValue("");
						font.setColor(Color.BLACK);	
						font.setSize(10);
						font.setBold(false);
						style.setFont(font);
						style.setNumber(0);
						style.setHAlignment(TextAlignmentType.LEFT);
						cell.setStyle(style);
						cell = CreateBorderSetting(cell,0);
						
						
						cell = cells.getCell("B"+row);			
						cell.setValue("TỔNG CỘNG");
						font.setColor(Color.BLACK);	
						font.setSize(10);
						font.setBold(false);
						style.setFont(font);
						style.setNumber(0);
						style.setHAlignment(TextAlignmentType.LEFT);
						cell.setStyle(style);
						cell = CreateBorderSetting(cell,0);
						

						
						cell = cells.getCell("C"+row);			
						cell.setValue(tongNguyenGiaDauKyTheoLoaiTS);
						font.setColor(Color.BLACK);	
						font.setSize(10);
						font.setBold(false);
						style.setFont(font);
						style.setNumber(0);
						style.setHAlignment(TextAlignmentType.LEFT);
						cell.setStyle(style);
						cell = CreateBorderSetting(cell,41);
						
						
						cell = cells.getCell("D"+row);			
						cell.setValue(tongKhauHaoDauKyTheoLoaiTS);
						font.setColor(Color.BLACK);	
						font.setSize(10);
						font.setBold(false);
						style.setFont(font);
						style.setNumber(0);
						style.setHAlignment(TextAlignmentType.LEFT);
						cell.setStyle(style);
						cell = CreateBorderSetting(cell,41);
						
						
						cell = cells.getCell("E"+row);			
						cell.setValue(tongConLaiDauKyTheoLoaiTS);
						font.setColor(Color.BLACK);	
						font.setSize(10);
						font.setBold(false);
						style.setFont(font);
						style.setNumber(0);
						style.setHAlignment(TextAlignmentType.LEFT);
						cell.setStyle(style);
						cell = CreateBorderSetting(cell,41);
						
						
						cell = cells.getCell("F"+row);			
						cell.setValue(tongKhauHaoTrongKyTheoLoaiTS);
						font.setColor(Color.BLACK);	
						font.setSize(10);
						font.setBold(false);
						style.setFont(font);
						style.setNumber(0);
						style.setHAlignment(TextAlignmentType.LEFT);
						cell.setStyle(style);
						cell = CreateBorderSetting(cell,41);
						
						
						cell = cells.getCell("G"+row);			
						cell.setValue(tongNguyenGiaCuoiKyTheoLoaiTS);
						font.setColor(Color.BLACK);	
						font.setSize(10);
						font.setBold(false);
						style.setFont(font);
						style.setNumber(0);
						style.setHAlignment(TextAlignmentType.LEFT);
						cell.setStyle(style);
						cell = CreateBorderSetting(cell,41);
						
						
						cell = cells.getCell("H"+row);			
						cell.setValue(tongKhauHaoCuoiKyTheoLoaiTS);
						font.setColor(Color.BLACK);	
						font.setSize(10);
						font.setBold(false);
						style.setFont(font);
						style.setNumber(0);
						style.setHAlignment(TextAlignmentType.LEFT);
						cell.setStyle(style);
						cell = CreateBorderSetting(cell,41);
						
						
						cell = cells.getCell("I"+row);			
						cell.setValue(tongConLaiCuoiKyTheoLoaiTS);
						font.setColor(Color.BLACK);	
						font.setSize(10);
						font.setBold(false);
						style.setFont(font);
						style.setNumber(0);
						style.setHAlignment(TextAlignmentType.LEFT);
						cell.setStyle(style);
						cell = CreateBorderSetting(cell,41);
						
						row++;
						
						
						tongConLaiDauKyTheoLoaiTS=0;
						tongConLaiCuoiKyTheoLoaiTS=0;
						tongKhauHaoTrongKyTheoLoaiTS=0;
						tongNguyenGiaDauKyTheoLoaiTS=0;
						tongNguyenGiaCuoiKyTheoLoaiTS=0;
						tongKhauHaoDauKyTheoLoaiTS=0;
						tongKhauHaoCuoiKyTheoLoaiTS=0;
					}
					cell = cells.getCell("A" + row);
					cell.setValue("" + rs.getString("LOAITAISAN"));
					font.setColor(Color.BLACK);	
					font.setSize(10);
					font.setBold(false);
					style.setFont(font);
					style.setNumber(0);
					style.setHAlignment(TextAlignmentType.LEFT);
					cell.setStyle(style);
					cell = CreateBorderSetting(cell,0);
					
					
					cell = cells.getCell("B"+row);			
					cell.setValue("" +rs.getString("DONVI"));
					font.setColor(Color.BLACK);	
					font.setSize(10);
					font.setBold(false);
					style.setFont(font);
					style.setNumber(0);
					style.setHAlignment(TextAlignmentType.LEFT);
					cell.setStyle(style);
					cell = CreateBorderSetting(cell,0);
					

					
					cell = cells.getCell("C"+row);			
					cell.setValue(rs.getDouble("NGUYENGIADAUKY"));
					font.setColor(Color.BLACK);	
					font.setSize(10);
					font.setBold(false);
					style.setFont(font);
					style.setNumber(0);
					style.setHAlignment(TextAlignmentType.LEFT);
					cell.setStyle(style);
					cell = CreateBorderSetting(cell,41);
					
					
					cell = cells.getCell("D"+row);			
					cell.setValue(rs.getDouble("KHAUHAODAUKY"));
					font.setColor(Color.BLACK);	
					font.setSize(10);
					font.setBold(false);
					style.setFont(font);
					style.setNumber(0);
					style.setHAlignment(TextAlignmentType.LEFT);
					cell.setStyle(style);
					cell = CreateBorderSetting(cell,41);
					
					
					cell = cells.getCell("E"+row);			
					cell.setValue(rs.getDouble("CONLAIDAUKY"));
					font.setColor(Color.BLACK);	
					font.setSize(10);
					font.setBold(false);
					style.setFont(font);
					style.setNumber(0);
					style.setHAlignment(TextAlignmentType.LEFT);
					cell.setStyle(style);
					cell = CreateBorderSetting(cell,41);
					
					
					cell = cells.getCell("F"+row);			
					cell.setValue(rs.getDouble("KHAUHAOTRONGKY"));
					font.setColor(Color.BLACK);	
					font.setSize(10);
					font.setBold(false);
					style.setFont(font);
					style.setNumber(0);
					style.setHAlignment(TextAlignmentType.LEFT);
					cell.setStyle(style);
					cell = CreateBorderSetting(cell,41);
					
					
					cell = cells.getCell("G"+row);			
					cell.setValue(rs.getDouble("NGUYENGIACUOIKY"));
					font.setColor(Color.BLACK);	
					font.setSize(10);
					font.setBold(false);
					style.setFont(font);
					style.setNumber(0);
					style.setHAlignment(TextAlignmentType.LEFT);
					cell.setStyle(style);
					cell = CreateBorderSetting(cell,41);
					
					
					cell = cells.getCell("H"+row);			
					cell.setValue(rs.getDouble("KHAUHAOCUOIKY"));
					font.setColor(Color.BLACK);	
					font.setSize(10);
					font.setBold(false);
					style.setFont(font);
					style.setNumber(0);
					style.setHAlignment(TextAlignmentType.LEFT);
					cell.setStyle(style);
					cell = CreateBorderSetting(cell,41);
					
					
					cell = cells.getCell("I"+row);			
					cell.setValue(rs.getDouble("CONLAICUOIKY"));
					font.setColor(Color.BLACK);	
					font.setSize(10);
					font.setBold(false);
					style.setFont(font);
					style.setNumber(0);
					style.setHAlignment(TextAlignmentType.LEFT);
					cell.setStyle(style);
					cell = CreateBorderSetting(cell,41);
					
					tongNguyenGiaDauKy += rs.getDouble("NGUYENGIADAUKY");		
					tongKhauHaoTrongKy+=rs.getDouble("KHAUHAOTRONGKY");
					tongNguyenGiaCuoiKy+=rs.getDouble("NGUYENGIACUOIKY");
					tongConLaiDauKy+=rs.getDouble("CONLAIDAUKY");
					tongConLaiCuoiKy+=rs.getDouble("CONLAICUOIKY");
					tongKhauHaoDauKy+=rs.getDouble("KHAUHAODAUKY");
					tongKhauHaoCuoiKy+=rs.getDouble("KHAUHAOCUOIKY");
					
					
					/// CỘNG CHO TỪNG LOẠI
					
					tongNguyenGiaDauKyTheoLoaiTS += rs.getDouble("NGUYENGIADAUKY");		
					tongKhauHaoTrongKyTheoLoaiTS+=rs.getDouble("KHAUHAOTRONGKY");
					tongNguyenGiaCuoiKyTheoLoaiTS+=rs.getDouble("NGUYENGIACUOIKY");
					tongConLaiDauKyTheoLoaiTS+=rs.getDouble("CONLAIDAUKY");
					tongConLaiCuoiKyTheoLoaiTS+=rs.getDouble("CONLAICUOIKY");
					tongKhauHaoDauKyTheoLoaiTS+=rs.getDouble("KHAUHAODAUKY");
					tongKhauHaoCuoiKyTheoLoaiTS+=rs.getDouble("KHAUHAOCUOIKY");
					row++;
					
					/// set lại loại mới
					
					loaitaisanOld=loaitaisanNew;

				}
			}
			
			/// GHI TỔNG LOẠI CUỐI CÙNG
			
			
			cell = cells.getCell("A" + row);
			cell.setValue("");
			font.setColor(Color.BLACK);	
			font.setSize(10);
			font.setBold(false);
			style.setFont(font);
			style.setNumber(0);
			style.setHAlignment(TextAlignmentType.LEFT);
			cell.setStyle(style);
			cell = CreateBorderSetting(cell,0);
			
			
			cell = cells.getCell("B"+row);			
			cell.setValue("TỔNG CỘNG");
			font.setColor(Color.BLACK);	
			font.setSize(10);
			font.setBold(false);
			style.setFont(font);
			style.setNumber(0);
			style.setHAlignment(TextAlignmentType.LEFT);
			cell.setStyle(style);
			cell = CreateBorderSetting(cell,0);
			

			
			cell = cells.getCell("C"+row);			
			cell.setValue(tongNguyenGiaDauKyTheoLoaiTS);
			font.setColor(Color.BLACK);	
			font.setSize(10);
			font.setBold(false);
			style.setFont(font);
			style.setNumber(0);
			style.setHAlignment(TextAlignmentType.LEFT);
			cell.setStyle(style);
			cell = CreateBorderSetting(cell,41);
			
			
			cell = cells.getCell("D"+row);			
			cell.setValue(tongKhauHaoDauKyTheoLoaiTS);
			font.setColor(Color.BLACK);	
			font.setSize(10);
			font.setBold(false);
			style.setFont(font);
			style.setNumber(0);
			style.setHAlignment(TextAlignmentType.LEFT);
			cell.setStyle(style);
			cell = CreateBorderSetting(cell,41);
			
			
			cell = cells.getCell("E"+row);			
			cell.setValue(tongConLaiDauKyTheoLoaiTS);
			font.setColor(Color.BLACK);	
			font.setSize(10);
			font.setBold(false);
			style.setFont(font);
			style.setNumber(0);
			style.setHAlignment(TextAlignmentType.LEFT);
			cell.setStyle(style);
			cell = CreateBorderSetting(cell,41);
			
			
			cell = cells.getCell("F"+row);			
			cell.setValue(tongKhauHaoTrongKyTheoLoaiTS);
			font.setColor(Color.BLACK);	
			font.setSize(10);
			font.setBold(false);
			style.setFont(font);
			style.setNumber(0);
			style.setHAlignment(TextAlignmentType.LEFT);
			cell.setStyle(style);
			cell = CreateBorderSetting(cell,41);
			
			
			cell = cells.getCell("G"+row);			
			cell.setValue(tongNguyenGiaCuoiKyTheoLoaiTS);
			font.setColor(Color.BLACK);	
			font.setSize(10);
			font.setBold(false);
			style.setFont(font);
			style.setNumber(0);
			style.setHAlignment(TextAlignmentType.LEFT);
			cell.setStyle(style);
			cell = CreateBorderSetting(cell,41);
			
			
			cell = cells.getCell("H"+row);			
			cell.setValue(tongKhauHaoCuoiKyTheoLoaiTS);
			font.setColor(Color.BLACK);	
			font.setSize(10);
			font.setBold(false);
			style.setFont(font);
			style.setNumber(0);
			style.setHAlignment(TextAlignmentType.LEFT);
			cell.setStyle(style);
			cell = CreateBorderSetting(cell,41);
			
			
			cell = cells.getCell("I"+row);			
			cell.setValue(tongConLaiCuoiKyTheoLoaiTS);
			font.setColor(Color.BLACK);	
			font.setSize(10);
			font.setBold(false);
			style.setFont(font);
			style.setNumber(0);
			style.setHAlignment(TextAlignmentType.LEFT);
			cell.setStyle(style);
			cell = CreateBorderSetting(cell,41);
			row++;
			
			// GHI TỔNG CỘNG 
			
			
			cell = cells.getCell("A" + row);
			cell.setValue("TỔNG CỘNG");
			font.setColor(Color.BLACK);	
			font.setSize(10);
			font.setBold(false);
			style.setFont(font);
			style.setNumber(0);
			style.setHAlignment(TextAlignmentType.LEFT);
			cell.setStyle(style);
			cell = CreateBorderSetting(cell,0);
			
			
			cell = cells.getCell("B"+row);			
			cell.setValue("");
			font.setColor(Color.BLACK);	
			font.setSize(10);
			font.setBold(false);
			style.setFont(font);
			style.setNumber(0);
			style.setHAlignment(TextAlignmentType.LEFT);
			cell.setStyle(style);
			cell = CreateBorderSetting(cell,0);
			

			
			cell = cells.getCell("C"+row);			
			cell.setValue(tongNguyenGiaDauKy);
			font.setColor(Color.BLACK);	
			font.setSize(10);
			font.setBold(false);
			style.setFont(font);
			style.setNumber(0);
			style.setHAlignment(TextAlignmentType.LEFT);
			cell.setStyle(style);
			cell = CreateBorderSetting(cell,41);
			
			
			cell = cells.getCell("D"+row);			
			cell.setValue(tongKhauHaoDauKy);
			font.setColor(Color.BLACK);	
			font.setSize(10);
			font.setBold(false);
			style.setFont(font);
			style.setNumber(0);
			style.setHAlignment(TextAlignmentType.LEFT);
			cell.setStyle(style);
			cell = CreateBorderSetting(cell,41);
			
			
			cell = cells.getCell("E"+row);			
			cell.setValue(tongConLaiDauKy);
			font.setColor(Color.BLACK);	
			font.setSize(10);
			font.setBold(false);
			style.setFont(font);
			style.setNumber(0);
			style.setHAlignment(TextAlignmentType.LEFT);
			cell.setStyle(style);
			cell = CreateBorderSetting(cell,41);
			
			
			cell = cells.getCell("F"+row);			
			cell.setValue(tongKhauHaoTrongKy);
			font.setColor(Color.BLACK);	
			font.setSize(10);
			font.setBold(false);
			style.setFont(font);
			style.setNumber(0);
			style.setHAlignment(TextAlignmentType.LEFT);
			cell.setStyle(style);
			cell = CreateBorderSetting(cell,41);
			
			
			cell = cells.getCell("G"+row);			
			cell.setValue(tongNguyenGiaCuoiKy);
			font.setColor(Color.BLACK);	
			font.setSize(10);
			font.setBold(false);
			style.setFont(font);
			style.setNumber(0);
			style.setHAlignment(TextAlignmentType.LEFT);
			cell.setStyle(style);
			cell = CreateBorderSetting(cell,41);
			
			
			cell = cells.getCell("H"+row);			
			cell.setValue(tongKhauHaoCuoiKy);
			font.setColor(Color.BLACK);	
			font.setSize(10);
			font.setBold(false);
			style.setFont(font);
			style.setNumber(0);
			style.setHAlignment(TextAlignmentType.LEFT);
			cell.setStyle(style);
			cell = CreateBorderSetting(cell,41);
			
			
			cell = cells.getCell("I"+row);			
			cell.setValue(tongConLaiCuoiKy);
			font.setColor(Color.BLACK);	
			font.setSize(10);
			font.setBold(false);
			style.setFont(font);
			style.setNumber(0);
			style.setHAlignment(TextAlignmentType.LEFT);
			cell.setStyle(style);
			cell = CreateBorderSetting(cell,41);
			
			row ++;
			cell = cells.getCell("B"+(row+1));
			cell.setValue("Người lập biểu");
			cell = cells.getCell("D"+(row+1));
			cell.setValue("Kế toán trưởng");
			cell = cells.getCell("H"+row);
			cell.setValue("Ngày   "+ "Tháng    "+"Năm    ");
			cell= cells.getCell("H"+(row+1));
			cell.setValue("Tổng giám đốc");
			workbook.save(out);
			fstream.close();

		} catch (Exception ex) {
			System.out.println(ex.toString());
			ex.printStackTrace();
			throw new Exception("Không tạo được báo cáo");
		}

	}

	public Cell CreateBorderSetting2(Cell cell, int number) throws IOException {

		Style style;
		style = cell.getStyle();

		// Set border color
		style.setBorderColor(BorderType.TOP, Color.BLACK);
		style.setBorderColor(BorderType.BOTTOM, Color.BLACK);
		style.setBorderColor(BorderType.LEFT, Color.BLACK);
		style.setBorderColor(BorderType.RIGHT, Color.BLACK);

		// Set the cell border type
		style.setBorderLine(BorderType.TOP, BorderLineType.DOTTED);
		style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);
		style.setBorderLine(BorderType.LEFT, BorderLineType.THIN);
		style.setBorderLine(BorderType.RIGHT, BorderLineType.THIN);
		// set number = 41 format currency
		style.setNumber(number);
		cell.setStyle(style);
		
		return cell;
	}

	public Cell CreateBorderSetting(Cell cell, int number) throws IOException {

		Style style;
		style = cell.getStyle();
		
		// Set border color
		style.setBorderColor(BorderType.TOP, Color.BLACK);
		style.setBorderColor(BorderType.BOTTOM, Color.BLACK);
		style.setBorderColor(BorderType.LEFT, Color.BLACK);
		style.setBorderColor(BorderType.RIGHT, Color.BLACK);

		// Set the cell border type
		style.setBorderLine(BorderType.TOP, BorderLineType.THIN);
		style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
		style.setBorderLine(BorderType.LEFT, BorderLineType.THIN);
		style.setBorderLine(BorderType.RIGHT, BorderLineType.THIN);
		// set number =41 format currency
		style.setNumber(number);
		cell.setStyle(style);
		return cell;
	}

	private String getDate() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}

	private String queryBaoCaoTangTSCD(IErp_BaoCaoSuDungTSCD obj) {
		String queryBaoCaoTangTSCD = "";
		queryBaoCaoTangTSCD = " SELECT LTS.diengiai AS LOAITAISAN,TSCD.ma AS MATSCD,TSCD.DIENGIAI AS TENTSCD,ISNULL(TTCP.TEN,'') AS TENDVTH, \n "
				+ " TSCD.thangbatdauKH AS NGAYBATDAUKH,ISNULL(TSCD.NGAYGHITANG,'') AS NGAYBATDAUSD,TSCD.sothangKH,TSCD.THANHTIEN AS NGUYENGIA, \n "
				+ " ISNULL(TSCD.GIATRIDAKHAUHAO,0) + ISNULL(KHAUHAO.KHAUHAOLUYKE,0) AS GIATRIDAKHAUHAO, \n "
				+ " ISNULL(TSCD.THANHTIEN,0) -ISNULL(TSCD.GIATRIDAKHAUHAO,0) - ISNULL(KHAUHAO.KHAUHAOLUYKE,0) AS GIATRICONLAI, \n "
				+ " CASE WHEN MSC.LOAI='0' THEN N'Sửa chữa lớn TSCD'  \n "
				+ " 	 WHEN MSC.LOAI='1' THEN N'Mua mới TSCD'  \n "
				+ " 	 WHEN MSC.LOAI='2' THEN N'Xây dựng cơ bản TSCD' \n " + " ELSE '' END AS LYDOTANG \n "
				+ " FROM ERP_TAISANCODINH TSCD \n " + " INNER JOIN ERP_MASCLON MSC ON MSC.TAISAN_FK = TSCD.PK_SEQ \n "
				+ " INNER JOIN Erp_LOAITAISAN LTS ON LTS.PK_SEQ = TSCD.LOAITAISAN_FK \n "
				+ " LEFT JOIN ERP_DONVITHUCHIEN TTCP ON TTCP.PK_SEQ=TSCD.DVTH_FK \n " + " LEFT JOIN  \n " + " ( \n "
				+ " 	SELECT TAISAN_FK, SUM(KHAUHAO)  AS KHAUHAOLUYKE \n " + " 	FROM ERP_KHAUHAOTAISAN \n "
				+ " 	WHERE KHAUHAO>0 \n " + " 	GROUP BY TAISAN_FK \n "
				+ " )KHAUHAO ON KHAUHAO.TAISAN_FK=TSCD.pk_seq \n " + " LEFT JOIN  \n " + " ( \n "
				+ " 	SELECT TSCD_FK AS TAISAN_FK, SUM(SOTHANG) AS SOTHANG, SUM(GIATRI) AS GIATRI \n "
				+ " 	FROM ERP_TAISANCODINH_DIEUCHINH  \n " + " 	GROUP BY TSCD_FK \n "
				+ " )THAYDOI ON THAYDOI.TAISAN_FK= TSCD.PK_SEQ \n " + " WHERE MSC.TRANGTHAI=3  \n ";
		if (obj.getYear().length() > 0)
			queryBaoCaoTangTSCD += " AND YEAR(MSC.NGAYCHUYEN) = " + obj.getYear() + " \n ";
		if (obj.getMonth().length() > 0)
			queryBaoCaoTangTSCD += " AND MONTH(MSC.NGAYCHUYEN) = " + obj.getMonth() + " \n ";
		if(obj.getLoaiTaiSanId().length() >0)
			queryBaoCaoTangTSCD += " AND LTS.PK_SEQ='"+obj.getLoaiTaiSanId()+"' \n";
		queryBaoCaoTangTSCD +=" order by TSCD.ma";
		return queryBaoCaoTangTSCD;
	}

	private String queryBaoCaoGiamTSCD(IErp_BaoCaoSuDungTSCD obj) {
		String queryBaoCaoGiamTSCD = "";
		queryBaoCaoGiamTSCD = " SELECT LTS.diengiai AS LOAITAISAN,TSCD.ma AS MATSCD,TSCD.DIENGIAI AS TENTSCD,ISNULL(TTCP.TEN,'') AS TENDVTH, \n "
				+ " TSCD.THANGBATDAUKH AS NGAYBATDAUKH,ISNULL(TSCD.NGAYGHITANG,'') AS NGAYBATDAUSD,TSCD.sothangKH, \n "
				+ " TL.NGAYHOADON AS NGAYTHANHLY,TSCD.THANHTIEN AS NGUYENGIA , \n "
				+ " ISNULL(TSCD.GIATRIDAKHAUHAO,0) + ISNULL(KHAUHAO.KHAUHAOLUYKE,0) AS GIATRIDAKHAUHAO, \n "
				+ " ISNULL(TSCD.THANHTIEN,0) -ISNULL(TSCD.GIATRIDAKHAUHAO,0) - ISNULL(KHAUHAO.KHAUHAOLUYKE,0) AS GIATRICONLAI, \n "
				+ " TL.GHICHU AS LYDOGIAM \n " + " FROM ERP_TAISANCODINH TSCD \n "
				+ " INNER JOIN ERP_THANHLYTAISAN_TAISAN TL_TS ON TL_TS.TAISAN_FK = TSCD.PK_SEQ \n "
				+ " INNER JOIN ERP_THANHLYTAISAN TL ON TL.PK_SEQ= TL_TS.THANHLYTAISAN_FK \n "
				+ " INNER JOIN Erp_LOAITAISAN LTS ON LTS.PK_SEQ = TSCD.LOAITAISAN_FK \n "
				+ " LEFT JOIN ERP_DONVITHUCHIEN TTCP ON TTCP.PK_SEQ=TSCD.DVTH_FK \n " + " LEFT JOIN  \n " + " ( \n "
				+ " 	SELECT TAISAN_FK, SUM(KHAUHAO)  AS KHAUHAOLUYKE \n " + " 	FROM ERP_KHAUHAOTAISAN \n "
				+ " 	WHERE KHAUHAO>0 \n " + " 	GROUP BY TAISAN_FK \n "
				+ " )KHAUHAO ON KHAUHAO.TAISAN_FK=TSCD.pk_seq \n " + " LEFT JOIN  \n " + " ( \n "
				+ " 	SELECT TSCD_FK AS TAISAN_FK, SUM(SOTHANG) AS SOTHANG, SUM(GIATRI) AS GIATRI \n "
				+ " 	FROM ERP_TAISANCODINH_DIEUCHINH  \n " + " 	GROUP BY TSCD_FK \n "
				+ " )THAYDOI ON THAYDOI.TAISAN_FK= TSCD.PK_SEQ \n " + " WHERE TL.TRANGTHAI in (1,2,3)  \n ";
		if (obj.getYear().length() > 0)
			queryBaoCaoGiamTSCD += " AND YEAR(TL.NGAYHOADON) = " + obj.getYear() + " \n ";
		if (obj.getMonth().length() > 0)
			queryBaoCaoGiamTSCD += " AND MONTH(TL.NGAYHOADON) = " + obj.getMonth() + " \n ";
		if(obj.getLoaiTaiSanId().length() >0)
			queryBaoCaoGiamTSCD += " AND LTS.PK_SEQ='"+obj.getLoaiTaiSanId()+"' \n";
		queryBaoCaoGiamTSCD +=" order by TSCD.ma";
		return queryBaoCaoGiamTSCD;
	}

	private String queryKhauHaoDonVi(IErp_BaoCaoSuDungTSCD obj) {
		String queryKhauHaoDonVi = "";
		queryKhauHaoDonVi = "SELECT LTS.diengiai AS LOAITAISAN,TSCD.ma AS MATSCD,TSCD.DIENGIAI AS TENTSCD,ISNULL(TSCD.NGAYGHITANG,'') AS NGAYGHITANG,ISNULL(TTCP.TEN,'') AS DONVI, \n " + 
				" TSCD.thangbatdauKH AS NGAYBATDAUKH,ISNULL(TSCD.NGAYGHITANG,'') AS NGAYBATDAUSD,TSCD.sothangKH, \n " + 
				" ISNULL(TSCD.SOTHANGDAKHAUHAO,0) + ISNULL(THAYDOICUOIKY.SOTHANG,0)  + ISNULL(KHAUHAO_SOTHANG.SOTHANGKHAUHAO,0) AS SOTHANGDAKHAUHAO, \n " + 
				" TSCD.ThanhTien +ISNULL(THAYDOIDAUKY.GIATRI,0) AS NGUYENGIADAUKY, \n " + 
				" (ISNULL(KHAUHAODAUKY.KHAUHAOLUYKE,0) + ISNULL(TSCD.GIATRIDAKHAUHAO,0))* PB.PHANTRAM/100 AS KHAUHAODAUKY, \n " + 
				" TSCD.ThanhTien + ISNULL(THAYDOIDAUKY.GIATRI,0) -ISNULL(KHAUHAODAUKY.KHAUHAOLUYKE,0) - ISNULL(TSCD.GIATRIDAKHAUHAO,0) AS CONLAIDAUKY, \n " + 
				" (ISNULL(KHAUHAOTRONGKY.KHAUHAOLUYKE,0))*PB.PHANTRAM/100 AS KHAUHAOTRONGKY, \n " + 
				" TSCD.ThanhTien +ISNULL(THAYDOICUOIKY.GIATRI,0) AS NGUYENGIACUOIKY, \n " + 
				" (ISNULL(KHAUHAOTRONGKY.KHAUHAOLUYKE,0) + ISNULL(KHAUHAODAUKY.KHAUHAOLUYKE,0) + ISNULL(TSCD.GIATRIDAKHAUHAO,0))*PB.PHANTRAM/100 AS KHAUHAOCUOIKY, \n " + 
				" TSCD.ThanhTien + ISNULL(THAYDOICUOIKY.GIATRI,0) - ISNULL(KHAUHAOTRONGKY.KHAUHAOLUYKE,0) - ISNULL(KHAUHAODAUKY.KHAUHAOLUYKE,0) - ISNULL(TSCD.GIATRIDAKHAUHAO,0) AS CONLAICUOIKY \n " + 
				" FROM ERP_TAISANCODINH_KHOANMUCCHIPHI PB  \n " +
				" LEFT JOIN ERP_NHOMCHIPHI NCP ON NCP.PK_SEQ= PB.KHOANMUCCHIPHI_FK AND PB.PHANTRAM >0" +
				" INNER JOIN ERP_DONVITHUCHIEN TTCP ON TTCP.PK_SEQ=NCP.DONVITHUCHIEN_FK \n " + 
				" INNER JOIN ERP_TAISANCODINH TSCD ON TSCD.pk_seq= PB.TAISANCODINH_FK \n " + 
				" INNER JOIN Erp_LOAITAISAN LTS ON LTS.PK_SEQ = TSCD.LOAITAISAN_FK \n " +  
				" LEFT JOIN  \n " + 
				" ( \n " + 
				" 	SELECT TAISAN_FK, SUM(KHAUHAO)  AS KHAUHAOLUYKE \n " + 
				" 	FROM ERP_KHAUHAOTAISAN \n " + 
				" 	WHERE KHAUHAO>0  \n " ;
		if(obj.getYear().length()>0)
			queryKhauHaoDonVi +=  " 	AND NAM='"+obj.getYear()+"' \n " ;
		if(obj.getMonth().length()>0)
			queryKhauHaoDonVi +=  " 	AND THANG='"+obj.getMonth()+"' \n "; 
		queryKhauHaoDonVi+=" 	GROUP BY TAISAN_FK \n " + 
				" )KHAUHAOTRONGKY ON KHAUHAOTRONGKY.TAISAN_FK=TSCD.pk_seq \n " + 
				" LEFT JOIN  \n " + 
				" ( \n " 
				+ " 	SELECT TAISAN_FK, count(KHAUHAO)  AS SOTHANGKHAUHAO \n " + " 	FROM ERP_KHAUHAOTAISAN \n "
				+ " 	WHERE KHAUHAO>0  \n ";	
		if (obj.getYear().length() > 0)
			queryKhauHaoDonVi += " AND NAM <= '" + obj.getYear() + "' \n ";
		if (obj.getMonth().length() > 0)
			queryKhauHaoDonVi += " AND  THANG<= '" + obj.getMonth() + "' \n ";
		queryKhauHaoDonVi +=	"  GROUP BY TAISAN_FK \n " +
				" )KHAUHAO_SOTHANG ON KHAUHAO_SOTHANG.TAISAN_FK=TSCD.pk_seq \n " + 
				" LEFT JOIN  \n " + 
				" ( \n " + 
				" 	SELECT TSCD_FK AS TAISAN_FK, SUM(SOTHANG) AS SOTHANG, SUM(GIATRI) AS GIATRI \n " + 
				" 	FROM ERP_TAISANCODINH_DIEUCHINH  \n " +
				" 	WHERE 1=1 \n ";
		if(obj.getYear().length() >0 && obj.getMonth().length() >0)
			queryKhauHaoDonVi+= "AND YEAR(NGAYDIEUCHINH) <='" +obj.getYear()+"' \n"+
							" AND MONTH(NGAYDIEUCHINH) <'"+ obj.getMonth()+ "' \n";
		if(obj.getYear().length()>0 && obj.getMonth().length() <= 0)
			queryKhauHaoDonVi+= "AND YEAR(NGAYDIEUCHINH) <'"+obj.getYear()+"' \n";
		queryKhauHaoDonVi+=" 	GROUP BY TSCD_FK \n " + 
				" )THAYDOIDAUKY ON THAYDOIDAUKY.TAISAN_FK= TSCD.PK_SEQ \n " + 
				" LEFT JOIN  \n " + 
				" ( \n " + 
				" 	SELECT TSCD_FK AS TAISAN_FK, SUM(SOTHANG) AS SOTHANG, SUM(GIATRI) AS GIATRI \n " + 
				" 	FROM ERP_TAISANCODINH_DIEUCHINH  \n " + 
				" 	WHERE 1=1 \n " ;
		if(obj.getYear().length() >0 && obj.getMonth().length() >0)
					queryKhauHaoDonVi+= "AND YEAR(NGAYDIEUCHINH) <='" +obj.getYear()+"' \n"+
									" AND MONTH(NGAYDIEUCHINH) <='"+ obj.getMonth()+ "' \n";
		if(obj.getYear().length()>0 && obj.getMonth().length() <= 0)
					queryKhauHaoDonVi+= "AND YEAR(NGAYDIEUCHINH) <'"+obj.getYear()+"' \n";
		queryKhauHaoDonVi+=" 	GROUP BY TSCD_FK \n " + 
				" )THAYDOICUOIKY ON THAYDOIDAUKY.TAISAN_FK= TSCD.PK_SEQ \n " + 
				" LEFT JOIN  \n " + 
				" ( \n " + 
				" 	SELECT TAISAN_FK, SUM(KHAUHAO)  AS KHAUHAOLUYKE \n " + 
				" 	FROM ERP_KHAUHAOTAISAN \n " + 
				" 	WHERE KHAUHAO>0  \n " ;
		if(obj.getYear().length() >0 && obj.getMonth().length() >0)
			queryKhauHaoDonVi+= "AND NAM <='" +obj.getYear()+"' \n"+
							" AND THANG <'"+ obj.getMonth()+ "' \n";
		if(obj.getYear().length()>0 && obj.getMonth().length() <= 0)
			queryKhauHaoDonVi+= "AND NAM <'"+obj.getYear()+"' \n";
		queryKhauHaoDonVi +=" 	GROUP BY TAISAN_FK \n " + 
				" )KHAUHAODAUKY ON KHAUHAODAUKY.TAISAN_FK=TSCD.pk_seq \n " + 
				" WHERE TSCD.TRANGTHAI =1 ";
		if(obj.getLoaiTaiSanId().length() >0)
			queryKhauHaoDonVi += " AND LTS.PK_SEQ='"+obj.getLoaiTaiSanId()+"' \n";
		if(obj.getDvthId().length() >0)
			queryKhauHaoDonVi += " AND TSCD.TTCP_FK='"+obj.getDvthId()+"' \n";
		if (obj.getYear().length() > 0&&obj.getMonth().length() > 0)
			queryKhauHaoDonVi += " AND ( (YEAR(ISNULL(NGAYGHITANG,'2017-01-01'))< '" + obj.getYear() + "' OR (YEAR(ISNULL(NGAYGHITANG,'2017-01-01'))= '" + obj.getYear() + "' AND MONTH(NGAYGHITANG)<= '" + obj.getMonth() + "' ))) \n ";
		queryKhauHaoDonVi +="ORDER BY LTS.diengiai ,tscd.ma";
		return queryKhauHaoDonVi;
	}

	private String queryKhauHaoChiTiet(IErp_BaoCaoSuDungTSCD obj) {
		String queryKhauHaoChiTiet = "";
		queryKhauHaoChiTiet = "SELECT LTS.diengiai AS LOAITAISAN,TSCD.ma AS MATSCD,TSCD.DIENGIAI AS TENTSCD , ISNULL(TTCP.TEN,'') AS DVTHTEN,ISNULL(TSCD.NGAYGHITANG,'') AS NGAYGHITANG,ISNULL(DC.TAIKHOAN_FK,TK.SOHIEUTAIKHOAN)  AS TAIKHOANCHIPHI, \n "
				+ " TSCD.thangbatdauKH AS NGAYBATDAUKH,ISNULL(TSCD.NGAYGHITANG,'') AS NGAYBATDAUSD,TSCD.sothangKH, \n "
				+ " ISNULL(TSCD.SOTHANGDAKHAUHAO,0) + ISNULL(THAYDOICUOIKY.SOTHANG,0)  + ISNULL(KHAUHAO_SOTHANG.SOTHANGKHAUHAO,0) AS SOTHANGDAKHAUHAO, \n "
				+ " TSCD.ThanhTien +ISNULL(THAYDOIDAUKY.GIATRI,0) AS NGUYENGIADAUKY, \n "
				+ " (ISNULL(KHAUHAODAUKY.KHAUHAOLUYKE,0) + ISNULL(TSCD.GIATRIDAKHAUHAO,0))* ISNULL(DC.PHANTRAM,PB.PHANTRAM)/100 AS KHAUHAODAUKY, \n "
				+ " TSCD.ThanhTien + ISNULL(THAYDOIDAUKY.GIATRI,0) -ISNULL(KHAUHAODAUKY.KHAUHAOLUYKE,0) - ISNULL(TSCD.GIATRIDAKHAUHAO,0) AS CONLAIDAUKY, \n "
				+ " (ISNULL(KHAUHAOTRONGKY.KHAUHAOLUYKE,0))*ISNULL(DC.PHANTRAM,PB.PHANTRAM)/100 AS KHAUHAOTRONGKY, \n "
				+ " TSCD.ThanhTien +ISNULL(THAYDOICUOIKY.GIATRI,0) AS NGUYENGIACUOIKY, \n "
				+ " (ISNULL(KHAUHAOTRONGKY.KHAUHAOLUYKE,0) + ISNULL(KHAUHAODAUKY.KHAUHAOLUYKE,0) + ISNULL(TSCD.GIATRIDAKHAUHAO,0))*ISNULL(DC.PHANTRAM,PB.PHANTRAM)/100 AS KHAUHAOCUOIKY, \n "
				+ " TSCD.ThanhTien + ISNULL(THAYDOICUOIKY.GIATRI,0) - ISNULL(KHAUHAOTRONGKY.KHAUHAOLUYKE,0) - ISNULL(KHAUHAODAUKY.KHAUHAOLUYKE,0) - ISNULL(TSCD.GIATRIDAKHAUHAO,0) AS CONLAICUOIKY \n "
				+ " FROM ERP_TAISANCODINH_KHOANMUCCHIPHI PB  \n "
				+ " LEFT JOIN ERP_NHOMCHIPHI NCP ON NCP.PK_SEQ = PB.KHOANMUCCHIPHI_FK AND PB.PHANTRAM>0"
				+ " INNER JOIN ERP_TAIKHOANKT TK ON NCP.TaiKhoan_FK=TK.SOHIEUTAIKHOAN AND TK.NPP_FK=1 \n "
				+ " INNER JOIN ERP_TAISANCODINH TSCD ON TSCD.pk_seq= PB.TAISANCODINH_FK \n "
				+ " INNER JOIN Erp_LOAITAISAN LTS ON LTS.PK_SEQ = TSCD.LOAITAISAN_FK \n "
				+ " LEFT JOIN " 
				+ " ( " 
				+ " SELECT ROW_NUMBER() OVER (PARTITION BY DC.TAISAN_FK \n"  
				+ " order by DC.NGAYCHUNGTU desc) AS STT,TAISAN_FK,DC.NGAYCHUNGTU\n" 
				+ " FROM  ERP_DIEUCHUYENTAISAN DC " 
				+ " ) DIEUCHINH_NEWEST ON DIEUCHINH_NEWEST.TAISAN_FK= TSCD.PK_SEQ AND DIEUCHINH_NEWEST.STT=1 \n"
				+ " LEFT JOIN  \n " 
				+ " ( \n" 
				+ " SELECT  DC.NGAYCHUNGTU,DC.TAISAN_FK,NCP.TAIKHOAN_FK,KMCP.KHOANMUCCHIPHI_FK,NCP.DONVITHUCHIEN_FK,KMCP.PHANTRAM FROM \n" 
				+ " ERP_DIEUCHUYENTAISAN DC INNER JOIN ERP_DIEUCHUYENTAISAN_KHOANMUCCHIPHI KMCP \n" 
				+ " ON KMCP.DIEUCHUYENTAISAN_FK=DC.PK_SEQ \n" 
				+ " LEFT JOIN ERP_NHOMCHIPHI NCP ON NCP.PK_SEQ = KMCP.KHOANMUCCHIPHI_FK WHERE 1 = 1 \n"
				+ " AND DC.PK_SEQ IN ( SELECT PK_SEQ FROM ERP_DIEUCHUYENTAISAN WHERE 1=1  \n" ;
		if (obj.getYear().length() > 0)
			queryKhauHaoChiTiet += " 	AND YEAR(DC.NGAYCHUNGTU)<='" + obj.getYear() + "' \n ";
		if (obj.getMonth().length() > 0)
			queryKhauHaoChiTiet += " AND  (YEAR(DC.NGAYCHUNGTU)<'" + obj.getYear() + "' OR (YEAR(DC.NGAYCHUNGTU)='" + obj.getYear() + "'	AND MONTH(DC.NGAYCHUNGTU)<='" + obj.getMonth() + "' ) )\n ";
		queryKhauHaoChiTiet +=	 " ) \n" +
				" ) DC ON DC.TAISAN_FK=DIEUCHINH_NEWEST.TAISAN_FK AND DC.NGAYCHUNGTU= DIEUCHINH_NEWEST.NGAYCHUNGTU  \n"
				+ " LEFT JOIN ERP_DONVITHUCHIEN TTCP ON TTCP.PK_SEQ=ISNULL( DC.DONVITHUCHIEN_FK,NCP.DONVITHUCHIEN_FK)  AND ISNULL(DC.PHANTRAM,PB.PHANTRAM)>0 \n " + " LEFT JOIN  \n " + " ( \n "
				+ " 	SELECT TAISAN_FK, SUM(KHAUHAO)  AS KHAUHAOLUYKE \n " + " 	FROM ERP_KHAUHAOTAISAN \n "
				+ " 	WHERE KHAUHAO>0  \n ";
		if (obj.getYear().length() > 0)
			queryKhauHaoChiTiet += " 	AND NAM='" + obj.getYear() + "' \n ";
		if (obj.getMonth().length() > 0)
			queryKhauHaoChiTiet += " 	AND THANG='" + obj.getMonth() + "' \n ";

		queryKhauHaoChiTiet += " 	GROUP BY TAISAN_FK \n "
				+ " )KHAUHAOTRONGKY ON KHAUHAOTRONGKY.TAISAN_FK=TSCD.pk_seq \n " + " LEFT JOIN  \n " + " ( \n "
				+ " 	SELECT TAISAN_FK, count(KHAUHAO)  AS SOTHANGKHAUHAO \n " + " 	FROM ERP_KHAUHAOTAISAN \n "
				+ " 	WHERE KHAUHAO>0  \n ";	
		if (obj.getYear().length() > 0)
			queryKhauHaoChiTiet += " AND NAM <= '" + obj.getYear() + "' \n ";
		if (obj.getMonth().length() > 0)
			queryKhauHaoChiTiet += " AND THANG <= '" + obj.getMonth() + "' \n ";
		queryKhauHaoChiTiet +=	"  GROUP BY TAISAN_FK \n "
				+ " )KHAUHAO_SOTHANG ON KHAUHAO_SOTHANG.TAISAN_FK=TSCD.pk_seq \n " + " LEFT JOIN  \n " + " ( \n "
				+ " 	SELECT TSCD_FK AS TAISAN_FK, SUM(SOTHANG) AS SOTHANG, SUM(GIATRI) AS GIATRI \n "
				+ " 	FROM ERP_TAISANCODINH_DIEUCHINH  \n " + "	WHERE 1=1 ";
		if (obj.getYear().length() > 0 && obj.getMonth().length() <= 0)
			queryKhauHaoChiTiet += " 	AND YEAR(NGAYDIEUCHINH)< '" + obj.getYear() + "' \n ";
		if (obj.getYear().length() > 0 && obj.getMonth().length() > 0)
			queryKhauHaoChiTiet += " 	AND YEAR(NGAYDIEUCHINH)<= '" + obj.getYear() + "' \n "
					+ " AND MONTH(NGAYDIEUCHINH) <'" + obj.getMonth() + "' \n";
		queryKhauHaoChiTiet += " 	GROUP BY TSCD_FK \n " + " )THAYDOIDAUKY ON THAYDOIDAUKY.TAISAN_FK= TSCD.PK_SEQ \n "
				+ " LEFT JOIN  \n " + " ( \n "
				+ " 	SELECT TSCD_FK AS TAISAN_FK, SUM(SOTHANG) AS SOTHANG, SUM(GIATRI) AS GIATRI \n "
				+ " 	FROM ERP_TAISANCODINH_DIEUCHINH  \n " + " 	WHERE 1=1 ";
		if (obj.getYear().length() > 0)
			queryKhauHaoChiTiet += " AND YEAR(NGAYDIEUCHINH)= '" + obj.getYear() + "' \n ";
		if (obj.getMonth().length() > 0)
			queryKhauHaoChiTiet += " AND MONTH(NGAYDIEUCHINH)= '" + obj.getMonth() + "' \n ";
		queryKhauHaoChiTiet += " 	GROUP BY TSCD_FK \n " + " )THAYDOICUOIKY ON THAYDOIDAUKY.TAISAN_FK= TSCD.PK_SEQ \n "
				+ " LEFT JOIN  \n " + " ( \n " + " 	SELECT TAISAN_FK, SUM(KHAUHAO)  AS KHAUHAOLUYKE \n "
				+ " 	FROM ERP_KHAUHAOTAISAN \n " + " 	WHERE KHAUHAO>0  \n ";
		if (obj.getYear().length() > 0 && obj.getMonth().length() <= 0)
			queryKhauHaoChiTiet += " 	AND NAM<'" + obj.getYear() + "'  \n ";
		if (obj.getYear().length() > 0 && obj.getMonth().length() > 0)
			queryKhauHaoChiTiet += " 	AND NAM<='" + obj.getYear() + "'  \n " + " 	AND THANG<'" + obj.getMonth()
					+ "'  \n ";
		queryKhauHaoChiTiet += " 	GROUP BY TAISAN_FK \n " + " )KHAUHAODAUKY ON KHAUHAODAUKY.TAISAN_FK=TSCD.pk_seq \n "
				+ " WHERE TSCD.TRANGTHAI =1 ";
		if(obj.getLoaiTaiSanId().length() >0)
			queryKhauHaoChiTiet += " AND LTS.PK_SEQ='"+obj.getLoaiTaiSanId()+"' \n";
		if (obj.getYear().length() > 0&&obj.getMonth().length() > 0)
			queryKhauHaoChiTiet += " AND ( (YEAR(ISNULL(NGAYGHITANG,'2017-01-01'))< '" + obj.getYear() + "' OR (YEAR(ISNULL(NGAYGHITANG,'2017-01-01'))= '" + obj.getYear() + "' AND MONTH(NGAYGHITANG)<= '" + obj.getMonth() + "' ))) \n ";

		
		queryKhauHaoChiTiet+=" ORDER BY LTS.diengiai ";
		return queryKhauHaoChiTiet;
	}

	private String queryKhauHaoPhanBo(IErp_BaoCaoSuDungTSCD obj) {
		String queryKhauHaoPhanBo = "";
		queryKhauHaoPhanBo = " SELECT LTS.diengiai AS LOAITAISAN,ISNULL(TTCPNHAN.TEN,'') AS DONVI, \n "
				+ " SUM(ISNULL(THAYDOIDAUKY.GIATRI,0)*PB.PHANTRAM/100) + SUM(TSCD.THANHTIEN*PB.PHANTRAM/100) AS NGUYENGIADAUKY, \n "
				+ " (SUM(ISNULL(KHAUHAODAUKY.KHAUHAOLUYKE,0)*PB.PHANTRAM/100) + SUM(ISNULL(TSCD.GIATRIDAKHAUHAO,0)*PB.PHANTRAM/100)) AS KHAUHAODAUKY, \n "
				+ " SUM(ISNULL(THAYDOIDAUKY.GIATRI,0)*PB.PHANTRAM/100) + SUM(TSCD.THANHTIEN*PB.PHANTRAM/100) -SUM(ISNULL(TSCD.GIATRIDAKHAUHAO,0)*PB.PHANTRAM/100) - SUM(ISNULL(KHAUHAODAUKY.KHAUHAOLUYKE,0)*PB.PHANTRAM/100) AS CONLAIDAUKY, \n "
				+ " (SUM(ISNULL(KHAUHAOTRONGKY.KHAUHAOLUYKE,0)*PB.PHANTRAM/100)) AS KHAUHAOTRONGKY, \n "
				+ " SUM(ISNULL(THAYDOICUOIKY.GIATRI,0)*PB.PHANTRAM/100) + SUM(TSCD.THANHTIEN*PB.PHANTRAM/100) AS NGUYENGIACUOIKY, \n "
				+ " (SUM(ISNULL(KHAUHAODAUKY.KHAUHAOLUYKE,0)*PB.PHANTRAM/100) + SUM(ISNULL(KHAUHAOTRONGKY.KHAUHAOLUYKE,0)*PB.PHANTRAM/100) + SUM(ISNULL(TSCD.GIATRIDAKHAUHAO,0)*PB.PHANTRAM/100)) AS KHAUHAOCUOIKY, \n "
				+ " SUM(ISNULL(THAYDOICUOIKY.GIATRI,0)*PB.PHANTRAM/100) + SUM(TSCD.THANHTIEN*PB.PHANTRAM/100) -(SUM(ISNULL(KHAUHAODAUKY.KHAUHAOLUYKE,0)*PB.PHANTRAM/100) + SUM(ISNULL(KHAUHAOTRONGKY.KHAUHAOLUYKE,0)*PB.PHANTRAM/100) + SUM(ISNULL(TSCD.GIATRIDAKHAUHAO,0)*PB.PHANTRAM/100)) AS CONLAICUOIKY \n "
				+ "  FROM ERP_TAISANCODINH_KHOANMUCCHIPHI PB  \n "
				+ " LEFT JOIN ERP_NHOMCHIPHI NCP ON PB.KHOANMUCCHIPHI_FK = NCP.PK_SEQ AND PB.PHANTRAM >0 \n "
				+ " INNER JOIN ERP_DONVITHUCHIEN TTCP ON TTCP.PK_SEQ=NCP.DONVITHUCHIEN_FK  \n "
				+ " INNER JOIN ERP_TAISANCODINH TSCD ON TSCD.pk_seq= PB.TAISANCODINH_FK \n "
				+ " INNER JOIN Erp_LOAITAISAN LTS ON LTS.PK_SEQ = TSCD.LOAITAISAN_FK \n "
				+ " LEFT JOIN ERP_DONVITHUCHIEN TTCPNHAN ON TTCPNHAN.PK_SEQ=NCP.DONVITHUCHIEN_FK  \n " + " LEFT JOIN  \n " + " ( \n "
				+ " 	SELECT TAISAN_FK, SUM(KHAUHAO)  AS KHAUHAOLUYKE \n " + " 	FROM ERP_KHAUHAOTAISAN \n "
				+ " 	WHERE KHAUHAO>0  \n ";
		if (obj.getYear().length() > 0)
			queryKhauHaoPhanBo += " 	AND NAM='" + obj.getYear() + "' \n ";
		if (obj.getMonth().length() > 0)
			queryKhauHaoPhanBo += " 	AND THANG='" + obj.getMonth() + "' \n ";
		queryKhauHaoPhanBo += " 	GROUP BY TAISAN_FK \n "
				+ " )KHAUHAOTRONGKY ON KHAUHAOTRONGKY.TAISAN_FK=TSCD.pk_seq \n " + " LEFT JOIN  \n " + " ( \n "
				+ " 	SELECT TAISAN_FK, count(KHAUHAO)  AS SOTHANGKHAUHAO \n " + " 	FROM ERP_KHAUHAOTAISAN \n "
				+ " 	WHERE KHAUHAO>0  \n ";	
		if (obj.getYear().length() > 0)
			queryKhauHaoPhanBo += " AND THANG <= '" + obj.getYear() + "' \n ";
		if (obj.getMonth().length() > 0)
			queryKhauHaoPhanBo += " AND NAM <= '" + obj.getMonth() + "' \n ";
		queryKhauHaoPhanBo +=	"  GROUP BY TAISAN_FK \n "
				+ " )KHAUHAO_SOTHANG ON KHAUHAO_SOTHANG.TAISAN_FK=TSCD.pk_seq \n " + " LEFT JOIN  \n " + " ( \n "
				+ " 	SELECT TSCD_FK AS TAISAN_FK, SUM(SOTHANG) AS SOTHANG, SUM(GIATRI) AS GIATRI \n "
				+ " 	FROM ERP_TAISANCODINH_DIEUCHINH  \n " + " 	WHERE 1=1 \n ";
		if (obj.getYear().length() > 0 && obj.getMonth().length() <= 0)
			queryKhauHaoPhanBo += " 	AND YEAR(NGAYDIEUCHINH)< '" + obj.getYear() + "' \n ";
		if (obj.getYear().length() > 0 && obj.getMonth().length() > 0)
			queryKhauHaoPhanBo += " 	AND YEAR(NGAYDIEUCHINH)<= '" + obj.getYear() + "' \n "
					+ " AND MONTH(NGAYDIEUCHINH) <'" + obj.getMonth() + "' \n";
		queryKhauHaoPhanBo += " 	GROUP BY TSCD_FK \n " + " )THAYDOIDAUKY ON THAYDOIDAUKY.TAISAN_FK= TSCD.PK_SEQ \n "
				+ " LEFT JOIN  \n " + " ( \n "
				+ " 	SELECT TSCD_FK AS TAISAN_FK, SUM(SOTHANG) AS SOTHANG, SUM(GIATRI) AS GIATRI \n "
				+ " 	FROM ERP_TAISANCODINH_DIEUCHINH  \n " + " 	WHERE 1=1 \n ";
		if (obj.getYear().length() > 0)
			queryKhauHaoPhanBo += " 	AND YEAR(NGAYDIEUCHINH)='" + obj.getYear() + "' \n ";
		if (obj.getMonth().length() > 0)
			queryKhauHaoPhanBo += " 	AND MONTH(NGAYDIEUCHINH)='" + obj.getMonth() + "' \n ";
		queryKhauHaoPhanBo += " 	GROUP BY TSCD_FK \n " + " )THAYDOICUOIKY ON THAYDOIDAUKY.TAISAN_FK= TSCD.PK_SEQ \n "
				+ " LEFT JOIN  \n " + " ( \n " + " 	SELECT TAISAN_FK, SUM(KHAUHAO)  AS KHAUHAOLUYKE \n "
				+ " 	FROM ERP_KHAUHAOTAISAN \n " + " 	WHERE KHAUHAO>0 \n ";
		if (obj.getYear().length() > 0 && obj.getMonth().length() <= 0)
			queryKhauHaoPhanBo += " AND NAM< '" + obj.getYear() + "'" + " \n";
		if (obj.getYear().length() > 0 && obj.getMonth().length() > 0)
			queryKhauHaoPhanBo += " AND NAM <='" + obj.getYear() + "'" + " \n" + " AND THANG <'" + obj.getMonth() + "'"
					+ " \n";
		
		queryKhauHaoPhanBo += " 	GROUP BY TAISAN_FK \n " + " )KHAUHAODAUKY ON KHAUHAODAUKY.TAISAN_FK=TSCD.pk_seq \n "
				+ " WHERE TSCD.TRANGTHAI =1  \n " ;
		if(obj.getLoaiTaiSanId().length() >0)
			queryKhauHaoPhanBo += " AND LTS.PK_SEQ='"+obj.getLoaiTaiSanId()+"' \n";		
		if (obj.getYear().length() > 0&&obj.getMonth().length() > 0)
			queryKhauHaoPhanBo += " AND ( (YEAR(ISNULL(NGAYGHITANG,'2017-01-01'))< '" + obj.getYear() + "' OR (YEAR(ISNULL(NGAYGHITANG,'2017-01-01'))= '" + obj.getYear() + "' AND MONTH(NGAYGHITANG)<= '" + obj.getMonth() + "' ))) \n ";

		queryKhauHaoPhanBo += " GROUP BY LTS.diengiai,TTCPNHAN.TEN \n "
				+ " ORDER BY LTS.diengiai ";
		
		return queryKhauHaoPhanBo;
	}

}
