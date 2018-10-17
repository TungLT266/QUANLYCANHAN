package geso.traphaco.center.servlets.reports;

import geso.traphaco.center.beans.Router.IDRouter;
import geso.traphaco.center.beans.Router.imp.Router;
import geso.traphaco.erp.db.sql.dbutils;
import geso.dms.distributor.util.Utility;

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

import org.apache.poi.hssf.record.formula.functions.Npv;

import com.aspose.cells.BorderLineType;
import com.aspose.cells.BorderType;
import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
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

public class Routereport extends HttpServlet {
	
       
	private static final long serialVersionUID = 1L;
	   Utility util=new Utility();
	   //String userTen = "";
	   //String userId;
	   //String ddkdId = "";
	   //String nppId = "";
	   //String kenhId = "";
	   //String tuyenId = "";
	   //boolean bfasle=true;
    public Routereport() {
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
			}
		}
		return "";
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
	//	PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		String userTen = (String)session.getAttribute("userTen");
		String querystring=request.getQueryString();
		String userId = util.getUserId(querystring);
		session.setAttribute("userId", userId);
		session.setAttribute("tungay", "");
 		session.setAttribute("denngay","");
    	 session.setAttribute("loi", "");
		session.setAttribute("userTen", userTen);
		
		
		
		IDRouter obj = new Router();
		
    	obj.setStatus("1");
		obj.init();
		
		session.setAttribute("obj",obj);
		String nextJSP = "/TraphacoHYERP/pages/Center/RouteReport.jsp";
		response.sendRedirect(nextJSP);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
	//	PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
        IDRouter obj = new Router();
        String nppId = util.antiSQLInspection(request.getParameter("nppId"));
        
        String kenhId = util.antiSQLInspection(request.getParameter("kenhId"));
        if(kenhId == null)
        	kenhId = "";
        obj.setKenhId(kenhId);
        
        if(nppId ==null)
        	nppId ="";
        obj.setnppId(nppId);
        
        String ddkdId = util.antiSQLInspection(request.getParameter("ddkdId"));
        
        if(ddkdId == null)
        	ddkdId ="";
        obj.setddkdId(ddkdId);
        
        String tuyenId = util.antiSQLInspection(request.getParameter("tuyenId"));
        if(tuyenId == null)
        	tuyenId ="";
        obj.settuyenId(tuyenId);
        
        obj.init();
        String action = request.getParameter("action");
        
        if(action.equals("export")){
        	OutputStream out = response.getOutputStream(); 
			String userTen = (String)session.getAttribute("userTen");
			
	    	response.setContentType("application/vnd.ms-excel");
	        response.setHeader("Content-Disposition", "attachment; filename=RouteReport.xls");
	        String userId = (String) session.getAttribute("userId");
			CreatePivotTable(out,response,request, userTen, nppId, tuyenId, ddkdId, kenhId, userId );

        }else{
        	
        	String khuvucId = util.antiSQLInspection(request.getParameter("khuvucId"));
        	if(khuvucId.trim().length() > 0)
	        	obj.setkhuvucId(khuvucId);
        	String status = util.antiSQLInspection(request.getParameter("status"));
        	obj.setStatus(status);
        	obj.createNPP();
        	
        	System.out.println("status : "+status);
        	
        	session.setAttribute("obj",obj);
        	String nextJSP = "/TraphacoHYERP/pages/Center/RouteReport.jsp";
        	response.sendRedirect(nextJSP);
        }
	}
	private void CreatePivotTable(OutputStream out,HttpServletResponse response,HttpServletRequest request, String userTen, String nppId, String tuyenId, String ddkdId, String kenhId, String userId) throws IOException
    {    //khoi tao de viet pivottable
		//buoc 1
	     Workbook workbook = new Workbook();
	     //Buoc2 tao khung
	    //ham tao khu du lieu
	     CreateStaticHeader(workbook, userTen);
	     //Buoc3 
	     // day du lieu vao
	     CreateStaticData(workbook, nppId, tuyenId, ddkdId, kenhId, userId);

	     workbook.save(out);
   }
	
	private void CreateStaticHeader(Workbook workbook, String UserName) 
	{
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	   	   
	    Cells cells = worksheet.getCells();
	   
	   	Style style;
	    //cells.setColumnWidth(0, 200.0f);
	    cells.setRowHeight(0, 20.0f);
	    Cell cell = cells.getCell("A1");  
	    cell.setValue("Sales Rep Route Report - Channel: General Trade");   		      
	    style = cell.getStyle();
	  
	    Font font2 = new Font();
	    font2.setColor(Color.RED);//mau chu
	    font2.setSize(16);// size chu
	    font2.setBold(true);
	    
	    style.setFont(font2); 
	    style.setHAlignment(TextAlignmentType.LEFT);// canh le cho chu       
	    cell.setStyle(style);
    
	    cell = cells.getCell("A3");getCellStyle(workbook,"A3",Color.BLUE,true,10);
	    cell.setValue("Reporting Date: " + this.getDateTime());
	     
	    cell = cells.getCell("A4");getCellStyle(workbook,"A4",Color.BLUE,true,10);
	    cell.setValue("Created by:  " + UserName);
	    
	  
	    //tieu de, hang dau tien cua bang du lieu, cell la toa do cua exel
	   
	   /* cell = cells.getCell("AA1"); cell.setValue("STT");
	    cell = cells.getCell("AB1"); cell.setValue("Region");
	    cell = cells.getCell("AC1"); cell.setValue("Area");
	    cell = cells.getCell("AD1"); cell.setValue("Distributor");
	    cell = cells.getCell("AE1"); cell.setValue("Sales Rep");
	    cell = cells.getCell("AF1"); cell.setValue("Thu");
	    cell = cells.getCell("AG1"); cell.setValue("Customer Code");
	    cell = cells.getCell("AH1"); cell.setValue("Customer Name");
	    cell = cells.getCell("AI1"); cell.setValue("Address");
	    cell = cells.getCell("AJ1"); cell.setValue("Town");
	    cell = cells.getCell("AK1"); cell.setValue("Average Volume");
	    cell = cells.getCell("AL1"); cell.setValue("Outlet Type");
	    cell = cells.getCell("AM1"); cell.setValue("Outlet Location");
	    cell = cells.getCell("AN1"); cell.setValue("Outlet Class");
	    cell = cells.getCell("AO1"); cell.setValue("Frequency");
	    
	    */
	    worksheet.setName("Route Report");
	}
	private void CreateStaticData(Workbook workbook, String nppId, String tuyenId, String ddkdId, String kenhId, String userId) 
	{
		dbutils db = new dbutils();
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	    Cells cells = worksheet.getCells();
	    String st="";
	    int dem =0;
	    
	    if(nppId.length()>0)
	    {
	    	st = st + "tbh.npp_fk ='"+ nppId +"'";
	    }
	    
	    if(tuyenId.length()>0)
	    {
	    	if(st.length()>0)
	    		st = st + " and tbh.ngaylamviec ='"+ tuyenId +"' ";
	    	else
	    		st ="tbh.ngaylamviec ='"+ tuyenId +"' ";
	    }
	    
	    if(ddkdId.length()>0)
	    {
	    	if(st.length()>0)
	    		st = st + " and tbh.ddkd_fk ='"+ ddkdId +"' ";
	    	else
	    		st = st + " tbh.ddkd_fk ='"+ ddkdId +"' ";
	    }
	    
	    if (kenhId.length() > 0)
	    {
	    	if(st.length()>0)
	    		st = st + " and kh.kbh_fk ='"+ kenhId +"' ";
	    	else
	    		st = st + " kh.kbh_fk ='"+ kenhId +"' ";
	    }
	    if(st.length()>0)
	    	st = " where " + st;
	    //khoi tao ket noi csdl
		String st1="";
	  
	    if(nppId != "")
	    {
	    	st1 =st1 + " npp_fk = '"+nppId+"' ";
	    }
	    if (tuyenId != ""){
	    
	    	st1 = st1+ " and ngaylamviec = '"+tuyenId+"' ";
	    }
	    
	    if (st1 == "")
	    {
	    	st1 = " group by ngayid";
	    }
	    else
	    {
	    	st1 = " where " + st1 + " group by ngayid";
	    }
	    String sql2 = "select ngayid  from tuyenbanhang " + st1;
	    System.out.println("Lay Du Lieu :"+sql2);
		ResultSet rs2 = db.get(sql2);
		int i =5;
		if (rs2 != null)
		{
			try
			{
				while(rs2.next())
				{
					 i = i + 2;
					 
					Integer ngay = Integer.parseInt(rs2.getString("ngayid"));
					if (ngay == 2)
					{
						Cell cell = cells.getCell("A" + i); cell.setValue("Monday");
					}else if (ngay  == 3)
					{
						Cell cell = cells.getCell("A" + i); cell.setValue("Tuesday");
					}
					else if (ngay  == 4)
					{
						Cell cell = cells.getCell("A" + i); cell.setValue("Wednesday");
					}
					else if (ngay  == 5)
					{
						Cell cell = cells.getCell("A" + i); cell.setValue("Thursday");
					}
					else if (ngay  == 6)
					{
						Cell cell = cells.getCell("A" + i); cell.setValue("Friday");
					}
					else
					{
						Cell cell = cells.getCell("A" + i); cell.setValue("Saturday");
					}
					i=i+1;
					tieude(workbook,String.valueOf(i));
					geso.traphaco.center.util.Utility ut = new geso.traphaco.center.util.Utility();
					 String sql = "select v.ten as vung, kv.ten as khuvuc, npp.ten as npp, ddkd.ten as ddkd, tbh.ngaylamviec,kh.pk_seq as Customer_Key,kh.ten as Customer_Name,kh.diachi as Address,qh.ten as province,case when ds.tonggiatri is null then 0 else ds.tonggiatri end as Average_Volume, lch.diengiai as Outlet_Type, "+
	    			 "vt.vitri as Outlet_Location,hch.hang as Outlet_Class,kh_tuyen.tanso as Frequency "+
	    			 "from khachhang kh "+
	    			 "inner join nhaphanphoi npp on npp.pk_seq = kh.npp_fk "+
      				 "inner join khuvuc kv on kv.pk_seq = npp.khuvuc_fk "+
	    		 	 "inner join vung v on v.pk_seq = kv.vung_fk "+
	    			 "left join quanhuyen qh on kh.quanhuyen_fk = qh.pk_seq "+
	    			 "left join loaicuahang lch on lch.pk_seq = kh.lch_fk "+
	    			 "left join vitricuahang vt on vt.pk_seq = kh.vtch_fk "+
	    			 "left join hangcuahang hch on hch.pk_seq = kh.hch_fk "+
	    			 "left join KHACHHANG_TUYENBH kh_tuyen on kh_tuyen.khachhang_fk = kh.pk_seq "+
	    			 "left join (select khachhang_fk,cast(sum(tonggiatri)/3 as int) as tonggiatri from donhang where ngaynhap >'2011-10-01' and ngaynhap < '2011-12-31' group by khachhang_fk) as ds "+
	    			 "on ds.khachhang_fk = kh.pk_seq " +
	    			 "left join (select * from tuyenbanhang where ngayid = "+ngay+") tbh on tbh.pk_seq = kh_tuyen.tbh_fk " +   
	    			 "inner join daidienkinhdoanh ddkd on ddkd.pk_seq = tbh.ddkd_fk " + st  

	    				
	    			 +" and npp.pk_seq in "+ ut.quyen_npp(userId)
	    			 +" order by v.ten, kv.ten, npp.ten ";
					 
					 

					 System.out.println("Lay Du Lieu :"+sql);
					 ResultSet rs = db.get(sql);
					 	i=i+1;
					 
					 if(rs!= null)
					 {
						 	
						 	cells.setColumnWidth(0, 8.0f);
							cells.setColumnWidth(1, 10.0f);
							cells.setColumnWidth(2, 15.0f);
							cells.setColumnWidth(3, 15.0f);
							cells.setColumnWidth(4, 15.0f);			
							cells.setColumnWidth(5, 15.0f);	
							cells.setColumnWidth(6, 15.0f);	
							cells.setColumnWidth(7, 15.0f);
							cells.setColumnWidth(8, 15.0f);
							cells.setColumnWidth(9, 15.0f);
							cells.setColumnWidth(10, 15.0f);
							cells.setColumnWidth(11, 20.0f);
							cells.setColumnWidth(12, 15.0f);
							cells.setColumnWidth(13, 15.0f);
							cells.setColumnWidth(14, 20.0f);
							cells.setColumnWidth(15, 20.0f);
							
						 try 
						 {
				
							 Cell cell = null;
							 int stt = 1;
							 while(rs.next())// lap den cuoi bang du lieu
							 {
				
					//lay tu co so du lieu, gan bien
					
								 String Stt = String.valueOf(stt);
								 String Region =rs.getString("vung");
								 String Area =rs.getString("khuvuc");
								 String Distributor =rs.getString("npp");
								 String SalesRep =rs.getString("ddkd");
								 String Thu = rs.getString("ngaylamviec");
								 String CustomerCode =rs.getString("Customer_Key");
								 String CustomerName =rs.getString("Customer_Name");
								 String Address =rs.getString("Address");
								 String Town = rs.getString("province");
								 String AverageVolume =rs.getString("Average_Volume");
								 String OutletType = rs.getString("Outlet_Type");
								 String OutletLocation = rs.getString("Outlet_Location");
								 String OutletClass = rs.getString("Outlet_Class");
								 String Frequency = rs.getString("Frequency");
					
					
					//cell = cells.getCell("AA" + Integer.toString(i)); cell.setValue(Channel);
								 cell = cells.getCell("A" + Integer.toString(i)); cell.setValue(Stt);			CreateBorderSetting(workbook,"A" + Integer.toString(i));
								 cell = cells.getCell("B" + Integer.toString(i)); cell.setValue(Region);		CreateBorderSetting(workbook,"B" + Integer.toString(i));
								 cell = cells.getCell("C" + Integer.toString(i)); cell.setValue(Area);			CreateBorderSetting(workbook,"C" + Integer.toString(i));
								 cell = cells.getCell("D" + Integer.toString(i)); cell.setValue(Distributor);	CreateBorderSetting(workbook,"D" + Integer.toString(i));
								 cell = cells.getCell("E" + Integer.toString(i)); cell.setValue(SalesRep);		CreateBorderSetting(workbook,"E" + Integer.toString(i));
								 //cell = cells.getCell("F" + Integer.toString(i)); cell.setValue(Thu);			CreateBorderSetting(workbook,"F" + Integer.toString(i));
								 cell = cells.getCell("F" + Integer.toString(i)); cell.setValue(CustomerCode);	CreateBorderSetting(workbook,"F" + Integer.toString(i));
								 cell = cells.getCell("G" + Integer.toString(i)); cell.setValue(CustomerName);	CreateBorderSetting(workbook,"G" + Integer.toString(i));
								 cell = cells.getCell("H" + Integer.toString(i)); cell.setValue(Address);		CreateBorderSetting(workbook,"H" + Integer.toString(i));
								 cell = cells.getCell("I" + Integer.toString(i)); cell.setValue(Town);			CreateBorderSetting(workbook,"I" + Integer.toString(i));
								 cell = cells.getCell("J" + Integer.toString(i)); cell.setValue(Float.parseFloat(AverageVolume));CreateBorderSetting(workbook,"J" + Integer.toString(i));
								 cell = cells.getCell("K" + Integer.toString(i)); cell.setValue(OutletType);	CreateBorderSetting(workbook,"K" + Integer.toString(i));
								 cell = cells.getCell("L" + Integer.toString(i)); cell.setValue(OutletLocation);CreateBorderSetting(workbook,"L" + Integer.toString(i));
								 cell = cells.getCell("M" + Integer.toString(i)); cell.setValue(OutletClass);	CreateBorderSetting(workbook,"M" + Integer.toString(i));
								 cell = cells.getCell("N" + Integer.toString(i)); cell.setValue(Frequency);		CreateBorderSetting(workbook,"N" + Integer.toString(i));
					
								 i++;
								 stt++;
							 }// end while
					
						 }//end try
						 catch (Exception e){}
					 		finally{
					 			if(rs != null)
					 			rs.close();
					 		}
					 }//end if
				}// end while
			} catch (Exception e){}//end try
			
	 		finally{
	 			if(rs2 != null)
					try {
						rs2.close();
					} catch (SQLException e) {
						
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	 		}
		}// end if
		
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
	private void tieude(Workbook workbook,String a) throws IOException{

		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	   	   
	    Cells cells = worksheet.getCells();
	    Cell cell = cells.getCell("A" + a);  cell.setValue("STT"); 		CreateBorderSetting(workbook,"A"+a); getCellStyle(workbook,"A"+a,Color.BLUE,true,10);
	    cell = cells.getCell("B"  + a); cell.setValue("Region");		CreateBorderSetting(workbook,"B"+a);	getCellStyle(workbook,"B"+a,Color.BLUE,true,10);
	    cell = cells.getCell("C"  + a); cell.setValue("Area");			CreateBorderSetting(workbook,"C"+a);	getCellStyle(workbook,"C"+a,Color.BLUE,true,10);
	    cell = cells.getCell("D"  + a); cell.setValue("Distributor");	CreateBorderSetting(workbook,"D"+a);	getCellStyle(workbook,"D"+a,Color.BLUE,true,10);
	    cell = cells.getCell("E"  + a); cell.setValue("Sales Rep");		CreateBorderSetting(workbook,"E"+a);	getCellStyle(workbook,"E"+a,Color.BLUE,true,10);
	    //cell = cells.getCell("F" + a); cell.setValue("Thu");			CreateBorderSetting(workbook,"F"+a);	getCellStyle(workbook,"F"+a,Color.BLUE,true,10);
	    cell = cells.getCell("F"  + a); cell.setValue("Customer Code");	CreateBorderSetting(workbook,"F"+a);	getCellStyle(workbook,"F"+a,Color.BLUE,true,10);
	    cell = cells.getCell("G" + a); cell.setValue("Customer Name");	CreateBorderSetting(workbook,"G"+a);	getCellStyle(workbook,"G"+a,Color.BLUE,true,10);
	    cell = cells.getCell("H" + a); cell.setValue("Address");		CreateBorderSetting(workbook,"H"+a);	getCellStyle(workbook,"H"+a,Color.BLUE,true,10);
	    cell = cells.getCell("I" + a); cell.setValue("Town");			CreateBorderSetting(workbook,"I"+a);	getCellStyle(workbook,"I"+a,Color.BLUE,true,10);
	    cell = cells.getCell("J" + a); cell.setValue("Average Volume");	CreateBorderSetting(workbook,"J"+a);	getCellStyle(workbook,"J"+a,Color.BLUE,true,10);
	    cell = cells.getCell("K" + a); cell.setValue("Outlet Type");	CreateBorderSetting(workbook,"K"+a);	getCellStyle(workbook,"K"+a,Color.BLUE,true,10);
	    cell = cells.getCell("L" + a); cell.setValue("Outlet Location");CreateBorderSetting(workbook,"L"+a);	getCellStyle(workbook,"L"+a,Color.BLUE,true,10);
	    cell = cells.getCell("M" + a); cell.setValue("Outlet Class");	CreateBorderSetting(workbook,"M"+a);	getCellStyle(workbook,"M"+a,Color.BLUE,true,10);	
	    cell = cells.getCell("N" + a); cell.setValue("Frequency");		CreateBorderSetting(workbook,"N"+a);	getCellStyle(workbook,"N"+a,Color.BLUE,true,10);
	}
	
	 public void CreateBorderSetting(Workbook workbook,String fileName) throws IOException
	    {
		 Worksheets worksheets = workbook.getWorksheets();
		    Worksheet worksheet = worksheets.getSheet(0);
	        Cells cells = worksheet.getCells();
	        Cell cell;
	        Style style;

	        cell = cells.getCell(fileName);
	        style = cell.getStyle();

	        //Set border color
	        style.setBorderColor(BorderType.TOP, Color.BLACK);
	        style.setBorderColor(BorderType.BOTTOM, Color.BLACK);
	        style.setBorderColor(BorderType.LEFT, Color.BLACK);
	        style.setBorderColor(BorderType.RIGHT, Color.BLACK);
	        //style.setBorderColor(BorderType.DIAGONAL_DOWN, Color.BLUE);
	        //style.setBorderColor(BorderType.DIAGONAL_UP, Color.BLUE);

	        //Set the cell border type
	        style.setBorderLine(BorderType.TOP, BorderLineType.THIN);
	        style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
	        style.setBorderLine(BorderType.LEFT, BorderLineType.THIN);
	        style.setBorderLine(BorderType.RIGHT, BorderLineType.THIN);
	        //style.setBorderLine(BorderType.DIAGONAL_DOWN, BorderLineType.DASHED);
	        //style.setBorderLine(BorderType.DIAGONAL_UP, BorderLineType.DASHED);

	        cell.setStyle(style);

	       
	    }
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy : hh-mm-ss");
        Date date = new Date();
        return dateFormat.format(date);	
	}
}
