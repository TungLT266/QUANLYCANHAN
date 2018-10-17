package geso.traphaco.erp.servlets.reports;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.servlets.report.ReportAPI;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.BCHangDiDuong.IBCHangDiDuong;
import geso.traphaco.erp.beans.BCHangDiDuong.imp.BCHangDiDuong;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
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

public class BCHangDiDuongSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	public BCHangDiDuongSvl()
	{
		super();
		
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IBCHangDiDuong obj = new BCHangDiDuong();
		Utility util = new Utility();
		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);
		
		obj.setuserId(userId);
		obj.setdiscount("1");
		obj.setvat("0");
		obj.setdvdlId("");
		obj.settype("1");
		obj.init();
		session.setAttribute("obj", obj);
		session.setAttribute("userId", userId);
		String nextJSP = "/TraphacoHYERP/pages/Erp/BCHangDiDuong.jsp";
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
		IBCHangDiDuong obj = new BCHangDiDuong();
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		
		Utility util = new Utility();
		if (userId == null)
			userId = "";
		obj.setuserId(userId);
		geso.traphaco.distributor.util.Utility Ult = new geso.traphaco.distributor.util.Utility();
		
		String nppId = request.getParameter("nppId");
		nppId = Ult.getIdNhapp(userId);
		obj.setnppId(nppId);
		
		String nppTen = Ult.getTenNhaPP();
		obj.setuserTen(userTen);
		
		String khId = request.getParameter("khId"); // <!---
		if (khId == null)
			khId = "";
		obj.setkhId(khId);
		
		String nccId = request.getParameter("nccId"); // <!---
		if (nccId == null)
			nccId = "";
		obj.setNccId(nccId);
		
		String kenhId = request.getParameter("kenhId"); // <!---
		if (kenhId == null)
			kenhId = "";
		obj.setkenhId(kenhId);
		
		String dvkdId = request.getParameter("dvkdId"); // <!---
		if (dvkdId == null)
			dvkdId = "";
		obj.setdvkdId(dvkdId);
		
		String nhanhangId = request.getParameter("nhanhangId"); // <!---
		if (nhanhangId == null)
			nhanhangId = "";
		obj.setnhanhangId(nhanhangId);
		
		String chungloaiId = request.getParameter("chungloaiId");// <!---
		if (chungloaiId == null)
			chungloaiId = "";
		obj.setchungloaiId(chungloaiId);
		
		String tungay = request.getParameter("Sdays"); // <!---
		if (tungay == null)
			tungay = "";
		obj.settungay(tungay);
		
		String denngay = request.getParameter("Edays");// <!---
		if (denngay == null)
			denngay = "";
		obj.setdenngay(denngay);
		
		String khoId = request.getParameter("khoId"); // <!---
		if (khoId == null)
			khoId = "";
		obj.setkhoId(khoId);
		
		String vat = request.getParameter("vat");
		obj.setvat(vat);
		
		String discount = request.getParameter("discount");
		obj.setdiscount(discount);
		
		String instransit = request.getParameter("instransit");
		obj.setHangDiDuong(instransit);
		
		obj.setsanphamId(util.antiSQLInspection(request.getParameter("sanphamId")) != null ? util.antiSQLInspection(request.getParameter("sanphamId")) : "");
		obj.setnhanhangId(util.antiSQLInspection(request.getParameter("nganhhangId")) != null ? util.antiSQLInspection(request.getParameter("nganhhangId")) : "");
		obj.setHoaDonKmDb(util.antiSQLInspection(request.getParameter("hdkmdb")) != null ? util.antiSQLInspection(request.getParameter("hdkmdb")) : "");
		obj.setHangDiDuong(util.antiSQLInspection(request.getParameter("instransit")) != null ? util.antiSQLInspection(request.getParameter("instransit")) : "");
		
		obj.settype(util.antiSQLInspection(request.getParameter("type")) != null ? util.antiSQLInspection(request.getParameter("type")) : "");	
		obj.setLaytheo(util.antiSQLInspection(request.getParameter("laysolo")) != null ? util.antiSQLInspection(request.getParameter("laysolo")) : "");
		
		obj.setChiTietXNT_Lo(util.antiSQLInspection(request.getParameter("layctnxsolo")) != null ? util.antiSQLInspection(request.getParameter("layctnxsolo")) : "");
		
		
		
		
		String[] fieldsHien = request.getParameterValues("fieldsHien");
		obj.setFieldShow(fieldsHien);
		
		String[] fieldsAn = request.getParameterValues("fieldsAn");
		obj.setFieldHidden(fieldsAn);
		if (!tungay.equals("") && !denngay.equals(""))
		{
			
			String action = request.getParameter("action");
			
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			if (action.equals("tao"))
			{
				response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "Attachment; filename=BaoCaoHangDiDuong" + this.getPiVotName() + ".xlsm");
				OutputStream out = response.getOutputStream();
				try
				{
					CreatePivotTable(out, response, request, obj, userTen); 
					
				} catch (Exception ex)
				{
					obj.setMsg(ex.getMessage());
					request.getSession().setAttribute("errors", ex.getMessage());
				}
			}
		}
		
		obj.init();
		session.setAttribute("obj", obj);
		session.setAttribute("userId", userId);
		
		String nextJSP = "/TraphacoHYERP/pages/Erp/BCHangDiDuong.jsp";
		response.sendRedirect(nextJSP);
		
	}
	
	private void CreatePivotTable(OutputStream out, HttpServletResponse response, HttpServletRequest request, IBCHangDiDuong obj, String nguoitao) throws Exception
	{
		try
		{
			
			String strfstream = getServletContext().getInitParameter("path") + "\\BaoCaoHangDiDuong.xlsm";
			
			FileInputStream fstream = new FileInputStream(strfstream);
			Workbook workbook = new Workbook();
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);

			dbutils db = new dbutils(); 
	  		Worksheets worksheets = workbook.getWorksheets();
	  		Worksheet worksheet = worksheets.getSheet(0);
	  		this.TaoBaoCao(db,worksheet,"BÁO CÁO HÀNG ĐI ĐƯỜNG", nguoitao, obj);
			
			workbook.save(out);
			fstream.close();
			db.shutDown();
		} catch (Exception ex)
		{
			
			ex.printStackTrace();
			throw new Exception("Error Message: " + ex.getMessage());
		}
	}
	
	private void TaoBaoCao(dbutils db, Worksheet worksheet, String diengiai, String nguoitao, IBCHangDiDuong obj)  
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
			
			String[] param = new String[3];
			param[0] = obj.getnppId().equals("") ? null : obj.getnppId();
			param[1] = obj.gettungay().equals("") ? null : obj.gettungay();
			param[2] = obj.getdenngay().equals("") ? null : obj.getdenngay();
			
			ResultSet rs = db.getRsByPro("REPORT_HANGDIDUONG", param);
		 
			ResultSetMetaData rsmd = rs.getMetaData();
			int socottrongSql = rsmd.getColumnCount();
	   
			int countRow = 4;

			for( int i =1 ; i <=socottrongSql ; i ++  )
			{
				cell = cells.getCell(countRow,i-1 );
				Style s = cell.getStyle();
				s.setTextWrapped(true);
				s.setHAlignment(TextAlignmentType.CENTER);
				s.setVAlignment(TextAlignmentType.JUSTIFY);
				cell.setStyle(s);
				ReportAPI.setCellBackground(cell, Color.GRAY, BorderLineType.THIN, false, 0);
    	
				ReportAPI.getCellStyle(cell, "Arial", Color.WHITE, true, 9, rsmd.getColumnName(i)); 
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

