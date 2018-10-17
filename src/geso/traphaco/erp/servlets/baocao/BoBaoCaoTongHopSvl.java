package geso.traphaco.erp.servlets.baocao;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.bobaocaotonghop.IBoBaoCaoTongHop;
import geso.traphaco.erp.beans.bobaocaotonghop.imp.BoBaoCaoTongHop;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

public class BoBaoCaoTongHopSvl extends HttpServlet {
		     
    public BoBaoCaoTongHopSvl() {
        super();
        
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		Utility Ult = new Utility();
		HttpSession session = request.getSession();

		String querystring = request.getQueryString();
		String userId = Ult.getUserId(querystring);
		
		IBoBaoCaoTongHop obj = new BoBaoCaoTongHop();
		obj.setuserId(userId);
		
		String ctyId = (String)session.getAttribute("congtyId");
		if(ctyId == null) ctyId = "";
		obj.setCtyId(ctyId);
		obj.setErpCongtyId(ctyId);
		obj.createRs();
		
		
		String view = Ult.antiSQLInspection(request.getParameter("view"));
		System.out.println("View"+ view);
		if(view == null) view = "";
		
		obj.setView(view);
		
		session.setAttribute("obj", obj);

		String nextJSP = "/TraphacoHYERP/pages/Erp/BoBaoCaoTongHop.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Utility util=new Utility();
		   
		HttpSession session = request.getSession();
		
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session.getAttribute("util");

		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		}else{
		
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");

			OutputStream out = response.getOutputStream();
			IBoBaoCaoTongHop obj = new BoBaoCaoTongHop();
			
			String ctyId = (String)session.getAttribute("congtyId");
			obj.setuserId(userId);		
			obj.setCtyId(ctyId);
			obj.setErpCongtyId(ctyId);
			obj.createRs();
			obj.setView(util.antiSQLInspection(request.getParameter("view")));

			String year = util.antiSQLInspection(request.getParameter("year"));
			if (year == null)
				year = "";
			obj.setYear(year);

			String month = util.antiSQLInspection(request.getParameter("month"));
			if (month == null)
				month = "";
			obj.setMonth(month);
			
			String tieuChiLocBC = util.antiSQLInspection(request.getParameter("tieuChiLocBC"));
			if (tieuChiLocBC == null)
				tieuChiLocBC = "";
			obj.setTieuChiLayBC(tieuChiLocBC);
			
			String tungay = util.antiSQLInspection(request.getParameter("tungay"));
			if (tungay == null)
				tungay = "";
			obj.setTuNgay(tungay);
			System.out.println(""+tungay);

			String denngay = util.antiSQLInspection(request.getParameter("denngay"));
			if (denngay == null)
				denngay = "";
			obj.setDenNgay(denngay);
			
			String nppId = util.antiSQLInspection(request.getParameter("nppId"));
			if(nppId == null)
				nppId= "";
			obj.setNppId(nppId);
			String action = request.getParameter("action");
			String nextJSP = "/TraphacoHYERP/pages/Erp/BoBaoCaoTongHop.jsp";
			// chọn công ty
			if (action.equals("change")){
				session.setAttribute("obj", obj);
				response.sendRedirect(nextJSP);
				return;
			}
			
			//lấy báo cáo 
		
			
			
		
			

			if (action.equals("tao")) {
				try {
					obj.init();
					response.setContentType("application/xlsm");
					response.setHeader("Content-Disposition", "attachment; filename=BaoCaoTaiChinh_theo_TT200.xlsm");
					CreateReport(out, obj);
					obj.DBClose();
				} catch (Exception ex) {	
					ex.printStackTrace();
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
	}

	private void CreateReport(OutputStream out, IBoBaoCaoTongHop obj)throws Exception {

		try{
			String file = getServletContext().getInitParameter("pathTemplate") + "\\BaoCaoTaiChinh_theo_TT200.xlsm";
			
			System.out.println(file);
			
			FileInputStream fstream = new FileInputStream(file);
			
			
			Workbook workbook = new Workbook();
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
		
			
			Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet("CDSPS");
			//worksheet.setName("CDSPS");

			Cells cells = worksheet.getCells();

			Style style;		
			Font font = new Font();
			font.setColor(Color.NAVY);
			font.setSize(10);
			font.setBold(true);

			cells.setColumnWidth(0, 1.4f);
			cells.setColumnWidth(1, 10.14f);
			cells.setColumnWidth(2, 52.4f);
			cells.setColumnWidth(3, 15.5f);
			cells.setColumnWidth(4, 15.5f);
			cells.setColumnWidth(5, 15.5f);
			cells.setColumnWidth(6, 15.5f);
			cells.setColumnWidth(7, 15.5f);
			cells.setColumnWidth(8, 15.5f);
			
			Cell cell = cells.getCell("A1");			
			cell.setValue(""); 

			cells.setRowHeight(0, 20.0f);
			cell = cells.getCell("B1");			
			cell.setValue("Đơn vị: " + obj.getCtyTen());
			System.out.println("Đơn vị: " + obj.getCtyTen());
			style = cell.getStyle();
			style.setFont(font);
			style.setHAlignment(TextAlignmentType.LEFT);
			cell.setStyle(style);

			if(!obj.getView().equals("TT")){
				cells.setRowHeight(1, 20.0f);
				cell = cells.getCell("B2");
				cell.setValue("Địa chỉ: " + obj.getDiachi());
				style = cell.getStyle();
				style.setFont(font);
				style.setHAlignment(TextAlignmentType.LEFT);
				cell.setStyle(style);
			
				cells.setRowHeight(2, 20.0f);
				cell = cells.getCell("B3");
				cell.setValue("Mã số thuế: " + obj.getMasothue()); 
				style = cell.getStyle();
				style.setFont(font);
				style.setHAlignment(TextAlignmentType.LEFT);
				cell.setStyle(style);
			
			}else{
				cells.setRowHeight(1, 20.0f);
				cell = cells.getCell("B2");
				cell.setValue("");
				style = cell.getStyle();
				style.setFont(font);
				style.setHAlignment(TextAlignmentType.LEFT);
				cell.setStyle(style);
			
				cells.setRowHeight(2, 20.0f);
				cell = cells.getCell("B3");
				cell.setValue(""); 
				style = cell.getStyle();
				style.setFont(font);
				style.setHAlignment(TextAlignmentType.LEFT);
				cell.setStyle(style);
				
			}
// CDPS			
			cells.merge(6, 1, 6, 8);
			cell = cells.getCell("B7");
			font.setColor(Color.MAGENTA);
			cell.setValue("BẢNG CÂN ĐỐi SỐ PHÁT SINH");			
			font.setSize(16);
			font.setBold(true);
			style.setFont(font);
			style.setHAlignment(TextAlignmentType.CENTER);
			cell.setStyle(style);
			
			cells.merge(7, 1, 7, 8);
			cell = cells.getCell("B8");
			font.setColor(Color.NAVY);
			cell.setValue("Tháng " + obj.getMonth() + " Năm " + obj.getYear());			
			font.setSize(10);
			font.setBold(true);
			style.setFont(font);
			style.setHAlignment(TextAlignmentType.CENTER);
			cell.setStyle(style);
			
			cell = cells.getCell("I9");
			font.setColor(Color.NAVY);
			cell.setValue("Đơn vị: VNĐ");			
			font.setSize(10);
			font.setBold(true);
			style.setFont(font);
			style.setHAlignment(TextAlignmentType.CENTER);
			cell.setStyle(style);

			cells.merge(9, 1, 10, 1);
			cell = cells.getCell("B10");
			font.setColor(Color.NAVY);
			cell.setValue("Số hiệu tài khoản");
			font.setSize(10);
			font.setBold(true);		
			style.setFont(font);
			style.setHAlignment(TextAlignmentType.CENTER);
			style.setTextWrapped(true);
			cell.setStyle(style);
			cell = CreateBorderSetting(cell);
			cell = CreateBorderSetting(cells.getCell("B11"));
			
			cells.merge(9, 2, 10, 2);
			cell = cells.getCell("C10");
			font.setColor(Color.NAVY);
			cell.setValue("Tên tài khoản");			
			font.setSize(10);
			font.setBold(true);
			style.setFont(font);
			style.setHAlignment(TextAlignmentType.CENTER);
			cell.setStyle(style);
			cell = CreateBorderSetting(cell);
			cell = CreateBorderSetting(cells.getCell("C11"));
			
			cells.merge(9, 3, 9, 4);
			cell = cells.getCell("D10");
			font.setColor(Color.NAVY);
			cell.setValue("Số dư đầu kỳ");			
			font.setSize(10);
			font.setBold(true);
			style.setFont(font);
			style.setHAlignment(TextAlignmentType.CENTER);
			cell.setStyle(style);
			cell = CreateBorderSetting(cell);
			cell = CreateBorderSetting(cells.getCell("E10"));
			
			cells.merge(9, 5, 9, 6);
			cell = cells.getCell("F10");
			font.setColor(Color.NAVY);
			cell.setValue("Phát sinh trong kỳ");			
			font.setSize(10);
			font.setBold(true);
			style.setFont(font);
			style.setHAlignment(TextAlignmentType.CENTER);
			cell.setStyle(style);
			cell = CreateBorderSetting(cell);
			cell = CreateBorderSetting(cells.getCell("G10"));
			
			cells.merge(9, 7, 9, 8);
			cell = cells.getCell("H10");
			font.setColor(Color.NAVY);
			cell.setValue("Số dư cuối kỳ");			
			font.setSize(10);
			font.setBold(true);
			style.setFont(font);
			style.setHAlignment(TextAlignmentType.CENTER);
			cell.setStyle(style);
			cell = CreateBorderSetting(cell);
			cell = CreateBorderSetting(cells.getCell("I10"));
			
			cell = cells.getCell("D11");
			font.setColor(Color.NAVY);
			cell.setValue("Nợ");			
			font.setSize(10);
			font.setBold(true);
			style.setFont(font);
			style.setHAlignment(TextAlignmentType.CENTER);
			cell.setStyle(style);
			cell = CreateBorderSetting(cell);			
			
			cell = cells.getCell("E11");
			font.setColor(Color.NAVY);
			cell.setValue("Có");			
			font.setSize(10);
			font.setBold(true);
			style.setFont(font);
			style.setHAlignment(TextAlignmentType.CENTER);
			cell.setStyle(style);
			cell = CreateBorderSetting(cell);

			cell = cells.getCell("F11");
			font.setColor(Color.NAVY);
			cell.setValue("Nợ");			
			font.setSize(10);
			font.setBold(true);
			style.setFont(font);
			style.setHAlignment(TextAlignmentType.CENTER);
			cell.setStyle(style);
			cell = CreateBorderSetting(cell);

			cell = cells.getCell("G11");
			font.setColor(Color.NAVY);
			cell.setValue("Có");			
			font.setSize(10);
			font.setBold(true);
			style.setFont(font);
			style.setHAlignment(TextAlignmentType.CENTER);
			cell.setStyle(style);
			cell = CreateBorderSetting(cell);

			cell = cells.getCell("H11");
			font.setColor(Color.NAVY);
			cell.setValue("Nợ");			
			font.setSize(10);
			font.setBold(true);
			style.setFont(font);
			style.setHAlignment(TextAlignmentType.CENTER);
			cell.setStyle(style);
			cell = CreateBorderSetting(cell);

			cell = cells.getCell("I11");
			font.setColor(Color.NAVY);
			cell.setValue("Có");			
			font.setSize(10);
			font.setBold(true);
			style.setFont(font);
			style.setHAlignment(TextAlignmentType.CENTER);
			cell.setStyle(style);
			cell = CreateBorderSetting(cell);

			cell = cells.getCell("B12");
			font.setColor(Color.NAVY);
			cell.setValue("A");			
			font.setSize(10);
			font.setBold(true);
			style.setFont(font);
			style.setHAlignment(TextAlignmentType.CENTER);
			cell.setStyle(style);
			cell = CreateBorderSetting(cell);

			cell = cells.getCell("C12");
			font.setColor(Color.NAVY);
			cell.setValue("B");			
			font.setSize(10);
			font.setBold(true);
			style.setFont(font);
			style.setHAlignment(TextAlignmentType.CENTER);
			cell.setStyle(style);
			cell = CreateBorderSetting(cell);

			cell = cells.getCell("D12");
			font.setColor(Color.NAVY);
			cell.setValue(1);			
			font.setSize(10);
			font.setBold(true);
			style.setFont(font);
			style.setHAlignment(TextAlignmentType.CENTER);
			cell.setStyle(style);
			cell = CreateBorderSetting(cell);

			cell = cells.getCell("E12");
			font.setColor(Color.NAVY);
			cell.setValue(2);			
			font.setSize(10);
			font.setBold(true);
			style.setFont(font);
			style.setHAlignment(TextAlignmentType.CENTER);
			cell.setStyle(style);
			cell = CreateBorderSetting(cell);

			cell = cells.getCell("F12");
			font.setColor(Color.NAVY);
			cell.setValue(3);			
			font.setSize(10);
			font.setBold(true);
			style.setFont(font);
			style.setHAlignment(TextAlignmentType.CENTER);
			cell.setStyle(style);
			cell = CreateBorderSetting(cell);

			cell = cells.getCell("G12");
			font.setColor(Color.NAVY);
			cell.setValue(4);			
			font.setSize(10);
			font.setBold(true);
			style.setFont(font);
			style.setHAlignment(TextAlignmentType.CENTER);
			cell.setStyle(style);
			cell = CreateBorderSetting(cell);

			cell = cells.getCell("H12");
			font.setColor(Color.NAVY);
			cell.setValue(5);			
			font.setSize(10);
			font.setBold(true);
			style.setFont(font);
			style.setHAlignment(TextAlignmentType.CENTER);
			cell.setStyle(style);
			cell = CreateBorderSetting(cell);

			cell = cells.getCell("I12");
			font.setColor(Color.NAVY);
			cell.setValue(6);			
			font.setSize(10);
			font.setBold(true);
			style.setFont(font);
			style.setHAlignment(TextAlignmentType.CENTER);
			cell.setStyle(style);
			cell = CreateBorderSetting(cell);
			
			ResultSet rs = obj.getRsCDPS();
			int row = 13;
			double daukyno = 0;
			double daukyco = 0;
			double phatsinhno = 0;
			double phatsinhco = 0;
			double cuoikyno = 0;
			double cuoikyco = 0;
			while(rs.next()){
				cell = cells.getCell("B" + row);
				font.setColor(Color.BLACK);
				cell.setValue(rs.getDouble("SOHIEUTAIKHOAN"));			
				font.setSize(10);
				font.setBold(false);
				style.setFont(font);
				style.setNumber(0);
				style.setHAlignment(TextAlignmentType.LEFT);
				cell.setStyle(style);
				cell = CreateBorderSetting2(cell);
				
				cell = cells.getCell("C" + row);
				font.setColor(Color.BLACK);
				cell.setValue(rs.getString("TENTAIKHOAN"));			
				font.setSize(10);
				font.setBold(false);
				style.setFont(font);
				style.setNumber(0);
				style.setHAlignment(TextAlignmentType.LEFT);
				cell.setStyle(style);
				cell = CreateBorderSetting2(cell);

				cell = cells.getCell("D" + row);
				font.setColor(Color.BLACK);
				cell.setValue(rs.getDouble("DAUKYNO"));				
				font.setSize(10);
				font.setBold(false);
				style.setFont(font);
				style.setNumber(3);
				style.setHAlignment(TextAlignmentType.RIGHT);
				cell.setStyle(style);
				cell = CreateBorderSetting2(cell);

				daukyno = daukyno + rs.getDouble("DAUKYNO");

				cell = cells.getCell("E" + row);
				font.setColor(Color.BLACK);
				cell.setValue(rs.getDouble("DAUKYCO"));			
				font.setSize(10);
				font.setBold(false);
				style.setFont(font);
				style.setNumber(3);
				style.setHAlignment(TextAlignmentType.RIGHT);
				cell.setStyle(style);
				cell = CreateBorderSetting2(cell);
				
				daukyco = daukyco + rs.getDouble("DAUKYCO");
				
				cell = cells.getCell("F" + row);
				font.setColor(Color.BLACK);
				cell.setValue(rs.getDouble("PHATSINHNOVND"));			
				font.setSize(10);
				font.setBold(false);
				style.setFont(font);
				style.setNumber(3);
				style.setHAlignment(TextAlignmentType.RIGHT);
				cell.setStyle(style);
				cell = CreateBorderSetting2(cell);
				
				phatsinhno = phatsinhno + rs.getDouble("PHATSINHNOVND");
				
				cell = cells.getCell("G" + row);
				font.setColor(Color.BLACK);
				cell.setValue(rs.getDouble("PHATSINHCOVND"));			
				font.setSize(10);
				font.setBold(false);
				style.setFont(font);
				style.setNumber(3);
				style.setHAlignment(TextAlignmentType.RIGHT);
				cell.setStyle(style);
				cell = CreateBorderSetting2(cell);
				
				phatsinhco = phatsinhco + rs.getDouble("PHATSINHCOVND");
				
				//Cuối kỳ nợ
				cell = cells.getCell("H" + row);
				font.setColor(Color.BLACK);
				cell.setValue(rs.getDouble("CUOIKYNO"));
				font.setSize(10);
				font.setBold(false);
				style.setFont(font);
				style.setNumber(3);
				style.setHAlignment(TextAlignmentType.RIGHT);
				cell.setStyle(style);
				cell = CreateBorderSetting2(cell);
				cuoikyno = cuoikyno + rs.getDouble("CUOIKYNO");
				
				//Cuối kỳ có 
				cell = cells.getCell("I" + row);
				font.setColor(Color.BLACK);
				cell.setValue(rs.getDouble("CUOIKYCO"));
				font.setSize(10);
				font.setBold(false);
				style.setFont(font);
				style.setNumber(3);
				style.setHAlignment(TextAlignmentType.RIGHT);
				cell.setStyle(style);
				cell = CreateBorderSetting2(cell);
				cuoikyco = cuoikyco + rs.getDouble("CUOIKYCO");
				
				
				row ++;
			}
			
			cell = cells.getCell("B" + row);
			font.setColor(Color.RED);
			cell.setValue("");
			font.setSize(10);
			font.setBold(true);
			style.setFont(font);
			style.setPatternColor(Color.CYAN);
			style.setHAlignment(TextAlignmentType.RIGHT);			
			cell.setStyle(style);
			cell = CreateBorderSetting(cell);
			
			cell = cells.getCell("C" + row);
			font.setColor(Color.RED);
			cell.setValue("Tổng cộng");
			font.setSize(10);
			font.setBold(true);
			style.setFont(font);
			style.setPatternColor(Color.CYAN);
			style.setHAlignment(TextAlignmentType.CENTER);			
			cell.setStyle(style);
			cell = CreateBorderSetting(cell);

			cell = cells.getCell("D" + row);
			font.setColor(Color.RED);
			cell.setValue(daukyno);
			font.setSize(10);
			font.setBold(true);
			style.setFont(font);
			style.setPatternColor(Color.CYAN);
			style.setHAlignment(TextAlignmentType.RIGHT);			
			cell.setStyle(style);
			cell = CreateBorderSetting(cell);

			cell = cells.getCell("E" + row);
			font.setColor(Color.RED);
			cell.setValue(daukyco);
			font.setSize(10);
			font.setBold(true);
			style.setFont(font);
			style.setPatternColor(Color.CYAN);
			style.setHAlignment(TextAlignmentType.RIGHT);			
			cell.setStyle(style);
			cell = CreateBorderSetting(cell);

			cell = cells.getCell("F" + row);
			font.setColor(Color.RED);
			cell.setValue(phatsinhno);
			font.setSize(10);
			font.setBold(true);
			style.setFont(font);
			style.setPatternColor(Color.CYAN);
			style.setHAlignment(TextAlignmentType.RIGHT);			
			cell.setStyle(style);
			cell = CreateBorderSetting(cell);

			cell = cells.getCell("G" + row);
			font.setColor(Color.RED);
			cell.setValue(phatsinhco);
			font.setSize(10);
			font.setBold(true);
			style.setFont(font);
			style.setPatternColor(Color.CYAN);
			style.setHAlignment(TextAlignmentType.RIGHT);			
			cell.setStyle(style);
			cell = CreateBorderSetting(cell);

			cell = cells.getCell("H" + row);
			font.setColor(Color.RED);
			cell.setValue(cuoikyno);
			font.setSize(10);
			font.setBold(true);
			style.setFont(font);
			style.setPatternColor(Color.CYAN);
			style.setHAlignment(TextAlignmentType.RIGHT);			
			cell.setStyle(style);
			cell = CreateBorderSetting(cell);

			cell = cells.getCell("I" + row);
			font.setColor(Color.RED);
			cell.setValue(cuoikyco);
			font.setSize(10);
			font.setBold(true);
			style.setFont(font);
			style.setPatternColor(Color.CYAN);
			style.setPatternStyle(Short.parseShort("0"));
			style.setHAlignment(TextAlignmentType.RIGHT);			
			cell.setStyle(style);
			cell = CreateBorderSetting(cell);

			row = row + 2;
			cells.merge(row - 1, 6, row - 1, 8);
			cell = cells.getCell("G" + row );
			font.setColor(Color.BLACK);
			cell.setValue("Ngày " + obj.getDate().substring(8, 10) + " tháng " + obj.getDate().substring(5, 7) + " năm " + obj.getDate().substring(0, 4) + "");
			font.setSize(10);
			font.setItalic(true);
			font.setBold(false);
			style.setFont(font);
			style.setHAlignment(TextAlignmentType.CENTER);			
			cell.setStyle(style);

			row = row + 1;
			cell = cells.getCell("C" + row );
			font.setColor(Color.BLACK);
			cell.setValue("Người lập");
			font.setSize(10);
			font.setBold(true);
			font.setItalic(false);
			style.setFont(font);
			style.setHAlignment(TextAlignmentType.CENTER);			
			cell.setStyle(style);

			cells.merge(row - 1, 6, row - 1, 8);
			cell = cells.getCell("G" + row );
			font.setColor(Color.BLACK);
			cell.setValue("Giám đốc");
			font.setSize(10);
			font.setBold(true);
			style.setFont(font);
			style.setHAlignment(TextAlignmentType.CENTER);			
			cell.setStyle(style);

			row = row + 1;
			cell = cells.getCell("C" + row );
			font.setColor(Color.BLACK);
			cell.setValue("(Ký, họ tên)");
			font.setSize(10);
			font.setItalic(true);
			font.setBold(false);
			style.setFont(font);
			style.setHAlignment(TextAlignmentType.CENTER);			
			cell.setStyle(style);

			cells.merge(row - 1, 6, row - 1, 8);
			cell = cells.getCell("G" + row );
			font.setColor(Color.BLACK);
			cell.setValue("(Ký, họ tên)");
			font.setSize(10);
			font.setItalic(true);
			font.setBold(false);
			style.setFont(font);
			style.setHAlignment(TextAlignmentType.CENTER);			
			cell.setStyle(style);

			row = row + 8;
			cell = cells.getCell("C" + row );
			font.setColor(Color.BLACK);
			cell.setValue("");
			font.setSize(10);
			font.setItalic(false);
			font.setBold(true);
			style.setFont(font);
			style.setHAlignment(TextAlignmentType.CENTER);			
			cell.setStyle(style);

			cells.merge(row - 1, 6, row - 1, 8);
			cell = cells.getCell("G" + row );
			font.setColor(Color.BLACK);
			cell.setValue("");
			font.setSize(10);
			font.setItalic(false);
			font.setBold(true);
			style.setFont(font);
			style.setHAlignment(TextAlignmentType.CENTER);			
			cell.setStyle(style);
			
					
			//Bat dau sheet CDKT
			worksheet = worksheets.getSheet(1);
			cells = worksheet.getCells();			

			cell = cells.getCell("A1");			
			cell.setValue("Đơn vị: " + obj.getCtyTen());
			style = cell.getStyle();
			cell.setStyle(style);

			if(!obj.getView().equals("TT")){

				cell = cells.getCell("A2");
				cell.setValue("Địa chỉ: " + obj.getDiachi()); 
				style = cell.getStyle();
				cell.setStyle(style);
			
				cell = cells.getCell("A3");
				cell.setValue("Mã số thuế: " + obj.getMasothue()); 
				style = cell.getStyle();
				cell.setStyle(style);
			}else{
				cell = cells.getCell("A2");
				cell.setValue(""); 
				style = cell.getStyle();
				cell.setStyle(style);
			
				cell = cells.getCell("A3");
				cell.setValue(""); 
				style = cell.getStyle();
				cell.setStyle(style);				
			}
			cell = cells.getCell("A7");
			cell.setValue("Tháng: " + obj.getMonth() + " năm: " + obj.getYear());			
			style = cell.getStyle();
			cell.setStyle(style);
			
			rs = obj.getRsCDKT();
			row = 11;
			while(rs.next()){
				cell = cells.getCell("D" + row);
				style = cell.getStyle();
				style.setNumber(41);
				cell.setStyle(style);
				cell.setValue(rs.getDouble("CUOIKYNAY"));
	

				
				cell = cells.getCell("E" + row);
				style = cell.getStyle();
				style.setNumber(41);
				cell.setStyle(style);
				cell.setValue(rs.getDouble("CUOIKYTRUOC"));				
				
				row ++;
			}
			//ket qua hoat dong kinh doanh
			worksheet = worksheets.getSheet(2);
			cells = worksheet.getCells();

			cell = cells.getCell("A2");			
			cell.setValue("Đơn vị: " + obj.getCtyTen());
			style = cell.getStyle();
			cell.setStyle(style);

			if(!obj.getView().equals("TT")){

				cell = cells.getCell("A3");
				cell.setValue("Địa chỉ: " + obj.getDiachi()); 
				style = cell.getStyle();
				cell.setStyle(style);
			
				cell = cells.getCell("A4");
				cell.setValue("Mã số thuế: " + obj.getMasothue()); 
				style = cell.getStyle();
				cell.setStyle(style);
			}else{
				cell = cells.getCell("A3");
				cell.setValue(""); 
				style = cell.getStyle();
				cell.setStyle(style);
			
				cell = cells.getCell("A4");
				cell.setValue(""); 
				style = cell.getStyle();
				cell.setStyle(style);
				
			}
			
			cell = cells.getCell("A7");
			cell.setValue("Tháng: " + obj.getMonth() + " năm: " + obj.getYear());			
			style = cell.getStyle();
			cell.setStyle(style);
		
			
			
			
			rs = obj.getRsHDKD();
			row = 12;
			while(rs.next()){
				cell = cells.getCell("D" + row);
				cell.setValue(rs.getDouble("KYNAY"));
				style = cell.getStyle();
				style.setNumber(3);
				cell.setStyle(style);

				
				cell = cells.getCell("E" + row);
				cell.setValue(rs.getDouble("KYTRUOC"));	
				cell.setStyle(style);
				row ++;
			}
			
			
			
			
			//Mã số 01
			cell = cells.getCell("D12");
			cell.setFormula("=D14 + D13" );
			style = cell.getStyle();
			cell.setStyle(style);
			
			cell = cells.getCell("E12");
			cell.setFormula("=E14 + E13 " );
			style = cell.getStyle();
			cell.setStyle(style);
			
			//Mã số 20
			cell = cells.getCell("D16");
			cell.setFormula("=D14-D15");			
			style = cell.getStyle();
			cell.setStyle(style);
			
			cell = cells.getCell("E16");
			cell.setFormula("=E14-E15");	
			style = cell.getStyle();
			cell.setStyle(style);
			
			//Mã số 30
			cell = cells.getCell("D22");
			cell.setFormula("=D16+(D17-D18)-D20-D21");			
			style = cell.getStyle();
			cell.setStyle(style);
			
			cell = cells.getCell("E22");
			cell.setFormula("=E16+(E17-E18)-E20-E21");	
			style = cell.getStyle();
			cell.setStyle(style);
			
			//Mã số 40
			cell = cells.getCell("D25");
			cell.setFormula("=D23 - D24");			
			style = cell.getStyle();
			cell.setStyle(style);
			
			cell = cells.getCell("E25");
			cell.setFormula("=E23 - E24");	
			style = cell.getStyle();
			cell.setStyle(style);
			
			//Mã số 50
			cell = cells.getCell("D26");
			cell.setFormula("=D22+D25");			
			style = cell.getStyle();
			cell.setStyle(style);
			
			
			cell = cells.getCell("E26");
			cell.setFormula("=E22+E25");	
			style = cell.getStyle();
			cell.setStyle(style);
			
			//Mã số 60
			cell = cells.getCell("D29");
			cell.setFormula("=D26 - D27 - D28");			
			style = cell.getStyle();
			cell.setStyle(style);
			
			cell = cells.getCell("E29");
			cell.setFormula("=E26 - E27 - E28");			
			style = cell.getStyle();
			cell.setStyle(style);
			
			workbook.calculateFormula();
			cell = cells.getCell("D12"); cell.setValue(cell.getDoubleValue());
			cell = cells.getCell("E12"); cell.setValue(cell.getDoubleValue());
			cell = cells.getCell("D16"); cell.setValue(cell.getDoubleValue());
			cell = cells.getCell("E16"); cell.setValue(cell.getDoubleValue());
			cell = cells.getCell("D22"); cell.setValue(cell.getDoubleValue());
			cell = cells.getCell("E22"); cell.setValue(cell.getDoubleValue());
			cell = cells.getCell("D25"); cell.setValue(cell.getDoubleValue());
			cell = cells.getCell("E25"); cell.setValue(cell.getDoubleValue());
			cell = cells.getCell("D26"); cell.setValue(cell.getDoubleValue());
			cell = cells.getCell("E26"); cell.setValue(cell.getDoubleValue());
			cell = cells.getCell("D29"); cell.setValue(cell.getDoubleValue());
			cell = cells.getCell("E29"); cell.setValue(cell.getDoubleValue());
			
			
			
	// Bat dau sheet LCTTGT
			
			worksheet = worksheets.getSheet(3);
			cells = worksheet.getCells();
			
			cell = cells.getCell("A2");			
			cell.setValue("Đơn vị: " + obj.getCtyTen());
			style = cell.getStyle();
			cell.setStyle(style);

			if(!obj.getView().equals("TT")){

				cell = cells.getCell("A3");
				cell.setValue("Địa chỉ: " + obj.getDiachi()); 
				style = cell.getStyle();
				cell.setStyle(style);
			
				cell = cells.getCell("A4");
				cell.setValue("Mã số thuế: " + obj.getMasothue()); 
				style = cell.getStyle();
				cell.setStyle(style);
			}else{
				cell = cells.getCell("A3");
				cell.setValue(""); 
				style = cell.getStyle();
				cell.setStyle(style);
			
				cell = cells.getCell("A4");
				cell.setValue(""); 
				style = cell.getStyle();
				cell.setStyle(style);
				
			}
			cell = cells.getCell("A7");
			cell.setValue("Tháng: " + obj.getMonth() + " năm: " + obj.getYear());			
			style = cell.getStyle();
			cell.setStyle(style);

			
			
			
			workbook.calculateFormula();
			
			//Kỳ này
			Double soTien = worksheets.getSheet("CDKT").getCells().getCell("D12").getDoubleValue()  - cells.getCell("D57").getDoubleValue();
			if (soTien >0){
				cell = cells.getCell("D29");
				double giaTriPhaiTra = cell.getDoubleValue();
				cell.setValue(giaTriPhaiTra + soTien);
			}
			if(soTien <0){
				cell = cells.getCell("D30");
				double giaTriPhaiThu = cell.getDoubleValue();
				cell.setValue(giaTriPhaiThu + soTien);
			}
			//Kỳ trước
			soTien = worksheets.getSheet("CDKT").getCells().getCell("E12").getDoubleValue()  - cells.getCell("E57").getDoubleValue();
			if (soTien >0){
				cell = cells.getCell("E29");
				double giaTriPhaiTra = cell.getDoubleValue();
				cell.setValue(giaTriPhaiTra + soTien);
			}
			if(soTien <0){
				cell = cells.getCell("E30");
				double giaTriPhaiThu = cell.getDoubleValue();
				cell.setValue(giaTriPhaiThu + soTien);
			}
			workbook.calculateFormula();
			cell = cells.getCell("D12"); cell.setValue(cell.getDoubleValue());
			cell = cells.getCell("D13"); cell.setValue(cell.getDoubleValue());
			cell = cells.getCell("D14"); cell.setValue(cell.getDoubleValue());
			cell = cells.getCell("D15"); cell.setValue(cell.getDoubleValue());
			cell = cells.getCell("D16"); cell.setValue(cell.getDoubleValue());
			cell = cells.getCell("D17"); cell.setValue(cell.getDoubleValue());
			cell = cells.getCell("D18"); cell.setValue(cell.getDoubleValue());
			cell = cells.getCell("D19"); cell.setValue(cell.getDoubleValue());
			cell = cells.getCell("D21"); cell.setValue(cell.getDoubleValue());
			cell = cells.getCell("D22"); cell.setValue(cell.getDoubleValue());
			cell = cells.getCell("D23"); cell.setValue(cell.getDoubleValue());
			cell = cells.getCell("D24"); cell.setValue(cell.getDoubleValue());
			cell = cells.getCell("D25"); cell.setValue(cell.getDoubleValue());
			cell = cells.getCell("D26"); cell.setValue(cell.getDoubleValue());
			cell = cells.getCell("D27"); cell.setValue(cell.getDoubleValue());
			cell = cells.getCell("D28"); cell.setValue(cell.getDoubleValue());
			cell = cells.getCell("D29"); cell.setValue(cell.getDoubleValue());
			cell = cells.getCell("D30"); cell.setValue(cell.getDoubleValue());
			cell = cells.getCell("D31"); cell.setValue(cell.getDoubleValue());
			cell = cells.getCell("D33"); cell.setValue(cell.getDoubleValue());
			cell = cells.getCell("D34"); cell.setValue(cell.getDoubleValue());
			cell = cells.getCell("D35"); cell.setValue(cell.getDoubleValue());
			cell = cells.getCell("D36"); cell.setValue(cell.getDoubleValue());
			cell = cells.getCell("D37"); cell.setValue(cell.getDoubleValue());
			cell = cells.getCell("D38"); cell.setValue(cell.getDoubleValue());
			cell = cells.getCell("D39"); cell.setValue(cell.getDoubleValue());
			cell = cells.getCell("D40"); cell.setValue(cell.getDoubleValue());
			cell = cells.getCell("D41"); cell.setValue(cell.getDoubleValue());
			cell = cells.getCell("D44"); cell.setValue(cell.getDoubleValue());
			cell = cells.getCell("D45"); cell.setValue(cell.getDoubleValue());
			cell = cells.getCell("D46"); cell.setValue(cell.getDoubleValue());
			cell = cells.getCell("D47"); cell.setValue(cell.getDoubleValue());
			cell = cells.getCell("D48"); cell.setValue(cell.getDoubleValue());
			cell = cells.getCell("D49"); cell.setValue(cell.getDoubleValue());
			cell = cells.getCell("D50"); cell.setValue(cell.getDoubleValue());
			cell = cells.getCell("D51"); cell.setValue(cell.getDoubleValue());
			cell = cells.getCell("D52"); cell.setValue(cell.getDoubleValue());
			cell = cells.getCell("D53"); cell.setValue(cell.getDoubleValue());
			cell = cells.getCell("D54"); cell.setValue(cell.getDoubleValue());
			cell = cells.getCell("D55"); cell.setValue(cell.getDoubleValue());
			cell = cells.getCell("D57"); cell.setValue(cell.getDoubleValue());
			
			
			if(obj.getMonth().equals("1") && obj.getYear().equals("2017")){
				cell = cells.getCell("E12"); cell.setValue(0);
				cell = cells.getCell("E13"); cell.setValue(0);
				cell = cells.getCell("E14"); cell.setValue(0);
				cell = cells.getCell("E15"); cell.setValue(0);
				cell = cells.getCell("E16"); cell.setValue(0);
				cell = cells.getCell("E17"); cell.setValue(0);
				cell = cells.getCell("E18"); cell.setValue(0);
				cell = cells.getCell("E19"); cell.setValue(0);
				cell = cells.getCell("E21"); cell.setValue(0);
				cell = cells.getCell("E22"); cell.setValue(0);
				cell = cells.getCell("E23"); cell.setValue(0);
				cell = cells.getCell("E24"); cell.setValue(0);
				cell = cells.getCell("E25"); cell.setValue(0);
				cell = cells.getCell("E26"); cell.setValue(0);
				cell = cells.getCell("E27"); cell.setValue(0);
				cell = cells.getCell("E28"); cell.setValue(0);
				cell = cells.getCell("E29"); cell.setValue(0);
				cell = cells.getCell("E30"); cell.setValue(0);
				cell = cells.getCell("E31"); cell.setValue(0);
				cell = cells.getCell("E33"); cell.setValue(0);
				cell = cells.getCell("E34"); cell.setValue(0);
				cell = cells.getCell("E35"); cell.setValue(0);
				cell = cells.getCell("E36"); cell.setValue(0);
				cell = cells.getCell("E37"); cell.setValue(0);
				cell = cells.getCell("E38"); cell.setValue(0);
				cell = cells.getCell("E39"); cell.setValue(0);
				cell = cells.getCell("E40"); cell.setValue(0);
				cell = cells.getCell("E41"); cell.setValue(0);
				cell = cells.getCell("E44"); cell.setValue(0);
				cell = cells.getCell("E45"); cell.setValue(0);
				cell = cells.getCell("E46"); cell.setValue(0);
				cell = cells.getCell("E47"); cell.setValue(0);
				cell = cells.getCell("E48"); cell.setValue(0);
				cell = cells.getCell("E49"); cell.setValue(0);
				cell = cells.getCell("E50"); cell.setValue(0);
				cell = cells.getCell("E51"); cell.setValue(0);
				cell = cells.getCell("E52"); cell.setValue(0);
				cell = cells.getCell("E53"); cell.setValue(0);
				cell = cells.getCell("E54"); cell.setValue(0);
				cell = cells.getCell("E55"); cell.setValue(0);
				cell = cells.getCell("E57"); cell.setValue(0);
			}else {
				cell = cells.getCell("E12"); cell.setValue(cell.getDoubleValue());
				cell = cells.getCell("E13"); cell.setValue(cell.getDoubleValue());
				cell = cells.getCell("E14"); cell.setValue(cell.getDoubleValue());
				cell = cells.getCell("E15"); cell.setValue(cell.getDoubleValue());
				cell = cells.getCell("E16"); cell.setValue(cell.getDoubleValue());
				cell = cells.getCell("E17"); cell.setValue(cell.getDoubleValue());
				cell = cells.getCell("E18"); cell.setValue(cell.getDoubleValue());
				cell = cells.getCell("E19"); cell.setValue(cell.getDoubleValue());
				cell = cells.getCell("E21"); cell.setValue(cell.getDoubleValue());
				cell = cells.getCell("E22"); cell.setValue(cell.getDoubleValue());
				cell = cells.getCell("E23"); cell.setValue(cell.getDoubleValue());
				cell = cells.getCell("E24"); cell.setValue(cell.getDoubleValue());
				cell = cells.getCell("E25"); cell.setValue(cell.getDoubleValue());
				cell = cells.getCell("E26"); cell.setValue(cell.getDoubleValue());
				cell = cells.getCell("E27"); cell.setValue(cell.getDoubleValue());
				cell = cells.getCell("E28"); cell.setValue(cell.getDoubleValue());
				cell = cells.getCell("E29"); cell.setValue(cell.getDoubleValue());
				cell = cells.getCell("E30"); cell.setValue(cell.getDoubleValue());
				cell = cells.getCell("E31"); cell.setValue(cell.getDoubleValue());
				cell = cells.getCell("E33"); cell.setValue(cell.getDoubleValue());
				cell = cells.getCell("E34"); cell.setValue(cell.getDoubleValue());
				cell = cells.getCell("E35"); cell.setValue(cell.getDoubleValue());
				cell = cells.getCell("E36"); cell.setValue(cell.getDoubleValue());
				cell = cells.getCell("E37"); cell.setValue(cell.getDoubleValue());
				cell = cells.getCell("E38"); cell.setValue(cell.getDoubleValue());
				cell = cells.getCell("E39"); cell.setValue(cell.getDoubleValue());
				cell = cells.getCell("E40"); cell.setValue(cell.getDoubleValue());
				cell = cells.getCell("E41"); cell.setValue(cell.getDoubleValue());
				cell = cells.getCell("E44"); cell.setValue(cell.getDoubleValue());
				cell = cells.getCell("E45"); cell.setValue(cell.getDoubleValue());
				cell = cells.getCell("E46"); cell.setValue(cell.getDoubleValue());
				cell = cells.getCell("E47"); cell.setValue(cell.getDoubleValue());
				cell = cells.getCell("E48"); cell.setValue(cell.getDoubleValue());
				cell = cells.getCell("E49"); cell.setValue(cell.getDoubleValue());
				cell = cells.getCell("E50"); cell.setValue(cell.getDoubleValue());
				cell = cells.getCell("E51"); cell.setValue(cell.getDoubleValue());
				cell = cells.getCell("E52"); cell.setValue(cell.getDoubleValue());
				cell = cells.getCell("E53"); cell.setValue(cell.getDoubleValue());
				cell = cells.getCell("E54"); cell.setValue(cell.getDoubleValue());
				cell = cells.getCell("E55"); cell.setValue(cell.getDoubleValue());
				cell = cells.getCell("E57"); cell.setValue(cell.getDoubleValue());
			}
			
			
			workbook.save(out);	
			fstream.close();
			
	

			
		}catch(Exception ex){
			System.out.println(ex.toString());
			ex.printStackTrace();
			throw new Exception("Không tạo được báo cáo");
		}
	}

		private String getDate() 
		{
	        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	        Date date = new Date();
	        return dateFormat.format(date);	
		}
		
		 public Cell CreateBorderSetting(Cell cell) throws IOException
		    {
			
		        Style style;
		        style = cell.getStyle();

		        //Set border color
		        style.setBorderColor(BorderType.TOP, Color.BLACK);
		        style.setBorderColor(BorderType.BOTTOM, Color.BLACK);
		        style.setBorderColor(BorderType.LEFT, Color.BLACK);
		        style.setBorderColor(BorderType.RIGHT, Color.BLACK);

		        //Set the cell border type
		        style.setBorderLine(BorderType.TOP, BorderLineType.THIN);
		        style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
		        style.setBorderLine(BorderType.LEFT, BorderLineType.THIN);
		        style.setBorderLine(BorderType.RIGHT, BorderLineType.THIN);

		        cell.setStyle(style);
		        return cell;
		    }

		 public Cell CreateBorderSetting2(Cell cell) throws IOException
		    {
			
		        Style style;
		        style = cell.getStyle();

		        //Set border color
		        style.setBorderColor(BorderType.TOP, Color.BLACK);
		        style.setBorderColor(BorderType.BOTTOM, Color.BLACK);
		        style.setBorderColor(BorderType.LEFT, Color.BLACK);
		        style.setBorderColor(BorderType.RIGHT, Color.BLACK);

		        //Set the cell border type
		        style.setBorderLine(BorderType.TOP, BorderLineType.DOTTED);
		        style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);
		        style.setBorderLine(BorderType.LEFT, BorderLineType.THIN);
		        style.setBorderLine(BorderType.RIGHT, BorderLineType.THIN);

		        cell.setStyle(style);
		        return cell;
		    }

}
