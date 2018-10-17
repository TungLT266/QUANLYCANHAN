package geso.traphaco.erp.servlets.khuvuckho;

import geso.traphaco.erp.beans.khuvuckho.IErpViTriKho;
import geso.traphaco.erp.beans.khuvuckho.imp.ErpViTriKho;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpViTriKhoUpdateSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public ErpViTriKhoUpdateSvl()
	{
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		System.out.println("Do get! ErpViTriKhoUpdateSvl");
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
			 
		
			IErpViTriKho kvkBean = new ErpViTriKho(id);
			kvkBean.setCtyId(ctyId);
			 
			kvkBean.setUserId(userId);
			if (request.getQueryString().indexOf("display") >= 0)
			{
				kvkBean.Init();
				kvkBean.CreateRs();
				nextJSP = "/TraphacoHYERP/pages/Erp/ErpViTriKhoDisplay.jsp";
			} 
			else if (request.getQueryString().indexOf("update") >= 0)
			{
				kvkBean.Init();
				kvkBean.CreateRs();
				nextJSP = "/TraphacoHYERP/pages/Erp/ErpViTriKhoUpdate.jsp";
			}
			

			session.setAttribute("kvkBean", kvkBean);
			response.sendRedirect(nextJSP);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		System.out.println("Do Post ErpViTriKhoUpdateSvl!");
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
			IErpViTriKho kvkBean = new ErpViTriKho();
			kvkBean.setCtyId(ctyId);
			
			String action = request.getParameter("action");
			
			String Id = util.antiSQLInspection(request.getParameter("Id"));
			kvkBean.setId(Id) ;
			
			String ma = util.antiSQLInspection(request.getParameter("ma"));
			kvkBean.setMa(ma);
			
			String ten = util.antiSQLInspection(request.getParameter("ten"));
			kvkBean.setDienGiai(ten);
			
			String khoId = util.antiSQLInspection(request.getParameter("KhoId"));
			if( khoId == null )
				khoId = "";
			kvkBean.setKhoId(khoId);
			
			String KhuvucId = util.antiSQLInspection(request.getParameter("KhuvucId"));
			if( KhuvucId == null )
				KhuvucId = "";
			kvkBean.setKhuvucId(KhuvucId);
			
			String trangthai = util.antiSQLInspection(request.getParameter("trangthai"));
			if (trangthai == null)
				trangthai = "0";
			else
				trangthai = "1";
			kvkBean.setTrangThai(trangthai);
			
			kvkBean.setUserId(userId);
			session.setAttribute("userId", userId);
			session.setAttribute("userTen", userTen);
			if (action.equals("Create"))
			{
				if (kvkBean.Create())
				{
					IErpViTriKho kvkList = new ErpViTriKho();
					kvkList.setCtyId(ctyId);
					kvkList.Search();
					kvkList.CreateRs();

					session.setAttribute("kvkList", kvkList);
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpViTriKho.jsp";
				} 
				else
				{
					kvkBean.CreateRs();
					session.setAttribute("kvkBean", kvkBean);
					
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpViTriKhoNew.jsp";
					response.sendRedirect(nextJSP);
				}
			} 
			else if (action.equals("Update"))
			{
				if (kvkBean.Update())
				{
					IErpViTriKho kvkList = new ErpViTriKho();
					kvkList.setCtyId(ctyId);
					kvkList.Search();
					kvkList.CreateRs();
					
					session.setAttribute("kvkList", kvkList);
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpViTriKho.jsp";
				} 
				else
				{
					kvkBean.CreateRs();
					session.setAttribute("kvkBean", kvkBean);
					
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpViTriKhoUpdate.jsp";
					response.sendRedirect(nextJSP);
				}
			} 
			else if (kvkBean.getId().trim().length() == 0)
				nextJSP = "/TraphacoHYERP/pages/Erp/ErpViTriKhoNew.jsp";
			else
				nextJSP = "/TraphacoHYERP/pages/Erp/ErpViTriKhoUpdate.jsp";
			
			kvkBean.CreateRs();
			session.setAttribute("kvkBean", kvkBean);
			response.sendRedirect(nextJSP);
		}
	}
}
