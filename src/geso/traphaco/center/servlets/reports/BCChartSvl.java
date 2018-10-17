package geso.traphaco.center.servlets.reports;

import geso.traphaco.center.beans.bcchart.IBcchart;
import geso.traphaco.center.beans.bcchart.imp.Bcchart;
import geso.traphaco.center.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class BCChartSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	PrintWriter out;

	public BCChartSvl()
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

		IBcchart obj = new Bcchart();
		obj.setUserId(userId);

		String msg = "";
		
		//BÁO CÁO TRONG THÁNG HIỆN TẠI
		obj.initDoanhSo("");
		obj.initDoanhThu("");
		obj.initTonKho("");
		obj.initSanPham("");
		
		obj.setMsg(msg);
		session.setAttribute("obj", obj);

		String nextJSP = "/TraphacoHYERP/pages/Center/Bcchart.jsp";
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

		Bcchart obj;		
		obj = new Bcchart();
		
		String userId = util.antiSQLInspection(request.getParameter("userId"));

		obj.setUserId(userId);
		
		//LẤY PHẠM VI LẬP BÁO CÁO
		String thang = request.getParameter("thang");
		if(thang == null)
			thang = "";
		obj.setThang(thang);
		String nam = request.getParameter("nam");
		if(nam == null)
			nam = "";
		obj.setnam(nam);
		
		//LẤY BÁO CÁO
		obj.initDoanhSo("");
		obj.initDoanhThu("");
		obj.initTonKho("");
		obj.initSanPham("");

		session.setAttribute("obj", obj);
		session.setAttribute("userId", userId);

		response.sendRedirect("/TraphacoHYERP/pages/Center/Bcchart.jsp");
		}
	}


