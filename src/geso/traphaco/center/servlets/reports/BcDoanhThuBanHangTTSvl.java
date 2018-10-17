package geso.traphaco.center.servlets.reports;

import geso.traphaco.center.beans.report.IBcDoanhThuBanHangTTList;
import geso.traphaco.center.beans.report.imp.BcDoanhThuBanHangTTList;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.servlets.report.ReportAPI;
import geso.traphaco.center.util.WebService;
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

@WebServlet("/BcDoanhThuBanHangTTSvl")
public class BcDoanhThuBanHangTTSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	public BcDoanhThuBanHangTTSvl()
	{
		super();
		
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		IBcDoanhThuBanHangTTList obj;
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
		
		obj = new BcDoanhThuBanHangTTList();
		obj.setUserId(userId);
		obj.setView("TT");
		String nextJSP = "";
		obj.init("");
		
		
		nextJSP = "/TraphacoHYERP/pages/Center/BcDoanhThuBanHangTT.jsp";
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
    
    IBcDoanhThuBanHangTTList obj = new BcDoanhThuBanHangTTList();
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
	
    String loainpp =request.getParameter("loainpp")==null?"": request.getParameter("loainpp");
    obj.setLoainp(loainpp);
	
    String nhomhangERPid =request.getParameter("nhomhangERPid")==null?"": request.getParameter("nhomhangERPid");
    obj.setNhomhangERPid(nhomhangERPid);
	
    String nhomkhERPid =request.getParameter("nhomkhERPid")==null?"": request.getParameter("nhomkhERPid");
    obj.setNhomkhERPid(nhomkhERPid);
    //obj.setMucCN_DT(util.antiSQLInspection(request.getParameter("cndt")) != null ? util.antiSQLInspection(request.getParameter("cndt")) : "");
    obj.setMucCN_DT("0");
		
	obj.setMuc_KhachHang(util.antiSQLInspection(request.getParameter("kh")) != null ? util.antiSQLInspection(request.getParameter("kh")) : "");
		
    
    obj.setAction(action);
    
    System.out.println("___ATION "+action+"____"+nppId);
    
    
    if (action.equals("excel")  )
    {
    	try
	    { 
				response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "attachment; filename=BcDoanhThuBanHangTT.xlsm");
	      FileInputStream fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\BcDoanhThuBanHangTT.xlsm");
				Workbook workbook = new Workbook();
				workbook.open(fstream);
				workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
				obj.init("");
				CreateStaticHeader(workbook, obj);
				obj.setUserId(userId);
				
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
  		nextJSP = "/TraphacoHYERP/pages/Center/BcDoanhThuBanHangTT.jsp";
  		response.sendRedirect(nextJSP); 
    }
    else  if(action.equals("view") || action.equals("next") || action.equals("prev")){
    	
    	obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
    	obj.setUserId(userId);
    	obj.init("");
    	
    	session.setAttribute("obj", obj);
    	response.sendRedirect("/TraphacoHYERP/pages/Center/BcDoanhThuBanHangTT.jsp");
    }
    
    else if(action.equals("search"))
    {	
    	obj.setUserId(userId);
    	session.setAttribute("obj", obj);
  		session.setAttribute("userId", userId);
  		obj.init("");
  		String nextJSP = "";
  		nextJSP = "/TraphacoHYERP/pages/Center/BcDoanhThuBanHangTT.jsp";
  		response.sendRedirect(nextJSP); 
    }
    else
    {
			session.setAttribute("obj", obj);
			String nextJSP = "";
			nextJSP = "/TraphacoHYERP/pages/Center/BcDoanhThuBanHangTT.jsp";
			obj.init("");
			response.sendRedirect(nextJSP);  
    }
		
	}
	
	private boolean FillData(Workbook workbook, IBcDoanhThuBanHangTTList obj, String query) throws Exception
  {
		dbutils db = new dbutils();
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		Style style;
		Cells cells = worksheet.getCells();	
		System.out.println("query la ____________"+query);
		ResultSet hdRs = db.get(query);
		double total_AVAT=0;
		double total_VAT =0;
		double total_BVAT =0;
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
					
				
					cell = cells.getCell("B" + Integer.toString(i));	cell.setValue(hdRs.getString("nppMa"));
					setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED,false);	
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					
					cell = cells.getCell("C" + Integer.toString(i));	cell.setValue(hdRs.getString("nppTen"));
					style = cell.getStyle();
					style.setTextWrapped(true);
					cell.setStyle(style);
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					
					for(int ij =3;ij<6;ij++)
					{
						cell = cells.getCell(i-1,ij);
						ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 41);
						
						
					}
					
					cells.merge(i-1, 3, i-1, 5);
					cell = cells.getCell("D" + Integer.toString(i));	cell.setValue(hdRs.getString("diachi"));
					style = cell.getStyle();
					style.setTextWrapped(true);
					cell.setStyle(style);
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					
					cell = cells.getCell("G" + Integer.toString(i));	cell.setValue(hdRs.getDouble("BVAT") );
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 3);
					
					cell = cells.getCell("H" + Integer.toString(i));	cell.setValue(hdRs.getDouble("VAT") );
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 3);
					cell = cells.getCell("I" + Integer.toString(i));	cell.setValue(hdRs.getDouble("AVAT") );
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 3);
					
					
					total_AVAT +=hdRs.getDouble("AVAT");
					total_VAT +=hdRs.getDouble("VAT");
					total_BVAT +=hdRs.getDouble("BVAT");
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
				cell = cells.getCell("A" + Integer.toString(i));	cell.setValue("Tổng cộng");
				setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED,false);	
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
								
				cell = cells.getCell("G" + Integer.toString(i));	cell.setValue( total_BVAT);			
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 3);
				cell = cells.getCell("H" + Integer.toString(i));	cell.setValue( total_VAT);			
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 3);
				cell = cells.getCell("I" + Integer.toString(i));	cell.setValue( total_AVAT);			
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 3);
			
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
	private void CreateStaticHeader(Workbook workbook, IBcDoanhThuBanHangTTList obj)
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
	    
	    /*String tieude = "DOANH THU BÁN HÀNG";
	    ReportAPI.getCellStyle(cell,Color.RED, true, 14, tieude);
	    
	    String message = "";
			cells.setRowHeight(2, 18.0f);
			cell = cells.getCell("A3");
			ReportAPI.getCellStyle(cell, Color.RED, true, 9, message);   
*/
	    cells.setRowHeight(3, 18.0f);
	    cell = cells.getCell("A6");
	    ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Từ ngày : " + obj.getTuNgay() + " Đến ngày :  "  + obj.getDenNgay());

	    cells.setRowHeight(4, 18.0f);
	    cell = cells.getCell("A7");
	    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Ngày báo cáo: " + ReportAPI.NOW("yyyy-MM-dd"));
	    
	    cells.setRowHeight(5, 18.0f);
	    cell = cells.getCell("A8");
	    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Được tạo bởi:  "  +obj.getUserTen()  );
	    		
		/*cell = cells.getCell("A8");	cell.setValue("STT");
		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
		

		cell = cells.getCell("B8");	cell.setValue("Mã KH");
		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
		
		cell = cells.getCell("C8");	cell.setValue("Tên khách hàng");
		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
		
		cell = cells.getCell("D8");	
		cell.setValue("Địa chỉ");
		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
		
		cell = cells.getCell("E8");	cell.setValue("Doanh thu");
		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
		cell = cells.getCell("E9");	cell.setValue("Doanh thu");
		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);*/
		
  }
	
}
