package geso.traphaco.center.servlets.reports;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
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
import com.aspose.cells.Font;
import com.aspose.cells.PivotFieldType;
import com.aspose.cells.PivotTable;
import com.aspose.cells.PivotTables;
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

import geso.traphaco.center.beans.denghitrakmtichluy.*;
import geso.traphaco.center.beans.denghitrakmtichluy.imp.Denghitrakmtichluy;
import geso.traphaco.erp.db.sql.*;
import geso.traphaco.center.util.Utility;


public class AccumulatedPromotion extends HttpServlet {
    public AccumulatedPromotion() {
        super();

    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

	//	dbutils db = new dbutils("best");
		dbutils db = new dbutils();
		String sql = "select pk_seq, scheme from ctkhuyenmai where loaict='3'";		
		ResultSet ctkmIds = db.get(sql);
		
		HttpSession session = request.getSession();
		String userTen = (String)session.getAttribute("userTen");
		String userId=	(String)session.getAttribute("userId");		
		session.setAttribute("userTen", userTen);
		session.setAttribute("userId", userId);
		session.setAttribute("baoloi", "");
		session.setAttribute("ctkmIds", ctkmIds);
		session.setAttribute("ctkmId", "");
		
		String nextJSP = "/TraphacoHYERP/pages/Center/AccumulatedPromotion.jsp";
		response.sendRedirect(nextJSP);		
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		OutputStream out = response.getOutputStream(); 
		Utility util = new Utility();
		try
		    {
			HttpSession session = request.getSession();
			String userTen = (String)session.getAttribute("userTen");
			String userId=	(String)session.getAttribute("userId");
			String ctkmId = util.antiSQLInspection(util.antiSQLInspection(request.getParameter("ctkmId")));
			if(userTen==null) userTen ="";
			if(userId ==null) userId ="";
			if(ctkmId ==null) ctkmId ="";
			
	    	response.setContentType("application/vnd.ms-excel");
	        response.setHeader("Content-Disposition", "attachment; filename=AccumulatedPromotionReport.xls");
	        IDenghitrakmtichluy trakm = new Denghitrakmtichluy();
	        System.out.println("userId" + userId +": ctkm "+ ctkmId +" UserTen :" + userTen);
	        trakm.setctkmId(ctkmId);
	        trakm.setUserId(userId);
	        trakm.setUserName(userTen);
	        session.setAttribute("ctkmTL", ctkmId);
	        trakm.Denghitrakmtichluy();
	        CreatePivotTable(out, response, request, trakm);
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
	
	private void CreatePivotTable(OutputStream out,HttpServletResponse response,HttpServletRequest request, IDenghitrakmtichluy trakm) throws IOException
    {    //khoi tao de viet pivottable
		//buoc 1
	     Workbook workbook = new Workbook();
	     //Buoc2 tao khung
	    //ham tao khu du lieu
	     CreateStaticHeader(workbook, trakm);
	     //Buoc3 
	     // day du lieu vao
	    boolean result = CreateStaticData(workbook,trakm);
	     if(result == false){
	 	//	dbutils db = new dbutils("best");
	    	dbutils db = new dbutils();
			String sql = "select pk_seq, scheme from ctkhuyenmai where loaict='3'";		
			ResultSet ctkmIds = db.get(sql);
			
			HttpSession session = request.getSession();
			String userTen = (String)session.getAttribute("userTen");
			String userId=	(String)session.getAttribute("userId");		
			session.setAttribute("userTen", userTen);
			session.setAttribute("userId", userId);
			session.setAttribute("baoloi", "");
			session.setAttribute("ctkmIds", ctkmIds);
			session.setAttribute("ctkmId", "");
			
			String nextJSP = "/TraphacoHYERP/pages/Center/AccumulatedPromotion.jsp";
			response.sendRedirect(nextJSP);		
	     }else{
	    	 //Saving the Excel file
	    	 workbook.save(out);
	     }
   }
	
	private void CreateStaticHeader(Workbook workbook, IDenghitrakmtichluy trakm) 
	{
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	   	   
	    Cells cells = worksheet.getCells();	       
	    
	   	Style style;
	    //cells.setColumnWidth(0, 200.0f);
	    cells.setRowHeight(0, 20.0f);
	    Cell cell = cells.getCell("A1");  
	    cell.setValue("BAO CAO KHUYEN MAI TICH LUY");   	
	    
	  
	    style = cell.getStyle();
	   
	   Font font2 = new Font();
       font2.setColor(Color.RED);//mau chu
       font2.setSize(16);// size chu
       style.setFont(font2); 
       style.setHAlignment(TextAlignmentType.LEFT);// canh le cho chu       
       cell.setStyle(style);
       
	    cell = cells.getCell("A2"); getCellStyle(workbook,"A2",Color.BLUE,true,10);
       
	    cell.setValue("Valid From " + trakm.getTungay() + "      To " + trakm.getDenngay());    
	    cell = cells.getCell("A3");getCellStyle(workbook,"A3",Color.BLUE,true,10);
	    cell.setValue("Reporting Date: " + this.getDateTime());
	    cell = cells.getCell("A4");getCellStyle(workbook,"A4",Color.BLUE,true,10);
	    cell.setValue("Created by:  " + trakm.getUserName()); 
	    
	    //tieu de, hang dau tien cua bang du lieu, cell la toa do cua exel
	    
//	    cell = cells.getCell("EA1"); cell.setValue("Chanel"); 
	    cell = cells.getCell("EA1"); cell.setValue("VUNG");
	    cell = cells.getCell("EB1"); cell.setValue("KHU VUC");
	    cell = cells.getCell("EC1");cell.setValue("SCHEME");
	    cell = cells.getCell("ED1"); cell.setValue("MA NHA PHAN PHOI");
	    cell = cells.getCell("EE1"); cell.setValue("NHA PHAN PHOI");
	    cell = cells.getCell("EF1"); cell.setValue("MA KHACH HANG");
	  	cell = cells.getCell("EG1"); cell.setValue("KHACH HANG");
	    cell = cells.getCell("EH1"); cell.setValue("SOXUATDK");
	    cell = cells.getCell("EI1"); cell.setValue("DE NGHI TRA");
	    worksheet.setName("KM TICH LUY(TT)");
	}

	private boolean CreateStaticData(Workbook workbook,IDenghitrakmtichluy ctkmId) 
	{
		boolean result = true;
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	    Cells cells = worksheet.getCells();
	    dbutils db = new dbutils();
	    
	   // Utility Ult = new  Utility();
	    
		//geso.traphaco.center.util.Utility ut = new geso.traphaco.center.util.Utility();
		
	  /*  String sql = "SELECT " +
	    				"VUNG.TEN AS VUNG, " +
	    				"KV.TEN AS KHUVUC, " +
	    				"CTKM.SCHEME AS SCHEME, " +
	    				"NPP.PK_SEQ AS NPPID, " +
	    				"NPP.TEN AS NPP, " +
	    				"DDKD.TEN AS DDKD, " +
	    				"KH.PK_SEQ AS KHID, " +
	    				"KH.TEN AS KH, " +
	    				"TRAKMTL_KH.XUATDENGHI AS DENGHI " +
	    			"FROM DENGHITRAKMTICHLUY TRAKMTL " +
	    			"INNER JOIN DENGHITRAKMTICHLUY_KHACHHANG TRAKMTL_KH ON TRAKMTL.PK_SEQ = TRAKMTL_KH.DENGHITRAKMTL_FK " +
	    			"INNER JOIN CTKHUYENMAI CTKM ON CTKM.PK_SEQ = TRAKMTL.CTKM_FK " +
	    			"INNER JOIN KHACHHANG KH ON KH.PK_SEQ = TRAKMTL_KH.KHACHHANG_FK " +
	    			"INNER JOIN NHAPHANPHOI NPP ON NPP.PK_SEQ = TRAKMTL_KH.NPP_FK " +
	    			"LEFT JOIN TUYENBANHANG TBH ON TBH.NPP_FK = NPP.PK_SEQ " + 
	    			"LEFT JOIN DAIDIENKINHDOANH DDKD ON DDKD.PK_SEQ = TBH.DDKD_FK " +
	    			"left JOIN KHUVUC KV ON KV.PK_SEQ = NPP.KHUVUC_FK " +
	    			"left JOIN VUNG VUNG ON VUNG.PK_SEQ = KV.VUNG_FK " +
	    			"ORDER BY VUNG.TEN, KV.TEN, NPP.TEN, DDKD.TEN, KH.TEN";
	    */
				String sql = "SELECT distinct " +
				"VUNG.TEN AS VUNG, " +
				"KV.TEN AS KHUVUC, " +
				"CTKM.SCHEME AS SCHEME, " +
				"NPP.PK_SEQ AS NPPID, " +
				"NPP.TEN AS NPP, " +
				" isnull(DK1.SOXUAT,0) AS SOXUATDK, " +
				"KH.PK_SEQ AS KHID, " +
				"KH.TEN AS KH, " +
				" isnull(TRAKMTL_KH.XUATDENGHI,0) AS DENGHI " +
			"FROM DENGHITRAKMTICHLUY TRAKMTL " +
			"INNER JOIN DENGHITRAKMTICHLUY_KHACHHANG TRAKMTL_KH ON TRAKMTL.PK_SEQ = TRAKMTL_KH.DENGHITRAKMTL_FK " +
			" INNER JOIN (" +
			"	select dkkh.khachhang_fk,dk.CTKM_FK,dkkh.SOXUAT   from DANGKYKM_TICHLUY_KHACHHANG dkkh " +
			"	INNER JOIN DANGKYKM_TICHLUY dk ON dk.pk_seq = dkkh.DKKMTICHLUY_FK" +
			"	where dk.trangthai ='1' " +
			"		) " +
			" dk1 on dk1.khachhang_fk = TRAKMTL_KH.khachhang_fk and TRAKMTL.ctkm_FK = dk1.CTKM_FK "+
			"INNER JOIN CTKHUYENMAI CTKM ON CTKM.PK_SEQ = TRAKMTL.CTKM_FK " +
			"INNER JOIN KHACHHANG KH ON KH.PK_SEQ = TRAKMTL_KH.KHACHHANG_FK " +
			"INNER JOIN NHAPHANPHOI NPP ON NPP.PK_SEQ = TRAKMTL_KH.NPP_FK " +
			"left JOIN KHUVUC KV ON KV.PK_SEQ = NPP.KHUVUC_FK " +
			"left JOIN VUNG VUNG ON VUNG.PK_SEQ = KV.VUNG_FK " +
			" where TRAKMTL.CTKM_FK ='"+ ctkmId.getctkmId() +"'"+
			"ORDER BY VUNG.TEN, KV.TEN, NPP.TEN, KH.TEN";
	    System.out.println(sql);
	    System.out.println();
			 ResultSet rs = db.get(sql);
		 int i = 2;
		if(rs!=null)
		{
			try 
			{// se do rong cho cac cot se dung
				cells.setColumnWidth(0, 10.0f);
				cells.setColumnWidth(1, 15.0f);
				cells.setColumnWidth(2, 15.0f);
				cells.setColumnWidth(3, 15.0f);
				cells.setColumnWidth(4, 15.0f);
				cells.setColumnWidth(5, 15.0f);
				cells.setColumnWidth(6, 15.0f);
				cells.setColumnWidth(7, 15.0f);
				cells.setColumnWidth(8, 15.0f);
				
				//set do rong cho dong
				for(int j = 12; j <= 12; j++)
					cells.setRowHeight(j, 14.0f);
				
				Cell cell = null;
				while(rs.next())// lap den cuoi bang du lieu
				{ 
					//lay tu co so du lieu, gan bien
					String Region = rs.getString("VUNG");
					String Area = rs.getString("KHUVUC");
					String Scheme = rs.getString("SCHEME");
					String DisCode = rs.getString("NPPID");
					String Distributor = rs.getString("NPP");
					String SaleRep = rs.getString("SOXUATDK");
					String CustomerKey = rs.getString("KHID");
					String Customer = rs.getString("KH");
					String Quantity = rs.getString("DENGHI");

					
					cell = cells.getCell("EA" + Integer.toString(i)); cell.setValue(Region);
					cell = cells.getCell("EB" + Integer.toString(i)); cell.setValue(Area);
					cell = cells.getCell("EC" + Integer.toString(i)); cell.setValue(Scheme);					
					cell = cells.getCell("ED" + Integer.toString(i));  cell.setValue(DisCode);
					cell = cells.getCell("EE" + Integer.toString(i));cell.setValue(Distributor);
					cell = cells.getCell("EF" + Integer.toString(i));cell.setValue(CustomerKey);
					cell = cells.getCell("EG" + Integer.toString(i)); cell.setValue(Customer);
					cell = cells.getCell("EH" + Integer.toString(i));  cell.setValue(Float.parseFloat(SaleRep));
					cell = cells.getCell("EI" + Integer.toString(i)); cell.setValue(Float.parseFloat(Quantity));
					i++;
					if(i>65000){
						if(rs!=null){
							rs.close();
						}
						if(db != null) db.shutDown();
						return false;
					}
				}
				if(rs!=null){
					rs.close();
				}
				
				if(db != null) db.shutDown();
		getAn(workbook,156);
		PivotTables pivotTables = worksheet.getPivotTables();

	    //Adding a PivotTable to the worksheet
		String pos = Integer.toString(i-1);	//pos la dong cuoi cung	,A12 la toa do dau cua banng du lieu, Q pos la dong cuoi
	    int index = pivotTables.add("=EA1:EI" + pos,"A12","PivotTableDemo");
         System.out.println("index:"+index);
	    //Accessing the instance of the newly added PivotTable
	    PivotTable pivotTable = pivotTables.get(index);//truyen index

	    pivotTable.setRowGrand(true);
	    pivotTable.setColumnGrand(true);
	    pivotTable.setAutoFormat(true);
	    pivotTable.addFieldToArea(PivotFieldType.ROW, 0);//pivotTable.getRowFields().get(0).setAutoSubtotals(false);
	    pivotTable.addFieldToArea(PivotFieldType.ROW, 1);//pivotTable.getRowFields().get(0).setAutoSubtotals(false);
	    pivotTable.addFieldToArea(PivotFieldType.ROW, 2);//pivotTable.getRowFields().get(1).setAutoSubtotals(false);
	    pivotTable.addFieldToArea(PivotFieldType.ROW, 3);pivotTable.getRowFields().get(2).setAutoSubtotals(false);
	    pivotTable.addFieldToArea(PivotFieldType.ROW, 4);pivotTable.getRowFields().get(3).setAutoSubtotals(false);
	    pivotTable.addFieldToArea(PivotFieldType.ROW, 5);pivotTable.getRowFields().get(4).setAutoSubtotals(false);
	    
	    pivotTable.addFieldToArea(PivotFieldType.ROW, 6);pivotTable.getRowFields().get(4).setAutoSubtotals(false);
	    pivotTable.addFieldToArea(PivotFieldType.DATA, 7);pivotTable.getRowFields().get(4).setAutoSubtotals(false);
	    pivotTable.addFieldToArea(PivotFieldType.DATA, 8);//pivotTable.getRowFields().get(4).setAutoSubtotals(false);
	    pivotTable.addFieldToArea(PivotFieldType.COLUMN, pivotTable.getDataField());
	   
	    result = true;
			}
			catch (Exception e){
				result = false;
				e.printStackTrace(); 
				System.out.println("Error : AccumulatedPromotion Servlet : " +e.toString());
			}
		}else{
			result = false;
		}
		if(db != null) db.shutDown();
		return result;
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
	    for(int j = 130; j <= i; j++)
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
