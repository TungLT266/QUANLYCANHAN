package geso.traphaco.distributor.servlets.dangkykhuyenmaitichluy;

import geso.traphaco.distributor.beans.dangkykhuyenmaitichluy.IDangkykhuyenmaitichluy;
import geso.traphaco.distributor.beans.dangkykhuyenmaitichluy.IDangkykhuyenmaitichluyList;
import geso.traphaco.distributor.beans.dangkykhuyenmaitichluy.imp.Dangkykhuyenmaitichluy;
import geso.traphaco.distributor.beans.dangkykhuyenmaitichluy.imp.DangkykhuyenmaitichluyList;
import geso.traphaco.distributor.util.Utility;
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

public class DangkykhuyenmaitichluyUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	private static final String UPLOAD_DIRECTORY = "c:\\upload\\excel\\";
	    
    public DangkykhuyenmaitichluyUpdateSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
	    HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session.getAttribute("util");
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		}else{
	    
		    PrintWriter out; 
			
			IDangkykhuyenmaitichluy obj;
			
			out = response.getWriter();
			Utility util = new Utility();
			
	    	String querystring = request.getQueryString();
		    userId = util.getUserId(querystring);
		    
		    out.println(userId);
		    
		    if (userId.length()==0)
		    	userId = request.getParameter("userId");
		    String action = util.getAction(querystring);
		    String id = util.getId(querystring);  	
		    obj = new Dangkykhuyenmaitichluy();
		    obj.setUserId(userId);
		    obj.setId(id);    
		    obj.init();
		    
		    session.setAttribute("obj", obj);  	
	    	if(action.equals("update"))
	    		response.sendRedirect("/TraphacoHYERP/pages/Distributor/DangKyKhuyenMaiTichLuyUpdate.jsp");
	    	else if(action.equals("dislay")){
	    		response.sendRedirect("/TraphacoHYERP/pages/Distributor/DangKyKhuyenMaiTichLuyDislay.jsp");
	    	}
	    	else if(action.equals("chot"))
	    	{
	    		 obj.Chot();
	    		 DangkykhuyenmaitichluyList   obj1  = new DangkykhuyenmaitichluyList();
	    	     obj1.setUserId(userId);
	    	     obj1.init("0");
	             session.setAttribute("obj",obj1);
	             String nextJSP = "/TraphacoHYERP/pages/Distributor/DangKyKhuyenMaiTichLuy.jsp";
	             response.sendRedirect(nextJSP);
	    	}
	    	else if(action.equals("mochot"))
	    	{
	    		 obj.MoChot();
	    		 DangkykhuyenmaitichluyList   obj1  = new DangkykhuyenmaitichluyList();
	    	     obj1.setUserId(userId);
	    	     obj1.init("0");
	             session.setAttribute("obj",obj1);
	             String nextJSP = "/TraphacoHYERP/pages/Distributor/DangKyKhuyenMaiTichLuy.jsp";
	             response.sendRedirect(nextJSP);
	    	}
	    	else
	    	{
	    		if(obj.Delete()){
	    			DangkykhuyenmaitichluyList   obj1  = new DangkykhuyenmaitichluyList();
		    	    obj1.setUserId(userId);
		    	    obj1.init("0");
		            session.setAttribute("obj",obj1);
		            String nextJSP = "/TraphacoHYERP/pages/Distributor/DangKyKhuyenMaiTichLuy.jsp";
		            response.sendRedirect(nextJSP);
	    		}
	    	}
	    }
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		geso.traphaco.center.util.Utility util = (geso.traphaco.center.util.Utility) session.getAttribute("util");
		if(!util.check(userId, userTen, sum)){
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		}else{

			IDangkykhuyenmaitichluy obj;

			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");

			obj = new Dangkykhuyenmaitichluy();

			String contentType = request.getContentType();
			if ((contentType != null) && (contentType.indexOf("multipart/form-data") >= 0))
			{
				MultipartRequest multi = new MultipartRequest(request, UPLOAD_DIRECTORY, 20000000, "UTF-8");
				userId = util.antiSQLInspection(multi.getParameter("userId"));
				session.setAttribute("userId", userId);
				obj.setUserId(userId);
				
				String id = util.antiSQLInspection(multi.getParameter("id"));
				obj.setId(id);
				
				String ctkmId = multi.getParameter("ctkmId");
				obj.setCtkmId(ctkmId);

				String nppId = multi.getParameter("nppId");
				obj.setNppId(nppId);

				String nppTen = multi.getParameter("nppTen");
				obj.setNppTen(nppTen);

				String nvbhId = multi.getParameter("nvbhId");
				if(nvbhId == null)
					nvbhId = "";
				obj.setNvbhIds(nvbhId);

				String mucdangky = multi.getParameter("mucdangky");
				if(mucdangky == null)
					mucdangky = "";
				obj.setMucdangky(mucdangky);
				
				
				Enumeration files = multi.getFileNames();
				String filename = "";
				System.out.println("__userId" + userId);
				while (files.hasMoreElements())
				{
					String name = (String) files.nextElement();
					filename = multi.getFilesystemName(name);
					System.out.println("File  " + UPLOAD_DIRECTORY + filename);
				}
				if (filename != null && filename.length() > 0)
					obj.setMessage(this.readExcel(UPLOAD_DIRECTORY + filename, obj));

				obj.createRs();
				session.setAttribute("obj", obj);  	 	
				
				if( id == null )
					response.sendRedirect("/TraphacoHYERP/pages/Distributor/DangKyKhuyenMaiTichLuyNew.jsp");
				else
					response.sendRedirect("/TraphacoHYERP/pages/Distributor/DangKyKhuyenMaiTichLuyUpdate.jsp");
			} 
			else
			{
				userId = request.getParameter("userId");
				obj.setUserId(userId);

				String ctkmId = request.getParameter("ctkmId");
				obj.setCtkmId(ctkmId);

				String nppId = request.getParameter("nppId");
				obj.setNppId(nppId);

				String id = request.getParameter("id");
				if( id == null )
					id = "";
				obj.setId(id);
				System.out.println(":::: ID: " + id);

				String nppTen = request.getParameter("nppTen");
				obj.setNppTen(nppTen);

				String nvbhId = request.getParameter("nvbhId");
				if(nvbhId == null)
					nvbhId = "";
				obj.setNvbhIds(nvbhId);

				String mucdangky = request.getParameter("mucdangky");
				if(mucdangky == null)
					mucdangky = "";
				obj.setMucdangky(mucdangky);

				String khachhang[] = request.getParameterValues("khIds");
				if(khachhang != null)
				{
					String kh = "";
					for(int i = 0; i < khachhang.length; i++ )
					{
						kh += khachhang[i] + ",";
					}
					if(kh.trim().length() > 0)
					{
						kh = kh.substring(0, kh.length() - 1);
						obj.setKhId(kh);
					}
				}
				
				String[] khMa = request.getParameterValues("khMa");
				obj.setKhMa(khMa);
				
				String[] khTen = request.getParameterValues("khTen");
				obj.setKhTen(khTen);
				
				String[] khDiachi = request.getParameterValues("khDiachi");
				obj.setKhDiachi(khDiachi);
				
				String[] khSohopdong = request.getParameterValues("khSohopdong");
				obj.setKhSohopdong(khSohopdong);
				
				String[] khTungay = request.getParameterValues("khTungay");
				obj.setKhTungay(khTungay);
				
				String[] khDenngay = request.getParameterValues("khDenngay");
				obj.setKhDenngay(khDenngay);
				
				String[] khDoanhso = request.getParameterValues("khDoanhso");
				obj.setKhDoanhso(khDoanhso);
				

				String action = request.getParameter("action");

				if(action.equals("submit"))
				{   
					obj.init();
					obj.createRs();
					session.setAttribute("obj", obj);  	 	
					
					if( id.trim().length() <= 0 )
						response.sendRedirect("/TraphacoHYERP/pages/Distributor/DangKyKhuyenMaiTichLuyNew.jsp");	
					else
						response.sendRedirect("/TraphacoHYERP/pages/Distributor/DangKyKhuyenMaiTichLuyUpdate.jsp");	
				}
				else
				{
					if( id.trim().length() > 0 )
					{
						if(obj.edit()){
							IDangkykhuyenmaitichluyList objl  = new DangkykhuyenmaitichluyList();
							objl.setUserId(userId);
							objl.init("0");
							session.setAttribute("obj",objl);
							String nextJSP = "/TraphacoHYERP/pages/Distributor/DangKyKhuyenMaiTichLuy.jsp";
							response.sendRedirect(nextJSP);
						}else{
							obj.init();
							obj.createRs();
							session.setAttribute("obj", obj);  	 		
							response.sendRedirect("/TraphacoHYERP/pages/Distributor/DangKyKhuyenMaiTichLuyUpdate.jsp");	
						}	
					}else{
						if(obj.save()){
							IDangkykhuyenmaitichluyList objl  = new DangkykhuyenmaitichluyList();
							objl.setUserId(userId);
							objl.init("0");
							session.setAttribute("obj",objl);
							String nextJSP = "/TraphacoHYERP/pages/Distributor/DangKyKhuyenMaiTichLuy.jsp";
							response.sendRedirect(nextJSP);
						}else{
							obj.init();
							obj.createRs();
							session.setAttribute("obj", obj);  	 		
							response.sendRedirect("/TraphacoHYERP/pages/Distributor/DangKyKhuyenMaiTichLuyNew.jsp");	
						}
					}

				}
			}
		}
	}

	private String readExcel(String fileName, IDangkykhuyenmaitichluy obj) 
	{
		try
		{
			File inputWorkbook = new File(fileName);
			jxl.Workbook w = null;
			w = jxl.Workbook.getWorkbook(inputWorkbook);
			jxl.Sheet sheet = w.getSheet(0);
			int sodong = sheet.getRows();
			
			String khMA = "";
			String khTen = "";
			String khDiachi = "";
			String khSohopdong = "";
			String khTungay = "";
			String khDenngay = "";
			String khDoanhso = "";
			
			for (int i = 4; i < sodong; i++)
			{
				String _khMa = getValue(sheet, 1, i).trim();
				String _khTen = getValue(sheet, 2, i).trim().length() <= 0 ? " " : getValue(sheet, 2, i).trim();
				String _khDiachi = getValue(sheet, 3, i).trim().length() <= 0 ? " " : getValue(sheet, 3, i).trim();
				String _khSohopdong = getValue(sheet, 4, i).trim().length() <= 0 ? " " : getValue(sheet, 4, i).trim();
				String _khTungay = getValue(sheet, 5, i).trim().length() <= 0 ? " " : getValue(sheet, 5, i).trim();
				String _khDenngay = getValue(sheet, 6, i).trim().length() <= 0 ? " " : getValue(sheet, 6, i).trim();
				String _khDoanhso = getValue(sheet, 7, i).trim().replaceAll(",", "").length() <= 0 ? " " : getValue(sheet, 7, i).trim();
				
				khMA += _khMa + "__";
				khTen += _khTen + "__";
				khDiachi += _khDiachi + "__";
				khSohopdong += _khSohopdong + "__";
				khTungay += _khTungay + "__";
				khDenngay += _khDenngay + "__";
				khDoanhso += _khDoanhso + "__";
				
				//System.out.println(":::: MA: " + _khMa + " - TU NGAY: " + _khTungay + " - DEN NGAY: " + _khDenngay + " - DEN NGAY 2: " + formatDATE(_khDenngay) );
			}
			
			if(khMA.trim().length() > 0)
			{
				khMA = khMA.substring(0, khMA.length() - 2);
				obj.setKhMa(khMA.split("__"));
				
				khTen = khTen.substring(0, khTen.length() - 2);
				obj.setKhTen(khTen.split("__"));
				
				khDiachi = khDiachi.substring(0, khDiachi.length() - 2);
				obj.setKhDiachi(khDiachi.split("__"));
				
				khSohopdong = khSohopdong.substring(0, khSohopdong.length() - 2);
				obj.setKhSohopdong(khSohopdong.split("__"));
				
				khTungay = khTungay.substring(0, khTungay.length() - 2);
				obj.setKhTungay(khTungay.split("__"));
				
				khDenngay = khDenngay.substring(0, khDenngay.length() - 2);
				obj.setKhDenngay(khDenngay.split("__"));
				
				khDoanhso = khDoanhso.substring(0, khDoanhso.length() - 2);
				obj.setKhDoanhso(khDoanhso.split("__"));
			}
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			return "Vui lòng coi lại file " + e.getMessage();
		}
		return "";
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
	
	private String formatDATE(String date)
	{
		String kq = date;
		
		/*if( date.contains("\\") )
		{
			String[] str = date.split("\\");
			kq = str[2] + "-" + str[0] + "-" + str[1];
		}*/
		
		return kq;
	}

}
