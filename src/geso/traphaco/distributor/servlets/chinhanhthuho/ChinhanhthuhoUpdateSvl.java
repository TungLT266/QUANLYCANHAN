package geso.traphaco.distributor.servlets.chinhanhthuho;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import geso.traphaco.center.util.Utility;
import geso.traphaco.distributor.beans.chinhanhthuho.IChinhanhthuho;
import geso.traphaco.distributor.beans.chinhanhthuho.imp.Chinhanhthuho;
import geso.traphaco.erp.beans.nganhang.IErpNganHang;
import geso.traphaco.erp.beans.nganhang.IErpNganHangList;
import geso.traphaco.erp.beans.nganhang.imp.*;

public class ChinhanhthuhoUpdateSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public ChinhanhthuhoUpdateSvl()
	{
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
//		PrintWriter out = response.getWriter();

		HttpSession session = request.getSession();

		Utility util = new Utility();
//		out = response.getWriter();

		String querystring = request.getQueryString();
		String action = util.getAction(querystring);
	    String userId = util.getUserId(querystring);
		String CNId = util.getId(querystring);

		   if (userId.length()==0)
		    	userId = util.antiSQLInspection(request.getParameter("userId"));
		    System.out.println("asdsadsadsa"+userId);
		    System.out.println("12312312312"+CNId);
		    if (action.equals("display"))
		{
			IChinhanhthuho thBean = new Chinhanhthuho(CNId);
			thBean.init();
			String nextJSP = "/TraphacoHYERP/pages/Distributor/ChinhanhthuhoDisplay.jsp";
			session.setAttribute("thBean", thBean);
			response.sendRedirect(nextJSP);
			return;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException
	{
		HttpSession session = request.getSession();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		String userId = request.getParameter("userId");
		String userTen = request.getParameter("userTen");
		String sum = (String) session.getAttribute("sum");
		Utility cutil = (Utility) session.getAttribute("util");
		if (!cutil.check(userId, userTen, sum))
		{
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		} else
		{
			IChinhanhthuho thBean = new Chinhanhthuho();

			String nextJSP = "";

			String id = cutil.antiSQLInspection(request.getParameter("id"));
			String ma = cutil.antiSQLInspection(request.getParameter("Ma"));
			String ten = cutil.antiSQLInspection(request.getParameter("Ten"));
			String trangthai = cutil.antiSQLInspection(request.getParameter("hoatdong"));
			if (trangthai == null)
				trangthai = "0";
			else
				trangthai = "1";
			thBean.setTrangthai(trangthai);
			String ngaytao = getDateTime();
			String ngaysua = getDateTime();

			if (id != null)
				thBean.setID(id);
			if (ma != null)
				thBean.setMA(ma);
			if (ten != null)
				thBean.setTEN(ten);
		

			thBean.setNgaysua(ngaysua);
			thBean.setNgaytao(ngaytao);



			session.setAttribute("userId", userId);
			session.setAttribute("userTen", userTen);

//			String action = request.getParameter("action");

	
			session.setAttribute("thBean", thBean);
			response.sendRedirect(nextJSP);

		}
	}

	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}

}
