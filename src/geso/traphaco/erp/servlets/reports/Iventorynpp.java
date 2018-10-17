package geso.traphaco.center.servlets.reports;

import geso.traphaco.center.beans.stockintransit.IStockintransit;
import geso.traphaco.center.beans.stockintransit.imp.Stockintransit;
import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.center.util.Utility;

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
import com.aspose.cells.PivotFieldDataDisplayFormat;
import com.aspose.cells.PivotFieldType;
import com.aspose.cells.PivotTable;
import com.aspose.cells.PivotTables;
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;



public class Iventorynpp extends HttpServlet {	
	public Iventorynpp() {
        super();
       
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
			}finally{
				try{
					if(rs_tenuser != null) rs_tenuser.close();
					if(db != null) db.shutDown();
					
				}catch(java.sql.SQLException e ){}
			}
		}
		return "";
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session.getAttribute("util");
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		}else{
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		IStockintransit obj = new Stockintransit();
		
		Utility util=new Utility();
		//HttpSession session = request.getSession();
		//String userTen = (String)session.getAttribute("userTen");
		//String querystring=request.getQueryString();
		//String userId=	util.getUserId(querystring);
		obj.settungay("");
		obj.setdenngay("");
		obj.setMsg("");
		obj.setuserId(userId);
		obj.setuserTen(userTen);
    	
		session.setAttribute("obj", obj);
		session.setAttribute("userId", userId);
		session.setAttribute("userTen", userTen);
		session.setAttribute("util", util);
		String nextJSP = "/TraphacoHYERP/pages/Center/Inventorynpp.jsp";
		response.sendRedirect(nextJSP);
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		OutputStream out = response.getOutputStream(); 
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IStockintransit obj = new Stockintransit();
		Utility util = new Utility();
		String nextJSP = "/TraphacoHYERP/pages/Center/Inventorynpp.jsp";
		try {
			obj.setuserTen((String) session.getAttribute("userTen")!=null?
						(String) session.getAttribute("userTen"):"");
			
			obj.settungay(util.antiSQLInspection(request.getParameter("tungay"))!=null?
						util.antiSQLInspection(request.getParameter("tungay")):"");
			
			obj.setdenngay(util.antiSQLInspection(request.getParameter("denngay"))!=null?
					util.antiSQLInspection(request.getParameter("denngay")):"");
			
			obj.setuserId(util.antiSQLInspection(request.getParameter("userId"))!=null? 
						util.antiSQLInspection(request.getParameter("userId")):"");
			String avaliable = util.antiSQLInspection(request.getParameter("avail"));
			/*response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition",
					"attachment; filename=TonHienTai.xls");*/

			response.setContentType("application/xlsm");
			response.setHeader("Content-Disposition", "attachment; filename=Iventorynpp.xlsm");
			
			
			boolean isTrue = CreatePivotTable(out, obj,avaliable);
			if(!isTrue){
				PrintWriter writer = new PrintWriter(out);
				writer.write("Khong co bao cao trong thoi gian nay....!!!");
				writer.close();
			}
		} catch (Exception ex) {
			obj.setMsg(ex.getMessage());
			response.sendRedirect(nextJSP);
		}
	}
	private boolean CreatePivotTable(OutputStream out,IStockintransit obj, String avaliable) throws Exception
    {   
	
		FileInputStream fstream;
			fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\Iventory(NPP).xlsm");
	//	fstream = new FileInputStream("D:\\Best Stable\\SalesUp\\WebContent\\pages\\Templates\\Iventory(NPP).xlsm");	

		Workbook workbook = new Workbook();
		workbook.open(fstream);
		workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
		
		
	     //Buoc2 tao khung
	    //ham tao khu du lieu
	     CreateStaticHeader(workbook, obj.getDateTime(),obj.getDateTime(), obj.getuserTen());
	    
	     boolean isTrue =  CreateStaticData(workbook,obj,avaliable);
	     if(!isTrue)
	    	 return false;
	     workbook.save(out);
	     fstream.close();
	     return true;
	    
   }
	
	private void CreateStaticHeader(Workbook workbook, String dateFrom, String dateTo, String UserName) throws Exception 
	{
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	   	   
	    Cells cells = worksheet.getCells();
	   
	    
	    
	   	Style style;
	    //cells.setColumnWidth(0, 200.0f);
	    cells.setRowHeight(0, 20.0f);
	    Cell cell = cells.getCell("A1");  
	    cell.setValue("TỒN HIỆN TẠI");   	
	    
	  
	    style = cell.getStyle();
	   
	   Font font2 = new Font();
       font2.setColor(Color.RED);//mau chu
       font2.setSize(14);// size chu
       style.setFont(font2); 
       style.setHAlignment(TextAlignmentType.LEFT);// canh le cho chu       
       cell.setStyle(style);
	    cell = cells.getCell("A2"); getCellStyle(workbook,"A2",Color.NAVY,true,9);
	    cell.setValue("Từ ngày  " + dateFrom + "      Đến ngày " + dateTo);    
	    cell = cells.getCell("A3");getCellStyle(workbook,"A3",Color.NAVY,true,9);
	     cell.setValue("Ngày Tạo : " + this.getDateTime());
	    cell = cells.getCell("A4");getCellStyle(workbook,"A4",Color.NAVY,true,9);
	    cell.setValue("Tạo Bởi:  " + UserName);
	    

	    

	  

	  
	  
	    //tieu de, hang dau tien cua bang du lieu, cell la toa do cua exel
	   
	    cell = cells.getCell("AA1"); cell.setValue("Kenh Ban Hang");
	    cell = cells.getCell("AB1"); cell.setValue("Ten San Pham");
	    cell = cells.getCell("AC1"); cell.setValue("So Luong Quy Le");	  
	    cell = cells.getCell("AD1"); cell.setValue("Kho");
	    cell = cells.getCell("AE1"); cell.setValue("Ma Nha Phan Phoi");
	    cell = cells.getCell("AF1"); cell.setValue("Ma San Pham");
	    cell = cells.getCell("AG1"); cell.setValue("So Luong Thung");
	    cell = cells.getCell("AH1"); cell.setValue("Don Vi Kinh Doanh");
	    cell = cells.getCell("AI1"); cell.setValue("Chung Loai");
	    cell = cells.getCell("AJ1"); cell.setValue("Nhan Hang");
	    cell = cells.getCell("AK1"); cell.setValue("So Tien");
	    
	    worksheet.setName("Sheet1");
	}
	private boolean CreateStaticData(Workbook workbook,IStockintransit obj, String avaliable) throws Exception
	{
		dbutils db = new dbutils();
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	    Cells cells = worksheet.getCells();
	    Utility utl = new  Utility();
	    String manpp ="";
	    manpp = utl.getIdNhapp(obj.getuserId());
	    
	
	    	String condition = "";
	    	if(avaliable.equals("1")){
	    		condition +=" and tkn.AVAILABLE > 0";
	    	}
	    	
			String sql =" select kbh.ten as Channel,sp.ma as Sku_code,sp.ten as SKU,tkn.AVAILABLE as Piece,k.ten as Warehouse, "+
			" tkn.npp_fk as Distributor_code,"+
            " nh.ten as Brans, isnull(cast(tkn.AVAILABLE/cast(qc.soluong1 as int) as int), 0) as Quantily,"+
			" dvkd.donvikinhdoanh as Business_unit,cl.ten as Category, "+
			" case when tkn.AVAILABLE* npk.giamua*1.1 is null then 0 else tkn.AVAILABLE * npk.giamua*1.1 end as Amount "+
			" from (select * from nhapp_kho where npp_fk = '"+manpp+"') tkn "+
			" inner join kenhbanhang kbh on kbh.pk_seq = tkn.kbh_fk "+
			" inner join sanpham sp on sp.pk_seq = tkn.sanpham_fk "+
			" inner join donvikinhdoanh dvkd on dvkd.pk_seq = sp.dvkd_fk "+
			" inner join kho k on k.pk_seq = tkn.kho_fk "+
			" left join quycach qc on qc.dvdl1_fk = sp.dvdl_fk and qc.sanpham_fk = sp.pk_seq "+
			" left join donvidoluong dvdl on dvdl.pk_seq = sp.dvdl_fk "+
			" left join chungloai cl on cl.pk_seq = sp.chungloai_fk "+
			" left join nhanhang nh on nh.pk_seq = sp.nhanhang_fk "+
		    " inner join (select distinct bgm.kenh_fk as kbh_fk,bgm_sp.sanpham_fk,bgmnpp.npp_fk,bgm_sp.giamuanpp as giamua from banggiamuanpp_npp bgmnpp "+ 
            " 					inner join banggiamuanpp bgm on bgm.pk_seq = bgmnpp.banggiamuanpp_fk "+
            " 					inner join bgmuanpp_sanpham bgm_sp on bgm_sp.bgmuanpp_fk = bgm.pk_seq "+
		    " 				where bgmnpp.npp_fk ='"+manpp+"') npk on "+
            " 				npk.sanpham_fk = tkn.sanpham_fk and tkn.kbh_fk = npk.kbh_fk and tkn.npp_fk = npk.npp_fk " +
            "where 1=1 " + condition;

		ResultSet rs = db.get(sql);
		
		int i = 2;
		if(rs!=null)
		{
			
			
			try 
			{// se do rong cho cac cot se dung
				cells.setColumnWidth(0, 25.0f);
				cells.setColumnWidth(1, 25.0f);
				cells.setColumnWidth(2, 30.0f);
				cells.setColumnWidth(3, 15.0f);
				cells.setColumnWidth(4, 15.0f);			
				cells.setColumnWidth(5, 15.0f);	
				cells.setColumnWidth(6, 15.0f);	
				cells.setColumnWidth(7, 15.0f);
				
			
				
				Cell cell = null;
				while(rs.next())// lap den cuoi bang du lieu
				{
				
					//lay tu co so du lieu, gan bien
					String Channel = rs.getString("Channel");
					String SKU =rs.getString("SKU");
					String Piece =rs.getString("Piece");			
										
					String Warehouse = rs.getString("Warehouse");
					String DistributorCode =rs.getString("Distributor_code");
					String SkuCode = rs.getString("SKU_code");		
					String Quantily = rs.getString("Quantily");
					String BusinessUnit = rs.getString("Business_unit");
					String Category = rs.getString("Category");
					String Brands = rs.getString("Brans");
					String Amount = rs.getString("Amount");
					
					//cell = cells.getCell("AA" + Integer.toString(i)); cell.setValue(Channel);
					cell = cells.getCell("AA" + Integer.toString(i)); cell.setValue(Channel);
					cell = cells.getCell("AB" + Integer.toString(i)); cell.setValue(SKU);
					cell = cells.getCell("AC" + Integer.toString(i)); cell.setValue(Long.parseLong(Piece));
					cell = cells.getCell("AD" + Integer.toString(i)); cell.setValue(Warehouse);				
					cell = cells.getCell("AE" + Integer.toString(i)); cell.setValue(DistributorCode);
					cell = cells.getCell("AF" + Integer.toString(i)); cell.setValue(SkuCode);
					cell = cells.getCell("AG" + Integer.toString(i)); cell.setValue(Quantily);
					cell = cells.getCell("AH" + Integer.toString(i)); cell.setValue(BusinessUnit);
					cell = cells.getCell("AI" + Integer.toString(i)); cell.setValue(Category);
					cell = cells.getCell("AJ" + Integer.toString(i)); cell.setValue(Brands);
					cell = cells.getCell("AK" + Integer.toString(i)); cell.setValue(Float.parseFloat(Amount));
					i++;
					
				}
				
				if(i==2)
					throw new Exception("Xin loi.Khong co bao cao trong thoi gian nay...!");
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
		 
			   
			    pivotTable.addFieldToArea(PivotFieldType.ROW, 0);
			    pivotTable.addFieldToArea(PivotFieldType.ROW,5);
			    pivotTable.getRowFields().get(1).setAutoSubtotals(false);
			    pivotTable.addFieldToArea(PivotFieldType.ROW, 1);
			    pivotTable.addFieldToArea(PivotFieldType.DATA, 2);
			    pivotTable.addFieldToArea(PivotFieldType.COLUMN, 3);*/
			}catch(Exception ex){
				throw new Exception(ex.getMessage());
			}
			finally{
				if(rs!=null)
					rs.close();
				if(db != null)
					db.shutDown();
				
			}
		}	    
	   return true;
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
