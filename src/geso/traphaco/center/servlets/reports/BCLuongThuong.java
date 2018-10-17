package geso.traphaco.center.servlets.reports;

import geso.traphaco.center.beans.stockintransit.IStockintransit;
import geso.traphaco.center.beans.stockintransit.imp.Stockintransit;
import geso.traphaco.center.servlets.report.ReportAPI;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.ServletException;
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
import com.aspose.cells.TextAlignmentType;

import com.aspose.cells.Style;

import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

public class BCLuongThuong extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public BCLuongThuong()
	{
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IStockintransit obj = new Stockintransit();
		Utility util = new Utility();

		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);
		obj.setuserId(userId);
		obj.init();

		session.setAttribute("obj", obj);
		session.setAttribute("userId", userId);
		String nextJSP = "/TraphacoHYERP/pages/Center/BCLuongThuong.jsp";
		
		   String view = request.getParameter("view");
		    if(view == null)
		    	view = "";
		response.sendRedirect(nextJSP);   
		
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IStockintransit obj = new Stockintransit();
		Utility util = new Utility();

		obj.setuserId((String) session.getAttribute("userId") == null ? "" : (String) session.getAttribute("userId"));

		obj.setuserTen((String) session.getAttribute("userTen") == null ? "" : (String) session.getAttribute("userTen"));

		obj.setnppId(util.antiSQLInspection(request.getParameter("nppId")) == null ? "" : util.antiSQLInspection(request.getParameter("nppId")));

		obj.setkenhId(util.antiSQLInspection(request.getParameter("kenhId")) == null ? "" : util.antiSQLInspection(request.getParameter("kenhId")));

		obj.setdvkdId(util.antiSQLInspection(request.getParameter("dvkdId")) == null ? "" : util.antiSQLInspection(request.getParameter("dvkdId")));

		obj.setMonth(util.antiSQLInspection(request.getParameter("month")) == null ? "" : util.antiSQLInspection(request.getParameter("month")));

		obj.setYear(util.antiSQLInspection(request.getParameter("year")) == null ? "" : util.antiSQLInspection(request.getParameter("year")));

		obj.setvungId(util.antiSQLInspection(request.getParameter("vungId")) == null ? "" : util.antiSQLInspection(request.getParameter("vungId")));

		obj.setkhuvucId(util.antiSQLInspection(request.getParameter("khuvucId")) == null ? "" : util.antiSQLInspection(request.getParameter("khuvucId")));

		obj.setdvdlId(util.antiSQLInspection(request.getParameter("dvdlId")) == null ? "" : util.antiSQLInspection(request.getParameter("dvdlId")));

		obj.setDdkd(util.antiSQLInspection(request.getParameter("ddkdId")) == null ? "" : util.antiSQLInspection(request.getParameter("ddkdId")));

		obj.setgsbhId(util.antiSQLInspection(request.getParameter("gsbhId")) == null ? "" : util.antiSQLInspection(request.getParameter("gsbhId")));

		String[] fieldsHien = request.getParameterValues("fieldsHien");
		obj.setFieldShow(fieldsHien);

		String userId = request.getParameter("userId");
		String view=request.getParameter("view");
		if(view == null)
			view = "";
		
	    String nppId ="";
		if(view.equals("TT"))
		{
			 nppId = util.antiSQLInspection(request.getParameter("nppId"));
			if (nppId == null)
				nppId = "";
			obj.setnppId(nppId);
		}else
		{
			nppId=util.getIdNhapp(userId);
			obj.setnppId(nppId);
		}
     	
	

		String action = util.antiSQLInspection(request.getParameter("action"));
		if (action.equals("Taomoi"))
		{
			try
			{
				response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "attachment; filename=BaoCaoLuongThuongTDV.xlsm");
				OutputStream out = response.getOutputStream();
				
				String query = setQuery(obj,request);
				ExportToExcel(out, obj, query);
			} catch (Exception ex)
			{
				ex.printStackTrace();
				obj.init();
				session.setAttribute("obj", obj);
				session.setAttribute("userId", obj.getuserId());
				obj.setMsg(ex.getMessage());
			}
		} else
		{
			obj.init();
			session.setAttribute("obj", obj);
			session.setAttribute("userId", obj.getuserId());
		}
		String nextJSP = "/TraphacoHYERP/pages/Center/BCLuongThuong.jsp";
		response.sendRedirect(nextJSP);

		
	}



	private String setQuery(IStockintransit obj,HttpServletRequest request)
	{
		String view = request.getParameter("view");
		if (view == null)
			view = "";
		String query = 
					// Khai báo 
						 
						 "\n declare @thang int ="+ obj.getMonth() + 
						 "\n declare @nam int = "+obj.getYear()+ 
						 "\n declare @dauthang varchar(10) ='"+( obj.getYear() +"-" + (obj.getMonth().trim().length()< 2? "0"+obj.getMonth():obj.getMonth()+"" ) +"-01"  )+"'  " + 
						 "\n declare @cuoithang varchar(10)   = CONVERT(char(10),DATEADD(DAY,-(DAY(@dauthang)),DATEADD(MONTH,1,@dauthang)),120)  " + 
						 "\n   select isnull((select ten from HETHONGBANHANG where pk_seq = kpi.HTBH_FK ),'') [HTBH] ,cast(ddkd.PK_SEQ as varchar) as [Mã NV],ddkd.TEN as [Tên NV],isnull(KBH,'') KBH ,lcbct.luongcoban,isnull(KPI.Thuong,0) KPI  " + 
						 "\n 		, luongcoban + isnull(KPI.Thuong,0)  as [Thu Nhập]  " + 
						 "\n   from UploadLuongCoBan lcb  " + 
						 "\n   inner join UpLoadLuongCoBan_SR lcbct on lcb.pk_seq = lcbct.uploadluongcoban_fk  " + 
						 "\n   inner join DAIDIENKINHDOANH ddkd on ddkd.PK_SEQ = lcbct.nhanvien_fk   " + 
						 "\n   left join    " + 
						 "\n   (     " + 
						 "\n   	select DDKD_FK,max(b.TEN) as kbh from DAIDIENKINHDOANH_KBH a    " + 
						 "\n   	inner join KENHBANHANG b  on a.KBH_FK = b.PK_SEQ    " + 
						 "\n   	group by a.DDKD_FK    " + 
						 "\n   ) kbh  on kbh.DDKD_FK = ddkd.PK_SEQ   " + 
						 "\n   left join LuongKPI_SR kpi on KPI.nhanvien_fk = lcbct.nhanvien_fk  and KPI.thang = @thang and KPI.nam = @nam  " +
						 "\n where lcb.thang = @thang and lcb.nam = @nam " ;
			System.out.println("1.Query SalesRep : " + query);
		return query;
	}

	private void TaoBaoCao(Workbook workbook,IStockintransit obj,String query )throws Exception
	{
		try{
			
			
			Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet(0);
			worksheet.setName("Sheet1");

			Cells cells = worksheet.getCells();
			cells.setRowHeight(0, 50.0f);
			Cell cell = cells.getCell("A1");
			ReportAPI.getCellStyle(cell, Color.RED, true, 16,"Lương Thưởng TDV");

			cell = cells.getCell("A3");
			ReportAPI.getCellStyle(cell, Color.BLUE, true, 10, "Ngày tạo : "+ obj.getDateTime());
			cell = cells.getCell("A4");
			ReportAPI.getCellStyle(cell, Color.BLUE, true, 10,"Người tạo : " + obj.getuserTen());

			worksheet.setGridlinesVisible(false);
			dbutils db = new dbutils();
			ResultSet rs = db.get(query);
			
			ResultSetMetaData rsmd = rs.getMetaData();
			int socottrongSql = rsmd.getColumnCount();
			
			int countRow = 8;
			for( int i =1 ; i <=socottrongSql ; i ++  )
			{				
				String columnName = rsmd.getColumnName(i);			
				cell = cells.getCell(countRow,i-1 );cell.setValue(columnName);
				ReportAPI.setCellBackground(cell,  new Color(197,217,241), BorderLineType.THIN, false, 0);
			 
			}
			countRow ++;
			
			while(rs.next())
			{

				
				Color c = Color.WHITE;
				
				for(int i =1;i <=socottrongSql ; i ++)
				{
					
					
					
					cell = cells.getCell(countRow,i-1 );
					if(rsmd.getColumnType(i) == Types.DOUBLE ||rsmd.getColumnType(i) == Types.INTEGER||rsmd.getColumnType(i) == Types.NUMERIC)
					{
						System.out.println(" Types.DOUBLE||||||||||  = "  +  rsmd.getColumnType(i) );
						cell.setValue(rs.getDouble(i));
						if(rsmd.getColumnName(i).indexOf("TL")==0 )
							ReportAPI.setCellBackground(cell, c, BorderLineType.THIN, false, 10);
						else
							ReportAPI.setCellBackground(cell, c, BorderLineType.THIN, false, 41);
					}
					else
					{
						cell.setValue(rs.getString(i));
						ReportAPI.setCellBackground(cell, c, BorderLineType.THIN, false, 0);
					}
				}
				++countRow;
			}

			if(rs!=null)rs.close();
			if(db!=null){
				db.shutDown();
			}

	
		}catch(Exception ex){
			
			System.out.println("Errrorr : "+ex.toString());
			throw new Exception("Qua trinh dien du lieu vao file Excel va tao PivotTable bi loi.!!!");
		}
	}
	
	private void ExportToExcel(OutputStream out,IStockintransit obj,String query )throws Exception
	 {
		try{ 			
			Workbook workbook = new Workbook();
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
			TaoBaoCao(workbook,obj,query);		
			workbook.save(out);	

		}catch(Exception ex){
			throw new Exception(ex.getMessage());
		}
		
	}
	
	
	
}
