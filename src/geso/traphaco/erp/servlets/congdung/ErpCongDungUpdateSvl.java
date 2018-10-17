package geso.traphaco.erp.servlets.congdung;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.congdung.IErpCongDung;
import geso.traphaco.erp.beans.congdung.imp.ErpCongDung;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/ErpCongDungUpdateSvl")
public class ErpCongDungUpdateSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	Utility util;
	IErpCongDung cdBean;

	public ErpCongDungUpdateSvl()
	{
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		System.out.println("Do get! ErpCongDungUpdateSvl");
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		String sum = (String) session.getAttribute("sum");
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session.getAttribute("util");
		if (!cutil.check(userId, userTen, sum))
		{
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		} else
		{
			session.setMaxInactiveInterval(30000);
			String nextJSP = "";
			util = new Utility();
			String querystring = request.getQueryString();
			userId = util.getUserId(querystring);
			if (userId.length() == 0)
				userId = util.antiSQLInspection(request.getParameter("userId"));
			String id = util.getId(querystring);
			System.out.println("ID la" + id);
			
			cdBean = new ErpCongDung(id);
			
			System.out.println("ID : " + cdBean.getId());
			cdBean.setUserId(userId);
			
			if (request.getQueryString().indexOf("display") >= 0)
			{
				nextJSP = "/TraphacoHYERP/pages/Erp/ErpCongDungDisplay.jsp";
			} else if (request.getQueryString().indexOf("update") >= 0)
			{
				cdBean.Init();
				cdBean.CreateRs();
				nextJSP = "/TraphacoHYERP/pages/Erp/ErpCongDungUpdate.jsp";
			}
			System.out.println("Ban dang o trang" + nextJSP);
			session.setAttribute("cdBean", cdBean);
			response.sendRedirect(nextJSP);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		System.out.println("Do Post ErpCongDungUpdateSvl!");
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		String sum = (String) session.getAttribute("sum");
		util = (Utility) session.getAttribute("util");
		
		if (!util.check(userId, userTen, sum))
		{
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		} else
		{
			String nextJSP = "";
			IErpCongDung cdBean;
			String action = request.getParameter("action");
			if(action == null) action = "";
			
			String ma = request.getParameter("ma");
			String ten = request.getParameter("ten");
			String id = util.antiSQLInspection(request.getParameter("id"));
			String taikhoan = request.getParameter("taikhoan");
			String trangthai = request.getParameter("trangthai");
			
			if (id == null)
				cdBean = new ErpCongDung();
			else
				cdBean = new ErpCongDung(id);

			if (trangthai == null)
				trangthai = "0";
			else
				trangthai = "1";

			cdBean.setMa(ma);
			cdBean.setTen(ten);
			cdBean.setTaiKhoan(taikhoan);
			cdBean.setTrangThai(trangthai);
			cdBean.setUserId(userId);
			session.setAttribute("userId", userId);
			session.setAttribute("userTen", userTen);
		
			if (action.equals("save"))
			{
				if (id == null)
				{
					if (cdBean.Create())
					{
						IErpCongDung cd = new ErpCongDung();
						cd.Search();
						cd.CreateRs();
						cdBean.DBClose();
						session.setAttribute("cd", cd);
						nextJSP = "/TraphacoHYERP/pages/Erp/ErpCongDung.jsp";
						response.sendRedirect(nextJSP);
					} else
					{
						cdBean.CreateRs();
						session.setAttribute("cdBean", cdBean);
						nextJSP = "/TraphacoHYERP/pages/Erp/ErpCongDungNew.jsp";
						response.sendRedirect(nextJSP);
					}
				} else
				{
					if (!cdBean.Update())
					{
						System.out.println("Khong Update  duoc");
						session.setAttribute("cdBean", cdBean);
						nextJSP = "/TraphacoHYERP/pages/Erp/ErpCongDungUpdate.jsp";
						response.sendRedirect(nextJSP);
					} else
					{
						System.out.println("Update duoc");
						IErpCongDung cd = new ErpCongDung();
						cd.Search();
						cdBean.DBClose();
						cd.CreateRs();
						session.setAttribute("cd", cd);
						nextJSP = "/TraphacoHYERP/pages/Erp/ErpCongDung.jsp";
						response.sendRedirect(nextJSP);
					}
				}
			} else{ 	
					if (id == null)
						nextJSP = "/TraphacoHYERP/pages/Erp/ErpCongDungNew.jsp";
					else
						nextJSP = "/TraphacoHYERP/pages/Erp/ErpCongDungUpdate.jsp";
					
					if(action.length() == 0){						
						cdBean.Init();
					}else{
						cdBean.Search();
					}
					cdBean.CreateRs();					
					session.setAttribute("cdBean", cdBean);
					response.sendRedirect(nextJSP);
			}
		}
	}
}
