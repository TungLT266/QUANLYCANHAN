package geso.traphaco.erp.servlets.cophieu;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import geso.traphaco.center.util.*;
import geso.traphaco.erp.beans.cophieu.IErpCoPhieu;
import geso.traphaco.erp.beans.cophieu.IErpCoPhieuList;
import geso.traphaco.erp.beans.cophieu.imp.ErpCoPhieu;
import geso.traphaco.erp.beans.cophieu.imp.ErpCoPhieuList;
@WebServlet("/ErpCoPhieuSvl")
public class ErpCoPhieuSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	public ErpCoPhieuSvl()
	{
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		IErpCoPhieuList nhList = new ErpCoPhieuList();
		String userId;
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		HttpSession session = request.getSession();
		Utility util = new Utility();
		
		String querystring = request.getQueryString();
		userId = util.getUserId(querystring);
		if (userId.length() == 0)
			userId = util.antiSQLInspection(request.getParameter("userId"));
		String action = util.getAction(querystring);
		if (action == null)
			action = "";
		String id = util.getId(querystring);
		if (action.equals("delete"))
		{
			nhList.setID(id);
			nhList.DeleteCoPhieu();
		}
		nhList.setUserid(userId);
		nhList.init();
		session.setAttribute("nhList", nhList);
		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpCoPhieu.jsp");
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		IErpCoPhieuList nhList = new ErpCoPhieuList();
		Utility util = new Utility();
		String userId;
		userId = util.antiSQLInspection(request.getParameter("userId"));
		String ma = util.antiSQLInspection(request.getParameter("MA"));
		if (ma == null)
			ma = "";
		nhList.setMA(ma);
		String ten = util.antiSQLInspection(request.getParameter("TEN"));
		if (ten == null)
			ten = "";
		nhList.setTEN(ten);
		String congtyphathanh = util.antiSQLInspection(request.getParameter("congtyphathanh"));
		if(congtyphathanh ==null)
			congtyphathanh = "";
		nhList.setCongtyphathanh(congtyphathanh);
		String action = request.getParameter("action");
		out.println(action);
		if (action.equals("search"))
		{
			nhList.init();
			HttpSession session = request.getSession();
			session.setAttribute("nhList", nhList);
			response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpCoPhieu.jsp");
		}
		else if(action.equals("new"))
		{
			IErpCoPhieu nhBean = new ErpCoPhieu();
			nhBean.setUserid(userId);
			HttpSession session = request.getSession();
			session.setAttribute("nhBean", nhBean);
			response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpCoPhieuNew.jsp");
		}
	}

}
