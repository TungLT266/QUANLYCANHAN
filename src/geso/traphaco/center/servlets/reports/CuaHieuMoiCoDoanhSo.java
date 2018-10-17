package geso.traphaco.center.servlets.reports;

import geso.traphaco.center.beans.stockintransit.IStockintransit;
import geso.traphaco.center.beans.stockintransit.imp.Stockintransit;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

@WebServlet("/UsingPromoTT")
public class CuaHieuMoiCoDoanhSo extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public CuaHieuMoiCoDoanhSo() {
        super();
        
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		Utility util=new Utility();
		HttpSession session = request.getSession();
		IStockintransit obj = new Stockintransit();	  
		
		String userTen = (String)session.getAttribute("userTen");
		obj.setuserTen(userTen==null? "":userTen);
		
		String querystring=request.getQueryString();
		String userId=	util.getUserId(querystring);
		obj.setuserId(userId==null? "":userId);
		
		obj.init();
		session.setAttribute("obj", obj);	
		session.setAttribute("userTen", userTen);
		String nextJSP = "/TraphacoHYERP/pages/Center/CuaHieuMoiCoDoanhSo.jsp";
		response.sendRedirect(nextJSP);		
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");
			HttpSession session = request.getSession();	 		
	 		IStockintransit obj = new Stockintransit();	
	 		OutputStream out = response.getOutputStream();
	 		try
	 		{
				String userId = (String) session.getAttribute("userId");
				String userTen = (String) session.getAttribute("userTen");	
				obj.setuserId(userId==null? "":userId);
				obj.setuserTen(userTen==null? "":userTen);
				obj.settungay(request.getParameter("Sdays")==null? "":request.getParameter("Sdays"));			
				obj.setdenngay(request.getParameter("Edays")==null? "":request.getParameter("Edays"));
 
				obj.setkenhId(request.getParameter("kenhId")==null? "":request.getParameter("kenhId"));				
	 
				obj.setnppId(request.getParameter("npp")==null? "":request.getParameter("npp"));
				obj.setvungId(request.getParameter("vung")==null? "":request.getParameter("vung"));
				obj.setkhuvucId(request.getParameter("khuvuc")==null? "":request.getParameter("khuvuc"));
 
				Utility Ult = new Utility();
			
				String condition = "";
				if(obj.getvungId().length()>0)
					condition +=" and v.pk_seq = '"+ obj.getvungId() +"'";
				
				if(obj.getkhuvucId().length()>0)
					condition +=" and kv.pk_seq = '"+ obj.getkhuvucId() +"'";
				
				if(obj.getnppId().length()>0)
					condition +=" and npp.pk_seq = '"+ obj.getnppId() +"'";
				
				if(obj.getkenhId().length()>0)
					condition +=" and kbh.pk_seq ='" + obj.getkenhId() +"'";
 
				condition=condition +  " and npp.pk_seq in "+ Ult.quyen_npp(obj.getuserId())  +
				"  and kbh.pk_seq in "+ Ult.quyen_kenh(obj.getuserId()) ;
				
				Utility util = new Utility();
				String action = request.getParameter("action");				
				if (action.equals("create")) {
					response.setContentType("application/xlsm");
					response.setHeader("Content-Disposition",
							"attachment; filename=CuaHieuMoiCoDoanhSo(TT)_"+util.setTieuDe(obj)+".xlsm");
					CreatePivotTable(out,obj,condition);
				}	else{
					  obj.init();
						session.setAttribute("obj", obj);	
						session.setAttribute("userTen", obj.getuserTen());
						String nextJSP = "/TraphacoHYERP/pages/Center/CuaHieuMoiCoDoanhSo.jsp";
						response.sendRedirect(nextJSP);		
				}
		     }
		    catch (Exception ex)
		    {
		    	  obj.init();
		    	  obj.setMsg(ex.getMessage());
					session.setAttribute("obj", obj);	
					session.setAttribute("userTen", obj.getuserTen());
					String nextJSP = "/TraphacoHYERP/pages/Center/CuaHieuMoiCoDoanhSo.jsp";
					response.sendRedirect(nextJSP);		
		     
		    }
		  
	}
	
	private void CreatePivotTable(OutputStream out,IStockintransit obj, String condition) throws Exception
    {       
 		String strfstream = getServletContext().getInitParameter("path")+"\\CuaHieuMoiCoDoanhSo.xlsm";
 		FileInputStream fstream = new FileInputStream(strfstream);
		Workbook workbook = new Workbook();
		workbook.open(fstream);
		workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);

		CreateStaticHeader(workbook,obj);	     
	    CreateStaticData(workbook, obj, condition);
	    workbook.save(out);
	    fstream.close();
	}
 	
 	private void CreateStaticHeader(Workbook workbook, IStockintransit obj)throws Exception 
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
	    style.setHAlignment(TextAlignmentType.LEFT) ;	   
	    
	    cell.setValue("KHÁCH HÀNG MỚI CÓ PHÁT SINH DOANH SỐ");  getCellStyle(workbook,"A1",Color.RED,true,14);	  	
	    
	    cells.setRowHeight(2, 18.0f);
	    cell = cells.getCell("A3"); 
	    getCellStyle(workbook,"A3",Color.NAVY,true,10);	    
	    cell.setValue("Từ ngày: " + obj.gettungay());
	    
	    cells.setRowHeight(3, 18.0f);
	    cell = cells.getCell("B3"); getCellStyle(workbook,"B3",Color.NAVY,true,9);	        
	    cell.setValue("Đến ngày: " + obj.getdenngay());    
	    
	    cells.setRowHeight(4, 18.0f);
	    cell = cells.getCell("A4");getCellStyle(workbook,"A4",Color.NAVY,true,9);
	    cell.setValue("Ngày báo cáo: " + this.getDate());
	    
	    cells.setRowHeight(5, 18.0f);
	    cell = cells.getCell("A5");getCellStyle(workbook,"A5",Color.NAVY,true,9);
	    cell.setValue("Được tạo bởi:  " + obj.getuserTen());
			  
	    cell = cells.getCell("AA1"); cell.setValue("Kenh");
		cell = cells.getCell("AB1"); cell.setValue("Vung");
 	    cell = cells.getCell("AC1"); cell.setValue("Khu vuc");  	
 	    cell = cells.getCell("AD1"); cell.setValue("MaCN/DT");
	    cell = cells.getCell("AE1"); cell.setValue("CN/DT");
	    cell = cells.getCell("AF1"); cell.setValue("MaKhachHang");
	    cell = cells.getCell("AG1"); cell.setValue("TenKhachHang");
	    cell = cells.getCell("AH1"); cell.setValue("DiaChi");
	    cell = cells.getCell("AI1"); cell.setValue("DienThoai");
	    cell = cells.getCell("AJ1"); cell.setValue("HangCuaHang");
	    cell = cells.getCell("AK1"); cell.setValue("LoaiCuaHang");
	    cell = cells.getCell("AL1"); cell.setValue("ViTriCuaHang");
	    cell = cells.getCell("AM1"); cell.setValue("NGAYPHATSINH");
	    
 
	    
 	}
 	
	private void CreateStaticData(Workbook workbook, IStockintransit obj, String condition) throws Exception
 	{
 		Worksheets worksheets = workbook.getWorksheets();
 	    Worksheet worksheet = worksheets.getSheet(0);
 	    Cells cells = worksheet.getCells();
 	    dbutils db = new dbutils();	  
	
 	    String sql = "  SELECT KH.PK_SEQ,KH.TEN,KH.DIACHI,KH.DIENTHOAI ,KH.SMARTID,KH.CHUCUAHIEU,  "+ 
					 "  NPP.SITECODE,NPP.TEN as tennpp,KV.TEN AS KHUVUC," +
					 " V.TEN AS VUNG,ISNULL(VTCH.DIENGIAI,'') AS VITRI,ISNULL(HCH.DIENGIAI,'')  "+ 
					 "   AS HANGCUAHANG,  "+ 
					 "  ISNULL(LCH.DIENGIAI,'') AS LOAICUAHANG ,  "+ 
					 "  KBH.DIENGIAI AS KENHBANHANG , (   "+ 
					 "     SELECT MIN(NGAYNHAP) FROM DONHANG DH WHERE DH.KHACHHANG_FK =KH.PK_SEQ    "+ 
					 "    ) AS NGAYPHATSINH  "+ 
					 "   FROM KHACHHANG KH  "+ 
					 "  LEFT JOIN NHAPHANPHOI NPP ON NPP.PK_SEQ=KH.NPP_FK  "+ 
					 "  LEFT JOIN KHUVUC KV ON KV.PK_SEQ=NPP.KHUVUC_FK  "+ 
					 "  LEFT JOIN VUNG V ON V.PK_SEQ=KV.VUNG_FK  "+ 
					 "  LEFT JOIN LOAICUAHANG LCH ON LCH.PK_SEQ=KH.LCH_FK  "+ 
					 "  LEFT JOIN VITRICUAHANG VTCH ON VTCH.PK_SEQ=KH.VTCH_FK  "+ 
					 "  LEFT JOIN HANGCUAHANG HCH  ON HCH.PK_SEQ=KH.HCH_FK  "+ 
					 "  LEFT JOIN KENHBANHANG KBH ON KBH.PK_SEQ=KH.KBH_FK  "+ 
					 "  WHERE    "+ 
					 "   ISNULL( (   "+ 
					 "     SELECT MIN(NGAYNHAP) FROM DONHANG DH WHERE DH.KHACHHANG_FK =KH.PK_SEQ    "+ 
					 "    ) ,'')  >  convert(varchar(10),DATEADD(month,-1,'"+obj.gettungay()+"'),20)    "+
					 "    "+ condition;
 	 
		//System.out.println("Get Sql : "+sql);
 	   	ResultSet rs = db.get(sql); 	   
 	    int i = 2;
 		if(rs!=null)
 		{
 			try 
 			{
 				cells.setColumnWidth(0, 19.0f);
 				cells.setColumnWidth(1, 50.0f);
 				cells.setColumnWidth(2, 12.0f);
 				cells.setColumnWidth(3, 12.0f);
 				cells.setColumnWidth(4, 20.0f);
 				cells.setColumnWidth(5, 20.0f);
 				cells.setColumnWidth(6, 20.0f);
 				cells.setColumnWidth(7, 20.0f);
 				cells.setColumnWidth(8, 20.0f);
 				cells.setColumnWidth(9, 20.0f);
 				cells.setColumnWidth(10, 20.0f);
 				cells.setColumnWidth(6, 20.0f);
 				cells.setColumnWidth(7, 20.0f);
 				cells.setColumnWidth(8, 20.0f);
 				cells.setColumnWidth(9, 20.0f);
 				cells.setColumnWidth(10, 20.0f);
 				Cell cell = null;
 				while(rs.next())
 				{  					
					cell = cells.getCell("AA" + Integer.toString(i)); cell.setValue(rs.getString("kenhbanhang"));
					cell = cells.getCell("AB" + Integer.toString(i)); cell.setValue(rs.getString("vung"));
 					cell = cells.getCell("AC" + Integer.toString(i)); cell.setValue(rs.getString("khuvuc"));
 					cell = cells.getCell("AD" + Integer.toString(i)); cell.setValue(rs.getString("sitecode"));
					cell = cells.getCell("AE" + Integer.toString(i)); cell.setValue(rs.getString("tennpp"));
					cell = cells.getCell("AF" + Integer.toString(i)); cell.setValue(rs.getString("smartid"));
					cell = cells.getCell("AG" + Integer.toString(i)); cell.setValue(rs.getString("ten"));
					cell = cells.getCell("AH" + Integer.toString(i)); cell.setValue(rs.getString("diachi"));
					cell = cells.getCell("AI" + Integer.toString(i)); cell.setValue(rs.getString("dienthoai"));
					cell = cells.getCell("AJ" + Integer.toString(i)); cell.setValue(rs.getString("hangcuahang"));
					cell = cells.getCell("AK" + Integer.toString(i)); cell.setValue(rs.getString("loaicuahang"));
					cell = cells.getCell("AL" + Integer.toString(i)); cell.setValue(rs.getString("VITRI"));
					cell = cells.getCell("AM" + Integer.toString(i)); cell.setValue(rs.getString("NGAYPHATSINH"));
				
					i++;
 				}

			    setAn(workbook, 50);
 		}catch (Exception e){ 	
 			e.printStackTrace();
 			throw new Exception("Khong tao duoc bao cao trong thoi gian nay. Loi : "+e.toString());
 		}
 		finally{
 			if(rs != null)
 			rs.close();
 			if(db!=null){
 				db.shutDown();
 			}
 		}
 		}else{
 			throw new Exception("Khong tao duoc bao cao trong thoi gian nay...");
 		}
		 
 	}

 	private void getCellStyle(Workbook workbook, String a, Color mau, Boolean dam, int size)
	{
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	   	   
	    Cells cells = worksheet.getCells();
		Style style;
		Cell cell = cells.getCell(a); 
		style = cell.getStyle();
	    Font font1 = new Font();
	    font1.setColor(mau);
	    font1.setBold(dam);
	    font1.setSize(size);
	    style.setFont(font1);
	    
		 
	    style.setHAlignment(TextAlignmentType.LEFT);
	    cell.setStyle(style);
	}

	private String getDate() 
	{
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy: hh:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);	
	}

	private void setAn(Workbook workbook,int i)
	{
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	   	   
	    Cells cells = worksheet.getCells();
	    for(int j = 26; j <= i; j++)
	    { 
	    	cells.hideColumn(j);
	    }
		
	}

}
