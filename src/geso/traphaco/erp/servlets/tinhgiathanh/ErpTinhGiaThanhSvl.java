package geso.traphaco.erp.servlets.tinhgiathanh;

import geso.traphaco.erp.beans.tinhgiathanh.IErp_Tinhgiathanh;
import geso.traphaco.erp.beans.tinhgiathanh.imp.Erp_Tinhgiathanh;
import geso.dms.center.util.Utility;
import geso.dms.db.sql.dbutils;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.Cell;
import jxl.Sheet;

import com.oreilly.servlet.MultipartRequest;


public class ErpTinhGiaThanhSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	private static final String UPLOAD_DIRECTORY = "c:\\upload\\excel\\";
	
	PrintWriter out;

	public ErpTinhGiaThanhSvl() {
		super(); 
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		this.out  = response.getWriter();

		HttpSession session = request.getSession();	

		Utility util = new Utility();
		out = response.getWriter();

		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);
		out.println(userId);

		if (userId.length() == 0)
			userId = util.antiSQLInspection(request.getParameter("userId"));

		IErp_Tinhgiathanh obj = new Erp_Tinhgiathanh();
		obj.setUserId(userId);
		String msg = "";
		obj.setCongtyId((String) session.getAttribute("congtyId"));
		obj.Init();
		obj.setMsg(msg);
		session.setAttribute("obj", obj);
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpTinhgiathanh.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		HttpSession session = request.getSession();	
		Utility util = new Utility();
		IErp_Tinhgiathanh obj;
		
		String contentType = request.getContentType();
		if ((contentType != null) && (contentType.indexOf("multipart/form-data") >= 0))
		{
			obj = new Erp_Tinhgiathanh();
			obj.setCongtyId("100000");
			
			MultipartRequest multi = new MultipartRequest(request, UPLOAD_DIRECTORY, 20000000, "UTF-8");
			
			String userId = util.antiSQLInspection(multi.getParameter("userId")); 
			obj.setUserId(userId);
			
			String thang = multi.getParameter("thang");
			obj.setthang(thang);
			
			String nam = multi.getParameter("nam");
			obj.setNam(nam);

			Enumeration files = multi.getFileNames();
			String filename = "";

			while (files.hasMoreElements())
			{
				String name = (String) files.nextElement();
				filename = multi.getFilesystemName(name);
				System.out.println("File  " + UPLOAD_DIRECTORY + filename);
			}
			
			String msg = "";
			if (filename != null && filename.length() > 0)
			{
				msg = this.readExcel(UPLOAD_DIRECTORY + filename, obj, session.getAttribute("userId").toString() );
			}
			
			if( msg.equals("OK") )
			{
				//Chạy các bước trong file giá thành
				obj.TinhGiaThanh_TheoMaTP("1");
				obj.setIsView(1);
			}
			else
				obj.setMsg( msg );

			obj.Init();
			session.setAttribute("obj", obj);
			
			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpTinhgiathanh.jsp";
			response.sendRedirect(nextJSP);
			
			return;
		} 
		
		
		String userId = util.antiSQLInspection(request.getParameter("userId"));     
		String action = request.getParameter("action");
		if (action == null){
			action = "";
		}

		String thang =  request.getParameter("thang");
		String nam = request.getParameter("nam");

		obj = new Erp_Tinhgiathanh();
		obj.setthang(thang);
		obj.setNam(nam);
		obj.setUserId(userId);
		obj.setCongtyId((String) session.getAttribute("congtyId"));

		String nhamayid = request.getParameter("nhamayid");
		if (nhamayid == null)
			nhamayid = "";
		obj.setNhaMayId(nhamayid);

		if(action.equals("tinhgiathanh"))
		{
			obj.setUserId(userId);
			
			obj.TinhGiaThanh_TheoMaTP("0");
			
			obj.setIsView(1);
			obj.Init();
			
			session.setAttribute("obj", obj);

			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpTinhgiathanh.jsp";
			response.sendRedirect(nextJSP);
		}
		else if(action.equals("xem"))
		{
			obj.setUserId(userId);
			obj.setIsView(1);
			obj.Init();
			
			session.setAttribute("obj", obj);

			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpTinhgiathanh.jsp";
			response.sendRedirect(nextJSP);
		}
		else
		{
			obj.setUserId(userId);
			
			obj.setIsView(1);
			obj.Init();
			
			session.setAttribute("obj", obj);

			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpTinhgiathanh.jsp";
			response.sendRedirect(nextJSP);
		}

	}
	
	private String readExcel(String fileName, IErp_Tinhgiathanh obj, String userId) 
	{
		dbutils db = new dbutils();
		try
		{
			File inputWorkbook = new File(fileName);
			jxl.Workbook w = null;
			w = jxl.Workbook.getWorkbook(inputWorkbook);
			jxl.Sheet sheet = w.getSheet(0);
			int sodong = sheet.getRows();

			db.getConnection().setAutoCommit(false);
			
			String sql = "delete ERP_GIATHANH_TEMP where congty_fk = '" + obj.getCongtyId() + "' and thang = '" + obj.getthang() + "' and nam = '" + obj.getNam() + "'  ";
			System.out.println("::: DELETE TEMP: " + sql);
			if(!db.update(sql))
			{
				db.getConnection().rollback();
				return "1.Lỗi import: " + sql;
			}
			
			System.out.println("::: DONG: " + sodong + " -- COT: " + sheet.getColumns() );
			for (int i = 3; i < sodong; i++)
			{
				String stt = getValue(sheet, 0, i).trim();
				String masanpham = getValue(sheet, 1, i).trim();
				String tensanpham = getValue(sheet, 2, i).trim();
				String donvi = getValue(sheet, 3, i).trim();
				
				String soluong = getValue(sheet, 4, i).trim().replaceAll(",", "");
				if( soluong.trim().length() <= 0 )
					soluong = "0";
				
				String giathanh = getValue(sheet, 5, i).trim().replaceAll(",", "");
				if( giathanh.trim().length() <= 0 )
					giathanh = "0";
			
				if( masanpham.trim().length() > 0 && giathanh.trim().length() > 0 )
				{
					sql = " insert ERP_GIATHANH_TEMP( congty_fk, thang, nam, stt, masanpham, tensanpham, donvi, soluong, giathanh ) " + 
						  " select '" + obj.getCongtyId() + "', '" + obj.getthang() + "', '" + obj.getNam() + "', N'" + stt + "', N'" + masanpham + "', N'" + tensanpham + "', N'" + donvi + "', '" + soluong + "', '" + giathanh + "' ";
					
					//System.out.println(":: CHEN GIA THANH TEMP: " + sql);
					db.update(sql);
				}
				
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			db.update("rollback");
			return "Vui lòng coi lại file " + e.getMessage();
		}
		
		db.shutDown();
		return "OK";
	
	}
	

	private String getValue(Sheet sheet, int column, int row)
	{
		Cell cell;
		cell = sheet.getCell(column, row);
		try
		{
			return cell.getContents();
		} catch (Exception er)
		{
			return "0";
		}
	}
	
}
