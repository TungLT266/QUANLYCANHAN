package geso.traphaco.distributor.servlets.hoadontaichinh;

import geso.traphaco.center.servlets.report.ReportAPI;
import geso.traphaco.distributor.beans.hoadontaichinh.ISoathoadon;
import geso.traphaco.distributor.beans.hoadontaichinh.imp.Soathoadon;
import geso.traphaco.distributor.util.Utility;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aspose.cells.BorderLineType;
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

public class SoathoadonSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public SoathoadonSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		ISoathoadon obj;
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
	    
	    obj = new Soathoadon();
	    obj.setUserId(userId);
	    obj.setLoaidonhang(loaihoadon);
	    obj.createRs();
	    
	    String nextJSP = "";
	    if(loaihoadon.equals("0"))
		{
			//obj.init();
			nextJSP = "/TraphacoHYERP/pages/Distributor/SoatHoaDon.jsp";
		}
		else
		{
			//obj.initETC();
			nextJSP = "/TraphacoHYERP/pages/Distributor/DoiSoHoaDon.jsp";
		}
	    
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
	    
	    ISoathoadon obj = new Soathoadon();
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
	    
	    String kyhieuhd = request.getParameter("kyhieuhd");
	    if(kyhieuhd == null)
	    	kyhieuhd = "";
	    obj.setKyhieuHD(kyhieuhd);
	    
	    String nvbhId = request.getParameter("nvbhId");
	    if(nvbhId == null)
	    	nvbhId = "";
	    obj.setNvbhId(nvbhId);
	
	    String maFAST = request.getParameter("maFAST");
	    if(maFAST == null)
	    	maFAST = "";
	    obj.setMaFast(maFAST);

	    if (action.equals("excel"))
	    {
	    	if(loaihoadon.equals("0"))
				obj.init();
			else
				obj.initETC();

	    	try
		    {
				response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "attachment; filename=SoatHoaDon.xlsm");
	
		    	//response.setContentType("application/vnd.ms-excel");
		        //response.setHeader("Content-Disposition", "attachment; filename=SoatHoaDon.xls");
		        
		        FileInputStream fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\SoatHoaDon.xlsm");
				Workbook workbook = new Workbook();
				workbook.open(fstream);
				workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
				
				CreateStaticHeader(workbook, "");
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
    		
    		if(loaihoadon.equals("0"))
				nextJSP = "/TraphacoHYERP/pages/Distributor/SoatHoaDon.jsp";
			else
				nextJSP = "/TraphacoHYERP/pages/Distributor/SoatHoaDonETC.jsp"; 	
    		response.sendRedirect(nextJSP); 
	    }
	    else
	    {
		    if(action.equals("save"))
		    	obj.save(request);
		    else if(action.equals("saveDOIHOADON"))
		    	obj.saveDoihoadon(request);
		    
			session.setAttribute("obj", obj);
			String nextJSP = "";
			
			if(loaihoadon.equals("0"))
			{
				System.out.println("vào đây!!!");
				obj.init();
				nextJSP = "/TraphacoHYERP/pages/Distributor/SoatHoaDon.jsp";
			}
			else
			{
				System.out.println("vào đây2222!!!");
				obj.initETC();
				nextJSP = "/TraphacoHYERP/pages/Distributor/DoiSoHoaDon.jsp";
			}
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

	private void CreateStaticData(Workbook workbook, ISoathoadon obj ) 
	{
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	    Cells cells = worksheet.getCells();

		ResultSet hdRs = obj.getHoadonRs();
		
		NumberFormat format = new DecimalFormat("#,###,###"); 

		int i = 5;
		if(hdRs != null)
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
				cells.setColumnWidth(7, 35.0f);
				cells.setColumnWidth(8, 15.0f);
				cells.setColumnWidth(9, 15.0f);
				cells.setColumnWidth(10, 15.0f);

				Cell cell = null;
				
				Style style;
				Font font2 = new Font();
				font2.setName("Calibri");				
				font2.setSize(11);
				
				while(hdRs.next())
				{
					String trangthai = "";
            		String tt = hdRs.getString("trangthai");
            		if(tt.equals("1")){ //NPP TAO
            			trangthai = "Chờ xác nhận";
            		}else
            		{
            			if(tt.equals("2")) {
                			trangthai = "Đã xác nhận";
            			}else{
            				if(tt.equals("3")) 
            					trangthai = "Đã xóa";
            				else 
            				{
            					if(tt.equals("5")) 
                					trangthai = "Đã hủy";
            					else
            						trangthai = "Đã in HĐ";
            				}
            			}
            		}
            		
					cell = cells.getCell("A" + Integer.toString(i));	cell.setValue(hdRs.getString("pk_seq")); 			style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
					cell = cells.getCell("B" + Integer.toString(i));	cell.setValue(hdRs.getString("ngayxuatHD")); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
					cell = cells.getCell("C" + Integer.toString(i));	cell.setValue(hdRs.getString("sohoadon")); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);		
					cell = cells.getCell("D" + Integer.toString(i));	cell.setValue(hdRs.getString("kyhieu")); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
					cell = cells.getCell("E" + Integer.toString(i));	cell.setValue(hdRs.getString("loaiHD")); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					cell = cells.getCell("F" + Integer.toString(i));	cell.setValue(trangthai); 			style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					cell = cells.getCell("G" + Integer.toString(i));	cell.setValue(hdRs.getString("MAFAST")); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					cell = cells.getCell("H" + Integer.toString(i));	cell.setValue(hdRs.getString("khTEN")); 			style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);													
					cell = cells.getCell("I" + Integer.toString(i));	cell.setValue((hdRs.getDouble("tongtienBVAT"))); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
					style.setNumber(3);
					cell = cells.getCell("J" + Integer.toString(i));	cell.setValue((hdRs.getDouble("VAT"))); 			style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
					style.setNumber(3);
					cell = cells.getCell("K" + Integer.toString(i));	cell.setValue((hdRs.getDouble("tongtienAVAT"))); 			style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);					
					style.setNumber(3);
					
					i++;
				}
				hdRs.close();
			}
			catch (Exception e)
			{ 
				e.printStackTrace(); 
			}
		}
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
		style.setBorderLine(BorderType.TOP, 1);
		style.setBorderLine(BorderType.RIGHT, 1);
		style.setBorderLine(BorderType.BOTTOM, 1);
		style.setBorderLine(BorderType.LEFT, 1);
		cell.setStyle(style);
	}
	
}
