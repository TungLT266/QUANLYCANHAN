package geso.traphaco.center.servlets.reports;

import geso.traphaco.center.beans.stockintransit.IStockintransit;
import geso.traphaco.center.beans.stockintransit.imp.Stockintransit;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.servlets.report.ReportAPI;
import geso.traphaco.center.util.Utility;

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

public class BCTreDonHang extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public BCTreDonHang() {
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
	
		
		obj.init();
		obj.settype("1");
		session.setAttribute("obj", obj);		
		session.setAttribute("userId", obj.getuserId());
		session.setAttribute("userTen", obj.getuserTen());
		String nextJSP = "/TraphacoHYERP/pages/Center/TreDonHang.jsp";
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
		String nextJSP = "/TraphacoHYERP/pages/Center/TreDonHang.jsp";
		
		if(action.equals("Taomoi"))
		{
			try
			{
				response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "attachment; filename=BCTreDonHang_"+util.setTieuDe(obj)+".xlsm");
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
		String chuoi = getServletContext().getInitParameter("path") + "\\TreDonHang.xlsm";
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
		    cell.setValue("HẬU CẦN NHÀ PHÂN PHỐI");   	
		    
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
		    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Được tạo bởi:  " + obj.getuserTen());
			
		    cell = cells.getCell("DA1"); 	cell.setValue("Mien");  ReportAPI.setCellHeader(cell);
		    cell = cells.getCell("DB1"); 	cell.setValue("Vung");  ReportAPI.setCellHeader(cell);
		    cell = cells.getCell("DC1"); 	cell.setValue("KenhBanHang");  ReportAPI.setCellHeader(cell);
		    cell = cells.getCell("DD1"); 	cell.setValue("MaCN/DT");  ReportAPI.setCellHeader(cell);
		    cell = cells.getCell("DE1"); 	cell.setValue("ChiNhanh/DoiTac");  ReportAPI.setCellHeader(cell);
		    cell = cells.getCell("DF1"); 	cell.setValue("KhachHang");  ReportAPI.setCellHeader(cell);
		    cell = cells.getCell("DG1"); 	cell.setValue("SoDonHang");  ReportAPI.setCellHeader(cell);
		    cell = cells.getCell("DH1"); 	cell.setValue("NgayTaoDH");  ReportAPI.setCellHeader(cell);
		    cell = cells.getCell("DI1"); 	cell.setValue("SoXuatKho");  ReportAPI.setCellHeader(cell);
		    cell = cells.getCell("DJ1"); 	cell.setValue("NgayTaoPXK");  ReportAPI.setCellHeader(cell);

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
		
	    String query = "select g.ten as vungTen, f.ten as khuvucTen, h.ten as kbhTen, e.ma as nppMa, e.ten as nppTen, d.ten +'__ '+isnull(d.diachi,'') +'__'+ isnull(d.dienthoai,'') as khachhangTen, " +
	    					"a.pk_seq as sodonhang, a.ngaygio as ngaydonhang, c.pk_seq as sophieuxuat, c.ngaygiosua as ngayxuatkho  " +
	    				"from donhang a inner join phieuxuatkho_donhang b on a.pk_seq = b.donhang_fk  " +
	    					"inner join phieuxuatkho c on b.pxk_fk = c.pk_seq " +
	    					"inner join khachhang d on a.khachhang_fk = d.pk_seq " +
	    					"inner join nhaphanphoi e on a.npp_fk = e.pk_seq " +
	    					"inner join khuvuc f on e.khuvuc_fk = f.pk_seq " +
	    					"inner join vung g on f.vung_fk = g.pk_seq " +
	    					"inner join kenhbanhang h on a.kbh_fk = h.pk_seq " +
	    				"where '" + obj.gettungay() + "' <= a.ngaynhap and a.ngaynhap <= '" + obj.getdenngay() + "' " +
	    					"and DATEDIFF (day, a.ngaysua, c.ngaysua) >= 2";
	    
	    if(obj.getvungId().length() > 0)
	    	query += " and g.pk_seq = '" + obj.getvungId() + "' ";
	    
	    if(obj.getkhuvucId().length() > 0)
	    	query += " and f.pk_seq = '" + obj.getkhuvucId() + "' ";
	    
	    if(obj.getkenhId().length() > 0)
	    	query += " and h.pk_seq = '" + obj.getkenhId() + "' ";
	    
	    if(obj.getnppId().length() > 0)
	    	query += " and e.pk_seq = '" + obj.getnppId() + "' ";
	    
	    Utility  util = new Utility();
	    query += " and e.pk_seq in " + util .quyen_npp(obj.getuserId()) + " and a.kbh_fk in  " + util.quyen_kenh(obj.getuserId());
	  
		System.out.println("1.Tre Don Hang: " + query);
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
					String vung = rs.getString("vungTen");
					String khuvuc = rs.getString("khuvucTen");
					String kbh = rs.getString("kbhTen");
					String maNPP = rs.getString("nppMa");
					String tenNPP = rs.getString("nppTen");
					String khachhang = rs.getString("khachhangTen");
					String sodonhang = rs.getString("sodonhang");
					String ngaydonhang = rs.getString("ngaydonhang");										
					String sophieuxuat = rs.getString("sophieuxuat");
					String ngayxuat = rs.getString("ngayxuatkho");	
            		
					cell = cells.getCell("DA" + Integer.toString(i)); 	cell.setValue(vung);
					cell = cells.getCell("DB" + Integer.toString(i)); 	cell.setValue(khuvuc);
					cell = cells.getCell("DC" + Integer.toString(i)); 	cell.setValue(kbh);
					cell = cells.getCell("DD" + Integer.toString(i)); 	cell.setValue(maNPP);
					cell = cells.getCell("DE" + Integer.toString(i)); 	cell.setValue(tenNPP);
					cell = cells.getCell("DF" + Integer.toString(i)); 	cell.setValue(khachhang);				
					cell = cells.getCell("DG" + Integer.toString(i)); 	cell.setValue(sodonhang);
					cell = cells.getCell("DH" + Integer.toString(i)); 	cell.setValue(ngaydonhang);
					cell = cells.getCell("DI" + Integer.toString(i)); 	cell.setValue(sophieuxuat);
					cell = cells.getCell("DJ" + Integer.toString(i)); 	cell.setValue(ngayxuat);

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
