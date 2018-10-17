package geso.traphaco.erp.servlets.erp_tiente;

import geso.traphaco.center.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import geso.traphaco.erp.beans.erp_tiente.IErp_tiente;
import geso.traphaco.erp.beans.erp_tiente.IErp_tienteList;
import geso.traphaco.erp.beans.erp_tiente.imp.Erp_tiente;
import geso.traphaco.erp.beans.erp_tiente.imp.Erp_tienteList;


public class Erp_TienTeSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;


	public Erp_TienTeSvl()
	{
		super();

	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		IErp_tienteList ttList = new Erp_tienteList();

		String userId;

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();

		HttpSession session = request.getSession();

		Utility util = new Utility();
		out = response.getWriter();

		String chixem = request.getParameter("chixem");
		if(chixem == null)
			chixem = "0"; 
		ttList.setChixem(chixem);
		
		String querystring = request.getQueryString();
		userId = util.getUserId(querystring);
				
		if (userId.length() == 0)
			userId = util.antiSQLInspection(request.getParameter("userid"));
		out.println("userId la :" + userId);
		String id = util.getId(querystring);
		String action = request.getParameter("delete");
		if (action != null)
		{
			ttList.setID(id);
			ttList.DeleteTienTe();
		}

		ttList.setUserid(userId);
		ttList.init();
		session.setAttribute("tt", ttList);
		response.sendRedirect("/TraphacoHYERP/pages/Erp/Erp_TienTe.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException
	{
		IErp_tienteList ttList = new Erp_tienteList();
		String userId;
		Utility util = new Utility();

		String chixem = request.getParameter("chixem");
		ttList.setChixem(chixem);
		
		String ma = util.antiSQLInspection(request.getParameter("ma"));
		if (ma == null)
			ma = "";
		ttList.setMA(ma);

		String ten = util.antiSQLInspection(request.getParameter("ten"));
		if (ten == null)
			ten = "";
		ttList.setTEN(ten);

		userId = util.antiSQLInspection(request.getParameter("userid"));

		String action = request.getParameter("action");
		if (action.equals("search"))
		{
			ttList.setUserid(userId);
			ttList.init();
			HttpSession session = request.getSession();
			session.setAttribute("tt", ttList);
			response.sendRedirect("/TraphacoHYERP/pages/Erp/Erp_TienTe.jsp");
		} else
		{
			IErp_tiente ttBean = new Erp_tiente();
			ttBean.setUserid(userId);
			HttpSession session = request.getSession();
			session.setAttribute("ttBean", ttBean);
			response.sendRedirect("/TraphacoHYERP/pages/Erp/Erp_TienTeNew.jsp");

		}
	}
}
