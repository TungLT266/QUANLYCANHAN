package geso.traphaco.erp.servlets.loainhacungcap;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.loainhacungcap.IErpLoaiNhaCungCap;
import geso.traphaco.erp.beans.loainhacungcap.imp.ErpLoaiNhaCungCap;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/ErpLoaiNhaCungCapSvl")
public class ErpLoaiNhaCungCapSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	Utility util = new Utility();
	

	public ErpLoaiNhaCungCapSvl()
	{
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// logFilter.doFilter(request,response,this.)
		System.out.println("Do get ErpLoaiNhaCungCap");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		Utility util = new Utility();
		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);
		
		if (userId.length() == 0)
			userId = util.antiSQLInspection(request.getParameter("userId"));
		String action = util.getAction(querystring);
		String Id = util.getId(querystring);
		
		IErpLoaiNhaCungCap lncc = new ErpLoaiNhaCungCap();
		
		String chixem = request.getParameter("chixem");
		lncc.setChixem(chixem);
		
		if (action.equals("delete"))
		{
			lncc = new ErpLoaiNhaCungCap(Id);
			lncc.DeleteLncc();
			System.out.println("Ngung hoat dong nhom nha cung cap");
		}
		lncc.setUserId(userId);
		lncc.search();
		session.setAttribute("lncc", lncc);
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpLoaiNhaCungCap.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		System.out.println("Do post ErpLoaiNhaCungCapUpdateSvl");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		String nextJSP = "";
		String ten = util.antiSQLInspection(request.getParameter("Ten"));
		String ma = util.antiSQLInspection(request.getParameter("Ma"));
		String action = request.getParameter("action");
		
		IErpLoaiNhaCungCap lncc = new ErpLoaiNhaCungCap(); //(ErpLoaiNhaCungCap) session.getAttribute("lncc");
		
		String chixem = request.getParameter("chixem");
		lncc.setChixem(chixem);
		
		if (ten == null)
		{
			ten = "";
		}
		lncc.setTen(ten);
		if (ma == null)
		{
			ma = "";
		}
		lncc.setMa(ma);
		System.out.println("Action : " + action);
		if (action == null)
		{
			action = "";
		}
		if (action.equals("New"))
		{
			System.out.println("Vao trang tao moi");
			IErpLoaiNhaCungCap lnccBean = new ErpLoaiNhaCungCap();

			session.setAttribute("lnccBean", lnccBean);
			nextJSP = "/TraphacoHYERP/pages/Erp/ErpLoaiNhaCungCapNew.jsp";
		}
		if (action.equals("Search"))
		{
			System.out.println("Search ");
			lncc.search();
			session.setAttribute("lncc", lncc);
			nextJSP = "/TraphacoHYERP/pages/Erp/ErpLoaiNhaCungCap.jsp";
		}
		response.sendRedirect(nextJSP);
	}
}
