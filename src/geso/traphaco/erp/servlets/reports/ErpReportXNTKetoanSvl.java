package geso.traphaco.erp.servlets.reports;

 

import geso.traphaco.distributor.util.Utility;
import geso.traphaco.erp.beans.stockintransit.IStockintransit;
import geso.traphaco.erp.beans.stockintransit.imp.Stockintransit;
import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.erp.servlets.baocao.ReportAPI;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.sql.Types;

import com.aspose.cells.BorderLineType;
import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Style;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

import java.util.*;

public class ErpReportXNTKetoanSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	public ErpReportXNTKetoanSvl()
	{
		super();
		
	}
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IStockintransit obj = new Stockintransit();
		Utility util = new Utility();
		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);
		String view = request.getParameter("view");
	    if(view == null)
	    	view = "";
	    obj.setView(view);
		obj.setuserId(userId);
		obj.setdiscount("1");
		obj.setvat("0");
		obj.setdvdlId("");
		obj.settype("1");
		obj.init_Khoaso();
		session.setAttribute("obj", obj);
		session.setAttribute("userId", userId);
		String nextJSP = "/TraphacoHYERP/pages/Erp/ReportXNTKetoan.jsp";
		response.sendRedirect(nextJSP);
	}
	
	private String getPiVotName()
	{
		String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
		String name = sdf.format(cal.getTime());
		name = name.replaceAll("-", "");
		name = name.replaceAll(" ", "_");
		name = name.replaceAll(":", "");
		return "_" + name;
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IStockintransit obj = new Stockintransit();
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		
		Utility util = new Utility();
		if (userId == null)
			userId = "";
		obj.setuserId(userId);
	 
		String view = request.getParameter("view");
	    if(view == null)
	    	view = "";
	    obj.setView(view);
	   
		 
		String tungay = request.getParameter("Sdays"); // <!---
		if (tungay == null)
			tungay = "";
		obj.settungay(tungay);
		
		String denngay = request.getParameter("Edays");// <!---
		if (denngay == null)
			denngay = "";
		obj.setdenngay(denngay);
		
		String ddkd = request.getParameter("ddkdId");// <!---
		if (ddkd == null)
			ddkd = "";
		obj.setDdkd(ddkd);
		System.out.println("ddkd id "+obj.getDdkd());
		
		String[] khoId = request.getParameterValues("khoId"); 
		String str = "";
		if (khoId != null)
		{
			for(int i = 0; i < khoId.length; i++)
				str += khoId[i] + ",";
			if(str.length() > 0)
				str = str.substring(0, str.length() - 1);
		}
	 
		obj.setkhoId(str);
 
		obj.init_Khoaso();
		
		if (!tungay.equals("") && !denngay.equals(""))
		{
			
			String action = request.getParameter("action");
			
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			if (action.equals("tao"))
			{
				response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "Attachment; filename=BaoCaoXuatNhapTon(NPP)" + this.getPiVotName() + ".xlsm");
				OutputStream out = response.getOutputStream();
				try
				{
					CreatePivotTable(out, response, request, obj); 
					
				} catch (Exception ex)
				{
					obj.init_Khoaso();
					obj.setMsg(ex.getMessage());

					session.setAttribute("obj", obj);
					session.setAttribute("userId", userId);
					String nextJSP = "/TraphacoHYERP/pages/Erp/ReportXNTKetoan.jsp";
					response.sendRedirect(nextJSP);
					
				}
			}else{
				obj.init_Khoaso();
				session.setAttribute("obj", obj);
				session.setAttribute("userId", userId);
				
				String nextJSP = "/TraphacoHYERP/pages/Erp/ReportXNTKetoan.jsp";
				response.sendRedirect(nextJSP);
			}
		}	
	}
	
	private void CreatePivotTable(OutputStream out, HttpServletResponse response, HttpServletRequest request,   IStockintransit obj) throws Exception
	{
		try
		{
			String strfstream = getServletContext().getInitParameter("path") + "\\BaoCaoXuatNhapTon(NPP).xlsm";
			 
			FileInputStream fstream = new FileInputStream(strfstream);
			Workbook workbook = new Workbook();
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
			Worksheets worksheets = workbook.getWorksheets();
	  		Worksheet worksheet_2 = worksheets.getSheet("sheet1");
	  		
	  		Cells cells = worksheet_2.getCells();
			   
	  		Cell cell = cells.getCell("P1");
			Style style1=cell.getStyle();
			
			FillData_Lo_Tong(workbook, obj);
			 
			workbook.save(out);
			fstream.close();
		} catch (Exception ex)
		{
			ex.printStackTrace();
			throw new Exception("Error Message: " + ex.getMessage());
		}
	}
 
 
	private void FillData_Lo_Tong(Workbook workbook,   IStockintransit obj) throws Exception
	{
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		worksheet.setName("Sheet1");
		Cells cells = worksheet.getCells();
		for(int i=0;i<30;i++){
			   cells.setColumnWidth(i, 20.0f);   
		}
		cells.setColumnWidth(3, 18.0f);
		cells.setColumnWidth(5, 18.0f);
		   
		cells.setRowHeight(0, 50.0f);
		
		Cell cell = cells.getCell("A1");
		ReportAPI.getCellStyle(cell, Color.RED, true, 14, "XUẤT NHẬP TỒN THEO LÔ");
		
		cell = cells.getCell("A3");
		ReportAPI.getCellStyle(cell, Color.NAVY, true, 10, "Từ ngày : " + obj.gettungay() + "   Đến ngày: " + obj.getdenngay());
		cell = cells.getCell("A4");
		ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Ngày tạo: " + obj.getDateTime());
		
		cell = cells.getCell("B4");
		ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Tạo bởi : " + obj.getuserTen());
		
		cell = cells.getCell("P1");
		Style style1=cell.getStyle();
		
		cell = cells.getCell("O1");
		Style style2=cell.getStyle();
		
			dbutils db = new dbutils();
		
			 
			String query = "delete kho_tmp";
			db.update(query);
			if(obj.getkhoId().length() > 0)
				query = "insert into kho_tmp(kho_fk) select pk_seq from erp_khott where pk_seq in (" + obj.getkhoId() + ")";
			else
				query = "insert into kho_tmp(kho_fk) select pk_seq from erp_khott where trangthai = 1 and loai not in (5,7)";
			
			
				db.update(query);
		 
				String[] param =  new String[5];
				param[0] = obj.gettungay().equals("") ? null : obj.gettungay();
				param[1] = obj.getdenngay().equals("") ? null : obj.getdenngay();
				param[2] = obj.getnppId().equals("") ? null : obj.getnppId();
				param[3] =param[1].substring(5, 7);
				param[4] =param[1].substring(0, 4);
			
				ResultSet rs =db.getRsByPro("REPORT_XNT_KETOAN", param);
			
				 this.TaoBaoCao(rs,worksheet,obj,"Xuất nhập tồn cuối kỳ khóa sổ",style2);
				 db.shutDown();
	}

	private void TaoBaoCao(  ResultSet rs,Worksheet worksheet, IStockintransit obj, String diengiai, Style style12) 
	{
		  try{
			  
			   Cells cells = worksheet.getCells();
			   
			   for(int i=0;i<30;i++){
				   cells.setColumnWidth(i, 20.0f);   
			   }
			
			    cells.setRowHeight(0, 50.0f);
			    Cell cell = cells.getCell("A1");
			    ReportAPI.getCellStyle(cell, Color.RED, true, 14, diengiai);
			    
			    cell = cells.getCell("A3");
				ReportAPI.getCellStyle(cell, Color.NAVY, true, 10, "Từ ngày : " + obj.gettungay() + "   Đến ngày: " + obj.getdenngay());
				cell = cells.getCell("A4");
				ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Ngày tạo: " + obj.getDateTime());
				
				cell = cells.getCell("B4");
				ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Tạo bởi : " + obj.getuserTen());

			   worksheet.setGridlinesVisible(false);
			   
			   ResultSetMetaData rsmd = rs.getMetaData();
			   int socottrongSql = rsmd.getColumnCount();
			   
			   int countRow = 4;

			   for( int i =1 ; i <=socottrongSql ; i ++  )
			   {
			    cell = cells.getCell(countRow,i-1 );
			    
		    	ReportAPI.setCellBackground(cell, Color.GRAY, BorderLineType.THIN, false, 0);
		    	
		    	ReportAPI.getCellStyle(cell, "Arial", Color.WHITE, true, 9, rsmd.getColumnName(i));
		    	
			   }
			   countRow ++;
			   
			  
			   while(rs.next())
			   {
			    for(int i = 1; i <= socottrongSql; i ++)
			    {
			     cell = cells.getCell(countRow,i-1 );
			     if(rsmd.getColumnType(i) == Types.DOUBLE || rsmd.getColumnType(i)==Types.NUMERIC || rsmd.getColumnType(i)==Types.INTEGER )
			     {
			    	 cell.setStyle(style12);
			    	 ReportAPI.getCellStyle_double(cell, "Arial", Color.BLACK, false, 9,  rs.getDouble(i));
			     // ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 41);
			     }
			     else
			     {
			    	 ReportAPI.getCellStyle(cell, "Arial", Color.BLACK, false, 9, rs.getString(i));
			    //  ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
			     }
			    }
			    ++countRow;
			   }
			   
			   if(rs!=null)rs.close();
			    

			 
			  }catch(Exception ex){
				  ex.printStackTrace();
				    
			  }
	}
 
}

