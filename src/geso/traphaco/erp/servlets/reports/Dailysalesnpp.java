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


import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Font;
import com.aspose.cells.PivotFieldType;
import com.aspose.cells.PivotTable;
import com.aspose.cells.PivotTableAutoFormatType;
import com.aspose.cells.PivotTables;
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;

import com.aspose.cells.Worksheets;



public class Dailysalesnpp extends HttpServlet {	
    public Dailysalesnpp() {
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
		obj.setnppId(util.getIdNhapp(obj.getuserId()));
		obj.setvat("1");
		obj.init();	
		session.setAttribute("obj", obj);		
		session.setAttribute("userId", obj.getuserId());
		String nextJSP = "/TraphacoHYERP/pages/Distributor/DailySalesReportDistributor.jsp";
		response.sendRedirect(nextJSP);
				
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IStockintransit obj = new Stockintransit();
		Utility util = new Utility();
		
		obj.setuserId((String) session.getAttribute("userId")!=null? (String) session.getAttribute("userId"):"");
		obj.setnppId(util.antiSQLInspection(request.getParameter("nppId"))!=null? "": util.antiSQLInspection(request.getParameter("nppId")));	 
		
	    Utility Ult = new Utility();		
		obj.setnppId(Ult.getIdNhapp(obj.getuserId()));
	    
        obj.setuserTen((String) session.getAttribute("userTen")!=null? 
        			(String) session.getAttribute("userTen"):""); 
        
	   
	   	obj.setkenhId(util.antiSQLInspection(request.getParameter("kenhId"))!=null? util.antiSQLInspection(request.getParameter("kenhId")):"");
	   	 
	   	obj.setDdkd(util.antiSQLInspection(request.getParameter("ddkdId"))!=null? 	util.antiSQLInspection(request.getParameter("ddkdId")):"");
	   	 
	   	obj.setdvkdId(util.antiSQLInspection(request.getParameter("dvkdId"))!=null? util.antiSQLInspection(request.getParameter("dvkdId")):"");	   	 
	   	 
	   	obj.settungay(util.antiSQLInspection(request.getParameter("Sdays")));
	   	
	   	obj.setdenngay(util.antiSQLInspection(request.getParameter("Edays")));	   	 
	   
		obj.setnhanhangId(util.antiSQLInspection(request.getParameter("nhanhangId"))!=null? util.antiSQLInspection(request.getParameter("nhanhangId")):"");
		
		obj.setchungloaiId(util.antiSQLInspection(request.getParameter("chungloaiId"))!=null? util.antiSQLInspection(request.getParameter("chungloaiId")):"");		
		
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
	   	 
	   	 String []fieldsHien = request.getParameterValues("fieldsHien");
		 obj.setFieldShow(fieldsHien!=null? fieldsHien:null);
		 
		 String sql = "";
		 
		 if(obj.getkenhId().length()>0){
			 sql +=" and kbh.pk_seq = '" + obj.getkenhId() + "' ";
		 }
		 if(obj.getdvkdId().length()>0){
			 sql +=" and dvkd.pk_seq = '" +  obj.getdvkdId() +"'";
		 }
		 
		 if(obj.getnhanhangId().length()>0){
			 sql +=" and nh.pk_seq='" +  obj.getnhanhangId() +"'";
		 }
		 
		 if(obj.getchungloaiId().length()>0){
			 sql +=" and cl.pk_seq='" +  obj.getchungloaiId() +"'";
		 }
		 
		 if(obj.getDdkd().length()>0){
			 sql +=" and ddkd.pk_seq='" + obj.getDdkd() + "'";
		 }
		/* 
	 	if (obj.getkenhId().length() > 0)
			sql = sql + " and chanelId ='" + obj.getkenhId() + "'";
		if (obj.getvungId().length() > 0)
			sql = sql + " and regionId ='" + obj.getvungId() + "'";
		if (obj.getkhuvucId().length() > 0)
			sql = sql + " and areaId ='" + obj.getkhuvucId() + "'";			
		if (obj.getnppId().length() > 0)
			sql = sql + " and nppId ='" + obj.getnppId() + "'";
		if (obj.getnhanhangId().length() > 0)
			sql = sql + " and brandId ='" + obj.getnhanhangId() + "'";
		if (obj.getchungloaiId().length() > 0)
			sql = sql + " and categoryId ='" + obj.getchungloaiId()	+ "'";
		*/
		 String nextJSP = "/TraphacoHYERP/pages/Distributor/DailySalesReportDistributor.jsp";		 
		 String action=request.getParameter("action");		 
		 
		 if(action.equals("create"))
		 {
			try{						
				response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "attachment; filename=DoanhSoNPP.xlsm");
								
		        OutputStream out = response.getOutputStream();
		        String query =  setQuery(sql,obj);

		        if(!CreatePivotTable(out,obj,query)){
		        	response.setContentType("text/html");
		            PrintWriter writer = new PrintWriter(out);
		            writer.print("Khong co du lieu trong thoi gian nay");
		            writer.close();
		        }

			}catch(Exception ex){
				obj.setMsg(ex.getMessage());				
				response.sendRedirect(nextJSP);	
			}
		 }
		 else{
			response.sendRedirect(nextJSP);			
		 }
		 
