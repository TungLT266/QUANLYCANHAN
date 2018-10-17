package geso.traphaco.erp.servlets.nhomkhoanmuc;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.nhomkhoanmuc.IErpNhomKhoanMuc;
import geso.traphaco.erp.beans.nhomkhoanmuc.IErpNhomKhoanMucList;
import geso.traphaco.erp.beans.nhomkhoanmuc.imp.ErpNhomKhoanMuc;
import geso.traphaco.erp.beans.nhomkhoanmuc.imp.ErpNhomKhoanMucList;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/ErpNhomKhoanMucSvl")
public class ErpNhomKhoanMucSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	Utility util = new Utility();
	

	public ErpNhomKhoanMucSvl()
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
		
		IErpNhomKhoanMucList obj =  new ErpNhomKhoanMucList();
		
		String ctyId = (String)session.getAttribute("congtyId");
		obj.setCongtyId(ctyId);
	    //String nppId = (String)session.getAttribute("nppId");
	    //lncc.setNppId(nppId);
	    
		String chixem = request.getParameter("chixem");
		obj.setChixem(chixem);
		
		if (action.equals("delete"))
		{	
			IErpNhomKhoanMuc obj1 =  new ErpNhomKhoanMuc(Id);
			obj1.Deletenkm();

		}
		obj.Init();
		obj.setUserId(userId);
		
		session.setAttribute("obj", obj);
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhomKhoanMuc.jsp";
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
		System.out.println("aaaaaaaa"+ten);
		System.out.println("bbbbbbbb"+ma);
		String action = request.getParameter("action");
		

		IErpNhomKhoanMucList obj =  new ErpNhomKhoanMucList();
		
		String ctyId = (String)session.getAttribute("congtyId");
		obj.setCongtyId(ctyId);
	    /*String nppId = (String)session.getAttribute("nppId");
	    lncc.setNppId(nppId);*/
	    

		System.out.println("Action : " + action);
		if (action == null)
		{
			action = "";
		}
		if (action.equals("New"))
		{
			System.out.println("Vao trang tao moi");
			
			IErpNhomKhoanMuc nkmBean=new ErpNhomKhoanMuc();
			nkmBean.setCongtyId(ctyId);
			nkmBean.createListKhoanMucChiPhi(); 
			nkmBean.createRsDonViThucHien();
			//lnccBean.setNppId(nppId);
			session.setAttribute("nkmBean", nkmBean);
			nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhomKhoanMucNew.jsp";
		}
		if (action.equals("Search"))
		{
			System.out.println("Search ");
			obj.setMa(ma);
			obj.setTen(ten);
			obj.Init();
			session.setAttribute("obj", obj);
			nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhomKhoanMuc.jsp";
		}
		response.sendRedirect(nextJSP);
	}
}
