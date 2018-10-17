package geso.traphaco.erp.servlets.baocaosudungccdc;

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
import geso.traphaco.erp.beans.baocaosudungccdc.IErp_BaoCaoSuDungCCDC;
import geso.traphaco.erp.beans.baocaosudungccdc.imp.Erp_BaoCaoSuDungCCDC;

/**
 * Servlet implementation class BoBaoCaoSuDungCCDC
 */
@WebServlet("/BoBaoCaoSuDungCCDC")
public class BoBaoCaoSuDungCCDC extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BoBaoCaoSuDungCCDC() {
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
		String ctyId = (String)session.getAttribute("congtyId");
		String nppId = (String)session.getAttribute("nppId");
		IErp_BaoCaoSuDungCCDC obj = new Erp_BaoCaoSuDungCCDC();
		obj.createRs();

		System.out.println("----------Báo cáo tăng TSCĐ --------------");
		System.out.println(queryBaoCaoTangCCDC(obj,nppId,ctyId));
		System.out.println("-------------------------------------------");

		System.out.println("----------Báo cáo giảm TSCĐ --------------");
		System.out.println(queryBaoCaoGiamCCDC(obj,nppId,ctyId));
		System.out.println("-------------------------------------------");

		System.out.println("----------Báo cáo Khấu hao chi tiết --------------");
		System.out.println(queryKhauHaoTheoDonVi(obj,nppId,ctyId));
		System.out.println("-------------------------------------------");


		System.out.println("----------Báo cáo Khấu hao phân bổ --------------");
		System.out.println(queryKhauHaoPhanBo(obj,nppId,ctyId));
		System.out.println("-------------------------------------------");

		String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_BaoCaoSuDungCCDC.jsp";
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
		IErp_BaoCaoSuDungCCDC obj = new Erp_BaoCaoSuDungCCDC();

		String ctyId = (String) session.getAttribute("congtyId");
		obj.setuserId(userId);
		obj.setCtyId(ctyId);
		
		String nppId = (String) session.getAttribute("nppId");
		obj.setNppId(nppId);

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
		
		String nhomCCDC = util.antiSQLInspection(request.getParameter("nhomCCDC"));
		if(nhomCCDC == null)
			nhomCCDC = "";
		obj.setNhomCCDCId(nhomCCDC);
		
		
		String dvth = util.antiSQLInspection(request.getParameter("dvth"));
		if(dvth == null)
			dvth = "";
		obj.setDvthId(dvth);

		obj.createRs();
		obj.getDataCongty();

		String action = request.getParameter("action");

		String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_BaoCaoSuDungCCDC.jsp";

