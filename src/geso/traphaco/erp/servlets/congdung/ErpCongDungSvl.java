package geso.traphaco.erp.servlets.congdung;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.congdung.IErpCongDung;
import geso.traphaco.erp.beans.congdung.imp.ErpCongDung;
import geso.traphaco.erp.db.sql.dbutils;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpCongDungSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	
	
	public ErpCongDungSvl( )
	{
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		System.out.println("Do get ErpCongDungSvl");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		Utility util = new Utility();
		
		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);
		if (userId.length() == 0) userId = util.antiSQLInspection(request.getParameter("userId"));
		String action = util.getAction(querystring);
		String id = util.getId(querystring);
		
		IErpCongDung cd = new ErpCongDung();
		
		if (action.equals("delete"))
		{
			dbutils db = new dbutils();
			String query = "delete Erp_CongDung where pk_seq= '" + id+ "'";
			System.out.print(query);
			if(!db.update(query))
			{
				cd.setMsg("Công dụng này đã phát sinh dữ liệu nên không thể xóa");
				System.out.println("Công dụng này đã phát sinh dữ liệu nên không thể xóa");
			}
			db.shutDown();
		}
		
		cd.Search();
		cd.CreateRs();
		session.setAttribute("cd", cd);
		
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpCongDung.jsp";
		response.sendRedirect(nextJSP);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		System.out.println("Do post ErpCongDungSvl");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();

		IErpCongDung cd = new ErpCongDung();

		String nextJSP = "";
		String action = request.getParameter("action");
		String ma = request.getParameter("Ma");
		String ten = request.getParameter("Ten");
		String taikhoan = request.getParameter("TaiKhoan");
		
		if (action == null) action = "";
		
		cd.setTaiKhoan(taikhoan);
		cd.setMa(ma);
		cd.setTen(ten);
		
		if (action.equals("New"))
		{
		
			IErpCongDung cdBean = new ErpCongDung();
			cdBean.CreateRs();
			cd.DBClose();
			session.setAttribute("cdBean", cdBean);
			nextJSP = "/TraphacoHYERP/pages/Erp/ErpCongDungNew.jsp";
			response.sendRedirect(nextJSP);
		}
		else
		{
		
			cd.Search();
			cd.CreateRs();
			session.setAttribute("cd", cd);
			nextJSP = "/TraphacoHYERP/pages/Erp/ErpCongDung.jsp";
			response.sendRedirect(nextJSP);
		}		
	}
}
