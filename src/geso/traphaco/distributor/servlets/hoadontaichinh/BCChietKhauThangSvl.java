package geso.traphaco.distributor.servlets.hoadontaichinh;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.distributor.beans.hoadontaichinh.IBCChietKhauThang;
import geso.traphaco.distributor.beans.hoadontaichinh.imp.BCChietKhauThang;
import geso.traphaco.center.util.Utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aspose.cells.BorderType;
import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Font;
import com.aspose.cells.HorizontalAlignmentType;
import com.aspose.cells.Style;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

public class BCChietKhauThangSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public BCChietKhauThangSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
			IBCChietKhauThang obj;
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
	    
	    obj = new BCChietKhauThang();
	    obj.setUserId(userId);
	    obj.setLoaidonhang(loaihoadon);
	    obj.createRs();
	    
	    session.setAttribute("obj", obj);
		  String view = request.getParameter("view");
	    if(view == null)
	    	view = "";
	    String loaick = request.getParameter("loaick");
	    if(loaick == null)
	    	loaick = "";
	    String nppId ="";
	    
		  
	    
			if(view.equals("TT"))
			{
				 nppId = util.antiSQLInspection(request.getParameter("nppId"));
				if (nppId == null)
						nppId = "";
					obj.setNppId(nppId);
			}else
			{
				nppId=util.getIdNhapp(userId);
				obj.setNppId(nppId);
			}
			
			  if(loaick.equals("1"))
				{
					String	nextJSP = "/TraphacoHYERP/pages/Distributor/BaoCaoChietKhauThangck.jsp";
					response.sendRedirect(nextJSP);
					return;
				}
			  
			if(!view.equals("TT"))
			{
				String	nextJSP = "/TraphacoHYERP/pages/Distributor/BaoCaoChietKhauThang.jsp";
				response.sendRedirect(nextJSP);
			}
			else
			{
				String	nextJSP = "/TraphacoHYERP/pages/Center/BaoCaoChietKhauThang.jsp";
				response.sendRedirect(nextJSP);
			}
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
	    
	    IBCChietKhauThang obj = new BCChietKhauThang();
	    obj.setUserId(userId);
	    
	    String nppId = request.getParameter("nppId");
	    if(nppId == null)
	    	nppId = ""; 
	    obj.setNppId(nppId);
	    System.out.println("NPP_IDDDDDDDDDDDDDDDDDDDDDDD"+nppId);
	    
	    String tungay = request.getParameter("tungay");
	    if(tungay == null)
	    	tungay = ""; 
	    System.out.println("tu ngay ne ____"+tungay);
	    obj.setTungay(tungay);
	    
	    String denngay = request.getParameter("denngay");
	    if(denngay == null)
	    	denngay = "";
	    obj.setDenngay(denngay);
	    
	    String tdvId = request.getParameter("tdvId")==null?"": request.getParameter("tdvId");
	    obj.setTdvId(tdvId);
	    
	    String lck = request.getParameter("type")==null?"": request.getParameter("type");
	    obj.setType(lck);
	  	String view = request.getParameter("type");
	    if(view == null)
	    	view = "";
	    String  view1 = request.getParameter("view");
	    if(view1 == null)
	    	view1 = "";
		if(action.equals("excel11"))
		{
			System.out.println("__________________________"+obj.getDenngay()+"-"+(Integer.parseInt(obj.getTungay()) < 10 ? "0" + obj.getTungay() : obj.getTungay()+"") +"-01");
			
			String sql=obj.GetTichLuyThangCu(nppId,obj.getDenngay()+"-"+(Integer.parseInt(obj.getTungay()) < 10 ? "0" + obj.getTungay() : obj.getTungay()+"") +"-01");
			response.setContentType("application/xlsm");
			response.setHeader("Content-Disposition", "attachment; filename=BaoCaoChietKhauThang.xlsm");
			System.out.println("_____++++++++++"+obj.getTungay());
			FileInputStream fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\BCChietKhauThangck.xls");
			Workbook workbook = new Workbook();
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
			CreateStaticHeader(workbook, obj);
		//	obj.init();
			CreateStaticDatack2015(workbook, obj,sql);
			workbook.save(out);
			fstream.close();
			return;
		}
			if(view1.equals("TT"))
			{
				// nppId = util.antiSQLInspection(request.getParameter("nppId"));
				if (nppId == null)
					nppId = "";
				obj.setNppId(nppId);
			}else
			{
				nppId=util.getIdNhapp(userId);
				obj.setNppId(nppId);
			}

			obj.setUserId(userId);
			obj.setType(util.antiSQLInspection(request.getParameter("type")) != null ? util.antiSQLInspection(request.getParameter("type")) : "");	
			

	    if (action.equals("excel") && ( tungay.trim().length() > 0 && denngay.trim().length() > 0 ) )
	    {
	    	
	      if(denngay.equals("2015"))    
		    {
	      	System.out.println("vao 2015");
	      	try
			    {
	      		
							response.setContentType("application/xlsm");
							response.setHeader("Content-Disposition", "attachment; filename=BaoCaoChietKhauThang.xlsm");
					 
							FileInputStream fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\BCChietKhauThang_2015.xls");
							Workbook workbook = new Workbook();
							workbook.open(fstream);
							workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
							CreateStaticHeaderck(workbook, obj);
							obj.init();
							CreateStaticData2015(workbook, obj);
							workbook.save(out);
							fstream.close();
					    }
					    catch (Exception ex)
					    {
					    	ex.printStackTrace();
					        obj.setMsg("Không thể tao báo cáo.");
					    }
		    }
	      else
	      {
	      	System.out.println("vao else");
	    	try
		    {
						response.setContentType("application/xlsm");
						response.setHeader("Content-Disposition", "attachment; filename=BaoCaoChietKhauThang.xlsm");
				 
						FileInputStream fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\BCChietKhauThang.xls");
						Workbook workbook = new Workbook();
						workbook.open(fstream);
						workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
						CreateStaticHeader(workbook, obj);
						obj.init();
						CreateStaticData(workbook, obj);
						workbook.save(out);
						fstream.close();
				    }
				    catch (Exception ex)
				    {
				    	ex.printStackTrace();
				        obj.setMsg("Không thể tao báo cáo.");
				    }
	      }	
	    	session.setAttribute("obj", obj);
    		session.setAttribute("userId", userId);
    		
    		if(!view.equals("TT"))
  			{
  				String	nextJSP = "/TraphacoHYERP/pages/Distributor/BaoCaoChietKhauThang.jsp";
  				response.sendRedirect(nextJSP);
  			}
  			else
  			{
  				String	nextJSP = "/TraphacoHYERP/pages/Center/BaoCaoChietKhauThang.jsp";
  				response.sendRedirect(nextJSP);
  			}
	    }
	    else
	    {
	    	session.setAttribute("userId", userId);
	    	session.setAttribute("obj", obj);
	    	obj.setMsg("Bạn phải chọn khoảng thời gian lấy báo cáo");
	    	if(!view.equals("TT"))
				{
					String	nextJSP = "/TraphacoHYERP/pages/Distributor/BaoCaoChietKhauThang.jsp";
					response.sendRedirect(nextJSP);
				}
				else
				{
					String	nextJSP = "/TraphacoHYERP/pages/Center/BaoCaoChietKhauThang.jsp";
					response.sendRedirect(nextJSP);
				}
	    }
	}
	
	private void CreateStaticHeaderck(Workbook workbook,IBCChietKhauThang obj)
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
	    
	    if(!obj.getTungay().equals("") || !obj.getDenngay().equals(""))
	    {
		    font2 = new Font();
		    cell = cells.getCell("A5");
		    
		    cell.setValue("Tháng: " + obj.getTungay() + " - Năm: "  + obj.getDenngay());
	    		    
	    }
	    
	
	    worksheet.setName("BCChietKhauThang");
	}
	private void CreateStaticHeader(Workbook workbook, IBCChietKhauThang obj) 
	{

		// TODO Auto-generated method stub
		
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
	    
	    if(!obj.getTungay().equals("") || !obj.getDenngay().equals(""))
	    {
		    font2 = new Font();
		    cell = cells.getCell("A5");
		    
		    cell.setValue("Tháng: " + obj.getTungay() + " - Năm: "  + obj.getDenngay());
	    		    
	    }
	    
	
	    worksheet.setName("BCChietKhauThang");
	
		
	}

	private void CreateStaticData(Workbook workbook, IBCChietKhauThang obj ) 
	{
		
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	    Cells cells = worksheet.getCells();

		ResultSet hdRs = obj.getHoadonRs();
		
		Cell cell = null;
		
		Style style;
		Font font2 = new Font();	
		font2.setName("Times New Roman");
		font2.setSize(12);
		
		Font font3 = new Font();
		font3.setName("Times New Roman");
		font3.setSize(12);
		font3.setBold(true);
		
		
		int i = 9;	
		
		if(hdRs != null)
		{
			try 
			{
				int stt = 1;
				double totalBB = 0;
				double totalBL = 0;
				double totalDT=0;
				
				double banBUON = 0;
				double banLE = 0;
				
				
				double total_CK_THG=0;
				double total_CK_QUY=0;
				double total_CK_NGAY=0;
				
				while(hdRs.next())
				{					
					banBUON =Math.round( hdRs.getDouble("banBUON"));
					banLE =Math.round( hdRs.getDouble("banLE"));
					
					totalBB += banBUON;
					totalBL += banLE;
					totalDT +=Math.round(hdRs.getDouble("tongtiensauVAT"));
					
					double	ck_NGAY =Math.round( hdRs.getDouble("CK_Ngay"));
					double	ck_THANG =Math.round( hdRs.getDouble("ck_THANG"));
					double	ck_QUY = Math.round(hdRs.getDouble("ck_QUY"));
					
					
					total_CK_THG+=ck_THANG;
					total_CK_QUY+=ck_QUY;
					total_CK_NGAY+=ck_NGAY;
					
					
					cell = cells.getCell("A" + Integer.toString(i));	cell.setValue(stt); 	    style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);			 				
					cell = cells.getCell("B" + Integer.toString(i));	cell.setValue(hdRs.getString("tdvTEN")); 	style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					cell = cells.getCell("C" + Integer.toString(i));	cell.setValue(hdRs.getString("MAFAST")); 	style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);			
					cell = cells.getCell("D" + Integer.toString(i));	cell.setValue(hdRs.getString("TEN")); 	style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);	
					cell = cells.getCell("E" + Integer.toString(i));	cell.setValue(hdRs.getString("DIACHI")); 	style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					cell = cells.getCell("F" + Integer.toString(i));	cell.setValue( Math.round( hdRs.getDouble("tongtiensauVAT") )); 	style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);	
					cell = cells.getCell("G" + Integer.toString(i));	cell.setValue(banBUON); 	style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);	
					cell = cells.getCell("H" + Integer.toString(i));	cell.setValue( banLE);  style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);	 					
					cell = cells.getCell("I" + Integer.toString(i));	cell.setValue(ck_NGAY);  style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
					cell = cells.getCell("J" + Integer.toString(i));	cell.setValue(ck_THANG);  style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
					cell = cells.getCell("K" + Integer.toString(i));	cell.setValue(ck_QUY);  style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
					
					i++;
					stt ++;
				}
				hdRs.close();
				
				
				//THEM DONG TONG CONG
					
				//cells.merge(i-1, 0, i-1, 7);
				cell = cells.getCell("A" + Integer.toString(i));	cell.setValue("Tổng");  style = cell.getStyle(); style.setFont(font3); cell.setStyle(style); setCellBorderStyle1(cell, HorizontalAlignmentType.CENTRED);
				cell = cells.getCell("B" + Integer.toString(i));	cell.setValue("");      style = cell.getStyle(); style.setFont(font3); cell.setStyle(style); setCellBorderStyle1(cell, HorizontalAlignmentType.RIGHT);
				cell = cells.getCell("C" + Integer.toString(i));	cell.setValue("");      style = cell.getStyle(); style.setFont(font3); cell.setStyle(style); setCellBorderStyle1(cell, HorizontalAlignmentType.RIGHT); 
				cell = cells.getCell("D" + Integer.toString(i));	cell.setValue("");		style = cell.getStyle(); style.setFont(font3); cell.setStyle(style); setCellBorderStyle1(cell, HorizontalAlignmentType.RIGHT); 
				cell = cells.getCell("E" + Integer.toString(i));	cell.setValue("");		style = cell.getStyle(); style.setFont(font3); cell.setStyle(style); setCellBorderStyle1(cell, HorizontalAlignmentType.RIGHT);
				cell = cells.getCell("F" + Integer.toString(i));	cell.setValue(totalDT);		style = cell.getStyle(); style.setFont(font3); cell.setStyle(style); setCellBorderStyle1(cell, HorizontalAlignmentType.RIGHT); 
				cell = cells.getCell("G" + Integer.toString(i));	cell.setValue(totalBB);		style = cell.getStyle(); style.setFont(font3); cell.setStyle(style); setCellBorderStyle1(cell, HorizontalAlignmentType.RIGHT); 
				cell = cells.getCell("H" + Integer.toString(i));	cell.setValue(totalBL);		style = cell.getStyle(); style.setFont(font3); cell.setStyle(style); setCellBorderStyle1(cell, HorizontalAlignmentType.RIGHT); 				
				cell = cells.getCell("I" + Integer.toString(i));	cell.setValue(total_CK_NGAY);		style = cell.getStyle(); style.setFont(font3); cell.setStyle(style); setCellBorderStyle1(cell, HorizontalAlignmentType.RIGHT);
				cell = cells.getCell("J" + Integer.toString(i));	cell.setValue(total_CK_THG);		style = cell.getStyle(); style.setFont(font3); cell.setStyle(style); setCellBorderStyle1(cell, HorizontalAlignmentType.RIGHT);
				cell = cells.getCell("K" + Integer.toString(i));	cell.setValue(total_CK_QUY);		style = cell.getStyle(); style.setFont(font3); cell.setStyle(style); setCellBorderStyle1(cell, HorizontalAlignmentType.RIGHT);
				i++;
				
			}
			catch (Exception e)
			{ 
				e.printStackTrace(); 
			}
		}
		

		
		i++;
		
		
	}
	
	private void CreateStaticData2015(Workbook workbook, IBCChietKhauThang obj ) 
	{
		
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	    Cells cells = worksheet.getCells();

		ResultSet hdRs = obj.getHoadonRs();
		
		Cell cell = null;
		
		Style style;
		Font font2 = new Font();	
		font2.setName("Times New Roman");
		font2.setSize(12);
		
		Font font3 = new Font();
		font3.setName("Times New Roman");
		font3.setSize(12);
		font3.setBold(true);
		
		
		int i = 9;	
		
		if(hdRs != null)
		{
			try 
			{
				int stt = 1;
					
				
				double totaldoanhthu = 0;
				double totalchietkhau = 0;
				double totalDoanhso=0;
				double totalBoga = 0;
				double totaltyle = 0;
		
				while(hdRs.next())
				{					
							
					totaldoanhthu += hdRs.getDouble("doanhthu");
					 totalchietkhau += (hdRs.getDouble("ptchietkhau")/100) * hdRs.getDouble("doanhthu");
					 totalDoanhso +=hdRs.getDouble("tongds");
					 totalBoga += hdRs.getDouble("dsboga");
					 
					 totaltyle = totalBoga/totalDoanhso;
				//		System.out.println("chiet khua"+(hdRs.getString("ptChietKhau")) );

					//System.out.println((hdRs.getDouble("ptChietKhau") /100) * hdRs.getDouble("doanhthu"));
					cell = cells.getCell("A" + Integer.toString(i));	cell.setValue(stt); 	    style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);			 				
					cell = cells.getCell("B" + Integer.toString(i));	cell.setValue(hdRs.getString("tdvTEN")); 	style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					cell = cells.getCell("C" + Integer.toString(i));	cell.setValue(hdRs.getString("khma")); 	style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);			
					cell = cells.getCell("D" + Integer.toString(i));	cell.setValue(hdRs.getString("mahd")); 	style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);	
					cell = cells.getCell("E" + Integer.toString(i));	cell.setValue(hdRs.getString("khten")); 	style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					cell = cells.getCell("F" + Integer.toString(i));	cell.setValue(  hdRs.getString("khdiachi") ); 	style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);	
					cell = cells.getCell("G" + Integer.toString(i));	cell.setValue(hdRs.getDouble("doanhthu")); 	style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);	
					cell = cells.getCell("H" + Integer.toString(i));	cell.setValue((hdRs.getDouble("ptChietKhau") /100) * hdRs.getDouble("doanhthu"));  style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);	 					
					cell = cells.getCell("I" + Integer.toString(i));	cell.setValue(hdRs.getDouble("tongds"));  style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
					cell = cells.getCell("J" + Integer.toString(i));	cell.setValue(hdRs.getDouble("dsboga"));  style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
					double pt=0;
					if(hdRs.getDouble("tongds")!=0)
						 pt=hdRs.getDouble("dsboga")/hdRs.getDouble("tongds");
					cell = cells.getCell("K" + Integer.toString(i));	cell.setValue(pt);  style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
					
					i++;
					stt ++;
				}
				hdRs.close();
				
				
				//THEM DONG TONG CONG

		
				//cells.merge(i-1, 0, i-1, 7);
				cell = cells.getCell("A" + Integer.toString(i));	cell.setValue("Tổng");  style = cell.getStyle(); style.setFont(font3); cell.setStyle(style); setCellBorderStyle1(cell, HorizontalAlignmentType.CENTRED);
				cell = cells.getCell("B" + Integer.toString(i));	cell.setValue("");      style = cell.getStyle(); style.setFont(font3); cell.setStyle(style); setCellBorderStyle1(cell, HorizontalAlignmentType.RIGHT);
				cell = cells.getCell("C" + Integer.toString(i));	cell.setValue("");      style = cell.getStyle(); style.setFont(font3); cell.setStyle(style); setCellBorderStyle1(cell, HorizontalAlignmentType.RIGHT); 
				cell = cells.getCell("D" + Integer.toString(i));	cell.setValue("");		style = cell.getStyle(); style.setFont(font3); cell.setStyle(style); setCellBorderStyle1(cell, HorizontalAlignmentType.RIGHT); 
				cell = cells.getCell("E" + Integer.toString(i));	cell.setValue("");		style = cell.getStyle(); style.setFont(font3); cell.setStyle(style); setCellBorderStyle1(cell, HorizontalAlignmentType.RIGHT);
				cell = cells.getCell("F" + Integer.toString(i));	cell.setValue("");		style = cell.getStyle(); style.setFont(font3); cell.setStyle(style); setCellBorderStyle1(cell, HorizontalAlignmentType.RIGHT); 
				cell = cells.getCell("G" + Integer.toString(i));	cell.setValue(totaldoanhthu);		style = cell.getStyle(); style.setFont(font3); cell.setStyle(style); setCellBorderStyle1(cell, HorizontalAlignmentType.RIGHT); 
				cell = cells.getCell("H" + Integer.toString(i));	cell.setValue(totalchietkhau);		style = cell.getStyle(); style.setFont(font3); cell.setStyle(style); setCellBorderStyle1(cell, HorizontalAlignmentType.RIGHT); 				
				cell = cells.getCell("I" + Integer.toString(i));	cell.setValue(totalDoanhso);		style = cell.getStyle(); style.setFont(font3); cell.setStyle(style); setCellBorderStyle1(cell, HorizontalAlignmentType.RIGHT);
				cell = cells.getCell("J" + Integer.toString(i));	cell.setValue(totalBoga);		style = cell.getStyle(); style.setFont(font3); cell.setStyle(style); setCellBorderStyle1(cell, HorizontalAlignmentType.RIGHT);
				cell = cells.getCell("K" + Integer.toString(i));	cell.setValue(totaltyle);		style = cell.getStyle(); style.setFont(font3); cell.setStyle(style); setCellBorderStyle1(cell, HorizontalAlignmentType.RIGHT);
				i++;
				
			}
			catch (Exception e)
			{ 
				e.printStackTrace(); 
			}
		}
		

		
		i++;
		
		
	}
	private void CreateStaticDatack2015(Workbook workbook, IBCChietKhauThang obj , String sql) 
	{
		
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	    Cells cells = worksheet.getCells();

	
		geso.traphaco.distributor.db.sql.dbutils db=new geso.traphaco.distributor.db.sql.dbutils();
		ResultSet rs=db.get(sql);
		System.out.println("---------------"+sql);
		Cell cell = null;
		
		Style style;
		Font font2 = new Font();	
		font2.setName("Times New Roman");
		font2.setSize(12);
		
		Font font3 = new Font();
		font3.setName("Times New Roman");
		font3.setSize(12);
		font3.setBold(true);
		
		
		int i = 9;	
		try {
			int stt = 1;
			
			
			double totaldoanhthu = 0;
			double totalchietkhau = 0;
			double totalDoanhso=0;
			double totalBoga = 0;
			double totaltyle = 0;
			while(rs.next())
			{
				double conlai=obj.condulai(rs.getInt("sodong"), rs.getInt("daduyet"), rs.getDouble("tongDSThangtruoc"), rs.getDouble("tongTHUVEThangtruoc"), rs.getString("loaiNPP"), rs.getString("xuatkhau"), rs.getString("thanhtoan"), rs.getString("PT_CHIETKHAU"), rs.getString("duno_dauky"),rs.getDouble("DsHHBG"),obj.getDenngay()+"-"+(Integer.parseInt(obj.getTungay()) < 10 ? "0" + obj.getTungay() : obj.getTungay()+"") +"-01",rs.getString("pk_seq"));
	//		System.out.println("loai "+obj.getType()+"------"+rs.getInt("sodong")+"---" + rs.getInt("daduyet") +"---"+ rs.getDouble("tongDSThangtruoc") +"---"+ rs.getDouble("tongTHUVEThangtruoc") +"---"+ rs.getString("loaiNPP") +"---"+ rs.getString("xuatkhau") +"---"+ rs.getString("thanhtoan") +"---"+ rs.getString("PT_CHIETKHAU") +"---"+ rs.getString("duno_dauky") +"---"+rs.getDouble("DsHHBG") +"---"+ obj.getDenngay()+"-"+obj.getTungay()+"-01" +"---"+rs.getString("pk_seq") +"---"+conlai);
					if(obj.getType().equals("1"))
					{
						if(conlai > 500 )
						{
							cell = cells.getCell("A" + Integer.toString(i));	cell.setValue(stt); 	    style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);			 				
							cell = cells.getCell("B" + Integer.toString(i));	cell.setValue(rs.getString("ten")); 	style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
							cell = cells.getCell("C" + Integer.toString(i));	cell.setValue(rs.getString("maFast")); 	style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);			
							cell = cells.getCell("D" + Integer.toString(i));	cell.setValue(rs.getDouble("tongDSThangtruoc")); 	style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);	
							cell = cells.getCell("E" + Integer.toString(i));	cell.setValue(rs.getDouble("tongTHUVEThangtruoc")); 	style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
							cell = cells.getCell("F" + Integer.toString(i));	cell.setValue(  rs.getDouble("DsHHBG") ); 	style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);	
							cell = cells.getCell("G" + Integer.toString(i));	cell.setValue(conlai); 	style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);	
							stt++;
							i++;
						}
					}
					if(obj.getType().equals("0"))
					{
						if(conlai < 500 )
						{
						cell = cells.getCell("A" + Integer.toString(i));	cell.setValue(stt); 	    style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);			 				
						cell = cells.getCell("B" + Integer.toString(i));	cell.setValue(rs.getString("ten")); 	style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
						cell = cells.getCell("C" + Integer.toString(i));	cell.setValue(rs.getString("maFast")); 	style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);			
						cell = cells.getCell("D" + Integer.toString(i));	cell.setValue(rs.getDouble("tongDSThangtruoc")); 	style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);	
						cell = cells.getCell("E" + Integer.toString(i));	cell.setValue(rs.getDouble("tongTHUVEThangtruoc")); 	style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
						cell = cells.getCell("F" + Integer.toString(i));	cell.setValue(  rs.getDouble("DsHHBG") ); 	style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);	
						cell = cells.getCell("G" + Integer.toString(i));	cell.setValue(0); 	style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);	
						stt++;
						i++;
						}
					}
			}

			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
	}
	
	
	
	private void setCellBorderStyle(Cell cell, short alignment) 
	{
		Style style = cell.getStyle();
		//style.setHAlignment(HorizontalAlignmentType.CENTRED);
		style.setHAlignment(alignment);
		style.setBorderLine(BorderType.TOP, 0);
		style.setBorderLine(BorderType.RIGHT, 1);
		style.setBorderLine(BorderType.BOTTOM, 0);
		style.setBorderLine(BorderType.LEFT, 1);
		cell.setStyle(style);
	}
	

	
	private void setCellBorderStyle1(Cell cell, short alignment) 
	{
		Style style = cell.getStyle();
		style.setHAlignment(alignment);
		style.setBorderLine(BorderType.TOP, 1);
		style.setBorderLine(BorderType.RIGHT, 1);
		style.setBorderLine(BorderType.BOTTOM, 1);
		style.setBorderLine(BorderType.LEFT, 1);
		cell.setStyle(style);
	}
	

	

}

