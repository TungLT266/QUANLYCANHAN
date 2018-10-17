package geso.traphaco.center.servlets.reports;

import geso.traphaco.center.beans.stockintransit.IStockintransit;
import geso.traphaco.center.beans.stockintransit.imp.Stockintransit;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.servlets.report.ReportAPI;
import geso.traphaco.distributor.util.Utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Font;
import com.aspose.cells.Style;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

public class BCThieuHangNppTT extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public BCThieuHangNppTT() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
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
		String nextJSP = "/TraphacoHYERP/pages/Center/BCThieuHangNppTT.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
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
		
		obj.setnppId(request.getParameter("nppId")!=null?
				util.antiSQLInspection(request.getParameter("nppId")):"");

		String action = request.getParameter("action") != null? util.antiSQLInspection(request.getParameter("action")) : "";
		String nextJSP = "/TraphacoHYERP/pages/Center/BCThieuHangNppTT.jsp";
		
		if(action.equals("Taomoi"))
		{
			try
			{
				response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "attachment; filename=ThieuHangNppTT_"+util.setTieuDe(obj)+".xlsm");
		        if(!CreatePivotTable(out,obj))
		        {
		        	obj.setMsg("Không có dữ liệu trong thời gian này.");
		        	obj.init();
		    		session.setAttribute("obj", obj);	
		    		response.sendRedirect(nextJSP);
		        }
		        
			}
			catch(Exception ex)
			{
				obj.setMsg(ex.getMessage());
			}
		}
	
		obj.init();
		session.setAttribute("obj", obj);	
		response.sendRedirect(nextJSP);
	}
	
	private boolean CreatePivotTable(OutputStream out, IStockintransit obj) throws Exception 
	{
		String chuoi = getServletContext().getInitParameter("path") + "\\BCThieuHangNpp.xlsm";
		FileInputStream fstream = new FileInputStream(chuoi);
			
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
		    ReportAPI.getCellStyle(cell,Color.RED, true, 16, "MỨC ĐỘ CUNG ỨNG NHÀ PHÂN PHỐI");
		    cell = cells.getCell("A2");
		    ReportAPI.getCellStyle(cell,Color.NAVY,true,10,"Từ ngày: " + obj.gettungay() + "  Đến ngày : " + obj.getdenngay());
		    cell = cells.getCell("A3");
		    ReportAPI.getCellStyle(cell,Color.NAVY,true,10,"Ngày tạo : " + ReportAPI.NOW("yyyy-MM-dd")); 
		    cell = cells.getCell("A4");
		    ReportAPI.getCellStyle(cell,Color.NAVY,true,10,"Người tạo : " + obj.getuserTen());
			
			
			/*cells.setRowHeight(0, 20.0f);
			Cell cell = cells.getCell("A1");
		    cell.setValue("BÁO CÁO THIẾU HÀNG NHÀ PHÂN PHỐI");   	
		    
		    cells.setRowHeight(2, 18.0f);
		    cell = cells.getCell("A2"); 
		    getCellStyle(workbook,"A2",Color.NAVY,true,10);
		    cell.setValue("Từ ngày: " + obj.gettungay());	
		    	
		    
		    cells.setRowHeight(3, 18.0f);
		    cell = cells.getCell("B2"); 
		    getCellStyle(workbook,"B2",Color.NAVY,true,9);
			cell.setValue("Đến ngày: " + obj.getdenngay());  
			 
		    cells.setRowHeight(4, 18.0f);
		    cell = cells.getCell("A3");
		    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Ngày báo cáo: " + ReportAPI.NOW("yyyy-MM-dd"));
		    
		    cells.setRowHeight(5, 18.0f);
		    cell = cells.getCell("A4");
		    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Được tạo bởi:  " + obj.getuserTen());*/
			
		    cell = cells.getCell("AA1");		cell.setValue("NPP");					ReportAPI.setCellHeader(cell);
			cell = cells.getCell("AB1");		cell.setValue("KHACH HANG");			ReportAPI.setCellHeader(cell);
			cell = cells.getCell("AC1");		cell.setValue("MA SAN PHAM");			ReportAPI.setCellHeader(cell);
			cell = cells.getCell("AD1");		cell.setValue("TEN SAN PHAM");			ReportAPI.setCellHeader(cell);
			cell = cells.getCell("AE1");		cell.setValue("NGAY");					ReportAPI.setCellHeader(cell);
			cell = cells.getCell("AF1");		cell.setValue("SL HIEN TAI");			ReportAPI.setCellHeader(cell);
			cell = cells.getCell("AG1");		cell.setValue("SL HIEN TAI(Thung)");			ReportAPI.setCellHeader(cell);
			cell = cells.getCell("AH1");		cell.setValue("SL DAT");				ReportAPI.setCellHeader(cell);
			cell = cells.getCell("AI1");		cell.setValue("SL DAT(Thung)");				ReportAPI.setCellHeader(cell);

		}
		catch(Exception ex)
		{
			throw new Exception("Bao cao bi loi khi khoi tao");
		}
	}
	
	private boolean CreateStaticData(Workbook workbook, IStockintransit obj) throws Exception
	{
		dbutils db = new dbutils();
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	    Cells cells = worksheet.getCells();
		
	    String query = "select npp.ten as nppTen, kh.ten as khTen, sp.ma as spMa, sp.ten as spTen,  " +
				   "ngayghinhan as ngay, soluonghientai as sl, soluongdat as sldat " +
				   " ,qc.soluong1 as qc1,qc.soluong2 as qc2 "+
				   "from thieuhang_npp a"+
				   " inner join nhaphanphoi npp on npp.pk_seq = a.npp_fk"+
				   " inner join khachhang kh on kh.pk_seq = a.kh_fk"+
				   " inner join sanpham sp on sp.pk_seq = a.sp_fk"+
					" left join quycach qc on sp.pk_seq = qc.sanpham_fk and qc.dvdl2_fk=100018 "+
				   " inner join khuvuc f on npp.khuvuc_fk = f.pk_seq"+
				   " inner join vung g on f.vung_fk = g.pk_seq"+				   
				   " where a.ngayghinhan >= '"+obj.gettungay()+"' and a.ngayghinhan<= '"+obj.getdenngay()+"'" ;
	    if(obj.getvungId().length() > 0)
	    	query += " and g.pk_seq = '" + obj.getvungId() + "' ";
	    
	    if(obj.getkhuvucId().length() > 0)
	    	query += " and f.pk_seq = '" + obj.getkhuvucId() + "' ";	    	  
	    
	    if(obj.getnppId().length() > 0)
	    	query += " and npp.pk_seq = '" + obj.getnppId() + "' ";
	  
		System.out.println("1.BC Thieu hang : " + query);
		ResultSet rs = db.get(query);
		
		int i = 2;
		if(rs!=null)
		{
			try 
			{
				for(int j = 0; j < 15; j++)
					cells.setColumnWidth(i, 15.0f);
				
				Cell cell = null;
				while(rs.next())
				{
					double soluong=rs.getDouble("sl");
					int qc1=rs.getInt("qc1");
					int qc2=rs.getInt("qc2");
					double slThg=soluong/qc1/qc2;
					
					cell = cells.getCell("AA" + String.valueOf(i));		cell.setValue(rs.getString("nppTen"));			
					cell = cells.getCell("AB" + String.valueOf(i));		cell.setValue(rs.getString("khTen"));
					cell = cells.getCell("AC" + String.valueOf(i));		cell.setValue(rs.getString("spMa"));
					cell = cells.getCell("AD" + String.valueOf(i));		cell.setValue(rs.getString("spTen"));
					cell = cells.getCell("AE" + String.valueOf(i));		cell.setValue(rs.getString("ngay"));  
					cell = cells.getCell("AF" + String.valueOf(i));		cell.setValue(Float.parseFloat(rs.getString("sl")));
					cell = cells.getCell("AG" + String.valueOf(i));		cell.setValue(slThg);
					cell = cells.getCell("AH" + String.valueOf(i));		cell.setValue(Float.parseFloat(rs.getString("sldat")));	          
					soluong=rs.getDouble("sldat");
					slThg=soluong/qc1/qc2;					
					cell = cells.getCell("AI" + String.valueOf(i));		cell.setValue(slThg);

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
		}
		
		if(db != null) 
			db.shutDown();
		return true;
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

}
