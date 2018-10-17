package geso.traphaco.erp.servlets.duyetbom;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.duyetbom.IErpDuyetbom;
import geso.traphaco.erp.beans.duyetbom.imp.ErpDuyetbom;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class ErpDuyetbomSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	
	public ErpDuyetbomSvl()
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
	    String ctyId = (String)session.getAttribute("congtyId");
		
		IErpDuyetbom duyetbom = new ErpDuyetbom();
		duyetbom.setUserId(userId);
		duyetbom.setCtyId(ctyId);
		duyetbom.Init();
		session.setAttribute("duyetbom", duyetbom);
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpDuyetBom.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		String ctyId = (String)session.getAttribute("congtyId");
		Utility util = new Utility();
		
		IErpDuyetbom duyetbom = new ErpDuyetbom();
		duyetbom.setCtyId(ctyId);
		
		String nextJSP = "";
		String action = request.getParameter("action");
		
		String chungloai = util.antiSQLInspection(request.getParameter("chungloai"));
		if(chungloai == null)
			chungloai = "";
		duyetbom.setChungloaiId(chungloai);
		
		String tensanpham = util.antiSQLInspection(request.getParameter("tensanpham"));
		if(tensanpham == null)
			tensanpham = "";
		duyetbom.setTenSanPham(tensanpham);
		
		String diengiai = util.antiSQLInspection(request.getParameter("diengiai"));
		if(diengiai == null)
			diengiai = "";
		duyetbom.setDienGiai(diengiai);
		
		String bomId = util.antiSQLInspection(request.getParameter("bomid"));
		String spma = util.antiSQLInspection(request.getParameter("spma"));
		if(spma != null){

			System.out.println(spma);
			duyetbom.setMaSP(spma);	
		}
		if(bomId != null)
			duyetbom.setBomId(bomId);
		System.out.println("bomid: " + bomId);
		
		if (action.equals("save"))
		{
			System.out.println("save");
			duyetbom.Save();
			duyetbom.Init();
			session.setAttribute("duyetbom", duyetbom);
			nextJSP = "/TraphacoHYERP/pages/Erp/ErpDuyetBom.jsp";
		}
		else if (action.equals("boduyet"))
		{
			System.out.println("boduyet");
			duyetbom.Boduyet();
			duyetbom.Init();
			session.setAttribute("duyetbom", duyetbom);
			nextJSP = "/TraphacoHYERP/pages/Erp/ErpDuyetBom.jsp";
		}
		else
		{
			duyetbom.Init();
			nextJSP = "/TraphacoHYERP/pages/Erp/ErpDuyetBom.jsp";
		}
		
		session.setAttribute("duyetbom", duyetbom);
		response.sendRedirect(nextJSP);
	}
}