		if (action.equals("tao")) {
			try {
				response.setContentType("application/xlsm");
				// 1 -- Báo cáo tăng
				// 2 -- Báo cáo giảm
				// 3 -- Báo cáo khấu hao chi tiết 
				// 4 -- Báo cáo khấu hao phân bổ
				if (loaiBC.equals("1")) {
					response.setHeader("Content-Disposition", "attachment; filename=BaoCaoTangCCDC.xlsm");
					CreateReportTangCCDC(out, obj,nppId,ctyId);
				} else if (loaiBC.equals("2")) {
					response.setHeader("Content-Disposition", "attachment; filename=BaoCaoGiamCCDC.xlsm");
					CreateReportGiamCCDC(out, obj,nppId,ctyId);
				} else if (loaiBC.equals("3")) {
					response.setHeader("Content-Disposition", "attachment; filename=BaoCaoKhauHaoChiTietCCDC.xlsm");
					CreateReportKhauHaoTheoDonVi(out, obj,nppId,ctyId);
				} else if (loaiBC.equals("4")) {
					response.setHeader("Content-Disposition", "attachment; filename=BaoCaoKhauHaoPhanBoCCDC.xlsm");
					CreateReportKhauHaoPhanBo(out, obj,nppId,ctyId);
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

	private void CreateReportTangCCDC(OutputStream out, IErp_BaoCaoSuDungCCDC obj,String nppId,String ctyId) throws Exception {

		try {
			String file = getServletContext().getInitParameter("path") + "\\BaoCaoTangCPPB.xlsm";

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
			double sothangdakhauhao=0;
			double giatritang=0;

			String query = queryBaoCaoTangCCDC(obj,nppId,ctyId);
			System.out.println("[CreateReportTangCCDC]: "+ query);
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
					cell.setValue(rs.getString("NHOMCCDC"));
					cell.setStyle(style);
					cell = CreateBorderSetting2(cell, 0);
					// Mã TS
					cell = cells.getCell("B" + row);
					cell.setValue(rs.getString("MACCDC"));
					cell.setStyle(style);
					cell = CreateBorderSetting2(cell, 0);
					// Tên TS
					cell = cells.getCell("C" + row);
					cell.setValue(rs.getString("TENCCDC"));
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
					
					//Số tháng đã khấu hao
					cell = cells.getCell("H" + row);
					cell.setValue(rs.getInt("SOTHANGDAKHAUHAO"));
					cell.setStyle(style);
					cell = CreateBorderSetting2(cell, 41);
					// Nguyên giá
					cell = cells.getCell("I" + row);
					cell.setValue(rs.getDouble("NGUYENGIA"));
					cell.setStyle(style);
					cell = CreateBorderSetting2(cell, 41);
					
					// Giá trị tăng
					cell = cells.getCell("J" + row);
					cell.setValue(rs.getDouble("GIATRITANG"));
					cell.setStyle(style);
					cell = CreateBorderSetting2(cell, 41);
					// Giá trị đã khấu hao
					cell = cells.getCell("K" + row);
					cell.setValue(rs.getDouble("GIATRIDAKHAUHAO"));
					cell.setStyle(style);
					cell = CreateBorderSetting2(cell, 41);
					// Giá trị còn lại
					cell = cells.getCell("L" + row);
					cell.setValue(rs.getDouble("GIATRICONLAI"));
					cell.setStyle(style);
					cell = CreateBorderSetting2(cell, 41);
					// Lý do tăng
					cell = cells.getCell("M" + row);
					cell.setValue(rs.getString("LYDOTANG"));
					cell.setStyle(style);
					cell = CreateBorderSetting2(cell, 0);
					
					tongNguyenGia +=rs.getDouble("NGUYENGIA");
					tongSoThangKH +=rs.getDouble("SOTHANGKH");
					tongGiaTriDaKH +=rs.getDouble("GIATRIDAKHAUHAO");
					tongGiaTriConLai +=rs.getDouble("GIATRICONLAI");
					sothangdakhauhao +=rs.getDouble("SOTHANGDAKHAUHAO");
					giatritang+=rs.getDouble("GIATRITANG");
					row ++;
				}
			}
			
			///TỔNG CỘNG
			
			
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
			// Ngày bắt đầu sử dụng
			cell = cells.getCell("E" + row);
			cell.setValue("");
			cell.setStyle(style);
			cell = CreateBorderSetting2(cell, 0);
			// Ngày tính khấu hao
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
			cell.setValue(sothangdakhauhao);
			cell.setStyle(style);
			cell = CreateBorderSetting2(cell, 41);
			// Nguyên giá
			cell = cells.getCell("I" + row);
			cell.setValue(tongNguyenGia);
			cell.setStyle(style);
			cell = CreateBorderSetting2(cell, 41);
			
			// Giá trị tăng
			cell = cells.getCell("J" + row);
			cell.setValue(giatritang);
			cell.setStyle(style);
			cell = CreateBorderSetting2(cell, 41);
			// Giá trị đã khấu hao
			cell = cells.getCell("K" + row);
			cell.setValue(tongGiaTriDaKH);
			cell.setStyle(style);
			cell = CreateBorderSetting2(cell, 41);
			// Giá trị còn lại
			cell = cells.getCell("L" + row);
			cell.setValue(tongGiaTriConLai);
			cell.setStyle(style);
			cell = CreateBorderSetting2(cell, 41);
			// Lý do giảm
			cell = cells.getCell("M" + row);
			cell.setValue("");
			cell.setStyle(style);
			cell = CreateBorderSetting2(cell, 0);
			
			
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

	private void CreateReportGiamCCDC(OutputStream out, IErp_BaoCaoSuDungCCDC obj,String nppId,String ctyId) throws Exception {

		try {
			String file = getServletContext().getInitParameter("path") + "\\BaoCaoGiamCPPB.xlsm";

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
			double sothangdakhauhao=0;
			double tonggiatrigiam=0;
			String query = queryBaoCaoGiamCCDC(obj,nppId,ctyId);
			System.out.println("[CreateReportGiamCCDC]:"+query);
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
					cell.setValue(rs.getString("NHOMCCDC"));
					cell.setStyle(style);
					cell = CreateBorderSetting2(cell, 0);
					// Mã TS
					cell = cells.getCell("B" + row);
					cell.setValue(rs.getString("MACCDC"));
					cell.setStyle(style);
					cell = CreateBorderSetting2(cell, 0);
					// Tên TS
					cell = cells.getCell("C" + row);
					cell.setValue(rs.getString("TENCCDC"));
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
					cell.setValue(rs.getInt("NGAYTHANHLY"));
					cell.setStyle(style);
					cell = CreateBorderSetting2(cell, 41);
					// Ngày thanh lý
					cell = cells.getCell("H" + row);
					cell.setValue(rs.getString("NGUYENGIA"));
					cell.setStyle(style);
					cell = CreateBorderSetting2(cell, 0);
					// Nguyên giá
					cell = cells.getCell("I" + row);
					cell.setValue(rs.getDouble("GIATRIGIAM"));
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
					
					row ++;
					
					tongNguyenGia +=rs.getDouble("NGUYENGIA");
					tongSoThangKH +=rs.getDouble("SOTHANGKH");
					tongGiaTriDaKH +=rs.getDouble("GIATRIDAKHAUHAO");
					tongGiaTriConLai +=rs.getDouble("GIATRICONLAI");
					sothangdakhauhao +=rs.getDouble("giatridakhauhao");
					tonggiatrigiam +=rs.getDouble("GIATRIGIAM");
				}
			}
			
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
			// Ngày bắt đầu sử dụng
			cell = cells.getCell("E" + row);
			cell.setValue("");
			cell.setStyle(style);
			cell = CreateBorderSetting2(cell, 0);
			// Ngày tính khấu hao
			cell = cells.getCell("F" + row);
			cell.setValue("");
			cell.setStyle(style);
			cell = CreateBorderSetting2(cell, 0);
			// Thời gian khấu hao (tháng)
			cell = cells.getCell("G" + row);
//			cell.setValue(tongSoThangKH);
			cell.setValue("");
			cell.setStyle(style);
			cell = CreateBorderSetting2(cell, 41);
			// Số tháng đã khấu hao
			cell = cells.getCell("H" + row);
			cell.setValue(tongNguyenGia);
			cell.setStyle(style);
			cell = CreateBorderSetting2(cell, 0);
			// Nguyên giá
			cell = cells.getCell("I" + row);
			cell.setValue(tonggiatrigiam);
			cell.setStyle(style);
			cell = CreateBorderSetting2(cell, 41);
			// Giá trị đã khấu hao
			cell = cells.getCell("J" + row);
			cell.setValue(tongGiaTriDaKH);
			cell.setStyle(style);
			cell = CreateBorderSetting2(cell, 41);
			// Giá trị còn lại
			cell = cells.getCell("K" + row);
			cell.setValue(tongGiaTriConLai);
			cell.setStyle(style);
			cell = CreateBorderSetting2(cell, 41);
			// Lý do giảm
			cell = cells.getCell("L" + row);
			cell.setValue("");
			cell.setStyle(style);
			cell = CreateBorderSetting2(cell, 0);
			
			
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
			
			
			workbook.save(out);
			fstream.close();

		} catch (Exception ex) {
			System.out.println(ex.toString());
			ex.printStackTrace();
			throw new Exception("Không tạo được báo cáo");
		}

	}


	private void CreateReportKhauHaoTheoDonVi(OutputStream out, IErp_BaoCaoSuDungCCDC obj,String nppId,String ctyId) throws Exception {

		try {
			String file = getServletContext().getInitParameter("path") + "\\BaoCaoCCDCChiTiet.xlsm";

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
			Style style2;
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
			
			if(obj.getDvthId().length()>0) {
				cell = cells.getCell("E7");
				String donvi=obj.getDonViTen();
				cell.setValue("Đơn vị: " + donvi); 
				style = cell.getStyle();
				style.setFont(font);
				style.setHAlignment(TextAlignmentType.CENTER);
				cell.setStyle(style);
			}
			
			
			
			double tongSoThangKH=0;
			double tongSoThangDaKH=0;
			double tongNguyenGiadauky=0;
			double tongNguyenGiacuoiky=0;
			double tongConLaiDauKy=0;
			double tongConLaiCuoiKy=0;
			double tongKhauHaoDauKy=0;
			double tongKhauHaoTrongKy=0;
			double tongThayDoiTrongKy=0;
			double tongkhauHaoCuoiKy=0;
			
			double tongSoThangKHTheoLoaiCCDC=0;
			double tongSoThangDaKHTheoLoaiCCDC=0;
			double tongConLaiDauKyTheoLoaiCCDC=0;
			double tongConLaiCuoiKyTheoLoaiCCDC=0;
			double tongKhauHaoTrongKyTheoLoaiCCDC=0;
			double tongNguyenGiaDauKyTheoLoaiCCDC=0;
			double tongNguyenGiaCuoiKyTheoLoaiCCDC=0;
			double tongKhauHaoDauKyTheoLoaiCCDC=0;
			double tongKhauHaoCuoiKyTheoLoaiCCDC=0;
			double tongThayDoiTrongKyTheoLoaiCCDC=0;
			String loaiccdcOld="";
			int rowmergeEnd=1;
			int rowmergeEndStart=1;
			int sttCu=1;
			int sttMoi=1;
			boolean isMerge =false;
			String query = queryKhauHaoTheoDonVi(obj,nppId,ctyId);
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
				
					
					String loaiccdcNew=rs.getString("NHOMCCDC");
					if((loaiccdcOld!="") && (!loaiccdcOld.equals(loaiccdcNew)))
					{
						System.out.println("Loại cũ là "+loaiccdcOld);
						System.out.println("Loại mới là"+loaiccdcNew);
						// Loại CCDC
						cell = cells.getCell("A" + row);
						cell.setValue("");
						//cell.setStyle(style2);
						style2 = cell.getStyle();
						cell = CreateBorderSetting2(cell, 0);
						// Mã CCDC
						cell = cells.getCell("B" + row);
						cell.setValue("TỔNG CỘNG");
						style2.setColor(Color.YELLOW);
						cell.setStyle(style2);
		
						cell = CreateBorderSetting2(cell, 0);
						// Tên CCDC
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
						cell.setValue(tongSoThangKHTheoLoaiCCDC);
						//cell.setStyle(style);
						style2.setColor(Color.YELLOW);
						cell.setStyle(style2);
						cell = CreateBorderSetting2(cell, 41);
						// Số tháng đã khấu hao
						cell = cells.getCell("H" + row);
						cell.setValue(tongSoThangDaKHTheoLoaiCCDC);
						//cell.setStyle(style);
						style2.setColor(Color.YELLOW);
						cell.setStyle(style2);
						cell = CreateBorderSetting2(cell, 41);
						// Tài khoản chi phí
						cell = cells.getCell("I" + row);
						cell.setValue("");
						cell.setStyle(style);
						cell = CreateBorderSetting2(cell, 41);
						// Giá trị TS đầu kỳ - Nguyên giá
						cell = cells.getCell("J" + row);
						cell.setValue(tongNguyenGiaDauKyTheoLoaiCCDC);
						//cell.setStyle(style);
						style2.setColor(Color.YELLOW);
						cell.setStyle(style2);
						cell = CreateBorderSetting2(cell, 41);
						// Giá trị TS đầu kỳ - Khấu hao
						cell = cells.getCell("K" + row);
						cell.setValue(tongKhauHaoDauKyTheoLoaiCCDC);
						//cell.setStyle(style);
						style2.setColor(Color.YELLOW);
						cell.setStyle(style2);
						cell = CreateBorderSetting2(cell, 41);
						// Giá trị TS đầu kỳ - Còn lại
						cell = cells.getCell("L" + row);
						cell.setValue(tongConLaiDauKyTheoLoaiCCDC);
						//cell.setStyle(style);
						style2.setColor(Color.YELLOW);
						cell.setStyle(style2);
						cell = CreateBorderSetting2(cell, 41);
						// Khấu hao trong kỳ
						cell = cells.getCell("M" + row);
						cell.setValue(tongKhauHaoTrongKyTheoLoaiCCDC);
						//cell.setStyle(style);
						style2.setColor(Color.YELLOW);
						cell.setStyle(style2);
						cell = CreateBorderSetting2(cell, 41);
						//Thay đổi trong kỳ
						cell = cells.getCell("N" + row);
						cell.setValue(tongThayDoiTrongKyTheoLoaiCCDC);
						//cell.setStyle(style);
						style2.setColor(Color.YELLOW);
						cell.setStyle(style2);
						cell = CreateBorderSetting2(cell, 41);
						// Giá trị TS cuối kỳ - Nguyên giá
						cell = cells.getCell("O" + row);
						cell.setValue(tongNguyenGiaCuoiKyTheoLoaiCCDC);
						//cell.setStyle(style);
						style2.setColor(Color.YELLOW);
						cell.setStyle(style2);
						cell = CreateBorderSetting2(cell, 41);
						// Giá trị TS cuối kỳ - Khấu hao
						cell = cells.getCell("P" + row);
						cell.setValue(tongKhauHaoCuoiKyTheoLoaiCCDC);
						//cell.setStyle(style);
						style2.setColor(Color.YELLOW);
						cell.setStyle(style2);
						cell = CreateBorderSetting2(cell, 41);
						// Giá trị TS cuối kỳ - Còn lại
						cell = cells.getCell("Q" + row);
						cell.setValue(tongConLaiCuoiKyTheoLoaiCCDC);
						//cell.setStyle(style);
						style2.setColor(Color.YELLOW);
						cell.setStyle(style2);
						cell = CreateBorderSetting2(cell, 41);	
						
						cell = cells.getCell("S" + row);
						cell.setValue("");
						cell.setStyle(style);
						cell = CreateBorderSetting2(cell, 41);	
						cell = cells.getCell("T" + row);
						cell.setValue("");
						cell.setStyle(style);
						cell = CreateBorderSetting2(cell, 41);	
						cell = cells.getCell("U" + row);
						cell.setValue("");
						cell.setStyle(style);
						cell = CreateBorderSetting2(cell, 41);	
						row++;
						
						
						 tongSoThangKHTheoLoaiCCDC=0;
						 tongSoThangDaKHTheoLoaiCCDC=0;
						 tongConLaiDauKyTheoLoaiCCDC=0;
						 tongConLaiCuoiKyTheoLoaiCCDC=0;
						 tongKhauHaoTrongKyTheoLoaiCCDC=0;
						 tongNguyenGiaDauKyTheoLoaiCCDC=0;
						 tongNguyenGiaCuoiKyTheoLoaiCCDC=0;
						 tongKhauHaoDauKyTheoLoaiCCDC=0;
						 tongKhauHaoCuoiKyTheoLoaiCCDC=0;
						 tongThayDoiTrongKyTheoLoaiCCDC=0;
						
						
					
					}
//					sttCu=rs.getString("STTCCDC");
					
						
					if(sttCu==1)
					{	isMerge=false;
						rowmergeEndStart = row-2;
					}
									
					if(sttCu>1&&sttMoi==1)
					{
						isMerge=true;
					
						rowmergeEnd = row-2	;
					}
						
						//Merge những thông tin chung để tránh bị double giá trị.
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
							cells.merge(rowmergeEndStart, 8, rowmergeEnd, 8);
							cells.merge(rowmergeEndStart, 9, rowmergeEnd, 9);
							cells.merge(rowmergeEndStart, 10, rowmergeEnd, 10);
							cells.merge(rowmergeEndStart, 11, rowmergeEnd, 11);
							cells.merge(rowmergeEndStart, 14, rowmergeEnd, 14);
							cells.merge(rowmergeEndStart, 15, rowmergeEnd, 15);
							cells.merge(rowmergeEndStart, 16, rowmergeEnd, 16);	
//							cells.merge(rowmergeEndStart, 17, rowmergeEnd, 17);	
							isMerge=false;
						}
					
						// Loại TS
						cell = cells.getCell("A" + row);
						cell.setValue(rs.getString("NHOMCCDC"));
						cell.setStyle(style);
						cell = CreateBorderSetting(cell, 0);	
						
					
						// Mã TS
						cell = cells.getCell("B" + row);
						cell.setValue(rs.getString("MACCDC"));
						cell.setStyle(style);
						cell = CreateBorderSetting(cell, 0);
					
						// Tên TS
						cell = cells.getCell("C" + row);
						cell.setValue(rs.getString("TENCCDC"));
						cell.setStyle(style);
						cell = CreateBorderSetting(cell, 0);
						
						// Đơn vị sử dụng
						cell = cells.getCell("D" + row);
						cell.setValue(rs.getString("DONVI"));
						cell.setStyle(style);
						cell = CreateBorderSetting(cell, 0);
						
						// Ngày ghi tăng
						cell = cells.getCell("E" + row);
						cell.setValue(rs.getString("THANGBATDAUKH"));
						cell.setStyle(style);
						cell = CreateBorderSetting(cell, 41);
						
						// Ngày bắt đầu khấu hao
						cell = cells.getCell("F" + row);
						cell.setValue(rs.getString("THANGBATDAUKH"));
						cell.setStyle(style);
						cell = CreateBorderSetting(cell, 41);
				
						
						// Thời gian khấu hao (tháng)
						cell = cells.getCell("G" + row);
						cell.setValue(rs.getInt("SOTHANGKH"));
						cell.setStyle(style);
						cell = CreateBorderSetting(cell, 41);
						
						// Số tháng đã khấu hao
						cell = cells.getCell("H" + row);
						cell.setValue(rs.getDouble("SOTHANGDAKHAUHAO"));
						cell.setStyle(style);
						cell = CreateBorderSetting(cell, 41);
						
						// Tài khoản chi phí
						cell = cells.getCell("I" + row);
						cell.setValue(rs.getString("TKNHANCP"));
			
						cell.setStyle(style);
						cell = CreateBorderSetting(cell, 41);
						
						// Nguyên giá đầu kỳ
						cell = cells.getCell("J" + row);
						cell.setValue(rs.getDouble("NGUYENGIADAUKY"));
						style.setNumber(41);
						cell.setStyle(style);
						cell = CreateBorderSetting(cell, 41);
						
						// Khấu hao đầu kỳ
						cell = cells.getCell("K" + row);
						cell.setValue(rs.getDouble("KHAUHAODAUKY"));
						style.setNumber(41);
						cell.setStyle(style);
						cell = CreateBorderSetting(cell, 41);
						
						// Còn lại đầu kỳ
						cell = cells.getCell("L" + row);
						cell.setValue(rs.getDouble("CONLAIDAUKY"));
						style.setNumber(0);
						cell.setStyle(style);
						cell = CreateBorderSetting(cell, 41);
						
						// Khấu hao trong kỳ
						cell = cells.getCell("M" + row);
						cell.setValue(rs.getDouble("KHAUHAOTRONGKY"));
						style.setNumber(0);
						cell.setStyle(style);
						cell = CreateBorderSetting(cell, 41);
						
						// Thay đổi trong kỳ
						cell = cells.getCell("N" + row);
						cell.setValue(rs.getDouble("THAYDOITRONGKY"));
						style.setNumber(0);
						cell.setStyle(style);
						cell = CreateBorderSetting(cell, 41);
						
						
						// Nguyên giá cuối kỳ
						cell = cells.getCell("O" + row);
						cell.setValue(rs.getDouble("NGUYENGIACUOIKY"));
						style.setNumber(0);
						cell.setStyle(style);
						cell = CreateBorderSetting(cell, 41);
						
						// Khấu hao cuối kỳ
						cell = cells.getCell("P" + row);
						cell.setValue(rs.getDouble("KHAUHAOCUOIKY"));
						style.setNumber(0);
						cell.setStyle(style);
						cell = CreateBorderSetting(cell, 41);
						
						//Còn lại cuối kỳ
						cell = cells.getCell("Q" + row);
						cell.setValue(rs.getDouble("CONLAICUOIKY"));
						style.setNumber(0);
						cell.setStyle(style);
						cell = CreateBorderSetting(cell, 41);
						
						// Trung tâm chi phí
						cell = cells.getCell("R" + row);
						cell.setValue(rs.getString("DVTHTEN"));
						cell.setStyle(style);
						cell = CreateBorderSetting(cell, 0);
						
							// Nhóm chi Phí
						cell = cells.getCell("S" + row);
						cell.setValue(rs.getString("NCPTEN"));
						cell.setStyle(style);
						cell = CreateBorderSetting(cell, 0);
					

					row ++;
					sttCu=rs.getInt("STT");
					
					
					if(rs.getInt("STT")==1)
					{
						tongNguyenGiadauky +=rs.getDouble("NGUYENGIADAUKY");	
						tongNguyenGiacuoiky +=rs.getDouble("NGUYENGIACUOIKY");	
						tongSoThangKH +=rs.getDouble("SOTHANGKH");
						tongSoThangDaKH +=rs.getDouble("SOTHANGDAKHAUHAO");
						tongConLaiDauKy+=rs.getDouble("CONLAIDAUKY");
						tongConLaiCuoiKy+=rs.getDouble("CONLAICUOIKY");
						tongKhauHaoDauKy+=rs.getDouble("KHAUHAODAUKY");
						tongThayDoiTrongKy+=rs.getDouble("THAYDOITRONGKY");
						tongkhauHaoCuoiKy+=rs.getDouble("KHAUHAOCUOIKY");
						
					}
					tongKhauHaoTrongKy+=rs.getDouble("KHAUHAOTRONGKY");
					// Công theo từng loại
					if(rs.getInt("STT")==1)
					{
						tongNguyenGiaDauKyTheoLoaiCCDC += rs.getDouble("NGUYENGIADAUKY");		
						tongSoThangKHTheoLoaiCCDC +=rs.getDouble("SOTHANGKH");
						tongSoThangDaKHTheoLoaiCCDC +=rs.getDouble("SOTHANGDAKHAUHAO");
						tongNguyenGiaCuoiKyTheoLoaiCCDC+=rs.getDouble("NGUYENGIACUOIKY");
						tongConLaiDauKyTheoLoaiCCDC+=rs.getDouble("CONLAIDAUKY");
						tongConLaiCuoiKyTheoLoaiCCDC+=rs.getDouble("CONLAICUOIKY");
						tongThayDoiTrongKyTheoLoaiCCDC+=rs.getDouble("THAYDOITRONGKY");
						tongKhauHaoDauKyTheoLoaiCCDC+=rs.getDouble("KHAUHAODAUKY");
						tongKhauHaoCuoiKyTheoLoaiCCDC+=rs.getDouble("KHAUHAOCUOIKY");
						tongKhauHaoTrongKyTheoLoaiCCDC+=rs.getDouble("KHAUHAOTRONGKY");
					}
					
					loaiccdcOld=loaiccdcNew;
				}
			}
			
