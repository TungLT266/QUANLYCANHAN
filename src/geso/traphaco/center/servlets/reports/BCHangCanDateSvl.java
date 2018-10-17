package geso.traphaco.center.servlets.reports;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.servlets.report.ReportAPI;
import geso.traphaco.center.util.Utility;
import geso.traphaco.distributor.beans.reports.IBcNghiepVuHT;
import geso.traphaco.distributor.beans.reports.imp.BcNghiepVuHT;

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

public class BCHangCanDateSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	public BCHangCanDateSvl()
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
		IBcNghiepVuHT obj = new BcNghiepVuHT();
		obj.setUserId(userId);
		obj.createRs();
		request.getSession().setAttribute("errors", "");
		session.setAttribute("userId", userId);
		session.setAttribute("obj", obj);
		String nextJSP = "/TraphacoHYERP/pages/Center/BCHangCanDate.jsp";
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
		
		IBcNghiepVuHT obj = new BcNghiepVuHT();
		obj.setUserId(userId);
		
		String khoId = request.getParameter("khoId");
	    if(khoId == null)
	    	khoId = "";
	    obj.setKhoId(khoId);
	    
	    String[] lspIds = request.getParameterValues("loaisanpham");
	    String lspId = "";
	    if(lspIds != null)
	    {
	    	for(int i = 0; i < lspIds.length; i++)
	    		lspId += lspIds[i] + ",";
	    	if(lspId.length() > 0)
	    		lspId = lspId.substring(0, lspId.length() - 1);
	    	obj.setLoaiSpId(lspId);
	    }
	    
	    String[] spIds = request.getParameterValues("spIds");
	    String spId = "";
	    if(spIds != null)
	    {
	    	for(int i = 0; i < spIds.length; i++)
	    		spId += spIds[i] + ",";
	    	if(spId.length() > 0)
	    		spId = spId.substring(0, spId.length() - 1);
	    	obj.setSpId(spId);
	    }
	    
		if (action.equals("tao"))
		{
			response.setContentType("application/xlsm");
			response.setHeader("Content-Disposition", "Attachment; filename=BCHangCanDate" + this.getPiVotName() + ".xlsm");
			OutputStream out = response.getOutputStream();
			try
			{
				CreatePivotTable(out, response, request, userTen, obj); 
				
			} catch (Exception ex)
			{
				request.getSession().setAttribute("errors", ex.getMessage());
			}
		}
		else
		{
			obj.setUserId(userId);
			obj.createRs();
			session.setAttribute("obj", obj);
			session.setAttribute("userId", userId);
			
			String nextJSP = "/TraphacoHYERP/pages/Center/BCHangCanDate.jsp";
			response.sendRedirect(nextJSP);
		}
	}
	
	private void CreatePivotTable(OutputStream out, HttpServletResponse response, HttpServletRequest request, String nguoitao, IBcNghiepVuHT obj) throws Exception
	{
		try
		{
			String strfstream = getServletContext().getInitParameter("path") + "\\BCHangCanDate.xlsm";
			
			FileInputStream fstream = new FileInputStream(strfstream);
			Workbook workbook = new Workbook();
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
			
			dbutils db = new dbutils(); 
	  		Worksheets worksheets = workbook.getWorksheets();
	  		Worksheet worksheet = worksheets.getSheet(0);
	  		this.TaoBaoCao(db,worksheet,"HẾT HẠN VISA SẢN PHẨM", nguoitao, obj);
				
			workbook.save(out);
			fstream.close();
		} catch (Exception ex)
		{
			ex.printStackTrace();
			throw new Exception("Error Message: " + ex.getMessage());
		}
	}
	
	private void TaoBaoCao(dbutils db, Worksheet worksheet, String diengiai, String nguoitao, IBcNghiepVuHT obj) 
	{
		try
		{
			Cells cells = worksheet.getCells();
	   
			for(int i=0;i<30;i++){
				cells.setColumnWidth(i, 20.0f);   
			}
	
			cells.setRowHeight(0, 50.0f);
			Cell cell;

			cell = cells.getCell("B3");
			ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, nguoitao);
			
			cell = cells.getCell("B4");
			ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, getDateTime());
			
			worksheet.setGridlinesVisible(false);
			
			Utility util = new Utility();
			String condition = " and khott_fk in  " + util.quyen_khott(obj.getUserId());
		    if( obj.getKhoId().trim().length() > 0 )
		    	condition += " and khott_fk in ( " + obj.getKhoId() + " ) ";
		    
		    if( obj.getLoaiSpId().trim().length() > 0 )
		    	condition += " and sanpham_fk in ( select PK_SEQ from ERP_SANPHAM where LOAISANPHAM_FK in ( " + obj.getLoaiSpId() + " ) ) ";
		    
		    if( obj.getSpId().trim().length() > 0 )
		    	condition += " and sanpham_fk in ( " + obj.getSpId() + " ) ";
		    
			String query = "select sp.MA, sp.TEN, dv.DONVI, kho.SOLO, sum(kho.SOLUONG), CONVERT(varchar(10), case when kho.ngayhethan like '%/%/%' then convert(datetime, kho.ngayhethan, 103) when kho.ngayhethan like '1900-01-00' then -1 else kho.NGAYHETHAN end, 105) ngayhethan, sp.HANSUDUNG, CONVERT(varchar(10),cast(kho.NGAYNHAPKHO as datetime),105) ngaynhapkho, \n" + 
				"DATEDIFF(d, GETDATE(), case when kho.ngayhethan like '%/%/%' then convert(datetime, kho.ngayhethan, 103) when kho.ngayhethan like '1900-01-00' then -1 else kho.NGAYHETHAN end) conlai \n" +
				"from ERP_KHOTT_SP_CHITIET kho inner join ERP_SANPHAM sp on kho.SANPHAM_FK = sp.PK_SEQ \n" +
				"inner join DONVIDOLUONG dv on sp.DVDL_FK = dv.PK_SEQ \n" +
				"where 1=1 and soluong > 0 \n" + condition +
				"group by sp.MA, sp.TEN, dv.DONVI, kho.SOLO, kho.NGAYHETHAN, sp.HANSUDUNG, kho.NGAYNHAPKHO";
			System.out.println("querybc="+query);
			ResultSet rs = db.get(query);
		 
			ResultSetMetaData rsmd = rs.getMetaData();
			int socottrongSql = rsmd.getColumnCount();
	   
			int countRow = 6;
	   
			while(rs.next())
			{
				for(int i = 1; i <= socottrongSql; i ++)
				{
					cell = cells.getCell(countRow,i-1 );
					if(rsmd.getColumnType(i) == Types.DOUBLE || rsmd.getColumnType(i)==Types.NUMERIC || rsmd.getColumnType(i)==Types.INTEGER )
					{
						double num = rs.getDouble(i);
						cell.setValue(num);
						ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 41);
						if(i == socottrongSql)
						{
							if(num < 365) ReportAPI.setCellBackground(cell, Color.YELLOW, BorderLineType.THIN, false, 41);
							if(num < 180) ReportAPI.setCellBackground(cell, Color.GREEN, BorderLineType.THIN, false, 41);
							if(num < 0) ReportAPI.setCellBackground(cell, Color.RED, BorderLineType.THIN, false, 41);
						}
					}
					else
					{
						cell.setValue(rs.getString(i));
						ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 41);
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

