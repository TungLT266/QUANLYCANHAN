package geso.traphaco.erp.servlets.khuvuckho;

import geso.traphaco.erp.beans.khuvuckho.IErpKhuVucKho;
import geso.traphaco.erp.beans.khuvuckho.imp.ErpKhuVucKho;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpKhuVucKhoUpdateSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public ErpKhuVucKhoUpdateSvl()
	{
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		System.out.println("Do get! ErpKhuVucKhoUpdateSvl");
		HttpSession session = request.getSession();
		
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		String sum = (String) session.getAttribute("sum");
		geso.traphaco.center.util.Utility util = (geso.traphaco.center.util.Utility) session.getAttribute("util");
		
		if (!util.check(userId, userTen, sum))
		{
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		} else
		{
			session.setMaxInactiveInterval(30000);
			String nextJSP = "";
			String querystring = request.getQueryString();
			userId = util.getUserId(querystring);
			String ctyId = (String)session.getAttribute("congtyId");
			
			if (userId.length() == 0)
				userId = util.antiSQLInspection(request.getParameter("userId"));
			String id = util.getId(querystring);
			 
		
			IErpKhuVucKho kvkBean = new ErpKhuVucKho(id);
			kvkBean.setCtyId(ctyId);
			 
			kvkBean.setUserId(userId);
			if (request.getQueryString().indexOf("display") >= 0)
			{
				kvkBean.Init();
				kvkBean.CreateRs();
				nextJSP = "/TraphacoHYERP/pages/Erp/ErpKhuVucKhoDisplay.jsp";
			} else if (request.getQueryString().indexOf("update") >= 0)
			{
				kvkBean.Init();
				kvkBean.CreateRs();
				nextJSP = "/TraphacoHYERP/pages/Erp/ErpKhuVucKhoUpdate.jsp";
			}
			System.out.println("Ban dang o trang" + nextJSP);
			session.setAttribute("kvkBean", kvkBean);
			response.sendRedirect(nextJSP);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		System.out.println("Do Post ErpKhuVucKhoUpdateSvl!");
		HttpSession session = request.getSession();
		
		String ctyId = (String)session.getAttribute("congtyId");		
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		String sum = (String) session.getAttribute("sum");
		geso.traphaco.center.util.Utility util = (geso.traphaco.center.util.Utility) session.getAttribute("util");

		if (!util.check(userId, userTen, sum))
		{
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		} else
		{
			String nextJSP = "";
			IErpKhuVucKho kvkBean;
			String action = request.getParameter("action");
			String ma = util.antiSQLInspection(request.getParameter("ma"));
			String ten = util.antiSQLInspection(request.getParameter("ten"));
			String Id = util.antiSQLInspection(request.getParameter("Id"));
			String KhoId = util.antiSQLInspection(request.getParameter("KhoId"));

			String trangthai = util.antiSQLInspection(request.getParameter("trangthai"));
			kvkBean = new ErpKhuVucKho();
			kvkBean.setCtyId(ctyId);
			
			if (action == null)
				action = "";

			if (KhoId == null || KhoId.trim().length() <= 0)
				KhoId = "NULL";

			if (ma == null || ma.length() == 0)
				ma = "";
			if (ten == null || ten.length() == 0)
				ten = "";

			if (trangthai == null)
				trangthai = "0";
			else
				trangthai = "1";
			kvkBean.setId(Id) ;
			kvkBean.setMa(ma);
			kvkBean.setDienGiai(ten);
			kvkBean.setKhoId(KhoId);
			kvkBean.setTrangThai(trangthai);
			kvkBean.setUserId(userId);
			session.setAttribute("userId", userId);
			session.setAttribute("userTen", userTen);
			if (action.equals("Create"))
			{
				System.out.println("Tao moi");
				if (kvkBean.Create())
				{
					System.out.println("Tao duoc");
					IErpKhuVucKho kvkList = new ErpKhuVucKho();
					kvkList.setCtyId(ctyId);
					kvkList.Search();
					kvkList.CreateRs();
					kvkBean.Close();
					session.setAttribute("kvkList", kvkList);
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpKhuVucKho.jsp";
				} else
				{
					System.out.println("Khong tao  duoc");
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpKhuVucKhoNew.jsp";
				}
			} else if (action.equals("Update"))
			{
				System.out.println("Update ErpKhuVucKhoUpdateSvl ");
				if (!kvkBean.Update())
				{
					System.out.println("Khong Update  duoc");
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpKhuVucKhoUpdate.jsp";
				} else
				{
					System.out.println("Update duoc");
					IErpKhuVucKho kvkList = new ErpKhuVucKho();
					kvkList.setCtyId(ctyId);
					kvkList.Search();
					kvkBean.Close();
					kvkList.CreateRs();
					session.setAttribute("kvkList", kvkList);
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpKhuVucKho.jsp";
				}
			} else if (kvkBean.getId().trim().length() == 0)
				nextJSP = "/TraphacoHYERP/pages/Erp/ErpKhuVucKhoNew.jsp";
			else
				nextJSP = "/TraphacoHYERP/pages/Erp/ErpKhuVucKhoUpdate.jsp";
			kvkBean.CreateRs();
			System.out.print("nextJSP " + nextJSP);
			session.setAttribute("kvkBean", kvkBean);
			response.sendRedirect(nextJSP);
		}
	}
}
