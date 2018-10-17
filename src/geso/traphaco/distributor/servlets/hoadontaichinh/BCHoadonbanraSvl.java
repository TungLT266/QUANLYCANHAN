package geso.traphaco.distributor.servlets.hoadontaichinh;

import geso.traphaco.distributor.beans.hoadontaichinh.IBCHoadonbanra;
import geso.traphaco.distributor.beans.hoadontaichinh.imp.BCHoadonbanra;
import geso.traphaco.distributor.util.Utility;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aspose.cells.BorderType;
import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Font;
import com.aspose.cells.HorizontalAlignmentType;
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

public class BCHoadonbanraSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public BCHoadonbanraSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IBCHoadonbanra obj;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
	    HttpSession session = request.getSession();	    

	    Utility util = new Utility();
	    	   
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    String loaihoadon = request.getParameter("loaihoadon");
	    if(loaihoadon == null)
	    	loaihoadon = "0";
	    
	    obj = new BCHoadonbanra();
	    obj.setUserId(userId);
	    obj.setLoaidonhang(loaihoadon);
	    obj.createRs();
	    
	    String nextJSP = "";

		nextJSP = "/TraphacoHYERP/pages/Distributor/BCHoaDonBanRa.jsp";
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
	    
	    IBCHoadonbanra obj = new BCHoadonbanra();
	    obj.setUserId(userId);
	    
	    String tungay = request.getParameter("tungay");
	    if(tungay == null)
	    	tungay = ""; 
	    obj.setTungay(tungay);
	    
	    String denngay = request.getParameter("denngay");
	    if(denngay == null)
	    	denngay = "";
	    obj.setDenngay(denngay);
	    
	    String khId = request.getParameter("khId");
	    if(khId == null)
	    	khId = "";
	    obj.setKhTen(khId);
	    
	    String loaihoadon = request.getParameter("loaihoadon");
	    if(loaihoadon == null)
	    	loaihoadon = "0";
	    obj.setLoaidonhang(loaihoadon);
	    
	    String sohoadontu = request.getParameter("sohoadontu");
	    if(sohoadontu == null)
	    	sohoadontu = "";
	    obj.setSohoadontu(sohoadontu);
	    
	    String sohoadonden = request.getParameter("sohoadonden");
	    if(sohoadonden == null)
	    	sohoadonden = "";
	    obj.setSohoadonden(sohoadonden);
	    
	    String ptVAT = request.getParameter("ptVAT");
	    if(ptVAT == null)
	    	ptVAT = "";
	    obj.setPtVat(ptVAT);
	    
	    String trangthai = request.getParameter("trangthai");
	    if(trangthai == null)
	    	trangthai = "";
	    obj.setTrangthai(trangthai);
	    
	    String nvbhId = request.getParameter("nvbhId");
	    if(nvbhId == null)
	    	nvbhId = "";
	    obj.setNvbhId(nvbhId);
	    
	    String maFAST = request.getParameter("maFAST");
	    if(maFAST == null)
	    	maFAST = "";
	    obj.setMaFast(maFAST);
	    
	    String view=request.getParameter("view");
	  		if(view == null)
	  			view = "";
	  		
  		String mauhoadon=request.getParameter("mauhoadon");
  		if(mauhoadon == null)
  			mauhoadon = "";	
  		
  		obj.setMauhoadon(mauhoadon);
  		
  		
	  		

	    if (action.equals("excel") && ( tungay.trim().length() > 0 && denngay.trim().length() > 0 ) )
	    {
	    	try
		    {
				response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "attachment; filename=BangKeHoaDon.xlsm");
	
		    	//response.setContentType("application/vnd.ms-excel");
		        //response.setHeader("Content-Disposition", "attachment; filename=BCHoadonbanra.xls");
		        
		        FileInputStream fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\BangKeBanRa.xlsm");
				Workbook workbook = new Workbook();
				workbook.open(fstream);
				workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
				
				//CreateStaticHeader(workbook, "");
				obj.init();
			    CreateStaticData(workbook, obj);
				
				workbook.save(out);
				fstream.close();
		    }
		    catch (Exception ex)
		    {
		    	ex.printStackTrace();
		        obj.setMsg("Khong the tao pivot.");
		    }
	    	
			obj.setUserId(userId);
			
	    	session.setAttribute("obj", obj);
    		session.setAttribute("userId", userId);
    		String nextJSP = "";
    		
			nextJSP = "/TraphacoHYERP/pages/Distributor/BCHoaDonBanRa.jsp";
    		response.sendRedirect(nextJSP); 
	    }
	    else
	    {
			session.setAttribute("obj", obj);
			String nextJSP = "";
			
			//obj.init();
			obj.setMsg("Bạn phải chọn khoảng thời gian lấy báo cáo");
			nextJSP = "/TraphacoHYERP/pages/Distributor/BCHoaDonBanRa.jsp";
			
			response.sendRedirect(nextJSP);  
	    }
	}
	

	private void CreateStaticHeader(Workbook workbook, String UserName) 
	{
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	   	   
	    Cells cells = worksheet.getCells();
	    Style style;
	   	    
	    Cell cell = cells.getCell("A1"); 
	    cell.setValue("SOÁT HÓA ĐƠN");	    
	    style = cell.getStyle();
		Font font2 = new Font();	
		font2.setName("Calibri");
		font2.setColor(Color.NAVY);
		font2.setSize(18);
		font2.setBold(true);
		style.setFont(font2);
		style.setHAlignment(TextAlignmentType.LEFT);					
		cell.setStyle(style);
		
		font2 = new Font();	
		font2.setName("Calibri");
		font2.setBold(false);
		font2.setSize(10);
	   
	    cell = cells.getCell("A2");
	    cell.setValue("Ngày tạo : " + this.getDateTime());
	    style = cell.getStyle();
	    style.setFont(font2);
		cell.setStyle(style);
	    
	    //tieu de
	    cell = cells.getCell("A4");cell.setValue("Mã hóa đơn");  		style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell);    
	    cell = cells.getCell("B4");cell.setValue("Ngày hóa đơn");  				style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell);
	    cell = cells.getCell("C4");cell.setValue("Số hóa đơn");  					style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell);
	    cell = cells.getCell("D4");cell.setValue("Ký hiệu hóa đơn"); 					style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell);
	    cell = cells.getCell("E4");cell.setValue("Loại hóa đơn");  				style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell);
	    cell = cells.getCell("F4");cell.setValue("Trạng thái");  				style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell);
	    cell = cells.getCell("G4");cell.setValue("Mã khách hàng"); 					style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell);
	    cell = cells.getCell("H4");cell.setValue("Họ tên");  				style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell);
	    cell = cells.getCell("I4");cell.setValue("Tổng tiền trước thuế");  				style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell);
	    cell = cells.getCell("J4");cell.setValue("Tổng tiền thuế"); 			style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell);
	    cell = cells.getCell("K4");cell.setValue("Số tiền hóa đơn"); 	 		style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell);
	   
	    worksheet.setName("Sheet1");
	}

	private void CreateStaticData(Workbook workbook, IBCHoadonbanra obj ) 
	{
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	    Cells cells = worksheet.getCells();

		ResultSet hdRs = obj.getHoadonRs();
		
		//NumberFormat format = new DecimalFormat("#,###,###"); 

		Cell cell_mau01 = cells.getCell("A1");
		Cell cell_mau02 = cells.getCell("M1");
		
		Cell cell_mau = cells.getCell("C7");
		Cell cell_mau2 = cells.getCell("L7");
		
		Cell cell = null;
		
		Style style;
		Font font2 = new Font();
		font2.setName("Tahoma");				
		font2.setSize(8);
		
		Font font3 = new Font();
		font3.setName("Tahoma");				
		font3.setSize(8);
		font3.setBold(true);
		
		
		int i = 12;
		
		//IN COT TIEU DE  --> 5%
		cell = cells.getCell("A" + Integer.toString(i));	cell.setValue(""); 			style = cell_mau01.getStyle(); style.setFont(font2); cell.setStyle(style); 
		
		cells.merge(i-1, 1, i-1, 10);	
		cell = cells.getCell("B" + Integer.toString(i));	
		cell.setValue("3. Hàng hóa, dịch vụ chịu thuế suất thuế GTGT 5%"); 	style = cell_mau.getStyle(); style.setFont(font3); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
		
		cell = cells.getCell("C" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
		cell = cells.getCell("D" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
		
		cell = cells.getCell("E" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);		
		cell = cells.getCell("F" + Integer.toString(i));	cell.setValue(""); 	style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		cell = cells.getCell("G" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
		cell = cells.getCell("H" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		cell = cells.getCell("I" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT); 
		cell = cells.getCell("J" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
		cell = cells.getCell("K" + Integer.toString(i));	cell.setValue(""); 			style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
		cell = cells.getCell("L" + Integer.toString(i));	cell.setValue(""); 			style = cell_mau2.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
		
		cell = cells.getCell("M" + Integer.toString(i));	cell.setValue(""); 			style = cell_mau02.getStyle(); style.setFont(font2); cell.setStyle(style); 
		i++;
		
		if(hdRs != null)
		{
			try 
			{
				int stt = 1;
				long totalTRUOCVAT = 0;
				long totalVAT = 0;
				while(hdRs.next())
				{
					long tongtienBVAT = Math.round(hdRs.getDouble("tongtienBVAT"));
					//long BVAT =  Math.round(0.05 * hdRs.getDouble("tongtienBVAT"));
					long BVAT =  Math.round(hdRs.getDouble("tienVAT"));
					
					//totalTRUOCVAT += Math.round(hdRs.getDouble("tongtienBVAT"));
					//totalVAT += Math.round(0.05 * hdRs.getDouble("tongtienBVAT"));
					
					totalTRUOCVAT += tongtienBVAT;
					totalVAT += BVAT;
					
					cell = cells.getCell("A" + Integer.toString(i));	cell.setValue(""); 			style = cell_mau01.getStyle(); style.setFont(font2); cell.setStyle(style); 
					
            		cell = cells.getCell("B" + Integer.toString(i));	cell.setValue(stt); 			style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
					cell = cells.getCell("C" + Integer.toString(i));	cell.setValue("01GTKT4/001"); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					cell = cells.getCell("D" + Integer.toString(i));	cell.setValue(hdRs.getString("kyhieu")); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					cell = cells.getCell("E" + Integer.toString(i));	cell.setValue(hdRs.getString("sohoadon")); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);		
					cell = cells.getCell("F" + Integer.toString(i));	cell.setValue(obj.getFormatDate(hdRs.getString("ngayxuatHD"))); 	style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
					cell = cells.getCell("G" + Integer.toString(i));	cell.setValue(hdRs.getString("khTEN")); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					cell = cells.getCell("H" + Integer.toString(i));	cell.setValue(hdRs.getString("masothue")); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
					cell = cells.getCell("I" + Integer.toString(i));	cell.setValue(hdRs.getString("spTEN")); 			style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT); 
					cell = cells.getCell("J" + Integer.toString(i));	cell.setValue(tongtienBVAT); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
					cell = cells.getCell("K" + Integer.toString(i));	cell.setValue(BVAT); 			style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
					cell = cells.getCell("L" + Integer.toString(i));	cell.setValue(""); 			style = cell_mau2.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					
					cell = cells.getCell("M" + Integer.toString(i));	cell.setValue(""); 			style = cell_mau02.getStyle(); style.setFont(font2); cell.setStyle(style); 
					
					i++;
					stt ++;
				}
				hdRs.close();
				
				
				//THEM DONG TONG CONG
				cell = cells.getCell("A" + Integer.toString(i));	cell.setValue(""); 			style = cell_mau01.getStyle(); style.setFont(font2); cell.setStyle(style); 
				
        		cells.merge(i-1, 1, i-1, 3);	
        		cell = cells.getCell("B" + Integer.toString(i));	
        	    //MergeCellAndBorder(cells, i-1, 1, i-1, 3);
        		cell.setValue("Tổng"); 	style = cell_mau.getStyle(); style.setFont(font3); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
        		
				cell = cells.getCell("C" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
				cell = cells.getCell("D" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
				
        		cell = cells.getCell("E" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);		
				cell = cells.getCell("F" + Integer.toString(i));	cell.setValue(""); 	style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
				cell = cells.getCell("G" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
				cell = cells.getCell("H" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
				cell = cells.getCell("I" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT); 
				cell = cells.getCell("J" + Integer.toString(i));	cell.setValue(totalTRUOCVAT); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
				cell = cells.getCell("K" + Integer.toString(i));	cell.setValue(totalVAT); 			style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
				cell = cells.getCell("L" + Integer.toString(i));	cell.setValue(""); 			style = cell_mau2.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
				
				cell = cells.getCell("M" + Integer.toString(i));	cell.setValue(""); 			style = cell_mau02.getStyle(); style.setFont(font2); cell.setStyle(style); 
				i++;
				
			}
			catch (Exception e)
			{ 
				e.printStackTrace(); 
			}
		}
		
		
		//IN COT TIEU DE  --> 10%
		cell = cells.getCell("A" + Integer.toString(i));	cell.setValue(""); 			style = cell_mau01.getStyle(); style.setFont(font2); cell.setStyle(style); 
		
		cells.merge(i-1, 1, i-1, 10);	
		cell = cells.getCell("B" + Integer.toString(i));	
		cell.setValue("4. Hàng hóa, dịch vụ chịu thuế suất thuế GTGT 10%"); 	style = cell_mau.getStyle(); style.setFont(font3); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
		
		cell = cells.getCell("C" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
		cell = cells.getCell("D" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
		
		cell = cells.getCell("E" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);		
		cell = cells.getCell("F" + Integer.toString(i));	cell.setValue(""); 	style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		cell = cells.getCell("G" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
		cell = cells.getCell("H" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		cell = cells.getCell("I" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT); 
		cell = cells.getCell("J" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
		cell = cells.getCell("K" + Integer.toString(i));	cell.setValue(""); 			style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
		cell = cells.getCell("L" + Integer.toString(i));	cell.setValue(""); 			style = cell_mau2.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
		
		cell = cells.getCell("M" + Integer.toString(i));	cell.setValue(""); 			style = cell_mau02.getStyle(); style.setFont(font2); cell.setStyle(style); 
		i++;
		
		hdRs = obj.getHoadon10PtRs();
		if(hdRs != null)
		{
			try 
			{
				int stt = 1;
				long totalTRUOCVAT = 0;
				long totalVAT = 0;
				while(hdRs.next())
				{
					long tongtienBVAT = Math.round(hdRs.getDouble("tongtienBVAT"));
					//long BVAT =  Math.round(0.1 * hdRs.getDouble("tongtienBVAT"));
					long BVAT =  Math.round(hdRs.getDouble("tienVAT"));
				
					//totalTRUOCVAT += Math.round(hdRs.getDouble("tongtienBVAT"));
					//totalVAT += Math.round(0.1 * hdRs.getDouble("tongtienBVAT"));
					
					totalTRUOCVAT += tongtienBVAT;
					totalVAT += BVAT;
						
					cell = cells.getCell("A" + Integer.toString(i));	cell.setValue(""); 			style = cell_mau01.getStyle(); style.setFont(font2); cell.setStyle(style); 
					
            		cell = cells.getCell("B" + Integer.toString(i));	cell.setValue(stt); 			style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
					cell = cells.getCell("C" + Integer.toString(i));	
					
					//cell.setValue(hdRs.getString("kyhieu")); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					cell.setValue("01GTKT4/001"); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					cell = cells.getCell("D" + Integer.toString(i));	cell.setValue(hdRs.getString("kyhieu")); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					cell = cells.getCell("E" + Integer.toString(i));	cell.setValue(hdRs.getString("sohoadon")); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);		
					cell = cells.getCell("F" + Integer.toString(i));	cell.setValue(obj.getFormatDate(hdRs.getString("ngayxuatHD"))); 	style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
					cell = cells.getCell("G" + Integer.toString(i));	cell.setValue(hdRs.getString("khTEN")); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					cell = cells.getCell("H" + Integer.toString(i));	cell.setValue(hdRs.getString("masothue")); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
					cell = cells.getCell("I" + Integer.toString(i));	cell.setValue(hdRs.getString("spTEN")); 			style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT); 
					cell = cells.getCell("J" + Integer.toString(i));	cell.setValue(tongtienBVAT); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
					cell = cells.getCell("K" + Integer.toString(i));	cell.setValue(BVAT); 			style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
					cell = cells.getCell("L" + Integer.toString(i));	cell.setValue(""); 			style = cell_mau2.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					
					cell = cells.getCell("M" + Integer.toString(i));	cell.setValue(""); 			style = cell_mau02.getStyle(); style.setFont(font2); cell.setStyle(style); 
					
					i++;
					stt ++;
				}
				hdRs.close();
				
				
				//THEM DONG TONG CONG
				cell = cells.getCell("A" + Integer.toString(i));	cell.setValue(""); 			style = cell_mau01.getStyle(); style.setFont(font2); cell.setStyle(style); 
				
        		cells.merge(i-1, 1, i-1, 3);	
        		cell = cells.getCell("B" + Integer.toString(i));	
        	    //MergeCellAndBorder(cells, i-1, 1, i-1, 3);
        		cell.setValue("Tổng"); 	style = cell_mau.getStyle(); style.setFont(font3); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
        		
				cell = cells.getCell("C" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
				cell = cells.getCell("D" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
				
        		cell = cells.getCell("E" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);		
				cell = cells.getCell("F" + Integer.toString(i));	cell.setValue(""); 	style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
				cell = cells.getCell("G" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
				cell = cells.getCell("H" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
				cell = cells.getCell("I" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT); 
				cell = cells.getCell("J" + Integer.toString(i));	cell.setValue(totalTRUOCVAT); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
				cell = cells.getCell("K" + Integer.toString(i));	cell.setValue(totalVAT); 			style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
				cell = cells.getCell("L" + Integer.toString(i));	cell.setValue(""); 			style = cell_mau2.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
				
				cell = cells.getCell("M" + Integer.toString(i));	cell.setValue(""); 			style = cell_mau02.getStyle(); style.setFont(font2); cell.setStyle(style); 
				i++;
				
			}
			catch (Exception e)
			{ 
				e.printStackTrace(); 
			}
		}
		
		
		//IN COT TIEU DE  --> 10%
		cell = cells.getCell("A" + Integer.toString(i));	cell.setValue(""); 			style = cell_mau01.getStyle(); style.setFont(font2); cell.setStyle(style); 
		
		cells.merge(i-1, 1, i-1, 10);	
		cell = cells.getCell("B" + Integer.toString(i));	
		cell.setValue("5. Hàng hóa, dịch vụ không phải tổng hợp trên tờ khai 01/GTGT"); 	style = cell_mau.getStyle(); style.setFont(font3); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
		
		cell = cells.getCell("C" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
		cell = cells.getCell("D" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
		
		cell = cells.getCell("E" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);		
		cell = cells.getCell("F" + Integer.toString(i));	cell.setValue(""); 	style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		cell = cells.getCell("G" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
		cell = cells.getCell("H" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		cell = cells.getCell("I" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT); 
		cell = cells.getCell("J" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
		cell = cells.getCell("K" + Integer.toString(i));	cell.setValue(""); 			style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
		cell = cells.getCell("L" + Integer.toString(i));	cell.setValue(""); 			style = cell_mau2.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
		
		cell = cells.getCell("M" + Integer.toString(i));	cell.setValue(""); 			style = cell_mau02.getStyle(); style.setFont(font2); cell.setStyle(style); 
		i++;
		
		
		cell = cells.getCell("A" + Integer.toString(i));	cell.setValue(""); 			style = cell_mau01.getStyle(); style.setFont(font2); cell.setStyle(style); 
		
		cell = cells.getCell("B" + Integer.toString(i));	
		cell.setValue("1"); 	style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
		
		cell = cells.getCell("C" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
		cell = cells.getCell("D" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
		
		cell = cells.getCell("E" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);		
		cell = cells.getCell("F" + Integer.toString(i));	cell.setValue(""); 	style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		cell = cells.getCell("G" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
		cell = cells.getCell("H" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		cell = cells.getCell("I" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT); 
		cell = cells.getCell("J" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
		cell = cells.getCell("K" + Integer.toString(i));	cell.setValue(""); 			style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
		cell = cells.getCell("L" + Integer.toString(i));	cell.setValue(""); 			style = cell_mau2.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
		
		cell = cells.getCell("M" + Integer.toString(i));	cell.setValue(""); 			style = cell_mau02.getStyle(); style.setFont(font2); cell.setStyle(style); 
		i++;
		
		//THEM DONG TONG CONG
		cell = cells.getCell("A" + Integer.toString(i));	cell.setValue(""); 			style = cell_mau01.getStyle(); style.setFont(font2); cell.setStyle(style); 
		
		cells.merge(i-1, 1, i-1, 3);	
		cell = cells.getCell("B" + Integer.toString(i));	
	    //MergeCellAndBorder(cells, i-1, 1, i-1, 3);
		cell.setValue("Tổng"); 	style = cell_mau.getStyle(); style.setFont(font3); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		
		cell = cells.getCell("C" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
		cell = cells.getCell("D" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
		
		cell = cells.getCell("E" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);		
		cell = cells.getCell("F" + Integer.toString(i));	cell.setValue(""); 	style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		cell = cells.getCell("G" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
		cell = cells.getCell("H" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		cell = cells.getCell("I" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT); 
		cell = cells.getCell("J" + Integer.toString(i));	cell.setValue("0"); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
		cell = cells.getCell("K" + Integer.toString(i));	cell.setValue("0"); 			style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
		cell = cells.getCell("L" + Integer.toString(i));	cell.setValue(""); 			style = cell_mau2.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
		
		cell = cells.getCell("M" + Integer.toString(i));	cell.setValue(""); 			style = cell_mau02.getStyle(); style.setFont(font2); cell.setStyle(style); 
		i++;
		
		
	}
	
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	private void setCellBorderStyle(Cell cell, short alignment) 
	{
		Style style = cell.getStyle();
		//style.setHAlignment(HorizontalAlignmentType.CENTRED);
		style.setHAlignment(alignment);
		/*style.setBorderLine(BorderType.TOP, 1);
		style.setBorderLine(BorderType.RIGHT, 1);
		style.setBorderLine(BorderType.BOTTOM, 1);
		style.setBorderLine(BorderType.LEFT, 1);*/
		cell.setStyle(style);
	}
	
	private void setCellBorderStyle2(Cell cell, short alignment) 
	{
		Style style = cell.getStyle();
		style.setHAlignment(alignment);
		style.setBorderLine(BorderType.TOP, 1);
		style.setBorderLine(BorderType.RIGHT, 1);
		style.setBorderLine(BorderType.BOTTOM, 1);
		style.setBorderLine(BorderType.LEFT, 1);
		cell.setStyle(style);
	}
	
	private void setCellBorderStyle(Cell cell) 
	{
		Style style = cell.getStyle();
		style.setHAlignment(HorizontalAlignmentType.CENTRED);
		/*style.setBorderLine(BorderType.TOP, 1);
		style.setBorderLine(BorderType.RIGHT, 1);
		style.setBorderLine(BorderType.BOTTOM, 1);
		style.setBorderLine(BorderType.LEFT, 1);*/
		cell.setStyle(style);
	}
	
	private void setHeaderCell(Cell cell) 
	{		
		Style style = cell.getStyle();		
		style.setHAlignment(HorizontalAlignmentType.CENTRED);
		style.setBorderLine(BorderType.BOTTOM, 1);
		style.setBorderLine(BorderType.LEFT, 1);
		style.setBorderLine(BorderType.TOP, 1);
		style.setBorderLine(BorderType.RIGHT, 1);
		cell.setStyle(style);
	}
	
	private void MergeCellAndBorder(Cells cells,int y1,int x1,int y2,int x2)
	{
		cells.merge(y1, x1, y2, x2);
		cells.getCells(y1, x1, y2, x2, true);
		
		Iterator<Cell> iCell = cells.getCellIterator();
		while (iCell.hasNext()) {
			setHeaderCell(iCell.next());
		}	
	}
	
}
