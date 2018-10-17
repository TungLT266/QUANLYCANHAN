package geso.traphaco.center.servlets.reports;

import geso.traphaco.center.beans.bccharttonkhomien.IBccharttonkhomien;
import geso.traphaco.center.beans.bccharttonkhomien.imp.Bccharttonkhomien;
import geso.traphaco.center.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class BCCharttonkhomienSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	PrintWriter out;

	public BCCharttonkhomienSvl()
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

		IBccharttonkhomien obj = new Bccharttonkhomien();
		obj.setUserId(userId);

		String msg = "";
		//CHO XEM THEO TỒN KHO HIỆN TẠI
		obj.initTonKho();
		obj.setMsg(msg);
		session.setAttribute("obj", obj);

		String nextJSP = "/TraphacoHYERP/pages/Center/Bccharttonkhomien.jsp";
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

		Bccharttonkhomien obj;		
		obj = new Bccharttonkhomien();
		
		String userId = util.antiSQLInspection(request.getParameter("userId"));

		obj.setUserId(userId);
		String trangthai = util.antiSQLInspection(request.getParameter("trangthai"));
		obj.setTrangThai(trangthai);
		
		//THAY ĐỔI TRẠNG THÁI XEM
		if(obj.getTrangThai().equals("0"))
			obj.initTonKho();
		else 
			obj.initXuatNhapTon();

		session.setAttribute("obj", obj);
		session.setAttribute("userId", userId);

		response.sendRedirect("/TraphacoHYERP/pages/Center/Bccharttonkhomien.jsp");
		}
	}


