package geso.traphaco.center.servlets.reports;

import geso.traphaco.center.beans.stockintransit.IStockintransit;
import geso.traphaco.center.beans.stockintransit.imp.Stockintransit;
import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.center.servlets.report.ReportAPI;
import geso.dms.distributor.util.Utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.Hashtable;

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

public class Erp_TheoDoiGiaBanKH extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
   
    public Erp_TheoDoiGiaBanKH() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IStockintransit obj = new Stockintransit();
		String querystring = request.getQueryString();
		Utility util = new Utility();
		
		obj.setuserId(util.getUserId(querystring));
		obj.setuserTen((String) session.getAttribute("userTen"));
		
		obj.setdiscount("1");
		obj.setvat("1");
		obj.init();
		
		session.setAttribute("obj", obj);
		String nextJSP = "/TraphacoHYERP/pages/Center/ErpTheoDoiGiaKH.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IStockintransit obj = new Stockintransit();
		obj.setdiscount("1");
		obj.setvat("1");
		
		OutputStream out = response.getOutputStream();
		
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		Utility util = new Utility();
		
		obj.settungay(util.antiSQLInspection(request.getParameter("Sdays")));
		obj.setdenngay(util.antiSQLInspection(request.getParameter("Edays")));
		
		obj.setuserId(userId!=null? userId:"");
		obj.setuserTen(userTen!=null? userTen:"");
		obj.setkenhId(util.antiSQLInspection(request.getParameter("kenhId"))!=null?
				util.antiSQLInspection(request.getParameter("kenhId")):"");
		
		obj.setvungId(util.antiSQLInspection(request.getParameter("vungId"))!=null?
				util.antiSQLInspection(request.getParameter("vungId")):"");
			
		obj.setkhuvucId(util.antiSQLInspection(request.getParameter("khuvucId"))!=null?
				util.antiSQLInspection(request.getParameter("khuvucId")):"");
		
		obj.setgsbhId(util.antiSQLInspection(request.getParameter("gsbhs"))!=null?
				util.antiSQLInspection(request.getParameter("gsbhs")):"");
		
		obj.setnppId(util.antiSQLInspection(request.getParameter("nppId"))!=null?
				util.antiSQLInspection(request.getParameter("nppId")):"");
		
		obj.setdvkdId(util.antiSQLInspection(request.getParameter("dvkdId"))!= null?
				util.antiSQLInspection(request.getParameter("dvkdId")):"");
		
		obj.setnhanhangId(util.antiSQLInspection(request.getParameter("nhanhangId"))!= null?
			util.antiSQLInspection(request.getParameter("nhanhangId")):"");
		obj.setchungloaiId(util.antiSQLInspection(request.getParameter("chungloaiId"))!= null?
				util.antiSQLInspection(request.getParameter("chungloaiId")):"");
		
		
		String tuthang=request.getParameter("tuthang").length()< 2 ? ("0"+request.getParameter("tuthang")) :request.getParameter("tuthang") ;
		String toithang=request.getParameter("denthang").length()< 2 ? ("0"+request.getParameter("denthang")) :request.getParameter("denthang") ;
		obj.setFromMonth(tuthang);
		
		obj.setToMonth(toithang);
			obj.setToYear(request.getParameter("dennam"));
			obj.setFromYear(request.getParameter("tunam"));
		String type= request.getParameter("xemtheo");
		
		if(type.equals("1")){
			obj.settungay(obj.getFromYear()+"-"+ (obj.getFromMonth().length() >1?obj.getFromMonth():"0"+obj.getFromMonth()) +"-01");
			obj.setdenngay(obj.getToYear()+"-"+ (obj.getToMonth().length() >1?obj.getToMonth():"0"+obj.getToMonth()) +"-31");
		}
		obj.settype(type);
		
		String vat = util.antiSQLInspection(request.getParameter("vats"));
		if (vat.equals("1"))
			obj.setvat("1.1");
		else
			obj.setvat("1");
		
		String dsc = util.antiSQLInspection(request.getParameter("discount"));
		if (dsc.equals("1"))
			obj.setdiscount("1");
		else
			obj.setdiscount("0");
		
		String promotion = request.getParameter("promotion");
		if(promotion == null)
			promotion = "0";
		obj.setpromotion(promotion);
		
		
	
		String trangthai = request.getParameter("trangthai");
		if(trangthai == null)
			trangthai = "";
	
	
		geso.traphaco.center.util.Utility utilcenter = new geso.traphaco.center.util.Utility();
		
		
		String sql ="";
		
		sql =" and kh.pk_seq in " + utilcenter.quyen_npp(obj.getuserId()) + " and kenh.pk_seq in  " + utilcenter.quyen_kenh(obj.getuserId()) + " and sp.pk_seq in " + utilcenter.quyen_sanpham(obj.getuserId()); 

	 
		if (obj.getnppId().length() > 0)
			sql = sql + " and KH.pk_seq ='" + obj.getnppId() + "'";
		if (obj.getkenhId().length() > 0)
			sql = sql + " and kenh.pk_seq ='" + obj.getkenhId() + "'";
		if (obj.getvungId().length() > 0)
			sql = sql + " and v.pk_seq ='" + obj.getvungId() + "'";
		if (obj.getkhuvucId().length() > 0)
			sql = sql + " and kv.pk_seq ='" + obj.getkhuvucId() + "'";
		if (obj.getdvkdId().length() > 0)
			sql = sql + " and dvkd.PK_SEQ ='" + obj.getdvkdId() + "'";
		if (obj.getnhanhangId().length() > 0)
			sql = sql + " and nh.pk_seq ='" + obj.getnhanhangId() + "'";
		if (obj.getchungloaiId().length() > 0)
			sql = sql + " and cl.pk_seq ='" + obj.getchungloaiId()	+ "'";
 
		
		System.out.println("SQL la: " + sql + "\n");
		

		
		String action = (String) util.antiSQLInspection(request.getParameter("action"));
		String nextJSP = "/TraphacoHYERP/pages/Center/ErpTheoDoiGiaKH.jsp";
		
		//System.out.println("Action la: " + action);
		if (action.equals("Taomoi")) 
		{			
			response.setContentType("application/xlsm");
			response.setHeader("Content-Disposition", "attachment; filename=ThoiDoiGiaBanKhachHang.xlsm");
			
			String query = setQuery(obj, sql); 

	        try 
	        {
				if(!CreatePivotTable(out, response, request, obj, query))
				{
					response.setContentType("text/html");
				    PrintWriter writer = new PrintWriter(out);
				    writer.print("Xin loi khong co bao cao trong thoi gian nay");
				    writer.close();
				}
			} 
	        catch (Exception e) 
	         { 
	        	e.printStackTrace();
	        	obj.init();
				obj.setMsg("Khong the tao bao cao " + e.getMessage());
				
				session.setAttribute("obj", obj);
				response.sendRedirect(nextJSP);
				return;
			}
		}else{
		
				obj.init();
				session.setAttribute("obj", obj);
				response.sendRedirect(nextJSP);
				return;
		}
	}
	
	public String setQuery(IStockintransit obj, String sql)
	{		
		String query="  SELECT DISTINCT  KH.PK_SEQ AS KHACHHANGID ,KH.MA AS MAKH,KH.TEN AS TENKH 	,   "+ 
					 "  SP.MA AS SPMA, SP.TEN AS SPTEN,SP.QUYCACH,SP.DINHLUONG,   "+ 
					 "  HD_SP.SANPHAM_FK,HD_SP.DONVITINH,HD_SP.DONGIA,HD_SP.NGAY  "+ 
					 "  FROM (  "+ 
					 "  SELECT HD.KHACHHANG_FK ,HD_SP.SANPHAM_FK, HD_SP.DONGIA,HD_SP.DONVITINH ,MIN(HD.NGAYXUATHD) AS NGAY  FROM ERP_HOADON HD 	    "+ 
					 "  INNER JOIN ERP_HOADON_SP HD_SP ON HD.PK_SEQ = HD_SP.HOADON_FK    "+ 
					 "  WHERE HD.TRANGTHAI = '1'  " ;
					 if(obj.gettungay().length() > 0 ){
						   query += "	AND HD.NGAYXUATHD >='"+obj.gettungay()+"' " ;
					   }
					   if(obj.getdenngay().length() > 0 ){
						   query +=   "	AND   HD.NGAYXUATHD <='"+obj.getdenngay()+"' " ;
					   }
					query+=
					 "  GROUP BY HD.KHACHHANG_FK ,HD_SP.SANPHAM_FK, HD_SP.DONGIA,HD_SP.DONVITINH   "+ 
					 "  ) AS  HD_SP   "+ 
					 "  INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = HD_SP.SANPHAM_FK    "+ 
					 "  INNER JOIN ERP_KHACHHANG KH ON KH.PK_SEQ =HD_SP.KHACHHANG_FK    " +
					 " LEFT JOIN NHANHANG NH ON NH.PK_SEQ=SP.NHANHANG_FK   " +
					 "	LEFT JOIN CHUNGLOAI CL ON CL.PK_SEQ=SP.CHUNGLOAI_FK     " + 	
         		     "		LEFT JOIN DONVIKINHDOANH DVKD ON DVKD.PK_SEQ=SP.DVKD_FK    " +     	
         		     "		LEFT JOIN KHUVUC KV ON KV.PK_SEQ=KH.KHUVUC_FK    " +
         		     "		LEFT JOIN VUNG V ON KV.VUNG_FK=V.PK_SEQ			" +
         		     "		 LEFT JOIN KENHBANHANG KENH ON KENH.PK_SEQ = KH.kenhbanhang_fk   	" +
         		     " where 1 = 1 "+ sql +
					 "  ORDER BY KH.MA,KH.TEN ,SP.MA, SP.TEN,SP.QUYCACH,SP.DINHLUONG, HD_SP.SANPHAM_FK,HD_SP.DONVITINH ";
		 System.out.println("Query: "+query);
		 
	    return query;   
	}
	
	private boolean CreatePivotTable(OutputStream out,HttpServletResponse response,HttpServletRequest request,IStockintransit obj, String query) throws Exception 
	{
		boolean isFillData = false;
		FileInputStream fstream = null;
		Workbook workbook = new Workbook();
		
		
			fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\ERP_THEODOIGIAKH.xlsm");
		
				
		workbook.open(fstream);
		workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);

		CreateHeader(workbook,obj);
		isFillData = FillData(workbook, obj, query);
   
		if (!isFillData)
		{
			if(fstream != null)
				fstream.close();
			return false;
		}
		
		workbook.save(out);
		fstream.close();
		return true;	
	}
	
	private void CreateHeader(Workbook workbook, IStockintransit obj)throws Exception
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
	    
	    String tieude = "BÁO CÁO THEO DÕI GIÁ BÁN THEO KHÁCH HÀNG";
	    
	    ReportAPI.getCellStyle(cell,Color.RED, true, 14, tieude);
	            
	  
		
	    cell = cells.getCell("A4"); 
	   
	    	ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Từ ngày : " + obj.gettungay() + "" );
	  
	    cells.setRowHeight(3, 18.0f);
	    cell = cells.getCell("B4"); 
	   
	    	ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Đến ngày : " + obj.getdenngay() + "" );
	   
	    cells.setRowHeight(4, 18.0f);
	    cell = cells.getCell("A5");
	    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Ngày báo cáo: " + ReportAPI.NOW("yyyy-MM-dd"));
	    
	    cells.setRowHeight(5, 18.0f);
	    cell = cells.getCell("A6");
	    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Được tạo bởi:  " + obj.getuserTen());
	    		
		cell = cells.getCell("A10");		cell.setValue("MÃ KHÁCH HÀNG");			ReportAPI.setCellHeader(cell);//1
	    cell = cells.getCell("B10");		cell.setValue("TÊN KHÁCH HÀNG");			ReportAPI.setCellHeader(cell);///2
		cell = cells.getCell("C10");		cell.setValue("MÃ SẢN PHẨM");				ReportAPI.setCellHeader(cell);//5
		cell = cells.getCell("D10");		cell.setValue("TÊN SẢN PHẨM");			ReportAPI.setCellHeader(cell);//6
		cell = cells.getCell("E10");		cell.setValue("QUY CÁCH");					ReportAPI.setCellHeader(cell);//8
		cell = cells.getCell("F10");		cell.setValue("ĐƠN VỊ");				ReportAPI.setCellHeader(cell);//9
		cell = cells.getCell("G10");		cell.setValue("GÍA 1");				ReportAPI.setCellHeader(cell);//10
		cell = cells.getCell("H10");		cell.setValue("NGÀY THAY ĐỔI");				ReportAPI.setCellHeader(cell);//10
		cell = cells.getCell("I10");		cell.setValue("GIÁ 2");			ReportAPI.setCellHeader(cell);//15
		cell = cells.getCell("J10");		cell.setValue("NGÀY THAY ĐỔI");				ReportAPI.setCellHeader(cell);//10
		cell = cells.getCell("K10");		cell.setValue("GIÁ 3");			ReportAPI.setCellHeader(cell);//16
		cell = cells.getCell("L10");		cell.setValue("NGÀY THAY ĐỔI");				ReportAPI.setCellHeader(cell);//10
		cell = cells.getCell("M10");		cell.setValue("GIÁ 4");			ReportAPI.setCellHeader(cell);//16
		cell = cells.getCell("N10");		cell.setValue("NGÀY THAY ĐỔI");				ReportAPI.setCellHeader(cell);//10
		cell = cells.getCell("O10");		cell.setValue("GIÁ 5");			ReportAPI.setCellHeader(cell);//16
		cell = cells.getCell("P10");		cell.setValue("NGÀY THAY ĐỔI");				ReportAPI.setCellHeader(cell);//10
		cell = cells.getCell("Q10");		cell.setValue("GIÁ 6");			ReportAPI.setCellHeader(cell);//16
		cell = cells.getCell("R10");		cell.setValue("NGÀY THAY ĐỔI");				ReportAPI.setCellHeader(cell);//10
	
		
		
		
	}
	private Hashtable< Integer, String > htbValueCell ()
	{
		Hashtable<Integer, String> htb=new Hashtable<Integer, String>();
		htb.put(1,"A");htb.put(2,"B");htb.put(3,"C");htb.put(4,"D");htb.put(5,"E");
		htb.put(6,"F");htb.put(7,"G");htb.put(8,"H");htb.put(9,"I");htb.put(10,"J");
		htb.put(11,"K");htb.put(12,"L");htb.put(13,"M");htb.put(14,"N");htb.put(15,"O");
		htb.put(16,"P");htb.put(17,"Q");htb.put(18,"R");htb.put(19,"S");htb.put(20,"T");
		htb.put(21,"U");htb.put(22,"V");
		return htb; 
	}
	private boolean FillData(Workbook workbook, IStockintransit obj, String query) throws Exception
	{
		dbutils db = new dbutils();
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		Hashtable<Integer, String> htb=this.htbValueCell();
		Cells cells = worksheet.getCells();	
		
		for(int i = 0;i < 7; ++i)
		{
	    	cells.setColumnWidth(i, 15.0f);
	    	if(i == 4)
	    		cells.setColumnWidth(i, 30.0f);
	    }	
		
		ResultSet rs = db.get(query);
		int index = 11;
		String chuoibk="";
		String chuoi;
		
			try 
			{
				int sott=0;
				Cell cell = null;
				while (rs.next())
				{					
					chuoi=rs.getString("khachhangid").trim()+rs.getString("sanpham_fk").trim()+rs.getString("donvitinh");
					if(!chuoibk.equals(chuoi)){
						if(index>11){
							for(int i=sott;i<19;i++){
								cell = cells.getCell(htb.get(i) + String.valueOf(index-1));		cell.setValue("");	// 5
								ReportAPI.setCellHasBorder(cell);
							}
						}
						
						cell = cells.getCell("A" + String.valueOf(index));		cell.setValue(rs.getString("makh"));	//Kenh ban hang  0	
						ReportAPI.setCellHasBorder(cell);
						cell = cells.getCell("B" + String.valueOf(index));		cell.setValue(rs.getString("tenkh"));	//Kenh ban hang  1	
						ReportAPI.setCellHasBorder(cell);
						cell = cells.getCell("C" + String.valueOf(index));		cell.setValue(rs.getString("spma"));	//   4
						ReportAPI.setCellHasBorder(cell);
						cell = cells.getCell("D" + String.valueOf(index));		cell.setValue(rs.getString("spten"));	// 5
						ReportAPI.setCellHasBorder(cell);
						cell = cells.getCell("E" + String.valueOf(index));		cell.setValue(rs.getString("quycach"));	// 5
						ReportAPI.setCellHasBorder(cell);
						cell = cells.getCell("F" + String.valueOf(index));		cell.setValue(rs.getString("donvitinh"));	// 5
						ReportAPI.setCellHasBorder(cell);
						chuoibk=chuoi;
					 	index++;	
					  sott =7;
					}
					
					cell = cells.getCell(htb.get(sott) + String.valueOf(index-1));		cell.setValue(rs.getDouble("dongia"));	// 5
					ReportAPI.setCellHasBorder(cell);
					sott++;
					cell = cells.getCell(htb.get(sott) + String.valueOf(index-1));		cell.setValue(rs.getString("NGAY"));	// 5
					ReportAPI.setCellHasBorder(cell);
					sott++;
				}
				if(sott >0){
					for(int i=sott;i<19;i++){
						
						cell = cells.getCell(htb.get(i) + String.valueOf(index-1));		cell.setValue("");	// 5
						ReportAPI.setCellHasBorder(cell);
					}
				}
				if (rs != null)
					rs.close();
				
				if(db != null)
					db.shutDown();
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				throw new Exception(ex.getMessage());
			}
		
		return true;
	}	
	

}
