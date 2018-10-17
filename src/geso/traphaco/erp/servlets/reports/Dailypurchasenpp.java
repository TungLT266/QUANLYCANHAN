package geso.traphaco.center.servlets.reports;


import geso.traphaco.center.beans.stockintransit.IStockintransit;
import geso.traphaco.center.beans.stockintransit.imp.Stockintransit;
import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.center.servlets.report.StockInTransit;
import geso.dms.distributor.util.Utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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
import com.aspose.cells.PivotTables;
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;


public class Dailypurchasenpp extends HttpServlet {
	private static final long serialVersionUID = 1L;
	/*Utility util=new Utility();
    String userTen;
    String tungay;
    String denngay;
    String userId;
 
    boolean bfasle=true;
	 */      
    
    public Dailypurchasenpp() {
        super();
        // TODO Auto-generated constructor stub
    }
    private String gettenuser(String userId_){
		dbutils db=new dbutils();
		String sql_getnam="select ten from nhanvien where pk_seq="+ userId_;
		ResultSet rs_tenuser=db.get(sql_getnam);
		if(rs_tenuser!= null){
			try{
			  while(rs_tenuser.next()){
				  return rs_tenuser.getString("ten");
			  }
			}catch(Exception er){
				return "";
			}
		}
		return "";
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
	//	PrintWriter out = response.getWriter();
		Utility util=new Utility();
		HttpSession session = request.getSession();
		String userTen = (String)session.getAttribute("userTen");
		String querystring=request.getQueryString();
		String userId=	util.getUserId(querystring);
	
		session.setAttribute("tungay", "");
 		session.setAttribute("denngay","");
    	 session.setAttribute("loi", "");
		session.setAttribute("userTen", userTen);
		session.setAttribute("userId", userId);
		String nextJSP = "/TraphacoHYERP/pages/Center/DailyPurchasenpp.jsp";
		response.sendRedirect(nextJSP);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		OutputStream out = response.getOutputStream();
		IStockintransit obj = new Stockintransit();
		boolean bfasle= true;
		Utility util = new Utility();
		try
		    {
			HttpSession session = request.getSession();
			String userTen = (String)session.getAttribute("userTen");
			
			String tungay=util.antiSQLInspection(request.getParameter("tungay"));
			String denngay=util.antiSQLInspection(request.getParameter("denngay"));
			 
			String userId =util.antiSQLInspection(request.getParameter("userId"));
			if(userId ==null)
				userId ="";
			obj.setuserId(userId);
			
		    if(userTen == null)
		    	userTen ="";
		    obj.setuserTen(userTen);
		    if(tungay == null) 
		    	tungay ="";
		    obj.settungay(tungay);
		    if(denngay == null)
		    	denngay ="";
		    obj.setdenngay(denngay);
		    
	    	/*response.setContentType("application/vnd.ms-excel");
	        response.setHeader("Content-Disposition", "attachment; filename=HangNhapKho.xls");*/
		    response.setContentType("application/xlsm");
			response.setHeader("Content-Disposition", "attachment; filename=Dailypurchase(NPP).xlsm");
		    
		    
	        CreatePivotTable(out,response,request,obj,bfasle);
	     }
	    catch (Exception ex)
	    {
	        ex.printStackTrace();
	
	        // say sorry
	        response.setContentType("text/html");
	        PrintWriter writer = new PrintWriter(out);
	
	        writer.println("<html>");
	        writer.println("<head>");
	        writer.println("<title>sorry</title>");
	        writer.println("</head>");
	        writer.println("<body>");
	        writer.println("<h1>Xin loi, khong the tao pivot table...</h1>");
	        ex.printStackTrace(writer);
	        writer.println("</body>");
	        writer.println("</html>");
	        writer.close();
	    }
	}
	private void CreatePivotTable(OutputStream out,HttpServletResponse response,HttpServletRequest request,IStockintransit obj,boolean bfasle) throws IOException
    {    //khoi tao de viet pivottable
	   
		//buoc 1
		FileInputStream fstream;
		
		fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\Dailypurchase(NPP).xlsm");
//		 fstream = new FileInputStream("D:\\Best Stable\\SalesUp\\WebContent\\pages\\Templates\\Dailypurchase(NPP).xlsm");	

			Workbook workbook = new Workbook();
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
	     //Buoc2 tao khung
	    //ham tao khu du lieu
	     CreateStaticHeader(workbook,obj.gettungay(),obj.getdenngay(),obj.getuserTen());
	     //Buoc3 
	     // day du lieu vao
	     CreateStaticData(workbook,obj,bfasle);
	     if(!bfasle){
	    	String loi="chua co bao cao trong thoi gian nay, vui long chon lai thoi gian xem bao cao";
	    	
	    	 HttpSession session = request.getSession();
	 		session.setAttribute("loi", loi);
	 		session.setAttribute("tungay",obj.gettungay());
	 		session.setAttribute("denngay",obj.getdenngay());
	    	 session.setAttribute("userTen",obj.getuserTen());
	 		String nextJSP = "/TraphacoHYERP/pages/Center/DailyPurchasenpp.jsp";
	 		response.sendRedirect(nextJSP);
	     }else{
	    	 //Saving the Excel file
	    	 workbook.save(out);
	     }
	     fstream.close();
   }
	
