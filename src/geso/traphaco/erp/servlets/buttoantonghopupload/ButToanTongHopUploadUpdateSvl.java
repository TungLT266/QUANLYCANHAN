package geso.traphaco.erp.servlets.buttoantonghopupload;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.buttoantonghopupload.imp.ButToanTongHopUpload;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;

public class ButToanTongHopUploadUpdateSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	private static final String UPLOAD_DIRECTORY = "C:\\upload\\";
	
	public ButToanTongHopUploadUpdateSvl()
	{
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
	    request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    PrintWriter out = response.getWriter();
	    	    
	    HttpSession session = request.getSession();	    
      
	    Utility util = new Utility();
	    out = response.getWriter();
	    
	    ButToanTongHopUpload obj = new ButToanTongHopUpload();
	    String ctyId = (String)session.getAttribute("congtyId");
	    obj.setCongTyId(ctyId);
	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    out.println(userId);
	    
	    if (userId.length()==0)
	    	userId = request.getParameter("userId");
	    
	    String action = util.getAction(querystring);
	    System.out.println("\n **************action:" + action);
	    
	    String id = util.getId(querystring);

	    obj.setId(id);
	   	
		obj.init();
		
		session.setAttribute("action", action);
    	session.setAttribute("obj", obj);

		session.setAttribute("userId", userId);
    		
		response.sendRedirect("/TraphacoHYERP/pages/Erp/ButToanTongHopUploadUpdate.jsp");
	}  	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
	    request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
		HttpSession session = request.getSession();
	    String userId = request.getParameter("userId");
//	    String id = request.getParameter("id");
	    String ctyId = (String)session.getAttribute("congtyId");
	  
	    
	    
	    String contentType = request.getContentType();
	    if ((contentType != null) && (contentType.indexOf("multipart/form-data") >= 0))
		{
	    	Utility util = new Utility();
			MultipartRequest multi = new MultipartRequest(request, UPLOAD_DIRECTORY, 20000000, "UTF-8");
			userId = util.antiSQLInspection(multi.getParameter("userId"));
			session.setAttribute("userId", userId);
			String userTen = (String) session.getAttribute("userTen");
			session.setAttribute("userTen", userTen);
			ButToanTongHopUpload obj = new ButToanTongHopUpload();
			obj.setUserId(userId);
			  obj.setCongTyId(ctyId);
			  obj.setNppId((String) session.getAttribute("nppId"));
			Enumeration<?> files = multi.getFileNames();
			String filename = "";
			while (files.hasMoreElements())
			{
				String name = (String) files.nextElement();
				filename = multi.getFilesystemName(name);
			}
			
			boolean flag = true;
			int bien=0;
			if (filename != null && filename.length() > 0)
			{
				response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "attachment; filename=Muahang_Upload_" + Utility.getCurrentDate()+ ".xlsx");
		    	obj.init();
		    	
		    	  bien = obj.readExcelFile(response.getOutputStream(), UPLOAD_DIRECTORY + filename);
			}
			
			if (bien == 0) {
				obj.setMsg("Tất cả các chứng từ đã được insert thành công");
				session.setAttribute("obj", obj);	
				System.out.println("obj.msg: " + obj.getMsg());
				String nextJSP = "/TraphacoHYERP/pages/Erp/ButToanTongHopUploadUpdate.jsp";
				response.sendRedirect(nextJSP);
			}
			return;
		}
	    
	    String action = request.getParameter("action");
	    if (action == null){
	    	action = "";
	    }
	        
	    ButToanTongHopUpload obj = new ButToanTongHopUpload();
	    obj.setUserId(userId);
	    String congTyId = (String)session.getAttribute("congtyId");
	    obj.setCongTyId(congTyId);

//	    setParams(session, request, obj);
	    
	    obj.init();
	    System.out.println("action: " + action);
	    
//	    if (action.equals("new") || action.equals("save"))
//	    {
//	    	if (!obj.create())
//	    	{
//	    		session.setAttribute("userId", userId);
//		    	session.setAttribute("obj", obj);
//		    	
//		    	String nextJSP = "/TraphacoHYERP/pages/Erp/ButToanTongHopUploadUpdate.jsp";
//	    		response.sendRedirect(nextJSP);
//	    		return;
//	    	}
//	    	else
//	    	{		    		
//	    		ButToanTongHopUploadList ob = new ButToanTongHopUploadList();
//	    		ob.setCongTyId(congTyId);
//	    		ob.init();
//	    		
//	    		session.setAttribute("userId", userId);
//		    	session.setAttribute("obj", ob);
//		    	
//		    	String nextJSP = "/TraphacoHYERP/pages/Erp/ButToanTongHopUpload.jsp";
//	    		response.sendRedirect(nextJSP);
//	    		return;
//	    	}
//	    }
	    
    	session.setAttribute("userId", userId);
    	session.setAttribute("obj", obj);
    	
    	String nextJSP = "/TraphacoHYERP/pages/Erp/ButToanTongHopUploadUpdate.jsp";
		response.sendRedirect(nextJSP);
		return;
	}
}