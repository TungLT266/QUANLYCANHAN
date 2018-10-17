package geso.traphaco.center.servlets.reports;

import geso.traphaco.center.servlets.report.ReportAPI;
import geso.traphaco.center.util.Utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aspose.cells.BorderLineType;
import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

import geso.traphaco.center.beans.stockintransit.IStockintransit;
import geso.traphaco.center.beans.stockintransit.imp.Stockintransit;
import geso.traphaco.center.db.sql.*;

public class AccumulatedPromotion extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public AccumulatedPromotion() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		Utility Ult = new Utility();
		IStockintransit obj = new Stockintransit();

		dbutils db = new dbutils();
		String sql = "select PK_SEQ, SCHEME, DIENGIAI from TIEUCHITHUONGTL where TRANGTHAI = '1' order by NAM desc";		
		ResultSet ctkmIds = db.get(sql);
		
		HttpSession session = request.getSession();
		String userTen = (String)session.getAttribute("userTen");
		String userId=	(String)session.getAttribute("userId");		
		session.setAttribute("userTen", userTen);
		session.setAttribute("userId", userId);
		session.setAttribute("baoloi", "");
		session.setAttribute("ctkmIds", ctkmIds);
		session.setAttribute("ctkmId", "");		
		obj.setuserId(userId);
		//obj.setnppId(userId);
		obj.init();
		session.setAttribute("obj", obj);
		String nextJSP = "/TraphacoHYERP/pages/Center/AccumulatedPromotion.jsp";
		response.sendRedirect(nextJSP);		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		OutputStream out = response.getOutputStream(); 
		Utility util = new Utility();
		String nextJSP = "/TraphacoHYERP/pages/Center/AccumulatedPromotion.jsp";
		
		IStockintransit obj = new Stockintransit();
		
		dbutils db = new dbutils();
		String sql = "select pk_seq, scheme from ctkhuyenmai where loaict='3'";		
		ResultSet ctkmIds = db.get(sql);
		
		try
		{
			HttpSession session = request.getSession();
			String userTen = (String)session.getAttribute("userTen");
			String userId=	(String)session.getAttribute("userId");
			String action = (String) util.antiSQLInspection(request.getParameter("action"));
			String ctkmId = util.antiSQLInspection(util.antiSQLInspection(request.getParameter("ctkmId")));
			if(userTen==null) 
				userTen ="";
			if(userId ==null) 
				userId ="";
			if(ctkmId ==null) 
				ctkmId ="";
			
			System.out.println(":::ACTION: " + action);
			
			obj.setuserId(userId!=null? userId:"");
			obj.setvungId(util.antiSQLInspection(request.getParameter("vungId"))!=null?util.antiSQLInspection(request.getParameter("vungId")):"");				
			obj.setkhuvucId(util.antiSQLInspection(request.getParameter("khuvucId"))!=null?util.antiSQLInspection(request.getParameter("khuvucId")):"");					
			obj.setnppId(util.antiSQLInspection(request.getParameter("nppId"))!=null?util.antiSQLInspection(request.getParameter("nppId")):"");
			
			if (action.equals("Taomoi")) 
			{	
				response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "attachment; filename=TheoDoiTraTichLuy.xlsm");

				CreatePivotTable(out, response, request, ctkmId, userId, userTen, obj);
			}
			else
			{
				obj.init();
				session.setAttribute("ctkmIds", ctkmIds);
				session.setAttribute("obj", obj);	
				response.sendRedirect(nextJSP);
			}
	    }
	    catch (Exception ex)
	    {
	        ex.printStackTrace();
	        // say sorry
	        response.setContentType("text/html");
	        PrintWriter writer = new PrintWriter(out);
	        writer.println("<html>");
	        writer.println("<head>");
	        writer.println("<title>sorry</title>");
	        writer.println("</head>");
	        writer.println("<body>");
	        writer.println("<h1>Xin loi, khong the tao pivot table...</h1>");
	        ex.printStackTrace(writer);
	        writer.println("</body>");
	        writer.println("</html>");
	        writer.close();
	    }
	    finally
	    {
	    	db.shutDown();
	    }
   }
	
	private void CreatePivotTable(OutputStream out,HttpServletResponse response,HttpServletRequest request, String ctkmId, String userId, String userTen, IStockintransit obj) throws IOException, SQLException
    {    
		System.out.println("::: VAO TAO PIVOT ");
		String chuoi =  getServletContext().getInitParameter("path") + "\\KhuyenMaiTichLuy.xlsm";
		FileInputStream fstream = new FileInputStream(chuoi);
			
		Workbook workbook = new Workbook();
		workbook.open(fstream);
		workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
	     
	     CreateStaticHeader(workbook, ctkmId, userId, userTen);
	     boolean result = CreateStaticData(workbook, ctkmId, userId, userTen, obj);
	     
	     if(result == false)
	     {
	    	dbutils db = new dbutils();
			String sql = "select PK_SEQ, SCHEME, DIENGIAI from TIEUCHITHUONGTL where TRANGTHAI = '1' order by NAM desc";		
			ResultSet ctkmIds = db.get(sql);
			
			HttpSession session = request.getSession();
			session.setAttribute("userTen", userTen);
			session.setAttribute("userId", userId);
			session.setAttribute("baoloi", "");
			
			String nextJSP = "/TraphacoHYERP/pages/Center/AccumulatedPromotion.jsp";
			response.sendRedirect(nextJSP);	
			
		    if(ctkmIds!=null)
			{
		    	ctkmIds.close();
		    }
	     }
	     else
	     {
	    	 workbook.save(out);
	     }
	     
   }
	
	private void CreateStaticHeader(Workbook workbook, String ctkmId, String userId, String userTen) {
	
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		worksheet.setName("Sheet1");

		Cells cells = worksheet.getCells();

		cells.setRowHeight(0, 20.0f);
		Cell cell = cells.getCell("A1");
	    cell.setValue("BC TRẢ KHUYẾN MẠI TÍCH LŨY");   	
	    
	    cells.setRowHeight(4, 18.0f);
	    cell = cells.getCell("A2");
	    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Ngày báo cáo: " + ReportAPI.NOW("yyyy-MM-dd"));
	    
	    cells.setRowHeight(5, 18.0f);
	    cell = cells.getCell("A3");
	    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Được tạo bởi:  " + userTen);
	    
	    cell = cells.getCell("A5"); cell.setValue("STT");
	    cell = cells.getCell("B5"); cell.setValue("SCHEME");
	    cell = cells.getCell("C5");cell.setValue("Mã khách hàng");
	    cell = cells.getCell("D5"); cell.setValue("Tên khách hàng");
	    cell = cells.getCell("E5"); cell.setValue("Địa chỉ");
	    cell = cells.getCell("F5"); cell.setValue("Số hợp đồng");
	  	cell = cells.getCell("G5"); cell.setValue("Ngày ký HĐ");
	  	cell = cells.getCell("H5"); cell.setValue("Ngày kết thúc HĐ");
	    cell = cells.getCell("I5"); cell.setValue("Doanh số đăng ký");
	    cell = cells.getCell("J5"); cell.setValue("% được hưởng");
	    cell = cells.getCell("K5"); cell.setValue("Doanh số đạt được");
	    cell = cells.getCell("L5"); cell.setValue("Số tiền đã trả thưởng");
	    cell = cells.getCell("M5"); cell.setValue("Hóa đơn, phiếu chi đã trả thưởng");

	}

	private boolean CreateStaticData(Workbook workbook, String ctkmId, String userId, String userTen, IStockintransit obj) 
	{
		dbutils db = new dbutils();
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		Cells cells = worksheet.getCells();

		String IdNpp="";
		boolean DuoiNPPLay=false;
		String sql="select pk_seq from nhanvien where trangthai=1 and phanloai='1' and pk_seq="+userId;
		ResultSet rs1=db.get(sql);
		try {
			if(rs1.next()){
				Utility util=new Utility();
				IdNpp=    util.getIdNhapp(userId);
				DuoiNPPLay=true;
			}
			rs1.close();
		} 
		catch (Exception e1) { }

		String query = "select * from ufn_trakhuyenmai_tichluy ( 106313, " + ctkmId + " ) ";
		System.out.println("1.Bao cao khuyen mai tich luy: " + query);
		ResultSet rs = db.get(query);

		try
		{
			ResultSetMetaData rsmd = rs.getMetaData();
			int socottrongSql = rsmd.getColumnCount();
			int countRow = 5;
			while(rs.next())
			{
				for(int i = 1; i <= socottrongSql; i++)
				{
					Color c = Color.WHITE;
					Cell cell = cells.getCell(countRow, i - 1 );
					
					if(rsmd.getColumnType(i) == Types.DOUBLE || rsmd.getColumnType(i) == Types.INTEGER || rsmd.getColumnType(i) == Types.DECIMAL )
					{
						cell.setValue(rs.getDouble(i));
						ReportAPI.setCellBackground(cell, c, BorderLineType.THIN, false, 41);
					}
					else
					{
						cell.setValue(rs.getString(i));
						ReportAPI.setCellBackground(cell, c, BorderLineType.THIN, false, 0);
					}
				}
				
				countRow ++;
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		/*int i = 6;
		if(rs!=null)
		{
			try 
			{
				for(int j = 0; j < 15; j++)
					cells.setColumnWidth(i, 15.0f);

				Cell cell = null;
				while(rs.next())
				{
					cell = cells.getCell("A" + Integer.toString(i)); 	cell.setValue(vung);
					cell = cells.getCell("B" + Integer.toString(i)); 	cell.setValue(khuvuc);
					cell = cells.getCell("C" + Integer.toString(i)); 	cell.setValue(scheme);
					cell = cells.getCell("D" + Integer.toString(i)); 	cell.setValue(maNpp);
					cell = cells.getCell("E" + Integer.toString(i)); 	cell.setValue(nppTen);
					cell = cells.getCell("F" + Integer.toString(i)); 	cell.setValue(maKh);				
					cell = cells.getCell("G" + Integer.toString(i)); 	cell.setValue(tenKh);
					cell = cells.getCell("H" + Integer.toString(i)); 	cell.setValue(diachi);
					cell = cells.getCell("I" + Integer.toString(i)); 	cell.setValue(dangky);
					cell = cells.getCell("J" + Integer.toString(i)); 	cell.setValue(soxuat);
					cell = cells.getCell("K" + Integer.toString(i)); 	cell.setValue(doanhso);
					cell = cells.getCell("L" + Integer.toString(i)); 	cell.setValue(tonggiatriKM);

					i++;
				}
				if(rs!=null)
					rs.close();
				if(db != null) 
					db.shutDown();
				if(i==2){
					throw new Exception("Khong co bao cao trong thoi gian nay...");
				}
			} 
			catch (Exception e) 
			{
				System.out.println("115.Error: " + e.getMessage());
			}
		} 
		else 
		{
			if(db != null) 
				db.shutDown();
			return false;
		}*/

		if(db != null) 
			db.shutDown();
		return true;
	}
	
	
}
