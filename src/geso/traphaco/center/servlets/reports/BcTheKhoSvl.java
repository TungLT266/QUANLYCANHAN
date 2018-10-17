package geso.traphaco.center.servlets.reports;

import geso.traphaco.center.beans.report.IBcTheKho;
import geso.traphaco.center.beans.report.imp.BcTheKho;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.servlets.report.ReportAPI;
import geso.traphaco.distributor.util.Utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;

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

@WebServlet("/BcTheKhoSvl")
public class BcTheKhoSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	public BcTheKhoSvl()
	{
		super();
		
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		IBcTheKho obj;
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		HttpSession session = request.getSession();
		
		Utility util = new Utility();
		String ctyId = (String)session.getAttribute("congtyId");
		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);
		
		if (userId.length() == 0)
			userId = util.antiSQLInspection(request.getParameter("userId"));
		
		String loaihoadon = request.getParameter("loaihoadon");
		if (loaihoadon == null)
			loaihoadon = "0";
		
		obj = new BcTheKho();
		obj.setUserId(userId);
		
		
		 String view = request.getParameter("view");
	    if(view == null)
	    	view = "";
	    obj.setView(view);
	    
	 if(view.length()<=0)
	 {
	   String  nppId=util.getIdNhapp(userId);
	   obj.setNppId(nppId);
	 }
	 dbutils db = new dbutils();
	 String query = "";
	    query = "select pk_seq from NHAPHANPHOI where congty_fk = "+ctyId+" and TRANGTHAI = 1 ";
		System.out.println("[congty]" + query);
		ResultSet rs = db.get(query);
		try
		{
			if(rs.next())
			{
				obj.setNppId(rs.getString("pk_seq"));
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		String nextJSP = "";
		obj.init("");
		
		session.setAttribute("congtyId", ctyId);
		nextJSP = "/TraphacoHYERP/pages/Center/BcTheKho.jsp";
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
	    String ctyId = (String)session.getAttribute("congtyId");
	    String action = request.getParameter("action");
	    if (action == null)
    	action = "";
    
	    IBcTheKho obj = new BcTheKho();
	    obj.setUserId(userId);
	    
	    String view = request.getParameter("view");
	    if(view == null)
	    	view = "";
	    obj.setView(view);
    
		 if(view.length()<=0)
		 {
		   String  nppId=util.getIdNhapp(userId);
		   obj.setNppId(nppId);
		 }
    
	    String tungay =request.getParameter("Sdays")==null?"": request.getParameter("Sdays");
	    obj.setTuNgay(tungay);
	    
	    String solo =request.getParameter("solo")==null?"": request.getParameter("solo");
	    obj.setSolo(solo.trim());
	    
	    
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
	    
	    
	    String loaihoadon =request.getParameter("loaidonhang")==null?"": request.getParameter("loaidonhang");
	    obj.setLoaiHoaDon(loaihoadon);
	    
	    String doituongid=request.getParameter("doituongid");
	    if(doituongid==null){
	    	doituongid="";
	    }
	    obj.setDoituongId(doituongid);
	    
	    
	    String khoId =request.getParameter("khoId")==null?"": request.getParameter("khoId");
	    obj.setKhoId(khoId);
		
	    obj.setAction(action);
	    
	    obj.setType(util.antiSQLInspection(request.getParameter("type")) == null ? "" : util.antiSQLInspection(request.getParameter("type")));
	    
	    System.out.println("___ATION "+action);
	    
		dbutils db = new dbutils();
		String query = "";
	    query = "select pk_seq from NHAPHANPHOI where congty_fk = "+ctyId+" and TRANGTHAI = 1 ";
		System.out.println("[congty]" + query);
		ResultSet rs = db.get(query);
		try
		{
			if(rs.next())
			{
				obj.setNppId(rs.getString("pk_seq"));
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
	    if (action.equals("excel")  )
	    {
	    	try
		    { 
				response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "attachment; filename=BcTheKho.xlsm");
				FileInputStream  fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\BcTheKho_ChiTiet.xlsm");
				
				Workbook workbook = new Workbook();
				workbook.open(fstream);
				workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
			 
				obj.setUserId(userId);
				obj.init("");
				 
					CreateStaticHeader(workbook, obj);
					FillData_Details(workbook,obj);
			 
				workbook.save(out);
				fstream.close();
	    }
	    catch (Exception ex)
	    {
	    	 
	    	obj.setUserId(userId);
	    	session.setAttribute("obj", obj);
	  		session.setAttribute("userId", userId);
	  		session.setAttribute("congtyId", ctyId);
	  		obj.init("");
	  		String nextJSP = "";
	  		nextJSP = "/TraphacoHYERP/pages/Center/BcTheKho.jsp";
	  		response.sendRedirect(nextJSP); 
	    }
    
  	 
 
    }
    
    else if(action.equals("search"))
    {	
    	obj.setUserId(userId);
    	session.setAttribute("obj", obj);
  		session.setAttribute("userId", userId);
  		session.setAttribute("congtyId", ctyId);
  		obj.init("");
  		String nextJSP = "";
  		nextJSP = "/TraphacoHYERP/pages/Center/BcTheKho.jsp";
  		response.sendRedirect(nextJSP); 
    }
    else
    {
			session.setAttribute("obj", obj);
			session.setAttribute("congtyId", ctyId);
			String nextJSP = "";
			nextJSP = "/TraphacoHYERP/pages/Center/BcTheKho.jsp";
			obj.init("");
			response.sendRedirect(nextJSP);  
    }
		
	}
	
	 
	private boolean FillData_Details(Workbook workbook, IBcTheKho obj) throws Exception
	{
		dbutils db = new dbutils();
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		Cell cell = null;
		
		Cells cells = worksheet.getCells();		
		ResultSet hdRs =  obj.getHoadonRs();
		
		Font font = new Font();
		font.setName("Times New Roman");
		font.setColor(Color.BLACK);
		font.setBold(true);
		font.setSize(11);
		
		
		cell = cells.getCell("G12");	cell.setValue(obj.getDauKy());
		
		 
			try 
			{
				
				
				int i = 13;
			 
				
				double Total_Nhap=0;
				double Total_Xuat=0;
				
				double toncuoi=obj.getDauKy();
				
				while (  hdRs.next()) 
				{	
					boolean tam_=true;
					
					if(obj.getSolo().length()>0){
						if(!hdRs.getString("Số Lô").equals(obj.getSolo())){
							tam_=false;
						}
					}
					
					if(tam_){
						
						
					toncuoi+=hdRs.getDouble("SOLUONGNHAP")-hdRs.getDouble("SOLUONGXUAT") ;
					Total_Nhap=Total_Nhap+hdRs.getDouble("SOLUONGNHAP");
					Total_Xuat=Total_Xuat+hdRs.getDouble("SOLUONGXUAT");
					
					cell = cells.getCell("A" + Integer.toString(i));	cell.setValue( hdRs.getString("Ngày chứng từ").length()>=10?getFormatDate(hdRs.getString("Ngày chứng từ")):"" );
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					
					cell = cells.getCell("B" + Integer.toString(i));	cell.setValue(hdRs.getString("Số chứng từ"));
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					
					String khTEN="";
					 
						khTEN= hdRs.getString("Tên đối tượng")+"("+hdRs.getString("Mã đối tượng") + ")"; 
					 
					
					cell = cells.getCell("C" + Integer.toString(i));	cell.setValue(khTEN );
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					
				 
					cell = cells.getCell("D" + Integer.toString(i));	cell.setValue(hdRs.getString("Diễn giải")); 
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 41);
					 
					
					cell = cells.getCell("E" + Integer.toString(i));	cell.setValue(hdRs.getString("Số Lô"));
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					
					cell = cells.getCell("F" + Integer.toString(i));	cell.setValue(hdRs.getString("Hạn S.dụng"));
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					 
					cell = cells.getCell("G" + Integer.toString(i));	cell.setValue(0);
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					
					cell = cells.getCell("H" + Integer.toString(i));	cell.setValue(hdRs.getDouble("SOLUONGNHAP"));
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					
					cell = cells.getCell("I" + Integer.toString(i));	cell.setValue(hdRs.getDouble("SOLUONGXUAT"));
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					
					cell = cells.getCell("J" + Integer.toString(i));	cell.setValue(0);
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					
					cell = cells.getCell("K" + Integer.toString(i));	cell.setValue("");
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					
					
					
					i++;
					}
				}
			 hdRs.close();
					
				
				i++;
				cell = cells.getCell("A" + Integer.toString(i));	cell.setValue("");
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
				
				cell = cells.getCell("B" + Integer.toString(i));	cell.setValue("");
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
				
				cell = cells.getCell("C" + Integer.toString(i));	cell.setValue("TỔNG CỘNG");
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
				
				cell = cells.getCell("D" + Integer.toString(i));	cell.setValue("");
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
				
				cell = cells.getCell("E" + Integer.toString(i));	cell.setValue("");
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
				
				cell = cells.getCell("F" + Integer.toString(i));	cell.setValue("");
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
				
				cell = cells.getCell("G" + Integer.toString(i));	cell.setValue("");
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
			
				cell = cells.getCell("H" + Integer.toString(i));	cell.setValue(Total_Nhap);
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 41);
				
				cell = cells.getCell("I" + Integer.toString(i));	cell.setValue(Total_Xuat);
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 41);
				
				cell = cells.getCell("J" + Integer.toString(i));	cell.setValue(toncuoi);
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 41);
				
				cell = cells.getCell("K" + Integer.toString(i));	cell.setValue("");ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);				
  
				if(db != null) 
					db.shutDown();
			 
				  
			}catch(Exception ex){
				ex.printStackTrace();
				throw new Exception(ex.getMessage());
			}
		 	
		return true;
	  
  }
	
	private void CreateStaticHeader(Workbook workbook, IBcTheKho obj)
  {
			Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet(0);
			worksheet.setName("Sheet1");
			Cells cells = worksheet.getCells();
		
	    Style style;
	    Font font = new Font();
	    font.setName("Times New Roman");
	    font.setColor(Color.RED);//mau chu
	    font.setSize(16);// size chu
	   	font.setBold(true);
	   	
	    cells.setRowHeight(0, 20.0f);
	    Cell cell = cells.getCell("A1");
	    style = cell.getStyle();
	    style.setFont(font); 
	    style.setHAlignment(TextAlignmentType.LEFT);// canh le cho chu 	   
	    
	    
	    dbutils db = new dbutils();
	    
	    ResultSet rs=db.get("select makho,ten from nhaphanphoi where pk_Seq ='"+obj.getNppId()+"' ");
	    if(rs!=null)
	    {
	    	try
        {
	        while(rs.next())
	        {
	        	 cells.setRowHeight(3, 18.0f);
	     	    cell = cells.getCell("C5");
	     	    ReportAPI.getCellStyle(cell,Color.BLACK,true, 11, " "+rs.getString("makho")+" - " +rs.getString("ten")  );
	        }
	        if(rs!=null)rs.close();
        } catch (SQLException e)
        {
	        e.printStackTrace();
        }
	    }
	    
	     rs=db.get("select ma as spMa,ten as spTEN from sanpham where pk_Seq ='"+obj.getSpId()+"' ");
	    if(rs!=null)
	    {
	    	try
        {
	        while(rs.next())
	        {
	        	 cells.setRowHeight(3, 18.0f);
	     	    cell = cells.getCell("C6");
	     	    ReportAPI.getCellStyle(cell,Color.BLACK,true, 11, "VẬT TƯ "+rs.getString("spMa" )+ " "    +rs.getString("spTEN")  );
	        }
	        if(rs!=null)rs.close();
        } catch (Exception e)
        {
	        e.printStackTrace();
        }
	    	finally
	    	{
	    		db.shutDown();
	    	}
	    }
	    
	    cells.setRowHeight(3, 18.0f);
	    cell = cells.getCell("C7");
	    ReportAPI.getCellStyle(cell,Color.NAVY,true, 11, "Từ ngày : " + obj.getTuNgay() + " Đến ngày : " + obj.getDenNgay() + ""  );

  }
	
	public String getFormatDate(String date) 
	{
		String[] arr = date.split("-");
		if(arr.length==3)
		return arr[2] + "/" + arr[1] + "/" + arr[0];
		else
		return "";
	}
}
