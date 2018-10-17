package geso.traphaco.distributor.servlets.reports;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.servlets.report.ReportAPI;
import geso.traphaco.distributor.beans.bangkehoadonsp.BangKeHoaDonSpList;
import geso.traphaco.distributor.beans.bangkehoadonsp.IBangKeHoaDonSpList;
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

@WebServlet("/BcDoanhSoDeNghiSvl")
public class BcDoanhSoDeNghiSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public BcDoanhSoDeNghiSvl() {
        super();
      
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
  		
  		obj = new BangKeHoaDonSpList();
  		obj.setUserId(userId);
  		String nextJSP = "";
  		obj.init("");
  		
  		nextJSP = "/TraphacoHYERP/pages/Distributor/BcDoanhSoDeNghi.jsp";
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
      
      String tungay = request.getParameter("Sdays");
      if(tungay == null)
      	tungay = "";
      obj.setTuNgay(tungay);
      
      String denngay = request.getParameter("Edays");
      if(denngay == null)
      	denngay = "";
      obj.setDenNgay(denngay);
      
      String khId = request.getParameter("khId");
      if(khId == null)
      	khId = "";
      obj.setKhId(khId);
      
      String ddkdId = request.getParameter("ddkdId");
      if(ddkdId == null)
      	ddkdId = "";
      obj.setDdkdId(ddkdId);
      
      String spId = request.getParameter("spId");
      if(spId == null)
      	spId = "";
      obj.setSpId(spId);
      
      String kbhId = request.getParameter("kbhId");
      if(kbhId == null)
      	kbhId = "";
      obj.setKbhId(kbhId);
    	

      String view = request.getParameter("view");
  	if(view == null)
  		view = "";
  	obj.setView(view);
  	System.out.println("VIEW LA: " + view);
  	
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
  			
  			

  	
      if (action.equals("excel")  )
      {
      	try
  	    { 
  				response.setContentType("application/xlsm");
  				response.setHeader("Content-Disposition", "attachment; filename=BcDoanhSoDeNghi.xlsm");
  	      FileInputStream fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\BcDoanhSoDeNghi.xlsm");
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
    		nextJSP = "/TraphacoHYERP/pages/Distributor/BcDoanhSoDeNghi.jsp";
    		response.sendRedirect(nextJSP); 
      }
      else  if(action.equals("view") || action.equals("next") || action.equals("prev")){
      	
      	obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
      	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
      	obj.setUserId(userId);
      	obj.init("");
      	
      	session.setAttribute("obj", obj);
      	response.sendRedirect("/TraphacoHYERP/pages/Distributor/BcDoanhSoDeNghi.jsp");
      }
      
      else if(action.equals("search"))
      {	
      	obj.setUserId(userId);
      	session.setAttribute("obj", obj);
    		session.setAttribute("userId", userId);
    		obj.init("");
    		String nextJSP = "";
    		nextJSP = "/TraphacoHYERP/pages/Distributor/BcDoanhSoDeNghi.jsp";
    		response.sendRedirect(nextJSP); 
      }
      else
      {
  			session.setAttribute("obj", obj);
  			String nextJSP = "";
  			obj.setMsg("Bạn phải chọn khoảng thời gian lấy báo cáo");
  			nextJSP = "/TraphacoHYERP/pages/Distributor/BangKeHoaDonSp.jsp";
  			response.sendRedirect(nextJSP);  
      }
  		
  	}

