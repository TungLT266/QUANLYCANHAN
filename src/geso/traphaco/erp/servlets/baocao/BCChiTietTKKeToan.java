package geso.traphaco.erp.servlets.baocao;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

import geso.traphaco.center.servlets.report.ReportAPI;
import geso.traphaco.center.util.Utility;
import geso.traphaco.distributor.db.sql.dbutils;
import geso.traphaco.erp.beans.stockintransit.IStockintransit;
import geso.traphaco.erp.beans.stockintransit.imp.Stockintransit;

public class BCChiTietTKKeToan extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
   
    public BCChiTietTKKeToan() {
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
		
		String view = util.antiSQLInspection(request.getParameter("view"));
		if(view == null) view = "";
		
		obj.setView(view);
		
		obj.getCongTyBaoCao();
		session.setAttribute("obj", obj);
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBCChiTietTKKeToan.jsp";
		response.sendRedirect(nextJSP);
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IStockintransit obj = new Stockintransit();
		String ctyId = (String)session.getAttribute("congtyId");
		obj.setErpCongtyId(ctyId);
		obj.setdiscount("1");
		obj.setvat("1");
		Utility util = new Utility();
		
		String[] ctyIds = request.getParameterValues("ctyIds");
		obj.setCtyIds(ctyIds);
		obj.setView(util.antiSQLInspection(request.getParameter("view")));
		
		obj.setErpCongtyId(ctyId);
		obj.getCongTyBaoCao();
		OutputStream out = response.getOutputStream();
		
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");

		
		session.setAttribute("congtyId", ctyId);
		
		
		obj.setYear(util.antiSQLInspection(request.getParameter("nam")));
		
		obj.setuserId(userId!=null? userId:"");
		obj.setuserTen(userTen!=null? userTen:"");
		
		obj.setErpTienteId(util.antiSQLInspection(request.getParameter("tienteid")));
		
		
		obj.setErpTaiKhoanKTId(util.antiSQLInspection(request.getParameter("taikhoanktid"))!=null?
				util.antiSQLInspection(request.getParameter("taikhoanktid")):"");

		//obj.InitErp();
		String action = (String) util.antiSQLInspection(request.getParameter("action"));
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBCChiTietTKKeToan.jsp";
		
		System.out.println("Action la: " + action);
		if (action.equals("Taomoi")) 
		{			
			response.setContentType("application/xlsm");
			response.setHeader("Content-Disposition", "attachment; filename=BCChiTietTKKeToan.xlsm");
	        try 
	        {
				if(!CreatePivotTable(out, response, request, obj, ""))
				{
					response.setContentType("text/html");
				    PrintWriter writer = new PrintWriter(out);
				    writer.print("Xin loi khong co bao cao trong thoi gian nay");
				    writer.close();
				}
			} 
	        catch (Exception e) 
	        {
				obj.setMsg("Khong the tao bao cao " + e.getMessage());
				e.printStackTrace();
			}
		}else if(action.equals("submit")) {
			try{
			 String[][] table =new String [16][5];//hang/cot
			 	
			 dbutils db=new dbutils();
//			 int i=0;
			 table[0][0]="Tháng";
			 table[0][1]="Nợ";
			 table[0][2]="Có";
			 table[0][3]="Số dư";
			 table[0][4]="Lũy kế";
			 
			 table[1][0]="Đầu kỳ";
			 table[1][1]="";
			 table[1][2]="";
			 table[1][3]="";
			 int namtruoc=Integer.parseInt(obj.getYear())-1;
			 
			 String congTy = obj.getErpCongtyId();
			 if (congTy.trim().endsWith(",")){
				 congTy = congTy.substring(0,congTy.length() -1);
			 }
			 	String sql = "SELECT ISNULL(NO - CO,0) AS DAUKY "
			+ " FROM  "
			+ " (SELECT ROUND(ISNULL(GIATRINOVND,0),0) AS NO , ROUND(ISNULL(GIATRICOVND,0),0) AS CO "
			+ " FROM ERP_TAIKHOAN_NOCO HOTK INNER JOIN ERP_TAIKHOANKT TK ON TK.PK_SEQ=HOTK.TAIKHOANKT_FK "
			+ " WHERE NGUYENTE_FK = " + obj.getErpTienteId() + " AND TK.SOHIEUTAIKHOAN = '" + obj.getErpTaiKhoanKTId().split("--")[0].trim() 
			+ "' AND THANG = 12 AND NAM = " + namtruoc + " AND TK.NPP_FK IN ("+congTy+")  "
			+ " UNION ALL  "
			+ " SELECT ROUND(ISNULL(GIATRINOVND,0),0) AS NO , ROUND(ISNULL(GIATRICOVND,0),0) AS CO "
			+ " FROM  " + geso.traphaco.center.util.Utility.prefixDMS + "ERP_TAIKHOAN_NOCO HOTK INNER JOIN  " + geso.traphaco.center.util.Utility.prefixDMS + "ERP_TAIKHOANKT TK ON TK.PK_SEQ=HOTK.TAIKHOANKT_FK "
			+ " WHERE NGUYENTE_FK = " + obj.getErpTienteId() + " AND TK.SOHIEUTAIKHOAN = '" + obj.getErpTaiKhoanKTId().split("--")[0].trim() 
			+ "' AND THANG = 12 AND NAM = " + namtruoc + " AND TK.NPP_FK IN ("+congTy+"))DATA ";
			 System.out.println("Get sql : "+sql);
			 double dauky=0;
			 double daukybk=0;
			 ResultSet rs=db.get(sql);
			 NumberFormat formatter = new DecimalFormat("#,###,###");
			 if(rs.next()){
			 dauky=rs.getDouble("dauky");
			 daukybk=dauky;
				
			}
			 table[1][4]= formatter.format(dauky);
			 
			 
			/* sql = 	"SELECT CONVERT(INT,SUBSTRING(NGAYGHINHAN, 1, 4)) AS NAM, CONVERT(INT, SUBSTRING(NGAYGHINHAN, 6, 2)) AS THANG, " +
			 		"ISNULL(SUM(NO), 0) AS GIATRINOVND, ISNULL(SUM(CO), 0) AS GIATRICOVND " +
				 	"FROM ERP_PHATSINHKETOAN " +
				 	"WHERE SUBSTRING(NGAYGHINHAN, 1, 4) = '" + obj.getYear()+ "' " +
				 	"AND TAIKHOAN_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT " +
				 	"WHERE SOHIEUTAIKHOAN IN ('" + obj.getErpTaiKhoanKTId().split("--")[0].trim() + "') AND NPP_FK IN ("+congTy+")) " +
				 	"AND TIENTEGOC_FK = " + obj.getErpTienteId() + " " +
				 	"GROUP BY CONVERT(INT,SUBSTRING(NGAYGHINHAN, 1, 4)), CONVERT(INT, SUBSTRING(NGAYGHINHAN, 6, 2)) " +
				 	"ORDER BY CONVERT(INT,SUBSTRING(NGAYGHINHAN, 1, 4)), CONVERT(INT, SUBSTRING(NGAYGHINHAN, 6, 2)) ";*/

/*			 sql=" select thang,nam, isnull(a.giatricovnd, 0) as giatricovnd , isnull(a.giatrinovnd, 0) as giatrinovnd from erp_taikhoan_noco a "+
			  " inner join erp_taikhoankt tkkt on tkkt.pk_seq=a.taikhoankt_fk "+
			  " where nguyente_fk="+obj.getErpTienteId()+" and tkkt.sohieutaikhoan='"+obj.getErpTaiKhoanKTId().split("--")[0]+"' and nam="+obj.getYear()+" order by thang";*/ 
			sql = "SELECT NAM,THANG, ISNULL(SUM(GIATRINOVND),0) AS GIATRINOVND, ISNULL(SUM(GIATRICOVND),0) AS GIATRICOVND \n"
			+ " FROM \n"
			+ " (SELECT CONVERT (INT,SUBSTRING(PS.NGAYGHINHAN,1,4)) AS NAM,CONVERT (INT,SUBSTRING(PS.NGAYGHINHAN,6,2)) AS THANG, \n"
			+ " ROUND(ISNULL(NO,0),0) AS GIATRINOVND , ROUND(ISNULL(CO,0),0) AS GIATRICOVND \n"
			+ " FROM ERP_PHATSINHKETOAN PS INNER JOIN ERP_TAIKHOANKT TK ON TK.PK_SEQ = PS.TAIKHOAN_FK \n"
			+ " WHERE SUBSTRING(PS.NGAYGHINHAN,1,4) = '" + obj.getYear()+ "' AND NPP_FK IN ("+congTy+") \n"
			+ " AND SOHIEUTAIKHOAN = '" + obj.getErpTaiKhoanKTId().split("--")[0].trim() 
			+ "' AND TIENTEGOC_FK =" + obj.getErpTienteId() + " \n"
			+ " UNION ALL \n"
			+ " SELECT CONVERT (INT,SUBSTRING(PS.NGAYGHINHAN,1,4)) AS NAM,CONVERT (INT,SUBSTRING(PS.NGAYGHINHAN,6,2)) AS THANG, \n"
			+ " ROUND(ISNULL(NO,0),0) AS GIATRINOVND , ROUND(ISNULL(CO,0),0) AS GIATRICOVND \n"
			+ " FROM  " + geso.traphaco.center.util.Utility.prefixDMS + "ERP_PHATSINHKETOAN PS INNER JOIN  " + geso.traphaco.center.util.Utility.prefixDMS + "ERP_TAIKHOANKT TK ON TK.PK_SEQ = PS.TAIKHOAN_FK \n"
			+ " WHERE SUBSTRING(PS.NGAYGHINHAN,1,4) = '" + obj.getYear()+ "' AND NPP_FK IN ("+congTy+") \n"
			+ " AND SOHIEUTAIKHOAN = '" + obj.getErpTaiKhoanKTId().split("--")[0].trim() 
			+ "' AND TIENTEGOC_FK =" + obj.getErpTienteId() + ")DATA  \n"
			+ " GROUP BY NAM, THANG \n"
			+ " ORDER BY NAM, THANG";
			System.out.println("Trong ky: " + sql);
			try{
				rs=db.get(sql);
			}catch (Exception ex){
				ex.printStackTrace();
			}
				  	
			double luyke = dauky;
			double totalno=0;
			double totalco=0;
		
			int thang=0;
			 if(rs!=null){
				 while(rs.next()){
				  thang=rs.getInt("thang");
				  System.out.println("Thang : "+thang);
				 int index = thang + 1;
				 table[index][0]=thang +"";
				 table[index][1]= formatter.format(rs.getDouble("giatrinovnd"));
				 totalno = totalno + rs.getDouble("giatrinovnd");
				
				 table[index][2]=formatter.format(rs.getDouble("giatricovnd"));
				 double sodu = rs.getDouble("giatrinovnd") - rs.getDouble("giatricovnd");
				 totalco = totalco + rs.getDouble("giatricovnd");
				 
				 table[index][3]=formatter.format(sodu);
				 
				 //dauky=luyke;
				 luyke = luyke + sodu;
				 table[index][4]=formatter.format(luyke);
				 
				 }
			 }
			 if(thang<12){
				 for(int j=thang+1;j <=12;j++){
					 table[j+1][0]=""+j;
					 table[j+1][1]="0";
					 table[j+1][2]="0";
					 table[j+1][3]="0";
					 table[j+1][4]=formatter.format(luyke);
				 }
			 }
			 for(int j=2;j <14;j++){
				 table[j][0]=""+(j-1);
				
			 }
			 table[14][0]="";
			 table[14][1]="";
			 table[14][2]="";
			 table[14][3]="";
			 table[14][4]="";
			 
			 table[15][0]="Tổng cộng";
			 table[15][1]=formatter.format(totalno);
			 table[15][2]=formatter.format(totalco);
			 table[15][3]=formatter.format(totalno-totalco);
			 
			 table[15][4]=formatter.format(daukybk+ totalno-totalco);
			 
			 System.out.println("leng wid :"+table[0].length);
			 System.out.println("leng  :"+table.length); 
			 
			 obj.setMang2Chieu(table);
			 
			}catch(Exception er){
				er.printStackTrace();
			}
		}
		
		//obj.InitErp();
		session.setAttribute("obj", obj);
		response.sendRedirect(nextJSP);
		return;
	}
	
	private boolean CreatePivotTable(OutputStream out,HttpServletResponse response,HttpServletRequest request,IStockintransit obj, String query) throws Exception 
	{
		boolean isFillData = false;
		FileInputStream fstream = null;
		Workbook workbook = new Workbook();
		
		fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\BaoCaoChiTietTKKeToan.xlsm");
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
	
	private void CreateHeader(Workbook workbook, IStockintransit obj) {
		
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		worksheet.setName("Sheet1");
		ResultSet ctyRs = obj.getRsErpCongty();
		String ctyName = "";
//		String diachi = "";
//		String masothue = "";
		
		try{
			if(ctyRs != null){
				ctyRs.next();
			
				ctyName = ctyRs.getString("TEN");
//				diachi =  ctyRs.getString("DIACHI");
//				masothue =  ctyRs.getString("MASOTHUE");
				
				ctyRs.close();
			}
		}catch(java.sql.SQLException e){
			e.printStackTrace();
		}
		
		Cells cells = worksheet.getCells();

		cells.setRowHeight(0, 20.0f);
		Cell cell = cells.getCell("A1");
		ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, ctyName);
	    
		cells.setRowHeight(0, 20.0f);
		cell = cells.getCell("C3");
		ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, "BÁO CÁO TỔNG HỢP TÀI KHOẢN KẾ TOÁN");

		cells.setRowHeight(1, 20.0f);
	    cell = cells.getCell("A4");
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, "Số tài khoản: "); 
	    
	    cell = cells.getCell("B4");
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, obj.getErpTaiKhoanKTId()); 

	    cells.setRowHeight(2, 20.0f);
	    cell = cells.getCell("A5");
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, "Năm: "); 
	    
	    cell = cells.getCell("B5");
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, obj.getDate().substring(0, 4)); 

	    cells.setRowHeight(6, 20.0f);
	    cell = cells.getCell("A6");
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, "Tiền tệ: "); 

	    cell = cells.getCell("B6");
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, obj.getErpTiente()); 
	}

	private boolean FillData(Workbook workbook, IStockintransit obj, String query) throws Exception
	{
		dbutils db = new dbutils();
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		
		Cells cells = worksheet.getCells();	
		
		for(int i = 0;i < 7; ++i)
		{
	    	cells.setColumnWidth(i, 15.0f);
	    	if(i == 4)
	    		cells.setColumnWidth(i, 30.0f);
	    }	
		
		 
		 int namtruoc=Integer.parseInt(obj.getYear())-1;
		 		 
		 String congTy = obj.getErpCongtyId().substring(0,obj.getErpCongtyId().length() -1);
		 String sql = "SELECT ISNULL(NO - CO,0) AS DAUKY "
					+ " FROM  "
					+ " (SELECT ROUND(ISNULL(GIATRINOVND,0),0) AS NO , ROUND(ISNULL(GIATRICOVND,0),0) AS CO "
					+ " FROM ERP_TAIKHOAN_NOCO HOTK INNER JOIN ERP_TAIKHOANKT TK ON TK.PK_SEQ=HOTK.TAIKHOANKT_FK "
					+ " WHERE NGUYENTE_FK = " + obj.getErpTienteId() + " AND TK.SOHIEUTAIKHOAN = '" + obj.getErpTaiKhoanKTId().split("--")[0].trim() 
					+ "' AND THANG = 12 AND NAM = " + namtruoc + " AND TK.NPP_FK IN ("+congTy+")  "
					+ " UNION ALL  "
					+ " SELECT ROUND(ISNULL(GIATRINOVND,0),0) AS NO , ROUND(ISNULL(GIATRICOVND,0),0) AS CO "
					+ " FROM  " + geso.traphaco.center.util.Utility.prefixDMS + "ERP_TAIKHOAN_NOCO HOTK INNER JOIN  " + geso.traphaco.center.util.Utility.prefixDMS + "ERP_TAIKHOANKT TK ON TK.PK_SEQ=HOTK.TAIKHOANKT_FK "
					+ " WHERE NGUYENTE_FK = " + obj.getErpTienteId() + " AND TK.SOHIEUTAIKHOAN = '" + obj.getErpTaiKhoanKTId().split("--")[0].trim() 
					+ "' AND THANG = 12 AND NAM = " + namtruoc + " AND TK.NPP_FK IN ("+congTy+"))DATA ";
		 System.out.println("SQL Đầu Kỳ : "+sql);
		 double dauky=0;
		 ResultSet rs=db.get(sql);
		 
		 Cell cell;
		 if(rs.next()){
			 dauky=rs.getDouble("dauky");
				cell = cells.getCell("E10");		cell.setValue( dauky);	
				
			}
		 
		 sql = "SELECT NAM,THANG, ISNULL(SUM(GIATRINOVND),0) AS GIATRINOVND, ISNULL(SUM(GIATRICOVND),0) AS GIATRICOVND \n"
					+ " FROM \n"
					+ " (SELECT CONVERT (INT,SUBSTRING(PS.NGAYGHINHAN,1,4)) AS NAM,CONVERT (INT,SUBSTRING(PS.NGAYGHINHAN,6,2)) AS THANG, \n"
					+ " ROUND(ISNULL(NO,0),0) AS GIATRINOVND , ROUND(ISNULL(CO,0),0) AS GIATRICOVND \n"
					+ " FROM ERP_PHATSINHKETOAN PS INNER JOIN ERP_TAIKHOANKT TK ON TK.PK_SEQ = PS.TAIKHOAN_FK \n"
					+ " WHERE SUBSTRING(PS.NGAYGHINHAN,1,4) = '" + obj.getYear()+ "' AND NPP_FK IN ("+congTy+") \n"
					+ " AND SOHIEUTAIKHOAN = '" + obj.getErpTaiKhoanKTId().split("--")[0].trim() 
					+ "' AND TIENTEGOC_FK =" + obj.getErpTienteId() + " \n"
					+ " UNION ALL \n"
					+ " SELECT CONVERT (INT,SUBSTRING(PS.NGAYGHINHAN,1,4)) AS NAM,CONVERT (INT,SUBSTRING(PS.NGAYGHINHAN,6,2)) AS THANG, \n"
					+ " ROUND(ISNULL(NO,0),0) AS GIATRINOVND , ROUND(ISNULL(CO,0),0) AS GIATRICOVND \n"
					+ " FROM  " + geso.traphaco.center.util.Utility.prefixDMS + "ERP_PHATSINHKETOAN PS INNER JOIN  " + geso.traphaco.center.util.Utility.prefixDMS + "ERP_TAIKHOANKT TK ON TK.PK_SEQ = PS.TAIKHOAN_FK \n"
					+ " WHERE SUBSTRING(PS.NGAYGHINHAN,1,4) = '" + obj.getYear()+ "' AND NPP_FK IN ("+congTy+") \n"
					+ " AND SOHIEUTAIKHOAN = '" + obj.getErpTaiKhoanKTId().split("--")[0].trim() 
					+ "' AND TIENTEGOC_FK =" + obj.getErpTienteId() + ")DATA  \n"
					+ " GROUP BY NAM, THANG \n"
					+ " ORDER BY NAM, THANG";
		 	System.out.println("Trong ky: " + sql);
		 
		
/*		sql=" select thang,nam, isnull(a.giatricovnd, 0) as giatricovnd, isnull(a.giatrinovnd, 0) as giatrinovnd from erp_taikhoan_noco a "+
		  " inner join erp_taikhoankt tkkt on tkkt.pk_seq=a.taikhoankt_fk "+
		  " where nguyente_fk="+obj.getErpTienteId()+" " +
		  " and tkkt.sohieutaikhoan='"+obj.getErpTaiKhoanKTId().split("--")[0]+"' and nam="+obj.getYear()+" order by thang";*/ 
		 //System.out.println(sql);
		 
		 rs = db.get(sql);
		 int index = 0;
		 if (rs != null) 
		 {
			try 
			{
				
				while (rs.next())
				{		
					
					index  = rs.getInt("thang")+10;
					
					cell = cells.getCell("B"+String.valueOf(index));
					cell.setValue(rs.getDouble("giatrinovnd"));	
						
					cell = cells.getCell("C"+String.valueOf(index));
					cell.setValue(rs.getDouble("giatricovnd"));
					cell = cells.getCell("D"+String.valueOf(index));
					double sodu= rs.getDouble("giatrinovnd")-rs.getDouble("giatricovnd");
					cell.setValue(sodu);
						
						//dauky=dauky+sodu;
						//cell = cells.getCell("E"+String.valueOf(index));
						//cell.setValue(dauky);
									
				}

				if (rs != null){
					rs.close();
				}
				
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				throw new Exception(ex.getMessage());
			}
		}
		else
		{
			return false;
		}	
		if(db != null)
			db.shutDown();
		return true;
	}	
}