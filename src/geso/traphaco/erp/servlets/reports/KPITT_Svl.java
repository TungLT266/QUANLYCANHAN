package geso.traphaco.center.servlets.reports;


import geso.traphaco.center.beans.stockintransit.IStockintransit;
import geso.traphaco.center.beans.stockintransit.imp.Stockintransit;
import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.center.servlets.report.ReportAPI;
import geso.dms.distributor.util.Utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;

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
import com.aspose.cells.PivotField;
import com.aspose.cells.PivotFieldDataDisplayFormat;
import com.aspose.cells.PivotFieldSubtotalType;
import com.aspose.cells.PivotFieldType;
import com.aspose.cells.PivotTableAutoFormatType;
import com.aspose.cells.Style;

import com.aspose.cells.PivotTable;
import com.aspose.cells.PivotTables;


import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

public class KPITT_Svl extends HttpServlet {
	public KPITT_Svl() {
        super();
       
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IStockintransit obj = new Stockintransit();
		String querystring = request.getQueryString();
		
		Utility util = new Utility();
		obj.setuserId(util.getUserId(querystring));
		obj.setuserTen((String) session.getAttribute("userTen"));
		obj.setnppId(util.getIdNhapp(obj.getuserId()));
		
		
		obj.init();
		obj.settype("1");
		session.setAttribute("obj", obj);		
		session.setAttribute("userId", obj.getuserId());
		session.setAttribute("userTen", obj.getuserTen());
		String nextJSP = "/TraphacoHYERP/pages/Center/KPITT.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IStockintransit obj = new Stockintransit();
		OutputStream out = response.getOutputStream();
		Utility util = new Utility();
		
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		
		obj.settungay(util.antiSQLInspection(request.getParameter("Sdays")));
		obj.setdenngay(util.antiSQLInspection(request.getParameter("Edays")));
		
		
		obj.setuserId(userId!=null? userId:"");
		obj.setuserTen(userTen!=null? userTen:"");
		obj.setkenhId(request.getParameter("kenhId")!=null?
				util.antiSQLInspection(request.getParameter("kenhId")):"");
		
		obj.setvungId(request.getParameter("vungId")!=null?
				util.antiSQLInspection(request.getParameter("vungId")):"");
			
		obj.setkhuvucId(request.getParameter("khuvucId")!=null?
				util.antiSQLInspection(request.getParameter("khuvucId")):"");
		
		obj.setgsbhId(request.getParameter("gsbhs")!=null?
				util.antiSQLInspection(request.getParameter("gsbhs")):"");
		
		obj.setnppId(request.getParameter("nppId")!=null?
				util.antiSQLInspection(request.getParameter("nppId")):"");
		
		obj.setdvkdId(request.getParameter("dvkdId")!=null?
				util.antiSQLInspection(request.getParameter("dvkdId")):"");
		
		obj.setDdkd(request.getParameter("ddkdId")!=null?	
				util.antiSQLInspection(request.getParameter("ddkdId")):"");
		System.out.println("Tu thang la :"+request.getParameter("tuthang"));
		String tuthang=request.getParameter("tuthang").length()< 2 ? ("0"+request.getParameter("tuthang")) :request.getParameter("tuthang") ;
		
		
		String toithang=request.getParameter("denthang").length()< 2 ? ("0"+request.getParameter("denthang")) :request.getParameter("denthang") ;
		obj.setFromMonth(tuthang);
		
		obj.setToMonth(toithang);
			obj.setToYear(request.getParameter("dennam"));
			obj.setFromYear(request.getParameter("tunam"));
		
		 obj.settype(request.getParameter("typeid"));
		
		System.out.println(obj.gettype());
		 //truong hop bao cao thang type la 1.
		String action = request.getParameter("action")!=null? util.antiSQLInspection(request.getParameter("action")):"";
		String nextJSP = "/TraphacoHYERP/pages/Center/KPITT.jsp";
		
		if(action.equals("Taomoi")){
			try{
		
				response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "attachment; filename=KPITT.xlsm");
		        if(!CreatePivotTable(out,obj)){
		        
		        	
		        	
		        }
			}catch(Exception ex){
				obj.setMsg(ex.getMessage());
			}
		}else{			
			
		}
		obj.init();
		session.setAttribute("obj", obj);	
		response.sendRedirect(nextJSP);
	}

	private boolean CreatePivotTable(OutputStream out, IStockintransit obj)
			throws Exception {
		FileInputStream fstream;
	
			fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\KPITT.xlsm");
//			 fstream = new FileInputStream("D:\\Best Stable\\SalesUp\\WebContent\\pages\\Templates\\KPITT.xlsm");	
	
		Workbook workbook = new Workbook();
		workbook.open(fstream);
		workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);

		CreateStaticHeader(workbook, obj);

		boolean isFill = CreateStaticData(workbook, obj);
		
		if (!isFill){
			fstream.close();
			return false;
		}else {
			workbook.save(out);
			fstream.close();
			return true;
		}
	}

	private void CreateStaticHeader(Workbook workbook, IStockintransit obj) throws Exception 
	{
		try{
			
			Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet(0);
			worksheet.setName("Sheet1");

			Cells cells = worksheet.getCells();

			cells.setRowHeight(0, 20.0f);
			Cell cell = cells.getCell("A1");
		    cell.setValue("CHỈ SỐ THÀNH TÍCH CỦA ĐẠI DIỆN KINH DOANH");   	
		    
		    cells.setRowHeight(2, 18.0f);
		    cell = cells.getCell("A3"); 
		    getCellStyle(workbook,"A3",Color.NAVY,true,10);
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
		    cell.setValue("Ngày báo cáo: " + this.getDateTime());
		    
		    cells.setRowHeight(5, 18.0f);
		    cell = cells.getCell("A5");getCellStyle(workbook,"A5",Color.NAVY,true,9);
		    cell.setValue("Được tạo bởi Nhà phân phối:  " + obj.getuserTen());
					
		 	cell = cells.getCell("AA1"); cell.setValue("KenhBanHang");
		    cell = cells.getCell("AB1"); cell.setValue("DonViKinhDoanh");
		 	cell = cells.getCell("AC1"); cell.setValue("ChiNhanh");
		    cell = cells.getCell("AD1"); cell.setValue("KhuVuc");
		    cell = cells.getCell("AE1"); cell.setValue("GiamSat");
		    cell = cells.getCell("AF1"); cell.setValue("MaNhaPhanPhoi");	
		    cell = cells.getCell("AG1"); cell.setValue("NhaPhanPhoi");
		    cell = cells.getCell("AH1"); cell.setValue("DaiDienKinhDoanh");
		    if(obj.gettype().equals("1")){
			cell = cells.getCell("AI1"); cell.setValue("Thang");
		    }else{
		    	cell = cells.getCell("AI1"); cell.setValue("Ngay");
		    }
			cell = cells.getCell("AJ1"); cell.setValue("CuaHieuQuanLy");
			cell = cells.getCell("AK1"); cell.setValue("CuaHieuCoDoanhSo");
			cell = cells.getCell("AL1"); cell.setValue("SoDonHang");
			cell = cells.getCell("AM1"); cell.setValue("MatHangBanDuoc");
			cell = cells.getCell("AN1"); cell.setValue("MatHangPhanPhoi");
			cell = cells.getCell("AO1"); cell.setValue("DoanhSo");
			cell = cells.getCell("AP1"); cell.setValue("DonHangTraVe");
			

		}catch(Exception ex){
			throw new Exception("Bao cao bi loi khi khoi tao");
		}
		
	      
	}
	
	private boolean CreateStaticData(Workbook workbook,IStockintransit obj) throws Exception
	{
		dbutils db = new dbutils();
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	    Cells cells = worksheet.getCells();

	    ResultSet rs1;
	    
	    if(obj.gettype().equals("1")){
	    	
	   	 String denngay=obj.getToYear()+"-"+obj.getToMonth()+"-31";
		   String tungay= obj.getFromYear()+"-"+obj.getFromMonth()+"-01";
	    	 String[] Param={tungay,denngay,obj.getDdkd(),obj.getnppId(),obj.getvungId(),obj.getkhuvucId(),obj.getgsbhId(),obj.getkenhId(),obj.getuserId(),"1",obj.getdvkdId()};
	    
	    	rs1=db.getRsByPro("getRsKpi_Month_report", Param);
	    }else{
	    	String[] Param={obj.gettungay(),obj.getdenngay(),obj.getDdkd(),obj.getnppId(),obj.getvungId(),obj.getkhuvucId(),obj.getgsbhId(),obj.getkenhId(),obj.getuserId(),"1",obj.getdvkdId()};

	    rs1=db.getRsByPro("getRsKpi_report", Param);
	    }
		int i = 2;

		try{
			cells.setColumnWidth(0, 15.0f);
			cells.setColumnWidth(1, 15.0f);
			cells.setColumnWidth(2, 15.0f);
			cells.setColumnWidth(3, 15.0f);
			cells.setColumnWidth(4, 15.0f);			
			cells.setColumnWidth(5, 15.0f);	
			cells.setColumnWidth(6, 15.0f);	
			cells.setColumnWidth(7, 15.0f);
			cells.setColumnWidth(8, 15.0f);
			cells.setColumnWidth(9, 15.0f);
			cells.setColumnWidth(10, 15.0f);
			cells.setColumnWidth(11, 15.0f);
			cells.setColumnWidth(12, 15.0f);			
			cells.setColumnWidth(13, 15.0f);	
			cells.setColumnWidth(14, 15.0f);	
			cells.setColumnWidth(15, 15.0f);
			cells.setColumnWidth(16, 15.0f);	
			cells.setColumnWidth(17, 15.0f);		
			cells.setColumnWidth(18, 15.0f);
			cells.setColumnWidth(19, 15.0f);	
			cells.setColumnWidth(20, 15.0f);
						
			i=2;			
			if(rs1!=null){
			Cell cell = null;
			Style style;
			while(rs1.next())// lap den cuoi bang du lieu
			{
			
					
				//lay tu co so du lieu, gan bien
				String Channel = rs1.getString("KBHTEN");
			
				String Region = rs1.getString("VUNGTEN");
				
				String Area = rs1.getString("KVTEN");
				
				String Distributor = rs1.getString("NPPTEN");
				
				String SalesRep = rs1.getString("DDKDTEN");
				
				String BusinessUnit =rs1.getString("DVKDTEN");
				
				String Sitecode=rs1.getString("SITECODE");
				
				String Salessup =rs1.getString("GSBHTEN");
									
				//lay tu co so du lieu, gan bien
					
				String SKU ="0";// rs1.getString("NPP");
					
				SKU = rs1.getString("SKU");		
					
				//DOANH SO
				String Volume = rs1.getString("DOANHSO");
					
				String Outlet ="0";
					
				Outlet = rs1.getString("SOCUAHIEU");
										
				String OutletHaveVolume = "0";
					
				OutletHaveVolume = rs1.getString("SOCHCODS");
				
				String Order ="0";
					
				Order = rs1.getString("SODH");
			
				//if(OrderReturn==null)
				String	OrderReturn =rs1.getString("SODHTRAVE");
					
				String	SoldSKU=rs1.getString("SOLDSKU");
				
				String date=rs1.getString("NGAY");
				cell = cells.getCell("AA" + Integer.toString(i)); cell.setValue(Channel); //0
				cell = cells.getCell("AB" + Integer.toString(i)); cell.setValue(BusinessUnit);//1
				cell = cells.getCell("AC" + Integer.toString(i)); cell.setValue(Region);//2
				cell = cells.getCell("AD" + Integer.toString(i)); cell.setValue(Area);//3
				cell = cells.getCell("AE" + Integer.toString(i)); cell.setValue(Salessup);//4
				cell = cells.getCell("AF" + Integer.toString(i)); cell.setValue(Sitecode);//5
				cell = cells.getCell("AG" + Integer.toString(i)); cell.setValue(Distributor);//6
				cell = cells.getCell("AH" + Integer.toString(i)); cell.setValue(SalesRep);//7									
				cell = cells.getCell("AI" + Integer.toString(i)); cell.setValue(date);	//8					
				cell = cells.getCell("AJ" + Integer.toString(i)); cell.setValue(Float.parseFloat(Outlet));	//9	
				style = cell.getStyle();
				style.setNumber(2);
				cell.setStyle(style);

				cell = cells.getCell("AK" + Integer.toString(i)); cell.setValue(Float.parseFloat(OutletHaveVolume));//10
				style = cell.getStyle();
				style.setNumber(2);
				cell.setStyle(style);
				
				cell = cells.getCell("AL" + Integer.toString(i)); cell.setValue(Float.parseFloat(Order));//11
				style = cell.getStyle();
				style.setNumber(2);
				cell.setStyle(style);
				
				cell = cells.getCell("AM" + Integer.toString(i)); cell.setValue(Float.parseFloat(SoldSKU));//12
				style = cell.getStyle();
				style.setNumber(2);
				cell.setStyle(style);
				
				cell = cells.getCell("AN" + Integer.toString(i)); cell.setValue(Float.parseFloat(SKU));//13
				style = cell.getStyle();
				style.setNumber(2);
				cell.setStyle(style);
				
				cell = cells.getCell("AO" + Integer.toString(i)); cell.setValue(Float.parseFloat(Volume));//14
				style = cell.getStyle();
				style.setNumber(2);
				cell.setStyle(style);
				
				cell = cells.getCell("AP" + Integer.toString(i)); cell.setValue(Float.parseFloat(OrderReturn));//15
				style = cell.getStyle();
				style.setNumber(2);
				cell.setStyle(style);
				
									
				i++;
			}
			
			if(rs1!=null){
				rs1.close();
			}

			if(db!=null){
				db.shutDown();
			}
			
			if(i==2){
				obj.setMsg("Khong co bao cao trong thoi gian nay");
				return false;
			}
	
							
			setHidden(workbook,49);
			PivotTables pivotTables = worksheet.getPivotTables();
			    
			String pos = Integer.toString(i-1);	
			
			int index = pivotTables.add("=AA1:AP" + pos,"A10","PivotTable1");  
			    
			PivotTable pivotTable = pivotTables.get(index);//truyen index

			pivotTable.setRowGrand(true);
			pivotTable.setColumnGrand(false);
			pivotTable.setAutoFormat(true);
			pivotTable.setAutoFormatType(PivotTableAutoFormatType.TABLE10);
			    
			Hashtable<String, Integer> selected = new Hashtable<String, Integer>();
			    
			selected.put("KenhBanHang", 0);
			selected.put("DonViKinhDoanh", 1);
			selected.put("ChiNhanh",2);
			selected.put("KhuVuc", 3);
			selected.put("GiamSat", 4);			    
			selected.put("MaNhaPhanPhoi", 5);
			selected.put("NhaPhanPhoi", 6);
			selected.put("DaiDienKinhDoanh", 7);	
			selected.put("Ngay", 8);
			selected.put("CuaHieuQuanLy",9);
			selected.put("CuaHieuCoDoanhSo",10);
			selected.put("TongDonHang", 11);
			selected.put("MatHangBanDuoc", 12);
			selected.put("MatHangPhanPhoi", 13);
			selected.put("TongDoanhSo", 14);
			selected.put("DonHangTra", 15);
			
		    pivotTable.addFieldToArea(PivotFieldType.ROW, 0);	 pivotTable.getRowFields().get(0).setAutoSubtotals(false);  
		    			    
		    pivotTable.addFieldToArea(PivotFieldType.ROW, 1);	pivotTable.getRowFields().get(1).setAutoSubtotals(false);
		    		    
		    pivotTable.addFieldToArea(PivotFieldType.ROW, 2);	 pivotTable.getRowFields().get(2).setAutoSubtotals(false);  
		      
		    pivotTable.addFieldToArea(PivotFieldType.ROW, 3);	 pivotTable.getRowFields().get(3).setAutoSubtotals(false);  
		        
		    pivotTable.addFieldToArea(PivotFieldType.ROW, 4);	 pivotTable.getRowFields().get(4).setAutoSubtotals(false);  
		    		        
		    pivotTable.addFieldToArea(PivotFieldType.ROW, 6);	 pivotTable.getRowFields().get(5).setAutoSubtotals(true);  
		    
		    pivotTable.addFieldToArea(PivotFieldType.ROW, 7);	 pivotTable.getRowFields().get(6).setAutoSubtotals(true);  
			    
		    pivotTable.addFieldToArea(PivotFieldType.DATA, 9);pivotTable.getDataFields().get(0).setDisplayName("Cua Hieu Quan Ly");				
		    pivotTable.getDataFields().get(0).setNumber(3);
		    
		    pivotTable.addFieldToArea(PivotFieldType.DATA, 10);	pivotTable.getDataFields().get(1).setDisplayName("Cua Hieu Co Doanh So");	
		    pivotTable.getDataFields().get(1).setNumber(3);
		    
		    pivotTable.addFieldToArea(PivotFieldType.DATA, 11);	pivotTable.getDataFields().get(2).setDisplayName("Tong Don Hang");
		    pivotTable.getDataFields().get(2).setNumber(3);
		    
		    pivotTable.addFieldToArea(PivotFieldType.DATA, 12);	pivotTable.getDataFields().get(3).setDisplayName("Mat Hang Ban Duoc");
		    pivotTable.getDataFields().get(3).setNumber(3);
		    
		    pivotTable.addFieldToArea(PivotFieldType.DATA, 13);	pivotTable.getDataFields().get(4).setDisplayName("Mat Hang Phan Phoi");
		    pivotTable.getDataFields().get(4).setNumber(3);
		    
		    pivotTable.addFieldToArea(PivotFieldType.DATA, 14);	pivotTable.getDataFields().get(5).setDisplayName("Tong Doanh So");
		    pivotTable.getDataFields().get(5).setNumber(3);
		    
		    pivotTable.addFieldToArea(PivotFieldType.DATA, 15);	pivotTable.getDataFields().get(6).setDisplayName("Don Hang Tra Ve");
		    pivotTable.getDataFields().get(6).setNumber(3);
		    
		    pivotTable.addFieldToArea(PivotFieldType.COLUMN,pivotTable.getDataField());
			    			    		
			pivotTable.getDataField().getDataDisplayFormat();
			  
		    return true;
		}else{
			obj.setMsg("Khong co bao cao trong thoi gian nay");
			return false;
		}
	}catch(Exception ex){
		System.out.println("Khong The Tao Duoc Bao Cao :"+ex.toString());
		obj.setMsg("Khong The Tao Duoc Bao Cao :"+ex.toString());
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
	
	private void setHidden(Workbook workbook,int i)
	{
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	   	   
	    Cells cells = worksheet.getCells();
	    for(int j = 26; j <= i; j++)
	    { 
	    	cells.hideColumn(j);
	    }
		
	}
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy : hh:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);	
	}
}
