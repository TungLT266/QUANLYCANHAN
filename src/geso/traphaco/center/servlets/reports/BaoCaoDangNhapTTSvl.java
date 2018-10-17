package geso.traphaco.center.servlets.reports;

import geso.traphaco.center.beans.stockintransit.IStockintransit;
import geso.traphaco.center.beans.stockintransit.imp.Stockintransit;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;

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

public class BaoCaoDangNhapTTSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    public BaoCaoDangNhapTTSvl() {
        super();
        
    }  
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	Utility util=new Utility();
    	  
    	request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");	
		HttpSession session = request.getSession();
		IStockintransit obj = new Stockintransit();	  
		String querystring=request.getQueryString();
		
		String userId = util.getUserId(querystring);
		if(userId==null) {
			obj.setuserId("");
		}
		obj.setuserId(userId);
		String userTen = (String)session.getAttribute("userTen");
		if(userTen==null) {
			obj.setuserTen("");
		}
		obj.setuserTen(userTen);
		obj.init();
		session.setAttribute("obj", obj);
		session.setAttribute("util", util);
		session.setAttribute("userTen", userTen);
		session.setAttribute("userId", userId);
		String nextJSP = "/TraphacoHYERP/pages/Center/BaoCaoDangNhapTT.jsp";
		response.sendRedirect(nextJSP);
 	}
	
 	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
 		Utility util=new Utility();
 		  
 		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();		
 		OutputStream out = response.getOutputStream(); 
 		IStockintransit obj = new Stockintransit();	
 		String nextJSP = "/TraphacoHYERP/pages/Center/BaoCaoDangNhapTT.jsp";
		try
		    {
			String userId = (String) session.getAttribute("userId");
			String userTen = (String) session.getAttribute("userTen");			
			
			obj.setuserId(userId == null ? "" : userId);
			obj.setuserTen(userTen == null ? "" : userTen);
			obj.settungay(util.antiSQLInspection(request.getParameter("Sdays"))==null? "":util.antiSQLInspection(request.getParameter("Sdays")));			
			obj.setdenngay(util.antiSQLInspection(request.getParameter("Edays"))==null? "":util.antiSQLInspection(request.getParameter("Edays")));
			obj.setvungId(util.antiSQLInspection(request.getParameter("mien")) == null ? "" : util.antiSQLInspection(request.getParameter("mien")));
			obj.setTinhthanhid(util.antiSQLInspection(request.getParameter("tinh")) == null ? "" : util.antiSQLInspection(request.getParameter("tinh")));
			
			String action = util.antiSQLInspection(request.getParameter("action"));
			
			if (action.equals("Taomoi")) {
				response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "attachment; filename=BaoCaoDangNhap"+util.setTieuDe(obj)+".xlsm");
				CreatePivotTable(out,obj);
				return;
			}			
		}
		catch (Exception ex) {
			obj.setMsg(ex.getMessage());
		}
		obj.init();
		session.setAttribute("obj", obj);
		session.setAttribute("util", util);
		session.setAttribute("userTen", obj.getuserTen());
		session.setAttribute("userId", obj.getuserId());
		response.sendRedirect(nextJSP);
 	}

 	private void CreatePivotTable(OutputStream out,IStockintransit obj) throws Exception
    {       
 				
 		String fstreamstr = getServletContext().getInitParameter("path") + "\\DangNhapTT.xlsm";
 		FileInputStream fstream = new FileInputStream(fstreamstr);
 		
 		Workbook workbook = new Workbook();
		workbook.open(fstream);
		workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
		
		CreateStaticHeader(workbook,obj);	     
	    CreateStaticData(workbook,obj);
	    workbook.save(out);
	    fstream.close();
    }
 	
	private void CreateStaticHeader(Workbook workbook, IStockintransit obj) {
 		
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
	    
		cell.setValue("BÁO CÁO THEO DÕI ĐĂNG NHẬP");
		getCellStyle(workbook,"A1",Color.RED,true,14);	    
	    
	    cells.setRowHeight(2, 18.0f);
	    cell = cells.getCell("A3"); 
	    getCellStyle(workbook,"A3",Color.NAVY,true,10);	    
		cell.setValue("Từ ngày: " + obj.gettungay());
	    
	    cells.setRowHeight(3, 18.0f);
	    cell = cells.getCell("B3"); getCellStyle(workbook,"B3",Color.NAVY,true,9);	        
		cell.setValue("Đến ngày: " + obj.getdenngay());
	    
	    cells.setRowHeight(4, 18.0f);
	    cell = cells.getCell("A4");getCellStyle(workbook,"A4",Color.NAVY,true,9);
		cell.setValue("Ngày báo cáo: " + this.getDate());
	    
	    cells.setRowHeight(5, 18.0f);
	    cell = cells.getCell("A5");getCellStyle(workbook,"A5",Color.NAVY,true,9);
		cell.setValue("Được tạo bởi:  " + obj.getuserTen());

		cell = cells.getCell("AA1"); cell.setValue("Mien");
		cell = cells.getCell("AB1"); cell.setValue("Chi nhanh doi tac");
		cell = cells.getCell("AC1"); cell.setValue("Tai khoan");
		cell = cells.getCell("AD1"); cell.setValue("Ho ten");
		cell = cells.getCell("AE1"); cell.setValue("Ngay");
		cell = cells.getCell("AF1"); cell.setValue("Thoi gian dang nhap");
		cell = cells.getCell("AG1"); cell.setValue("Thoi gian dang xuat");
		

	}
 	private void CreateStaticData(Workbook workbook,IStockintransit obj) throws Exception
 	{
 		Worksheets worksheets = workbook.getWorksheets();
 	    Worksheet worksheet = worksheets.getSheet(0);
 	    Cells cells = worksheet.getCells();
 	    dbutils db = new dbutils();
 	    Utility Ult = new  Utility();

		String chuoi="";
 	    if(obj.getvungId().length()>0)
 	    	chuoi=chuoi +"and v.PK_SEQ='"+obj.getvungId()+"' ";
 	    if(obj.getTinhthanhid().length()>0)
 	    	chuoi=chuoi + " and tp.PK_SEQ='"+obj.getTinhthanhid()+"'";
		String sql = "select nv.TEN as tennv,nv.DANGNHAP as tk,dn.ngay,CONVERT(VARCHAR, dn.CREATED_DATE, 108) as CREATED_DATE,CONVERT(VARCHAR, dn.logout, 108) as logout,isnull(npp.TEN, 'null') as chinhanh, isnull(v.TEN, 'null') as vung from DANGNHAP_NHANVIEN dn"
				+
			" left join NHANVIEN nv on nv.PK_SEQ=dn.NHANVIEN_FK"+ 
			" left join PHAMVIHOATDONG hd on nv.PK_SEQ=hd.Nhanvien_fk"+
			" left join NHAPHANPHOI npp on hd.Npp_fk=npp.PK_SEQ"+
			" left join TINHTHANH tp on tp.PK_SEQ=npp.TINHTHANH_FK"+
			" left join VUNG v on v.PK_SEQ=tp.VUNG_FK"+
			" where  '"+obj.gettungay()+"' <= dn.NGAY and dn.NGAY <= '"+obj.getdenngay()+"' " +chuoi;

 	    System.out.println("[BaoCaoDangNhapTTSvl.CreateStaticData] sql = " + sql);
 	    ResultSet rs = db.get(sql);
 	    int i = 2; 	     	    
 		if(rs!=null)
 		{
 			try 
 			{
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
 				
 				Cell cell = null;
 				Style style;
				String mien, chinhanh, taikhoan, hoten, ngaybd, ngaykt,ngay;
				while (rs.next()) 
				{
					mien = rs.getString("vung");
					chinhanh = rs.getString("chinhanh");
					taikhoan = rs.getString("tk");
					hoten = rs.getString("tennv");
					ngay = rs.getString("ngay");
					ngaybd = rs.getString("CREATED_DATE");
					ngaykt = "0";
					if (rs.getString("logout") != null)
					{
						ngaykt = rs.getString("logout");
					}

					cell = cells.getCell("AA" + Integer.toString(i));	cell.setValue(mien);
					cell = cells.getCell("AB" + Integer.toString(i));	cell.setValue(chinhanh);
					cell = cells.getCell("AC" + Integer.toString(i));	cell.setValue(taikhoan);
					cell = cells.getCell("AD" + Integer.toString(i));	cell.setValue(hoten);
					cell = cells.getCell("AE" + Integer.toString(i));	cell.setValue(ngay);
					cell = cells.getCell("AF" + Integer.toString(i));	cell.setValue(ngaybd);
					cell = cells.getCell("AG" + Integer.toString(i));	cell.setValue(ngaykt);
					
					i++;
				} 	
				if(rs != null)
				{
		 			rs.close();
				}
				if(db!=null)
				{
					db.shutDown();
				}
 			}
			catch (Exception e)
 			{	 		
 				e.printStackTrace();
	 			throw new Exception(e.getMessage());
	 		}
	 		finally 
			{
	 			if(rs != null)
	 			rs.close();
	 		}
	 	} else {	 			
			throw new Exception("Không thể tạo báo cáo trong thời gian này");
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
	    
		//Setting the horizontal alignment of the text in the cell 
	    style.setHAlignment(TextAlignmentType.LEFT);
	    cell.setStyle(style);
	}

	private String getDate() 
	{
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy: hh:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);	
	} 	
}

 