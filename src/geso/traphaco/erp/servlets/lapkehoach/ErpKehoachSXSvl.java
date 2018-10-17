package geso.traphaco.erp.servlets.lapkehoach;

import geso.traphaco.erp.beans.dubaokinhdoanh.IDubaokinhdoanh_Giay;
import geso.traphaco.erp.beans.dubaokinhdoanh.imp.Dubaokinhdoanh_Giay;
import geso.traphaco.erp.beans.lapkehoach.*;
import geso.traphaco.erp.beans.lapkehoach.imp.*;
import geso.traphaco.erp.beans.lenhsanxuatgiay.ILenhSXCongDoan;
import geso.traphaco.erp.beans.lenhsanxuatgiay.imp.LenhSXCongDoan;
import geso.traphaco.erp.beans.tigia.imp.CongTy;
import geso.traphaco.center.beans.stockintransit.IStockintransit;
import geso.traphaco.center.beans.stockintransit.imp.Stockintransit;
import geso.traphaco.center.servlets.report.ReportAPI;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.db.sql.dbutils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;
import com.oreilly.servlet.MultipartRequest;

public class ErpKehoachSXSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	PrintWriter out;
	private static final String UPLOAD_DIRECTORY = "C:\\upload\\excel\\";
    public ErpKehoachSXSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    out  = response.getWriter();
	    HttpSession session = request.getSession();	
	    String ctyId = (String)session.getAttribute("congtyId");
	    String task = request.getParameter("task");

	    Utility util = new Utility();
		    	    
	    String querystring = request.getQueryString();
		    
	    String userId = util.getUserId(querystring);
	    out.println(userId);
		    
	    if (userId.length() == 0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
		    
	    IErpKehoachsanxuatList khsxList = new ErpKehoachsanxuatList();
	    khsxList.setCtyId(ctyId);
	    khsxList.setUserId(userId);

	    String action = util.getAction(querystring);
	    String id = util.getId(querystring);
		    
	    khsxList.init();
		session.setAttribute("khsxList", khsxList);
		    
	    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpKehoachSX.jsp";
		response.sendRedirect(nextJSP);
	    
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    //out  = response.getWriter();
		String contentType = request.getContentType();

	    HttpSession session = request.getSession();	
	    String ctyId = (String)session.getAttribute("congtyId");
	    
	    //out = response.getWriter();
	    Utility util = new Utility();
	    
	    String userId = util.antiSQLInspection(request.getParameter("userId"));     
	   
		IErpKehoachsanxuatList khsxList;
		khsxList = new ErpKehoachsanxuatList();
		khsxList.setCtyId(ctyId);
		khsxList.setUserId(userId);
	    
		getSearchQuery(request, khsxList);
	    
		String action = request.getParameter("action");
		if (action == null){
			action = "";
		}
		

		System.out.println("Action:" + action);
	    
		try{
			String msg = "";
			if(action.equals("exportExcel")){
				response.setContentType("application/xlsm");
				
				response.setHeader("Content-Disposition", "attachment; filename=DE_NGHI_SAN_XUAT_THANG.xlsx");
				IStockintransit obj = new Stockintransit();
				obj.setErpCongtyId(ctyId);
	    				
	    			
				String thang = util.antiSQLInspection(request.getParameter("thang"));
				if(thang == null) thang = "" +  Integer.parseInt(this.getDateTime().substring(5, 7));
				obj.setMonth(thang);
				
				String nam = util.antiSQLInspection(request.getParameter("nam"));
				if(nam == null) nam = "" +  Integer.parseInt(this.getDateTime().substring(0, 4));
				obj.setYear(nam);
				
				System.out.println("Nam:" + nam + ", thang:" + thang);
	    			
				OutputStream out = response.getOutputStream();
	    			
				if(!CreatePivotTable(out, response, request, obj))
				{
					response.setContentType("text/html");
					PrintWriter writer = new PrintWriter(out);
					writer.print("Không có dữ liệu trong thời gian này");
					writer.close();
				}
		
			}else if(action.equals("run")){
				khsxList.TaoKeHoachSanXuat();
			}

	    		
			khsxList.setUserId(userId);
			khsxList.setMsg(msg);
			khsxList.init();
			
			session.setAttribute("khsxList", khsxList);  	
			session.setAttribute("userId", userId);
		
			response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpKehoachSX.jsp");
		}catch (Exception e) 
		{
			System.out.println(e.toString());
			return;
			
		}
	        
	}
	    
	
	private boolean CreatePivotTable(OutputStream out,HttpServletResponse response,HttpServletRequest request,IStockintransit obj) throws Exception 
	{
		FileInputStream fstream = null;
		Workbook workbook = new Workbook();
		
		
		fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\DE NGHI SAN XUAT_THANG.xlsx");
		
				
		workbook.open(fstream);
		workbook.setFileFormatType(FileFormatType.EXCEL2007);

		FillData(workbook, obj, "CONG TY", "");
		FillData(workbook, obj, "MIEN BAC", "100012");
		FillData(workbook, obj, "MIEN TRUNG", "100014");
		FillData(workbook, obj, "MIEN NAM", "100013");

		workbook.save(out);
		fstream.close();
		return true;	
	}

	private void FillData(Workbook workbook, IStockintransit obj, String sheet, String khoId) {
		dbutils db = new dbutils();
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		Cell cell;
		String thang = obj.getMonth();
		String nam = obj.getYear();

		// ĐIỀU CHỈNH LẠI THÁNG TRONG CỘT G, I, K
		// XỬ LÝ SHEET MIENBAC, MIEN TRUNG, MIEN NAM
		
		String query;
		String t1 = "", t2 = "", t3 = "", n1 = "", n2 = "", n3 = "";
		t1 = thang;
		n1 = nam;
		
		if(t1.equals("12")){
			t2 = "1";
			n2 = "" + (Integer.parseInt(n1) + 1);
		}else{
			t2 = "" + (Integer.parseInt(t1) + 1);
			n2 = n1;
		}
		
		if(t2.equals("12")){
			t3 = "1";
			n3 = "" + (Integer.parseInt(n2) + 1);
		}else{
			t3 = "" + (Integer.parseInt(t2) + 1);
			n3 = n2;
		}
		
		
			worksheet = worksheets.getSheet(sheet);
			Cells cells = worksheet.getCells();
			if(sheet.equals("CONG TY")){
				cell = cells.getCell("G6");
				cell.setValue("Tháng " + (t1.length() == 1? "0" + t1:t1) + "." + n1);
			
				cell = cells.getCell("L6");
				cell.setValue("Tháng " + (t2.length() == 1? "0" + t2:t2) + "." + n2);
			
				cell = cells.getCell("Q6");
				cell.setValue("Tháng " + (t3.length() == 1? "0" + t3:t3) + "." + n3);				
			}else{
				cell = cells.getCell("G6");
				cell.setValue("Tháng " + (t1.length() == 1? "0" + t1:t1) + "." + n1);
			
				cell = cells.getCell("I6");
				cell.setValue("Tháng " + (t2.length() == 1? "0" + t2:t2) + "." + n2);
			
				cell = cells.getCell("K6");
				cell.setValue("Tháng " + (t3.length() == 1? "0" + t3:t3) + "." + n3);
			}
		
		if(!sheet.equals("CONG TY")){
		// ĐƯA DỮ LIỆU VÀO SHEET CÁC KHO VÙNG			
			query = "SELECT  CL.TEN AS CHUNGLOAI, SP.MA, SP.PK_SEQ AS SPID, SP.TEN, DVDL.DONVI, \n " +
					"ISNULL(KHOTT_SP.AVAILABLE, 0) AS TONHIENTAI, ISNULL(KHOTT_SP.TONANTOAN, 0) AS TONANTOAN, \n  " +
					"ISNULL(THANG_1.DUKIENBAN, 0) AS DUBAO_THANG_1, \n " +
					"YC1.YEUCAU AS YCSX_THANG_1, " +

					"ISNULL(THANG_2.DUKIENBAN, 0) AS DUBAO_THANG_2, \n  " +

					"YC2.YEUCAU AS YCSX_THANG_2, \n " +

					"THANG_3.DUKIENBAN AS DUBAO_THANG_3, \n  " +
	
					"YC3.YEUCAU AS YCSX_THANG_3 \n " +

					"FROM ERP_KHOTT_SANPHAM KHOTT_SP \n " +
					"INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = KHOTT_SP.SANPHAM_FK \n " +
					"INNER JOIN CHUNGLOAI CL ON CL.PK_SEQ = SP.CHUNGLOAI_FK \n " +
					"INNER JOIN DONVIDOLUONG DVDL ON DVDL.PK_SEQ = SP.DVDL_FK \n " +
					"LEFT JOIN \n " +
					"( \n " +
					"	SELECT KHOTT_FK, SANPHAM_FK, YEUCAU \n " +
					"	FROM ERP_YEUCAUCUNGUNG \n " +
					"	WHERE THANG = " + t1 + " AND NAM = " + n1 + " \n " +
					")YC1 ON YC1.KHOTT_FK = KHOTT_SP.KHOTT_FK AND  YC1.SANPHAM_FK = KHOTT_SP.SANPHAM_FK \n " +
					"LEFT JOIN \n " +
					"( \n " +
					"	SELECT KHOTT_FK, SANPHAM_FK, YEUCAU \n " +
					"	FROM ERP_YEUCAUCUNGUNG \n " +
					"	WHERE THANG = " + t2 + " AND NAM = " + n2 + " \n " +
					")YC2 ON YC2.KHOTT_FK = KHOTT_SP.KHOTT_FK AND  YC2.SANPHAM_FK = KHOTT_SP.SANPHAM_FK \n " +
					"LEFT JOIN \n " +
					"( \n " +
					"	SELECT KHOTT_FK, SANPHAM_FK, YEUCAU \n " +
					"	FROM ERP_YEUCAUCUNGUNG \n " +
					"	WHERE THANG = " + t3 + " AND NAM = " + n3 + " \n " +
					")YC3 ON YC3.KHOTT_FK = KHOTT_SP.KHOTT_FK AND  YC3.SANPHAM_FK = KHOTT_SP.SANPHAM_FK \n " +
					"LEFT JOIN \n " +
					"( \n " +
					"	SELECT DBSP.KHO_FK, DBSP.SANPHAM_FK, ISNULL(DBSP.DUKIENBAN, 0) AS DUKIENBAN \n " +
					"	FROM ERP_DUBAOSANPHAMMTS DBSP \n " +
					"	INNER JOIN ERP_DUBAO DB ON DB.PK_SEQ = DBSP.DUBAO_FK \n " +
					"	WHERE THANG = " + t1 + " AND NAM = " + n1 + " AND DB.HIEULUC = 1 \n " +
					")THANG_1 ON THANG_1.KHO_FK = KHOTT_SP.KHOTT_FK AND THANG_1.SANPHAM_FK = KHOTT_SP.SANPHAM_FK \n " +
					"LEFT JOIN \n " +
					"( \n " +
					"	SELECT DBSP.KHO_FK, DBSP.SANPHAM_FK, ISNULL(DBSP.DUKIENBAN, 0) AS DUKIENBAN \n " +
					"	FROM ERP_DUBAOSANPHAMMTS DBSP \n " +
					"	INNER JOIN ERP_DUBAO DB ON DB.PK_SEQ = DBSP.DUBAO_FK \n " +
					"	WHERE THANG = " + t2 + " AND NAM = " + n2 + " AND DB.HIEULUC = 1 \n " +
					")THANG_2 ON THANG_2.KHO_FK = KHOTT_SP.KHOTT_FK AND THANG_2.SANPHAM_FK = KHOTT_SP.SANPHAM_FK \n " +
					"LEFT JOIN \n " +
					"( \n " +
					"	SELECT DBSP.KHO_FK, DBSP.SANPHAM_FK, ISNULL(DBSP.DUKIENBAN, 0) AS DUKIENBAN \n " +
					"	FROM ERP_DUBAOSANPHAMMTS DBSP \n " +
					"	INNER JOIN ERP_DUBAO DB ON DB.PK_SEQ = DBSP.DUBAO_FK \n " +
					"	WHERE THANG = " + t3 + " AND NAM = " + n3 + "  AND DB.HIEULUC = 1 \n " +
					")THANG_3 ON THANG_3.KHO_FK = KHOTT_SP.KHOTT_FK AND THANG_3.SANPHAM_FK = KHOTT_SP.SANPHAM_FK \n " +
					"WHERE 1 = 1 AND KHOTT_SP.KHOTT_FK IN ( " + khoId + ") AND SP.TRANGTHAI = 1 \n " +
					"ORDER BY CL.PK_SEQ, SP.MA \n ";
				
			System.out.println(query);
			ResultSet rs = db.get(query);
			try{
				if(rs != null){
					int row = 8;
					int row_begin = 0;
					String chungloai_bk = "";
					Style style;
					Font font;
					worksheet = worksheets.getSheet(sheet);
					cells = worksheet.getCells();

					while(rs.next()){
						if(chungloai_bk.equals(rs.getString("CHUNGLOAI"))){
							row_begin = 0;
							
							cell = cells.getCell("A" + row);
							style = cell.getStyle();
							style.setHAlignment(TextAlignmentType.CENTER);
							cell.setStyle(style);
							ReportAPI.getCellStyle(cell, Color.BLACK, true, 11, rs.getString("MA"), 0);

							cell = cells.getCell("B" + row);
							style = cell.getStyle();
							style.setHAlignment(TextAlignmentType.CENTER);
							cell.setStyle(style);
							ReportAPI.getCellStyle(cell, Color.BLACK, true, 11, rs.getString("SPID"), 0);

							cell = cells.getCell("C" + row);
							ReportAPI.getCellStyle(cell, Color.BLACK, true, 11, rs.getString("TEN"), 0);

							cell = cells.getCell("D" + row);
							style = cell.getStyle();
							style.setHAlignment(TextAlignmentType.CENTER);							
							cell.setStyle(style);
							ReportAPI.getCellStyle(cell, Color.BLACK, true, 11, rs.getString("DONVI"), 0);
							
							cell = cells.getCell("E" + row);
							style = cell.getStyle();
							style.setHAlignment(TextAlignmentType.RIGHT);							
							style.setNumber(3);
							font = style.getFont();
							font.setName("Times New Roman");
							font.setSize(11);
							style.setFont(font);
							cell.setStyle(style);
							cell.setValue(rs.getDouble("TONHIENTAI"));

							cell = cells.getCell("F" + row);
							style = cell.getStyle();
							style.setHAlignment(TextAlignmentType.RIGHT);							
							style.setNumber(3);
							font = style.getFont();
							font.setName("Times New Roman");
							font.setSize(11);
							style.setFont(font);
							cell.setStyle(style);
							cell.setValue(rs.getDouble("TONANTOAN"));

							cell = cells.getCell("G" + row);
							style = cell.getStyle();
							style.setHAlignment(TextAlignmentType.RIGHT);							
							style.setNumber(3);
							font = style.getFont();
							font.setName("Times New Roman");
							font.setSize(11);
							style.setFont(font);
							cell.setStyle(style);
							cell.setValue( rs.getDouble("DUBAO_THANG_1"));

							cell = cells.getCell("H" + row);
							style = cell.getStyle();
							style.setHAlignment(TextAlignmentType.RIGHT);							
							style.setNumber(3);
							font = style.getFont();
							font.setName("Times New Roman");
							font.setSize(11);
							style.setFont(font);
							cell.setStyle(style);
							cell.setValue(rs.getDouble("YCSX_THANG_1"));

							cell = cells.getCell("I" + row);
							style = cell.getStyle();
							style.setHAlignment(TextAlignmentType.RIGHT);							
							style.setNumber(3);
							font = style.getFont();
							font.setName("Times New Roman");
							font.setSize(11);
							style.setFont(font);
							cell.setStyle(style);
							cell.setValue(rs.getDouble("DUBAO_THANG_2"));

							cell = cells.getCell("J" + row);
							style = cell.getStyle();
							style.setHAlignment(TextAlignmentType.RIGHT);							
							style.setNumber(3);
							font = style.getFont();
							font.setName("Times New Roman");
							font.setSize(11);
							style.setFont(font);
							cell.setStyle(style);
							cell.setValue(rs.getDouble("YCSX_THANG_2"));
							
							cell = cells.getCell("K" + row);
							style = cell.getStyle();
							style.setHAlignment(TextAlignmentType.RIGHT);							
							style.setNumber(3);
							font = style.getFont();
							font.setName("Times New Roman");
							font.setSize(11);
							style.setFont(font);
							cell.setStyle(style);
							cell.setValue(rs.getDouble("DUBAO_THANG_3"));

							cell = cells.getCell("L" + row);
							style = cell.getStyle();
							style.setHAlignment(TextAlignmentType.RIGHT);							
							style.setNumber(3);
							font = style.getFont();
							font.setName("Times New Roman");
							font.setSize(11);
							style.setFont(font);
							cell.setStyle(style);
							cell.setValue(rs.getDouble("YCSX_THANG_3"));
							
						}else{
							row_begin = row;
							cells.merge(row - 1, 0, row - 1, 11);
							cell = cells.getCell("A" + row);
							setCategoryStyle(cells, cell);
							ReportAPI.getCellStyle(cell, Color.WHITE, true, 12, rs.getString("CHUNGLOAI"), 1);	
							
							cell = cells.getCell("A" + (row_begin + 1));
							style = cell.getStyle();
							style.setHAlignment(TextAlignmentType.CENTER);
							cell.setStyle(style);
							ReportAPI.getCellStyle(cell, Color.BLACK, true, 11, rs.getString("MA"), 0);

							cell = cells.getCell("B" + (row_begin + 1));
							style = cell.getStyle();
							style.setHAlignment(TextAlignmentType.CENTER);
							cell.setStyle(style);
							ReportAPI.getCellStyle(cell, Color.BLACK, true, 11, rs.getString("SPID"), 0);

							cell = cells.getCell("C" + (row_begin + 1));
							ReportAPI.getCellStyle(cell, Color.BLACK, true, 11, rs.getString("TEN"), 0);

							cell = cells.getCell("D" + (row_begin + 1));
							style = cell.getStyle();
							style.setHAlignment(TextAlignmentType.CENTER);
							cell.setStyle(style);
							ReportAPI.getCellStyle(cell, Color.BLACK, true, 11, rs.getString("DONVI"), 0);

							cell = cells.getCell("E" + (row_begin + 1));
							style = cell.getStyle();
							style.setHAlignment(TextAlignmentType.RIGHT);							
							style.setNumber(3);
							font = style.getFont();
							font.setName("Times New Roman");
							font.setSize(11);
							style.setFont(font);
							cell.setStyle(style);
							cell.setValue(rs.getDouble("TONHIENTAI"));

							cell = cells.getCell("F" + (row_begin + 1));
							style = cell.getStyle();
							style.setHAlignment(TextAlignmentType.RIGHT);							
							style.setNumber(3);
							font = style.getFont();
							font.setName("Times New Roman");
							font.setSize(11);
							style.setFont(font);
							cell.setStyle(style);
							cell.setValue(rs.getDouble("TONANTOAN"));

							cell = cells.getCell("G" + (row_begin + 1));
							style = cell.getStyle();
							style.setHAlignment(TextAlignmentType.RIGHT);							
							style.setNumber(3);
							font = style.getFont();
							font.setName("Times New Roman");
							font.setSize(11);
							style.setFont(font);
							cell.setStyle(style);
							cell.setValue(rs.getDouble("DUBAO_THANG_1"));

							cell = cells.getCell("H" + (row_begin + 1));
							style = cell.getStyle();
							style.setHAlignment(TextAlignmentType.RIGHT);							
							style.setNumber(3);
							font = style.getFont();
							font.setName("Times New Roman");
							font.setSize(11);
							style.setFont(font);
							cell.setStyle(style);
							cell.setValue(rs.getDouble("YCSX_THANG_1"));

							cell = cells.getCell("I" + (row_begin + 1));
							style = cell.getStyle();
							style.setHAlignment(TextAlignmentType.RIGHT);							
							style.setNumber(3);
							font = style.getFont();
							font.setName("Times New Roman");
							font.setSize(11);
							style.setFont(font);
							cell.setStyle(style);
							cell.setValue(rs.getDouble("DUBAO_THANG_2"));

							cell = cells.getCell("J" + (row_begin + 1));
							style = cell.getStyle();
							style.setHAlignment(TextAlignmentType.RIGHT);							
							style.setNumber(3);
							font = style.getFont();
							font.setName("Times New Roman");
							font.setSize(11);
							style.setFont(font);
							cell.setStyle(style);
							cell.setValue(rs.getDouble("YCSX_THANG_2"));

							cell = cells.getCell("K" + (row_begin + 1));
							style = cell.getStyle();
							style.setHAlignment(TextAlignmentType.RIGHT);							
							style.setNumber(3);
							font = style.getFont();
							font.setName("Times New Roman");
							font.setSize(11);
							style.setFont(font);
							cell.setStyle(style);
							cell.setValue(rs.getDouble("DUBAO_THANG_3"));

							cell = cells.getCell("L" + (row_begin + 1));
							style = cell.getStyle();
							style.setHAlignment(TextAlignmentType.RIGHT);							
							style.setNumber(3);
							font = style.getFont();
							font.setName("Times New Roman");
							font.setSize(11);
							style.setFont(font);
							cell.setStyle(style);
							cell.setValue(rs.getDouble("YCSX_THANG_3"));

						}

					
					chungloai_bk = rs.getString("CHUNGLOAI");
					if(row_begin == 0){
						row++;
					}else{
						row = row + 2;
					}
				}
				rs.close();
			}
		}catch(java.sql.SQLException e){
			
		}
		
		}else{
			// ĐƯA DỮ LIỆU VÀO SHEET CÔNG TY			
			query = 		"SELECT  CL.TEN AS CHUNGLOAI, SP.MA , SP.PK_SEQ AS SPID, SP.TEN, DVDL.DONVI, \n " +
			
							"SUM(ISNULL(KHOTT_SP.AVAILABLE, 0)) AS TONHIENTAI, \n " +
			
							"SUM(ISNULL(KHOTT_SP.TONANTOAN, 0)) AS TONANTOAN,  \n " +
	
							"SUM(ISNULL(YC1.YEUCAU, 0)) AS YCSX_THANG_1, \n " +
	
							"SUM(ISNULL(DN_THANG_1.DNSX, 0)) AS DNSX_THANG_1, \n " +
							
							"SUM(ISNULL(DN_THANG_1.DC, 0)) AS DC_THANG_1, \n " +
			
							"SUM(ISNULL(SX_THANG_1.QTY, 0)) AS SX_THANG_1, \n " +
			
							"SUM(ISNULL(YC2.YEUCAU, 0)) AS YCSX_THANG_2, \n " +
			
							"SUM(ISNULL(SX_THANG_2.QTY, 0)) AS SX_THANG_2, \n " +
			
							"SUM(ISNULL(DN_THANG_2.DNSX, 0)) AS DNSX_THANG_2, \n " +
							
							"SUM(ISNULL(DN_THANG_2.DC, 0)) AS DC_THANG_2, \n " +

							"SUM(ISNULL(YC3.YEUCAU, 0)) AS YCSX_THANG_3, \n " +
			
							"SUM(ISNULL(SX_THANG_3.QTY, 0)) AS SX_THANG_3, \n " +
			
							"SUM(ISNULL(DN_THANG_3.DNSX, 0)) AS DNSX_THANG_3, \n " +
							
							"SUM(ISNULL(DN_THANG_3.DC, 0)) AS DC_THANG_3 \n " +

							"FROM ERP_KHOTT_SANPHAM KHOTT_SP \n " +
							"INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = KHOTT_SP.SANPHAM_FK \n " +
							"INNER JOIN CHUNGLOAI CL ON CL.PK_SEQ = SP.CHUNGLOAI_FK \n " +
							"INNER JOIN DONVIDOLUONG DVDL ON DVDL.PK_SEQ = SP.DVDL_FK \n " +
							"LEFT JOIN \n " +
							"( \n " +
							"	SELECT SANPHAM_FK, SUM(YEUCAU) AS YEUCAU \n " +
							"	FROM ERP_YEUCAUCUNGUNG \n " +
							"	WHERE THANG = " + t1 + " AND NAM = " + n1 + " \n " +
							" 		  AND KHOTT_FK IN (100012, 100013, 100014) \n " +
							" 	GROUP BY SANPHAM_FK \n " +
							")YC1 ON YC1.SANPHAM_FK = KHOTT_SP.SANPHAM_FK \n " +
							"LEFT JOIN \n " +
							"( \n " +
							"	SELECT SANPHAM_FK, SUM(YEUCAU) AS YEUCAU  \n " +
							"	FROM ERP_YEUCAUCUNGUNG \n " +
							"	WHERE THANG = " + t2 + " AND NAM = " + n2 + " \n " +
							" 		  AND KHOTT_FK IN (100012, 100013, 100014) \n " +
							" 	GROUP BY SANPHAM_FK \n " +
							")YC2 ON YC2.SANPHAM_FK = KHOTT_SP.SANPHAM_FK \n " +
							"LEFT JOIN \n " +
							"( \n " +
							"	SELECT SANPHAM_FK, SUM(YEUCAU) AS YEUCAU  \n " +
							"	FROM ERP_YEUCAUCUNGUNG \n " +
							"	WHERE THANG = " + t3 + " AND NAM = " + n3 + " \n " +
							" 		  AND KHOTT_FK IN (100012, 100013, 100014) \n " +
							" 	GROUP BY SANPHAM_FK \n " +
							")YC3 ON YC3.SANPHAM_FK = KHOTT_SP.SANPHAM_FK \n " +
							
							"LEFT JOIN( \n " +
							"	SELECT SPID, SUM(QTY) AS QTY \n " +
							"	FROM(  \n " +		
												
								"	SELECT	LSXSP.SANPHAM_FK AS SPID, \n " + 
								"			(SUM(ISNULL(LSXSP.SOLUONG,0)) -  ISNULL(NH.NHANHANG, 0))  AS QTY \n " +  			
								"	FROM ERP_LENHSANXUAT_GIAY LSX   \n  " +
								"	INNER JOIN ERP_LENHSANXUAT_SANPHAM LSXSP ON LSXSP.LENHSANXUAT_FK = LSX.PK_SEQ \n " +  
								"	LEFT JOIN( \n " +
								"		SELECT NKSP.SANPHAM_FK, SUM(ISNULL(NKSP.SOLUONGNHAP, 0)) AS NHANHANG \n " +
								"		FROM ERP_NHAPKHO NK " +
								"		INNER JOIN ERP_NHAPKHO_SANPHAM NKSP ON NKSP.SONHAPKHO_FK = NK.PK_SEQ \n " + 
								"		WHERE NK.NGAYNHAPKHO >=  '" + this.getDateTime() + "' \n " +
								"		AND NK.NGAYNHAPKHO <= '" + n1 + "-" + (t1.length()== 1? "0" + t1 : t1) + "-31'  AND NK.TRANGTHAI = 1 \n " +
								"		AND NK.KHONHAP IN (100005, 100006) \n " +
								"		GROUP BY NKSP.SANPHAM_FK \n " +
								"	)NH ON NH.SANPHAM_FK = LSXSP.SANPHAM_FK \n " +
								
								"	WHERE LSX.NGAYDUKIENHT >=  '" + this.getDateTime() + "' \n  " +
								"	AND LSX.NGAYDUKIENHT <= '" + n1 + "-" + (t1.length()== 1? "0" + t1 : t1) + "-31'  AND LSX.TRANGTHAI < 3 \n " + 
								"	AND LSX.LENHSANXUATDUKIEN_FK IS NULL \n " +
								"	GROUP BY LSXSP.SANPHAM_FK, NH.NHANHANG \n " +
							"	)SX GROUP BY SPID  \n " +			

							")SX_THANG_1 ON SX_THANG_1.SPID = KHOTT_SP.SANPHAM_FK \n " + 
							
							"LEFT JOIN( \n " +
							"	SELECT SPID, SUM(QTY) AS QTY \n " +
							"	FROM(  \n " +		
												

								"	SELECT	LSXSP.SANPHAM_FK AS SPID, \n " + 
								"			(SUM(ISNULL(LSXSP.SOLUONG,0)) -  ISNULL(NH.NHANHANG, 0))  AS QTY \n " +  			
								"	FROM ERP_LENHSANXUAT_GIAY LSX \n " +   
								"	INNER JOIN ERP_LENHSANXUAT_SANPHAM LSXSP ON LSXSP.LENHSANXUAT_FK = LSX.PK_SEQ \n " +  
								"	LEFT JOIN( \n " +
								"		SELECT NKSP.SANPHAM_FK, SUM(ISNULL(NKSP.SOLUONGNHAP, 0)) AS NHANHANG \n " +
								"		FROM ERP_NHAPKHO NK \n " +
								"		INNER JOIN ERP_NHAPKHO_SANPHAM NKSP ON NKSP.SONHAPKHO_FK = NK.PK_SEQ \n " + 
								"		WHERE NK.NGAYNHAPKHO >=  '" + n2 + "-" + (t2.length()== 1? "0" + t2 : t2) + "-01' AND NK.NGAYNHAPKHO <= '" + n2 + "-" + (t2.length()== 1? "0" + t2 : t2) + "-31' AND NK.TRANGTHAI = 1 \n  " +
								"		AND NK.KHONHAP IN (100005, 100006) \n " +
								"		GROUP BY NKSP.SANPHAM_FK \n " +
								"	)NH ON NH.SANPHAM_FK = LSXSP.SANPHAM_FK \n " +
	
								"	WHERE LSX.NGAYDUKIENHT >=  '" + n2 + "-" + (t2.length()== 1? "0" + t2 : t2) + "-01' \n " + 
								"	AND LSX.NGAYDUKIENHT <= '" + n2 + "-" + (t2.length()== 1? "0" + t2 : t2) + "-31'  AND LSX.TRANGTHAI < 3 \n " + 
								"	AND LSX.LENHSANXUATDUKIEN_FK IS NULL " +
								"	GROUP BY LSXSP.SANPHAM_FK, NH.NHANHANG \n " +
							"	)SX GROUP BY SPID  \n " +
							")SX_THANG_2 ON SX_THANG_2.SPID = KHOTT_SP.SANPHAM_FK \n " +
							
							"LEFT JOIN( " +
							"	SELECT SPID, SUM(QTY) AS QTY \n " +
							"	FROM(  \n " +		
												

								"	SELECT	LSXSP.SANPHAM_FK AS SPID, \n " + 
								"			(SUM(ISNULL(LSXSP.SOLUONG,0)) -  ISNULL(NH.NHANHANG, 0))  AS QTY \n " +  			
								"	FROM ERP_LENHSANXUAT_GIAY LSX   \n  " +
								"	INNER JOIN ERP_LENHSANXUAT_SANPHAM LSXSP ON LSXSP.LENHSANXUAT_FK = LSX.PK_SEQ \n " +  
								"	LEFT JOIN( \n " +
								"		SELECT NKSP.SANPHAM_FK, SUM(ISNULL(NKSP.SOLUONGNHAP, 0)) AS NHANHANG \n " +
								"		FROM ERP_NHAPKHO NK \n " +
								"		INNER JOIN ERP_NHAPKHO_SANPHAM NKSP ON NKSP.SONHAPKHO_FK = NK.PK_SEQ \n " + 
								"		WHERE NK.NGAYNHAPKHO >=  '" + n3 + "-" + (t3.length()== 1? "0" + t3 : t3) + "-01' \n  " +
								"		AND NK.NGAYNHAPKHO <= '" + n3 + "-" + (t3.length()== 1? "0" + t3 : t3) + "-31' AND NK.TRANGTHAI = 1 \n " +
								"		AND NK.KHONHAP IN (100005, 100006) \n " +
								"		GROUP BY NKSP.SANPHAM_FK \n " +
								"	)NH ON NH.SANPHAM_FK = LSXSP.SANPHAM_FK \n " +
	
								"	WHERE LSX.NGAYDUKIENHT >=  '" + n3 + "-" + (t3.length()== 1? "0" + t3 : t3) + "-01'  \n " +
								"	AND LSX.NGAYDUKIENHT <= '" + n3 + "-" + (t3.length()== 1? "0" + t3 : t3) + "-31'  AND LSX.TRANGTHAI < 3 \n " + 
								"	AND LSX.LENHSANXUATDUKIEN_FK IS NULL \n " +
								"	GROUP BY LSXSP.SANPHAM_FK, NH.NHANHANG \n " +
							"	)SX GROUP BY SPID  \n " +
							")SX_THANG_3 ON SX_THANG_3.SPID = KHOTT_SP.SANPHAM_FK \n " +
								
							"LEFT JOIN \n " +
							"( \n " +
							"	SELECT KHO_FK, SANPHAM_FK, SOLUONG AS DNSX, DIEUCHINH AS DC \n " +
							"	FROM ERP_LENHSANXUATDUKIEN \n  " +
							"	WHERE THANG = " + t1 + " AND NAM = " + n1 + " \n " +
							")DN_THANG_1 ON DN_THANG_1.KHO_FK = KHOTT_SP.KHOTT_FK AND DN_THANG_1.SANPHAM_FK = KHOTT_SP.SANPHAM_FK \n " +
							"LEFT JOIN \n " +
							"( \n " +
							"	SELECT KHO_FK, SANPHAM_FK, SOLUONG AS DNSX, DIEUCHINH AS DC \n " +
							"	FROM ERP_LENHSANXUATDUKIEN  \n " +
							"	WHERE THANG = " + t2 + " AND NAM = " + n2 + " \n " +
							")DN_THANG_2 ON DN_THANG_2.KHO_FK = KHOTT_SP.KHOTT_FK AND DN_THANG_2.SANPHAM_FK = KHOTT_SP.SANPHAM_FK \n " +
							"LEFT JOIN \n " +
							"( \n " +
							"	SELECT KHO_FK, SANPHAM_FK, SOLUONG AS DNSX, DIEUCHINH AS DC \n " +
							"	FROM ERP_LENHSANXUATDUKIEN  \n " +
							"	WHERE THANG = " + t3 + " AND NAM = " + n3 + " \n " +
							")DN_THANG_3 ON DN_THANG_3.KHO_FK = KHOTT_SP.KHOTT_FK AND DN_THANG_3.SANPHAM_FK = KHOTT_SP.SANPHAM_FK \n " +
							"WHERE KHOTT_SP.KHOTT_FK IN (100003, 100004) AND SP.TRANGTHAI = 1 AND SP.LOAISANPHAM_FK IN (100041, 100042, 100043) \n " +
							"GROUP BY CL.PK_SEQ, CL.TEN, SP.MA , SP.PK_SEQ, SP.TEN, DVDL.DONVI \n " +
							"ORDER BY CL.PK_SEQ, SP.MA \n ";
			System.out.println(query);
			ResultSet rs = db.get(query);
			try{
				if(rs != null){
					int row = 8;
					int row_begin = 0;
					String chungloai_bk = "";
					Style style;
					Font font;
					worksheet = worksheets.getSheet(sheet);
					cells = worksheet.getCells();

					while(rs.next()){
						if(chungloai_bk.equals(rs.getString("CHUNGLOAI"))){
							row_begin = 0;
							
							cell = cells.getCell("A" + row);
							style = cell.getStyle();
							style.setHAlignment(TextAlignmentType.CENTER);
							cell.setStyle(style);
							ReportAPI.getCellStyle(cell, Color.BLACK, true, 11, rs.getString("MA"), 0);

							cell = cells.getCell("B" + row);
							style = cell.getStyle();
							style.setHAlignment(TextAlignmentType.CENTER);
							cell.setStyle(style);
							ReportAPI.getCellStyle(cell, Color.BLACK, true, 11, rs.getString("SPID"), 0);

							cell = cells.getCell("C" + row);
							ReportAPI.getCellStyle(cell, Color.BLACK, true, 11, rs.getString("TEN"), 0);

							cell = cells.getCell("D" + row);
							style = cell.getStyle();
							style.setHAlignment(TextAlignmentType.CENTER);							
							cell.setStyle(style);
							ReportAPI.getCellStyle(cell, Color.BLACK, true, 11, rs.getString("DONVI"), 0);
							
							cell = cells.getCell("E" + row);
							style = cell.getStyle();
							style.setHAlignment(TextAlignmentType.RIGHT);							
							style.setNumber(3);
							font = style.getFont();
							font.setName("Times New Roman");
							font.setSize(11);
							style.setFont(font);
							cell.setStyle(style);
							cell.setValue(rs.getDouble("TONHIENTAI"));

							cell = cells.getCell("F" + row);
							style = cell.getStyle();
							style.setHAlignment(TextAlignmentType.RIGHT);							
							style.setNumber(3);
							font = style.getFont();
							font.setName("Times New Roman");
							font.setSize(11);
							style.setFont(font);
							cell.setStyle(style);
							cell.setValue(rs.getDouble("TONANTOAN"));

							cell = cells.getCell("G" + row);
							style = cell.getStyle();
							style.setHAlignment(TextAlignmentType.RIGHT);							
							style.setNumber(3);
							font = style.getFont();
							font.setName("Times New Roman");
							font.setSize(11);
							style.setFont(font);
							cell.setStyle(style);
							cell.setValue( rs.getDouble("YCSX_THANG_1"));

							cell = cells.getCell("H" + row);
							style = cell.getStyle();
							style.setHAlignment(TextAlignmentType.RIGHT);							
							style.setNumber(3);
							font = style.getFont();
							font.setName("Times New Roman");
							font.setSize(11);
							style.setFont(font);
							cell.setStyle(style);
							cell.setValue(rs.getDouble("SX_THANG_1"));

							cell = cells.getCell("I" + row);
							style = cell.getStyle();
							style.setHAlignment(TextAlignmentType.RIGHT);							
							style.setNumber(3);
							font = style.getFont();
							font.setName("Times New Roman");
							font.setSize(11);
							style.setFont(font);
							cell.setStyle(style);
							cell.setValue(rs.getDouble("DNSX_THANG_1"));

							cell = cells.getCell("J" + row);
							style = cell.getStyle();
							style.setHAlignment(TextAlignmentType.RIGHT);							
							style.setNumber(3);
							font = style.getFont();
							font.setName("Times New Roman");
							font.setSize(11);
							style.setFont(font);
							cell.setStyle(style);
							cell.setValue(rs.getDouble("DC_THANG_1"));

							cell = cells.getCell("K" + row);
							style = cell.getStyle();
							style.setHAlignment(TextAlignmentType.RIGHT);							
							style.setNumber(3);
							font = style.getFont();
							font.setName("Times New Roman");
							font.setSize(11);
							style.setFont(font);
							cell.setStyle(style);
							cell.setFormula("=SUM(I" + row + ":J" + row + ")");
							//cell.setValue(rs.getDouble("DNSX_THANG_1") + rs.getDouble("DC_THANG_1"));
							
							cell = cells.getCell("L" + row);
							style = cell.getStyle();
							style.setHAlignment(TextAlignmentType.RIGHT);							
							style.setNumber(3);
							font = style.getFont();
							font.setName("Times New Roman");
							font.setSize(11);
							style.setFont(font);
							cell.setStyle(style);
							cell.setValue(rs.getDouble("YCSX_THANG_2"));
							
							cell = cells.getCell("M" + row);
							style = cell.getStyle();
							style.setHAlignment(TextAlignmentType.RIGHT);							
							style.setNumber(3);
							font = style.getFont();
							font.setName("Times New Roman");
							font.setSize(11);
							style.setFont(font);
							cell.setStyle(style);
							cell.setValue(rs.getDouble("SX_THANG_2"));

							cell = cells.getCell("N" + row);
							style = cell.getStyle();
							style.setHAlignment(TextAlignmentType.RIGHT);							
							style.setNumber(3);
							font = style.getFont();
							font.setName("Times New Roman");
							font.setSize(11);
							style.setFont(font);
							cell.setStyle(style);
							cell.setValue(rs.getDouble("DNSX_THANG_2"));
							
							cell = cells.getCell("O" + row);
							style = cell.getStyle();
							style.setHAlignment(TextAlignmentType.RIGHT);							
							style.setNumber(3);
							font = style.getFont();
							font.setName("Times New Roman");
							font.setSize(11);
							style.setFont(font);
							cell.setStyle(style);
							cell.setValue(rs.getDouble("DC_THANG_2"));

							cell = cells.getCell("P" + row);
							style = cell.getStyle();
							style.setHAlignment(TextAlignmentType.RIGHT);							
							style.setNumber(3);
							font = style.getFont();
							font.setName("Times New Roman");
							font.setSize(11);
							style.setFont(font);
							cell.setStyle(style);
							cell.setFormula("=SUM(N" + row + ":O" + row + ")");
							//cell.setValue(rs.getDouble("DNSX_THANG_2") + rs.getDouble("DC_THANG_2"));
							
							cell = cells.getCell("Q" + row);
							style = cell.getStyle();
							style.setHAlignment(TextAlignmentType.RIGHT);							
							style.setNumber(3);
							font = style.getFont();
							font.setName("Times New Roman");
							font.setSize(11);
							style.setFont(font);
							cell.setStyle(style);
							cell.setValue(rs.getDouble("YCSX_THANG_3"));
							
							cell = cells.getCell("R" + row);
							style = cell.getStyle();
							style.setHAlignment(TextAlignmentType.RIGHT);							
							style.setNumber(3);
							font = style.getFont();
							font.setName("Times New Roman");
							font.setSize(11);
							style.setFont(font);
							cell.setStyle(style);
							cell.setValue(rs.getDouble("SX_THANG_3"));

							cell = cells.getCell("S" + row);
							style = cell.getStyle();
							style.setHAlignment(TextAlignmentType.RIGHT);							
							style.setNumber(3);
							font = style.getFont();
							font.setName("Times New Roman");
							font.setSize(11);
							style.setFont(font);
							cell.setStyle(style);
							cell.setValue(rs.getDouble("DNSX_THANG_3"));
							
							cell = cells.getCell("T" + row);
							style = cell.getStyle();
							style.setHAlignment(TextAlignmentType.RIGHT);							
							style.setNumber(3);
							font = style.getFont();
							font.setName("Times New Roman");
							font.setSize(11);
							style.setFont(font);
							cell.setStyle(style);
							cell.setValue(rs.getDouble("DC_THANG_3"));

							cell = cells.getCell("U" + row);
							style = cell.getStyle();
							style.setHAlignment(TextAlignmentType.RIGHT);							
							style.setNumber(3);
							font = style.getFont();
							font.setName("Times New Roman");
							font.setSize(11);
							style.setFont(font);
							cell.setStyle(style);
							cell.setFormula("=SUM(S" + row + ":T" + row + ")");
							//cell.setValue(rs.getDouble("DNSX_THANG_3") + rs.getDouble("DC_THANG_3"));


						}else{
							row_begin = row;
							cells.merge(row - 1, 0, row - 1, 20);
							cell = cells.getCell("A" + row);
							setCategoryStyle(cells, cell);
							ReportAPI.getCellStyle(cell, Color.WHITE, true, 12, rs.getString("CHUNGLOAI"), 1);	
							
							cell = cells.getCell("A" + (row_begin + 1));
							style = cell.getStyle();
							style.setHAlignment(TextAlignmentType.CENTER);
							cell.setStyle(style);
							ReportAPI.getCellStyle(cell, Color.BLACK, true, 11, rs.getString("MA"), 0);

							cell = cells.getCell("B" + (row_begin + 1));
							style = cell.getStyle();
							style.setHAlignment(TextAlignmentType.CENTER);
							cell.setStyle(style);
							ReportAPI.getCellStyle(cell, Color.BLACK, true, 11, rs.getString("SPID"), 0);

							cell = cells.getCell("C" + (row_begin + 1));
							ReportAPI.getCellStyle(cell, Color.BLACK, true, 11, rs.getString("TEN"), 0);

							cell = cells.getCell("D" + (row_begin + 1));
							style = cell.getStyle();
							style.setHAlignment(TextAlignmentType.CENTER);
							cell.setStyle(style);
							ReportAPI.getCellStyle(cell, Color.BLACK, true, 11, rs.getString("DONVI"), 0);

							cell = cells.getCell("E" + (row_begin + 1));
							style = cell.getStyle();
							style.setHAlignment(TextAlignmentType.RIGHT);							
							style.setNumber(3);
							font = style.getFont();
							font.setName("Times New Roman");
							font.setSize(11);
							style.setFont(font);
							cell.setStyle(style);
							cell.setValue(rs.getDouble("TONHIENTAI"));

							cell = cells.getCell("F" + (row_begin + 1));
							style = cell.getStyle();
							style.setHAlignment(TextAlignmentType.RIGHT);							
							style.setNumber(3);
							font = style.getFont();
							font.setName("Times New Roman");
							font.setSize(11);
							style.setFont(font);
							cell.setStyle(style);
							cell.setValue(rs.getDouble("TONANTOAN"));

							cell = cells.getCell("G" + (row_begin + 1));
							style = cell.getStyle();
							style.setHAlignment(TextAlignmentType.RIGHT);							
							style.setNumber(3);
							font = style.getFont();
							font.setName("Times New Roman");
							font.setSize(11);
							style.setFont(font);
							cell.setStyle(style);
							cell.setValue(rs.getDouble("YCSX_THANG_1"));

							cell = cells.getCell("H" + (row_begin + 1));
							style = cell.getStyle();
							style.setHAlignment(TextAlignmentType.RIGHT);							
							style.setNumber(3);
							font = style.getFont();
							font.setName("Times New Roman");
							font.setSize(11);
							style.setFont(font);
							cell.setStyle(style);
							cell.setValue(rs.getDouble("SX_THANG_1"));

							cell = cells.getCell("I" + (row_begin + 1));
							style = cell.getStyle();
							style.setHAlignment(TextAlignmentType.RIGHT);							
							style.setNumber(3);
							font = style.getFont();
							font.setName("Times New Roman");
							font.setSize(11);
							style.setFont(font);
							cell.setStyle(style);
							cell.setValue(rs.getDouble("DNSX_THANG_1"));

							cell = cells.getCell("J" + (row_begin + 1));
							style = cell.getStyle();
							style.setHAlignment(TextAlignmentType.RIGHT);							
							style.setNumber(3);
							font = style.getFont();
							font.setName("Times New Roman");
							font.setSize(11);
							style.setFont(font);
							cell.setStyle(style);
							cell.setValue(rs.getDouble("DC_THANG_1"));

							cell = cells.getCell("K" + (row_begin + 1));
							style = cell.getStyle();
							style.setHAlignment(TextAlignmentType.RIGHT);							
							style.setNumber(3);
							font = style.getFont();
							font.setName("Times New Roman");
							font.setSize(11);
							style.setFont(font);
							cell.setStyle(style);
							cell.setFormula("=SUM(I" + (row_begin + 1) + ":J" + (row_begin + 1) + ")");
							//cell.setValue(rs.getDouble("DNSX_THANG_1") + rs.getDouble("DC_THANG_1"));
							
							cell = cells.getCell("L" + (row_begin + 1));
							style = cell.getStyle();
							style.setHAlignment(TextAlignmentType.RIGHT);							
							style.setNumber(3);
							font = style.getFont();
							font.setName("Times New Roman");
							font.setSize(11);
							style.setFont(font);
							cell.setStyle(style);
							cell.setValue(rs.getDouble("YCSX_THANG_2"));

							
							cell = cells.getCell("M" + (row_begin + 1));
							style = cell.getStyle();
							style.setHAlignment(TextAlignmentType.RIGHT);							
							style.setNumber(3);
							font = style.getFont();
							font.setName("Times New Roman");
							font.setSize(11);
							style.setFont(font);
							cell.setStyle(style);
							cell.setValue(rs.getDouble("SX_THANG_2"));

							cell = cells.getCell("N" + (row_begin + 1));
							style = cell.getStyle();
							style.setHAlignment(TextAlignmentType.RIGHT);							
							style.setNumber(3);
							font = style.getFont();
							font.setName("Times New Roman");
							font.setSize(11);
							style.setFont(font);
							cell.setStyle(style);
							cell.setValue(rs.getDouble("DNSX_THANG_2"));

							cell = cells.getCell("O" + (row_begin + 1));
							style = cell.getStyle();
							style.setHAlignment(TextAlignmentType.RIGHT);							
							style.setNumber(3);
							font = style.getFont();
							font.setName("Times New Roman");
							font.setSize(11);
							style.setFont(font);
							cell.setStyle(style);
							cell.setValue(rs.getDouble("DC_THANG_2"));

							cell = cells.getCell("P" + (row_begin + 1));
							style = cell.getStyle();
							style.setHAlignment(TextAlignmentType.RIGHT);							
							style.setNumber(3);
							font = style.getFont();
							font.setName("Times New Roman");
							font.setSize(11);
							style.setFont(font);
							cell.setStyle(style);
							cell.setFormula("=SUM(N" + (row_begin + 1) + ":O" + (row_begin + 1) + ")");
							//cell.setValue(rs.getDouble("DNSX_THANG_2") + rs.getDouble("DC_THANG_2"));
							
							cell = cells.getCell("Q" + (row_begin + 1));
							style = cell.getStyle();
							style.setHAlignment(TextAlignmentType.RIGHT);							
							style.setNumber(3);
							font = style.getFont();
							font.setName("Times New Roman");
							font.setSize(11);
							style.setFont(font);
							cell.setStyle(style);
							cell.setValue(rs.getDouble("YCSX_THANG_3"));

							cell = cells.getCell("R" + (row_begin + 1));
							style = cell.getStyle();
							style.setHAlignment(TextAlignmentType.RIGHT);							
							style.setNumber(3);
							font = style.getFont();
							font.setName("Times New Roman");
							font.setSize(11);
							style.setFont(font);
							cell.setStyle(style);
							cell.setValue(rs.getDouble("SX_THANG_3"));

							cell = cells.getCell("S" + (row_begin + 1));
							style = cell.getStyle();
							style.setHAlignment(TextAlignmentType.RIGHT);							
							style.setNumber(3);
							font = style.getFont();
							font.setName("Times New Roman");
							font.setSize(11);
							style.setFont(font);
							cell.setStyle(style);
							cell.setValue(rs.getDouble("DNSX_THANG_3"));
							
							cell = cells.getCell("T" + (row_begin + 1));
							style = cell.getStyle();
							style.setHAlignment(TextAlignmentType.RIGHT);							
							style.setNumber(3);
							font = style.getFont();
							font.setName("Times New Roman");
							font.setSize(11);
							style.setFont(font);
							cell.setStyle(style);
							cell.setValue(rs.getDouble("DC_THANG_3"));

							cell = cells.getCell("U" + (row_begin + 1));
							style = cell.getStyle();
							style.setHAlignment(TextAlignmentType.RIGHT);							
							style.setNumber(3);
							font = style.getFont();
							font.setName("Times New Roman");
							font.setSize(11);
							style.setFont(font);
							cell.setStyle(style);
							cell.setFormula("=SUM(S" + (row_begin + 1) + ":T" + (row_begin + 1) + ")");
							//cell.setValue(rs.getDouble("DNSX_THANG_3") + rs.getDouble("DC_THANG_3"));

						}

					
					chungloai_bk = rs.getString("CHUNGLOAI");
					if(row_begin == 0){
						row++;
					}else{
						row = row + 2;
					}
				}
				rs.close();
			}
		}catch(java.sql.SQLException e){
			
		}
			
		}
		db.shutDown();
	}


	private String ChuyenThanhLSX(String userId, String ctyId, String soluongsx, String Id, String kbsxId) 
	{
		//Lay kich ban
		String msg = "";
		
		dbutils db = new dbutils();
		try 
		{
						
			db.getConnection().setAutoCommit(false);
			
			List<ILenhSXCongDoan> ListCongdoan= new ArrayList<ILenhSXCongDoan>();
			/*************B0 insert lenh san xuat****************************/
			

			String spId, ngaybatdau, ngaydukienHT ;
			
			String sql = "SELECT SANPHAM_FK AS SPID, NGAYBATDAU, NGAYKETTHUC FROM ERP_LENHSANXUATDUKIEN " +
						 "WHERE PK_SEQ = " + Id + "";
			
			System.out.println("Lay LSX Du kien: " + sql);
			ResultSet rs = db.get(sql);
			rs.next();
			spId = rs.getString("SPID");
			ngaybatdau = rs.getString("NGAYBATDAU");
			ngaydukienHT = rs.getString("NGAYKETTHUC");
			
			String lsxdkId = Id;
			rs.close();
			
			sql = "UPDATE ERP_LENHSANXUATDUKIEN SET SANXUAT = ISNULL(SANXUAT, 0) + " + soluongsx + " WHERE PK_SEQ = " + Id + "";
			db.update(sql);
			
			sql = 	" insert into ERP_LENHSANXUAT_GIAY( lenhsanxuatdukien_fk, ngaybatdau, ngaydukienht, kichbansanxuat_fk, congty_fk, soluong, nhamay_fk, trangthai, ngaytao, nguoitao, ngaysua, nguoisua) " +
					" select  "+lsxdkId+", '" + ngaybatdau + "', '" + ngaydukienHT + "' ,'" + kbsxId + "', '" +ctyId + "',  " + soluongsx+ ", nhamay_fk  , '0', '" + getDateTime() + "', '" + userId + "', '" + getDateTime() + "', '" + userId + "' " +
					" from erp_kichbansanxuat_giay " +
					" where pk_seq = " + kbsxId;
			
			System.out.println("Insert LenhSanXuat: " + sql);
			
			if(!db.update(sql))
			{
			 
			db.getConnection().rollback();
			return sql;
			}
			
			sql = "select IDENT_CURRENT('ERP_LENHSANXUAT_GIAY') as clId";
			rs = db.get(sql);
			rs.next();
			String	lsxId = rs.getString("clId");
			rs.close();
			
			
			sql="";
			/*************B1 Đưa vào lệnh sản xuất **************/
			
				sql=" select distinct nm.pk_seq as nhamayid, a.thutu, a.danhmucvattu_fk ,0 as spid , "+ 
				" A.DINHLUONG_FK,isnull(B.DINHTINH,'0') as dinhtinh ,isnull(a.kiemdinhchatluong,'0') as kiemdinhchatluong, b.pk_seq,b.diengiai,a.kichbansanxuat_fk,  "+
				" a.thutu,b.nhamay_fk,nm.manhamay,nm.tennhamay ,isnull( a.sanpham_fk,0) as sanpham_fk ,isnull(sp.ma,'') as masp,isnull(sp.ten,'') as tensp "+ 
				" from Erp_KichBanSanXuat_CongDoanSanXuat_Giay a  "+
				" inner join erp_kichbansanxuat_giay kb on kb.pk_seq= a.kichbansanxuat_fk "+ 
				" inner join Erp_CongDoanSanXuat_Giay b on a.congdoansanxuat_fk=b.pk_Seq "+ 
				" inner join erp_nhamay nm on nm.pk_seq=b.nhamay_fk  " +
				" left join  erp_danhmucvattu dmvt on dmvt.pk_seq=a.danhmucvattu_fk		 "+
				" LEFT join  erp_sanpham sp on sp.ma=dmvt.mavattu "+
				" where kichbansanxuat_fk='"+kbsxId+"' and dmvt.sudung = 0  "+
				" order by a.thutu ";
			System.out.println("get Cong doan  : "+sql);
			rs=db.get(sql);
			while (rs.next()){
				
				ILenhSXCongDoan lsxcd=new LenhSXCongDoan();
				lsxcd.setCongDoanId(rs.getString("pk_seq"))	;
				lsxcd.setDiengiai(rs.getString("diengiai"));
				lsxcd.Setkichbansanxuat(rs.getString("kichbansanxuat_fk"));
				lsxcd.setTrangthai("0");
				lsxcd.setActive("1");
					
				lsxcd.setNhaMayId(rs.getString("nhamay_fk"));
				lsxcd.setBomId(rs.getString("danhmucvattu_fk"));
				lsxcd.setThuTu(rs.getFloat("thutu"));
				lsxcd.setPhanXuong(rs.getString("manhamay"));
				lsxcd.setSanPham(rs.getString("masp") +"-"+rs.getString("tensp"));
				lsxcd.setMaSp(rs.getString("masp"));
					
				lsxcd.setSpId(rs.getString("sanpham_fk"));
				
					
				lsxcd.SetKiemDinhCL(rs.getString("kiemdinhchatluong"));
				lsxcd.setSoLuong("0");
				ListCongdoan.add(lsxcd);
				
			}
			rs.close();
				
			// thuc hien save cong doan 
			if(ListCongdoan.size() >0){
					

				for(int i = 0; i < ListCongdoan.size(); i++)
				{
					ILenhSXCongDoan congdoan=ListCongdoan.get(i);
						
					String trangthaicd="";
					if(congdoan.getActive().equals("1")){
						trangthaicd="0";
					}else{
						trangthaicd="2";
					}
					String query = "insert into ERP_LENHSANXUAT_CONGDOAN_GIAY (lenhsanxuat_fk,kichban_fk,congdoan_fk,tinhtrang,soluong,THUTU,SANPHAM_FK,DANHMUCVATTU_FK,KIEMDINHCHATLUONG,MASANPHAM) values " +
					" ( '"+lsxId+"','"+kbsxId+"','"+congdoan.getCongDoanId()+"','"+trangthaicd+"',"+congdoan.getSoLuong()+","+congdoan.getThutu()+","+congdoan.getSpid()+","+congdoan.getBomId()+",'"+congdoan.getKiemDinhCL()+"','"+congdoan.getMaSp()+"')";

					System.out.println("Các Cong Doanh");
					if(!db.update(query))
					{
						
						db.getConnection().rollback();
						return query;
					}
						
				}
			}
				
				
			sql =	"insert into erp_lenhsanxuat_sanpham(lenhsanxuat_fk, sanpham_fk, soluong, danhmucvt_fk) " +
					"SELECT  '"+lsxId+"','"+ spId+"','"+soluongsx+"',(select top(1) pk_seq from ERP_DANHMUCVATTU where trangthai=1 and uutien = '1' and mavattu=(select ma from erp_sanpham where pk_seq = " + spId + ") and congty_fk="+ctyId+") ";
				
			System.out.println("Chen LSX - SP: " + sql);
			if(!db.update(sql))
			{
				
				db.getConnection().rollback();
				return sql;
			}
				
			/*************END**************************/
			/*sql=" update ERP_LENHSANXUATDUKIEN set "+
			" sanxuat= isnull((select SUM(lsx.SOLUONG) from ERP_LENHSANXUAT_GIAY LSX "+  
			" where LSX.LENHSANXUATDUKIEN_FK=ERP_LENHSANXUATDUKIEN.PK_SEQ ),0) "+
			" WHERE ERP_LENHSANXUATDUKIEN.pk_seq ="+lsxdkId;	
			System.out.println(sql);
			if(!db.update(sql))
			{
				msg = "Không thể tạo mới Kichbansanxuat: " + sql;
				db.getConnection().rollback();
				return msg;
			}*/
			

			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			db.shutDown();
			
			return msg;
		} 
		catch (Exception e) 
		{
			msg = "Không thể kích hoạt lệnh sản xuất: " + e.getMessage();
			db.update("rollback");
			db.shutDown();
			return msg;
		}

	}

	
	private void getSearchQuery(HttpServletRequest request, IErpKehoachsanxuatList obj) 
	{
		Utility util = new Utility();
		
		String thang = util.antiSQLInspection(request.getParameter("thang"));
		if(thang == null)
			thang = "";
		obj.setThang(thang);
		
		String nam = util.antiSQLInspection(request.getParameter("nam"));
		if(nam == null)
			nam = "";
		obj.setNam(nam);
		
		String clId = util.antiSQLInspection(request.getParameter("chungloai")); 
		if(clId == null) clId = "";
		obj.setClId(clId);

		String spId = util.antiSQLInspection(request.getParameter("spId")); 
		if(spId == null) spId = "";
		obj.setSpId(spId);
		
		String dtsxId = util.antiSQLInspection(request.getParameter("dtsxId"));
		
		obj.setDtsxId(dtsxId);

	}
	
	
	
	private void setCategoryStyle(Cells cells ,Cell cell)
	{
		Cell cell1 = cells.getCell("AZ1");
		Style style;	
		style = cell1.getStyle();
        style.setBorderLine(BorderType.LEFT, BorderLineType.THIN);
        style.setBorderLine(BorderType.RIGHT, BorderLineType.THIN);
        cell.setStyle(style);
	}
	
	private String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	

}
