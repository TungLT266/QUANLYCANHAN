package geso.traphaco.erp.servlets.erp_donvithuchien;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.erp_donvithuchien.IErp_donvithuchien;
import geso.traphaco.erp.beans.erp_donvithuchien.IErp_donvithuchienList;
import geso.traphaco.erp.beans.erp_donvithuchien.imp.Erp_donvithuchien;
import geso.traphaco.erp.beans.erp_donvithuchien.imp.Erp_donvithuchienList;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Erp_DonViThucHienSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public Erp_DonViThucHienSvl()
	{
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		IErp_donvithuchienList dvthList = new Erp_donvithuchienList();
		String userId;
	
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();

		HttpSession session = request.getSession();
		
		Utility util = new Utility();
		out = response.getWriter();

		String querystring = request.getQueryString();
		String id = util.getId(querystring);
		userId = util.getUserId(querystring);
		if (userId.length() == 0)
			userId = util.antiSQLInspection(request.getParameter("userId"));

		String action = request.getParameter("delete");
		if (action != null)
		{
			dvthList.setID(id);
			dvthList.DeleteDvth();
		}

		dvthList.setUserid(userId);
		dvthList.setCongtyId((String)session.getAttribute("congtyId"));
		dvthList.init();
		session.setAttribute("dvthList", dvthList);
		response.sendRedirect("pages/Erp/Erp_DonViThucHien.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		IErp_donvithuchienList dvthList = new Erp_donvithuchienList();
		String userId;
		Utility util = new Utility();

		HttpSession session = request.getSession();
		String ctyId = (String)session.getAttribute("congtyId");
		dvthList.setCongtyId(ctyId);
		
		userId = util.antiSQLInspection(request.getParameter("userId"));


		String ten = util.antiSQLInspection(request.getParameter("ten")); 
		if (ten == null) ten = "";
		dvthList.setTEN(ten);

		String action = request.getParameter("action");
		if (action.equals("search"))
		{
			dvthList.setUserid(userId);
			dvthList.init();
			session.setAttribute("dvthList", dvthList);
			response.sendRedirect("pages/Erp/Erp_DonViThucHien.jsp");
		} 
		else
		{
			IErp_donvithuchien dvthBean = new Erp_donvithuchien();
			dvthBean.setCtyId((String)session.getAttribute("congtyId"));
			dvthList.DBClose();
			session.setAttribute("dvthBean", dvthBean);
			response.sendRedirect("pages/Erp/Erp_DonViThucHienNew.jsp");

		}
	}
}
