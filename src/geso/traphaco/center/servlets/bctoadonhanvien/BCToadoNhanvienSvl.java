package geso.traphaco.center.servlets.bctoadonhanvien;

import geso.traphaco.center.beans.bctoadonhanvien.IBCToadoNhanvien;
import geso.traphaco.center.beans.bctoadonhanvien.imp.BCToadoNhanvien;
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

public class BCToadoNhanvienSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public BCToadoNhanvienSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IBCToadoNhanvien obj;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
	    HttpSession session = request.getSession();	    

	    Utility util = new Utility();
	    	   
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    obj = new BCToadoNhanvien();
	    obj.setUserId(userId);
	    obj.init();
	    
	    String nextJSP = "";

		nextJSP = "/TraphacoHYERP/pages/Center/BCToadoNhanvien.jsp";
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
	    
	    IBCToadoNhanvien obj = new BCToadoNhanvien();
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
				response.setHeader("Content-Disposition", "attachment; filename=BCToadoNhanvien.xlsm");
	
		    	//response.setContentType("application/vnd.ms-excel");
		        //response.setHeader("Content-Disposition", "attachment; filename=BCHoadonbanra.xls");
		        
		        FileInputStream fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\BCToadoNhanvien.xlsm");
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
			nextJSP = "/TraphacoHYERP/pages/Center/BCToadoNhanvien.jsp";
    		response.sendRedirect(nextJSP); 
    		return;
	    }
	    else
	    {
			String nextJSP = "";
			if (action.equals("excel") && ( tungay.trim().length() == 0 && denngay.trim().length() == 0 ) )
				obj.setMsg("Vui lòng chọn ngày bắt đầu và kết thúc");
			session.setAttribute("obj", obj);
			nextJSP = "/TraphacoHYERP/pages/Center/BCToadoNhanvien.jsp";
			response.sendRedirect(nextJSP); 
			return;
	    }
	}
	

	private void CreateStaticData(Workbook workbook, IBCToadoNhanvien obj ) 
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
		int i = 1;
		
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
		cell = cells.getCell("CK" + Integer.toString(i));	cell.setValue("CNDT"); 
		 
		if(bcRs != null)
		{
			try 
			{
				while(bcRs.next())
				{			
					i++;
					cell = cells.getCell("CA" + Integer.toString(i));	cell.setValue(bcRs.getString("NGAY")); 	  
					cell = cells.getCell("CB" + Integer.toString(i));	cell.setValue(bcRs.getString("TEN")); 
					cell = cells.getCell("CC" + Integer.toString(i));	cell.setValue(bcRs.getInt("STT")); 
					cell = cells.getCell("CD" + Integer.toString(i));	cell.setValue(bcRs.getString("THOIDIEM")); 
					cell = cells.getCell("CE" + Integer.toString(i));	cell.setValue(bcRs.getString("KHOANGCACH")); 	
					cell = cells.getCell("CF" + Integer.toString(i));	cell.setValue(bcRs.getString("DIACHI"));
					cell = cells.getCell("CG" + Integer.toString(i));	cell.setValue(bcRs.getString("LAT")); 	
					cell = cells.getCell("CH" + Integer.toString(i));	cell.setValue(bcRs.getString("LONG"));  
					cell = cells.getCell("CI" + Integer.toString(i));	cell.setValue(bcRs.getString("VUNG")); 	
					cell = cells.getCell("CJ" + Integer.toString(i));	cell.setValue(bcRs.getString("TINHTHANH"));  
					cell = cells.getCell("CK" + Integer.toString(i));	cell.setValue(bcRs.getString("CNDT")); 
					
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
		//style.setHAlignment(HorizontalAlignmentType.CENTRED);
		style.setHAlignment(alignment);
		/*style.setBorderLine(BorderType.TOP, 1);
		style.setBorderLine(BorderType.RIGHT, 1);
		style.setBorderLine(BorderType.BOTTOM, 1);
		style.setBorderLine(BorderType.LEFT, 1);*/
		cell.setStyle(style);
	}
	
}
