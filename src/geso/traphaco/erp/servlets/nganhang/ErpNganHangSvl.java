package geso.traphaco.erp.servlets.nganhang;

import geso.traphaco.center.util.*;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import geso.traphaco.erp.beans.nganhang.IErpNganHang;
import geso.traphaco.erp.beans.nganhang.IErpNganHangList;
import geso.traphaco.erp.beans.nganhang.imp.ErpNganHang;
import geso.traphaco.erp.beans.nganhang.imp.ErpNganHangList;
@WebServlet("/ErpNganHangSvl")
public class ErpNganHangSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	public ErpNganHangSvl()
	{
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		IErpNganHangList nhList = new ErpNganHangList();
		String userId;
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		HttpSession session = request.getSession();
		Utility util = new Utility();
		
		String chixem = request.getParameter("chixem");
		nhList.setChixem(chixem);
		
		String querystring = request.getQueryString();
		userId = util.getUserId(querystring);
		if (userId.length() == 0)
			userId = util.antiSQLInspection(request.getParameter("userId"));
		String action = util.getAction(querystring);
		if (action == null)
			action = "";
		String id = util.getId(querystring);
		if (action.equals("delete"))
		{
			nhList.setID(id);
			nhList.DeleteNganHang();
		}
		nhList.setUserid(userId);
		nhList.init();
		session.setAttribute("nhList", nhList);
		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpNganHang.jsp");
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		IErpNganHangList nhList = new ErpNganHangList();
		Utility util = new Utility();
		String userId;
		userId = util.antiSQLInspection(request.getParameter("userId"));
		
		String chixem = request.getParameter("chixem");
		nhList.setChixem(chixem);
		
		String ma = util.antiSQLInspection(request.getParameter("MA"));
		if (ma == null)
			ma = "";
		nhList.setMA(ma);
		String ten = util.antiSQLInspection(request.getParameter("TEN"));
		if (ten == null)
			ten = "";
		nhList.setTEN(ten);
		String action = request.getParameter("action");
		out.println(action);
		if (action.equals("search"))
		{
			nhList.init();
			HttpSession session = request.getSession();
			session.setAttribute("nhList", nhList);
			response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpNganHang.jsp");
		}
		else
		{
			IErpNganHang nhBean = new ErpNganHang();
			nhBean.setUserid(userId);
			HttpSession session = request.getSession();
			session.setAttribute("nhBean", nhBean);
			response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpNganHangNew.jsp");
		}
	}

}
