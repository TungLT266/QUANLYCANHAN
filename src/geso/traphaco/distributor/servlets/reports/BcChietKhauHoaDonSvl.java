package geso.traphaco.distributor.servlets.reports;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.servlets.report.ReportAPI;
import geso.traphaco.distributor.beans.reports.IBcChietKhauHoaDon;
import geso.traphaco.distributor.beans.reports.imp.BcChietKhauHoaDon;
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

@WebServlet("/BcChietKhauHoaDonSvl")
public class BcChietKhauHoaDonSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	public BcChietKhauHoaDonSvl()
	{
		super();
		
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		IBcChietKhauHoaDon obj;
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
		
		obj = new BcChietKhauHoaDon();
		obj.setUserId(userId);
		obj.setView("");
		String nextJSP = "";
		obj.init("");
		
		
		nextJSP = "/TraphacoHYERP/pages/Distributor/BcChietKhauHoaDon.jsp";
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
    
    IBcChietKhauHoaDon obj = new BcChietKhauHoaDon();
    obj.setUserId(userId);
    
    obj.setView("");
    
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
    
    System.out.println("[BcChietKhauHoaDonSvl] "+action+"____"+nppId);
    
    
    if (action.equals("excel")  )
    {
    	try
	    { 
				response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "attachment; filename=BcChietKhauHoaDon.xlsm");
	      FileInputStream fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\BcChietKhauHoaDon.xlsm");
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
  		nextJSP = "/TraphacoHYERP/pages/Distributor/BcChietKhauHoaDon.jsp";
  		response.sendRedirect(nextJSP); 
    }
    else  if(action.equals("view") || action.equals("next") || action.equals("prev")){
    	
    	System.out.println("______::::::::::::::::_____"+action+"_________"+request.getParameter("nxtApprSplitting"));
    	obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
    	obj.setUserId(userId);
    	obj.init("");
    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
    	session.setAttribute("obj", obj);
    	
    	String 	nextJSP = "/TraphacoHYERP/pages/Distributor/BcChietKhauHoaDon.jsp";
    	response.sendRedirect(nextJSP);
    }
    else
    {
    	obj.setUserId(userId);
    	session.setAttribute("obj", obj);
  		session.setAttribute("userId", userId);
  		obj.init("");
  		String nextJSP = "";
  		nextJSP = "/TraphacoHYERP/pages/Distributor/BcChietKhauHoaDon.jsp";
  		response.sendRedirect(nextJSP); 
    }
		
	}
	
	private boolean FillData(Workbook workbook, IBcChietKhauHoaDon obj, String query) throws Exception
  {
		dbutils db = new dbutils();
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);

		Cells cells = worksheet.getCells();		
		ResultSet hdRs = db.get(query);
		
		double CQX5=0;
		double CQB5=0;
		double DoanhThu=0;
		
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
					
					cell = cells.getCell("C" + Integer.toString(i));	cell.setValue(hdRs.getString("MaFAST"));
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					
					cell = cells.getCell("D" + Integer.toString(i));	cell.setValue(hdRs.getString("MaHD"));
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					
					cell = cells.getCell("E" + Integer.toString(i));	cell.setValue(hdRs.getString("khTEN"));
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					
					cell = cells.getCell("F" + Integer.toString(i));	cell.setValue(hdRs.getString("khDiaChi"));
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					
					cell = cells.getCell("G" + Integer.toString(i));	cell.setValue(hdRs.getString("SoHoaDon"));
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					
					cell = cells.getCell("H" + Integer.toString(i));	cell.setValue(hdRs.getDouble("DoanhThu"));
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 41);
					
					cell = cells.getCell("I" + Integer.toString(i));	cell.setValue(hdRs.getDouble("CQB5"));
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 41);
					
					cell = cells.getCell("J" + Integer.toString(i));	cell.setValue(hdRs.getDouble("CQX5"));
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 41);
					
					cell = cells.getCell("K" + Integer.toString(i));	cell.setValue(hdRs.getDouble("CQX5")+hdRs.getDouble("CQB5"));
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 41);
					
					CQX5+=hdRs.getDouble("CQX5");
					CQB5+=hdRs.getDouble("CQB5");
					DoanhThu+=hdRs.getDouble("DoanhThu");
					
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
				
				cell = cells.getCell("H" + Integer.toString(i));	cell.setValue(DoanhThu);
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 41);
				
				cell = cells.getCell("I" + Integer.toString(i));	cell.setValue(CQB5 );
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 41);
				
				cell = cells.getCell("J" + Integer.toString(i));	cell.setValue(CQX5 );
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 41);
								
				cell = cells.getCell("K" + Integer.toString(i));	cell.setValue( CQB5+CQX5);			
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

	private void CreateStaticHeader(Workbook workbook, IBcChietKhauHoaDon obj)
  {
		Worksheets worksheets = workbook.getWorksheets();
    Worksheet worksheet = worksheets.getSheet(0);
   	   
    Cells cells = worksheet.getCells();
   
    Cell cell ;  
  
    Font font2 = new Font();
    font2.setBold(true);

    try
     {
        dbutils db = new dbutils();	  
        
    	String sql = " select ten, case when diachi = 'NA' then '' else  isnull(diachi, '') end as diachi, " +
    			     "        case when dienthoai = 'NA' then '' else  isnull(dienthoai, '') end as dienthoai ," +
    			     "        case when fax = 'NA' then '' else  isnull(fax, '') end as fax  " +
    			     " from NHAPHANPHOI  where pk_seq = '"+ obj.getNppId() +"' ";
    	
    	System.out.println("Nhà phân phối : "+sql);	    	
    	ResultSet rs = db.get(sql);
    	
    	String ten = "";
    	String diachi = "";
    	String dienthoai = "";
    	String fax = "";
    	
    	if(rs!= null)
    	{
	    	while(rs.next())
	    	{
	    		ten = rs.getString("ten");
	    		diachi = rs.getString("diachi");
	    		dienthoai = rs.getString("dienthoai");
	    		fax = rs.getString("fax");
	    	}
	    	rs.close();	
    	}
    	
	    cell = cells.getCell("C1");
	    cell.setValue(ten.toUpperCase());
	    
	    cell = cells.getCell("C2");
	    cell.setValue("Địa chỉ: "+diachi);
	    		    
	    cell = cells.getCell("C3");
	    cell.setValue("Tel: "+dienthoai +" - Fax: "+fax);
	    
	    
	    }catch(Exception ex){}    
    
    if(!obj.getTuNgay().equals("") || !obj.getDenNgay().equals(""))
    {
	    font2 = new Font();
	    cell = cells.getCell("A5");
	    
	    cell.setValue("Từ ngày: " + obj.getTuNgay() + " - Đến ngày: "  + obj.getDenNgay());
    		    
    }
    

    worksheet.setName("BCChietKhauQuy");
		
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
