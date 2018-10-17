package geso.traphaco.distributor.servlets.hoadontaichinh;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.distributor.beans.hoadontaichinh.IBCChietKhauQuy;
import geso.traphaco.distributor.beans.hoadontaichinh.imp.BCChietKhauQuy;
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
import java.util.Iterator;

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

public class BCChietKhauQuySvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public BCChietKhauQuySvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IBCChietKhauQuy obj;
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
	    
	    
	    String view = request.getParameter("view");
	    if(view == null)
	    	view = "";
	    
	    obj = new BCChietKhauQuy();
	    obj.setUserId(userId);
	    obj.setLoaidonhang(loaihoadon);
	    
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
			obj.createRs();
			session.setAttribute("obj", obj);		
		
			if(!view.equals("TT"))
			{
				String nextJSP = "/TraphacoHYERP/pages/Distributor/BaoCaoChietKhauQuy.jsp";
				response.sendRedirect(nextJSP);
			}
			else
			{
				String nextJSP = "/TraphacoHYERP/pages/Center/BaoCaoChietKhauQuy.jsp";
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
	    
	    IBCChietKhauQuy obj = new BCChietKhauQuy();
	    obj.setUserId(userId);
	    
	    String nppId = request.getParameter("nppId");
	    if(nppId == null)
	    	nppId = ""; 
	    obj.setNppId(nppId);
	    
	    String tungay = request.getParameter("tungay");
	    if(tungay == null)
	    	tungay = ""; 
	    obj.setTungay(tungay);
	    
	    String denngay = request.getParameter("denngay");
	    if(denngay == null)
	    	denngay = "";
	    obj.setDenngay(denngay);
	    
	    
	    String tdvId = request.getParameter("tdvId")==null?"": request.getParameter("tdvId");
	    obj.setTdvId(tdvId);
	    
	    obj.setType(util.antiSQLInspection(request.getParameter("type")) != null ? util.antiSQLInspection(request.getParameter("type")) : "");
	  	
			String view = request.getParameter("view");
	    if(view == null)
	    	view = "";
	    
			obj.setUserId(userId);
	    
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

	    if (action.equals("excel") && ( tungay.trim().length() > 0 && denngay.trim().length() > 0 ) )
	    {
	    	try
		    {
				response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "attachment; filename=BaoCaoChietKhauQuy.xlsm");
		        
				if(denngay.equals("2015"))
				{
					 FileInputStream fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\BCChietKhauQuy_2015.xls");
						Workbook workbook = new Workbook();
						workbook.open(fstream);
						workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
						
						CreateStaticHeader(workbook, obj);
						obj.init2015();
					    CreateStaticData2015(workbook, obj);
						
						workbook.save(out);
						fstream.close();
				}
				else
				{
		        FileInputStream fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\BCChietKhauQuy.xls");
				Workbook workbook = new Workbook();
				workbook.open(fstream);
				workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
				
				CreateStaticHeader(workbook, obj);
				obj.init();
			    CreateStaticData(workbook, obj);
				
				workbook.save(out);
				fstream.close();
				}
		    }
		    catch (Exception ex)
		    {
		    	ex.printStackTrace();
		        obj.setMsg("Không thể tao báo cáo.");
		    }
	    	

			
	    	session.setAttribute("obj", obj);
    		session.setAttribute("userId", userId);
    		
    		
			
    		if(!view.equals("TT"))
  			{
  				String nextJSP = "/TraphacoHYERP/pages/Distributor/BaoCaoChietKhauQuy.jsp";
  				response.sendRedirect(nextJSP);
  			}
  			else
  			{
  				String nextJSP = "/TraphacoHYERP/pages/Center/BaoCaoChietKhauQuy.jsp";
  				response.sendRedirect(nextJSP);
  			}
    		
    		
	    }
	    else
	    {
	    	session.setAttribute("obj", obj);
	    	obj.setMsg("Bạn phải chọn khoảng thời gian lấy báo cáo");
			
			
			if(!view.equals("TT"))
			{
				String nextJSP = "/TraphacoHYERP/pages/Distributor/BaoCaoChietKhauQuy.jsp";
				response.sendRedirect(nextJSP);
			}
			else
			{
				String nextJSP = "/TraphacoHYERP/pages/Center/BaoCaoChietKhauQuy.jsp";
				response.sendRedirect(nextJSP);
			}
			
			
	    }
	}
	

	private void CreateStaticHeader(Workbook workbook, IBCChietKhauQuy obj) 
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
		    
		    cell.setValue("Quý: " + obj.getTungay() + " - Năm: "  + obj.getDenngay());
	    		    
	    }
	    
	
	    worksheet.setName("BCChietKhauQuy");
	
		
	}
	private void CreateStaticData2015(Workbook workbook, IBCChietKhauQuy obj ) 
	{
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	    Cells cells = worksheet.getCells();

		ResultSet hdRs = obj.getHoadonRs();
		
		NumberFormat format = new DecimalFormat("#,###,###"); 

		Cell cell = null;
		
		Style style;
		Font font2 = new Font();	
		font2.setName("Times New Roman");
		font2.setSize(12);
		
		Font font3 = new Font();
		font3.setName("Times New Roman");
		font3.setSize(12);
		font3.setBold(true);
		
		
		int i = 8;
		
		if(hdRs != null)
		{
			try 
			{
				int stt = 1;
				double totalCK_BGHH = 0;
				double totalCK_XANH = 0;
				
				double total_DoanhSo = 0;
				double total_DoanhThu = 0;
				
				double total_XANH = 0;
				double total_HHBG = 0;
				double total_KHAC = 0;
				
				while(hdRs.next())
				{
					//double DOANHTHU = hdRs.getDouble("tienthuve");
					double DOANHTHU =hdRs.getDouble("t1")+hdRs.getDouble("t2")+hdRs.getDouble("t3");
					total_DoanhThu += DOANHTHU;
					
					double DS_HHBG = hdRs.getDouble("DS_HHBG");
					total_HHBG += DS_HHBG;
					
					double DS_XANH = hdRs.getDouble("DS_XANH");
					total_XANH += DS_XANH;
					
					double DS_KHAC = hdRs.getDouble("DS_KHAC");
					total_KHAC += DS_KHAC;
					
					double TONGTIEN = DS_HHBG + DS_XANH + DS_KHAC;
					total_DoanhSo += TONGTIEN;
					
					double ptHHBG = hdRs.getDouble("PT_HHBG");
					double ptXANH = hdRs.getDouble("PT_XANH");
					double ptthuong_xanh=0;
					double ptthuong_bg=0;
					
					/*double thueVAT = 0;
					if(!loaiNPP.equals("4") && !loaiNPP.equals("5") )
						thueVAT = 5;*/
					
				/*-	 a. Nhóm HH-BG:   HH-BG ≤ 30% sẽ được hưởng ck tháng.
							b. Nhóm XANH:   
								i. 25% ≥ XANH ≥ 15% tổng DS Quý  được hưởng 2%
								ii. XANH  ≥ 25%             tổng DS Quý  được hưởng 3%
       			NHóm XANH muốn được thưởng thì  điều kiện HH-BG như sau: DS HH-BG ≤  30% Ds Quý
       			    - CK Quý nhóm HH-BG: <= 30 % tổng DT được thưởng 2% * Doanh thu quý.
      					- CK Quý nhóm HH-BG: 30% <= HH-BG <= 40%: không được hưởng CK quý.
					100010	Quầy 207 Hapu
					106211 Cửa hàng số 01 - Chi Nhánh Công ty cổ phần Traphaco*/
				
					if(obj.getNppId().equals("100010")||obj.getNppId().equals("106211"))
					{
					//	System.out.println("[BT]"+obj.getNppId());
						if( ( 15 <=ptXANH && ptXANH<25 ) && ptHHBG <=30)
						{
							ptthuong_xanh=0.02;
						}
						else if( 25<=ptXANH && ptHHBG <=30)
						{
							ptthuong_xanh=0.03;
						}
						if( ptHHBG <=30)
						{
							ptthuong_bg=0.02;
						}	
					}else 
					{
					//	System.out.println("[###]"+obj.getNppId());
						
					 if( ( 15 <=ptXANH && ptXANH<25 ) && ptHHBG <=60)
						{
							ptthuong_xanh=0.02;
						}
						else if( 25<=ptXANH && ptHHBG <=60)
						{
							ptthuong_xanh=0.03;
						}
					 if(40<=ptHHBG && ptHHBG <=60)
						{
							ptthuong_bg=0.02;
						}	
					 
					}
					cell = cells.getCell("A" + Integer.toString(i));	cell.setValue(stt); 	    style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);			 				
					cell = cells.getCell("B" + Integer.toString(i));	cell.setValue(hdRs.getString("ddkdTEN")); 	style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					cell = cells.getCell("C" + Integer.toString(i));	cell.setValue(hdRs.getString("MAFAST")); 	style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);			
					cell = cells.getCell("D" + Integer.toString(i));	cell.setValue(hdRs.getString("MaHD")); 	style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);			
					cell = cells.getCell("E" + Integer.toString(i));	cell.setValue(hdRs.getString("khTEN")); 	style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					cell = cells.getCell("F" + Integer.toString(i));	cell.setValue(hdRs.getString("DIACHI")); 	style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					
					cell = cells.getCell("G" + Integer.toString(i));	cell.setValue(TONGTIEN); 	style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
					//cell = cells.getCell("H" + Integer.toString(i));	cell.setValue(DOANHTHU); 	style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);	
					cell = cells.getCell("H" + Integer.toString(i));	cell.setValue(DS_HHBG); 	style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);	
					cell = cells.getCell("I" + Integer.toString(i));	cell.setValue(ptHHBG/100);  style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);	 
					cell = cells.getCell("J" + Integer.toString(i));	cell.setValue(ptthuong_bg); 	style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);		
					cell = cells.getCell("K" + Integer.toString(i));	cell.setValue(DS_XANH); 	style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);	
					cell = cells.getCell("L" + Integer.toString(i));	cell.setValue(ptXANH/100); 	style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);		 
					cell = cells.getCell("M" + Integer.toString(i));	cell.setValue(ptthuong_xanh); 	style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);		
					cell = cells.getCell("N" + Integer.toString(i));	cell.setValue(ptthuong_xanh+ptthuong_bg); 	style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);		
					cell = cells.getCell("O" + Integer.toString(i));	cell.setValue(hdRs.getDouble("t1")); 	style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);		
					cell = cells.getCell("P" + Integer.toString(i));	cell.setValue(hdRs.getDouble("t2")); 	style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);		
					cell = cells.getCell("Q" + Integer.toString(i));	cell.setValue(hdRs.getDouble("t3")); 	style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);		
					cell = cells.getCell("R" + Integer.toString(i));	cell.setValue(DOANHTHU); 	style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);		
					totalCK_BGHH+=DOANHTHU*ptthuong_bg;
					cell = cells.getCell("S" + Integer.toString(i));	cell.setValue(DOANHTHU*ptthuong_bg); 	style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);		
					totalCK_XANH=DOANHTHU*ptthuong_xanh;
					cell = cells.getCell("T" + Integer.toString(i));	cell.setValue(DOANHTHU*ptthuong_xanh); 	style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);		
					cell = cells.getCell("U" + Integer.toString(i));	cell.setValue((DOANHTHU*ptthuong_bg)+(DOANHTHU*ptthuong_xanh)); 	style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);		
					
					i++;
					stt ++;
				}
				hdRs.close();
				
				
				//THEM DONG TONG CONG
					
				cells.merge(i-1, 0, i-1,4);
				cell = cells.getCell("A" + Integer.toString(i));	cell.setValue("Tổng");  style = cell.getStyle(); style.setFont(font3); cell.setStyle(style); setCellBorderStyle1(cell, HorizontalAlignmentType.CENTRED);
				cell = cells.getCell("B" + Integer.toString(i));	cell.setValue("");      style = cell.getStyle(); style.setFont(font3); cell.setStyle(style); setCellBorderStyle1(cell, HorizontalAlignmentType.LEFT);
				cell = cells.getCell("C" + Integer.toString(i));	cell.setValue("");      style = cell.getStyle(); style.setFont(font3); cell.setStyle(style); setCellBorderStyle1(cell, HorizontalAlignmentType.LEFT);
				cell = cells.getCell("D" + Integer.toString(i));	cell.setValue("");      style = cell.getStyle(); style.setFont(font3); cell.setStyle(style); setCellBorderStyle1(cell, HorizontalAlignmentType.LEFT); 
				cell = cells.getCell("E" + Integer.toString(i));	cell.setValue("");      style = cell.getStyle(); style.setFont(font3); cell.setStyle(style); setCellBorderStyle1(cell, HorizontalAlignmentType.LEFT);
				cell = cells.getCell("F" + Integer.toString(i));	cell.setValue("");		style = cell.getStyle(); style.setFont(font3); cell.setStyle(style); setCellBorderStyle1(cell, HorizontalAlignmentType.LEFT); 
				cell = cells.getCell("G" + Integer.toString(i));	cell.setValue(total_DoanhSo);		style = cell.getStyle(); style.setFont(font3); cell.setStyle(style); setCellBorderStyle1(cell, HorizontalAlignmentType.RIGHT); 
				cell = cells.getCell("H" + Integer.toString(i));	cell.setValue(total_HHBG);		style = cell.getStyle(); style.setFont(font3); cell.setStyle(style); setCellBorderStyle1(cell, HorizontalAlignmentType.RIGHT); 
				cell = cells.getCell("I" + Integer.toString(i));	cell.setValue("");		style = cell.getStyle(); style.setFont(font3); cell.setStyle(style); setCellBorderStyle1(cell, HorizontalAlignmentType.RIGHT); 
				cell = cells.getCell("J" + Integer.toString(i));	cell.setValue("");		style = cell.getStyle(); style.setFont(font3); cell.setStyle(style); setCellBorderStyle1(cell, HorizontalAlignmentType.RIGHT); 
				cell = cells.getCell("K" + Integer.toString(i));	cell.setValue(total_XANH);		style = cell.getStyle(); style.setFont(font3); cell.setStyle(style); setCellBorderStyle1(cell, HorizontalAlignmentType.RIGHT);
				cell = cells.getCell("L" + Integer.toString(i));	cell.setValue(""); 		style = cell.getStyle(); style.setFont(font3); cell.setStyle(style); setCellBorderStyle1(cell, HorizontalAlignmentType.RIGHT); 
				cell = cells.getCell("M" + Integer.toString(i));	cell.setValue(""); 		style = cell.getStyle(); style.setFont(font3); cell.setStyle(style); setCellBorderStyle1(cell, HorizontalAlignmentType.RIGHT);
				cell = cells.getCell("N" + Integer.toString(i));	cell.setValue(""); 		style = cell.getStyle(); style.setFont(font3); cell.setStyle(style); setCellBorderStyle1(cell, HorizontalAlignmentType.RIGHT);
				cell = cells.getCell("O" + Integer.toString(i));	cell.setValue(""); 		style = cell.getStyle(); style.setFont(font3); cell.setStyle(style); setCellBorderStyle1(cell, HorizontalAlignmentType.RIGHT);
				cell = cells.getCell("P" + Integer.toString(i));	cell.setValue(""); 		style = cell.getStyle(); style.setFont(font3); cell.setStyle(style); setCellBorderStyle1(cell, HorizontalAlignmentType.RIGHT);
				cell = cells.getCell("Q" + Integer.toString(i));	cell.setValue(""); 		style = cell.getStyle(); style.setFont(font3); cell.setStyle(style); setCellBorderStyle1(cell, HorizontalAlignmentType.RIGHT);
				cell = cells.getCell("R" + Integer.toString(i));	cell.setValue(total_DoanhThu); 		style = cell.getStyle(); style.setFont(font3); cell.setStyle(style); setCellBorderStyle1(cell, HorizontalAlignmentType.RIGHT);
				cell = cells.getCell("S" + Integer.toString(i));	cell.setValue(totalCK_BGHH); 		style = cell.getStyle(); style.setFont(font3); cell.setStyle(style); setCellBorderStyle1(cell, HorizontalAlignmentType.RIGHT);
				cell = cells.getCell("T" + Integer.toString(i));	cell.setValue(totalCK_XANH); 		style = cell.getStyle(); style.setFont(font3); cell.setStyle(style); setCellBorderStyle1(cell, HorizontalAlignmentType.RIGHT);
				cell = cells.getCell("U" + Integer.toString(i));	cell.setValue(""); 		style = cell.getStyle(); style.setFont(font3); cell.setStyle(style); setCellBorderStyle1(cell, HorizontalAlignmentType.RIGHT);
			
				i++;
				
			}
			catch (Exception e)
			{ 
				e.printStackTrace(); 
			}
		}
		

		
		i++;
		
		
	}
	
	private void CreateStaticData(Workbook workbook, IBCChietKhauQuy obj ) 
	{
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	    Cells cells = worksheet.getCells();

		ResultSet hdRs = obj.getHoadonRs();
		
		NumberFormat format = new DecimalFormat("#,###,###"); 

		Cell cell = null;
		
		Style style;
		Font font2 = new Font();	
		font2.setName("Times New Roman");
		font2.setSize(12);
		
		Font font3 = new Font();
		font3.setName("Times New Roman");
		font3.setSize(12);
		font3.setBold(true);
		
		
		int i = 8;
		
		if(hdRs != null)
		{
			try 
			{
				int stt = 1;
				double totalCK_BGHH = 0;
				double totalCK_XANH = 0;
				
				double total_DoanhSo = 0;
				double total_DoanhThu = 0;
				
				double total_XANH = 0;
				double total_HHBG = 0;
				double total_KHAC = 0;
				
				while(hdRs.next())
				{
					double DOANHTHU = hdRs.getDouble("tienthuve");
					total_DoanhThu += DOANHTHU;
					
					double TONGTIEN_HHBG = hdRs.getDouble("DS_HHBG");
					total_HHBG += TONGTIEN_HHBG;
					
					double TONGTIEN_XANH = hdRs.getDouble("DS_XANH");
					total_XANH += TONGTIEN_XANH;
					
					double TONGTIEN_KHAC = hdRs.getDouble("DS_KHAC");
					total_KHAC += TONGTIEN_KHAC;
					
					double TONGTIEN = TONGTIEN_HHBG + TONGTIEN_XANH + TONGTIEN_KHAC;
					total_DoanhSo += TONGTIEN;
					
					double ptHHBG = hdRs.getDouble("PT_HHBG");
					double CK_HHBG = 0;
					if(ptHHBG >= 45)
					{
						CK_HHBG = 2 * DOANHTHU / 100;
					}
					
					double ptXANH = hdRs.getDouble("PT_XANH");
					double CK_XANH = 0;
					if( ptXANH >= 15 && ptXANH < 25 )
					{
						CK_XANH = 2 * DOANHTHU / 100;
					}
					else
					{
						if(ptXANH >= 25)
						{
							CK_XANH = 3 * DOANHTHU / 100;
						}
					}
					
					
					double TILE_HHBG = TONGTIEN_HHBG / TONGTIEN;
					double TILE_XANH = TONGTIEN_XANH / TONGTIEN;
					
					totalCK_BGHH += CK_HHBG;
					totalCK_XANH += CK_XANH;
					
					cell = cells.getCell("A" + Integer.toString(i));	cell.setValue(stt); 	    style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);			 				
					cell = cells.getCell("B" + Integer.toString(i));	cell.setValue(hdRs.getString("ddkdTEN")); 	style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					cell = cells.getCell("C" + Integer.toString(i));	cell.setValue(hdRs.getString("MAFAST")); 	style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);			
					cell = cells.getCell("D" + Integer.toString(i));	cell.setValue(hdRs.getString("khTEN")); 	style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					cell = cells.getCell("E" + Integer.toString(i));	cell.setValue(hdRs.getString("DIACHI")); 	style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					
					cell = cells.getCell("F" + Integer.toString(i));	cell.setValue(TONGTIEN); 	style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
					cell = cells.getCell("G" + Integer.toString(i));	cell.setValue(DOANHTHU); 	style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);	
					cell = cells.getCell("H" + Integer.toString(i));	cell.setValue(TONGTIEN_HHBG); 	style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);	
					cell = cells.getCell("I" + Integer.toString(i));	cell.setValue(TONGTIEN_XANH);  style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);	 
					cell = cells.getCell("J" + Integer.toString(i));	cell.setValue(TILE_HHBG); 	style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);		
					cell = cells.getCell("K" + Integer.toString(i));	cell.setValue(TILE_XANH); 	style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);	
					cell = cells.getCell("L" + Integer.toString(i));	cell.setValue(CK_HHBG); 	style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);		 
					cell = cells.getCell("M" + Integer.toString(i));	cell.setValue(CK_XANH); 	style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);		
					
					i++;
					stt ++;
				}
				hdRs.close();
				
				
				//THEM DONG TONG CONG
					
				cells.merge(i-1, 0, i-1,4);
				cell = cells.getCell("A" + Integer.toString(i));	cell.setValue("Tổng");  style = cell.getStyle(); style.setFont(font3); cell.setStyle(style); setCellBorderStyle1(cell, HorizontalAlignmentType.CENTRED);
				cell = cells.getCell("B" + Integer.toString(i));	cell.setValue("");      style = cell.getStyle(); style.setFont(font3); cell.setStyle(style); setCellBorderStyle1(cell, HorizontalAlignmentType.LEFT);
				cell = cells.getCell("C" + Integer.toString(i));	cell.setValue("");      style = cell.getStyle(); style.setFont(font3); cell.setStyle(style); setCellBorderStyle1(cell, HorizontalAlignmentType.LEFT);
				cell = cells.getCell("D" + Integer.toString(i));	cell.setValue("");      style = cell.getStyle(); style.setFont(font3); cell.setStyle(style); setCellBorderStyle1(cell, HorizontalAlignmentType.LEFT); 
				cell = cells.getCell("E" + Integer.toString(i));	cell.setValue("");      style = cell.getStyle(); style.setFont(font3); cell.setStyle(style); setCellBorderStyle1(cell, HorizontalAlignmentType.LEFT);
				cell = cells.getCell("F" + Integer.toString(i));	cell.setValue(total_DoanhSo);		style = cell.getStyle(); style.setFont(font3); cell.setStyle(style); setCellBorderStyle1(cell, HorizontalAlignmentType.LEFT); 
				cell = cells.getCell("G" + Integer.toString(i));	cell.setValue(total_DoanhThu);		style = cell.getStyle(); style.setFont(font3); cell.setStyle(style); setCellBorderStyle1(cell, HorizontalAlignmentType.RIGHT); 
				cell = cells.getCell("H" + Integer.toString(i));	cell.setValue(total_HHBG);		style = cell.getStyle(); style.setFont(font3); cell.setStyle(style); setCellBorderStyle1(cell, HorizontalAlignmentType.RIGHT); 
				cell = cells.getCell("I" + Integer.toString(i));	cell.setValue(total_XANH);		style = cell.getStyle(); style.setFont(font3); cell.setStyle(style); setCellBorderStyle1(cell, HorizontalAlignmentType.RIGHT); 
				cell = cells.getCell("J" + Integer.toString(i));	cell.setValue("");		style = cell.getStyle(); style.setFont(font3); cell.setStyle(style); setCellBorderStyle1(cell, HorizontalAlignmentType.RIGHT); 
				cell = cells.getCell("K" + Integer.toString(i));	cell.setValue("");		style = cell.getStyle(); style.setFont(font3); cell.setStyle(style); setCellBorderStyle1(cell, HorizontalAlignmentType.RIGHT);
				cell = cells.getCell("L" + Integer.toString(i));	cell.setValue(totalCK_BGHH); 		style = cell.getStyle(); style.setFont(font3); cell.setStyle(style); setCellBorderStyle1(cell, HorizontalAlignmentType.RIGHT); 
				cell = cells.getCell("M" + Integer.toString(i));	cell.setValue(totalCK_XANH); 		style = cell.getStyle(); style.setFont(font3); cell.setStyle(style); setCellBorderStyle1(cell, HorizontalAlignmentType.RIGHT);

				i++;
				
			}
			catch (Exception e)
			{ 
				e.printStackTrace(); 
			}
		}
		

		
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
		style.setBorderLine(BorderType.TOP, 0);
		style.setBorderLine(BorderType.RIGHT, 1);
		style.setBorderLine(BorderType.BOTTOM, 0);
		style.setBorderLine(BorderType.LEFT, 1);
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
