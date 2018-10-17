package geso.traphaco.erp.servlets.erp_capquanly;


import geso.traphaco.center.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import geso.traphaco.erp.beans.erp_capquanly.IErp_capquanly;
import geso.traphaco.erp.beans.erp_capquanly.IErp_capquanlyList;
import geso.traphaco.erp.beans.erp_capquanly.imp.Erp_capquanly;
import geso.traphaco.erp.beans.erp_capquanly.imp.Erp_capquanlyList;


public class Erp_CapQuanLySvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public Erp_CapQuanLySvl()
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
		if (userId.length() == 0)
			userId = util.antiSQLInspection(request.getParameter("userId"));

		String cnId = util.getId(querystring);
		if (userId.length() == 0)
			userId = util.antiSQLInspection(request.getParameter("userId"));
		
		IErp_capquanlyList cnList = new Erp_capquanlyList();
		cnList.setTRANGTHAI("");
				
		String action = util.getAction(querystring);
				
		if (action.equals("delete"))
		{
			cnList.setID(cnId);
			cnList.Delete();
		}
		else if(action.equals("chot"))
		{
			cnList.setID(cnId);
			cnList.Chot();
		}
		cnList.init("");
		session.setAttribute("cnList", cnList);
		response.sendRedirect("/TraphacoHYERP/pages/Erp/Erp_CapQuanLy.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		IErp_capquanlyList cnList;

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		
		String userId;
		Utility util = new Utility();

		cnList = new Erp_capquanlyList();
				
		String ma = util.antiSQLInspection(request.getParameter("ma"));
		if (ma == null)
			ma = "";
		cnList.setMA(ma);

		String ngaytao = util.antiSQLInspection(request.getParameter("ngay"));
		if (ngaytao == null)
			ngaytao = "";
		cnList.setNGAYTAO(ngaytao);

		String ten = util.antiSQLInspection(request.getParameter("ten"));
		if (ten == null)
			ten = "";
		cnList.setTEN(ten);

		String trangthai = util.antiSQLInspection(request.getParameter("tt"));
		if (trangthai == null)
			trangthai = "";
		cnList.setTRANGTHAI(trangthai);
		
		String ctyId = session.getAttribute("congtyId").toString();
		if(ctyId == null)
			ctyId = "";
		cnList.setCtyId(ctyId);

		userId = util.antiSQLInspection(request.getParameter("userId"));
		String action = request.getParameter("action");
		if (action.equals("search"))
		{
			cnList.setUserid(userId);
			cnList.init("");
			session.setAttribute("cnList", cnList);
			response.sendRedirect("/TraphacoHYERP/pages/Erp/Erp_CapQuanLy.jsp");
		}

		else if (action.equals("new"))
		{
			System.out.println("Đã vào new");
			IErp_capquanly cnBean =new  Erp_capquanly();
			cnBean.setCtyId(ctyId);
			cnBean.createRs();
			cnBean.CreateNhanVien(null);
			session = request.getSession();
			session.setAttribute("cnBean", cnBean);
			response.sendRedirect("/TraphacoHYERP/pages/Erp/Erp_CapQuanLyNew.jsp");
		}
	}

}
