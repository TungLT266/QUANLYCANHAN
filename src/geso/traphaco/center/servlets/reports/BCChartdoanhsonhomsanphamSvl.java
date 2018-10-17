package geso.traphaco.center.servlets.reports;

import geso.traphaco.center.beans.bcchartdoanhsonhomsanpham.IBcchartdoanhsonhomsanpham;
import geso.traphaco.center.beans.bcchartdoanhsonhomsanpham.imp.Bcchartdoanhsonhomsanpham;
import geso.traphaco.center.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class BCChartdoanhsonhomsanphamSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	PrintWriter out;

	public BCChartdoanhsonhomsanphamSvl()
	{
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

		IBcchartdoanhsonhomsanpham obj = new Bcchartdoanhsonhomsanpham();
		obj.setUserId(userId);

		String msg = "";

		obj.init();
		obj.setMsg(msg);
		session.setAttribute("obj", obj);

		String nextJSP = "/TraphacoHYERP/pages/Center/Bcchartdoanhsonhomsanpham.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		this.out = response.getWriter();

		HttpSession session = request.getSession();

		out = response.getWriter();
		Utility util = new Utility();

		Bcchartdoanhsonhomsanpham obj;		
		obj = new Bcchartdoanhsonhomsanpham();
		
		String userId = util.antiSQLInspection(request.getParameter("userId"));

		obj.setUserId(userId);
		String thang = request.getParameter("thang");
		if(thang == null)
			thang = "";
		obj.setThang(thang);
		String nam = request.getParameter("nam");
		if(nam == null)
			nam = "";
		obj.setnam(nam);
		obj.init();

		session.setAttribute("obj", obj);
		session.setAttribute("userId", userId);

		response.sendRedirect("/TraphacoHYERP/pages/Center/Bcchartdoanhsonhomsanpham.jsp");
		}
	}


