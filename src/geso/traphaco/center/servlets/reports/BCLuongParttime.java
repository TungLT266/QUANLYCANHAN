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

public class BCLuongParttime extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public BCLuongParttime()
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
		String nextJSP = "/TraphacoHYERP/pages/Center/BCLuongParttime.jsp";
		
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
				response.setHeader("Content-Disposition", "attachment; filename=BCLuongParttime.xlsm");
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
		String nextJSP = "/TraphacoHYERP/pages/Center/BCLuongParttime.jsp";
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
						 "\n select cast(ddkd.PK_SEQ as varchar) as [Mã NV],ddkd.TEN as [Tên NV],isnull(KBH,'')KBH,isnull(sp.ten,'') as [Tên SP],isnull(DDKD_SP.DOANHSO,0)/100 [TL Thưởng],isnull(thucdat.doanhso,0) as doanhso   " + 
						 "\n 		, ( isnull(thucdat.doanhso,0) * isnull(DDKD_SP.DOANHSO,0))/100 as [Thưởng]  " + 
						 "\n from DAIDIENKINHDOANH ddkd  " + 
						 "\n left join  DDKD_SP on DDKD_SP.DDKD_FK =ddkd.PK_SEQ and DDKD_SP.DOANHSO > 0 " + 
						 "\n left join  " + 
						 "\n (  " + 
						 "\n 	select DDKD_FK,max(b.TEN) as kbh from DAIDIENKINHDOANH_KBH a  " + 
						 "\n 	inner join KENHBANHANG b  on a.KBH_FK = b.PK_SEQ  " + 
						 "\n 	inner join hethongbanhang_kenhbanhang c on c.kbh_fk = b.PK_SEQ   " + 
						 "\n 	group by a.DDKD_FK  " + 
						 "\n ) kbh  on kbh.DDKD_FK = ddkd.PK_SEQ  " + 
						 "\n left join  " + 
						 "\n (  " + 
						 "\n 	select ddh.DDKD_FK,hd_sp.SANPHAM_FK,SUM(hd_sp.DONGIA * hd_sp.SOLUONG) as doanhso  " + 
						 "\n 	from ERP_HOADONNPP hd inner join ERP_HOADONNPP_DDH hd_dh on hd.PK_SEQ = hd_dh.HOADONNPP_FK     " + 
						 "\n 	inner join ERP_DONDATHANGNPP ddh on  hd_dh.DDH_FK = ddh.PK_SEQ      " + 
						 "\n 	inner join ERP_HOADONNPP_SP hd_sp on hd.PK_SEQ = hd_sp.HOADON_FK     " + 
						 "\n 	inner join DDKD_SP on DDKD_SP.DDKD_FK = ddh.DDKD_FK and DDKD_SP.SP_FK = hd_sp.SANPHAM_FK and isnull(DDKD_SP.DOANHSO,0) >0  " + 
						 "\n 	where hd.TRANGTHAI = 4 and ddh.DDKD_FK is not null    " + 
						 "\n 	and hd.NgayXuatHD >=@dauthang and  hd.NgayXuatHD <=@cuoithang    " + 
						 "\n 	group by ddh.DDKD_FK,hd_sp.SANPHAM_FK   " + 
						 "\n   " + 
						 "\n )thucdat on thucdat.DDKD_FK = ddkd.PK_SEQ and   thucdat.SANPHAM_FK = DDKD_SP.SP_FK  " + 
						 "\n left join SANPHAM sp on sp.PK_SEQ = DDKD_SP.SP_FK  " + 
						 "\n where isnull(KYHOPDONG,0) = 0  " + 
						 "\n order by ddkd.TEN " ;
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
			ReportAPI.getCellStyle(cell, Color.RED, true, 16,"Lương part time TDV");

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
			
			String manvOld = "";
			String tennvOld ="";
			int batdaukhuvuc = countRow;
			
			while(rs.next())
			{
				
				if(manvOld.trim().length() > 0 && !manvOld.equals(rs.getString("Mã NV")) )
				{
					for(int i =1;i <=socottrongSql ; i ++)
					{
						if(rsmd.getColumnName(i).equals("Tên NV"))
						{
							cell = cells.getCell(countRow,i-1 );
							cell.setValue(tennvOld);
							ReportAPI.setCellBackground(cell, new Color(238,236,225), BorderLineType.THIN, false, 0);
						}else
							if(rsmd.getColumnName(i).equals("Mã NV"))
							{
								cell = cells.getCell(countRow,i-1 );
								cell.setValue(manvOld);
								ReportAPI.setCellBackground(cell, new Color(238,236,225), BorderLineType.THIN, false, 0);
							}else
						if(rsmd.getColumnName(i).equals("Thưởng"))
						{
							cell = cells.getCell(countRow,i-1 );
							String formula = "=SUM("+ReportAPI.GetExcelColumnName(i)+(batdaukhuvuc+1 ) +":"+ReportAPI.GetExcelColumnName(i)+(countRow)+")";
							cell.setFormula(formula);
							ReportAPI.setCellBackground(cell, new Color(238,236,225), BorderLineType.THIN, false, 41);
						}
						else
						{
							cell = cells.getCell(countRow,i-1 );
							cell.setValue("");
							ReportAPI.setCellBackground(cell, new Color(238,236,225), BorderLineType.THIN, false, 0);
						}
					}
					countRow ++;
					batdaukhuvuc = countRow;
				}
				manvOld =rs.getString("Mã NV");
				tennvOld =rs.getString("Tên NV");

				
				for(int i =1;i <=socottrongSql ; i ++)
				{
					cell = cells.getCell(countRow,i-1 );
					if(rsmd.getColumnType(i) == Types.DOUBLE ||rsmd.getColumnType(i) == Types.INTEGER||rsmd.getColumnType(i) == Types.NUMERIC)
					{
						
						cell.setValue(rs.getDouble(i));
						if(rsmd.getColumnName(i).indexOf("TL")==0 )
							ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 10);
						else
							ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 41);
					}
					else
					{
						cell.setValue(rs.getString(i));
						ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					}
				}
				++countRow;
			}
			if(countRow >9 )
			{
				for(int i =1;i <=socottrongSql ; i ++)
				{
					if(rsmd.getColumnName(i).equals("Tên NV"))
					{
						cell = cells.getCell(countRow,i-1 );
						cell.setValue(tennvOld);
						ReportAPI.setCellBackground(cell, new Color(238,236,225), BorderLineType.THIN, false, 0);
					}else
						if(rsmd.getColumnName(i).equals("Mã NV"))
						{
							cell = cells.getCell(countRow,i-1 );
							cell.setValue(manvOld);
							ReportAPI.setCellBackground(cell, new Color(238,236,225), BorderLineType.THIN, false, 0);
						}else
					if(rsmd.getColumnName(i).equals("Thưởng"))
					{
						cell = cells.getCell(countRow,i-1 );
						String formula = "=SUM("+ReportAPI.GetExcelColumnName(i)+(batdaukhuvuc+1 ) +":"+ReportAPI.GetExcelColumnName(i)+(countRow)+")";
						cell.setFormula(formula);
						ReportAPI.setCellBackground(cell, new Color(238,236,225), BorderLineType.THIN, false, 41);
					}
					else
					{
						cell = cells.getCell(countRow,i-1 );
						cell.setValue("");
						ReportAPI.setCellBackground(cell, new Color(238,236,225), BorderLineType.THIN, false, 0);
					}
				}
				countRow ++;
				batdaukhuvuc = countRow;
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
