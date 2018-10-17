package geso.traphaco.distributor.servlets.reports;


import geso.traphaco.center.beans.stockintransit.IStockintransit;
import geso.traphaco.center.beans.stockintransit.imp.Stockintransit;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.distributor.util.Utility;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
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
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Font;
import com.aspose.cells.Style;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;


public class RouteSumaryReport extends HttpServlet {
	private static final long serialVersionUID = 1L;


	public RouteSumaryReport() {
		super();

	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IStockintransit obj = new Stockintransit();
		String querystring = request.getQueryString();
		
		Utility util = new Utility();
		obj.setuserId(util.getUserId(querystring));
		obj.setuserTen((String) session.getAttribute("userTen"));
		obj.setnppId("");
		obj.init();
		
		session.setAttribute("obj", obj);		
		session.setAttribute("userId", obj.getuserId());
		session.setAttribute("userTen", obj.getuserTen());
		String nextJSP = "/TraphacoHYERP/pages/Distributor/BaoCaoTomTatTBH.jsp";
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
		Utility util = new Utility();
		
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		
		obj.settungay(util.antiSQLInspection(request.getParameter("Sdays")));
		obj.setdenngay(util.antiSQLInspection(request.getParameter("Edays")));
		
		
		obj.setuserId(userId!=null? userId:"");
		obj.setuserTen(userTen!=null? userTen:"");
		
		obj.setvungId(request.getParameter("vungId")!=null?
				util.antiSQLInspection(request.getParameter("vungId")):"");
			
		obj.setkhuvucId(request.getParameter("khuvucId")!=null?
				util.antiSQLInspection(request.getParameter("khuvucId")):"");
		
	
		obj.setnppId(request.getParameter("nppId")!=null?
				util.antiSQLInspection(request.getParameter("nppId")):"");
		

		obj.setDdkd(request.getParameter("ddkdId")!=null?	
				util.antiSQLInspection(request.getParameter("ddkdId")):"");
		
		 obj.setnppId(util.getIdNhapp(userId));
		 

		String action = request.getParameter("action")!=null? util.antiSQLInspection(request.getParameter("action")):"";
		String nextJSP = "/TraphacoHYERP/pages/Distributor/BaoCaoTomTatTBH.jsp";
		
		if(action.equals("Taomoi")){
			try{
		
				response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "attachment; filename=BaoCaoTomTatTBH_New.xlsm");
		        if(!CreatePivotTable(out,obj)){

		        }
			}catch(Exception ex){
				obj.setMsg(ex.getMessage());
			}
		}else{			
			
		}
		obj.init();
		session.setAttribute("obj", obj);	
		response.sendRedirect(nextJSP);
	}

	private boolean CreatePivotTable(OutputStream out, IStockintransit obj) throws Exception 
	{
		String chuoi=getServletContext().getInitParameter("path") + "\\BAOCAOTOMTATTBH.xlsm";
		FileInputStream fstream = new FileInputStream(chuoi);
			
		Workbook workbook = new Workbook();
		workbook.open(fstream);
		workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);

		CreateStaticHeader(workbook, obj);

		boolean isFill = CreateStaticData(workbook, obj);
		
		if (!isFill){
			fstream.close();
			return false;
		}else {
			workbook.save(out);
			fstream.close();
			return true;
		}
	}

	private void CreateStaticHeader(Workbook workbook, IStockintransit obj) throws Exception 
	{
		try{
			
			Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet(0);
			worksheet.setName("Sheet1");

			Cells cells = worksheet.getCells();

			cells.setRowHeight(0, 20.0f);
			Cell cell = cells.getCell("A1");
		    cell.setValue("BÁO CÁO TÓM TẮT TUYẾN BÁN HÀNG");   	
		    
		    cells.setRowHeight(2, 18.0f);
		    cell = cells.getCell("A3"); 
		    getCellStyle(workbook,"A3",Color.NAVY,true,10);
		   
		    
		    cells.setRowHeight(3, 18.0f);
		    cell = cells.getCell("B3"); getCellStyle(workbook,"B3",Color.NAVY,true,9);
		    
		
		    cells.setRowHeight(4, 18.0f);
		    cell = cells.getCell("A4");getCellStyle(workbook,"A4",Color.NAVY,true,9);
		    cell.setValue("Ngày báo cáo: " + this.getDateTime());
		    
		  
					
	
		 	cell = cells.getCell("AA1"); cell.setValue("ChiNhanh");
		    cell = cells.getCell("AB1"); cell.setValue("KhuVuc");
		    cell = cells.getCell("AC1"); cell.setValue("NhaPhanPhoi");
		    cell = cells.getCell("AD1"); cell.setValue("MADDKD");
		    cell = cells.getCell("AE1"); cell.setValue("TENDDKD");
		    
			cell = cells.getCell("AF1"); cell.setValue("THU");
			cell = cells.getCell("AG1"); cell.setValue("TUYENBANHANG");
			cell = cells.getCell("AH1"); cell.setValue("DSTB/NGAY");
			cell = cells.getCell("AI1"); cell.setValue("F2");
			cell = cells.getCell("AJ1"); cell.setValue("F4");
			cell = cells.getCell("AK1"); cell.setValue("F8");
			cell = cells.getCell("AL1"); cell.setValue("F12");
			cell = cells.getCell("AM1"); cell.setValue("TongCong");
			

		}catch(Exception ex){
			throw new Exception("Bao cao bi loi khi khoi tao");
		}
		
	      
	}
	
	private boolean CreateStaticData(Workbook workbook,IStockintransit obj) throws Exception
	{
		dbutils db = new dbutils();
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	    Cells cells = worksheet.getCells();

	    String sql=this.getsql(obj);
	    System.out.println("Cau Lenh Lay Du Lieu : "+sql);
	    ResultSet rs =db.get(sql);
	    
	    	
		
		try{
			cells.setColumnWidth(0, 15.0f);
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

						
			int i=2;			
			if(rs!=null){
			Cell cell = null;
			Style style;
			while(rs.next())// lap den cuoi bang du lieu
			{
			
			
				
			
				cell = cells.getCell("AA" + Integer.toString(i)); cell.setValue(rs.getString("vung")); //0
				cell = cells.getCell("AB" + Integer.toString(i)); cell.setValue(rs.getString("khuvuc"));//1
				cell = cells.getCell("AC" + Integer.toString(i)); cell.setValue(rs.getString("tennpp"));//2
				cell = cells.getCell("AD" + Integer.toString(i)); cell.setValue(rs.getString("ddkdid"));//3
				cell = cells.getCell("AE" + Integer.toString(i)); cell.setValue(rs.getString("tenddkd"));//4
				cell = cells.getCell("AF" + Integer.toString(i)); cell.setValue("Thứ "+rs.getString("NGAYID"));//5
				cell = cells.getCell("AG" + Integer.toString(i)); cell.setValue(rs.getString("tuyenbanhang"));//4
				cell = cells.getCell("AH" + Integer.toString(i)); cell.setValue(rs.getFloat("doanhso"));//6
				cell = cells.getCell("AI" + Integer.toString(i)); cell.setValue(rs.getFloat("F2"));//7									
				cell = cells.getCell("AJ" + Integer.toString(i)); cell.setValue(rs.getFloat("F4"));	//8					
				cell = cells.getCell("AK" + Integer.toString(i)); cell.setValue(rs.getFloat("F8"));	//9	
				cell = cells.getCell("AL" + Integer.toString(i)); cell.setValue(rs.getFloat("F12"));	//9	
				cell = cells.getCell("AM" + Integer.toString(i)); cell.setValue(rs.getFloat("F2") + rs.getFloat("F4")+rs.getFloat("F8")+rs.getFloat("F12"));	//9	
				
									
				i++;
			}
			
			if(rs!=null){
				rs.close();
			}

			if(db!=null){
				db.shutDown();
			}
			
			if(i==2){
				obj.setMsg("Khong co bao cao trong thoi gian nay");
				return false;
			}
	
							
			setHidden(workbook,50);
			  			  
		    return true;
		}else{
			obj.setMsg("Khong co bao cao trong thoi gian nay");
			return false;
		}
	}
	catch(Exception ex)
	{
		System.out.println("Khong The Tao Duoc Bao Cao :" + ex.toString());
		obj.setMsg("Khong The Tao Duoc Bao Cao :"+ex.toString());
		return false;
	}
	}	
	
	private String getsql(IStockintransit obj) {

		String sql=" SELECT V.TEN AS VUNG,KV.TEN AS KHUVUC,  NPP.TEN as tennpp,DDKD.PK_SEQ AS DDKDID,DDKD.TEN as tenddkd, NGAYID,T.DIENGIAI as tuyenbanhang ,DS.DOANHSO/4 as doanhso, "+
		" ISNULL([F2],0) AS [F2],ISNULL([F4],0) AS [F4],ISNULL([F8],0) AS [F8],ISNULL([F12],0) AS [F12] FROM "+ 
		" ( "+
		" SELECT TBH.DDKD_FK,NGAYID,TBH.DIENGIAI , "+
		" ISNULL( COUNT (KH_TBH.PK_SEQ),0) AS SOCH,CASE WHEN TANSO='1 tuan / 1 lan' or TANSO='4' THEN 'F4' ELSE "+ 
		" ( CASE WHEN TANSO='2 tuan / 1 lan-le' or TANSO='2 tuan / 1 lan-chan' or TANSO='2' "+ 
		" THEN 'F2' ELSE ISNULL(TANSO,'F4') END  ) "+ 
		" END AS TANSO FROM TUYENBANHANG TBH  "+
		" INNER JOIN KHACHHANG_TUYENBH KH_TBH ON TBH.PK_SEQ=KH_TBH.TBH_FK "+ 
		" GROUP BY TBH.DDKD_FK,NGAYID,TBH.DIENGIAI ,  (CASE WHEN TANSO='1 tuan / 1 lan' or TANSO='4' THEN 'F4' ELSE "+ 
		" ( CASE WHEN TANSO='2 tuan / 1 lan-le' or TANSO='2 tuan / 1 lan-chan' or TANSO='2'  THEN 'F2' "+
		" ELSE ISNULL(TANSO,'F4') END  )  "+
		" END "+
		" ) "+
		" ) A  "+
		" PIVOT "+
		" ( "+
		" SUM(SOCH) "+
		" FOR TANSO IN ([F2],[F4],[F8],[F12]) "+
		" ) AS T "+
		" INNER JOIN DAIDIENKINHDOANH DDKD ON T.DDKD_FK=DDKD.PK_SEQ "+
		" INNER JOIN NHAPHANPHOI NPP ON DDKD.NPP_FK=NPP.PK_SEQ "+
		" INNER JOIN KHUVUC KV ON KV.PK_SEQ=NPP.KHUVUC_FK "+
		" INNER JOIN VUNG V ON KV.VUNG_FK=V.PK_SEQ "+
		" LEFT JOIN   "+
		" ( "+
		" SELECT DDKD_FK,DATEPART(weekday,NGAYNHAP) AS THU, SUM(SOLUONG*GIAMUA) "+
		" AS DOANHSO FROM DONHANG DH "+
		" INNER JOIN DONHANG_SANPHAM DH_SP ON DH.PK_SEQ=DH_SP.DONHANG_FK "+
		" WHERE DH.NGAYNHAP >= Replace(convert(char(10), DATEADD(day, -30, getdate()) , 102) , '.', '-' ) " +
		"  AND DH.NGAYNHAP <='"+this.getDateTime()+"'  AND DH.TRANGTHAI='1' "+
		" GROUP BY DDKD_FK,DATEPART(weekday,NGAYNHAP) "+
		" ) DS ON DS.THU= T.NGAYID AND T.DDKD_FK=DS.DDKD_FK  where 1=1" +
		"  and npp.pk_seq='"+obj.getnppId()+"'";
		String chuoi="";
		if(obj.getvungId().length() >0) {
			chuoi=chuoi + " and v.pk_seq="+ obj.getvungId();
		}
		if(obj.getkhuvucId().length() > 0) {
			chuoi=chuoi + " and kv.pk_seq="+ obj.getkhuvucId();
		}
		if(obj.getnppId().length() > 0) {
			chuoi=chuoi + " and npp.pk_seq="+ obj.getnppId();
		}
		if(obj.getDdkd().length() > 0) {
			chuoi=chuoi + " and ddkd.pk_seq="+ obj.getDdkd();
		}
	
		sql=sql + chuoi +	" ORDER BY T.DDKD_FK,NGAYID";
		return sql;
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
	
	private void setHidden(Workbook workbook,int i)
	{
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	   	   
	    Cells cells = worksheet.getCells();
	    for(int j = 27; j <= i; j++)
	    { 
	    	cells.hideColumn(j);
	    }
		
	}
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
}
