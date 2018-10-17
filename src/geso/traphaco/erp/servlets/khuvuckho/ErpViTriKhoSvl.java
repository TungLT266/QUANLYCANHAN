package geso.traphaco.erp.servlets.khuvuckho;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.khuvuckho.IErpViTriKho;
import geso.traphaco.erp.beans.khuvuckho.imp.ErpViTriKho;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class ErpViTriKhoSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	
	public ErpViTriKhoSvl()
	{
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
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
		String id = util.getId(querystring);
		String ctyId = (String)session.getAttribute("congtyId");
		
		IErpViTriKho kvkList = new ErpViTriKho();
		kvkList.setCtyId(ctyId);
		
		if (action.equals("delete"))
		{
			IErpViTriKho kvkBean = new ErpViTriKho(id);
			kvkBean.setCtyId(ctyId);
			kvkBean.Delete();
			kvkList.setMsg(kvkBean.getMsg());
		}
		kvkList.Search();
		kvkList.CreateRs();
		session.setAttribute("kvkList", kvkList);
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpViTriKho.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		String ctyId = (String)session.getAttribute("congtyId");
		Utility util = new Utility();
		
		IErpViTriKho kvkList = new ErpViTriKho();
		kvkList.setCtyId(ctyId);
		
		String nextJSP = "";
		String action = request.getParameter("action");
		
		String makhu = util.antiSQLInspection(request.getParameter("makhu"));
		if(makhu == null)
			makhu = "";
		kvkList.setMa(makhu);
		
		String tenkhu = util.antiSQLInspection(request.getParameter("tenkhu"));
		if(tenkhu == null)
			tenkhu = "";
		kvkList.setDienGiai(tenkhu);
		
		String makho = util.antiSQLInspection(request.getParameter("makho"));
		if(makho == null)
			makho = "";
		kvkList.setKhoId(makho);
		
		String KhuvucId = util.antiSQLInspection(request.getParameter("KhuvucId"));
		if(KhuvucId == null)
			KhuvucId = "";
		kvkList.setKhuvucId(KhuvucId);
		
		if (action.equals("New"))
		{
			IErpViTriKho kvkBean = new ErpViTriKho();
			kvkBean.setCtyId(ctyId);
			kvkList.Close();
			kvkBean.CreateRs();
			session.setAttribute("kvkBean", kvkBean);
			nextJSP = "/TraphacoHYERP/pages/Erp/ErpViTriKhoNew.jsp";
		} 
		else
		{
			kvkList.Search();
			kvkList.CreateRs();
			nextJSP = "/TraphacoHYERP/pages/Erp/ErpViTriKho.jsp";
		}
		
		session.setAttribute("kvkList", kvkList);
		response.sendRedirect(nextJSP);
	}
}
