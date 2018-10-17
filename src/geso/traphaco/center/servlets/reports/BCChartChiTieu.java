package geso.traphaco.center.servlets.reports;

import geso.traphaco.center.beans.chart.IChart;
import geso.traphaco.center.beans.chart.imp.Chart;
import geso.traphaco.distributor.util.Utility;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class BCChartChiTieu extends HttpServlet
{
	private static final long serialVersionUID = 1L;

    public BCChartChiTieu() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		
		IChart obj = new Chart();
		String querystring = request.getQueryString();
		
		Utility util = new Utility();
		obj.setUserId(util.getUserId(querystring));
		obj.setUserTen((String) session.getAttribute("userTen"));
		
		obj.initChartCT();
		
		session.setAttribute("obj", obj);		
		session.setAttribute("userId", obj.getUserId());
		session.setAttribute("userTen", obj.getUserTen());
		
		String nextJSP = "/TraphacoHYERP/pages/Center/BCChartChiTieu.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		
		IChart obj = new Chart();
		String userId = (String) session.getAttribute("userId");
		obj.setUserId(userId);
		
		String userTen = (String) session.getAttribute("userTen");
		obj.setUserTen(userTen);
		
		String thang = request.getParameter("thang");
		if(thang == null)
			thang = "";
		obj.setThang(thang);
		
		String nam = request.getParameter("nam");
		if(nam == null)
			nam = "";
		obj.setNam(nam);
		
		String muclay = request.getParameter("muclay");
		if(muclay == null)
			muclay = "0";
		obj.setMuclay(muclay);

		obj.initChartCT();
		session.setAttribute("obj", obj);
		
		String nextJSP = "/TraphacoHYERP/pages/Center/BCChartChiTieu.jsp";
		response.sendRedirect(nextJSP);
	}
	

}
