
package geso.traphaco.distributor.servlets.chuyentuyen;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.distributor.beans.chuyentuyen.IBCSoHoaDon;
import geso.traphaco.distributor.beans.chuyentuyen.imp.BCSoHoaDon;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/BCSoHoaDonSvl")
public class BCSoHoaDonSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public BCSoHoaDonSvl()
	{
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		IBCSoHoaDon obj;
		PrintWriter out;
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		out = response.getWriter();

		HttpSession session = request.getSession();

		Utility util = new Utility();
		out = response.getWriter();

		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);
		String action = request.getParameter("action");
		String type = request.getParameter("type");
		if(type == null)
			type = "";
		
		out.println(userId);
		
		obj = new BCSoHoaDon();
		
		if (userId.length() == 0)
			userId = util.antiSQLInspection(request.getParameter("userId"));
		
		obj.setUserId(userId);
		
		obj.initSohoadon();
		session.setAttribute("ctuyenBean", obj);
		String nextJSP = "/TraphacoHYERP/pages/Distributor/BCSoHoaDon.jsp";
		response.sendRedirect(nextJSP);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		String sum = (String) session.getAttribute("sum");
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session.getAttribute("util");
		if (!cutil.check(userId, userTen, sum))
		{
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		} 
		else
		{
			IBCSoHoaDon ctuyenBean;
			PrintWriter out;

			ctuyenBean = new BCSoHoaDon();
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");

			out = response.getWriter();

			userId = request.getParameter("userId");
			ctuyenBean.setUserId(userId);

			String nppId = request.getParameter("nppId");
			if (nppId == null)
				nppId = "";
			ctuyenBean.setNppId(nppId);
			
			String action = request.getParameter("action");
			
			ctuyenBean.setUserId(userId);
			ctuyenBean.createRs();
			session.setAttribute("ctuyenBean", ctuyenBean);
			String nextJSP;
			nextJSP = "/TraphacoHYERP/pages/Distributor/ChuyenTuyen.jsp";
			response.sendRedirect(nextJSP);
		}
	}
}
