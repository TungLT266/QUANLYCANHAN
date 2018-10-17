package geso.traphaco.distributor.servlets.reports;

import geso.traphaco.distributor.beans.reports.IBcBanHangTheoChiTieuNPPList;
import geso.traphaco.distributor.beans.reports.imp.BcBanHangTheoChiTieuNPPList;
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

@WebServlet("/BcBanHangTheoChiTieuSvl")
public class BcBanHangTheoChiTieuNPPSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	public BcBanHangTheoChiTieuNPPSvl()
	{
		super();
		
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		IBcBanHangTheoChiTieuNPPList obj;
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
		
		obj = new BcBanHangTheoChiTieuNPPList();
		obj.setUserId(userId);
		
		System.out.println("NPP ID: "+obj.getUserId());
		obj.setView("TT");
		String nextJSP = "";
		obj.init("");
		
		
		nextJSP = "/TraphacoHYERP/pages/Distributor/BcBanHangTheoChiTieuNPP.jsp";
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
	    
	    IBcBanHangTheoChiTieuNPPList obj = new BcBanHangTheoChiTieuNPPList();
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
	    
	    String[] khId = request.getParameterValues("khId");
	    obj.setKhId(Doisangchuoi(khId));
	    
	    String[] ddkdId = request.getParameterValues("ddkdId");
	    obj.setDdkdId(Doisangchuoi(ddkdId));
	    
	    String[] spId = request.getParameterValues("spId");
	    obj.setSpId(Doisangchuoi(spId));
	    
	    
	    String nppId =request.getParameter("nppId")==null?"": request.getParameter("nppId");
	    obj.setNppId(nppId);
		
	    obj.setAction_(action);
	    
	    System.out.println("___ATION "+action+"____"+nppId);
	    
	    
	    if (action.equals("excel")  )
	    {
	    	try
		    { 
					response.setContentType("application/xlsm");
					response.setHeader("Content-Disposition", "attachment; filename=BcBanHangTheoChiTieu.xlsm");
		      FileInputStream fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\BcBanHangTheoChiTieu.xlsm");
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
	  		nextJSP = "/TraphacoHYERP/pages/Distributor/BcBanHangTheoChiTieuNPP.jsp";
	  		response.sendRedirect(nextJSP); 
	    }
	    else  if(action.equals("view") || action.equals("next") || action.equals("prev")){
	    	
	    	System.out.println("______::::::::::::::::_____"+action+"_________"+request.getParameter("nxtApprSplitting"));
	    	obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
	    	obj.setUserId(userId);
	    	obj.init("");
	    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
	    	session.setAttribute("obj", obj);
	    	
	    	String 	nextJSP = "/TraphacoHYERP/pages/Distributor/BcBanHangTheoChiTieuNPP.jsp";
	    	response.sendRedirect(nextJSP);
	    }
	    else
	    {
	    	System.out.println("SEARCH");
	    	obj.setUserId(userId);
	    	session.setAttribute("obj", obj);
	  		session.setAttribute("userId", userId);
	  		obj.init("");
	  		String nextJSP = "";
	  		nextJSP = "/TraphacoHYERP/pages/Distributor/BcBanHangTheoChiTieuNPP.jsp";
	  		response.sendRedirect(nextJSP); 
	    }
		
	}
	
	private boolean FillData(Workbook workbook, IBcBanHangTheoChiTieuNPPList obj, String query) throws Exception
  {
		dbutils db = new dbutils();
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);

		Cells cells = worksheet.getCells();		
		ResultSet hdRs = db.get(query);
		
		double total_AVAT=0;
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
					
				
					cell = cells.getCell("B" + Integer.toString(i));	cell.setValue(hdRs.getString("ddkdTEN"));
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					
					cell = cells.getCell("C" + Integer.toString(i));	cell.setValue(hdRs.getString("khMA"));
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					
					cell = cells.getCell("D" + Integer.toString(i));	cell.setValue(hdRs.getString("khTEN"));
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					
					cell = cells.getCell("E" + Integer.toString(i));	cell.setValue(hdRs.getString("spMa"));
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);

					
					cell = cells.getCell("F" + Integer.toString(i));	cell.setValue(hdRs.getString("spTEN"));
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					
					cell = cells.getCell("G" + Integer.toString(i));	cell.setValue(hdRs.getDouble("soLuong"));
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 41);

					
					cell = cells.getCell("H" + Integer.toString(i));	cell.setValue(hdRs.getDouble("AVAT")/hdRs.getDouble("soLuong"));
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 41);
					
					
					cell = cells.getCell("I" + Integer.toString(i));	cell.setValue(hdRs.getDouble("AVAT"));
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 3);
					
					
					total_AVAT += hdRs.getDouble("AVAT");
					
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
								
				cell = cells.getCell("I" + Integer.toString(i));	cell.setValue( total_AVAT);			
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

	private void CreateStaticHeader(Workbook workbook, IBcBanHangTheoChiTieuNPPList obj)
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
	    
	    String tieude = "BÁO CÁO BÁN HÀNG THEO CHỈ TIÊU";
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
	
		cell = cells.getCell("B8");	cell.setValue("TRÌNH DƯỢC VIÊN");
		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
		
		cell = cells.getCell("C8");	cell.setValue("MÃ KHÁCH HÀNG");
		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
		
		cell = cells.getCell("D8");	cell.setValue("TÊN KHÁCH HÀNG");
		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
		
		cell = cells.getCell("E8");	cell.setValue("MÃ SẢN PHẨM");
		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);

		cell = cells.getCell("F8");	cell.setValue("TÊN SẢN PHẨM");
		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
		
		cell = cells.getCell("G8");	cell.setValue("SỐ LƯỢNG");
		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
		
		cell = cells.getCell("H8");	cell.setValue("ĐƠN GIÁ");
		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
		
		cell = cells.getCell("I8");	cell.setValue("THÀNH TIỀN");
		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
		
  }
	
	private String Doisangchuoi(String[] checknpp)
	{
		// TODO Auto-generated method stub
		String str = "";
		if (checknpp != null)
		{
			for (int i = 0; i < checknpp.length; i++)
			{
				if (i == 0)
				{
					str = checknpp[i];
				} else
				{
					str = str + "," + checknpp[i];
				}
			}
		}
		return str;

	}
	
}
