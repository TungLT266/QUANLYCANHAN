package geso.traphaco.center.servlets.bchangtralai;

import geso.traphaco.center.beans.bchangtralai.IBCHangtralai;
import geso.traphaco.center.beans.bchangtralai.imp.BCHangtralai;
import geso.traphaco.center.util.Utility;

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

import org.hsqldb.SetFunction;

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

public class BCHangbantralaiSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public BCHangbantralaiSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IBCHangtralai obj;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
	    HttpSession session = request.getSession();	    

	    Utility util = new Utility();
	    	   
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    obj = new BCHangtralai();
	    obj.setUserId(userId);
	    obj.init();
	    
	    String nextJSP = "";

		nextJSP = "/TraphacoHYERP/pages/Center/BCHangbantralai.jsp";
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
	    System.out.println("action " + action);
	    
	    IBCHangtralai obj = new BCHangtralai();
	    obj.setUserId(userId);
	    
	    String tungay = request.getParameter("tungay");
	    if(tungay == null)
	    	tungay = ""; 
	    obj.setTungay(tungay);
	    
	    String denngay = request.getParameter("denngay");
	    if(denngay == null)
	    	denngay = "";
	    obj.setDenngay(denngay);
	    
	    String nhanvienId = request.getParameter("nhanvienId");
	    if(nhanvienId == null)
	    	nhanvienId = "";
	    obj.setNhanvienId(nhanvienId);
	    
	    String vungid = request.getParameter("vungId");
	    if(vungid == null)
	    	vungid = "";
	    obj.setvungId(vungid);
	    
	    String khuvucid = request.getParameter("khuvucId");
	    if(khuvucid == null)
	    	khuvucid = "";
	    obj.setkhuvucId(khuvucid);
	    
	    obj.setTtId(util.antiSQLInspection(request.getParameter("ttId"))!=null?
				util.antiSQLInspection(request.getParameter("ttId")):"");
	    
	    obj.setNppId((util.antiSQLInspection(request.getParameter("nppId"))!=null?
				util.antiSQLInspection(request.getParameter("nppId")):""));
	    
	    String loai = request.getParameter("loai");
	    if(loai == null)
	    	loai = "";
	    obj.setLoai(loai);
	    
	    obj.init();
	    if (action.equals("excel") && ( tungay.trim().length() > 0 && denngay.trim().length() > 0 ) )
	    {
	    	try
		    {
				response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "attachment; filename=BCHangtralai.xlsm");
	
		    	//response.setContentType("application/vnd.ms-excel");
		        //response.setHeader("Content-Disposition", "attachment; filename=BCHoadonbanra.xls");
		        
		        FileInputStream fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\BCHangtralai.xlsm");
				Workbook workbook = new Workbook();
				workbook.open(fstream);
				workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
				
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
			nextJSP = "/TraphacoHYERP/pages/Center/BCHangbantralai.jsp";
    		response.sendRedirect(nextJSP); 
    		return;
	    }
	    else
	    {
			String nextJSP = "";
			if (action.equals("excel") && ( tungay.trim().length() == 0 && denngay.trim().length() == 0 ) )
				obj.setMsg("Vui lòng chọn ngày bắt đầu và kết thúc");
			session.setAttribute("obj", obj);
			nextJSP = "/TraphacoHYERP/pages/Center/BCHangbantralai.jsp";
			response.sendRedirect(nextJSP); 
			return;
	    }
	}
	

	private void CreateStaticData(Workbook workbook, IBCHangtralai obj ) 
	{
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	    Cells cells = worksheet.getCells();

		ResultSet bcRs = obj.getBcRs();
		
		Cell cell = null;
		
		Style style;
		Font font2 = new Font();	
		font2.setName("Times New Roman");
		font2.setSize(12);
		
		Font font3 = new Font();
		font3.setName("Times New Roman");
		font3.setSize(12);
		font3.setBold(true);
		cell = cells.getCell("B3");	cell.setValue(obj.getTungay());
		cell = cells.getCell("B4");	cell.setValue(obj.getDenngay());
		int i = 6;
		/*
		cell = cells.getCell("CA" + Integer.toString(i));	cell.setValue("NGAY");		 				
		cell = cells.getCell("CB" + Integer.toString(i));	cell.setValue("NHANVIEN");
		cell = cells.getCell("CC" + Integer.toString(i));	cell.setValue("STT"); 	
		cell = cells.getCell("CD" + Integer.toString(i));	cell.setValue("THOIDIEM");
		cell = cells.getCell("CE" + Integer.toString(i));	cell.setValue("KHOANGCACH");
		cell = cells.getCell("CF" + Integer.toString(i));	cell.setValue("DIACHI");
		cell = cells.getCell("CG" + Integer.toString(i));	cell.setValue("LAT"); 	
		cell = cells.getCell("CH" + Integer.toString(i));	cell.setValue("LONG"); 
		cell = cells.getCell("CI" + Integer.toString(i));	cell.setValue("VUNG"); 
		cell = cells.getCell("CJ" + Integer.toString(i));	cell.setValue("TINHTHANH"); 
		cell = cells.getCell("CK" + Integer.toString(i));	cell.setValue("CNDT"); */
		float tongtien =0;
		float tongthue =0;
		float tongcong =0;
		String tongc ="Tổng cộng";
		
		if(bcRs != null)
		{
			try 
			{
				while(bcRs.next())
				{			
					i++;
					cell = cells.getCell("A" + Integer.toString(i));	cell.setValue(bcRs.getString("STT")); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					cell = cells.getCell("B" + Integer.toString(i));	cell.setValue(bcRs.getString("Mien")); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					cell = cells.getCell("C" + Integer.toString(i));	cell.setValue(bcRs.getString("DiaBan")); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					cell = cells.getCell("D" + Integer.toString(i));	cell.setValue(bcRs.getString("nhaphanphoi")); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					cell = cells.getCell("E" + Integer.toString(i));	cell.setValue(bcRs.getString("sohd")); 	setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					cell = cells.getCell("F" + Integer.toString(i));	cell.setValue(bcRs.getString("ngayhd"));setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					cell = cells.getCell("G" + Integer.toString(i));	cell.setValue(bcRs.getString("mavattu")); 	setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					cell = cells.getCell("H" + Integer.toString(i));	cell.setValue(bcRs.getString("tenvattu"));  setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					cell = cells.getCell("I" + Integer.toString(i));	cell.setValue(bcRs.getString("dvt")); 	setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					cell = cells.getCell("J" + Integer.toString(i));	cell.setValue(bcRs.getFloat("soluong"));  setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					cell = cells.getCell("K" + Integer.toString(i));	cell.setValue(bcRs.getFloat("dongia")); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					cell = cells.getCell("L" + Integer.toString(i));	cell.setValue(bcRs.getFloat("thanhtien")); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					cell = cells.getCell("M" + Integer.toString(i));	cell.setValue(bcRs.getFloat("thue"));setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					cell = cells.getCell("N" + Integer.toString(i));	cell.setValue(bcRs.getFloat("tongtien")); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					
					if(bcRs.getInt("stt") == bcRs.getInt("Sonpp")  )
					{
						i++;
						cell = cells.getCell("A" + Integer.toString(i));	cell.setValue(tongc); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
						style = cell.getStyle();
						font2 = style.getFont();
						font2.setBold(true);
						style.setFont(font2);
						cell.setStyle(style);
						cell = cells.getCell("B" + Integer.toString(i));	cell.setValue(""); setCellBorderStyle_TB(cell, HorizontalAlignmentType.LEFT);
						cell = cells.getCell("C" + Integer.toString(i));	cell.setValue(""); setCellBorderStyle_TB(cell, HorizontalAlignmentType.LEFT);
						cell = cells.getCell("D" + Integer.toString(i));	cell.setValue(""); setCellBorderStyle_TB(cell, HorizontalAlignmentType.LEFT);
						cell = cells.getCell("E" + Integer.toString(i));	cell.setValue(""); setCellBorderStyle_TB(cell, HorizontalAlignmentType.LEFT);
						cell = cells.getCell("F" + Integer.toString(i));	cell.setValue(""); setCellBorderStyle_TB(cell, HorizontalAlignmentType.LEFT);
						cell = cells.getCell("G" + Integer.toString(i));	cell.setValue(""); setCellBorderStyle_TB(cell, HorizontalAlignmentType.LEFT);
						cell = cells.getCell("H" + Integer.toString(i));	cell.setValue(""); setCellBorderStyle_TB(cell, HorizontalAlignmentType.LEFT);
						cell = cells.getCell("I" + Integer.toString(i));	cell.setValue(""); setCellBorderStyle_TB(cell, HorizontalAlignmentType.LEFT);
						cell = cells.getCell("J" + Integer.toString(i));	cell.setValue(""); setCellBorderStyle_TB(cell, HorizontalAlignmentType.LEFT);
						cell = cells.getCell("K" + Integer.toString(i));	cell.setValue(""); setCellBorderStyle_TB(cell, HorizontalAlignmentType.LEFT);
						cell = cells.getCell("L" + Integer.toString(i));	cell.setValue(tongtien +=bcRs.getFloat("thanhtien")); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);style = cell.getStyle();font2 = style.getFont();font2.setBold(true);style.setFont(font2);cell.setStyle(style);
						cell = cells.getCell("M" + Integer.toString(i));	cell.setValue(tongthue +=bcRs.getFloat("thue"));setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);	style = cell.getStyle();font2 = style.getFont();font2.setBold(true);style.setFont(font2);cell.setStyle(style);
						cell = cells.getCell("N" + Integer.toString(i));	cell.setValue(tongcong +=bcRs.getFloat("tongtien")); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);style = cell.getStyle();font2 = style.getFont();font2.setBold(true);style.setFont(font2);cell.setStyle(style);
						tongtien = 0;
						tongthue = 0;
						tongcong = 0;
					}
					else
					{
						tongtien += bcRs.getFloat("tongtien");
						tongthue +=bcRs.getFloat("thue");
						tongcong +=bcRs.getFloat("tongtien");
					}
					
					
					
				}
				bcRs.close();
				
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
		style.setHAlignment(alignment);
		style.setBorderLine(BorderType.TOP, 1);
		style.setBorderLine(BorderType.RIGHT, 1);
		style.setBorderLine(BorderType.BOTTOM, 1);
		style.setBorderLine(BorderType.LEFT, 1);
		cell.setStyle(style);
	}
	
	private void setCellBorderStyle_TB(Cell cell, short alignment) 
	{
		Style style = cell.getStyle();
		style.setHAlignment(alignment);
		style.setBorderLine(BorderType.TOP, 1);
		style.setBorderLine(BorderType.BOTTOM, 1);

		cell.setStyle(style);
	}
	
}
