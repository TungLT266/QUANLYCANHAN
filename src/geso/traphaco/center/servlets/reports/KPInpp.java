package geso.traphaco.center.servlets.reports;

import geso.traphaco.center.beans.stockintransit.IStockintransit;
import geso.traphaco.center.beans.stockintransit.imp.Stockintransit;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.ConsolidationFunction;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Font;
import com.aspose.cells.PivotFieldType;
import com.aspose.cells.PivotTable;
import com.aspose.cells.PivotTableAutoFormatType;
import com.aspose.cells.PivotTables;
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;
import java.util.Hashtable;


public class KPInpp extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public KPInpp() {
        super();
       
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Utility util=new Utility();
	  
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
	//	PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		String userTen = (String)session.getAttribute("userTen");
		String querystring=request.getQueryString();
	
	
		IStockintransit obj = new Stockintransit();
		
	
		obj.setuserId(util.getUserId(querystring));
		obj.setuserTen(userTen);
		obj.setnppId(util.getIdNhapp(obj.getuserId()));
		obj.settype("1");
		session.setAttribute("obj", obj);
		
		session.setAttribute("userId", obj.getuserId());
		session.setAttribute("userTen", obj.getuserTen());
		session.setAttribute("util", util);		
		String nextJSP = "/TraphacoHYERP/pages/Center/KPInpp.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		OutputStream out = response.getOutputStream(); 
		Utility util=new Utility();
	    String userTen;
	    String tungay;
	    String denngay;
	    String userId;
		IStockintransit obj = new Stockintransit();
		HttpSession session = request.getSession();
		userTen = 	(String)session.getAttribute("userTen");
		tungay	=	util.antiSQLInspection(request.getParameter("tungay"));
		denngay	=	util.antiSQLInspection(request.getParameter("denngay"));
		userId 	= 	(String)session.getAttribute("userId");
		obj.setuserId(userId);
		obj.setuserTen(userTen);
		obj.setdenngay(denngay);
		obj.settungay(tungay);
		obj.setnppId(util.getIdNhapp(obj.getuserId()));
		//System.out.println("Tu thang la :"+request.getParameter("tuthang"));
		String tuthang=request.getParameter("tuthang").length()< 2 ? ("0"+request.getParameter("tuthang")) :request.getParameter("tuthang") ;
		String toithang=request.getParameter("denthang").length()< 2 ? ("0"+request.getParameter("denthang")) :request.getParameter("denthang") ;
		obj.setFromMonth(tuthang);
		
		obj.setToMonth(toithang);
			obj.setToYear(request.getParameter("dennam"));
			obj.setFromYear(request.getParameter("tunam"));
		
