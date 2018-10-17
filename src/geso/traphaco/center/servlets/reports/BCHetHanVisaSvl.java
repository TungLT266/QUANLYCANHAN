package geso.traphaco.center.servlets.reports;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.servlets.report.ReportAPI;
import geso.traphaco.center.util.Utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.sql.Types;

import com.aspose.cells.BorderLineType;
import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

import java.util.*;

public class BCHetHanVisaSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	public BCHetHanVisaSvl()
	{
		super();
		
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		Utility util = new Utility();
		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);
		
		request.getSession().setAttribute("errors", "");
		session.setAttribute("userId", userId);
		String nextJSP = "/TraphacoHYERP/pages/Center/BaoCaoHetHanVisa.jsp";
		response.sendRedirect(nextJSP);
	}
	
	private String getPiVotName()
	{
		String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
		String name = sdf.format(cal.getTime());
		name = name.replaceAll("-", "");
		name = name.replaceAll(" ", "_");
		name = name.replaceAll(":", "");
		return "_" + name;
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		
		Utility util = new Utility();
					
		String action = request.getParameter("action");
			
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		if (action.equals("tao"))
		{
			response.setContentType("application/xlsm");
			response.setHeader("Content-Disposition", "Attachment; filename=BaoCaoHetHanVisaSP" + this.getPiVotName() + ".xlsm");
			OutputStream out = response.getOutputStream();
			try
			{
				CreatePivotTable(out, response, request, userTen); 
				
			} catch (Exception ex)
			{
				request.getSession().setAttribute("errors", ex.getMessage());
			}
		}

		session.setAttribute("userId", userId);
		
		String nextJSP = "/TraphacoHYERP/pages/Center/BaoCaoHetHanVisa.jsp";
		response.sendRedirect(nextJSP);
		
	}
	
	private void CreatePivotTable(OutputStream out, HttpServletResponse response, HttpServletRequest request, String nguoitao) throws Exception
	{
		try
		{
			String strfstream = getServletContext().getInitParameter("path") + "\\BaoCaoHetHanVisaSP.xlsm";
			
			FileInputStream fstream = new FileInputStream(strfstream);
			Workbook workbook = new Workbook();
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
			
			dbutils db = new dbutils(); 
	  		Worksheets worksheets = workbook.getWorksheets();
	  		Worksheet worksheet = worksheets.getSheet(0);
	  		this.TaoBaoCao(db,worksheet,"HẾT HẠN VISA SẢN PHẨM", nguoitao);
				
			workbook.save(out);
			fstream.close();
		} catch (Exception ex)
		{
			ex.printStackTrace();
			throw new Exception("Error Message: " + ex.getMessage());
		}
	}
	
	private void TaoBaoCao(dbutils db, Worksheet worksheet, String diengiai, String nguoitao) 
	{
		try
		{
			Cells cells = worksheet.getCells();
	   
			for(int i=0;i<30;i++){
				cells.setColumnWidth(i, 20.0f);   
			}
	
			cells.setRowHeight(0, 50.0f);
			Cell cell = cells.getCell("A1");
			ReportAPI.getCellStyle(cell, Color.RED, true, 14, diengiai);

			cell = cells.getCell("A3");
			ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Ngày tạo: " + getDateTime());
			
			cell = cells.getCell("A4");
			ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Tạo bởi : " + nguoitao);
			

			worksheet.setGridlinesVisible(false);
			
			String query = "select pk_seq as N'SANPHAM_ID', MA as N'Mã sản phẩm', MA_FAST as N'Mã Fast', TEN as 'Tên sản phẩm', VISA as N'Số Visa', hethanvisa as N'Ngày hết hạn' "
					+ "from SANPHAM "
					+ "where hethanvisa < GETDATE() or hethanvisa is null";
			ResultSet rs = db.get(query);
		 
			ResultSetMetaData rsmd = rs.getMetaData();
			int socottrongSql = rsmd.getColumnCount();
	   
			int countRow = 4;

			for( int i =1 ; i <=socottrongSql ; i ++  )
			{
				cell = cells.getCell(countRow,i-1 );cell.setValue(rsmd.getColumnName(i)); 
			}
			countRow ++;
	   
			while(rs.next())
			{
				for(int i = 1; i <= socottrongSql; i ++)
				{
					cell = cells.getCell(countRow,i-1 );
					if(rsmd.getColumnType(i) == Types.DOUBLE || rsmd.getColumnType(i)==Types.NUMERIC || rsmd.getColumnType(i)==Types.INTEGER )
					{
						cell.setValue(rs.getDouble(i));
					}
					else
					{
						cell.setValue(rs.getString(i));
					}
				}
				++countRow;
			}
	   
			if(rs!=null)
				rs.close();

		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	private String getDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
}

