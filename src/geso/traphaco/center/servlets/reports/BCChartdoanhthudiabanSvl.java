package geso.traphaco.center.servlets.reports;

import geso.traphaco.center.beans.bcchartdoanhthudiaban.IBcchartdoanhthudiaban;
import geso.traphaco.center.beans.bcchartdoanhthudiaban.imp.Bcchartdoanhthudiaban;
import geso.traphaco.center.util.Utility;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class BCChartdoanhthudiabanSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	PrintWriter out;

	public BCChartdoanhthudiabanSvl()
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

		IBcchartdoanhthudiaban obj = new Bcchartdoanhthudiaban();
		obj.setUserId(userId);

		String msg = "";
		//PHẠM VI BÁO CÁO LÀ THÁNG HIỆN TẠI
		obj.init();
		obj.setMsg(msg);
		session.setAttribute("obj", obj);

		String nextJSP = "/TraphacoHYERP/pages/Center/Bcchartdoanhthudiaban.jsp";
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

		String userId = util.antiSQLInspection(request.getParameter("userId"));

		Bcchartdoanhthudiaban obj;
	
		obj = new Bcchartdoanhthudiaban();
		obj.setUserId(userId);
		
		//LẤY PHẠM VI BÁO CÁO
		String thang = request.getParameter("thang");
		if(thang == null)
			thang = "";
		obj.setThang(thang);
		String nam = request.getParameter("nam");
		if(nam == null)
			nam = "";
		obj.setNam(nam);
		
		//TẠO DỮ LIỆU
		obj.init();

		session.setAttribute("obj", obj);
		session.setAttribute("userId", userId);

		response.sendRedirect("/TraphacoHYERP/pages/Center/Bcchartdoanhthudiaban.jsp");
		}
	}


