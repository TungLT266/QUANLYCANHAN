package geso.traphaco.erp.servlets.erp_chinhanh;


import geso.traphaco.center.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import geso.traphaco.erp.beans.erp_chinhanh.IErp_chinhanh;
import geso.traphaco.erp.beans.erp_chinhanh.IErp_chinhanhList;
import geso.traphaco.erp.beans.erp_chinhanh.imp.Erp_chinhanh;
import geso.traphaco.erp.beans.erp_chinhanh.imp.Erp_chinhanhList;


public class Erp_ChiNhanhSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public Erp_ChiNhanhSvl()
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
		
		
		IErp_chinhanhList cnList = new Erp_chinhanhList();
		cnList.setTRANGTHAI("");
		
		String ctyId = session.getAttribute("congtyId").toString();
		if(ctyId == null)
			ctyId = "";
		cnList.setcongtyId(ctyId);
				
		String action = util.getAction(querystring);
		if (action.equals("delete"))
		{
			cnList.setID(cnId);
			cnList.Delete();
		}
		cnList.init("");
		session.setAttribute("cnList", cnList);
		response.sendRedirect("/TraphacoHYERP/pages/Erp/Erp_ChiNhanh.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		IErp_chinhanhList cnList;
		HttpSession session = request.getSession();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		String userId;
		Utility util = new Utility();

		cnList = new Erp_chinhanhList();
		
		userId = util.antiSQLInspection(request.getParameter("userId"));
		
		String nppId = util.getIdNhapp(userId);
	    if(nppId == null)
	    	nppId = "";
	    cnList.setnppId(nppId);
		
		String chixem = request.getParameter("chixem");
		cnList.setChixem(chixem);
		
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
		cnList.setcongtyId(ctyId);

		String action = request.getParameter("action");
		if (action.equals("search"))
		{
			cnList.setUserid(userId);
			cnList.init("");
			session.setAttribute("cnList", cnList);
			response.sendRedirect("/TraphacoHYERP/pages/Erp/Erp_ChiNhanh.jsp");
		}

		else
		{
			IErp_chinhanh cnBean =new  Erp_chinhanh();
			session = request.getSession();
			session.setAttribute("cnBean", cnBean);
			response.sendRedirect("/TraphacoHYERP/pages/Erp/Erp_ChiNhanhNew.jsp");
		}
	}

}
