package geso.traphaco.center.servlets.reports;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.servlets.report.ReportAPI;
import geso.traphaco.center.util.WebService;
import geso.traphaco.center.beans.report.IBangKeHoaDonSpList;
import geso.traphaco.center.beans.report.imp.BangKeHoaDonSpList;
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

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

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

@WebServlet("/BangKeHoaDonSpTTSvl")
public class BangKeHoaDonSpTTSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	public BangKeHoaDonSpTTSvl()
	{
		super();
		// TODO Auto-generated constructor stub
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		IBangKeHoaDonSpList obj;
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
		
		
		String loai = request.getParameter("loai");
		if (loai == null)
			loai = "";
		
		
		obj = new BangKeHoaDonSpList();
		obj.setUserId(userId);
		obj.setView("TT");
		obj.setLoai(loai);
		
		String nextJSP = "";
		obj.init("");
		
		if(loai.equals("NH"))
		{
			nextJSP = "/TraphacoHYERP/pages/Center/BangKeHoaDonSpTTNH.jsp";
		}
		else
		{
			nextJSP = "/TraphacoHYERP/pages/Center/BangKeHoaDonSpTT.jsp";
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
    
    IBangKeHoaDonSpList obj = new BangKeHoaDonSpList();
    obj.setUserId(userId);
    
    obj.setView("TT");
    
    String tungay =request.getParameter("Sdays")==null?"": request.getParameter("Sdays");
    obj.setTuNgay(tungay);
    
    String denngay = request.getParameter("Edays")==null?"": request.getParameter("Edays");
    obj.setDenNgay(denngay);
    
    String vungId = request.getParameter("vungId")==null?"": request.getParameter("vungId");
    obj.setVungId(vungId);
    
    String kvId = request.getParameter("kvId")==null?"": request.getParameter("kvId");
    obj.setKvId(kvId);

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

    String nhomhangERPid =request.getParameter("nhomhangERPid")==null?"": request.getParameter("nhomhangERPid");
    obj.setNhomhangERPid(nhomhangERPid);
	
    String nhomkhERPid =request.getParameter("nhomkhERPid")==null?"": request.getParameter("nhomkhERPid");
    obj.setNhomkhERPid(nhomkhERPid);
    //obj.setMucCN_DT(util.antiSQLInspection(request.getParameter("cndt")) != null ? util.antiSQLInspection(request.getParameter("cndt")) : "");
    obj.setMucCN_DT("0");
		
	obj.setMuc_KhachHang(util.antiSQLInspection(request.getParameter("kh")) != null ? util.antiSQLInspection(request.getParameter("kh")) : "");
	
	obj.setLaynk(util.antiSQLInspection(request.getParameter("klaynhomnk")) != null ? util.antiSQLInspection(request.getParameter("klaynhomnk")) : "0");
	System.out.println("nhom sp la"+ (request.getParameter("klaynhomnk")));
    
    obj.setAction(action);
    
    System.out.println("___ATION "+action+"___"+nppId);
	String loai = request.getParameter("loai");
	if (loai == null)
		loai = "";
	obj.setLoai(loai);
    
    if (action.equals("excel")  )
    {
    	try
	    { 
				response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "attachment; filename=DoanhThuTheoSanPham.xlsm");
				FileInputStream fstream ;
				if(obj.getLoai().equals("NH"))
				{
					 fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\BangKeHoaDonSpTTNH.xlsm");	
				}
				else
				{
					 fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\BangKeHoaDonSpTT.xlsm");
				}
				Workbook workbook = new Workbook();
				workbook.open(fstream);
				workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
				
				obj.setUserId(userId);
				obj.init("");
				String query="";
				if(obj.getLoai().equals("NH"))
				{
					 query=obj.getQuerynh();
					 FillData_NH(workbook,obj, query);
				}
				else
				{
					CreateStaticHeader(workbook, obj);
					 query=obj.getQueryHd();
					 FillData(workbook,obj, query);
				}
				
				
				
				workbook.save(out);
				fstream.close();
	    }
	    catch (Exception ex)
	    {
	    	ex.printStackTrace();
	    }
    	session.setAttribute("obj", obj);
  		session.setAttribute("userId", userId);
  		String nextJSP = "";
  		nextJSP = "/TraphacoHYERP/pages/Center/BangKeHoaDonSpTT.jsp";
  		response.sendRedirect(nextJSP); 
    }
    else  if(action.equals("view") || action.equals("next") || action.equals("prev")){
    	
    	obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
    	obj.setUserId(userId);
    	obj.init("");
    	
    	session.setAttribute("obj", obj);
    	response.sendRedirect("/TraphacoHYERP/pages/Center/BangKeHoaDonSpTT.jsp");
    }
    
    else if(action.equals("search"))
    {	
    	obj.setUserId(userId);
    	session.setAttribute("obj", obj);
  		session.setAttribute("userId", userId);
  		obj.init("");
  		String nextJSP = "";
  		nextJSP = "/TraphacoHYERP/pages/Center/BangKeHoaDonSpTT.jsp";
  		response.sendRedirect(nextJSP); 
    }
    else
    {
			session.setAttribute("obj", obj);
			String nextJSP="";
			if(loai.equals("NH"))
			{
				nextJSP = "/TraphacoHYERP/pages/Center/BangKeHoaDonSpTTNH.jsp";
			}
			else
			{
				nextJSP = "/TraphacoHYERP/pages/Center/BangKeHoaDonSpTT.jsp";
			}
			obj.init("");
			response.sendRedirect(nextJSP);  
    }
		
	}
	
	private boolean FillData(Workbook workbook, IBangKeHoaDonSpList obj, String query) throws Exception
  {
		dbutils db = new dbutils();
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);

		Cells cells = worksheet.getCells();		
		System.out.println("query chay "+query);
		ResultSet hdRs = db.get(query);
		ResultSet hdRstong =  obj.getTotalRs();
		double tongtientruocthue=0;
		double tongdoanhthu=0;
		double tongthue=0;
		Style style;
		if (hdRstong != null) 
		{
			while(hdRstong.next())
			{
				
				tongtientruocthue+=hdRstong.getDouble("BVAT");
					tongdoanhthu +=hdRstong.getDouble("AVAT");
				
					tongthue+=hdRstong.getDouble("VAT");
				
			}
		}
			
		int i = 11;
		int SoTt=1;
		if (hdRs != null) 
		{
			try 
			{
				Cell cell = null;

				while(hdRs.next() )
				{		
					
					
					cell = cells.getCell("A" + Integer.toString(i));	cell.setValue(SoTt++ );
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					
					
					System.out.println("::: ABC: " + i + ": " +hdRs.getString("spMa") );
				
					cell = cells.getCell("B" + Integer.toString(i));	cell.setValue(hdRs.getString("spMa"));
					setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED,false);	
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					
					cell = cells.getCell("C" + Integer.toString(i));	cell.setValue(hdRs.getString("spTen"));
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					style = cell.getStyle();
					style.setTextWrapped(true);
					cell.setStyle(style);
					cell = cells.getCell("D" + Integer.toString(i));	cell.setValue(hdRs.getString("spDonVi") );
					 
					setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED,false);	
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					
					cell = cells.getCell("E" + Integer.toString(i));	cell.setValue(hdRs.getDouble("SoLuong"));
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 41);
					
					cell = cells.getCell("F" + Integer.toString(i));	cell.setValue(( hdRs.getDouble( "BVAT" )   )/(hdRs.getDouble( "SoLuong" ) )   );
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 41);
					/*style=cell.getStyle();
					style.setCustom("#,##0.0000");
					cell.setStyle(style);*/
					
					cell = cells.getCell("G" + Integer.toString(i));	cell.setValue(hdRs.getDouble( "BVAT" ) );
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 41);
					
					cell = cells.getCell("H" + Integer.toString(i));	cell.setValue(hdRs.getDouble( "VAT" ) );
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 41);
					
					cell = cells.getCell("I" + Integer.toString(i));	cell.setValue(hdRs.getDouble( "AVAT" ) );
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 41);
					
					
					
					
					
					++i;					
				}
				i--;
				for(int it =0;it<6;it++)
				{
					cell = cells.getCell(i,it);
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 41);
					
					
				}
				cells.merge(i, 0, i, 5);
				i++;
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 41);
				cell = cells.getCell("A" + Integer.toString(i));	cell.setValue("Tổng cộng " );
				setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED,false);	
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 41);
				
				cell = cells.getCell("G" + Integer.toString(i));	cell.setValue(tongtientruocthue );
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 41);
				cell = cells.getCell("H" + Integer.toString(i));	cell.setValue(tongthue );
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 41);
				cell = cells.getCell("I" + Integer.toString(i));	cell.setValue(tongdoanhthu );
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 41);
				
				//ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
				

				
			/*	cell = cells.getCell("A" + Integer.toString(i));	cell.setValue("Tổng cộng");
				ReportAPI.mergeCells(worksheet, i-1,i-1, 0, 4);
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
				
				
				cell = cells.getCell("B" + Integer.toString(i));	cell.setValue("" );
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
				
				cell = cells.getCell("C" + Integer.toString(i));	cell.setValue("");
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
				
				cell = cells.getCell("D" + Integer.toString(i));	cell.setValue("" );
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
				
				cell = cells.getCell("E" + Integer.toString(i));	cell.setValue("" );
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
				
				cell = cells.getCell("F" + Integer.toString(i));	cell.setValue(total_BH );
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
				
				
				cell = cells.getCell("G" + Integer.toString(i));	cell.setValue( "" );
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 41);
				
				cell = cells.getCell("H" + Integer.toString(i));	cell.setValue(total_TH  );
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 41);
				
				cell = cells.getCell("I" + Integer.toString(i));	cell.setValue( "");			
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 41);
				

				cell = cells.getCell("J" + Integer.toString(i));	cell.setValue( "");			
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 41);
				
				
				cell = cells.getCell("K" + Integer.toString(i));	cell.setValue( "");			
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 41);
				
				
				cell = cells.getCell("L" + Integer.toString(i));	cell.setValue( "");			
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 41);
				*/
				
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
	private void setCellBorderStyle(Cell cell, short align,boolean kt) {
		Style style = cell.getStyle();
		style.setHAlignment(align);
		style.setBorderLine(BorderType.TOP, 1);
		style.setBorderLine(BorderType.RIGHT, 1);
		style.setBorderLine(BorderType.BOTTOM, 1);
		style.setBorderLine(BorderType.LEFT, 1);
		if(kt)
		{
			com.aspose.cells.Font font2 = new com.aspose.cells.Font(); 
			font2.setName("Calibri");
			font2.setColor(Color.BLACK);
			font2.setSize(11);
			style.setFont(font2);
			style.setColor(Color.SILVER);
		}
		else
			style.setColor(Color.WHITE);

		cell.setStyle(style);
	}
	
	private boolean FillData_NH(Workbook workbook, IBangKeHoaDonSpList obj, String query) throws Exception
	  {
			dbutils db = new dbutils();
			Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet(0);

			Cells cells = worksheet.getCells();		
			System.out.println("query chay "+query);
			ResultSet hdRs = db.get(query);
			ResultSet hdRstong = db.get(obj.getQuerytong());
			double tyle=0;
			double tongdoanhthu=0;
			double tongck=0;
			try {
				if (hdRstong != null) 
				{
					while( hdRstong.next() )
					{
						
						//if(WebService.getValues( element, "STT" ).equals("0"))
						if(hdRstong.getString("STT").equals("0"))
						{
							tongdoanhthu += hdRstong.getDouble("AVATDS");
							//tongdoanhthu=Double.parseDouble(WebService.getValues( element, "AVATDS" ));
						}
						else
						{
							tongck += hdRstong.getDouble("AVATDS");
							//tongck=Double.parseDouble(WebService.getValues( element, "AVATDS" ));
						}
					}
					hdRstong.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
				
			tyle=tongck/tongdoanhthu*(-1.0);
			System.out.println("ty le "+tyle);
				
			Style style;
			
			double DonGia_AVG=0;
			double total_BH=0;
			double total_TH=0;
			double total_ckquy=0;
			double total_ckdt=0;
			int i = 6;
			int SoTt=1;
			if (hdRs != null) 
			{
				try 
				{
					Cell cell = null;

					while( hdRs.next() )
					{		
						
						DonGia_AVG=0;
						
						/*if(Double.parseDouble(WebService.getValues( element, "SoLuong" ) )  >0)
							DonGia_AVG= Double.parseDouble(WebService.getValues( element, "BVAT" ) )/Double.parseDouble(WebService.getValues( element, "SoLuong" ) );
						*/
						DonGia_AVG=1;
						cell = cells.getCell("A" + Integer.toString(i));	cell.setValue(SoTt++ );
						ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
						
						
						//System.out.println("::: ABC: " + i + ": " + WebService.getValues( element, "spMa" ));
					
						cell = cells.getCell("B" + Integer.toString(i));	cell.setValue(hdRs.getString("NhomHang"));
						ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
						
						cell = cells.getCell("C" + Integer.toString(i));	cell.setValue(hdRs.getString("Avatb"));
						ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
						
						cell = cells.getCell("D" + Integer.toString(i));	cell.setValue(hdRs.getString("GIATON") );
						ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
						
						cell = cells.getCell("E" + Integer.toString(i));	cell.setValue(hdRs.getDouble("AvatT"));
						ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 41);
						
						cell = cells.getCell("F" + Integer.toString(i));	cell.setValue(hdRs.getDouble("DonGiaTra") );
						ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 41);

						
						cell = cells.getCell("G" + Integer.toString(i));	cell.setValue(hdRs.getDouble("Avatb")*tyle*(-1));
						ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 41);
						double tt=(hdRs.getDouble("Avatb")*tyle*(-1)) + hdRs.getDouble("Avatb") +  hdRs.getDouble("AvatT"); 
						
						cell = cells.getCell("H" + Integer.toString(i));	cell.setValue(tt);
						ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 41);
						
						cell = cells.getCell("I" + Integer.toString(i));	cell.setValue(hdRs.getDouble("GIATON")+hdRs.getDouble("DonGiaTra"));
						ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 41);
						
						
						total_BH += hdRs.getDouble("Avatb");
						total_TH +=hdRs.getDouble("AvatT");
						
						++i;					
					}
					hdRs.close();
					i++;
					cell = cells.getCell("B" + Integer.toString(i));	cell.setValue("Tổng chung " );
					cell = cells.getCell("C" + Integer.toString(i));	cell.setValue(tongdoanhthu );
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.NONE, false, 41);
					i++;
					cell = cells.getCell("B" + Integer.toString(i));	cell.setValue("Giảm trừ (Chiết khấu bán hàng)" );
					cell = cells.getCell("C" + Integer.toString(i));	cell.setValue(tongck*-1.0 );
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.NONE, false,41);
					i++;
					cell = cells.getCell("B" + Integer.toString(i));	cell.setValue("Phân bổ CK" );
					cell = cells.getCell("C" + Integer.toString(i));	cell.setValue(tyle );
					//ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					

					
				/*	cell = cells.getCell("A" + Integer.toString(i));	cell.setValue("Tổng cộng");
					ReportAPI.mergeCells(worksheet, i-1,i-1, 0, 4);
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
					
					
					cell = cells.getCell("B" + Integer.toString(i));	cell.setValue("" );
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					
					cell = cells.getCell("C" + Integer.toString(i));	cell.setValue("");
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					
					cell = cells.getCell("D" + Integer.toString(i));	cell.setValue("" );
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					
					cell = cells.getCell("E" + Integer.toString(i));	cell.setValue("" );
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					
					cell = cells.getCell("F" + Integer.toString(i));	cell.setValue(total_BH );
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					
					
					cell = cells.getCell("G" + Integer.toString(i));	cell.setValue( "" );
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 41);
					
					cell = cells.getCell("H" + Integer.toString(i));	cell.setValue(total_TH  );
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 41);
					
					cell = cells.getCell("I" + Integer.toString(i));	cell.setValue( "");			
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 41);
					

					cell = cells.getCell("J" + Integer.toString(i));	cell.setValue( "");			
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 41);
					
					
					cell = cells.getCell("K" + Integer.toString(i));	cell.setValue( "");			
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 41);
					
					
					cell = cells.getCell("L" + Integer.toString(i));	cell.setValue( "");			
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 41);
					*/
					
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


	private void CreateStaticHeader(Workbook workbook, IBangKeHoaDonSpList obj)
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
	    
	   
	    
	    String message = "";
			cells.setRowHeight(2, 18.0f);
			cell = cells.getCell("A2");
			ReportAPI.getCellStyle(cell, Color.RED, true, 9, message);   

	    cells.setRowHeight(3, 18.0f);
	    cell = cells.getCell("A6");
	    ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Từ ngày : " + obj.getTuNgay() + " Đến ngày : " + obj.getDenNgay() + "" );
		
	    cells.setRowHeight(4, 18.0f);
	    cell = cells.getCell("A7");
	    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Ngày báo cáo: " + ReportAPI.NOW("yyyy-MM-dd"));
	    
	    cells.setRowHeight(5, 18.0f);
	    cell = cells.getCell("A8");
	    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Được tạo bởi:  "  + obj.getUserTen() );
	    		
		/*cell = cells.getCell("A8");	cell.setValue("STT");
		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
		

		cell = cells.getCell("B8");	cell.setValue("MÃ VẬT TƯ");
		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
		
		cell = cells.getCell("C8");	cell.setValue("TÊN VẬT TƯ");
		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
		
		cell = cells.getCell("D8");	cell.setValue("ĐVT");
		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
		
		cell = cells.getCell("E8");	cell.setValue("SỐ LƯỢNG");
		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
		
		cell = cells.getCell("F8");	cell.setValue("ĐƠN GIÁ");
		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
		
		cell = cells.getCell("G8");	cell.setValue("DOANH THU");
		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
		
		cell = cells.getCell("H8");	cell.setValue("THUẾ GTGT");
		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
		
		cell = cells.getCell("I8");	cell.setValue("TỔNG TIỀN");
		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
		
		
		cell = cells.getCell("J8");	cell.setValue("Tiền Vốn");
		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
		
		cell = cells.getCell("K8");	cell.setValue("Giá Vốn");
		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);*/
		
		
  }

}
