package geso.traphaco.center.servlets.chitieunhanvienetc;

import geso.traphaco.center.beans.chitieunhanvienetc.IChiTieuNhanvienETC;
import geso.traphaco.center.beans.chitieunhanvienetc.imp.ChiTieuNhanvienETC;
import geso.traphaco.center.util.Utility;
import java.io.File;
import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;

import jxl.Cell;
import jxl.Sheet;

public class ChiTieuNhanvienETCNewSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	private static final String UPLOAD_DIRECTORY = "c:\\upload\\excel\\";

	public ChiTieuNhanvienETCNewSvl()
	{
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		HttpSession session = request.getSession();
		String querystring = request.getQueryString();
		Utility util = new Utility();
		String userId = util.getUserId(querystring);
		if (userId.length() == 0)
			userId = util.antiSQLInspection(request.getParameter("userId"));
		String id = util.getId(querystring);

		IChiTieuNhanvienETC obj = new ChiTieuNhanvienETC(id);
		obj.setUserId(userId);
		obj.init();
		
		String action = util.getAction(querystring);
		if (action.equals("display"))
			obj.setDisplay("1");

		session.setAttribute("userId", userId);
		session.setAttribute("obj", obj);

		String nextJSP = "/TraphacoHYERP/pages/Center/ChiTieuNhanvienETCUpdate.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		String contentType = request.getContentType();

		Utility util = new Utility();
		
		if ((contentType != null) && (contentType.indexOf("multipart/form-data") >= 0))
		{
			MultipartRequest multi = new MultipartRequest(request, UPLOAD_DIRECTORY, 20000000, "UTF-8");
			
			String id = util.antiSQLInspection(multi.getParameter("id"));
			IChiTieuNhanvienETC obj = new ChiTieuNhanvienETC(id);
			
			String userId = util.antiSQLInspection(multi.getParameter("userId"));
			obj.setUserId(userId);
			
			String nppId  =  util.antiSQLInspection(multi.getParameter("nppId"));
			obj.setNppId(nppId);
			
			String isDisplay  =  util.antiSQLInspection(multi.getParameter("isDisplay"));
			obj.setDisplay(isDisplay);
			
			String thang  =  util.antiSQLInspection(multi.getParameter("thang"));
			obj.setThang( thang );
			
			String nam  =  util.antiSQLInspection(multi.getParameter("nam"));
			obj.setNam(nam);
			
			String loaichitieu = util.antiSQLInspection(multi.getParameter("loaichitieu"));
			obj.setLoai(loaichitieu);
			
			String diengiai  =  util.antiSQLInspection(multi.getParameter("diengiai"));
			obj.setDienGiai(diengiai);

			String loai = util.antiSQLInspection(multi.getParameter("loaichitieu"));
			obj.setLoai(loai);
			
			Enumeration files = multi.getFileNames();
			String filename = "";
			while (files.hasMoreElements())
			{
				String name = (String) files.nextElement();
				filename = multi.getFilesystemName(name);
				System.out.println("File  " + UPLOAD_DIRECTORY + filename);
			}
			if (filename != null && filename.length() > 0)
				obj.setMessage(this.readExcel(UPLOAD_DIRECTORY + filename , obj));

			session.setAttribute("obj", obj);
			
			String nextJSP = "/TraphacoHYERP/pages/Center/ChiTieuNhanvienETCUpdate.jsp";
			response.sendRedirect(nextJSP);
		}
		else
		{
			String id = util.antiSQLInspection(request.getParameter("id"));
			IChiTieuNhanvienETC obj = new ChiTieuNhanvienETC(id);
			
			String nppId = util.antiSQLInspection(request.getParameter("nppId"));
			obj.setNppId(nppId);
			
			String isDisplay  =  util.antiSQLInspection(request.getParameter("isDisplay"));
			obj.setDisplay(isDisplay);
			
			String thang  =  util.antiSQLInspection(request.getParameter("thang"));
			obj.setThang( thang );
			
			String nam  =  util.antiSQLInspection(request.getParameter("nam"));
			obj.setNam(nam);
			
			String loaichitieu = util.antiSQLInspection(request.getParameter("loaichitieu"));
			obj.setLoai(loaichitieu);
			
			String diengiai  =  util.antiSQLInspection(request.getParameter("diengiai"));
			obj.setDienGiai(diengiai);

			String userId = util.antiSQLInspection(request.getParameter("userId"));
			obj.setUserId(userId);

			String loai = util.antiSQLInspection(request.getParameter("loaichitieu"));
			obj.setLoai(loai);
			
			String[] codeTDV = request.getParameterValues("codeTDV");
			obj.setCodeTDV(codeTDV);
			
			String[] tenTDV = request.getParameterValues("tenTDV");
			obj.setTenTDV(tenTDV);
			
			String[] hangchienluoc = request.getParameterValues("hangchienluoc");
			obj.setHangchienluoc(hangchienluoc);
			
			String[] hangdactri = request.getParameterValues("hangdactri");
			obj.setHangdactri(hangdactri);
			
			String[] dskhoan = request.getParameterValues("dskhoan");
			obj.setTongdskhoan(dskhoan);
			
			String[] kpiChienluoc = request.getParameterValues("kpiChienluoc");
			obj.setKPIChienluoc(kpiChienluoc);
			
			String[] kpiDactri = request.getParameterValues("kpiDactri");
			obj.setKPIDactri(kpiDactri);

			String action = request.getParameter("action");
			if( action.equals("save") )
			{
				if( id.trim().length() <= 0 )
				{
					if( !obj.Create() )
					{
						String nextJSP = "/TraphacoHYERP/pages/Center/ChiTieuNhanvienETCUpdate.jsp";		
						session.setAttribute("obj", obj);
						response.sendRedirect(nextJSP);
					}
					else
					{
						obj = new ChiTieuNhanvienETC();
						obj.setUserId(userId);
						obj.initCtLict("");

						session.setAttribute("obj", obj);
						String nextJSP = "/TraphacoHYERP/pages/Center/ChiTieuNhanvienETC.jsp";
						response.sendRedirect(nextJSP);
					}
				}
				else
				{
					if( !obj.Update() )
					{
						String nextJSP = "/TraphacoHYERP/pages/Center/ChiTieuNhanvienETCUpdate.jsp";		
						session.setAttribute("obj", obj);
						response.sendRedirect(nextJSP);
					}
					else
					{
						obj = new ChiTieuNhanvienETC();
						obj.setUserId(userId);
						obj.initCtLict("");

						session.setAttribute("obj", obj);
						String nextJSP = "/TraphacoHYERP/pages/Center/ChiTieuNhanvienETC.jsp";
						response.sendRedirect(nextJSP);
					}
				}
			}
			else
			{
				//THAY ĐỔI LOẠI CHỈ TIÊU, RESET LẠI DANH SÁCH SẢN PHẨM
				obj.setCodeTDV(null);
				
				String nextJSP = "/TraphacoHYERP/pages/Center/ChiTieuNhanvienETCUpdate.jsp";		
				session.setAttribute("obj", obj);
				response.sendRedirect(nextJSP);
			}
		}
	}
	

	private String readExcel(String fileName, IChiTieuNhanvienETC obj) 
	{
		try
		{
			File inputWorkbook = new File(fileName);
			jxl.Workbook w = null;
			w = jxl.Workbook.getWorkbook(inputWorkbook);
			jxl.Sheet sheet = w.getSheet(0);
			int sodong = sheet.getRows();
			
			String codeTDV = "";
			String tenTDV = "";
			String hangchienluoc = "";
			String hangdactri = "";
			String dskhoan = "";
			String kpiChienluoc = "";
			String kpiDactri = "";
			
			for (int i = 3; i < sodong; i++)
			{
				int cot = 2;
				if(obj.getLoai().equals("1") ) //CHI TIEU ETC
					cot = 1;
				
				String _codeTDV = getValue(sheet, cot++, i).trim();
				if( _codeTDV.length() <= 0 )
					_codeTDV = " ";
				
				String _tenTDV = getValue(sheet, cot++, i).trim();
				if( _tenTDV.length() <= 0 )
					_tenTDV = " ";
				
				String _hangchienluoc = getValue(sheet, cot++, i).trim();
				if( _hangchienluoc.length() <= 0 )
					_hangchienluoc = "0";
				
				String _hangdactri = getValue(sheet, cot++, i).trim();
				if( _hangdactri.length() <= 0 )
					_hangdactri = "0";
				
				String _dskhoan = getValue(sheet, cot++, i).trim();
				if( _dskhoan.length() <= 0 )
					_dskhoan = "0";
				
				String _kpiChienluoc = getValue(sheet, cot ++, i).trim() ;
				if( _kpiChienluoc.length() <= 0 )
					_kpiChienluoc = "0";
				
				if( !_codeTDV.toUpperCase().contains("TỔNG CỘNG") )
				{
					codeTDV += _codeTDV + "__";
					tenTDV += _tenTDV + "__";
					hangchienluoc += _hangchienluoc + "__";
					hangdactri += _hangdactri + "__";
					dskhoan += _dskhoan + "__";
					kpiChienluoc += _kpiChienluoc + "__";
					
					if( obj.getLoai().equals("0") )
					{
						String _kpiDactri = getValue(sheet,cot ++, i).trim();
						if( _kpiDactri.length() <= 0 )
							_kpiDactri = "0";
						
						kpiDactri += _kpiDactri + "__";
					}
				}
			}
			
			if( codeTDV.trim().length() > 0 )
			{
				codeTDV = codeTDV.substring(0, codeTDV.length() - 2);
				obj.setCodeTDV(codeTDV.split("__"));
				
				tenTDV = tenTDV.substring(0, tenTDV.length() - 2);
				obj.setTenTDV(tenTDV.split("__"));
				
				hangchienluoc = hangchienluoc.substring(0, hangchienluoc.length() - 2);
				obj.setHangchienluoc(hangchienluoc.split("__"));
				
				hangdactri = hangdactri.substring(0, hangdactri.length() - 2);
				obj.setHangdactri(hangdactri.split("__"));
				
				dskhoan = dskhoan.substring(0, dskhoan.length() - 2);
				obj.setTongdskhoan(dskhoan.split("__"));
				
				kpiChienluoc = kpiChienluoc.substring(0, kpiChienluoc.length() - 2);
				obj.setKPIChienluoc(kpiChienluoc.split("__"));
				
				if( obj.getLoai().equals("0") ) 
				{
					kpiDactri = kpiDactri.substring(0, kpiDactri.length() - 2);
					obj.setKPIDactri(kpiDactri.split("__"));
				}
			}
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			return "Vui lòng coi lại file " + e.getMessage();
		}
		
		return "Import thành công";
	}

	private String getValue(Sheet sheet, int column, int row)
	{
		Cell cell;
		cell = sheet.getCell(column, row);
		try
		{
			return cell.getContents();
		} 
		catch (Exception er)
		{
			return "0";
		}
	}

}

