package geso.traphaco.erp.servlets.erp_nhomtaisan;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.erp_nhomtaisan.IErp_nhomtaisan;
import geso.traphaco.erp.beans.erp_nhomtaisan.IErp_nhomtaisanList;
import geso.traphaco.erp.beans.erp_nhomtaisan.imp.Erp_nhomtaisan;
import geso.traphaco.erp.beans.erp_nhomtaisan.imp.Erp_nhomtaisanList;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Erp_NhomTaiSanSvl
 */
@WebServlet("/Erp_NhomTaiSanSvl")
public class Erp_NhomTaiSanSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public Erp_NhomTaiSanSvl()
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

		IErp_nhomtaisanList ntsList = new Erp_nhomtaisanList();
		String userId;
		String querystring = request.getQueryString();
		userId = util.getUserId(querystring);

		if (userId.length() == 0)
			userId = util.antiSQLInspection(request.getParameter("userId"));
		out.println("userId la :" + userId);

		String action = util.getAction(querystring);
		if (action == null)
			action = "";

		String id = util.getId(querystring);
		if (id == null)
			id = "";

		if (action.equals("delete"))
		{
			if (id != null)
				ntsList.setId(id);
			ntsList.DeleteNhts();
		}
		ntsList.setUserid(userId);
		ntsList.init();
		session.setAttribute("ntsList", ntsList);
		response.sendRedirect("/TraphacoHYERP/pages/Erp/Erp_NhomTaiSan.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		IErp_nhomtaisanList ntsList;
		String userId;
		Utility util = new Utility();

		ntsList = new Erp_nhomtaisanList();
		HttpSession session = request.getSession();
		userId = util.antiSQLInspection(request.getParameter("userId"));
		String ma = util.antiSQLInspection(request.getParameter("mats"));
		if (ma == null)
			ma = "";
		ntsList.setMa(ma);

		String ten = util.antiSQLInspection(request.getParameter("diengiai"));
		if (ten == null)
			ten = "";
		ntsList.setTen(ten);

		String ltsId = util.antiSQLInspection(request.getParameter("ltsId"));
		ntsList.setLtsId(ltsId);
		
		String cdtsId = util.antiSQLInspection(request.getParameter("cdtsId"));
		ntsList.setCdtsId(cdtsId);
		
		String action = request.getParameter("action");
		if (action == null)
		{
			action = "";
		}

		out.println(action);

		if (action.equals("new"))
		{
			IErp_nhomtaisan ntsBean = new Erp_nhomtaisan();
			ntsBean.createRs();
			ntsList.DBClose();
			session.setAttribute("ntsBean", ntsBean);
			String nextJSP = "pages/Erp/Erp_NhomTaiSanNew.jsp";
			response.sendRedirect(nextJSP);
		} else
		{
			ntsList.setUserid(userId);
			ntsList.init();
			session.setAttribute("ntsList", ntsList);
			String nextJSP = "pages/Erp/Erp_NhomTaiSan.jsp";
			response.sendRedirect(nextJSP);
		}
	}
}