		obj.init();
		session.setAttribute("obj", obj);
		session.setAttribute("userId", obj.getuserId());
	}

	private String setQuery(String sql,IStockintransit obj)
	{
		String query=	"SELECT	" +
						"V.TEN AS REGION, " + 
						"KV.TEN AS AREA, " +
						"NPP.TEN AS DISTRIBUTOR, " +  
						"DVKD.DIENGIAI AS DVKD, " +
						"NPP.SITECODE AS DISTCODE, " + 	 
						"NH.TEN AS BRAND, " +
						"CL.TEN AS CATEGORY, " +
						"SP.MA + '_' + SP.TEN AS SKU, " +   
						"SP.MA AS SKUCODE, " +
						"DDKD.TEN AS SALESREP, " + 
						"NPP.SITECODE + '_' + KH.TEN + '( ' + KH.DIACHI +  ' )' AS CUSTOMER, " +
						"KH.PK_SEQ AS CUSTOMERCODE, " +
						"VTCH.VITRI AS OUTLETPOSITION, " + 
						"ISNULL(M.DIENGIAI,    'Chua Xac Dinh') AS OUTLETTYPE, " + 
						"KBH.DIENGIAI AS CHANEL, " +
						"DH.NGAYNHAP AS OFFDATE, " +
						"DH.SOLUONG AS PIECE,	" ; 

			
		//	" 	 dh.giamua * (dh.soluong) * 1.1  as amount" +
			 if(obj.getdiscount().equals("1"))          
				 query +=	" (DH.GIAMUA * DH.SOLUONG* "+ obj.getvat()+") AS AMOUNT ,"; 
			 else{
				 query +=	" ((DH.GIAMUA * DH.SOLUONG )- DH.CHIETKHAU )  * "+ obj.getvat()+" AS AMOUNT ,";
			 }
			 
		query = query +	"ISNULL(GSBH.TEN, 'Chua xac dinh') AS SALESUPER, " +  	 
						"CASE WHEN ROUND((CAST((QC.SOLUONG2 * DH.SOLUONG) AS FLOAT) / CAST(QC.SOLUONG1 AS FLOAT)),3) IS NULL THEN 0 " + 
						"ELSE ROUND((CAST((QC.SOLUONG2 * DH.SOLUONG) AS FLOAT) / CAST( QC.SOLUONG1 AS FLOAT)), 3) " +
						"END AS QUANTITY, " +
						"ISNULL(QH.TEN, 'Chua xac dinh') AS TOWN, " +   
						"ISNULL(TT.TEN, 'Chua xac dinh') AS PROVINCE, " + 
						"HCH.DIENGIAI AS OUTLETCLASS, " +
						"NKH.DIENGIAI AS NHOMKHACHHANG, " +
						"NSP.DIENGIAI AS NHOMSANPHAM   " +
						"FROM	" +   
						"(  " +
							"SELECT  DH.NGAYNHAP, DH.DDKD_FK, DH.KHACHHANG_FK, " + 
									"DH.NPP_FK, DH.GSBH_FK, DH.KHO_FK, DH.KBH_FK, " +
									"ISNULL(DH_SP.SANPHAM_FK, DH_SP1.SANPHAM_FK) AS SANPHAM_FK, " +  	
									"ISNULL(DH_SP.GIAMUA, DH_SP1.GIAMUA) AS GIAMUA , " +
									"(-1)*ISNULL(DH_SP.SOLUONG, DH_SP1.SOLUONG) AS SOLUONG, " + 
									"(-1)*ISNULL(DH_SP1.CHIETKHAU, 0) AS CHIETKHAU  " +

							"FROM  DONHANGTRAVE DH " +  	
							"LEFT OUTER JOIN  DONHANGTRAVE_SANPHAM DH_SP ON DH_SP.DONHANGTRAVE_FK = DH.PK_SEQ " +  	
							"LEFT OUTER JOIN  DONHANG_SANPHAM DH_SP1 ON DH.DONHANG_FK = DH_SP1.DONHANG_FK  " +
							"WHERE DH.TRANGTHAI = 3 AND DH.NPP_FK ='"+ obj.getnppId() +"' " +
							"AND DH.NGAYNHAP >='" + obj.gettungay() + "' AND DH.NGAYNHAP <= '" + obj.getdenngay() + "' " +  	
		
							"UNION ALL " +
		
							"SELECT  DH.NGAYNHAP, DH.DDKD_FK, DH.KHACHHANG_FK, DH.NPP_FK, DH.GSBH_FK, DH.KHO_FK, DH.KBH_FK, " +
									"DH_SP.SANPHAM_FK, DH_SP.GIAMUA, DH_SP.SOLUONG, DH_SP.CHIETKHAU  " +
							"FROM DONHANG DH  " +
							"INNER JOIN DONHANG_SANPHAM  DH_SP ON DH.PK_SEQ = DH_SP.DONHANG_FK " +  	
							"WHERE DH.TRANGTHAI = 1 AND DH.NPP_FK = '" + obj.getnppId() + "' AND DH.NGAYNHAP >='" + obj.gettungay() + "' " +
								   "AND DH.NGAYNHAP <= '" + obj.getdenngay() + "' " +   
						")DH " +	  
						"INNER JOIN SANPHAM SP ON DH.SANPHAM_FK = SP.PK_SEQ " +   
						"INNER JOIN NHAPHANPHOI NPP ON DH.NPP_FK = NPP.PK_SEQ " +  
						"LEFT JOIN KHUVUC KV ON NPP.KHUVUC_FK = KV.PK_SEQ " +  
						"LEFT JOIN VUNG V ON KV.VUNG_FK = V.PK_SEQ " +   
						"LEFT JOIN NHANHANG NH ON SP.NHANHANG_FK = NH.PK_SEQ " +  
						"LEFT JOIN CHUNGLOAI CL ON SP.CHUNGLOAI_FK = CL.PK_SEQ " +     
						"LEFT JOIN DAIDIENKINHDOANH DDKD ON DH.DDKD_FK = DDKD.PK_SEQ " +  
						"LEFT JOIN KHACHHANG KH ON DH.KHACHHANG_FK = KH.PK_SEQ " +   
						"LEFT JOIN VITRICUAHANG VTCH ON KH.VTCH_FK = VTCH.PK_SEQ " +  
						"LEFT JOIN KENHBANHANG KBH ON KH.KBH_FK = KBH.PK_SEQ " +    
						"LEFT JOIN LOAICUAHANG M ON KH.LCH_FK = M.PK_SEQ " +
						"LEFT JOIN GIAMSATBANHANG GSBH ON DH.GSBH_FK = GSBH.PK_SEQ " +    
						"INNER JOIN DONVIKINHDOANH DVKD ON DVKD.PK_SEQ = SP.DVKD_FK " +  
						"LEFT JOIN QUYCACH QC ON QC.SANPHAM_FK = SP.PK_SEQ " +   
						"LEFT JOIN QUANHUYEN QH ON KH.QUANHUYEN_FK = QH.PK_SEQ " +  
						"LEFT JOIN TINHTHANH TT ON KH.TINHTHANH_FK = TT.PK_SEQ " +     
						"LEFT JOIN HANGCUAHANG HCH ON KH.HCH_FK = HCH.PK_SEQ " +  
						"LEFT JOIN NHOMKHACHHANG_KHACHHANG NKHKH ON NKHKH.KH_FK = KH.PK_SEQ " +   
						"LEFT JOIN NHOMKHACHHANG NKH ON NKH.PK_SEQ = NKHKH.NKH_FK " + 
						"LEFT JOIN " + 
						"( " +
								"SELECT SP_FK, MAX(NSP_FK) AS NHOMSP " + 
								"FROM NHOMSANPHAM_SANPHAM  GROUP BY SP_FK " +
						") NSPSP ON NSPSP.SP_FK = SP.PK_SEQ " + 
						"LEFT JOIN NHOMSANPHAM NSP ON NSP.PK_SEQ = NSPSP.NHOMSP " +  
						"WHERE DH.NPP_FK ='"+ obj.getnppId() +" ' AND DH.NGAYNHAP >='" + obj.gettungay() + "' " + 
							   "AND DH.NGAYNHAP <= '"+ obj.getdenngay() +"' " +
							   "ORDER BY DVKD.DIENGIAI, DDKD.TEN, DH.NGAYNHAP " ;
		
	 		 System.out.print("QUERY: NPP " + query);
		 return query;

		/*
		String Customer = "";
		if(obj.getdiscount().equals("1")) //co ck
		{
			if(obj.getvat().equals("1")) //khong VAT
				Customer = "select *, amount_CK_YES_VAT_NO as amount from doanhsobanhang where offdate >= '" + obj.gettungay() + "' and offdate <= '" + obj.getdenngay() + "' " + sql;
			else
				Customer = "select *, amount_CK_YES_VAT_YES as amount from doanhsobanhang where offdate >= '" + obj.gettungay() + "' and offdate <= '" + obj.getdenngay() + "' " + sql;
		}
		else
		{
			//Distributor+= "  sum(a.giamua * a.soluong *  " + obj.getvat() +" - a.CHIETKHAU) as amount";
			if(obj.getvat().equals("1")) //khong VAT
				Customer = "select *, amount_CK_NO_VAT_NO as amount from doanhsobanhang where offdate >= '" + obj.gettungay() + "' and offdate <= '" + obj.getdenngay() + "' " + sql;
			else
				Customer = "select *, amount_CK_NO_VAT_YES as amount from doanhsobanhang where offdate >= '" + obj.gettungay() + "' and offdate <= '" + obj.getdenngay() + "' " + sql;
		}
		return Customer;*/
	}
	private boolean CreatePivotTable(OutputStream out,IStockintransit obj,String query) throws Exception
    {    
		 FileInputStream fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\DoanhSoNPP.xlsm");		

		 //FileInputStream fstream = new FileInputStream("C:\\Program Files\\Apache Software Foundation\\Tomcat 7.0\\webapps\\SalesUp\\WebContent\\pages\\Templates\\DoanhSoTT.xlsm");
		 Workbook workbook = new Workbook();
		 workbook.open(fstream);
		 workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
	     	    
	     CreateStaticHeader(workbook,obj);	    
	     boolean isFillData = CreateStaticData(workbook,obj,query);
	     
	     if(!isFillData)
	    	 return false;
	     
	     workbook.save(out);
	     fstream.close();
	     
	     return true;
   }
	
	private void CreateStaticHeader(Workbook workbook, IStockintransit obj) throws Exception
	{
		
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
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
	    
	    ReportAPI.getCellStyle(cell,Color.RED, true, 14, "BÁO CÁO DOANH SỐ BÁN HÀNG THEO NGÀY");
	            
	    String message = "";
		if(obj.getdiscount().equals("0")){
			message += "Không chiết khấu";

			if(obj.getvat().equals("1")){
				message += ", không VAT";
			}else{
				message += ", có VAT";
			}			
		}else{
			message += "Có chiết khấu";
			if(obj.getvat().equals("1")){
				message += ", không VAT";
			}else{
				message +=", có VAT";
			}
		}
		
		cells.setRowHeight(2, 18.0f);
		cell = cells.getCell("A2");
		ReportAPI.getCellStyle(cell, Color.RED, true, 9, message);   

	    cells.setRowHeight(3, 18.0f);
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
	    		
	   
		cell = cells.getCell("DA1"); cell.setValue("Kenh Ban Hang"); 
		cell = cells.getCell("DB1"); cell.setValue("Don Vi Kinh Doanh");
		cell = cells.getCell("DC1"); cell.setValue("Dai Dien Kinh Doanh");
		cell = cells.getCell("DD1"); cell.setValue("Nhan Hang");
		cell = cells.getCell("DE1"); cell.setValue("Chung Loai");	  
		cell = cells.getCell("DF1"); cell.setValue("Ten San Pham");
		cell = cells.getCell("DG1"); cell.setValue("Ma San Pham");	  
		cell = cells.getCell("DH1"); cell.setValue("Khach Hang");
		cell = cells.getCell("DI1"); cell.setValue("Hang Cua Hang");
		cell = cells.getCell("DJ1"); cell.setValue("Loai Cua Hang");
		cell = cells.getCell("DK1"); cell.setValue("Ngay");
		cell = cells.getCell("DL1"); cell.setValue("Nhom San Pham");
		cell = cells.getCell("DM1"); cell.setValue("Nhom Khach Hang");
		cell = cells.getCell("DN1"); cell.setValue("Ma Khach Hang");
		cell = cells.getCell("DO1"); cell.setValue("Tong Tien");
		cell = cells.getCell("DP1"); cell.setValue("So Luong Le");
		cell = cells.getCell("DQ1"); cell.setValue("So Luong Thung");
	   
	}
	private boolean CreateStaticData(Workbook workbook,IStockintransit obj,String query) throws Exception
	{
		
		dbutils db = new dbutils();
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	    Cells cells = worksheet.getCells();
	    ResultSet rs = db.get(query);
		int i = 2;
		if(rs!= null)
		{
			try 
			{
				cells.setColumnWidth(0, 25.0f);
				cells.setColumnWidth(1, 15.0f);
				cells.setColumnWidth(2, 15.0f);
				cells.setColumnWidth(3, 15.0f);
				cells.setColumnWidth(4, 15.0f);
				cells.setColumnWidth(5, 15.0f);
				cells.setColumnWidth(6, 15.0f);	
				cells.setColumnWidth(7, 15.0f);
				cells.setColumnWidth(8, 15.0f);
				cells.setColumnWidth(9, 15.0f);
				cells.setColumnWidth(10, 15.0f);
				cells.setColumnWidth(11, 15.0f);
				cells.setColumnWidth(12, 15.0f);
				cells.setColumnWidth(13, 15.0f);
				cells.setColumnWidth(14, 15.0f);
				cells.setColumnWidth(15, 15.0f);
				cells.setColumnWidth(16, 15.0f);		
				
				for(int j = 12; j <= 22; j++)
					cells.setRowHeight(j, 14.0f);				
				Cell cell = null;
				while(rs.next())
				{
					String Channel = rs.getString("chanel");
					String Dvkd = rs.getString("dvkd");
					String SalesRep = rs.getString("SalesRep");
					String Brands = rs.getString("brand");
					String Category = rs.getString("category");
					
					long Amount = Math.round(Float.parseFloat(rs.getString("Amount")));

					String Sku = rs.getString("SKU");
					String SkuCode = rs.getString("SKUcode");

					long Piece = Math.round(Float.parseFloat(rs.getString("piece")));
					
					String Customer = rs.getString("customer");
					String CustomerCode = rs.getString("customercode");
					String OutletClass = rs.getString("OutletClass");
					String OutletType = rs.getString("outlettype");
					String OffDate = rs.getString("offdate");
					
					long Quantity = Math.round(Float.parseFloat(rs.getString("quantity")));
					
					String GroupSku = rs.getString("nhomsanpham");
					String GroupCustomer = rs.getString("nhomkhachhang");				

					cell = cells.getCell("DA" + Integer.toString(i)); cell.setValue(Channel);	//0
					cell = cells.getCell("DB" + Integer.toString(i)); cell.setValue(Dvkd);		//1
					cell = cells.getCell("DC" + Integer.toString(i)); cell.setValue(SalesRep);	//2
					cell = cells.getCell("DD" + Integer.toString(i)); cell.setValue(Brands);	//3
					cell = cells.getCell("DE" + Integer.toString(i)); cell.setValue(Category);	//4
					cell = cells.getCell("DF" + Integer.toString(i)); cell.setValue(Sku);		//5
					cell = cells.getCell("DG" + Integer.toString(i)); cell.setValue(SkuCode);	//6
					cell = cells.getCell("DH" + Integer.toString(i)); cell.setValue(Customer);	//7
					cell = cells.getCell("DI" + Integer.toString(i)); cell.setValue(OutletClass);	//8
					cell = cells.getCell("DJ" + Integer.toString(i)); cell.setValue(OutletType);	//9
					cell = cells.getCell("DK" + Integer.toString(i)); cell.setValue(OffDate);		//10
					cell = cells.getCell("DL" + Integer.toString(i)); cell.setValue(GroupSku);		//11
					cell = cells.getCell("DM" + Integer.toString(i)); cell.setValue(GroupCustomer);	//12
					cell = cells.getCell("DN" + Integer.toString(i)); cell.setValue(CustomerCode);	//13
					cell = cells.getCell("DO" + Integer.toString(i)); cell.setValue(Amount);	//14
					cell = cells.getCell("DP" + Integer.toString(i)); cell.setValue(Piece); //15
					cell = cells.getCell("DQ" + Integer.toString(i)); cell.setValue(Quantity); //16
					i++;
				}
				if (rs != null)
					rs.close();	
				if(db!=null){
					db.shutDown();
				}
				if(i==2){
					throw new Exception("Xin loi,khong co bao cao voi dieu kien da chon....!!!");
				}
		
/*				ReportAPI.setHidden(workbook, 50);
				PivotTables pivotTables = worksheet.getPivotTables();			
				String pos = Integer.toString(i - 1); 
				int index = pivotTables.add("=DA1:DQ" + pos, "A8","DoanhSoNPP");				
				PivotTable pivotTable = pivotTables.get(index);

				pivotTable.setRowGrand(true);
				pivotTable.setColumnGrand(true);
				pivotTable.setAutoFormat(true);	  
				pivotTable.setAutoFormatType(PivotTableAutoFormatType.TABLE10);
				
				Hashtable<String, Integer> selected = new Hashtable<String, Integer>();
				selected.put("Channel",0);
				selected.put("Dvkd",1);
			   	selected.put("SalesRep", 2); 
			    selected.put("Brands",3);
			    selected.put("Category",4);	 			   
			    selected.put("SKU",5);
			    selected.put("SKUCode",6);
			    selected.put("Customer",7);
			    selected.put("OutletClass",8);
			    selected.put("OutletType",9);
			    selected.put("Date",10);
			    selected.put("GroupSKU",11);
			    selected.put("GroupCustomer",12);
			    selected.put("CustomerCode",13);
			    selected.put("Amount",14);
			    selected.put("Piece",15);
			    selected.put("Quantity",16);
			    		    
			    for(String value:obj.getFieldShow()){
			    	int VALUE = selected.get(value);
			    	switch(VALUE){
				    	case 0: case 1: case 2:
							pivotTable.addFieldToArea(PivotFieldType.ROW, VALUE);	 
							break;  		
	
				    	case 3 : case 4 : case 5 : case 6 : case 7:
				    	case 8: case 9: case 11: case 12:case 13:
				    		pivotTable.addFieldToArea(PivotFieldType.ROW, VALUE);
				    		break;
				    		
				    	case 14: 
				    		pivotTable.addFieldToArea(PivotFieldType.DATA, VALUE);
				    		pivotTable.getDataFields().get(0).setNumber(3);
				    		pivotTable.getDataFields().get(0).setDisplayName("Tong Tien");	
				    		break;
				    		
				    	case 15 : 
				    		pivotTable.addFieldToArea(PivotFieldType.DATA, VALUE);
				    		pivotTable.getDataFields().get(1).setNumber(3);
				    		pivotTable.getDataFields().get(1).setDisplayName("So Luong Le");
				    		break;
				    		
				    	case 16:
				    		pivotTable.addFieldToArea(PivotFieldType.DATA, VALUE);
				    		pivotTable.getDataFields().get(2).setNumber(3);
				    		pivotTable.getDataFields().get(2).setDisplayName("So Luong Thung");
				    		break;				    		
				    		
				    	case 10:
				    		pivotTable.addFieldToArea(PivotFieldType.COLUMN, VALUE);
				    		break;
				    		
				    	default:
				    		pivotTable.addFieldToArea(PivotFieldType.ROW, 12);
				    		break;				    		
			    	}
			    }*/
			   			

			}
			catch (Exception e){
				throw new Exception(e.getMessage());
			}
			
		}else{
			return false;
		}	
		return true;
	}	
}