  	private boolean FillData(Workbook workbook, IBangKeHoaDonSpList obj, String query) throws Exception
    {
  		dbutils db = new dbutils();
  		Worksheets worksheets = workbook.getWorksheets();
  		Worksheet worksheet = worksheets.getSheet(0);

  		Cells cells = worksheet.getCells();		
  		ResultSet hdRs = db.get(query);
  		
  		double total_SoLuong=0;
  		double total_BVAT=0;
  		double total_AVAT=0;
  		double total_VAT=0;
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
  					
  					
  					cell = cells.getCell("B" + Integer.toString(i));	cell.setValue( hdRs.getInt("n_Row")==1?hdRs.getString("khMa"):"" );
  					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
  					
  					cell = cells.getCell("C" + Integer.toString(i));	cell.setValue(hdRs.getString("soHoaDon"));
  					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
  					
  					cell = cells.getCell("D" + Integer.toString(i));	cell.setValue(hdRs.getInt("n_Row")==1?hdRs.getString("khTen"):"" );
  					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
  					
  					
  					cell = cells.getCell("E" + Integer.toString(i));	cell.setValue(hdRs.getInt("n_Row")==1?hdRs.getString("khDiaCHI"):"" );
  					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
  					
  					cell = cells.getCell("F" + Integer.toString(i));	cell.setValue(hdRs.getString("spTen"));
  					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
  					
  					
  					cell = cells.getCell("G" + Integer.toString(i));	cell.setValue( hdRs.getDouble("soluong") );
  					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 3);
  					
  					cell = cells.getCell("H" + Integer.toString(i));	cell.setValue( hdRs.getDouble("dongia") );
  					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 3);
  					
  					cell = cells.getCell("I" + Integer.toString(i));	cell.setValue( hdRs.getDouble("BVAT") );
  					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 3);
  					
  					cell = cells.getCell("J" + Integer.toString(i));	cell.setValue( hdRs.getDouble("VAT") );
  					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 3);
  					
  					cell = cells.getCell("K" + Integer.toString(i));	cell.setValue( hdRs.getDouble("AVAT") );			
  					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 3);
  					
  					
  					total_SoLuong += hdRs.getDouble("soluong");
  					total_BVAT += hdRs.getDouble("BVAT");
  					total_VAT += hdRs.getDouble("VAT");
  					total_AVAT += hdRs.getDouble("AVAT");
  					
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
  				
  				
  				
  				cell = cells.getCell("G" + Integer.toString(i));	cell.setValue(total_SoLuong );
  				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 3);
  				
  				cell = cells.getCell("H" + Integer.toString(i));	cell.setValue( "" );
  				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 3);
  				
  				cell = cells.getCell("I" + Integer.toString(i));	cell.setValue( total_BVAT );
  				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 3);
  				
  				cell = cells.getCell("J" + Integer.toString(i));	cell.setValue(total_VAT  );
  				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 3);
  				
  				cell = cells.getCell("K" + Integer.toString(i));	cell.setValue( total_AVAT);			
  				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 3);
  				

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
  	    
  	    String tieude = "BẢNG KÊ HÓA ĐƠN CỦA MỘT MẶT HÀNG";
  	    ReportAPI.getCellStyle(cell,Color.RED, true, 14, tieude);
  	    
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
  	    		
  		cell = cells.getCell("A8");	cell.setValue("STT");
  		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
  		
  		cell = cells.getCell("B8");	cell.setValue("Mã KH(FAST)");
  		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
  		
  		cell = cells.getCell("C8");	cell.setValue("Số HĐ");
  		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
  		
  		cell = cells.getCell("D8");	cell.setValue("Tên KH");
  		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
  		
  		cell = cells.getCell("E8");	cell.setValue("Địa chỉ");
  		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
  		
  		cell = cells.getCell("F8");	cell.setValue("Sản phẩm");
  		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
  		
  		cell = cells.getCell("G8");	cell.setValue("Số lượng");
  		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
  		
  		cell = cells.getCell("H8");	cell.setValue("Đơn giá");
  		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
  		
  		cell = cells.getCell("I8");	cell.setValue("Thành tiền");
  		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
  		
  		cell = cells.getCell("J8");	cell.setValue("Thuế");
  		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
  		
  		cell = cells.getCell("K8");	cell.setValue("Tổng tiền thuế");
  		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
  		
  	  
    }

}
