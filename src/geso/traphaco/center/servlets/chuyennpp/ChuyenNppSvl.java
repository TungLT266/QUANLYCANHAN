package geso.traphaco.center.servlets.chuyennpp;

import geso.traphaco.center.beans.chuyennpp.imp.ChuyenNpp;
import geso.traphaco.center.beans.chuyennpp.IChuyenNpp;

import geso.traphaco.center.util.Utility;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/ChuyenNppSvl")
public class ChuyenNppSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ChuyenNppSvl() {
		super();

	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IChuyenNpp obj = new ChuyenNpp();

		Utility util = new Utility();
		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);
		obj.setuserId(userId);
		obj.init();
		session.setAttribute("obj", obj);
		session.setAttribute("userId", userId);

		String nextJSP = "/TraphacoHYERP/pages/Center/ChuyenNpp.jsp";
		response.sendRedirect(nextJSP);

	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IChuyenNpp obj = new ChuyenNpp();
		Utility util = new Utility();

		String userId = (String) session.getAttribute("userId");
		if (userId == null)
			userId = "";
		obj.setuserId(userId);

		 obj.setvungId(util.antiSQLInspection(request.getParameter("vungId"))!=null?
		 util.antiSQLInspection(request.getParameter("vungId")):"");
		
		 obj.setkhuvucId(util.antiSQLInspection(request.getParameter("khuvucId"))!=null?
		 util.antiSQLInspection(request.getParameter("khuvucId")):"");

		String nppIdFrom = util.antiSQLInspection(request
				.getParameter("nppIdFrom"));
		if (nppIdFrom == null)
			nppIdFrom = "";
		obj.setNppIdFrom(nppIdFrom);

		String nppIdTo = util
				.antiSQLInspection(request.getParameter("nppIdTo"));
		if (nppIdTo == null)
			nppIdTo = "";
		obj.setNppIdTo(nppIdTo);
		
		
		String ngayks=util.antiSQLInspection(request.getParameter("ngayks"));
		if (ngayks == null)
			ngayks = "";
		obj.setNgayKs(ngayks);
		
		System.out.println("[nppIdFrom]" + nppIdFrom + "[nppIdTo]" + nppIdTo+"[ngayKs]"+ngayks);
		String action = request.getParameter("action");
		if (action.equals("TransferData")) {
			if(obj.TransferData())
			{
				obj.setMsg("Chuyen du lieu thanh cong");
			}
		}
		obj.init();
		session.setAttribute("obj", obj);
		session.setAttribute("userId", userId);
		String nextJSP = "/TraphacoHYERP/pages/Center/ChuyenNpp.jsp";
		response.sendRedirect(nextJSP);
	}

}
