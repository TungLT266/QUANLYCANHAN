package geso.traphaco.distributor.servlets.reports;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.servlets.report.ReportAPI;
import geso.traphaco.distributor.beans.report.tytrongdoanhsoquy.IBcTyTrongDsQuy;
import geso.traphaco.distributor.beans.report.tytrongdoanhsoquy.imp.BcTyTrongDsQuy;
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
import com.aspose.cells.BorderType;
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

@WebServlet("/BcTyTrongDsQuySvl")
public class BcTyTrongDsQuySvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	public BcTyTrongDsQuySvl()
	{
		super();
		
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		IBcTyTrongDsQuy obj;
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
		
		obj = new BcTyTrongDsQuy();
		obj.setUserId(userId);
		obj.setView("TT");
		String nextJSP = "";
		obj.init("");
		
		
		nextJSP = "/TraphacoHYERP/pages/Distributor/BcTyTrongDsQuy.jsp";
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
    
    IBcTyTrongDsQuy obj = new BcTyTrongDsQuy();
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
    
    String view = request.getParameter("view")==null?"":request.getParameter("view");
	
			String npp="";
			if(view.length()>0  )
			{
				 npp=  request.getParameter("npp");
				 if(npp==null)
					 npp="";
			}
			else 
			{
				npp=util.getIdNhapp(userId);
			}
			obj.setNppId(npp);
			
    
    
	
    obj.setAction(action);
    
    System.out.println("___ATION "+action);
    
    
    if (action.equals("excel")  )
    {
    	try
	    { 
				response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "attachment; filename=BcTyTrongDsQuy.xlsm");
	      FileInputStream fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\BcTyTrongDsQuy.xlsm");
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
  		nextJSP = "/TraphacoHYERP/pages/Distributor/BcTyTrongDsQuy.jsp";
  		response.sendRedirect(nextJSP); 
    }
    else  if(action.equals("view") || action.equals("next") || action.equals("prev")){
    	
    	obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
    	obj.setUserId(userId);
    	obj.init("");
    	
    	session.setAttribute("obj", obj);
    	response.sendRedirect("/TraphacoHYERP/pages/Distributor/BcTyTrongDsQuy.jsp");
    }
    
    else if(action.equals("search"))
    {	
    	obj.setUserId(userId);
    	session.setAttribute("obj", obj);
  		session.setAttribute("userId", userId);
  		obj.init("");
  		String nextJSP = "";
  		nextJSP = "/TraphacoHYERP/pages/Distributor/BcTyTrongDsQuy.jsp";
  		response.sendRedirect(nextJSP); 
    }
    else
    {
			session.setAttribute("obj", obj);
			String nextJSP = "";
			nextJSP = "/TraphacoHYERP/pages/Distributor/BcTyTrongDsQuy.jsp";
			obj.init("");
			response.sendRedirect(nextJSP);  
    }
		
	}
	
	private boolean FillData(Workbook workbook, IBcTyTrongDsQuy obj, String query) throws Exception
  {
		dbutils db = new dbutils();
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);

		Cells cells = worksheet.getCells();		
		ResultSet hdRs = db.get(query);
		
		if(hdRs != null)
  	{
  		try {
  			
			int i = 10;
			Cell cell = null;
			
			double tongds = 0;
			double total_ThuVe = 0;
			double tongdsboga = 0;
			double tongdsxanh = 0;
			double tongdskhac = 0;
			
			
			int so = 1;
			Font f = new Font();
			Style style ;
			
  			while (hdRs.next()) 
  			{    		
				String tenkh = hdRs.getString("khTEN");
  				String makh = hdRs.getString("khMA");
  				String tennpp = hdRs.getString("nppten");
			
  				double doanhsoboga = 0;
  				try{
  					doanhsoboga = hdRs.getDouble("dsnhomhh");
  				
  				}catch(Exception er){
  					
  				}
  				
  				double doanhsoxanh = 0;
  				try{
  					doanhsoxanh = hdRs.getDouble("dsnhomxanh");
  				
  				}catch(Exception er){
  					
  				}
  				
  				double doanhsokhac = 0;
  				try{
  					doanhsokhac = hdRs.getDouble("dsnhomkhac");
  				
  				}catch(Exception er){
  					
  				}
  				
  				double doanhso = doanhsoboga + doanhsoxanh + doanhsokhac;
  				
  				double tileboga =   Math.round(doanhsoboga / doanhso * 100 - 0.5+0.05 )  ;
  				
  				double tilexanh =  Math.round(doanhsoxanh / doanhso * 100 - 0.5+0.05 )  ;  
  				
  				double tilekhac = 100-tileboga-tilexanh;
  				
  				
  			
  				    				
				cell = cells.getCell("A" + Integer.toString(i)); cell.setValue(so);			
				 style = cell.getStyle();
				 f.setColor(Color.BLACK);
				 style.setFont(f);
				 cell.setStyle(style);
				 
				CreateBorderSetting(workbook,"A" + Integer.toString(i));
				
				cell = cells.getCell("B" + Integer.toString(i)); cell.setValue(tennpp);		
				CreateBorderSetting(workbook,"B" + Integer.toString(i));
				

				cell = cells.getCell("C" + Integer.toString(i)); cell.setValue(makh);		
				CreateBorderSetting(workbook,"C" + Integer.toString(i));
				
				
				cell = cells.getCell("D" + Integer.toString(i)); cell.setValue(tenkh);	
				CreateBorderSetting(workbook,"D" + Integer.toString(i));
				
				 
				cell = cells.getCell("E" + Integer.toString(i)); cell.setValue(doanhsoxanh);		
				CreateBorderSetting(workbook,"E" + Integer.toString(i));
				 					
				cell = cells.getCell("F" + Integer.toString(i)); cell.setValue(   Math.round(tilexanh - 0.5+0.05)  );		
				CreateBorderSetting(workbook,"F" + Integer.toString(i));
				
				cell = cells.getCell("G" + Integer.toString(i)); cell.setValue(doanhsoboga);		
				CreateBorderSetting(workbook,"G" + Integer.toString(i));
				
				cell = cells.getCell("H" + Integer.toString(i)); cell.setValue(  Math.round(tileboga - 0.5+0.05   ) );		
				CreateBorderSetting(workbook,"H" + Integer.toString(i));
				
				cell = cells.getCell("I" + Integer.toString(i)); cell.setValue(doanhsokhac);		
				CreateBorderSetting(workbook,"I" + Integer.toString(i));
				
				cell = cells.getCell("J" + Integer.toString(i)); cell.setValue(   Math.round(tilekhac- 0.5+0.05 ) );		
				CreateBorderSetting(workbook,"J" + Integer.toString(i));
				
				cell = cells.getCell("K" + Integer.toString(i)); cell.setValue(doanhso);		
				CreateBorderSetting(workbook,"K" + Integer.toString(i));
				
				
				double ThuVe = hdRs.getDouble("Thuve");
				total_ThuVe+=ThuVe;
				cell = cells.getCell("L" + Integer.toString(i)); cell.setValue(ThuVe);	CreateBorderSetting(workbook,"L" + Integer.toString(i));
				

				tongds += doanhso;
				tongdsboga +=doanhsoboga;
				tongdsxanh += doanhsoxanh;
				tongdskhac += doanhsokhac;

				so++;
				 
				i++;
			}
  			if(hdRs!=null)hdRs.close();
  					
  			
  			 cell = cells.getCell("A" + Integer.toString(i)); cell.setValue("");
			 CreateBorderSetting(workbook,"A" + Integer.toString(i));
			 
			 cell = cells.getCell("B" + Integer.toString(i)); cell.setValue("");
			 CreateBorderSetting(workbook,"B" + Integer.toString(i));	
			 
			 cell = cells.getCell("F" + Integer.toString(i)); cell.setValue(tongdsxanh/tongds * 100);
			 CreateBorderSetting(workbook,"F" + Integer.toString(i));
			 style = cell.getStyle();
			 f.setColor(Color.BLACK);
			 style.setFont(f);
			 cell.setStyle(style);
			 
			 cell = cells.getCell("H" + Integer.toString(i)); cell.setValue(tongdsboga/tongds * 100);
			 CreateBorderSetting(workbook,"H" + Integer.toString(i));						 
			 style = cell.getStyle();
			 f.setColor(Color.BLACK);
			 style.setFont(f);
			 cell.setStyle(style);
			 
			 cell = cells.getCell("J" + Integer.toString(i)); cell.setValue(tongdskhac/tongds * 100);
			 CreateBorderSetting(workbook,"J" + Integer.toString(i));		
			 style = cell.getStyle();
			 f.setColor(Color.BLACK);
			 style.setFont(f);
			 cell.setStyle(style);

  			
			 CreateBorderSetting(workbook,"C" + Integer.toString(i));
			 
			 
  			 cell = cells.getCell("D" + Integer.toString(i)); cell.setValue("Tổng");
			 CreateBorderSetting(workbook,"D" + Integer.toString(i));
			 style = cell.getStyle();
			 f.setBold(true);
			 f.setColor(Color.BLACK);				
			 style.setFont(f);
			 cell.setStyle(style);
			 
			 cell = cells.getCell("K" + Integer.toString(i)); cell.setValue(tongds);
			 CreateBorderSetting(workbook,"K" + Integer.toString(i));
			 style = cell.getStyle();
			 f.setColor(Color.BLACK);
			 style.setFont(f);
			 cell.setStyle(style);
			 
			 cell = cells.getCell("E" + Integer.toString(i)); cell.setValue(tongdsxanh);
			 CreateBorderSetting(workbook,"E" + Integer.toString(i));
			 style = cell.getStyle();
			 f.setColor(Color.BLACK);
			 style.setFont(f);
			 cell.setStyle(style);
			 
			 cell = cells.getCell("G" + Integer.toString(i)); cell.setValue(tongdsboga);
			 CreateBorderSetting(workbook,"G" + Integer.toString(i));
			 style = cell.getStyle();
			 f.setColor(Color.BLACK);
			 style.setFont(f);
			 cell.setStyle(style);
			 
			 cell = cells.getCell("I" + Integer.toString(i)); cell.setValue(tongdskhac);
			 CreateBorderSetting(workbook,"I" + Integer.toString(i));
			 style = cell.getStyle();
			 f.setColor(Color.BLACK);
			 style.setFont(f);
			 cell.setStyle(style);
			 
			 
			 cell = cells.getCell("L" + Integer.toString(i)); cell.setValue(total_ThuVe);
			 CreateBorderSetting(workbook,"L" + Integer.toString(i));
			 style = cell.getStyle();
			 f.setColor(Color.BLACK);
			 style.setFont(f);
			 cell.setStyle(style);
			
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("Lỗi dữ liệu");
		}
  	}
		return true;
	  
  }

	private void CreateStaticHeader(Workbook workbook, IBcTyTrongDsQuy obj)
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
	    
//	    String tieude = "BÁO CÁO TỶ TRỌNG DOANH SỐ QUÝ";
//	    ReportAPI.getCellStyle(cell,Color.RED, true, 14, tieude);
	    
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
	    		

		
		
  }
	
	public void CreateBorderSetting(Workbook workbook,String fileName) throws IOException
  {
 Worksheets worksheets = workbook.getWorksheets();
    Worksheet worksheet = worksheets.getSheet(0);
      Cells cells = worksheet.getCells();
      Cell cell;
      Style style;

      cell = cells.getCell(fileName);
      style = cell.getStyle();

      //Set border color
      style.setBorderColor(BorderType.TOP, Color.BLACK);
      style.setBorderColor(BorderType.BOTTOM, Color.BLACK);
      style.setBorderColor(BorderType.LEFT, Color.BLACK);
      style.setBorderColor(BorderType.RIGHT, Color.BLACK);
      //Set the cell border type
      style.setBorderLine(BorderType.TOP, BorderLineType.THIN);
      style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
      style.setBorderLine(BorderType.LEFT, BorderLineType.THIN);
      style.setBorderLine(BorderType.RIGHT, BorderLineType.THIN);
      cell.setStyle(style);
  }
	
	
}
