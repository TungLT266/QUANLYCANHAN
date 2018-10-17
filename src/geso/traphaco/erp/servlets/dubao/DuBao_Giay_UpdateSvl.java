package geso.traphaco.erp.servlets.dubao;

import geso.traphaco.center.beans.stockintransit.IStockintransit;
import geso.traphaco.center.beans.stockintransit.imp.Stockintransit;
import geso.traphaco.center.servlets.report.ReportAPI;
import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.dubaokinhdoanh.IDubaokinhdoanh_Giay;
import geso.traphaco.erp.beans.dubaokinhdoanh.imp.Dubaokinhdoanh_Giay;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Enumeration;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.oreilly.servlet.MultipartRequest;
import com.aspose.cells.BorderLineType;
import com.aspose.cells.BorderType;
import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Style ;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;
import com.aspose.cells.Font;

public class DuBao_Giay_UpdateSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	private static final String UPLOAD_DIRECTORY = "C:\\upload\\excel\\";

	public DuBao_Giay_UpdateSvl()
	{
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		String sum = (String) session.getAttribute("sum");
		Utility cutil = (Utility) session.getAttribute("util");
		if (!cutil.check(userId, userTen, sum))
		{
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		} 
		else
		{
			session.setMaxInactiveInterval(30000);

			Utility util = new Utility();

			String querystring = request.getQueryString();
			userId = util.getUserId(querystring);
			String ctyId = (String)session.getAttribute("congtyId");
			
			if (userId.length() == 0)
				userId = util.antiSQLInspection(request.getParameter("userId"));
			String id = util.getId(querystring);
			Dubaokinhdoanh_Giay dbkdBean = new Dubaokinhdoanh_Giay(id);
			dbkdBean.setCtyId(ctyId);
			
			dbkdBean.setUserId(userId);
			String nextJSP;

			if (request.getQueryString().indexOf("display") >= 0)
			{
				dbkdBean.init();

				nextJSP = "/TraphacoHYERP/pages/Erp/Erp_DuBao_Giay_Display.jsp";
			} 
			else
			{
				dbkdBean.init();
				nextJSP = "/TraphacoHYERP/pages/Erp/Erp_DuBao_Giay_Update.jsp";
			}
			session.setAttribute("dbkdBean", dbkdBean);
			response.sendRedirect(nextJSP);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		IDubaokinhdoanh_Giay dbkdBean;
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html ; charset=UTF-8");
		String contentType = request.getContentType();
		HttpSession session = request.getSession();
		
		String userId = (String) session.getAttribute("userId");
		String ctyId = (String)session.getAttribute("congtyId");
		
		Utility util = new Utility();
		String nextJSP="";
		String action = "";
		System.out.println("VÀO ĐÂY ");
		
		//Khi upload file file phai dung MultipartRequest de lay parameter
		if ((contentType != null) && (contentType.indexOf("multipart/form-data") >= 0))
		{
			MultipartRequest multi = new MultipartRequest(request, UPLOAD_DIRECTORY, 20000000, "UTF-8");
			action = util.antiSQLInspection(multi.getParameter("action"));
			if (action == null)
				action = "";
			
			System.out.println("Action Request encrypt: " + action);
			String id = util.antiSQLInspection(multi.getParameter("id"));
			if (id == null)
			{
				dbkdBean = new Dubaokinhdoanh_Giay();
			} 
			else
			{
				dbkdBean = new Dubaokinhdoanh_Giay(id);
			}
			
			dbkdBean.setCtyId(ctyId);
			dbkdBean.setUserId(userId);
			
			// HÀM NÀY LẤY CÁC GIÁ TRỊ TỪ BROWSER
			getRequestEcrypt(util, dbkdBean, multi, request, 1);
			
			Enumeration files = multi.getFileNames();
			String filename = "";
			while (files.hasMoreElements())
			{
				String name = (String) files.nextElement();
				filename = multi.getFilesystemName(name);
				//System.out.println("File:  " + UPLOAD_DIRECTORY + filename);
			}
			
			System.out.println("1____READ EXCEL TOI DAY, FILE NAME......" + filename);
			if (filename !=null && filename.length() > 0 )
			{
				System.out.println("___READ EXCEL FILE: ");
				this.readExcel(dbkdBean, UPLOAD_DIRECTORY + filename, response);				
				
			}
			
			dbkdBean.init();
			nextJSP = "/TraphacoHYERP/pages/Erp/Erp_DuBao_Giay_Update.jsp";
			session.setAttribute("dbkdBean", dbkdBean);
			response.sendRedirect(nextJSP);
		} 
		else
		{
			action = util.antiSQLInspection(request.getParameter("action"));
			if (action == null)
				action = "";
			
			String id = util.antiSQLInspection(request.getParameter("id"));
			System.out.println("Action: " + action);
			if (id == null)
			{
				dbkdBean = new Dubaokinhdoanh_Giay();				
			} 
			else
			{
				dbkdBean = new Dubaokinhdoanh_Giay(id);
			}
			
			String loai = util.antiSQLInspection(request.getParameter("loai"));
			dbkdBean.setLoai(loai);
			
			String ngaydubao = request.getParameter("ngaydubao");
			dbkdBean.setNgaydubao(ngaydubao);
			
			dbkdBean.setCtyId(ctyId);
			dbkdBean.setUserId(userId);
			getRequestEcrypt(util, dbkdBean, null, request, 0);
			
			//Lấy thông tin 
			//getRequest(util, dbkdBean, request);
		
			if (action.equals("new"))
			{
				if (!dbkdBean.CreateDubao(request))
				{
					dbkdBean.setMsg("Tạo dự báo kinh doanh không thành công");
				} 
				else
				{
					dbkdBean.setMsg("Cập nhật dự báo kinh doanh thành công");
				}
			} 
			else
			{
				if(action.equals("exportExcel"))
				{
					response.setContentType("application/xlsm");
					
					System.out.println("Loại nè:" + dbkdBean.getLoai());
		        	System.out.println("Tháng nè:" + dbkdBean.getThang());
					if(dbkdBean.getLoai().equals("1")){ 
						response.setHeader("Content-Disposition", "attachment; filename=KE_HOACH_DAT_HANG_THANG.xlsx");
					}else{
						response.setHeader("Content-Disposition", "attachment; filename=KE_HOACH_DAT_HANG_TUAN.xlsx");
					}
					IStockintransit obj = new Stockintransit();
					obj.setErpCongtyId(ctyId);
					String query = ""; //setQuery(obj); 
					OutputStream out = response.getOutputStream();
			        try 
			        {
			        	if(!CreatePivotTable(out, response, request, obj, query, dbkdBean.getId()))
			        	{
			        		response.setContentType("text/html");
			        		PrintWriter writer = new PrintWriter(out);
			        		writer.print("Không có dữ liệu trong thời gian này");
			        		writer.close();
			        	}
			        }
			        catch (Exception e) 
			        {
						obj.setMsg("Khong the tao bao cao " + e.getMessage());
						System.out.println(e.toString());
						session.setAttribute("obj", obj);
						response.sendRedirect(nextJSP);
						return;
						
					}
				}

				else if(action.equals("readExcel"))
				{
					System.out.println("ĐỌC FILE");
				}
				else if(action.equals("save"))
				{
					if (!dbkdBean.Update(request))
					{
						dbkdBean.setMsg("Cập nhật dự báo kinh doanh không thành công");
					}
					else
					{
						dbkdBean.setMsg("Cập nhật dự báo kinh doanh thành công");
					}
				}
			}
			
			dbkdBean.init();
			dbkdBean.createRsMTS();
			nextJSP = "/TraphacoHYERP/pages/Erp/Erp_DuBao_Giay_Update.jsp";
			
			session.setAttribute("dbkdBean", dbkdBean);
			response.sendRedirect(nextJSP);
			
		}
		
	}

	private boolean CreatePivotTable(OutputStream out,HttpServletResponse response,HttpServletRequest request,IStockintransit obj, String query, String Id) throws Exception 
	{
		FileInputStream fstream = null;
		Workbook workbook = new Workbook();
		
		
		fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\KE HOACH DAT HANG THANG.xlsx");
		
				
		workbook.open(fstream);
		workbook.setFileFormatType(FileFormatType.EXCEL2007);


		FillData(workbook, obj, Id, 0, "100023");
		FillData(workbook, obj, Id, 1, "100041");
		FillData(workbook, obj, Id, 2, "100034");
		FillData(workbook, obj, Id, 3, "");
		workbook.save(out);
		fstream.close();
		return true;	
	}
	
	private boolean CreatePivotTable_2(OutputStream out,HttpServletResponse response,HttpServletRequest request,IStockintransit obj, String query, String Id, String thang, String nam) throws Exception 
	{
		FileInputStream fstream = null;
		Workbook workbook = new Workbook();
		
		fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\KE HOACH DAT HANG TUAN.xlsx");
				
		workbook.open(fstream);
		workbook.setFileFormatType(FileFormatType.EXCEL2007);

		FillData_2(workbook, obj, Id, 0, "100023", thang, nam );
		FillData_2(workbook, obj, Id, 1, "100041", thang, nam);
		FillData_2(workbook, obj, Id, 2, "100034", thang, nam);
		FillData_2(workbook, obj, Id, 3, "", thang, nam);
		
		workbook.save(out);
		fstream.close();
		return true;	
	}
	
	private void FillData(Workbook workbook, IStockintransit obj, String Id, int sheet, String khoId) {
		
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		Cell cell;
		dbutils db = new dbutils();
		String ngaydubao = "";
		String query = "SELECT NGAYDUBAO FROM ERP_DUBAO WHERE PK_SEQ = " + Id;
		ResultSet rs = db.get(query);
		try{
			rs.next();
			ngaydubao = rs.getString("NGAYDUBAO");
			rs.close();
		}catch(java.sql.SQLException e){}
		
		String thang = ngaydubao.substring(5, 7);
		String nam = ngaydubao.substring(0, 4);
		
		String M = "", Y = "";
		String m1 = "", m2 = "", m3 = "", m4 = "";
		String y1 = "", y2 = "", y3 = "", y4 = "";
		// ĐIỀU CHỈNH LẠI THÁNG TRONG CỘT E, F, G
		for(int i = 0; i < 4; i++){
			worksheet = worksheets.getSheet(i);
			Cells cells = worksheet.getCells();
			
			for(int j = 1; j <= 4; j++){
				if(Integer.parseInt(thang) + j >  12){
					M = "" + (Integer.parseInt(thang) + j - 12 );	
					Y = "" + (Integer.parseInt(nam) + 1);
				}else{
					M = "" + (Integer.parseInt(thang) + j);
					Y = "" + (Integer.parseInt(nam));
				}
				cells.setRowHeight(0, 20.0f);
				cell = cells.getCell("G7");
				if(j == 1){
					cell = cells.getCell("G7");
					m1 = M;
					y1 = Y;
				}else if (j == 2){
					cell = cells.getCell("H7");
					m2 = M;
					y2 = Y;
				}else if (j == 3){
					cell = cells.getCell("I7");
					m3 = M;
					y3 = Y;
				}else if (j == 4){
					cell = cells.getCell("J7");
					m4 = M;
					y4 = Y;
				}		
				ReportAPI.getCellStyle(cell, Color.BLUE, true, 10, "T." + M + "/" + Y, 1);
			}
		}
		// THÊM SẢN PHẨM
		
		query = "";
		if(khoId.length() > 0){
			query =  		"SELECT SP.PK_SEQ AS SPID, SP.TEN, DVDL.DONVI, SP.MA, " +
							"ISNULL((SELECT GIABAN FROM ERP_BGBAN_SANPHAM WHERE BGBAN_FK IN (SELECT TOP 1 PK_SEQ FROM ERP_BANGGIABAN WHERE SUDUNG = 1)  AND TRANGTHAI = 1 AND SANPHAM_FK = SP.PK_SEQ), 0) AS GIA, " +
							"ISNULL((SELECT DUKIENBAN FROM ERP_DUBAOSANPHAMMTS " +
							"WHERE SANPHAM_FK = SP.PK_SEQ AND DUBAO_FK = " + Id  + " AND THANG = " + m1 + " AND NAM = " + y1 + " AND KHO_FK = " + khoId + " ), 0) AS T1, " +
							
							"ISNULL((SELECT ISNULL(DUKIENBAN, 0) FROM ERP_DUBAOSANPHAMMTS " +
							"WHERE SANPHAM_FK = SP.PK_SEQ AND DUBAO_FK = " + Id + " AND THANG = " + m2 + " AND NAM = " + y2 + " AND KHO_FK = " + khoId + " ), 0) AS T2, " +
							
							"ISNULL((SELECT ISNULL(DUKIENBAN, 0) FROM ERP_DUBAOSANPHAMMTS " +
							"WHERE SANPHAM_FK = SP.PK_SEQ AND DUBAO_FK = " + Id + " AND THANG = " + m3 + " AND NAM = " + y3 + " AND KHO_FK = " + khoId + " ), 0) AS T3, " +
		 
							"ISNULL((SELECT ISNULL(DUKIENBAN, 0) FROM ERP_DUBAOSANPHAMMTS " +
							"WHERE SANPHAM_FK = SP.PK_SEQ AND DUBAO_FK = " + Id + " AND THANG = " + m4 + " AND NAM = " + y4 + " AND KHO_FK = " + khoId + " ), 0) AS T4 " +


							"FROM ERP_SANPHAM SP " +
							"INNER JOIN DONVIDOLUONG DVDL ON DVDL.PK_SEQ = SP.DVDL_FK " +
							"WHERE SP.TRANGTHAI =  1  AND SP.LOAISANPHAM_FK IN (100002, 100003) " +
							"ORDER BY SP.MA ASC ";
		}else{
			query =  		"SELECT SP.PK_SEQ AS SPID, SP.TEN, DVDL.DONVI, SP.MA, " +
							"ISNULL((SELECT GIABAN FROM ERP_BGBAN_SANPHAM WHERE BGBAN_FK IN (SELECT TOP 1 PK_SEQ FROM ERP_BANGGIABAN WHERE SUDUNG = 1)  AND TRANGTHAI = 1 AND SANPHAM_FK = SP.PK_SEQ), 0) AS GIA, " +
							"ISNULL((" +
							"	SELECT ISNULL(SUM(DUKIENBAN), 0) " +
							"	FROM ERP_DUBAOSANPHAMMTS " +
							"	WHERE SANPHAM_FK = SP.PK_SEQ AND DUBAO_FK = " + Id  + " AND THANG = " + m1 + " AND NAM = " + y1 + " " +
							"), 0) AS T1, " +
			
							"ISNULL((" +
							"	SELECT ISNULL(SUM(DUKIENBAN), 0) " +
							"	FROM ERP_DUBAOSANPHAMMTS " +
							"	WHERE SANPHAM_FK = SP.PK_SEQ AND DUBAO_FK = " + Id + " AND THANG = " + m2 + " AND NAM = " + y2 + "  " +
							"), 0) AS T2, " +
							
							"ISNULL((" +
							"	SELECT ISNULL(SUM(DUKIENBAN), 0) " +
							"	FROM ERP_DUBAOSANPHAMMTS " +
							"	WHERE SANPHAM_FK = SP.PK_SEQ AND DUBAO_FK = " + Id + " AND THANG = " + m3 + " AND NAM = " + y3 + "  " +
							"), 0) AS T3, " +

							"ISNULL((" +
							"	SELECT ISNULL(SUM(DUKIENBAN), 0) " +
							"	FROM ERP_DUBAOSANPHAMMTS " +
							"	WHERE SANPHAM_FK = SP.PK_SEQ AND DUBAO_FK = " + Id + " AND THANG = " + m4 + " AND NAM = " + y4 + "  " +
							"), 0) AS T4 " +


							"FROM ERP_SANPHAM SP " +
							"INNER JOIN DONVIDOLUONG DVDL ON DVDL.PK_SEQ = SP.DVDL_FK " +
							"WHERE SP.TRANGTHAI =  1   AND SP.LOAISANPHAM_FK IN (100002, 100003) " +
							"ORDER BY SP.MA ASC ";
			
		}
		System.out.println(query);
		rs = db.get(query);
		try{
			if(rs != null){
				int row = 8;
				int row_begin = 0;
				String chungloai_bk = "";
				Style style;
				Font font;
				
				while(rs.next()){
					
						worksheet = worksheets.getSheet(sheet);
						Cells cells = worksheet.getCells();
						
						if(chungloai_bk.length() == 0){
							row_begin = 0;
							
							cell = cells.getCell("A" + row);
							style = cell.getStyle();
							style.setHAlignment(TextAlignmentType.CENTER);
							cell.setStyle(style);
							ReportAPI.getCellStyle(cell, Color.BLACK, true, 11, "" + (row - 7), 0);

							cell = cells.getCell("B" + row);
							style = cell.getStyle();
							style.setHAlignment(TextAlignmentType.CENTER);
							cell.setStyle(style);
							ReportAPI.getCellStyle(cell, Color.BLACK, true, 11, rs.getString("SPID"), 0);

							cell = cells.getCell("C" + row);
							style = cell.getStyle();
							style.setHAlignment(TextAlignmentType.CENTER);
							cell.setStyle(style);
							ReportAPI.getCellStyle(cell, Color.BLACK, true, 11, rs.getString("MA"), 0);

							cell = cells.getCell("D" + row);
							ReportAPI.getCellStyle(cell, Color.BLACK, true, 11, rs.getString("TEN"), 0);

							cell = cells.getCell("E" + row);
							style = cell.getStyle();
							style.setHAlignment(TextAlignmentType.CENTER);							
							cell.setStyle(style);
							ReportAPI.getCellStyle(cell, Color.BLACK, true, 11, rs.getString("DONVI"), 0);
							
							cell = cells.getCell("F" + row);
							style = cell.getStyle();
							style.setHAlignment(TextAlignmentType.RIGHT);							
							style.setNumber(3);
							font = style.getFont();
							font.setName("Times New Roman");
							font.setSize(11);
							style.setFont(font);
							cell.setStyle(style);
							cell.setValue(rs.getDouble("GIA"));

							cell = cells.getCell("G" + row);
							style = cell.getStyle();
							style.setHAlignment(TextAlignmentType.RIGHT);							
							style.setNumber(3);
							font = style.getFont();
							font.setName("Times New Roman");
							font.setSize(11);
							style.setFont(font);
							cell.setStyle(style);
							cell.setValue(rs.getDouble("T1"));

							cell = cells.getCell("H" + row);
							style = cell.getStyle();
							style.setHAlignment(TextAlignmentType.RIGHT);							
							style.setNumber(3);
							font = style.getFont();
							font.setName("Times New Roman");
							font.setSize(11);
							style.setFont(font);
							cell.setStyle(style);
							cell.setValue( rs.getDouble("T2"));

							cell = cells.getCell("I" + row);
							style = cell.getStyle();
							style.setHAlignment(TextAlignmentType.RIGHT);							
							style.setNumber(3);
							font = style.getFont();
							font.setName("Times New Roman");
							font.setSize(11);
							style.setFont(font);
							cell.setStyle(style);
							cell.setValue(rs.getDouble("T3"));

							cell = cells.getCell("J" + row);
							style = cell.getStyle();
							style.setHAlignment(TextAlignmentType.RIGHT);							
							style.setNumber(3);
							font = style.getFont();
							font.setName("Times New Roman");
							font.setSize(11);
							style.setFont(font);
							cell.setStyle(style);
							cell.setValue(rs.getDouble("T4"));

							cell = cells.getCell("K" + row);
							style = cell.getStyle();
							style.setHAlignment(TextAlignmentType.RIGHT);							
							style.setNumber(3);
							font = style.getFont();
							font.setName("Times New Roman");
							font.setSize(11);
							style.setFont(font);
							cell.setStyle(style);
							//cell.setValue((rs.getDouble("T1") + rs.getDouble("T2") + rs.getDouble("T3")));
							cell.setFormula("=SUM(G" + row + ":J" + row + ")");
							
							cell = cells.getCell("L" + row);
							style = cell.getStyle();
							style.setHAlignment(TextAlignmentType.RIGHT);	
							style.setNumber(3);
							font = style.getFont();
							font.setName("Times New Roman");
							font.setSize(11);
							style.setFont(font);
							cell.setStyle(style);
							//cell.setValue((rs.getDouble("T1") + rs.getDouble("T2") + rs.getDouble("T3"))*rs.getDouble("GIA"));
							cell.setFormula("=SUM(G" + row + ":J" + row + ")*F" + row +"");
						}else{
							row_begin = row;
							cells.merge(row - 1, 0, row - 1, 18);
							cell = cells.getCell("A" + row);
							setCategoryStyle(cells, cell);
							ReportAPI.getCellStyle(cell, Color.WHITE, true, 12, "" + (row-7), 1);	
							
							cell = cells.getCell("B" + (row_begin + 1));
							style = cell.getStyle();
							style.setHAlignment(TextAlignmentType.CENTER);
							cell.setStyle(style);
							ReportAPI.getCellStyle(cell, Color.BLACK, true, 11, rs.getString("SPID"), 0);

							cell = cells.getCell("C" + (row_begin + 1));
							style = cell.getStyle();
							style.setHAlignment(TextAlignmentType.CENTER);
							cell.setStyle(style);
							ReportAPI.getCellStyle(cell, Color.BLACK, true, 11, rs.getString("MA"), 0);

							cell = cells.getCell("D" + (row_begin + 1));
							ReportAPI.getCellStyle(cell, Color.BLACK, true, 11, rs.getString("TEN"), 0);

							cell = cells.getCell("E" + (row_begin + 1));
							style = cell.getStyle();
							style.setHAlignment(TextAlignmentType.CENTER);
							cell.setStyle(style);
							ReportAPI.getCellStyle(cell, Color.BLACK, true, 11, rs.getString("DONVI"), 0);

							cell = cells.getCell("F" + (row_begin + 1));
							style = cell.getStyle();
							style.setHAlignment(TextAlignmentType.RIGHT);							
							style.setNumber(3);
							font = style.getFont();
							font.setName("Times New Roman");
							font.setSize(11);
							style.setFont(font);
							cell.setStyle(style);
							cell.setValue(rs.getDouble("GIA"));

							cell = cells.getCell("G" + (row_begin + 1));
							style = cell.getStyle();
							style.setHAlignment(TextAlignmentType.RIGHT);							
							style.setNumber(3);
							font = style.getFont();
							font.setName("Times New Roman");
							font.setSize(11);
							style.setFont(font);
							cell.setStyle(style);
							cell.setValue(rs.getDouble("T1"));

							cell = cells.getCell("H" + (row_begin + 1));
							style = cell.getStyle();
							style.setHAlignment(TextAlignmentType.RIGHT);							
							style.setNumber(3);
							font = style.getFont();
							font.setName("Times New Roman");
							font.setSize(11);
							style.setFont(font);
							cell.setStyle(style);
							cell.setValue(rs.getDouble("T2"));

							cell = cells.getCell("I" + (row_begin + 1));
							style = cell.getStyle();
							style.setHAlignment(TextAlignmentType.RIGHT);							
							style.setNumber(3);
							font = style.getFont();
							font.setName("Times New Roman");
							font.setSize(11);
							style.setFont(font);
							cell.setStyle(style);
							cell.setValue(rs.getDouble("T3"));

							cell = cells.getCell("J" + (row_begin + 1));
							style = cell.getStyle();
							style.setHAlignment(TextAlignmentType.RIGHT);							
							style.setNumber(3);
							font = style.getFont();
							font.setName("Times New Roman");
							font.setSize(11);
							style.setFont(font);
							cell.setStyle(style);
							cell.setValue(rs.getDouble("T4"));

							cell = cells.getCell("K" + (row_begin + 1));
							style = cell.getStyle();
							style.setHAlignment(TextAlignmentType.RIGHT);							
							//style.setNumber(3);
							font = style.getFont();
							font.setName("Times New Roman");
							font.setSize(11);
							style.setFont(font);
							cell.setFormula("=SUM(G" + (row + 1) + ":J" + (row + 1) + ")");
							cell.setStyle(style); 
							
							cell = cells.getCell("L" + (row_begin + 1));
							style = cell.getStyle();
							style.setHAlignment(TextAlignmentType.RIGHT);							
							style.setNumber(3);
							font = style.getFont();
							font.setName("Times New Roman");
							font.setSize(11);
							style.setFont(font);
							cell.setStyle(style);							
							cell.setFormula("=SUM(G" + (row + 1)  + ":J" + (row + 1) + ")*F" + (row + 1) +"");
						}

					
					chungloai_bk = "";
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
		
		db.shutDown();
	}


	private void FillData_2(Workbook workbook, IStockintransit obj, String Id, int sheet, String khoId, String thang, String nam) {
		
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		Cell cell;

		// THÊM SẢN PHẨM
		dbutils db = new dbutils();
		String query = "";
		if(khoId.length() > 0){
			query =  		"SELECT SP.PK_SEQ AS SPID, SP.TEN, DVDL.DONVI, SP.MA, " +
							"ISNULL((SELECT GIABAN FROM ERP_BGBAN_SANPHAM WHERE BGBAN_FK IN (SELECT TOP 1 PK_SEQ FROM ERP_BANGGIABAN WHERE SUDUNG = 1)  AND TRANGTHAI = 1 AND SANPHAM_FK = SP.PK_SEQ), 0) AS GIA, " +
							"ISNULL((SELECT T1 FROM ERP_DUBAOSANPHAMMTS_TUAN " +
							"WHERE SANPHAM_FK = SP.PK_SEQ AND DUBAO_FK = " + Id  + " AND THANG = " + thang + " AND NAM = " + nam + " AND KHO_FK = " + khoId + " ), 0) AS T1, " +
							
							"ISNULL((SELECT T2 FROM ERP_DUBAOSANPHAMMTS_TUAN " +
							"WHERE SANPHAM_FK = SP.PK_SEQ AND DUBAO_FK = " + Id + " AND THANG = " + thang + " AND NAM = " + nam + " AND KHO_FK = " + khoId + " ), 0) AS T2, " +
							
							"ISNULL((SELECT T3 FROM ERP_DUBAOSANPHAMMTS_TUAN " +
							"WHERE SANPHAM_FK = SP.PK_SEQ AND DUBAO_FK = " + Id + " AND THANG = " + thang + " AND NAM = " + nam + " AND KHO_FK = " + khoId + " ), 0) AS T3, " +
		 
							"ISNULL((SELECT T4 FROM ERP_DUBAOSANPHAMMTS_TUAN " +
							"WHERE SANPHAM_FK = SP.PK_SEQ AND DUBAO_FK = " + Id + " AND THANG = " + thang + " AND NAM = " + nam + " AND KHO_FK = " + khoId + " ), 0) AS T4 " +

							"FROM ERP_SANPHAM SP " +
							"INNER JOIN DONVIDOLUONG DVDL ON DVDL.PK_SEQ = SP.DVDL_FK " +
							"WHERE SP.TRANGTHAI =  1  AND SP.LOAISANPHAM_FK IN (100002, 100003) " +
							"ORDER BY SP.MA ASC ";
		}else{
			query =  		"SELECT SP.PK_SEQ AS SPID, SP.TEN, DVDL.DONVI, SP.MA, " +
							"ISNULL((SELECT GIABAN FROM ERP_BGBAN_SANPHAM WHERE BGBAN_FK IN (SELECT TOP 1 PK_SEQ FROM ERP_BANGGIABAN WHERE SUDUNG = 1)  AND TRANGTHAI = 1 AND SANPHAM_FK = SP.PK_SEQ), 0) AS GIA, " +
							"ISNULL((" +
							"	SELECT ISNULL(SUM(T1), 0) " +
							"	FROM ERP_DUBAOSANPHAMMTS_TUAN " +
							"	WHERE SANPHAM_FK = SP.PK_SEQ AND DUBAO_FK = " + Id  + " AND THANG = " + thang + " AND NAM = " + nam + " " +
							"), 0) AS T1, " +
			
							"ISNULL((" +
							"	SELECT ISNULL(SUM(T2), 0) " +
							"	FROM ERP_DUBAOSANPHAMMTS_TUAN " +
							"	WHERE SANPHAM_FK = SP.PK_SEQ AND DUBAO_FK = " + Id + " AND THANG = " + thang + " AND NAM = " + nam + "  " +
							"), 0) AS T2, " +
							
							"ISNULL((" +
							"	SELECT ISNULL(SUM(T3), 0) " +
							"	FROM ERP_DUBAOSANPHAMMTS_TUAN " +
							"	WHERE SANPHAM_FK = SP.PK_SEQ AND DUBAO_FK = " + Id + " AND THANG = " + thang + " AND NAM = " + nam + "  " +
							"), 0) AS T3, " +

							"ISNULL((" +
							"	SELECT ISNULL(SUM(T4), 0) " +
							"	FROM ERP_DUBAOSANPHAMMTS_TUAN " +
							"	WHERE SANPHAM_FK = SP.PK_SEQ AND DUBAO_FK = " + Id + " AND THANG = " + thang + " AND NAM = " + nam + "  " +
							"), 0) AS T4 " +
							
							"FROM ERP_SANPHAM SP " +
							"INNER JOIN DONVIDOLUONG DVDL ON DVDL.PK_SEQ = SP.DVDL_FK " +
							"WHERE SP.TRANGTHAI =  1   AND SP.LOAISANPHAM_FK IN (100002, 100003) " +
							"ORDER BY CL.PK_SEQ, SP.MA ASC ";
			
		}
		System.out.println(query);
		ResultSet rs = db.get(query);
		try{
			if(rs != null){
				int row = 8;
				int row_begin = 0;
				String chungloai_bk = "";
				Style style;
				worksheet = worksheets.getSheet(sheet);
				Cells cells = worksheet.getCells();
				
				cell = cells.getCell("F6");
				style = cell.getStyle();
				style.setHAlignment(TextAlignmentType.CENTER);
				cell.setStyle(style);
				ReportAPI.getCellStyle(cell, Color.BLUE, true, 11, "THÁNG " + thang + "." + nam, 1);
				int i = 1;
				while(rs.next()){
					
						//worksheet = worksheets.getSheet(sheet);
						//Cells cells = worksheet.getCells();
						
						if(chungloai_bk.length() == 0){
							row_begin = 0;
							
							cell = cells.getCell("A" + row);
							style = cell.getStyle();
							style.setHAlignment(TextAlignmentType.CENTER);
							cell.setStyle(style);
							ReportAPI.getCellStyle(cell, Color.BLACK, true, 11, "" + i, 0);

							cell = cells.getCell("B" + row);
							style = cell.getStyle();
							style.setHAlignment(TextAlignmentType.CENTER);
							cell.setStyle(style);
							ReportAPI.getCellStyle(cell, Color.BLACK, true, 11, rs.getString("SPID"), 0);

							cell = cells.getCell("C" + row);
							style = cell.getStyle();
							style.setHAlignment(TextAlignmentType.CENTER);
							cell.setStyle(style);
							ReportAPI.getCellStyle(cell, Color.BLACK, true, 11, rs.getString("MA"), 0);

							cell = cells.getCell("D" + row);
							ReportAPI.getCellStyle(cell, Color.BLACK, true, 11, rs.getString("TEN"), 0);

							cell = cells.getCell("E" + row);
							style = cell.getStyle();
							style.setHAlignment(TextAlignmentType.CENTER);							
							cell.setStyle(style);
							ReportAPI.getCellStyle(cell, Color.BLACK, true, 11, rs.getString("DONVI"), 0);
							
							cell = cells.getCell("F" + row);
							style = cell.getStyle();
							style.setHAlignment(TextAlignmentType.RIGHT);	
							style.setNumber(3);
							cell.setStyle(style);
							cell.setValue(rs.getDouble("GIA"));

							cell = cells.getCell("G" + row);
							style = cell.getStyle();
							style.setHAlignment(TextAlignmentType.RIGHT);							
							style.setNumber(3);
							cell.setStyle(style);
							cell.setValue(rs.getDouble("T1"));

							cell = cells.getCell("H" + row);
							style = cell.getStyle();
							style.setHAlignment(TextAlignmentType.RIGHT);							
							style.setNumber(3);
							cell.setStyle(style);
							cell.setValue( rs.getDouble("T2"));

							cell = cells.getCell("I" + row);
							style = cell.getStyle();
							style.setHAlignment(TextAlignmentType.RIGHT);							
							style.setNumber(3);
							cell.setStyle(style);
							cell.setValue(rs.getDouble("T3"));

							cell = cells.getCell("J" + row);
							style = cell.getStyle();
							style.setHAlignment(TextAlignmentType.RIGHT);							
							style.setNumber(3);
							cell.setStyle(style);
							cell.setValue( rs.getDouble("T4"));

							cell = cells.getCell("K" + row);
							style = cell.getStyle();
							style.setHAlignment(TextAlignmentType.RIGHT);							
							style.setNumber(3);
							cell.setStyle(style);
							//cell.setValue((rs.getDouble("T1") + rs.getDouble("T2") + rs.getDouble("T3") + rs.getDouble("T4")));
							cell.setFormula("=SUM(G" + row + ":J" + row  + ")");

							cell = cells.getCell("L" + row);
							style = cell.getStyle();
							style.setHAlignment(TextAlignmentType.RIGHT);							
							style.setNumber(3);
							
							cell.setStyle(style);
							//cell.setValue((rs.getDouble("T1") + rs.getDouble("T2") + rs.getDouble("T3") + rs.getDouble("T4"))*rs.getDouble("GIA"));
							cell.setFormula("=SUM(G" + row + ":J" + row  + ")*F" + row + "");
							
						}else{
							row_begin = row;
							cells.merge(row - 1, 0, row - 1, 10);
							cell = cells.getCell("A" + row);
							setCategoryStyle(cells, cell);
							ReportAPI.getCellStyle(cell, Color.WHITE, true, 12, "" + i, 1);	
							

							cell = cells.getCell("B" + (row_begin + 1));
							style = cell.getStyle();
							style.setHAlignment(TextAlignmentType.CENTER);
							cell.setStyle(style);
							ReportAPI.getCellStyle(cell, Color.BLACK, true, 11, rs.getString("SPID"), 0);

							cell = cells.getCell("C" + (row_begin + 1));
							style = cell.getStyle();
							style.setHAlignment(TextAlignmentType.CENTER);
							cell.setStyle(style);
							ReportAPI.getCellStyle(cell, Color.BLACK, true, 11, rs.getString("MA"), 0);

							cell = cells.getCell("D" + (row_begin + 1));
							ReportAPI.getCellStyle(cell, Color.BLACK, true, 11, rs.getString("TEN"), 0);

							cell = cells.getCell("E" + (row_begin + 1));
							style = cell.getStyle();
							style.setHAlignment(TextAlignmentType.CENTER);
							cell.setStyle(style);
							ReportAPI.getCellStyle(cell, Color.BLACK, true, 11, rs.getString("DONVI"), 0);

							cell = cells.getCell("F" + (row_begin + 1));
							style = cell.getStyle();
							style.setHAlignment(TextAlignmentType.RIGHT);							
							style.setNumber(3);
							cell.setStyle(style);
							cell.setValue(rs.getDouble("GIA"));

							cell = cells.getCell("G" + (row_begin + 1));
							style = cell.getStyle();
							style.setHAlignment(TextAlignmentType.RIGHT);							
							cell.setStyle(style);
							cell.setValue(rs.getDouble("T1"));

							cell = cells.getCell("H" + (row_begin + 1));
							style = cell.getStyle();
							style.setHAlignment(TextAlignmentType.RIGHT);							
							style.setNumber(3);
							cell.setStyle(style);
							cell.setValue(rs.getDouble("T2"));

							cell = cells.getCell("I" + (row_begin + 1));
							style = cell.getStyle();
							style.setHAlignment(TextAlignmentType.RIGHT);							
							style.setNumber(3);
							cell.setStyle(style);
							cell.setValue(rs.getDouble("T3"));

							cell = cells.getCell("J" + (row_begin + 1));
							style = cell.getStyle();
							style.setHAlignment(TextAlignmentType.RIGHT);							
							style.setNumber(3);
							cell.setStyle(style);
							cell.setValue(rs.getDouble("T4"));

							cell = cells.getCell("K" + (row_begin + 1));
							style = cell.getStyle();
							style.setHAlignment(TextAlignmentType.RIGHT);							
							style.setNumber(3);
							cell.setStyle(style);
							//cell.setValue((rs.getDouble("T1") + rs.getDouble("T2") + rs.getDouble("T3") + rs.getDouble("T4")));
							cell.setFormula("=SUM(G" + (row + 1) + ":J" + (row + 1) + ")");

							cell = cells.getCell("L" + (row_begin + 1));
							style = cell.getStyle();
							style.setHAlignment(TextAlignmentType.RIGHT);							
							style.setNumber(3);
							cell.setStyle(style);
							//cell.setValue((rs.getDouble("T1") + rs.getDouble("T2") + rs.getDouble("T3") + rs.getDouble("T4"))*rs.getDouble("GIA"));
							
							cell.setFormula("=SUM(G" + (row + 1) + ":J" + (row + 1) + ")*F"  + (row + 1) + "");
						}

					
					chungloai_bk = "";
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
		db.shutDown();
		
		
	}

	void getRequestEcrypt(Utility util, IDubaokinhdoanh_Giay dbkdBean, MultipartRequest multirequest, HttpServletRequest request, int i)
	{

		String loai = "";
		loai = util.antiSQLInspection(request.getParameter("loai"));
		if(loai == null) loai = "1";
		dbkdBean.setLoai(loai);
		
		System.out.println("Loại: " + loai);
		
		String thang = "";
		thang = util.antiSQLInspection(request.getParameter("thang"));
		if(thang == null) thang = "";
		dbkdBean.setThang(thang);
		
		System.out.println("thang: " + thang);

		String chungloai = "";
		if(i == 0){
			chungloai = util.antiSQLInspection(request.getParameter("chungloai"));
		}else{
			chungloai = util.antiSQLInspection(multirequest.getParameter("chungloai"));
		}
		if (chungloai == null)
			chungloai = "";
		dbkdBean.setChungloai(chungloai);

		String diengiai = "";
		
		if(i == 0){
			diengiai = util.antiSQLInspection(request.getParameter("diengiai"));
		}else{
			diengiai = util.antiSQLInspection(multirequest.getParameter("diengiai"));
		}
		if (diengiai == null)
			diengiai = "";
		dbkdBean.setDiengiai(diengiai);

		String filename = "" ;
		if(i == 0){
			filename = request.getParameter("filename");
		}else{
			filename = multirequest.getParameter("filename");
		}
		if (filename == null)
			filename = "";

		String sothangdubao = "";
		if(i == 0){
			sothangdubao = util.antiSQLInspection(request.getParameter("sothangdubao"));
		}else{
			sothangdubao = util.antiSQLInspection(multirequest.getParameter("sothangdubao"));
		}

		if (sothangdubao == null)
			sothangdubao = "";
		dbkdBean.setSothangdubao(sothangdubao);

		String hieuluc = "";
		if(i == 0){
			hieuluc = util.antiSQLInspection(request.getParameter("hieuluc"));
		}else{
			hieuluc = util.antiSQLInspection(multirequest.getParameter("hieuluc"));
		}
		if(hieuluc == null)
			hieuluc = "0";
		else
			hieuluc = "1";
		
		dbkdBean.setHieuluc(hieuluc);
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
	

	public void readExcel(IDubaokinhdoanh_Giay dbkdBean, String fileName, HttpServletResponse response) throws IOException
	{
		dbutils db = new dbutils();
		
		OutputStream out = response.getOutputStream();
		FileInputStream fstream = null;
		Workbook workbook = new Workbook();
		
		fstream = new FileInputStream(fileName);
		workbook.open(fstream);
		workbook.setFileFormatType(FileFormatType.EXCEL2007);
		
		Worksheets worksheets = workbook.getWorksheets();		

		String DUBAO_FK = dbkdBean.getId();
		
		//DÒNG VÀ CỘT TÍNH TỪ 0
		
		System.out.println("BẮT ĐẦU ĐỌC FILE");
		
		//LẤY THÁNG VÀ NĂM		
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
//		Calendar c = Calendar.getInstance();
		
		//MẶC ĐỊNH LẤY 3 THÁNG TIẾP THEO
		//int soThang = Integer.parseInt(dbkdBean.getSothangdubao());
		int soThang = 4;
		String[] Thang = new String[12];
		String[] Nam = new String[12];

//		try {
//			c.setTime(sdf.parse(dbkdBean.getNgaydubao()));
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
		String query = "SELECT NGAYDUBAO FROM ERP_DUBAO WHERE PK_SEQ =  " + dbkdBean.getId();
		ResultSet rs = db.get(query);
		String ngaydubao = "";
		try{
			if(rs != null){
				rs.next();
				ngaydubao = rs.getString("NGAYDUBAO");
				rs.close();
			}
		}catch(java.sql.SQLException e){}
		int thang = Integer.parseInt(ngaydubao.substring(5, 7));
		int nam = Integer.parseInt(ngaydubao.substring(0, 4));

		for( int i = 0; i < soThang; i++)
		{

			if(thang == 12){
				thang = 1;
				nam = nam + 1;
			}else{
				thang = thang + 1;
			}
			
			Thang[i] = "" + thang;
			Nam[i] = "" + nam;
 			
			System.out.print(Thang[i]+"-"+Nam[i]+" ## ");
		}
		
		//FILE BẮT ĐẦU CHỨA DỮ LIỆU TỪ DÒNG SỐ 8 - CÓ 3 SHEET
		try
		{
			//db.getConnection().setAutoCommit(false);
			String sql = "";
			for(int j = 0; j < 3 ; j++)
			{
				String KHOTT_FK="";
				switch(j)
				{
					case 0:KHOTT_FK="100023";
							break;
					case 1:KHOTT_FK="100041";
							break;
					case 2:KHOTT_FK="100034";
							break;
				}
				
			
				Worksheet readSheet = worksheets.getSheet(j);
				
				//LẤY SỐ DÒNG CỦA SHEET ĐANG ĐỌC. TÍNH TỪ 0.			
				int readSheetRow = readSheet.getLastRowIndex() + 1;
				System.out.println("\nĐỌC SHEET: "+readSheet.getName()+" - " + KHOTT_FK);
				
				for(int i = 7; i < readSheetRow;i++)
				{
					//CỘT 1 - MÃ SẢN PHẨM
					//CỘT 4 - ĐƠN GIÁ
					//CỘT 8 - SỐ LƯỢNg
					String maSP =  readSheet.getCell(i, 1).getStringValue();
					String donGIA =  readSheet.getCell(i, 5).getStringValue().replaceAll(",", "");		
					
					if(maSP.length()> 0)
					{
						for( int k = 0; k < soThang; k++)
						{
							String soLUONG =  readSheet.getCell(i, 6 + k).getStringValue().replaceAll(",", "");
							System.out.println(DUBAO_FK+" - "+maSP + " - " + donGIA +" - "+ soLUONG+" - "+Thang[k] + " - " + Nam[k]+" - " + KHOTT_FK);
							sql = 	"UPDATE ERP_DUBAOSANPHAMMTS SET DUKIENBAN = " + soLUONG + ", GIA = " + donGIA + "" +
									" WHERE DUBAO_FK = " + DUBAO_FK + " AND SANPHAM_FK = " + maSP + " " +
									"AND THANG = " +Thang[k] + " AND NAM = " + Nam[k]+ " AND KHO_FK = " + KHOTT_FK + "  ";

							System.out.println(sql);
							db.update(sql);

							sql = 	"UPDATE ERP_DUBAOSANPHAMMTS_TUAN " +
									"SET T1 = " + Double.parseDouble(soLUONG)/4 + ",  T2 = " + Double.parseDouble(soLUONG)/4 + ", " +
									"	 T3 = " + Double.parseDouble(soLUONG)/4 + ", T4 = " + Double.parseDouble(soLUONG)/4 + " " +
									"WHERE DUBAO_FK = " + DUBAO_FK + " AND SANPHAM_FK = " + maSP + " " +
									"AND THANG = " +Thang[k] + " AND NAM = " + Nam[k]+ " AND KHO_FK = " + KHOTT_FK + "  ";
							System.out.println(sql);
							db.update(sql);

						}
					}
				}

				System.out.println("KẾT THÚC ĐỌC SHEET: "+readSheet.getName());
			}
			
			//db.getConnection().commit();
			//db.getConnection().setAutoCommit(true);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
			
		db.shutDown();
	
		System.out.println("KẾT THÚC ĐỌC FILE");
		
	}

	public void readExcel_2(IDubaokinhdoanh_Giay dbkdBean, String fileName, HttpServletResponse response) throws IOException
	{
		dbutils db = new dbutils();
		
		OutputStream out = response.getOutputStream();
		FileInputStream fstream = null;
		Workbook workbook = new Workbook();
		
		fstream = new FileInputStream(fileName);
		workbook.open(fstream);
		workbook.setFileFormatType(FileFormatType.EXCEL2007);
		
		Worksheets worksheets = workbook.getWorksheets();		

		String DUBAO_FK = dbkdBean.getId();
		
		//DÒNG VÀ CỘT TÍNH TỪ 0
		
		System.out.println("BẮT ĐẦU ĐỌC FILE");
		
		//LẤY THÁNG VÀ NĂM		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
		Calendar c = Calendar.getInstance();
		
		//MẶC ĐỊNH LẤY 3 THÁNG TIẾP THEO
		//int soThang = Integer.parseInt(dbkdBean.getSothangdubao());

		try {
			c.setTime(sdf.parse(dbkdBean.getNgaydubao()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		
		//FILE BẮT ĐẦU CHỨA DỮ LIỆU TỪ DÒNG SỐ 8 - CÓ 3 SHEET
		try
		{
			db.getConnection().setAutoCommit(false);
			String sql = "";
			for(int j = 0; j < 3 ; j++)
			{
				String KHOTT_FK="";
				switch(j)
				{
					case 0:KHOTT_FK="100023";
							break;
					case 1:KHOTT_FK="100041";
							break;
					case 2:KHOTT_FK="100034";
							break;
				}
				
			
				Worksheet readSheet = worksheets.getSheet(j);
				
				//LẤY SỐ DÒNG CỦA SHEET ĐANG ĐỌC. TÍNH TỪ 0.			
				int readSheetRow = readSheet.getLastRowIndex() + 1;
				System.out.println("\nĐỌC SHEET: "+readSheet.getName()+" - "+KHOTT_FK);
				String thangnam = readSheet.getCell(5, 5).getStringValue().trim();
				String thang = thangnam.substring(thangnam.indexOf(" ") + 1, thangnam.indexOf("."));
				String nam = thangnam.substring(thangnam.indexOf(".") + 1, thangnam.length());
				dbkdBean.setThang(thang + "." + nam);
				dbkdBean.setLoai("2");
				
				for(int i = 7; i < readSheetRow;i++)
				{
					//CỘT 1 - MÃ SẢN PHẨM
					//CỘT 4 - ĐƠN GIÁ
					//CỘT 8 - SỐ LƯỢNg
					String maSP =  readSheet.getCell(i, 1).getStringValue();
					String donGIA =  readSheet.getCell(i, 5).getStringValue();		
					
					if(maSP.length()>0)
					{
						
						String T1 =  readSheet.getCell(i, 6).getStringValue().replaceAll(",", "");
						String T2 =  readSheet.getCell(i, 7).getStringValue().replaceAll(",", "");
						String T3 =  readSheet.getCell(i, 8).getStringValue().replaceAll(",", "");
						String T4 =  readSheet.getCell(i, 9).getStringValue().replaceAll(",", "");
						
						sql = 	"UPDATE ERP_DUBAOSANPHAMMTS_TUAN " +
								"SET " +
								"T1 = " + T1 + ", " +
								"T2 = " + T2 + ", " +
								"T3 = " + T3 + ", " +
								"T4 = " + T4 + " " +
								"WHERE DUBAO_FK = " + DUBAO_FK + " AND SANPHAM_FK = " + maSP + " " +
								"AND THANG = " + thang + " AND NAM = " + nam + " AND KHO_FK = " + KHOTT_FK + " ";
						
						System.out.println(sql);
						db.update(sql);
						
						sql = 	"UPDATE ERP_DUBAOSANPHAMMTS  SET DUKIENBAN = " + (Double.parseDouble(T1) + Double.parseDouble(T2)+ Double.parseDouble(T3)+ Double.parseDouble(T4)) + " " +
								"WHERE DUBAO_FK = " + DUBAO_FK + " AND SANPHAM_FK = " + maSP + " " +
								"AND THANG = " + thang + " AND NAM = " + nam + " AND KHO_FK = " + KHOTT_FK + " ";
						
						System.out.println(sql);
						db.update(sql);
						
					}
	
				}
	
				
				System.out.println("KẾT THÚC ĐỌC SHEET: "+readSheet.getName());
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}
		catch(SQLException e)
		{
			
		}
			
		db.shutDown();
	
		System.out.println("KẾT THÚC ĐỌC FILE");
		
	}

}