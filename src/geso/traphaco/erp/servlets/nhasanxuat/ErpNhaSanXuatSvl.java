package geso.traphaco.erp.servlets.nhasanxuat;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.nhasanxuat.IErpNhaSanXuat;
import geso.traphaco.erp.beans.nhasanxuat.IErpNhaSanXuatList;
import geso.traphaco.erp.beans.nhasanxuat.imp.ErpNhaSanXuat;
import geso.traphaco.erp.beans.nhasanxuat.imp.ErpNhaSanXuatList;


public class ErpNhaSanXuatSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		IErpNhaSanXuatList nsxList = new ErpNhaSanXuatList();
		String userId;
		HttpSession session = request.getSession();
		Utility util = new Utility();
		
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
			nsxList.setID(id);
			nsxList.deleteNhaSanXuat();
		}
		nsxList.setUserid(userId);
		nsxList.init();
		session.setAttribute("nsxList", nsxList);
		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpNhaSanXuat.jsp");
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		IErpNhaSanXuatList nsxList = new ErpNhaSanXuatList();
		Utility util = new Utility();
		String userId;
		userId = util.antiSQLInspection(request.getParameter("userId"));
		
		String ma = util.antiSQLInspection(request.getParameter("MA"));
		if (ma == null)
			ma = "";
		nsxList.setMA(ma);
		String ten = util.antiSQLInspection(request.getParameter("TEN"));
		if (ten == null)
			ten = "";
		nsxList.setTEN(ten);
		String trangthai = util.antiSQLInspection(request.getParameter("trangthai"));
		if (trangthai == null)
			trangthai = "";
		nsxList.setTRANGTHAI(trangthai);
		
		String action = request.getParameter("action");
		out.println(action);
		if (action.equals("search"))
		{
			nsxList.init();
			HttpSession session = request.getSession();
			session.setAttribute("nsxList", nsxList);
			response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpNhaSanXuat.jsp");
		}
		else
		{
			IErpNhaSanXuat nsxBean = new ErpNhaSanXuat();
			nsxBean.setUserid(userId);
			HttpSession session = request.getSession();
			session.setAttribute("nsxBean", nsxBean);
			response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpNhaSanXuatNew.jsp");
		}
	}

}
