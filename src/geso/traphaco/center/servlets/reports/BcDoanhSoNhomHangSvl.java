package geso.traphaco.center.servlets.reports;

import geso.traphaco.center.beans.report.IBcDoanhSoNhomHangList;
import geso.traphaco.center.beans.report.imp.BcDoanhSoNhomHangList;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.servlets.report.ReportAPI;
import geso.traphaco.distributor.util.Utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aspose.cells.BorderLineType;
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

@WebServlet("/BcDoanhSoNhomHangSvl")
public class BcDoanhSoNhomHangSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	public BcDoanhSoNhomHangSvl()
	{
		super();
		
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		IBcDoanhSoNhomHangList obj;
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		HttpSession session = request.getSession();
		
		Utility util = new Utility();
		
		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);
		
		if (userId.length() == 0)
			userId = util.antiSQLInspection(request.getParameter("userId"));
		
		String loaihoadon = request.getParameter("loaihoadon");
		if (loaihoadon == null)
			loaihoadon = "0";
		
		obj = new BcDoanhSoNhomHangList();
		obj.setUserId(userId);
		obj.setView("TT");
		String nextJSP = "";
		obj.init("");
		
		
		nextJSP = "/TraphacoHYERP/pages/Center/BcDoanhSoNhomHang.jsp";
		session.setAttribute("obj", obj);
		response.sendRedirect(nextJSP);
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		
		request.setCharacterEncoding("UTF-8");
    response.setCharacterEncoding("UTF-8");
    response.setContentType("text/html; charset=UTF-8");
    
    Utility util = new Utility();
    String userId = util.antiSQLInspection(request.getParameter("userId")); 
    HttpSession session = request.getSession();
    
    OutputStream out = response.getOutputStream();
    
    String action = request.getParameter("action");
    if (action == null)
    	action = "";
    
    IBcDoanhSoNhomHangList obj = new BcDoanhSoNhomHangList();
    obj.setUserId(userId);
    
    obj.setView("TT");
    
    String tungay =request.getParameter("Sdays")==null?"": request.getParameter("Sdays");
    obj.setTuNgay(tungay);
    
    String denngay = request.getParameter("Edays")==null?"": request.getParameter("Edays");
    obj.setDenNgay(denngay);
    
    String vungId = request.getParameter("vungId")==null?"": request.getParameter("vungId");
    obj.setVungId(vungId);

    String kbhId = request.getParameter("kbhId")==null?"": request.getParameter("kbhId");
    obj.setKbhId(kbhId);    
    

    String ttId = request.getParameter("ttId")==null?"": request.getParameter("ttId");
    obj.setTtId(ttId);   
    
    String nhomId = request.getParameter("nhomId")==null?"": request.getParameter("nhomId");
    obj.setNhomId(nhomId);
    
    
    String khId = request.getParameter("khId")==null?"": request.getParameter("khId");
    obj.setKhId(khId);
    
    String ddkdId =  request.getParameter("ddkdId")==null?"": request.getParameter("ddkdId");
    obj.setDdkdId(ddkdId);
    
    String spId =request.getParameter("spId")==null?"": request.getParameter("spId");
    obj.setSpId(spId);
    
    
    String nppId =request.getParameter("nppId")==null?"": request.getParameter("nppId");
    obj.setNppId(nppId);
	
    obj.setAction(action);
    
    System.out.println("___ATION "+action);
    
    
    if (action.equals("excel")  )
    {
    	try
	    { 
				response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "attachment; filename=BcDoanhSoNhomHang.xlsm");
	      FileInputStream fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\BcDoanhSoNhomHang.xlsm");
				Workbook workbook = new Workbook();
				workbook.open(fstream);
				workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
				obj.init("");
				CreateStaticHeader(workbook, obj);
				obj.setUserId(userId);
				String query=obj.getQueryHd();
				FillData(workbook,obj, query);
				workbook.save(out);
				fstream.close();
	    }
	    catch (Exception ex)
	    {
	    	ex.printStackTrace();
	        obj.setMsg("Khong the tao pivot.");
	    }
    	session.setAttribute("obj", obj);
  		session.setAttribute("userId", userId);
  		String nextJSP = "";
  		nextJSP = "/TraphacoHYERP/pages/Center/BcDoanhSoNhomHang.jsp";
  		response.sendRedirect(nextJSP); 
    }
    else  if(action.equals("view") || action.equals("next") || action.equals("prev")){
    	
    	obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
    	obj.setUserId(userId);
    	obj.init("");
    	
    	session.setAttribute("obj", obj);
    	response.sendRedirect("/TraphacoHYERP/pages/Center/BcDoanhSoNhomHang.jsp");
    }
    
    else if(action.equals("search"))
    {	
    	obj.setUserId(userId);
    	session.setAttribute("obj", obj);
  		session.setAttribute("userId", userId);
  		obj.init("");
  		String nextJSP = "";
  		nextJSP = "/TraphacoHYERP/pages/Center/BcDoanhSoNhomHang.jsp";
  		response.sendRedirect(nextJSP); 
    }
    else
    {
			session.setAttribute("obj", obj);
			String nextJSP = "";
			nextJSP = "/TraphacoHYERP/pages/Center/BcDoanhSoNhomHang.jsp";
			obj.init("");
			response.sendRedirect(nextJSP);  
    }
		
	}
	
	private boolean FillData(Workbook workbook, IBcDoanhSoNhomHangList obj, String query) throws Exception
	{
		dbutils db = new dbutils();
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);

		Cells cells = worksheet.getCells();		
		ResultSet hdRs = db.get(query);
		
		
		String[] spNhomId = obj.getSpNhomId();
		String[] spNhomTen = obj.getSpNhomTen();
		
		double DonGia_AVG=0;
		double total_BVAT=0;
		double total_AVAT=0;
		double total_VAT=0;
		int i = 9;
		int SoTt=1;
		
		if (hdRs != null) 
		{
			try 
			{
				Cell cell = null;

				while (hdRs.next()) 
				{					

					cell = cells.getCell("A" + Integer.toString(i));	cell.setValue(SoTt++ );
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					
					cell = cells.getCell("B" + Integer.toString(i));	cell.setValue(hdRs.getString("kvTEN"));
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					
					cell = cells.getCell("C" + Integer.toString(i));	cell.setValue(hdRs.getString("ttTEN"));
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					
					cell = cells.getCell("D" + Integer.toString(i));	cell.setValue(hdRs.getString("nppten"));
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					
					cell = cells.getCell("E" + Integer.toString(i));	cell.setValue(hdRs.getString("DDKD"));
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
				
					
					
				
					int lastColumn=6;
					int indexColumn=7;
					for(int ii=0;ii<spNhomId.length;ii++)
					{
						indexColumn=6;
						cell = cells.getCell(GetExcelColumnName(indexColumn+ii*2)+ Integer.toString(i));	cell.setValue(hdRs.getDouble(spNhomId[ii].trim()));ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 41);
						
						indexColumn=7;
						cell = cells.getCell(GetExcelColumnName(indexColumn+ii*2)+ Integer.toString(i));	cell.setValue( hdRs.getDouble(spNhomId[ii].trim()) / hdRs.getDouble("tongds")      );ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 10);
						
						lastColumn=indexColumn+ii*2+1;
					}
				
					cell = cells.getCell(GetExcelColumnName(lastColumn)+ Integer.toString(i));	cell.setValue(  hdRs.getDouble("tongds")      );ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 41);
			
					++i;					
				}
				
				
				if (hdRs != null) hdRs.close();
				
				if(db != null) db.shutDown();
				
				if(i==2){					
					throw new Exception("Xin loi,khong co bao cao voi dieu kien da chon....!!!");
				}
				  
			}catch(Exception ex){
				ex.printStackTrace();
				throw new Exception(ex.getMessage());
			}
		}else{
			return false;
		}		
		return true;
	  
  }

	private void CreateStaticHeader(Workbook workbook, IBcDoanhSoNhomHangList obj)
  {
			Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet(0);
			worksheet.setName("Sheet1");
			Cells cells = worksheet.getCells();
		
	    Style style;
	    Font font = new Font();
	    font.setColor(Color.RED);//mau chu
	    font.setSize(16);// size chu
	   	font.setBold(true);
	   	
	    cells.setRowHeight(0, 20.0f);
	    Cell cell = cells.getCell("A1");
	    style = cell.getStyle();
	    style.setFont(font); 
	    style.setHAlignment(TextAlignmentType.LEFT);// canh le cho chu 	        
	    
	    String tieude = "DOANH THU THEO NHÓM SẢN PHẨM";
	    ReportAPI.getCellStyle(cell,Color.RED, true, 14, tieude);
	    
			
	    cells.setRowHeight(3, 18.0f);
	    cell = cells.getCell("A3");
	    ReportAPI.getCellStyle(cell, Color.BLACK,true, 9, "Từ ngày : " + obj.getTuNgay() +  "Đến ngày : " + obj.getDenNgay() + "" );
	    
	    cells.setRowHeight(4, 18.0f);
	    cell = cells.getCell("A4");
	    ReportAPI.getCellStyle(cell,  Color.BLACK, true, 9, "Ngày báo cáo: " + ReportAPI.NOW("yyyy-MM-dd"));
	    
	    
	    cell = cells.getCell("A7");	cell.setValue("STT");
	    
	    ReportAPI.setCellBackground_Font(cell,  Color.GREEN, BorderLineType.THIN, true, 0,Color.WHITE);
	    ReportAPI.mergeCells(worksheet,6,7,0,0);
	    
	    cell = cells.getCell("B7");	cell.setValue("Miền");
	    ReportAPI.setCellBackground_Font(cell,  Color.GREEN, BorderLineType.THIN, true, 0,Color.WHITE);
	    ReportAPI.mergeCells(worksheet,6,7,1,1);
	   
	    cell = cells.getCell("C7");	cell.setValue("Địa bàn");
	    ReportAPI.setCellBackground_Font(cell,  Color.GREEN, BorderLineType.THIN, true, 0,Color.WHITE);
	    ReportAPI.mergeCells(worksheet,6,7,2,2);
		
	    cell = cells.getCell("D7");	cell.setValue("Chi nhánh/Đối tác");
	    ReportAPI.setCellBackground_Font(cell,  Color.GREEN, BorderLineType.THIN, true, 0,Color.WHITE);
	    ReportAPI.mergeCells(worksheet,6,7,3,3);
	    
	    cell = cells.getCell("E7");	cell.setValue("Trình dược viên");
	    ReportAPI.setCellBackground_Font(cell,  Color.GREEN, BorderLineType.THIN, true, 0,Color.WHITE);
	    ReportAPI.mergeCells(worksheet,6,7,4,4);
	  

		String[] spNhomId = obj.getSpNhomId();
		String[] spNhomTen = obj.getSpNhomTen();
		
		for(int i=0;i<spNhomId.length;i++)
		{
			int indexColumn=6;
			cell = cells.getCell(GetExcelColumnName(indexColumn+i*2)+ Integer.toString(7)); cell.setValue( spNhomTen[i]  );
			ReportAPI.setCellBackground_Font(cell,  Color.GREEN, BorderLineType.THIN, true, 0,Color.WHITE);
			ReportAPI.mergeCells(worksheet,6,6,indexColumn+(i*2)-1,indexColumn+(i*2+1)-1);
			style=cell.getStyle();
			style.setTextWrapped(true);
			style.setHAlignment(TextAlignmentType.CENTER);
			style.setVAlignment(TextAlignmentType.CENTER);
			cell.setStyle(style);
		}
		
		int LastColumn=6;
		for(int i=0;i<spNhomId.length;i++)
		{
			int indexColumn=6;
			cell = cells.getCell(GetExcelColumnName(indexColumn+(i*2))+ Integer.toString(8)); cell.setValue( "DS" );
			ReportAPI.setCellBackground_Font(cell,  Color.GREEN, BorderLineType.THIN, true, 0,Color.WHITE);
			
			indexColumn=7;
			cell = cells.getCell(GetExcelColumnName(indexColumn+i*2)+ Integer.toString(8)); cell.setValue( "Tỷ Lệ" );
			ReportAPI.setCellBackground_Font(cell, Color.GREEN, BorderLineType.THIN, true, 0,Color.WHITE);
			LastColumn=indexColumn+i*2+1;
		}
		
		cell = cells.getCell(GetExcelColumnName(LastColumn)+ Integer.toString(8)); cell.setValue( "DS Tổng" );
		ReportAPI.setCellBackground_Font(cell,  Color.GREEN, BorderLineType.THIN, true, 0,Color.WHITE);
		ReportAPI.mergeCells(worksheet,6,7,LastColumn-1,LastColumn-1);
		
		
		
  }
	
	private String GetExcelColumnName(int columnNumber)
	 {
	     int dividend = columnNumber;
	     String columnName = "";
	     int modulo;

	     while (dividend > 0)
	     {
	         modulo = (dividend - 1) % 26;
	         columnName = (char)(65 + modulo) + columnName;
	         dividend = (int)((dividend - modulo) / 26);
	     } 

	     return columnName;
	 }
	
}
