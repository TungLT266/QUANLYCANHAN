package geso.traphaco.erp.servlets.duyetbom;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.duyetbom.IErpDuyetbomTP;
import geso.traphaco.erp.beans.duyetbom.imp.ErpDuyetbomTP;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class ErpDuyetbomTPSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	
	public ErpDuyetbomTPSvl()
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
	    String userId = (String) session.getAttribute("userId");  
	    String ctyId = (String)session.getAttribute("congtyId");
		
		IErpDuyetbomTP duyetbom = new ErpDuyetbomTP();
		duyetbom.setUserId(userId);
		duyetbom.setCtyId(ctyId);
		duyetbom.setUserId(userId);
		duyetbom.init();
		session.setAttribute("duyetbom", duyetbom);
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpDuyetBomTP.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		String ctyId = (String)session.getAttribute("congtyId");
		String userId = (String) session.getAttribute("userId");  
		Utility util = new Utility();
		
		IErpDuyetbomTP duyetbom = new ErpDuyetbomTP();
		duyetbom.setCtyId(ctyId);
		duyetbom.setUserId(userId);
		String nextJSP = "";
		String action = request.getParameter("action");
		
		
		
		String masanpham = util.antiSQLInspection(request.getParameter("masanpham"));
		if(masanpham == null)
			masanpham = "";
		duyetbom.setMasp(masanpham);
		
		String diengiai = util.antiSQLInspection(request.getParameter("diengiai"));
		if(diengiai == null)
			diengiai = "";
		duyetbom.setDiengiai(diengiai);
		
		String tenbom = util.antiSQLInspection(request.getParameter("tenbom"));
		if(tenbom == null)
			tenbom = "";
		duyetbom.setTenBom(tenbom);
		
		String bomId = util.antiSQLInspection(request.getParameter("bomid"));
		if(bomId == null)
			bomId = "";
		duyetbom.setBomId(bomId);

		System.out.println("bomid: " + bomId);
		
		
		if (action.equals("duyet"))
		{
			System.out.println("boduyet");
			duyetbom.setMsg(duyetbom.duyet());
			duyetbom.init();
			session.setAttribute("duyetbom", duyetbom);
			nextJSP = "/TraphacoHYERP/pages/Erp/ErpDuyetBomTP.jsp";
		}
		else
		{
			duyetbom.init();
			nextJSP = "/TraphacoHYERP/pages/Erp/ErpDuyetBomTP.jsp";
		}
		
		session.setAttribute("duyetbom", duyetbom);
		response.sendRedirect(nextJSP);
	}
}