		 obj.settype(request.getParameter("typeid"));
		try
		    {
			
			response.setContentType("application/xlsm");
			response.setHeader("Content-Disposition", "attachment; filename=KPI.xlsm");
			CreatePivotTable(out,response,request, obj);
			
	     }
	    catch (Exception ex)
	    {
	    	obj.setMsg("Loi Khong Tao Duoc Bao Cao : "+ex.toString());
	    	session.setAttribute("obj", obj);		
			session.setAttribute("userId", obj.getuserId());
			session.setAttribute("userTen", obj.getuserTen());
			
			session.setAttribute("util", util);	
	 		String nextJSP = "/TraphacoHYERP/pages/Center/KPInpp.jsp";
	 		response.sendRedirect(nextJSP);
	    }
	    obj.DBclose();
	}
	private void CreatePivotTable(OutputStream out,HttpServletResponse response,HttpServletRequest request,IStockintransit obj) throws IOException
    {    
		//khoi tao de viet pivottable
		//buoc 1
		String chuoi = getServletContext().getInitParameter("path") + "\\KPINPP.xlsm";
	    FileInputStream fstream = new FileInputStream(chuoi);
   		Workbook workbook = new Workbook();
		workbook.open(fstream);
		workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
 
	    CreateStaticHeader(workbook,obj);
 
	    
	  
	   boolean bfalse= CreateStaticData(workbook, obj);
	    if(bfalse==false){
	    
	    	HttpSession session = request.getSession();
	    	session.setAttribute("obj", obj);		
			session.setAttribute("userId", obj.getuserId());
			session.setAttribute("userTen", obj.getuserTen());
			
	 		String nextJSP = "/TraphacoHYERP/pages/Center/KPInpp.jsp";
	 		response.sendRedirect(nextJSP);
	     }else{
	    	 //Saving the Excel file
	    	 workbook.save(out);
	     }
	    fstream.close();
   }
	
	private void CreateStaticHeader(Workbook workbook,IStockintransit obj) 
	{
		
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		worksheet.setName("Sheet1");

		Cells cells = worksheet.getCells();

		cells.setRowHeight(0, 20.0f);
		Cell cell = cells.getCell("A1");
	    cell.setValue("CHỈ SỐ THÀNH TÍCH CỦA NHÂN VIÊN BÁN HÀNG");   	
	    
	    cells.setRowHeight(2, 18.0f);
	
	    cell = cells.getCell("A3");
	    getCellStyle(workbook,"A3",Color.NAVY,true,9);	
	    
	    if(obj.gettype().equals("1")){
	    	 cell.setValue("Từ tháng: " +obj.getFromYear()+"-"+ obj.getFromMonth());  
	    
	    }else{
	    	cell.setValue("Từ ngày: " + obj.gettungay());	
	    }
	    
	    cells.setRowHeight(3, 18.0f);
	    cell = cells.getCell("B3"); getCellStyle(workbook,"B3",Color.NAVY,true,9);	
	   if(obj.gettype().equals("1")){
			cell.setValue("Đến tháng: " + obj.getToYear()+"-"+obj.getToMonth());
	   }else{
	    cell.setValue("Đến ngày: " + obj.getdenngay());    
	   }   
	    
	    cells.setRowHeight(4, 18.0f);
	    cell = cells.getCell("A4");getCellStyle(workbook,"A4",Color.NAVY,true,9);
	    cell.setValue("Ngày báo cáo: " + this.getDate());
	    
	    cells.setRowHeight(5, 18.0f);
	    cell = cells.getCell("A5");getCellStyle(workbook,"A5",Color.NAVY,true,9);
	    cell.setValue("Được tạo bởi Nhà phân phối:  " + obj.getuserTen());
 
	    cell = cells.getCell("AA1"); cell.setValue("Don Vi Kinh Doanh");
	    cell = cells.getCell("AB1"); cell.setValue("Dai Dien Kinh Doanh");
	    cell = cells.getCell("AC1"); cell.setValue("Ngay");
	    cell = cells.getCell("AD1"); cell.setValue("Cua Hieu Quan Ly");	  
	    cell = cells.getCell("AE1"); cell.setValue("Cua Hieu Co Doanh So");
	    cell = cells.getCell("AF1"); cell.setValue("Tong Don Hang");
	    cell = cells.getCell("AG1"); cell.setValue("Mat Hang Ban Duoc");
	    cell = cells.getCell("AH1"); cell.setValue("Mat Hang Phan Phoi(SKU)");
	    cell = cells.getCell("AI1"); cell.setValue("Tong Doanh So");
	    cell = cells.getCell("AJ1"); cell.setValue("Don Hang Tra Ve");	    
	 
	}
	private boolean CreateStaticData(Workbook workbook,IStockintransit obj) 
	{
		dbutils db = new dbutils();
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	    Cells cells = worksheet.getCells();
	    
		ResultSet rs1;
		
		if(obj.gettype().equals("1")){
			
			 String denngay=obj.getToYear()+"-"+obj.getToMonth()+"-31";
			   String tungay= obj.getFromYear()+"-"+obj.getFromMonth()+"-01";
		    	 String[] Param={tungay,denngay,obj.getDdkd(),obj.getnppId(),obj.getvungId(),obj.getkhuvucId(),obj.getgsbhId(),obj.getkenhId(),obj.getuserId(),"0",obj.getdvkdId()};
		    
		    	rs1=db.getRsByPro("getRsKpi_Month_report", Param);
		    	 
		}else{
			String[] Param = {obj.gettungay(),obj.getdenngay(), "", obj.getnppId(), "", "", "","",obj.getuserId(),"0",""};
		 rs1 = db.getRsByPro("getRsKpi_report", Param);
		 
		}
 

		int i = 2;
		if(rs1!=null)
		{
			
			try 
			{ 
				cells.setColumnWidth(0, 15.0f);
				cells.setColumnWidth(1, 20.0f);
				cells.setColumnWidth(2, 20.0f);
				cells.setColumnWidth(3, 20.0f);
				cells.setColumnWidth(4, 20.0f);			
				cells.setColumnWidth(5, 20.0f);	
				cells.setColumnWidth(6, 20.0f);	
				cells.setColumnWidth(7, 20.0f);
				cells.setColumnWidth(8, 20.0f);
				cells.setColumnWidth(8, 20.0f);
				cells.setRowHeight(12, 20.0f);
				
				Cell cell = null;
								
				Style style;
			while(rs1.next())// lap den cuoi bang du lieu
				{ 
				 
					
					String SalesRep = rs1.getString("DDKDTEN");
					
					String BusinessUnit =rs1.getString("DVKDTEN");
			 
					String SKU =	"0"; 
					
				 
				 
		 
					
					String Outlet ="0";
					
					Outlet = rs1.getString("SOCUAHIEU");
						
					String OutletHaveVolume = "0";
					
					OutletHaveVolume = rs1.getString("SOCHCODS");
				
					String Order ="0";
					
					Order = rs1.getString("SODH");

					String	OrderReturn = rs1.getString("SODHTRAVE");
					String	SoldSKU = rs1.getString("SOLDSKU");				
					String date = rs1.getString("NGAY");
					
					cell = cells.getCell("AA" + Integer.toString(i)); cell.setValue(BusinessUnit); 						//0
					cell = cells.getCell("AB" + Integer.toString(i)); cell.setValue(SalesRep);							//1					
					cell = cells.getCell("AC" + Integer.toString(i)); cell.setValue(date);								//2		
					cell = cells.getCell("AD" + Integer.toString(i)); cell.setValue(Float.parseFloat(Outlet)); 			//3
 
					cell = cells.getCell("AE" + Integer.toString(i)); cell.setValue(Float.parseFloat(OutletHaveVolume));//4
  
					cell = cells.getCell("AF" + Integer.toString(i)); cell.setValue(Float.parseFloat(Order));			//5
  
					cell = cells.getCell("AG" + Integer.toString(i)); cell.setValue(Float.parseFloat(SoldSKU));			//6
 
					
					cell = cells.getCell("AH" + Integer.toString(i)); cell.setValue(rs1.getDouble("SKU"));				//7
 
					cell = cells.getCell("AI" + Integer.toString(i)); cell.setValue(rs1.getDouble("DOANHSO"));		//8
 
					
					cell = cells.getCell("AJ" + Integer.toString(i)); cell.setValue(Float.parseFloat(OrderReturn));		//9
 
					i++;
				}
				if(rs1!=null){
					rs1.close();
					}
				
				if(db!=null){
					db.shutDown();
				}
			
			if(i==2){
				obj.setMsg("Khong CO Du Lieu TRong Thoi Gian Nay");
				return false;
			}
			
		
			 
 
				   return true;
					
			}
			catch (Exception e){
				e.printStackTrace();
				obj.setMsg("Error : Inventory Report : "+e.toString());
				return false;
			}
		}else{
			obj.setMsg("Error : Inventory Report :Khong Co Du Lieu Trong Thoi Gian Nay ");
			return false;
		}
	
	}

	private void getCellStyle(Workbook workbook, String a, Color mau, Boolean dam, int size)
	{
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	   	   
	    Cells cells = worksheet.getCells();
		Style style;
		Cell cell = cells.getCell(a); 
		 style = cell.getStyle();
	        Font font1 = new Font();
	        font1.setColor(mau);
	        font1.setBold(dam);
	        font1.setSize(size);
	        style.setFont(font1);
	        cell.setStyle(style);
	}
 
	private String getDate() 
	{
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy : hh-mm-ss");
        Date date = new Date();
        return dateFormat.format(date);	
	}
}
