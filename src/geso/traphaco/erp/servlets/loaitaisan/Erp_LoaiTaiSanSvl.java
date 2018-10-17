package geso.traphaco.erp.servlets.loaitaisan;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.loaitaisan.IErp_loaitaisan;
import geso.traphaco.erp.beans.loaitaisan.IErp_loaitaisanList;
import geso.traphaco.erp.beans.loaitaisan.imp.Erp_loaitaisan;
import geso.traphaco.erp.beans.loaitaisan.imp.Erp_loaitaisanList;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Erp_LoaiTaiSanSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public Erp_LoaiTaiSanSvl()
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

		IErp_loaitaisanList ltsList = new Erp_loaitaisanList();
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
				ltsList.setId(id);
			ltsList.DeleteNhts();
		}
		ltsList.setUserid(userId);
		ltsList.init();
		session.setAttribute("ltsList", ltsList);
		response.sendRedirect("/TraphacoHYERP/pages/Erp/Erp_LoaiTaiSan.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		IErp_loaitaisanList ltsList;
		String userId;
		Utility util = new Utility();

		ltsList = new Erp_loaitaisanList();
		HttpSession session = request.getSession();
		userId = util.antiSQLInspection(request.getParameter("userId"));
		String ma = util.antiSQLInspection(request.getParameter("mats"));
		if (ma == null)
			ma = "";
		ltsList.setMa(ma);

		String ten = util.antiSQLInspection(request.getParameter("diengiai"));
		if (ten == null)
			ten = "";
		ltsList.setTen(ten);

		String taikhoan = util.antiSQLInspection(request.getParameter("tk"));
		if (taikhoan == null)
			taikhoan = "";
		ltsList.setTkId(taikhoan);

		String action = request.getParameter("action");
		if (action == null)
		{
			action = "";
		}

		out.println(action);

		if (action.equals("new"))
		{
			IErp_loaitaisan ltsBean = new Erp_loaitaisan();
			ltsBean.createRs();
			ltsList.DBClose();
			session.setAttribute("ltsBean", ltsBean);
			String nextJSP = "pages/Erp/Erp_LoaiTaiSanNew.jsp";
			response.sendRedirect(nextJSP);
		} else
		{

			ltsList.setUserid(userId);
			ltsList.init();
			session.setAttribute("ltsList", ltsList);
			String nextJSP = "pages/Erp/Erp_LoaiTaiSan.jsp";
			response.sendRedirect(nextJSP);
		}
	}

}
