package geso.traphaco.erp.servlets.khuvuckho;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.khuvuckho.IErpKhuVucKho;
import geso.traphaco.erp.beans.khuvuckho.imp.ErpKhuVucKho;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class ErpKhuVucKhoSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	
	public ErpKhuVucKhoSvl()
	{
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		System.out.println("Do get ErpKhuVucKhoSvl");
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
		
		IErpKhuVucKho kvkList = new ErpKhuVucKho();
		kvkList.setCtyId(ctyId);
		
		if (action.equals("delete"))
		{
			IErpKhuVucKho kvkBean = new ErpKhuVucKho(id);
			kvkBean.setCtyId(ctyId);
			kvkBean.Delete();
			kvkList.setMsg(kvkBean.getMsg());
			System.out.println("Action "+action);
		}
		kvkList.Search();
		kvkList.CreateRs();
		session.setAttribute("kvkList", kvkList);
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpKhuVucKho.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		System.out.println("Do post ErpKhuVucKhoSvl");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		String ctyId = (String)session.getAttribute("congtyId");
		Utility util = new Utility();
		
		IErpKhuVucKho kvkList = new ErpKhuVucKho();
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
		
		if (action.equals("New"))
		{
			System.out.println("Vao trang tao moi");
			IErpKhuVucKho kvkBean = new ErpKhuVucKho();
			kvkBean.setCtyId(ctyId);
			kvkList.Close();
			kvkBean.CreateRs();
			session.setAttribute("kvkBean", kvkBean);
			nextJSP = "/TraphacoHYERP/pages/Erp/ErpKhuVucKhoNew.jsp";
		} 
		else
		{
			kvkList.Search();
			kvkList.CreateRs();
			nextJSP = "/TraphacoHYERP/pages/Erp/ErpKhuVucKho.jsp";
		}
		
		session.setAttribute("kvkList", kvkList);
		response.sendRedirect(nextJSP);
	}
}
