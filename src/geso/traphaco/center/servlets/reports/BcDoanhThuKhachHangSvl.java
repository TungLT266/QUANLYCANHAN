package geso.traphaco.center.servlets.reports;

import geso.traphaco.center.beans.report.IBcDoanhThuKhachHangList;
import geso.traphaco.center.beans.report.imp.BcDoanhThuKhachHangList;
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

@WebServlet("/BcDoanhThuKhachHangSvl")
public class BcDoanhThuKhachHangSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	
	public BcDoanhThuKhachHangSvl()
	{
		super();
		
	}
	
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		IBcDoanhThuKhachHangList obj;
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
		
		obj = new BcDoanhThuKhachHangList();
		obj.setUserId(userId);
		obj.setView("TT");
		String nextJSP = "";
		obj.init("");
		
		
		nextJSP = "/TraphacoHYERP/pages/Center/BcDoanhThuKhachHang.jsp";
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
    
    IBcDoanhThuKhachHangList obj = new BcDoanhThuKhachHangList();
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
	
    obj.set_Action(action);
    
    System.out.println("___ATION "+action);
    
    
    if (action.equals("excel")  )
    {
    	try
	    { 
				response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "attachment; filename=BcDoanhThuKhachHang.xlsm");
	      FileInputStream fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\BcDoanhThuKhachHang.xlsm");
				Workbook workbook = new Workbook();
				workbook.open(fstream);
				workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
				CreateStaticHeader(workbook, obj);
				obj.setUserId(userId);
				obj.init("");
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
  		nextJSP = "/TraphacoHYERP/pages/Center/BcDoanhThuKhachHang.jsp";
  		response.sendRedirect(nextJSP); 
    }
    else  if(action.equals("view") || action.equals("next") || action.equals("prev")){
    	
    	System.out.println("______::::::::::::::::_____"+action+"_________"+request.getParameter("nxtApprSplitting"));
    	obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
    	obj.setUserId(userId);
    	obj.init("");
    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
    	session.setAttribute("obj", obj);
    	String 	nextJSP = "/TraphacoHYERP/pages/Center/BcDoanhThuKhachHang.jsp";
    	response.sendRedirect(nextJSP);
    }
    else
    {
    	obj.init("");
			session.setAttribute("obj", obj);
			String nextJSP = "";
			nextJSP = "/TraphacoHYERP/pages/Center/BcDoanhThuKhachHang.jsp";
			response.sendRedirect(nextJSP);  
    }
		
	}
	
	private boolean FillData(Workbook workbook, IBcDoanhThuKhachHangList obj, String query) throws Exception
  {
		dbutils db = new dbutils();
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);

		Cells cells = worksheet.getCells();		
		ResultSet hdRs = db.get(query);
		
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
					
				
					cell = cells.getCell("B" + Integer.toString(i));	cell.setValue(hdRs.getString("ttTEN"));
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					
					cell = cells.getCell("C" + Integer.toString(i));	cell.setValue(hdRs.getString("nppTEN"));
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					
					cell = cells.getCell("D" + Integer.toString(i));	cell.setValue(hdRs.getString("DDKD"));
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
				
					cell = cells.getCell("E" + Integer.toString(i));	cell.setValue(hdRs.getString("khMA"));
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					
					cell = cells.getCell("F" + Integer.toString(i));	cell.setValue(hdRs.getString("khMAHD"));
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					
					cell = cells.getCell("G" + Integer.toString(i));	cell.setValue(hdRs.getString("khCHUCH"));
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					
					cell = cells.getCell("H" + Integer.toString(i));	cell.setValue(hdRs.getString("khTEN"));
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					
					cell = cells.getCell("I" + Integer.toString(i));	cell.setValue(hdRs.getString("khDIACHI"));
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					
					cell = cells.getCell("J" + Integer.toString(i));	cell.setValue(hdRs.getString("khLOAI"));
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					
					
					cell = cells.getCell("K" + Integer.toString(i));	cell.setValue(hdRs.getDouble("AVAT"));
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 41);
					
					
					
					total_BVAT += (hdRs.getDouble("BVAT"));
					total_VAT +=  (hdRs.getDouble("VAT"));
					total_AVAT += (hdRs.getDouble("AVAT"));
					
					++i;					
				}
				
				cell = cells.getCell("A" + Integer.toString(i));	cell.setValue("Tổng cộng");
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
				
				
				cell = cells.getCell("B" + Integer.toString(i));	cell.setValue("" );
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
				
				cell = cells.getCell("C" + Integer.toString(i));	cell.setValue("");
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
				
				
				
				cell = cells.getCell("D" + Integer.toString(i));	cell.setValue("" );
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
				
				cell = cells.getCell("E" + Integer.toString(i));	cell.setValue("" );
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
				
				cell = cells.getCell("F" + Integer.toString(i));	cell.setValue("" );
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
				
				
				cell = cells.getCell("G" + Integer.toString(i));	cell.setValue("" );
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);

				
				cell = cells.getCell("H" + Integer.toString(i));	cell.setValue("" );
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
				
				cell = cells.getCell("I" + Integer.toString(i));	cell.setValue("" );
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
				
				cell = cells.getCell("J" + Integer.toString(i));	cell.setValue("");			
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 41);
				
				cell = cells.getCell("K" + Integer.toString(i));	cell.setValue(Math.round(total_AVAT));			
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 41);
				

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

	private void CreateStaticHeader(Workbook workbook, IBcDoanhThuKhachHangList obj)
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
	    
	    String tieude = "DOANH THU BÁN HÀNG";
	    ReportAPI.getCellStyle(cell,Color.RED, true, 14, tieude);
	    
	    String message = "";
			cells.setRowHeight(2, 18.0f);
			cell = cells.getCell("A3");
			ReportAPI.getCellStyle(cell, Color.RED, true, 9, message);   

	    cells.setRowHeight(3, 18.0f);
	    cell = cells.getCell("A4");
	    ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Từ ngày : " + obj.getTuNgay() + "" );
	    
	    
	    cells.setRowHeight(3, 18.0f);
	    cell = cells.getCell("B4"); 
	    ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Đến ngày : " + obj.getDenNgay() + "" );
	 
		
	    cells.setRowHeight(4, 18.0f);
	    cell = cells.getCell("A5");
	    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Ngày báo cáo: " + ReportAPI.NOW("yyyy-MM-dd"));
	    
	    cells.setRowHeight(5, 18.0f);
	    cell = cells.getCell("A6");
	    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Được tạo bởi:  "    );
	    		
		cell = cells.getCell("A8");	cell.setValue("STT");
		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
		

		cell = cells.getCell("B8");	cell.setValue("Địa bàn");
		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
		
		cell = cells.getCell("C8");	cell.setValue("Chi nhánh/Đối tác");
		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
	
		cell = cells.getCell("D8");	cell.setValue("Trình dược viên");
		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
	
		
		cell = cells.getCell("E8");	cell.setValue("Mã KH");
		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
		
		cell = cells.getCell("F8");	cell.setValue("Mã HĐ");
		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
		
		cell = cells.getCell("G8");	cell.setValue("Khách hàng");
		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
		
		cell = cells.getCell("H8");	cell.setValue("Chủ cửa hàng	");
		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
		
		cell = cells.getCell("I8");	cell.setValue("Địa chỉ	");
		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);

		cell = cells.getCell("J8");	cell.setValue("Loại KH");
		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
	
		cell = cells.getCell("K8");	cell.setValue("Doanh thu");
		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);

		
  }
	
}