			if(sttCu==1)
			{	isMerge=false;
				rowmergeEndStart = row-2;
			}
							
			if(sttCu>1&&sttMoi==1)
			{
				isMerge=true;
			
				rowmergeEnd = row-2	;
			}
				
				//Merge những thông tin chung để tránh bị double giá trị.
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
					cells.merge(rowmergeEndStart, 8, rowmergeEnd, 8);
					cells.merge(rowmergeEndStart, 9, rowmergeEnd, 9);
					cells.merge(rowmergeEndStart, 10, rowmergeEnd, 10);
					cells.merge(rowmergeEndStart, 11, rowmergeEnd, 11);
					cells.merge(rowmergeEndStart, 14, rowmergeEnd, 14);
					cells.merge(rowmergeEndStart, 15, rowmergeEnd, 15);
					cells.merge(rowmergeEndStart, 16, rowmergeEnd, 16);	
					//cells.merge(rowmergeEndStart, 17, rowmergeEnd, 17);	
					isMerge=false;
				}
			
			
			
			
		
			
			
			///////////// TỔNG CỘNG
			
			cell = cells.getCell("A" + row);
			cell.setValue("TỔNG CỘNG");
			style2=cell.getStyle();
			style2.setColor(Color.YELLOW);
			cell.setStyle(style2);
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
			
			
			// Ngày ghi tăng
			
			cell = cells.getCell("E" + row);
			cell.setValue("");
			cell.setStyle(style);
			cell = CreateBorderSetting(cell, 41);
			
			
			//Ngày bắt đầu khấu hao
			cell = cells.getCell("F" + row);
			cell.setValue("");
			cell.setStyle(style);
			cell = CreateBorderSetting(cell,0);
			//Thời gian khấu hao
			cell = cells.getCell("G" + row);
			cell.setValue("");
			cell.setStyle(style);
			cell = CreateBorderSetting(cell,0);
			//Số tháng đã khấu hao
			cell = cells.getCell("H" + row);
			cell.setValue("");
			cell.setStyle(style);
			cell = CreateBorderSetting(cell,0);
			//Tài khoản chi phí
			cell = cells.getCell("I" + row);
			cell.setValue("");
			cell.setStyle(style);
			cell = CreateBorderSetting(cell,0);
			
			//Nguyên giá đầu kỳ
			cell = cells.getCell("J" + row);
			cell.setValue(tongNguyenGiadauky);
			style2=cell.getStyle();
			style2.setColor(Color.YELLOW);
			style2.setNumber(0);
			cell.setStyle(style2);
			cell = CreateBorderSetting(cell,41);
			
			
			// Khấu hao đầu kỳ
			cell = cells.getCell("K" + row);
			cell.setValue(tongKhauHaoDauKy);
			style2=cell.getStyle();
			style2.setColor(Color.YELLOW);
			style2.setNumber(0);
			cell.setStyle(style2);
			cell = CreateBorderSetting(cell, 41);
			
			
			// Còn lại đầu kỳ
			cell = cells.getCell("L" + row);
			cell.setValue(tongConLaiDauKy);
			style2=cell.getStyle();
			style2.setColor(Color.YELLOW);
			style2.setNumber(0);
			cell.setStyle(style2);
			cell = CreateBorderSetting(cell, 41);
			// Số tháng đã khấu hao
		
			
		

			cell = cells.getCell("M" + row);
			cell.setValue(tongKhauHaoTrongKy);
			style2=cell.getStyle();
			style2.setColor(Color.YELLOW);
			style2.setNumber(0);
			cell.setStyle(style2);
			cell = CreateBorderSetting(cell, 41);
			
			// Thay đổi trong kỳ
			cell = cells.getCell("N" + row);
			cell.setValue(tongThayDoiTrongKy);
			style2=cell.getStyle();
			style2.setColor(Color.YELLOW);
			style2.setNumber(0);
			cell.setStyle(style2);
			cell = CreateBorderSetting(cell, 41);

			//Tổng nguyên giá cuối kỳ
			cell = cells.getCell("O" + row);
			cell.setValue(tongNguyenGiacuoiky);
			style2=cell.getStyle();
			style2.setColor(Color.YELLOW);
			style2.setNumber(0);
			cell.setStyle(style2);
			cell = CreateBorderSetting(cell, 41);
			// Khấu hao cuối kỳ
			cell = cells.getCell("P" + row);
			cell.setValue(tongkhauHaoCuoiKy);
			style2=cell.getStyle();
			style2.setColor(Color.YELLOW);
			style2.setNumber(0);
			cell.setStyle(style2);
			cell = CreateBorderSetting(cell, 41);
			
			
			// Còn lại cuối kỳ
			cell = cells.getCell("Q" + row);
			cell.setValue(tongConLaiCuoiKy);
			style2=cell.getStyle();
			style2.setColor(Color.YELLOW);
			style2.setNumber(0);
			cell.setStyle(style2);
			cell = CreateBorderSetting(cell, 41);
			
			//Trung tâm chi phí
			cell = cells.getCell("R" + row);
			cell.setValue("");
			cell.setStyle(style);
			cell = CreateBorderSetting(cell, 41);
			
			//Tên nhóm chi phí
			cell = cells.getCell("S" + row);
			cell.setValue("");
			cell.setStyle(style);
			cell = CreateBorderSetting(cell, 41);
			
			
			
			
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

	private void CreateReportKhauHaoPhanBo(OutputStream out, IErp_BaoCaoSuDungCCDC obj,String nppId,String ctyId) throws Exception {

		try {
			String file = getServletContext().getInitParameter("path") + "\\BaoCaoPhanBoKHCPPB.xlsm";

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
			
			double tongSoThangKH=0;
			double tongSoThangDaKH=0;
			double tongNguyenGia=0;
			double tongConLaiDauKy=0;
			double tongConLaiCuoiKy=0;
			double tongKhauHaoTrongKy=0;
			
			int row = 10;
			String query = queryKhauHaoPhanBo(obj,nppId,ctyId);
			System.out.println("[CreateReportKhauHaoPhanBo]:"+query);
			obj.createRsBaoCao(query);
			ResultSet rs = obj.getRsBaoCao();
			if (rs != null) {
				while (rs.next()) {
					
					cell = cells.getCell("A" + row);
					cell.setValue("" + rs.getString("NHOMCCDC"));
					font.setColor(Color.BLACK);	
					font.setSize(10);
					font.setBold(false);
					style.setFont(font);
					style.setNumber(0);
					style.setHAlignment(TextAlignmentType.LEFT);
					cell.setStyle(style);
					cell = CreateBorderSetting(cell,0);
					
					
					cell = cells.getCell("B"+row);			
					cell.setValue("" +rs.getString("MACCDC"));
					font.setColor(Color.BLACK);	
					font.setSize(10);
					font.setBold(false);
					style.setFont(font);
					style.setNumber(0);
					style.setHAlignment(TextAlignmentType.LEFT);
					cell.setStyle(style);
					cell = CreateBorderSetting(cell,0);
					

					
					cell = cells.getCell("C"+row);			
					cell.setValue(rs.getString("DVTHTEN"));
					font.setColor(Color.BLACK);	
					font.setSize(10);
					font.setBold(false);
					style.setFont(font);
					style.setNumber(0);
					style.setHAlignment(TextAlignmentType.LEFT);
					cell.setStyle(style);
					cell = CreateBorderSetting(cell,0);
					
					
					cell = cells.getCell("D"+row);			
					cell.setValue(rs.getString("TENCCDC"));
					font.setColor(Color.BLACK);	
					font.setSize(10);
					font.setBold(false);
					style.setFont(font);
					style.setNumber(0);
					style.setHAlignment(TextAlignmentType.LEFT);
					cell.setStyle(style);
					cell = CreateBorderSetting(cell,0);
					
					
					cell = cells.getCell("E"+row);			
					cell.setValue(rs.getString("DONVI"));
					font.setColor(Color.BLACK);	
					font.setSize(10);
					font.setBold(false);
					style.setFont(font);
					style.setNumber(0);
					style.setHAlignment(TextAlignmentType.LEFT);
					cell.setStyle(style);
					cell = CreateBorderSetting(cell,0);
					
					
					cell = cells.getCell("F"+row);			
					cell.setValue(rs.getDouble("NGUYENGIA"));
					font.setColor(Color.BLACK);	
					font.setSize(10);
					font.setBold(false);
					style.setFont(font);
					style.setNumber(0);
					style.setHAlignment(TextAlignmentType.LEFT);
					cell.setStyle(style);
					cell = CreateBorderSetting(cell,41);
					
					
					cell = cells.getCell("G"+row);			
					cell.setValue(rs.getString("NGAYBATDAUPB"));
					font.setColor(Color.BLACK);	
					font.setSize(10);
					font.setBold(false);
					style.setFont(font);
					style.setNumber(0);
					style.setHAlignment(TextAlignmentType.LEFT);
					cell.setStyle(style);
					cell = CreateBorderSetting(cell,0);
					
					
					cell = cells.getCell("H"+row);			
					cell.setValue(rs.getDouble("SOTHANGKH"));
					font.setColor(Color.BLACK);	
					font.setSize(10);
					font.setBold(false);
					style.setFont(font);
					style.setNumber(0);
					style.setHAlignment(TextAlignmentType.LEFT);
					cell.setStyle(style);
					cell = CreateBorderSetting(cell,41);
					
					
					cell = cells.getCell("I"+row);			
					cell.setValue(rs.getDouble("SOTHANGDAKHAUHAO"));
					font.setColor(Color.BLACK);	
					font.setSize(10);
					font.setBold(false);
					style.setFont(font);
					style.setNumber(0);
					style.setHAlignment(TextAlignmentType.LEFT);
					cell.setStyle(style);
					cell = CreateBorderSetting(cell,41);
					
					
					cell = cells.getCell("J"+row);			
					cell.setValue(rs.getString("TKKHAUHAO"));
					font.setColor(Color.BLACK);	
					font.setSize(10);
					font.setBold(false);
					style.setFont(font);
					style.setNumber(0);
					style.setHAlignment(TextAlignmentType.LEFT);
					cell.setStyle(style);
					cell = CreateBorderSetting(cell,0);
					
					cell = cells.getCell("K"+row);			
					cell.setValue(rs.getString("TKNHANCP"));
					font.setColor(Color.BLACK);	
					font.setSize(10);
					font.setBold(false);
					style.setFont(font);
					style.setNumber(0);
					style.setHAlignment(TextAlignmentType.LEFT);
					cell.setStyle(style);
					cell = CreateBorderSetting(cell,0);
					
					
					cell = cells.getCell("L"+row);			
					cell.setValue(rs.getDouble("CONLAIDAUKY"));
					font.setColor(Color.BLACK);	
					font.setSize(10);
					font.setBold(false);
					style.setFont(font);
					style.setNumber(0);
					style.setHAlignment(TextAlignmentType.LEFT);
					cell.setStyle(style);
					cell = CreateBorderSetting(cell,41);
					
					cell = cells.getCell("M"+row);			
					cell.setValue(0);
					font.setColor(Color.BLACK);	
					font.setSize(10);
					font.setBold(false);
					style.setFont(font);
					style.setNumber(0);
					style.setHAlignment(TextAlignmentType.LEFT);
					cell.setStyle(style);
					cell = CreateBorderSetting(cell,41); 
					
					
					cell = cells.getCell("N"+row);			
					cell.setValue(rs.getDouble("KHAUHAOTRONGKY"));
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
					
					row++;
					
					tongConLaiCuoiKy+=rs.getDouble("CONLAICUOIKY");
					tongConLaiDauKy+=rs.getDouble("CONLAIDAUKY");
					tongKhauHaoTrongKy+=rs.getDouble("KHAUHAOTRONGKY");
					tongNguyenGia+=rs.getDouble("NGUYENGIA");
					tongSoThangKH+=rs.getDouble("SOTHANGKH");
					tongSoThangDaKH+=rs.getDouble("SOTHANGDAKHAUHAO");
				}
			}
			
			/// TỔNG CỘNG
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
			cell.setValue(tongNguyenGia);
			font.setColor(Color.BLACK);	
			font.setSize(10);
			font.setBold(false);
			style.setFont(font);
			style.setNumber(0);
			style.setHAlignment(TextAlignmentType.LEFT);
			cell.setStyle(style);
			cell = CreateBorderSetting(cell,41);
			
			
			cell = cells.getCell("G"+row);			
			cell.setValue("");
			font.setColor(Color.BLACK);	
			font.setSize(10);
			font.setBold(false);
			style.setFont(font);
			style.setNumber(0);
			style.setHAlignment(TextAlignmentType.LEFT);
			cell.setStyle(style);
			cell = CreateBorderSetting(cell,0);
			
			
			cell = cells.getCell("H"+row);			
			cell.setValue(tongSoThangKH);
			font.setColor(Color.BLACK);	
			font.setSize(10);
			font.setBold(false);
			style.setFont(font);
			style.setNumber(0);
			style.setHAlignment(TextAlignmentType.LEFT);
			cell.setStyle(style);
			cell = CreateBorderSetting(cell,41);
			
			
			cell = cells.getCell("I"+row);			
			cell.setValue(tongSoThangDaKH);
			font.setColor(Color.BLACK);	
			font.setSize(10);
			font.setBold(false);
			style.setFont(font);
			style.setNumber(0);
			style.setHAlignment(TextAlignmentType.LEFT);
			cell.setStyle(style);
			cell = CreateBorderSetting(cell,41);
			
			
			cell = cells.getCell("J"+row);			
			cell.setValue("");
			font.setColor(Color.BLACK);	
			font.setSize(10);
			font.setBold(false);
			style.setFont(font);
			style.setNumber(0);
			style.setHAlignment(TextAlignmentType.LEFT);
			cell.setStyle(style);
			cell = CreateBorderSetting(cell,0);
			
			cell = cells.getCell("K"+row);			
			cell.setValue("");
			font.setColor(Color.BLACK);	
			font.setSize(10);
			font.setBold(false);
			style.setFont(font);
			style.setNumber(0);
			style.setHAlignment(TextAlignmentType.LEFT);
			cell.setStyle(style);
			cell = CreateBorderSetting(cell,0);
			
			
			cell = cells.getCell("L"+row);			
			cell.setValue(tongConLaiDauKy);
			font.setColor(Color.BLACK);	
			font.setSize(10);
			font.setBold(false);
			style.setFont(font);
			style.setNumber(0);
			style.setHAlignment(TextAlignmentType.LEFT);
			cell.setStyle(style);
			cell = CreateBorderSetting(cell,41);
			
			cell = cells.getCell("M"+row);			
			cell.setValue(0);
			font.setColor(Color.BLACK);	
			font.setSize(10);
			font.setBold(false);
			style.setFont(font);
			style.setNumber(0);
			style.setHAlignment(TextAlignmentType.LEFT);
			cell.setStyle(style);
			cell = CreateBorderSetting(cell,41); 
			
			
			cell = cells.getCell("N"+row);			
			cell.setValue(tongKhauHaoTrongKy);
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

	
	private String queryBaoCaoTangCCDC(IErp_BaoCaoSuDungCCDC obj,String nppId,String CtyId) {
		String queryBaoCaoTangCCDC = "";
		queryBaoCaoTangCCDC = " SELECT lccdc.diengiai AS NHOMCCDC,CCDC.ma AS MACCDC,CCDC.DIENGIAI AS TENCCDC,ISNULL(dvth.TEN,'') AS TENDVTH, \n " + 
			" CCDC.THANGBATDAUKH AS NGAYBATDAUKH,CCDC.THANGBATDAUKH AS NGAYBATDAUSD,CCDC.sothangKH," +
			" CCDC.THANHTIEN + ISNULL(THAYDOITRUOCKY.GIATRI,0) AS NGUYENGIA,ISNULL(THAYDOITRONGKY.GIATRI,0) AS GIATRITANG, \n " + 
			" ISNULL(CCDC.GIATRIDAKHAUHAO,0) + ISNULL(KHAUHAO.KHAUHAOLUYKE,0) AS GIATRIDAKHAUHAO, \n " + 
			" ISNULL(CCDC.THANHTIEN,0) + ISNULL(THAYDOITRUOCKY.GIATRI,0)  -ISNULL(CCDC.GIATRIDAKHAUHAO,0) - ISNULL(KHAUHAO.KHAUHAOLUYKE,0) AS GIATRICONLAI, \n " + 
			" CASE WHEN " +
			" THAYDOITRONGKY.LOAICHUNGTU='1' " +" THEN N'Đánh giá lại CPTT' " +
			" ELSE '' END AS LYDOTANG " +
			",CCDC.sothangdakhauhao\n " + 
//-------------------------------------------------------------------------------------------------
			//Thay đổi trong kỳ
			" FROM  \n" +
			" ( \n " + 
			" 	SELECT CCDC_FK AS CCDC_FK, SUM(GIATRI) AS GIATRI,LOAICHUNGTU \n " + 
			" 	FROM ERP_CONGCUDUNGCU_DIEUCHINH  \n " +
			"   where giatri>0";
			if (obj.getYear().length() > 0)
				queryBaoCaoTangCCDC += " AND YEAR(NGAYDIEUCHINH) = " + obj.getYear() + " \n ";
			if (obj.getMonth().length() > 0)
				queryBaoCaoTangCCDC += " AND MONTH(NGAYDIEUCHINH) = " + obj.getMonth() + " \n "+
			" 	GROUP BY CCDC_FK,LOAICHUNGTU \n " + 
			" )THAYDOITRONGKY"+
//-----------------------------------------------------------------------------------------------			
			" INNER JOIN ERP_CONGCUDUNGCU CCDC ON THAYDOITRONGKY.CCDC_FK=CCDC.PK_SEQ \n " + 
			" left join Erp_LOAICCDC lccdc ON ccdc.loaiccdc_fk = lccdc.pk_seq \n " + 
			" LEFT JOIN ERP_donvithuchien dvth ON ccdc.dvth_fk=dvth.pk_seq \n " + 
//--------------------------------------------------------------------------------------------------	
			// Khấu hao
			" LEFT JOIN  \n " + 
			" ( \n " + 
			" 	SELECT CCDC_FK, SUM(KHAUHAO)  AS KHAUHAOLUYKE \n " + 
			" 	FROM ERP_KHAUHAOCCDC \n " + 
			" 	WHERE KHAUHAO>0 \n " + 
			" 	GROUP BY CCDC_FK \n " + 
			" )KHAUHAO ON KHAUHAO.CCDC_FK=CCDC.pk_seq \n " + 
//-----------------------------------------------------------------------------------------------------------
		// Thay đổi trước kỳ	
			" LEFT JOIN  \n " + 
			" ( \n " + 
			" 	SELECT CCDC_FK AS CCDC_FK, SUM(GIATRI) AS GIATRI \n " + 
			" 	FROM ERP_CONGCUDUNGCU_DIEUCHINH  \n " +
			"	WHERE CCDC_FK IS NOT NULL";
			if (obj.getYear().length() > 0)
				queryBaoCaoTangCCDC += " 	AND YEAR(NGAYDIEUCHINH)<='" + obj.getYear() + "' \n ";
			if (obj.getMonth().length() > 0)
				queryBaoCaoTangCCDC += " AND  (YEAR(NGAYDIEUCHINH)<'" + obj.getYear() + "' OR (YEAR(NGAYDIEUCHINH)='" + obj.getYear() + "'	AND MONTH(NGAYDIEUCHINH)<'" + obj.getMonth() + "' ) )\n "+
			" 	GROUP BY CCDC_FK \n " + 
			" )THAYDOITRUOCKY ON THAYDOITRUOCKY.CCDC_FK= CCDC.PK_SEQ \n " +
//----------------------------------------------------------------------------------------------------------------			
			" WHERE CCDC.TRANGTHAI=1 and CCDC.congty_Fk="+CtyId+"\n" ;

		if(obj.getNhomCCDCId().length() >0)
			queryBaoCaoTangCCDC += " AND lccdc.PK_SEQ='"+obj.getNhomCCDCId()+"' \n";
		queryBaoCaoTangCCDC +=" order by CCDC.ma";
		return queryBaoCaoTangCCDC;
	}

	private String queryBaoCaoGiamCCDC(IErp_BaoCaoSuDungCCDC obj,String nppId,String CtyId) {
		String queryBaoCaoGiamCCDC = "";
		queryBaoCaoGiamCCDC = " SELECT lccdc.diengiai AS NHOMCCDC,CCDC.ma AS MACCDC,CCDC.DIENGIAI AS TENCCDC,ISNULL(dvth.TEN,'') AS TENDVTH, \n " + 
				" CCDC.THANGBATDAUKH AS NGAYBATDAUKH,CCDC.THANGBATDAUKH AS NGAYBATDAUSD,CCDC.sothangKH,  \n " + 
				" CCDC.THANHTIEN + ISNULL(THAYDOITRUOCKY.GIATRI,0) AS NGUYENGIA,ISNULL(THAYDOITRONGKY.GIATRI,0) AS GIATRIGIAM, \n " + 
				" CASE WHEN " +
				" THAYDOITRONGKY.LOAICHUNGTU='1' " +" THEN N'Đánh giá lại CPTT' " +
				" ELSE TL.GHICHU END AS LYDOGIAM, " +
				" TL.ngayhoadon AS NGAYTHANHLY, \n " + 
				" ISNULL(CCDC.GIATRIDAKHAUHAO,0) + ISNULL(KHAUHAO.KHAUHAOLUYKE,0) AS GIATRIDAKHAUHAO, \n " + 
				" ISNULL(CCDC.THANHTIEN,0) -ISNULL(CCDC.GIATRIDAKHAUHAO,0) - ISNULL(KHAUHAO.KHAUHAOLUYKE,0) AS GIATRICONLAI, \n " + 
				" CCDC.sothangdakhauhao\n " + 
				" FROM ERP_CONGCUDUNGCU CCDC  \n" +
				" LEFT JOIN \n"+
				" ( \n " + 
				" 	SELECT CCDC_FK AS CCDC_FK, SUM(GIATRI) AS GIATRI,LOAICHUNGTU \n " + 
				" 	FROM ERP_CONGCUDUNGCU_DIEUCHINH  \n " +
				"   where giatri<0";
				if (obj.getYear().length() > 0)
					queryBaoCaoGiamCCDC += " AND YEAR(NGAYDIEUCHINH) = " + obj.getYear() + " \n ";
				if (obj.getMonth().length() > 0)
					queryBaoCaoGiamCCDC += " AND MONTH(NGAYDIEUCHINH) = " + obj.getMonth() + " \n "+
				" 	GROUP BY CCDC_FK,LOAICHUNGTU \n " + 
				" )THAYDOITRONGKY ON THAYDOITRONGKY.CCDC_FK=CCDC.PK_SEQ \n"+ 
				" LEFT JOIN ERP_THANHLYTAISAN_TAISAN TL_TS ON TL_TS.CCDC_FK = CCDC.PK_SEQ \n " + 
				" LEFT JOIN ERP_THANHLYTAISAN TL ON TL.PK_SEQ= TL_TS.THANHLYTAISAN_FK \n " + 
				" left join Erp_LOAICCDC lccdc ON ccdc.loaiccdc_fk = lccdc.pk_seq \n " + 
				" LEFT JOIN ERP_donvithuchien dvth ON ccdc.dvth_fk=dvth.pk_seq \n " + 
				" LEFT JOIN  \n " + 
				" ( \n " + 
				" 	SELECT CCDC_FK, SUM(KHAUHAO)  AS KHAUHAOLUYKE \n " + 
				" 	FROM ERP_KHAUHAOCCDC \n " + 
				" 	WHERE KHAUHAO>0 \n " + 
				" 	GROUP BY CCDC_FK \n " + 
				" )KHAUHAO ON KHAUHAO.CCDC_FK=CCDC.pk_seq \n " + 
				" LEFT JOIN  \n " + 
				" ( \n " + 
				" 	SELECT CCDC_FK AS CCDC_FK, SUM(GIATRI) AS GIATRI \n " + 
				" 	FROM ERP_CONGCUDUNGCU_DIEUCHINH  \n " +
				"	WHERE CCDC_FK IS NOT NULL"; 
				if (obj.getYear().length() > 0)
					queryBaoCaoGiamCCDC += " 	AND YEAR(NGAYDIEUCHINH)<='" + obj.getYear() + "' \n ";
				if (obj.getMonth().length() > 0)
					queryBaoCaoGiamCCDC += " AND  (YEAR(NGAYDIEUCHINH)<'" + obj.getYear() + "' OR (YEAR(NGAYDIEUCHINH)='" + obj.getYear() + "'	AND MONTH(NGAYDIEUCHINH)<'" + obj.getMonth() + "' ) )\n "+
				" 	GROUP BY CCDC_FK \n " + 
				" )THAYDOITRUOCKY ON THAYDOITRUOCKY.CCDC_FK= CCDC.PK_SEQ \n " +
				" WHERE ISNULL(THAYDOITRONGKY.GIATRI,0)!=0 " +
				" or (" +
				"TL.TRANGTHAI in (1,2,3) " ;	
		if (obj.getYear().length() > 0)
			queryBaoCaoGiamCCDC += " AND YEAR(TL.NGAYHOADON) = " + obj.getYear() + "  \n ";
		if (obj.getMonth().length() > 0)
			queryBaoCaoGiamCCDC += " AND MONTH(TL.NGAYHOADON) = " + obj.getMonth() + "  \n ";
		queryBaoCaoGiamCCDC += "	 ) \n" ;
		if(obj.getNhomCCDCId().length() >0)
			queryBaoCaoGiamCCDC += " AND lccdc.PK_SEQ='"+obj.getNhomCCDCId()+"' \n";
		queryBaoCaoGiamCCDC +=" order by CCDC.ma";
		return queryBaoCaoGiamCCDC;
	}


	private String queryKhauHaoTheoDonVi(IErp_BaoCaoSuDungCCDC obj,String nppId,String CtyId) {
		String queryKhauHaoTheoDonVi = "";
		queryKhauHaoTheoDonVi = "SELECT   ROW_NUMBER() OVER (PARTITION BY CCDC.MA order by CCDC.MA) as STT , LCC.DIENGIAI AS NHOMCCDC,CCDC.MA AS MACCDC,CCDC.DIENGIAI AS TENCCDC,DVTH.MA + ' -' + DVTH.TEN AS DVTHTEN, \n" +
		"CCDC.SOLUONG,CCDC.DONVI,NCP.TEN + ' - ' + NCP.DIENGIAI AS NCPTEN,CCDC.THANGBATDAUKH,CCDC.SOTHANGKH,\n " +
		" ISNULL(CCDC.SOTHANGDAKHAUHAO,0)  + ISNULL(KHAUHAO_SOTHANG.SOTHANGKHAUHAO,0) AS SOTHANGDAKHAUHAO, \n "+
		"	ISNULL(TKKHAUHAO.SOHIEUTAIKHOAN,'') AS TKKHAUHAO,ISNULL(TK.SOHIEUTAIKHOAN,'') AS TKNHANCP," +
		"	 CCDC.ThanhTien +ISNULL(THAYDOIDAUKY.GIATRI,0) AS NGUYENGIADAUKY,\n" +
		" (ISNULL(KHAUHAODAUKY.KHAUHAOLUYKE,0) + ISNULL(CCDC.GIATRIDAKHAUHAO,0))* DC.PHANTRAM/100 AS KHAUHAODAUKY, \n "+
		"  isnull(NGUYENGIADAUKY.GIATRI,0) + ISNULL(THAYDOIDAUKY.GIATRI,0) -ISNULL(KHAUHAODAUKY.KHAUHAOLUYKE,0) - ISNULL(CCDC.GIATRIDAKHAUHAO,0) AS CONLAIDAUKY, \n "+
		" (ISNULL(KHAUHAOTRONGKY.KHAUHAOLUYKE,0))*DC.PHANTRAM/100 AS KHAUHAOTRONGKY, \n "+
		"ISNULL(THAYDOITRONGKY.GIATRI,0) AS THAYDOITRONGKY ,\n"+
		" CCDC.ThanhTien +ISNULL(THAYDOICUOIKY.GIATRI,0) AS NGUYENGIACUOIKY, \n "+
		" (ISNULL(KHAUHAOTRONGKY.KHAUHAOLUYKE,0) + ISNULL(KHAUHAODAUKY.KHAUHAOLUYKE,0) + ISNULL(CCDC.GIATRIDAKHAUHAO,0))*DC.PHANTRAM/100 AS KHAUHAOCUOIKY, \n "+
		"  isnull(NGUYENGIADAUKY.GIATRI,0) + ISNULL(THAYDOIDAUKY.GIATRI,0) -ISNULL(KHAUHAODAUKY.KHAUHAOLUYKE,0) - ISNULL(ccdc.GIATRIDAKHAUHAO,0) AS CONLAIDAUKY, \n "+
	    "   CCDC.ThanhTien  + ISNULL(THAYDOICUOIKY.GIATRI,0) - ISNULL(KHAUHAOTRONGKY.KHAUHAOLUYKE,0) - ISNULL(KHAUHAODAUKY.KHAUHAOLUYKE,0) - ISNULL(CCDC.GIATRIDAKHAUHAO,0) AS CONLAICUOIKY \n " +
		"	FROM ERP_CONGCUDUNGCU CCDC \n " +
		
		"	LEFT JOIN ERP_DONVITHUCHIEN DVTHQL ON DVTHQL.PK_SEQ=CCDC.DVTH_FK\n " +
		"	INNER JOIN Erp_LOAICCDC LCC ON LCC.pk_seq=CCDC.LOAICCDC_FK\n " 
		+ "LEFT JOIN \n" 
		+ " ("  
		+  "select DISTINCT  ISNULL(dcccdc.khoanmucchiphi_fk,kmcp.NHOMCHIPHI_FK) as KHOANMUCCHIPHI_FK,\n" 
							+ "ISNULL(DCCCDC.CCDC_FK,KMCP.CCDC_FK) as CCDC_FK,\n"
							+ "ISNULL(dcccdc.phantram,kmcp.phantram) as phantram,dc.NGAYCHUNGTU \n" 
							+ "from ERP_CONGCUDUNGCU ccdc \n"
							+ "left join ERP_CONGCUDUNGCU_KHOANMUCCHIPHI kmcp on ccdc.pk_seq=kmcp.CCDC_FK \n"
							+ " LEFT JOIN  \n" 
							+ "( \n"
							+ "SELECT ROW_NUMBER() OVER (PARTITION BY DC.CCDC_FK \n"
							+ "order by DC.NGAYCHUNGTU desc) AS STT,CCDC_FK,DC.NGAYCHUNGTU,PK_SEQ \n"
							+ " FROM  ERP_DIEUCHUYENCONGCUDUNGCU DC  \n"
							+ " ) dc ON dc.CCDC_FK= ccdc.PK_SEQ AND dc.STT=1 and month(dc.NGAYCHUNGTU)<='"+obj.getMonth()+"'  \n"
							+ "left join ERP_DIEUCHUYENCONGCUDUNGCU_KHOANMUCCHIPHI dcccdc on dcccdc.DIEUCHUYENCONGCUDUNGCU_FK=dc.PK_SEQ \n";
	
if (obj.getYear().length() > 0)
	queryKhauHaoTheoDonVi += " 	AND YEAR(DC.NGAYCHUNGTU)<='" + obj.getYear() + "' \n ";
if (obj.getMonth().length() > 0)
	queryKhauHaoTheoDonVi += " AND  (YEAR(DC.NGAYCHUNGTU)<'" + obj.getYear() + "' OR (YEAR(DC.NGAYCHUNGTU)='" + obj.getYear() + "'	AND MONTH(DC.NGAYCHUNGTU)<='" + obj.getMonth() + "' ) )\n "
		+" ) DC ON DC.CCDC_FK=CCDC.pk_seq \n"
		+ " INNER JOIN erp_nhomchiphi ncp ON DC.KHOANMUCCHIPHI_FK=ncp.PK_SEQ \n "
		+ " LEFT JOIN erp_donvithuchien dvth ON dvth.PK_SEQ=NCP.DONVITHUCHIEN_FK AND DC.PHANTRAM>0 \n "
		+ " INNER JOIN ERP_DONVITHUCHIEN DVTHSD ON NCP.DONVITHUCHIEN_FK=DVTHSD.PK_SEQ\n	"+
		"	LEFT JOIN ERP_TAIKHOANKT TKKHAUHAO ON TKKHAUHAO.PK_SEQ= LCC.taikhoan_fk\n " 
		+ " INNER JOIN ERP_TAIKHOANKT TK ON ncp.TaiKhoan_FK=TK.SOHIEUTAIKHOAN and tk.npp_fk=1 \n "
//		+ "	INNER JOIN ERP_NHOMKHOANMUC NKM ON NKM.PK_SEQ=NCP.NCP_FK \n" +
//-----------------------------------------------------------------------------------------------------------------------------
		// Khấu hao trong kỳ
		+"	LEFT JOIN \n " +
		"	(\n " +
		"	SELECT CCDC_FK, SUM(KHAUHAO)  AS KHAUHAOLUYKE\n " +
		"	FROM ERP_KHAUHAOCCDC\n " +
		"	WHERE KHAUHAO>0 ";
	if (obj.getYear().length() > 0)
			queryKhauHaoTheoDonVi += " 	AND NAM='" + obj.getYear() + "' \n ";
	if (obj.getMonth().length() > 0)
			queryKhauHaoTheoDonVi += " 	AND THANG='" + obj.getMonth() + "' \n ";
		queryKhauHaoTheoDonVi += 	"	GROUP BY CCDC_FK\n " +
		"	)KHAUHAOTRONGKY ON KHAUHAOTRONGKY.CCDC_FK=CCDC.pk_seq\n " +
//-------------------------------------------------------------------------------------------------------------------------------
		//Số tháng khấu hao
		"	LEFT JOIN \n " +
		"	(\n " +
		"	SELECT CCDC_FK, count(KHAUHAO)  AS SOTHANGKHAUHAO\n " +
		"	FROM ERP_KHAUHAOCCDC\n " +
		"	WHERE KHAUHAO>0 \n ";
		if (obj.getYear().length() > 0)
			queryKhauHaoTheoDonVi += " AND NAM <= '" + obj.getYear() + "' \n ";
		if (obj.getMonth().length() > 0)
			queryKhauHaoTheoDonVi += " AND  THANG<= '" + obj.getMonth() + "' \n ";
		queryKhauHaoTheoDonVi +=	"  GROUP BY CCDC_FK \n " +
		"	)KHAUHAO_SOTHANG ON KHAUHAO_SOTHANG.CCDC_FK=CCDC.pk_seq\n " +
//---------------------------------------------------------------------------------------------------------------------------------
		//Thay đổi đầu kỳ
		"	LEFT JOIN \n " +
		"	(\n " +
		"	SELECT CCDC_FK AS CCDC_FK, SUM(GIATRI) AS GIATRI,SUM (sothang) as sothang\n " +
		"	FROM ERP_CONGCUDUNGCU_DIEUCHINH \n " +
		"	WHERE 1=1 \n " ;
		if (obj.getYear().length() > 0)
			queryKhauHaoTheoDonVi += " AND ( (YEAR(ISNULL(NGAYDIEUCHINH,'2017-01-01'))<= '" + obj.getYear() + "' AND MONTH(NGAYDIEUCHINH)< '" + obj.getMonth() + "' ) \n ";
		if (obj.getMonth().length() > 0)
			queryKhauHaoTheoDonVi += " OR (YEAR(ISNULL(NGAYDIEUCHINH,'2017-01-01'))< '" + obj.getYear() + "' )) \n ";
	queryKhauHaoTheoDonVi +=	"	GROUP BY CCDC_FK\n " +
		"	)THAYDOIDAUKY ON THAYDOIDAUKY.CCDC_FK= CCDC.PK_SEQ\n " +
		
//------------------------------------------------------------------------------------------------------------------------------------
		 " LEFT JOIN  \n " + " ( \n "+
		 " 	SELECT CCDC_FK AS CCDC_FK, SUM(SOTHANG) AS SOTHANG, SUM(GIATRI) AS GIATRI \n "+
		 " 	FROM ERP_CONGCUDUNGCU_DIEUCHINH  \n " + " " +
		 "	WHERE 1=1 ";
if (obj.getYear().length() > 0)
	queryKhauHaoTheoDonVi += " AND YEAR(NGAYDIEUCHINH)= '" + obj.getYear() + "' \n ";
if (obj.getMonth().length() > 0)
	queryKhauHaoTheoDonVi += " AND MONTH(NGAYDIEUCHINH)= '" + obj.getMonth() + "' \n ";
queryKhauHaoTheoDonVi += " 	GROUP BY CCDC_FK \n " + " )THAYDOITRONGKY ON THAYDOITRONGKY.CCDC_FK= CCDC.PK_SEQ \n "	+
//-------------------------------------------------------------------------------------------------------------------------------------
		//Thay đổi cuối kỳ
		"	LEFT JOIN \n " +
		"	(\n " +
		"	SELECT CCDC_FK AS CCDC_FK, SUM(GIATRI) AS GIATRI\n " +
		"	FROM ERP_CONGCUDUNGCU_DIEUCHINH \n " +
		"	WHERE 1=1 \n " ;
if (obj.getYear().length() > 0)
	queryKhauHaoTheoDonVi += " AND ( (YEAR(ISNULL(NGAYDIEUCHINH,'2017-01-01'))<= '" + obj.getYear() + "' AND MONTH(NGAYDIEUCHINH)< '" + obj.getMonth() + "' ) \n ";
if (obj.getMonth().length() > 0)
	queryKhauHaoTheoDonVi += " OR (YEAR(ISNULL(NGAYDIEUCHINH,'2017-01-01'))< '" + obj.getYear() + "' )) \n ";
	queryKhauHaoTheoDonVi+=	"	GROUP BY CCDC_FK\n " +
		"	)THAYDOICUOIKY ON THAYDOICUOIKY.CCDC_FK= CCDC.PK_SEQ\n " +
//--------------------------------------------------------------------------------------------------------------------------------------
		
		 " LEFT JOIN  \n " + " ( \n "
		+ " 		SELECT pk_seq AS CCDC_FK, SUM(ThanhTien) AS GIATRI  \n "
		+ " 		FROM ERP_CONGCUDUNGCU    \n " + " 	WHERE 1=1 ";
if (obj.getYear().length() > 0)
	queryKhauHaoTheoDonVi += " AND ( (YEAR(ISNULL(THANGBATDAUKH,'2017-01-01'))<= '" + obj.getYear() + "' AND MONTH(THANGBATDAUKH)< '" + obj.getMonth() + "' ) \n ";
if (obj.getMonth().length() > 0)
	queryKhauHaoTheoDonVi += " OR (YEAR(ISNULL(THANGBATDAUKH,'2017-01-01'))< '" + obj.getYear() + "' )) \n ";
queryKhauHaoTheoDonVi += " 	GROUP BY pk_seq \n " + " )NGUYENGIADAUKY ON NGUYENGIADAUKY.CCDC_FK= CCDC.PK_SEQ \n "	
//---------------------------------------------------------------------------------------------------------------------
		//Khấu hao đầu kỳ
	+	"	LEFT JOIN \n " +
		"	(\n " +
		"	SELECT CCDC_FK, SUM(KHAUHAO)  AS KHAUHAOLUYKE,COUNT(KHAUHAO) AS SOTHANG\n " +
		"	FROM ERP_KHAUHAOCCDC\n " +
		"	WHERE KHAUHAO>0 \n " ;
	if (obj.getYear().length() > 0 && obj.getMonth().length() <= 0)
		queryKhauHaoTheoDonVi += " AND NAM< '" + obj.getYear() + "'" + " \n";
	if (obj.getYear().length() > 0 && obj.getMonth().length() > 0)
		queryKhauHaoTheoDonVi += " AND NAM <='" + obj.getYear() + "'" + " \n" + " AND THANG <'" + obj.getMonth() + "' ";
		queryKhauHaoTheoDonVi += 	"	GROUP BY CCDC_FK\n " +
		"	)KHAUHAODAUKY ON KHAUHAODAUKY.CCDC_FK=CCDC.pk_seq\n " +
//----------------------------------------------------------------------------------------------------------------------------------------
		//Số tháng khấu hao
		"	LEFT JOIN \n " +
		"	(\n " +
		"	SELECT CCDC_FK,COUNT(KHAUHAO) AS SOTHANG\n " +
		"	FROM ERP_KHAUHAOCCDC\n " +
		"	WHERE KHAUHAO>0 \n " ;
	if (obj.getYear().length() > 0 )
		queryKhauHaoTheoDonVi += " AND NAM <= '" + obj.getYear() + "'" + " \n";
	if (obj.getMonth().length() > 0)
		queryKhauHaoTheoDonVi += " AND THANG <='" + obj.getMonth() + "'" ;
	queryKhauHaoTheoDonVi+=	"	GROUP BY CCDC_FK\n " +
		")SOTHANGKH ON SOTHANGKH.CCDC_FK=CCDC.pk_seq\n " +
//---------------------------------------------------------------------------------------------------------------------------------------------	
	"WHERE CCDC.TRANGTHAI =1  \n " ;
	if(obj.getNhomCCDCId().length() >0)
		queryKhauHaoTheoDonVi += " AND LCC.PK_SEQ='"+obj.getNhomCCDCId()+"' \n";
	if(obj.getDvthId().length()>0)
		queryKhauHaoTheoDonVi += " AND CCDC.DVTH_FK='"+obj.getDvthId()+"' \n";
	if (obj.getYear().length() > 0&&obj.getMonth().length() > 0)
		queryKhauHaoTheoDonVi += " AND ( (YEAR(ISNULL(THANGBATDAUKH,'2017-01-01'))< '" + obj.getYear() + "' OR (YEAR(ISNULL(THANGBATDAUKH,'2017-01-01'))= '" + obj.getYear() + "' AND MONTH(THANGBATDAUKH)<= '" + obj.getMonth() + "' ))) \n ";

	
	queryKhauHaoTheoDonVi +=
		"ORDER BY LCC.diengiai,CCDC.MA";
		return queryKhauHaoTheoDonVi;
	}

	private String queryKhauHaoPhanBo(IErp_BaoCaoSuDungCCDC obj,String nppId,String CtyId) {
		String queryKhauHaoPhanBo = "";
		queryKhauHaoPhanBo = "SELECT  LCC.DIENGIAI AS NHOMCCDC,DVTH.TEN AS DVTHTEN,CCDC.MA AS MACCDC,CCDC.DIENGIAI AS TENCCDC,CCDC.SOLUONG,CCDC.DONVI,\n " +
		"	TKKHAUHAO.SOHIEUTAIKHOAN AS TKKHAUHAO,TKNHANCP.SOHIEUTAIKHOAN AS TKNHANCP," +
		"   CCDC.THANHTIEN AS NGUYENGIA, \n " +
		"	CCDC.THANGBATDAUKH AS NGAYBATDAUPB,CCDC.SOTHANGKH,CCDC.SOTHANGDAKHAUHAO +ISNULL(SOTHANGKH.SOTHANG,0) AS SOTHANGDAKHAUHAO,\n " +
		"	SUM(ISNULL(THAYDOIDAUKY.GIATRI,0)) + SUM(CCDC.THANHTIEN) -SUM(ISNULL(CCDC.GIATRIDAKHAUHAO,0)) - \n " +
		" 	SUM(ISNULL( KHAUHAODAUKY.KHAUHAOLUYKE,0)) AS CONLAIDAUKY,\n " +
		"	(SUM(ISNULL(KHAUHAOTRONGKY.KHAUHAOLUYKE,0)))*PB.PHANTRAM/100 AS KHAUHAOTRONGKY,\n " +
		"	SUM(ISNULL(THAYDOICUOIKY.GIATRI,0)) + SUM(CCDC.THANHTIEN) -(SUM(ISNULL(KHAUHAODAUKY.KHAUHAOLUYKE,0)) \n " +
		"	+ SUM(ISNULL(KHAUHAOTRONGKY.KHAUHAOLUYKE,0)) + SUM(ISNULL(CCDC.GIATRIDAKHAUHAO,0))) AS CONLAICUOIKY \n " +
		"	FROM Erp_CongCuDungCu_KhoanMucChiPhi PB \n " +
		"	LEFT JOIN ERP_NHOMCHIPHI NCP ON PB.NHOMCHIPHI_FK=NCP.PK_SEQ\n " +
		"	LEFT JOIN ERP_DONVITHUCHIEN DVTH ON DVTH.PK_SEQ=NCP.DONVITHUCHIEN_FK" +
		"	LEFT JOIN ERP_CONGCUDUNGCU CCDC ON CCDC.pk_seq= PB.CCDC_FK\n " +
		"	LEFT JOIN Erp_LOAICCDC LCC ON LCC.pk_seq=CCDC.LOAICCDC_FK\n " +
		"	LEFT JOIN ERP_TAIKHOANKT TKKHAUHAO ON TKKHAUHAO.SOHIEUTAIKHOAN= LCC.taikhoan_fk and TKKHAUHAO.npp_fk=1 \n " +
		"	LEFT JOIN ERP_TAIKHOANKT TKNHANCP on TKNHANCP.SOHIEUTAIKHOAN= NCP.TaiKhoan_FK and TKNHANCP.npp_fk=1 \n " +
		"	LEFT JOIN \n " +
		"	(\n " +
		"	SELECT CCDC_FK, SUM(KHAUHAO)  AS KHAUHAOLUYKE\n " +
		"	FROM ERP_KHAUHAOCCDC\n " +
		"	WHERE KHAUHAO>0 ";
	if (obj.getYear().length() > 0)
		queryKhauHaoPhanBo += " 	AND NAM='" + obj.getYear() + "' \n ";
	if (obj.getMonth().length() > 0)
		queryKhauHaoPhanBo += " 	AND THANG='" + obj.getMonth() + "' \n ";
	queryKhauHaoPhanBo += 	"	GROUP BY CCDC_FK\n " +
		"	)KHAUHAOTRONGKY ON KHAUHAOTRONGKY.CCDC_FK=CCDC.pk_seq\n " +
		"	LEFT JOIN \n " +
		"	(\n " +
		"	SELECT CCDC_FK,COUNT(KHAUHAO) AS SOTHANG\n " +
		"	FROM ERP_KHAUHAOCCDC\n " +
		"	WHERE KHAUHAO>0 \n " ;
	if (obj.getYear().length() > 0 && obj.getMonth().length() <= 0)
		queryKhauHaoPhanBo += " AND NAM<= '" + obj.getYear() + "'" + " \n";
	if (obj.getYear().length() > 0 && obj.getMonth().length() > 0)
		queryKhauHaoPhanBo += " AND NAM <='" + obj.getYear() + "'" + " \n" + " AND THANG <'" + obj.getMonth() + "'" ;
	queryKhauHaoPhanBo+=	"	GROUP BY CCDC_FK\n " +
		"	)KHAUHAO_SOTHANG ON KHAUHAO_SOTHANG.CCDC_FK=CCDC.pk_seq\n " +
		"	LEFT JOIN \n " +
		"	(\n " +
		"	SELECT CCDC_FK AS CCDC_FK, SUM(GIATRI) AS GIATRI\n " +
		"	FROM ERP_CONGCUDUNGCU_DIEUCHINH \n " +
		"	WHERE 1=1 \n " ;
	if (obj.getYear().length() > 0)
		queryKhauHaoPhanBo += " AND ( (YEAR(ISNULL(NGAYDIEUCHINH,'2017-01-01'))<= '" + obj.getYear() + "' AND MONTH(NGAYDIEUCHINH)< '" + obj.getMonth() + "' ) \n ";
	if (obj.getMonth().length() > 0)
		queryKhauHaoPhanBo += " OR (YEAR(ISNULL(NGAYDIEUCHINH,'2017-01-01'))< '" + obj.getYear() + "' )) \n ";
	queryKhauHaoPhanBo +=	"	GROUP BY CCDC_FK\n " +
		"	)THAYDOIDAUKY ON THAYDOIDAUKY.CCDC_FK= CCDC.PK_SEQ\n " +
		"	LEFT JOIN \n " +
		"	(\n " +
		"	SELECT CCDC_FK AS CCDC_FK, SUM(GIATRI) AS GIATRI\n " +
		"	FROM ERP_CONGCUDUNGCU_DIEUCHINH \n " +
		"	WHERE 1=1 \n " ;
	if (obj.getYear().length() > 0)
		queryKhauHaoPhanBo += " AND ( (YEAR(ISNULL(NGAYDIEUCHINH,'2017-01-01'))<= '" + obj.getYear() + "' AND MONTH(NGAYDIEUCHINH)< '" + obj.getMonth() + "' ) \n ";
	if (obj.getMonth().length() > 0)
		queryKhauHaoPhanBo += " OR (YEAR(ISNULL(NGAYDIEUCHINH,'2017-01-01'))< '" + obj.getYear() + "' )) \n ";
	queryKhauHaoPhanBo+=	"	GROUP BY CCDC_FK\n " +
		"	)THAYDOICUOIKY ON THAYDOIDAUKY.CCDC_FK= CCDC.PK_SEQ\n " +
		"	LEFT JOIN \n " +
		"	(\n " +
		"	SELECT CCDC_FK, SUM(KHAUHAO)  AS KHAUHAOLUYKE,COUNT(KHAUHAO) AS SOTHANG\n " +
		"	FROM ERP_KHAUHAOCCDC\n " +
		"	WHERE KHAUHAO>0 \n " ;
	if (obj.getYear().length() > 0 && obj.getMonth().length() <= 0)
		queryKhauHaoPhanBo += " AND NAM< '" + obj.getYear() + "'" + " \n";
	if (obj.getYear().length() > 0 && obj.getMonth().length() > 0)
		queryKhauHaoPhanBo += " AND NAM <='" + obj.getYear() + "'" + " \n" + " AND THANG <'" + obj.getMonth() + "' ";
	queryKhauHaoPhanBo += 	"	GROUP BY CCDC_FK\n " +
		"	)KHAUHAODAUKY ON KHAUHAODAUKY.CCDC_FK=CCDC.pk_seq\n " +
		"	LEFT JOIN \n " +
		"	(\n " +
		"	SELECT CCDC_FK,COUNT(KHAUHAO) AS SOTHANG\n " +
		"	FROM ERP_KHAUHAOCCDC\n " +
		"	WHERE KHAUHAO>0 \n " ;
	if (obj.getYear().length() > 0 )
		queryKhauHaoPhanBo += " AND NAM <= '" + obj.getYear() + "'" + " \n";
	if (obj.getMonth().length() > 0)
		queryKhauHaoPhanBo += " AND THANG <='" + obj.getMonth() + "'" ;
	queryKhauHaoPhanBo+=	"	GROUP BY CCDC_FK\n " +
		")SOTHANGKH ON SOTHANGKH.CCDC_FK=CCDC.pk_seq\n " +
		"WHERE CCDC.TRANGTHAI =1  and ccdc.congty_fk="+CtyId ;
	if(obj.getNhomCCDCId().length() >0)
		queryKhauHaoPhanBo += " AND LCC.PK_SEQ='"+obj.getNhomCCDCId()+"' \n";		
	
	if (obj.getYear().length() > 0&&obj.getMonth().length() > 0)
		queryKhauHaoPhanBo += " AND ( (YEAR(ISNULL(THANGBATDAUKH,'2017-01-01'))< '" + obj.getYear() + "' OR (YEAR(ISNULL(THANGBATDAUKH,'2017-01-01'))= '" + obj.getYear() + "' AND MONTH(THANGBATDAUKH)<= '" + obj.getMonth() + "' ))) \n ";
	
	queryKhauHaoPhanBo += " GROUP BY lCC.diengiai,DVTH.TEN,PB.PHANTRAM,TKKHAUHAO.SOHIEUTAIKHOAN,TKNHANCP.SOHIEUTAIKHOAN,CCDC.SOLUONG,CCDC.DONVI,\n " +
		" CCDC.SOTHANGKH,CCDC.SOTHANGDAKHAUHAO,KHAUHAODAUKY.SOTHANG,SOTHANGKH.SOTHANG \n " +
		",CCDC.MA,CCDC.DIENGIAI,CCDC.THANGBATDAUKH,CCDC.THANHTIEN\n " +
		"ORDER BY lCC.diengiai,CCDC.MA ";
		return queryKhauHaoPhanBo;
	}


}
