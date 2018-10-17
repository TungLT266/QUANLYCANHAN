package geso.traphaco.distributor.servlets.huynhaphang;

import geso.traphaco.distributor.beans.huynhaphang.*;
import geso.traphaco.distributor.beans.huynhaphang.imp.*;

import geso.traphaco.distributor.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class HuynhaphangUpdateSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public HuynhaphangUpdateSvl()
	{
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();

		Utility util = new Utility();

		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);

		if (userId.length() == 0)
			userId = request.getParameter("userId");

		String id = util.getId(querystring);

		IHuynhaphang dhtvBean = new Huynhaphang(id);
		dhtvBean.setUserId(userId);
		dhtvBean.init();
		session.setAttribute("dhtvBean", dhtvBean);
		session.setAttribute("nppId", dhtvBean.getNppId());
		String nextJSP = "/TraphacoHYERP/pages/Distributor/Huynhaphangdisplay.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();

		String sochungtu = request.getParameter("Sochungtu");

		IHuynhaphang dhtvBean = new Huynhaphang("");
		dhtvBean.setSochungtu(sochungtu);
		System.out.println("[sjdof]" + sochungtu);
		String userId = request.getParameter("userId");
		dhtvBean.setUserId(userId);
		String nppId = request.getParameter("nppId");
		if (nppId == null)
		{
			nppId = "";
		}
		dhtvBean.setNppId(nppId);
		String action = request.getParameter("action");
		if (action.equals("thuhien"))
		{

			if (!(dhtvBean.CreateDhtv()))
			{
				dhtvBean.createRS();

				session.setAttribute("dhtvBean", dhtvBean);
				String nextJSP = "/TraphacoHYERP/pages/Distributor/HuynhaphangNew.jsp";
				response.sendRedirect(nextJSP);
			} else
			{
				IHuynhaphangList obj = new HuynhaphangList();
				obj.setUserId(userId);
				obj.init("");
				session.setAttribute("obj", obj);
				String nextJSP = "/TraphacoHYERP/pages/Distributor/Huynhaphang.jsp";
				response.sendRedirect(nextJSP);
			}
		} else
		{
			dhtvBean.setUserId(userId);
			dhtvBean.createRS();
			String nextJSP = "/TraphacoHYERP/pages/Distributor/HuynhaphangNew.jsp";
			session.setAttribute("dhtvBean", dhtvBean);
			response.sendRedirect(nextJSP);
		}
	}

}