	private void CreateStaticHeader(Workbook workbook, String dateFrom, String dateTo, String UserName) 
	{
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	   	   
	    Cells cells = worksheet.getCells();
	   
	    
	    
	   	Style style;
	    //cells.setColumnWidth(0, 200.0f);
	    cells.setRowHeight(0, 20.0f);
	    Cell cell = cells.getCell("A1");  
	    cell.setValue("HÀNG NHẬP KHO");   	
	    
	  
	    style = cell.getStyle();
	   
	   Font font2 = new Font();
       font2.setColor(Color.RED);//mau chu
       font2.setSize(14);// size chu
       style.setFont(font2); 
       style.setHAlignment(TextAlignmentType.LEFT);// canh le cho chu       
       cell.setStyle(style);
	    cell = cells.getCell("A2"); getCellStyle(workbook,"A2",Color.NAVY,true,9);
	    cell.setValue("Từ ngày " + dateFrom + "      Đến ngày " + dateTo);    
	    cell = cells.getCell("A3");getCellStyle(workbook,"A3",Color.NAVY,true,9);
	     cell.setValue("Ngày Tạo: " + this.getDateTime());
	    cell = cells.getCell("A4");getCellStyle(workbook,"A4",Color.NAVY,true,9);
	    cell.setValue("Tạo bởi :  " + UserName);
	    
	  
	    //tieu de, hang dau tien cua bang du lieu, cell la toa do cua exel
	   
	    cell = cells.getCell("AA1"); cell.setValue("Don Vi Kinh Doanh");
	    cell = cells.getCell("AB1"); cell.setValue("Ma Nha Phan Phoi");
	    cell = cells.getCell("AC1"); cell.setValue("Nhan Hang");	  
	    cell = cells.getCell("AD1"); cell.setValue("Chung Loai");
	    cell = cells.getCell("AE1"); cell.setValue("So Chung Tu");;
	    cell = cells.getCell("AF1"); cell.setValue("Ngay Nhap Hang");
	    cell = cells.getCell("AG1"); cell.setValue("Ngay Chung Tu");
	    cell = cells.getCell("AH1"); cell.setValue("Ma San Pham");
	    cell = cells.getCell("AI1"); cell.setValue("Ten San Pham");
	    cell = cells.getCell("AJ1"); cell.setValue("Kho");
	    
	    cell = cells.getCell("AK1"); cell.setValue("So Luong Le");
	    cell = cells.getCell("AL1"); cell.setValue("So Tien");
	  //  cell = cells.getCell("AL1"); cell.setValue("Pridate");
	 //   cell = cells.getCell("AM1"); cell.setValue("Transtype");
	    
	    worksheet.setName("Sheet1");
	}
	private void CreateStaticData(Workbook workbook,IStockintransit obj,boolean bfasle) 
	{
		dbutils db = new dbutils();
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	    Cells cells = worksheet.getCells();
	   Utility Ult = new Utility();
	    String manpp = Ult.getIdNhapp(obj.getuserId());
	    System.out.println(" ma nha phan phoi"+ manpp);
				String sql ="select  kbh.ten as Channel, "+
				" v.ten as Region,kv.ten as Area, "+
				 " tt.ten as Province,qh.ten as Town , "+ 
				 " npp.sitecode as Distributor_code, "+
			     " npp.ten as Distributor, "+
			     " dvkd.donvikinhdoanh as Business_unit, "+
			     " nhsp.sanpham_fk as SKU_code,kho.ten as tenkho, "+
			     " sp.ten as SKU,nh.ngaynhan as Purdate,nh.ngaychungtu , "+
			     " nhah.ten as Brands,cl.ten as Category, "+
			     " nhsp.soluong as Piece, "+
			     " nh.chungtu as Series_number ,"+
			     " nhsp.tienavat as Amount "+
			    " from nhaphang nh "+
			    " inner join nhaphang_sp nhsp on nhsp.nhaphang_fk = nh.pk_seq "+  
				" left  join kenhbanhang kbh on kbh.pk_seq = nh.kbh_fk "+
				" inner join nhaphanphoi npp on npp.pk_seq = nh.npp_fk  "+
				" left join donvikinhdoanh dvkd on dvkd.pk_seq = nh.dvkd_fk "+ 
				" inner join sanpham sp on sp.ma = nhsp.sanpham_fk  "+
				" inner join kho on kho.pk_seq=nh.kho_fk	"+
				" left join nhanhang nhah on nhah.pk_seq = sp.nhanhang_fk "+ 
				" left join chungloai cl on cl.pk_seq = sp.chungloai_fk  "+
				" left join quycach qc on qc.sanpham_fk = sp.pk_seq and sp.dvdl_fk = qc.dvdl1_fk "+  
				" left join khuvuc kv on kv.pk_seq = npp.khuvuc_fk "+
				" left join vung v on v.pk_seq = kv.vung_fk "+
				" left join quanhuyen qh on qh.pk_seq = npp.quanhuyen_fk "+  
				" left join tinhthanh tt on tt.pk_seq = npp.tinhthanh_fk "+		
				
				" where nh.trangthai <> '0' and npp.pk_seq = '"+ manpp +"' "+
			    " and nh.ngaynhan >= '"+ obj.gettungay()+"'"+
			    " and nh.ngaynhan <= '"+ obj.getdenngay() +"'";
         System.out.println(sql);
		ResultSet rs = db.get(sql);
		
		int i = 2;
		if(rs!=null)
		{
			
			
			try 
			{// se do rong cho cac cot se dung
				cells.setColumnWidth(0, 15.0f);
				cells.setColumnWidth(1, 15.0f);
				cells.setColumnWidth(2, 15.0f);
				cells.setColumnWidth(3, 15.0f);
				cells.setColumnWidth(4, 15.0f);			
				cells.setColumnWidth(5, 15.0f);	
				cells.setColumnWidth(6, 15.0f);	
				cells.setColumnWidth(7, 15.0f);
				cells.setColumnWidth(7, 15.0f);
			
				
				Cell cell = null;
				while(rs.next())// lap den cuoi bang du lieu
				{
				
					//lay tu co so du lieu, gan bien
					String BusinessUnit = rs.getString("Business_unit");
					String Brands =rs.getString("Brands");
					String Category =rs.getString("Category");			
										
					String SKUcode = rs.getString("SKU_Code");
					String SKU =rs.getString("SKU");
					String Purdate = rs.getString("Purdate");
					String ngaychungtu = rs.getString("ngaychungtu");
					String Amount = rs.getString("Amount");
					String DistributorCode = rs.getString("Distributor_code");
				//	String BillingNumber = rs.getString("Billing_number");
					String SeriesNumber = rs.getString("Series_number");
					String Piece = rs.getString("Piece");
					//String Pridate = rs.getString("Pridate");
					//String Transtype = rs.getString("Transtype");
					String tenkho=rs.getString("tenkho");
					
					
					//cell = cells.getCell("AA" + Integer.toString(i)); cell.setValue(Channel);
					cell = cells.getCell("AA" + Integer.toString(i)); cell.setValue(BusinessUnit);
					cell = cells.getCell("AB" + Integer.toString(i)); cell.setValue(DistributorCode);
					cell = cells.getCell("AC" + Integer.toString(i)); cell.setValue(Brands);
					cell = cells.getCell("AD" + Integer.toString(i)); cell.setValue(Category);
					cell = cells.getCell("AE" + Integer.toString(i)); cell.setValue(SeriesNumber);
					cell = cells.getCell("AF" + Integer.toString(i)); cell.setValue(Purdate);
					cell = cells.getCell("AG" + Integer.toString(i)); cell.setValue(ngaychungtu);
					cell = cells.getCell("AH" + Integer.toString(i)); cell.setValue(SKUcode);
					cell = cells.getCell("AI" + Integer.toString(i)); cell.setValue(SKU);				
					
					cell = cells.getCell("AJ" + Integer.toString(i)); cell.setValue(tenkho);	
					
					cell = cells.getCell("AK" + Integer.toString(i)); cell.setValue(Float.parseFloat(Piece));
					cell = cells.getCell("AL" + Integer.toString(i)); cell.setValue(Float.parseFloat(Amount));
					
				
				//	cell = cells.getCell("AL" + Integer.toString(i)); cell.setValue(Pridate);
				//	cell = cells.getCell("AM" + Integer.toString(i)); cell.setValue(Transtype);
				
					i++;
				}
			
		
		//xong buoc dua du lieu vao exel
		
		
		
	    
	
		//create pivot
		 getAn(workbook,49);
	/*	PivotTables pivotTables = worksheet.getPivotTables();

	    //Adding a PivotTable to the worksheet
		String pos = Integer.toString(i-1);	//pos la dong cuoi cung	,A12 la toa do dau cua banng du lieu, Q pos la dong cuoi
	    int index = pivotTables.add("=AA1:AK" + pos,"A12","PivotTableDemo");
        // System.out.println("index:"+index);
	    //Accessing the instance of the newly added PivotTable
	    PivotTable pivotTable = pivotTables.get(index);//truyen index

	    pivotTable.setRowGrand(false);
	    pivotTable.setColumnGrand(true);
	    pivotTable.setAutoFormat(true);
	    //Setting the PivotTable autoformat type.
	    //pivotTable.setAutoFormatType(PivotTableAutoFormatType.REPORT6);
 
	   
	  
	    pivotTable.addFieldToArea(PivotFieldType.ROW, 0);	pivotTable.getRowFields().get(0).setAutoSubtotals(false);  
	    pivotTable.addFieldToArea(PivotFieldType.ROW, 1);	pivotTable.getRowFields().get(1).setAutoSubtotals(false);  
	    pivotTable.addFieldToArea(PivotFieldType.ROW, 2);	pivotTable.getRowFields().get(2).setAutoSubtotals(false);  
	    pivotTable.addFieldToArea(PivotFieldType.ROW, 3);	pivotTable.getRowFields().get(3).setAutoSubtotals(false);  
	    pivotTable.addFieldToArea(PivotFieldType.ROW, 4);	pivotTable.getRowFields().get(4).setAutoSubtotals(false);
	    pivotTable.addFieldToArea(PivotFieldType.ROW, 5);	pivotTable.getRowFields().get(5).setAutoSubtotals(false);  
	    pivotTable.addFieldToArea(PivotFieldType.DATA, 8);	pivotTable.getDataFields().get(0).setDisplayName("Số Lượng Lẻ");
	  //  pivotTable.addFieldToArea(PivotFieldType.DATA, 9);	pivotTable.getDataFields().get(0).setDisplayName("Amount");
	   
	   // pivotTable.addFieldToArea(PivotFieldType.COLUMN, pivotTable.getDataField());
	    pivotTable.getDataFields().get(0).setNumber(3);
*/	   
	    bfasle=true;
			}
			catch (Exception e){
				bfasle=false;
				e.printStackTrace(); 
				System.out.println("Error : Inventory Report : " +e.toString());
			}
		}else{
			bfasle=false;
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
	        cell.setStyle(style);
	}
	private void getAn(Workbook workbook,int i)
	{
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	   	   
	    Cells cells = worksheet.getCells();
	    for(int j = 26; j <= i; j++)
	    { 
	    	cells.hideColumn(j);
	    }
		
	}
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy : hh-mm-ss");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
}
